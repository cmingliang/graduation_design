<template>
  <div class="content_container">
    <section>
      <div class="content_title">
        <h3>应用详情</h3>
        <div class="operate_content">
          <el-button type="success" @click="openDialog">续订</el-button>
        </div>
        <div>
          <MapTable :tableData="tableData" :tableLangData="tableLangData" :tableStyle="{ width:'100%' }"></MapTable>
        </div>

      </div>
    </section>
    <section>
      <h3>证书管理</h3>
      <el-checkbox v-model="checked" @change="onDownloadFlagChange">未下载过</el-checkbox>
      <div class="operate_content">

        <el-button type="primary" @click="openLicenceDialog(1)">创建证书</el-button>
        <el-button type="primary" @click="openLicenceDialog(2)">批量创建</el-button>
        <el-button type="success" @click="downloadBatch">批量下载</el-button>
      </div>
      <div>
        <el-table
          :data="licence"
          stripe
          border
          :header-cell-style="{background:'#e6e6e6'}"
          style="width: 100%">
          <el-table-column
            prop="id"
            label="序号"
            width="180">
          </el-table-column>
          <el-table-column
            prop="serial"
            label="设备指纹"
            width="180">
          </el-table-column>
          <el-table-column
            prop="batchNo"
            label="批次号">
          </el-table-column>
          <el-table-column
            prop="createTime"
            :formatter="dateFormat"
            label="创建时间">
          </el-table-column>
          <el-table-column
            prop="downloadFlag"
            :formatter="downloadFlagFormatter"
            label="状态">
          </el-table-column>
          <el-table-column
            label="操作">
            <template slot-scope="scope">
              <el-button
                size="mini"
                @click="download(scope.row)">下载
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div>
        <el-pagination
          background
          @current-change="handleCurrentChange"
          :current-page.sync="licenceTable.currentPage"
          :page-size="licenceTable.pageSize"
          :pager-count="5"
          layout="total, prev, pager, next"
          :total="licenceTable.totalElements">
        </el-pagination>
      </div>
      <div style="clear: both"></div>
    </section>
    <section>
      <h3>购买记录</h3>
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
            width="150px"
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
            width="150px"
          >
          </el-table-column>
          <el-table-column
            prop="statusValue"
            label="状态"
          >
          </el-table-column>
          <el-table-column
            label="操作"
            width="150px">

            <template slot-scope="scope">
              <el-button
                size="mini"
                @click="jumpToDetail(scope.row)">查看
              </el-button>
              <el-button
                size="mini"
                v-if="scope.row.status != 4"
                @click="deleteOrder(scope.$index,scope.row)">删除
              </el-button>
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
    <el-dialog :title="dialogName" :visible.sync="dialogFormVisible" @close="" center>
      <el-form :model="licenceForm"   ref="licenceForm" :rules="licenceRules">
        <el-form-item label="设备指纹" v-if="licenceForm.type == 1" :label-width="formLabelWidth"  prop="serial">
          <el-input v-model="licenceForm.serial" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="选择文件" v-if="licenceForm.type == 2" :label-width="formLabelWidth" >
          <el-upload
            class="upload-demo"
            ref="upload"
            drag
            accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            action="https://jsonplaceholder.typicode.com/posts/"
            :on-change="handlePreview"
            :file-list="fileList"
            :auto-upload="false">
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>

          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addLicence">确 定</el-button>
      </div>
    </el-dialog>

    <!--<transition name="fade">-->
    <Dialog v-show="showDialog" :dialog-option="dialogOption" @cancel="cancelOperate" @submit="submitRenew" ref="dialog">
      <div slot="name1">

        <el-form  ref="dialogForm" :rules="formRules" :model="dialogForm" label-width="80px">

          <el-form-item label="授权方式">
            <el-select v-model="dialogForm.chargeType" placeholder="请选择授权方式" disabled >
              <el-option label="计时(年)" value="1"></el-option>
              <el-option label="计次" value="2"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="购买时长" v-show="dialogForm.chargeType == 1" prop="number">
            <el-input v-model="dialogForm.number" min="1" type="number"></el-input>
          </el-form-item>
          <el-form-item label="购买数量" v-show="dialogForm.chargeType == 2" prop="number">
            <el-input v-model="dialogForm.number" min="1" type="number"></el-input>
          </el-form-item>
          <el-form-item label="销售人员" prop="saler">
            <el-select v-model="dialogForm.saler" placeholder="请选择销售人员" >
              <el-option
                v-for="item in salers"
                :key="item.key"
                :label="item.value"
                :value="item.key">
              </el-option>
            </el-select>
          </el-form-item>
        </el-form>
      </div>
    </Dialog>

  </div>

