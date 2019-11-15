var dcRouter = {
	routers: [
		{
			meta: {
				requireAuth: true,
				permit: 1
			},
			path: '/dc/companydoc',
			component: () => import('@/views/dc/CompanyDoc.vue'),
			name: '公司文档'
		},
		{
			meta: {
				requireAuth: true,
				permit: 9
			},
			path: '/dc/folder',
			component: () => import('@/views/dc/FolderClassification.vue'),
			name: '文件夹浏览'
		}
	]
}


export default dcRouter;
