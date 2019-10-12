
import Vue from 'vue';
<template>
  <div id="app">
    <el-container>
      <el-header style="height:44px" v-show="userName != ''">
        <div class="navbar-top">
          <div class="navbar-top-inner">
            <div class="container-top">
              <img src="./assets/ecmlogo.png" border="0">
            </div>
            <div class="container-top">
              <span style="font-size: 18px;color: #fff;">{{$t('application.name')}}</span>
            </div>
            <div class="container-top">
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
            </div>

              <div v-if="clientPermission>4" class="container-top-right">
                <el-select v-model="currentLanguage" @change="languageChange" style="width:105px">
                  <el-option label="简体中文" value="zh-cn" key="zh-cn"></el-option>
                  <el-option label="English" value="en" key="en"></el-option>
                </el-select>
              </div>
              <div class="container-top-right">
                <img :src="'/static/img/head128.jpg'" class="img-head">
                <span >{{userName}} &nbsp;</span>
                <i class="el-icon-switch-button" @click="logout" :title="$t('application.logout')"></i>
              </div>
          </div>
        </div>
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
      userName:'',
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
      sessionStorage.removeItem('access-userName');
      this.$router.push({path: '/login'});
    },
    checklogin()
    {
      var user = sessionStorage.getItem('access-userName');
      if(user)
      {
        this.userName = sessionStorage.getItem('access-userName');
        this.clientPermission = sessionStorage.getItem('access-clientPermission');
      }
      else
      {
        this.userName = '';
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

.el-menu.el-menu--horizontal{
  border: none;
}
.el-menu--horizontal>.el-menu-item {
    float: left;
    height: 32px;
    line-height: 32px;
    margin: 0;
    border: none;
    /*border-bottom: 2px solid transparent;*/
    color: #ffffff;
    background-color: #36a9e1;
}

.el-menu--horizontal>.el-submenu .el-submenu__title {
    height: 32px;
    line-height: 32px;
    border: none;
    /*border-bottom: 2px solid transparent;*/
    color: #070707;
}
.el-menu--horizontal {
    border: none;
    background-color: #36a9e1;
}
.el-header, .el-footer {
    background-color: #36a9e1;
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
  .el-tree-node.is-current > .el-tree-node__content {
    background-color: #d9dff5 !important;
  }
  .searchhighlight  {
    background-color: rgb(245, 241, 1);
    font-size: 14px;
    font-weight: bold;
  }
  .el-table__expanded-cell[class*=cell]{
  padding: 0px 0px;
  padding-left: 106px;
}
</style>
