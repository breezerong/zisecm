var customRouter = {
	routers: [{
		meta: {
			requireAuth: true
		},
		path: '/project',
		name: 'project',
		component: () => import( /* webpackChunkName: "project" */ './views/Custom.vue')
	}]
}


export default customRouter;
