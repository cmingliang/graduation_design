<template>
  <div class="navbar">
    <div class="logo">
      <img class="logo_img" src="@/assets/logo@2x.png"/>
      <span class="logo_text">阳光暖果授权证书管理系统</span>
    </div>
    <div class="right-menu">
      <template v-if="device!=='mobile'">
        <!--<error-log class="errLog-container right-menu-item"/>--><!--错误日志按钮-->
       <!-- 全屏按钮-->
        <!--<el-tooltip :content="$t('navbar.screenfull')" effect="dark" placement="bottom">-->
          <!--<screenfull class="screenfull right-menu-item"/>-->
        <!--</el-tooltip>-->
        <!--<lang-select class="international right-menu-item"/>-->
      </template>

      <el-dropdown class="avatar-container right-menu-item" trigger="click">
        <div class="message-wrapper"><i class="el-icon-bell"/></div>
        
        <div class="avatar-wrapper">
         <!--<span class="svg-container ">-->
          <!--<svg-icon icon-class="user" />-->
        <!--</span>-->
          <!--<img src="@/assets/head1.jpg" class="user-avatar">-->
          <img class="user-avatar" src="@/assets/bear.jpg" alt="bear">
          <span class="user-name" style="color: #fff">
            {{userInfo.name}}
          </span>
          <i class="el-icon-caret-bottom"/>
        </div>
        <el-dropdown-menu slot="dropdown">
          <router-link to="/user/myinfo" v-if="hasPermission('u_myInfo')">
            <el-dropdown-item>
              {{ $t('navbar.myInfo') }}
            </el-dropdown-item>
          </router-link>
          <a target="_blank" >
            <router-link to="/user/modifyPassword">
            <el-dropdown-item>
              {{ $t('navbar.changePassword') }}
            </el-dropdown-item>
            </router-link>
          </a>
          <el-dropdown-item divided>
            <span style="display:block;" @click="logout">{{ $t('navbar.logOut') }}</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import Hamburger from '@/components/Hamburger'
import ErrorLog from '@/components/ErrorLog'
import Screenfull from '@/components/Screenfull'
import LangSelect from '@/components/LangSelect'
import {hasPermission} from '@/utils/permission' // 权限判断函数


export default {
  components: {
    Breadcrumb,
    Hamburger,
    ErrorLog,
    Screenfull,
    LangSelect
  },
  computed: {
    ...mapGetters([
      'userInfo',
      'name',
      'avatar',
      'device'
    ])
  },
  methods: {
    hasPermission,
    toggleSideBar() {
      this.$store.dispatch('toggleSideBar')
    },
    logout() {
      this.$store.dispatch('LogOut').then(() => {
        location.reload()// In order to re-instantiate the vue-router object to avoid bugs
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .logo_img{
    position: absolute;
    top: 3px;
    width: 122px;
    margin-left: 2px;
    margin-top: 20px;
  }
.navbar {
  position:fixed;
  width: 100%;
  background: #3cafcc;
  z-index: 5;
  box-shadow: 1px 2px 10px 0 rgba(0, 0, 0, 0.1);

  height: 80px;
  line-height: 60px;
  border-radius: 0px !important;
  .logo{
    float: left;
    margin: 0 20px;
    color:#606266;

    .logo_text{
      width: 249px;
      height: 24px;
      font-family: MicrosoftYaHei;
      font-size: 24px;
      font-weight: normal;
      font-stretch: normal;
      line-height: 24px;
      letter-spacing: 1px;
      color: #ffffff;
      margin-left: 141px;
      position: relative;
      top:21px;
    }
  }

  .breadcrumb-container{
    float: left;
  }
  .errLog-container {
    display: inline-block;
    vertical-align: top;
  }
  .right-menu {
    float: right;
    height: 100%;
    margin-right: 10px;
    &:focus{
     outline: none;
    }
    .right-menu-item {
      display: inline-block;
      margin: 0 8px;     
    }
    .screenfull {
      height: 20px;
    }
    .international{
      vertical-align: top;
    }
    .theme-switch {
      vertical-align: 15px;
    }
   
    .avatar-container {
      height: 100%;
      margin-right: 15px;
      .message-wrapper {
        height: 100%;
        display: inline-block;
        margin-right: 30px;
        display: inline-block;  
        line-height:80px; 
        .el-icon-bell {
        font-size: 24px;
        color: white;
        vertical-align: middle;
      }
      }
      .avatar-wrapper {
        cursor: pointer;
        height: 100%;
        position: relative;
        display: inline-block;  
        line-height:80px; 
        .user-name {
          height: 100%;
          vertical-align: middle;
        }
        .user-avatar {
          width: 30px;
          height: 30px;
          border-radius: 50%;
          vertical-align: middle;
        }
        .el-icon-caret-bottom {
          font-size: 12px;
          color: white;
          vertical-align: middle;
        }
       
      }
    }
  }
}
</style>
