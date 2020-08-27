<template>
  <el-table :data="tabledata">
    <template  v-for="item in gridList">
      <el-table-column :key="item.id" v-if="item.visibleType!='3'" :label="item.label" :prop="item.attrName">
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
    <el-table-column align="right">
      <template slot-scope="scope">
        <el-button size="mini" @click="viewdoc(scope.row)">查看</el-button>
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

      },
      viewdoc(row){
        let condition = row.ID;
        let href = this.$router.resolve({
        path: "/viewdoc",
        query: {
          id: condition
        }
      });
      window.open(href.href, "_blank");
      }
    }
}
</script>

<style>

</style>