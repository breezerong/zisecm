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
      <el-row>
        
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>根据Excel挂载</span>
          </div>
          <el-row>
            <div style="float: left;text-align:left;">
              &nbsp; &nbsp; 批量挂载的Excel文件为公司文档Excel导出结果，第二列填写挂载电子文件名。
              <br>
              <br>
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
                <el-button type="primary" plain icon="el-icon-upload2" @click="batchImport()">开始挂载</el-button>
                <el-button plain type="primary" @click="cleanFiles()">清除所有文件</el-button>
              </el-col>
            </el-row>
        </el-card>
        
      </el-row>
      <el-row>
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>根据名称挂载</span>
          </div>
          <el-row>
            <div style="float: left;text-align:left;">
              &nbsp; &nbsp; 根据文件名挂载只支持图纸文件挂载，命名规则：编码@版本@标题，如：1188XGASBS01@A@设计平面图.pdf。
              <br>
              <br>
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
                <el-button type="primary" plain icon="el-icon-upload2" @click="batchImport()">开始挂载</el-button>
                <el-button plain type="primary" @click="cleanFiles()">清除所有文件</el-button>
              </el-col>
            </el-row>
        </el-card>
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
  name: "batchMount",
  permit: 1,
  data() {
    return {
      contentHeight: window.innerHeight - 64
    };
  },
  methods: {}
};
</script>
<style scoped>
</style>