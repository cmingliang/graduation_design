import { loginByUsername, logout, getUserInfo } from '@/api/login'
import { getToken, setToken, removeToken } from '@/utils/auth'
import Cookies from 'js-cookie'

const user = {
  state: {
    userInfo: '',
    permissions: []
  },

  mutations: {
    SET_USER: (state, user) => {
      state.userInfo = user
    },
    SET_PERMISSIONS: (state, permissions) => {
      state.permissions = permissions
    }
  },

  actions: {
    // 用户名登录
    LoginByUsername({ commit }, userInfo) {
      const username = userInfo.username.trim()
      return new Promise((resolve, reject) => {
        loginByUsername(username, userInfo.password,userInfo.imageCode).then(response => {
          const data = response.data;
          let user = data.principal;
          let permission = data.authorities;
          // commit('SET_USER', user);
          commit('SET_PERMISSIONS', permission);
          Cookies.set("user",user);
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 获取用户信息
    GetUserInfo({ commit}) {
      return new Promise((resolve, reject) => {
        getUserInfo().then(response => {
          if (!response.data) { // 由于mockjs 不支持自定义状态码只能这样hack
            reject('error')
          }
          const data = response.data.data;
          let user = data.principal;
          let permission = data.authorities;
          commit('SET_USER', user);
          commit('SET_PERMISSIONS', permission);

          resolve(response);
        }).catch(error => {
          reject(error)
        })
      })
    },


    // 登出
    LogOut({ commit, state }) {
      return new Promise((resolve, reject) => {
        logout().then(() => {

          Cookies.set("user","");
          commit('SET_PERMISSIONS', []);
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 前端 登出
    FedLogOut({ commit }) {
      return new Promise(resolve => {
        Cookies.set("user","");
        commit('SET_PERMISSIONS', []);
        removeToken()
        resolve()
      })
    },

    // 动态修改权限
    ChangeRoles({ commit }, role) {
      return new Promise(resolve => {
        setToken(role)
        getUserInfo(role).then(response => {
          const data = response.data
          resolve()
        })
      })
    }
  }
}

export default user
