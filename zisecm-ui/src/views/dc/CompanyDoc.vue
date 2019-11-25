<template>
  <div>
    <el-dialog
      title="选择需要展示的字段"
      :visible.sync="columnsInfo.dialogFormVisible"
      width="40%"
      center
      top="15vh"
      :close-on-click-modal="false"
    >
      <el-checkbox
        :indeterminate="columnsInfo.isIndeterminate"
        v-model="columnsInfo.checkAll"
        @change="handleCheckAllChange"
      >全选</el-checkbox>
      <el-checkbox-group v-model="showFields" @change="handleCheckedColsChange">
        <el-checkbox
          v-for="item in gridList"
          :label="item.attrName"
          :key="item.attrName"
        >{{item.label}}</el-checkbox>
      </el-checkbox-group>
      <div slot="footer" class="dialog-footer">
        <el-button @click="columnsInfo.dialogFormVisible=false" size="medium">取 消</el-button>
        <el-button type="primary" @click="confirmShow" size="medium">确定</el-button>
      </div>
    </el-dialog>
    <table border="0" width="100%" height="100%">
      <tr>
        <td class="navbar">
          <el-breadcrumb>
            <el-breadcrumb-item>{{$t('menu.companyDoc')}}</el-breadcrumb-item>
          </el-breadcrumb>
        </td>
      </tr>
      <el-container>
        <el-aside width="160px">
          <el-tree
            :props="defaultProps"
            :data="dataList"
            node-key="id"
            :render-content="renderContent"
            default-expand-all
            highlight-current
            @node-click="handleNodeClick"
          ></el-tree>
        </el-aside>
        <el-main>
          <el-row>
            <el-col :span="4">
              <el-input
                v-model="inputkey"
                :placeholder="$t('message.pleaseInput')+$t('application.keyword')"
                @change="searchItem"
                prefix-icon="el-icon-search"
              ></el-input>
            </el-col>
            <el-col :span="20" style="text-align: left">
              &nbsp;&nbsp;&nbsp;
              <el-button
                type="primary"
                icon="el-icon-document"
                @click="borrowItem()"
              >{{$t('application.borrow')}}</el-button>
              <el-button
                type="primary"
                icon="el-icon-upload2"
                @click="exportExcel()"
              >{{$t('application.export')+'Excel'}}</el-button>
              <el-button
                type="primary"
                icon="el-icon-bottom"
                @click="obtainItem()"
              >{{$t('application.obtained')}}</el-button>
              <el-button
                type="primary"
                icon="el-icon-document-delete"
                @click="destroyItem()"
              >{{$t('application.destroy')}}</el-button>
              &nbsp;&nbsp;&nbsp;
              <template v-if="isFileAdmin">
                <!-- `checked` 为 true显示卷宗 或 false不显示卷宗 -->
                <el-checkbox v-model="showBox" @change="showFileBox">{{$t('application.show')+$t('application.fileBox')}}</el-checkbox>
              </template>
            </el-col>
          </el-row>
          <el-row>
            <el-table
              :height="tableHeight"
              :data="itemDataList"
              border
              v-loading="loading"
              @selection-change="selectChange"
              @sort-change="sortchange"
              style="width: 100%"
            >
              <el-table-column type="selection" @selection-change="selectChange"></el-table-column>
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
                          <span>{{dateFormat(scope.row[citem.attrName])}}</span>
                        </div>
                        <div v-else>
                          <span>{{scope.row[citem.attrName]}}</span>
                        </div>
                      </template>
                    </el-table-column>
                  </div>
                </div>
              </div>
              <el-table-column align="left">
                <template slot="header" slot-scope="scope">
                  <el-button icon="el-icon-s-grid" @click="dialogFormShow"></el-button>
                </template>
                <template  slot-scope="scope">
                  <el-button type="primary" @click="showItemProperty(scope.row)">{{$t('application.view')}}</el-button>                  
                </template>
              </el-table-column>
            </el-table>
          </el-row>
        </el-main>
      </el-container>
    </table>
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
  </div>
