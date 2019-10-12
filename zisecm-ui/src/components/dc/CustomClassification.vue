<template>
  <div>
    <el-dialog :title="$t('application.property')" :visible.sync="propertyVisible" >
      <ShowProperty ref="ShowProperty"  @onSaved="onSaved" width="560" v-bind:itemId="selectedItemId" v-bind:folderId="currentFolder.id" v-bind:typeName="currentFolder.typeName"></ShowProperty>
      <div slot="footer" class="dialog-footer">
        <el-button @click="saveItem">{{$t('application.save')}}</el-button> <el-button @click="propertyVisible = false">{{$t('application.cancel')}}</el-button>
      </div>
    </el-dialog>
    <el-dialog :title="folderAction" :visible.sync="folderDialogVisible">
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
            <td class="navbar">
              <el-breadcrumb>
                <el-breadcrumb-item>{{$t('menu.dataCenter')}}</el-breadcrumb-item>
                <el-breadcrumb-item>{{$t('menu.customClassification')}}</el-breadcrumb-item>
              </el-breadcrumb>
            </td>
          </tr>
          <tr>
            <td>
              <table border="0" width="100%" class="topbar">
                <tr>
                  <td align="left" width="160px">
                    <el-tooltip  class="item" effect="dark" :content="$t('application.newFolder')" placement="top">
                      <el-button type="primary" icon="el-icon-circle-plus" circle @click="onNewFolder()"></el-button>
                    </el-tooltip>
                    <el-tooltip  class="item" effect="dark" :content="$t('application.edit')+$t('application.folder')" placement="top">
                      <el-button type="primary" icon="el-icon-info" circle @click="onEditFolder()"></el-button>
                    </el-tooltip>
                    <el-tooltip  class="item" effect="dark" :content="$t('application.delete')+$t('application.folder')" placement="top">
                      <el-button type="primary" icon="el-icon-delete" circle @click="onDeleleFolder()"></el-button>
                    </el-tooltip>
                  </td>
                  <td align="left" width="160px">
                    <el-input  v-model="inputkey" :placeholder="$t('message.pleaseInput')+$t('application.keyword')" @change="searchItem" prefix-icon="el-icon-search"></el-input>
                  </td>
                  <td>
                    <el-tooltip  class="item" effect="dark" :content="$t('application.newDocument')" placement="top">
                      <el-button type="primary" icon="el-icon-edit" circle @click="newItem()"></el-button>
                    </el-tooltip>
                    <el-tooltip  class="item" effect="dark" :content="$t('application.delete')+$t('application.document')" placement="top">
                      <el-button type="primary" icon="el-icon-delete" circle @click="onDeleleItem()"></el-button>
                    </el-tooltip>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        <tr>
          <td>
            <table border="0" width="100%">
                <tr valign="top">
                  <td align="left" width="160px" >
                    <el-tree
                      :props="defaultProps"
                      :data="dataList"
                      node-key="id"
                      default-expand-all
                      @node-click="handleNodeClick">
                    </el-tree>
                  </td>
                  <td>
                    <el-table
                      :height="tableHeight"
                      :data="itemDataList"
                      border
                      v-loading="loading"
                      @selection-change="selectChange"
                      style="width: 100%">
                      <el-table-column type="selection" width="40" @selection-change="selectChange">
                      </el-table-column>
                      <el-table-column :label="$t('field.indexNumber')" width="60">
                        <template slot-scope="scope">
                          <span>{{(currentPage-1) * pageSize + scope.$index+1}}</span>
                        </template>
                      </el-table-column>
                      <div v-for="(citem,idx) in gridList">
                        <div v-if="citem.visibleType==1">
                          <el-table-column :label="citem.label" :width="citem.width">
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
                      </div>
                      <el-table-column :label="$t('application.operation')" width="152">
                        <template slot-scope="scope">
                          <el-button type="primary" size="small" :title="$t('application.viewProperty')" icon="el-icon-info" @click="showItemProperty(scope.row)"></el-button>
                          <el-button type="primary" size="small" :title="$t('application.viewContent')" icon="el-icon-picture-outline" @click="showItemContent(scope.row)"></el-button>
                        </template>
                      </el-table-column>
                    </el-table>
                    <el-pagination
                      @size-change="handleSizeChange"
                      @current-change="handleCurrentChange"
                      :current-page="currentPage"
                      :page-sizes="[10, 20, 50, 100, 200]"
                      :page-size="pageSize"
                      layout="total, sizes, prev, pager, next, jumper"
                      :total="itemCount">
                    </el-pagination>
                  </td>
                </tr>
              </table>
          </td>
        </tr>
      </table>
  </div>
</template>

<script type="text/javascript">
import ShowProperty from '@/components/dc/ShowProperty'
import 'url-search-params-polyfill'

