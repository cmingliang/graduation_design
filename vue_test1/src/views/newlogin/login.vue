<template>
  <div class="index_main">
    <LoginNavbar></LoginNavbar>
    <LoginContent></LoginContent>


  </div>

</template>

<script>
  import {LoginNavbar,LoginContent}  from './components'
    export default {
      name: "login",
      components: {
        LoginNavbar,
        LoginContent,
      },
      created(){
        console.log("hello test");
      }

    }
</script>

<style scoped>
.index_main{
  width: 100%;
  height:100%;
  min-height: 200px;
  background: #253046;
}
</style>
