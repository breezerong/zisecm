export const reportRouter = {

	meta: {
		requireAuth: true,
		permit: 7
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
			path: '/report/systemreport',
			name: '系统报表',
			component: () => import('@/views/report/SystemReport.vue')
		},
		{
			meta: {
				requireAuth: true,
				permit: 2
			},
			path: '/report/archivesreport',
			name: '档案报表',
			component: () => import('@/views/report/ArchivesReport.vue')
		}
	]

}
