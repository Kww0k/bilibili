import {createRouter, createWebHashHistory} from 'vue-router'

const router = createRouter({
    history: createWebHashHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'home',
            component: () => import('../views/home/HomeVue.vue'),
        },
        {
            path: '/message',
            name: 'message',
            component: () => import('../views/message/MessageVue.vue'),
            children: [
                {
                    path: '/selfMessage',
                    name: 'message-selfMessage',
                    component: () => import('@/views/message/selfMessage/SelfMessagePage.vue')
                },
                {
                    path: '/aiteME',
                    name: 'message-aiteME',
                    component: () => import('@/views/message/aiteMe/AiteMePage.vue')
                },
                {
                    path: '/likes',
                    name: 'message-likes',
                    component: () => import('@/views/message/likes/LikesPage.vue')
                },
                {
                    path: '/recoverMe',
                    name: 'message-recoverMe',
                    component: () => import('@/views/message/recoverMe/RecoverMePage.vue')
                },
                {
                    path: '/setting',
                    name: 'message-setting',
                    component: () => import('@/views/message/setting/SettingPage.vue')
                },
                {
                    path: '/systemMessage',
                    name: 'message-systemMessage',
                    component: () => import('@/views/message/systemMessage/SystemMessagePage.vue')
                },
            ]
        },
        {
            path: '/:id',
            name: 'info',
            component: () => import('../views/info/InfoVue.vue'),
            props: true
        },
        {
            path: '/:pathMatch(.*)*',
            name: 'NotFound',
            component: () => import('../views/404/404Vue.vue')
        },
    ]
})

export default router
