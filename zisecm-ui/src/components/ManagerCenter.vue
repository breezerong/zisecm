<template>
    <el-container>
      <el-container>
        <el-aside width="200px">
          <div v-bind:style="{height: menuHeight +'px'}">
          <!--左侧导航-->
          <el-menu default-active="101" class="el-menu-vertical-ecm"  :open="100">
            <div v-if="clientPermission>3">
              <el-submenu index="100">
                <template slot="title">
                  <i class="el-icon-setting"></i>
                  <span>系统管理</span>
                </template>
                  <div v-if="systemPermission>=5">
                  <el-menu-item index="101">
                    <i class="el-icon-caret-right"></i>
                    <span slot="title"><router-link to="/managercenter/parametermanager">参数设置</router-link></span>
                  </el-menu-item>
                   <el-submenu index="110">
                    <template slot="title">
                      <i class="el-icon-menu"></i>
                      <span>元数据管理</span>
                    </template>
                    <el-menu-item index="111">
                      <i class="el-icon-caret-right"></i>
                      <span slot="title"><router-link to="/managercenter/docattrmanager">系统元数据</router-link></span>
                    </el-menu-item>
                    <el-menu-item index="112">
                      <i class="el-icon-caret-right"></i>
                      <span slot="title"><router-link to="/managercenter/typemanager">业务类型</router-link></span>
                    </el-menu-item>
                    <el-menu-item index="113">
                      <i class="el-icon-caret-right"></i>
                      <span slot="title"><router-link to="/managercenter/storemanager">存储管理</router-link></span>
                    </el-menu-item>
                   </el-submenu>
                  </div>
                  <div v-if="systemPermission>=4">
                  <el-submenu index="200">
                    <template slot="title">
                      <i class="el-icon-menu"></i>
                      <span>界面配置</span>
                    </template>
                    <el-menu-item index="201">
                      <i class="el-icon-caret-right"></i>
                      <span slot="title"><router-link to="/managercenter/componentmanager">组件设置</router-link></span>
                    </el-menu-item>
                    <el-menu-item index="202">
                      <i class="el-icon-caret-right"></i>
                      <span slot="title"><router-link to="/managercenter/actionmanager">事件设置</router-link></span>
                    </el-menu-item>
                    <el-menu-item index="203">
                      <i class="el-icon-caret-right"></i>
                      <span slot="title"><router-link to="/managercenter/menumanager">菜单设置</router-link></span>
                    </el-menu-item>
                    <el-menu-item index="204">
                      <i class="el-icon-caret-right"></i>
                      <span slot="title"><router-link to="/managercenter/formmanager">表单设置</router-link></span>
                    </el-menu-item>
                    <el-menu-item index="205">
                      <i class="el-icon-caret-right"></i>
                      <span slot="title"><router-link to="/managercenter/gridviewmanager">列表设置</router-link></span>
                    </el-menu-item>
                    <el-menu-item index="206">
                      <i class="el-icon-caret-right"></i>
                      <span slot="title"><router-link to="/managercenter/querymanager">查询设置</router-link></span>
                    </el-menu-item>
                    
                    <el-menu-item index="208">
                      <i class="el-icon-caret-right"></i>
                      <span slot="title"><router-link to="/managercenter/cardsearchmanager">卡片查询</router-link></span>
                    </el-menu-item>
                  </el-submenu>
                  </div>
                  <div v-if="systemPermission>=2">
                  <el-submenu index="300">
                    <template slot="title">
                      <i class="el-icon-ecm-group"></i>
                      <span>用户组管理</span>
                    </template>
                    <el-menu-item index="301">
                      <i class="el-icon-ecm-people_list_light"></i>
                      <span slot="title"><router-link to="/managercenter/groupmanager">部门管理</router-link></span>
                    </el-menu-item>
                    <el-menu-item index="302">
                      <i class="el-icon-ecm-people_list_light"></i>
                      <span slot="title"><router-link to="/managercenter/rolemanager">角色管理</router-link></span>
                    </el-menu-item>
                    <el-menu-item index="303">
                      <i class="el-icon-ecm-my_light"></i>
                      <span slot="title"><router-link to="/managercenter/usermanager">用户管理</router-link></span>
                    </el-menu-item>
                  </el-submenu>
                   <el-menu-item index="103">
                    <i class="el-icon-lock"></i>
                    <span slot="title"><router-link to="/managercenter/aclmanager">ACL管理</router-link></span>
                  </el-menu-item>
                  <el-menu-item index="104">
                    <i class="el-icon-refresh"></i>
                    <span slot="title"><router-link to="/managercenter/systemmanager">缓存管理</router-link></span>
                  </el-menu-item>
                </div>
                <div v-if="systemPermission>0">
                  <el-menu-item index="120">
                      <i class="el-icon-ecm-sortlight"></i>
                      <span slot="title"><router-link to="/managercenter/selectvaluemanager">选项设置</router-link></span>
                    </el-menu-item>
                  <el-menu-item index="121">
                    <i class="el-icon-message"></i>
                    <span slot="title"><router-link to="/managercenter/foldermanager">文件夹设置</router-link></span>
                  </el-menu-item>
                </div>
              </el-submenu>
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
  name: 'SearchCenter',
  data() {
    return {
       username:'',
       clientPermission: 0,
       systemPermission: 0,
       menuHeight: window.innerHeight -64
    };
  },
  created()
  {
    this.checkLogin();
  },
  methods: {
    checkLogin()
    {
      var user = sessionStorage.getItem('access-user');
      if(user)
      {
        this.clientPermission = Number(sessionStorage.getItem('access-clientPermission'));
        this.systemPermission = Number(sessionStorage.getItem('access-systemPermission'));
        console.log("clientPermission:"+this.clientPermission);
        console.log("systemPermission:"+this.systemPermission);
        this.username = JSON.parse(sessionStorage.getItem('access-user')).username;
      }
      else
      {
        this.username = '';
      }
    }
  }
}
</script scoped>

<style>
  body > .el-container {
    padding: 0px;
    margin-top: 0px;
    margin-left: 0px;
    margin-right: 0px;
    margin-bottom: 0px;
  }

  
element.style {
    padding-left: 60px;
}
.el-submenu .el-menu-item {
    height: 32px;
    line-height: 32px;
    padding: 0 36px;
    min-width: 200px;
}
</style>
