import Vue from 'vue'
import Vuex from 'vuex'
import firebase from 'firebase'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    user: null
  },
  mutations: {
    user (state, newUser) {
      state.user = newUser
    }
  },
  getters: {
    uuid: state => {
      if (state.user != null) {
        return state.user.uid
      } else {
        return null
      }
    },
    habitsRef: state => {
      if (state.user != null) {
        return firebase.database().ref(state.user.uid + '/habits')
      } else {
        return null
      }
    },
    todosRef: state => {
      if (state.user != null) {
        return firebase.database().ref(state.user.uid + '/todos')
      } else {
        return null
      }
    }
  }
})
