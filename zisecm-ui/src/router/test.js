export const testRouter =  [
    {
        meta: {
            requireAuth: true,
            permit: 1
        },
        path: '/test/test1',
        name: '测试1',
        component: () => import('@/views/test/TestComp1.vue')
    },
    {
        meta: {
            requireAuth: true,
            permit: 1
        },
        path: '/test/test2',
        name: '测试2',
        component: () => import('@/views/test/TestComp2.vue')
    },
    // {
    //     meta: {
    //         requireAuth: true,
    //         permit: 1
    //     },
    //     path: '/borroworder',
    //     name: '测试2',
    //     component: () => import('@/components/form/Borrow.vue')
        
    // }
]
