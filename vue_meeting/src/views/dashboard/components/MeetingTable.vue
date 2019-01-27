<template>
  <div>
    <div class="meeting-table-wrapper" v-show="status.showState==='日'">
      <el-table :data="tableData" border style="width: 100%">
        <el-table-column prop="roomName" label="会议室/时间" width="91" align="center" label-class-name='meeting-table-header'>
        </el-table-column>
        <el-table-column v-for="time in times" :label="time" :key="time" align="center" label-class-name='meeting-table-header' class-name="meeting-table-column">
          <template slot-scope="scope">
            <el-button @click="handleEdit(scope.row,scope.column.label)" class="meeting-btn" :style="handleStyle(scope)">
              {{scope.row[scope.column.label]?scope.row[scope.column.label].split('-')[0]:''}}
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="meeting-table-wrapper" v-show="status.showState==='周'">
      <el-table :data="tableData" border style="width: 100%">
        <el-table-column prop="roomName" label="会议室/时间" align="center" label-class-name='meeting-table-header'>
        </el-table-column>
        <el-table-column v-for="day in days" :label="day.label" :key="day.label" align="center" label-class-name='meeting-table-header' class-name="meeting-table-column">
          <template slot-scope="scope">
            <p v-for="item in scope.row[day.value]" :key="item.id" :style="handleWeekStyle(item)">{{item.title}}&nbsp;预约:{{item.createBy}}</p>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="meeting-table-wrapper" v-show="status.showState==='月'">
      <el-table :data="tableData" border style="width: 100%">
        <el-table-column prop="roomName" label="会议室/时间" align="center" label-class-name='meeting-table-header'>
        </el-table-column>
        <el-table-column v-for="month in months" :label="month" :key="month" align="center" label-class-name='meeting-table-header' class-name="meeting-table-column">
          <template slot-scope="scope">
            <p v-for="item in scope.row[month]" :key="item.id" :style="handleWeekStyle(item)">{{item.title}}&nbsp;预约:{{item.createBy}}</p>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import { getMeetingRoom } from '@/api/meetingRoom'
