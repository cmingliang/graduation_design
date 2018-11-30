<template>
  <div class="content_container">
    <div class="content_title">
      <h3>订单详情</h3>
    </div>
    <section class="steps_content">
      <el-steps :active="status"  align-center>
        <el-step title="创建应用" ></el-step>
        <el-step title="销售报价" ></el-step>
        <el-step title="审核" ></el-step>
        <el-step title="付款确认" ></el-step>
      </el-steps>
    </section>
    <section class="progress_info">
      <h4>订单处理信息</h4>
      <div>
        <el-table
          :data="tableData"
          stripe
          style="width: 100%">
          <el-table-column
            prop="createTime"
            :formatter="dateFormat"
            label="处理时间"
            >
          </el-table-column>
          <el-table-column
            prop="info"
            label="处理信息"
            >
          </el-table-column>
          <el-table-column
            prop="operatorName"
            label="操作人">
          </el-table-column>
        </el-table>
      </div>
    </section>
    <section class="user_info">
      <h4>用户信息</h4>
      <div>
        <MapTable :tableData="userData" :tableStyle="{ width:'100%' }"></MapTable>
      </div>
    </section>
    <section class="order_info">
      <h4>订单信息</h4>
      <div>
        <MapTable :tableData="orderData"  :tableStyle="{ width:'100%' }"></MapTable>
        <div class="operate_content">
          <el-button @click="openDialog(1)" v-if="hasPermission('a_order_baojia') && status == 1">报价</el-button>
          <el-button @click="openDialog(2)" v-if="hasPermission('a_order_check') && status == 2">审核</el-button>
        </div>
        <div style="clear: both"></div>
      </div>
    </section>
    <section class="payment_info">
      <h4>支付信息</h4>
      <div>
        <MapTable :tableData="paymentData"  :tableStyle="{ width:'100%' }"></MapTable>
        <div class="operate_content">
          <el-button @click="openDialog(3)"  v-if="hasPermission('a_order_confirm') && (status == 3 || status==4)">{{status == 3?'确认付款':'修改'}}</el-button>
        </div>
      </div>
    </section>
    <div>
      <Dialog v-show="dialog1.showDialog" :dialog-option="dialog1.dialogOption" @cancel="cancelOperate(1)" @submit="submitForm(1)" ref="dialog">
        <div slot="name1">

          <el-form :label-position="labelPosition"  ref="dialogForm" :model="dialog1.form" label-width="80px">

            <el-form-item label="订单号">
              <el-input v-model="dialog1.form.orderNo" disabled></el-input>
            </el-form-item>
            <el-form-item label="SDK">
              <el-input v-model="dialog1.form.sdk" disabled></el-input>
            </el-form-item>

            <el-form-item label="授权方式">
              <el-select v-model="dialog1.form.chargeType" placeholder="请选择授权方式" disabled >
                <el-option label="计时(年)" value="1"></el-option>
                <el-option label="计次" value="2"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="购买时长" v-show="dialog1.form.chargeType == 1">
              <el-input v-model="dialog1.form.number" min="1" type="number" disabled></el-input>
            </el-form-item>
            <el-form-item label="购买次数" v-show="dialog1.form.chargeType == 2">
              <el-input v-model="dialog1.form.number" min="1" type="number" disabled></el-input>
            </el-form-item>
            <el-form-item label="价格">
              <el-input v-model.trim="dialog1.form.totalPrice" min="1" type="number"></el-input>
            </el-form-item>

          </el-form>
        </div>
      </Dialog>

      <Dialog v-show="dialog2.showDialog" :dialog-option="dialog2.dialogOption" @cancel="cancelOperate(2)" @submit="submitForm(2)" ref="dialog">
        <div slot="name1">

          <el-form :label-position="labelPosition"  ref="dialogForm" :model="dialog2.form" label-width="80px">

            <el-form-item label="操作">
              <el-radio-group v-model="dialog2.form.operate">
                <el-radio label="1">通过</el-radio>
                <el-radio label="2">驳回</el-radio>
              </el-radio-group>
            </el-form-item>

            <el-form-item label="驳回理由" prop="desc" v-if="dialog2.form.operate == 2">
              <el-input type="textarea" v-model.trim="dialog2.form.reason"></el-input>
            </el-form-item>

          </el-form>
        </div>
      </Dialog>

      <Dialog v-show="dialog3.showDialog" :dialog-option="dialog3.dialogOption" @cancel="cancelOperate(3)" @submit="submitForm(3)" ref="dialog">
        <div slot="name1">

          <el-form :label-position="labelPosition"  ref="dialogForm" :model="dialog1.form" label-width="80px">

            <el-form-item label="付款账户">
              <el-input v-model.trim="dialog3.form.payerAccount" ></el-input>
            </el-form-item>
            <el-form-item label="收款账户">
              <el-input v-model.trim="dialog3.form.payeeAccount" ></el-input>
            </el-form-item>
            <el-form-item label="支付单号">
              <el-input v-model.trim="dialog3.form.payNo" ></el-input>
            </el-form-item>
            <el-form-item label="支付日期">
              <el-date-picker type="date" placeholder="选择时间" v-model="dialog3.form.payDate" format="yyyy-MM-dd" value-format="yyyy-MM-dd" style="width: 100%;"></el-date-picker>
            </el-form-item>
            <el-form-item label="已开发票">
              <el-select v-model="dialog3.form.invoiceStatus" placeholder="是否已开发票">
                <el-option label="未开" value="1"></el-option>
                <el-option label="已开" value="2"></el-option>
              </el-select>

            </el-form-item>
            <el-form-item label="发票号" v-show="dialog3.form.invoiceStatus == 2">
              <el-input v-model.trim="dialog3.form.invoiceNumber" ></el-input>
            </el-form-item>
            <el-form-item label="开票日期" v-show="dialog3.form.invoiceStatus == 2">
              <el-date-picker type="date" placeholder="选择时间" v-model="dialog3.form.invoiceTime" format="yyyy-MM-dd" value-format="yyyy-MM-dd" style="width: 100%;"></el-date-picker>
            </el-form-item>

            <el-form-item label="备注" prop="desc">
              <el-input type="textarea" v-model.trim="dialog3.form.remark "></el-input>
            </el-form-item>
          </el-form>
        </div>
      </Dialog>

    </div>
  </div>

