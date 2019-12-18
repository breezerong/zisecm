<template>
  <div>
    <div class="navbar">
      <el-breadcrumb>
        <el-breadcrumb-item>{{$t('menu.fileManage')}}</el-breadcrumb-item>
        <el-breadcrumb-item>{{$t('menu.batchMount')}}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div>
      <el-form label-width="120px" v-loading="loading" @submit.native.prevent>
        <el-tabs v-model="activeName" type="border-card">
          <el-tab-pane label="根据Excel挂载" name="first">
            <el-row>
              <div style="float: left;text-align:left;">
                &nbsp; &nbsp; 批量挂载的Excel文件为库存档案Excel导出结果，第二列填写挂载电子文件名。
                <br />
                <br />
              </div>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="Excel文件" style="float: left;">
                  <el-upload
                    :limit="1"
                    :file-list="fileList1"
                    action
                    :on-change="handleChange1"
                    :auto-upload="false"
                    :multiple="false"
                  >
                    <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
                  </el-upload>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="电子文件" style="float: left;">
                  <el-upload
                    :limit="100"
                    :file-list="fileList2"
                    action
                    :on-change="handleChange2"
                    :auto-upload="false"
                    :multiple="true"
                  >
                    <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
                  </el-upload>
                </el-form-item>
              </el-col>
              <el-col :span="8" style="float: left;text-align:left;">
                <el-button type="primary" plain icon="el-icon-upload2" @click="batchMount1()">开始挂载</el-button>
                <el-button plain type="primary" @click="cleanFiles1()">清除所有文件</el-button>
              </el-col>
            </el-row>
          </el-tab-pane>
          <el-tab-pane label="根据名称挂载" name="second">
            <el-row>
              <div style="float: left;text-align:left;">
                &nbsp; &nbsp; 根据文件名挂载只支持图纸文件挂载，命名规则：编码@版本@标题，如：1188XGASBS01@A@设计平面图.pdf,1188XGASBS01@A.pdf。
                <br />
                <br />
              </div>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="电子文件" style="float: left;">
                  <el-upload
                    :limit="100"
                    :file-list="fileList3"
                    action
                    :on-change="handleChange3"
                    :auto-upload="false"
                    :multiple="true"
                  >
                    <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
                  </el-upload>
                </el-form-item>
              </el-col>
              <el-col :span="8" style="float: left;text-align:left;">
                <el-button type="primary" plain icon="el-icon-upload2" @click="batchMount2()">开始挂载</el-button>
                <el-button plain type="primary" @click="cleanFiles2()">清除所有文件</el-button>
              </el-col>
            </el-row>
          </el-tab-pane>
        </el-tabs>
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
  name: "BatchMount",
  permit: 1,
  data() {
    return {
      activeName: "first",
      fileList1:[],
      fileList2:[],
      fileList3:[],
      importMessage:"",
      loading:false
    };
  },
  methods: {
    handleChange1(file, fileList) {
      this.fileList1 = fileList;
    },
    handleChange2(file, fileList) {
      this.fileList2 = fileList;
    },
    handleChange3(file, fileList) {
      this.fileList3 = fileList;
    },
    cleanFiles1(){
      this.fileList1 = [];
      this.fileList2 = [];
    },
    cleanFiles2(){
      this.fileList3 = [];
    },
    batchMount1() {
      let _self = this;
      if (_self.fileList1 == null || _self.fileList1.length == 0||_self.fileList1[0].raw==null) {
        _self.$message("请选择挂载Excel文件!");
        return;
      }
      let formdata = new FormData();
      formdata.append("excel", _self.fileList1[0].raw);
      _self.fileList2.forEach(function(file) {
        formdata.append("files", file.raw, file.name);
      });
      _self.loading = true;
      axios
        .post("/tools/mountByExcel", formdata, {
          "Content-Type": "multipart/form-data"
        })
        .then(function(response) {
          _self.importMessage = response.data.data;
          _self.loading = false;
          _self.$message("挂载成功!");
          _self.cleanFiles1();
        })
        .catch(function(error) {
          _self.$message("挂载失败!");
          console.log(error);
        });
    },
    batchMount2() {
      let _self = this;
      let formdata = new FormData();
      _self.fileList3.forEach(function(file) {
        formdata.append("files", file.raw, file.name);
      });
      _self.loading = true;
      axios
        .post("/tools/mountByFile", formdata, {
          "Content-Type": "multipart/form-data"
        })
        .then(function(response) {
          _self.importMessage = response.data.data;
          _self.loading = false;
          _self.$message("挂载成功!");
          _self.cleanFiles2();
        })
        .catch(function(error) {
          _self.$message("挂载失败!");
          console.log(error);
        });
    }
  }
};
</script>
<style scoped>
</style>