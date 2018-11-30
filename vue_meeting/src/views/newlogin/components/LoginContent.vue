<template>
  <div class="login_main">
    <div class="box-card">
      <div class="title">会议预约系统</div>
      <ul>
        <li v-for="item in items" :key="item" class="text item">
        {{item}}
        </li>
      </ul>    
    </div>
    <div class="login_form_container ">
      <LoginForm></LoginForm>
    </div>
  </div>

</template>

<script>
  import LoginForm  from './LoginForm'
    export default {
        name: "LoginContent",
        data: function () {
          return {
            items:[
              '快速预约',
              '自动审核',
              '会议提醒',
              '数据统计'
            ]
          }
        },
        components: {
          LoginForm
        },
    }
</script>

<style scoped>
  .login_main{
    max-width: 1000px;
    /*height: 100%;*/
    margin: 0 auto;
    position: relative;
    display: flex;
    justify-content:space-between;
    padding-top: 80px;
  }
  .logo_img{
    width: 550px;
  }
  .box-card{
    width: 500px;
    background-color: #253046
    
  }
  .title{
    font-size: 45px;
    font-display: bold;
    color:white;
  }
  .text{
    color: white;
    font-size: 15px;
    margin-top:10px;
  }
</style>
