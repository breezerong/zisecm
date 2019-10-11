
import Vue from 'vue';
<template>
  <div id="app">
    <el-container>
      <el-header style="height:44px">
        <table border="0" width="100%">
          <tr>
            <td align="left" width="56px">
              <img src="./assets/ecmlogo.png" border="0">
            </td>
            <td align="left" width="180px">
              <span style="font-size: 18px;color: #fff;">{{$t('application.name')}}</span>
            </td>
            <td >
              <el-menu default-active="1" mode="horizontal">
                <el-menu-item index="1"><router-link to="/home">{{$t('menu.home')}}</router-link></el-menu-item>
                  <el-menu-item index="2" v-if="clientPermission>4"><router-link to="/searchcenter">{{$t('menu.searchCenter')}}</router-link></el-menu-item>
                  <el-menu-item index="3" v-if="clientPermission>4"><router-link to="/taskcenter">{{$t('menu.taskCenter')}}</router-link></el-menu-item>
                  <el-submenu index="4" v-if="clientPermission>4">
                    <template slot="title">{{$t('menu.dataCenter')}}</template>
                    <el-menu-item index="4-1"><router-link to="/datacenter/folder">{{$t('menu.folderClassification')}}</router-link></el-menu-item>
                    <el-menu-item index="4-2"><router-link to="/datacenter/standard">{{$t('menu.standardClassification')}}</router-link></el-menu-item>
                    <el-menu-item index="4-3"><router-link to="/datacenter/custom">{{$t('menu.customClassification')}}</router-link></el-menu-item>
                  </el-submenu>
                  <el-menu-item index="5"><router-link to="/reportcenter">{{$t('menu.reportCenter')}}</router-link></el-menu-item>
                  <el-menu-item index="6" v-if="clientPermission>3"><router-link to="/drawingmanager">{{$t('menu.systemManager')}}</router-link></el-menu-item>
                  <el-menu-item index="7" v-if="clientPermission>4"><router-link to="/managercenter">{{$t('menu.managerCenter')}}</router-link></el-menu-item>
                <el-menu-item index="8"><router-link to="/helpcenter">{{$t('menu.helpCenter')}}</router-link></el-menu-item>
              </el-menu>
            </td>
              <td align="right" width="120px">
                 <div v-if="clientPermission>4">
                <el-select v-model="currentLanguage" @change="languageChange">
                  <el-option label="简体中文" value="zh-cn" key="zh-cn"></el-option>
                  <el-option label="English" value="en" key="en"></el-option>
                </el-select>
                </div>
              </td>
            <td align="right" width="200px">
              <i class="el-icon-s-custom"></i>
              <span >{{username}}</span>
            </td>
            <td width="60px">
             <el-button size="mini" @click="logout">{{$t('application.logout')}}</el-button>
            </td>
          </tr>
        </table>
      </el-header>
      <el-container>
        <el-main>
          <transition name="fade" mode="out-in">
            <router-view></router-view>
          </transition>
        </el-main>
      </el-container>
    </el-container>
   </div>
</template>
<script>
import enLocale from 'element-ui/lib/locale/lang/en'
import zhLocale from 'element-ui/lib/locale/lang/zh-CN'
import locale from 'element-ui/lib/locale'

export default {
  name: 'App',
  data() {
    return {
      currentLanguage:"",
      username:'',
      clientPermission: 0,
      defaultColor:'#409EFF',
      menuHeight: window.innerHeight -70
    };
  },
  beforeCreate()
  {
    var user = sessionStorage.getItem('access-user');
    if(!user)
    {
	    this.$router.push({path: '/Login'});
    }
  },
  mounted(){ 
    this.currentLanguage = localStorage.getItem("localeLanguage") || "zh-cn";
    this.$nextTick(function () {
      setInterval(this.checklogin, 1000);
    })
  },
  methods: {
    logout() {
      sessionStorage.removeItem('access-user');
      this.$router.push({path: '/login'});
    },
    checklogin()
    {
      var user = sessionStorage.getItem('access-user');
      if(user)
      {
        this.username = JSON.parse(sessionStorage.getItem('access-user')).username;
        this.clientPermission = sessionStorage.getItem('access-clientPermission');
      }
      else
      {
        this.username = '';
      }
    },
    languageChange(val)
    {
      var lang = localStorage.getItem("localeLanguage") || "zh-cn";
      if(lang!=val){
        this.$i18n.local = val;
        if(lang === "zh-cn"){
          locale.use(zhLocale);
        }else{
          locale.use(enLocale);
        }
        localStorage.setItem("localeLanguage",val);
        this.$router.go(0);
      }
    }
  }
}
</script>

<style>
 body{
    padding: 0px;
    margin-top: 0px;
    margin-left: 0px;
    margin-right: 0px;
    margin-bottom: 0px;
    background: #f5f5f5;
    color: #383e4b;
  }
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 0px;
  margin-left: 0px;
  margin-right: 0px;
  margin-bottom: 0px;
  padding: 0px;
}
.el-menu-item, .el-submenu__title{
  height: 32px;
  line-height:32px;
}

element.style {
    border-bottom-color: transparent;
}
.el-menu--horizontal>.el-menu-item {
    float: left;
    height: 32px;
    line-height: 32px;
    margin: 0;
    border-bottom: 2px solid transparent;
    color: #070707;
    background-color: #B3C0D1;
}

.el-menu--horizontal>.el-submenu .el-submenu__title {
    height: 32px;
    line-height: 32px;
    border-bottom: 2px solid transparent;
    color: #070707;
}
.el-menu--horizontal {
    border-right: none;
    border-bottom: solid 1px #e6e6e6;
    background-color: #B3C0D1;
}
.el-header, .el-footer {
    background-color: #B3C0D1;
    color: #333;
    text-align: center;
    padding: 0px;
    margin-top: 0px;
    margin-left: 0px;
    margin-right: 0px;
    margin-bottom: 0px;
  }
  
  .el-aside {
    background-color: #D3DCE6;
    color: #333;
    text-align: center;
    padding: 0px;
    margin-top: 0px;
    margin-left: 0px;
    margin-right: 0px;
    margin-bottom: 0px;
  }
  
  .el-main {
    background-color: #E9EEF3;
    color: #333;
    text-align: center;
    padding: 0px;
    margin-top: 0px;
    margin-left: 0px;
    margin-right: 0px;
    margin-bottom: 0px;
  }
  .el-breadcrumb {
    font-size: 14px;
    line-height: 1;
    height: 24px;
    display:table-cell;
    vertical-align:middle;
  }
  body > .el-container {
    padding: 0px;
    margin-top: 0px;
    margin-left: 0px;
    margin-right: 0px;
    margin-bottom: 0px;
  }
</style>
