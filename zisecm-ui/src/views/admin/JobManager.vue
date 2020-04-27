<template>
  <div>
 
    <el-container>
      <el-header>
        <!-- <el-breadcrumb separator="/" class="navbar">
          <el-breadcrumb-item>系统管理</el-breadcrumb-item>
          <el-breadcrumb-item>Job管理</el-breadcrumb-item>
        </el-breadcrumb> -->
        <el-row class="topbar">
          <el-col :span="4">
            <el-input
              v-model="inputKey"
              placeholder="请输入关键字"
              @change="search"
              prefix-icon="el-icon-search"
            ></el-input>
          </el-col>
           <el-col :span="4">
                  <el-select style="width:12em" :span="4" v-model="jobType"   @change="getJobList">
                    <el-option label="当前Job" value="currentJob" ></el-option>
                    <el-option label="暂停Job" value="suspendJob"></el-option>
                    <el-option label="死掉Job" value="deadJob"></el-option>
                  </el-select>
         </el-col>
        </el-row>
      </el-header>
      <el-main>
        <el-table
          :data="dataList"
          border
          :height="tableHeight"
          v-loading="loading"
          style="width: 100%"
        >
          <el-table-column label="行号" type="index" width="60"></el-table-column>
          <el-table-column label="ID" prop="id" min-width="10px" >
          </el-table-column>
          <el-table-column label="流程模板ID" prop="processDefinitionId" min-width="10%" >
          </el-table-column>
          <el-table-column label="流程实例ID" prop="processInstanceId" min-width="10%" >
          </el-table-column>
          <el-table-column label="Job类型" prop="jobType" min-width="10%" sortable>
          </el-table-column>
          <el-table-column label="Job名称" prop="elementName" min-width="10%" sortable>
          </el-table-column>
          <el-table-column label="待重试次数" prop="retries" min-width="10%" >
          </el-table-column>
          <el-table-column label="Job控制类型" prop="jobHandlerType" min-width="10%" sortable>
          </el-table-column>
          <el-table-column label="创建时间" prop="createTime" min-width="10%" sortable>
          </el-table-column>
          <el-table-column label="操作" width="100px">
            <template slot-scope="scope">
              <el-button 
                :plain="true"
                type="primary"
                size="small"
                icon="edit"
                @click="executeJob(scope.row)"
              >重新运行</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-main>
    </el-container>
  </div>
</template>

<script type="text/javascript">
// $.ajaxSetup({
//   contentType: "application/json"
// });
import ComponentSelector from "@/components/controls/ComponentSelector";
export default {
  components: {
    ComponentSelector: ComponentSelector,
  },
  name: "JobManager",
  permit: 9,
  data() {
    return {
      jobType: "currentJob",
      dataList: [],
      dataListFull: [],
      userTaskList: [],
      userTaskListFull: [],
      activityList: [],
      inputKey: "",
      inputKey2: "",
      currentProcess:[],
      loading: false,
      isEdit:false,
      dialogVisible: false,
      listDialogVisible: false,
      listtableHeight: window.innerHeight - 420,
      tableHeight: window.innerHeight - 115,
      form: {
        processId: "",
        processName: "",
        activityName: "",
        roleName:"",
        isMulti:false,
        formAttribute:"",
        selectActivities: "start",
        orderIndex: 1,
        componentName: "",
        nextActivity:"",
        rejectActivity:"",
        nextActivityLabel:"",
        rejectActivityLabel:"",
        enableDelegate:true,
        enableRepeat:false,
        enableEdit:true
      },
      formLabelWidth: "120px"
    };
  },
  mounted() {
    this.refreshData();
  },
  methods: {
    refreshData() {
      let _self = this;
      _self.loading = true;
      _self.getJobList(new Map());
    },

    
    getJobList(indata){
      let _self = this;
      let map= new Map();
      map.set("jobType",_self.jobType);
         axios
        .post("/workflow/getJobList", JSON.stringify(map))
        .then(function(response) {
          _self.dataListFull = response.data.data;
          _self.dataList = response.data.data;
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },



  executeJob(indata){
      let _self = this;
      let map= new Map();
      map.set("jobId",indata.id);
      map.set("jobType",_self.jobType);
      axios
        .post("/workflow/executeJob",JSON.stringify(map))
        .then(function(response) {
          _self.$message("正在执行!");
          _self.showItems(_self.currentProcess);
          _self.dialogVisible = false;
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    delItem(indata) {
      let _self = this;
      axios
        .post("/workflow/deleteJobById",JSON.stringify(indata))
        .then(function(response) {
          _self.$message("删除成功!");
          _self.showItems(_self.currentProcess);
        })
        .catch(function(error) {
          console.log(error);
        });
    },

    filterUserTask(){
      let ret = [];
      for (let u of this.userTaskListFull) {
        for (let a of this.activityList) {
          if(u.name == a.activityName){
            break;
          }
        }
        ret.push(u);
      }
      this.userTaskList = ret;
    },
    search() {
      let _self = this;
      _self.dataList = [];
      if (_self.inputKey != "" || _self.parentid != "") {
        _self.dataList = _self.dataListFull.filter(function(item) {
          return (
            item.elementName.match(_self.inputKey) ||
            item.processDefinitionId.match(_self.inputKey)
          );
        });
      }
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
.el-header {
  background-color: #e8eaeb;
  height: 42px !important;
}
.el-main{
  padding:5px;
}
.el-row {
  padding-bottom: 10px;
}
</style>
