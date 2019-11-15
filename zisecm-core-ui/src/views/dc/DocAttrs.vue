<template>
  <el-table :data="tabledata">
    <el-table-column label="属性名" prop="attrName"></el-table-column>
    <el-table-column label="属性值" prop="attrValue"></el-table-column>
    
  </el-table>
</template>

<script>
export default {
    data(){
        return{
            tabledata:[
                {
                    attrName:"文件名",attrValue:"AAAA"
                },
                {
                    attrName:"文件编码",attrValue:"AAAA"
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
    name:"DocAttrs",
    created(){
      console.log("文档属性 created");
    },
    mounted(){
      console.log("文档属性 mounted");
    }
}
</script>

<style>

</style>