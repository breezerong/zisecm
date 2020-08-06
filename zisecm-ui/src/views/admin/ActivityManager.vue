<template>
  <div>
    <el-dialog title="添加" :visible.sync="dialogVisible" width="70%">
      <el-form :model="form">
        <el-row>
        <el-col :span="12">
        <el-form-item label="活动名称" :label-width="formLabelWidth" required>
          <el-select v-model="form.activityName">
            <div v-for="(item,idx) in userTaskList" :key="'U_'+idx">
              <el-option :label="item" :key="item" :value="item"></el-option>
            </div>
          </el-select>
        </el-form-item>
        </el-col>
        <el-col :span="12">
        <el-form-item label="组件" :label-width="formLabelWidth" required>
           <ComponentSelector
                v-model="form.componentName"
                v-bind:inputValue="form.componentName"
              ></ComponentSelector>
        </el-form-item>
        </el-col>
        </el-row>
        <el-row>
        <el-col :span="10">
          <el-form-item label="角色名" :label-width="formLabelWidth">
            <RoleSelectInput v-model="form.roleName" v-bind:inputValue="form.roleName"></RoleSelectInput>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="是否多选" :label-width="formLabelWidth">
            <el-select v-model="form.isMulti">
              <el-option label="否" :value="false"></el-option>
              <el-option label="是" :value="true"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="表单属性名" :label-width="formLabelWidth">
            <el-input v-model="form.formAttribute" auto-complete="off"></el-input>
          </el-form-item>
        </el-col>
        </el-row>
        <el-row>
        <el-col :span="18">
          <el-form-item label="选人活动" :label-width="formLabelWidth">
            <el-input v-model="form.selectActivities" placeholder="开始：start，多个用英文分号分隔" auto-complete="off"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="序号" :label-width="formLabelWidth">
           <el-input v-model="form.orderIndex" auto-complete="off"></el-input>
          </el-form-item>
        </el-col>
        </el-row>
        <el-row>
        <el-col :span="12">
          <el-form-item label="通过活动名" :label-width="formLabelWidth" required>
            <el-input v-model="form.nextActivity" auto-complete="off"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="通过活动标签" :label-width="formLabelWidth" required>
            <el-input v-model="form.nextActivityLabel" auto-complete="off"></el-input>
          </el-form-item>
        </el-col>
        </el-row>
        <el-row>
        <el-col :span="12">
        <el-form-item label="回退活动名" :label-width="formLabelWidth">
           <el-input v-model="form.rejectActivity" auto-complete="off"></el-input>
        </el-form-item>
        </el-col>
        <el-col :span="12">
        <el-form-item label="回退活动标签" :label-width="formLabelWidth">
           <el-input v-model="form.rejectActivityLabel" auto-complete="off"></el-input>
        </el-form-item>
        </el-col>
        </el-row>
        <el-row>
        <el-col :span="8">
          <el-form-item label="允许编辑" :label-width="formLabelWidth">
            <el-select v-model="form.enableEdit">
              <el-option label="否" :value="false"></el-option>
              <el-option label="是" :value="true"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
         <el-col :span="8">
          <el-form-item label="允许代理" :label-width="formLabelWidth">
            <el-select v-model="form.enableDelegate">
              <el-option label="否" :value="false"></el-option>
              <el-option label="是" :value="true"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
         <el-col :span="8">
          <el-form-item label="允许重做" :label-width="formLabelWidth">
            <el-select v-model="form.enableRepeat">
              <el-option label="否" :value="false"></el-option>
              <el-option label="是" :value="true"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">{{$t('application.cancel')}}</el-button>
        <el-button type="primary" @click="saveItem(form)">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="工作流活动界面配置" :visible.sync="listDialogVisible" width="80%">
      <el-row>
      <el-col :span="6">
        <el-input
          v-model="inputKey2"
          placeholder="请输入关键字"
          @change="search"
          prefix-icon="el-icon-search"
        ></el-input>
      </el-col>
      <el-col :span="18" style="text-align:left;">
        &nbsp; 
        <el-button type="primary" icon="el-icon-edit" plain @click="newItem()">添加</el-button>
      </el-col>
      </el-row>
      <el-table
          :data="activityList"
          border
          :height="listtableHeight"
          v-loading="loading"
          style="width: 100%"
        >
          <el-table-column label="行号" type="index" width="60"></el-table-column>
          <el-table-column label="流程名称" prop="processName" min-width="20%" sortable>
          </el-table-column>
          <el-table-column label="任务名称" prop="activityName" min-width="20%" sortable>
          </el-table-column>
          <el-table-column label="组件名称" prop="componentName" min-width="20%">
          </el-table-column>
          <el-table-column label="操作" width="180">
            <template slot-scope="scope">
              <el-button
                :plain="true"
                type="primary"
                size="small"
                icon="edit"
                @click="editItem(scope.row)"
              >编辑</el-button>
              <el-button
                :plain="true"
                type="warning"
                size="small"
                icon="edit"
                @click="delItem(scope.row)"
              >{{$t('application.delete')}}</el-button>
            </template>
          </el-table-column>
        </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="listDialogVisible = false">{{$t('application.close')}}</el-button>
      </div>
    </el-dialog>
    <el-container>
      <el-header>
        <!-- <el-breadcrumb separator="/" class="navbar">
          <el-breadcrumb-item>系统管理</el-breadcrumb-item>
          <el-breadcrumb-item>流程配置</el-breadcrumb-item>
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
          <el-table-column label="名称" prop="name" min-width="20%" sortable>
          </el-table-column>
          <el-table-column label="说明" prop="description" min-width="30%">
          </el-table-column>
          <el-table-column label="版本" prop="version" width="100">
            
          </el-table-column>
          <el-table-column label="操作" width="320">
            <template slot-scope="scope">
              <el-button
                :plain="true"
                type="primary"
                size="small"
                icon="edit"
                @click="showItems(scope.row)"
              >配置界面</el-button>
              <el-button
                :plain="true"
                type="warning"
                size="small"
                icon="edit"
                @click="updateProcess(scope.row)"
              >更新版本</el-button>
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
import RoleSelectInput from '@/components/controls/RoleSelectInput'
export default {
  components: {
    ComponentSelector: ComponentSelector,
    RoleSelectInput: RoleSelectInput
  },
  name: "ActivityManager",
  permit: 9,
  data() {
    return {
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
      tableHeight: window.innerHeight - 135,
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
      axios
        .get("/cfgworkflow/processes")
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
    updateProcess(indata){
      let _self = this;
      axios
        .post("/cfgworkflow/updateProcessId", JSON.stringify(indata))
        .then(function(response) {
          _self.$message("更新流程成功!");
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    saveItem(indata) {
      if(!this.isEdit){
        this.addItem(indata);
      }else{
        this.updateItem(indata);
      }
    },
    updateItem(indata){
      let _self = this;
      axios
        .post("/cfgworkflow/updateCfgActivity", JSON.stringify(indata))
        .then(function(response) {
          _self.$message("保存成功!");
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
        .delete("/cfgworkflow/cfgActivity/"+indata.id)
        .then(function(response) {
          _self.$message("删除成功!");
          _self.showItems(_self.currentProcess);
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    newItem(){
      this.isEdit = false;
      this.dialogVisible = true;
    },
    editItem(indata){
      this.isEdit = true;
      this.form = indata;
      this.dialogVisible = true;
    },
    addItem(indata) {
      let _self = this;
      _self.isEdit = false;
      indata.processId = _self.currentProcess.id;
      indata.processName = _self.currentProcess.name;
      axios
        .post("/cfgworkflow/newCfgActivity", JSON.stringify(indata))
        .then(function(response) {
          _self.dialogVisible = false;
          _self.showItems(_self.currentProcess);
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    refreshUserTask(id) {
      let _self = this;
      _self.loading = true;
      axios
        .get("/cfgworkflow/activities/"+id)
        .then(function(response) {
          _self.userTaskListFull = response.data.data;
          _self.filterUserTask();
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
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
    showItems(indata) {
      let _self = this;
      _self.listDialogVisible = true;
      _self.currentProcess = indata;
      axios
        .get("/cfgworkflow/cfgActivities/"+indata.id)
        .then(function(response) {
          _self.activityList = response.data.data;
          _self.refreshUserTask(indata.id);
          _self.dialogVisible = false;
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    search() {
      let _self = this;
      _self.dataList = [];
      if (_self.inputKey != "" || _self.parentid != "") {
        _self.dataList = _self.dataListFull.filter(function(item) {
          return (
            item.name.match(_self.inputKey) ||
            item.description.match(_self.inputKey)
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
