<template>
  <div>
      <table border="0" width="100%">
          <tr>
            <td class="navbar">
              /图纸管理/处理中
            </td>
          </tr>
          <tr>
            <td>
              <table border="0" width="100%" class="topbar">
                <tr>
                  <td align="left" width="300px">
                    <el-input v-model="inputkey" placeholder="请输入关键字" @change="search()" prefix-icon="el-icon-search"></el-input>
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
              <el-table-column type="selection" width="40" @selection-change="selectChange">
              </el-table-column> 
              <el-table-column type="index" width="50">
              </el-table-column>      
              <el-table-column prop="NAME" label="名称" sortable min-width="50%" >
              </el-table-column>
              <el-table-column prop="C_PROJECT" label="项目名称" sortable min-width="20%" >
              </el-table-column>
              <el-table-column prop="CREATION_DATE" label="创建时间" sortable :formatter="dateFormatter" width="180">
              </el-table-column>
              <el-table-column prop="STATUS" label="状态" width="120">
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

export default {
  name: "Processing",
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
    _self.refreshData();
    },
  methods: {
    refreshData() {
      let _self = this;
      _self.loading = true;
      var m = new Map();
      m.set("status","已审批");
      m.set("condition", _self.inputkey);
      m.set("pageSize", _self.pageSize);
      m.set("pageIndex", (_self.currentPage - 1) * _self.pageSize);
      _self.axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        method: 'post',
        data: JSON.stringify(m),
        url: '/zisecm/drawing/getDrawing'
      })
      .then(function(response) {
        _self.dataList = response.data.data;
        console.log(JSON.stringify(_self.dataList));
        _self.dataListFull = response.data.data;
        //加载分页信息
        _self.itemCount = response.data.pager.total;
        _self.loading = false;
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
      localStorage.setItem("docPageSize", val);
      this.refreshData();
      //console.log('handleSizeChange', val);
    },
    // 分页 当前页改变
    handleCurrentChange(val) {
      this.currentPage = val;
      this.refreshData();
    },
    dateFormatter(row, column) {
        let datetime = row.CREATION_DATE;
        return this.datetimeFormat(datetime);
      },
    search() {
      let _self = this;
      _self.dataList = _self.dataListFull.filter(function(item){
          return item.NAME.match(_self.inputkey);
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
