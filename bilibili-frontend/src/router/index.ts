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
