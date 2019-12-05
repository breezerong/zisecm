<template>
    <div>
        <el-row>
            <el-col :span="5" style="float:left;text-align:left;">
                <el-input
                v-model="inputkey"
                :placeholder="$t('message.pleaseInput')+$t('application.keyword')"
                @change="searchItem"
                prefix-icon="el-icon-search"
                ></el-input>
            </el-col>
            <el-col :span="12" style="padding-top:4px;float:left;text-align:left;">
                <el-button type="primary" plain
                size="small" icon="el-icon-printer" >打印出库单</el-button>
                <el-button type="primary" plain
                size="small" icon="el-icon-check" >出库完成</el-button>
            </el-col>
            
        </el-row>
        <el-row>
            <el-col>
                <DataGrid ref="orderGrid" key="main" v-bind:itemDataList="itemDataList"
                      v-bind:columnList="gridList" @pagesizechange="pageSizeChange"
                      @pagechange="pageChange" v-bind:itemCount="fileItemCount"
                      v-bind:tableHeight="rightTableHeight" :isshowOption="true"
                      :loading="orderLoading"
                       @selectchange="fileSelectChange"></DataGrid>
            </el-col>

        </el-row>
        <el-row>
         <el-col :span="5" style="float:left;text-align:left;">
                <el-input
                v-model="inputkey"
                :placeholder="$t('message.pleaseInput')+$t('application.keyword')"
                @change="searchItem"
                prefix-icon="el-icon-search"
                ></el-input>
            </el-col>
            <el-col :span="12" style="padding-top:4px;float:left;text-align:left;">
               <el-button type="primary" plain
                size="small" icon="el-icon-check" >出库</el-button>
            </el-col>
        </el-row>
        <el-row>
            <el-col>
                <DataGrid ref="fileGrid" key="fileGrid" v-bind:itemDataList="gridListFileData"
                      v-bind:columnList="gridListFile" @pagesizechange="filePageSizeChange"
                      @pagechange="filePageChange" v-bind:itemCount="itemCount"
                      v-bind:tableHeight="rightTableHeight" :isshowOption="true"
                      :loading="fileLoading"
                       @selectchange="selectChange"></DataGrid>
            </el-col>

        </el-row>
    </div>
</template>
<script type="text/javascript">
import DataGrid from'@/components/DataGrid'

export default {
    name:'archivepending',
    permit:1,
    data(){
        return{
             a:[],
             gridList:[],
             innerDataList:[],
             itemDataList:[],
             itemDataListFull:[],
             pageSize: 20,
             currentPage: 1,
             filePageSize:20,
             fileCurrentPage: 1,
             fileItemCount:0,
             itemCount: 0,
             inputkey: "",
             gridListFile:[],
             gridListFileData:[],
             orderLoading:false,
             fileLoading:false,
             selectedOrderRow:[],
             rightTableHeight: (window.innerHeight - 200)/2,
        }
       
    },
    mounted(){
        this.loadGridInfo();
        this.loadGridData();
        this.loadGridInfoFile();
    },
    components:{
        DataGrid:DataGrid
    },
    methods:{
        pageSizeChange(val){
            this.pageSize = val;
            localStorage.setItem("docPageSize",val);
            _self.loadGridData(_self.currentFolder);
        },
        //查询文档
        searchItem() {
            this.loadGridData();
            this.loadPageInfo();
        },
        // 分页 当前页改变
        pageChange(val) {
            this.currentPage = val;
            this.loadGridData();
            //console.log('handleCurrentChange', val);
        },
        show(){

        },
         // 加载表格数据
    loadFileGridData(row) {
      let _self = this;
      if (row != null) {
        _self.selectedOrderRow = row;
        
      }
      _self.orderLoading=true;
      var key = _self.inputkey;
      if (key != "") {
        key = "coding like '%" + key + "%' or title like '%" + key + "%' ";
      }
     
      var m = new Map();
      m.set("configName", "InBorrowOrderFile");
      // m.set('folderId',indata.id);
      // m.set("condition", key);
      m.set("parentId",row.ID);
      
      m.set("pageSize", _self.filePageSize);
      m.set("pageIndex", (_self.fileCurrentPage - 1) * _self.filePageSize);
      m.set("orderBy", "");
      // console.log('pagesize:', _self.pageSize);
      _self
        .axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data: JSON.stringify(m),
          url: "/dc/getObjectsByConfigclause"
        })
        .then(function(response) {
          _self.gridListFileData = response.data.data;
          _self.fileItemCount = response.data.pager.total;
          //console.log(JSON.stringify(response.data.datalist));
          _self.orderLoading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.orderLoading = false;
        });
    },
        // 加载借阅单表格数据
    loadGridData() {
      let _self = this;
      _self.gridListFileData=[];
      
      _self.orderLoading=true;
      var key = _self.inputkey;
      if (key != "") {
        key = "coding like '%" + key + "%' or title like '%" + key + "%' and STATUS='待出库'";
      }else{
          key=" STATUS='待出库' "
      }
     
      var m = new Map();
      m.set("gridName", "BorrowFormGrid");
      // m.set('folderId',indata.id);
      m.set("condition", key);
      
      m.set("pageSize", _self.pageSize);
      m.set("pageIndex", (_self.currentPage - 1) * _self.pageSize);
      m.set("orderBy", "");
      // console.log('pagesize:', _self.pageSize);
      _self
        .axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data: JSON.stringify(m),
          url: "/dc/getBorrowOrder"
        })
        .then(function(response) {
          _self.itemDataList = response.data.data;
          _self.itemDataListFull = response.data.data;
          _self.itemCount = response.data.pager.total;
          //console.log(JSON.stringify(response.data.datalist));
          _self.orderLoading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.orderLoading = false;
        });
    },
    loadGridInfoFile(){
      let _self = this;
      var m = new Map();
      m.set("gridName", "BorrowFileGrid");
      m.set("lang", _self.getLang());
      _self
        .axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data: JSON.stringify(m),
          url: "/dc/getGridViewInfo"
        })
        .then(function(response) {
          _self.gridListFile = response.data.data;
          
        })
        .catch(function(error) {
          console.log(error);
        });
    },
        // 加载表格样式
    loadGridInfo() {
      let _self = this;
      _self.orderLoading = true;
      var m = new Map();
      m.set("gridName", "BorrowFormGrid");
      m.set("lang", _self.getLang());
      _self
        .axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data: JSON.stringify(m),
          url: "/dc/getGridViewInfo"
        })
        .then(function(response) {
          _self.gridList = response.data.data;
          
          _self.orderLoading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.orderLoading = false;
        });
    }

    }

};
</script>
<style scoped>

</style>