<template>
  <div class="navbar">
    <div class="logo">
      <img class="logo_img" src="@/assets/logo@2x.png" />
      <span class="logo_text">阳光暖果会议预约系统</span>
    </div>
    <div class="right-menu">
      <template v-if="device!=='mobile'">
        <!--<error-log class="errLog-container right-menu-item"/>-->
        <!--错误日志按钮-->
        <!-- 全屏按钮-->
        <!--<el-tooltip :content="$t('navbar.screenfull')" effect="dark" placement="bottom">-->
        <!--<screenfull class="screenfull right-menu-item"/>-->
        <!--</el-tooltip>-->
        <!--<lang-select class="international right-menu-item"/>-->
      </template>

      <div class="message-wrapper">
        <el-dropdown class="message-down" :hide-on-click="false">
          <el-badge :value="messageNumber" class="item" :hidden="messageNumber===0">
            <i class="el-icon-bell" />
          </el-badge>
          <el-dropdown-menu slot="dropdown" class="noticedropdown">
            <el-dropdown-item>
              <div class="message-card" style="display:flex;justify-content:space-between">
                会议信息
                <span style="color:#777777;font-size:12px">忽略全部</span>
              </div>
            </el-dropdown-item>
            <el-dropdown-item divided v-for="list in listData" :key="list.id">
              <div class="message-card">
                <p>{{list.createBy.name}}邀请你参加会议：{{list.title}}</p>
                <p>会议时间：{{list.startTime}}-{{list.endTime}}</p>
                <p>会议地点：{{list.roomName}}</p>
                <div v-if='userState[list.id]===0'>
                  <p>请确认是否参加</p>
                  <el-button type="primary" class="message-btn" style="background-color: #3cafcc;" @click='handleJoin({state:1,meetingId:list.id})'>参加</el-button>
                  <el-button type="primary" class="message-btn" style="background-color: #f5856d;" @click='handleJoin({state:2,meetingId:list.id})'>请假</el-button>
                </div>
                <div v-else-if='userState[list.id]===1'>
                  <el-button type="primary" class="message-btn" style="background-color: #3cafcc;" disabled>已参加</el-button>
                </div>
                <div v-else-if='userState[list.id]===2'>
                  <el-button type="primary" class="message-btn" style="background-color: #f5856d;" disabled>已请假</el-button>
                </div>
              </div>
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>

      <div class="avatar-wrapper">
        <el-dropdown class="avatar-container right-menu-item" trigger="click">
          <div class="top-dropdown">
            <img class="user-avatar" src="@/assets/bear.jpg" alt="bear">
            <span class="user-name" style="color: #fff">
              {{userInfo.name}}
            </span>

            <i class="el-icon-caret-bottom" />
          </div>
          <el-dropdown-menu slot="dropdown">
            <router-link to="/user/myinfo" v-if="hasPermission('u_myInfo')">
              <el-dropdown-item>
                {{ $t('navbar.myInfo') }}
              </el-dropdown-item>
            </router-link>
            <a target="_blank">
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
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import Hamburger from '@/components/Hamburger'
import ErrorLog from '@/components/ErrorLog'
import Screenfull from '@/components/Screenfull'
import LangSelect from '@/components/LangSelect'
import { hasPermission } from '@/utils/permission' // 权限判断函数
import moment from 'moment'
import { joinMeeting, getUserState } from '@/api/navbar'


export default {
  components: {
    Breadcrumb,
    Hamburger,
    ErrorLog,
    Screenfull,
    LangSelect
  },
  data() {
    return {
      messageNumber: 2,
      listData: [],
      userState: ''
    }
  },
  computed: {
    ...mapGetters([
      'userInfo',
      'name',
      'avatar',
      'device'
    ]),
    listLength() {
      return listData.length
    }
  },
  methods: {
    hasPermission,
    handleJoin(data) {
      joinMeeting(data).then(
        response => {
          getUserState().then(
            response => {
              this.userState = response.data
            }
          ).catch(
            error => {
              console.log(error);
            }
          )
        }
      ).catch(
        error => { console.log(error); }
      )
    },
    toggleSideBar() {
      this.$store.dispatch('toggleSideBar')
    },
    logout() {
      this.$store.dispatch('LogOut').then(() => {
        location.reload()// In order to re-instantiate the vue-router object to avoid bugs
      })
    }
  },
  mounted: function () {
    let es = new EventSource('/api/es')
    es.onmessage = event => {
      let a = JSON.parse(event.data);
      a = a.filter(item => {
        if (item.state === 1) {
          return true
        } else {
          return false
        }
      })
      a.map(item => {
        let b = []
        item.startTime = moment(item.startTime.time).format('YYYY-MM-D HH:mm')
        item.endTime = moment(item.endTime.time).format('YYYY-MM-D HH:mm')
        item.rooms.forEach(element => { b = b.concat(element.roomName) });
        item.roomName = b.join(',')
        return item
      })
      a.reverse()
      this.listData = a
    }
    getUserState().then(
      response => {
        this.userState = response.data
      }
    ).catch(
      error => {
        console.log(error);
      }
    )

  }

}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.message-btn {
  width: 80px;
  height: 28px;
  border: 0;
  padding: 0 0;
  margin-top: 15px;
  margin-bottom: 15px;
}
.logo_img {
  position: absolute;
  top: 3px;
  width: 122px;
  margin-left: 2px;
  margin-top: 20px;
}
.message-card {
  p {
    font-size: 12px;
    color: #777777;
    height: 13px;
  }
}
.item {
  height: 24px;
  line-height: 24px;
}
.noticedropdown {
  max-height: 400px;
  overflow: scroll;
}
.message-down {
  line-height: 24px;
}
.navbar {
  position: fixed;
  width: 100%;
  background: #3cafcc;
  z-index: 5;
  box-shadow: 1px 2px 10px 0 rgba(0, 0, 0, 0.1);

  height: 80px;
  line-height: 60px;
  border-radius: 0px !important;
  .logo {
    float: left;
    margin: 0 20px;
    color: #606266;

    .logo_text {
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
      top: 21px;
    }
  }

  .breadcrumb-container {
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
    display: flex;
    &:focus {
      outline: none;
    }
    .right-menu-item {
      display: inline-block;
      margin: 0 8px;
    }
    .screenfull {
      height: 20px;
    }
    .international {
      vertical-align: top;
    }
    .theme-switch {
      vertical-align: 15px;
    }
    .message-wrapper {
      height: 100%;
      display: inline-block;
      margin-right: 30px;
      display: inline-block;
      line-height: 80px;
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
      line-height: 80px;
    }
  }
  .top-dropdown {
    height: 40px;
    line-height: 50px;
    vertical-align: middle;
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
</style>
