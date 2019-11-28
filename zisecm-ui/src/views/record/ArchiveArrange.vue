<template>
  <div>
    <el-dialog :visible.sync="printsVisible">
      <PrintPage ref='printPage' v-bind:archiveId="this.archiveId"></PrintPage>
    </el-dialog>
    <el-dialog :visible.sync="printVolumesVisible"  width="80%">
      <PrintVolumes ref='printVolumes' v-bind:archiveId="this.archiveId" v-bind:currentFolderId="this.currentFolder.id"></PrintVolumes>
    </el-dialog>
    
    <el-dialog :title="dialogName+$t('application.property')" :visible.sync="propertyVisible" @close="propertyVisible = false">
      <ShowProperty ref="ShowProperty"  @onSaved="onSaved" width="560" v-bind:itemId="selectedItemId" v-bind:folderId="currentFolder.id" v-bind:typeName="currentFolder.typeName"></ShowProperty>
      <div slot="footer" class="dialog-footer">
        <el-button @click="saveItem">{{$t('application.save')}}</el-button> <el-button @click="propertyVisible = false">{{$t('application.cancel')}}</el-button>
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

    <iframe frameborder="0" name="PDFViewer" id="PDFViewer" style="width:100%;height:760px;display:none;" ref="PDFViewer"></iframe>
    <el-dialog :visible.syn="imageViewVisible" @close="imageViewVisible = false">
      <div v-if=" currentType!='' && imageFormat.indexOf(currentType)>-1">
         <viewer :images="imageArray" @inited="inited" class="viewer" ref="viewer" >
          <img v-for="src in imageArray" :src="src" :key="src" width="240" @click="onImageClick" style="cursor:hand">
        </viewer>
      </div>
      <div v-else>
        <a :href="imageArray[0]" target="_blank">{{$t('application.download')}}</a>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="imageViewVisible = false">{{$t('application.cancel')}}</el-button>
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
      <table border="0" width="100%" >
          <tr>
            <td class="navbar" colspan="2">
              <el-breadcrumb>
                <el-breadcrumb-item>{{$t('menu.dataCenter')}}</el-breadcrumb-item>
                <el-breadcrumb-item>{{$t('menu.folderClassification')}}</el-breadcrumb-item>
              </el-breadcrumb>
            </td>
            
          </tr>
          <tr>
            <td rowspan="2" align="left" valign="top">
              <el-tree
                      :props="defaultProps"
                      :data="dataList"
                      node-key="id"
                      :render-content="renderContent"
                      default-expand-all
                      highlight-current
                      @node-click="handleNodeClick">
                    </el-tree>
            </td>
            <td>
              <table border="0" width="100%" class="topbar">
                <tr>
                  <!-- <td align="left" width="160px"> -->
                    <!-- <el-tooltip  class="item" effect="dark" :content="$t('application.newFolder')" placement="top">
                      <el-button type="primary" icon="el-icon-circle-plus" circle @click="onNewFolder()"></el-button>
                    </el-tooltip>
                    <el-tooltip  class="item" effect="dark" :content="$t('application.edit')+$t('application.folder')" placement="top">
                      <el-button type="primary" icon="el-icon-info" circle @click="onEditFolder()"></el-button>
                    </el-tooltip>
                    <el-tooltip  class="item" effect="dark" :content="$t('application.delete')+$t('application.folder')" placement="top">
                      <el-button type="primary" icon="el-icon-delete" circle @click="onDeleleFolder()"></el-button>
                    </el-tooltip> -->
                    
                  <!-- </td> -->
                  <td align="left" width="160px">
                    <el-input  v-model="inputkey" :placeholder="$t('message.pleaseInput')+$t('application.keyword')" @change="searchItem" prefix-icon="el-icon-search"></el-input>
                    <el-radio v-model="radio" label="卷盒" @change="changeRadio">卷盒</el-radio>
                    <el-radio v-model="radio" label="图册" @change="changeRadio">图册</el-radio>
                  </td>
                  <td>
                    <!-- <el-button type="primary" icon="el-icon-edit"  @click="newArchiveItem()">{{$t('application.newDocument')}}</el-button> -->
                    
                    <el-button type="primary" icon="el-icon-edit"  @click="newArchiveItem('卷盒')">{{$t('application.newArchive')}}</el-button>
                    <el-button type="primary" icon="el-icon-edit"  @click="newArchiveItem('图册')">{{$t('application.newVolume')}}</el-button>
                    <el-button type="primary" icon="el-icon-delete"  @click="onDeleleArchiveItem()">{{$t('application.delete')+$t('application.document')}}</el-button>
                    <el-button type="primary" icon="el-icon-s-order"  @click="takeNumbers">{{$t('application.takeNumbers')}}</el-button>
                    <!-- <el-button type="primary" icon="el-icon-s-release"  @click="onClosePage()">{{$t('application.sealVolume')}}</el-button>
                    <el-button type="primary" icon="el-icon-folder-opened"  @click="onOpenPage()">{{$t('application.openPage')}}</el-button> -->
                    <el-button type="primary" icon="el-icon-printer" @click="printsVisible = true">{{$t('application.PrintCover')}}</el-button>
                    <el-button type="primary" icon="el-icon-printer" @click="printVolumesVisible = true">{{$t('application.PrintVolumes')}}</el-button>
                  
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        <tr>
          <td>
            <table border="0" width="100%">
                <tr valign="top">
                  <!-- <td align="left" width="160px" >
                    
                  </td> -->
                  <td>
                    <div>
                      <DataGrid ref="mainDataGrid" key="main" v-bind:itemDataList="itemDataList"
                      v-bind:columnList="gridList" @pagesizechange="pageSizeChange"
                      @pagechange="pageChange" v-bind:itemCount="itemCount"
                      @rowclick="showInnerFile" @selectchange="selectChange"></DataGrid>

                      
                    </div>
                    <div class="left">
                      <span style="float:left;text-align:left;">卷内文件列表</span>
                      <!-- <el-button type="primary" plain size="small" title="自动组卷"  @click="autoPaper()">自动组卷</el-button> -->
                      <!-- <el-button type="primary" plain size="small"  @click="childrenTypeSelectVisible=true">{{$t('application.createDocument')}}</el-button>
                      <el-button type="primary" plain size="small" :title="$t('application.addReuseFile')"  @click="reuseVisible=true">{{$t('application.addReuseFile')}}</el-button>
                      
                      <el-button type="primary" plain size="small" title="删除"  @click="onDeleleFileItem()">删除</el-button>
                      <el-button type="primary" plain size="small" title="挂载文件"  @click="importdialogVisible=true;uploadUrl='/dc/mountFile'">挂载文件</el-button>
                      <el-button type="primary" plain size="small" :title="$t('application.viewRedition')"  @click="importdialogVisible=true;uploadUrl='/dc/addRendition'">格式副本</el-button> -->
                      <el-button type="primary" plain size="small"  @click="childrenTypeSelectVisible=true">{{$t('application.createDocument')}}</el-button>
                      <el-button type="primary" plain size="small" title="挂载文件"  @click="beforeMount(selectedInnerItems);uploadUrl='/dc/mountFile'">挂载文件</el-button>
                      <el-button type="primary" plain size="small" :title="$t('application.viewRedition')"  @click="beforeMount(selectedInnerItems);uploadUrl='/dc/addRendition'">格式副本</el-button>
                      
                      <el-button type="primary" plain size="small" title="上移"  @click="onMoveUp()">上移</el-button>
                      <el-button type="primary" plain size="small" title="下移"  @click="onMoveDown()">下移</el-button>
                      <DataGrid ref="leftDataGrid" key="left" v-bind:itemDataList="innerDataList"
                      v-bind:columnList="innerGridList" v-bind:itemCount="innerCount"
                       @pagesizechange="innerPageSizeChange" @rowclick="selectOneFile"
                      @pagechange="innerPageChange" @selectchange="selectInnerChange"></DataGrid>
                    </div>
                    <div class="middle">
                      <el-button  v-show="showButton" type="primary" plain size="small" title="移除案卷"  @click="removeFromArchive()">&gt;</el-button>
                      <br>
                      <el-button  v-show="showButton" type="primary" plain size="small" title="添加到案卷"  @click="addToArchive()">&lt;</el-button>
                      
                    </div>
                    <div class="right">
                      <span style="float:left;text-align:left;">未组卷列表</span>
                      
                      <el-button type="primary" plain size="small" title="挂载文件"  @click="beforeMount(selectedOutItems);uploadUrl='/dc/mountFile'">挂载文件</el-button>
                      <el-button type="primary" plain size="small" :title="$t('application.viewRedition')"  @click="beforeMount(selectedOutItems);uploadUrl='/dc/addRendition'">格式副本</el-button>
                      <el-button type="primary" plain size="small" title="删除"  @click="onDeleleFileItem()">删除</el-button>
                      <DataGrid ref="outDataGrid" key="right" v-bind:itemDataList="outerDataList"
                      v-bind:columnList="outerGridList" v-bind:itemCount="outerCount"
                       @pagesizechange="outerPageSizeChange"
                      @pagechange="outerPageChange" @selectchange="selectOutChange"></DataGrid>
                       
                    </div>
                  </td>
                </tr>
              </table>
          </td>
        </tr>
      </table>
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
export default {
  name: "FolderClassification",
  components: {
    ShowProperty: ShowProperty,
    // PDFViewer: PDFViewer,
    DataGrid:DataGrid,
    PrintPage:PrintPage,
    PrintVolumes:PrintVolumes
    //Prints:Prints
  },
  data() {
    return {
      imageFormat: 'jpg,jpeg,bmp,gif,png',
      baseServerUrl: this.baseURL,
      currentLanguage: "zh-cn",
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
      selectedItems: [],
      selectedFileId:"",
      selectedOutItems: [],
      selectedInnerItems:[],
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
        console.log(JSON.stringify(_self.dataList));
        _self.loading = false;
      })
      .catch(function(error) {
        console.log(error);
        _self.loading = false;
      });
  },
  methods: {
    selectOneFile(row){
      let _self = this;
      if(_self.selectRow)
      {
        _self.selectedFileId=row.ID;
      }
    },
    changeRadio(val){
      let _self=this;
       _self.loadGridData(_self.currentFolder);
       _self.innerDataList=[];
       _self.outerDataList=[];
    },
    onMoveUp(){
      let _self=this;
      if(_self.selectedInnerItems.length!=1){
         _self.$message("请选择一条数据！");
        return;
      }
      var m = new Map();
      m.set('parentId',_self.archiveId);
      m.set('childId',_self.selectedInnerItems[0].ID);
       _self.axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        method: 'post',
        data: JSON.stringify(m),
        url: "/dc/moveUp"
      })
        .then(function(response) {
          
          let code = response.data.code;
          //console.log(JSON.stringify(response));
          if(code==1){
            _self.showInnerFile(null);
          }
          else{
             _self.$message( response.data.message);
          }
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    beforeMount(selrow){
      
      let _self=this;
      _self.fileList=[];
      if(selrow.length!=1){
         _self.$message("请选择一条数据！");
        return;
      }
      _self.uploadID=selrow[0].ID;
      _self.importdialogVisible=true;
      
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
      console.log("UploadData getData");
      console.log(formdata);
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
        _self.$message("导入成功!");
      })
      .catch(function(error) {
        console.log(error);
      });
    },
    onMoveDown(){
      let _self=this;
      if(_self.selectedInnerItems.length!=1){
         _self.$message("请选择一条数据！");
        return;
      }
      var m = new Map();
      m.set('parentId',_self.archiveId);
      m.set('childId',_self.selectedInnerItems[0].ID);
       _self.axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        method: 'post',
        data: JSON.stringify(m),
        url: "/dc/moveDown"
      })
        .then(function(response) {
          
          let code = response.data.code;
          //console.log(JSON.stringify(response));
          if(code==1){
            _self.showInnerFile(null);
          }
          else{
             _self.$message( response.data.message);
          }
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    handleChange(file, fileList){
      this.fileList = fileList;
    },
    // 分页 当前页改变
    innerPageChange(val)
    {
      this.innerCurrentPage = val;
      this.showInnerFilepageChange();
      //console.log('handleCurrentChange', val);
    },
    
    // 加载表格样式
    loadOutGridInfo() 
    {
      let _self = this;
      _self.loading = true;
      var m = new Map();
      m.set('gridName','ArchiveGrid');
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
          
          _self.outerGridList = response.data.data;
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    loadInnerGridInfo(){
      let _self = this;
      _self.loading = true;
      var m = new Map();
      m.set('gridName','ArchiveGrid');
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
    showInnerFile(row){
      let _self = this;
      if(row!=null){
        _self.selectRow=row;
      }
      _self.selectedChildrenType=[];
      _self.getTypeNamesByMainList(_self.selectRow.SUB_TYPE);
      
      _self.loadInnerGridInfo();
      var m = new Map();
      m.set('gridName','ArchiveGrid');
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
      console.log(data);
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
    formatImage(indata){
      var url = './static/img/format/f_'+indata+'_16.gif';
      return url;
    },
    dateFtt(fmt, date)
    {
      var o = {   
        "M+" : date.getMonth()+1,                 //月份   
        "d+" : date.getDate(),                    //日   
        "h+" : date.getHours(),                   //小时   
        "m+" : date.getMinutes(),                 //分   
        "s+" : date.getSeconds(),                 //秒   
        "q+" : Math.floor((date.getMonth()+3)/3), //季度   
        "S"  : date.getMilliseconds()             //毫秒   
      };   
      if(/(y+)/.test(fmt))   
          fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));   
      for(var k in o)   
          if(new RegExp("("+ k +")").test(fmt))   
              fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
      return fmt;   
    },
    dateFormat(value){
      var crtTime = new Date(value);
      return this.dateFtt("yyyy-MM-dd",crtTime);
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

      if(_self.selectedInnerItems.length===0)
      {
        alert('请选择一条或多条未组卷文件！');
        return;
      }
      var m = new Map();
      m.set('archiveId',_self.archiveId);
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

      if(_self.selectedOutItems.length===0)
      {
        alert('请选择一条或多条未组卷文件！');
        return;
      }
      var m = new Map();
      m.set('archiveId',_self.archiveId);
      let outIds=new Array();
      for(let i=0;i<_self.selectedOutItems.length;i++)
      {
        outIds.push(_self.selectedOutItems[i].ID);
      }
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
        key = "coding like '%"+key+"%' or title like '%"+key+"%'"; 
        if(_self.radio=='卷盒'){
          m.set('condition',key+" and TYPE_NAME='卷盒' ");
        }else{
          m.set('condition',key+" and TYPE_NAME='图册' ");
        }
      }else{
        if(_self.radio=='卷盒'){
          m.set('condition'," TYPE_NAME='卷盒' ");
        }else{
          m.set('condition'," TYPE_NAME='图册' ");
        }
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
        console.log(_self.dataList);
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
          url: "/admin/getFolder"
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
      
    },
    loadGridOutData(indata){
      let _self = this;
      _self.loading = true;
      let whereSql='';
      for(let i=0;i<_self.childrenTypes.length;i++){
        whereSql+=" TYPE_NAME='"+_self.childrenTypes[i]+"' ";
        if(i!=_self.childrenTypes.length-1){
          whereSql+=" or "
        }
      }
      
      var m = new Map();
      m.set('gridName',indata.gridView);
      if(whereSql==''){
        m.set('condition'," folder_id='"+indata.id+"' ");
      }else{
        m.set('condition',"folder_id='"+indata.id+"' and ("+whereSql+")");
      }
      
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
        if(_self.$refs.ShowProperty){
          _self.$refs.ShowProperty.myItemId = "";
          _self.dialogName=typeName;
          _self.$refs.ShowProperty.myTypeName = typeName;
          _self.$refs.ShowProperty.myFolderId = _self.currentFolder.id;
          _self.$refs.ShowProperty.loadFormInfo();
        }
      }
      else{
        _self.$message(_self.$t("message.pleaseSelectFolder"));
      }
    },
    newArchiveFileItem(typeName,selectedRow)
    {
      let _self = this;
      if(selectedRow.ID ){
        _self.selectedItemId = "";
        _self.propertyVisible = true; 
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
      }
      else{
        _self.$message(_self.$t("message.pleaseSelectFolder"));
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
        _self.$message(_self.$t("message.pleaseSelectFolder"));
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
            _self.$message("创建成功!");
            _self.propertyVisible=false;
            
            _self.loadGridData(_self.currentFolder);
            _self.showInnerFile(null);
            // _self.loadGridOutData(_self.currentFolder);
            _self.outerDataList=[];
          }
          else{
             _self.$message("新建失败!");
          }
        })
        .catch(function(error) {
          _self.$message("新建失败!");
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
    onSaved(indata){
      if(indata=='update')
      {
        this.$message(this.$t("message.saveSuccess"));
      }
      else
      {
        this.$message("新建成功!");
      }
      this.propertyVisible = false;
      this.loadGridData(this.currentFolder);
      this.loadGridOutData(this.currentFolder);
    },
    //开卷
    onOpenPage(){
      let _self = this;
      var m=new Map();
      let tab = _self.selectedItems;
      if(tab.length<1)
      {
        _self.$message("请选择一条或多条案卷数据！");
        return;
      }
      m.set("files",tab);
      console.log(JSON.stringify(m));
      _self.axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data: JSON.stringify(m),
          url: "/dc/openPage"
        })
        .then(function(response) {
          _self.loadGridData(_self.currentFolder);
          _self.$message(_self.$t("message.openPage")+_self.$t("message.success"));
        })
        .catch(function(error) {
          _self.$message(_self.$t("message.openPage")+_self.$t("message.failured"));
          console.log(error);
      });
    },

    //封卷
    onClosePage(){
      let _self = this;
      var m=new Map();
      let tab = _self.selectedItems;
      if(tab.length<1)
      {
        _self.$message("请选择一条或多条案卷数据！");
        return;
      }
      m.set("files",tab);
      console.log(JSON.stringify(m));
      _self.axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data: JSON.stringify(m),
          url: "/dc/closePage"
        })
        .then(function(response) {
          _self.loadGridData(_self.currentFolder);
          _self.$message(_self.$t("message.closePage")+_self.$t("message.success"));
        })
        .catch(function(error) {
          _self.$message(_self.$t("message.closePage")+_self.$t("message.failured"));
          console.log(error);
      });
    },
    // 删除文档事件
    onDeleleArchiveItem() {
      let _self = this;
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
      var m = [];
      let tab = _self.selectedItems;
      
      var i;
      for(i in tab){
        
        m.push(tab[i]["ID"]);
      }
      console.log(JSON.stringify(m));
      _self.axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data: JSON.stringify(m),
          url: "/dc/delDocumentAndRelation"
        })
        .then(function(response) {
          _self.loadGridData(_self.currentFolder);
           
            _self.showInnerFile(null);
          _self.$message(_self.$t("message.deleteSuccess"));
        })
        .catch(function(error) {
          _self.$message(_self.$t("message.deleteFailured"));
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
    takeNumbers(){
      let _self =this;
      if(_self.radio=='卷盒'&&_self.selectedItems.length<1){
         _self.$message("请选择一条或多条卷盒数据！");
          return;
      }
      let tab=_self.selectedItems;
      let m = [];
      let i;
      for(i in tab){
        if(tab[i]["CODING"]!=undefined &&tab[i]["CODING"]!=="")
        {
          _self.$message("卷盒"+tab[i]["CODING"]+"已取过号不需要再取号!");
          return;
        }
        m.push(tab[i]["ID"]);
      }
      _self.axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data: JSON.stringify(m),
          url: "/dc/takeNumbers"
        })
        .then(function(response) {
          _self.loadGridData(_self.currentFolder);
           
            _self.showInnerFile(null);
          _self.$message(_self.$t("message.takeNumberSuccess"));
        })
        .catch(function(error) {
          _self.$message(_self.$t("message.takeNumberFaild"));
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
          _self.$message("案卷："+tab[i]["CODING"]+"已封卷不允许删除！");
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
    // 自动组卷
    autoPaper() {
      let _self = this;
      var m=new Map();
      let tab = _self.selectedOutItems;
      m.set("files",tab);
      console.log(JSON.stringify(m));
      _self.axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data: JSON.stringify(m),
          url: "/dc/autoPaper"
        })
        .then(function(response) {
          _self.loadGridOutData(_self.currentFolder);
          _self.loadGridData(_self.currentFolder);
          _self.$message(_self.$t("message.paper"));
        })
        .catch(function(error) {
          _self.$message(_self.$t("message.paperFaild"));
          console.log(error);
      });
    },
    // 删除文档
    deleleFileItem() {
      let _self = this;
      var m = [];
      let tab = _self.selectedOutItems;
      var i;
      for(i in tab){
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
          _self.loadGridOutData(_self.currentFolder);
          _self.$message(_self.$t("message.deleteSuccess"));
        })
        .catch(function(error) {
          _self.$message(_self.$t("message.deleteFailured"));
          console.log(error);
      });
    },
    // 查看属性
    showItemProperty(indata){
      let _self = this;
      _self.selectedItemId = indata.ID ;
      _self.propertyVisible = true;
      if(_self.$refs.ShowProperty){
        _self.dialogName=indata.TYPE_NAME;
        _self.$refs.ShowProperty.myItemId = indata.ID ;
        _self.$refs.ShowProperty.loadFormInfo();
      }
    },
    // 查看内容
    showItemContent(indata){
      let _self = this;
      _self.imageArray = [];
      _self.currentType = indata.FORMAT_NAME;
      // 拦截器会自动替换成目标url
      _self.imageArray[0] =  "/dc/getContent?id="+indata.ID+"&token="+sessionStorage.getItem('access-token');
      if(_self.currentType == "pdf"){
         window.open("./static/pdfviewer/web/viewer.html?file="+encodeURIComponent(_self.imageArray[0])+"&.pdf");
      }else{
         _self.imageViewVisible =true;
      }
    },
    closepdf(){
      this.imageViewVisible=false
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
          _self.$message(_self.$t("message.saveSuccess"));
          _self.folderDialogVisible = false;
        })
        .catch(function(error) {
          console.log(error);
        });
      }
    },
    // 删除文件夹
    delFolder() {
      let _self = this;
      _self.axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          datatype: "json",
          method: "post",
          data: JSON.stringify(_self.currentFolder),
          url: "/admin/deleteFolder"
        })
        .then(function(response) {
          if(response.data.code==1)
          {
            _self.$message(_self.$t("message.deleteSuccess"));
            _self.refreshFolderData();
          }
          else
          {
            _self.$message(response.data.msg);
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    // 新建文件夹事件
    onNewFolder()
    {
      if(!this.currentFolder||!this.currentFolder.id)
      {
        this.$message(_self.$t("message.cannotCreateRoot"));
        return;
      }
      this.folderAction = _self.$t("application.newFolder");
      this.folderForm ={
        id: null,
        name: "",
        description: "",
        parentId: this.currentFolder.id,
        typeName: this.currentFolder.typeName,
        gridView: this.currentFolder.gridView,
        aclName: this.currentFolder.aclName
      };
      this.folderDialogVisible = true;
    },
    // 编辑文件夹事件
    onEditFolder()
    {
      if(!this.currentFolder||!this.currentFolder.id)
      {
        this.$message(this.$t("message.pleaseSelectFolder"));
        return;
      }
      this.folderAction = this.$t("application.edit")+this.$t("application.folder");
      this.folderForm = this.currentFolder;
      this.folderDialogVisible = true;
    },
    onDeleleFolder()
    {
      let _self = this;
      if(!_self.currentFolder||!_self.currentFolder.id)
      {
        _self.$message(_self.$t("message.pleaseSelectFolder"));
        return;
      }
      _self.$confirm(_self.$t("message.deleteInfo"), _self.$t("application.info"), {
          confirmButtonText: _self.$t("application.ok"),
          cancelButtonText: _self.$t("application.cancel"),
          type: 'warning'
        }).then(() => {
          _self.delFolder();
        }).catch(() => {
          // this.$message({
          //   type: 'info',
          //   message: '已取消删除'
          // });          
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
          url: "/admin/newFolder"
        })
        .then(function(response) {
          _self.folderDialogVisible = false;
          _self.currentFolder.children = [];
          _self.currentFolder.extended = false;
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
          _self.$message(_self.$t("message.copySuccess"));
          _self.dialogVisible = false;
          _self.refreshFolderData();
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    //查询文档
    searchItem() {
     this.loadGridData(this.currentFolder);
     this. loadPageInfo(this.currentFolder);
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
