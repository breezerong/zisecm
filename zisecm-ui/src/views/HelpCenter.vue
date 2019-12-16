<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-breadcrumb>
          <el-breadcrumb-item>帮助中心</el-breadcrumb-item>
        </el-breadcrumb>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24">
        <el-table
          :height="tableHeight"
          :data="itemDataList"
          border
          v-loading="loading"
          @selection-change="selectChange"
          @sort-change="sortChange"
          style="width: 100%"
        >
          <el-table-column :label="$t('field.indexNumber')" width="60">
            <template slot-scope="scope">
              <span>{{scope.$index+1}}</span>
            </template>
          </el-table-column>
          <el-table-column width="40">
            <template slot-scope="scope">
              <img :src="'./static/img/format/f_'+scope.row.FORMAT_NAME+'_16.gif'" border="0" />
            </template>
          </el-table-column>
          <div v-for="(citem,idx) in gridList">
            <div v-if="citem.visibleType==1">
              <div v-if="(citem.width+'').indexOf('%')>0">
                <el-table-column
                  :label="citem.label"
                  :prop="citem.attrName"
                  :min-width="citem.width"
                  :sortable="citem.allowOrderby"
                >
                  <template slot-scope="scope">
                    <div v-if="citem.attrName.indexOf('DATE')>0">
                      <span>{{dateFormat(scope.row[citem.attrName])}}</span>
                    </div>
                    <div v-else>
                      <span>{{scope.row[citem.attrName]}}</span>
                    </div>
                  </template>
                </el-table-column>
              </div>
              <div v-else>
                <el-table-column
                  :label="citem.label"
                  :width="citem.width"
                  :prop="citem.attrName"
                  :sortable="citem.allowOrderby"
                >
                  <template slot-scope="scope">
                    <div v-if="citem.attrName.indexOf('DATE')>0">
                      <span>{{datetimeFormat(scope.row[citem.attrName])}}</span>
                    </div>
                    <div v-else>
                      <span>{{scope.row[citem.attrName]}}</span>
                    </div>
                  </template>
                </el-table-column>
              </div>
            </div>
          </div>
          <el-table-column :label="$t('application.operation')" width="200">
            <template slot-scope="scope">
              <el-button
                type="primary"
                plain
                size="small"
                :title="$t('application.viewContent')"
                icon="el-icon-picture-outline"
                @click="showItemContent(scope.row)"
              ></el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
  </div>
</template>
<script>
export default {
  name: "HelpCenter",
  data() {
    return {
      imageFormat: "jpg,jpeg,bmp,gif,png",
      itemDataList: [],
      itemDataListFull: [],
      gridList: [],
      currentFolder: "",
      loading: false,
      tableHeight: window.innerHeight - 80,
      imageViewVisible: false
    };
  },
  mounted() {
    let _self = this;
    _self.loading = true;
    axios.post("/folder/getFolderByConfige","HelpFolder")
      .then(function(response) {
        _self.currentFolder = response.data.data;
        _self.loading = false;
        _self.loadGridInfo(_self.currentFolder);
        _self.loadGridData(_self.currentFolder);
      })
      .catch(function(error) {
        console.log(error);
        _self.loading = false;
      });
  },
  methods: {
    // 加载表格样式
    loadGridInfo(indata) {
      let _self = this;
      _self.loading = true;
      var m = new Map();
      m.set("gridName", indata.gridView);
      m.set("lang", this.getLang());
      axios.post("/dc/getGridViewInfo",JSON.stringify(m))
        .then(function(response) {
          _self.gridList = response.data.data;
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    // 加载表格数据
    loadGridData(indata) {
      let _self = this;

      var m = new Map();
      m.set("gridName", indata.gridView);
      m.set("folderId", indata.id);
      m.set("condition", "");
      m.set("pageSize", 50);
      m.set("pageIndex", 0);
      m.set("orderBy", "");
      // console.log('pagesize:', _self.pageSize);
      axios.post("/dc/getDocuments",JSON.stringify(m))
        .then(function(response) {
          _self.itemDataList = response.data.data;
          _self.itemDataListFull = response.data.data;
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    showItemContent(indata) {
      let _self = this;
      _self.imageArray = [];
      _self.currentType = indata.FORMAT_NAME;
      // 拦截器会自动替换成目标url
      _self.imageArray[0] =
        "/zisecm/dc/getContent?id=" +
        indata.ID +
        "&token=" +
        sessionStorage.getItem("access-token");
      // if (_self.currentType == "pdf") {
      //   window.open(
      //     "./static/pdfviewer/web/viewer.html?file=" +
      //       encodeURIComponent(_self.imageArray[0]) +
      //       "&.pdf"
      //   );
      // } else {
        let condition = indata.ID;
        let href = this.$router.resolve({
          path: '/viewdoc',
          query: {
            id: condition
            //token: sessionStorage.getItem('access-token')
          }
        });
        window.open(href.href, '_blank');
      // }
    },
    selectChange(val) 
    {
      // console.log(JSON.stringify(val));
      //this.selectedItems = val;
    },
    sortChange(column){
      //console.log(JSON.stringify(column));
      //console.log(column.column.property);
      //console.log(column.column.order);//ascending, descending
      //this.orderBy = column.column.property+ column.column.order=="ascending"?" ASC":" DESC";
    },
  }
};
</script>

<style>
</style>
