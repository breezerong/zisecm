export const docexchange =  
    {
        meta: {
            requireAuth: true,
            permit: 1
        },
        path: '/docexchange/docTransfer',
        name: '传递单',
        component: () => import('@/views/docexchange/DocTransfer.vue')
    }
