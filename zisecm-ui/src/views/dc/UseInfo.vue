<template>
  <el-container>    
      <el-form label-position="right" label-width="120px">
        <el-form-item label="借阅次数:">
            <label>{{viewData.borrowCount}}</label>
        </el-form-item>
        <el-form-item label="查看次数:">
            <label>{{viewData.readCount}}</label>
        </el-form-item>
        <el-form-item label="下载次数:">
            <label>{{viewData.downloadCount}}</label>
        </el-form-item>
      </el-form>    
  </el-container>
</template>

<script>
export default {
    data(){
      return{
          viewData:{
            borrowCount:0,
            downloadCount:0,
            readCount:0
          }
      }
    },
    props:{
        docId:{
            type:String,
            default:""
        }        
    },
    name:"UseInfo",
    created(){
      let self = this;
      if(this.docId.length>0){
        var url="/dc/getDocUseInfo"
        axios.post(url,this.docId).then(function(result){
          self.viewData = result.data.data
        });
      }     
    }
}
</script>

<style>

</style>