<template>
  <div>
    <div class="meeting-table-wrapper" v-show="status.showState==='日'">
      <el-table :data="tableData" border style="width: 100%">
        <el-table-column prop="roomName" label="会议室/时间" width="91" align="center" label-class-name='meeting-table-header'>
        </el-table-column>
        <el-table-column v-for="time in times" :label="time" :key="time" align="center" label-class-name='meeting-table-header' class-name="meeting-table-column">
          <template slot-scope="scope">
            <el-button @click="handleEdit(scope.row,scope.column.label)" class="meeting-btn" />
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
    handleEdit(row, col) {
      console.log(row);
      col = col.split("-")
      col = col.map(item => parseInt(item))
      console.log(col);
      this.$store.commit("CHANGE_ROOMNAME", row.roomName)
      this.$store.commit("CHANGE_TIME", col)
      this.$store.commit("CHANGE_EQUIPMENTS", row.equipments)
      this.$store.commit("CHANGE_CAPACITY", row.capacity)
      this.$router.push({ path: 'booking' })
    }
  },
  mounted: function () {
    getMeetingRoom().then(
      response => {
        this.tableData = response.data      }
    ).catch(
      error => { console.log(error); }
    )
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