</template>
<script>
export default {
  data() {
    return {
      currentuser:{
        user:{},
        roles:[]
      },
      columnsInfo: {
        checkAll: true,
        dialogFormVisible: false,
        isIndeterminate: false
      },
      tableHeight: window.innerHeight - 178,
      currentLanguage: "zh-cn",
      loading: false,
      currentFolder: [],
      isFileAdmin:false,
      showFields: [],
      pageSize: 20,
      dataList: [],
      gridList: [],
      itemDataList: [],
      itemDataListFull: [],
      showBox:false,
      orderBy: "",
      itemCount: 0,
      inputkey: "",
      currentPage: 1,
      defaultProps: {
        children: "children",
        label: "name"
      },
      selectedItemList: []
    };
  },
  created() {
    var username = sessionStorage.getItem("access-userName")
    let _self = this
     axios.post("/user/getGroupByUserName",username)
     .then(function(response){
        var groupList = response.data.data
        groupList.forEach(function(val,index,arr){
             if (val.name == '档案管理员') {
               _self.isFileAdmin = true
             }
        })
    })
  },
  mounted() {
    let _self = this;
    var psize = localStorage.getItem("docPageSize");
    if (psize) {
      _self.pageSize = parseInt(psize);
    }
    _self.currentLanguage = localStorage.getItem("localeLanguage") || "zh-cn";
    _self.loading = true;
    axios.post("/admin/getArchivesFolder",0)
      .then(function(response) {
        _self.dataList = response.data.data;
        // console.log(JSON.stringify(_self.dataList));
        _self.loading = false;
      })
      .catch(function(error) {
        console.log(error);
        _self.loading = false;
      });
  },
  methods: {
    renderContent: function(h, { node, data, store }) {
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
    handleNodeClick(indata) {
      let _self = this;
      _self.currentFolder = indata;
      if (indata.extended == false) {
        _self.loading = true;
        axios.post("/admin/getFolder",indata.id)
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
      if(_self.showBox){
        _self.loadAllGridData(indata)
      }else{
        _self.loadGridData(indata)
      }
    },
    sortchange(column) {
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
      axios.post("/dc/getGridViewInfo",JSON.stringify(m))
        .then(function(response) {
          _self.showFields = [];
          _self.gridList = response.data.data;
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
      var m = new Map();
      m.set("gridName", indata.gridView);
      m.set("folderId", indata.id);
      m.set("condition", key);
      m.set("pageSize", _self.pageSize);
      m.set("pageIndex", (_self.currentPage - 1) * _self.pageSize);
      m.set("orderBy", "MODIFIED_DATE desc");
      axios.post("/dc/getExceptBoxDocuments",JSON.stringify(m))
        .then(function(response) {
          _self.itemDataList = response.data.data;
          _self.itemDataListFull = response.data.data;
          _self.itemCount = response.data.pager.total;
          //console.log(JSON.stringify(response.data.datalist));
          _self.loading = false;
        });
    },
    //获取包含卷盒在内的所有信息
    loadAllGridData(indata){
      let _self = this;
      var key = _self.inputkey;
      var m = new Map();
      m.set("gridName", indata.gridView);
      m.set("folderId", indata.id);
      m.set("condition", key);
      m.set("pageSize", _self.pageSize);
      m.set("pageIndex", (_self.currentPage - 1) * _self.pageSize );
      m.set("orderBy", "MODIFIED_DATE desc");
      axios.post("/dc/getContainBoxDocuments",JSON.stringify(m))
        .then(function(response) {
          _self.itemDataList = response.data.data;
          _self.itemDataListFull = response.data.data;
          _self.itemCount = response.data.pager.total;
          //console.log(JSON.stringify(response.data.datalist));
          _self.loading = false;
        });
    },
    selectChange(selection) {
      this.selectedItemList = [];
      if (selection.length > 0) {
        for (var i = 0; i < selection.length; i++) {
          this.selectedItemList.push(selection[i]);
        }
      }
    },
    //展示勾选弹框
    dialogFormShow() {
      this.columnsInfo.dialogFormVisible = true;
    },
    //弹框中全选，展示所有属性
    handleCheckAllChange(val) {
      this.showFields = [];
      if (val) {
        this.gridList.forEach(element => {
          this.showFields.push(element.attrName);
        });
      }
      this.columnsInfo.isIndeterminate = false;
    },
    //单个勾选属性
    handleCheckedColsChange(value) {
      let checkedCount = value.length;
      this.columnsInfo.checkAll = checkedCount === this.gridList.length;
      this.columnsInfo.isIndeterminate =
        checkedCount > 0 && checkedCount < this.gridList.length;
    },
    //确认按钮显示哪些属性
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
    //搜索
    searchItem() {
      let _self = this
      _self.loadGridInfo(_self.currentFolder);
      if(_self.showBox){
        _self.loadAllGridData(_self.currentFolder)
      }else{
        _self.loadGridData(_self.currentFolder)
      }
    },
     //借阅
    borrowItem() {},
    //导出Excel
    exportExcel() {},
    //下架文档
    obtainItem() {
      let _self = this;
      var obtainItemId = [];
      if (this.selectedItemList.length > 0){
        for (var i = 0; i < this.selectedItemList.length; i++) {
          obtainItemId.push(this.selectedItemList[i].ID);
        }
        axios.post("/dc/obtainDocuments",JSON.stringify(obtainItemId)).then(function(response){
          if(response.data.code){
            if(_self.showBox){
              _self.loadAllGridData(_self.currentFolder)
            }else{
              _self.loadGridData(_self.currentFolder);
            }
            _self.$message({
            showClose: true,
            message: "删除成功",
            duration: 2000,
            type: 'success'
            });
          }else{
            _self.$message({
            showClose: true,
            message: "删除失败",
            duration: 2000,
            type: 'warning'
            });
          }
        })
      }
    },
    //销毁文档
    destroyItem() {
      let _self = this;
      var deletItemId = [];
      if (this.selectedItemList.length > 0){
        for (var i = 0; i < this.selectedItemList.length; i++) {
          deletItemId.push(this.selectedItemList[i].ID);
        }
        axios("/dc/destroyDocuments",JSON.stringify(deletItemId)).then(function(response){
          if(response.data.code){
            if(_self.showBox){
              _self.loadAllGridData(_self.currentFolder)
            }else{
              _self.loadGridData(_self.currentFolder);
            }
            _self.$message({
            showClose: true,
            message: "删除成功",
            duration: 2000,
            type: 'success'
            });
          }else{
            _self.$message({
            showClose: true,
            message: "删除失败",
            duration: 2000,
            type: 'warning'
            });
          }
        })
      }
    },
    //查看属性
    showItemProperty(row) {
      window.open("/#/ViewDoc?id="+row.ID,"_blank")
    },
    showFileBox(){
      if(this.showBox){
        this.loadAllGridData(this.currentFolder)
      }else{
        this.loadGridData(this.currentFolder)
      }
    },
    //分页 页数改变
    handleSizeChange(val){
      let _self = this
      this.pageSize = val;
      localStorage.setItem("docPageSize", val);
      _self.loadGridInfo(this.currentFolder);
      if(_self.showBox){
        _self.loadAllGridData(this.currentFolder)
      }else{
        _self.loadGridData(this.currentFolder)
      }
    },
    // 分页 当前页改变
    handleCurrentChange(val) {
      let _self = this
      this.currentPage = val;
      _self.loadGridInfo(this.currentFolder);
      if(_self.showBox){
        _self.loadAllGridData(this.currentFolder)
      }else{
        _self.loadGridData(this.currentFolder)
      }
    }
  }
};
</script>
<style scoped>
.el-container {
  height: 100%;
}
</style>