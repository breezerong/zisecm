<template>
  <div v-if="clientPermission>3">
    <el-submenu index="999">
        <template slot="title">
          <i class="white el-icon-setting"></i>
          <span slot="title">{{$t('route.managerCenter')}}</span>
        </template>
    <div v-if="systemPermission>=5">
      <router-link to="/managercenter/parametermanager">
        <el-menu-item index="101">
          <i class="white el-icon-ecm-edit_light"></i>
          <span slot="title">参数设置</span>
        </el-menu-item>
      </router-link>
      <el-submenu index="1010">
        <template slot="title">
          <i class="white el-icon-menu"></i>
          <span slot="title">元数据管理</span>
        </template>
        <router-link to="/managercenter/docattrmanager">
          <el-menu-item index="1011">
            <i class="white el-icon-ecm-form"></i>
            <span slot="title">系统元数据</span>
          </el-menu-item>
        </router-link>
        <router-link to="/managercenter/typemanager">
          <el-menu-item index="1012">
            <i class="white el-icon-ecm-cascades"></i>
            <span slot="title">业务类型</span>
          </el-menu-item>
        </router-link>
        <router-link to="/managercenter/storemanager">
          <el-menu-item index="1013">
            <i class="white el-icon-ecm-file"></i>
            <span slot="title">存储管理</span>
          </el-menu-item>
        </router-link>
        <router-link to="/managercenter/languagemanager">
          <el-menu-item index="1014">
            <i class="white el-icon-warning-outline"></i>
            <span slot="title">语言管理</span>
          </el-menu-item>
        </router-link>
      </el-submenu>
    </div>
    <div v-if="systemPermission>=4">
      <el-submenu index="1200">
        <template slot="title">
          <i class="white el-icon-menu"></i>
          <span slot="title">界面配置</span>
        </template>
        <router-link to="/managercenter/langinfomanager">
          <el-menu-item index="1209">
            <i class="white el-icon-info"></i>
            <span slot="title">语言标签</span>
          </el-menu-item>
        </router-link>
        <router-link to="/managercenter/componentmanager">
          <el-menu-item index="1210">
            <i class="white el-icon-ecm-news_light"></i>
            <span slot="title">组件设置</span>
          </el-menu-item>
        </router-link>
        <router-link to="/managercenter/actionmanager">
          <el-menu-item index="1211">
            <i class="white el-icon-ecm-link"></i>
            <span slot="title">事件设置</span>
          </el-menu-item>
        </router-link>
        <router-link to="/managercenter/menumanager">
          <el-menu-item index="1212">
            <i class="white el-icon-ecm-round_menu_fill"></i>
            <span slot="title">菜单设置</span>
          </el-menu-item>
        </router-link>
        <router-link to="/managercenter/formmanager">
          <el-menu-item index="1213">
            <i class="white el-icon-ecm-form_light"></i>
            <span slot="title">表单设置</span>
          </el-menu-item>
        </router-link>
        <router-link to="/managercenter/gridviewmanager">
          <el-menu-item index="1214">
            <i class="white el-icon-ecm-list"></i>
            <span slot="title">列表设置</span>
          </el-menu-item>
        </router-link>
        <router-link to="/managercenter/querymanager">
          <el-menu-item index="1215">
            <i class="white el-icon-ecm-search_list_light"></i>
            <span slot="title">查询设置</span>
          </el-menu-item>
        </router-link>
        <router-link to="/managercenter/cardsearchmanager">
          <el-menu-item index="1216">
            <i class="white el-icon-ecm-vipcard"></i>
            <span slot="title">卡片查询</span>
          </el-menu-item>
        </router-link>
      </el-submenu>
    </div>
    <div v-if="systemPermission>=2">
      <el-submenu index="1300">
        <template slot="title">
          <i class="white el-icon-ecm-group"></i>
          <span slot="title">用户组管理</span>
        </template>
        <router-link to="/managercenter/groupmanager">
          <el-menu-item index="1301">
            <i class="white el-icon-ecm-people_list_light"></i>
            <span slot="title">部门管理</span>
          </el-menu-item>
        </router-link>
        <router-link to="/managercenter/rolemanager">
          <el-menu-item index="1302">
            <i class="white el-icon-ecm-people_list_light"></i>
            <span slot="title">角色管理</span>
          </el-menu-item>
        </router-link>
        <router-link to="/managercenter/usermanager">
          <el-menu-item index="1303">
            <i class="white el-icon-ecm-my_light"></i>
            <span slot="title">用户管理</span>
          </el-menu-item>
        </router-link>
      </el-submenu>
      <router-link to="/managercenter/aclmanager">
        <el-menu-item index="1103">
          <i class="white el-icon-lock"></i>
          <span slot="title">ACL管理</span>
        </el-menu-item>
      </router-link>
      <router-link to="/managercenter/systemmanager">
        <el-menu-item index="1104">
          <i class="white el-icon-refresh"></i>
          <span slot="title">缓存管理</span>
        </el-menu-item>
      </router-link>
    </div>
    <div v-if="systemPermission>0">
      <router-link to="/managercenter/selectvaluemanager">
        <el-menu-item index="1120">
          <i class="white el-icon-ecm-sortlight"></i>
          <span slot="title">选项设置</span>
        </el-menu-item>
      </router-link>
      <router-link to="/managercenter/foldermanager">
        <el-menu-item index="1121">
          <i class="white el-icon-message"></i>
          <span slot="title">文件夹设置</span>
        </el-menu-item>
      </router-link>
    </div>
    <div v-if="systemPermission>=4">
      <el-submenu index="1500">
        <template slot="title">
          <i class="white el-icon-menu"></i>
          <span>工作流管理</span>
        </template>
        <router-link to="/managercenter/activitymanager">
          <el-menu-item index="1501">
            <i class="white el-icon-s-order"></i>
            <span slot="title">流程配置</span>
          </el-menu-item>
        </router-link>
        <router-link to="/managercenter/jobmanager">
          <el-menu-item index="1502">
            <i class="white el-icon-s-order"></i>
            <span slot="title">Job管理</span>
          </el-menu-item>
        </router-link>
        <router-link :to="{ path: '/workflow/allWorkflow', query: { showAllWorkflow: 1}}">
          <el-menu-item index="1503">
            <i class="white el-icon-ecm-round_pay"></i>
            <span slot="title">流程查看</span>
          </el-menu-item>
        </router-link>
      </el-submenu>
    </div>
    <div v-if="systemPermission>=5">
      <el-submenu index="1600">
        <template slot="title">
          <i class="white el-icon-menu"></i>
          <span>日志管理</span>
        </template>
        <router-link to="/managercenter/syseventmanager">
          <el-menu-item index="1601">
            <i class="white el-icon-ecm-edit"></i>
            <span slot="title">事件管理</span>
          </el-menu-item>
        </router-link>
        <router-link to="/managercenter/auditmanager">
          <el-menu-item index="1602">
            <i class="white el-icon-search"></i>
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
.white {
    filter: brightness(100);
}
</style>
