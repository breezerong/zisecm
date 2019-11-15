var searchRouter = {
	routers: [
		{
			meta: {
				requireAuth: true,
				permit: 9
			},
			path: '/searchcenter',
			component: () => import( /* webpackChunkName: "managercenter" */ '@/views/SearchCenter.vue'),
			name: '搜索中心',
			children: [
				{
				  meta: {
					requireAuth: true,
					permit: 1
				  },
				  path: '/search/fulltextsearch',
				  name: '全文搜索',
				  component: () => import( '@/views/search/FullTextSearch.vue')
				},
				{
				  meta: {
					requireAuth: true,
					permit: 1
				  },
				  path: '/search/cardsearch',
				  name: '卡片搜索',
				  component: () => import( '@/views/search/CardSearch.vue')
				},
				{
				  meta: {
					requireAuth: true,
					permit: 1
				  },
				  path: '/search/advsearch',
				  name: '高级搜索',
				  component: () => import( '@/views/search/AdvSearch.vue')
				},
				{
				  meta: {
					requireAuth: true,
					permit: 1
				  },
				  path: '/search/mysearch',
				  name: '我的搜索',
				  component: () => import( '@/views/search/MySearch.vue')
				}
			  ]
		},
	]
}


export default searchRouter;
