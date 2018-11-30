<template>
  <div class="content_container">
    <div v-if="!hasPermission('u_app')">
      欢迎您! <span class="link-type">{{userInfo.name}}</span>
    </div>
    <div v-if="hasPermission('u_app')">
      <panel-group @handleSetLineChartData="handleSetLineChartData"/>
      <div class="el-row information_container ">
        <h4>提示信息:</h4>
        <div class="page-guide">
          <ul class="content">
            <li>请用户在创建应用前到
              <router-link to="/user/myinfo" class="link-type">我的资料</router-link>
              完善个人和公司信息;
            </li>
            <li>如有疑问请先联系售前咨询:010-62163581-802 ;</li>
            <li>系统操作流程如下:
              <el-steps simple style="margin-top: 10px">
                <el-step title="创建应用" icon="el-icon-edit"></el-step>
                <el-step title="销售报价" icon="el-icon-tickets"></el-step>
                <el-step title="审价"></el-step>
                <el-step title="支付确认" icon="el-icon-success"></el-step>
              </el-steps>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import {mapGetters} from 'vuex'
  import {Message, MessageBox} from 'element-ui'
  import store from '@/store'
  import router from '@/router'
  import PanelGroup from './components/PanelGroup'
  import {hasPermission} from '@/utils/permission' // 权限判断函数




  export default {
    name: 'Dashboard',
    components: {
      PanelGroup

    },
    data() {
      return {
        currentRole: 'adminDashboard',
      }
    },
    computed: {
      ...mapGetters([
        'roles',
        'userInfo'
      ])
    },
    methods: {
      hasPermission,
      handleSetLineChartData(type) {

      }
    },
    created() {

    }
  }
</script>
<style>
  .dashboard-container {
    margin: 20px;

  }

  .information_container {
    background: rgb(255, 255, 255);
    padding: 16px 16px 0px;
    margin-bottom: 32px;
    border: 1px solid #ebeef5;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, .1);
    min-height: 200px;

  }

  .page-guide .content li {
    font-size: 14px;
    margin-bottom: 10px;
    color: #99a9bf;
    list-style: none;
  }

  .page-guide .content li:before {
    content: "";
    display: inline-block;
    width: 4px;
    height: 4px;
    border-radius: 50%;
    vertical-align: middle;
    background-color: #5e6d82;
    margin-right: 5px;
  }
</style>
