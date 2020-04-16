export const workflowRouter = 
{
  meta: {
    requireAuth: true,
    permit: 1
  },
  path: '/taskcenter',
  name: '任务中心',
  component: () => import('@/views/TaskCenter.vue'),
  children:
    [
        {
          meta: {
            requireAuth: true,
            permit: 1
          },
          path: '/workflow/todotask',
          name: '待办工作',
          component: () => import('@/views/workflow/task/TodoTask.vue'),
          children:[
            {
              meta: {
                requireAuth: true,
                permit: 1
              },
              path: '/borrow1',
              name: '测试1',
              component: () => import('@/components/form/Borrow.vue')
              
            }
          ]
    
        },
        {
          meta: {
            requireAuth: true,
            permit: 1
          },
          path: '/workflow/donetask',
          name: '已办工作',
          component: () => import('@/views/workflow/DoneTask.vue'),
          children:[
            {
              meta: {
                requireAuth: true,
                permit: 1
              },
              path: '/borrow2',
              name: '测试2',
              component: () => import('@/components/form/Borrow.vue')
              
            }
          ]

        },
        {
          meta: {
            requireAuth: true,
            permit: 1
          },
          path: '/workflow/myworkflow',
          name: '我的流程',
          component: () => import('@/views/workflow/MyWorkflow.vue'),
          children:[
            {
              meta: {
                requireAuth: true,
                permit: 1
              },
              path: '/borrow3',
              name: '测试3',
              component: () => import('@/components/form/Borrow.vue')
              
            }
          ]
        },{
          meta: {
            requireAuth: true,
            permit: 1
          },
          path: '/record/archiveoutgoing/archivegivebackmine',
          name: '待归还',
          component: () => import('@/views/record/ArchivePendinggivebackMine.vue')
          
        }
    ]
  }
