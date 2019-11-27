<template>
  <el-table>
    <el-table-column v-for="item in gridList" :key="item.id" :label="item.label" :prop="item.attrName"></el-table-column> 
    
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
    name:"RelationDocs",
    created(){
      this.loadGridView()
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
      },
      downloadDoc(row){

      }
    }
}
</script>

<style>

</style>