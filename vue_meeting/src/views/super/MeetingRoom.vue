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
      <el-table-column label="负责人" prop="manager.name" align="center">
      </el-table-column>
      <el-table-column label="会议室状态" prop="roomIsOpen" align="center">
        <template slot-scope="scope">
          <el-switch v-model="tableData[scope.$index].roomIsOpen" active-color="#3cafcc" inactive-color="#d0cfcf" @change="handleOpen(scope.row)">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="自动审核" prop="auto" align="center">
        <template slot-scope="scope">
          <el-switch v-model="tableData[scope.$index].auto" active-color="#3cafcc" inactive-color="#d0cfcf" @change="handleAuto(scope.row)">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <div class="btn-wrapper">
            <el-button type="primary" class="btn" style="background-color:#3cafcc;" @click="handleEditFirst(scope.row)">编辑</el-button>
            <el-button type="primary" class="btn" style="background-color:#f5856d;" @click="handleDeleteFirst(scope.row.id)">删除</el-button>
          </div>
          <el-dialog :modal=false title="新增会议室" :visible.sync="addDialogVisible" fullscreen class="addDialog">
            <el-form :model="form" label-position="left" :rules="rules">
              <el-form-item label="会议室名称" :label-width="formLabelWidth" prop="roomName">
                <el-input v-model="form.roomName"></el-input>
              </el-form-item>
              <el-form-item label="客容量" :label-width="formLabelWidth" prop="capacity">
                <el-input v-model.number="form.capacity"></el-input>
              </el-form-item>
              <el-form-item label="地点" :label-width="formLabelWidth" prop="location">
                <el-input v-model="form.location"></el-input>
              </el-form-item>
              <el-form-item label="管理员" :label-width="formLabelWidth" prop="manager">
                <el-select v-model="form.manager" filterable placeholder="请选择" style="width:232px">
                  <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
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
    <el-dialog :modal=false title="编辑会议室" :visible.sync="editDialogVisible" fullscreen class="addDialog">
      <el-form :model="editForm" label-position="left">
        <el-form-item label="会议室名称" :label-width="formLabelWidth">
          <el-input v-model="editForm.roomName"></el-input>
        </el-form-item>
        <el-form-item label="客容量" :label-width="formLabelWidth">
          <el-input v-model.number="editForm.capacity"></el-input>
        </el-form-item>
        <el-form-item label="地点" :label-width="formLabelWidth">
          <el-input v-model="editForm.location"></el-input>
        </el-form-item>
        <el-form-item label="管理员" :label-width="formLabelWidth">
          <el-select v-model="editForm.manager" filterable placeholder="请选择" style="width:232px">
            <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="设备">
          <el-checkbox-group v-model="editForm.equipments">
            <el-checkbox label="白板"></el-checkbox>
            <el-checkbox label="远程视频"></el-checkbox>
            <el-checkbox label="投影仪"></el-checkbox>
            <el-checkbox label="麦克风"></el-checkbox>
          </el-checkbox-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="add-footer">
        <el-button type="primary" @click="handleEdit">确 定</el-button>
        <el-button @click="editDialogVisible = false">取 消</el-button>
      </div>
    </el-dialog>

  </div>

</template>

<script>
import { addMeetingRoom, getMeetingRoom, deleteMeetingRoom, updateMeetingRoom, getUser } from '@/api/meetingRoom';

export default {
  data() {
    return {
      tableData: [],
      addDialogVisible: false,
      deleteDialogVisible: false,
      editDialogVisible: false,
      formLabelWidth: '100px',
      form: {
        roomName: '',
        capacity: '',
        manager: '',
        location: '',
        equipments: []
      },
      editForm: {
        id: '',
        roomName: '',
        capacity: '',
        manager: '',
        location: '',
        equipments: [],
        auto: true,
        roomIsOpen: true,
      },
      options: '',
      rules: {
        roomName: [
          { required: true, message: '请输入会议室名称', trigger: 'blur' },
        ],
        capacity: [
          { required: true, message: '请输入课容量', trigger: 'blur' },
          { type: 'number', message: '年龄必须为数字值' }
        ],
        manager: [
          { required: true, message: '请选择会议室负责人', trigger: 'change' },
        ],
        location: [
          { required: true, message: '请输入会议室地址', trigger: 'blur' },
        ]
      }
    }
  },
  // computed:{
  //   totalEquipments: function () {     
  //     return this.form.equipments.join(',')
  //   }
  // },
  methods: {
    handleOpen(row) {
      updateMeetingRoom(row).then(
        response => {
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
    },
    handleAuto(row) {
      updateMeetingRoom(row).then(
        response => {
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
    },
    handleEditFirst(row) {
      this.editForm.id = row.id
      this.editForm.roomName = row.roomName
      this.editForm.capacity = row.capacity
      this.editForm.manager = row.manager.name
      this.editForm.location = row.location
      this.editForm.auto = row.auto
      this.editForm.roomIsOpen = row.roomIsOpen
      this.editForm.equipments = row.equipments === null ? [] : row.equipments.split(',')
      this.editDialogVisible = true
    },
    handleEdit() {
      updateMeetingRoom({ ...this.editForm, equipments: this.editForm.equipments.join(","), manager: this.users.find(item => { return item.id = this.editForm.manager }) }).then(
        response => {
          this.$message({
            message: '编辑成功',
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
      this.editDialogVisible = false
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
        error => {
          this.$message.error({
            message: '此会议室还有预约的会议，无法删除',
            duration: 5000
          })
        }
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
      addMeetingRoom({ ...this.form, equipments: this.form.equipments.join(","), manager: this.users.find(item => { return item.id = this.form.manager }) }).then(
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
    getUser().then(
      response => {
        this.users = response.data
        this.options = response.data.map(
          item => {
            let b = {}
            b.label = item.name
            b.value = item.id
            return b
          }
        )
      }
    ).catch(
      error => { console.log(error); }
    )
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
  