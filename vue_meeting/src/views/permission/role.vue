<template>
  <div class="content_container">
    <h3>
      角色管理
    </h3>
    <div class="operate" >
      <el-button  @click="openAddRole" size="mini">添加</el-button>
    </div>
    <div>
      <el-table
        :data="tableData"
        stripe
        border
        :header-cell-style="{background:'#e6e6e6'}"
        style="width: 100%">
        <el-table-column
          prop="name"
          label="角色名"
        >
        </el-table-column>
        <el-table-column
          prop="permissionStr"
          label="权限">
        </el-table-column>
        <el-table-column
          prop="description"
          label="描述">
        </el-table-column>
        <el-table-column
          label="操作">
          <template slot-scope="scope">
            <el-button
              size="mini"
              v-if="scope.row.changeable == 1"
              @click="openUpdateRole(scope.row)">修改</el-button>

          </template>
        </el-table-column>
      </el-table>
    </div>

    <el-dialog :title="dialogName" :visible.sync="dialogFormVisible" @close="resetForm" center>
      <el-form :model="form"   ref="roleForm" :rules="roleRules" >
        <el-form-item label="角色名" :label-width="formLabelWidth"  prop="name">
          <el-input v-model.trim="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="描述" :label-width="formLabelWidth"  prop="description">
          <el-input   type="textarea" v-model="form.description" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="权限" :label-width="formLabelWidth"  prop="permission">
          <div style="max-height: 100px;overflow: hidden;overflow-y:scroll;">
          <el-tree
            :data="permissionList"
            show-checkbox
            node-key="id"
            ref="tree"
            :default-checked-keys="form.permission"
            :props="defaultProps">
          </el-tree>
          </div>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addRole">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import  { getRoleList,getPermissionList,addRole} from '@/api/user';
  import {formatDate} from '@/utils/DateUtil';
  import { listToTree } from '@/utils'
  import { Message } from 'element-ui'
  export default {
    name: "administrator",
    created(){
      this.getRoleList();
      this.getPermissionList();
    },
    mounted() {
      this.$refs.dialog.open();
      setTimeout(() => {
        this.$refs.dialog.close();
      }, 2000);
    },
    data(){
      const validateUsername = (rule, value, callback) => {
        if (!value) {
          callback(new Error('请输入正确的角色名'))
        } else {
          callback()
        }
      }

      return{
        dialogName:"添加角色",
        formLabelWidth: '120px',
        dialogFormVisible:false,
        tableData:[],
        form:{
          id:'',
          name:'',
          description:'',
          permission:[],
        },
        options:[],
        roleRules: {
          name: [{required: true, trigger: 'blur', validator: validateUsername}],


        },

        permissionList: [],
        defaultProps: {
          children: 'children',
          label: 'name'
        }
      }
    },
    methods:{

      dateFormat(row, column){
        const time = formatDate(new Date(row.lastLoginTime),"yyyy-MM-dd hh:mm:ss");
        return time;
      },
      getRoleList(){
        getRoleList().then(response=>{
          this.tableData = response.data.data;
        });
      },
      getPermissionList(){
        getPermissionList().then(response=>{
          let permissions = response.data.data;
          this.permissionList = listToTree(permissions);
          console.log(this.permissionList);
        });
      },
      addRole(){
        let treeNode =  this.$refs.tree.getCheckedNodes();
        console.log(treeNode);

        if(treeNode == null || treeNode.length == 0){
          alert("请选择权限");
          return;
        }
        this.form.permission=[];
        for(let i = 0 ;i< treeNode.length;i++){
          this.form.permission.push(treeNode[i].id);
        }
        // this.form.permission = treeNode;
        let $qs = require('querystring') //nodejs自带querystring模块
        let data = $qs.stringify(this.form);

        this.$refs.roleForm.validate(valid => {{
          if(valid){
            addRole(data).then(response=>{
              if(response.data.errorCode == 200){
                Message.success("操作成功");
                this.dialogFormVisible = false
                this.getRoleList();
              }else{
                Message.error(response.data.errorMsg);
              }
            });
          }
        }})
      },

      openUpdateRole(row){

        this.dialogName = "修改角色";
        this.dialogFormVisible = true;
        this.form = row;
        this.$refs.tree.setCheckedKeys(this.form.permission);

      },

      openAddRole(){

        this.dialogName = "添加角色";
        this.dialogFormVisible = true;
        this.form = {
          id:'',
          name:'',
          description:'',
          permission:[],
        }
      },
      resetForm(){
        this.$refs.tree.setCheckedKeys([]);
      }


    }
  }
</script>

<style scoped>
  .el-dialog .el-input, .el-dialog .el-select, .el-dialog .el-textarea  {
    width: 300px;
  }
</style>
