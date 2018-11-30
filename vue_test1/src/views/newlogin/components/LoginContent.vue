<template>
  <div class="login_main">
    <div class="login_pic_container">
      <img class="logo_img"  src="@/assets/login_pic.png"/>
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
  }
  .logo_img{
    width: 550px;
  }
  .login_pic_container{
    float: left;
  }
  .login_form_container{
    margin-top: 80px;
    float: left;
  }
</style>
