<template>
<div>
  <el-dialog
      :title="$t('application.property')"
      :visible.sync="propertyVisible"
      @close="propertyVisible = false"
      width="90%"
    >
      <ShowProperty
        ref="ShowProperty"
        width="100%"
        v-bind:itemId="selectedItemId"
        v-bind:folderId="currentFolder.id"
        v-bind:typeName="currentFolder.TYPE_NAME"
      ></ShowProperty>
      <div slot="footer" class="dialog-footer">
        <el-button @click="propertyVisible = false">{{$t('application.cancel')}}</el-button>
      </div>
    </el-dialog>
  <el-container>
    <el-header style="height:48px;">
      <el-col :span="5" class="topbar-input">
        <el-input v-model="inputKey" :placeholder="$t('application.placeholderSearch')" @keyup.enter.native="search"></el-input>
      </el-col>
    </el-header>
    <el-main>
      <DataGrid
          ref="mainDataGrid"
          key="main"
          v-bind:itemDataList="itemDataList"
          v-bind:columnList="gridList"
          @pagesizechange="pageSizeChange"
          @pagechange="pageChange"
          v-bind:itemCount="itemCount"
          v-bind:tableHeight="tableHeight"
          v-bind:isshowOption="true" v-bind:isshowSelection ="false"
          v-bind:propertyComponent="this.$refs.ShowProperty"
        ></DataGrid>
    </el-main>
  </el-container>
</div>
</template>

<script type="text/javascript">
import ShowProperty from "@/components/ShowProperty";
import DataGrid from "@/components/DataGrid";

export default {
  components: {
    ShowProperty: ShowProperty,
    DataGrid: DataGrid
  },
  data() {
    return {
        loading:false,
        itemDataList:[],
        itemDataListFull:[],
        currentFolder: [],
        inputKey:'',
        gridList: [],
        folderName:'',
        propertyVisible: false,
        selectedItemId: "",
        itemCount: 0,
        currentPage: 1,
        itemDialogVisible: false,
        pageSize:20,
        tableHeight: window.innerHeight - 130
    };
  },
  mounted() {
   let _self = this
   let cfgName = _self.$route.query.cfgName;
   if(cfgName){
     _self.loadFolderByCfg(cfgName);
   }else{
     let folderId = _self.$route.query.folderId;
     _self.loadFolderById(folderId);
   }
  },
  methods: {
    loadFolderByCfg(cfgName){
      let _self = this;
      _self.loading = true;
      axios.post("/folder/getFolderByConfige",cfgName)
        .then(function(response) {
          if(response.data.code == 1){
            _self.currentFolder = response.data.data;
            _self.loadGridInfo(_self.currentFolder);
          }
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    loadFolderById(folderId){
      let _self = this;
       _self.loading = true;
      axios.post("/folder/getFolderById",folderId)
        .then(function(response) {
          if(response.data.code == 1){
            _self.currentFolder = response.data.data;
            _self.loadGridInfo(_self.currentFolder);
          }
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    loadGridInfo(indata) {
      let _self = this;
      _self.loading = true;
      var m = new Map();
      m.set("gridName", indata.gridView);
      m.set("lang", _self.getLang());
      axios
        .post("/dc/getGridViewInfo", JSON.stringify(m))
        .then(function(response) {
          _self.gridList = response.data.data;
          _self.loading = false;
          _self.loadGridData();
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    // 加载表格数据
    loadGridData() {
      let _self = this;
      var key = _self.inputKey;
      if (key != "" && key != undefined) {
        key = "CODING like '%" + key + "%' or NAME like '%" + key + "%'";
      }else{
        key = "";
      }
      var m = new Map();
      m.set("gridName", _self.currentFolder.gridView);
      m.set("folderId", _self.currentFolder.id);
      m.set("condition", key);
      m.set("pageSize", _self.pageSize);
      m.set("pageIndex", _self.currentPage - 1);
      m.set("orderBy", "");
      // console.log('pagesize:', _self.pageSize);
      axios.post("/dc/getDocuments",JSON.stringify(m))
        .then(function(response) {
          _self.itemDataList = response.data.data;
          _self.itemDataListFull = response.data.data;
          _self.itemCount = response.data.pager.total;
          console.log(JSON.stringify(response.data.data));
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    search(){
      this.loadGridData();
    },
    showFile(indata){
       let condition = indata.ID;
      let href = this.$router.resolve({
        path: '/viewdoc',
        query: {
          id: condition
        }
      });
      window.open(href.href, '_blank');
    },
    //分页 页数改变
    pageSizeChange(val) {
      let _self = this;
      this.pageSize = val;
      localStorage.setItem("docPageSize", val);
      _self.loadGridData()
    },
    // 分页 当前页改变
    pageChange(val) {
      let _self = this;
      this.currentPage = val;
      _self.loadGridData()
    },
    //查看属性
    showItemProperty(indata) {
      let _self = this;
      _self.selectedItemId = indata.ID;
      _self.currentFolder = indata
      _self.propertyVisible = true;
      _self.itemDialogVisible = false;
      if (_self.$refs.ShowProperty) {
        _self.$refs.ShowProperty.myItemId = indata.ID;
        _self.$refs.ShowProperty.loadFormInfo();
      }
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
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.el-header {
  background-color: white;
}
.el-container {
  height: 100%;
}
.el-footer {
  background-color: white;
}
</style>
