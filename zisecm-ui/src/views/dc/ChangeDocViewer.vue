<template>
   <DataGrid
    ref="DataGrid"
    key="items"
    v-bind:itemDataList="dataList"
    v-bind:columnList="columnList"
    v-bind:itemCount="itemCount"
    v-bind:tableHeight="tableHeight"
    :isshowOption="true"
    @pagesizechange="pageSizeChange"
    @pagechange="pageChange"
  ></DataGrid>
</template>
<script type="text/javascript">
import DataGrid from "@/components/DataGrid";

export default {
  name:"ChangeDocViewer",
  components: {
    DataGrid
  },
  data() {
    return {
      tableHeight: window.innerHeight - 400,
      dataList:[],
      columnList:[],
      itemCount:0,
      currentPage:1,
      pageSize:20
    };
  },
  props: {
    coding:{type:String},
    revision:{type:String}
  },
  mounted() {
   let _self = this
   _self.loadGridInfo();
  },
  methods: {
    loadGridInfo() {
      let _self = this;
      _self.loading = true;
      var m = new Map();
      m.set("gridName", "ChangeGrid");
      m.set("lang", _self.getLang());
      axios.post("/dc/getGridViewInfo",JSON.stringify(m))
        .then(function(response) {
          _self.columnList = response.data.data;
          _self.bindData();
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    bindData(){
      let _self = this;
      var m = new Map();
      m.set("gridName", "ChangeGrid");
      m.set("condition", "CODING='"+_self.coding+"' and REVISION='"+_self.revision+"'");
      m.set("folderId", "");
      m.set("pageSize", _self.pageSize);
      m.set("pageIndex", (_self.currentPage - 1) * _self.pageSize);
      m.set("orderBy", "");
      // console.log('pagesize:', _self.pageSize);
      axios
        .post("/dc/getDocuments", JSON.stringify(m))
        .then(function(response) {
          _self.dataList = response.data.data;
          _self.itemCount = response.data.pager.total;
          //console.log(JSON.stringify(response.data.data));
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    //分页 页数改变
    pageSizeChange(val) {
      let _self = this;
      localStorage.setItem("docPageSize", val);
      _self.bindData()
    },
    // 分页 当前页改变
    pageChange(val) {
      let _self = this;
      this.currentPage = val;
      _self.bindData()
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
