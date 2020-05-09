export const dcRouter = [
		{
			meta: {
				requireAuth: true,
				permit: 1,
				title:'companyDoc'
			},
			path: '/dc/companydoc',
			component: () => import('@/views/dc/CompanyDoc.vue'),
			name: '库存档案',
			children:[
				{
					meta: {
						requireAuth: true,
						permit: 1
					},
					path: '/borrow',
					name: '测试2',
					component: () => import('@/components/form/Borrow.vue')
					
				},
				{
					meta: {
						requireAuth: true,
						permit: 1
					},
					path: '/ShopingCart',
					name: '测试2',
					component: () => import('@/components/form/ShopingCart.vue')
					
				}
			]
		},
		{
			meta: {
				requireAuth: true,
				permit: 1
			},
			path: '/dc/folderviewer',
			component: () => import('@/views/dc/FolderViewer.vue'),
			name: '查看文件夹'
		},
		{
			meta: {
				requireAuth: true,
				permit: 1
			},
			path: '/dc/folder',
			component: () => import('@/views/dc/FolderClassification.vue'),
			name: '文件夹浏览'
		},
		{
			meta: {
				requireAuth: true,
				permit:1
			},
			path: '/dc/officedocviewer',
			component: () => import('@/views/dc/OfficeDocViewer.vue'),
			name: '文档浏览'
		},
		{
			meta: {
				requireAuth: true,
				permit: 1
			},
			path: '/dc/imageviewer',
			component: () => import('@/views/dc/ImageViewer.vue'),
			name: '图像浏览'
		},
		{
			meta: {
				requireAuth: true,
				permit: 1
			},
			path: '/dc/videoplayer',
			component: () => import('@/views/dc/VideoPlayer.vue'),
			name: '视频播放'
		},
		{
			meta: {
				requireAuth: true,
				permit: 1
			},
			path: '/dc/audioplayer',
			component: () => import('@/views/dc/AudioPlayer.vue'),
			name: '音频播放'
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
