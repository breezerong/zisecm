<template>
  <el-form
    ref="AccountFrom"
    v-loading="loading"
    :model="account"
    :rules="rules"
    class="login-container"
  >
    <el-form-item>
      <h2 class="title">
        <img src="/static/img/logo.png" border="0" />&nbsp;{{$t("application.name")}}<br/>
        {{$t("application.user")+$t("application.login")}}
      </h2>
    </el-form-item>
    <el-form-item prop="username">
      <el-input
        type="text"
        v-model="account.username"
        ref="username"
        @keyup.enter.native="handleLogin"
        :placeholder="$t('application.user')"
      ></el-input>
    </el-form-item>
    <el-form-item prop="pwd">
      <el-input
        type="password"
        v-model="account.password"
        @keyup.enter.native="handleLogin"
        :placeholder="$t('application.password')"
      ></el-input>
    </el-form-item>
    <el-checkbox v-model="rememberInfo" checked class="remember">记住密码</el-checkbox>
    <el-form-item style="width:100%;">
      <el-button
        type="primary"
        style="width:100%;"
        @click.native.prevent="handleLogin"
        :loading="loading"
      >{{$t('application.login')}}</el-button>
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
      checked: true
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
body {
  background: #dfe9fb;
}
</style>
<style scoped>
.login-container {
  /*box-shadow: 0 0px 8px 0 rgba(0, 0, 0, 0.06), 0 1px 0px 0 rgba(0, 0, 0, 0.02);*/
  -webkit-border-radius: 5px;
  border-radius: 5px;
  -moz-border-radius: 5px;
  background-clip: padding-box;
  margin: 100px auto;
  width: 350px;
  padding: 35px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;

  background: -ms-linear-gradient(top, #ace, #00c1de); /* IE 10 */
  background: -moz-linear-gradient(top, #ace, #00c1de); /*火狐*/
  background: -webkit-gradient(
    linear,
    0% 0%,
    0% 100%,
    from(#ace),
    to(#00c1de)
  ); /*谷歌*/
  background: -webkit-linear-gradient(
    top,
    #ace,
    #00c1de
  ); /*Safari5.1 Chrome 10+*/
  background: -o-linear-gradient(top, #ace, #00c1de); /*Opera 11.10+*/
}
.title {
  margin: 0px auto 10px auto;
  text-align: center;
  color: #505458;
}
.remember {
  margin: 0px 0px 35px 0px;
}
</style>
