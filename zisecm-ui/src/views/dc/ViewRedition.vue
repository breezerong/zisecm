<template>
  <el-table :data="tabledata">
    <template  v-for="item in gridList">
      <el-table-column :key="item.id" :label="item.label" :prop="item.attrName">
        <template v-if="item.attrName=='C_DOC_DATE'"></template>
      </el-table-column>
    </template>
    
    <el-table-column>
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
            gridviewName:'GeneralGrid',
            gridList: [
              {id:'1',attrName:'NAME',label:'文件名'},
              {id:'2',attrName:'FORMAT_NAME',label:'格式'},
              {id:'3',attrName:'CREATION_DATE',label:'创建时间'},
              {id:'4',attrName:'MODIFIED_DATE',label:'修改时间'}
            ],
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
    name:"ViewRedition",
    created(){
      console.log("格式副本 created");
      this.loadGridView();
    },
    methods:{
      loadGridView(){
        let _self = this;
        _self.loadData();        
      },
      loadData(){
          let _self = this;
          axios.post("/dc/getRenditions",this.docId).then(function(response) {
            let result = response.data;
            console.log(result);
            if(result.code==1){
              _self.tabledata = result.data;
            }
          });
      },
      downloadDoc(row){

      },
      formatDocDate(date){
        return this.dateFormat(date);
      }
    }
}
</script>

<style>

</style>