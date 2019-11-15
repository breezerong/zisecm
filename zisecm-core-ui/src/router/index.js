import Vue from 'vue'
import Router from 'vue-router'
import adminRouter from '@/router/admin.js'
import recordRouter from '@/router/record.js'
import searchRouter from '@/router/search.js'
import dcRouter from '@/router/doccenter.js'
import reportRouter from '@/router/report.js'
import userRouter from '@/router/user.js'
import store from '@/store'

Vue.use(Router)

if (sessionStorage.getItem('token')) {
	store.commit('set_token', sessionStorage.getItem('access-token'))
}

var currentRouter = {
	routers: [
		{
			meta: {
				requireAuth: true
			},
			path: '/home',
			component: () => import( /* webpackChunkName: "home" */ '@/views/Home.vue'),
			name: '首页'
		},
		
		{
			meta: {
				requireAuth: true
			},
			path: '/helpcenter',
			name: '帮助中心',
			component: () => import( /* webpackChunkName: "helpcenter" */ '@/views/HelpCenter.vue')
		}
	]
}

const router = new Router({
	routes: [{
			meta: {
				requireAuth: true
			},
			path: '/',
			name: 'main',
			component: () => import( /* webpackChunkName: "home1" */ '@/views/Main.vue'),
			children: [
				searchRouter,
				dcRouter,
				recordRouter,
				reportRouter,
				adminRouter,
				userRouter
			]
		},
		{
			meta: {
				requireAuth: true,
				permit: 9
			},
			path: '/viewdoc',
			component: () => import( /* webpackChunkName: "managercenter" */ '@/views/dc/ViewDoc.vue'),
			name: '查看文档'
		},
		{
			path: '/login',
			name: 'login',
			component: () => import( /* webpackChunkName: "Login" */ '@/views/Login.vue')
		}
	]
})
router.beforeEach((to, from, next) => {
	var user = sessionStorage.getItem('access-token')
	if (!user && to.path !== '/login') {
		sessionStorage.removeItem('access-user')
		sessionStorage.removeItem('access-token')
		next('/login')
	} else {
		next()
	}

})

export default router;
