<template>
  <div>
      <table border="0" width="100%">
          <tr>
            <td>
              
            </td>
          </tr>
        <tr>
          <td>
            <el-form :model="form">
            <el-form-item label="名称" :label-width="formLabelWidth">
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
            <el-form-item label="文件Id" :label-width="formLabelWidth">
              <el-input v-model="form.docId" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="表单Id" :label-width="formLabelWidth">
              <el-input v-model="form.formId" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="说明" :label-width="formLabelWidth">
              <el-input v-model="form.description" auto-complete="off"></el-input>
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
      form: {
        name: "编校审批",
        description: "我的测试流程说明",
        processId: "BianJiaoShenPi",
        docId:"e757fa37dad74f5fb77eb32a9acd06c1",
        formId:"383036d87ddd4dddb79b83fb05372c36"
      },
      formLabelWidth: "120px"
    };
  },
   created(){ 
    let _self = this;
    _self.loading = true;
    axios.get('/zisecm/workflow/getProcess')
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
      axios.post('/workflow/startWorkflow',JSON.stringify(_self.form))
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
