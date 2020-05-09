<template>
  <div>
    
    <el-dialog title="传递文件列表" :visible.sync="innerTransferVisible" width="80%" :modal-append-to-body='false'>
      <InnerTransferDoc ref="InnerTransferDoc" :transferId="transferId"></InnerTransferDoc>
      <div slot="footer" class="dialog-footer">
        <el-button @click="reuseVisible = false">取 消</el-button>
        <el-button type="primary" @click="addReuseToVolume()">确定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="添加复用文件" :visible.sync="reuseVisible" width="80%">
      <AddReuse ref="addReuseModel"></AddReuse>
      <div slot="footer" class="dialog-footer">
        <el-button @click="reuseVisible = false">取 消</el-button>
        <el-button type="primary" @click="addReuseToVolume()">确定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="批量导入文档" :visible.sync="batchDialogVisible" width="80%" >
        <BatchImport ref="BatchImport"  @onImported="onBatchImported" width="100%" v-bind:deliveryId="selectedOneTransfer.ID"></BatchImport>
        <div slot="footer" class="dialog-footer">
          <el-button @click="batchDialogVisible=false" size="medium">关闭</el-button>
         </div>
      </el-dialog>
    <el-dialog title="导入" :visible.sync="importdialogVisible" width="70%">
      <el-form size="mini" :label-width="formLabelWidth" v-loading='uploading'>
        <div style="height:200px;overflow-y:scroll; overflow-x:scroll;">
          <el-upload
            :limit="100"
            :file-list="fileList"
            action
            :on-change="handleChange"
            :auto-upload="false"
            :multiple="false"
          >
            <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
          </el-upload>
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="importdialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="uploadData()">开始导入</el-button>
      </div>
    </el-dialog>

    <!-- <el-dialog :visible.sync="typeSelectVisible">
      <el-form>
        <el-form-item label="文件类型" :rules="[{required:true,message:'必填',trigger:'blur'}]">
          <el-select
            name="selectName"
            v-model="selectedTypeName"
            placeholder="'请选择文件类型'"
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
    </el-dialog> -->

    <el-dialog :visible.sync="childrenTypeSelectVisible">
      <el-form>
        <el-form-item label="文件类型" :rules="[{required:true,message:'必填',trigger:'blur'}]">
          <el-select
            name="selectName"
            v-model="selectedChildrenType"
            placeholder="'请选择文件类型'"
            style="display:block;"
          >
            <div v-for="(name,nameIndex) in childrenTypes" :key="'T2_'+nameIndex">
              <el-option :label="name" :value="name" :key="nameIndex"></el-option>
            </div>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button
          @click="childrenTypeSelectVisible=false;newArchiveItem(selectedChildrenType,selectRow)"
        >{{$t('application.ok')}}</el-button>
      </div>
    </el-dialog>

    <el-dialog :visible.sync="printsVisible">
      <PrintPage ref="printPage" v-bind:archiveId="this.archiveId"></PrintPage>
    </el-dialog>
    <el-dialog :visible.sync="printVolumesVisible" width="80%">
      <PrintVolumes
        ref="printVolumes"
        v-bind:archiveId="this.printObjId"
        v-bind:gridName="printGridName"
        v-bind:currentFolderId="this.currentFolder.id"
      ></PrintVolumes>
    </el-dialog>

    <el-dialog
      :title="dialogName+$t('application.property')"
      :visible.sync="propertyVisible"
      @close="propertyVisible = false"
      width="80%"
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
    <el-dialog
      :title="folderAction"
      :visible.sync="folderDialogVisible"
      @close="folderDialogVisible = false"
    >
      <el-form :model="transferForm">
        <el-form-item :label="$t('field.name')" :label-width="formLabelWidth">
          <el-input v-model="transferForm.title" auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="saveTransfer(transferForm)">{{$t('application.ok')}}</el-button>
        <el-button @click="folderDialogVisible = false">{{$t('application.cancel')}}</el-button>
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
          @click="newArchiveItem('传递单',selectedOneTransfer)"
        >新建传递单</el-button>
        
        <!-- <el-button
          type="primary"
           plain
            size="small"
          icon="el-icon-edit"
          @click="beforeNewDocument(selectedOneTransfer)"
        >{{$t('application.newDocument')}}</el-button> -->
        <el-button type="primary" plain size="small" icon="el-icon-upload2"
         @click="batchDialogVisible=true">批量导入</el-button> 
       
        <!-- <el-button type="primary" icon="el-icon-s-release"  @click="onClosePage()">{{$t('application.sealVolume')}}</el-button>
        <el-button type="primary" icon="el-icon-folder-opened"  @click="onOpenPage()">{{$t('application.openPage')}}</el-button>-->
        <!-- <el-button type="primary" icon="el-icon-printer" @click="printsVisible = true">{{$t('application.PrintCover')}}</el-button> -->
        <el-button
          type="primary"
           plain
            size="small"
          icon="el-icon-printer"
          @click="beforePrint(selectRow,'PrintVolumeOrder','文件清单')"
        >打印清单</el-button>
         <el-button
          type="primary"
          plain
          size="small"
          icon="el-icon-delete"
          @click="onDeleleItem()"
        >{{$t('application.delete')}}</el-button>
      </el-col>
    </el-row>
    <el-row>
      <el-col>
        <DataGrid
          ref="mainDataGrid"
          key="main"
          v-bind:itemDataList="itemDataList"
          v-bind:columnList="gridList"
          @pagesizechange="pageSizeChange"
          @pagechange="pageChange"
          v-bind:itemCount="itemCount"
          v-bind:tableHeight="rightTableHeight"
          v-bind:isshowOption="true" v-bind:isshowSelection ="true"
          v-bind:propertyComponent="this.$refs.ShowProperty"
          @dbclick="dbclick"
          @selectchange="selectChange"
          @refreshdatagrid="refreshMain"
        ></DataGrid>
        
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
import InnerTransferDoc from "@/views/docexchange/InnerTransferDoc";
import BatchImport from '@/components/controls/ImportDocument'