</template>

<script>
  import MapTable from '@/components/MapTable/index';
  import Dialog from '@/components/dialog/index';
  import { Message } from 'element-ui'
  import {applicationDetail} from '@/api/application';
  import {getMyOrderList,renew,deleteOrder} from '@/api/order';
  import {formatDate} from '@/utils/DateUtil'
  import {getLicenceList,addLicence,addLicenceBatch,downloadBatch} from '@/api/licence';
  import store from '@/store'
  export default {
    name: "detail",
    created() {
      this.getAppDetail();
      this.getOrderList();
      this.getLicenceList();
      this.initData();

    },
    components: {
      MapTable, Dialog
    },
    data() {
      const validateNumber = (rule, value, callback) => {
        if (value == null || value == "") {
          callback(new Error('请填写数量'))
        }
        if (value <= 0) {
          callback(new Error('数量必须大于或者等于1'))
        } else {
          callback()
        }
      }
      const validateSaler = (rule, value, callback) => {
        if (value == null || value == "") {
          callback(new Error('请选择销售人员'))
        } else {
          callback()
        }
      }
      return {
        formLabelWidth: '120px',
        tableData: [
          {key: '应用名称', ekey: 'appName', value: '--'},
          {key: '应用分类', ekey: 'type', value: '--'},
          {key: '授权平台', ekey: 'platformValue', value: '--'},
          {key: 'app key', ekey: 'appKey', value: '--'},
          {key: 'app secret', ekey: 'appSecret', value: '--'},
          {key: '创建时间', ekey: 'createTime', value: '--'},
          {key: 'bundle id', ekey: 'bundleId', value: '--'},
          {key: 'sdk', ekey: 'sdk', value: '--'},
          {key: '授权类型', ekey: 'chargeTypeValue', value: '--'},
          {key: '总授权', ekey: 'totalNumber', value: '--'},
          {key: '未用授权', ekey: 'remaindNumber', value: '--'},
          {key: '授权时长(年)', ekey: 'totalYears', value: '--'},
          {key: '到期日', ekey: 'endDate', value: '--'},
          {key: '状态', ekey: 'statusValue', value: '--'},
        ],
        tableLangData: {key: "描述", value: "--"},
        orderLog: [],
        appId: '',
        licence: [],
        licenceTable: {
          currentPage: 0,
          pageSize: 10,
          totalElements: 0,
        },
        orderTable: {
          currentPage: 0,
          pageSize: 10,
          totalElements: 0,
        },
        licenceParam: {
          appId: '',
          start: '0',
          length: '10',
          downloadFlag: '0'
        },
        orderParam:{
          appId: '',
          start: '0',
          length: '10',
        },
        fileList:[],
        licenceBatchParam:'',
        showDialog: false,
        activeNames: ['1'],
        dialogOption: {
          title: '续订',
          cancelButtonText: '取消',
          confirmButtonText: '确定',

        },
        dialogForm: {
          chargeType: '1',
          number: '',
          saler: '',
          appId: ''
        },
        formRules: {
          number: [{required: true, trigger: 'blur', validator: validateNumber}],
          saler: [{required: true, trigger: 'blur', validator: validateSaler}],
        },
        licenceRules: {
          serial: [{required: true, trigger: 'blur'}],

        },
        dialogFormVisible: false,
        dialogName: '创建证书',
        licenceForm: {
          appId:'',
          type:'1',
          serial: '',
          file: ''
        },
        checked:true,
        salers:[]
      }

    },
    methods: {
      initData(){
        if(store.getters.selectOption.saler.length == 0){
          this.$store.dispatch('loadSelectOption', 'saler').then((response)=>{
            this.salers = response.data.data;
            console.log(this.salers);
          });
        }else{
          this.salers = store.getters.selectOption.saler;
        }
      },
      openDialog() {
        this.showDialog = true;
      },
      cancelOperate() {
        this.showDialog = false;
        console.log(this.showDialog);
      },
      getAppDetail() {
        this.appId = this.$route.params.id || localStorage.appId;
        if (!this.appId) {//如果没有应用id,就跳转到应用列表页
          this.$router.push({name: 'Application'});
        }

        this.dialogForm.appId = this.appId;
        applicationDetail(this.appId).then(response => {
          const appDetail = response.data.data;
          let valueArr = this.tableData;
          for (let i = 0; i < valueArr.length; i++) {
            if (this.tableData[i].ekey == "createTime") {
              this.tableData[i].value = formatDate(new Date(appDetail[this.tableData[i].ekey]), "yyyy-MM-dd hh:mm:ss");
            }else if(this.tableData[i].ekey == "endDate"){
              this.tableData[i].value =  appDetail[this.tableData[i].ekey] != ""?formatDate(new Date(appDetail[this.tableData[i].ekey]), "yyyy-MM-dd"):"";
            }
            else {
              this.tableData[i].value = appDetail[this.tableData[i].ekey];
            }
          }
          this.tableLangData.value = appDetail.description;
          this.dialogForm.chargeType = appDetail.chargeType + "";
        })

      },
      dateFormat(row, column) {
        const createTime = formatDate(new Date(row.createTime), "yyyy-MM-dd hh:mm:ss");
        return createTime;
      },
      downloadFlagFormatter(row, column){
        return row.downloadFlag == 0?"未下载":"已下载";
      },
      getOrderList() {
        this.orderParam.appId = this.appId;
        getMyOrderList(this.orderParam).then(response => {
          let result = response.data.data;
          this.orderLog = result.content;
          this.orderTable.pageSize = result.size;
          this.orderTable.totalElements = result.totalElements
        })
      },
      handleCurrentChange(val) {
        this.licenceParam.start = val - 1;
        this.getLicenceList();
      },
      handleOrderCurrentChange(val) {
        this.orderParam.start = val - 1;
        this.getOrderList();
      },

      getLicenceList() {
        this.licenceParam.appId = this.appId;
        getLicenceList(this.licenceParam).then(response => {
          let result = response.data.data;
          this.licence = result.content;
          this.licenceTable.pageSize = result.size;
          this.licenceTable.totalElements = result.totalElements
        })
      },
      jumpToDetail(row) {
        let param = {id: row.id, userId: row.userId, status: row.status};
        localStorage.setItem("orderDetailParam", JSON.stringify(param));
        this.$router.push({name: 'orderDetail', params: param});
      },
      handleChange(val) {
        console.log(val);
      },

      submitRenew() {
        this.$refs.dialogForm.validate(valid => {
          if (valid) {
            renew(this.dialogForm).then(response => {
              this.showDialog = false;
              let result = response.data;
              if (result.errorCode != 200) {
                Message.error(result.errorMsg);
              } else {
                Message.success("操作成功");
                this.getOrderList();
              }
              console.log(response);
            })
          }
        })
      },
      deleteOrder(index, row) {
        this.$confirm('即将删除未完成订单, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteOrder(row.id).then(response => {
            let result = response.data;
            if (result.errorCode != 200) {
              Message.error(result.errorMsg);
            } else {
              Message.success("操作成功");
              console.log(index);
              this.orderLog.splice(index, 1);
            }
          });
        })

      },
      openLicenceDialog(type) {

        this.licenceForm.type = type;
        this.licenceForm.appId = this.appId;
        if(type == 1){
          this.dialogName = "创建证书";
        }else{
          this.dialogName = "批量创建";
        }
        this.dialogFormVisible = true;
        this.$refs.licenceForm.clearValidate();//清除上次的校验结果

      },
      addLicence(){

        if(this.licenceForm.type==1){//单个创建
          this.$refs.licenceForm.validateField("serial",message=> {
            if (message != "") {
              return;
            }
            addLicence(this.licenceForm).then(response=>{
              let result = response.data;
              console.log(response);
              if (result.errorCode != 200) {
                Message.error(result.errorMsg);
              } else {
                Message.success("操作成功");
                this.dialogFormVisible = false;
                this.getLicenceList();
              }

            });
          })
        }else if(this.licenceForm.type == 2){
          this.licenceBatchParam.append('appId', this.appId);
          console.log(this.licenceBatchParam.get("file"));
          addLicenceBatch(this.licenceBatchParam).then(response=>{
            let result = response.data;
            console.log(response);
            if (result.errorCode != 200) {
              Message.error(result.errorMsg);
            } else {
              Message.success("操作成功");
              this.dialogFormVisible = false;
              this.getLicenceList();
            }

          });
        }
      },
      handlePreview(file){
        this.licenceBatchParam = new FormData();
        this.licenceBatchParam.append('file', file.raw, file.name);
        return false;

      },
      onDownloadFlagChange(check){
        if(!check){//全部
          this.licenceParam.downloadFlag = 1;
        }else{//未下载过
          this.licenceParam.downloadFlag = 0;
        }
        this.getLicenceList();
      },
      downloadBatch(){
        window.location.href=process.env.BASE_API+"licence/download?appId="+this.appId+"&downloadFlag="+this.licenceParam.downloadFlag;
      },
      download(row){
        window.location.href=process.env.BASE_API+"licence/download/"+row.id;
      }
    }
  }
</script>

<style scoped>
  .operate_content {
    margin: 0 0 5px 0;
    float: right;
  }

</style>
