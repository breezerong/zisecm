<template>
  <el-table :data="tabledata">
    <template  v-for="item in gridList">
      <el-table-column :key="item.id" :label="item.label" :prop="item.attrName">
        <template slot-scope="scope">
            <template v-if="item.attrName=='C_DOC_DATE'">
              {{dateFormat(scope.row.C_DOC_DATE)}}
            </template>
            <template v-else>
              {{scope.row[item.attrName]}}  
            </template>            
        </template>        
      </el-table-column>
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
      this.loadGridView();
    },
    mounted(){
      this.currentLanguage = localStorage.getItem("localeLanguage") || "zh-cn";      
    }
}
</script>

<style>

</style>