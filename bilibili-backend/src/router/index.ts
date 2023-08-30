import {createRouter, createWebHashHistory} from 'vue-router'

const router = createRouter({
  history: createWebHashHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'dashboard',
      component: () => import('../views/dashboard/DashboardVue.vue'),
      children: [
        {
          path: '/contribute',
          name: 'dashboard-contribute',
          component: () => import('@/views/dashboard/contribute/ContributePage.vue')
        },
        {
          path: '/grassGod',
          name: 'dashboard-grassGod',
          component: () => import('@/views/dashboard/grassGod/GrassGodPage.vue')
        },
        {
          path: '/',
          name: 'dashboard-home',
          component: () => import('../views/dashboard/home/home/HomePage.vue')
        },
        {
          path: '/home/advice',
          name: 'dashboard-advice',
          component: () => import('@/views/dashboard/home/advice/AdvicePage.vue')
        },
        {
          path: '/home/examine',
          name: 'dashboard-examine',
          component: () => import('@/views/dashboard/home/examine/ExaminePage.vue')
        },
        {
          path: '/home/banner',
          name: 'dashboard-banner',
          component: () => import('@/views/dashboard/home/banner/BannerPage.vue')
        },
        {
          path: '/income',
          name: 'dashboard-income',
          component: () => import('@/views/dashboard/income/IncomePage.vue')
        },
        {
          path: '/matter/todo',
          name: 'dashboard-todo',
          component: () => import('@/views/dashboard/matter/todo/TodoPage.vue')
        },
        {
          path: '/matter/project',
          name: 'dashboard-project',
          component: () => import('@/views/dashboard/matter/project/ProjectPage.vue')
        },
        {
          path: '/state/state',
          name: 'dashboard-state',
          component: () => import('@/views/dashboard/state/state/StatePage.vue')
        },
        {
          path: '/state/comment',
          name: 'dashboard-comment',
          component: () => import('@/views/dashboard/state/comment/CommentPage.vue')
        },
        {
          path: '/system/user',
          name: 'dashboard-user',
          component: () => import('@/views/dashboard/system/user/UserPage.vue')
        },
        {
          path: '/system/message',
          name: 'dashboard-message',
          component: () => import('@/views/dashboard/system/message/MessagePage.vue')
        },
        {
          path: '/system/file',
          name: 'dashboard-file',
          component: () => import('@/views/dashboard/system/file/FilePage.vue')
        },
        {
          path: '/system/tag',
          name: 'dashboard-tag',
          component: () => import('@/views/dashboard/system/tag/TagPage.vue')
        },
        {
          path: '/video/video',
          name: 'dashboard-video',
          component: () => import('../views/dashboard/video/video/VideoPage.vue')
        },
        {
          path: '/video/review',
          name: 'dashboard-review',
          component: () => import('../views/dashboard/video/review/ReviewPage.vue')
        },
        {
          path: '/video/scrolling',
          name: 'dashboard-scrolling',
          component: () => import('../views/dashboard/video/scrolling/ScorllingPage.vue')
        }
      ]
    },
    {
      path: '/test',
      name: 'dashboard-test',
      component: () => import('@/views/Test.vue')
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/login/LoginVue.vue')
    },
    {
      path: '/:pathMatch(.*)*',
      name: 'NotFound',
      component: () => import('../views/404/404Vue.vue')
    },
  ]
})

export default router
