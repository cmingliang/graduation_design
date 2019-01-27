 <template>
  <div class="table-wrapper">
    <el-table :data="tableData" style="width: 100%" border header-row-class-name="header">
      <el-table-column label="会议室" prop="roomName" align="center">
      </el-table-column>
      <el-table-column label="会议时间" align="center" width="140px">
        <template slot-scope="scope">
          {{tableData[scope.$index].startTime}}-{{tableData[scope.$index].endTime}}
        </template>
      </el-table-column>
      <el-table-column label="会议主题" prop="title" align="center">
      </el-table-column>
      <el-table-column label="主持人" prop="host.name" align="center">
      </el-table-column>
      <el-table-column label="状态" align="center">
        <template slot-scope="scope">
          <p :style="handleStateStyle(scope.row.leave)">{{scope.row.leave}}</p>
        </template>
      </el-table-column>
      <el-table-column label="会议说明" prop="description" align="center">
      </el-table-column>
    </el-table>
  </div>

</template>

  <script>
import { getJoin } from '@/api/myJoin'
import moment from 'moment'
export default {
  data() {
    return {
      tableData: [
      ],
      centerDialogVisible: false

    }
  },
  methods: {
    handleEdit(index, row) {
      console.log(index, row);
    },
    handleDelete(index, row) {
      console.log(index, row);
    },
    handletest(index) {
      console.log(index);
    },
    handleStateStyle(state) {
      switch (state) {
        case "请假":
          return { textAlign: 'center' }
        case "未开始":
          return { color: '#3cafcc', textAlign: 'center' }
        case "进行中":
          return { color: '#f5856d', textAlign: 'center' }
        case "未开始":
          return { textAlign: 'center' }
      }
    }
  },
  mounted: function () {
    getJoin().then(
      response => {
        let a = []
        response.data.forEach(element => {
          if (element.state === 2) {
            element.m.leave = true
          } else {
            element.m.leave = false
          }
          a = a.concat(element.m)
        })
        a.forEach(item => {
          let c = []
          item.rooms.forEach(room => {
            c = c.concat(room.roomName)
          })
          if (item.leave) {
            item.leave = '请假'
          } else if (Date.now() > item.startTime.time && Date.now() < item.endTime.time) {
            item.leave = '进行中'
          } else if (Date.now() > item.startTime.time && Date.now() > item.endTime.time) {
            item.leave = '已完成'
          } else if (Date.now() < item.startTime.time) {
            item.leave = '未开始'
          }
          item.roomName = c.join(',')
          item.startTime = moment(item.startTime.time).format('YYYY-MM-D HH:mm')
          item.endTime = moment(item.endTime.time).format('YYYY-MM-D HH:mm')
        })
        console.log(a);
        a.reverse()
        this.tableData = a
      }
    ).catch(
      error => { console.log(error); }
    )
  }
}
</script>

  <style>
.header {
  background-color: #f2f2f2 !important;
  color: #666666;
}
.table-wrapper {
  margin: 60px 40px;
}
.btn {
  width: 45px;
  height: 20px;
  border: 0;
  font-size: 12px;
  padding: 0 0;
}
.btn-wrapper {
  display: flex;
  justify-content: space-between;
  margin: 0 10px;
}
</style>
  