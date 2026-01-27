import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router'

// 路由配置
const routes: RouteRecordRaw[] = [
  {
    path: '/',
    name: 'home',
    component: () => import('../views/index.vue'),
    meta: {
      title: '药膳云厨 - 首页',
      requiresAuth: false,
      transition: 'fade'
    }
  },
  {
    path: '/yjkquery',
    name: 'yjkquery',
    component: () => import('../views/Yjkquery.vue'),
    meta: {
      title: '亚健康查询',
      requiresAuth: false,
      transition: 'slide-left'
    }
  },
  {
    path: '/article',
    name: 'article',
    component: () => import('../views/article.vue'),
    meta: {
      title: '文献资料',
      requiresAuth: false,
      transition: 'slide-left'
    }
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('@/views/login.vue'),
    meta: {
      title: '用户登录',
      requiresAuth: false,
      transition: 'fade'
    },
    beforeEnter: (to, from, next) => {
      // 如果已登录，重定向到首页
      if (localStorage.getItem('isLoggedIn') === 'true') {
        next('/')
      } else {
        next()
      }
    }
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('@/views/register.vue'),
    meta: {
      title: '用户注册',
      requiresAuth: false,
      transition: 'fade'
    },
    beforeEnter: (to, from, next) => {
      // 如果已登录，重定向到首页
      if (localStorage.getItem('isLoggedIn') === 'true') {
        next('/')
      } else {
        next()
      }
    }
  },
  {
    path: '/user',
    name: 'user',
    component: () => import('@/views/user.vue'),
    meta: {
      title: '个人中心',
      requiresAuth: true,
      transition: 'slide-left'
    }
  },
  {
    path: '/administrate',
    name: 'administrate',
    component: () => import('@/views/administrate.vue'),
    meta: {
      title: '管理后台',
      requiresAuth: true,
      requiresAdmin: true,
      transition: 'slide-left'
    }
  },
  {
    path: '/ttt',
    name: 'ttt',
    component: () => import('@/views/ttt.vue'),
    meta: {
      title: '测试页面',
      requiresAuth: false,
      transition: 'fade'
    }
  },
  // 404 页面
  {
    path: '/:pathMatch(.*)*',
    name: 'notFound',
    component: () => import('../views/index.vue'),
    meta: {
      title: '页面未找到',
      requiresAuth: false
    },
    beforeEnter: (to, from, next) => {
      // 重定向到首页
      next('/')
    }
  }
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
  // 滚动行为优化
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      // 浏览器前进/后退时恢复滚动位置
      return savedPosition
    } else if (to.hash) {
      // 如果有锚点，滚动到锚点位置
      return {
        el: to.hash,
        behavior: 'smooth'
      }
    } else {
      // 默认滚动到顶部
      return { top: 0, behavior: 'smooth' }
    }
  }
})

// 全局前置守卫 - 路由权限控制
router.beforeEach((to, from, next) => {
  // 设置页面标题
  if (to.meta.title) {
    document.title = to.meta.title as string
  }

  const isLoggedIn = localStorage.getItem('isLoggedIn') === 'true'
  const account = localStorage.getItem('account')

  // 检查是否需要登录
  if (to.meta.requiresAuth && !isLoggedIn) {
    // 未登录，重定向到登录页
    next({
      path: '/login',
      query: { redirect: to.fullPath } // 保存目标路由，登录后可以跳转回来
    })
  } else if (to.meta.requiresAdmin && account !== 'admin') {
    // 需要管理员权限但不是管理员
    next('/')
  } else {
    next()
  }
})

// 全局后置钩子 - 页面加载完成后的处理
router.afterEach((to, from) => {
  // 可以在这里添加页面访问统计等逻辑
  console.log(`路由跳转: ${from.path} -> ${to.path}`)
})

// 路由错误处理
router.onError((error) => {
  console.error('路由错误:', error)
})

export default router
