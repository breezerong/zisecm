var coreRouter = {
	routers: [{
			meta: {
				requireAuth: true
			},
			path: 'admin',
			name: 'admin',
			component: () => import('./views/UserManager.vue')
		},
		{
			meta: {
				requireAuth: true
			},
			path: 'workflow',
			name: 'workflow',
			component: () => import('./views/WorkflowManager.vue')
		}
	]
}


export default coreRouter;
