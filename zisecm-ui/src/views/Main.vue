<template>
  <el-container>
    <el-header style="height:44px;width:100%">
      <div class="navbar-top">
        <div class="navbar-top-inner">
          <div class="container-top"  @click="$router.push(homePath)" style="cursor:pointer">
            <img :src="'../static/img/logo.png'" border="0" />
          </div>
          <div class="container-top" @click="$router.push(homePath)" style="cursor:pointer">
            <span style="font-size: 18px;color: #fff;"  >{{$t('application.name')}}</span>
          </div>
          <div class="container-top">
            <el-menu v-if="isExtUser==false" default-active="1"  mode="horizontal">
              <template v-for="item in dataList.menuItems">
                <template v-if="item.submenus && item.url==null">
                  <el-submenu :index="item.id+''" :key="item.id">
                    <template slot="title">{{item.label}}</template>
                    <template v-for="sitem in item.submenus">
                      <el-menu-item :index="sitem.id+''" :key="sitem.id" @click="clickRouter(sitem.url)">
                        <i v-if="sitem.icon!=null" :class="sitem.icon"></i>
                        <router-link :to="sitem.url">{{sitem.label}}</router-link>
                      </el-menu-item>
                    </template>
                  </el-submenu>
                </template>
                <template v-else>
                  <el-menu-item :index="item.id+''" :key="item.id+'_e'" @click="clickRouter(item.url)">
                    <router-link :to="item.url">{{item.label}}</router-link>
                  </el-menu-item>
                </template>
              </template>
            </el-menu>
          </div>
          <div v-if="systemPermission>7" class="container-top-right">
            <el-select v-model="currentLanguage" @change="languageChange" style="width:105px">
              <el-option label="简体中文" value="zh-cn" key="zh-cn"></el-option>
              <el-option label="English" value="en" key="en"></el-option>
            </el-select>
          </div>
          <div class="container-top-right" style="padding-right:8px">
            <img :src="'../static/img/head128.jpg'" class="img-head" style="cursor:pointer" @click="clickRouter('/user/userinfo')"/>
            <span>
              <router-link to="/user/userinfo" style="color:#fff;">{{userName}}</router-link>&nbsp;
            </span>
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
</template>
<script>
export default {
  name: "Main",
  data() {
    return {
      dataList:{
        menuItems: []
      },
      currentLanguage: "",
      isExtUser: false,
      userName: "",
      clientPermission: 0,
      systemPermission: 0,
      defaultColor: "#409EFF",
      homePath : "/home"
    };
  },
  beforeCreate() {
    
    var user = sessionStorage.getItem("access-user");
    if (!user) {
      //console.log("go to login.................");
      this.$router.push({ name: "login" });
    }
   
  },
  mounted() {
    var extUser = sessionStorage.getItem("access-externalUser");
    console.log("extUser:"+extUser);
    if(extUser && extUser == "true"){
      this.isExtUser = true;
    }
    this.currentLanguage = localStorage.getItem("localeLanguage") || "zh-cn";
    if(!this.isExtUser){
      this.loadMenu();
    }
    this.checklogin();
    if(this.$route && this.$route.path && this.$route.path=="/"){
      this.$router.push({ path: "/home" });
    }
    this.$nextTick(function() {
			setInterval(this.checklogin, 2000);
		});
  },
  methods: {
    loadMenu(){
      let _self = this;
      var m = new Map();
      m.set("name", "TopMenu");
      m.set("lang",_self.getLang());
      axios.post("/memu/getMyMenu",JSON.stringify(m))
        .then(function(response) {
          //console.log(response);
          if(response.data.code==1){
            _self.dataList = response.data.data;
          }
          //console.log(_self.dataList);
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    logout() {
      sessionStorage.removeItem("access-user");
      sessionStorage.removeItem("access-userName");
      var loca = window.location;
      if(loca.search!=null&&loca.search!=""){
        if(loca.search.substr(1,9) == "LoginName"){
          var url = loca.origin+loca.pathname+"#/login"
          window.location.href= url;
        }else{
          this.$router.push({ path: "/login" });
        }
      }else{
        this.$router.push({ path: "/login" });
      }        
    },
    checklogin() {
      var user = sessionStorage.getItem("access-userName");
      if (user) {
        this.userName = sessionStorage.getItem("access-userName");
        this.clientPermission = sessionStorage.getItem(
          "access-clientPermission"
        );
        this.systemPermission = sessionStorage.getItem("access-systemPermission");
      } else {
        this.userName = "";
      }
    },
    languageChange(val) {
      var lang = localStorage.getItem("localeLanguage") || "zh-cn";
      if (lang != val) {
        this.$i18n.local = val;
        if (lang === "zh-cn") {
          this.locale.use(this.zhLocale);
        } else {
          this.locale.use(this.enLocale);
        }
        localStorage.setItem("localeLanguage", val);
        this.$router.go(0);
      }
    },
    clickRouter(pathVal){
    let _self = this;
    _self.$router.push({ 
      path: pathVal,
    });

  },

  }
};
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
