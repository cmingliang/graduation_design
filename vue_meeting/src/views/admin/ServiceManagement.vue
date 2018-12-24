 <template>
  <div class="table-wrapper">
    <el-button type="primary" style="margin-bottom:30px" @click="handleAddService">新增服务</el-button>
    <el-table :data="tableData" style="width: 100%" :span-method="arraySpanMethod" border header-row-class-name="header">
      <el-table-column label="类别" prop="type" align="center">
      </el-table-column>
      <el-table-column label="服务内容" prop="name" align="center">

      </el-table-column>
      <el-table-column label="价格" prop="price" align="center">
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <div class="btn-wrapper">
            <el-button type="primary" class="btn" style="background-color:#3cafcc;" @click="handleEditFirst(scope.row)">编辑</el-button>
            <el-button type="primary" class="btn" style="background-color:#f5856d;" @click="handleDeleteFirst(scope.row.id)">删除</el-button>
          </div>

        </template>
      </el-table-column>
    </el-table>
    <el-dialog :modal=false title="新增会议室" :visible.sync="addDialogVisible" fullscreen class="addDialog">
      <el-form :model="form" label-position="left">
        <el-form-item label="服务类型" :label-width="formLabelWidth">
          <el-select v-model="form.type" placeholder="请选择">
            <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="服务内容" :label-width="formLabelWidth">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="服务价格" :label-width="formLabelWidth">
          <el-input v-model="form.price"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="add-footer">
        <el-button type="primary" @click="handleAdd">确 定</el-button>
        <el-button @click="addDialogVisible = false">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog title="提示" :visible.sync="deleteDialogVisible" width="30%" center>
      <span>是否要删除此服务</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="deleteDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleDelete">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog :modal=false title="编辑会议室" :visible.sync="editDialogVisible" fullscreen class="addDialog">
      <el-form :model="editForm" label-position="left">
        <el-form-item label="服务类型" :label-width="formLabelWidth">
          <el-select v-model="editForm.type" placeholder="请选择">
            <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="服务内容" :label-width="formLabelWidth">
          <el-input v-model="editForm.name"></el-input>
        </el-form-item>
        <el-form-item label="服务价格" :label-width="formLabelWidth">
          <el-input v-model="editForm.price"></el-input>
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
import { addMeetingService, getMeetingService, deleteMeetingService, updateMeetingService } from '@/api/meetingService';
export default {
  data() {
    return {
      tableData: [],
      addDialogVisible: false,
      deleteDialogVisible: false,
      editDialogVisible: false,
      formLabelWidth: '100px',
      form: {
        name: '',
        price: '',
        type: ''
      },
      editForm: {
        name: '',
        price: '',
        type: ''
      },
      options: [{
        value: '果盘类',
        label: '果盘类'
      }, {
        value: '茶歇类',
        label: '茶歇类'
      }, {
        value: '饮品类',
        label: '饮品类'
      }],

    }
  },
  // computed:{
  //   totalEquipments: function () {     
  //     return this.tableData.equipments.join('、')
  //   }
  // },
  methods: {
    handleEdit() {
      updateMeetingService(this.editForm).then(
        response => {
          this.$message({
            message: '编辑成功',
            type: 'success'
          })
          getMeetingService().then(
            response => {
              let totalNumber = response.data.reduce((total, cur) => {
                let { type } = cur
                if (total[type] !== undefined) total[type]++
                else {
                  total[type] = 1;
                }
                return total
              }, {})
              this.totalNumber = totalNumber;
              response.data.sort((a, b) => {
                if (a.type > b.type) return 1
                else if (a.type === b.type) return 0
                else return -1
              })
              this.tableData = response.data
            }
          ).catch(
            error => { console.log(error); }
          )
        }
      ).catch(
        error => { console.log(error); }
      )
      this.editDialogVisible = false
    },
    handleEditFirst(row) {
      this.editForm = { ...row }
      this.editDialogVisible = true;
    },
    handleDelete() {
      console.log(111);
      deleteMeetingService(this.id).then(
        response => {
          this.$message({
            message: '删除成功',
            type: 'success'
          })
          getMeetingService().then(
            response => {
              let totalNumber = response.data.reduce((total, cur) => {
                let { type } = cur
                if (total[type] !== undefined) total[type]++
                else {
                  total[type] = 1;
                }
                return total
              }, {})
              this.totalNumber = totalNumber;
              response.data.sort((a, b) => {
                if (a.type > b.type) return 1
                else if (a.type === b.type) return 0
                else return -1
              })
              this.tableData = response.data
            }
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
    handleAdd() {
      console.log(this.form);
      addMeetingService(this.form).then(
        response => {
          this.$message({
            message: '添加成功',
            type: 'success'
          })
          getMeetingService().then(
            response => {
              let totalNumber = response.data.reduce((total, cur) => {
                let { type } = cur
                if (total[type] !== undefined) total[type]++
                else {
                  total[type] = 1;
                }
                return total
              }, {})
              this.totalNumber = totalNumber;
              response.data.sort((a, b) => {
                if (a.type > b.type) return 1
                else if (a.type === b.type) return 0
                else return -1
              })
              this.tableData = response.data
            }
          ).catch(
            error => { console.log(error); }
          )
        }
      ).catch(
        error => { this.$message.error(error) }
      )
      this.addDialogVisible = false
    },
    handleChange1() {
      this.dialogTitle = "编辑服务"
      this.addDialogVisible = true
    },
    handleAddService() {
      this.dialogTitle = "新增服务"
      this.addDialogVisible = true
    },
    arraySpanMethod({ row, column, rowIndex, columnIndex }) {
      if (columnIndex === 0) {
        if (rowIndex === 0) {
          this.prevService = row.type;
          return {
            rowspan: this.totalNumber[row.type],  //要合并的行数
            colspan: 1
          };
        } else if (row.type !== this.prevService) {
          this.prevService = row.type;
          return {
            rowspan: this.totalNumber[row.type],  //要合并的行数
            colspan: 1
          };
        } else {
          return {
            rowspan: 0,
            colspan: 0
          };
        }
      }

    }
  },
  mounted: function () {
    getMeetingService().then(
      response => {
        console.log(response.data);
        let totalNumber = response.data.reduce((total, cur) => {
          let { type } = cur
          if (total[type] !== undefined) total[type]++
          else {
            total[type] = 1;
          }
          return total
        }, {})
        this.totalNumber = totalNumber;
        response.data.sort((a, b) => {
          if (a.type > b.type) return 1
          else if (a.type === b.type) return 0
          else return -1
        })
        this.tableData = response.data
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
  