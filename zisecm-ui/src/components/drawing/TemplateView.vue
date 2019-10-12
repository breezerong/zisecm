<template>
  <div>
    <el-dialog :title="$t('application.property')" :visible.sync="propertyVisible" @close="propertyVisible = false">
      <ShowProperty ref="ShowProperty"  @onSaved="onSaved" width="560" v-bind:itemId="selectedItemId" v-bind:folderId="currentFolder.id" v-bind:typeName="currentFolder.typeName"></ShowProperty>
      <div slot="footer" class="dialog-footer">
        <el-button @click="saveItem">{{$t('application.save')}}</el-button> <el-button @click="propertyVisible = false">{{$t('application.cancel')}}</el-button>
      </div>
    </el-dialog>
    <iframe frameborder="0" name="PDFViewer" id="PDFViewer" style="width:100%;height:760px;display:none;" ref="PDFViewer"></iframe>
    <el-dialog :visible.syn="imageViewVisible" @close="imageViewVisible = false">
      <div v-if="currentType=='pdf'">
        
        <!--
        <PDFViewer :pdfurl="imageArray[0]" @closepdf="closepdf" style="width:100%;height:760px;" ref="PDFViewer">
        </PDFViewer>
        -->
      </div>
      <div v-else-if=" currentType!='' && imageFormat.indexOf(currentType)>-1">
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
      <table border="0" width="100%" >
          <tr>
            <td class="navbar">
              <el-breadcrumb>
                <el-breadcrumb-item>{{$t('menu.dataCenter')}}</el-breadcrumb-item>
                <el-breadcrumb-item>{{subTitle}}</el-breadcrumb-item>
              </el-breadcrumb>
            </td>
          </tr>
          <tr>
            <td>
              <table border="0" width="100%" class="topbar">
                <tr>
                  <td align="left" width="240px">
                    <el-input  v-model="inputkey" :placeholder="$t('message.pleaseInput')+$t('application.keyword')" @change="searchItem" prefix-icon="el-icon-search"></el-input>
                  </td>
                  <td>
                    <el-button type="primary" icon="el-icon-edit"  @click="newItem()">{{$t('application.new')}}</el-button>
                    <el-button type="primary" icon="el-icon-delete"  @click="onDeleleItem()">{{$t('application.delete')}}</el-button>
                  </td>
                  <td>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
          <td valign="top">
                    <el-table
                      :height="tableHeight"
                      :data="itemDataList"
                      border
                      v-loading="loading"
                      @selection-change="selectChange"
                      @sort-change="sortchange"
                      style="width: 100%">
                      <el-table-column type="selection" width="40" @selection-change="selectChange">
                      </el-table-column>
                      <el-table-column :label="$t('field.indexNumber')" width="60">
                        <template slot-scope="scope">
                          <span>{{(currentPage-1) * pageSize + scope.$index+1}}</span>
                        </template>
                      </el-table-column>
                      <el-table-column width="40">
                        <template slot-scope="scope">
                          <img :src="'./static/img/format/f_'+scope.row.FORMAT_NAME+'_16.gif'" border="0">
                        </template>
                      </el-table-column>
                       <div v-for="(citem,idx) in gridList">
                        <div v-if="citem.visibleType==1">
                          <div v-if="(citem.width+'').indexOf('%')>0">
                              <el-table-column :label="citem.label" :prop="citem.attrName" :min-width="citem.width" :sortable="citem.allowOrderby">
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
                            <el-table-column :label="citem.label" :width="citem.width" :prop="citem.attrName" :sortable="citem.allowOrderby">
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
                      <el-table-column :label="$t('application.operation')" width="152">
                        <template slot-scope="scope">
                          <el-button type="primary" plain size="small" :title="$t('application.viewProperty')" icon="el-icon-info" @click="showItemProperty(scope.row)"></el-button>
                          <el-button type="primary" plain size="small" :title="$t('application.viewContent')" icon="el-icon-picture-outline" @click="showItemContent(scope.row)"></el-button>
                        </template>
                      </el-table-column>
                    </el-table>
                    <el-pagination
                      background
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
  </div>
</template>

<script type="text/javascript">
import ShowProperty from '@/components/dc/ShowProperty'
import PDFViewer from '@/components/controls/PDFViewer'
import 'url-search-params-polyfill'

export default {
  name: "TemplateView",
  components: {
    ShowProperty: ShowProperty,
    PDFViewer: PDFViewer
  },
  data() {
    return {
      imageFormat: 'jpg,jpeg,bmp,gif,png',
      baseServerUrl: this.baseURL,
      currentLanguage: "zh-cn",
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
      imageArray:[""],
      imageViewVisible:false,
      imageViewer: Object,
      subTitle:"",
      currentType:"",
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
      },
      paraName:""
    };
  },
  created() {
    let _self = this;
    var psize = localStorage.getItem("docPageSize");
    if(psize)
    {
      _self.pageSize = parseInt(psize);
    }
    _self.currentLanguage = localStorage.getItem("localeLanguage") || "zh-cn";
    _self.paraName = _self.$route.query.paraName;
    console.log(_self.paraName);
    _self.loadFolder();
  },
  methods: {
    loadFolder(){
      let _self = this;
      _self.loading = true;
      _self.axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        method: 'post',
        data: _self.paraName,
        url: '/zisecm/folder/getFolderByConfige'
      })
        .then(function(response) {
          _self.currentFolder = response.data.data;
          _self.subTitle = _self.currentFolder.name;
          _self.loading = false;
          _self.loadGridInfo(_self.currentFolder);
          _self.loadGridData(_self.currentFolder);
          _self.loadPageInfo(_self.currentFolder);
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
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
     sortchange(column){
      //console.log(JSON.stringify(column));
      //console.log(column.column.property);
      //console.log(column.column.order);//ascending, descending
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
      var key =_self.inputkey;
      if(key!=""){
        key = "coding like '%"+key+"%' or title like '%"+key+"%'"; 
      }
      var m = new Map();
      m.set('gridName',indata.gridView);
      m.set('folderId',indata.id);
      m.set('condition',key);
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
        _self.itemDataList = response.data.data;
        _self.itemDataListFull = response.data.data;
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
      this. loadPageInfo(this.currentFolder);
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
      var key =_self.inputkey;
      if(key!=""){
        key = "coding like '%"+key+"%' or title like '%"+key+"%'"; 
      }
      var m = new Map();
      m.set('gridName',indata.gridView);
      m.set('folderId',indata.id);
      m.set('condition',key);
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
        _self.$message(_self.$t("message.pleaseSelectFolder"));
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
        this.$message(this.$t("message.saveSuccess"));
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
      _self.imageArray = [];
      _self.currentType = indata.FORMAT_NAME;
      // 拦截器会自动替换成目标url
      _self.imageArray[0] =  "/zisecm/dc/getContent?id="+indata.ID+"&token="+sessionStorage.getItem('access-token');
      if(_self.currentType == "pdf"){
         window.open("./static/pdfviewer/web/viewer.html?file="+encodeURIComponent(_self.imageArray[0])+"&.pdf");
      }else{
         _self.imageViewVisible =true;
      }
    },
    closepdf(){
      this.imageViewVisible=false
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
     this.loadGridData(this.currentFolder);
     this. loadPageInfo(this.currentFolder);
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
