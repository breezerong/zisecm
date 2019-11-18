<template>
  <div>
    <el-form label-width="120px" @submit.native.prevent>
      <el-row>
         <el-col :span="8">
           取号测试
         </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="文档ID">
            <el-input type="text"  v-model="numData.id" ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="编码">
            <el-input type="text" v-model="numData.number"></el-input>
          </el-form-item>
        </el-col>
         <el-col :span="8">
           <el-button type="primary" plain icon="save" @click="newNumber()">取号</el-button> 
         </el-col>
      </el-row>
    </el-form>

    <el-form label-width="120px" @submit.native.prevent>
       <el-row>
         <el-col :span="8">
           目录测试
         </el-col>
      </el-row>
      <el-row>
        <el-col :span="6">
          <el-form-item label="文档ID">
            <el-input type="text"  v-model="pathData.id" ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="类型">
            <el-input type="text" v-model="pathData.type"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="路径">
            <el-input type="text" v-model="pathData.path"></el-input>
          </el-form-item>
        </el-col>
         <el-col :span="6">
           <el-button type="primary" plain icon="save" @click="getPath()">提交</el-button> 
         </el-col>
      </el-row>
    </el-form>
    <el-form label-width="120px" @submit.native.prevent>
       <el-row>
         <el-col :span="8">
           重建fullpath
         </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="文件夹ID">
            <el-input type="text"  v-model="fullData.id" ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="结果">
            <el-input type="text" v-model="fullData.result"></el-input>
          </el-form-item>
        </el-col>
         <el-col :span="8">
           <el-button type="primary" plain icon="save" @click="buildPath()">重建路径</el-button> 
         </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script type="text/javascript">

export default {
  name: "TestComp1",
  permit: 1,
  data() {
    return {
      numData:{
        id:"",
        number:""
      },
      pathData:{
        id:"",
        type:"3",
        path:""
      },
      fullData:{
        id:"",
        result:""
      },
      formLabelWidth: "120px"
    };
  },
  methods: {
    newNumber(){
      let _self = this;
      _self.loading =true;
      axios.get("/test/newNumber/"+_self.numData.id).then(function(response){
        console.log(response);
        _self.numData.number = response.data.data;	   
        _self.loading = false;
      }).catch(function(error){
        console.log(error);
        _self.loading = false;
      });
    },
    getPath(){
      let _self = this;
      _self.loading =true;
      axios.get("/test/getPath/"+_self.pathData.id+"/"+_self.pathData.type).then(function(response){
        console.log(response);
        _self.pathData.path = response.data.data;	   
        _self.loading = false;
      }).catch(function(error){
        console.log(error);
        _self.loading = false;
      });
    },
    buildPath(){
      let _self = this;
      _self.loading =true;
      axios.get("/test/builderPath/"+_self.fullData.id).then(function(response){
        _self.fullData.result = response.data.data;	   
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
