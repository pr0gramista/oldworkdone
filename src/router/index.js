import Vue from 'vue'
import Router from 'vue-router'
import Dashboard from '@/components/Dashboard'
import Landing from '@/components/Landing'
import Work from '@/components/Work'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Landing',
      component: Landing
    },
    {
      path: '/work',
      name: 'Work',
      component: Work,
      children: [
        {
          path: '/',
          component: Dashboard
        }
      ]
    }
  ]
})
