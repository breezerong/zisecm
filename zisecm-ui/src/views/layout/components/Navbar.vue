<template>
  <el-menu class="navbar" mode="horizontal">
    <ecm-hamburger class="hamburger-container" :toggleClick="toggleSideBar" :isActive="sidebar.opened"></ecm-hamburger>

    <ecm-breadcrumb class="breadcrumb-container"></ecm-breadcrumb>

    <div class="right-menu">

      <!-- <error-log class="errLog-container right-menu-item"></error-log> -->
     
      <span>
        <router-link to="/user/userinfo" style="color:#fff;">{{userName}}</router-link>&nbsp;
      </span>
      <ecm-help class="ecm-help right-menu-item" />
      <ecm-top-lock style="cursor:pointer" class="ecm-help"></ecm-top-lock>
      <el-tooltip effect="dark" content="全屏" placement="bottom">
        <ecm-full-screen class="screenfull right-menu-item"></ecm-full-screen>
      </el-tooltip>

      <!-- <lang-select class="international right-menu-item"></lang-select> -->
      <ecm-lang-select class="international right-menu-item"></ecm-lang-select>

      <el-tooltip effect="dark" content="主题" placement="bottom">
        <!-- <theme-picker class="theme-switch right-menu-item"></theme-picker> -->
        <ecm-skin class="theme-switch right-menu-item"></ecm-skin>
      </el-tooltip>

      <el-dropdown class="avatar-container right-menu-item" trigger="click">
        <div class="avatar-wrapper">
          <i class="el-icon-caret-bottom"></i>
        </div>
        <el-dropdown-menu slot="dropdown">
          <router-link to="/">
            <el-dropdown-item>
              首页
            </el-dropdown-item>
          </router-link>
          <el-dropdown-item divided>
            <span @click="logout" style="display:block;">退出</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </el-menu>
</template>

<script>
import { mapGetters } from 'vuex'
import ecmTopLock from '@/components/ecm-top-lock'
import ecmBreadcrumb from '@/components/ecm-breadcrumb'
import ecmHamburger from '@/components/ecm-hamburger'
import ecmHelp from '@/components/ecm-help/index'
import ecmFullScreen from '@/components/ecm-full-screen/index'
import ecmLangSelect from '@/components/ecm-lang-select/index'
import ecmSkin from '@/components/ecm-skin/index'

export default {
  name: 'navBar',
  components: {
    ecmBreadcrumb,
    ecmHamburger,
    ecmHelp,
    ecmFullScreen,
    ecmLangSelect,
    ecmSkin,
    ecmTopLock
  },
  data() {
    return {
      userName: "",
      systemPermission: 1,
      clientPermission: 1
    };
  },
  mounted(){
    this.checklogin();
  },
  computed: {
    ...mapGetters(['sidebar', 'name', 'avatar'])
    
  },
  methods: {
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
    toggleSideBar() {
      this.$store.dispatch('ToggleSideBar')
    },
    logout() {
      sessionStorage.removeItem("access-user");
      sessionStorage.removeItem("access-userName");
      var loca = window.location;
      if (loca.search != null && loca.search != "") {
        if (loca.search.substr(1, 9) == "LoginName") {
          var url = loca.origin + loca.pathname + "#/login";
          window.location.href = url;
        } else {
          this.$router.push({ path: "/login" });
        }
      } else {
        this.$router.push({ path: "/login" });
      }
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.navbar {
  height: 50px;
  line-height: 50px;
  border-radius: 0px !important;
  .hamburger-container {
    line-height: 58px;
    height: 50px;
    float: left;
    padding: 0 10px;
  }
  .breadcrumb-container {
    float: left;
  }
  .ecm-help {
    display: inline-block;
    vertical-align: top;
  }
  .errLog-container {
    display: inline-block;
    vertical-align: top;
  }
  .head-image {
    width: 32px;
    height: 32px;
    border-radius: 0px;
  }
  .right-menu {
    float: right;
    height: 100%;
    &:focus {
      outline: none;
    }
    .right-menu-item {
      display: inline-block;
      margin: 0 8px;
    }
    .screenfull {
      height: 20px;
    }
    .international {
      vertical-align: top;
    }
    .theme-switch {
      vertical-align: 15px;
    }
    .avatar-container {
      height: 32px;
      margin-right: 30px;
      .avatar-wrapper {
        cursor: pointer;
        margin-top: 0px;
        position: relative;
        .user-avatar {
          width: 32px;
          height: 32px;
          border-radius: 2px;
        }
        .el-icon-caret-bottom {
          position: absolute;
          right: -20px;
          top: 5px;
          font-size: 12px;
        }
      }
    }
  }
}
</style>
