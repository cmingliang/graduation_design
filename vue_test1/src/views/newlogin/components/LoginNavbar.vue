<template>
    <div class="login_navbar">
      <div class="login_main">
        <img class="logo_img" src="@/assets/logo_black.png"/>
        <span class="logo_text">阳光暖果</span>
      </div>
    </div>
</template>

<script>
    export default {
        name: "LoginNavbar"
    }
</script>

<style scoped>
  .login_navbar{
    background: #f5f5f5;
    height: 60px;
    /*line-height: 60px;*/
    width: 100%;
  }
  .login_main{
    max-width: 1000px;
    height: 100%;
    margin: 0 auto;
    position: relative;

  }
  .logo_img{
    position: absolute;
    top: 50%;
    left:40px;
    transform:translateY(-50%);
  }
  .logo_text{
    position: absolute;
    font-size: 26px;
    color: #28334b;
    font-weight: bold;
    margin-left: 180px;
    top: 21px;

  }
</style>
