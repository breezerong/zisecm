<template>
  <el-form ref="AccountFrom" :model="account" :rules="rules" label-position="left" label-width="0px"
           class="login-container">
    <table border="0" width="100%">
      <tr>
        <td nowrap>
          <img src="../assets/logo.png" border="0"><h3 class="title">{{$t('application.name')}}</h3>
        </td>
      </tr>
      <tr>
        <td>
    <el-form-item prop="username">
      <el-input style="width:18em" type="text" v-model="account.username" auto-complete="off" :placeholder="$t('application.user')"></el-input>
    </el-form-item>
     </td>
      </tr>
      <tr>
        <td>
        <el-form-item prop="pwd">
          <el-input style="width:18em" type="password" v-model="account.password" auto-complete="off" @change="handleLogin" :placeholder="$t('application.password')"></el-input>
        </el-form-item>
     </td>
      </tr>
      <tr>
        <td>
            <el-checkbox v-model="rememberInfo" checked class="remember">记住密码</el-checkbox>
        </td>
      </tr>
      <tr>
        <td>
 
    <el-form-item style="width:100%;">
      <el-button type="primary" style="width:100%;" @click.native.prevent="handleLogin" :loading="loading">{{$t('application.login')}}</el-button>
    </el-form-item>
     </td>
      </tr>
    </table>

  </el-form>
</template>

<script>

export default {
 data() {
    return {
      loading: false,
      rememberInfo:false,
      account: {
        username: '',
        password: ''
      },
      rules: {
        username: [
          {required: true, message: this.$t("message.pleaseInput")+this.$t("application.user"), trigger: 'blur'},
            // { validator: validaePass }
        ],
        password: [
          {required: true, message: this.$t("message.pleaseInput")+this.$t("application.password"), trigger: 'blur'},
            // { validator: validaePass2 }
        ]
      },
      checked: true
    };
  },
  mounted(){
    var reinfo = localStorage.getItem("ziecm-rememberInfo");
    if(reinfo)
    {
      this.rememberInfo = reinfo=='1';
      this.account.username=localStorage.getItem("ziecm-ass12bn");
      this.account.password=localStorage.getItem("ziecm-ass12bp");
    }
  },
  methods: {
    handleLogin() {
      let _self = this;
      var tocomp = _self.$route.query.redirect;
      if(!tocomp){
        tocomp = "/";
      }
      _self.$refs.AccountFrom.validate((valid) => {
        if (valid) {
          _self.loading = true;
          _self.axios({
            headers: {
              "Content-Type": "application/json;charset=UTF-8"
            },
            method: 'post',
            data: JSON.stringify(_self.account),
            url: '/zisecm/userLogin'
          })
          .then(function(response) {
            //console.log(response.data);
            if(response.data.code==1)
            {
              sessionStorage.setItem('access-user',JSON.stringify(_self.account));
              sessionStorage.setItem('access-userName',response.data.userName);
              sessionStorage.setItem('access-token',response.data.token);
              sessionStorage.setItem('access-clientPermission',response.data.clientPermission);
              sessionStorage.setItem('access-systemPermission',response.data.systemPermission);
              if(_self.rememberInfo){
                localStorage.setItem("ziecm-rememberInfo","1");
                localStorage.setItem("ziecm-ass12bn",_self.account.username);
                localStorage.setItem("ziecm-ass12bp",_self.account.password);
              }else{
                localStorage.removeItem("ziecm-rememberInfo");
                localStorage.removeItem("ziecm-ass12bn");
                localStorage.removeItem("ziecm-ass12bp");
              }
              _self.$router.push({path: tocomp});
            }
            else
            {
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
}
</script>
<style>
  body {
    background: #DFE9FB;
  }
</style>
<style scoped>
  .login-container {
    /*box-shadow: 0 0px 8px 0 rgba(0, 0, 0, 0.06), 0 1px 0px 0 rgba(0, 0, 0, 0.02);*/
    -webkit-border-radius: 5px;
    border-radius: 5px;
    -moz-border-radius: 5px;
    background-clip: padding-box;
    margin: 160px auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;

    background: -ms-linear-gradient(top, #ace, #00C1DE); /* IE 10 */
    background: -moz-linear-gradient(top, #ace, #00C1DE); /*火狐*/
    background: -webkit-gradient(linear, 0% 0%, 0% 100%, from(#ace), to(#00C1DE)); /*谷歌*/
    background: -webkit-linear-gradient(top, #ace, #00C1DE); /*Safari5.1 Chrome 10+*/
    background: -o-linear-gradient(top,#ace, #00C1DE); /*Opera 11.10+*/

    .title {
      margin: 0px auto 40px auto;
      text-align: center;
      color: #505458;
    }
    .remember {
      margin: 0px 0px 35px 0px;
    }
  }
</style>
