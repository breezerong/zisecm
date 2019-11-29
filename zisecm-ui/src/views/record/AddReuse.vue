<!--添加复用文件-->

<template>
  <div>
    <el-row>
      <el-col>
        <el-input  v-model="inputkey" :placeholder="$t('message.pleaseInput')+$t('application.keyword')" @change="initData()" prefix-icon="el-icon-search"></el-input>
      </el-col>
      
    </el-row>
    <el-row>
      <DataGrid ref="dataGrid" key="left" v-bind:itemDataList="dataList"
                      v-bind:columnList="gridList" v-bind:itemCount="dataCount"
                      @pagesizechange="handleSizeChange" @pagechange="handleCurrentChange"
                      @selectchange="selectChange"></DataGrid>
    </el-row>
    
  </div>
  
</template>

<script type="text/javascript">
import DataGrid from'@/components/DataGrid'
export default {
  name: "ArchiveArrange",
  permit: 1,
  data() {
    return {
      dataList:[],
      gridList:[],
      dataCount:0,
      pageSize:20,
      currentPage:1,
      currentLanguage: "zh-cn",
      inputkey:"",
      selectedRow:[],
      formLabelWidth: "120px"
    };
  },
  mounted(){
    this.initDataGrid();
    this.initData();
  },
  methods: {
    // 表格行选择
    selectChange(val) 
    {
      // console.log(JSON.stringify(val));
      this.selectedRow = val;
    },
    // 分页 当前页改变
    handleCurrentChange(val) 
    {
      this.currentPage = val;
      this.initData();
    },
    // 分页 页数改变
    handleSizeChange(val) 
    {
      this.pageSize = val;
      localStorage.setItem("docPageSize",val);
      this.initDataGrid();
      //console.log('handleSizeChange', val);
    },
      // 加载移交单表格样式
    initDataGrid(){
      let _self = this;
      _self.loading = true;
      var m = new Map();
      m.set('gridName','DesignGrid');
      m.set('lang',_self.currentLanguage);
      _self.axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        method: 'post',
        data: JSON.stringify(m),
        url: "/dc/getGridViewInfo"
      })
        .then(function(response) {
          _self.gridList = response.data.data;
         
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    
    },
    initData(){
      let _self = this;
      var key =_self.inputkey;
      if(key!=""){
        key = " (coding like '%"+key+"%' or title like '%"+key+"%') "; 
        key=key+" and status='利用' ";
      }else{
        key=" status='利用' ";
      }
      var m = new Map();
      m.set('gridName','DesignGrid');
      m.set('condition',key);
      // m.set('folderId',indata.id);
      // m.set('status','产生')
      m.set('pageSize',_self.pageSize);
      m.set('pageIndex', (_self.currentPage-1)*_self.pageSize);
      m.set('orderBy','');
      // console.log('pagesize:', _self.pageSize);
      _self
      .axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        method: "post",
        data: JSON.stringify(m),
        url: "/dc/getDesignFiles"
      })
      .then(function(response) {
        _self.dataList = response.data.data;
        _self.dataCount = response.data.pager.total;
        //console.log(JSON.stringify(response.data.datalist));
       
      })
      .catch(function(error) {
        console.log(error);
        
      });
    }
  },
  components: {
    
    DataGrid:DataGrid
    
    //Prints:Prints
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1,
h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
body,html{
			height:100%;
			margin:0px;
			padding:0px;
			overflow:hidden;
		}
    .left,.right{
			width:100%;
      }
      .middle{
        width:5%;
      }
		.left,.middle,.right{
			/* width:200px; */
			/* height:100px; */
			/* background-color:rgb(34,124,134); */
			float:left;
			height:100%;
		}
</style>
