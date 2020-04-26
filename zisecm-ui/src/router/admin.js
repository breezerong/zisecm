
export const adminRouter = 
[
  {
    meta: {
      requireAuth: true,
      permit: 9,
      title: 'managerCenter'
    },
    path: '/managermain',
    component: () => import('@/views/ManagerCenter.vue'),
    name: '系统管理首页',
  },
  {
    meta: {
      requireAuth: true,
      permit: 5,
      title: 'parameterManager'
    },
    path: '/managercenter/parametermanager',
    component: () => import('@/views/admin/ParameterManager.vue'),
    name: '参数管理'
  },
  {

    meta: {
      requireAuth: true,
      permit: 5,
      title: 'statusWorkflow'
    },
    path: '/managercenter/workflowstatus',
    component: () => import('@/views/admin/WorkflowStatus.vue'),
    name: '状态流程'
  },
  {
    meta: {
      requireAuth: true,
      permit: 5,
      title: 'userManager'
    },
    path: '/managercenter/usermanager',
    component: () => import('@/views/admin/UserManager.vue'),
    name: '用户管理'
  },
  {
    meta: {
      requireAuth: true,
      permit: 5,
      title: 'groupManager'
    },
    path: '/managercenter/groupmanager',
    component: () => import('@/views/admin/GroupManager.vue'),
    name: '组管理'
  },
  {
    meta: {
      requireAuth: true,
      permit: 5,
      title: 'roleManager'
    },
    path: '/managercenter/rolemanager',
    component: () => import('@/views/admin/RoleManager.vue'),
    name: '角色管理'
  },
  {
    meta: {
      requireAuth: true,
      permit: 5,
      title: 'componentManager'
    },
    path: '/managercenter/componentmanager',
    component: () => import('@/views/admin/ComponentManager.vue'),
    name: '组件管理'
  },
  {
    meta: {
      requireAuth: true,
      permit: 5,
      title: 'actionManager'
    },
    path: '/managercenter/actionmanager',
    component: () => import('@/views/admin/ActionManager.vue'),
    name: '事件管理'
  },
  {
    meta: {
      requireAuth: true,
      permit: 5,
      title: 'menuManager'
    },
    path: '/managercenter/menumanager',
    component: () => import('@/views/admin/MenuManager.vue'),
    name: '菜单管理'
  },
  {
    meta: {
      requireAuth: true,
      permit: 5,
      title: 'menuitemManager'
    },
    path: '/managercenter/menuitemmanager',
    component: () => import('@/views/admin/MenuItemManager.vue'),
    name: '菜单项管理'
  },
  {
    meta: {
      requireAuth: true,
      permit: 5,
      title: 'formManager'
    },
    path: '/managercenter/formmanager',
    component: () => import('@/views/admin/FormManager.vue'),
    name: '表单管理'
  },
  {
    meta: {
      requireAuth: true,
      permit: 5,
      title: 'formItemManager'
    },
    path: '/managercenter/formItemmanager',
    component: () => import('@/views/admin/FormItemManager.vue'),
    name: '表单项管理'
  },
  {
    meta: {
      requireAuth: true,
      permit: 5,
      title: 'gridviewManager'
    },
    path: '/managercenter/gridviewmanager',
    component: () => import('@/views/admin/GridViewManager.vue'),
    name: '列表管理'
  },
  {
    meta: {
      requireAuth: true,
      permit: 5,
      title: 'gridviewitemManager'
    },
    path: '/managercenter/gridviewitemmanager',
    component: () => import('@/views/admin/GridViewItemManager.vue'),
    name: '列表项管理'
  },
  {
    meta: {
      requireAuth: true,
      permit: 5,
      title: 'metaData'
    },
    path: '/managercenter/docattrmanager',
    component: () => import('@/views/admin/DocAttrManager.vue'),
    name: '文档元数据管理'
  },
  {
    meta: {
      requireAuth: true,
      permit: 5,
      title: 'objectType'
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
      permit: 5,
      title: 'storeManager'
    },
    path: '/managercenter/storemanager',
    component: () => import('@/views/admin/StoreManager.vue'),
    name: '存储管理'
  },
  {
    meta: {
      requireAuth: true,
      permit: 5,
      title: 'aclManager'
    },
    path: '/managercenter/aclmanager',
    component: () => import('@/views/admin/AclManager.vue'),
    name: 'ACL管理'
  },
  {
    meta: {
      requireAuth: true,
      permit: 2,
      title: 'queryManager'
    },
    path: '/managercenter/querymanager',
    component: () => import('@/views/admin/QueryManager.vue'),
    name: '查询管理'
  },
  {
    meta: {
      requireAuth: true,
      permit: 2,
      title: 'selectvalueManager'
    },
    path: '/managercenter/selectvaluemanager',
    component: () => import('@/views/admin/SelectValueManager.vue'),
    name: '选项管理'
  },
  {
    meta: {
      requireAuth: true,
      permit: 9,
      title: 'cardsearchManager'
    },
    path: '/managercenter/cardsearchmanager',
    component: () => import('@/views/admin/CardSearchManager.vue'),
    name: '卡片查询管理'
  },
  {
    meta: {
      requireAuth: true,
      permit: 9,
      title: 'cardsearchitemManager'
    },
    path: '/managercenter/cardsearchitemmanager',
    component: () => import('@/views/admin/CardSearchItemManager.vue'),
    name: '卡片表单项'
  },
  {
    meta: {
      requireAuth: true,
      permit: 9,
      title: 'languageManager'
    },
    path: '/managercenter/languagemanager',
    component: () => import('@/views/admin/LanguageManager.vue'),
    name: '语言管理'
  },
  {
    meta: {
      requireAuth: true,
      permit: 9,
      title: 'langinfoManager'
    },
    path: '/managercenter/langinfomanager',
    component: () => import('@/views/admin/LangInfoManager.vue'),
    name: '语言标签管理'
  },
  {
    meta: {
      requireAuth: true,
      permit: 9,
      title: 'auditManager'
    },
    path: '/managercenter/auditmanager',
    component: () => import('@/views/admin/AuditManager.vue'),
    name: '日志管理'
  },
  {
    meta: {
      requireAuth: true,
      permit: 9,
      title: 'syseventManager'
    },
    path: '/managercenter/syseventmanager',
    component: () => import('@/views/admin/SysEventManager.vue'),
    name: '事件管理'
  },
  {
    meta: {
      requireAuth: true,
      permit: 9,
      title: 'systemManager'
    },
    path: '/managercenter/systemmanager',
    component: () => import('@/views/admin/SystemManager.vue'),
    name: '系统配置'
  },
  {
    meta: {
      requireAuth: true,
      permit: 2,
      title: 'folderManager'
    },
    path: '/managercenter/foldermanager',
    component: () => import('@/views/admin/FolderManager.vue'),
    name: '文件夹管理'
  },
  {
    meta: {
      requireAuth: true,
      permit: 9,
      title: 'activityManager'
    },
    path: '/managercenter/activitymanager',
    component: () => import('@/views/admin/ActivityManager.vue'),
    name: '工作流活动管理'
  },
  {
    meta: {
      requireAuth: true,
      permit: 9,
      title: 'jobManager'
    },
    path: '/managercenter/jobmanager',
    component: () => import('@/views/admin/JobManager.vue'),
    name: 'Job管理'
  },
  {
    meta: {
      requireAuth: true,
      permit: 9,
      title: 'allWorkflow'
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
// {
//   meta: {
//     requireAuth: true,
//     permit: 9
//   },
//   path: '/managercenter',
//   component: Layout,
//   name: '系统管理',
//   redirect: '/managercenter/managermain',
//   children: [
//     {
//       meta: {
//         requireAuth: true,
//         permit: 9,
//         title: 'managerCenter'
//       },
//       path: 'managermain',
//       component: () => import('@/views/ManagerCenter.vue'),
//       name: '系统管理首页',
//     },
//     {
//       meta: {
//         requireAuth: true,
//         permit: 5,
//         title: 'parameterManager'
//       },
//       path: 'parametermanager',
//       component: () => import('@/views/admin/ParameterManager.vue'),
//       name: '参数管理'
//     },
//     {

//       meta: {
//         requireAuth: true,
//         permit: 5,
//         title: 'statusWorkflow'
//       },
//       path: 'workflowstatus',
//       component: () => import('@/views/admin/WorkflowStatus.vue'),
//       name: '状态流程'
//     },
//     {
//       meta: {
//         requireAuth: true,
//         permit: 5,
//         title: 'userManager'
//       },
//       path: 'usermanager',
//       component: () => import('@/views/admin/UserManager.vue'),
//       name: '用户管理'
//     },
//     {
//       meta: {
//         requireAuth: true,
//         permit: 5,
//         title: 'groupManager'
//       },
//       path: 'groupmanager',
//       component: () => import('@/views/admin/GroupManager.vue'),
//       name: '组管理'
//     },
//     {
//       meta: {
//         requireAuth: true,
//         permit: 5,
//         title: 'roleManager'
//       },
//       path: 'rolemanager',
//       component: () => import('@/views/admin/RoleManager.vue'),
//       name: '角色管理'
//     },
//     {
//       meta: {
//         requireAuth: true,
//         permit: 5,
//         title: 'componentManager'
//       },
//       path: 'componentmanager',
//       component: () => import('@/views/admin/ComponentManager.vue'),
//       name: '组件管理'
//     },
//     {
//       meta: {
//         requireAuth: true,
//         permit: 5,
//         title: 'actionManager'
//       },
//       path: 'actionmanager',
//       component: () => import('@/views/admin/ActionManager.vue'),
//       name: '事件管理'
//     },
//     {
//       meta: {
//         requireAuth: true,
//         permit: 5,
//         title: 'menuManager'
//       },
//       path: 'menumanager',
//       component: () => import('@/views/admin/MenuManager.vue'),
//       name: '菜单管理'
//     },
//     {
//       meta: {
//         requireAuth: true,
//         permit: 5,
//         title: 'menuitemManager'
//       },
//       path: 'menuitemmanager',
//       component: () => import('@/views/admin/MenuItemManager.vue'),
//       name: '菜单项管理'
//     },
//     {
//       meta: {
//         requireAuth: true,
//         permit: 5,
//         title: 'formManager'
//       },
//       path: 'formmanager',
//       component: () => import('@/views/admin/FormManager.vue'),
//       name: '表单管理'
//     },
//     {
//       meta: {
//         requireAuth: true,
//         permit: 5,
//         title: 'formItemManager'
//       },
//       path: 'formItemmanager',
//       component: () => import('@/views/admin/FormItemManager.vue'),
//       name: '表单项管理'
//     },
//     {
//       meta: {
//         requireAuth: true,
//         permit: 5,
//         title: 'gridviewManager'
//       },
//       path: 'gridviewmanager',
//       component: () => import('@/views/admin/GridViewManager.vue'),
//       name: '列表管理'
//     },
//     {
//       meta: {
//         requireAuth: true,
//         permit: 5,
//         title: 'gridviewitemManager'
//       },
//       path: 'gridviewitemmanager',
//       component: () => import('@/views/admin/GridViewItemManager.vue'),
//       name: '列表项管理'
//     },
//     {
//       meta: {
//         requireAuth: true,
//         permit: 5,
//         title: 'metaData'
//       },
//       path: 'docattrmanager',
//       component: () => import('@/views/admin/DocAttrManager.vue'),
//       name: '文档元数据管理'
//     },
//     {
//       meta: {
//         requireAuth: true,
//         permit: 5,
//         title: 'objectType'
//       },
//       path: 'typemanager',
//       component: () => import('@/views/admin/TypeManager.vue'),
//       name: '业务类型管理'
//     },
//     {
//       meta: {
//         requireAuth: true,
//         permit: 5
//       },
//       path: 'defattrmanager',
//       component: () => import('@/views/admin/DefAttrManager.vue'),
//       name: '业务元数据管理'
//     },
//     {
//       meta: {
//         requireAuth: true,
//         permit: 5,
//         title: 'storeManager'
//       },
//       path: 'storemanager',
//       component: () => import('@/views/admin/StoreManager.vue'),
//       name: '存储管理'
//     },
//     {
//       meta: {
//         requireAuth: true,
//         permit: 5,
//         title: 'aclManager'
//       },
//       path: 'aclmanager',
//       component: () => import('@/views/admin/AclManager.vue'),
//       name: 'ACL管理'
//     },
//     {
//       meta: {
//         requireAuth: true,
//         permit: 2,
//         title: 'queryManager'
//       },
//       path: 'querymanager',
//       component: () => import('@/views/admin/QueryManager.vue'),
//       name: '查询管理'
//     },
//     {
//       meta: {
//         requireAuth: true,
//         permit: 2,
//         title: 'selectvalueManager'
//       },
//       path: 'selectvaluemanager',
//       component: () => import('@/views/admin/SelectValueManager.vue'),
//       name: '选项管理'
//     },
//     {
//       meta: {
//         requireAuth: true,
//         permit: 9,
//         title: 'cardsearchManager'
//       },
//       path: 'cardsearchmanager',
//       component: () => import('@/views/admin/CardSearchManager.vue'),
//       name: '卡片查询管理'
//     },
//     {
//       meta: {
//         requireAuth: true,
//         permit: 9,
//         title: 'cardsearchitemManager'
//       },
//       path: 'cardsearchitemmanager',
//       component: () => import('@/views/admin/CardSearchItemManager.vue'),
//       name: '卡片表单项'
//     },
//     {
//       meta: {
//         requireAuth: true,
//         permit: 9,
//         title: 'languageManager'
//       },
//       path: 'languagemanager',
//       component: () => import('@/views/admin/LanguageManager.vue'),
//       name: '语言管理'
//     },
//     {
//       meta: {
//         requireAuth: true,
//         permit: 9,
//         title: 'langinfoManager'
//       },
//       path: 'langinfomanager',
//       component: () => import('@/views/admin/LangInfoManager.vue'),
//       name: '语言标签管理'
//     },
//     {
//       meta: {
//         requireAuth: true,
//         permit: 9,
//         title: 'auditManager'
//       },
//       path: 'auditmanager',
//       component: () => import('@/views/admin/AuditManager.vue'),
//       name: '日志管理'
//     },
//     {
//       meta: {
//         requireAuth: true,
//         permit: 9,
//         title: 'syseventManager'
//       },
//       path: 'syseventmanager',
//       component: () => import('@/views/admin/SysEventManager.vue'),
//       name: '事件管理'
//     },
//     {
//       meta: {
//         requireAuth: true,
//         permit: 9,
//         title: 'systemManager'
//       },
//       path: 'systemmanager',
//       component: () => import('@/views/admin/SystemManager.vue'),
//       name: '系统配置'
//     },
//     {
//       meta: {
//         requireAuth: true,
//         permit: 2,
//         title: 'folderManager'
//       },
//       path: 'foldermanager',
//       component: () => import('@/views/admin/FolderManager.vue'),
//       name: '文件夹管理'
//     },
//     {
//       meta: {
//         requireAuth: true,
//         permit: 9,
//         title: 'activityManager'
//       },
//       path: 'activitymanager',
//       component: () => import('@/views/admin/ActivityManager.vue'),
//       name: '工作流活动管理'
//     },
//     {
//       meta: {
//         requireAuth: true,
//         permit: 9,
//         title: 'jobManager'
//       },
//       path: 'jobmanager',
//       component: () => import('@/views/admin/JobManager.vue'),
//       name: 'Job管理'
//     },
//     {
//       meta: {
//         requireAuth: true,
//         permit: 9,
//         title: 'allWorkflow'
//       },
//       path: '/workflow/allWorkflow',
//       component: () => import('@/views/workflow/MyWorkflow.vue'),
//       name: '所有工作流',
//       children:[
//         {
//           meta: {
//             requireAuth: true,
//             permit: 1
//           },
//           path: '/admin_borrow3',
//           name: '测试3',
//           component: () => import('@/components/form/Borrow.vue')
          
//         }
//       ]
//     }
//   ]
// }