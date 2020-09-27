<template>
  <div>
    <!-- 选字段对话框 -->
    <el-dialog
      title="选择需要展示的字段"
      :visible.sync="columnsInfo.dialogFormVisible"
      width="40%"
      center
      top="15vh"
    >
      <el-checkbox
        :indeterminate="columnsInfo.isIndeterminate"
        v-model="columnsInfo.checkAll"
        @change="handleCheckAllChange"
      >全选</el-checkbox>
      <div style="margin: 15px 0;"></div>
      <el-checkbox-group v-model="showFields" @change="handleCheckedColsChange">
        <el-checkbox
          v-for="item in gridList"
          :label="item.attrName"
          :key="item.attrName"
        >{{item.label}}</el-checkbox>
      </el-checkbox-group>
      <div slot="footer" class="dialog-footer">
        <el-button @click="columnsInfo.dialogFormVisible=false" size="medium">{{$t('application.cancel')}}</el-button>
        <el-button type="primary" @click="confirmShow" size="medium">确定</el-button>
      </div>
    </el-dialog>
    <el-dialog
      :title="$t('application.property')"
      :visible.sync="propertyVisible"
      @close="propertyVisible = false"
      width="96%"
    >
    <el-tabs type="border-card" >
      <el-tab-pane label="基本信息">
        <ShowProperty
          ref="ShowProperty"
          @onSaved="onSaved"
          width="100%"
          v-bind:itemId="selectedItemId"
          v-bind:folderId="currentFolder.id"
          v-bind:typeName="currentFolder.typeName"
        ></ShowProperty>
      </el-tab-pane>
      <el-tab-pane label="系统信息">
        <SystemInfo   ref="SystemInfo"
          width="100%"
          v-bind:itemData="currentDocument">
        </SystemInfo>
      </el-tab-pane>
      <el-tab-pane label="权限管理">
        <ObjectAcl
          ref="ObjectAcl"
          width="100%"
          v-bind:name="currentDocument.ACL_NAME"
          v-bind:docId="currentDocument.ID"
        ></ObjectAcl>
      </el-tab-pane>
      <!--
      <el-tab-pane label="启动流程" >
        <StartWorkflow
          ref="StartWorkflow"
          @onSaved="onSaved"
          width="100%"
          v-bind:itemId="selectedItemId"
          v-bind:folderId="currentFolder.id"
          v-bind:typeName="currentFolder.typeName"
        ></StartWorkflow>
      </el-tab-pane>
      -->
    </el-tabs>
      
      <div slot="footer" class="dialog-footer">
        <el-button @click="saveItem">{{$t('application.save')}}</el-button>
        <el-button @click="propertyVisible = false">{{$t('application.cancel')}}</el-button>
      </div>
    </el-dialog>
    <el-dialog
      :title="folderAction"
      :visible.sync="folderDialogVisible"
      @close="folderDialogVisible = false" width="80%"
    >
    <el-tabs type="border-card">
      <el-tab-pane label="基本信息">
      <el-form :model="folderForm">
        <el-form-item :label="$t('field.name')" :label-width="formLabelWidth">
          <el-input v-model="folderForm.name" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item :label="$t('field.description')" :label-width="formLabelWidth">
          <el-input v-model="folderForm.description" auto-complete="off"></el-input>
        </el-form-item>
        <el-col :span="12" v-show="clientPermission>3">
          <el-form-item label="代码" :label-width="formLabelWidth">
            <el-input v-model="folderForm.coding" auto-complete="off"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12" v-show="clientPermission>3">
          <el-form-item v-show="clientPermission>3" label="完整代码" :label-width="formLabelWidth">
            <el-input v-model="folderForm.fullCoding" auto-complete="off"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12" v-show="clientPermission>4">
          <el-form-item label="ACL名称" :label-width="formLabelWidth">
            <el-input v-model="folderForm.aclName" auto-complete="off"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12" v-show="clientPermission>4">
          <el-form-item  label="列表名称" :label-width="formLabelWidth">
            <el-input v-model="folderForm.gridView" auto-complete="off"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24" v-show="clientPermission>3">
            <el-form-item label="排序" :label-width="formLabelWidth">
              <el-input v-model="folderForm.orderIndex" auto-complete="off"></el-input>
            </el-form-item>
            </el-col>
      </el-form>
      </el-tab-pane>
      <el-tab-pane label="权限管理">
        <FolderAcl
          ref="FolderAcl"
          width="100%"
          v-bind:name="folderForm.aclName"
          v-bind:folderId="folderForm.id"
        ></FolderAcl>
        </el-tab-pane>
    </el-tabs>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="saveFolder(folderForm)">{{$t('application.ok')}}</el-button>
        <el-button @click="folderDialogVisible = false">{{$t('application.cancel')}}</el-button>
      </div>
    </el-dialog>
    <el-dialog title="移动位置" :visible.sync="moveDialogVisible" @close="moveDialogVisible = false">
      <el-form>
        <el-form-item label="当前ID" :label-width="formLabelWidth">{{getSelectedIds()}}</el-form-item>
        <el-form-item label="目标文件夹ID" :label-width="formLabelWidth">
          <FolderSelector v-model="targetFolderId" v-bind:inputValue="targetFolderId"></FolderSelector>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="handleMoveItem()">{{$t('application.ok')}}</el-button>
        <el-button @click="moveDialogVisible = false">{{$t('application.cancel')}}</el-button>
      </div>
    </el-dialog>
    <el-dialog
      :title="uploadType==0?'更新主文件':'更新格式副本'"
      :visible.sync="udialogVisible"
      v-loading="uploading"
    >
      <el-form  label-position="right" label-width="120px">
        <el-form-item label="文件" :label-width="formLabelWidth">
          <el-upload
            :limit="1"
            :file-list="newFileList"
            action
            :on-change="handleFileChange"
            :auto-upload="false"
            :multiple="false"
          >
            <el-button slot="trigger" size="small" type="primary">{{$t('application.selectFile')}}</el-button>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="udialogVisible = false">{{$t('application.cancel')}}</el-button>
        <el-button type="primary" @click="updateNewFile()">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog
      title="Json包导入"
      :visible.sync="importDialogVisible"
      v-loading="uploading"
    >
      <el-form  label-position="right" label-width="120px">
        <el-form-item label="文件" :label-width="formLabelWidth">
          <el-upload
            :limit="1"
            :file-list="jsonFileList"
            action
            :on-change="handleJsonFileChange"
            :auto-upload="false"
            :multiple="false"
          >
            <el-button slot="trigger" size="small" type="primary">{{$t('application.selectFile')}}</el-button>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="importDialogVisible = false">{{$t('application.cancel')}}</el-button>
        <el-button type="primary" @click="importPackage()">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog
      title="文件列表"
      :visible.sync="itemDialogVisible"
      width="80%"
      @close="itemDialogVisible = false"
    >
      <InnerItemViewer v-bind:id="currentId"></InnerItemViewer>
    </el-dialog>
 
        <el-row style="padding-top:10px;padding-bottom:10px;">
          <el-col :span="4" style="text-align: left">
            <el-tooltip
              class="item"
              effect="dark"
              :content="$t('application.newFolder')"
              placement="top"
            >
              <el-button type="primary" icon="el-icon-circle-plus" circle @click="onNewFolder()"></el-button>
            </el-tooltip>
            <el-tooltip
              class="item"
              effect="dark"
              :content="$t('application.edit')+$t('application.folder')"
              placement="top"
            >
              <el-button type="primary" icon="el-icon-info" circle @click="onEditFolder()"></el-button>
            </el-tooltip>
            <el-tooltip
              class="item"
              effect="dark"
              :content="$t('application.delete')+$t('application.folder')"
              placement="top"
            >
              <el-button type="primary" icon="el-icon-delete" circle @click="onDeleleFolder()"></el-button>
            </el-tooltip>
             <el-tooltip
              class="item"
              effect="dark"
              :content="$t('application.move')+$t('application.folder')"
              placement="top"
            >
              <el-button type="primary" icon="el-icon-top-right" circle @click="moveFolder()"></el-button>
            </el-tooltip>
          </el-col>
          <el-col :span="4" style="text-align: left">
            <el-input
              v-model="inputkey"
              :placeholder="$t('message.pleaseInput')+$t('application.keyword')"
              @change="searchItem"
              prefix-icon="el-icon-search"
            ></el-input>
          </el-col>
          <el-col :span="15" style="text-align: left">
            &nbsp; &nbsp; 
            <el-button
              type="primary"
              icon="el-icon-edit"
              @click="newItem()"
            >{{$t('application.new')}}</el-button>
            <el-button
              type="primary"
              icon="el-icon-delete"
              @click="onDeleleItem()"
            >{{$t('application.delete')}}</el-button>
            <el-button type="primary" icon="el-icon-top-right" @click="moveItem()">移动</el-button>
            <el-button type="primary" icon="el-icon-document-copy" @click="copyItem()">复制</el-button>
            <el-button type="primary" icon="el-icon-upload2" @click="showUpdateFile(0)">更新</el-button>
            <el-button type="primary" icon="el-icon-upload2" @click="showUpdateFile(1)">副本</el-button>
            <el-button
              type="primary"
              icon="el-icon-download"
              @click="handleExportItem()"
            >导出</el-button>
            <el-button
              type="primary"
              icon="el-icon-upload2"
              @click="importDialogVisible = true"
            >导入</el-button>
          </el-col>
        </el-row>
      <div :style="{position:'relative',height: asideHeight+'px'}">
      <split-pane split="vertical" @resize="resize" :min-percent='10' :default-percent='15'>
      <template slot="paneL">
         <el-container :style="{height:treeHeight+'px',width:asideWidth,overflow:'auto'}">
          <el-tree
            :props="defaultProps"
            :data="dataList"
            node-key="id"
            style="width:100%;"
            :render-content="renderContent"
            default-expand-all
            highlight-current
            @node-click="handleNodeClick"
          ></el-tree>
       </el-container>
      </template>
     <template slot="paneR">
          <el-row>
            <el-table
              :height="tableHeight"
              :data="itemDataList"
              border
              v-loading="loading"
              @selection-change="selectChange"
              @sort-change="sortchange"
              @header-dragend="onHeaderDragend"
              
              style="width: 100%"
            >
              <el-table-column type="selection" width="40" @selection-change="selectChange"></el-table-column>
              <el-table-column :label="$t('field.indexNumber')" width="70">
                <template slot-scope="scope">
                  <span>{{(currentPage-1) * pageSize + scope.$index+1}}</span>
                </template>
              </el-table-column>
              <el-table-column width="40">
                <template slot-scope="scope">
                  <img :src="'./static/img/format/f_'+scope.row.FORMAT_NAME+'_16.gif'" border="0" />
                </template>
              </el-table-column>
              <div v-for="(citem,idx) in gridList" :key="'C_'+idx">
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
                          <span @click="rowClick(scope.row)">{{scope.row[citem.attrName]}}</span>
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
                          <span>{{dateFormat(scope.row[citem.attrName])}}</span>
                        </div>
                        <div v-else>
                          <span @click="rowClick(scope.row)">{{scope.row[citem.attrName]}}</span>
                        </div>
                      </template>
                    </el-table-column>
                  </div>
                </div>
              </div>
              <el-table-column :label="$t('application.operation')" width="130">
                <template slot="header">
                  <el-button icon="el-icon-s-grid" size="small" @click="dialogFormShow"></el-button>
                </template>
                <template slot-scope="scope">
                  <el-button
                    type="primary"
                    plain
                    size="small"
                    :title="$t('application.view')"
                    icon="el-icon-picture-outline"
                    @click="showNewWindow(scope.row.ID)"
                  ></el-button>
                  <el-button
                    type="primary"
                    plain
                    size="small"
                    :title="$t('application.property')"
                    icon="el-icon-info"
                    @click="showItemProperty(scope.row)"
                  ></el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-row>
          <el-row>
            <el-pagination
              background
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="currentPage"
              :page-sizes="[10, 20, 50, 100, 200]"
              :page-size="pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="itemCount"
            ></el-pagination>
          </el-row>
         </template>
      </split-pane>
    </div>
  </div>
