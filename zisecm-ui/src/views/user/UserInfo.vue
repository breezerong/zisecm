<template>
  <div>
    <el-dialog :title="$t('application.updateSignImage')" :visible.sync="sdialogVisible">
          <el-form label-position="right" label-width="100px">
            <el-row>
              <el-col :span="24">
                <el-form-item :label="$t('application.signImage')">
                  {{$t('message.signImageSize')}}
                  <el-upload
                    :limit="1"
                    :file-list="fileList" 
                    action=""
                    :on-change="handleChange"
                    :auto-upload="false"
                    :multiple="false">
                    <el-button slot="trigger" size="small" type="primary">{{$t('application.selectFile')}}</el-button>
                  </el-upload>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">{{$t('application.cancel')}}</el-button>
            <el-button type="primary" @click="updateSignImage()">{{$t('application.ok')}}</el-button>
          </div>
        </el-dialog>
    <el-form label-width="120px" @submit.native.prevent>
      <el-row>
        <el-col :span="8">
          <el-form-item :label="$t('application.user')">
            <el-input type="text"  v-model="userData.name" :disabled="disabledVal"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item :label="$t('application.loginUserName')">
            <el-input type="text" v-model="userData.loginName" :disabled="disabledVal"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item :label="$t('application.phone')">
            <el-input type="text" v-model="userData.phone"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item :label="$t('application.email')">
            <el-input type="text" v-model="userData.email"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item :label="$t('application.description')">
            <el-input type="text" v-model="userData.description"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <!-- <el-row>
        <el-col :span="2">
          <el-form-item :label="$t('application.signImage')">
            <img v-if="userData.id" :src="_self.axios.defaults.baseURL+'/user/getUserImage?id='+userData.id+'&token='+token+'&ticket='+ticket" >
          </el-form-item>
        </el-col>
      </el-row>
      <el-row> 
        <el-col>
          &nbsp;
        </el-col>
      </el-row>-->
      <el-row>
        <el-col :span="2">
          &nbsp;
        </el-col>
        <el-col :span="14">
          <el-button type="primary" plain icon="save" @click="saveUserInfo()" >{{$t('application.save')}}</el-button> 
          <!-- &nbsp; &nbsp; 
          <el-button type="primary" plain icon="save" @click="sdialogVisible=true" >{{$t('application.updateSignImage')}}</el-button>  -->
        </el-col>
      </el-row>
      
    </el-form>
     
  </div>
  </div>
</template>

<script type="text/javascript">
export default {
  name: "UserInfo",
  permit: 1,
  data() {
    return {
      disabledVal: true,
      sdialogVisible:false,
      userData: {},
      loading: false,
      file:"",
      fileList:[],
      userName: "",
      token:"",
      ticket:12345
    };
  },
  mounted(){
    console.log(this.$route.name);
	let _self = this;
	_self.ticket = Math.floor(Math.random() * 1000);
	_self.userName = sessionStorage.getItem('access-userName');
	_self.token = sessionStorage.getItem('access-token');
	axios.post("/user/getUserByName",JSON.stringify(_self.userName)).then(function(response){
		console.log(response);
		_self.userData = response.data.data;	   
		_self.loading = false;
	}).catch(function(error){
		console.log(error);
		_self.loading = false;
	});   
  },
  methods: {
    handleChange(file, fileList){
      this.file = file;
    },
    updateSignImage() {
      let _self = this;
        let formdata = new FormData();
        formdata.append("id",JSON.stringify(_self.userData.id));
        console.log(_self.userData.id);
        if(_self.file!=""){
          //console.log(_self.file);
          formdata.append("uploadFile",_self.file.raw);
        }
		
		axios.post("/user/updateSignImage",formdata).then(function(response){
		   _self.sdialogVisible = false;
		   _self.ticket = Math.floor(Math.random() * 1000);
		   _self.$message(_self.$t('message.saveSuccess'));
		   _self.refreshData();
		}).catch(function(error){
		   console.log(error);
		});
		
    },
    saveUserInfo(){
      let _self = this;
	  axios.post("/user/updateUser",JSON.stringify(_self.userData)).then(function(response){
        if(response.data.code == 1){
          _self.$message(_self.$t('message.saveSuccess'));
        }else{
         _self.$message(_self.$t('message.saveFailured'));
        }
        _self.loading = false;
      }).catch(function(error){
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
