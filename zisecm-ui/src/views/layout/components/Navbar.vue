<template>
  <el-menu class="navbar" mode="horizontal">
    <ecm-hamburger
      class="hamburger-container"
      :toggleClick="toggleSideBar"
      :isActive="sidebar.opened"
    ></ecm-hamburger>

    <ecm-breadcrumb class="breadcrumb-container"></ecm-breadcrumb>

    <div class="right-menu">
      <!-- <error-log class="errLog-container right-menu-item"></error-log> 
      <span class="ecm-help" title="购物车">
        <router-link to="/docexchange/shoppingCart" >
          <svg-icon icon-class="shopping" />
        </router-link>&nbsp;
      </span>
      -->

      <ecm-help class="ecm-help right-menu-item" />
      <!--
      <ecm-top-lock style="cursor:pointer" class="ecm-help"></ecm-top-lock>
      -->
      <el-tooltip effect="dark" v-bind:content="$t('application.fullscreen')" placement="bottom">
        <ecm-full-screen class="screenfull right-menu-item"></ecm-full-screen>
      </el-tooltip>

      <ecm-lang-select class="international right-menu-item"></ecm-lang-select>
      <ecm-skin class="theme-switch right-menu-item"></ecm-skin>
      <span class="ecm-help">
        <router-link to="/user/userinfo">{{currentUser().userName}}</router-link>&nbsp;
      </span>

      <el-dropdown class="avatar-container right-menu-item" trigger="click">
        <div class="avatar-wrapper">
          <i class="el-icon-caret-bottom" style="font-size:18px"></i>
        </div>
        <el-dropdown-menu slot="dropdown">
          <router-link to="/">
            <el-dropdown-item>{{$t('route.home')}}</el-dropdown-item>
          </router-link>
          <el-dropdown-item divided>
            <span @click="logout" style="display:block;">{{$t('application.logout')}}</span>
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
      userName: ""
    };
  },
  mounted(){
    
  },
  computed: {
    ...mapGetters(['sidebar', 'name', 'avatar'])
    
  },
  methods: {
    toggleSideBar() {
      this.$store.dispatch('ToggleSideBar')
    },
    logout() {
      let _self = this;
      // _self.closeAllTags();
      axios.post("/userLogout","").then(function(response) {
        _self.$store.commit("DEL_ALL_VIEWS")
        var loca = window.location;
        if (loca.search != null && loca.search != "") {
          if (loca.search.substr(1, 9) == "LoginName") {
            var url = loca.origin + loca.pathname + "#/login";
            window.location.href = url;
          } else {
            _self.$router.push({ path: "/login" });
          }
        } else {
          _self.$router.push({ path: "/login" });
        }
      });
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
