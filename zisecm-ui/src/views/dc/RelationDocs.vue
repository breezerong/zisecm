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
            tabledata:[]
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
          _self.loadData();
        });
      },
      loadData(){
          let _self = this;
          axios.post("/dc/getRelations",this.docId).then(function(response) {
            let result = response.data;
            if(result.code==1){
              _self.tabledata = result.data;
            }
          });
      },
      downloadDoc(row){

      }
    }
}
</script>

<style>

</style>