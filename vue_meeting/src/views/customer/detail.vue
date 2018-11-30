<template>
  <div class="content_container">

    <section>
      <div class="content_title">
        <h3>| 用户信息</h3>
      </div>
      <div>
        <MapTable :tableData="tableData"  :tableStyle="{ width:'100%' }"></MapTable>
      </div>
    </section>
    <section>
      <div class="content_title">
        <h3>| 应用列表</h3>
      </div>
      <div>
        <el-table
          :data="listData"
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

    </section>

  </div>



</template>

<script>
    import MapTable from '@/components/MapTable/index';
    import  { applicationList } from '@/api/application';
    import {formatDate} from '@/utils/DateUtil'

    export default {
      name: "detail",
      components: {
        MapTable
      },
      created() {
        this.initData();
        this.getAppList();
      },
      data(){
        return  {
          tableData: [
            {key: '公司名称', ekey:'cname', value: '--'},
            // {key: '业务类型', ekey:'type', value: '--'},
            {key: '联系人', ekey:'name', value: '--'},
            {key: '手机号', ekey:'phone', value: '--'},
            {key: '邮箱', ekey:'email', value: '--'},
            {key: '纳税人识别号', ekey:'tin', value: '--'}
          ],
          orderLog: [],
          listData: [],
          userInfo:{},
          form:{
            userId:'',
            start:'0',
            length:'10'
          },
          currentPage: 0,
          pageSize:10,
          totalElements:0,
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
          this.getAppList();
        },
        dateFormat(row, column){
          let date ="";
          if(row.endDate != null && row.endDate != ""){
            date = formatDate(new Date(row.endDate),"yyyy-MM-dd");
          }
          return date;
        },
        initData(){
          this.userInfo = this.$route.params;
          if(!this.userInfo.name){
            this.userInfo = JSON.parse(localStorage.cInfo);
          }
          let valueArr = this.tableData;
          for(let i = 0 ;i < valueArr.length;i++ ){
            this.tableData[i].value =  this.userInfo[this.tableData[i].ekey];
          }
        },
        //获取应用列表
        getAppList(){
          this.form.userId = this.userInfo.id;
          applicationList(this.form).then(response => {
            let result = response.data.data;
            this.listData = result.content;
            this.pageSize = result.size;
            this.totalElements = result.totalElements
            // this.listData = response.data.data;
            // console.log(response);
          })

        },
        jumpToDetail(row){
          localStorage.setItem("appId", row.id);
          this.$router.push({ name: 'customerAppDetail',params:{id:row.id}});
        },
      }
    }
</script>

<style scoped>
  .content_container{
    margin: 20px;
  }
</style>
