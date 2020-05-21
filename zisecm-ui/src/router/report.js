export const reportRouter = {

	meta: {
		requireAuth: true,
		permit: 7,
		title:'reportCenter'
	},
	path: '/reportcenter',
	component: () => import('@/views/ReportCenter.vue'),
	name: '报表中心',
	children: [
		{
			meta: {
				requireAuth: true,
				permit: 2,
				title: 'systemReport'
			},
			path: '/report/systemreport',
			name: '系统报表',
			component: () => import('@/views/report/SystemReport.vue')
		},
		{
			meta: {
				requireAuth: true,
				permit: 2,
				title: 'storageReport'
			},
			path: '/report/collectionreport',
			name: '馆藏报表',
			component: () => import('@/views/report/CollectionReport.vue')
		},
		{
			meta: {
				requireAuth: true,
				permit: 2,
				title: 'workloadReport'
			},
			path: '/report/workloadreport',
			name: '工作量报表',
			component: () => import('@/views/report/WorkloadReport.vue')
		},
		{
			meta: {
				requireAuth: true,
				permit: 2,
				title: 'utilizationReport'
			},
			path: '/report/archivesreport',
			name: '利用统计报表',
			component: () => import('@/views/report/ArchivesReport.vue')
		}
	]

}
