<template>
  <div>
      <table border="0" width="100%">
          <tr>
            <td class="navbar">
              /发起工作流
            </td>
          </tr>
          <tr>
            <td>
              
            </td>
          </tr>
        <tr>
          <td>
            <el-form :model="form">
            <el-form-item label="设计者" :label-width="formLabelWidth">
              <el-input v-model="form.name" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="校对者" :label-width="formLabelWidth">
              <el-input v-model="form.name" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="工种负责人" :label-width="formLabelWidth">
              <el-input v-model="form.name" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="室负责人" :label-width="formLabelWidth">
              <el-input v-model="form.name" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="所负责人" :label-width="formLabelWidth">
              <el-input v-model="form.name" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="设计主持人" :label-width="formLabelWidth">
              <el-input v-model="form.name" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="总工程师" :label-width="formLabelWidth">
              <el-input v-model="form.name" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="工作流" :label-width="formLabelWidth">
              <el-select v-model="form.processId">
                  <el-option v-for="item in processData"
                  :label="item.name"
                  :key="item.id"
                  :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
            
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="startworkflow()">启动流程</el-button>
          </div>
          </td>
        </tr>
    </table>
  </div>
</template>

<script type="text/javascript">
// $.ajaxSetup({
//   contentType: "application/json"
// });

export default {
  name: "StartWorkflow",
  permit: 3,
  data() {
    return {
      inputkey: "",
      loading: false,
      processData: [],
      dialogVisible: false,
      tableHeight: window.innerHeight - 140,
      IDS:[],
      form: {
        name: "文件生效流程",
        description: "我的测试流程说明",
        processId: 1,
        docId:81,
        formId:84
      },
      formLabelWidth: "120px"
    };
  },
  props:["IDS"],
   created(){ 
    let _self = this;
    _self.loading = true;
    _self.axios({
      headers: {
        "Content-Type": "application/json;charset=UTF-8"
      },
      method: 'get',
      url: '/zisecm/workflow/getProcess'
    })
      .then(function(response) {
        _self.processData = response.data.data;
        _self.loading = false;
      })
      .catch(function(error) {
        console.log(error);
        _self.loading = false;
      });

    },
  methods: {
    startworkflow() {
      let _self = this;
      _self.loading = true;
      _self.axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        method: 'post',
        data: JSON.stringify(_self.form),
        url: '/zisecm/workflow/startWrokflow'
      })
      .then(function(response) {
        _self.$message("发起流程成功!");
        _self.loading = false;
      })
      .catch(function(error) {
        console.log(error);
        _self.loading = false;
      });
    },
    search() {
      let _self = this;
      // _self.dataList = _self.dataListFull.filter(function(item){
      //     return item.name.match(_self.inputkey) || item.description.match(_self.inputkey);
      //   }
      // );
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
