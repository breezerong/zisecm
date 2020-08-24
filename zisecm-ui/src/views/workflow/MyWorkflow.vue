<template>
  <div>
    <el-dialog title="查看" :visible.sync="dialogVisible" v-if="dialogVisible" width="95%">
      <el-steps :active="activeIndex" align-center finish-status="process" process-status="success">
        <el-step
          v-for="item in activityList"
          :title="item.name"
          :key="item.id"
          :description="item.description"
        ></el-step>
      </el-steps>
      <el-divider content-position="left">表单信息</el-divider>
      <!-- <router-view ref="formRouter"></router-view> -->
      <!-- <DocViewTask
        :formId="currentFormId"
        :docId="currentFormId"
        :istask="1"
        :processDefinitionId="currentData.processDefinitionId"
        :activityName="currentData.name"
        :formEnableType="this.$options.name"
      ></DocViewTask> -->
      <component
        :is="taskName"
        :typeName="taskName"
        :formId="currentFormId"
        :docId="currentFormId"
        :istask="1"
        :processDefinitionId="currentData.processDefinitionId"
        :activityName="currentData.name"
        :formEditPermision=0
        :formEnableType="this.$options.name"
      ></component>
      <el-divider content-position="left">流转意见</el-divider>
      <el-table :data="taskList" border v-loading="loading" style="width: 100%">
        <el-table-column label="序号" width="65">
          <template slot-scope="scope">
            <div v-if="scope.row.endTime==null||scope.row.endTime==''">
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
        <el-table-column prop="name" label="名称" min-width="12%" sortable></el-table-column>
        <el-table-column prop="assignee" label="用户" width="120"></el-table-column>
        <el-table-column
          prop="createTime"
          label="开始时间"
          :formatter="dateFormatter"
          width="160"
          sortable
        ></el-table-column>
        <el-table-column
          prop="endTime"
          label="完成时间"
          :formatter="dateFormatter"
          width="160"
          sortable
        ></el-table-column>
        <el-table-column prop="result" label="完成结果" width="100"></el-table-column>
        <el-table-column prop="message" label="审批意见" min-width="20%"></el-table-column>
      </el-table>
      <div v-if="workflowPicVisible=='显示流程图'">
        {{workflowPicVisible}}
        <img
          ref="processDiagram"
          style="width:100%;height:100%"
          :src="this.axios.defaults.baseURL+'/workflow/processDiagram?processId='+currentProcessId"
        />
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogVisible = false">{{$t('application.close')}}</el-button>
        <el-button @click="showprocessDiagram()">显示流程图</el-button>
      </div>
    </el-dialog>
    <el-row>
      <el-form ref="workflowForm" :model="workflowForm">
        <el-row class="topbar-button">
          <el-col>
            <el-form-item
              v-if="currentUserName=='all'"
              label="发起人"
              :label-width="formLabelWidth"
              style="float:left"
            >
              <UserSelectInput
                v-model="workflowForm.startUser"
                v-bind:inputValue="workflowForm.startUser"
              ></UserSelectInput>
            </el-form-item>
          </el-col>
          <el-col>
            <el-form-item label="状态" :label-width="formLabelWidth" style="float:left">
              <el-select style="width:12em" v-model="workflowForm.isFinished">
                <el-option label="全部" value="全部"></el-option>
                <el-option label="未完成" value="未完成"></el-option>
                <el-option label="已完成" value="已完成"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="开始时间" :label-width="formLabelWidth" style="float:left">
              <el-date-picker
                v-model="workflowForm.startTimeAfter"
                auto-complete="off"
                value-format="yyyy-MM-dd HH:mm:ss"
              ></el-date-picker>
              <el-date-picker
                v-model="workflowForm.startTimeBefore"
                auto-complete="off"
                value-format="yyyy-MM-dd HH:mm:ss"
              ></el-date-picker>
            </el-form-item>
            <el-form-item label="完成时间" :label-width="formLabelWidth" style="float:left">
              <el-date-picker
                v-model="workflowForm.endTimeAfter"
                auto-complete="off"
                value-format="yyyy-MM-dd HH:mm:ss"
              ></el-date-picker>
              <el-date-picker
                v-model="workflowForm.endTimeBefore"
                auto-complete="off"
                value-format="yyyy-MM-dd HH:mm:ss"
              ></el-date-picker>
            </el-form-item>
            <el-form-item style="float:left;padding-left:3px">
              <el-button type="primary" plain="true" size="small" @click="search()">查询</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-row>
    <el-table
      :data="dataList"
      border
      :height="tableHeight"
      v-loading="loading"
      style="width: 99.8%"
    >
      <el-table-column label="序号" width="80px">
        <template slot-scope="scope">
          <div v-if="scope.row.endTime==''">
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
      <el-table-column prop="name" label="名称" min-width="10%"></el-table-column>
      <el-table-column v-if="showAllWorkflow=='1'" prop="startUser" label="发起者" min-width="20%"></el-table-column>
      <el-table-column
        prop="startTime"
        label="开始时间"
        sortable
        :formatter="dateFormatter"
        min-width="20%"
      ></el-table-column>
      <el-table-column
        prop="endTime"
        label="完成时间"
        sortable
        :formatter="dateFormatter"
        min-width="20%"
      ></el-table-column>
      <!-- <el-table-column prop="currentAssignee" label="当前执行人"  min-width="20%">
      </el-table-column>-->
      <el-table-column label="操作" width="210px" v-if="currentUserName=='all'">
        <template slot-scope="scope">
          <el-button
            :plain="true"
            type="success"
            size="small"
            icon="save"
            @click="showitem(scope.row)"
          >查看</el-button>
          <el-button
            v-if="scope.row.endTime=='' && currentUserName=='all'"
            type="danger"
            size="small"
            @click="terminateWorkflow(scope.row)"
          >结束流程</el-button>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="80px" v-else>
        <template slot-scope="scope">
          <el-button
            :plain="true"
            type="success"
            size="small"
            icon="save"
            @click="showitem(scope.row)"
          >查看</el-button>
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
      :total="itemCount"
    ></el-pagination>
  </div>
