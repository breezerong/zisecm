
<template>
    <el-container>
      <el-container>
        <el-aside width="180px">
          <div v-bind:style="{height: menuHeight +'px'}">
          <!--左侧导航-->
          <el-menu default-active="31" class="el-menu-vertical-ecm"  :default-openeds="opens">
            <el-submenu index="30" v-if="clientPermission>3">
              <template slot="title">
                <i class="el-icon-setting"></i>
                <span>系统管理</span>
              </template>
              <el-menu-item index="31">
                <i class="el-icon-ecm-my_light"></i>
                <span slot="title">
                  <router-link to="/drawing/usermanager">用户管理</router-link>
                </span>
              </el-menu-item>
              <el-menu-item index="32">
                <i class="el-icon-help"></i>
                <span slot="title">
                  <router-link :to="{path:'/drawing/folderview',query:{paraName:'Project'}}">项目管理</router-link>
                </span>
              </el-menu-item>
              <el-menu-item index="33">
                <i class="el-icon-s-check"></i>
                <span slot="title">
                  <router-link :to="{path:'/drawing/folderview',query:{paraName:'CompanySign'}}">公司签章</router-link>
                </span>
              </el-menu-item>
              <el-menu-item index="34">
                <i class="el-icon-s-check"></i>
                <span slot="title">
                  <router-link :to="{path:'/drawing/folderview',query:{paraName:'UserSign'}}">负责人章</router-link>
                </span>
              </el-menu-item>
              <el-menu-item index="35">
                <i class="el-icon-warning"></i>
                <span slot="title">
                  <router-link :to="{path:'/drawing/folderview',query:{paraName:'DigitalCertificate'}}">数字证书</router-link>
                </span>
              </el-menu-item>
              <el-menu-item index="36">
                <i class="el-icon-tickets"></i>
                <span slot="title">
                  <router-link :to="{path:'/drawing/templateview',query:{paraName:'PdfTemplate'}}">模板管理</router-link>
                </span>
              </el-menu-item>
              <el-menu-item index="37">
                <i class="el-icon-refresh"></i>
                <span slot="title">
                  <router-link to="/drawing/refreshcache">刷新缓存</router-link>
                </span>
              </el-menu-item>
              
            </el-submenu>
          </el-menu>
          </div>
        </el-aside>
        <el-main>
          <transition name="fade" mode="out-in">
            <router-view :key="$route.fullPath"></router-view>
          </transition>
        </el-main>
      </el-container>
    </el-container>
</template>
<script>
export default {
  name: 'DrawingManager',
  data() {
    return {
      todoCount:0,
      opens:['30'],
      username:'',
      clientPermission: 0,
      menuHeight: window.innerHeight -64
    };
  },
  created(){ 
    let _self = this;
    var user = sessionStorage.getItem('access-user');
    if(user)
    {
      _self.clientPermission = sessionStorage.getItem('access-clientPermission');
    }
    //_self.refreshcount();
    _self.$router.push({path: "/drawing/usermanager"});
  },
  methods: {
  
  }
}
</script>

<style>
  body > .el-container {
    padding: 0px;
    margin-top: 0px;
    margin-left: 0px;
    margin-right: 0px;
    margin-bottom: 0px;
  }
</style>
