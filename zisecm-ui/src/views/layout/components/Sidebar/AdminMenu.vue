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
          <span slot="title">{{$t('route.parameterManager')}}</span>
        </el-menu-item>
      </router-link>
      <el-submenu index="1010">
        <template slot="title">
          <i class="menu-white el-icon-menu"></i>
          <span slot="title">{{$t('route.metaData')}}</span>
        </template>
        <router-link to="/managercenter/docattrmanager">
          <el-menu-item index="/managercenter/docattrmanager">
            <i class="menu-white iconfont zisecm-form"></i>
            <span slot="title">{{$t('route.defAttribute')}}</span>
          </el-menu-item>
        </router-link>
        <router-link to="/managercenter/typemanager">
          <el-menu-item index="/managercenter/typemanager">
            <i class="menu-white iconfont zisecm-cascades"></i>
            <span slot="title">{{$t('route.objectType')}}</span>
          </el-menu-item>
        </router-link>
        <router-link to="/managercenter/storemanager">
          <el-menu-item index="/managercenter/storemanager">
            <i class="menu-white iconfont zisecm-file"></i>
            <span slot="title">{{$t('route.storeManager')}}</span>
          </el-menu-item>
        </router-link>
        <router-link to="/managercenter/languagemanager">
          <el-menu-item index="/managercenter/languagemanager">
            <i class="menu-white el-icon-warning-outline"></i>
            <span slot="title">{{$t('route.languageManager')}}</span>
          </el-menu-item>
        </router-link>
      </el-submenu>
    </div>
    <div v-if="systemPermission>=4">
      <el-submenu index="1200">
        <template slot="title">
          <i class="el-icon-menu menu-white"></i>
          <span slot="title">{{$t('application.systemPermission4')}}</span>
        </template>
        <router-link to="/managercenter/langinfomanager">
          <el-menu-item index="/managercenter/langinfomanager">
            <i class="el-icon-info menu-white"></i>
            <span slot="title">{{$t('route.langinfoManager')}}</span>
          </el-menu-item>
        </router-link>
        <router-link to="/managercenter/componentmanager">
          <el-menu-item index="/managercenter/componentmanager">
            <i class="iconfont zisecm-news_light menu-white"></i>
            <span slot="title">{{$t('route.componentManager')}}</span>
          </el-menu-item>
        </router-link>
        <router-link to="/managercenter/actionmanager">
          <el-menu-item index="/managercenter/actionmanager">
            <i class="iconfont zisecm-link menu-white"></i>
            <span slot="title">{{$t('route.actionManager')}}</span>
          </el-menu-item>
        </router-link>
        <router-link to="/managercenter/menumanager">
          <el-menu-item index="/managercenter/menumanager">
            <i class="iconfont zisecm-round_menu_fill menu-white"></i>
            <span slot="title">{{$t('route.menuManager')}}</span>
          </el-menu-item>
        </router-link>
        <router-link to="/managercenter/formmanager">
          <el-menu-item index="/managercenter/formmanager">
            <i class="iconfont zisecm-form_light menu-white"></i>
            <span slot="title">{{$t('route.formManager')}}</span>
          </el-menu-item>
        </router-link>
        <router-link to="/managercenter/gridviewmanager">
          <el-menu-item index="/managercenter/gridviewmanager">
            <i class="iconfont zisecm-list menu-white"></i>
            <span slot="title">{{$t('route.gridviewManager')}}</span>
          </el-menu-item>
        </router-link>
        <router-link to="/managercenter/querymanager">
          <el-menu-item index="/managercenter/querymanager">
            <i class="iconfont zisecm-search_list_light menu-white"></i>
            <span slot="title">{{$t('route.queryManager')}}</span>
          </el-menu-item>
        </router-link>
        <router-link to="/managercenter/cardsearchmanager">
          <el-menu-item index="/managercenter/cardsearchmanager">
            <i class="iconfont zisecm-vipcard menu-white"></i>
            <span slot="title">{{$t('route.cardsearchManager')}}</span>
          </el-menu-item>
        </router-link>

         <router-link to="/managercenter/uirelationmanager">
          <el-menu-item index="/managercenter/uirelationmanager">
            <i class="iconfont zisecm-vipcard menu-white"></i>
            <span slot="title">{{$t('route.relationFileTypeSetting')}}</span>
          </el-menu-item>
        </router-link>
      </el-submenu>
    </div>
    <div v-if="systemPermission>=2">
      <el-submenu index="1300">
        <template slot="title">
          <i class="iconfont zisecm-group menu-white"></i>
          <span slot="title">{{$t('route.usergroupManager')}}</span>
        </template>
        <router-link to="/managercenter/groupmanager">
          <el-menu-item index="/managercenter/groupmanager">
            <i class="iconfont zisecm-people_list_light menu-white"></i>
            <span slot="title">{{$t('route.groupManager')}}</span>
          </el-menu-item>
        </router-link>
        <router-link to="/managercenter/rolemanager">
          <el-menu-item index="/managercenter/rolemanager">
            <i class="iconfont zisecm-people_list_light menu-white"></i>
            <span slot="title">{{$t('route.roleManager')}}</span>
          </el-menu-item>
        </router-link>
        <router-link to="/managercenter/usermanager">
          <el-menu-item index="/managercenter/usermanager">
            <i class="iconfont zisecm-my_light menu-white"></i>
            <span slot="title">{{$t('route.userManager')}}</span>
          </el-menu-item>
        </router-link>
      </el-submenu>
      <router-link to="/managercenter/aclmanager">
        <el-menu-item index="/managercenter/aclmanager">
          <i class="el-icon-lock menu-white"></i>
          <span slot="title">{{$t('route.aclManager')}}</span>
        </el-menu-item>
      </router-link>
      <router-link to="/managercenter/systemmanager">
        <el-menu-item index="/managercenter/systemmanager">
          <i class="el-icon-refresh menu-white"></i>
          <span slot="title">{{$t('route.cacheManager')}}</span>
        </el-menu-item>
      </router-link>
    </div>
    <div v-if="systemPermission>0">
      <router-link to="/managercenter/selectvaluemanager">
        <el-menu-item index="/managercenter/selectvaluemanager">
          <i class="iconfont zisecm-sortlight menu-white"></i>
          <span slot="title">{{$t('route.selectvalueManager')}}</span>
        </el-menu-item>
      </router-link>
      <router-link to="/managercenter/foldermanager">
        <el-menu-item index="/managercenter/foldermanager">
          <i class="el-icon-message menu-white"></i>
          <span slot="title">{{$t('route.folderManager')}}</span>
        </el-menu-item>
      </router-link>
    </div>
    <div v-if="systemPermission>=4">
      <el-submenu index="1500">
        <template slot="title">
          <i class="el-icon-menu menu-white"></i>
          <span>{{$t('route.workflowManager')}}</span>
        </template>
        <router-link to="/managercenter/activitymanager">
          <el-menu-item index="/managercenter/activitymanager">
            <i class="el-icon-s-order menu-white"></i>
            <span slot="title">{{$t('route.activityManager')}}</span>
          </el-menu-item>
        </router-link>
        <router-link to="/managercenter/jobmanager">
          <el-menu-item index="/managercenter/jobmanager">
            <i class="el-icon-s-order menu-white"></i>
            <span slot="title">{{$t('route.jobManager')}}</span>
          </el-menu-item>
        </router-link>
        <router-link :to="{ path: '/workflow/allWorkflow', query: { showAllWorkflow: 1}}">
          <el-menu-item index="/workflow/allWorkflow">
            <i class="iconfont zisecm-round_pay menu-white"></i>
            <span slot="title">{{$t('route.allWorkflow')}}</span>
          </el-menu-item>
        </router-link>
      </el-submenu>
    </div>
    <div v-if="systemPermission>=5">
      <el-submenu index="1600">
        <template slot="title">
          <i class="el-icon-menu menu-white"></i>
          <span>{{$t('route.logManager')}}</span>
        </template>
        <router-link to="/managercenter/syseventmanager">
          <el-menu-item index="/managercenter/syseventmanager">
            <i class="iconfont zisecm-edit menu-white"></i>
            <span slot="title">{{$t('route.syseventManager')}}</span>
          </el-menu-item>
        </router-link>
        <router-link to="/managercenter/auditmanager">
          <el-menu-item index="/managercenter/auditmanager">
            <i class="el-icon-search menu-white"></i>
            <span slot="title">{{$t('route.auditManager')}}</span>
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
      if (this.currentUser()) {
        this.clientPermission = Number(
          this.currentUser().clientPermission
        );
        this.systemPermission = Number(
          this.currentUser().systemPermission
        );
        //console.log("clientPermission:"+this.clientPermission);
        //console.log("systemPermission:"+this.systemPermission);
        this.username = this.currentUser().userName;
      } else {
        this.username = "";
      }
    }
  }
};
</script scoped>
<style scoped>

</style>
