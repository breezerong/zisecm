<template>
  <div>
    <el-dialog :visible.sync="printsVisible">
      <PrintPage ref='printPage' v-bind:archiveId="this.archiveId"></PrintPage>
    </el-dialog>
    <el-dialog :visible.sync="printVolumesVisible"  width="80%">
      <PrintVolumes ref='printVolumes' v-bind:archiveId="this.archiveId" v-bind:currentFolderId="this.currentFolder.id"></PrintVolumes>
    </el-dialog>
    
    <el-dialog width="80%" :title="dialogName+$t('application.property')" :visible.sync="propertyVisible" @close="propertyVisible = false">
      <ShowProperty ref="ShowProperty"  @onSaved="onSaved" width="560" v-bind:itemId="selectedItemId" v-bind:folderId="currentFolder.id" v-bind:typeName="currentFolder.typeName"></ShowProperty>
      <div slot="footer" class="dialog-footer">
        <el-button @click="saveItem">{{$t('application.save')}}</el-button> <el-button @click="propertyVisible = false">{{$t('application.cancel')}}</el-button>
      </div>
    </el-dialog>

    <el-dialog title="打印背脊" width="43%" :visible="printRidgeVisible" @close="printRidgeVisible=false">
      <div style="height:900px;">
        <PrintRidge ref="printRidge"></PrintRidge>
      </div>
      
      
    </el-dialog>

    <el-dialog title="导入" :visible.sync="importdialogVisible" width="70%">
          
          <el-form size="mini" :label-width="formLabelWidth">
            
            <div style="height:200px;overflow-y:scroll; overflow-x:scroll;">
              <el-upload
                :limit="100"
                :file-list="fileList" 
                action=""
                :on-change="handleChange"
                :auto-upload="false"
                :multiple="false">
                <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
              </el-upload>
            </div>
          </el-form> 
          <div slot="footer" class="dialog-footer">
            <el-button @click="importdialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="uploadData(uploadID)">开始导入</el-button>
          </div>
        </el-dialog>

    <el-dialog :visible.sync="childrenTypeSelectVisible">
      <el-form>
          <el-form-item label="文件类型" :rules="[{required:true,message:'必填',trigger:'blur'}]">
                <el-select  name="selectName"
                v-model="selectedChildrenType" placeholder="'请选择文件类型'" 
                style="display:block;">
                      <div v-for="(name,nameIndex) in childrenTypes">
                        <el-option :label="name" :value="name" :key="nameIndex"></el-option>
                      </div>
                  </el-select>
                
          </el-form-item>
      </el-form>
       <div slot="footer" class="dialog-footer">
          <el-button @click="childrenTypeSelectVisible=false;newArchiveFileItem(selectedChildrenType,selectRow)">{{$t('application.ok')}}</el-button>
      </div>
    </el-dialog>

    
    <el-dialog :title="folderAction" :visible.sync="folderDialogVisible"  @close="folderDialogVisible = false">
          <el-form :model="folderForm">
            <el-form-item :label="$t('field.name')" :label-width="formLabelWidth">
              <el-input v-model="folderForm.name" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item :label="$t('field.description')" :label-width="formLabelWidth">
              <el-input v-model="folderForm.description" auto-complete="off"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="saveFolder(folderForm)">{{$t('application.ok')}}</el-button>
            <el-button @click="folderDialogVisible = false">{{$t('application.cancel')}}</el-button>
          </div>
        </el-dialog>
      <div :style="{position:'relative',height: asideHeight+'px'}">
      <split-pane split="vertical" @resize="resize" min-percent='10' :default-percent='15'>
      <template slot="paneL">
         <el-container :style="{height:asideHeight+'px',width:asideWidt,overflow:'auto'}">
            <el-tree
              style="width:100%"
              :props="defaultProps"
              :data="dataList"
              node-key="id"
              :render-content="renderContent"
              :default-expand-all="isExpand"
              highlight-current
              @node-click="handleNodeClick">
            </el-tree>
         </el-container>
      </template>
     <template slot="paneR">
          <el-row v-loading="loading">
            <el-col :span="3" class="topbar-input">
              
                    <el-input  v-model="inputkey" :placeholder="$t('message.pleaseInput')+$t('application.keyword')" @change="searchItem" prefix-icon="el-icon-search"></el-input>
                </el-col>
            
            
          </el-row>
          <el-row>
                      <DataGrid ref="mainDataGrid" key="main" v-bind:itemDataList="itemDataList"
                      v-bind:columnList="gridList" @pagesizechange="pageSizeChange"
                      @pagechange="pageChange" v-bind:itemCount="itemCount"
                      v-bind:tableHeight="rightTableHeight" :isshowOption="true" :isshowSelection ="true"
                      @rowclick="beforeShowInnerFile" @selectchange="selectChange"></DataGrid>
                    
                    
                    
          </el-row>
        </template>
      </split-pane>
      </div>
  </div>
