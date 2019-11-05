import Vue from 'vue'
import Router from 'vue-router'
import coreRouter from '@/core/router.js'
import customRouter from '@/custom/router.js'
import store from './store'

Vue.use(Router)

var homeChildren = []
coreRouter.routers.forEach(element => {
	homeChildren.push(element);
})

customRouter.routers.forEach(element => {
	homeChildren.push(element);
})

if (sessionStorage.getItem('token')) {
	store.commit('set_token', sessionStorage.getItem('access-token'))
}

var currentRouter = {
	routers: [{
			meta: {
				requireAuth: true
			},
			path: '/usercenter',
			name: 'UserCenter',
			component: () => import( /* webpackChunkName: "usercenter" */ './views/UserCenter.vue'),
			children: [{
					meta: {
						requireAuth: true,
						permit: 1
					},
					path: '/user/userinfo',
					name: '用户基本信息',
					component: () => import( /* webpackChunkName: "userinfo" */ './views/user/UserInfo.vue')
				},
				{
					meta: {
						requireAuth: true,
						permit: 1
					},
					path: '/user/changepassword',
					name: '修改密码',
					component: () => import( /* webpackChunkName: "changepassword" */ './views/user/ChangePassword.vue')
				}
			]
		},
		{
			meta: {
				requireAuth: true
			},
			path: '/helpcenter',
			name: 'Helpcenter',
			component: () => import( /* webpackChunkName: "helpcenter" */ './views/HelpCenter.vue')
		}, {
			meta: {
				requireAuth: true,
				permit: 9
			},
			path: '/managercenter',
			component: () => import( /* webpackChunkName: "managercenter" */ './views/ManagerCenter.vue'),
			name: '系统管理',
			children: [{
					meta: {
						requireAuth: true,
						permit: 5
					},
					path: 'parametermanager',
					component:  () => import( /* webpackChunkName: "parametermanager" */ './views/admin/ParameterManager.vue'),
					name: '参数管理'
				},{
					meta: {
						requireAuth: true,
						permit: 5
					},
					path: 'docattrmanager',
					component:  () => import( /* webpackChunkName: "docattrmanager" */ './views/admin/DocAttrManager.vue'),
					name: '系统元数据'
				},{
					meta: {
						requireAuth: true,
						permit: 5
					},
					path: 'storemanager',
					component:  () => import( /* webpackChunkName: "docattrmanager" */ './views/admin/StoreManager.vue'),
					name: '存储管理'
				},
				{
				  meta: {
					requireAuth: true,
					permit: 5
				  },
				  path: '/managercenter/typemanager',
				  component:  () => import( /* webpackChunkName: "typemanager" */ './views/admin/TypeManager.vue'),
				  name: '业务类型管理'
				}
			]
		}
	]
}

currentRouter.routers.forEach(element => {
	homeChildren.push(element);
})


const router = new Router({
	routes: [{
			meta: {
				requireAuth: true
			},
			path: '/',
			name: 'home',
			component: () => import( /* webpackChunkName: "home" */ './views/Home.vue'),
			children: homeChildren
		}, {
			meta: {
				requireAuth: true
			},
			path: '/home',
			name: 'home',
			component: () => import( /* webpackChunkName: "home" */ './views/Home.vue'),
			children: homeChildren
		},
		{
			path: '/login',
			name: 'Login',
			component: () => import( /* webpackChunkName: "Login" */ './views/Login.vue')
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