</template>

<script type="text/javascript">
// $.ajaxSetup({
//   contentType: "application/json"
// });

import UserSelectInput from "@/components/controls/UserSelectInput";
import EditTask from "@/views/workflow/task/EditTask.vue";
import DocViewTask from "@/views/workflow/task/DocViewTask.vue";
import borrow1 from "@/components/form/Borrow1.vue";
export default {
  name: "MyWorkflow",
  permit: 1,
  components: {
    UserSelectInput: UserSelectInput,
    EditTask: EditTask,
    DocViewTask: DocViewTask,
    borrow1: borrow1
  },
  data() {
    return {
      currentData: [],
      taskName:'',
      currentFormId:'',
      dataList: [],
      dataListFull: [],
      taskList: [],
      inputkey: "",
      activeIndex: 0,
      activityList: [],
      pageSize: 20,
      itemCount: 0,
      selectedItems: [],
      currentPage: 1,
      loading: false,
      dialogVisible: false,
      tableHeight: window.innerHeight - 175,
      formLabelWidth: "80px",
      currentProcessId: "",
      workflowPicVisible: "",
      isPocessFinished: "0",
      currentUserName: "",
      showAllWorkflow: "0",
      workflowForm: {
        startUser: "",
        startTimeAfter: "",
        endTimeAfter: "",
        startTimeBefore: "",
        endTimeBefore: "",
        isFinished: "全部",
      },
    };
  },
  created() {
    let _self = this;
    var psize = localStorage.getItem("taskPageSize");
    if (psize) {
      _self.pageSize = parseInt(psize);
    }
    _self.currentUserName = sessionStorage.getItem("access-userName");
    _self.showAllWorkflow = _self.$route.query.showAllWorkflow;
    if (_self.showAllWorkflow == "1") {
      _self.currentUserName = "all";
      _self.tableHeight = window.innerHeight - 205;
    }
    _self.refreshData();
  },
  methods: {
    search() {
      let _self = this;
      _self.loading = true;
      _self.refreshData("1");
    },
    dateFormatter(row, column) {
      let datetime = row[column.property];
      return this.datetimeFormat(datetime);
    },
    refreshProcess(wfId) {
      let _self = this;
      _self.loading = true;
      // axios.post('/workflow/getWfActivities',wfId)
      // .then(function(response) {
      //   _self.activityList = response.data.data;
      //   _self.loading = false;
      // })
      // .catch(function(error) {
      //   console.log(error);
      //   _self.loading = false;
      // });
    },
    //格式化时间
    dateFtt(fmt, date) {
      var o = {
        "M+": date.getMonth() + 1, //月份
        "d+": date.getDate(), //日
        "h+": date.getHours(), //小时
        "m+": date.getMinutes(), //分
        "s+": date.getSeconds(), //秒
        "q+": Math.floor((date.getMonth() + 3) / 3), //季度
        S: date.getMilliseconds(), //毫秒
      };
      if (/(y+)/.test(fmt))
        fmt = fmt.replace(
          RegExp.$1,
          (date.getFullYear() + "").substr(4 - RegExp.$1.length)
        );
      for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
          fmt = fmt.replace(
            RegExp.$1,
            RegExp.$1.length == 1
              ? o[k]
              : ("00" + o[k]).substr(("" + o[k]).length)
          );
      return fmt;
    },
    dateFormat(value) {
      var crtTime = new Date(value);
      return this.dateFtt("yyyy-MM-dd", crtTime);
    },

    refreshData(obj) {
      let _self = this;
      _self.loading = true;
      var m = new Map();
      m.set("condition", _self.inputkey);
      m.set("pageSize", _self.pageSize);
      m.set("pageIndex", (_self.currentPage - 1) * _self.pageSize);
      // if(obj=="1"){
      // _self.workflowForm.startTimeAfter=_self.workflowForm.startTimeAfter==""?"":_self.dateFormat(_self.workflowForm.startTimeAfter);
      // _self.workflowForm.startTimeBefore==_self.workflowForm.startTimeBefore==""?"":_self.dateFormat(_self.workflowForm.startTimeBefore);
      // _self.workflowForm.endTimeAfter==_self.workflowForm.endTimeAfter==""?"":_self.dateFormat(_self.workflowForm.endTimeAfter);
      // _self.workflowForm.endTimeBefore==_self.workflowForm.endTimeBefore==""?"":_self.dateFormat(_self.workflowForm.endTimeBefore);
      m.set("workflowForm", _self.workflowForm);
      // }
      m.set("userId", _self.currentUserName);
      axios
        .post("/workflow/myWorkflow", JSON.stringify(m))
        .then(function (response) {
          _self.dataList = response.data.data;
          _self.dataListFull = response.data.data;
          _self.loading = false;
          _self.loadPageInfo(response.data.totalCount);
        })
        .catch(function (error) {
          console.log(error);
          _self.loading = false;
        });
    },

    terminateWorkflow(indata) {
      let _self = this;
      _self
        .$confirm("流程结束后不能恢复，请确认是否需要结束流程？", "结束流程", {
          confirmButtonText: _self.$t("application.ok"),
          cancelButtonText: _self.$t("application.cancel"),
          type: "warning",
        })
        .then(() => {
          _self.handleTermiateWorkflow(indata);
        })
        .catch(() => {
          // this.$message({
          //   type: 'info',
          //   message: '已取消删除'
          // });
        });
    },
    handleTermiateWorkflow(indata) {
      let _self = this;
      _self.refreshProcess(indata.id);
      var m = new Map();
      m.set("processInstanceId", indata.id);
      _self.loading = true;
      axios
        .post("/workflow/stopProcessInstanceById", JSON.stringify(m))
        .then(function (response) {
          _self.refreshData();
          _self.$message({
            showClose: true,
            message: "结束流程成功!",
            duration: 3000,
            type: "success",
          });
          _self.loading = false;
        })
        .catch(function (error) {
          console.log(error);
          _self.loading = false;
        });
    },
    // 表格行选择
    selectChange(val) {
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
    loadPageInfo(val) {
      let _self = this;
      _self.itemCount = val;
      _self.loading = false;
    },
    showitem(indata) {
      let _self = this;
      _self.currentData = indata;
      _self.refreshProcess(indata.id);
      _self.currentFormId = indata.formId;
      var m = new Map();
      switch (_self.currentData.processDefinitionId.split(":")[0]) {
        case "BianJiaoShenPi":
            _self.taskName = 'DocViewTask';
          break;
        case "process_borrow":
            _self.taskName = 'borrow1';
          break;
      }
      // var n = new Map();
      // n.set("processDefinitionId", indata.processDefinitionId);
      // n.set("activityName", indata.name);
      // axios
      //   .post("/workflow/getEcmCfgActivity", JSON.stringify(n))
      //   .then(function(response){
      //     _self.taskName = response.data.data.componentName;
      //   }).catch(function(error) {
      //     console.log(error);
      //     _self.loading = false;
      //   });
      m.set("processInstanceId", indata.id);
      _self.currentProcessId = indata.id;
      axios
        .post("/workflow/getWorkflowTask", JSON.stringify(m))
        .then(function (response) {
          _self.taskList = response.data.data;
          _self.isPocessFinished = response.data.isPocessFinished;
          if (_self.isPocessFinished == "1") {
            _self.workflowPicVisible = "";
          }
          var k = -1;
          for (var i in _self.taskList) {
            if (_self.taskList[i].completeDate == null) {
              //console.log(JSON.stringify(_self.taskList[i].taskName));
              for (var j in _self.activityList) {
                k++;
                if (_self.taskList[i].taskName === _self.activityList[j].name) {
                  //console.log(JSON.stringify(_self.activityList[j].name));
                  break;
                }
              }
              break;
            }
          }
          //console.log(k);
          if (k == -1) {
            k = _self.activityList.length;
          }
          _self.activeIndex = k;
          //console.log(JSON.stringify(_self.taskList));
          _self.dialogVisible = true;
          _self.loading = false;
        })
        .catch(function (error) {
          console.log(error);
          _self.loading = false;
        });

      // let routeBorrowPathName = "/borrow3";
      // if (_self.showAllWorkflow == "1") {
      //   routeBorrowPathName = "/admin_borrow3";
      // }
      // _self.$router.replace({
      //   path: routeBorrowPathName,
      //   query: {
      //     tabledata: [],
      //     borrowFormId: indata.formId,
      //     istask: 1,
      //     formEditPermision: 0
      //   }
      // });
    },
    showprocessDiagram() {
      let _self = this;
      if (_self.isPocessFinished == "1") {
        _self.workflowPicVisible = "";
        _self.$message({
          showClose: true,
          message: "流程已结束，过程流程图不可查看!",
          duration: 2000,
          type: "success",
        });
      } else {
        if (_self.workflowPicVisible == "显示流程图") {
          _self.workflowPicVisible = "";
        } else {
          _self.workflowPicVisible = "显示流程图";
        }
      }
    },
  },
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
.active1 {
  background: rgb(2, 136, 31);
}

.active2 {
  background: rgb(145, 146, 145);
}
header.el-header {
  background-color: #e8eaeb;
  height: 100% !important;
}
.el-form-item {
  margin-bottom: 3px;
}
.el-date-editor.el-input,
.el-date-editor.el-input__inner {
  width: 140px;
}
</style>
