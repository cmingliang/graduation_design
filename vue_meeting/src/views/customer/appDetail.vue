<template>
  <div class="content_container">
    <section>
      <div class="content_title">
        <h3>| 应用详情</h3>
        <div>
          <MapTable :tableData="tableData" :tableLangData="tableLangData" :tableStyle="{ width:'100%' }"></MapTable>
        </div>

      </div>
    </section>

  <section>
    <h3>
      | 购买记录
    </h3>
    <div>

      <el-table
        :data="orderLog"
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
          prop="salerName"
          label="销售人员"
        >
        </el-table-column>
        <el-table-column
          prop="chargeTypeValue"
          label="授权类型"
        >
        </el-table-column>
        <el-table-column
          prop="number"
          label="数量"
        >
        </el-table-column>

        <!--<el-table-column-->
          <!--prop="unitPrice"-->
          <!--label="单价"-->
        <!--&gt;-->
        <!--</el-table-column>-->
        <el-table-column
          prop="totalPrice"
          label="总价(元)"
        >
        </el-table-column>
        <el-table-column
          prop="createTime"
          label="时间"
          :formatter="dateFormat"
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
          @current-change="handleOrderCurrentChange"
          :current-page.sync="orderTable.currentPage"
          :page-size="orderTable.pageSize"
          :pager-count="5"
          layout="total, prev, pager, next"
          :total="orderTable.totalElements">
        </el-pagination>
      </div>
    </div>


  </section>
  </div>
</template>

<script>
    import MapTable from '@/components/MapTable/index';
    import  { applicationDetail } from '@/api/application';
    import  { getMyOrderList } from '@/api/order';
    import {formatDate} from '@/utils/DateUtil'
    import {hasPermission} from '@/utils/permission' // 权限判断函数

    export default {
      name: "detail",
      created() {
        this.getAppDetail();
        this.getOrderList();
      },
      components: {
        MapTable
      },
      data(){
        return  {
          tableData: [
            {key: '应用名称', ekey:'appName', value: '--'},
            {key: '应用分类', ekey:'type', value: '--'},
            {key: '授权平台', ekey:'platformValue', value: '--'},
            {key: '创建时间', ekey:'createTime', value: '--'},
            {key: 'bundle id', ekey:'bundleId', value: '--'},
            {key: 'sdk', ekey:'sdk', value: '--'},
            {key: '授权类型', ekey:'chargeTypeValue', value: '--'},
            {key: '总授权', ekey:'totalNumber', value: '--'},
            {key: '未用授权', ekey:'remaindNumber', value: '--'},
            {key: '授权时长(年)', ekey:'totalYears', value: '--'},
            {key: '到期日', ekey:'endDate', value: '--'},
            {key: '状态', ekey:'statusValue', value: '--'},
          ],
          tableLangData:{key:"描述",value:"--"},
          orderLog: [],
          appId:'',
          orderParam:{
            appId: '',
            start: '0',
            length: '10',
          },
          orderTable: {
            currentPage: 0,
            pageSize: 10,
            totalElements: 0,
          },
        }

      },
      methods:{
        hasPermission,
        handleOrderCurrentChange(val) {
          this.orderParam.start = val - 1;
          this.getOrderList();
        },
        getAppDetail(){
          this.appId = this.$route.params.id || localStorage.appId;
          applicationDetail(this.appId).then(response => {
            const appDetail = response.data.data;
            let valueArr = this.tableData;
            for(let i = 0 ;i < valueArr.length;i++ ){
              if(this.tableData[i].ekey == "createTime"){
                this.tableData[i].value =  formatDate(new Date(appDetail[this.tableData[i].ekey]),"yyyy-MM-dd hh:mm:ss");
              }else if(this.tableData[i].ekey == "endDate"){
                this.tableData[i].value = appDetail[this.tableData[i].ekey] != ""?formatDate(new Date(appDetail[this.tableData[i].ekey]), "yyyy-MM-dd"):"";
              }
              else{
                this.tableData[i].value = appDetail[this.tableData[i].ekey];
              }
            }
            this.tableLangData.value = appDetail.description;
          })

        },
        dateFormat(row, column){
          const createTime = formatDate(new Date(row.createTime),"yyyy-MM-dd hh:mm:ss");
          return createTime;
        },
        getOrderList(){
          this.orderParam.appId = this.appId;
          getMyOrderList(this.orderParam).then(response => {
            let result = response.data.data;
            this.orderLog = result.content;
            this.orderTable.pageSize = result.size;
            this.orderTable.totalElements = result.totalElements
          })
        },
        jumpToDetail(row){
          let param = {id: row.id, userId: row.userId, status: row.status};
          localStorage.setItem("orderDetailParam", JSON.stringify(param));
          this.$router.push({ name: 'orderDetail', params:param});
        },
      }

    }
</script>

<style scoped>
  .content_container{
    margin: 20px;
  }
</style>
