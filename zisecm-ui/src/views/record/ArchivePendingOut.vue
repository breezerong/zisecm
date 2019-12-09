<template>
    <div>
        <el-row>
            <el-col :span="5" style="float:left;text-align:left;">
                <el-input
                v-model="orderInputkey"
                :placeholder="$t('message.pleaseInput')+$t('application.keyword')"
                @change="searchOrderItem"
                prefix-icon="el-icon-search"
                ></el-input>
            </el-col>
            <el-col :span="12" style="padding-top:4px;float:left;text-align:left;">
                <el-button type="primary" plain
                size="small" icon="el-icon-printer" >打印出库单</el-button>
                <el-button type="primary" plain
                size="small" icon="el-icon-check" @click="outboundOrder">出库完成</el-button>
            </el-col>
            
        </el-row>
        <el-row>
            <el-col>
                <DataGrid ref="orderGrid" key="main" v-bind:itemDataList="itemDataList"
                      v-bind:columnList="gridList" @pagesizechange="pageSizeChange"
                      @pagechange="pageChange" v-bind:itemCount="itemCount"
                      v-bind:tableHeight="rightTableHeight" :isshowOption="true"
                      :loading="orderLoading"
                      @rowclick="orderclick"
                       @selectchange="orderSelectChange"></DataGrid>
            </el-col>

        </el-row>
        <el-row>
         <el-col :span="6" style="float:left;text-align:left;">
                <el-input
                v-model="inputkey"
                :placeholder="$t('message.pleaseInput')+$t('application.keyword')"
                @change="searchItem"
                prefix-icon="el-icon-search"
                ></el-input>
            </el-col>
            <el-col :span="12" style="padding-top:4px;float:left;text-align:left;">
               <el-button type="primary" plain
                size="small" icon="el-icon-check" @click="outboundFile">出库</el-button>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="12">
                <DataGrid ref="fileGrid" key="fileGrid" v-bind:itemDataList="gridListFileData"
                      v-bind:columnList="gridListFile" @pagesizechange="filePageSizeChange"
                      @pagechange="filePageChange" v-bind:itemCount="fileItemCount"
                      v-bind:tableHeight="rightTableHeight" :isshowOption="true"
                      :loading="fileLoading"
                       @selectchange="selectChange"></DataGrid>
            </el-col>
            <el-col :span="12">
                <DataGrid ref="outFileGrid" key="outFileGrid" v-bind:itemDataList="gridListOutFileData"
                      v-bind:columnList="gridListOutFile" @pagesizechange="outFilePageSizeChange"
                      @pagechange="outFilePageChange" v-bind:itemCount="outFileItemCount"
                      v-bind:tableHeight="rightOutTableHeight" :isshowOption="true"
                      :loading="outFileLoading"
                       @selectchange="selectOutChange"></DataGrid>
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
             outFilePageSize:20,
             outFileCurrentPage: 1,
             outFileItemCount:0,
             itemCount: 0,
             inputkey: "",
             gridListFile:[],
             gridListFileData:[],
             orderLoading:false,
             fileLoading:false,
             selectedOrderRow:[],
             selectedOrder:[],
             seletedFile:[],
             orderInputkey:"",
             gridListOutFileData:[],
             gridListOutFile:[],
             outFileLoading:false,
             seletedOutFile:[],
             selectedOutFileRow:[],
             rightTableHeight: (window.innerHeight - 200)/2,
             rightOutTableHeight:(window.innerHeight - 200)/2
        }
       
    },
    mounted(){
        this.loadGridInfo();
        this.loadGridData();
        this.loadGridInfoFile();
        this.loadOutFileGridInfo();
    },
    components:{
        DataGrid:DataGrid
    },
    methods:{
        orderclick(row){
          this.loadFileGridData(row);
          this.loadOutFileGridData(row);
        },
        selectOutChange(val){
          this.seletedOutFile=val;
        },
        outFilePageSizeChange(){

        },
        outFilePageChange(){
          this.fileCurrentPage = val;
          this.loadOutFileGridData();
        },
        pageSizeChange(val){
            this.pageSize = val;
            localStorage.setItem("docPageSize",val);
            _self.loadGridData();
        },
        //查询文档
        searchOrderItem() {
            this.loadGridData();
            // this.loadPageInfo();
        },
        selectChange(val){
          this.seletedFile=val;
        },
        orderSelectChange(val){
          this.selectedOrder = val;
        },
        searchItem(){
          this.loadFileGridData();
        },
        // 分页 当前页改变
        filePageChange(val){
          this.fileCurrentPage = val;
          this.loadFileGridData();
        },
        // 分页 当前页改变
        pageChange(val) {
            this.currentPage = val;
            this.loadGridData();
            //console.log('handleCurrentChange', val);
        },
        show(){

        },
        outboundFile(){
          let _self=this;
          if(_self.seletedFile.length<1){
             _self.$message("请选择待出库文件数据！");
            return;
          }
          let tab=_self.seletedFile;
          let p=new Map();
          let m = [];
          let i;
          for(i in tab){
            m.push(tab[i]["ID"]);
          }
          p.set("ids",m);
          p.set("pid",_self.selectedOrderRow.ID)
          _self.axios({
              headers: {
                "Content-Type": "application/json;charset=UTF-8"
              },
              method: "post",
              data: JSON.stringify(p),
              url: "/dc/outboundfile"
            })
            .then(function(response) {
              _self.loadGridData();
              _self.loadFileGridData();
              _self.loadOutFileGridData();
              // _self.innerDataList=[];
                // _self.showInnerFile(null);
              _self.$message(response.data.message);
            })
            .catch(function(error) {
              _self.$message(response.data.message);
              console.log(error);
          });
        },
        outboundOrder(){
          let _self=this;
          if(_self.selectedOrder.length<1){
            _self.$message("请选择借阅单数据！");
            return;
          }

          let tab=_self.selectedOrder;
          let m = [];
          let i;
          for(i in tab){
            m.push(tab[i]["ID"]);
          }
          _self.axios({
              headers: {
                "Content-Type": "application/json;charset=UTF-8"
              },
              method: "post",
              data: JSON.stringify(m),
              url: "/dc/outboundorder"
            })
            .then(function(response) {
              _self.loadGridData();
              _self.loadFileGridData();
              _self.loadOutFileGridData();
              // _self.innerDataList=[];
                // _self.showInnerFile(null);
              _self.$message(response.data.message);
            })
            .catch(function(error) {
              _self.$message(response.data.message);
              console.log(error);
          });

        },
        loadOutFileGridInfo(){
          let _self = this;
          var m = new Map();
          m.set("gridName", "BorrowOutFileGrid");
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
              _self.gridListOutFile = response.data.data;
              
            })
            .catch(function(error) {
              console.log(error);
            });
        },
        // 加载未入库表格数据
        loadOutFileGridData(row){
          let _self = this;
          if (row != null) {
            _self.selectedOutFileRow = row;
            
          }
          _self.outFileLoading=true;
          var m = new Map();
          // var key = _self.inputkey;
          // if (key != "") {
          //   key = " and( a.coding like '%" + key + "%' or a.C_DRAFTER like '%" + key + "%' )";
          //   m.set("condition", key);
          // }
        
          m.set("configName", "InBorrowOrderOutFile");
          // m.set('folderId',indata.id);
          // m.set("condition", key);
          m.set("parentId",_self.selectedOutFileRow.ID);
          m.set("pageSize", _self.outFilePageSize);
          m.set("pageIndex", (_self.outFileCurrentPage - 1) * _self.outFilePageSize);
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
              _self.gridListOutFileData = response.data.data;
              _self.outFileItemCount = response.data.pager.total;
              //console.log(JSON.stringify(response.data.datalist));
              _self.outFileLoading = false;
            })
            .catch(function(error) {
              console.log(error);
              _self.outFileLoading = false;
            });
        },
        // 加载表格数据
        loadFileGridData(row) {
          let _self = this;
          if (row != null) {
            _self.selectedOrderRow = row;
            
          }
          _self.fileLoading=true;
          var m = new Map();
          var key = _self.inputkey;
          if (key != "") {
            key = " and( a.coding like '%" + key + "%' or a.C_DRAFTER like '%" + key + "%' )";
            m.set("condition", key);
          }
        
          m.set("configName", "InBorrowOrderFile");
          // m.set('folderId',indata.id);
          // m.set("condition", key);
          m.set("parentId",_self.selectedOrderRow.ID);
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
              _self.fileLoading = false;
            })
            .catch(function(error) {
              console.log(error);
              _self.fileLoading = false;
            });
        },
            // 加载借阅单表格数据
        loadGridData() {
          let _self = this;
          _self.gridListFileData=[];
          _self.gridListOutFileData=[];
          _self.orderLoading=true;
          var key = _self.orderInputkey;
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