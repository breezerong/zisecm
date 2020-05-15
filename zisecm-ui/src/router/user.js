export const userRouter = {
	meta: {
		requireAuth: true,
		title: 'userCenter'
	},
	path: '/usercenter',
	name: '个人信息',
	component: () => import( /* webpackChunkName: "usercenter" */ '@/views/UserCenter.vue'),
	children: [{
		meta: {
			requireAuth: true,
			permit: 1,
			title: 'userInfo'
		},
		path: '/user/userinfo',
		name: '用户基本信息',
		component: () => import( /* webpackChunkName: "userinfo" */ '@/views/user/UserInfo.vue')
	},
	{
		meta: {
			requireAuth: true,
			permit: 1,
			title: 'userRoleInfo'
		},
		path: '/user/userroleinfo',
		name: '我的授权',
		component: () => import('@/views/user/UserRoleInfo.vue')
	},
	{
		meta: {
			requireAuth: true,
			permit: 1,
			title: 'changePassword'
		},
		path: '/user/changepassword',
		name: '修改密码',
		component: () => import( /* webpackChunkName: "changepassword" */ '@/views/user/ChangePassword.vue')
	}]

}
