<template>
  <div>
    <div class="navbar">
      <el-breadcrumb>
        <el-breadcrumb-item>{{$t('route.fileManage')}}</el-breadcrumb-item>
        <el-breadcrumb-item>服务端批量导入</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
      <el-form label-width="120px" v-loading="loading" @submit.native.prevent>
        <el-row>
          <el-col :span="12">
                <el-form-item label="扫描根文件夹" style="float: left;">
                  <el-input type="text" v-model="scanFolder" ></el-input>
                </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
                <el-form-item label="每个移交单文件夹数量" style="float: left;">
                  <el-input type="text" v-model="folderCount" ></el-input>
                </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-input type="textarea" :rows="6" v-model="importMessage"></el-input>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-button type="primary" plain icon="el-icon-upload2" @click="batchImport()">{{$t('application.start')+$t('application.Import')}}</el-button>
          </el-col>
        </el-row>
      </el-form>
  </div>
</template>
<script type="text/javascript">
export default {
  name: "BatchServerImport",
  permit: 1,
  data() {
    return {
      loading:false,
      scanFolder:"d:\import",
      folderCount:5
    };
  },
  methods: {
   
    batchImport() {
      let _self = this;
      var m = new Map();
      m.set("scanFolder",_self.scanFolder);
      m.set("folderCount",_self.folderCount);
      _self.loading = true;
      axios
        .post("/tools/importOnServer",JSON.stringify(m))
        .then(function(response) {
          _self.importMessage = response.data.data;
          _self.loading = false;
          _self.$message(_self.$t('application.Import')+_self.$t('message.success'));
        })
        .catch(function(error) {
          _self.$message(_self.$t('application.importFailed'));
          console.log(error);
        });
    }
  }
};
</script>
<style scoped>
</style>