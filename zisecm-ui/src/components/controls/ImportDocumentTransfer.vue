<template>
  <el-form label-width="120px" v-loading="loading" @submit.native.prevent>
    <el-row>
      <el-col :span="8">
        <el-form-item :label="$t('application.Import')+$t('message.template')">
          <el-select v-model="selectedTemplate">
            <div v-for="(item,idx) in templateData" :key="idx">
              <el-option :label="item.NAME" :value="item.ID" :key="idx+10"></el-option>
            </div>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="16" style="text-align:left">
        <el-button type="primary" plain icon="el-icon-download" @click="downloadTemplate()">{{$t('application.download')+$t('message.template')}}</el-button>
      
        <el-button type="primary" plain icon="el-icon-upload2" @click="batchImport()">{{$t('application.start')+$t('application.Import')}}</el-button>
        &nbsp; &nbsp;
         <el-button plain type="primary" @click="cleanFiles()">{{$t('message.ClearFiles')}}</el-button>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="20">
        <el-progress :text-inside="true" :stroke-width="24" :percentage="progressNum" status="success"></el-progress>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="10">
        <el-form-item :label="'Excel'+$t('message.file')" style="float: left;">
          <el-upload
            :limit="1"
            :file-list="fileList1"
            action
            :on-change="handleChange1"
            :auto-upload="false"
            :multiple="false"
          >
          &nbsp; &nbsp;
            <el-button slot="trigger" size="small" type="primary">{{$t('application.selectFile')}}</el-button>
          </el-upload>
          
        </el-form-item>
      </el-col>
      <el-col :span="14">
        <el-form-item :label="'Excel'+$t('message.ElectronicFiles')" style="float: left;">
          <el-container >
          <el-upload
            :limit="400"
            :file-list="fileList2"
            action
            :on-change="handleChange2"
            :auto-upload="false"
            :multiple="true"
            style="width:400px;height:360px;overflow:scroll;"
          >
          &nbsp; &nbsp;
            <el-button slot="trigger" size="small" type="primary">{{$t('application.selectFile')}}</el-button>
          </el-upload>
         </el-container>
        </el-form-item>
      </el-col>
      
    </el-row>

    <el-row>
      <el-col>
        <el-input type="textarea" :rows="6" v-model="importMessage"></el-input>
      </el-col>
    </el-row>
  </el-form>
</template>

<script type="text/javascript">
export default {
  name: "ImportDocument",
  data() {
    return {
      loading: false,
      fileList1: [],
      fileList2: [],
      importMessage: "",
      templateData:[],
      selectedTemplate:"",
      formLabelWidth: "120px",
      progressNum:0
    };
  },
  mounted() {
    this.loadTemplate();
    this.progressNum=0;
  },
  props: {
    deliveryId: { type: [String], required: true }
  },
  methods: {
    loadTemplate(){
      let _self = this;
      _self.loading = true;
      axios.get("/import/getImportTemplates").then(function(response) {
          _self.templateData = response.data.data;
          _self.loading = false;
        })
        .catch(function(error) {
          _self.$message(_self.$t('application.LoadTemplateFailed'));
          console.log(error);
        });
    },
    downloadTemplate(){
      let _self = this;
      // if(_self.deliveryId==null || _self.deliveryId.length==0){
      //   _self.$message("请选择移交单导入!");
      //   return;
      // }
      if(_self.selectedTemplate==null || _self.selectedTemplate.length==0){
        _self.$message(_self.$t('application.PleaseSelectTemplate'));
        return;
      }
      // 拦截器会自动替换成目标url
      let url = this.axios.defaults.baseURL+"/dc/getContent?id="+_self.selectedTemplate+"&token="+sessionStorage.getItem('access-token');
      window.open(url);
    },
    handleChange1(file, fileList) {
      this.fileList1 = fileList;
    },
    handleChange2(file, fileList) {
      this.fileList2 = fileList;
    },
    batchImport() {
      let _self = this;
      if (_self.fileList1 == null || _self.fileList1.length == 0||_self.fileList1[0].raw==null) {
         _self.$message(_self.$t('application.PleaseSelect'));
        return;
      }
      // if(_self.deliveryId==null || _self.deliveryId.length==0){
      //    _self.$message("请选择移交单导入!");
      //   return;
      // }
      let formdata = new FormData();
      let m = new Map();
      // m.set("id", _self.deliveryId);
      formdata.append("metaData", JSON.stringify(m));
      formdata.append("excel", _self.fileList1[0].raw);
      _self.fileList2.forEach(function(file) {
        formdata.append("files", file.raw, file.name);
      });
      // _self.loading = true;
      axios
        .post("/import/batchImport", formdata, {headers:{
          "Content-Type": "multipart/form-data"
        },
        onUploadProgress: progressEvent => {
            _self.progressNum=(progressEvent.loaded / progressEvent.total * 100).toFixed(0) //调用onProgress方法来显示进度条，需要传递个对象 percent为进度值
            
        }
        })
        .then(function(response) {
          _self.importMessage = response.data.data;
          // _self.loading = false;
          _self.$message(_self.$t('application.Import')+_self.$t('message.success'));
          _self.cleanFiles();
          _self.$emit("onImported");
          
        })
        .catch(function(error) {
          _self.$message(_self.$t('application.importFailed'));
          console.log(error);
        });
    },
    cleanFiles(){
      this.fileList1 = [];
      this.fileList2 = [];
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
/* ul {
  list-style-type: none;
  padding: 0;
 
} */
/* ul.el-upload-list{
   height: 200px;
  overflow: scroll;
} */
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
.el-upload-list {
  
    margin: 0;
    padding: 0;
    list-style: none;
    max-height: 300px !important;
    overflow: scroll !important;
    
}

</style>

