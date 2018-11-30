<template>
  <div class="content_container">
    <div class="content_title">
      <h3>财务管理</h3>
    </div>
    <div>
      <el-form :label-position="labelPosition" :inline="true" :model="form" class="demo-form-inline"  size="mini" >
        <el-form-item label="订单号">
          <el-input v-model="form.orderNo" placeholder="订单号"></el-input>
        </el-form-item>
        <el-form-item label="支付单号">
          <el-input v-model="form.payNo" placeholder="支付单号"></el-input>
        </el-form-item>
        <el-form-item label="用户">
          <el-input v-model="form.name" placeholder="用户"></el-input>
        </el-form-item>
        <el-form-item label="销售人员">
          <DynamicSelect optionName="saler" addAllOption="true"   :defaultValue="form.saler" v-model="form.saler"></DynamicSelect>
        </el-form-item>
        <el-form-item label="时间">
          <el-col :span="11">
            <el-date-picker type="date" placeholder="选择时间" v-model="form.startTime" format="yyyy-MM-dd" value-format="yyyy-MM-dd" style="width: 100%;"></el-date-picker>
          </el-col>
          <el-col class="line text-center" :span="2">-</el-col>
          <el-col :span="11">
            <el-date-picker type="date" placeholder="选择时间" v-model="form.endTime" format="yyyy-MM-dd" value-format="yyyy-MM-dd" style="width: 100%;"></el-date-picker>
          </el-col>
        </el-form-item>
        <el-form-item label="状态">
          <DynamicSelect optionName="order_status" addAllOption="false"   :defaultValue="form.status" v-model="form.status"></DynamicSelect>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="getOrderList">查询</el-button>
        </el-form-item>

      </el-form>
    </div>

    <div>
      <el-table
        :data="orderList"
        stripe
        border
        :header-cell-style="{background:'#e6e6e6'}"
        style="width: 100%">
        <el-table-column
          prop="orderNo"
          label="订单号"
        >
        </el-table-column>

        <el-table-column
          prop="name"
          label="用户"
        >
        </el-table-column>
        <el-table-column
          prop="salerName"
          label="销售人员"
        >
        </el-table-column>


        <el-table-column
          prop="totalPrice"
          label="总价(元)"
        >
        </el-table-column>
        <el-table-column
          prop="payTypeValue"
          label="支付方式"
        >
        </el-table-column>
        <el-table-column
          prop="payNo"
          label="支付单号"
        >
        </el-table-column>
        <el-table-column
          prop="payDate"
          label="支付时间"
          :formatter="dateFormat"
        >
        </el-table-column>
        <el-table-column
          prop="invoiceStatusValue"
          label="发票"
        >
        </el-table-column>
        <el-table-column
          prop="statusValue"
          label="状态"
        >
        </el-table-column>
        <el-table-column
          v-if="hasPermission('a_order_detail')"
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
  import  { getFinanceOrderList } from '@/api/order';
  import {formatDate} from '@/utils/DateUtil'
  import {hasPermission} from '@/utils/permission' // 权限判断函数
  import DynamicSelect from '@/views/select/DynamicSelect'

    export default {
      name: "index",
      created(){
        this.getOrderList();
        this.initData();
      },
      components: {
        DynamicSelect
      },
      data() {
        return {
          labelPosition: 'left',
          form: {
            orderNo: '',
            payNo: '',
            name:'',
            saler:'',
            startTime:'',
            endTime:'',
            status:'',
            start:'',
            length:'10'
          },
          orderList: [],
          currentPage: 5,
          pageSize:5,
          totalElements:0,
          salers:[]
        }
      },
      methods:{
        hasPermission,
        initData(){
        },
        onSubmit() {
          console.log('submit!');
        },
        handleSizeChange(val) {
          console.log(`每页 ${val} 条`);
        },
        handleCurrentChange(val) {
          this.form.start = val-1;
          this.getOrderList();
        },
        getOrderList(){
          console.log(this.form.startTime);
          getFinanceOrderList(this.form).then(response => {
            let result = response.data.data;
            this.orderList = result.content;
            this.pageSize = result.size;
            this.totalElements = result.totalElements
          })

        },
        dateFormat(row, column){
          const createTime = formatDate(new Date(row.createTime),"yyyy-MM-dd hh:mm:ss");
          return createTime;
        },
        jumpToDetail(row){
          let param = {id: row.id, userId: row.userId, status: row.status};
          localStorage.setItem("orderDetailParam", JSON.stringify(param));
          this.$router.push({ name: 'orderDetail', params:param});
        }
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
