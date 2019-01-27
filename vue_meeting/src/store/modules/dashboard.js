import moment from 'moment'

const dashboard = {
  state: {
    showState: '日',
    roomName: '',
    time: [],
    date: moment().startOf('day'),
    equipments: '',
    capacity: '',
    roomId: '',
    roomFilter: {
      capacity: '',
      name: '',
      equipments: []
    }
  },

  mutations: {
    CHANGE_SHOWSTATE: (state, data) => {
      state.showState = data
    },
    CHANGE_ROOMNAME: (state, data) => {
      state.roomName = data
    },
    CHANGE_TIME: (state, data) => {
      state.time = data
    },
    CHANGE_DATE: (state, data) => {
      state.date = data
    },
    CHANGE_EQUIPMENTS: (state, data) => {
      state.equipments = data
    },
    CHANGE_CAPACITY: (state, data) => {
      state.capacity = data
    },
    CHANGE_ROOMID: (state, data) => {
      state.roomId = data
    },
    CHANGE_ROOMFILTER: (state, data) => {
      state.roomFilter = Object.assign({}, state.roomFilter, data)
    }
  }
}

export default dashboard
