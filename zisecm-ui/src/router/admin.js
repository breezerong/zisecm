export const adminRouter = {
  meta: {
    requireAuth: true,
    permit: 9
  },
  path: '/managercenter',
  component: () => import('@/views/ManagerCenter.vue'),
  name: '系统管理',
  children: [
    {
      meta: {
        requireAuth: true,
        permit: 5
      },
      path: '/managercenter/parametermanager',
      component: () => import('@/views/admin/ParameterManager.vue'),
      name: '参数管理'
    },
    {

      meta: {
        requireAuth: true,
        permit: 5
      },
      path: '/managercenter/workflowstatus',
      component: () => import('@/views/admin/WorkflowStatus.vue'),
      name: '状态流程'
    },
    {
      meta: {
        requireAuth: true,
        permit: 5
      },
      path: '/managercenter/usermanager',
      component: () => import('@/views/admin/UserManager.vue'),
      name: '用户管理'
    },
    {
      meta: {
        requireAuth: true,
        permit: 5
      },
      path: '/managercenter/groupmanager',
      component: () => import('@/views/admin/GroupManager.vue'),
      name: '组管理'
    },
    {
      meta: {
        requireAuth: true,
        permit: 5
      },
      path: '/managercenter/rolemanager',
      component: () => import('@/views/admin/RoleManager.vue'),
      name: '角色管理'
    },
    {
      meta: {
        requireAuth: true,
        permit: 5
      },
      path: '/managercenter/componentmanager',
      component: () => import('@/views/admin/ComponentManager.vue'),
      name: '组件管理'
    },
    {
      meta: {
        requireAuth: true,
        permit: 5
      },
      path: '/managercenter/actionmanager',
      component: () => import('@/views/admin/ActionManager.vue'),
      name: '事件管理'
    },
    {
      meta: {
        requireAuth: true,
        permit: 5
      },
      path: '/managercenter/menumanager',
      component: () => import('@/views/admin/MenuManager.vue'),
      name: '菜单管理'
    },
    {
      meta: {
        requireAuth: true,
        permit: 5
      },
      path: '/managercenter/menuitemmanager',
      component: () => import('@/views/admin/MenuItemManager.vue'),
      name: '菜单项管理'
    },
    {
      meta: {
        requireAuth: true,
        permit: 5
      },
      path: '/managercenter/formmanager',
      component: () => import('@/views/admin/FormManager.vue'),
      name: '表单管理'
    },
    {
      meta: {
        requireAuth: true,
        permit: 5
      },
      path: '/managercenter/formItemmanager',
      component: () => import('@/views/admin/FormItemManager.vue'),
      name: '表单项管理'
    },
    {
      meta: {
        requireAuth: true,
        permit: 5
      },
      path: '/managercenter/gridviewmanager',
      component: () => import('@/views/admin/GridViewManager.vue'),
      name: '列表管理'
    },
    {
      meta: {
        requireAuth: true,
        permit: 5
      },
      path: '/managercenter/gridviewitemmanager',
      component: () => import('@/views/admin/GridViewItemManager.vue'),
      name: '列表项管理'
    },
    {
      meta: {
        requireAuth: true,
        permit: 5
      },
      path: '/managercenter/docattrmanager',
      component: () => import('@/views/admin/DocAttrManager.vue'),
      name: '文档元数据管理'
    },
    {
      meta: {
        requireAuth: true,
        permit: 5
      },
      path: '/managercenter/typemanager',
      component: () => import('@/views/admin/TypeManager.vue'),
      name: '业务类型管理'
    },
    {
      meta: {
        requireAuth: true,
        permit: 5
      },
      path: '/managercenter/defattrmanager',
      component: () => import('@/views/admin/DefAttrManager.vue'),
      name: '业务元数据管理'
    },
    {
      meta: {
        requireAuth: true,
        permit: 5
      },
      path: '/managercenter/storemanager',
      component: () => import('@/views/admin/StoreManager.vue'),
      name: '存储管理'
    },
    {
      meta: {
        requireAuth: true,
        permit: 5
      },
      path: '/managercenter/aclmanager',
      component: () => import('@/views/admin/AclManager.vue'),
      name: 'ACL管理'
    },
    {
      meta: {
        requireAuth: true,
        permit: 2
      },
      path: '/managercenter/querymanager',
      component: () => import('@/views/admin/QueryManager.vue'),
      name: '查询管理'
    },
    {
      meta: {
        requireAuth: true,
        permit: 2
      },
      path: '/managercenter/selectvaluemanager',
      component: () => import('@/views/admin/SelectValueManager.vue'),
      name: '选项管理'
    },
    {
      meta: {
        requireAuth: true,
        permit: 9
      },
      path: '/managercenter/cardsearchmanager',
      component: () => import('@/views/admin/CardSearchManager.vue'),
      name: '卡片查询管理'
    },
    {
      meta: {
        requireAuth: true,
        permit: 9
      },
      path: '/managercenter/cardsearchitemmanager',
      component: () => import('@/views/admin/CardSearchItemManager.vue'),
      name: '卡片表单项'
    },
    {
      meta: {
        requireAuth: true,
        permit: 9
      },
      path: '/managercenter/systemmanager',
      component: () => import('@/views/admin/SystemManager.vue'),
      name: '系统配置'
    },
    {
      meta: {
        requireAuth: true,
        permit: 2
      },
      path: '/managercenter/foldermanager',
      component: () => import('@/views/admin/FolderManager.vue'),
      name: '文件夹管理'
    },
    {
      meta: {
        requireAuth: true,
        permit: 2,
      },
      path: '/workflow/allWorkflow',
      component: () => import('@/views/workflow/MyWorkflow.vue'),
      name: '所有工作流',
      children:[
        {
          meta: {
            requireAuth: true,
            permit: 1
          },
          path: '/admin_borrow3',
          name: '测试3',
          component: () => import('@/components/form/Borrow.vue')
          
        }
      ]
    }
  ]
}