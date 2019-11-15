import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import Home from '@/components/Home'
import SearchCenter from '@/components/SearchCenter'
import ManagerCenter from '@/components/ManagerCenter'
import TaskCenter from '@/components/TaskCenter'
import ReportCenter from '@/components/ReportCenter'
import HelpCenter from '@/components/HelpCenter'
import ParameterManager from '@/components/admin/ParameterManager'
import ComponentManager from '@/components/admin/ComponentManager'
import ActionManager from '@/components/admin/ActionManager'
import MenuManager from '@/components/admin/MenuManager'
import MenuItemManager from '@/components/admin/MenuItemManager'
import FormManager from '@/components/admin/FormManager'
import FormItemManager from '@/components/admin/FormItemManager'
import GridViewManager from '@/components/admin/GridViewManager'
import GridViewItemManager from '@/components/admin/GridViewItemManager'
import CategoryManager from '@/components/admin/CategoryManager'
import QueryManager from '@/components/admin/QueryManager'
import SelectValueManager from '@/components/admin/SelectValueManager'
import CardSearchManager from '@/components/admin/CardSearchManager'
import CardSearchItemManager from '@/components/admin/CardSearchItemManager'
import FolderManager from '@/components/admin/FolderManager'
import FolderClassification from '@/components/dc/FolderClassification'
import StandardClassification from '@/components/dc/StandardClassification'
import CustomClassification from '@/components/dc/CustomClassification'
import FolderView from '@/components/dc/FolderView'
import SystemManager from '@/components/admin/SystemManager'
import UserManager from '@/components/admin/UserManager'
import GroupManager from '@/components/admin/GroupManager'
import RoleManager from '@/components/admin/RoleManager'
import DocAttrManager from '@/components/admin/DocAttrManager'
import TypeManager from '@/components/admin/TypeManager'
import DefAttrManager from '@/components/admin/DefAttrManager'
import StoreManager from '@/components/admin/StoreManager'
import AclManager from '@/components/admin/AclManager'
import TodoTask from '@/components/workflow/TodoTask'
import DoneTask from '@/components/workflow/DoneTask'
import MyWorkflow from '@/components/workflow/MyWorkflow'
import StartWorkflow from '@/components/workflow/StartWorkflow'
import ShowWorkitem from '@/components/workflow/ShowWorkitem'
import ImportDoc from '@/components/drawing/ImportDoc'
import Processing from '@/components/drawing/Processing'
import DownloadDoc from '@/components/drawing/DownloadDoc'
import DoneDoc from '@/components/drawing/DoneDoc'
import ErrorDoc from '@/components/drawing/ErrorDoc'
import DrawingMananger from '@/components/DrawingMananger'
import TemplateView from '@/components/drawing/TemplateView'
import DrawingReport from '@/components/report/DrawingReport'
import SystemReport from '@/components/report/SystemReport'
import Error401 from '@/components/error/Error401'
import DocViewer from '@/components/dc/DocViewer'
import FullTextSearch from '@/components/search/FullTextSearch'
import CardSearch from '@/components/search/CardSearch'
import AdvSearch from '@/components/search/AdvSearch'
import MySearch from '@/components/search/MySearch'
import UserCenter from '@/components/UserCenter'
import UserInfo from '@/components/user/UserInfo'
import ChangePassword from '@/components/user/ChangePassword'
import Main from '@/components/Main'
import CreateRecord from '@/components/record/CreateRecord'
import CreateArchive from '@/components/record/CreateArchive'
import WorkflowStatus from '@/components/admin/WorkflowStatus'
import RecycleBin from '@/views/dc/RecycleBin'
import CompanyDoc from '@/views/dc/CompanyDoc'

import Test1 from '@/components/test/Test1'
import Test2 from '@/components/test/Test2'

Vue.use(Router)

