<template>
  <el-table :data="tabledata">
    <el-table-column label="文件名" prop="fileName"></el-table-column>
    <el-table-column label="版本" prop="version"></el-table-column>
    <el-table-column align="right">
      <template slot-scope="scope">
        <el-button size="mini">下载</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
export default {
  data(){
        return{
            tabledata:[
                {
                    fileName:"文件名",version:"AAAA"
                },
                {
                    fileName:"ban'b",version:"AAAA"
                }
            ]
        }
    },
    props:{
        docId:{
            type:String,
            default:""
        }
    },
    name:"DocVersion",
    created(){
      console.log("文档版本 created");
    },
    mounted(){
      console.log("文档版本 mounted");
    }
}
</script>

<style>

</style>