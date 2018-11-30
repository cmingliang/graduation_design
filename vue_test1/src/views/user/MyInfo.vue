<template>
  <div class="content_container">
    <div class="info_container">
      <h3>用户信息</h3>
      <el-form :label-position="'left'"  :rules="formRules" ref="form" :model="form" label-width="100px" :disabled="forbidden">
        <el-form-item label="用户名" prop="name">
          <el-input v-model.trim="form.name"></el-input>
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model.trim="form.phone" disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model.trim="form.email"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="form.gender" placeholder="请选择性别">
            <el-option label="保密" value="0"></el-option>
            <el-option label="男" value="1"></el-option>
            <el-option label="女" value="2"></el-option>
          </el-select>
        </el-form-item>
        <h3>
          公司信息
        </h3>
        <el-form-item label="公司名称" >
          <el-input v-model.trim="form.cname"></el-input>
        </el-form-item>
        <el-form-item label="公司电话" >
          <el-input v-model.trim="form.cphone"></el-input>
        </el-form-item>
        <el-form-item label="公司地址" >
          <el-input v-model.trim="form.address"></el-input>
        </el-form-item>
        <el-form-item label="纳税人识别号" >
          <el-input v-model.trim="form.tin"></el-input>
        </el-form-item>
        <!--<el-form-item label="开户行账号" >-->
          <!--<el-input v-model="form.depositBank"></el-input>-->
        <!--</el-form-item>-->

      </el-form>
      <div class="text-center">
        <el-button type="primary" v-show="forbidden == true" @click="forbidden = false">修改</el-button>
        <el-button type="primary" v-show="forbidden == false" @click="updateUserInfo">确认修改</el-button>
        <!--<el-button>取消</el-button>-->
      </div>
    </div>
  </div>

</template>

<script>
  import { mapGetters } from 'vuex'
  import  {getUserInfo,updateUserInfo} from '@/api/user';
  import store from '@/store';
  import { Message } from 'element-ui'
  export default {
    created(){
      this.getUserInfo();
    },
    data() {
      const validateUsername = (rule, value, callback) => {
        if (!value) {
          callback(new Error('请输入正确的用户名'))
        } else {
          callback()
        }
      }
      return {
        forbidden:true,
        form: {
          id:'',
          name: '',
          phone: '',
          email: '',
          gender: '',
          cname: '',
          cphone: '',
          address: '',
          tin: '',
          depositBank:''
        },
        formRules: {
          name: [{required: true, trigger: 'blur', validator: validateUsername}]
        },
      }
    },
    computed: {
      ...mapGetters([
        'userInfo'
      ])
    },
    methods: {
      onSubmit() {
        console.log('submit!');
      },
      getUserInfo(){
        let  userId = store.getters.userInfo.id;
        getUserInfo(userId).then(response => {
          let user = response.data.data;
          this.form = user;
          this.form.gender = user.gender+"";
          this.$refs.form.disabled;

        })
      },

      updateUserInfo(){
        this.$refs.form.validate(valid => {
          if (!valid) {
            return;
          }
          let $qs = require('querystring') //nodejs自带querystring模块
          let data = $qs.stringify(this.form);
          updateUserInfo(data).then(response=>{
            if(response.data.errorCode == 200){
              Message.success("操作成功");
              this.forbidden = true;
            }else{
              Message.error(response.data.errorMsg);
            }
          });
        })

      }
    }
  }
</script>

<style scoped>
  .content_container {
    margin: 20px;
  }
  .info_container{
    width: 50%;
  }

</style>