import { getMeeting } from '@/api/meeting'
import moment from 'moment';
export default {
  data() {
    return {
      tableData: [],
      times: [
        '08:00-09:00',
        '09:00-10:00',
        '10:00-11:00',
        '11:00-12:00',
        '12:00-13:00',
        '13:00-14:00',
        '14:00-15:00',
        '15:00-16:00',
        '16:00-17:00',
        '17:00-18:00',
        '18:00-19:00',
        '19:00-20:00'
      ],
      days: [
        { label: '星期一', value: 'one' },
        { label: '星期二', value: 'two' },
        { label: '星期三', value: 'three' },
        { label: '星期四', value: 'four' },
        { label: '星期五', value: 'five' },
        { label: '星期六', value: 'six' },
        { label: '星期日', value: 'seven' }
      ],
      months: [],
      status: this.$store.state.dashboard
    }
  },
  methods: {
    handleSource() {
      let a = {}
      let d = []
      d = this.source.filter(item => {
        if (moment(item.startTime).isAfter(this.$store.state.dashboard.date) && moment(item.startTime).isBefore(moment(this.$store.state.dashboard.date).add(1, 'days'))) {
          return true
        } else {
          return false
        }
      })
      let b = [];
      d.forEach(item => {
        this.times.filter(time => {
          if (time.split('-').map(point => parseInt(point))[0] >= new Date(item.startTime).getHours() && time.split('-').map(point => parseInt(point))[0] < new Date(item.endTime).getHours()) {
            return true
          } else {
            return false
          }
        }).forEach(s => {
          item.rooms.forEach(room => {
            if (a[room.roomName]) {
              a[room.roomName][s] = item.title + '-' + item.state
            } else {
              a[room.roomName] = {}
              a[room.roomName][s] = item.title + '-' + item.state
            }
          })
        })
      })
      this.handleMeetingRoom(a)
    },
    handleWeekData() {
      let a = {}
      let one = this.getOneDayMeeting(0)
      let two = this.getOneDayMeeting(1)
      let three = this.getOneDayMeeting(2)
      let four = this.getOneDayMeeting(3)
      let five = this.getOneDayMeeting(4)
      let six = this.getOneDayMeeting(5)
      let seven = this.getOneDayMeeting(6)
      this.oneDayMeetingToRoom(one, 'one', a)
      this.oneDayMeetingToRoom(two, 'two', a)
      this.oneDayMeetingToRoom(three, 'three', a)
      this.oneDayMeetingToRoom(four, 'four', a)
      this.oneDayMeetingToRoom(five, 'five', a)
      this.oneDayMeetingToRoom(six, 'six', a)
      this.oneDayMeetingToRoom(seven, 'seven', a)
      this.handleMeetingRoom(a);
    },
    handleMonthData() {
      let a = {}
      for (let i = 0; i < moment().daysInMonth(); i++) {
        this.oneDayInMonth(i, a)
      }
      this.handleMeetingRoom(a);
    },
    handleMeetingRoom(a) {
      getMeetingRoom().then(
        response => {
          let roomfilter = response.data.filter(item => {
            if (item.roomName.indexOf(this.$store.state.dashboard.roomFilter.name) !== -1 &&
              (item.capacity === this.$store.state.dashboard.roomFilter.capacity || this.$store.state.dashboard.roomFilter.capacity === '')) {
              if (this.$store.state.dashboard.roomFilter.equipments.every(equipment => {
                if (item.equipments) return item.equipments.indexOf(equipment) !== -1
                else return false
              })) {
                return true
              } else {
                return false
              }
            } else {
              return false
            }
          })
          roomfilter.map(item => {
            if (a[item.roomName]) {
              return Object.assign(item, a[item.roomName])
            } else {
              return item
            }
          })
          this.tableData = roomfilter
          console.log(this.tableData);
        }).catch(
          error => { console.log(error); }
        )
    },
    handleStyle(scope) {
      let a = scope.row[scope.column.label]
      if (a) {
        a = a.split('-')
        if (a[1] === '1') {
          return { backgroundColor: '#3cafcc', color: 'white', textAlign: 'center', fontSize: '12px' }
        } else if (a[1] === '0') {
          return { backgroundColor: '#f5856d', color: 'white', textAlign: 'center', fontSize: '12px' }
        } else if (a[1] === '2') {
          return { backgroundColor: '#c6c6c6', color: 'white', textAlign: 'center', fontSize: '12px' }
        }
      }
    },
    handleWeekStyle(item) {
      let a = item.state
      if (a === 0) {
        return { backgroundColor: '#f5856d', color: 'white', textAlign: 'center', fontSize: '12px' }
      } else if (a === 1) {
        return { backgroundColor: '#3cafcc', color: 'white', textAlign: 'center', fontSize: '12px' }
      } else if (a === 2) {
        return { backgroundColor: '#c6c6c6', color: 'white', textAlign: 'center', fontSize: '12px' }
      }
    },
    handleEdit(row, col) {
      col = col.split("-")
      col = col.map(item => parseInt(item))
      this.$store.commit("CHANGE_ROOMNAME", row.roomName)
      this.$store.commit("CHANGE_TIME", col)
      this.$store.commit("CHANGE_EQUIPMENTS", row.equipments)
      this.$store.commit("CHANGE_CAPACITY", row.capacity)
      this.$store.commit("CHANGE_ROOMID", row.id)
      this.$router.push({ path: 'booking' })
      // let { href } = this.$router.resolve({ path: 'booking' });
      // window.open(href, '_blank');
    },
    getOneDayMeeting(day) {
      return this.source.filter(item => {
        if (moment(item.startTime).isAfter(moment().startOf('isoWeek').add(day, 'days')) && moment(item.startTime).isBefore(moment().startOf('isoWeek').add(day + 1, 'days'))) {
          return true
        } else {
          return false
        }
      })
    },
    oneDayMeetingToRoom(data, day, a) {
      data.forEach(item => {
        item.rooms.forEach(room => {
          if (a[room.roomName]) {
            if (a[room.roomName][day]) {
              a[room.roomName][day] = a[room.roomName][day].concat({ title: item.title, createBy: item.createBy.name, state: item.state, id: item.id })
            } else {
              a[room.roomName][day] = [{ title: item.title, createBy: item.createBy.name, state: item.state, id: item.id }]
            }
          } else {
            a[room.roomName] = {}
            a[room.roomName][day] = [{ title: item.title, createBy: item.createBy.name, state: item.state, id: item.id }]
          }
        })
      })
    },
    oneDayInMonth(day, a) {
      let oneDay = this.source.filter(item => {
        if (moment(item.startTime).isAfter(moment().startOf('month').add(day, 'days')) && moment(item.startTime).isBefore(moment().startOf('month').add(day + 1, 'days'))) {
          return true
        } else {
          return false
        }
      })
      let dayStr = moment().startOf('month').add(day, 'days').format("M-D")
      oneDay.forEach(item => {
        item.rooms.forEach(room => {
          if (a[room.roomName]) {
            if (a[room.roomName][dayStr]) {
              a[room.roomName][dayStr] = a[room.roomName][dayStr].concat({ title: item.title, createBy: item.createBy.name, state: item.state, id: item.id })
            } else {
              a[room.roomName][dayStr] = [{ title: item.title, createBy: item.createBy.name, state: item.state, id: item.id }]
            }
          } else {
            a[room.roomName] = {}
            a[room.roomName][dayStr] = [{ title: item.title, createBy: item.createBy.name, state: item.state, id: item.id }]
          }
        })
      })
    }
  },
  computed: {
    getDate() {
      return this.$store.state.dashboard.date
    },
    getFilter() {
      return this.$store.state.dashboard.roomFilter
    },
    getShow() {
      return this.$store.state.dashboard.showState
    }
  },
  watch: {
    getDate() {
      this.handleSource()
    },
    getFilter() {
      if (this.getShow === '日') {
        this.handleSource()
      } else if (this.getShow === '周') {
        this.handleWeekData()
      } else if (this.getShow === '月') {
        this.handleMonthData()
      }
    },
    getShow() {
      if (this.getShow === '周') {
        this.handleWeekData()
      } else if (this.getShow === '月') {
        this.handleMonthData()
      }
    }

  },
  mounted: function () {
    for (let i = 0; i < moment().daysInMonth(); i++) {
      this.months = this.months.concat(moment().startOf('month').add(i, 'days').format("M-D"))
    }
    getMeeting().then(
      response => {
        this.source = response.data
        this.handleSource()
      }
    ).catch(
      error => { console.log(error); }
    )
    // Object.keys(a).forEach(key => { b = b.concat([Object.assign({}, a[key], { roomName: key })]) })
  }



}
</script>

<style >
.meeting-table-header {
  font-size: 10px;
}
.meeting-table-wrapper {
  margin-left: 10px;
  margin-top: 20px;
}
.meeting-btn {
  border: 0;
  border-radius: 0;
  width: 100%;
  height: 47px;
}
.meeting-table-column {
  padding: 0 !important;
}
.cell {
  padding: 0 0 !important;
}
</style>

