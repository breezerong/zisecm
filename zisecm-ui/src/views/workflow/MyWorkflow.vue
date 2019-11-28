<template>
  <div>
        <el-dialog title="查看" :visible.sync="dialogVisible" width="70%">
          <el-steps :active="activeIndex" align-center finish-status="process" process-status="success">
            <el-step v-for="item in activityList" :title="item.name" :key="item.id" :description="item.description"></el-step>
          </el-steps>
          <el-table
              :data="taskList"
              border
              :height="tableHeight-200"
              v-loading="loading"
              style="width: 100%">
                <el-table-column label="序号" width="65">
                  <template slot-scope="scope">
                    <div v-if="scope.row.id==''">
                      <el-tooltip class="item" effect="light" content="未完成" placement="right">
                        <el-button type="success" round>{{scope.$index+1}}</el-button>
                      </el-tooltip>
                    </div>
                    <div v-else>
                      <el-tooltip class="item" effect="light" content="已完成" placement="right">
                        <el-button type="info" round>{{scope.$index+1}}</el-button>
                      </el-tooltip>
                    </div>
                  </template>
                 </el-table-column>
                </el-table-column>
                <el-table-column prop="taskName" label="名称" min-width="15%" sortable>
                </el-table-column>
                <el-table-column prop="performer" label="用户" width="120" >
                </el-table-column>
                <el-table-column prop="startDate" label="开始时间" :formatter="dateFormatter"  width="160" sortable>
                </el-table-column>
                <el-table-column prop="completeDate" label="完成时间" :formatter="dateFormatter"  width="160" sortable>
                </el-table-column>
                <el-table-column prop="result" label="完成结果" width="100">
                </el-table-column>
                <el-table-column prop="message" label="审批意见" min-width="20%">
                </el-table-column>
            </el-table>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">关闭</el-button>
          </div>
        </el-dialog>
      <table border="0" width="100%">
          <tr>
            <td class="navbar">
              /工作流/我的流程
            </td>
          </tr>
          <tr>
            <td>
              <table border="0" width="100%" class="topbar">
                <tr>
                  <td align="left" width="200px">
                    <el-input v-model="inputkey" placeholder="请输入关键字" @change="search" prefix-icon="el-icon-search"></el-input>
                  </td>
                  <td>
                   
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        <tr>
          <td>
            <el-table
              :data="dataList"
              border
              :height="tableHeight"
              v-loading="loading"
              style="width: 100%">
                <el-table-column label="序号" width="80">
                  <template slot-scope="scope">
                    <div v-if="scope.row.completeDate">
                      <el-tooltip class="item" effect="light" content="已完成" placement="right">
                        <el-button type="info" round>{{scope.$index+1}}</el-button>
                      </el-tooltip>
                    </div>
                    <div v-else>
                      <el-tooltip class="item" effect="light" content="未完成" placement="right">
                        <el-button type="success" round>{{scope.$index+1}}</el-button>
                      </el-tooltip>
                    </div>
                  </template>
                 </el-table-column>
              <el-table-column prop="workflowName" label="名称" min-width="20%" >
              </el-table-column>
              <el-table-column prop="startDate" label="开始时间" sortable :formatter="dateFormatter" min-width="12%">
              </el-table-column>
              <el-table-column prop="completeDate" label="完成时间" sortable :formatter="dateFormatter"  min-width="12%">
              </el-table-column>
              <el-table-column prop="currentUser" label="当前执行人"  min-width="20%">
              </el-table-column>
              <el-table-column prop="description" label="流程说明" min-width="15%">
              </el-table-column>
              <el-table-column label="操作"  width="80">
                <template slot-scope="scope">
                  <el-button :plain="true" type="success" size="small" icon="save" @click="showitem(scope.row)">查看</el-button>
                </template>
              </el-table-column>
            </el-table>
            <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="currentPage"
              :page-sizes="[20, 50, 100, 200]"
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
// $.ajaxSetup({
//   contentType: "application/json"
// });

export default {
  name: "MyWorkflow",
  permit: 1,
  data() {
    return {
      currentData: [],
      dataList: [],
      dataListFull: [],
      taskList:[],
      inputkey: "",
      activeIndex:0,
      activityList: [],
      pageSize: 20,
      itemCount: 0,
      selectedItems:[],
      currentPage: 1,
      loading: false,
      dialogVisible: false,
      tableHeight: window.innerHeight - 190,
      formLabelWidth: "120px"
    };
  },
  created(){ 
    let _self = this;
    var psize = localStorage.getItem("taskPageSize");
    if (psize) {
      _self.pageSize = parseInt(psize);
    }
    _self.refreshData();
  },
  methods: {
    dateFormatter(row, column) {
      let datetime = row[column.property];
      return this.datetimeFormat(datetime);
    },
    refreshProcess(wfId) {
      let _self = this;
      _self.loading = true;
      axios.post('/workflow/getWfActivities',wfId)
      .then(function(response) {
        _self.activityList = response.data.data;
        _self.loading = false;
      })
      .catch(function(error) {
        console.log(error);
        _self.loading = false;
      });
    },
    refreshData() {
      let _self = this;
      _self.loading = true;
      var m = new Map();
      m.set("condition", _self.inputkey);
      m.set("pageSize", _self.pageSize);
      m.set("pageIndex", (_self.currentPage - 1) * _self.pageSize);
      axios.post('/workflow/myWorkflow',JSON.stringify(m))
      .then(function(response) {
        _self.dataList = response.data.data;
        _self.dataListFull = response.data.data;
        _self.loading = false;
        _self.loadPageInfo();
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
      this.refreshData();
      //console.log('handleSizeChange', val);
    },
    // 分页 当前页改变
    handleCurrentChange(val) {
      this.currentPage = val;
      this.refreshData();
    },
    // 加载页数 暂时未处理查询条件
    loadPageInfo() {
      let _self = this;
      var m = new Map();
      m.set("condition", _self.inputkey);
      axios.post("/workflow/getMyWorkflowCount",JSON.stringify(m))
        .then(function(response) {
          _self.itemCount = response.data.data;
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    showitem(indata) {
      let _self = this;
      _self.refreshProcess(indata.workflowId);
      axios.post("/workflow/getWorkflowTask",JSON.stringify(indata.workflowId))
        .then(function(response) {
          _self.taskList = response.data.data;
          var k=-1;
          for(var i in _self.taskList){
            if(_self.taskList[i].completeDate==null){
              //console.log(JSON.stringify(_self.taskList[i].taskName));
              for(var j in _self.activityList){
                k++;
                if(_self.taskList[i].taskName === _self.activityList[j].name){
                  //console.log(JSON.stringify(_self.activityList[j].name));
                  break;
                }
              }
              break;
            }
          }
          //console.log(k);
          if(k==-1){
            k = _self.activityList.length;
          }
          _self.activeIndex = k;
          //console.log(JSON.stringify(_self.taskList));
          _self.dialogVisible = true;
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
      
    },
    search() {
      let _self = this;
      _self.dataList = _self.dataListFull.filter(function(item){
          return item.workflowName.match(_self.inputkey) || item.description.match(_self.inputkey);
        }
      );
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
.active1{
  background: rgb(2, 136, 31);
}

.active2{
  background: rgb(145, 146, 145);
}
</style>
