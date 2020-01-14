<template>
<div>
  <el-dialog
      :title="$t('application.property')"
      :visible.sync="propertyVisible"
      @close="propertyVisible = false"
      width="90%"
    >
      <ShowProperty
        ref="ShowProperty"
        @onSaved="onSaved"
        width="100%"
        v-bind:itemId="selectedItemId"
        v-bind:folderId="currentFolder.id"
        v-bind:typeName="currentFolder.TYPE_NAME"
      ></ShowProperty>
      <div slot="footer" class="dialog-footer">
        <el-button @click="propertyVisible = false">{{$t('application.cancel')}}</el-button>
      </div>
    </el-dialog>
  <el-container>
    <el-row style="height:30px">
      <el-breadcrumb style="padding-top:10px;padding-bottom:10px;padding-top:5px">
        <el-breadcrumb-item>&nbsp; {{folderName}}</el-breadcrumb-item>
      </el-breadcrumb>
    </el-row>
    <el-header style="height:48px;">
      <el-col :span="5" class="topbar-input">
        <el-input v-model="inputKey" :placeholder="$t('application.placeholderSearch')" @keyup.enter.native="search"></el-input>
      </el-col>
    </el-header>
    <el-main>
      <el-table v-loading="loadingNoticeData" border style="width:100%;" :height="tableHeight" :data="dataList.notiData">
        <el-table-column :label="$t('field.indexNumber')" width="70">
              <template slot-scope="scope">
                <span>{{(currentPage-1) * pageSize + scope.$index+1}}</span>
              </template>
         </el-table-column>
        <div v-for="(citem,idx) in gridList" :key="idx">
              <div v-if="citem.visibleType==1">
                <el-table-column
                  v-if="(citem.width+'').indexOf('%')>0"
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
                <el-table-column
                  v-else
                  :label="citem.label"
                  :prop="citem.attrName"
                  :width="citem.width"
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
            <el-table-column align="left" width="140" label="操作">
              <template slot-scope="scope">
                <el-button
                  type="primary"
                  plain
                  size="small"
                  :title="$t('application.viewContent')"
                  icon="el-icon-picture-outline"
                  @click="showItemContent(scope.row)"
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
    </el-main>
    <el-footer>
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
    </el-footer>
  </el-container>
</div>
</template>

<script type="text/javascript">
import ShowProperty from "@/components/ShowProperty";
export default {
  components: {
    ShowProperty: ShowProperty
  },
  data() {
    return {
        loadingNoticeData:false,
        dataList:{
          notiData:[]
        },
        currentFolder: [],
        inputKey:'',
        gridList: [],
        folderName:'',
        propertyVisible: false,
        selectedItemId: "",
        itemCount: 0,
        currentPage: 1,
        itemDialogVisible: false,
        pageSize:20,
        innerTableHeight: window.innerHeight - 360,
        tableHeight: window.innerHeight - 180,
        loadingNoticeData:false
    };
  },
  created() {
   let _self = this
   _self.folderName = _self.$route.query.folderName
   _self.refresh()
  },
  methods: {
    
    refresh() {
      let _self = this;
      _self.loadingNoticeData = true
      var m = new Map();
      // m.set("gridName", "NewsGrid");
      // m.set("folderId", "");
      // m.set("condition", " type_name='通知公告' and NAME like '%"+_self.inputKey+"%'");
      // m.set("pageSize", _self.pageSize);
      // m.set("pageIndex", (_self.currentPage - 1) * _self.pageSize);
      // m.set("orderBy", " CREATION_DATE DESC");
      m.set("folderPath",'/表单/'+_self.folderName)
      m.set("condition","NAME like '%"+_self.inputKey+"%' or coding like '%"+_self.inputKey+"%'")
      m.set("pageSize", _self.pageSize);
      m.set("pageIndex", (_self.currentPage - 1) * _self.pageSize);
      m.set("orderBy", " CREATION_DATE DESC");
      axios
        .post("/dc/getDocsViewDataByFolderPath", JSON.stringify(m))
        .then(function(response) {
          _self.dataList.notiData = response.data.data;
          _self.itemCount = response.data.pager.total;
          _self.gridList = response.data.view
          _self.loadingNoticeData = false
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    search(){
      this.refresh()
    },
    showFile(indata){
       let condition = indata.ID;
      let href = this.$router.resolve({
        path: '/viewdoc',
        query: {
          id: condition
        }
      });
      window.open(href.href, '_blank');
    },
    //分页 页数改变
    handleSizeChange(val) {
      let _self = this;
      this.pageSize = val;
      localStorage.setItem("docPageSize", val);
      _self.refresh()
    },
    // 分页 当前页改变
    handleCurrentChange(val) {
      let _self = this;
      this.currentPage = val;
      _self.refresh()
    },
    //查看属性
    showItemProperty(indata) {
      let _self = this;
      _self.selectedItemId = indata.ID;
      _self.currentFolder = indata
      _self.propertyVisible = true;
      _self.itemDialogVisible = false;
      if (_self.$refs.ShowProperty) {
        _self.$refs.ShowProperty.myItemId = indata.ID;
        _self.$refs.ShowProperty.loadFormInfo();
      }
    },
    // 查看内容
    showItemContent(indata) {
      let condition = indata.ID;
      let href = this.$router.resolve({
        path: "/viewdoc",
        query: {
          id: condition
          //token: sessionStorage.getItem('access-token')
        }
      });
      //console.log(href);
      window.open(href.href, "_blank");
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
