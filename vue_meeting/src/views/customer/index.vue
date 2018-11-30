<template>
  <div class="content_container">
    <div class="content_title">
      <h3>客户管理</h3>
    </div>
    <div>
      <el-form :label-position="labelPosition" :inline="true" :model="form" class="demo-form-inline" size="mini">
        <el-form-item label="公司名称">
          <el-input v-model="form.cname" placeholder="公司名称"></el-input>
        </el-form-item>
        <el-form-item label="联系人">
          <el-input v-model="form.name" placeholder="联系人"></el-input>
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="form.phone" placeholder="手机号"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="form.email" placeholder="邮箱"></el-input>
        </el-form-item>
        <!--<el-form-item label="业务类型">-->
          <!--<el-select v-model="form.type" placeholder="活动区域">-->
            <!--<el-option label="区域一" value="1"></el-option>-->
            <!--<el-option label="区域二" value="2"></el-option>-->
          <!--</el-select>-->
        <!--</el-form-item>-->
        <!--<el-form-item label="状态">-->
          <!--<el-select v-model="form.status" placeholder="活动区域">-->
            <!--<el-option label="区域一" value="1"></el-option>-->
            <!--<el-option label="区域二" value="2"></el-option>-->
          <!--</el-select>-->
        <!--</el-form-item>-->

        <el-form-item>
          <el-button type="primary" @click="onSelect">查询</el-button>
        </el-form-item>

      </el-form>
    </div>

    <div>
      <el-table
        :data="tableData"
        stripe
        border
        :header-cell-style="{background:'#e6e6e6'}"
        style="width: 100%">
        <el-table-column
          prop="cname"
          label="公司名称"
         >
        </el-table-column>
        <!--<el-table-column-->
          <!--prop="type"-->
          <!--label="业务类型">-->
        <!--</el-table-column>-->
        <el-table-column
          prop="name"
          label="联系人">
        </el-table-column>
        <el-table-column
          prop="phone"
          label="手机号">
        </el-table-column>
        <el-table-column
          prop="email"
          label="邮箱">
        </el-table-column>
        <el-table-column
          prop="createTime"
          :formatter="dateFormat"
          label="注册时间">
        </el-table-column>
        <!--<el-table-column-->
          <!--prop="status"-->
          <!--label="状态">-->
        <!--</el-table-column>-->
        <el-table-column
          label="操作">
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


  </div>

</template>

<script>
  import {formatDate} from '@/utils/DateUtil'
  import  { customerList } from '@/api/customer';
    export default {
      created(){
        this.onSelect();
      },
      data() {
        return {
          labelPosition: 'left',
          form: {
            cname: '',
            name: '',
            phone:'',
            email:'',
            type:'',
            status:'',
            start:'',
            length:'10'
          },
          tableData: [],
          currentPage: 5,
          pageSize:10,
          totalElements:0
        }
      },
      methods: {
        dateFormat(row, column){
          const createTime = formatDate(new Date(row.createTime),"yyyy-MM-dd hh:mm:ss");
          return createTime;
        },
        onSubmit() {
          console.log('submit!');
        },
        handleSizeChange(val) {
          console.log(`每页 ${val} 条`);
        },
        handleCurrentChange(val) {
          this.form.start = val-1;
          this.onSelect();
        },
        onSelect(){
          customerList(this.form).then(response => {
            let result = response.data.data;
            this.tableData = result.content;
            this.pageSize = result.pageable.pageSize;
            this.totalElements = result.totalElements
          })
        },
        jumpToDetail(row){
          localStorage.setItem("cInfo", JSON.stringify(row));
          this.$router.push({ name: 'customerDetail',params:row});
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
