 <template>
  <div class="table-wrapper">
    <el-table :data="tableData" style="width: 100%" border header-row-class-name="header">
      <el-table-column label="会议室" prop="roomName" align="center">
      </el-table-column>
      <el-table-column label="开始时间" prop="startTime" align="center">
      </el-table-column>
      <el-table-column label="结束时间" prop="endTime" align="center">
      </el-table-column>
      <el-table-column label="会议主题" prop="title" align="center">
      </el-table-column>
      <el-table-column label="预约人" prop="createBy" align="center">
      </el-table-column>
      <el-table-column label="操作" align="center" width="180">
        <template slot-scope="scope">
          <div v-if="tableData[scope.$index].state===1">
            <el-button type="primary" class="btn" style="background-color:rgba(60, 175, 204, 0.5);" disabled>已批准</el-button>
          </div>
          <div v-else-if="tableData[scope.$index].state===2">
            <el-button type="primary" class="btn" style="background-color: rgba(245, 133, 109, 0.5);" disabled>已拒绝</el-button>
          </div>
          <div v-else-if="tableData[scope.$index].state===0">
            <el-button type="primary" class="btn" style="background-color:#3cafcc;" @click="handleOk(scope.row)">批准</el-button>
            <el-button type="primary" class="btn" style="background-color:#f5856d;" @click="handleNo(scope.row)">拒绝</el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog title="会议信息" :visible.sync="okDialogVisible" width="50%" center>
      <div>
        <div style="display:flex;justify-content:space-between">
          <div>
            <p>会议室：{{checkData.roomName}}</p>
            <p>会议主题：{{checkData.title}}</p>
          </div>
          <div>
            <p>预约日期：{{checkData.createTime}}</p>
            <p>预约人：{{checkData.createBy}}</p>
          </div>
        </div>
        <span>批准此会议？</span>
        <el-button type="primary" @click="handleEdit(checkData.id)">确定</el-button>
        <el-button type="primary" @click="okDialogVisible=false">取消</el-button>
      </div>
    </el-dialog>
    <el-dialog title="会议信息" :visible.sync="refuseDialogVisible" width="50%" center>
      <div>
        <div style="display:flex;justify-content:space-between">
          <div>
            <p>会议室：{{checkData.roomName}}</p>
            <p>会议主题：{{checkData.title}}</p>
          </div>
          <div>
            <p>预约日期：{{checkData.createTime}}</p>
            <p>预约人：{{checkData.createBy}}</p>
          </div>
        </div>
        <span>拒绝此会议？</span>
        <el-input type="textarea" :rows="2" placeholder="请输入拒绝原因" v-model="reason">
        </el-input>
        <el-button type="primary" @click="handleRefuse(checkData.id)">确定</el-button>
        <el-button type="primary" @click="refuseDialogVisible=false">取消</el-button>
      </div>
    </el-dialog>
  </div>

</template>

  <script>
import { getMeetingCheck, acceptMeeting, refuseMeeting } from '@/api/meetingCheck'
import moment from 'moment'
export default {
  data() {
    return {
      tableData: [],
      okDialogVisible: false,
      refuseDialogVisible: false,
      reason: '',
      checkData: ''
    }
  },
  methods: {
    handleOk(row) {
      this.okDialogVisible = true
      this.checkData = row
    },
    handleNo(row) {
      console.log(row);
      this.refuseDialogVisible = true
      this.checkData = row
    },
    handleResponse() {
      getMeetingCheck().then(
        response => {
          response.data.map(item => {
            let a = []
            item.startTime = moment(item.startTime).format('YYYY-MM-D HH:mm')
            item.endTime = moment(item.endTime).format('YYYY-MM-D HH:mm')
            item.createTime = moment(item.createTime).format('YYYY-MM-D HH:mm')
            item.rooms.forEach(element => { a = a.concat(element.roomName) });
            item.roomName = a.join(',')
            item.createBy = item.createBy.name
            return item
          })
          response.data.reverse()
          this.tableData = response.data
        }
      ).catch(
        error => { console.log(error); }
      )
    },
    handleEdit(id) {
      acceptMeeting(id).then(
        response => {
          this.$message({
            message: '已批准该会议',
            type: 'success'
          })
          this.handleResponse()
        }
      ).catch(
        error => { console.log(error); }
      )
      this.okDialogVisible = false
    },
    handleRefuse(id) {
      refuseMeeting({ id, refuseReason: this.reason }).then(
        response => {
          this.$message({
            message: '已拒绝该会议',
            type: 'success'
          })
          this.handleResponse()
        }
      ).catch(
        error => { console.log(error); }
      )
      this.refuseDialogVisible = false
    }
  },
  mounted: function () {
    this.handleResponse()
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
  