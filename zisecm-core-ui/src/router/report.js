var reportRouter = {
	routers: [
		{
			meta: {
				requireAuth: true,
				permit: 9
			},
			path: '/reportcenter',
			component: () => import('@/views/ReportCenter.vue'),
			name: '报表中心',
			children: [
				{
				  meta: {
					requireAuth: true,
					permit: 2
				  },
				  path: '/report/drawingreport',
				  name: '图纸报表',
				  component: () => import('@/views/report/DrawingReport.vue')
				},
				{
				  meta: {
					requireAuth: true,
					permit: 2
				  },
				  path: '/report/systemreport',
				  name: '系统报表',
				  component: () => import('@/views/report/SystemReport.vue')
				}
			  ]
		}
	]
}


export default reportRouter;
