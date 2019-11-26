<template>
  <el-table :data="tabledata">
    <el-table-column v-for="item in gridList" :key="item.id" :label="item.label" :prop="item.attrName"></el-table-column>   
    <el-table-column align="right">
      <template slot-scope="scope">
        <el-button size="mini" @click="downloadDoc(scope.row)">下载</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
export default {
  data(){
        return{
            gridviewName:'RevisionGrid',
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
    name:"DocVersion",
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
      },
      downloadDoc(row){

      }
    },
    created(){
      console.log("文档版本 created");
      this.loadGridView();
    },
    mounted(){
      this.currentLanguage = localStorage.getItem("localeLanguage") || "zh-cn";
      
    }
}
</script>

<style>

</style>