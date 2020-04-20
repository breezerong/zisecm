<template>
  <el-container>
    <el-container>
      <el-aside :width="asideWidth">
        <div
          style="text-align:left;cursor:pointer;background-color: #DCDFE6; height:25px;"
          @click="handleClose"
        >
          <i v-if="isCollapse==false" class="el-icon-s-fold"></i>
          <i v-else class="el-icon-s-unfold"></i>
        </div>
        <div v-bind:style="{height: menuHeight +'px'}">
          <!--左侧导航class="el-menu-vertical-ecm"-->
          <el-menu default-active="101" :collapse="isCollapse" class="menu-wrapper">
            <div v-if="clientPermission>3">
              <div v-if="systemPermission>=5">
                <router-link to="/managercenter/parametermanager">
                  <el-menu-item index="101">
                    <i class="el-icon-ecm-edit_light"></i>
                    <span slot="title">参数设置</span>
                  </el-menu-item>
                </router-link>
                <el-submenu index="110">
                  <template slot="title">
                    <i class="el-icon-menu"></i>
                    <span slot="title">元数据管理</span>
                  </template>
                  <router-link to="/managercenter/docattrmanager">
                    <el-menu-item index="111">
                      <i class="el-icon-ecm-form"></i>
                      <span slot="title">系统元数据</span>
                    </el-menu-item>
                  </router-link>
                  <router-link to="/managercenter/typemanager">
                    <el-menu-item index="112">
                      <i class="el-icon-ecm-cascades"></i>
                      <span slot="title">业务类型</span>
                    </el-menu-item>
                  </router-link>
                  <router-link to="/managercenter/storemanager">
                    <el-menu-item index="113">
                      <i class="el-icon-ecm-file"></i>
                      <span slot="title">存储管理</span>
                    </el-menu-item>
                  </router-link>
                  <router-link to="/managercenter/languagemanager">
                    <el-menu-item index="114">
                      <i class="el-icon-warning-outline"></i>
                      <span slot="title">语言管理</span>
                    </el-menu-item>
                  </router-link>
                </el-submenu>
              </div>
              <!--流程
                   <div v-if="systemPermission>=4">
                  <el-submenu index="200">
                    <template slot="title">
                      <i class="el-icon-menu"></i>
                      <span>状态流程</span>
                    </template>
                    <el-menu-item index="201">
                      <i class="el-icon-caret-right"></i>
                      <span slot="title"><router-link to="/admin/WorkflowStatus">状态流</router-link></span>
                    </el-menu-item>
                    
                  </el-submenu>
                  </div>

              end流程-->
              <div v-if="systemPermission>=4">
                <el-submenu index="200">
                  <template slot="title">
                    <i class="el-icon-menu"></i>
                    <span slot="title">界面配置</span>
                  </template>
                  <router-link to="/managercenter/langinfomanager">
                    <el-menu-item index="209">
                      <i class="el-icon-info"></i>
                      <span slot="title">语言标签</span>
                    </el-menu-item>
                  </router-link>
                  <router-link to="/managercenter/componentmanager">
                    <el-menu-item index="210">
                      <i class="el-icon-ecm-news_light"></i>
                      <span slot="title">组件设置</span>
                    </el-menu-item>
                  </router-link>
                  <router-link to="/managercenter/actionmanager">
                    <el-menu-item index="211">
                      <i class="el-icon-ecm-link"></i>
                      <span slot="title">事件设置</span>
                    </el-menu-item>
                  </router-link>
                  <router-link to="/managercenter/menumanager">
                    <el-menu-item index="212">
                      <i class="el-icon-ecm-round_menu_fill"></i>
                      <span slot="title">菜单设置</span>
                    </el-menu-item>
                  </router-link>
                  <router-link to="/managercenter/formmanager">
                    <el-menu-item index="213">
                      <i class="el-icon-ecm-form_light"></i>
                      <span slot="title">表单设置</span>
                    </el-menu-item>
                  </router-link>
                  <router-link to="/managercenter/gridviewmanager">
                    <el-menu-item index="214">
                      <i class="el-icon-ecm-list"></i>
                      <span slot="title">列表设置</span>
                    </el-menu-item>
                  </router-link>
                  <router-link to="/managercenter/querymanager">
                    <el-menu-item index="215">
                      <i class="el-icon-ecm-search_list_light"></i>
                      <span slot="title">查询设置</span>
                    </el-menu-item>
                  </router-link>
                  <router-link to="/managercenter/cardsearchmanager">
                    <el-menu-item index="216">
                      <i class="el-icon-ecm-vipcard"></i>
                      <span slot="title">卡片查询</span>
                    </el-menu-item>
                  </router-link>
                </el-submenu>
              </div>
              <div v-if="systemPermission>=2">
                <el-submenu index="300">
                  <template slot="title">
                    <i class="el-icon-ecm-group"></i>
                    <span slot="title">用户组管理</span>
                  </template>
                  <router-link to="/managercenter/groupmanager">
                    <el-menu-item index="301">
                      <i class="el-icon-ecm-people_list_light"></i>
                      <span slot="title">部门管理</span>
                    </el-menu-item>
                  </router-link>
                  <router-link to="/managercenter/rolemanager">
                    <el-menu-item index="302">
                      <i class="el-icon-ecm-people_list_light"></i>
                      <span slot="title">角色管理</span>
                    </el-menu-item>
                  </router-link>
                  <router-link to="/managercenter/usermanager">
                    <el-menu-item index="303">
                      <i class="el-icon-ecm-my_light"></i>
                      <span slot="title">用户管理</span>
                    </el-menu-item>
                  </router-link>
                </el-submenu>
                <router-link to="/managercenter/aclmanager">
                  <el-menu-item index="103">
                    <i class="el-icon-lock"></i>
                    <span slot="title">ACL管理</span>
                  </el-menu-item>
                </router-link>
                <router-link to="/managercenter/systemmanager">
                  <el-menu-item index="104">
                    <i class="el-icon-refresh"></i>
                    <span slot="title">缓存管理</span>
                  </el-menu-item>
                </router-link>
              </div>
              <div v-if="systemPermission>0">
                <router-link to="/managercenter/selectvaluemanager">
                  <el-menu-item index="120">
                    <i class="el-icon-ecm-sortlight"></i>
                    <span slot="title">选项设置</span>
                  </el-menu-item>
                </router-link>
                <router-link to="/managercenter/foldermanager">
                  <el-menu-item index="121">
                    <i class="el-icon-message"></i>
                    <span slot="title">文件夹设置</span>
                  </el-menu-item>
                </router-link>
              </div>
              <div v-if="systemPermission>=4">
                <el-submenu index="500">
                  <template slot="title">
                    <i class="el-icon-menu"></i>
                    <span>工作流管理</span>
                  </template>
                  <router-link to="/managercenter/activitymanager">
                    <el-menu-item index="501">
                      <i class="el-icon-s-order"></i>
                      <span slot="title">流程配置</span>
                    </el-menu-item>
                  </router-link>
                  <router-link to="/managercenter/jobmanager">
                    <el-menu-item index="502">
                      <i class="el-icon-s-order"></i>
                      <span slot="title">Job管理</span>
                    </el-menu-item>
                  </router-link>
                  <router-link :to="{ path: '/workflow/allWorkflow', query: { showAllWorkflow: 1}}">
                    <el-menu-item index="503">
                      <i class="el-icon-ecm-round_pay"></i>
                      <span slot="title">流程查看</span>
                    </el-menu-item>
                  </router-link>
                </el-submenu>
              </div>
              <div v-if="systemPermission>=5">
                  <el-submenu index="600">
                  <template slot="title">
                    <i class="el-icon-menu"></i>
                    <span>日志管理</span>
                  </template>
                  <router-link to="/managercenter/syseventmanager">
                    <el-menu-item index="601">
                      <i class="el-icon-ecm-edit"></i>
                      <span slot="title">事件管理</span>
                    </el-menu-item>
                  </router-link>
                  <router-link to="/managercenter/auditmanager">
                    <el-menu-item index="602">
                      <i class="el-icon-search"></i>
                      <span slot="title">日志查询</span>
                    </el-menu-item>
                  </router-link>
                </el-submenu>
              </div>
            </div>
          </el-menu>
        </div>
      </el-aside>
      <el-main>
        <transition name="fade" mode="out-in">
          <router-view></router-view>
        </transition>
      </el-main>
    </el-container>
  </el-container>
</template>
<script>
export default {
  name: "SearchCenter",
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
    handleClose() {
      this.isCollapse = !this.isCollapse;
      this.asideWidth = this.isCollapse ? "50px" : "200px";
    },
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
body > .el-container {
  padding: 0px;
  margin-top: 0px;
  margin-left: 0px;
  margin-right: 0px;
  margin-bottom: 0px;
  height: 100%;
}

element.style {
  padding-left: 20px;
  color: rgb(64, 158, 255);
  background-color: rgb(48, 65, 86);
}

li {
  width: 100%;
  text-align: left;
}
i {
  font-size: 20px !important;
}
</style>
