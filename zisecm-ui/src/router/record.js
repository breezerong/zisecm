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
]
