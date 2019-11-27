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
            gridviewName:'GeneralRelationGrid',
            gridList: [],
            currentLanguage: "zh-cn",
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
    name:"ViewRedition",
    created(){
      console.log("格式副本 created");
    },
    methods:{
      loadGridView(){
        let _self = this;
        var m = new Map();
        m.set("gridName", _self.gridviewName);
        m.set("lang", _self.currentLanguage);
        axios.post("/dc/getGridViewInfo",JSON.stringify(m)).then(function(response) {
          _self.gridList = response.data.data;
          console.log(_self.gridList);
        });
      }
    }
}
</script>

<style>

</style>