<template>
  <div>
    <el-row v-loading="loading">
      <el-col :span="6" class="topbar-button">
        <el-button
          type="primary"
          icon="el-icon-back"
          plain
          size="small"
          @click="onUpdateStatus('产生')"
        >回退</el-button>
        <el-button
          type="primary"
          icon="eel-icon-check"
          plain
          size="small"
          @click="onUpdateStatus('整编')"
        >接收</el-button>
      </el-col>
      <el-col :span="4" class="topbar-input">
        <el-input
          v-model="inputkey"
          :placeholder="$t('message.pleaseInput')+$t('application.keyword')"
          @change="searchItem"
          prefix-icon="el-icon-search"
        ></el-input>
      </el-col>
      <el-col :span="14">&nbsp;</el-col>
    </el-row>
    <el-row>
      <el-col :span="6">
        <DataGrid
          ref="transferDataGrid"
          key="transfer"
          v-bind:isshowPage="false"
          v-bind:isshowOption="true"
          v-bind:isshowicon="false"
     
          v-bind:itemDataList="transferDataList"
          v-bind:columnList="transferColumnList"
          @pagesizechange="handleSizeChange"
          @pagechange="handleCurrentChange"
          v-bind:itemCount="transferCount"
          v-bind:tableHeight="leftTableHeight"
          @rowclick="loadGridData"
          @selectchange="transferselectChange"
        ></DataGrid>
      </el-col>
      <el-col :span="18">
        <DataGrid
          ref="mainDataGrid"
          key="main"
          v-bind:itemDataList="itemDataList"
          v-bind:columnList="gridList"
          @pagesizechange="pageSizeChange"
          @pagechange="pageChange"
          v-bind:isshowOption="true" v-bind:isshowSelection ="false"
          v-bind:itemCount="itemCount"
          @rowclick="beforeShowInnerFile"
          v-bind:tableHeight="rightTableHeight"
          @selectchange="selectChange"
        ></DataGrid>
        <div class="left">
          <span style="float:left;text-align:left;">文件列表</span>
          <DataGrid
            ref="leftDataGrid"
            key="left"
            v-bind:isshowOption="true" v-bind:isshowSelection ="false"
            v-bind:itemDataList="innerDataList"
            v-bind:columnList="innerGridList"
            v-bind:itemCount="innerCount"
            v-bind:tableHeight="rightTableHeight"
            @pagesizechange="innerPageSizeChange"
            @rowclick="selectOneFile"  
            @pagechange="innerPageChange"
            @selectchange="selectInnerChange"
          ></DataGrid>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script type="text/javascript">
import ShowProperty from "@/components/ShowProperty";
import DataGrid from "@/components/DataGrid";
import DataGridleft from "@/components/DataGrid";
//import Prints from '@/components/record/Print'

import "url-search-params-polyfill";

