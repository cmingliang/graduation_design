<template>
  <div class="content_container">
    <el-form ref="form" :rules="formRules" :model="form" label-width="90px">
      <el-form-item label="应用名称" prop="appName" >
        <el-input v-model.trim="form.appName" maxlength="50"></el-input>
      </el-form-item>
      <el-form-item label="Bundle Id" prop="bundleId" >
        <el-input v-model.trim="form.bundleId" maxlength="50"></el-input>
      </el-form-item>
      <el-form-item label="应用分类" prop="type" >
        <el-cascader
          expand-trigger="hover"
          :show-all-levels="false"
          :options="options"
          :props="defaultProps"
          v-model="selectedOptions"
          @change="handleChange">
        </el-cascader>

      </el-form-item>


      <el-form-item label="SDK选择" prop="sdk" >
        <el-checkbox-group v-model="checkList">
          <el-checkbox label="人脸识别SDK" >人脸识别SDK</el-checkbox>
          <el-checkbox label="人脸对比SDK" >人脸对比SDK</el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item label="应用平台" prop="platform" >
        <el-radio-group v-model="form.platform">
          <el-radio label="1">APP-Android</el-radio>
          <el-radio label="2">APP-IOS</el-radio>
          <el-radio label="3" >终端-Android-在线</el-radio>
          <el-radio label="4" >终端-Android-离线</el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="授权方式" prop="chargeType" >
        <DynamicSelect optionName="appinfo_charge_type" addAllOption="false" :defaultValue="form.chargeType"  v-model="form.chargeType"></DynamicSelect>
        <!--<el-select v-model="form.chargeType" placeholder="请选择授权方式">-->
          <!--<el-option label="计时(年)" value="1"></el-option>-->
          <!--<el-option label="计次" value="2"></el-option>-->
        <!--</el-select>-->
      </el-form-item>
      <el-form-item label="购买时长" v-show="form.chargeType == 1" prop="number" >
        <el-input v-model.trim="form.number" min="1" type="number"></el-input>
      </el-form-item>
      <el-form-item label="购买数量" v-show="form.chargeType == 2" prop="number" >
        <el-input v-model.trim="form.number"  min="1" type="number"></el-input>
      </el-form-item>
      <el-form-item label="销售人员" prop="saler" >
         <DynamicSelect optionName="saler" addAllOption="false"   :defaultValue="form.saler" v-model="form.saler"></DynamicSelect>
        <!--<el-select v-model="form.saler" placeholder="请选择销售人员">-->
          <!--<el-option-->
            <!--v-for="item in salers"-->
            <!--:key="item.key"-->
            <!--:label="item.value"-->
            <!--:value="item.key">-->
          <!--</el-option>      -->
        <!--</el-select>-->
      </el-form-item>
      <el-form-item label="应用描述" >
        <el-input type="textarea" v-model.trim="form.description" placeholder="不超过200字" maxlength="200"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="onSubmit">提交</el-button>
        <el-button type="success" @click="onSave">保存</el-button>
        <el-button @click="back">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import  { addApplication,saveApplication ,getTempApplication} from '@/api/application';
  import { Message } from 'element-ui';
  import select from '@/select/index'
  import DynamicSelect from '@/views/select/DynamicSelect'


  export default {
    created() {
      this.onPageLoad();
      this.defaultSaler = this.form.saler;
      this.defaultChargeType = this.form.chargeType;
    },
    components: {
      DynamicSelect
    },
    data() {
      const validateAppName = (rule, value, callback) => {
        if (value == null || value == "") {
          callback(new Error('应用名不能为空'))
        } else {
          callback()
        }
      }
      const validateBundleId = (rule, value, callback) => {
        if (value == null || value == "") {
          callback(new Error('bundleId不能为空'))
        } else {
          callback()
        }
      }
      const validateType = (rule, value, callback) => {
        if (value == null || value == "") {
          callback(new Error('请选择类型'))
        } else {
          callback()
        }
      }
      const validateSdk = (rule, value, callback) => {
        if (value == null || value == "") {
          callback(new Error('请选择sdk'))
        } else {
          callback()
        }
      }
      const validatePlatform = (rule, value, callback) => {
        if (value == null || value == "") {
          callback(new Error('请选择应用平台'))
        } else {
          callback()
        }
      }

      const validateChargeType = (rule, value, callback) => {
        if (value == null || value == "") {
          callback(new Error('请选择授权方式'))
        } else {
          callback()
        }
      }
      const validateNumber = (rule, value, callback) => {
        if (value == null || value == "") {
          callback(new Error('请填写数量'))
        } else if(value <= 0){
          callback(new Error('数量必须大于或者等于1'))
        }else {
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
        checkList:[],
        form: {
          appName: '',
          bundleId:'',
          type: '',
          sdk:[],
          platform:'',
          chargeType: '',
          number: '',
          saler: '',
          description: ''
        },
        formRules:{
          appName: [{required: true, trigger: 'blur', validator: validateAppName}],
          bundleId: [{required: true, trigger: 'blur', validator: validateBundleId}],
          type: [{required: true, trigger: 'blur', validator: validateType}],
          sdk:[{required: true, trigger: 'blur', validator: validateSdk}],
          platform:[{required: true, trigger: 'blur', validator: validatePlatform}],
          chargeType: [{required: true, trigger: 'blur', validator: validateChargeType}],
          number: [{required: true, trigger: 'blur', validator: validateNumber}],
          saler: [{required: true, trigger: 'blur', validator: validateSaler}],
        },
        selectedOptions: [],
        options:select.app_type_options,
        defaultProps: {
          value: 'label',
          label: 'label'
        },
        salers:[],
        defaultSaler:'',
        defaultChargeType:'',
      }
    },
    methods: {

      onSubmit() {
        this.form.sdk = this.checkList;
        console.log(this.form);
        this.$refs.form.validate(valid => {
          if(valid){
            let $qs = require('querystring') //nodejs自带querystring模块
            let data = $qs.stringify(this.form);

            addApplication(data).then(response => {
              if(response.data.errorCode == 200){
                Message.success("操作成功");
                this.$router.push({ name: 'Application'});
              }else{
                Message.error(response.data.errorMsg);
              }
            });
          }
        })

      },
      onSave(){
        this.form.sdk = this.checkList;
        console.log(this.form);
        let $qs = require('querystring') //nodejs自带querystring模块
        let data = $qs.stringify(this.form);
        console.log(data);
        saveApplication(data).then(response => {
          if(response.data.errorCode == 200){
            Message.success("操作成功");
            // this.$router.push({ name: 'Application'});
          }else{
            Message.error(response.data.errorMsg);
          }
        })
      },
      onPageLoad(){
        // if(store.getters.selectOption.saler.length == 0){
        //   this.$store.dispatch('loadSelectOption', 'saler').then((response)=>{
        //     this.salers = response.data.data;
        //   });
        // }else{
        //   this.salers = store.getters.selectOption.saler;
        // }
        // console.log(this.salers);


        getTempApplication().then(response => {
          this.form = response.data.data;
          this.selectedOptions = this.form.type.split("-");
          this.form.platform = this.form.platform+"";
          this.form.chargeType = this.form.chargeType;
          this.checkList = this.form.sdk==""?[]:this.form.sdk;
        });


      },
      back(){
        this.$router.go(-1);//返回上一层
      },
      handleChange(value) {
        let temp = value[0];
        if(value.length > 1){
          temp = temp+"-"+value[1];
        }
        this.form.type = temp;
      }
    }
  }
</script>

<style scoped>
  .content_container {
    margin: 20px;

  }
  .el-input{
     width: 200px !important;
   }
</style>
