var coreRouter = {
	routers: [
		{
			meta: {
				requireAuth: true,
				permit: 9
			},
			path: '/managercenter',
			component: () => import('@/views/ManagerCenter.vue'),
			name: '系统管理',
			children: [{
					meta: {
						requireAuth: true,
						permit: 5
					},
					path: 'parametermanager',
					component:  () => import('@/views/admin/ParameterManager.vue'),
					name: '参数管理'
				},{
					meta: {
						requireAuth: true,
						permit: 5
					},
					path: 'docattrmanager',
					component:  () => import('@/views/admin/DocAttrManager.vue'),
					name: '系统元数据'
				},{
					meta: {
						requireAuth: true,
						permit: 5
					},
					path: 'storemanager',
					component:  () => import('@/views/admin/StoreManager.vue'),
					name: '存储管理'
				},
				{
				  meta: {
					requireAuth: true,
					permit: 5
				  },
				  path: '/managercenter/typemanager',
				  component:  () => import('@/views/admin/TypeManager.vue'),
				  name: '业务类型管理'
				},
				{
				  meta: {
					requireAuth: true,
					permit: 5
				  },
				  path: '/managercenter/usermanager',
				  component:  () => import( '@/views/admin/UserManager.vue'),
				  name: '业务类型管理'
				}
			]
		}
	]
}


export default coreRouter;
