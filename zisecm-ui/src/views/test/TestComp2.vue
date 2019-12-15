<template>
  <div>
    <el-row>
      <el-button type="primary" plain icon="save" @click="updateDept()">更新部门</el-button> 
    </el-row>
    <el-row>
      <el-input type="textarea" :rows="6" v-model="optionMessage"></el-input>
    </el-row>
  </div>
</template>

<script type="text/javascript">

export default {
  name: "TestComp2",
  permit: 1,
  data() {
    return {
      loading:false,
      optionMessage:"",
      wfData: {
        formId:""
      },
      formLabelWidth: "120px"
    };
  },
  methods: {
    updateDept(){
      let _self = this;
      _self.loading =true;
      axios.post("/tools/updateDepartment").then(function(response){
        _self.optionMessage = response.data.data;
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
