<template>
    <div>
      <el-dialog width="80%" title="列表" :visible.sync="archiveBackupVisible" @close="archiveBackupVisible = false">
        <el-row>
            <el-col>
                <DataGrid ref="orderGrid" key="main" v-bind:itemDataList="childDataList"
                      v-bind:columnList="childColumnList" @pagesizechange="childPageSizeChange"
                      @pagechange="childPageChange" v-bind:itemCount="childItemCount"
                      v-bind:tableHeight="rightTableHeight" :isshowOption="false"
                       ></DataGrid>
            </el-col>

        </el-row>
        <div slot="footer" class="dialog-footer">
          <!-- <el-button @click="saveItem">{{$t('application.save')}}</el-button>  -->
          <el-button @click="archiveBackupVisible = false">{{$t('application.cancel')}}</el-button>
        </div>
      </el-dialog>
        <el-row>
          <el-col :span="4" class="topbar-input">
          <el-input
            v-model="inputkey"
            :placeholder="$t('message.pleaseInput')+$t('application.keyword')"
            @change="searchItem"
            prefix-icon="el-icon-search"
          ></el-input>
          </el-col>
        </el-row>
        <el-row>
            <el-col>
                <DataGrid ref="orderGrid" key="main" v-bind:itemDataList="itemDataList"
                      v-bind:columnList="gridList" @pagesizechange="pageSizeChange"
                      @pagechange="pageChange" v-bind:itemCount="itemCount"
                      v-bind:tableHeight="rightTableHeight" :isshowOption="true"
                      @rowclick="onRowClick"
                      :loading="orderLoading"
                      @refreshdatagrid="loadGridData"></DataGrid>
            </el-col>

        </el-row>
        
    </div>
</template>
<script type="text/javascript">
import DataGrid from'@/components/DataGrid';
export default {
    name:'archiveBackupNew',
    permit:1,
    data(){
        return{
          archiveBackupVisible:false,
             gridList:[],
             itemDataList:[],
             itemDataListFull:[],
             inputkey:'',
             pageSize: 20,
             currentPage: 1,
             itemCount: 0,
             childCurrentPage:1,
             childPageSize: 20,
             childItemCount:0,
             childDataList:[],
             childColumnList:[],
             orderLoading:false,
             selectedRow:[],
            form: {
              coding:"",
              title:"",
              createDate:"",
              endDate:"",
              size:0,
              director:"",
              condition:""
            },
             rightTableHeight: window.innerHeight - 130
        }
       
    },
    mounted(){
        this.loadGridInfo();
        this.loadGridData();
    },
    components:{
        DataGrid:DataGrid
    },
    methods:{
      onRowClick(row){
        let _self=this;
        _self.archiveBackupVisible=true
        setTimeout(()=>{
          _self.loadChildGridInfo();
          _self.loadChildGridData(row);
        },10);
        
      },
       
        pageSizeChange(val){
            this.pageSize = val;
            localStorage.setItem("docPageSize",val);
            _self.loadGridData();
        },
        childPageSizeChange(val){
            this.childPageSize = val;
            localStorage.setItem("docPageSize",val);
            this.loadChildGridData(this.selectedRow);
        },
        
        
        // 分页 当前页改变
        pageChange(val) {
            this.currentPage = val;
            this.loadGridData();
            //console.log('handleCurrentChange', val);
        },
        // 分页 当前页改变
        childPageChange(val) {
            this.childCurrentPage = val;
            this.loadChildGridData(this.selectedRow);
            //console.log('handleCurrentChange', val);
        },
        //查询文档
        searchItem() {
          this.loadGridData();
          // this.loadPageInfo();
        },
        // 加载借阅单表格数据
        loadGridData() {
          let _self = this;
          _self.orderLoading=true;
          var key0 = _self.inputkey;
          if (key0 != "") {
            key0 = " (coding like '%" + key0 + "%' or C_DRAFTER like '%" + key0 + "%') and STATUS='已完成' ";
          }else{
              key0=" STATUS='已完成' "
          }
        
          var m = new Map();
          m.set("gridName", "ArchiveBackup");
          // m.set('folderId',indata.id);
          m.set("condition", key0);
          
          m.set("pageSize", _self.pageSize);
          m.set("pageIndex", (_self.currentPage - 1) * _self.pageSize);
          m.set("orderBy", "");
          // console.log('pagesize:', _self.pageSize);
          axios.post("/dc/getBorrowOrder",JSON.stringify(m))
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
        
            // 加载表格样式
        loadGridInfo() {
          let _self = this;
          _self.orderLoading = true;
          var m = new Map();
          m.set("gridName", "ArchiveBackup");
          m.set("lang", _self.getLang());
          axios.post("/dc/getGridViewInfo",JSON.stringify(m))
            .then(function(response) {
              _self.gridList = response.data.data;
              
              _self.orderLoading = false;
            })
            .catch(function(error) {
              console.log(error);
              _self.orderLoading = false;
            });
        },
         // 加载表格样式
        loadChildGridInfo() {
          let _self = this;
          var m = new Map();
          m.set("gridName", "ArchiveBackupRecord");
          m.set("lang", _self.getLang());
          axios.post("/dc/getGridViewInfo",JSON.stringify(m))
            .then(function(response) {
              _self.childColumnList = response.data.data;
              
            })
            .catch(function(error) {
              console.log(error);
              _self.orderLoading = false;
            });
        },
        
        loadChildGridData(row){
          let _self = this;
          _self.orderLoading=true;
          var key0 = "";
          if (key0 != "") {
            key0 = " (coding like '%" + key0 + "%' or C_DRAFTER like '%" + key0 + "%')  ";
          }else{
              key0=" C_BATCH_CODE='"+row.CODING+"' "
          }
        
          var m = new Map();
          m.set("gridName", "ArchiveBackupRecord");
          // m.set('folderId',indata.id);
          m.set("condition", key0);
          
          m.set("pageSize", _self.childPageSize);
          m.set("pageIndex", (_self.childCurrentPage - 1) * _self.childPageSize);
          m.set("orderBy", "");
          // console.log('pagesize:', _self.pageSize);
          axios.post("/dc/getBorrowOrder",JSON.stringify(m))
            .then(function(response) {
              _self.childDataList = response.data.data;
              _self.childItemCount = response.data.pager.total;
              //console.log(JSON.stringify(response.data.datalist));
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