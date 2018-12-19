 <template>
  <div class="table-wrapper">
    <el-button type="primary" style="margin-bottom:30px" @click="handleChange2">新增会议室</el-button>
    <el-table :data="tableData" style="width: 100%" border header-row-class-name="header">
      <el-table-column label="会议室" prop="roomName" align="center">
      </el-table-column>
      <el-table-column label="客容量" prop="capacity" align="center">
      </el-table-column>
      <el-table-column label="设备" prop="equipments" align="center">
      </el-table-column>
      <el-table-column label="地点" prop="location" align="center">
      </el-table-column>
      <el-table-column label="会议室状态" prop="roomIsOpen" align="center">
        <template slot-scope="scope">
          <el-switch v-model="tableData[scope.$index].roomIsOpen" active-color="#3cafcc" inactive-color="#d0cfcf">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="自动审核" prop="auto" align="center">
        <template slot-scope="scope">
          <el-switch v-model="tableData[scope.$index].roomIsOpen" active-color="#3cafcc" inactive-color="#d0cfcf">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <div class="btn-wrapper">
            <el-button type="primary" class="btn" style="background-color:#3cafcc;" @click="handleChange1">编辑</el-button>
            <el-button type="primary" class="btn" style="background-color:#f5856d;" @click="handleDeleteFirst(scope.row.roomId)">删除</el-button>
          </div>
          <el-dialog :modal=false :title="dialogTitle" :visible.sync="addDialogVisible" fullscreen class="addDialog">
            <el-form :model="form" label-position="left">
              <el-form-item label="会议室名称" :label-width="formLabelWidth">
                <el-input v-model="form.roomName"></el-input>
              </el-form-item>
              <el-form-item label="客容量" :label-width="formLabelWidth">
                <el-input v-model="form.capacity"></el-input>
              </el-form-item>
              <el-form-item label="地点" :label-width="formLabelWidth">
                <el-input v-model="form.location"></el-input>
              </el-form-item>
              <!-- <el-form-item label="管理员" :label-width="formLabelWidth">
                <el-input v-model="form.managerId"></el-input>
              </el-form-item> -->
              <el-form-item label="设备">
                <el-checkbox-group v-model="form.equipments">
                  <el-checkbox label="白板"></el-checkbox>
                  <el-checkbox label="远程视频"></el-checkbox>
                  <el-checkbox label="投影仪"></el-checkbox>
                  <el-checkbox label="麦克风"></el-checkbox>
                </el-checkbox-group>
              </el-form-item>
            </el-form>
            <div slot="footer" class="add-footer">
              <el-button type="primary" @click="handleAdd">确 定</el-button>
              <el-button @click="addDialogVisible = false">取 消</el-button>
            </div>
          </el-dialog>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog title="提示" :visible.sync="deleteDialogVisible" width="30%" center>
      <span>是否要删除此会议室</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="deleteDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleDelete()">确 定</el-button>
      </span>
    </el-dialog>

  </div>

</template>

<script>
import { addMeetingRoom, getMeetingRoom, deleteMeetingRoom } from '@/api/meetingRoom';

export default {
  data() {
    return {
      tableData: [],
      addDialogVisible: false,
      deleteDialogVisible: false,
      formLabelWidth: '100px',
      form: {
        roomName: '',
        capacity: '',
        managerId: 1,
        location: '',
        equipments: []
      },
      dialogTitle: '新增会议室'
    }
  },
  // computed:{
  //   totalEquipments: function () {     
  //     return this.form.equipments.join(',')
  //   }
  // },
  methods: {
    handleEdit(index, row) {
      console.log(index, row);
    },
    handleDelete() {
      deleteMeetingRoom(this.id).then(
        response => {
          this.$message({
            message: '删除成功',
            type: 'success'
          })
          getMeetingRoom().then(
            response => {
              this.tableData = response.data            }
          ).catch(
            error => { console.log(error); }
          )
        }
      ).catch(
        error => { console.log(error); }
      )
      this.deleteDialogVisible = false
    },
    handleDeleteFirst(row) {
      this.id = row
      this.deleteDialogVisible = true
    },
    handleChange1() {
      this.dialogTitle = "编辑会议室"
      this.addDialogVisible = true
    },
    handleChange2() {
      this.dialogTitle = "新增会议室"
      this.addDialogVisible = true
    },
    handleAdd() {
      addMeetingRoom({ ...this.form, equipments: this.form.equipments.join(",") }).then(
        response => {
          this.$message({
            message: '添加成功',
            type: 'success'
          })
          getMeetingRoom().then(
            response => {
              this.tableData = response.data            }
          ).catch(
            error => { console.log(error); }
          )
        }
      ).catch(
        error => { this.$message.error(error) }
      )
      this.addDialogVisible = false
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
  justify-content: center;
  margin: 0 10px;
}
.addDialog {
  height: 92%;
  width: 30%;
  position: fixed;
  right: 0;
  left: auto !important;
  top: 80px;
  box-shadow: -5px 0px 5px 0px rgba(16, 5, 10, 0.1);
}
.add-footer {
  display: flex;
  justify-content: space-around;
}
</style>
  