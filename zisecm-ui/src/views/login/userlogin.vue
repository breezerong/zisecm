<template>
  <el-form class="login-form" status-icon  ref="AccountFrom" :model="account" label-width="0">
    <el-form-item prop="username">
      <el-input size="small" ref="username" @keyup.enter.native="handleLogin" v-model="account.username" auto-complete="off" placeholder="请输入用户名">
        <i slot="prefix" class="icon-yonghu"></i>
      </el-input>
    </el-form-item>
    <el-form-item prop="password">
      <el-input size="small" @keyup.enter.native="handleLogin" :type="passwordType" v-model="account.password" auto-complete="off" placeholder="请输入密码">
        <i class="el-icon-view el-input__icon" slot="suffix"></i>
        <i slot="prefix" class="icon-mima"></i>
      </el-input>
    </el-form-item>
    <el-checkbox v-model="checked">记住账号</el-checkbox>
    <el-form-item>
      <el-button type="primary" size="small" @click.native.prevent="handleLogin" class="login-submit">登录</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
export default {
  data() {
    return {
      loading: false,
      rememberInfo: false,
      account: {
        username: "",
        password: ""
      },
      rules: {
        username: [
          {
            required: true,
            message:
              this.$t("message.pleaseInput") + this.$t("application.user"),
            trigger: "blur"
          }
          // { validator: validaePass }
        ],
        password: [
          {
            required: true,
            message:
              this.$t("message.pleaseInput") + this.$t("application.password"),
            trigger: "blur"
          }
          // { validator: validaePass2 }
        ]
      },
      checked: true,
      passwordType: 'password'
    };
  },
  created() {
    // let loginName = this.$route.query.LoginName;
    
    // if(!loginName){
    //   loginName = this.getValue(window.location.href, "LoginName");
    // }
    // console.log("loginname:"+loginName);
    // if (loginName) {
    //   this.loginSSO(loginName);
    // }
  },
  mounted() {
    var reinfo = localStorage.getItem("ziecm-rememberInfo");
    if (reinfo) {
      this.rememberInfo = reinfo == "1";
      this.account.username = localStorage.getItem("ziecm-ass12bn");
      this.account.password = localStorage.getItem("ziecm-ass12bp");
    }
    this.$refs.username.focus();
  },
  methods: {
    getValue(str, name) {
      var reg = new RegExp("(^|&|\\?)" + name + "=([^#]*)(&|$|#)"),
        r;
      if ((r = str.match(reg))) return unescape(r[2]);
      return null;
    },
    handleLogin() {
      let _self = this;
      var tocomp = _self.$route.query.redirect;
      if (!tocomp) {
        tocomp = "/";
      }
      _self.$refs.AccountFrom.validate(valid => {
        if (valid) {
          _self.loading = true;
          axios
            .post("/userLogin", JSON.stringify(_self.account))
            .then(function(response) {
              //console.log(response.data);
              if (response.data.code == 1) {
                sessionStorage.setItem(
                  "access-user",
                  JSON.stringify(_self.account)
                );
                sessionStorage.setItem(
                  "access-userName",
                  response.data.userName
                );
                sessionStorage.setItem(
                  "access-department",
                  response.data.department
                );
                sessionStorage.setItem("access-token", response.data.token);
                sessionStorage.setItem(
                  "access-clientPermission",
                  response.data.clientPermission
                );
                sessionStorage.setItem(
                  "access-systemPermission",
                  response.data.systemPermission
                );
                if (_self.rememberInfo) {
                  localStorage.setItem("ziecm-rememberInfo", "1");
                  localStorage.setItem("ziecm-ass12bn", _self.account.username);
                  localStorage.setItem("ziecm-ass12bp", _self.account.password);
                } else {
                  localStorage.removeItem("ziecm-rememberInfo");
                  localStorage.removeItem("ziecm-ass12bn");
                  localStorage.removeItem("ziecm-ass12bp");
                }
                sessionStorage.setItem(
                  "access-externalUser",
                  response.data.externalUser
                );
                _self.$router.push({ path: tocomp });
              } else {
                _self.$message(_self.$t("message.loginFailured"));
              }
              _self.loading = false;
            })
            .catch(function(error) {
              console.log(error);
              _self.$message(_self.$t("message.loginFailured"));
              _self.loading = false;
            });
        }
      });
    }
  }
};
</script>
<style>
</style>
