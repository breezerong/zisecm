<template>
  <div>
      <table border="0" width="100%">
          <tr>
            <td>
              <table border="0" width="100%" class="topbar">
                <tr>
                  <td align="left" width="160px">
                    <el-input v-model="inputkey" placeholder="请输入关键字" @change="search" prefix-icon="el-icon-search"></el-input>
                  </td>
                  <td>
                    <el-button type="primary" icon="el-icon-edit" circle @click="dialogVisible = true"></el-button>
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
        <el-table-column prop="id" label="ID" width="80" sortable>
        </el-table-column>
        <el-table-column prop="taskName" label="名称" width="160" >
        </el-table-column>
        <el-table-column prop="startDate" label="开始时间" :formatter="dateFormatter"  width="140">
        </el-table-column>
        <el-table-column prop="completeDate" label="完成时间" :formatter="dateFormatter"  width="140">
        </el-table-column>
        <el-table-column prop="result" label="完成结果" width="100">
        </el-table-column>
        <el-table-column prop="message" label="审批意见" width="200">
        </el-table-column>
    </el-table>
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
  name: "ShowWorkitem",
  permit: 1,
  data() {
    return {
      currentData: [],
      workflowId:0,
      dataList: [],
      dataListFull: [],
      inputkey: "",
      loading: false,
      dialogVisible: false,
      tableHeight: window.innerHeight - 140,
      formLabelWidth: "120px"
    };
  },
   created(){ 
    let _self = this;
    _self.workflowId =_self.$route.query.workflowid;
    _self.refreshData();
    },
  methods: {
    refreshData() {
      let _self = this;
      _self.loading = true;
      axios.get('/workflow/getWorkitem',_self.workflowId)
      .then(function(response) {
        _self.dataList = response.data.data;
        console.log(JSON.stringify(_self.dataList));
        _self.dataListFull = response.data.data;
        _self.loading = false;
      })
      .catch(function(error) {
        console.log(error);
        _self.loading = false;
      });
    },
    dateFormatter(row, column) {
      let datetime = row[column.property];
      return this.datetimeFormat(datetime);
    },
    item(indata) {
      let _self = this;
      _self.currentData=indata;
      _self.form.taskId = indata.id;
      _self.dialogVisible = true;
    },
    search() {
      let _self = this;
      _self.dataList = _self.dataListFull.filter(function(item){
          return item.taskName.match(_self.inputkey) || item.result.match(_self.inputkey);
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
