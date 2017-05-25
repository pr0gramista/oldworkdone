// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'
import VueFire from 'vuefire'
import firebase from 'firebase'

Vue.config.productionTip = false

Vue.use(VueFire)

var config = {
  apiKey: 'AIzaSyAQH_mzKLGnD7a-MubUxR-zz0AmNGpH2PQ',
  authDomain: 'workdone-160900.firebaseapp.com',
  databaseURL: 'https://workdone-160900.firebaseio.com',
  projectId: 'workdone-160900',
  storageBucket: 'workdone-160900.appspot.com',
  messagingSenderId: '753972019068'
}
firebase.initializeApp(config)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  template: '<App/>',
  components: { App }
})
