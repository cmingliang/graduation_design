 <template>
 <div class="table-wrapper">
   <el-button type="primary" style="margin-bottom:30px" @click="handleChange2">新增用户</el-button>
   <el-table
      :data="tableData"
      style="width: 100%"
      border
      header-row-class-name="header">
       <el-table-column
        label="ID"
        prop="id"
        align="center"
        >
      </el-table-column>
       <el-table-column
        label="用户名"
        prop="username"
        align="center"
        >
      </el-table-column>
       <el-table-column
        label="部门"
        prop="department"
        align="center"
        >
      </el-table-column>
      <el-table-column
        label="角色"
        prop="permission"
        align="center"
        >
      </el-table-column>
      <el-table-column
        label="手机号"
        prop="phoneNumber"
        align="center"
        >
      </el-table-column>
      <el-table-column
        label="邮箱"
        prop="email"
        align="center"
        >
      </el-table-column>
      <el-table-column
        label="状态"
        prop="status"
        align="center"
        >
        <template slot-scope='scope'>
             <el-switch
            v-model="tableData[scope.$index].status"
            active-color="#3cafcc"
            >
            </el-switch>        
        </template> 
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
      >
        <template slot-scope="scope">
          <div class="btn-wrapper">
            <el-button
          type="primary"
          class="btn"
          style="background-color:#3cafcc;"
          @click="handleChange1"
          >编辑</el-button>
          <el-button
          type="primary"
          class="btn"
          style="background-color:#f5856d;"
          >删除</el-button>
          </div>
          <el-dialog
          :modal=false
          :title="dialogTitle"
          :visible.sync="addDialogVisible"
          fullscreen
          class="addDialog">
          <el-form :model="form" label-position="left">
          <el-form-item label="用户名" :label-width="formLabelWidth">
            <el-input v-model="form.name" ></el-input>
          </el-form-item>
          <el-form-item label="部门" :label-width="formLabelWidth">
            <el-input v-model="form.department" ></el-input>        
          </el-form-item>
          <el-form-item label="角色" :label-width="formLabelWidth">
            <el-radio-group v-model="form.permission">
            <el-radio :label="1">超级管理员</el-radio>
            <el-radio :label="2">会议室管理员</el-radio>
            <el-radio :label="3">用户</el-radio>
            </el-radio-group>        
          </el-form-item>
          <el-form-item label="手机号" :label-width="formLabelWidth">
            <el-input v-model="form.phoneNumber" ></el-input>
          </el-form-item>
          <el-form-item label="邮箱" :label-width="formLabelWidth">
            <el-input v-model="form.email" ></el-input>        
          </el-form-item>
          <el-form-item label="状态" :label-width="formLabelWidth">
            <el-switch
            v-model="form.status"
            active-color="#3cafcc"
            active-text="开启"
            inactive-text="关闭">
            </el-switch>        
          </el-form-item>  
        </el-form>
        <div slot="footer" class="add-footer">
        <el-button type="primary" @click="addDialogVisible = false">确 定</el-button>
        <el-button @click="addDialogVisible = false">取 消</el-button>       
        </div>
          </el-dialog>
      </template>
      </el-table-column>
    </el-table>
   
 </div>
    
  </template>

  <script>
    export default {
      data() {
        return {
          tableData: [
            {
              id:1,
              username:'张三',
              department:'运营部',
              permission:'用户',
              phoneNumber:12332313232,
              email:'sssss@qqqq.com',
              status:true            
            },
           {
              id:2,
              username:'张三',
              department:'运营部',
              permission:'管理员',
              phoneNumber:12332313232,
              email:'sssss@qqqq.com',
              status:true            
            }],
          addDialogVisible:false,
          formLabelWidth: '80px', 
          form:{
            name:'',
            capacity:'',
            place:'',
            equipments:[],
            type:1
          } ,
          dialogTitle:'新增用户'   
        }
      },
      // computed:{
      //   totalEquipments: function () {     
      //     return this.tableData.equipments.join('、')
      //   }
      // },
      methods: {
      handleEdit(index, row) {
        console.log(index, row);
      },
      handleDelete(index, row) {
        console.log(index, row);
      },
      handleChange1(){
        this.dialogTitle="编辑用户"
        this.addDialogVisible=true
      },
       handleChange2(){
        this.dialogTitle="新增用户"
        this.addDialogVisible=true
      }
     
      }
    }
  </script>

  <style>
    .header {
      background-color: #f2f2f2 !important;
      color:#666666;
    }
    .table-wrapper {
      margin: 60px 40px;
    }
    .btn{
      width: 45px;
      height: 20px;
      border:0;
      font-size: 12px;
      padding: 0 0;
    }
    .btn-wrapper {
      display: flex;
      justify-content: center;
      margin: 0 10px ;
    }
    .addDialog {
      height: 92%;
      width: 35%;
      position: fixed;
      right: 0;
      left:auto !important;
      top:80px;
      box-shadow: -5px 0px 5px 0px 
		rgba(16, 5, 10, 0.1);
    }
    .add-footer {
      display: flex;
      justify-content: space-around;
    }
  </style>
  