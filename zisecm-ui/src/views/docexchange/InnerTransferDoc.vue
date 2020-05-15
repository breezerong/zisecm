<template>
  <div>
    <el-dialog :visible.sync="showAddfile" :append-to-body='true' width="80%">
      <AddFile ref="addfile" ></AddFile>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addReuseToVolume">{{$t('application.save')}}</el-button>
        <el-button @click="showAddfile = false">{{$t('application.cancel')}}</el-button>
      </div>
    </el-dialog>

    <el-dialog :visible.sync="typeSelectVisible" :append-to-body='true'>
      <el-form>
        <el-form-item :label="$t('application.fileType')" :rules="[{required:true,message:'必填',trigger:'blur'}]">
          <el-select
            name="selectName"
            v-model="selectedTypeName"
            placeholder="$t('application.selectFileType')"
            style="display:block;"
          >
            <div v-for="(name,nameIndex) in typeNames" :key="'T_'+nameIndex">
              <el-option :label="name" :value="name" :key="nameIndex"></el-option>
            </div>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button
          @click="typeSelectVisible=false;newArchiveItem(selectedTypeName,selectedOneTransfer)"
        >{{$t('application.ok')}}</el-button>
      </div>
    </el-dialog>
    <el-dialog 
        :title="dialogName+$t('application.property')"
        :visible.sync="upgradepropertyVisible"
        @close="upgradepropertyVisible = false"
        width="80%"
        :append-to-body='true'>
      <DialogProperties
         ref="upgradeproperties"
        @onSaved="onSaved"
        width="100%"
        v-bind:itemId="newDocId"
       >
      
      </DialogProperties>
      <div slot="footer" class="dialog-footer">
        <el-button @click="saveUpgradeItem">{{$t('application.save')}}</el-button>
        <el-button @click="upgradepropertyVisible = false">{{$t('application.cancel')}}</el-button>
      </div>
    </el-dialog>

    <el-dialog
      :title="dialogName+$t('application.property')"
      :visible.sync="propertyVisible"
      @close="propertyVisible = false"
      width="80%"
      :append-to-body='true'
    >
      <ShowProperty
        ref="ShowProperty"
        @onSaved="onSaved"
        width="100%"
        :folderPath="folderPath"
        v-bind:itemId="selectedItemId"
        v-bind:typeName="typeName"
      ></ShowProperty>
      <div slot="footer" class="dialog-footer">
        <el-button @click="saveItem">{{$t('application.save')}}</el-button>
        <el-button @click="propertyVisible = false">{{$t('application.cancel')}}</el-button>
      </div>
    </el-dialog>
    
    <el-row v-loading="loading">
      
      <el-col :span="4" class="topbar-input">
        <el-input
          v-model="inputkey"
          :placeholder="$t('message.pleaseInput')+$t('application.keyword')"
          @change="searchItem"
          prefix-icon="el-icon-search"
        ></el-input>
      </el-col>

      <el-col :span="13" class="topbar-button">
        &nbsp; 
        <el-button
          type="primary"
          plain
          size="small"
          icon="el-icon-edit"
          @click="beforeCreateFile()"
        >{{$t('application.createFileToTransfer')}}</el-button>
        
        <el-button
          type="primary"
          plain
          size="small"
          icon="el-icon-edit"
          @click="showAddfile=true"
        >{{$t('application.addToTransfer')}}</el-button>
        <el-button
          type="primary"
          plain
          size="small"
          icon="el-icon-delete"
          @click="removeFromArchive"
        >{{$t('application.removeFromTransfer')}}</el-button>
        <el-button
          type="primary"
          plain
          size="small"
          icon="el-icon-delete"
          @click="addToShoppingCar(selectedInnerItems)"
        >{{$t('application.addToShoppingCart')}}</el-button>
      </el-col>
    </el-row>
    <el-row>
      <el-col>
        <DataGrid
          ref="mainDataGrid"
          key="main"
          v-bind:itemDataList="itemDataList"
          v-bind:columnList="gridList"
          @upgradeFun="afterUpgrade"
          @pagesizechange="pageSizeChange"
          @pagechange="pageChange"
          v-bind:itemCount="itemCount"
          v-bind:tableHeight="rightTableHeight"
          v-bind:isshowOption="true" v-bind:isshowSelection ="true"
          v-bind:propertyComponent="this.$refs.ShowProperty"
          @rowclick="selectedRow"
          @selectchange="selectChange"
          @refreshdatagrid="refreshMain"
        ></DataGrid>
        
      </el-col>
    </el-row>
  </div>
