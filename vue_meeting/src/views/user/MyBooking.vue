 <template>
  <div class="table-wrapper">
    <el-table :data="tableData" style="width: 100%" border header-row-class-name="header">
      <el-table-column label="会议室" prop="roomName" align="center">
      </el-table-column>
      <el-table-column label="预约时间" prop="createTime" align="center">
      </el-table-column>
      <el-table-column label="会议主题" prop="title" align="center">
      </el-table-column>
      <el-table-column label="审核人" prop="managerId" align="center">
      </el-table-column>
      <el-table-column label="状态" prop="state" align="center">
      </el-table-column>
      <el-table-column label="备注" prop="refuseReason" align="center">
      </el-table-column>
      <el-table-column label="操作" align="center" width="180">
        <template slot-scope="scope">
          <div v-if="tableData[scope.$index].state==='已批准'" class="btn-wrapper">
            <el-button type="primary" class="btn" style="background-color:#3ccc97;" @click="handleLook(scope.row)">查看</el-button>
            <el-button type="primary" class="btn" style="background-color:#3cafcc;" @click="handleDelete(scope.$index, scope.row)">编辑</el-button>
            <el-button type="primary" disabled class="btn" style="background-color:#f5856d;" @click="handleDelete(scope.$index, scope.row)">取消</el-button>
          </div>
          <div v-else-if="tableData[scope.$index].state==='已拒绝'">
            <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">查看</el-button>
            <el-button size="mini" type="danger" disabled @click="handleDelete(scope.$index, scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">取消</el-button>
          </div>
          <div v-else-if="tableData[scope.$index].state==='待审核'">
            <el-button size="mini" @click="centerDialogVisible=true">查看</el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">取消</el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog title="会议信息" :visible.sync="centerDialogVisible" width="50%" center>
      <div>
        <div style="display:flex;justify-content:space-between">
          <div>
            <p>会议室：{{checkRow.roomName}}</p>
            <p>会议主题：{{checkRow.title}}</p>
          </div>
          <div>
            <p>预约日期：{{checkRow.createTime}}</p>
            <p>状态：{{checkRow.state}}</p>
          </div>
        </div>
        <div>确认参加
          <i class="el-icon-caret-bottom"></i>
          <span>{{totalAccept}}</span>
          <p>{{AcceptPeople}}</p>
        </div>
        <div>请假
          <i class="el-icon-caret-bottom"></i>
          <span>{{totalRefuse}}</span>
          <p>{{RefusePeople}}</p>
        </div>
        <div>未响应
          <i class="el-icon-caret-bottom"></i>
          <span>{{totalUnanswer}}</span>
          <p>{{UnanswerPeople}}</p>
        </div>
      </div>
    </el-dialog>
  </div>

</template>

<script>
import { getBooking } from '@/api/myBooking'
import moment from 'moment'

export default {
  data() {
    return {
      tableData: [
      ],
      centerDialogVisible: false,
      checkRow: ''
    }
  },
  computed: {
    totalAccept: function () {
      if (this.checkRow.accept) {
        return this.checkRow.accept.length
      } else {
        return ''
      }
    },
    AcceptPeople: function () {
      if (this.checkRow.accept) {
        return this.checkRow.accept.join(',')
      } else {
        return ''
      }
    },
    totalRefuse: function () {
      if (this.checkRow.refuse) {
        return this.checkRow.refuse.length
      } else {
        return ''
      }
    },
    RefusePeople: function () {
      if (this.checkRow.refuse) {
        return this.checkRow.refuse.join(',')
      } else {
        return ''
      }
    },
    totalUnanswer: function () {
      if (this.checkRow.unanswer) {
        return this.checkRow.unanswer.length
      } else {
        return ''
      }
    },
    UnanswerPeople: function () {
      if (this.checkRow.unanswer) {
        return this.checkRow.unanswer.join(',')
      } else {
        return ''
      }
    }
  },
  methods: {
    handleLook(row) {
      this.checkRow = row
      this.centerDialogVisible = true
    },
    handleEdit(index, row) {
      console.log(index, row);
    },
    handleDelete(index, row) {
      console.log(index, row);
    },
    handletest(index) {
      console.log(index);
    }
  },
  mounted: function () {
    getBooking().then(
      response => {
        let a = response.data.reduce(
          (total, cur) => {
            if (total[cur.m.id]) {
              switch (cur.state) {
                case 0:
                  total[cur.m.id].unanswer = total[cur.m.id].unanswer.concat(cur.u.name)
                  break
                case 1:
                  total[cur.m.id].accept = total[cur.m.id].accept.concat(cur.u.name)
                  break
                case 2:
                  total[cur.m.id].refuse = total[cur.m.id].refuse.concat(cur.u.name)
                  break
              }
            } else {
              total[cur.m.id] = cur.m
              total[cur.m.id].accept = []
              total[cur.m.id].refuse = []
              total[cur.m.id].unanswer = []
              switch (cur.state) {
                case 0:
                  total[cur.m.id].unanswer = [cur.u.name]
                  break
                case 1:
                  total[cur.m.id].accept = [cur.u.name]
                  break
                case 2:
                  total[cur.m.id].refuse = [cur.u.name]
                  break
              }
            }
            return total
          }, {}
        )
        let b = []
        Object.keys(a).forEach(key => { b = b.concat(a[key]) })
        b.forEach(element => {
          let c = []
          element.rooms.forEach(item => {
            c = c.concat(item.roomName)
          })
          element.roomName = c.join(',')
          element.createTime = moment(element.createTime.time).format('YYYY-MM-D HH:mm')
          if (element.state === 0) {
            element.state = '待审核'
          } else if (element.state === 1) {
            element.state = '已批准'
          } else if (element.state === 2) {
            element.state = '已拒绝'
          }
        })
        this.tableData = b
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
  