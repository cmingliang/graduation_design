<template>
  <div class="content_container">
    <h3>
      数据字典列表
    </h3>
    <div class="operate" >
      <el-button  @click="openDialog(1)" size="mini">添加</el-button>
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
          prop="dictName"
          label="字典名称"
        >
        </el-table-column>
        <el-table-column
          prop="tableName"
          label="表名">
        </el-table-column>
        <el-table-column
          prop="fieldName"
          label="字段名">
        </el-table-column>
        <el-table-column
          prop="key"
          label="值">
        </el-table-column>
        <el-table-column
          prop="value"
          label="名称">
        </el-table-column>
        <el-table-column
          prop="desciption"
          label="描述">
        </el-table-column>
        <el-table-column
          label="操作">
          <template slot-scope="scope">
            <el-button
              size="mini"
              @click="openDialog(2,scope.row)">修改</el-button>
            <!--<el-button-->
              <!--size="mini"-->
              <!--@click="">删除</el-button>-->
          </template>
        </el-table-column>
      </el-table>
      <div>
        <el-pagination
          background
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page.sync="currentPage"
          :page-size="pageSize"
          :pager-count="5"
          layout="total, prev, pager, next"
          :total="totalElements">
        </el-pagination>
      </div>
    </div>

    <el-dialog title="添加字典项" :visible.sync="dialogFormVisible" center>
      <el-form :model="form"   ref="form" :rules="formRules" >
        <el-form-item label="字典名称" :label-width="formLabelWidth"  prop="dictName">
          <el-input v-model.trim="form.dictName" autocomplete="off" ></el-input>
        </el-form-item>
        <el-form-item label="表名" :label-width="formLabelWidth"  prop="tableName">
          <el-input v-model.trim="form.tableName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="值" :label-width="formLabelWidth"  prop="key">
          <el-input v-model.trim="form.key" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="内容" :label-width="formLabelWidth"  prop="value">
          <el-input v-model.trim="form.value" autocomplete="off" ></el-input>
        </el-form-item>
        <el-form-item label="描述" :label-width="formLabelWidth"  prop="desciption">
          <el-input v-model.trim="form.desciption" autocomplete="off"  ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addObject">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
  import  { getDictList,saveDict} from '@/api/dict';
  import {formatDate} from '@/utils/DateUtil';
  import { Message } from 'element-ui'
  export default {
    name: "dict",
    created(){
      this.getDictList();
    },
    data(){
      const validateDictName = (rule, value, callback) => {
        if (!value || "" == value) {
          callback(new Error('请输入名称'))
        } else {
          callback()
        }
      }
      const validateKey = (rule, value, callback) => {
        if (!value || "" == value) {
          callback(new Error('请输入对应的值'))
        } else {
          callback()
        }
      }
      const validateValue= (rule, value, callback) => {
        if (!value || "" == value) {
          callback(new Error('请输入名称'))
        } else {
          callback()
        }
      }

      return{
        formLabelWidth: '120px',
        dialogFormVisible:false,
        tableData:[],
        param:{
          start:0,
          length:10
        },
        form:{
          id:'',
          dictName:'',
          tableName:'',
          key:'',
          value:'',
          desciption:'',
        },
        formRules: {
          dictName: [{required: true, trigger: 'blur', validator: validateDictName}],
          key: [{required: true, trigger: 'blur', validator: validateKey}],
          value: [{required: true, trigger: 'blur', validator: validateValue}],
        },

        currentPage: 1,
        pageSize:10,
        totalElements:0

      }
    },
    methods:{
      onSubmit() {
        console.log('submit!');
      },
      handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
      },
      handleCurrentChange(val) {
        this.param.start = val-1;
        this.getDictList();
      },
      getDictList(){
        getDictList(this.param).then(response=>{
          let result = response.data.data;
          this.tableData = result.content;
          this.pageSize = result.size;
          this.totalElements = result.totalElements


        });
      },
      dateFormat(row, column){
        const time = formatDate(new Date(row.lastLoginTime),"yyyy-MM-dd hh:mm:ss");
        return time;
      },

      addObject(){
        this.$refs.form.validate(valid => {{
          if(valid){
            saveDict(this.form).then(response=>{
              if(response.data.errorCode == 200){
                Message.success("操作成功");
                this.dialogFormVisible = false
                this.getDictList();
              }else{
                Message.error(response.data.errorMsg);
              }
            });
          }

        }})
      },

      openDialog(type,row){
        this.dialogFormVisible = true;
        if(type == 1){
          this.form = {
            id:'',
            dictName:'',
            tableName:'',
            key:'',
            value:'',
            desciption:'',
          }
          this.$refs.form.clearValidate();//清除上次的校验结果
        }else{
          this.form = row;
        }


      },




    }
  }
</script>

<style scoped>
  .el-dialog .el-input, .el-dialog .el-select {
    width: 300px;
  }
</style>