export default {
  name: "CustomClassification",
  components: {
    ShowProperty: ShowProperty
  },
  data() {
    return {
      dataList: [],
      itemDataList: [],
      itemDataListFull: [],
      gridList: [],
      currentFolder: [],
      dataListFull: "",
      inputkey: "",
      loading: false,
      pageSize: 20,
      itemCount: 0,
      selectedItemId: 0,
      currentPage:1,
      dialogVisible: false,
      propertyVisible: false,
      selectedItems: [],
      tableHeight: window.innerHeight - 178,
      folderAction:"",
      folderDialogVisible: false,
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
  created() {
    let _self = this;
    var psize = localStorage.getItem("docPageSize");
    if(psize)
    {
      _self.pageSize = parseInt(psize);
    }
    _self.loading = true;
    _self.axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        method: "post",
        data: 0,
        url: "/zisecm/admin/getFolder"
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
  methods: {
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
    // 加载表格样式
    loadGridInfo(indata) 
    {
      let _self = this;
      _self.loading = true;
      _self.axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        method: 'post',
        data: indata.gridView,
        url: '/zisecm/dc/getGridViewInfo'
      })
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
    loadGridData(indata) 
    {
      let _self = this;
      var m = new Map();
      m.set('gridName',indata.gridView);
      m.set('folderId',indata.id);
      m.set('condition','');
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
        url: "/zisecm/dc/getDocuments"
      })
      .then(function(response) {
        _self.itemDataList = response.data.datalist;
        _self.itemDataListFull = response.data.datalist;
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
      console.log('handleSizeChange', val);
    },
    // 分页 当前页改变
    handleCurrentChange(val) 
    {
      this.currentPage = val;
      this.loadGridData(this.currentFolder);
      console.log('handleCurrentChange', val);
    },
    // 加载页数 暂时未处理查询条件
    loadPageInfo(indata)
    {
      let _self = this;
      var m = new Map();
      m.set('gridName',indata.gridView);
      m.set('folderId',indata.id);
      m.set('condition','');
      _self
      .axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        method: "post",
        data: JSON.stringify(m),
        url: "/zisecm/dc/getDocumentCount"
      })
      .then(function(response) {
        _self.itemCount = response.data.itemCount;
        _self.loading = false;
      })
      .catch(function(error) {
        console.log(error);
        _self.loading = false;
      });
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
        url: "/zisecm/admin/getFolder"
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
      _self.currentFolder = indata;
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
          url: "/zisecm/admin/getFolder"
        })
        .then(function(response) {
          indata.children = response.data.data;
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
      _self.loadPageInfo(indata);
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
          _self.$refs.ShowProperty.myTypeName = _self.currentFolder.typeName;
          _self.$refs.ShowProperty.myFolderId = _self.currentFolder.id;
          _self.$refs.ShowProperty.loadFormInfo();
        }
      }
      else{
        _self.$message(_self.$t("application.pleaseSelectFolder"));
      }
    },
    // 保存文档
    saveItem(){
      this.$refs.ShowProperty.saveItem();
    },
    // 保存结果事件
    onSaved(indata){
      if(indata=='update')
      {
        this.$message(this.$t("application.saveSuccess"));
      }
      else
      {
        this.$message("新建成功!");
      }
      this.propertyVisible = false;
      this.loadGridData(this.currentFolder);
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
    // 删除文档
    deleleItem() {
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
          url: "/zisecm/dc/delDocument"
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
    // 查看属性
    showItemProperty(indata){
      let _self = this;
      _self.selectedItemId = indata.ID ;
      _self.propertyVisible = true;
      if(_self.$refs.ShowProperty){
        _self.$refs.ShowProperty.myItemId = indata.ID ;
        _self.$refs.ShowProperty.loadFormInfo();
      }
    },
    // 查看内容
    showItemContent(indata){
      let _self = this;
     
      var params = new URLSearchParams();
      params.append('id', indata.ID);
       /*
      _self.axios.post("/zisecm/dc/getContent?id="+indata.id, params).then(function (response) {
      　　
      }).catch(function (error) {
      　　alert(error);
      });
           */
       _self.axios({
          headers: {
            "Content-Type": "application/x-www-form-urlencoded"
          },
          method: "post",
          data: params,
          url: "/zisecm/dc/getContent"
        })
        .then(function(response) {
          console.log(response);
          let blob = new Blob([response.data],{type:"jpg"});
          let objectUrl = URL.createObjectURL(blob);
          let link = document.createElement('a')
          link.style.display = 'none'
          link.href = objectUrl
          link.setAttribute('download', 'temp.jpg')

          document.body.appendChild(link)
          link.click()
        })
        .catch(function(error) {
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
          url: "/zisecm/admin/updateFolder"
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
          url: "/zisecm/admin/deleteFolder"
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
          url: "/zisecm/admin/newFolder"
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
          url: "/zisecm/admin/copyFolder"
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
      let _self = this;
      _self.itemDataList = _self.itemDataListFull.filter(function(item){
        return item.coding.match(_self.inputkey) || item.title.match(_self.inputkey);
      });
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
</style>
