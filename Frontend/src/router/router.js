import { createRouter, createWebHashHistory } from 'vue-router'

import Login from '../components/Login.vue'
import Register from '../components/Register.vue'
import ShowSchedule from '../components/ShowSchedule.vue'

import pinia from '../pinia'
import { userStore } from '../store/userStore'
let user = userStore(pinia)

let router = createRouter({
  history: createWebHashHistory(),
  routes: [
    {
      path: '/',
      component: Login,
    },
    {
      path: '/login',
      component: Login,
    },
    {
      path: '/register',
      component: Register,
    },
    {
      path: '/showSchedule',
      component: ShowSchedule,
    },
  ],
})

// 配置路由的前置守卫，在登录状态下才可以访问shoeSchedule.vue
router.beforeEach((to, from, next) => {
  // 如果查看日程
  if (to.path == '/showSchedule') {
    // 如果尚未登录
    if (user.username == '') {
      alert('尚未登陆，请登录后再查看日程')
      next('/login')
    } else {
      // 已经登陆，放行
      next()
    }
    // 其他资源，放行
  } else {
    next()
  }
})

export default router
