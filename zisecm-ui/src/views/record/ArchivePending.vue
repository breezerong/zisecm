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
                      @pagechange="pageChange" v-bind:itemCount="itemCount"
                      v-bind:tableHeight="rightTableHeight" :isshowOption="true"
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
             showFields:[],
             innerDataList:[],
             itemDataList:[],
             itemDataListFull:[],
             pageSize: 20,
             currentPage: 1,
             itemCount: 0,
             inputkey: "",
             rightTableHeight: (window.innerHeight - 200)/2,
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
    loadGridData(row) {
      let _self = this;
      _self.innerDataList=[];
      _self.loadGridInfo();
      if (row != null) {
        _self.selectedRow = row;
        
      }
      var key = _self.inputkey;
      if (key != "") {
        key = "coding like '%" + key + "%' or title like '%" + key + "%'";
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
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
        // 加载表格样式
    loadGridInfo() {
      let _self = this;
      _self.loading = true;
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
          _self.showFields = [];
          _self.gridList = response.data.data;
          _self.gridList.forEach(element => {
            if (element.visibleType == 1) {
              _self.showFields.push(element.attrName);
            }
          });
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    }

    }

};
</script>
<style scoped>

</style>