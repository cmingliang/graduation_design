const dashboard = {
  state: {
    showState: '日'
  },

  mutations: {
    CHANGE_SHOWSTATE: (state, data) => {
      state.showState = data
      console.log(state.showState);
    },

  },
}

export default dashboard
