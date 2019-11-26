export const dcRouter = [
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
			path: '/viewdoc',
			component: () => import('@/views/dc/ViewDoc.vue'),
			name: '查看文档'
		},
		{
			meta: {
				requireAuth: true,
				permit: 9
			},
			path: '/dc/folderview',
			component: () => import('@/views/dc/FolderView.vue'),
			name: '查看文件夹'
		},
		{
			meta: {
				requireAuth: true,
				permit: 9
			},
			path: '/dc/folder',
			component: () => import('@/views/dc/FolderClassification.vue'),
			name: '文件夹浏览'
		},
		{
			meta: {
				requireAuth: true,
				permit: 1
			},
			path: '/dc/recyclebin',
			component: () => import('@/views/dc/RecycleBin.vue'),
			name: '回收站'
		}
	]
