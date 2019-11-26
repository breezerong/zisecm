<template>
  <div>
    测试2
    <el-form label-width="120px" @submit.native.prevent>
      <el-row>
         <el-col :span="8">
           起流程
         </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="表单">
            <el-input type="text"  v-model="wfData.formId" ></el-input>
          </el-form-item>
        </el-col>
         <el-col :span="8">
           <el-button type="primary" plain icon="save" @click="startWorkflow()">启动</el-button> 
         </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script type="text/javascript">

export default {
  name: "TestComp2",
  permit: 1,
  data() {
    return {
      wfData: {
        formId:""
      },
      formLabelWidth: "120px"
    };
  },
  methods: {
    startWorkflow(){
      let _self = this;
      _self.loading =true;
      let m = new  Map();
      m.set("processId",_self.wfData.formId);
      m.set("name","2");
      axios.post("/workflow/startWrokflow",JSON.stringify(m)).then(function(response){
        console.log(response);  
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
