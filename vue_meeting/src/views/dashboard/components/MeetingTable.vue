<template>
  <div>
    <div class="meeting-table-wrapper" v-show="status.showState==='日'">
      <el-table :data="tableData" border style="width: 100%" :cell-style="handleColor">
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
        <el-table-column v-for="day in days" :label="day" :key="day" align="center" label-class-name='meeting-table-header' class-name="meeting-table-column">
          <template slot-scope="scope">
            <el-button @click="handleEdit(scope.row.roomName)" class="meeting-btn" />
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
            <el-button @click="handleEdit(scope.$index, scope.row)" class="meeting-btn" />
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
        '星期一',
        '星期二',
        '星期三',
        '星期四',
        '星期五',
        '星期六',
        '星期日'
      ],
      months: [
        '12-4',
        '12-5',
        '12-6',
        '12-7',
        '12-8',
        '12-9',
        '12-10',
        '12-11',
        '12-12',
        '12-13',
        '12-14',
        '12-15',
        '12-16',
      ],
      status: this.$store.state.dashboard
    }
  },
  methods: {
    handleSource() {
      let a = {}
      let d = this.source.filter(item => {
        if (moment(item.startTime).isAfter(this.$store.state.dashboard.date) && moment(item.startTime).isBefore(moment(this.$store.state.dashboard.date).add(1, 'days'))) {
          return true
        } else {
          return false
        }
      })
      console.log(d);
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
      getMeetingRoom().then(
        response => {
          console.log(a);
          response.data.map(item => {
            if (a[item.roomName]) {
              return Object.assign(item, a[item.roomName])
            } else {
              return item
            }
          })
          this.tableData = response.data
          console.log(this.tableData);
        }).catch(
          error => { console.log(error); }
        )
    },
    handleColor({ row, column, rowIndex, columnIndex }) {
      if (row.roomName === '第一会议室' && columnIndex === 4) {
        return 'background-color:blue'
      }
      else {
        return ''
      }
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
    handleEdit(row, col) {
      console.log(row);
      col = col.split("-")
      col = col.map(item => parseInt(item))
      console.log(this.$store.state.dashboard.date.format('DD'));
      this.$store.commit("CHANGE_ROOMNAME", row.roomName)
      console.log(this.$store.state.dashboard.date.format('DD'));
      this.$store.commit("CHANGE_TIME", col)
      this.$store.commit("CHANGE_EQUIPMENTS", row.equipments)
      this.$store.commit("CHANGE_CAPACITY", row.capacity)
      this.$store.commit("CHANGE_ROOMID", row.id)
      this.$router.push({ path: 'booking' })
      // let { href } = this.$router.resolve({ path: 'booking' });
      // window.open(href, '_blank');
    }
  },
  computed: {
    getDate() {
      return this.$store.state.dashboard.date
    }
  },
  watch: {
    getDate() {
      this.handleSource()
    }
  },
  mounted: function () {
    console.log(333);
    getMeeting().then(
      response => {
        this.source = response.data
        this.handleSource()
      }
    ).catch(
      error => { console.log(error); }
    )
    let es = new EventSource('/api/es')
    es.onmessage = function (event) {
      this.listData = JSON.parse(event.data);
      console.log(this.listData);
    }
    es.onerror = function (event) {
      console.log(event);
    }
    // Object.keys(a).forEach(key => { b = b.concat([Object.assign({}, a[key], { roomName: key })]) })
  }



}
</script>

<style >
.meeting-table-header {
  font-size: 10px;
}
.meeting-table-wrapper {
  margin-left: 20px;
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

