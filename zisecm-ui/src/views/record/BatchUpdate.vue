<template>
  <div>
    <div class="navbar">
      <el-breadcrumb>
        <el-breadcrumb-item>{{$t('menu.fileManage')}}</el-breadcrumb-item>
        <el-breadcrumb-item>{{$t('menu.batchUpdate')}}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div>
      <el-form label-width="120px" v-loading="loading" @submit.native.prevent>
        <el-row > 
          <el-col :span="24" style="float: left;text-align:left;">
            <br>
            &nbsp; &nbsp; 批量更新的Excel文件为库存档案Excel导出结果，修改需要更新属性值，删除不需要修改属性列（ID列不能删除）。
            <br>
            <br>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="4">
            <el-form-item label="Excel文件" style="float: left;">
              <el-upload
                :limit="1"
                :file-list="fileList"
                action
                :on-change="handleChange"
                :auto-upload="false"
                :multiple="false"
              >
                <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
              </el-upload>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-button type="primary" plain  icon="el-icon-upload2" @click="batchUpdate()">开始更新</el-button>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-input type="textarea" :rows="6" v-model="importMessage"></el-input>
          </el-col>
        </el-row>
      </el-form>
    </div>
  </div>
</template>
<script type="text/javascript">
export default {
  name: "BatchUpdate",
  permit: 1,
  data() {
    return {
      fileList:[],
      importMessage:"",
      loading: false
    };
  },
  methods: {
    handleChange(file, fileList) {
      this.fileList = fileList;
    },
    batchUpdate(){
      let _self = this;
      if (_self.fileList == null || _self.fileList.length == 0||_self.fileList[0].raw==null) {
        _self.$message("请选择导入Excel文件!");
        return;
      }
      let formdata = new FormData();
      let m = new Map();
      formdata.append("excel", _self.fileList[0].raw);
      _self.loading = true;
      axios
        .post("/tools/batchUpdate", formdata, {
          "Content-Type": "multipart/form-data"
        })
        .then(function(response) {
          _self.importMessage = response.data.data;
          _self.loading = false;
          _self.$message("更新成功!");
        })
        .catch(function(error) {
          _self.$message("更新失败!");
          console.log(error);
        });
    }
  }
};
</script>
<style scoped>
</style>