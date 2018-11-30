<template>
  <div class="content_container">
    <div class="content_title">
      <h3>财务管理</h3>
    </div>
    <div>
      <el-form :label-position="labelPosition" :inline="true" :model="form" class="demo-form-inline">
        <el-form-item label="日志类型">
          <el-input v-model="form.logType" placeholder="日志类型"></el-input>
        </el-form-item>
        <el-form-item label="日志名称">
          <el-input v-model="form.logName" placeholder="日志名称"></el-input>
        </el-form-item>

        <el-form-item label="时间">
          <el-col :span="11">
            <el-date-picker type="date" placeholder="选择时间" v-model="form.beginTime" format="yyyy-MM-dd" value-format="yyyy-MM-dd" style="width: 100%;"></el-date-picker>
          </el-col>
          <el-col class="line text-center" :span="2" >-</el-col>
          <el-col :span="11">
            <el-date-picker type="date" placeholder="选择时间" v-model="form.endTime" format="yyyy-MM-dd" value-format="yyyy-MM-dd" style="width: 100%;"></el-date-picker>
          </el-col>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="getLogList">查询</el-button>
        </el-form-item>

      </el-form>
    </div>

    <div>
      <el-table
        :data="logList"
        stripe
        border
        :header-cell-style="{background:'#e6e6e6'}"
        style="width: 100%">
        <el-table-column
          prop="logType"
          label="日志类型"
        >
        </el-table-column>

        <el-table-column
          prop="logName"
          label="日志名称"
        >
        </el-table-column>
        <el-table-column
          prop="userName"
          label="操作人员"
        >
        </el-table-column>


        <el-table-column
          prop="succeed"
          label="是否成功"
        >
        </el-table-column>
        <el-table-column
          prop="ip"
          label="IP地址"
        >
        </el-table-column>
        <el-table-column
          prop="createTime"
          :formatter="dateFormat"
          label="操作时间"
        >
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


  </div>
</template>

<script>
  import  { getLogList } from '@/api/log';
  import {formatDate} from '@/utils/DateUtil'
    export default {
      name: "index",
      created(){
        this.getLogList();
      },
      data() {
        return {
          labelPosition: 'left',
          form: {
            logType: '',
            logName: '',
            beginTime:'',
            endTime:'',
            start:'',
            length:'10'
          },
          logList: [],
          currentPage: 5,
          pageSize:5,
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
          this.form.start = val-1;
          this.getLogList();
        },
        getLogList(){

          getLogList(this.form).then(response => {
            let result = response.data.data;
            this.logList = result.content;
            this.pageSize = result.size;
            this.totalElements = result.totalElements
          })

        },
        dateFormat(row, column){
          const createTime = formatDate(new Date(row.createTime),"yyyy-MM-dd hh:mm:ss");
          return createTime;
        },

      }


    }
</script>

<style scoped>
  .content_container{
    margin: 20px;
  }

  .el-pagination{
    float: right;
  }
</style>
