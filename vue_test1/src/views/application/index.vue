<template>
  <div class="content_container">
    <div class="content_title">
      <h3>应用列表</h3>
      <div class="operate" >
        <router-link to="/application/add">
        <el-button
          size="mini"
          >创建应用</el-button>
        </router-link>
      </div>
    </div>
    <el-table
      :data="tableData"
      stripe
      border
      :header-cell-style="{background:'#e6e6e6'}"
      style="width: 100%"
    >
      <el-table-column
        prop="appName"
        label="应用"

        >
      </el-table-column>
      <el-table-column
        prop="sdk"
        label="SDK名称"
        width="130px"
        >
      </el-table-column>
      <el-table-column
        prop="platformValue"
        label="授权平台"

        >
      </el-table-column>
      <el-table-column
        prop="totalYears"
        label="授权时长(年)"

        >
      </el-table-column>
      <el-table-column
        prop="endDate"
        width="170px"
        :formatter="dateFormat"
        label="到期日"
        >
      </el-table-column>
      <el-table-column
        prop="totalNumber"
        label="总授权"

        >
      </el-table-column>
      <el-table-column
        prop="remaindNumber"
        label="未用授权"

        >
      </el-table-column>
      <el-table-column
        prop="purchaseTimes"
        label="下单次数"

       >
      </el-table-column>
      <el-table-column
        prop="statusValue"
        label="状态"

        >
      </el-table-column>
      <el-table-column
        label="操作" >
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="jumpToDetail(scope.row)">查看</el-button>
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
</template>

<script>
  import  { applicationList } from '@/api/application';
  import {formatDate} from '@/utils/DateUtil'

  export default {
    created() {
      this.getAppList();
    },
    data() {
      return {
        form:{
          start:'0',
          length:'10'
        },
        tableData: [],
        currentPage: 0,
        pageSize:10,
        totalElements:0,
      }
    },
    methods: {
      onSubmit() {
        console.log('submit!');
      },
      handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
      },
      handleCurrentChange(val) {
        this.form.start = val-1;
        this.getAppList();
      },
      formatter(row, column) {
        return row.address;
      },
      dateFormat(row, column){
        let date ="";
        if(row.endDate != null && row.endDate != ""){
          date = formatDate(new Date(row.endDate),"yyyy-MM-dd");
        }
        return date;
      },
      //获取应用列表
      getAppList(){
        applicationList(this.form).then(response => {
          let result = response.data.data;
          this.tableData = result.content;
          this.pageSize = result.size;
          this.totalElements = result.totalElements

        })

      },
      jumpToDetail(row){
        localStorage.setItem("appId", row.id);
        this.$router.push({ name: 'applicationDetail',params: {
            id: row.id
        }});
      }
    }
  }
</script>

<style>
  .content_container{
    margin: 20px;
  }
</style>
