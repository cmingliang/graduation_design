import {loadSelectOption} from '@/api/selectOption'


const select = {
  state: {
    saler: []
  },

  mutations: {
    SET_SALER: (state, saler) => {
      state.saler = saler
    },

  },

  actions: {
    // 用户名登录
    loadSelectOption({ commit }, dictName) {

      return new Promise((resolve, reject) => {
        loadSelectOption(dictName).then(response => {
          const data = response.data.data;
          if(dictName == "saler"){
            commit('SET_SALER', data);
          }
          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },

  }
}

export default select