</template>

<script type="text/javascript">
import ShowProperty from "@/components/ShowProperty";
import DialogProperties from "@/components/DialogProperties";
import DataGrid from "@/components/DataGrid";
import DataGridleft from "@/components/DataGrid";
import AddFile from "@/views/docexchange/AddFile"


export default {
  name: "ArchiveDelivery",
  data() {
    return {
      currentLanguage: "zh-cn",
      printsVisible: false,
      printVolumesVisible: false,
      printObjId:"",
      archiveId: "",
      dataList: [],
      transferColumnList: [],
      showFields: [],
      itemDataList: [],
      itemDataListFull: [],
      showAddfile:false,
      newDocId:"",
      selectedTypeName: [],
      printGridName:"",
      upgradepropertyVisible:false,
      reuseVisible: false,
      uploadFileLoding:false,
      typeName: "卷盒",
      folderPath: "/设计分包/传递单管理/ATOS",
     
      gridList: [],
      
      currentFolder: [],
      dataListFull: "",
      inputkey: "",
      typeNames: [],
      loading: false,
      uploading:false,
      dialogName: "",
      
      pageSize: 20,
      itemCount: 0,
      innerCount: 0,
      
      selectedItemId: "",
      fileList: [],
      currentPage: 1,
      dialogVisible: false,
      propertyVisible: false,
      showButton: true,
      typeSelectVisible: false,
      selectRow: [],
      importdialogVisible: false,
      batchDialogVisible:false,
      selectedItems: [],
      selectedFileId: "",
      selectedFileItem:[],
      selectedOutItems: [],
      selectedInnerItems: [],
      childrenTypes: [],
      uploadUrl: "",
      selectedChildrenType: "",
      leftTableHeight: window.innerHeight - 124,
      rightTableHeight: window.innerHeight - 200,
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
  mounted() {
    // let _self=this;
    // this.getTypeNames("innerTransferDocType");
    // this.loadTransferGridInfo();
    // this.loadTransferGridData();
    // this.loadGridInfo();
    // this.loadInnerGridInfo();
    this.loadGridInfo();
    this.loadGridData();
  },
  methods: {
    // 表格行选择
    selectChange(val) 
    {
      // console.log(JSON.stringify(val));
      this.selectedInnerItems = val;
    },
    removeFromArchive()
    {
      let _self = this;
      

      if(_self.selectedInnerItems.length==0)
      {
        _self.$message({
                showClose: true,
                message: _self.$t('message.selectOneOrMoreData'),
                duration: 2000,
                type: "waring"
              });
        
        return;
      }
      var m = new Map();
      m.set('archiveId',_self.transferId);
      let innerIds=new Array();
      for(let i=0;i<_self.selectedInnerItems.length;i++)
      {
        innerIds.push(_self.selectedInnerItems[i].ID);
      }
      m.set('fileIds',innerIds);

      // console.log('pagesize:', _self.pageSize);
      _self
      .axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        method: "post",
        data: JSON.stringify(m),
        url: "/dcexchange/removeFromArchive"
      })
      .then(function(response) {
       _self.loadGridData();
        
        //console.log(JSON.stringify(response.data.datalist));
        _self.loading = false;
      })
      .catch(function(error) {
        console.log(error);
        _self.loading = false;
      });
    },
    addReuseToVolume() {
      let _self = this;
      _self.selectedReuses = _self.$refs.addfile.selectedItems;

      var params = new Map();
      var m = [];
      let tab = _self.selectedReuses;

      var i;
      for (i in tab) {
        m.push(tab[i]["ID"]);
      }
      params.set("cids", m);
      params.set("id", _self.transferId);
      console.log(JSON.stringify(m));
      _self
        .axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data: JSON.stringify(params),
          url: "/dcexchange/addReuseToVolume"
        })
        .then(function(response) {
          _self.loadGridData(null);

          // _self.showInnerFile(null);
          _self.showAddfile = false;
          // _self.$message("添加成功！");
          _self.$message({
                showClose: true,
                message: _self.$t('message.addSuccess'),
                duration: 2000,
                type: "success"
              });
        })
        .catch(function(error) {
          // _self.$message("添加失败！");
          _self.$message({
                showClose: true,
                message: _self.$t('message.addFaild'),
                duration: 5000,
                type: "error"
              });
          console.log(error);
        });
    },
    // 加载表格样式
    loadGridInfo() {
      let _self = this;
      _self.loading = true;
      var m = new Map();
      m.set("gridName", "TransferGrid");
      m.set("lang", _self.currentLanguage);
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
    newArchiveItem(typeName) {
      let _self = this;
      if (_self.transferId) {
        _self.selectedItemId = "";

        _self.dialogName = typeName;
        _self.propertyVisible = true;

        setTimeout(()=>{
          if(_self.$refs.ShowProperty){
          _self.$refs.ShowProperty.myItemId = "";
          _self.dialogName=typeName;
          _self.$refs.ShowProperty.myTypeName =typeName;
          _self.typeName=typeName;
          _self.$refs.ShowProperty.parentDocId=_self.transferId;
          _self.$refs.ShowProperty.folderPath = '/设计分包/传递单管理/ATOS';
          // _self.$refs.ShowProperty.myFolderId = _self.selectTransferRow.id;
          _self.$refs.ShowProperty.loadFormInfo();
        }
        },10);

      } else {
        // _self.$message(_self.$t("message.pleaseSelectOneTransfer"));
        _self.$message({
                showClose: true,
                message: _self.$t("message.pleaseSelectOneTransfer"),
                duration: 2000,
                type: "warning"
              });
      }
    },
    // 加载表格数据
    loadGridData(row) {
      let _self = this;
      
      _self.loadGridInfo();
      var key = _self.inputkey;
      if (key != "") {
        key = " and (b.coding like '%" + key + "%' or b.name like '%" + key + "%')";
      }
      
      var m = new Map();
      m.set("gridName", "TransferGrid");
      // m.set('folderId',indata.id);
      m.set("condition", key);
      m.set("id",_self.transferId);
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
    // 新建文档
    newItem() {
      let _self = this;
      if (_self.currentFolder.id) {
        _self.selectedItemId = "";
        _self.propertyVisible = true;
        if (_self.$refs.ShowProperty) {
          _self.$refs.ShowProperty.myItemId = "";
          _self.dialogName = _self.currentFolder.description;
          _self.$refs.ShowProperty.myTypeName = _self.currentFolder.description;
          _self.$refs.ShowProperty.myFolderId = _self.currentFolder.id;
          _self.$refs.ShowProperty.loadFormInfo();
        }
      } else {
        // _self.$message(_self.$t("message.pleaseSelectFolder"));
        _self.$message({
                showClose: true,
                message: _self.$t("message.pleaseSelectFolder"),
                duration: 2000,
                type: "warning"
              });
      }
    },
    // 保存文档
    saveUpgradeItem() {
      if (!this.$refs.upgradeproperties.validFormValue()) {
        return;
      }
      let _self = this;
      var m = new Map();
      let dataRows = this.$refs.upgradeproperties.dataList;
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
      if (_self.$refs.upgradeproperties.myItemId != "") {
        m.set("ID", _self.$refs.upgradeproperties.myItemId);
      }
      // if (_self.$refs.upgradeproperties.myTypeName != "") {
      //   m.set("TYPE_NAME", _self.$refs.upgradeproperties.myTypeName);
      //   m.set("folderPath", _self.$refs.upgradeproperties.folderPath);
      //   m.set("transferId", _self.$refs.upgradeproperties.parentDocId);
      // }
      let formdata = new FormData();
      formdata.append("metaData", JSON.stringify(m));

      if (_self.$refs.upgradeproperties.file != "") {
        //console.log(_self.file);
        formdata.append("uploadFile", _self.$refs.upgradeproperties.file.raw);
      }
      // console.log(JSON.stringify(m));
      if (_self.$refs.upgradeproperties.myItemId == "") {
        _self
          .axios({
            headers: {
              "Content-Type": "multipart/form-data"
              // x-www-form-urlencoded'
              //"Content-Type": "application/json;charset=UTF-8"
            },
            method: "post",
            data: formdata,
            url: "/dc/newInnerDocument"
          })
          .then(function(response) {
            let code = response.data.code;
            //console.log(JSON.stringify(response));
            if (code == 1) {
              // _self.$message("创建成功!");
              _self.$message({
                showClose: true,
                message: _self.$t('message.newSuccess'),
                duration: 2000,
                type: "success"
              });
              _self.upgradepropertyVisible = false;

              // _self.loadTransferGridData();
              _self.loadGridData(null);
            } else {
              // _self.$message("新建失败!");
              _self.$message({
                showClose: true,
                message: _self.$t('message.newFailured'),
                duration: 2000,
                type: "warning"
              });
            }
          })
          .catch(function(error) {
            // _self.$message("新建失败!");
            _self.$message({
                showClose: true,
                message: _self.$t('message.newFailured'),
                duration: 5000,
                type: "error"
              });
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
              // _self.$emit("onSaved", "update");
              _self.$message({
                showClose: true,
                message: _self.$t('message.newSuccess'),
                duration: 2000,
                type: "success"
              });
              _self.upgradepropertyVisible = false;
            } else {
              // _self.$message("保存失败!");
              _self.$message({
                showClose: true,
                message: _self.$t('message.saveFailured'),
                duration: 5000,
                type: "error"
              });
            }
          })
          .catch(function(error) {
            // _self.$message("保存失败!");
            _self.$message({
                showClose: true,
                message:  _self.$t('message.saveFailured'),
                duration: 5000,
                type: "error"
              });
            console.log(error);
          });
      }
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
            url: "/dc/newInnerDocument"
          })
          .then(function(response) {
            let code = response.data.code;
            //console.log(JSON.stringify(response));
            if (code == 1) {
              // _self.$message("创建成功!");
              _self.$message({
                showClose: true,
                message: _self.$t('message.newSuccess'),
                duration: 2000,
                type: "success"
              });
              _self.propertyVisible = false;

              // _self.loadTransferGridData();
              _self.loadGridData(null);
            } else {
              // _self.$message("新建失败!");
              _self.$message({
                showClose: true,
                message: _self.$t('message.newFailured'),
                duration: 2000,
                type: "warning"
              });
            }
          })
          .catch(function(error) {
            // _self.$message("新建失败!");
            _self.$message({
                showClose: true,
                message: _self.$t('message.newFailured'),
                duration: 5000,
                type: "error"
              });
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
              // _self.$message("保存失败!");
              _self.$message({
                showClose: true,
                message: _self.$t('message.saveFailured'),
                duration: 5000,
                type: "error"
              });
            }
          })
          .catch(function(error) {
            // _self.$message("保存失败!");
            _self.$message({
                showClose: true,
                message:  _self.$t('message.saveFailured'),
                duration: 5000,
                type: "error"
              });
            console.log(error);
          });
      }
    },
    //著录文件
    beforeCreateFile(){
      let _self=this;
      _self.typeSelectVisible=true;
      setTimeout(()=>{
        _self.getTypeNames("innerTransferDocType");
      },10);
       
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
    // 分页 页数改变
    handleSizeChange(val) {
      this.pageSize = val;
      localStorage.setItem("docPageSize", val);
      this.loadGridData();
      //console.log('handleSizeChange', val);
    },
    refreshMain(){
      this.loadGridData();
    },
    refreshLeft(){
      this.showInnerFile();
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
    
    sortchange(column) {
      console.log(JSON.stringify(column));
      console.log(column.column.property);
      console.log(column.column.order); //ascending, descending
      this.orderBy =
        column.column.property + column.column.order == "ascending"
          ? " ASC"
          : " DESC";
    },
    afterUpgrade(docId){
      this.newDocId=docId;
      this.upgradepropertyVisible=true;
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
    
    
    
    // 保存结果事件
    onSaved(indata) {
      let _self=this;
      if (indata == "update") {
        // _self.$message(_self.$t("message.saveSuccess"));
        _self.$message({
            showClose: true,
            message: _self.$t("message.saveSuccess"),
            duration: 2000,
            type: 'success'
          });
      } else {
        // _self.$message("新建成功!");
        _self.$message({
            showClose: true,
            message: _self.$t("message.newSuccess"),
            duration: 2000,
            type: 'success'
          });
      }
      _self.propertyVisible = false;
      _self.loadGridData();
      
    },
    selectedRow(row){
      let _self=this;
      _self.selectRow=row;
    },
    // 删除文档事件
    onDeleleItem() {
      let _self = this;
      if(_self.selectRow.ID==undefined){
        // _self.$message("请选择一条要删除的图册或卷盒数据！");
        _self.$message({
            showClose: true,
            message: _self.$t('message.selectOneOrMoreDeleteData'),
            duration: 2000,
            type: 'warning'
          });
        return;
      }
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
      // var m = [];
      // let tab = _self.selectedItems;

      // var i;
      // for (i in tab) {
      //   m.push(tab[i]["ID"]);
      // }
      if(_self.selectRow.ID==undefined){
        // _self.$message("请选择一条要删除的图册或卷盒数据！");
        _self.$message({
              showClose: true,
              message: _self.$t('message.selectOneOrMoreDeleteData'),
              duration: 2000,
              type: 'warning'
          });
        return;
      }
      var m = [];
      m.push(_self.selectRow.ID);
      // console.log(JSON.stringify(m));
      _self
        .axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data: JSON.stringify(m),
          url: "/dcexchange/delDocumentAndRelation"
        })
        .then(function(response) {
          _self.loadGridData(null);

          // _self.$message(_self.$t("message.deleteSuccess"));
          _self.$message({
              showClose: true,
              message: _self.$t("message.deleteSuccess"),
              duration: 2000,
              type: 'success'
          });
        })
        .catch(function(error) {
          // _self.$message(_self.$t("message.deleteFailured"));
          _self.$message({
              showClose: true,
              message: _self.$t("message.deleteFailured"),
              duration: 5000,
              type: 'error'
          });
          console.log(error);
        });
    },
    // 删除文档事件
    onDeleleFileItem() {
      let _self = this;
      if(_self.selectedFileItem.RELATION_ID==undefined){
        // _self.$message("请选择一条要删除的文件数据！")
        _self.$message({
              showClose: true,
              message: _self.$t('message.selectOneOrMoreDeleteData'),
              duration: 2000,
              type: 'warning'
          });
        return;
      }
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
      // var m = [];
      // let tab = _self.selectedInnerItems;
      // var i;
      // for (i in tab) {
      //   m.push(tab[i]["ID"]);
      // }
      var m = [];
      if(_self.selectedFileItem.RELATION_ID==undefined){
        // _self.$message("请选择一条要删除的文件数据！")
        _self.$message({
              showClose: true,
              message: _self.$t('message.selectOneOrMoreDeleteData'),
              duration: 2000,
              type: 'warning'
          });
        return;
      }
      m.push(_self.selectedFileItem.RELATION_ID);
      // console.log(JSON.stringify(m));
      _self
        .axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data:JSON.stringify(m),
          url: "/dc/delDocumentByRelationId"
        })
        .then(function(response) {
          _self.showInnerFile(null);
          _self.$message(_self.$t("message.deleteSuccess"));
        })
        .catch(function(error) {
          // _self.$message(_self.$t("message.deleteFailured"));
          _self.$message({
              showClose: true,
              message: _self.$t("message.deleteFailured"),
              duration: 5000,
              type: 'error'
          });
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
    
    
    handleChange(file, fileList) {
      this.fileList = fileList;
    },
    getFormData() {
      let _self = this;
      let formdata = new FormData();
      var data = {};
      data["ID"] = _self.selectedFileItem.ID;//_self.selectedInnerItems[0].ID;//_self.selectedFileId;
      formdata.append("metaData", JSON.stringify(data));
      _self.fileList.forEach(function(file) {
        //console.log(file.name);
        formdata.append("uploadFile", file.raw, file.name);
      });
      return formdata;
    },
    
    //查询文档
    searchItem() {
      this.loadGridData();
      // this.loadPageInfo();
    }
  },
  props: {
    transferId: {type:String}
    
  },
  components: {
    ShowProperty: ShowProperty,
    
    DataGrid: DataGrid,
    AddFile:AddFile,
    DialogProperties:DialogProperties
    
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
  /* width:200px; */
  /* height:100px; */
  /* background-color:rgb(34,124,134); */
  float: left;
  height: 100%;
}

/* *{
        margin: 0;
        padding: 0;
      }            
             .middle{
                 position: absolute;
                 left: 50.3%;
                 right: 41%;
                 background-color: red;
                 word-break: break-word;
                 height: 20%;
             }
             .left{
               position:relative;
                 width: 45%;
                 height: 200px;
                 float: left;
                background-color: blue;
             }
            .right{
              position:relative;
                 width: 45%;
                 height: 200px;
                 float: right;
                 background-color: yellow;
            } */
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