</template>

<script type="text/javascript">
import ShowProperty from "@/components/ShowProperty";
import FolderSelector from "@/components/controls/FolderSelector";
import InnerItemViewer from "./InnerItemViewer.vue";
import ObjectAcl from '@/components/controls/ObjectAcl';
import SystemInfo from '@/components/controls/SystemInfo';
import FolderAcl from '@/components/controls/FolderAcl';
import StartWorkflow from '@/views/workflow/StartWorkflow';

import "url-search-params-polyfill";

export default {
  name: "FolderClassification",

  components: {
    ShowProperty: ShowProperty,
    InnerItemViewer: InnerItemViewer,
    FolderSelector: FolderSelector,
    ObjectAcl: ObjectAcl,
    SystemInfo: SystemInfo,
    FolderAcl:FolderAcl,
    StartWorkflow: StartWorkflow
  },
  data() {
    return {
      activeTab:"基本信息",
      targetFolderId: "",
      moveDialogVisible: false,
      udialogVisible: false,
      itemDialogVisible: false,
      importDialogVisible: false,
      jsonFileList: [],
      jsonFile: null,
      newFileList: [],
      currentId: "",
      isMoveFolder:false,
      columnsInfo: {
        checkAll: true,
        checkedCities: [],
        temCol: [],
        dialogFormVisible: false,
        isIndeterminate: false
      },
      currentLanguage: "zh-cn",
      clientPermission:0,
      systemPermission:0,
      dataList: [],
      showFields: [],
      itemDataList: [],
      itemDataListFull: [],
      gridList: [],
      currentFolder: [],
      currentDocument: [],
      dataListFull: "",
      uploadFile:null,
      inputkey: "",
      loading: false,
      uploading: false,
      uploadType: 0, //0：主格式，1：格式副本
      pageSize: 20,
      itemCount: 0,
      selectedItemId: 0,
      currentPage: 1,
      dialogVisible: false,
      propertyVisible: false,
      dialogFormVisible: false,
      selectedItems: [],
      asideHeight: window.innerHeight - 150,
      treeHeight:window.innerHeight - 160,
      tableHeight: window.innerHeight - 180,
      asideWidth: '100%',
      folderAction: "",
      folderDialogVisible: false,
      imageArray: [""],
      imageViewVisible: false,
      imageViewer: Object,
      currentType: "",
      orderBy: "",
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
      defaultData: {
        gridView: "GeneralGrid"
      },
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
    let _self = this;
    var psize = localStorage.getItem("docPageSize");
    if (psize) {
      _self.pageSize = parseInt(psize);
    }
    _self.currentLanguage = localStorage.getItem("localeLanguage") || "zh-cn";
    _self.loading = true;
      if(_self.currentUser())
      {
        _self.clientPermission = Number(_self.currentUser().clientPermission);
        _self.systemPermission = Number(_self.currentUser().systemPermission);
      }
    axios
      .post("/admin/getFolder", 0)
      .then(function(response) {
        _self.dataList = response.data.data;
        //console.log(JSON.stringify(_self.dataList));
        _self.loading = false;
      })
      .catch(function(error) {
        console.log(error);
        _self.loading = false;
      });
    _self.loadGridInfo(_self.defaultData);
  },
  methods: {
    resize() {
      //console.log('resize')
      this.asideWidth = '100%';
    },
    showUpdateFile(indata) {
      if (this.selectedItems && this.selectedItems.length > 0) {
        this.uploadFile = [];
        this.uploadType = indata;
        this.udialogVisible = true;
      }
    },
    handleFileChange(file, fileList) {
      this.uploadFile = file;
    },
    handleJsonFileChange(file, fileList) {
      this.jsonFile = file;
    },
    updateNewFile() {
      let _self = this;
      if (_self.selectedItems && _self.selectedItems.length > 0) {
        if (_self.uploadType == 0) {
          _self.uploadPrimry();
        } else {
          _self.uploadRendition();
        }
      }
    },
    importPackage(){
      let _self = this;
      _self.uploading = true;
      let formdata = new FormData();
      formdata.append("folderId", _self.currentFolder.id);
      if (_self.jsonFile != "") {
        formdata.append("jsonFile", _self.jsonFile.raw);
      }
      axios
        .post("/admin/importPackage", formdata, {
          "Content-Type": "multipart/form-data"
        })
        .then(function(response) {
          _self.importDialogVisible = false;
          _self.loadGridData(_self.currentFolder);
          _self.$message(_self.$t('application.Import')+_self.$t('message.success'));
          _self.uploading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.uploading = false;
        });
    },
    uploadPrimry() {
      let _self = this;
      _self.uploading = true;
      let formdata = new FormData();
      formdata.append("id", _self.selectedItems[0].ID);
      if (_self.uploadFile != "") {
        formdata.append("uploadFile", _self.uploadFile.raw);
      }
      axios
        .post("/dc/updatePrimaryContent", formdata, {
          "Content-Type": "multipart/form-data"
        })
        .then(function(response) {
          _self.udialogVisible = false;
          _self.loadGridData(_self.currentFolder);
          _self.$message("更新成功!");
          _self.uploading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.uploading = false;
        });
    },
    uploadRendition() {
      let _self = this;
      _self.uploading = true;
      let formdata = new FormData();
      var data = {};
      data["ID"] = _self.selectedItems[0].ID; //_self.selectedInnerItems[0].ID;//_self.selectedFileId;
      formdata.append("metaData", JSON.stringify(data));

      if (_self.uploadFile != "") {
        formdata.append("uploadFile", _self.uploadFile.raw);
      }
      axios
        .post("/dc/addRendition", formdata, {
          "Content-Type": "multipart/form-data"
        })
        .then(function(response) {
          _self.udialogVisible = false;
          _self.$message("更新成功!");
          _self.uploading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.uploading = false;
        });
    },
    moveItem() {
      this.isMoveFolder = false;
      if (this.selectedItems && this.selectedItems.length > 0) {
        this.moveDialogVisible = true;
      }
    },
    moveFolder(){
      if(this.currentFolder){
        this.isMoveFolder = true;
        this.moveDialogVisible = true;
      }
    },
    handleMoveItem() {
      if(this.isMoveFolder){
        this.handleMoveFolder();
      }else{
        this.handleMoveDocument();
      }
    },
    handleMoveFolder(){
       let _self = this;
       if(_self.targetFolderId==_self.currentFolder.id || _self.targetFolderId==_self.currentFolder.parentId ){
          _self.$message("目标文件夹不能更当前文件夹相同!");
          return;
       }
       var fld = _self.currentFolder;
       fld.parentId = _self.targetFolderId;
        axios
          .post("/admin/updateFolder", JSON.stringify(fld))
          .then(function(response) {
            _self.$message(_self.$t("message.saveSuccess"));
            _self.moveDialogVisible = false;
            _self.loading = false;
            _self.refreshFolderData();
          })
          .catch(function(error) {
            console.log(error);
          });
    },
    handleMoveDocument(){
      let _self = this;
      _self.loading = true;
      var m = new Map();
      m.set("ids", _self.getSelectedIds());
      m.set("folderId", _self.targetFolderId);
      axios
        .post("/dc/moveDocument", JSON.stringify(m))
        .then(function(response) {
          if (response.data.code == 1) {
            _self.$message("目录移动成功。");
            _self.loadGridData(_self.currentFolder);
          } else {
            _self.$message("目录移动失败。<br>" + response.data.message);
          }
          _self.moveDialogVisible = false;
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    getSelectedIds() {
      if(this.isMoveFolder)
      {
        return this.currentFolder.id;
      }else{
        var str = "";
        this.selectedItems.forEach(function(val) {
          str += val.ID + ";";
        });
        return str;
      }
    },
    rowClick(row) {
      this.currentId = row.ID;
      if (row.TYPE_NAME == "卷盒" || row.TYPE_NAME == "图册") {
        this.itemDialogVisible = true;
      }
    },
    renderContent: function(h, { node, data, store }) {
      //console.log(data);
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
      let condition = id;
      let href = this.$router.resolve({
        path: "/viewdoc",
        query: {
          id: condition
          //token: sessionStorage.getItem('access-token')
        }
      });
      //console.log(href);
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
    formatImage(indata) {
      var url = "./static/img/format/f_" + indata + "_16.gif";
      return url;
    },
    // 表格行选择
    selectChange(val) {
      // console.log(JSON.stringify(val));
      this.selectedItems = val;
    },
    sortchange(column) {
      //console.log(JSON.stringify(column));
      //console.log(column.column.property);
      //console.log(column.column.order);//ascending, descending
      this.orderBy =
        column.column.property + column.column.order == "ascending"
          ? " ASC"
          : " DESC";
    },
    // 加载表格样式
    loadGridInfo(indata) {
      let _self = this;
      _self.loading = true;
      var m = new Map();
      m.set("gridName", indata.gridView);
      m.set("lang", _self.currentLanguage);
      axios
        .post("/dc/getGridViewInfo", JSON.stringify(m))
        .then(function(response) {
          _self.showFields = [];
          _self.gridList = response.data.data;
          //console.log(JSON.stringify(_self.gridList));
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
    },
    // 加载表格数据
    loadGridData(indata) {
      let _self = this;
      var key = _self.inputkey;
      if (key != "") {
        key = "CODING like '%" + key + "%' or NAME like '%" + key + "%'";
      }
      var m = new Map();
      m.set("gridName", indata.gridView);
      m.set("folderId", indata.id);
      m.set("condition", key);
      m.set("pageSize", _self.pageSize);
      m.set("pageIndex", _self.currentPage - 1);
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
      this.loadGridData(this.currentFolder);
      //console.log('handleSizeChange', val);
    },
    // 分页 当前页改变
    handleCurrentChange(val) {
      this.currentPage = val;
      this.loadGridData(this.currentFolder);
      //console.log('handleCurrentChange', val);
    },
    //刷新文件夹数据
    refreshFolderData() {
      let _self = this;
      _self.loading = true;
      axios
        .post("/admin/getFolder", 0)
        .then(function(response) {
          _self.dataList = response.data.data;
          //console.log(_self.dataList);
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
      if (indata.extended == false) {
        _self.loading = true;
        axios
          .post("/admin/getFolder", indata.id)
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
    // 新建文档
    newItem() {
      let _self = this;
      if (_self.currentFolder.id) {
        _self.selectedItemId = "";
        _self.propertyVisible = true;
        if (_self.$refs.ShowProperty) {
          _self.$refs.ShowProperty.myItemId = "";
          _self.$refs.ShowProperty.myTypeName = _self.currentFolder.typeName;
          _self.$refs.ShowProperty.myFolderId = _self.currentFolder.id;
          _self.$refs.ShowProperty.loadFormInfo();
        }
      } else {
        _self.$message(_self.$t("message.pleaseSelectFolder"));
      }
    },
    // 保存文档
    saveItem() {
      this.$refs.ShowProperty.saveItem();
    },
    // 保存结果事件
    onSaved(indata) {
      if (indata == "update") {
        this.$message(this.$t("message.saveSuccess"));
      } else {
        this.$message(this.$t("message.operationSuccess"));
      }
      this.propertyVisible = false;
      this.loadGridData(this.currentFolder);
    },
    handleExportItem(){
      let _self = this;
      var m = [];
      let tab = _self.selectedItems;
      var i;
      for (i in tab) {
        m.push(tab[i]["ID"]);
      }
      axios
        .post("/admin/getPackage", JSON.stringify(m))
        .then(function(response) {
          const content = JSON.stringify(response.data)
          //_self.exportRaw("package.txt", content)
        
          const blob = new Blob([content]) // 构造一个blob对象来处理数据
          const fileName = 'package.json' // 导出文件名
          // 对于<a>标签，只有 Firefox 和 Chrome（内核） 支持 download 属性
          // IE10以上支持blob但是依然不支持download
          if ('download' in document.createElement('a')) { // 支持a标签download的浏览器
            const link = document.createElement('a') // 创建a标签
            link.download = fileName // a标签添加属性
            link.style.display = 'none'
            link.href = URL.createObjectURL(blob)
            document.body.appendChild(link)
            link.click() // 执行下载
            URL.revokeObjectURL(link.href) // 释放url
            document.body.removeChild(link) // 释放标签
          } else { // 其他浏览器
            navigator.msSaveBlob(blob, fileName)
          }
        })
        .catch(function(error) {
          _self.$message("打包失败");
          console.log(error);
        });
    },
    exportRaw(name, data) {
      var urlObject = window.URL || window.webkitURL || window;
      var export_blob = new Blob([data]);
      var save_link = document.createElementNS(
        "http://www.w3.org/1999/xhtml",
        "a"
      );
      save_link.href = urlObject.createObjectURL(export_blob);
      save_link.download = name;
      this.fakeClick(save_link);
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
      axios
        .post("/dc/delDocument", JSON.stringify(m))
        .then(function(response) {
          _self.loadGridData(_self.currentFolder);
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
      _self.currentDocument = indata;
      _self.selectedItemId = indata.ID;
      _self.propertyVisible = true;
      if (_self.$refs.ShowProperty) {
        _self.$refs.ShowProperty.myItemId = indata.ID;
        _self.$refs.ShowProperty.loadFormInfo();
      }
      if (_self.$refs.ObjectAcl) {
        _self.$refs.ObjectAcl.name = indata.ACL_NAME;
        _self.$refs.ObjectAcl.docId = indata.ID;
        _self.$refs.ObjectAcl.loadAcl();
      }
      if (_self.$refs.SystemInfo) {
        _self.$refs.SystemInfo.itemData = indata;
        _self.$refs.SystemInfo.refreshData();
      }
    },
    // 查看属性
    showsStartBJSPWorkflow(indata) {
      let _self = this;
      _self.currentDocument = indata;
      _self.selectedItemId = indata.ID;
      _self.propertyVisible = true;
      _self.activeTab="启动流程";
      if (_self.$refs.StartWorkflow) {
        _self.$refs.StartWorkflow.docId = indata.ID;
      }
    },
    // 查看内容
    showItemContent(indata) {
      let _self = this;
      _self.imageArray = [];
      _self.currentType = indata.FORMAT_NAME;
      // 拦截器会自动替换成目标url
      _self.imageArray[0] =
        _self.axios.defaults.baseURL +
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
    // 保存文件夹
    saveFolder(indata) {
      let _self = this;
      if (_self.folderAction == _self.$t("application.newFolder")) {
        _self.newFolder(indata);
      } else {
        axios
          .post("/admin/updateFolder", JSON.stringify(indata))
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
      axios
        .post("/admin/deleteFolder", JSON.stringify(_self.currentFolder))
        .then(function(response) {
          if (response.data.code == 1) {
            _self.$message(_self.$t("message.deleteSuccess"));
            _self.refreshFolderData();
          } else {
            _self.$message(response.data.msg);
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    // 新建文件夹事件
    onNewFolder() {
      if (!this.currentFolder || !this.currentFolder.id) {
        this.$message(this.$t("message.cannotCreateRoot"));
        return;
      }
      this.folderAction = this.$t("application.newFolder");
      this.folderForm = {
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
    onEditFolder() {
      if (!this.currentFolder || !this.currentFolder.id) {
        this.$message(this.$t("message.pleaseSelectFolder"));
        return;
      }
      this.folderAction =
        this.$t("application.edit") + this.$t("application.folder");
      this.folderForm = this.currentFolder;
      this.folderDialogVisible = true;
      if (this.$refs.FolderAcl) {
        this.$refs.FolderAcl.name = currentFolder.name;
        this.$refs.FolderAcl.folderId = currentFolder.id;
        this.$refs.FolderAcl.loadAcl();
      }
    },
    onDeleleFolder() {
      let _self = this;
      if (!_self.currentFolder || !_self.currentFolder.id) {
        _self.$message(_self.$t("message.pleaseSelectFolder"));
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
    // 新建文件夹
    newFolder(indata) {
      let _self = this;
      axios
        .post("/admin/newFolder", JSON.stringify(indata))
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
    copyItem() {
      let _self = this;
      if (_self.selectedItems && _self.selectedItems.length > 0) {
        var m = new Map();
        m.set("ids", _self.getSelectedIds());
        m.set("folderId", "");
        axios
          .post("/dc/copyDocument", JSON.stringify(m))
          .then(function(response) {
            _self.$message(_self.$t("message.copySuccess"));
            _self.dialogVisible = false;
            _self.loadGridData(_self.currentFolder);
          })
          .catch(function(error) {
            console.log(error);
          });
      }
    },
    //查询文档
    searchItem() {
      this.loadGridData(this.currentFolder);
    },
    //展示勾选弹框
    dialogFormShow() {
      this.columnsInfo.dialogFormVisible = true;
    },
    //全选按钮
    handleCheckAllChange(val) {
      this.showFields = [];
      if (val) {
        this.gridList.forEach(element => {
          this.showFields.push(element.attrName);
        });
      }
      this.columnsInfo.isIndeterminate = false;
    },
    //单个选中
    handleCheckedColsChange(value) {
      let checkedCount = value.length;
      this.columnsInfo.checkAll = checkedCount === this.gridList.length;
      this.columnsInfo.isIndeterminate =
        checkedCount > 0 && checkedCount < this.gridList.length;
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
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
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
.el-aside {
  height: 600px;
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
.el-header {
    background-color: #e8eaeb;
    color: #333;
}
</style>