</template>

<script type="text/javascript">
import ShowProperty from '@/components/ShowProperty'
import DataGrid from'@/components/DataGrid'
import DataGridleft from'@/components/DataGrid'
//import Prints from '@/components/record/Print'

import 'url-search-params-polyfill'

import PrintPage from '@/views/record/PrintPage'
import PrintVolumes from '@/views/record/PrintVolumes'
import PrintRidge from '@/views/record/PrintRidge'
export default {
  name: "FolderClassification",
  components: {
    ShowProperty: ShowProperty,
    // PDFViewer: PDFViewer,
    DataGrid:DataGrid,
    PrintPage:PrintPage,
    PrintVolumes:PrintVolumes,
    PrintRidge:PrintRidge
    //Prints:Prints
  },
  data() {
    return {
      isExpand:false,
      rightTableHeight: (window.innerHeight - 100)/2,
      asideHeight: window.innerHeight - 55,
      treeHight: window.innerHeight - 95,
      asideWidth: '100%',
      currentLanguage: this.getLang(),
      printsVisible:false,
      printVolumesVisible:false,
      archiveId:"",
      dataList: [],
      showFields: [],
      itemDataList: [],
      itemDataListFull: [],
      innerDataList:[],
      innerDataListFull:[],
      outerDataList:[],
      outerDataListFull:[],
      childrenTypes:[],
      gridList: [],
      innerGridList:[],
      radio:"卷盒",
      outerGridList:[],
      innerSelectedOne:[],
      currentFolder: [],
      dataListFull: "",
      uploadID:"",
      inputkey: "",
      loading: false,
      dialogName:"",
      pageSize: 20,
      itemCount: 0,
      innerCount:0,
      outerCount:0,
      outerCurrentPage:1,
      outerPageSize:20,
      innerCurrentPage:1,
      innerPageSize:20,
      selectedItemId: 0,
      currentPage:1,
      dialogVisible: false,
      propertyVisible: false,
      showButton:true,
      selectedItems: [],
      selectedOutItems: [],
      selectedInnerItems:[],
      selectedChildrenType:"",
      selectRow:[],
      importdialogVisible:false,
      selectedFileId:"",
      selectedOneOutItem:[],
      fileList:[],
      childrenTypes:[],
      childrenTypeSelectVisible:false,
      tableHeight: window.innerHeight - 408,
      folderAction:"",
      folderDialogVisible: false,
      imageArray:[""],
      imageViewVisible:false,
      imageViewer: Object,
      currentType:"",
      orderBy:"",
      printRidgeVisible:false,
      folderForm: {
        id: 0,
        name: "",
        description: "",
        parentId: 0,
        typeName: "Folder",
        gridView: "",
        aclName: ""
      },
      formLabelWidth: "120px",
      defaultProps: {
        children: 'children',
        label: 'name'
      }
    };
  },
  watch:{
    
    showFields(val, oldVal){//普通的watch监听
      //console.log("a: "+val, oldVal);
      let _self = this;
      _self.gridList.forEach(element => {
        element.visibleType=2;
      });
      val.forEach(element => {
        let item = _self.getgriditem(element);
        if(item){
          //console.log(element);
          item.visibleType=1;
        } 
      }); 
    }
  },
  
  created() {

    let _self = this;
    _self.loadInnerGridInfo();
    var psize = localStorage.getItem("docPageSize");
    if(psize)
    {
      _self.pageSize = parseInt(psize);
    }
    _self.currentLanguage = localStorage.getItem("localeLanguage") || "zh-cn";
    _self.loading = true;
    _self.axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        method: "post",
        data: 'ArchiveCollatedID',
        url: "/folder/getArchiveFolderByConfige"
      })
      .then(function(response) {
        _self.dataList = response.data.data;
        
        _self.handleNodeClick(_self.dataList[0]);
        _self.isExpand = true;
        //console.log(JSON.stringify(_self.dataList));
        _self.loading = false;
      })
      .catch(function(error) {
        console.log(error);
        _self.loading = false;
      });
  },
  methods: {
    resize() {
      //console.log('resize')
      this.asideWidth = '100%';
    },
    selectOneOutFile(row){
      this.selectedOneOutItem=row;
    },
  
    getFormData(selId){
      let _self = this;
      let formdata = new FormData();
      var data = {};
      data["ID"]=selId;
      formdata.append("metaData",JSON.stringify(data));
      _self.fileList.forEach(function (file) {
        //console.log(file.name);
        formdata.append("uploadFile", file.raw, file.name);
      });
      return formdata;
    },
    //上传文件
    uploadData(selId){
      let _self = this;
      let formdata = _self.getFormData(selId);
      //console.log("UploadData getData");
      //console.log(formdata);
      _self.axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        datatype: 'json',
        method: 'post',
        data: formdata,
        url: _self.uploadUrl
      })
      .then(function(response) {
        _self.importdialogVisible = false;
        // _self.refreshData();
        _self.showInnerFile(null);
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
    handleChange(file, fileList){
      this.fileList = fileList;
    },
    
    loadInnerGridInfo(){
      let _self = this;
      _self.loading = true;
      var m = new Map();
      m.set('gridName','ArrangeInnerGrid');
      m.set('lang',_self.currentLanguage);
      _self.axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        method: 'post',
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
    innerPageSizeChange(val){
      this.innerPageSize = val;
      localStorage.setItem("docPageSize",val);
      this.showInnerFilepageChange();
    },
   // 分页 当前页改变
    pageChange(val) 
    {
      this.currentPage = val;
      _self.loadGridData(_self.currentFolder);
      //console.log('handleCurrentChange', val);
    },
    pageSizeChange(val){
      this.pageSize = val;
      localStorage.setItem("docPageSize",val);
      _self.loadGridData(_self.currentFolder);
    },
    // 分页 当前页改变
    outerPageChange(val)
    {
      this.outerCurrentPage = val;
      this.loadGridOutData(_self.currentFolder);
      //console.log('handleCurrentChange', val);
    },
    outerPageSizeChange(val){
      this.outerPageSize = val;
      localStorage.setItem("docPageSize",val);
      this.loadGridOutData(_self.currentFolder);
    },
    getTypeNamesByMainList(keyName){
      let _self=this;
      axios.post("/dc/getOneParameterValue",keyName)
      .then(function(response) {
        
        _self.childrenTypes = response.data.data;
        
      })
      .catch(function(error) {
        console.log(error);
        
      });
    },
    beforeShowInnerFile(row){
      this.innerCurrentPage=1;
      this.innerSelectedOne=[];
      this.showInnerFile(row);
    },
    showInnerFile(row){
      let _self = this;
      if(row!=null){
        _self.selectRow=row;
      }
      _self.selectedChildrenType=[];
      if(_self.selectRow.TYPE_NAME=='图册'){
        _self.getTypeNamesByMainList("图册");
      }else{
        _self.getTypeNamesByMainList(_self.selectRow.SUB_TYPE);
      }
      
      _self.outerCurrentPage=1;
      _self.loadInnerGridInfo();
      var m = new Map();
      m.set('gridName','ArrangeInnerGrid');
      m.set('condition',"");
      if(_self.selectRow)
      {
        _self.archiveId=_self.selectRow.ID;
      }
      m.set('id',_self.archiveId);
      m.set('pageSize',_self.pageSize);
      m.set('pageIndex', (_self.innerCurrentPage-1)*_self.innerPageSize);
      m.set('orderBy','');
      // console.log('pagesize:', _self.pageSize);
      axios.post("/dc/getDocuByRelationParentId",JSON.stringify(m))
      .then(function(response) {
        
        _self.innerDataList = response.data.data;
        _self.innerDataListFull = response.data.data;
        _self.innerCount = response.data.pager.total;
        //console.log(JSON.stringify(response.data.datalist));
        _self.loadOutGridInfo();
        _self.loadGridOutData(_self.currentFolder);
        _self.loading = false;
      })
      .catch(function(error) {
        console.log(error);
        _self.loading = false;
      });
    },
    renderContent: function (h, {node, data, store}) {
      //console.log(data);
      if(data.extended){
        return (<span><i class='el-icon-folder-opened'></i><span style="padding-left: 4px;">{node.label}</span></span>);
      }else{
        return (<span><i class='el-icon-folder'></i><span style="padding-left: 4px;">{node.label}</span></span>);
      }
    },
    showNewWindow(id){
      let condition = this.id;
      let href = this.$router.resolve({
        name: 'docviewer',
        query: {
          id: condition,
          token: this.getToken()
        }
      });
      console.log(href);
      window.open(href.href, '_blank');
    },
    getgriditem(attrName){
      let _self = this;
      let ret =null;
      _self.gridList.forEach(element => {
        if(element.attrName==attrName){
          ret = element;
          return;
        }
      });
      return ret;
    },
    handleCheckChange(data, checked, indeterminate) {
        data.visibleType=(checked?1:0);
    },
    inited (viewer) {
      this.imageViewer = viewer;
    },
    onImageClick(){
      this.imageViewVisible = false;
    },
       
    // 表格行选择
    selectChange(val) 
    {
      // console.log(JSON.stringify(val));
      this.selectedItems = val;
    },
    // 表格行选择
    selectOutChange(val) 
    {
      // console.log(JSON.stringify(val));
      this.selectedOutItems = val;
    },
    selectInnerChange(val){
      this.selectedInnerItems = val;
    }
    ,
    sortchange(column){
      console.log(JSON.stringify(column));
      console.log(column.column.property);
      console.log(column.column.order);//ascending, descending
      this.orderBy = column.column.property+ column.column.order=="ascending"?" ASC":" DESC";
    },
    // 加载表格样式
    loadGridInfo(indata) 
    {
      let _self = this;
      _self.loading = true;
      var m = new Map();
      m.set('gridName',indata.gridView);
      m.set('lang',_self.currentLanguage);
      _self.axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        method: 'post',
        data: JSON.stringify(m),
        url: "/dc/getGridViewInfo"
      })
        .then(function(response) {
          _self.showFields = [];
          _self.gridList = response.data.data;
          _self.gridList.forEach(element => {
            if(element.visibleType==1){
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
    removeFromArchive()
    {
      let _self = this;
      if(_self.archiveId==='')
      {
        alert('请选择一条案卷！');
        return;
      }

      if(_self.innerSelectedOne.ID==undefined)
      {
        alert('请选择一条或多条未组卷文件！');
        return;
      }
      var m = new Map();
      m.set('archiveId',_self.archiveId);
      let innerIds=new Array();
      // for(let i=0;i<_self.selectedInnerItems.length;i++)
      // {
        innerIds.push(_self.innerSelectedOne.ID);
      // }
      m.set('fileIds',innerIds);

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
        _self.loadGridOutData(_self.currentFolder);
        
        //console.log(JSON.stringify(response.data.datalist));
        _self.loading = false;
      })
      .catch(function(error) {
        console.log(error);
        _self.loading = false;
      });
    }
    ,
    // 添加至archive
    addToArchive() 
    {
      let _self = this;
      if(_self.archiveId==='')
      {
        alert('请选择一条案卷！');
        return;
      }

      if(_self.selectedOneOutItem.ID==undefined)
      {
        alert('请选择一条或多条未组卷文件！');
        return;
      }
      var m = new Map();
      m.set('archiveId',_self.archiveId);
      let outIds=new Array();
      // for(let i=0;i<_self.selectedOutItems.length;i++)
      // {
        outIds.push(_self.selectedOneOutItem.ID);
      // }
      m.set('fileIds',outIds);

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
        _self.loadGridOutData(_self.currentFolder);
        
        //console.log(JSON.stringify(response.data.datalist));
        _self.loading = false;
      })
      .catch(function(error) {
        console.log(error);
        _self.loading = false;
      });
    },
    // 加载表格数据
    loadGridData(indata) 
    {
      let _self = this;
      
      var key =_self.inputkey;
      var m = new Map();
      if(key!=""){
        key = " (coding like '%"+key+"%' or title like '%"+key+"%') ";
        m.set('condition',key);
        
      }
      
      m.set('gridName',indata.gridView);
      m.set('folderId',indata.id);
      
      
      m.set('pageSize',_self.pageSize);
      m.set('pageIndex', (_self.currentPage-1)*_self.pageSize);
      m.set('orderBy','');
      // console.log('pagesize:', _self.pageSize);
      _self
      .axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        method: "post",
        data: JSON.stringify(m),
        url: "/dc/getInnerFolderDocuments"
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
    handleSizeChange(val) 
    {
      this.pageSize = val;
      localStorage.setItem("docPageSize",val);
      this.loadGridData(this.currentFolder);
      //console.log('handleSizeChange', val);
    },
    // 分页 当前页改变
    handleCurrentChange(val) 
    {
      this.currentPage = val;
      this.loadGridData(this.currentFolder);
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
       
        _self.loading = false;
      })
      .catch(function(error) {
        console.log(error);
        _self.loading = false;
      });
    },
    // 文件夹节点点击事件
    handleNodeClick(indata) {
      let _self = this;
      _self.selectRow=[];
      _self.selectedOneOutItem=[];
      _self.selectedFileId="";
      _self.itemDataList=[];
      _self.innerDataList=[];
      _self.outerDataList=[];

      _self.currentFolder = indata;
      //console.log(JSON.stringify(indata));
      // 没有加载，逐级加载
      if(indata.extended == false)
      {
        _self.loading = true;
        _self
        .axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data: indata.id,
          url: "/folder/getArchiveFolderByConfige"//"/admin/getFolder"
        })
        .then(function(response) {
          indata.children = response.data.data;
          //console.log(JSON.stringify(indata));
          indata.extended = true;
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
      }
      _self.loadGridInfo(indata);
      _self.loadGridData(indata);
    //   _self.loadOutGridInfo();
    //   _self.loadGridOutData(_self.currentFolder);
      
    },
    loadGridOutData(indata){
      let _self = this;
      _self.loading = true;
      let whereSql='';
      for(let i=0;i<_self.childrenTypes.length;i++){
        whereSql+=" TYPE_NAME='"+_self.childrenTypes[i]+"' and id!='"+_self.selectRow.ID+"'";
        if(i!=_self.childrenTypes.length-1){
          whereSql+=" or "
        }
      }
      
      var m = new Map();
      m.set('gridName',"ArrangeInnerGrid");
      if(whereSql==''){
        m.set('condition'," folder_id='"+indata.id+"' ");
      }else{
        m.set('condition',"folder_id='"+indata.id+"' and ("+whereSql+")");
      }
      
      m.set('folderId',indata.id);
      m.set('pageSize',_self.outerPageSize);
      m.set('pageIndex', (_self.outerCurrentPage-1)*_self.outerPageSize);
      m.set('orderBy','');
      // console.log('pagesize:', _self.pageSize);
      _self
      .axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        method: "post",
        data: JSON.stringify(m),
        url: "/dc/getOutDocuments"
      })
      .then(function(response) {
        _self.outerDataList = response.data.data;
        _self.outerDataListFull = response.data.data;
        _self.outerCount = response.data.pager.total;
        //console.log(JSON.stringify(response.data.datalist));
        _self.loading = false;
      })
      .catch(function(error) {
        console.log(error);
        _self.loading = false;
      });

    },
    newArchiveItem(typeName)
    {
      let _self = this;
      if(_self.currentFolder.id ){
        _self.selectedItemId = "";
        _self.propertyVisible = true; 
        setTimeout(()=>{
            if(_self.$refs.ShowProperty){
            _self.$refs.ShowProperty.myItemId = "";
            _self.dialogName=typeName;
            _self.$refs.ShowProperty.myTypeName = typeName;
            _self.$refs.ShowProperty.myFolderId = _self.currentFolder.id;
            _self.$refs.ShowProperty.loadFormInfo();
          }
        },10);
        
      }
      else{
        // _self.$message(_self.$t("message.pleaseSelectFolder"));
        _self.$message({
                showClose: true,
                message: _self.$t("message.pleaseSelectFolder"),
                duration: 2000,
                type: "warning"
              });
      }
    },
    newArchiveFileItem(typeName,selectedRow)
    {
      let _self = this;
      if(selectedRow.ID ){
        _self.selectedItemId = "";
        _self.propertyVisible = true;
        setTimeout(()=>{
          if(_self.$refs.ShowProperty){
            _self.$refs.ShowProperty.myItemId = "";
            _self.dialogName=typeName;
            _self.$refs.ShowProperty.myTypeName =typeName;
            _self.typeName=typeName;
            _self.$refs.ShowProperty.parentDocId=selectedRow.ID;
            _self.$refs.ShowProperty.folderId = _self.currentFolder.id;
            // _self.$refs.ShowProperty.myFolderId = _self.selectTransferRow.id;
            _self.$refs.ShowProperty.loadFormInfo();
          }
        },10);
        
      }
      else{
        // _self.$message(_self.$t("message.pleaseSelectFolder"));
        _self.$message({
                showClose: true,
                message: _self.$t("message.pleaseSelectFolder"),
                duration: 2000,
                type: "warning"
              });
      }
    },
    // 新建文档
    newItem()
    {
      let _self = this;
      if(_self.currentFolder.id ){
        _self.selectedItemId = "";
        _self.propertyVisible = true; 
        if(_self.$refs.ShowProperty){
          _self.$refs.ShowProperty.myItemId = "";
          _self.dialogName=_self.currentFolder.description;
          _self.$refs.ShowProperty.myTypeName = _self.currentFolder.description;
          _self.$refs.ShowProperty.myFolderId = _self.currentFolder.id;
          _self.$refs.ShowProperty.loadFormInfo();
        }
      }
      else{
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
    saveItem(){
      if(!this.$refs.ShowProperty.validFormValue()){
        return;
      }
      let _self=this;
      var m = new Map();
      let dataRows = this.$refs.ShowProperty.dataList;
      var i;
      for (i in dataRows) {
        if(dataRows[i].attrName && dataRows[i].attrName !='')
        {
          if(dataRows[i].attrName !='FOLDER_ID'&&dataRows[i].attrName !='ID')
          {
            m.set(dataRows[i].attrName, dataRows[i].defaultValue);
          }
        }
      }
      if(_self.$refs.ShowProperty.myItemId!='')
      {
        m.set('ID',_self.$refs.ShowProperty.myItemId);
      }
      if(_self.$refs.ShowProperty.myTypeName!='')
      {
        m.set('TYPE_NAME',_self.$refs.ShowProperty.myTypeName);
        m.set('folderPath',_self.$refs.ShowProperty.folderPath);
        m.set('transferId',_self.$refs.ShowProperty.parentDocId);
        m.set('folderId',_self.$refs.ShowProperty.folderId);
      }
      let formdata = new FormData();
      formdata.append("metaData",JSON.stringify(m));
      
      if(_self.$refs.ShowProperty.file!="")
      {
        //console.log(_self.file);
        formdata.append("uploadFile",_self.$refs.ShowProperty.file.raw);
      }
      // console.log(JSON.stringify(m));
      if(_self.$refs.ShowProperty.myItemId=='')
      {
        _self.axios({
          headers: {
            'Content-Type': 'multipart/form-data'
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
          if(code==1){
            // _self.$message("创建成功!");
            _self.$message({
              showClose: true,
              message: "创建成功!",
              duration: 2000,
              type: 'success'
            });
            _self.propertyVisible=false;
            
            _self.loadGridData(_self.currentFolder);
            _self.showInnerFile(null);
            // _self.loadGridOutData(_self.currentFolder);
            _self.outerDataList=[];
          }
          else{
            //  _self.$message("新建失败!");
             _self.$message({
                  showClose: true,
                  message: "新建失败!",
                  duration: 5000,
                  type: "error"
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
      }
      else
      {
        _self.axios({
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
          if(code==1){
            _self.$emit('onSaved','update');
          }
          else{
            //  _self.$message("保存失败!");
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
    onSaved(indata){
      let _self=this;
      if(indata=='update')
      {
        // this.$message(this.$t("message.saveSuccess"));
        
        _self.$message({
          showClose: true,
          message: _self.$t("message.saveSuccess"),
          duration: 2000,
          type: 'success'
            });
      }
      else
      {
        // this.$message("新建成功!");
        _self.$message({
          showClose: true,
          message: "新建成功!",
          duration: 2000,
          type: 'success'
            });
      }
      _self.propertyVisible = false;
      _self.loadGridData(_self.currentFolder);
      _self.loadGridOutData(_self.currentFolder);
    },
    

    
    // 删除文档事件
    onDeleleArchiveItem() {
      let _self = this;
      if(_self.selectRow.ID==undefined){
        // _self.$message("请选择一条卷盒或图册数据！");
        _self.$message({
                showClose: true,
                message: "请选择一条卷盒或图册数据！",
                duration: 2000,
                type: "warning"
              });
        return;
      }
      this.$confirm(_self.$t("message.deleteInfo"),_self.$t("application.info"), {
          confirmButtonText: _self.$t("application.ok"),
          cancelButtonText: _self.$t("application.cancel"),
          type: 'warning'
        }).then(() => {
          _self.deleleInnerItem();
        }).catch(() => {
          // this.$message({
          //   type: 'info',
          //   message: '已取消删除'
          // });          
        });
    },
    // 删除文档
    deleleInnerItem() {
      let _self = this;
      // var m = [];
      // let tab = _self.selectedItems;
      
      // var i;
      // for(i in tab){
        
      //   m.push(tab[i]["ID"]);
      // }
      // console.log(JSON.stringify(m));
      var m=[];
      m.push(_self.selectRow.ID);
      _self.axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data: JSON.stringify(m),//_self.selectRow.ID,//JSON.stringify(m),
          url: "/dc/delDocumentAndRelation"
        })
        .then(function(response) {
          _self.loadGridData(_self.currentFolder);
           
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
                  type: "error"
                });
          console.log(error);
      });
    },

    // 删除文档事件
    onDeleleItem() {
      let _self = this;
      this.$confirm(_self.$t("message.deleteInfo"),_self.$t("application.info"), {
          confirmButtonText: _self.$t("application.ok"),
          cancelButtonText: _self.$t("application.cancel"),
          type: 'warning'
        }).then(() => {
          _self.deleleItem();
        }).catch(() => {
          // this.$message({
          //   type: 'info',
          //   message: '已取消删除'
          // });          
        });
    },
    //提取信息
    fetchInformation(){
      let _self=this;
      if(_self.radio=='卷盒'&&_self.selectRow.ID==undefined){
        //  _self.$message("请选择一条卷盒数据！");
         _self.$message({
                showClose: true,
                message: "请选择一条卷盒数据！",
                duration: 2000,
                type: "warning"
              });
          return;
      }
      // let tab=_self.selectedItems;
      // let m = [];
      // let p=[];
      // let i;
      // for(i in tab){
      //   if((tab[i]["CODING"]==undefined ||tab[i]["CODING"]=="")&&tab[i]["SUB_TYPE"]!="盒")
      //   {
      //     _self.$message("所选卷盒中有未取号的数据，请先对其进行取号！");
      //     return;
      //   }
      //   if(tab[i]["SUB_TYPE"]=="盒"){
      //     p.push(tab[i]["ID"]);
      //   }
      //   m.push(tab[i]["ID"]);
      // }
      if((_self.selectRow.CODING==undefined ||_self.selectRow.CODING=="")
      &&_self.selectRow.SUB_TYPE!="盒"){
        _self.$message({
                showClose: true,
                message: "所选卷盒中有未取号的数据，请先对其进行取号！",
                duration: 2000,
                type: "warning"
              });
        // _self.$message("所选卷盒中有未取号的数据，请先对其进行取号！");
        return;
      }
      let m = [];
      m.push(_self.selectRow.ID);
      // if(p.length>0){
        if(_self.selectRow.SUB_TYPE=='盒'){
        // p=p.join("','");
        let pm = new Map();
        pm.set('configName', 'ValidataHasArchiveCode');
        // pm.set('parentId',"'"+p+"'");
        pm.set('parentId',"'"+_self.selectRow.ID+"'");
         _self
            .axios({
              headers: {
                "Content-Type": "application/json;charset=UTF-8"
              },
              method: "post",
              data: JSON.stringify(pm),
              url: "/dc/getObjectsByConfigClauseNoPage"
            })
            .then(function(response) {
              let sdata = response.data.data;
              if(sdata.length>0){
                 _self.$message({
                  showClose: true,
                  message: "盒内文件"+sdata[0].CODING+"未取号！",
                  duration: 2000,
                  type: "warning"
                });
                // _self.$message("盒内文件"+sdata[0].CODING+"未取号！");
                return;
              }else{
                _self.axios({
                      headers: {
                        "Content-Type": "application/json;charset=UTF-8"
                      },
                      method: "post",
                      data: JSON.stringify(m),
                      url: "/dc/fetchInformation"
                    })
                    .then(function(response) {
                      if(response.data.code=='1'){
                        _self.loadGridData(_self.currentFolder);
                      
                        _self.showInnerFile(null);
                        // _self.$message(_self.$t("message.fetchInformationSuccess"));
                        _self.$message({
                            showClose: true,
                            message: _self.$t("message.fetchInformationSuccess"),
                            duration: 2000,
                            type: 'success'
                          });
                      }else{
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
                      // _self.$message(_self.$t("message.fetchInformationFailed"));
                      _self.$message({
                          showClose: true,
                          message: _self.$t("message.fetchInformationFailed"),
                          duration: 5000,
                          type: "error"
                        });
                      console.log(error);
                  });
              }
              
            })
            .catch(function(error) {
              console.log(error);
            });

      }else{
        _self.axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data: JSON.stringify(m),
          url: "/dc/fetchInformation"
        })
        .then(function(response) {
          _self.loadGridData(_self.currentFolder);
           
            _self.showInnerFile(null);
          // _self.$message(_self.$t("message.fetchInformationSuccess"));
          _self.$message({
              showClose: true,
              message: _self.$t("message.fetchInformationSuccess"),
              duration: 2000,
              type: 'success'
            });
        })
        .catch(function(error) {
          // _self.$message(_self.$t("message.fetchInformationFailed"));
          _self.$message({
                  showClose: true,
                  message: _self.$t("message.fetchInformationFailed"),
                  duration: 5000,
                  type: "error"
                });
          console.log(error);
      });
      }
      
    },
    takeNumbers(){
      let _self =this;
      if(_self.radio=='卷盒'&&_self.selectRow.ID==undefined){
        //  _self.$message("请选择一条或多条卷盒数据！");
        _self.$message({
                showClose: true,
                message: "请选择一条或多条卷盒数据！",
                duration: 2000,
                type: "warning"
              });
          return;
      }
      // let tab=_self.selectedItems;
      // let m = [];
      // let i;
      // for(i in tab){
      //   if(tab[i]["CODING"]!=undefined &&tab[i]["CODING"]!=="")
      //   {
      //     _self.$message("卷盒"+tab[i]["CODING"]+"已取过号不需要再取号!");
      //     return;
      //   }
      //   m.push(tab[i]["ID"]);
      // }
      let m=[];
      m.push(_self.selectRow.ID);
      _self.axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data: JSON.stringify(m),//JSON.stringify(m),
          url: "/dc/takeNumbers"
        })
        .then(function(response) {
            _self.loadGridData(_self.currentFolder);
            // _self.showInnerFile(null);
            _self.innerDataList =[];
            _self.outerDataList=[];
            let code=response.data.code;
            if(code==1){
              //  _self.$message("取号成功！");
              _self.$message({
                  showClose: true,
                  message: "取号成功！",
                  duration: 2000,
                  type: 'success'
                });
            }else{
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
          // _self.$message(_self.$t("message.takeNumberFaild"));
          _self.$message({
                  showClose: true,
                  message: _self.$t("message.takeNumberFaild"),
                  duration: 5000,
                  type: "error"
                });
          console.log(error);
      });
    },
    // 删除文档
    deleleItem() {
      let _self = this;
      var m = [];
      let tab = _self.selectedItems;
      
      var i;
      for(i in tab){
        if(tab[i]["C_LOCK_STATUS"]==="已封卷")
        {
          // _self.$message("案卷："+tab[i]["CODING"]+"已封卷不允许删除！");
          _self.$message({
                showClose: true,
                message: "案卷："+tab[i]["CODING"]+"已封卷不允许删除！",
                duration: 2000,
                type: "warning"
              });
          return;
        }
        m.push(tab[i]["ID"]);
      }
      console.log(JSON.stringify(m));
      _self.axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data: JSON.stringify(m),
          url: "/dc/delDocument"
        })
        .then(function(response) {
          _self.loadGridData(_self.currentFolder);
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
                  type: "error"
                });
          console.log(error);
      });
    },
    // 删除文档事件
    onDeleleFileItem() {
      let _self = this;
      if(_self.selectedOneOutItem.ID==undefined){
        //  _self.$message("请选择一条要删除的文件！");
         _self.$message({
                showClose: true,
                message: "请选择一条要删除的文件！",
                duration: 2000,
                type: "warning"
              });
         return;
      }
      this.$confirm(_self.$t("message.deleteInfo"),_self.$t("application.info"), {
          confirmButtonText: _self.$t("application.ok"),
          cancelButtonText: _self.$t("application.cancel"),
          type: 'warning'
        }).then(() => {
          _self.deleleFileItem();
        }).catch(() => {
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
      // let tab = _self.selectedOutItems;
      // var i;
      // for(i in tab){
      //   m.push(tab[i]["ID"]);
      // }
      m.push(_self.selectedOneOutItem.ID);
      console.log(JSON.stringify(m));
      _self.axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data: JSON.stringify(m),
          url: "/dc/delDocument"
        })
        .then(function(response) {
          _self.loadGridOutData(_self.currentFolder);
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
                  type: "error"
                });
          console.log(error);
      });
    },
    
    // 保存文件夹
    saveFolder(indata) {
      let _self = this;
      if(_self.folderAction == _self.$t("application.newFolder"))
      {
        _self.newFolder(indata);
      }
      else
      {
        _self.axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          datatype: "json",
          method: "post",
          data: JSON.stringify(indata),
          url: "/admin/updateFolder"
        })
        .then(function(response) {
          // _self.$message(_self.$t("message.saveSuccess"));
          _self.$message({
              showClose: true,
              message: _self.$t("message.saveSuccess"),
              duration: 2000,
              type: 'success'
            });
          _self.folderDialogVisible = false;
        })
        .catch(function(error) {
          console.log(error);
        });
      }
    },
    //查询文档
    searchItem() {
     this.loadGridData(this.currentFolder);
    //  this. loadPageInfo(this.currentFolder);
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

body,html{
			height:100%;
			margin:0px;
			padding:0px;
			overflow:hidden;
		}
    .left,.right{
			width:47.5%;
      }
      .middle{
        width:5%;
      }
		.left,.middle,.right{
			/* width:200px; */
			/* height:100px; */
			/* background-color:rgb(34,124,134); */
			float:left;
			height:100%;
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
