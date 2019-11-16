<template>
  <div>
        <el-dialog :title="dialogTitle" :visible.sync="dialogVisible">
          <el-form :model="form">
            <el-row>
              <div v-if="!isCompleteSelected">
              <el-col :span="12">
                <el-form-item label="档案号" :label-width="formLabelWidth" style="float:left">
                  {{currentData.taskName}}
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="文件编码" :label-width="formLabelWidth" style="float:left">
                  {{currentData.sendUser}}
                </el-form-item>
              </el-col>
              <el-col>
                <el-form-item label="标题" :label-width="formLabelWidth" style="float:left">
                  {{dateFormat(currentData,'')}}
                </el-form-item>
              </el-col>
              <el-col>
                <el-form-item label="版本" :label-width="formLabelWidth" style="float:left">
                  {{dateFormat(currentData,'')}}
                </el-form-item>
              </el-col>
              <el-col>
                <el-form-item label="编制单位" :label-width="formLabelWidth" style="float:left">
                  {{dateFormat(currentData,'')}}
                </el-form-item>
              </el-col>
              </div>
              
            </el-row>
          </el-form>
  
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="completetask(form)">完成任务</el-button>
          </div>
        </el-dialog>
      <table border="0" width="100%">
          <tr>
            <td class="navbar">
              /档案管理/列表
            </td>
          </tr>
          <tr>
            <td>
              <table border="0" width="100%" class="topbar">
                <tr>
                  <td align="left" width="200px">
                    <el-input v-model="inputkey" placeholder="请输入关键字" @change="search" prefix-icon="el-icon-search"></el-input>
                  </td>
                  <td width="220px">
                    <el-button type="primary" icon="el-icon-finished" @click="completeselected()">批量完成</el-button>
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
              @selection-change="selectChange"
              style="width: 100%">
                <el-table-column type="selection" width="40">
                </el-table-column> 
                <el-table-column type="index" width="50">
                </el-table-column>
                <el-table-column prop="taskName" label="档案号" min-width="15%" sortable>
                </el-table-column>
                <el-table-column prop="sendUser" label="文件编码" min-width="15%" sortable>
                </el-table-column>
                <el-table-column prop="sendDate" label="标题" :formatter="dateFormat" min-width="10%" sortable>
                </el-table-column>
                <el-table-column prop="sendDate" label="版本" :formatter="dateFormat" min-width="10%" sortable>
                </el-table-column>
                <el-table-column prop="sendDate" label="编制单位" :formatter="dateFormat" min-width="10%" sortable>
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
  name: "archiveList",
  permit: 1,
  data() {
    return {
      currentData: [],
      dataList: [],
      dataListFull: [],
      inputkey: "",
      pageSize: 20,
      itemCount: 0,
      selectedItems:[],
      currentPage: 1,
      loading: false,
      dialogVisible: false,
      dialogTitle:"查看任务",
      isCompleteSelected: false,
      tableHeight: window.innerHeight - 170,
      form: {
        taskId: 0,
        result: "通过",
        message: ""
      },
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
    refreshData() {
      let _self = this;
      _self.selectedItems = [];
      _self.loading = true;
      var m = new Map();
      m.set("condition", _self.inputkey);
      m.set("pageSize", _self.pageSize);
      m.set("pageIndex", (_self.currentPage - 1) * _self.pageSize);
      _self.axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        method: 'post',
        data: JSON.stringify(m),
        url: '/workflow/getMyTodoTask'
      })
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
      console.log(val);
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
      _self
        .axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data: JSON.stringify(m),
          url: "/workflow/getMyTodoCount"
        })
        .then(function(response) {
          _self.itemCount = response.data.data;
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    completetask(indata) {
      let _self = this;
      if(_self.isCompleteSelected) {
        _self.form.taskId = [];
        for (let i = 0;i < _self.selectedItems.length;i++) {
          _self.form.taskId[i] = _self.selectedItems[i].id;
        }
      }
      console.log(JSON.stringify(_self.form));
      _self.axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        datatype: 'json',
        method: 'post',
        data: JSON.stringify(_self.form),
        url: '/workflow/completeTask'
      })
      .then(function(response) {
        _self.dialogVisible = false;
        _self.refreshData();
        _self.$message("完成任务成功!");
        _self.$emit('refreshcount');
      })
      .catch(function(error) {
        console.log(error);
      });
    },
    completeselected(){
      let _self = this;
      if(_self.selectedItems.length==0){
        _self.$message("未选择任务!");
      }
      else{
        _self.dialogTitle = "批量完成任务";
        _self.isCompleteSelected = true;
        _self.dialogVisible = true;
      }
    },
    showitem(indata) {
      let _self = this;
      _self.dialogTitle = "查看任务";
      _self.isCompleteSelected = false;
      _self.currentData=indata;
      _self.form.taskId = indata.id;
      _self.dialogVisible = true;
    },
    dateFormat(row, column) {
        let datetime = row.sendDate;
        if(datetime){
          datetime = new Date(datetime);
          let y = datetime.getFullYear() + '-';
          let mon = datetime.getMonth()+1 + '-';
          let d = datetime.getDate();
          return y + mon + d + " "+datetime.getHours()+":"+datetime.getMinutes()+":"+datetime.getSeconds();
        }
        return ''
      },
    search() {
      let _self = this;
      _self.dataList = _self.dataListFull.filter(function(item){
          return item.taskName.match(_self.inputkey) || item.description.match(_self.inputkey);
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
</style>
