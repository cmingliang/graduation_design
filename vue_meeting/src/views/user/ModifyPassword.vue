<template>
  <div class="content_container">
    <div class="info_container">
      <h3>修改密码</h3>
      <el-form :label-position="'left'"  :rules="passwordRules"  ref="form" :model="form" label-width="100px" >
        <el-form-item label="原密码" prop="oldPassword">
          <el-input v-model.trim="form.oldPassword" type="password"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input v-model.trim="form.newPassword" type="password" ></el-input>
        </el-form-item>
        <el-form-item label="确认新密码" prop="newPasswordRepeat">
          <el-input v-model.trim="form.newPasswordRepeat" type="password"></el-input>
        </el-form-item>
      </el-form>
      <div class="text-center">
        <el-button type="primary"  @click="modifyPassword">确认修改</el-button>
      </div>
    </div>
  </div>
</template>


<script>

  import  {modifyPassword} from '@/api/user';
  import { Message } from 'element-ui'
  import router from '@/router'
  export default {
    created(){

    },
    data() {
      const validatePassword = (rule, value, callback) => {
        if (value == null || value == "") {
          callback(new Error('原密码不能为空'))
        } else {
          callback()
        }
      }
      const validateNewPassword = (rule, value, callback) => {
        if (value.length < 6) {
          callback(new Error('密码长度不能低于6位'))
        } else if(value == this.form.oldPassword){
          callback(new Error('不能使用旧密码'))
        }
        else {
          callback()
        }
      }
      const validateNewPasswordRepeat = (rule, value, callback) => {
        if (value != this.form.newPassword) {
          callback(new Error('新密码两次输入不一致'))
        } else {
          callback()
        }
      }
      return {
        form: {
          oldPassword:'',
          newPassword: '',
          newPasswordRepeat: ''
        },
        passwordRules: {
          oldPassword: [{required: true, trigger: 'blur', validator: validatePassword}],
          newPassword: [{required: true, trigger: 'blur', validator: validateNewPassword}],
          newPasswordRepeat: [{required: true, trigger: 'blur', validator: validateNewPasswordRepeat}]
        },
      }
    },

    methods: {
      onSubmit() {
        console.log('submit!');
      },

      modifyPassword(){
        this.$refs.form.validate(valid => {
          if(valid){
            let $qs = require('querystring') //nodejs自带querystring模块
            let data = $qs.stringify(this.form);
            modifyPassword(data).then(response=>{
              if(response.data.errorCode == 200){
                Message.success("操作成功");
                this.$store.dispatch('LogOut').then(() => {
                  location.reload()// In order to re-instantiate the vue-router object to avoid bugs
                })
              }else{
                Message.error(response.data.errorMsg);
              }
            });
          }
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
