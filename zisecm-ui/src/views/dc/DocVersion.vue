<template>
  <el-table :data="tabledata">
    <template v-for="item in gridList">
      <el-table-column :key="item.id" v-if="item.visibleType!='3'" :label="item.label" :prop="item.attrName">
        <template slot-scope="scope">
          <template v-if="item.attrName=='C_DOC_DATE'">{{dateFormat(scope.row.C_DOC_DATE)}}</template>
          <template v-else>{{scope.row[item.attrName]}}</template>
        </template>
      </el-table-column>
    </template>
    <el-table-column width="80">
      <template slot-scope="scope">
        <el-button
          type="primary"
          plain
          size="small"
          :title="$t('application.viewContent')"
          icon="el-icon-picture-outline"
          @click="showItemContent(scope.row)"
        ></el-button>
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
        m.set("lang", _self.getLang());
        axios.post("/dc/getGridViewInfo",JSON.stringify(m)).then(function(response) {
          _self.gridList = response.data.data;
          _self.loadData();
        });
      },
      loadData(){
          let _self = this;
          axios.post("/dc/getVerionsEx",this.docId).then(function(response) {
            let result = response.data;
            if(result.code==1){
              _self.tabledata = result.data;
            }
          });
      },
      // 查看内容
    showItemContent(indata) {
      let condition = indata.ID;
      let href = this.$router.resolve({
        path: "/viewdoc",
        query: {
          id: condition
          //token: sessionStorage.getItem('access-token')
        }
      });
      //console.log(href);
      window.open(href.href, "_blank");
    }
    },
    created(){
      this.loadGridView();
    },
    mounted(){
      this.currentLanguage = localStorage.getItem("localeLanguage") || "en";      
    }
}
</script>

<style>
</style>