export const recordRouter =  [
    {
        meta: {
            requireAuth: true,
            permit: 1
        },
        path: '/record/archivedelivery',
        name: '档案移交',
        component: () => import('@/views/record/ArchiveDelivery.vue')
    },
    {
        meta: {
            requireAuth: true,
            permit: 1
        },
        path: '/record/archivereceive',
        name: '档案接收',
        component: () => import('@/views/record/ArchiveReceive.vue')
    },
    {
        meta: {
            requireAuth: true,
            permit: 1
        },
        path: '/record/archivearrange',
        name: '档案整理',
        component: () => import('@/views/record/ArchiveArrange.vue')
    },
    {
        meta: {
            requireAuth: true,
            permit: 1
        },
        path: '/record/archivetool',
        name: '档案工具',
        component: () => import('@/views/record/ArchiveTool.vue'),
        children:[
            {
                meta: {
                    requireAuth: true,
                    permit: 1
                },
                path: '/record/batchupdate',
                name: '批量更新',
                component: () => import('@/views/record/BatchUpdate.vue')
            },
            {
                meta: {
                    requireAuth: true,
                    permit: 1
                },
                path: '/record/batchmount',
                name: '批量挂载',
                component: () => import('@/views/record/BatchMount.vue')
            }
        ]
    },
    {
        meta: {
            requireAuth: true,
            permit: 1
        },
        path: '/record/archiveoutgoing',
        name: '出入库管理',
        component: () => import('@/views/record/ArchiveOutgoing.vue'),
        children:[
            {
                meta: {
                    requireAuth: true,
                    permit: 1
                },
                path: '/record/archiveoutgoing/archivependingout',
                name: '待出库',
                component: () => import('@/views/record/ArchivePendingOut.vue')
            },
            {
                meta: {
                    requireAuth: true,
                    permit: 1
                },
                path: '/record/archiveoutgoing/archivependingin',
                name: '待入库',
                component: () => import('@/views/record/ArchivePendingIn.vue')
            }
        ]
    }
    
]

// export const borrowCenter={
    // meta: {
    //     requireAuth: true,
    //     permit: 1
    // },
    // path: '/record/archiveoutgoing',
    // name: '出入库管理',
    // component: () => import('@/views/record/ArchiveOutgoing.vue'),
    // children: [
    //     {
    //         meta: {
    //             requireAuth: true,
    //             permit: 1
    //         },
    //         path: '/record/archiveoutgoing/archivepending',
    //         name: '待入库',
    //         component: () => import('@/views/record/ArchivePending.vue')
    //     }
    // ]
// }
