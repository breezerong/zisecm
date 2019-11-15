<template>
  <el-table>
    <el-table-column label="Name" prop="name"></el-table-column>
    <el-table-column label="Address" prop="address"></el-table-column>
    
  </el-table>
</template>

<script>
export default {
    props:{
        docId:{
            type:String,
            default:""
        }
    },
    name:"RelationDocs",
    created(){
      //获取关联文档
    },
    mounted(){
      
    }
}
</script>

<style>

</style>