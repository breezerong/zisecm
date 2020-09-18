export const cnpeRouter=
[
    {
        meta: {
            requireAuth: true,
            permit: 1,
            title:'DCReport'
        },
        path: '/cnpe/DCManagement/dcreport',
        name: '文函报表',
        component: () => import('@/views/docexchange/DCManagement/DCReport.vue')
    },
    {
        meta: {
            requireAuth: true,
            permit: 1,
            title:'Deblockingdc'
        },
        path: '/cnpe/DCManagement/deblockingdc',
        name: '待解锁文函',
        component: () => import('@/views/docexchange/DCManagement/Deblockingdc.vue')
    },
    {
        meta: {
            requireAuth: true,
            permit: 1,
            title:'SubmitedDC'
        },
        path: '/cnpe/DCManagement/pendingDC',
        name: '已提交文函',//'待确认文函'
        component: () => import('@/views/docexchange/DCManagement/PendingDC.vue')
    },
    {
        meta: {
            requireAuth: true,
            permit: 1,
            title:'BeenDispensed'
        },
        path: '/cnpe/DCManagement/BeenDispensed',
        name: '已分发',//'待确认文函'
        component: () => import('@/views/docexchange/DCManagement/BeenDispensed.vue')
    },
    {
        meta: {
            requireAuth: true,
            permit: 1,
            title:'ReceivedDC'
        },
        path: '/cnpe/DCManagement/receivedDC',
        name: '已接收文函',
        component: () => import('@/views/docexchange/DCManagement/ReceivedDC.vue')
    },
    {
        meta: {
            requireAuth: true,
            permit: 1,
            title:'ReceivingDC'
        },
        path: '/cnpe/DCManagement/receivingdc',
        name: '待接收文函',
        component: () => import('@/views/docexchange/DCManagement/ReceivingDC.vue')
    },
    {
        meta:{
            requireAuth: true,
            permit: 1,
            title:'DispenseDc'
        },
        path:'/cnpe/DCManagement/dispenseDc',
        name:'分发文函',
        component:()=>import('@/views/docexchange/DCManagement/CreateDispense.vue')
    },
    {
        meta:{
            requireAuth: true,
            permit: 1,
            title:'ReceivingDcFromCnpe'
        },
        path:'/cnpe/DCManagement/ReceivingDC4Cnpe',
        name:'分包商接收',
        component:()=>import('@/views/docexchange/DCManagement/ReceivingDC4Cnpe.vue')
    },
    {
        meta:{
            requireAuth: true,
            permit: 1,
            title:'RejectDC'
        },
        path:'/cnpe/DCManagement/Reject4Cnpe',
        name:'驳回文函Cnpe',
        component:()=>import('@/views/docexchange/DCManagement/Reject4Cnpe.vue')
    },
    {
        meta:{
            requireAuth: true,
            permit: 1,
            title:'applyReject'
        },
        path:'/cnpe/DCManagement/applyRejectCnpe',
        name:'申请驳回确认Cnpe',
        component:()=>import('@/views/docexchange/DCManagement/ApplyRejectCnpe.vue')
    },
    {
        meta:{
            requireAuth: true,
            permit: 1,
            title:'applyReject'
        },
        path:'/cnpe/DCManagement/applyRejectSub',
        name:'申请驳回确认Sub',
        component:()=>import('@/views/docexchange/DCManagement/ApplyRejectSub.vue')
    },
    {
        meta:{
            requireAuth: true,
            permit: 1,
            title:'MyApplyReject'
        },
        path:'/cnpe/DCManagement/myApplyReject',
        name:'我的申请驳回',
        component:()=>import('@/views/docexchange/DCManagement/MyApplyReject.vue')
    },
    {
        meta:{
            requireAuth: true,
            permit: 1,
            title:'ReceivedDcFromCnpe'
        },
        path:'/cnpe/DCManagement/ReceivedDC4Cnpe',
        name:'分包商已接收',
        component:()=>import('@/views/docexchange/DCManagement/ReceivedDC4Cnpe.vue')
    },
    {
        meta: {
            requireAuth: true,
            permit: 1,
            title:'RejectedDC'
        },
        path: '/cnpe/DCManagement/rejectedDC',
        name: '驳回文函',
        component: () => import('@/views/docexchange/DCManagement/RejectedDC.vue')
    },
    {
        meta: {
            requireAuth: true,
            permit: 1,
            title:'Submissiondc'
        },
        path: '/cnpe/DCManagement/submissiondc',
        name: '待提交文函',
        component: () => import('@/views/docexchange/DCManagement/Submissiondc.vue')
    },
    {
        meta: {
            requireAuth: true,
            permit: 1,
            title:'InterfaceManual'
        },
        path: '/cnpe/icmmanagement/interfacemanual',
        name: '接口手册',
        component: () => import('@/views/docexchange/ICMManagement/InterfaceManual.vue')
    },
    {
        meta: {
            requireAuth: true,
            permit: 1,
            title:'projectManagement'
        },
        path: '/cnpe/projectmanagement/projectsList',
        name: '项目管理',
        component: () => import('@/views/docexchange/Management/ProjectManageMent/ProjectsList.vue')
    },
    {
        meta: {
            requireAuth: true,
            permit: 1,
            title:'userManagement'
        },
        path: '/cnpe/userManagement/userLists',
        name: '用户管理',
        component: () => import('@/views/docexchange/Management/UserManagement/UserLists.vue')
    },
    {
        meta: {
            requireAuth: true,
            permit: 1,
            title:'AddressList'
        },
        path: '/cnpe/userManagement/AddressList',
        name: '通讯录',
        component: () => import('@/views/docexchange/AddressList')
    },
    {
        meta: {
          requireAuth: true,
          permit: 5,
          title: 'roleManager'
        },
        path: '/cnpe/managercenter/rolemanager',
        component: () => import('@/views/docexchange/Management/UserManagement/RoleManager.vue'),
        name: '角色管理'
    },
    {
        meta: {
          requireAuth: true,
          permit: 5,
          title: 'companyManage'
        },
        path: '/cnpe/managercenter/companyManage',
        component: () => import('@/views/docexchange/Management/CompanyManagement/CompanyManager.vue'),
        name: '公司管理'
      },
    {
        meta: {
            requireAuth: true,
            permit: 1,
            title:'feedbackManagement'
        },
        path: '/cnpe/feedbackManagement/feedback',
        name: '问题反馈',
        component: () => import('@/views/docexchange/Feedback/Feedback.vue')
    },
    {
        meta: {
            requireAuth: true,
            permit: 1,
            title:'icmfeedback'
        },
        path: '/cnpe/icmmanagement/icmfeedback',
        name: '延误打开反馈',
        component: () => import('@/views/docexchange/ICMManagement/icmfeedback.vue')
    },
    {
        meta: {
            requireAuth: true,
            permit: 1,
            title:'delayFeedback'
        },
        path: '/cnpe/icmmanagement/delayfeedback',
        name: '延误回复反馈',
        component: () => import('@/views/docexchange/ICMManagement/DelayFeedback.vue')
    },
    {
        meta: {
            requireAuth: true,
            permit: 1,
            title:'delayConfirm'
        },
        path: '/cnpe/icmmanagement/delayconfirm',
        name: '延误回复确认',
        component: () => import('@/views/docexchange/ICMManagement/DelayConfirm.vue')
    },
    {
        meta: {
            requireAuth: true,
            permit: 1,
            title:'delayCloseConfirm'
        },
        path: '/cnpe/icmmanagement/delaycloseconfirm',
        name: '延误关闭确认',
        component: () => import('@/views/docexchange/ICMManagement/DelayCloseConfirm.vue')
    },
    {
        meta: {
            requireAuth: true,
            permit: 1,
            title:'ICMReport'
        },
        path: '/cnpe/icmmanagement/icmreport',
        name: 'ICM报表',
        component: () => import('@/views/docexchange/ICMManagement/ICMReport.vue')
    },
    {
        meta: {
            requireAuth: true,
            permit: 1,
            title:'IEDpublished'
        },
        path: '/cnpe/iedmanagement/IEDpublished',
        name: '已发布的IED',
        component: () => import('@/views/docexchange/IEDManagement/IEDpublished.vue')
    },
    {
        meta: {
            requireAuth: true,
            permit: 1,
            title:'IEDRejectied'
        },
        path: '/cnpe/iedmanagement/IEDRejectied',
        name: '驳回IED',
        component: () => import('@/views/docexchange/IEDManagement/IEDRejectied.vue')
    },
    {
        meta: {
            requireAuth: true,
            permit: 1,
            title:'IEDReport'
        },
        path: '/cnpe/iedmanagement/IEDReport',
        name: 'IED报表',
        component: () => import('@/views/docexchange/IEDManagement/IEDReport.vue')
    },
    {
        meta: {
            requireAuth: true,
            permit: 1,
            title:'PendingIED'
        },
        path: '/cnpe/iedmanagement/pendingied',
        name: '待确认IED',
        component: () => import('@/views/docexchange/IEDManagement/PendingIED.vue')
    },
    {
        meta: {
            requireAuth: true,
            permit: 1,
            title:'PendingSubmit'
        },
        path: '/cnpe/iedmanagement/pendingsubmit',
        name: '待提交IED',
        component: () => import('@/views/docexchange/IEDManagement/PendingSubmit.vue')
    },
    {
        meta: {
            requireAuth: true,
            permit: 1,
            title:'ProjectViewer'
        },
        path: '/cnpe/MoreViewerBrowe/projectviewer',
        name: '项目视图',
        component: () => import('@/views/docexchange/MoreViewerBrowe/ProjectViewer.vue')
    },
    {
        meta: {
            requireAuth: true,
            permit: 1,
            title:'SSCviewer'
        },
        path: '/cnpe/MoreViewerBrowe/sscviewer',
        name: 'SSC视图',
        component: () => import('@/views/docexchange/MoreViewerBrowe/SSCviewer.vue')
    },
    {
        meta: {
            requireAuth: true,
            permit: 1,
            title:'CnpeGantt'
        },
        path: '/cnpe/PlanManagement/gantt',
        name: '甘特图',
        component: () => import('@/views/docexchange/planmanagement/Gantt.vue')
    },
    {
        meta: {
            requireAuth: true,
            permit: 1,
            title:'CnpePlanReport'
        },
        path: '/cnpe/PlanManagement/planreport',
        name: '计划报表',
        component: () => import('@/views/docexchange/planmanagement/PlanReport.vue')
    },
    {
        meta: {
            requireAuth: true,
            permit: 1,
            title:'CnpePlanSync'
        },
        path: '/cnpe/PlanManagement/plansync',
        name: '计划同步',
        component: () => import('@/views/docexchange/planmanagement/PlanSync.vue')
    },
    {
        meta: {
            requireAuth: true,
            permit: 1,
            title:'Cnpe3levelPlan'
        },
        path: '/cnpe/plan/threelevelplan',
        name: '计划作业',
        component: () => import('@/views/docexchange/planmanagement/ThreeLevelPlan.vue')
    },
    {
        meta: {
            requireAuth: true,
            permit: 1,
            title:'LogMonitor'
        },
        path: '/cnpe/sys/logmonitor',
        name: '日志监控',
        component: () => import('@/views/docexchange/SysManagement/LogMonitor.vue')
    },
]