<template>
  <el-table :data="tabledata">
    <template  v-for="item in gridList">
      <el-table-column :key="item.id" :label="item.label" :prop="item.attrName">
        <template slot-scope="scope">
            <template v-if="item.attrName=='creationDate' || item.attrName=='modifiedDate'">
              {{datetimeFormat(scope.row[item.attrName])}}
            </template>
            <template v-else-if="item.attrName=='contentSize'">
              {{formatSize(scope.row[item.attrName])}} 
            </template>
            <template v-else>
              {{scope.row[item.attrName]}}  
            </template>            
        </template>        
      </el-table-column>
    </template>
    
    <el-table-column v-if="downloadEnable" align="right">
      <template slot-scope="scope">
        <el-button size="mini" @click="download(scope.row)">{{$t('application.download')}}</el-button>
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
              //{id:'1',attrName:'name',label:'文件名'},
              {id:'2',attrName:'formatName',label:this.$t('field.format')},
              {id:'3',attrName:'contentSize',label:this.$t('field.size')},
              {id:'4',attrName:'creationDate',label:this.$t('application.creationDate')},
              {id:'5',attrName:'modifiedDate',label:this.$t('field.modifiedDate')}
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
            if(result.code==1){
              _self.tabledata = result.data;
            }
          });
      },
      download(row){
        let url = this.axios.defaults.baseURL+"/dc/getContent?id="+row.parentId+"&token="+sessionStorage.getItem('access-token')+"&action=download&format="+row.formatName;
        window.open(url, '_blank');
      }
    }
}
</script>
<style>
</style>