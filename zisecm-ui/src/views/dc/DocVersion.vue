<template>
  <el-table :data="tabledata" size="mini">
    <template  v-for="item in gridList">
      <el-table-column :key="item.id" :label="item.label" :prop="item.attrName"></el-table-column>
    </template>
  </el-table>
</template>

<script>
export default {
  data(){
        return{
            gridviewName:'RevisionGrid',
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
          _self.loadData();
        });
      },
      loadData(){
          let _self = this;
          axios.post("/dc/getVerions",this.docId).then(function(response) {
            let result = response.data;
            if(result.code==1){
              _self.tabledata = result.data;
            }
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