import Vue from 'vue'
import Router from 'vue-router'
import {adminRouter} from '@/router/admin.js'
import {recordRouter} from '@/router/record.js'
import {searchRouter} from '@/router/search.js'
import {dcRouter} from '@/router/doccenter.js'
import {reportRouter} from '@/router/report.js'
import {userRouter} from '@/router/user.js'
import {workflowRouter} from '@/router/workflow.js'
import {testRouter} from '@/router/test.js'
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
				{
					meta: {
						requireAuth: true
					},
					path: '/home',
					component: () => import('@/views/Home.vue'),
					name: '首页',
					children:[
						{
							path: '',
							name: '首页内容',
							component: () => import('@/views/HomeContent.vue'),
						}
					]
				},
				searchRouter,
				...dcRouter,
				...recordRouter,
				workflowRouter,
				reportRouter,
				adminRouter,
				userRouter,
				...testRouter
			]
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
		next({"name":'login'})
	} else {
		next()
	}

})

export default router;
