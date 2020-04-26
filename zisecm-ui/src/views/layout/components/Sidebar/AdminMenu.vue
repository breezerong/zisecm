<template>
  <div v-if="clientPermission>3">
    <el-submenu index="999">
        <template slot="title">
          <i class="menu-white el-icon-setting"></i>
          <span slot="title">{{$t('route.managerCenter')}}</span>
        </template>
    <div v-if="systemPermission>=5">
      <router-link to="/managercenter/parametermanager">
        <el-menu-item index="/managercenter/parametermanager">
          <i class="iconfont zisecm-edit_light menu-white"></i>
          <span slot="title">参数设置</span>
        </el-menu-item>
      </router-link>
      <el-submenu index="1010">
        <template slot="title">
          <i class="menu-white el-icon-menu"></i>
          <span slot="title">元数据管理</span>
        </template>
        <router-link to="/managercenter/docattrmanager">
          <el-menu-item index="/managercenter/docattrmanager">
            <i class="menu-white iconfont zisecm-form"></i>
            <span slot="title">系统元数据</span>
          </el-menu-item>
        </router-link>
        <router-link to="/managercenter/typemanager">
          <el-menu-item index="/managercenter/typemanager">
            <i class="menu-white iconfont zisecm-cascades"></i>
            <span slot="title">业务类型</span>
          </el-menu-item>
        </router-link>
        <router-link to="/managercenter/storemanager">
          <el-menu-item index="/managercenter/storemanager">
            <i class="menu-white iconfont zisecm-file"></i>
            <span slot="title">存储管理</span>
          </el-menu-item>
        </router-link>
        <router-link to="/managercenter/languagemanager">
          <el-menu-item index="/managercenter/languagemanager">
            <i class="menu-white el-icon-warning-outline"></i>
            <span slot="title">语言管理</span>
          </el-menu-item>
        </router-link>
      </el-submenu>
    </div>
    <div v-if="systemPermission>=4">
      <el-submenu index="1200">
        <template slot="title">
          <i class="el-icon-menu menu-white"></i>
          <span slot="title">界面配置</span>
        </template>
        <router-link to="/managercenter/langinfomanager">
          <el-menu-item index="/managercenter/langinfomanager">
            <i class="el-icon-info menu-white"></i>
            <span slot="title">语言标签</span>
          </el-menu-item>
        </router-link>
        <router-link to="/managercenter/componentmanager">
          <el-menu-item index="/managercenter/componentmanager">
            <i class="iconfont zisecm-news_light menu-white"></i>
            <span slot="title">组件设置</span>
          </el-menu-item>
        </router-link>
        <router-link to="/managercenter/actionmanager">
          <el-menu-item index="/managercenter/actionmanager">
            <i class="iconfont zisecm-link menu-white"></i>
            <span slot="title">事件设置</span>
          </el-menu-item>
        </router-link>
        <router-link to="/managercenter/menumanager">
          <el-menu-item index="/managercenter/menumanager">
            <i class="iconfont zisecm-round_menu_fill menu-white"></i>
            <span slot="title">菜单设置</span>
          </el-menu-item>
        </router-link>
        <router-link to="/managercenter/formmanager">
          <el-menu-item index="/managercenter/formmanager">
            <i class="iconfont zisecm-form_light menu-white"></i>
            <span slot="title">表单设置</span>
          </el-menu-item>
        </router-link>
        <router-link to="/managercenter/gridviewmanager">
          <el-menu-item index="/managercenter/gridviewmanager">
            <i class="iconfont zisecm-list menu-white"></i>
            <span slot="title">列表设置</span>
          </el-menu-item>
        </router-link>
        <router-link to="/managercenter/querymanager">
          <el-menu-item index="/managercenter/querymanager">
            <i class="iconfont zisecm-search_list_light menu-white"></i>
            <span slot="title">查询设置</span>
          </el-menu-item>
        </router-link>
        <router-link to="/managercenter/cardsearchmanager">
          <el-menu-item index="/managercenter/cardsearchmanager">
            <i class="iconfont zisecm-vipcard menu-white"></i>
            <span slot="title">卡片查询</span>
          </el-menu-item>
        </router-link>
      </el-submenu>
    </div>
    <div v-if="systemPermission>=2">
      <el-submenu index="1300">
        <template slot="title">
          <i class="iconfont zisecm-group menu-white"></i>
          <span slot="title">用户组管理</span>
        </template>
        <router-link to="/managercenter/groupmanager">
          <el-menu-item index="/managercenter/groupmanager">
            <i class="iconfont zisecm-people_list_light menu-white"></i>
            <span slot="title">部门管理</span>
          </el-menu-item>
        </router-link>
        <router-link to="/managercenter/rolemanager">
          <el-menu-item index="/managercenter/rolemanager">
            <i class="iconfont zisecm-people_list_light menu-white"></i>
            <span slot="title">角色管理</span>
          </el-menu-item>
        </router-link>
        <router-link to="/managercenter/usermanager">
          <el-menu-item index="/managercenter/usermanager">
            <i class="iconfont zisecm-my_light menu-white"></i>
            <span slot="title">用户管理</span>
          </el-menu-item>
        </router-link>
      </el-submenu>
      <router-link to="/managercenter/aclmanager">
        <el-menu-item index="/managercenter/aclmanager">
          <i class="el-icon-lock menu-white"></i>
          <span slot="title">ACL管理</span>
        </el-menu-item>
      </router-link>
      <router-link to="/managercenter/systemmanager">
        <el-menu-item index="/managercenter/systemmanager">
          <i class="el-icon-refresh menu-white"></i>
          <span slot="title">缓存管理</span>
        </el-menu-item>
      </router-link>
    </div>
    <div v-if="systemPermission>0">
      <router-link to="/managercenter/selectvaluemanager">
        <el-menu-item index="/managercenter/selectvaluemanager">
          <i class="iconfont zisecm-sortlight menu-white"></i>
          <span slot="title">选项设置</span>
        </el-menu-item>
      </router-link>
      <router-link to="/managercenter/foldermanager">
        <el-menu-item index="/managercenter/foldermanager">
          <i class="el-icon-message menu-white"></i>
          <span slot="title">文件夹设置</span>
        </el-menu-item>
      </router-link>
    </div>
    <div v-if="systemPermission>=4">
      <el-submenu index="1500">
        <template slot="title">
          <i class="el-icon-menu menu-white"></i>
          <span>工作流管理</span>
        </template>
        <router-link to="/managercenter/activitymanager">
          <el-menu-item index="/managercenter/activitymanager">
            <i class="el-icon-s-order menu-white"></i>
            <span slot="title">流程配置</span>
          </el-menu-item>
        </router-link>
        <router-link to="/managercenter/jobmanager">
          <el-menu-item index="/managercenter/jobmanager">
            <i class="el-icon-s-order menu-white"></i>
            <span slot="title">Job管理</span>
          </el-menu-item>
        </router-link>
        <router-link :to="{ path: '/workflow/allWorkflow', query: { showAllWorkflow: 1}}">
          <el-menu-item index="/workflow/allWorkflow">
            <i class="iconfont zisecm-round_pay menu-white"></i>
            <span slot="title">流程查看</span>
          </el-menu-item>
        </router-link>
      </el-submenu>
    </div>
    <div v-if="systemPermission>=5">
      <el-submenu index="1600">
        <template slot="title">
          <i class="el-icon-menu menu-white"></i>
          <span>日志管理</span>
        </template>
        <router-link to="/managercenter/syseventmanager">
          <el-menu-item index="/managercenter/syseventmanager">
            <i class="iconfont zisecm-edit menu-white"></i>
            <span slot="title">事件管理</span>
          </el-menu-item>
        </router-link>
        <router-link to="/managercenter/auditmanager">
          <el-menu-item index="/managercenter/auditmanager">
            <i class="el-icon-search menu-white"></i>
            <span slot="title">日志查询</span>
          </el-menu-item>
        </router-link>
      </el-submenu>
    </div>
    </el-submenu>
  </div>
</template>
<script>
export default {
  name: "AdminMenu",
  data() {
    return {
      isCollapse: false,
      username: "",
      clientPermission: 0,
      systemPermission: 0,
      asideWidth: "200px",
      menuHeight: window.innerHeight - 100
    };
  },
  props: {
    isNest: {
      type: Boolean,
      default: false
    }
  },
  created() {
    this.checkLogin();
  },
  methods: {
    checkLogin() {
      var user = sessionStorage.getItem("access-user");
      if (user) {
        this.clientPermission = Number(
          sessionStorage.getItem("access-clientPermission")
        );
        this.systemPermission = Number(
          sessionStorage.getItem("access-systemPermission")
        );
        //console.log("clientPermission:"+this.clientPermission);
        //console.log("systemPermission:"+this.systemPermission);
        this.username = JSON.parse(
          sessionStorage.getItem("access-user")
        ).username;
      } else {
        this.username = "";
      }
    }
  }
};
</script scoped>
<style scoped>

</style>