</template>

<script>
    import MapTable from '@/components/MapTable/index';
    import  {getOrderTrail,getOrderInfo,getOrderPaymentInfo,quotedPrice,checkPrice,confirm} from '@/api/order';
    import  {getUserInfo} from '@/api/user';
    import {formatDate} from '@/utils/DateUtil'
    import Dialog from '@/components/dialog/index';
    import {hasPermission} from '@/utils/permission' // 权限判断函数
    import { Message } from 'element-ui';
    export default {
      name: "detail",
      created(){
        this.initData();
        this.getOrderTrail();
        this.getUserInfo();
        this.getOrderInfo();
        this.getOrderPaymentInfo();
      },
      components: {
        MapTable,Dialog
      },
      data(){
        return  {
          labelPosition:"right",
          userData: [
            {key: '公司名称', ekey:'cname', value: '--'},
            {key: '纳税人识别号', ekey:'tin', value: '--'},
            {key: '联系人', ekey:'name', value: '--'},
            {key: '手机号', ekey:'phone', value: '--'},
            {key: '邮箱', ekey:'email', value: '--'}
          ],
          orderData: [
            {key: '订单号', ekey:'orderNo', value: '--'},
            {key: '销售人员', ekey:'salerName', value: '--'},
            // {key: '应用', ekey:'appName', value: '--'},
            {key: 'sdk', ekey:'sdk', value: '--'},
            {key: '计费方式', ekey:'chargeTypeValue', value: '--'},
            {key: '数量', ekey:'number', value: '--'},
            // {key: '有效期', ekey:'duration', value: '--'},
            {key: '金额', ekey:'totalPrice', value: '--'}
          ],
          paymentData:[
            {key: '付款人账号', ekey:'payerAccount', value: '--'},
            {key: '收款人账号', ekey:'payeeAccount', value: '--'},
            {key: '支付方式', ekey:'payTypeValue', value: '--'},
            {key: '支付单号', ekey:'payNo', value: '--'},
            {key: '支付日期', ekey:'payDate', value: '--'},
            {key: '是否已开发票', ekey:'invoiceStatusValue', value: '--'},
            {key: '发票号', ekey:'invoiceNumber', value: '--'},
            {key: '开票日期', ekey:'invoiceTime', value: '--'},
            {key: '备注', ekey:'remark', value: '--'}
          ],
          tableData: [],
          orderId:'',
          appId:'',
          status:0,
          userId:'',
          dialog1:{
            showDialog:false,
            dialogOption:{
              title: '销售报价',
              cancelButtonText: '取消',
              confirmButtonText: '确定',
            },
            form:{
              orderNo:'',
              chargeType: '1',
              number: '',
              totalPrice: '',
              appId: ''
            }
          },
          dialog2:{
            showDialog:false,
            dialogOption:{
              title: '报价审核',
              cancelButtonText: '取消',
              confirmButtonText: '确定',
            },
            form:{
              orderNo:'',
              operate: '1',
              reason: ''
            }
          },
          dialog3:{
            showDialog:false,
            dialogOption:{
              title: '支付信息',
              cancelButtonText: '取消',
              confirmButtonText: '确定',
            },
            form:{
              orderNo:'',
              payerAccount: '',
              payeeAccount: '',
              payNo: '',
              payDate:'',
              invoiceStatus: '',
              invoiceStatusValue:'',
              invoiceNumber:'',
              invoiceTime:'',
              remark:''
            }
          },

        }

      },

      methods:{
        hasPermission,
        dateFormat(row, column){
          const createTime = formatDate(new Date(row.createTime),"yyyy-MM-dd");
          return createTime;
        },
        initData(){
          let param = this.$route.params;
          if(!param.id){
            param = JSON.parse(localStorage.orderDetailParam);
          }

          console.log(param);
          this.orderId = param.id;
          this.userId = param.userId;
        },
        getOrderTrail(){
          getOrderTrail(this.orderId).then(response => {
            this.tableData = response.data.data;

          })
        },
        getUserInfo(){
          getUserInfo(this.userId).then(response => {
            let userInfo = response.data.data;
            for(let i = 0 ; i< this.userData.length;i++){
              this.userData[i].value = userInfo[this.userData[i].ekey];
            }

          })
        },
        getOrderInfo(){
          getOrderInfo(this.orderId).then(response => {
            let orderInfo = response.data.data;
            for(let i = 0 ; i< this.orderData.length;i++){
              this.orderData[i].value = orderInfo[this.orderData[i].ekey];
            }


            this.dialog1.form.orderNo = orderInfo.orderNo;
            this.dialog1.form.sdk =  orderInfo.sdk;
            this.dialog1.form.chargeType = orderInfo.chargeType+"";
            this.dialog1.form.number = orderInfo.number;
            this.dialog1.form.appId = orderInfo.appId;
            this.dialog2.form.orderNo = orderInfo.orderNo;
            this.dialog3.form.orderNo = orderInfo.orderNo;
            this.status = orderInfo.status;



          })
        },
        getOrderPaymentInfo(){
          getOrderPaymentInfo(this.orderId).then(response => {
            let paymentInfo = response.data.data;
            for(let i = 0 ; i< this.paymentData.length;i++){

              if (this.paymentData[i].ekey == "invoiceTime" || this.paymentData[i].ekey == "payDate") {

                this.paymentData[i].value = paymentInfo[this.paymentData[i].ekey] != ""?formatDate(new Date(paymentInfo[this.paymentData[i].ekey]), "yyyy-MM-dd"):"";
              } else{
                this.paymentData[i].value = paymentInfo[this.paymentData[i].ekey];
              }



            }

            // this.dialog3.form.orderNo = paymentInfo.orderNo;
            this.dialog3.form.payerAccount =  paymentInfo.payerAccount;
            this.dialog3.form.payeeAccount = paymentInfo.payeeAccount;
            this.dialog3.form.payNo = paymentInfo.payNo;

            this.dialog3.form.payDate = paymentInfo.payDate != ""?formatDate(new Date(paymentInfo.payDate), "yyyy-MM-dd"):"";
            this.dialog3.form.invoiceStatus = paymentInfo.invoiceStatus+"";
            this.dialog3.form.invoiceNumber = paymentInfo.invoiceNumber;
            this.dialog3.form.invoiceTime = paymentInfo.invoiceTime!= ""?formatDate(new Date(paymentInfo.invoiceTime), "yyyy-MM-dd"):"";
            this.dialog3.form.remark = paymentInfo.remark;

          })
        },
        openDialog(num) {
          if(num == 1){
            this.dialog1.showDialog = true;
          }else if(num == 2){
            this.dialog2.showDialog = true;
          }else if(num == 3){
            this.dialog3.showDialog = true;
          }
        },
        cancelOperate(num) {
          if(num == 1){
            this.dialog1.showDialog = false;
          }else if(num == 2){
            this.dialog2.showDialog = false;
          }else if(num == 3){
            this.dialog3.showDialog = false;
          }
        },
        submitForm(num){
          if(num == 1){
            if(this.dialog1.form.totalPrice == null  || this.dialog1.form.totalPrice == ""){
              alert("请填写价格");
              return;
            }
            if(this.dialog1.form.totalPrice <= 0 ){
              alert("价格不能等于或小于0");
              return;
            }
            this.dialog1.form.sdk = "";
            quotedPrice(this.dialog1.form).then(response=>{
              this.dialog1.showDialog = false;
              if(response.data.errorCode == 200){
                Message.success("操作成功");
                this.status = 2;
                this.getOrderInfo();
                this.getOrderTrail();
              }else{
                Message.error(response.data.errorMsg);
              }
            })

          }else if(num == 2){
            checkPrice(this.dialog2.form).then(response=>{
              this.dialog2.showDialog = false;
              if(response.data.errorCode == 200){
                Message.success("操作成功");
                this.status = 3;
                this.getOrderTrail();
              }else{
                Message.error(response.data.errorMsg);
              }
            })

          }else if(num == 3){
            console.log(this.dialog3.form);
            confirm(this.dialog3.form).then(response=>{
              this.dialog3.showDialog = false;
              if(response.data.errorCode == 200){
                Message.success("操作成功");
                this.getOrderPaymentInfo();
                this.status = 4;
                this.getOrderTrail();
              }else{
                Message.error(response.data.errorMsg);
              }
            })

          }

        }

      }
    }
</script>

<style scoped>
  .content_container{
    margin: 20px;
  }
  .operate_content {
    margin: 5px 0 5px 0;
    float: right;
  }
</style>
