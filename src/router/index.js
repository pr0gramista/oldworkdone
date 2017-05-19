import Vue from 'vue'
import Router from 'vue-router'
import Dashboard from '@/components/Dashboard'
import Front from '@/components/Front'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Front',
      component: Front
    },
    {
      path: '/dashboard',
      name: 'Dashboard',
      component: Dashboard
    }
  ]
})
