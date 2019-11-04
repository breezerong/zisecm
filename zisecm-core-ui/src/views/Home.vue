<template>
	<el-container>
		<el-header style="height:44px" v-show="userName != ''">
		  <div class="navbar-top" >
		    <div class="navbar-top-inner">
		      <div class="container-top">
		        <img src="@/assets/ecmlogo.png" border="0">
		      </div>
		      <div class="container-top">
		        <span style="font-size: 18px;color: #fff;">{{$t('application.name')}}</span>
		      </div>
		      <div class="container-top">
		        <el-menu default-active="1" mode="horizontal">
		          	<el-menu-item index="1"><router-link to="/home">{{$t('menu.home')}}</router-link></el-menu-item>
		            <el-menu-item index="4" v-if="clientPermission>4"><router-link to="/datacenter/folder">{{$t('menu.dataCenter')}}</router-link></el-menu-item>
		            <el-menu-item index="5"><router-link to="/reportcenter">{{$t('menu.reportCenter')}}</router-link></el-menu-item>
		            <el-menu-item index="6" v-if="clientPermission>3"><router-link to="/drawingmanager">{{$t('menu.systemManager')}}</router-link></el-menu-item>
		            <el-menu-item index="7" v-if="clientPermission>4"><router-link to="/managercenter">{{$t('menu.managerCenter')}}</router-link></el-menu-item>
		            <el-menu-item index="8"><router-link to="/usercenter">{{$t('menu.userCenter')}}</router-link></el-menu-item>
		            <el-menu-item index="9"><router-link to="/helpcenter">{{$t('menu.helpCenter')}}</router-link></el-menu-item>
					<template v-for="item in topmenu">
						 <el-menu-item v-bind:key="item.id" :index="item.id"><router-link :to="item.name">{{item.label}}</router-link></el-menu-item>
					</template>
		        </el-menu>
		      </div>
		
		        <div v-if="clientPermission>4" class="container-top-right">
		          <el-select v-model="currentLanguage" @change="languageChange" style="width:105px">
		            <el-option label="简体中文" value="zh-cn" key="zh-cn"></el-option>
		            <el-option label="English" value="en" key="en"></el-option>
		          </el-select>
		        </div>
		        <div class="container-top-right">
		          <img :src="'img/head128.jpg'" class="img-head">
		          <span ><router-link to="/usercenter" style="color:#fff;">{{userName}} </router-link>&nbsp;</span>
		          <i class="el-icon-switch-button" @click="logout" :title="$t('application.logout')"></i>
		        </div>
		    </div>
		  </div>
		</el-header>
		<el-main>
			<transition name="fade" mode="out-in"><router-view></router-view></transition>
		</el-main>
	</el-container>
</template>

<script>
export default {
	data() {
		return {
			currentLanguage: '',
			userName: '',
			clientPermission: 0,
			defaultColor: '#409EFF',
			menuHeight: window.innerHeight - 70,
			user: {},
			topmenu:[]
		};
	},
	beforeCreate() {
		var user = sessionStorage.getItem('access-user');
		var userObj = JSON.parse(user);
		this.user = userObj;
		console.log(this.user);
		if (!user) {
			//this.$router.push({path: '/login'});
		}
	},
	
	mounted() {
		let _self = this;
		console.log("GET MESSAGE");
		console.log(this.$store.state.workflow)
		var url="/admin/getMenu";
		axios.post(url).then(resp=>{
			console.log("getMenu");
			
			_self.topmenu = resp.data.data;
			console.log(_self.topmenu);
		});
		var user = sessionStorage.getItem('access-user');
		var userObj = JSON.parse(user);
		this.user = userObj;

		this.currentLanguage = localStorage.getItem('localeLanguage') || 'zh-cn';
		this.$nextTick(function() {
			setInterval(this.checklogin, 1000);
		});
	},
	methods: {
		logout() {
		  sessionStorage.removeItem('access-user');
		  sessionStorage.removeItem('access-userName');
		  this.$router.push({path: '/login'});
		},
		checklogin() {
			var user = sessionStorage.getItem('access-userName');
			if (user) {
				this.userName = sessionStorage.getItem('access-userName');
				this.clientPermission = sessionStorage.getItem('access-clientPermission');
			} else {
				this.userName = '';
			}
		},
		languageChange(val) {
			var lang = localStorage.getItem('localeLanguage') || 'zh-cn';
			if (lang != val) {
				this.$i18n.local = val;
				if (lang === 'zh-cn') {
					locale.use(zhLocale);
				} else {
					locale.use(enLocale);
				}
				localStorage.setItem('localeLanguage', val);
				this.$router.go(0);
			}
		}
	}
};
</script>

<style scoped>
body > .el-container {
	padding: 0px;
	margin-top: 0px;
	margin-left: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.container-top-right {
	height: 40px;
	padding-left: 10px;
	padding-right: 10px;
	white-space: nowrap;
	float: right;
	display: flex;
	align-items: center;
	justify-content: center;
}

.el-header {
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
	background-color: #d3dce6;
	color: #333;
	text-align: center;
	padding: 0px;
	margin-top: 0px;
	margin-left: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}

.el-main {
	background-color: #e9eef3;
	color: #333;
	text-align: center;
	padding: 0px;
	margin-top: 0px;
	margin-left: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
</style>
