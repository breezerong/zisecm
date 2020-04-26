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

    
// import Layout from "../views/layout/Layout";
// export const docexchange =  
// {
//     meta:{
//         meta: {
//             requireAuth: true,
//             permit: 1
//         },
//         path: '/docexchange/docTransfer',
//         name: '传递单',
//         component: Layout,
//         redirect:'/docexchange/docTransfer1'
//     },
//     children:[
//         {
//             meta: {
//                 requireAuth: true,
//                 permit: 1
//             },
//             path: '/docexchange/docTransfer1',
//             name: '传递单',
//             component: () => import('@/views/docexchange/DocTransfer.vue')
//         }
//     ]
// }
    
