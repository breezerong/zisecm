<template>
  <div>
    <el-form>
      <el-row>
        <el-col :span="12">
          <el-form-item label="ID:" :label-width="formLabelWidth" >
            <div class="grid-content">{{itemData.ID}}</div>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="类型:" :label-width="formLabelWidth" >
            <div class="grid-content">{{itemData.TYPE_NAME}}</div>
          </el-form-item>
        </el-col>
       </el-row>
      
      <el-row>
        <el-col :span="12">
          <el-form-item label="创建人:" :label-width="formLabelWidth" >
            <div class="grid-content">{{itemData.CREATOR}}</div>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="创建时间:" :label-width="formLabelWidth" >
            <div class="grid-content">{{datetimeFormat(itemData.CREATION_DATE)}}</div>
          </el-form-item>
        </el-col>
       </el-row>
       <el-row>
        <el-col :span="12">
          <el-form-item label="修改人:" :label-width="formLabelWidth" >
            <div class="grid-content">{{itemData.MODIFIER}}</div>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="修改时间:" :label-width="formLabelWidth" >
            <div class="grid-content">{{datetimeFormat(itemData.MODIFIED_DATE)}}</div>
          </el-form-item>
        </el-col>
      </el-row>
      
       <el-row>
        <el-col :span="12">
          <el-form-item label="文件夹:" :label-width="formLabelWidth" >
            <div class="grid-content">{{folderPath}}</div>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="状态:" :label-width="formLabelWidth" >
            <div class="grid-content">{{itemData.STATUS}}</div>
          </el-form-item>
        </el-col>
       </el-row>
       <el-row>
        <el-col :span="12">
          <el-form-item label="格式:" :label-width="formLabelWidth" >
            <div class="grid-content">{{itemData.FORMAT_NAME}}</div>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="大小:" :label-width="formLabelWidth" > 
            <div class="grid-content">{{formatSize(itemData.CONTENT_SIZE)}}</div>
          </el-form-item>
        </el-col>
       </el-row>
       <el-row>
        <el-col :span="12">
          <el-form-item label="ACL:" :label-width="formLabelWidth" >
            <div class="grid-content">{{itemData.ACL_NAME}}</div>
          </el-form-item>
        </el-col>
       </el-row>
    </el-form>
  </div>
</template>

<script>

export default {
  data(){
    return {
      formLabelWidth: "120px",
      folderPath:""
    }
  },
  props:{
    itemData:{//数据
      type:String, default: null
    }
  },
  mounted() {
    this.refreshData();
  },
  methods:{
   
    refreshData(){
       let _self = this;
       axios.post("/folder/getFolderById",_self.itemData.FOLDER_ID).then(function(response){
        if(response.data.code == 1){
          _self.folderPath = response.data.data.folderPath;
        }else{
         _self.folderPath = "";
        }
      });
    }
  }
}
</script>

<style scoped>

element.style {
  margin-left: 10px !important;
  text-align: left !important;
}
.el-form-item__content {
  text-align: left !important;
  font-size: 14px;
}
.grid-content{
  background-color:rgb(243, 242, 242)
}
</style>