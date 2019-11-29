<template>
  <el-table :data="tabledata">
    <template  v-for="item in gridList">
      <el-table-column :key="item.id" :label="item.label" :prop="item.attrName">
        <template v-if="item.attrName=='C_DOC_DATE'"></template>
      </el-table-column>
    </template>
    
    <el-table-column v-if="downloadEnable">
      <template slot-scope="scope">
        <el-button size="mini" @click="download(scope.row)">下载</el-button>
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
              {id:'1',attrName:'name',label:'文件名'},
              {id:'2',attrName:'formatName',label:'格式'},
              {id:'3',attrName:'creationDate',label:'创建时间'},
              {id:'4',attrName:'modifiedDate',label:'修改时间'}
            ],
            currentLanguage: "zh-cn",
            tabledata:[]
        }
    },
    props:{
        docId:{
            type:String,
            default:""
        },
        downloadEnable:{
          type:Boolean,
          default:false
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
              console.log(_self.tabledata);
            }
          });
      },
      download(row){
        let url = "/dc/getContent?id="+row.id+"&token="+sessionStorage.getItem('access-token');
        window.open(url, '_blank');
      },
      formatDocDate(date){
        return this.dateFormat(date);
      }
    }
}
</script>

<style>

</style>