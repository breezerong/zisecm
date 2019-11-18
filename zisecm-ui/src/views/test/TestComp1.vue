<template>
  <div>
    <el-form label-width="120px" @submit.native.prevent>
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
