import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    uuid: ''
  },
  mutations: {
    setUUID (state, newUUID) {
      state.uuid = newUUID
    }
  }
})
