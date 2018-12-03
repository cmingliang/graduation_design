<template>
    <div class="meeting-table-wrapper">
        <el-table
        :data="tableData"
        border
        style="width: 100%">
        <el-table-column
        prop="meetingroom"
        label="会议室/时间"
        width="91"
        align="center"
        label-class-name='meeting-table-header'>
        </el-table-column>
        <el-table-column
        v-for="time in times"
        :label="time"
        :key="time"
        width="91"
        align="center"
        label-class-name='meeting-table-header'
        class-name="meeting-table-column"
        >
            <template slot-scope="scope">
            <el-button
            @click="handleEdit(scope.$index, scope.row)"
            class="meeting-btn"/>
            </template>
        </el-table-column>
        </el-table>
    </div>
</template>

<script>
  export default {
    data() {
      return {
        tableData: [{
          meetingroom:'第一会议室'
        }, {
          meetingroom:'第二会议室'
        }, {
          meetingroom:'第三会议室'
        }, {
          meetingroom:'第四会议室'
        },{
          meetingroom:'第五会议室'
        },{
          meetingroom:'第六会议室'
        },{
          meetingroom:'第七会议室'
        }],
        times:[
            '8:00-9:00',
            '9:00-10:00',
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
        ]
      }
    },
    methods: {
      handleEdit(index, row) {
        console.log(index, row);
      }
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
        border:0;
        border-radius: 0;
        width: 90px;
        height: 47px;
    }
    .meeting-table-column {
        padding: 0 !important;
    }
    .cell {
        padding:0 0 !important; 
    }
        
</style>

