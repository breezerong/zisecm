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
        width="100%"
        v-bind:itemId="selectedItemId"
      ></ShowProperty>
      <div slot="footer" class="dialog-footer">
        <el-button @click="propertyVisible = false">{{$t('application.cancel')}}</el-button>
      </div>
    </el-dialog>
    <el-dialog title="文件列表" :visible.sync="itemDialogVisible" width="96%"  @close="itemDialogVisible = false">  
      <InnerItemViewer ref='innerItemViewer' v-bind:id = "currentId" v-bind:tableHeight="innerTableHeight"></InnerItemViewer>
     </el-dialog>
    <div class="navbar">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item >{{$t('menu.searchCenter')}}</el-breadcrumb-item>
        <el-breadcrumb-item>{{$t('menu.advSearch')}}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-container>
      <el-header style="height:50px">
        <el-row>
          <el-col :span="12">
            <AddCondition v-model="inputkey" v-bind:inputValue="inputkey"></AddCondition>
          </el-col>
          <el-col :span="1">
            <el-button type="primary" @click="search">查询</el-button>
          </el-col>
          <el-col :span="11">
          </el-col>
        </el-row>
      </el-header>
      <el-main>
        <el-table
        :height="tableHeight"
        :data="itemDataList"
        border
        style="width: 100%"
        v-loading="loading"
      >
      <el-table-column :label="$t('field.indexNumber')" width="60">
        <template slot-scope="scope">
            <span>{{(currentPage-1) * pageSize + scope.$index+1}}</span>
          </template>
      </el-table-column>
      <el-table-column width="40">
        <template slot-scope="scope">
            <img
              v-if="scope.row.TYPE_NAME=='图册'"
              :src="'./static/img/drawing.gif'"
              :title="scope.row.TYPE_NAME"
              border="0"
            />
            <img
              v-else-if="scope.row.TYPE_NAME=='卷盒'"
              :src="'./static/img/box.gif'"
              :title="scope.row.TYPE_NAME"
              border="0"
            />
            <img
              v-else
              :title="scope.row.FORMAT_NAME"
              :src="'./static/img/format/f_'+scope.row.FORMAT_NAME+'_16.gif'"
              border="0"
            />
          </template>
      </el-table-column>
        <div v-for="(citem,idx) in gridList" :key="idx+'_C'">
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
        <el-table-column :label="$t('application.operation')" width="160">
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
              :title="$t('application.viewProperty')"
              icon="el-icon-info"
              @click="showItemProperty(scope.row)"
            ></el-button>
          </template>
        </el-table-column>
      </el-table>
      </el-main>
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
    </el-container>
    
  </div>
</template>

<script type="text/javascript">
import AddCondition from '@/views/record/AddCondition'
import ShowProperty from "@/components/ShowProperty";
import InnerItemViewer from "@/views/dc/InnerItemViewer";
export default {
  components:{
    AddCondition:AddCondition,
    ShowProperty: ShowProperty,
    InnerItemViewer: InnerItemViewer
  },
  name: "AdvSearch",
  permit: 1,
  data() {
    return {
      dataList: [],
      propertyVisible:false,
      itemDialogVisible:false,
      currentId:"",
      innerTableHeight: window.innerHeight - 360,
      dataListFull: [],
      itemDataList:[],
      gridList:[],
      inputkey: "",
      selectedItemId:"",
      pageSize: 20,
      itemCount: 0,
      selectedItems:[],
      currentPage: 1,
      loading: false,
      dialogVisible: false,
      tableHeight: window.innerHeight - 170,
    };
  },
  created(){
    let _self = this;
    var psize = localStorage.getItem("taskPageSize");
    if (psize) {
      _self.pageSize = parseInt(psize);
    }
    _self.loadGridInfo()
  },
  methods: {
    loadGridInfo() {
      let _self = this;
      //console.log(val);
      var m = new Map();
      m.set("gridName", 'GeneralGrid');
      m.set("lang", _self.getLang());
      axios
        .post("/dc/getGridViewInfo", JSON.stringify(m))
        .then(function(response) {
          _self.gridList = response.data.data;
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    //查看属性
    showItemProperty(indata) {
      let _self = this;
      _self.selectedItemId = indata.ID;
      _self.propertyVisible = true;
      _self.itemDialogVisible = false;
      if (_self.$refs.ShowProperty) {
        _self.$refs.ShowProperty.myItemId = indata.ID;
        _self.$refs.ShowProperty.loadFormInfo();
      }
    },
    //查看内容
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
    },
    //卷盒或图册点击查看子文件列表
    rowClick(row) {
      this.currentId = row.ID;
      if (row.TYPE_NAME == "卷盒" || row.TYPE_NAME == "图册") {
        this.itemDialogVisible = true;
        let _self=this;
        setTimeout(()=>{
          _self.$refs.innerItemViewer.loadGridInfo();
        },10);
      }
    },
    search(){
      let _self = this;
      _self.loading = true
      var m = new Map();
      m.set("gridName",'GeneralGrid')
      m.set("folderId", '');
      m.set("condition", _self.inputkey+" and STATUS = '利用'");
      m.set("pageSize", _self.pageSize);
      m.set("pageIndex", (_self.currentPage - 1) * _self.pageSize);
      m.set("orderBy", "MODIFIED_DATE desc");
      axios.post("/dc/getDocumentsByConditon",JSON.stringify(m))
      .then(function(response){
        // console.log(response.data.data.data)
         _self.itemDataList = response.data.data;
          _self.itemCount = response.data.pager.totalRecord;
          _self.loading = false
          _self.tableHeight = window.innerHeight - 160
      })
      .catch(function(error) {
          console.log(error);
          _self.loading = false;
      });
    },
    // 表格行选择
    selectChange(val) 
    {
      this.selectedItems = val;
    },
    // 分页 页数改变
    handleSizeChange(val) {
      this.pageSize = val;
      localStorage.setItem("taskPageSize", val);
      this.search();
    },
    // 分页 当前页改变
    handleCurrentChange(val) {
      this.currentPage = val;
      this.search();
    },
    dateFormat(row, column) {
        let datetime = row
        if(datetime){
          datetime = new Date(datetime);
          let y = datetime.getFullYear() + '-';
          let mon = datetime.getMonth()+1 + '-';
          let d = datetime.getDate();
          return y + mon + d;
        }
        return ''
      },
      dateFormat2(row, column) {
        let datetime = row.completeDate;
        if(datetime){
          datetime = new Date(datetime);
          let y = datetime.getFullYear() + '-';
          let mon = datetime.getMonth()+1 + '-';
          let d = datetime.getDate();
          return y + mon + d + " "+datetime.getHours()+":"+datetime.getMinutes()+":"+datetime.getSeconds();
        }
        return ''
      }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.el-header {
  margin-top: 4px;
  background-color: #E9EEF3;
  text-align: center;
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
</style>
