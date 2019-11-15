<template>
	<el-container>
		
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
			topmenuData:[]
		};
	},
	beforeCreate() {
		var user = sessionStorage.getItem('access-user');
		var userObj = JSON.parse(user);
		this.user = userObj;
		if (!user) {
			this.$router.push({path: '/login'});
		}
	},
	created(){
		this.init();
	},
	mounted() {	   
		let _self = this;
	
		var user = sessionStorage.getItem('access-user');
		var userObj = JSON.parse(user);
		this.user = userObj;

		
		this.$nextTick(function() {
			setInterval(this.checklogin, 1000);
		});
	},
	methods: {
		init:async function(){
			var _self = this;
			await axios.post("/memu/getMyMenu",new Map([["name","TopMenu"],["lang",_self.getLang()]])).then(res => {
				console.log("getMymenu");
				console.log(res);
				_self.topmenuData = [];
				var topmenuList = res.data.data.menuItems;
				topmenuList.forEach(element => {
					var pathObj = new Object();
					pathObj.path = element.url;
					var menu={
							id:element.id,
							path:pathObj,
							name:element.label
						}
					_self.topmenuData.push(menu);
				});
			});
		},
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
					this.locale.use(this.zhLocale);
				} else {
					this.locale.use(this.enLocale);
				}
				localStorage.setItem('localeLanguage', val);
				this.$router.go(0);
			}
		}
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
