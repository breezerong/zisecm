<template>
	<el-container>
	  <el-container>
	    <el-aside width="200px">
	      <div v-bind:style="{height: menuHeight +'px'}">
	      <!--左侧导航-->
	      <el-menu default-active="31" class="el-menu-vertical-ecm">
	        <el-submenu index="30">
	          <template slot="title">
	            <i class="el-icon-setting"></i>
	            <span>{{$t('menu.userCenter')}}</span>
	          </template>
	          <el-menu-item index="31">
	            <i class="el-icon-ecm-my_light"></i>
	            <span slot="title">
	              <router-link to="/user/userinfo">{{$t('menu.userInfo')}}</router-link>
	            </span>
	          </el-menu-item>
			  <el-menu-item index="32">
	            <i class="el-icon-ecm-group"></i>
	            <span slot="title">
	              <router-link to="/user/userroleinfo">{{$t('menu.userRoleInfo')}}</router-link>
	            </span>
	          </el-menu-item>
	          <el-menu-item index="33">
	            <i class="el-icon-lock"></i>
	            <span slot="title">
	              <router-link to="/user/changepassword">{{$t('menu.changePassword')}}</router-link>
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
  name: 'UserCenter',
  data() {
    return {
      username:'',
      menuHeight: window.innerHeight -64
    };
  },
   mounted(){
    //this.$router.push({ path: "/user/userinfo" });
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
