<template>
  <div class="content_container">
    <h3>
      管理员列表
    </h3>
    <div class="operate" >
      <el-button  @click="dialogFormVisible = true" size="mini">添加</el-button>
    </div>
    <div>
      <el-table
        :data="tableData"
        :header-cell-style="{background:'#e6e6e6'}"
        stripe
        border
        style="width: 100%">
        <el-table-column
          prop="id"
          label="ID"
          width="80px"
        >
        </el-table-column>
        <el-table-column
          prop="name"
          label="用户名"
        >
        </el-table-column>
        <el-table-column
          prop="phone"
          label="手机号">
        </el-table-column>
        <el-table-column
          prop="email"
          label="邮箱">
        </el-table-column>
        <!--<el-table-column-->
          <!--prop="lastLoginTime"-->
          <!--:formatter="dateFormat"-->
          <!--label="上次登录时间">-->
        <!--</el-table-column>-->
        <el-table-column
          prop="roleName"
          label="角色">
        </el-table-column>
        <el-table-column
          label="操作">
          <template slot-scope="scope">
            <el-button
              size="mini"
              @click="openUpdate(scope.row)">修改</el-button>
          </template>
        </el-table-column>

      </el-table>
    </div>

    <el-dialog title="添加管理员" :visible.sync="dialogFormVisible" center>
      <el-form :model="form"   ref="adminForm" :rules="adminRules" >
        <el-form-item label="姓名" :label-width="formLabelWidth"  prop="name">
          <el-input v-model.trim="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="手机号" :label-width="formLabelWidth"  prop="phone">
          <el-input v-model.trim="form.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" :label-width="formLabelWidth"  prop="email">
        <el-input v-model.trim="form.email" autocomplete="off"></el-input>
      </el-form-item>
        <el-form-item label="初始密码" :label-width="formLabelWidth"  prop="password">
          <el-input v-model.trim="form.password" autocomplete="off"  type="password"></el-input>
        </el-form-item>
        <el-form-item label="确认初始密码" :label-width="formLabelWidth"  prop="password">
          <el-input v-model.trim="form.passwordRepeat" autocomplete="off"  type="password"></el-input>
        </el-form-item>
        <el-form-item label="角色" :label-width="formLabelWidth" prop="roleId">
          <el-select v-model="form.roleId" placeholder="请选择角色">
            <el-option
              v-for="item in options"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addAdmin">确 定</el-button>
      </div>
    </el-dialog>


    <el-dialog title="修改管理员" :visible.sync="updateDialogFormVisible" center>
      <el-form :model="updateForm"   ref="updateForm" :rules="adminRules" >
        <el-form-item label="角色" :label-width="formLabelWidth" prop="roleId">
          <el-select v-model="updateForm.roleId" placeholder="请选择角色">
            <el-option
              v-for="item in options"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="updateDialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateAdmin">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import  { getAdmin,getRoleList ,addAdmin,updateAdmin} from '@/api/user';
  import {formatDate} from '@/utils/DateUtil';
  import { Message } from 'element-ui'
    export default {
      name: "administrator",
      created(){
        this.getAdmin();
        this.getRoleList();
      },
      data(){
        const validateUsername = (rule, value, callback) => {
          if (!value) {
            callback(new Error('请输入正确的用户名'))
          } else {
            callback()
          }
        }
        const validatePhone = (rule, value, callback) => {
          if (value.length != 11) {
            callback(new Error('请输入正确的手机号'))
          } else {
            callback()
          }
        }
        const validatePassword = (rule, value, callback) => {
          if (value.length < 6) {
            callback(new Error('密码长度不能低于6位'))
          } else {
            callback()
          }
        }
        const validateRole = (rule, value, callback) => {
          if (!value) {
            callback(new Error('请选择角色'))
          } else {
            callback()
          }
        }
          return{
            formLabelWidth: '120px',
            dialogFormVisible:false,
            updateDialogFormVisible:false,
            tableData:[],
            form:{
              name:'',
              phone:'',
              email:'',
              password:'',
              passwordRepeat:'',
              roleId:''

            },
            updateForm:{
              id:'',
              roleId:''
            },

            options:[],
            adminRules: {
              name: [{required: true, trigger: 'blur', validator: validateUsername}],
              password: [{required: true, trigger: 'blur', validator: validatePassword}],
              phone: [{required: true, trigger: 'blur', validator: validatePhone}],
              role: [{required: true, trigger: 'blur', validator: validateRole}],
            },
          }
      },
      methods:{
        getAdmin(){
          getAdmin().then(response=>{
            this.tableData = response.data.data;


          });
        },
        dateFormat(row, column){
          const time = formatDate(new Date(row.lastLoginTime),"yyyy-MM-dd hh:mm:ss");
          return time;
        },
        getRoleList(){
          getRoleList().then(response=>{
            this.options = response.data.data;
            console.log(this.options);

          });
        },
        addAdmin(){
          if(this.form.password != this.form. passwordRepeat){
            alert("两次密码不一致");
            return;
          }
          this.$refs.adminForm.validate(valid => {{
            if(valid){
              addAdmin(this.form).then(response=>{
                if(response.data.errorCode == 200){
                  Message.success("操作成功");
                  this.dialogFormVisible = false
                  this.getAdmin();
                }else if(response.data.errorCode == 5003){
                  Message.error("手机号已存在,请勿重复添加");
                }
                else{
                  Message.error(response.data.errorMsg);
                }
              });
            }

          }})
        },
        openUpdate(row){
          this.updateDialogFormVisible = true;
          this.updateForm.id = row.id;
          this.updateForm.roleId = row.roleId;

        },
        updateAdmin(){
          updateAdmin(this.updateForm).then(response=>{
            if(response.data.errorCode == 200){
              Message.success("操作成功");
              this.updateDialogFormVisible = false;
              this.getAdmin();
            }else{
              Message.error(response.data.errorMsg);
            }
          });
        }



      }
    }
</script>

<style scoped>
  .el-dialog .el-input, .el-dialog .el-select {
    width: 300px;
  }
</style>