const router = new Router({
  base: '/zisecm/',
  routes: [
    {
      path: '/login',
      name: '登录',
      component: Login
    },
    {
      meta: {
        requireAuth: true
      },
      path: '/',
      name: '默认',
      component: Home
    },
    {
      meta: {
        requireAuth: true
      },
      path: '/docviewer',
      name: '文档查看',
      component: DocViewer
    },
    {
      meta: {
        requireAuth: true
      },
      path: '/main',
      name: '主界面',
      component: Main,
      children: [
        {
          meta: {
            requireAuth: true
          },
          path: '/home',
          name: '首页',
          component: Home,
          children: [
            {
              meta: {
                requireAuth: true,
                permit: 1
              },
              path: '/workflow/todotask',
              name: '待办工作',
              component: TodoTask
            },
            {
              meta: {
                requireAuth: true,
                permit: 1
              },
              path: '/workflow/donetask',
              name: '已办工作',
              component: DoneTask
            },
            {
              meta: {
                requireAuth: true,
                permit: 1
              },
              path: '/workflow/myworkflow',
              name: '我的流程',
              component: MyWorkflow
            },
            {
              meta: {
                requireAuth: true,
                permit: 1
              },
              path: '/drawing/importdoc',
              name: '导入图纸',
              component: ImportDoc
            },
            {
              meta: {
                requireAuth: true,
                permit: 1
              },
              path: '/drawing/processing',
              name: '处理中',
              component: Processing
            },
            {
              meta: {
                requireAuth: true,
                permit: 1
              },
              path: '/drawing/downloaddoc',
              name: '待导出',
              component: DownloadDoc
            },
            {
              meta: {
                requireAuth: true,
                permit: 3
              },
              path: '/drawing/donedoc',
              name: '已完成',
              component: DoneDoc
            },
            {
              meta: {
                requireAuth: true,
                permit: 3
              },
              path: '/drawing/errordoc',
              name: '错误处理',
              component: ErrorDoc
            }
            
          ]
        },{
          meta: {
            requireAuth: true
            },
            path: '/record/createRecord',
            component: CreateRecord,
            name: '按件整理'
        },
        {
          meta: {
          requireAuth: true
          },
          path: '/record/createArchive',
          component: CreateArchive,
          name: '按卷整理'
        },
        {
          meta: {
            requireAuth: true
          },
          path: '/drawingmanager',
          name: '图纸管理',
          component: DrawingMananger,
          children: [
            {
              meta: {
                requireAuth: true,
                permit: 3
              },
              path: '/drawing/templateview',
              name: '模板管理',
              component: TemplateView
            },
            {
              meta: {
                requireAuth: true,
                permit: 4
              },
              path: '/drawing/usermanager',
              name: '用户管理1',
              component: UserManager
            }
            ,
            {
              meta: {
                requireAuth: true,
                permit: 4
              },
              path: '/drawing/folderview',
              name: '文件夹浏览1',
              component: FolderView
            },
            {
              meta: {
                requireAuth: true,
                permit: 3
              },
              path: '/drawing/refreshcache',
              name: '刷新缓存1',
              component: SystemManager
            }
          ]
        },
        {
          meta: {
            requireAuth: true
          },
          path: '/searchcenter',
          name: '搜索中心',
          component: SearchCenter,
          children: [
            {
              meta: {
                requireAuth: true,
                permit: 1
              },
              path: '/search/fulltextsearch',
              name: '全文搜索',
              component: FullTextSearch
            },
            {
              meta: {
                requireAuth: true,
                permit: 1
              },
              path: '/search/cardsearch',
              name: '卡片搜索',
              component: CardSearch
            },
            {
              meta: {
                requireAuth: true,
                permit: 1
              },
              path: '/search/advsearch',
              name: '高级搜索',
              component: AdvSearch
            },
            {
              meta: {
                requireAuth: true,
                permit: 1
              },
              path: '/search/mysearch',
              name: '我的搜索',
              component: MySearch
            }
          ]
        },
        {
          meta: {
            requireAuth: true,
            permit: 1
          },
          path: '/taskcenter',
          name: '任务中心',
          component: TaskCenter,
          children: [
            {
              meta: {
                requireAuth: true,
                permit: 1
              },
              path: '/taskcenter/todotask',
              name: '待办工作2',
              component: TodoTask
            },
            {
              meta: {
                requireAuth: true,
                permit: 1
              },
              path: '/taskcenter/donetask',
              name: '已办工作2',
              component: DoneTask
            },
            {
              meta: {
                requireAuth: true,
                permit: 1
              },
              path: '/taskcenter/myworkflow',
              name: '我的流程2',
              component: MyWorkflow
            }
            ,
            {
              meta: {
                requireAuth: true,
                permit: 3
              },
              path: '/taskcenter/startworkflow',
              name: '发起流程',
              component: StartWorkflow
            }
            ,
            {
              meta: {
                requireAuth: true,
                permit: 1
              },
              path: '/taskcenter/showworkitem',
              name: '查看任务',
              component: ShowWorkitem
            }
          ]
        },
        {
          meta: {
            requireAuth: true
          },
          path: '/reportcenter',
          name: '报表中心',
          component: ReportCenter,
          children: [
            {
              meta: {
                requireAuth: true,
                permit: 2
              },
              path: '/reportcenter/drawingreport',
              name: '图纸报表',
              component: DrawingReport
            },
            {
              meta: {
                requireAuth: true,
                permit: 2
              },
              path: '/reportcenter/systemreport',
              name: '系统报表',
              component: SystemReport
            }
          ]
        }
        ,
        {
          meta: {
            requireAuth: true
          },
          path: '/usercenter',
          name: '个人信息',
          component: UserCenter,
          children: [
            {
              meta: {
                requireAuth: true,
                permit: 1
              },
              path: '/user/userinfo',
              name: '用户基本信息',
              component: UserInfo
            },
            {
              meta: {
                requireAuth: true,
                permit: 1
              },
              path: '/user/changepassword',
              name: '修改密码',
              component: ChangePassword
            }
          ]
        },
        {
          meta: {
            requireAuth: true
          },
          path: '/helpcenter',
          name: '帮助中心',
          component: HelpCenter
        },
        {
          meta: {
            requireAuth: true
          },
          path: '/error/401',
          name: 'Error401',
          component: Error401
        },
        {
          meta: {
            requireAuth: true,
            permit: 9
          },
          path: '/managercenter',
          component: ManagerCenter,
          name: '系统管理',
          children: [
            {
              meta: {
                requireAuth: true,
                permit: 5
              },
              path: '/managercenter/parametermanager',
              component: ParameterManager,
              name: '参数管理'
            },
            {
             
              meta: {
                requireAuth: true,
                permit: 5
              },
              path: '/admin/workflowstatus',
              component:  WorkflowStatus,
              name: '状态流程'
            },
            {
              meta: {
                requireAuth: true,
                permit: 5
              },
              path: '/managercenter/usermanager',
              component: UserManager,
              name: '用户管理'
            },
            {
              meta: {
                requireAuth: true,
                permit: 5
              },
              path: '/managercenter/groupmanager',
              component: GroupManager,
              name: '组管理'
            },
            {
              meta: {
                requireAuth: true,
                permit: 5
              },
              path: '/managercenter/rolemanager',
              component: RoleManager,
              name: '角色管理'
            },
            {
              meta: {
                requireAuth: true,
                permit: 5
              },
              path: '/managercenter/componentmanager',
              component: ComponentManager,
              name: '组件管理'
            },
            {
              meta: {
                requireAuth: true,
                permit: 5
              },
              path: '/managercenter/actionmanager',
              component: ActionManager,
              name: '事件管理'
            },
            {
              meta: {
                requireAuth: true,
                permit: 5
              },
              path: '/managercenter/menumanager',
              component: MenuManager,
              name: '菜单管理'
            },
            {
              meta: {
                requireAuth: true,
                permit: 5
              },
              path: '/managercenter/menuitemmanager',
              component: MenuItemManager,
              name: '菜单项管理'
            },
            {
              meta: {
                requireAuth: true,
                permit: 5
              },
              path: '/managercenter/formmanager',
              component: FormManager,
              name: '表单管理'
            },
            {
              meta: {
                requireAuth: true,
                permit: 5
              },
              path: '/managercenter/formItemmanager',
              component: FormItemManager,
              name: '表单项管理'
            },
            {
              meta: {
                requireAuth: true,
                permit: 5
              },
              path: '/managercenter/gridviewmanager',
              component: GridViewManager,
              name: '列表管理'
            },
            {
              meta: {
                requireAuth: true,
                permit: 5
              },
              path: '/managercenter/gridviewitemmanager',
              component: GridViewItemManager,
              name: '列表项管理'
            },
            {
              meta: {
                requireAuth: true,
                permit: 5
              },
              path: '/managercenter/docattrmanager',
              component: DocAttrManager,
              name: '文档元数据管理'
            },
            {
              meta: {
                requireAuth: true,
                permit: 5
              },
              path: '/managercenter/typemanager',
              component: TypeManager,
              name: '业务类型管理'
            },
            {
              meta: {
                requireAuth: true,
                permit: 5
              },
              path: '/managercenter/defattrmanager',
              component: DefAttrManager,
              name: '业务元数据管理'
            },
            {
              meta: {
                requireAuth: true,
                permit: 5
              },
              path: '/managercenter/storemanager',
              component: StoreManager,
              name: '存储管理'
            },
            {
              meta: {
                requireAuth: true,
                permit: 5
              },
              path: '/managercenter/aclmanager',
              component: AclManager,
              name: 'ACL管理'
            },
            {
              meta: {
                requireAuth: true,
                permit: 0
              },
              path: '/managercenter/categorymanager',
              component: CategoryManager,
              name: '元数据查询'
            },
            {
              meta: {
                requireAuth: true,
                permit: 2
              },
              path: '/managercenter/querymanager',
              component: QueryManager,
              name: '查询管理'
            },
            {
              meta: {
                requireAuth: true,
                permit: 2
              },
              path: '/managercenter/selectvaluemanager',
              component: SelectValueManager,
              name: '选项管理'
            },
            {
              meta: {
                requireAuth: true,
                permit: 9
              },
              path: '/managercenter/cardsearchmanager',
              component: CardSearchManager,
              name: '卡片查询管理'
            },
            {
              meta: {
                requireAuth: true,
                permit: 9
              },
              path: '/managercenter/cardSearchitemmanager',
              component: CardSearchItemManager,
              name: '卡片表单项'
            },
            {
              meta: {
                requireAuth: true,
                permit: 9
              },
              path: '/managercenter/systemmanager',
              component: SystemManager,
              name: '系统配置'
            },
            {
              meta: {
                requireAuth: true,
                permit: 2
              },
              path: '/managercenter/foldermanager',
              component: FolderManager,
              name: '文件夹管理'
            }
          ]
        },
        {
          meta: {
            requireAuth: true
          },
          path: '/datacenter/folder',
          component: FolderClassification,
          name: '文件夹分类'
        },
        {
          meta: {
            requireAuth: true
          },
          path: '/datacenter/standard',
          component: StandardClassification,
          name: '标准分类'
        },
        {
          meta: {
            requireAuth: true
          },
          path: '/datacenter/custom',
          component: CustomClassification,
          name: '自定义分类'
        },
        {
          meta: {
            requireAuth: true
          },
          path: '/dc/recyclebin',
          component: RecycleBin,
          name: '回收站'
        },
        {
          meta: {
            requireAuth: true
          },
          path: '/dc/companydoc',
          component: CompanyDoc,
          name: '公司文档'
        }
        ,
        {
          meta: {
            requireAuth: true
          },
          path: '/test/test1',
          component: Test1,
          name: '测试1'
        }
        ,
        {
          meta: {
            requireAuth: true
          },
          path: '/test/test2',
          component: Test2,
          name: '测试2'
        }
      ]
    }
  ]
})

router.beforeEach((to, from, next) => {
  var user = sessionStorage.getItem('access-user')
  var permit = sessionStorage.getItem('access-clientPermission') || '0'
  // console.log(user)
  // console.log(permit)
  // 未登录
  if (!user && to.path !== '/Login') {
    sessionStorage.removeItem('access-userName')
    next('/Login')
  } else {
    // 权限未定义或最高权限
    if (!to.meta.permit || permit === '9') {
      next()
    } else {
      // 有权限
      if (Number(permit) >= to.meta.permit) {
        next()
      } else {
        next('/error/401')
      }
    }
  }
})

export default router
