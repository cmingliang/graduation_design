<template>
  <div class="content_container">
    <div class="filter-container">
      <meeting-room-filter/>
      <meeting-show/>
    </div>   
    <meeting-table/>
    <meeting-page/>
  </div>
</template>

<script>
  import {mapGetters} from 'vuex'
  import {Message, MessageBox} from 'element-ui'
  import store from '@/store'
  import router from '@/router'
  import {hasPermission} from '@/utils/permission' // 权限判断函数
  import MeetingRoomFilter from './components/MeetingRoomFilter'
  import MeetingTable from './components/MeetingTable'
  import MeetingShow from './components/MeetingShow'
  import MeetingPage from './components/MeetingPage'



  export default {
    name: 'Dashboard',
    components: {
      MeetingRoomFilter,
      MeetingTable,
      MeetingShow,
      MeetingPage,
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
  .filter-container {
    display: flex;
    justify-content: space-between;
  }
</style>
