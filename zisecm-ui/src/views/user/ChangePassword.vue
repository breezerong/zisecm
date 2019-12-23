<template>
  <div>
     <div class="navbar">
      <el-breadcrumb>
        <el-breadcrumb-item>{{$t('menu.userCenter')}}</el-breadcrumb-item>
        <el-breadcrumb-item>{{$t('menu.changePassword')}}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div>&nbsp;</div>
    <el-form label-width="120px" @submit.native.prevent>
      <el-row>
        <el-col :span="8">
          <el-form-item :label="$t('application.user')">
            <el-input type="text"  v-model="userData.name" :disabled="true"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row v-if="loginType=='1'">
        <el-col :span="8">
          <el-form-item :label="$t('application.password')" :required="true">
            <el-input type="password" v-model="userData.password"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row v-if="loginType=='1'">
        <el-col :span="8">
          <el-form-item :label="$t('application.newPassword')" :required="true">
            <el-input type="password" v-model="userData.newPassword"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row v-if="loginType=='1'">
        <el-col :span="8">
          <el-form-item :label="$t('application.confirmPassword')" :required="true">
            <el-input type="password" v-model="userData.confirmPassword" ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col>
          &nbsp;
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="6">
          <el-button  v-if="loginType=='1'" type="primary" plain icon="save" @click="updatePassword()" >{{$t('application.save')}}</el-button> 
          <span v-if="loginType=='2'">LDAP {{$t('message.cannotChangePassword')}}</span>
          <span v-if="loginType=='3'">{{$t('application.loginType3') + ' ' + $t('message.cannotChangePassword')}}</span>
        </el-col>
      </el-row>
      
    </el-form>
  </div>
</template>

<script type="text/javascript">
export default {
  name: "ChangePassword",
  permit: 1,
  data() {
    return {
      loginType:"1",
      userData:{
        name:"",
        password:"",
        newPassword:"",
        confirmPassword:""
      }
    };
  },
   mounted(){ 
    let _self = this;
    _self.userData.name = sessionStorage.getItem('access-userName');
    axios.post("/user/getUserByName",JSON.stringify(_self.userData.name)).then(function(response){
       // console.log(JSON.stringify(response.data.data));
        _self.loginType = response.data.data.loginType;
        
        _self.loading = false;
      })
      .catch(function(error){
        console.log(error);
        _self.loading = false;
      });
    },
  methods: {
    updatePassword(){
      let _self = this;
      if(_self.userData.newPassword != _self.userData.confirmPassword){
         _self.$message({
           type: 'error',
           message: _self.$t('message.differentPassword')
           });
         return;
      }
      axios.post("/user/updatePassword",JSON.stringify(_self.userData))
      .then(function(response){
        //console.log(JSON.stringify(response.data.data));
        //_self.userData = response.data.data;
        if(response.data.code == 1){
          _self.$message(_self.$t('message.saveSuccess'));
        }
        else{
         _self.$message(_self.$t('message.saveFailured'));
        }
        _self.loading = false;
      })
      .catch(function(error){
        console.log(error);
        _self.loading = false;
      });
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1,
h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