import PrintPage from "@/views/record/PrintPage";
import PrintVolumes from "@/views/record/PrintVolumes";
import AddReuse from "@/views/record/AddReuse";
export default {
  name: "ArchiveReceive",
  permit: 1,
  data() {
    return {
      leftTableHeight: window.innerHeight - 124,
      rightTableHeight: (window.innerHeight - 180)/2,
      printsVisible: false,
      printVolumesVisible: false,
      archiveId: "",
      dataList: [],
      transferColumnList: [],
      showFields: [],
      itemDataList: [],
      itemDataListFull: [],
      innerDataList: [],
      innerDataListFull: [],
      outerDataList: [],
      outerDataListFull: [],
      transferPageSize: 200,
      transferCurrentPage: 1,
      transferDataList: [],
      transferDataListFull: [],
      selectedTypeName: [],
      transferCount: 0,
      reuseVisible: false,
      typeName: "卷盒",
      folderPath: "/表单/移交单",
      selectTransferRow: [],
      gridList: [],
      innerGridList: [],
      currentFolder: [],
      dataListFull: "",
      inputkey: "",
      typeNames: [],
      loading: false,
      dialogName: "",
      outerCurrentPage: 1,
      outerPageSize: 20,
      innerCurrentPage: 1,
      innerPageSize: 20,
      pageSize: 20,
      itemCount: 0,
      innerCount: 0,
      outerCount: 0,
      selectedItemId: 0,
      fileList: [],
      currentPage: 1,
      dialogVisible: false,
      propertyVisible: false,
      showButton: true,
      typeSelectVisible: false,
      selectRow: [],
      importdialogVisible: false,
      selectedItems: [],
      selectedFileId: "",
      selectedOutItems: [],
      selectedInnerItems: [],
      childrenTypes: [],
      uploadUrl: "",
      selectedChildrenType: "",
      selectTransferOneRow:[],
      childrenTypeSelectVisible: false,
      folderAction: "",
      folderDialogVisible: false,
      imageArray: [""],
      imageViewVisible: false,
      imageViewer: Object,
      currentType: "",
      orderBy: "",
      selectedReuses: [],
      columnsInfo: {
        checkAll: true,
        checkedCities: [],
        temCol: [],
        dialogFormVisible: false,
        isIndeterminate: false
      },
      transferForm: {
        id: 0,
        title: "",
        folderPath: "/表单/移交单",
        typeName: "移交单",
        status: "产生"
      },
      formLabelWidth: "120px",
      defaultProps: {
        children: "children",
        label: "name"
      }
    };
  },
  watch: {
    showFields(val, oldVal) {
      //普通的watch监听
      //console.log("a: "+val, oldVal);
      let _self = this;
      _self.gridList.forEach(element => {
        element.visibleType = 2;
      });
      val.forEach(element => {
        let item = _self.getgriditem(element);
        if (item) {
          //console.log(element);
          item.visibleType = 1;
        }
      });
    }
  },
  created() {
    console.log("档案移交");

    this.getTypeNames("innerTransferDocType");
    this.loadTransferGridInfo();
    this.loadTransferGridData();
    this.loadInnerGridInfo();

    this.loadGridInfo();
  },
  methods: {
    addReuseToVolume() {
      let _self = this;
      _self.selectedReuses = _self.$refs.addReuseModel.selectedRow;

      var params = new Map();
      var m = [];
      let tab = _self.selectedReuses;

      var i;
      for (i in tab) {
        m.push(tab[i]["ID"]);
      }
      params.set("cids", m);
      params.set("id", _self.archiveId);
      console.log(JSON.stringify(m));
      _self
        .axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data: JSON.stringify(params),
          url: "/dc/addReuseToVolume"
        })
        .then(function(response) {
          _self.loadGridData(null);

          _self.showInnerFile(null);
          _self.reuseVisible = false;
          // _self.$message("添加成功！");
          _self.$message({
              showClose: true,
              message: "添加成功！",
              duration: 2000,
              type: 'success'
            });
        })
        .catch(function(error) {
          // _self.$message("添加失败！");
          _self.$message({
              showClose: true,
              message: "添加失败！",
              duration: 5000,
              type: 'error'
            });
          console.log(error);
        });
    },

    getTypeNames(keyName) {
      let _self = this;
      axios
        .post("/dc/getParameters", keyName)
        .then(function(response) {
          _self.typeNames = response.data.data.innerTransferDocType;
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    getTypeNamesByMainList(keyName) {
      let _self = this;
      axios
        .post("/dc/getOneParameterValue", keyName)
        .then(function(response) {
          _self.childrenTypes = response.data.data;
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    confirmShow() {
      let _self = this;
      _self.gridList.forEach(element => {
        element.visibleType = 2;
      });
      _self.showFields.forEach(element => {
        let item = _self.getgriditem(element);
        if (item) {
          item.visibleType = 1;
        }
      });
      this.columnsInfo.dialogFormVisible = false;
    },
    dialogFormShow() {
      this.columnsInfo.dialogFormVisible = true;
    },
    selectOneFile(row) {
      let _self = this;
      if (_self.selectRow) {
        _self.selectedFileId = row.ID;
      }
    },
    beforeShowInnerFile(row){
      this.innerCurrentPage=1;
      this.showInnerFile(row);
    },
    showInnerFile(row) {
      let _self = this;
      if (row != null) {
        _self.selectRow = row;
      }
      _self.selectedChildrenType = [];
      _self.getTypeNamesByMainList(_self.selectRow.SUB_TYPE);
      // var key =row.CODING;
      // if(key!=""){
      //   key = "coding = '"+key+"' "; //此处需要修改
      // }

      // if(_self.selectRow.C_LOCK_STATUS==='已封卷')
      // {
      //   _self.showButton=false;
      // }else
      // {
      //    _self.showButton=true;
      // }
      _self.loading=true;
      _self.loadInnerGridInfo();
      var m = new Map();
      m.set("gridName", "ArchiveGrid");
      m.set("condition", "");
      if (_self.selectRow) {
        _self.archiveId = _self.selectRow.ID;
      }
      m.set("id", _self.archiveId);
      m.set("pageSize", _self.pageSize);
      m.set("pageIndex", (_self.innerCurrentPage - 1) * _self.innerPageSize);
      m.set("orderBy", "");
      // console.log('pagesize:', _self.pageSize);
      axios
        .post("/dc/getDocuByRelationParentId", JSON.stringify(m))
        .then(function(response) {
          _self.innerDataList = response.data.data;
          _self.innerDataListFull = response.data.data;
          _self.innerCount = response.data.pager.total;
          //console.log(JSON.stringify(response.data.datalist));
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },

    renderContent: function(h, { node, data, store }) {
      console.log(data);
      if (data.extended) {
        return (
          <span>
            <i class="el-icon-folder-opened"></i>
            <span style="padding-left: 4px;">{node.label}</span>
          </span>
        );
      } else {
        return (
          <span>
            <i class="el-icon-folder"></i>
            <span style="padding-left: 4px;">{node.label}</span>
          </span>
        );
      }
    },
    showNewWindow(id) {
      let condition = this.id;
      let href = this.$router.resolve({
        name: "docviewer",
        query: {
          id: condition,
          token: this.getToken()
        }
      });
      console.log(href);
      window.open(href.href, "_blank");
    },
    getgriditem(attrName) {
      let _self = this;
      let ret = null;
      _self.gridList.forEach(element => {
        if (element.attrName == attrName) {
          ret = element;
          return;
        }
      });
      return ret;
    },
    handleCheckChange(data, checked, indeterminate) {
      data.visibleType = checked ? 1 : 0;
    },
    inited(viewer) {
      this.imageViewer = viewer;
    },
    onImageClick() {
      this.imageViewVisible = false;
    },
    formatImage(indata) {
      var url = "./static/img/format/f_" + indata + "_16.gif";
      return url;
    },
    // 表格行选择
    transferselectChange(val) {
      // console.log(JSON.stringify(val));
      this.selectTransferRow = val;
    },
    // 表格行选择
    selectChange(val) {
      // console.log(JSON.stringify(val));
      this.selectedItems = val;
    },
    // 表格行选择
    selectOutChange(val) {
      // console.log(JSON.stringify(val));
      this.selectedOutItems = val;
    },
    selectInnerChange(val) {
      this.selectedInnerItems = val;
    },
    onMoveUp() {
      let _self = this;
      if (_self.selectedInnerItems.length != 1) {
        // _self.$message("请选择一条数据！");
        _self.$message({
              showClose: true,
              message: "请选择一条数据！",
              duration: 2000,
              type: 'warning'
            });
        return;
      }
      var m = new Map();
      m.set("parentId", _self.archiveId);
      m.set("childId", _self.selectedInnerItems[0].ID);
      _self
        .axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data: JSON.stringify(m),
          url: "/dc/moveUp"
        })
        .then(function(response) {
          let code = response.data.code;
          //console.log(JSON.stringify(response));
          if (code == 1) {
            _self.showInnerFile(null);
          } else {
            // _self.$message(response.data.message);
            _self.$message({
              showClose: true,
              message: response.data.message,
              duration: 5000,
              type: 'error'
            });
          }
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    onMoveDown() {
      let _self = this;
      if (_self.selectedInnerItems.length != 1) {
        // _self.$message("请选择一条数据！");
        _self.$message({
              showClose: true,
              message: "请选择一条数据！",
              duration: 2000,
              type: 'warning'
            });
        return;
      }
      var m = new Map();
      m.set("parentId", _self.archiveId);
      m.set("childId", _self.selectedInnerItems[0].ID);
      _self
        .axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data: JSON.stringify(m),
          url: "/dc/moveDown"
        })
        .then(function(response) {
          let code = response.data.code;
          //console.log(JSON.stringify(response));
          if (code == 1) {
            _self.showInnerFile(null);
          } else {
            // _self.$message(response.data.message);
            _self.$message({
              showClose: true,
              message: response.data.message,
              duration: 5000,
              type: 'error'
            });
          }
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    sortchange(column) {
      console.log(JSON.stringify(column));
      console.log(column.column.property);
      console.log(column.column.order); //ascending, descending
      this.orderBy =
        column.column.property + column.column.order == "ascending"
          ? " ASC"
          : " DESC";
    },
    loadInnerGridInfo() {
      let _self = this;
      _self.loading = true;
      var m = new Map();
      m.set("gridName", "ArchiveGrid");
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
          _self.innerGridList = response.data.data;
          _self.rightTableHeight = "100%";
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    // 分页 当前页改变
    transferPageChange(val) {
      this.transferCurrentPage = val;
      this.loadTransferGridData();
      //console.log('handleCurrentChange', val);
    },
    transferPageSizeChange(val) {
      this.transferPageSize = val;
      localStorage.setItem("docPageSize", val);
      this.loadTransferGridData();
    },
    // 加载移交单表格样式
    loadTransferGridInfo() {
      let _self = this;
      _self.loading = true;
      var m = new Map();
      m.set("gridName", "DeliveryGrid");
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
          _self.transferColumnList = response.data.data;
          _self.transferColumnList.forEach(element => {
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
    },
    loadTransferGridData() {
      let _self = this;
      _self.loading = true;
      var m = new Map();
      m.set("gridName", "DeliveryGrid");
      m.set("condition", " status='归档'");
      // m.set('folderId',indata.id);
      // m.set('status','产生')
      m.set("folderPath", "/表单/移交单");
      m.set("pageSize", _self.transferPageSize);
      m.set(
        "pageIndex",
        (_self.transferCurrentPage - 1) * _self.transferPageSize
      );
      m.set("orderBy", "");
      // console.log('pagesize:', _self.pageSize);
      _self.loading=true;
      _self
        .axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data: JSON.stringify(m),
          url: "/dc/getTransfer"
        })
        .then(function(response) {
          _self.transferDataList = response.data.data;
          _self.transferDataListFull = response.data.data;
          _self.transferCount = response.data.pager.total;
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
      m.set("gridName", "ArchiveGrid");
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
          _self.rightTableHeight = "100%";
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    removeFromArchive() {
      let _self = this;
      if (_self.archiveId === "") {
        alert("请选择一条案卷！");
        return;
      }

      if (_self.selectedInnerItems.length === 0) {
        alert("请选择一条或多条卷内文件！");
        return;
      }
      var m = new Map();
      m.set("archiveId", _self.archiveId);
      let innerIds = new Array();
      for (let i = 0; i < _self.selectedInnerItems.length; i++) {
        innerIds.push(_self.selectedInnerItems[i].ID);
      }
      m.set("fileIds", innerIds);

      // console.log('pagesize:', _self.pageSize);
      _self
        .axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data: JSON.stringify(m),
          url: "/dc/removeFromArchive"
        })
        .then(function(response) {
          _self.showInnerFile(null);
          _self.loadGridOutData();

          //console.log(JSON.stringify(response.data.datalist));
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    // 添加至archive
    addToArchive() {
      let _self = this;
      if (_self.archiveId === "") {
        alert("请选择一条案卷！");
        return;
      }

      if (_self.selectedOutItems.length === 0) {
        alert("请选择一条或多条未组卷文件！");
        return;
      }
      var m = new Map();
      m.set("archiveId", _self.archiveId);
      let outIds = new Array();
      for (let i = 0; i < _self.selectedOutItems.length; i++) {
        outIds.push(_self.selectedOutItems[i].ID);
      }
      m.set("fileIds", outIds);

      // console.log('pagesize:', _self.pageSize);
      _self
        .axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data: JSON.stringify(m),
          url: "/dc/addToArchive"
        })
        .then(function(response) {
          _self.showInnerFile(null);
          _self.loadGridOutData();

          //console.log(JSON.stringify(response.data.datalist));
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    // 加载表格数据
    loadGridData(row) {
      let _self = this;
      _self.innerDataList=[];
      _self.loadGridInfo();

      if (row != null) {
        _self.selectTransferOneRow = row;
      }
      var key = _self.inputkey;
      if (key != "") {
        key = "coding like '%" + key + "%' or title like '%" + key + "%'";
      }
      var m = new Map();
      m.set("gridName", "ArchiveGrid");
      // m.set('folderId',indata.id);
      m.set("condition", key);
      m.set("id", _self.selectTransferOneRow.ID);
      m.set("pageSize", _self.pageSize);
      m.set("pageIndex", (_self.currentPage - 1) * _self.pageSize);
      m.set("orderBy", "");
      // console.log('pagesize:', _self.pageSize);
      _self.loading=true;
      _self
        .axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data: JSON.stringify(m),
          url: "/dc/getDocuByRelationParentId"
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
    // 分页 页数改变
    handleSizeChange(val) {
      this.pageSize = val;
      localStorage.setItem("docPageSize", val);
      this.loadGridData();
      //console.log('handleSizeChange', val);
    },
    // 分页 当前页改变
    outerPageChange(val) {
      this.outerCurrentPage = val;
      this.loadGridOutData();
      //console.log('handleCurrentChange', val);
    },
    outerPageSizeChange(val) {
      this.outerPageSize = val;
      localStorage.setItem("docPageSize", val);
      this.loadGridOutData();
    },
    // 分页 当前页改变
    innerPageChange(val) {
      this.innerCurrentPage = val;
      this.showInnerFilepageChange();
      //console.log('handleCurrentChange', val);
    },
    innerPageSizeChange(val) {
      this.innerPageSize = val;
      localStorage.setItem("docPageSize", val);
      this.showInnerFilepageChange();
    },
    showInnerFilepageChange() {
      let _self = this;

      // if(_self.selectRow.C_LOCK_STATUS==='已封卷')
      // {
      //   _self.showButton=false;
      // }else
      // {
      //    _self.showButton=true;
      // }
      _self.loadGridInfo();
      var m = new Map();
      m.set("gridName", "ArchiveGrid");
      m.set("condition", "");
      if (_self.selectRow) {
        _self.archiveId = _self.selectRow.ID;
      }
      m.set("id", _self.archiveId);
      m.set("pageSize", _self.innerPageSize);
      m.set("pageIndex", (_self.innerCurrentPage - 1) * _self.innerPageSize);
      m.set("orderBy", "");
      // console.log('pagesize:', _self.pageSize);
      _self
        .axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data: JSON.stringify(m),
          url: "/dc/getDocuByRelationParentId"
        })
        .then(function(response) {
          _self.innerDataList = response.data.data;
          _self.innerDataListFull = response.data.data;
          _self.innerCount = response.data.pager.total;
          //console.log(JSON.stringify(response.data.datalist));
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    pageSizeChange(val) {
      this.pageSize = val;
      localStorage.setItem("docPageSize", val);
      this.loadGridData();
    },
    // 分页 当前页改变
    pageChange(val) {
      this.currentPage = val;
      this.loadGridData();
      //console.log('handleCurrentChange', val);
    },
    // 分页 当前页改变
    handleCurrentChange(val) {
      this.currentPage = val;
      this.loadGridData();
      //console.log('handleCurrentChange', val);
    },
    
    // 保存文档
    saveItem() {
      if (!this.$refs.ShowProperty.validFormValue()) {
        return;
      }
      let _self = this;
      var m = new Map();
      let dataRows = this.$refs.ShowProperty.dataList;
      var i;
      for (i in dataRows) {
        if (dataRows[i].attrName && dataRows[i].attrName != "") {
          if (
            dataRows[i].attrName != "FOLDER_ID" &&
            dataRows[i].attrName != "ID"
          ) {
            m.set(dataRows[i].attrName, dataRows[i].defaultValue);
          }
        }
      }
      if (_self.$refs.ShowProperty.myItemId != "") {
        m.set("ID", _self.$refs.ShowProperty.myItemId);
      }
      if (_self.$refs.ShowProperty.myTypeName != "") {
        m.set("TYPE_NAME", _self.$refs.ShowProperty.myTypeName);
        m.set("folderPath", _self.$refs.ShowProperty.folderPath);
        m.set("transferId", _self.$refs.ShowProperty.parentDocId);
      }
      let formdata = new FormData();
      formdata.append("metaData", JSON.stringify(m));

      if (_self.$refs.ShowProperty.file != "") {
        //console.log(_self.file);
        formdata.append("uploadFile", _self.$refs.ShowProperty.file.raw);
      }
      // console.log(JSON.stringify(m));
      if (_self.$refs.ShowProperty.myItemId == "") {
        _self
          .axios({
            headers: {
              "Content-Type": "multipart/form-data"
              // x-www-form-urlencoded'
              //"Content-Type": "application/json;charset=UTF-8"
            },
            method: "post",
            data: formdata,
            url: "/dc/newArchiveOrDocument"
          })
          .then(function(response) {
            let code = response.data.code;
            //console.log(JSON.stringify(response));
            if (code == 1) {
              _self.$message("创建成功!");
              _self.propertyVisible = false;

              _self.loadTransferGridData();
              _self.loadGridData(null);
              _self.showInnerFile(null);
            } else {
              _self.$message("新建失败!");
            }
          })
          .catch(function(error) {
            _self.$message("新建失败!");
            console.log(error);
          });
      } else {
        _self
          .axios({
            headers: {
              "Content-Type": "application/json;charset=UTF-8"
            },
            method: "post",
            data: JSON.stringify(m),
            url: "/dc/saveDocument"
          })
          .then(function(response) {
            let code = response.data.code;
            //console.log(JSON.stringify(response));
            if (code == 1) {
              _self.$emit("onSaved", "update");
            } else {
              _self.$message("保存失败!");
            }
          })
          .catch(function(error) {
            _self.$message("保存失败!");
            console.log(error);
          });
      }
    },
    // 保存结果事件
    onSaved(indata) {
      if (indata == "update") {
        this.$message(this.$t("message.saveSuccess"));
      } else {
        this.$message("新建成功!");
      }
      this.propertyVisible = false;
      this.loadGridData();
      this.loadGridOutData();
    },
    // 删除文档事件
    onDeleleItem() {
      let _self = this;
      this.$confirm(
        _self.$t("message.deleteInfo"),
        _self.$t("application.info"),
        {
          confirmButtonText: _self.$t("application.ok"),
          cancelButtonText: _self.$t("application.cancel"),
          type: "warning"
        }
      )
        .then(() => {
          _self.deleleItem();
        })
        .catch(() => {
          // this.$message({
          //   type: 'info',
          //   message: '已取消删除'
          // });
        });
    },
    // 删除文档
    deleleItem() {
      let _self = this;
      var m = [];
      let tab = _self.selectedItems;

      var i;
      for (i in tab) {
        m.push(tab[i]["ID"]);
      }
      console.log(JSON.stringify(m));
      _self
        .axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data: JSON.stringify(m),
          url: "/dc/delDocumentAndRelation"
        })
        .then(function(response) {
          _self.loadGridData(null);

          _self.showInnerFile(null);
          _self.$message(_self.$t("message.deleteSuccess"));
        })
        .catch(function(error) {
          _self.$message(_self.$t("message.deleteFailured"));
          console.log(error);
        });
    },
    // 删除文档事件
    onDeleleFileItem() {
      let _self = this;
      this.$confirm(
        _self.$t("message.deleteInfo"),
        _self.$t("application.info"),
        {
          confirmButtonText: _self.$t("application.ok"),
          cancelButtonText: _self.$t("application.cancel"),
          type: "warning"
        }
      )
        .then(() => {
          _self.deleleFileItem();
        })
        .catch(() => {
          // this.$message({
          //   type: 'info',
          //   message: '已取消删除'
          // });
        });
    },
    
    // 删除文档
    deleleFileItem() {
      let _self = this;
      var m = [];
      let tab = _self.selectedInnerItems;
      var i;
      for (i in tab) {
        m.push(tab[i]["ID"]);
      }
      console.log(JSON.stringify(m));
      _self
        .axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data: JSON.stringify(m),
          url: "/dc/delDocumentAndRelation"
        })
        .then(function(response) {
          _self.showInnerFile(null);
          _self.$message(_self.$t("message.deleteSuccess"));
        })
        .catch(function(error) {
          _self.$message(_self.$t("message.deleteFailured"));
          console.log(error);
        });
    },
    // 查看属性
    showItemProperty(indata) {
      let _self = this;
      _self.selectedItemId = indata.ID;
      _self.propertyVisible = true;
      if (_self.$refs.ShowProperty) {
        _self.dialogName = indata.TYPE_NAME;
        _self.$refs.ShowProperty.myItemId = indata.ID;
        _self.$refs.ShowProperty.loadFormInfo();
      }
    },
    // 查看内容
    showItemContent(indata) {
      let _self = this;
      _self.imageArray = [];
      _self.currentType = indata.FORMAT_NAME;
      // 拦截器会自动替换成目标url
      _self.imageArray[0] =
        "/dc/getContent?id=" +
        indata.ID +
        "&token=" +
        sessionStorage.getItem("access-token");
      if (_self.currentType == "pdf") {
        window.open(
          "./static/pdfviewer/web/viewer.html?file=" +
            encodeURIComponent(_self.imageArray[0]) +
            "&.pdf"
        );
      } else {
        _self.imageViewVisible = true;
      }
    },
    closepdf() {
      this.imageViewVisible = false;
    },
    // 保存移交单
    saveTransfer(indata) {
      let _self = this;
      if (_self.folderAction == _self.$t("application.newTransfer")) {
        _self.newFolder(indata);
      }
    },
    
    onUpdateStatus(statusStep) {
      let _self = this;
      if (
        _self.selectTransferRow == null ||
        _self.selectTransferRow.length == 0
      ) {
        // _self.$message("请选择数据！");
        _self.$message({
              showClose: true,
              message: "请选择数据！",
              duration: 2000,
              type: 'warning'
            });
        return;
      }
      let tab = _self.selectTransferRow;
      var params = new Map();
      var m = [];
      var i;
      for (i in tab) {
        m.push(tab[i]["ID"]);
      }
      params.set("ids", m);
      params.set("status", statusStep);
      console.log(JSON.stringify(m));
      _self.loading=true;
      _self
        .axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data: JSON.stringify(params),
          url: "/dc/updatestatus"
        })
        .then(function(response) {
          _self.loading=false;
          _self.loadTransferGridData();
          // _self.loadGridData(null);

          // _self.showInnerFile(null);
          _self.itemDataList=[];
          _self.innerDataList=[];
          // _self.$message("操作成功");
           _self.$message({
              showClose: true,
              message: "操作成功！",
              duration: 2000,
              type: 'success'
            });
        })
        .catch(function(error) {
          // _self.$message("操作失败");
          _self.$message({
              showClose: true,
              message: "操作失败",
              duration: 5000,
              type: 'error'
            });
          console.log(error);
        });
    },

    //归档
    onArchived() {
      let _self = this;
      if (
        _self.selectTransferRow == null ||
        _self.selectTransferRow.length == 0
      ) {
        _self.$message("请选择数据！");
        return;
      }

      let tab = _self.selectTransferRow;
      var m = [];
      var i;
      for (i in tab) {
        if (tab[i]["STATUS"] != "产生") {
          _self.$message("移交单" + tab[i]["CODING"] + "已提交不能再次提交！");
          return;
        }
        m.push(tab[i]["ID"]);
      }
      console.log(JSON.stringify(m));
      _self
        .axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data: JSON.stringify(m),
          url: "/dc/archived"
        })
        .then(function(response) {
          _self.loadTransferGridData();
          _self.loadGridData(null);

          _self.showInnerFile(null);
          _self.$message("操作成功");
        })
        .catch(function(error) {
          _self.$message("操作失败");
          console.log(error);
        });
    },
    // 编辑文件夹事件
    onEditFolder() {
      if (!this.currentFolder || !this.currentFolder.id) {
        this.$message(this.$t("message.pleaseSelectFolder"));
        return;
      }
      this.folderAction =
        this.$t("application.edit") + this.$t("application.folder");
      this.folderForm = this.currentFolder;
      this.folderDialogVisible = true;
    },
    onDeleteTransfer() {
      let _self = this;
      if (!_self.selectTransferRow) {
        // _self.$message(_self.$t("message.pleaseSelectTransfer"));
        _self.$message({
                showClose: true,
                message: _self.$t("message.pleaseSelectTransfer"),
                duration: 2000,
                type: "warning"
              });
        return;
      }

      _self
        .$confirm(
          _self.$t("message.deleteInfo"),
          _self.$t("application.info"),
          {
            confirmButtonText: _self.$t("application.ok"),
            cancelButtonText: _self.$t("application.cancel"),
            type: "warning"
          }
        )
        .then(() => {
          let tab = _self.selectTransferRow;
          var m = [];
          var i;
          for (i in tab) {
            if (tab[i]["STATUS"] != "产生") {
              // _self.$message("移交单" + tab[i]["CODING"] + "已提交不能删除！");
              _self.$message({
                showClose: true,
                message: "移交单" + tab[i]["CODING"] + "已提交不能删除！",
                duration: 2000,
                type: "warning"
              });
              return;
            }
            m.push(tab[i]["ID"]);
          }
          console.log(JSON.stringify(m));
          _self
            .axios({
              headers: {
                "Content-Type": "application/json;charset=UTF-8"
              },
              method: "post",
              data: JSON.stringify(m),
              url: "/dc/delDocumentAndRelation"
            })
            .then(function(response) {
              _self.loadTransferGridData();
              _self.loadGridData(null);

              _self.showInnerFile(null);
              // _self.$message(_self.$t("message.deleteSuccess"));
              _self.$message({
                showClose: true,
                message: _self.$t("message.deleteSuccess"),
                duration: 2000,
                type: "success"
              });
            })
            .catch(function(error) {
              // _self.$message(_self.$t("message.deleteFailured"));
              _self.$message({
                showClose: true,
                message: _self.$t("message.deleteFailured"),
                duration: 5000,
                type: "error"
              });
              console.log(error);
            });
        })
        .catch(() => {
          // this.$message({
          //   type: 'info',
          //   message: '已取消删除'
          // });
        });
    },
    onDeleleFolder() {
      let _self = this;
      if (!_self.currentFolder || !_self.currentFolder.id) {
        // _self.$message(_self.$t("message.pleaseSelectFolder"));
        _self.$message({
                showClose: true,
                message: _self.$t("message.pleaseSelectFolder"),
                duration: 2000,
                type: "warning"
              });
        return;
      }
      _self
        .$confirm(
          _self.$t("message.deleteInfo"),
          _self.$t("application.info"),
          {
            confirmButtonText: _self.$t("application.ok"),
            cancelButtonText: _self.$t("application.cancel"),
            type: "warning"
          }
        )
        .then(() => {
          _self.delFolder();
        })
        .catch(() => {
          // this.$message({
          //   type: 'info',
          //   message: '已取消删除'
          // });
        });
    },
    handleChange(file, fileList) {
      this.fileList = fileList;
    },
    getFormData() {
      let _self = this;
      let formdata = new FormData();
      var data = {};
      data["ID"] = _self.selectedFileId;
      formdata.append("metaData", JSON.stringify(data));
      _self.fileList.forEach(function(file) {
        //console.log(file.name);
        formdata.append("uploadFile", file.raw, file.name);
      });
      return formdata;
    },
    //上传文件
    uploadData() {
      let _self = this;
      let formdata = _self.getFormData();
      console.log("UploadData getData");
      console.log(formdata);
      _self
        .axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          datatype: "json",
          method: "post",
          data: formdata,
          url: _self.uploadUrl
        })
        .then(function(response) {
          _self.importdialogVisible = false;
          // _self.refreshData();
          // _self.$message("导入成功!");
          _self.$message({
              showClose: true,
              message: "导入成功!",
              duration: 2000,
              type: 'success'
            });
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    //查询文档
    searchItem() {
      this.loadGridData();
      this.loadPageInfo();
    }
  },
  components: {
    ShowProperty: ShowProperty,
    PrintPage: PrintPage,
    PrintVolumes: PrintVolumes,
    DataGrid: DataGrid,
    AddReuse: AddReuse
    //Prints:Prints
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
body,
html {
  height: 100%;
  margin: 0px;
  padding: 0px;
  overflow: hidden;
}
.left,
.right {
  width: 100%;
}
.middle {
  width: 5%;
}
.left,
.middle,
.right {
  float: left;
  height: 100%;
}

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
.el-select-dropdown.is-multiple .el-select-dropdown__item.selected::after {
  position: absolute;
  right: 2px;
  font-family: element-icons;
  content: "\E6DA";
  font-size: 12px;
  font-weight: 700;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}
</style>