export default {
  name: "DocTransfer",
  data() {
    return {
      currentLanguage: "zh-cn",
      printsVisible: false,
      innerTransferVisible:false,
      printVolumesVisible: false,
      printObjId:"",
      archiveId: "",
      dataList: [],
      transferId:"",
      transferColumnList: [],
      showFields: [],
      itemDataList: [],
      itemDataListFull: [],
      innerDataList: [],
      innerDataListFull: [],
      outerDataList: [],
      outerDataListFull: [],
      transferPageSize: 20,
      transferCurrentPage: 1,
      transferDataList: [],
      transferDataListFull: [],
      selectedTypeName: [],
      printGridName:"",
      transferCount: 0,
      reuseVisible: false,
      uploadFileLoding:false,
      typeName: "卷盒",
      folderPath: "/设计分包/传递单管理/ATOS",
      selectTransferRow: [],
      selectedOneTransfer:[],
      gridList: [],
      innerGridList: [],
      outerGridList: [],
      currentFolder: [],
      dataListFull: "",
      inputkey: "",
      typeNames: [],
      loading: false,
      uploading:false,
      dialogName: "",
      outerCurrentPage: 1,
      outerPageSize: 20,
      innerCurrentPage: 1,
      innerPageSize: 20,
      pageSize: 20,
      itemCount: 0,
      innerCount: 0,
      outerCount: 0,
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
      childrenTypeSelectVisible: false,
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
    refreshMain(){
      this.loadGridData();
    },
    refreshLeft(){
      this.showInnerFile();
    },
    beforeNewDocument(selectTrRow){
      let _self=this;
      
      if(selectTrRow.ID==undefined){
        // _self.$message(_self.$t("message.pleaseSelectOneTransfer"));
        _self.$message({
                showClose: true,
                message: _self.$t("message.pleaseSelectOneTransfer"),
                duration: 2000,
                type: "warning"
              });
        return;
      }
      _self.typeSelectVisible=true;
    },
    dbclick(row){
      
      let _self=this;
      _self.transferId=row.ID;
      _self.innerTransferVisible=true;
      
      
    },
    beforeAddreuse(){
      let _self=this;
       if(_self.selectRow.ID==undefined){
        // _self.$message('请选择一条图册或卷盒数据！')
        _self.$message({
                showClose: true,
                message: _self.$t('message.selectOneOrMoreDeleteData'),
                duration: 2000,
                type: "warning"
              });
        return;
      }
      _self.reuseVisible=true;
    },
    
    beforeUploadFile(uploadpath){
      let _self=this;
      if(_self.selectedFileItem.ID==undefined){
        // _self.$message('请选择一条文件数据');
         _self.$message({
                showClose: true,
                message: _self.$t('message.selectOneOrMoreDeleteData'),
                duration: 2000,
                type: "warning"
              });
        return;
      }
      _self.uploadUrl=uploadpath;
      _self.fileList=[];
      _self.importdialogVisible=true;
      
    },
    beforePrint(selectedRow,gridName,vtitle){
      let _self=this;
      if(selectedRow.ID==undefined){
        // _self.$message('请选择一条数据进行打印');
        _self.$message({
                showClose: true,
                message: _self.$t('message.selectOneOrMoreData')+_self.$t('message.print'),
                duration: 2000,
                type: "warning"
              });
        return;
      }
      _self.printVolumesVisible = true;

      setTimeout(()=>{
        _self.$refs.printVolumes.dialogQrcodeVisible = false
        _self.$refs.printVolumes.getArchiveObj(_self.$refs.printVolumes.archiveId,
        _self.$refs.printVolumes.gridName,
        vtitle); 
      },10);

      _self.printGridName=gridName;
      _self.printObjId=selectedRow.ID;
    },
    //批量导入完成
    onBatchImported(){
      this.loadGridData();
    },
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
      this.selectedFileItem=row;
      // let _self = this;
      // if (_self.selectRow) {
      //   _self.selectedFileId = row.ID;
      // }
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
      if (_self.selectedFileItem.ID==undefined) {
        // _self.$message("请选择一条数据！");
        _self.$message({
                showClose: true,
                message: '请选择一条数据！',
                duration: 2000,
                type: "warning"
              });
        return;
      }
      var m = new Map();
      m.set("parentId", _self.archiveId);
      m.set("childId", _self.selectedFileItem.ID);
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
                type: "error"
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
      if (_self.selectedFileItem.ID==undefined) {
        // _self.$message("请选择一条数据！");
        _self.$message({
                showClose: true,
                message: "请选择一条数据！",
                duration: 2000,
                type: "warning"
              });
        return;
      }
      var m = new Map();
      m.set("parentId", _self.archiveId);
      m.set("childId", _self.selectedFileItem.ID);
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
                duration: 2000,
                type: "warning"
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
      m.set("gridName", "DeliveryInnerGrid");
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
          _self.transferColumnList = response.data.data;
          _self.transferColumnList.forEach(element => {
            if (element.visibleType == 1) {
              _self.showFields.push(element.attrName);
            }
          });
          //_self.leftTableHeight = "98%";
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
      m.set("condition", "creator='@currentuser' and status='产生'");
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
          

          //console.log(JSON.stringify(response.data.datalist));
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    beforeLoadGridData(row){
      this.innerDataList=[];
      this.selectedFileItem=[];
      this.selectRow=[];
      this.loadGridData(row);
    },
    // 加载表格数据
    loadGridData(row) {
      let _self = this;
      
      _self.loadGridInfo();
      var key = _self.inputkey;
      if (key != "") {
        key = " coding like '%" + key + "%' or name like '%" + key + "%'";
      }
      
      var m = new Map();
      m.set("gridName", "TransferGrid");
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
          url: "/dc/getDocuments"
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
      
      //console.log('handleCurrentChange', val);
    },
    outerPageSizeChange(val) {
      this.outerPageSize = val;
      localStorage.setItem("docPageSize", val);
      
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
      m.set("gridName", "DeliveryInnerGrid");
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
    //刷新文件夹数据
    refreshFolderData() {
      let _self = this;
      _self.loading = true;
      _self
        .axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data: 0,
          url: "/admin/getFolder"
        })
        .then(function(response) {
          _self.dataList = response.data.data;
          console.log(_self.dataList);
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    
    newArchiveItem(typeName, selectedRow) {
      let _self = this;
      _self.selectedItemId = "";

        _self.dialogName = typeName;
        _self.propertyVisible = true;

        setTimeout(()=>{
          if(_self.$refs.ShowProperty){
          _self.$refs.ShowProperty.myItemId = "";
          _self.dialogName=typeName;
          _self.$refs.ShowProperty.myTypeName =typeName;
          _self.typeName=typeName;
          _self.$refs.ShowProperty.parentDocId=selectedRow.ID;
          _self.$refs.ShowProperty.folderPath = '/设计分包/传递单管理/ATOS';
          // _self.$refs.ShowProperty.myFolderId = _self.selectTransferRow.id;
          _self.$refs.ShowProperty.loadFormInfo();
        }
        },10);
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
            url: "/dc/createTransfer"
          })
          .then(function(response) {
            let code = response.data.code;
            //console.log(JSON.stringify(response));
            if (code == 1) {
              // _self.$message("创建成功!");
              _self.$message({
                showClose: true,
                message: "创建成功!",
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
                message: "新建失败!",
                duration: 2000,
                type: "warning"
              });
            }
          })
          .catch(function(error) {
            // _self.$message("新建失败!");
            _self.$message({
                showClose: true,
                message: "新建失败!",
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
                message: "保存失败!",
                duration: 5000,
                type: "error"
              });
            }
          })
          .catch(function(error) {
            // _self.$message("保存失败!");
            _self.$message({
                showClose: true,
                message: "保存失败!",
                duration: 5000,
                type: "error"
              });
            console.log(error);
          });
      }
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
            message: "新建成功",
            duration: 2000,
            type: 'success'
          });
      }
      _self.propertyVisible = false;
      _self.loadGridData();
      
    },
    // 删除文档事件
    onDeleleItem() {
      let _self = this;
      if(_self.selectedItems.length==0){
        _self.$message({
                    showClose: true,
                    message: "请选择一条或多条要删除的数据！",
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
      var m = [];
      let tab = _self.selectedItems;

      var i;
      for (i in tab) {
        m.push(tab[i]["ID"]);
      }
      // if(_self.selectRow.ID==undefined){
      //   // _self.$message("请选择一条要删除的图册或卷盒数据！");
      //   _self.$message({
      //         showClose: true,
      //         message: "请选择一条要删除的图册或卷盒数据！",
      //         duration: 2000,
      //         type: 'warning'
      //     });
      //   return;
      // }
      // var m = [];
      // m.push(_self.selectRow.ID);
      // console.log(JSON.stringify(m));
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

          // _self.showInnerFile(null);
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
              message: "请选择一条要删除的文件数据！",
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
              message: "请选择一条要删除的文件数据！",
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
    // 保存移交单
    saveTransfer(indata) {
      let _self = this;
      if (_self.folderAction == _self.$t("application.newTransfer")) {
        _self.newFolder(indata);
      }
    },
    // 删除文件夹
    delFolder() {
      let _self = this;
      _self
        .axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          datatype: "json",
          method: "post",
          data: JSON.stringify(_self.currentFolder),
          url: "/admin/deleteFolder"
        })
        .then(function(response) {
          if (response.data.code == 1) {
            // _self.$message(_self.$t("message.deleteSuccess"));
            _self.$message({
              showClose: true,
              message: _self.$t("message.deleteSuccess"),
              duration: 2000,
              type: 'success'
            });
            _self.refreshFolderData();
          } else {
            // _self.$message(response.data.msg);
            _self.$message({
              showClose: true,
              message: response.data.msg,
              duration: 2000,
              type: 'warning'
            });
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    // 新建文件夹事件
    onNewFolder() {
      this.folderAction = this.$t("application.newTransfer");
      this.transferForm = {
        id: null,
        title: "",
        folderPath: "/表单/移交单",
        typeName: "移交单",
        status: "产生"
      };
      this.folderDialogVisible = true;
    },
    //归档
    onArchived() {
      let _self = this;
      if (
        _self.selectedOneTransfer.ID==undefined
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

      // let tab = _self.selectTransferRow;
      // var m = [];
      // var i;
      // for (i in tab) {
      //   if (tab[i]["STATUS"] != "产生") {
      //     _self.$message("移交单" + tab[i]["CODING"] + "已提交不能再次提交！");
      //     return;
      //   }
      //   m.push(tab[i]["ID"]);
      // }
      // console.log(JSON.stringify(m));
      var m = [];
      m.push(_self.selectedOneTransfer.ID);
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
          if(response.data.code==1){
            _self.loadTransferGridData();

          // _self.loadGridData(null);

          // _self.showInnerFile(null);
          _self.itemDataList=[]
          _self.innerDataList=[];
          _self.selectedFileItem=[];
          _self.selectRow=[];
          // _self.$message("操作成功");
            _self.$message({
              showClose: true,
              message: "操作成功",
              duration: 2000,
              type: 'success'
            });
          }else{
            _self.$message({
              showClose: true,
              message: response.data.message,
              duration: 2000,
              type: 'warning'
            });
          }
          
          
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
      if (_self.selectedOneTransfer.ID==undefined) {
        // _self.$message(_self.$t("message.pleaseSelectTransfer"));
        _self.$message({
              showClose: true,
              message: _self.$t("message.pleaseSelectTransfer"),
              duration: 2000,
              type: 'warning'
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
          // let tab = _self.selectTransferRow;
          // var m = [];
          // var i;
          // for (i in tab) {
          //   if (tab[i]["STATUS"] != "产生") {
          //     _self.$message("移交单" + tab[i]["CODING"] + "已提交不能删除！");
          //     return;
          //   }
          //   m.push(tab[i]["ID"]);
          // }
          // console.log(JSON.stringify(m));
          var m = [];
          m.push(_self.selectedOneTransfer.ID);
          _self
            .axios({
              headers: {
                "Content-Type": "application/json;charset=UTF-8"
              },
              method: "post",
              data:JSON.stringify(m),
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
                type: 'warning'
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
      data["ID"] = _self.selectedFileItem.ID;//_self.selectedInnerItems[0].ID;//_self.selectedFileId;
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
      _self.uploading=true;
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
          _self.uploading=false;
          // _self.$message("导入成功!");
          _self.$message({
                showClose: true,
                message: "导入成功!",
                duration: 2000,
                type: 'success'
              });
        })
        .catch(function(error) {
          _self.uploading=false;
          console.log(error);
        });
    },
    // 新建文件夹
    newFolder(indata) {
      let _self = this;
      _self
        .axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          datatype: "json",
          method: "post",
          data: JSON.stringify(indata),
          url: "/dc/newTransfer"
        })
        .then(function(response) {
          let code = response.data.code;
          if (code == "1") {
            // _self.$message("创建成功!");
            _self.$message({
                showClose: true,
                message: "创建成功!",
                duration: 2000,
                type: 'success'
              });
            _self.folderDialogVisible = false;

            _self.loadTransferGridData();
            // _self.loadGridData(null);
            // _self.showInnerFile(null);
          } else {
            // _self.$message("新建失败!");
            _self.$message({
                showClose: true,
                message: "新建失败!",
                duration: 5000,
                type: 'error'
              });
          }
          //_self.refreshFolderData();
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    //复制文档
    copyItem(indata) {
      let _self = this;
      _self
        .axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          datatype: "json",
          method: "post",
          data: JSON.stringify(indata),
          url: "/admin/copyFolder"
        })
        .then(function(response) {
          // _self.$message(_self.$t("message.copySuccess"));
          _self.$message({
                showClose: true,
                message: _self.$t("message.copySuccess"),
                duration: 2000,
                type: 'success'
              });
          _self.dialogVisible = false;
          _self.refreshFolderData();
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    //查询文档
    searchItem() {
      this.loadGridData();
      // this.loadPageInfo();
    }
  },
  components: {
    ShowProperty: ShowProperty,
    PrintPage: PrintPage,
    PrintVolumes: PrintVolumes,
    DataGrid: DataGrid,
    AddReuse: AddReuse,
    BatchImport:BatchImport,
    InnerTransferDoc:InnerTransferDoc
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
