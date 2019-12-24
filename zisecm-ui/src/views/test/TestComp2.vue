<template>
  <div  class="components-container">
    <el-row>
      <el-button type="primary" plain icon="save" @click="updateDept()">更新部门</el-button> 
    </el-row>
    <el-row>
      <el-input type="textarea" :rows="6" v-model="optionMessage"></el-input>
    </el-row>
    <el-row>
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
           <el-button type="primary" plain icon="save" @click="deployProcess()">发布流程</el-button> 
           <el-button type="primary" plain icon="save" @click="startWorkflow()">启动流程</el-button> 
           <el-button type="primary" plain icon="save" @click="testWorkflow()">完成任务</el-button> 
         </el-col>
      </el-row>
    </el-form>
    </el-row>
    <split-pane split="vertical" @resize="resize">
      <template slot="paneL">
        <div class="left-container" />
      </template>
      <template slot="paneR">
        <split-pane split="horizontal">
          <template slot="paneL">
            <div class="top-container" />
          </template>
          <template slot="paneR">
            <div class="bottom-container" />
          </template>
        </split-pane>
      </template>
    </split-pane>
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
     resize() {
      console.log('resize')
    },
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
    },
    startWorkflow(){
      let _self = this;
      _self.loading =true;
      let m = new  Map();
      m.set("formId",_self.wfData.formId);
      axios.post("/workflow/startWorkflow",JSON.stringify(m)).then(function(response){
        console.log(response);  
        _self.loading = false;
      }).catch(function(error){
        console.log(error);
        _self.loading = false;
      });
    },
    testWorkflow(){
      let _self = this;
      _self.loading =true;
      let m = new  Map();
      m.set("formId",_self.wfData.formId);
      axios.post("/workflow/testWorkflow",JSON.stringify(m)).then(function(response){
        console.log(response);  
        _self.loading = false;
      }).catch(function(error){
        console.log(error);
        _self.loading = false;
      });
    },
      deployProcess(){
      let _self = this;
      _self.loading =true;
      let m = new  Map();
      m.set("formId",_self.wfData.formId);
      axios.post("/workflow/deploymentProcess",JSON.stringify(m)).then(function(response){
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

.components-container {
    position: relative;
    height: 100vh;
  }

  .left-container {
    background-color: #F38181;
    height: 100%;
  }

  .right-container {
    background-color: #FCE38A;
    height: 200px;
  }

  .top-container {
    background-color: #FCE38A;
    width: 100%;
    height: 100%;
  }

  .bottom-container {
    width: 100%;
    background-color: #95E1D3;
    height: 100%;
  }
</style>
