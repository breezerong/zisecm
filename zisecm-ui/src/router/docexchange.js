export const docexchange =  [
    {
        meta: {
            requireAuth: true,
            permit: 1,
            title:'transferOrder'
        },
        path: '/docexchange/docTransfer',
        name: '传递单',
        component: () => import('@/views/docexchange/DocTransfer.vue')
    },
     {
        meta: {
            requireAuth: true,
            permit: 1,
            title:"shoppingCar"
        },
        path: '/docexchange/shoppingCart',
        name: '购物车',
        component: () => import('@/components/ShoppingCart.vue')
    },
    {
        meta: {
            requireAuth: true,
            permit: 1,
            title:'createDocFile'
        },
        path: '/docexchange/ChoiceType',
        name: '类型选择',
        component: () => import('@/views/docexchange/ChoiceType.vue')
    },
    {
        meta: {
            requireAuth: true,
            permit: 1,
            title:'createDocFile'
        },
        path: '/docexchange/CreateDoc',
        name: '创建文件',
        component: () => import('@/components/Properties.vue')
    }
]
    
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
    
