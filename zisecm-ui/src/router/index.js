import Vue from "vue";
import Router from "vue-router";
import { adminRouter } from "@/router/admin.js";
import { recordRouter } from "@/router/record.js";
import { searchRouter } from "@/router/search.js";
import { dcRouter } from "@/router/doccenter.js";
import { reportRouter } from "@/router/report.js";
import { userRouter } from "@/router/user.js";
import { workflowRouter } from "@/router/workflow.js";
import { testRouter } from "@/router/test.js";
import { docexchange } from "@/router/docexchange.js";
import {cnpeRouter} from "@/router/cnpe.js"
import store from "@/store";
import Layout from "../views/layout/Layout";

Vue.use(Router);

if (sessionStorage.getItem("token")) {
  store.commit("set_token", sessionStorage.getItem("access-token"));
}

const router = new Router({
  routes: [
    {
      meta: {
        requireAuth: true
      },
      path: "/",
      component: Layout,
      redirect: "/home",
      children: [
        {
          meta: {
            requireAuth: true,
            title: 'home'
          },
          path: "/home",
          component: () => import("@/views/HomeContent.vue"),
          name: "首页内容"
        },
        
        ...docexchange,
        ...cnpeRouter,
        searchRouter,
        ...dcRouter,
        ...recordRouter,
        workflowRouter,
        reportRouter,
        userRouter,
        // docexchange,
        ...testRouter,
        ...adminRouter,
        {
          meta: {
            requireAuth: true,
            title: 'mainContainer'
          },
          path: "/container",
          name: "内容容器",
          component: () => import("@/views/MainContainer.vue")
        },
        // 图标组件
        {
          path: '/icons',
          name: 'iconIndex',
          component: () => import('@/views/icons/svg-icons/iconIndex'),
          meta: { title: 'svgicons', icon: 'icon' }
        },
      ]
    },
    {
      meta: {
        requireAuth: true,
        title: 'NoPermission'
      },
      path: "/NoPermission",
      component: () => import("@/views/NoPermission.vue"),
      name: "无权限"
    },
    {
      meta: {
        requireAuth: true,
        title: 'helpCenter'
      },
      path: "/helpcenter",
      name: "帮助中心",
      component: () =>
        import(/* webpackChunkName: "helpcenter" */ "@/views/HelpCenter.vue")
    },
    {
      meta: {
        requireAuth: true,
        permit: 3
      },
      path: "/viewdoc",
      name: "viewdoc",
      component: () =>
        import(/* webpackChunkName: "home1" */ "@/views/dc/ViewDoc.vue"),
      children: [
        {
          meta: {
            requireAuth: true,
            permit: 1
          },
          path: "/viewDoc_borrow",
          name: "测试2",
          component: () => import("@/components/form/Borrow.vue")
        }
      ]
    },
    {
      meta: {
        requireAuth: true,
        permit: 3
      },
      path: "/FeedbackDetail",
      name: "问题详情",
      component: () =>
        import(/* webpackChunkName: "home1" */ "@/views/docexchange/Feedback/FeedbackDetail.vue"),
    },
    {
      path: "/login",
      name: "login",
      component: () => import(/* webpackChunkName: "Login" */ "@/views/login")
    }
  ]
});
router.beforeEach((to, from, next) => {
  var user = sessionStorage.getItem("access-token");
  let loginName = getValue(window.location.href, "LoginName");
  if (!user && to.path !== "/login") {
    sessionStorage.removeItem("access-user");
    sessionStorage.removeItem("access-token");
    if (loginName) {
      next({
        name: "login",
        query: {
          LoginName: loginName,
          redirect: to.path
        }
      });
    } else {
      next({
        name: "login",
        query: {
          redirect: to.path
        }
      });
    }
  } else {
    next();
  }
});

function getValue(str, name) {
  var reg = new RegExp("(^|&|\\?)" + name + "=([^#]*)(&|$|#)"),
    r;
  if ((r = str.match(reg))) return unescape(r[2]);
  return null;
}

router.onError(error => {
  const pattern = /Loading chunk (\d)+ failed/g;
  const isChunkLoadFailed = error.message.match(pattern);
  const targetPath = router.history.pending.fullPath;
  if (isChunkLoadFailed) {
    router.replace(targetPath);
  }
});
export default router;
