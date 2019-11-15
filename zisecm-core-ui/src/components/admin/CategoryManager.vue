<template>
  <div >
     <table border="0" width="100%" >
          <tr>
            <td>
              <table border="0" width="100%" class="topbar">
                <tr>
                  <td width="160px">
                     系统元数据
                  </td>
                  <td width="160px">
                    <el-input  v-model="inputkey" placeholder="请输入关键字" @change="search" prefix-icon="el-icon-search"></el-input>
                  </td>
                  <td>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>
              <table border="0" width="100%">
                <tr>
                  <td width="160px">
        <el-table
                  :data="catList"
                  border
                  :height="tableHeight"
                  v-loading="loading"
                  style="width: 100%">
          <el-table-column label="自定义表名称" sortable align="left">
            <template slot-scope="scope">
              <el-button type="primary"  @click="showitem(scope.row)">{{scope.row.name}}</el-button>
            </template>
          </el-table-column>
        </el-table>  
                  </td>
                  <td>
          <el-table
                    :data="dataList"
                    border
                    :height="tableHeight"
                    v-loading="loading2"
                    row-style="height: 0"
                    cell-style="padding:0"
                    style="width: 100%">
            <el-table-column
              label="行号"
              type="index"
              width="60">
            </el-table-column>
            <el-table-column prop="name" label="字段" width="120" sortable>
            </el-table-column>
            <el-table-column prop="type" label="类型" width="120" sortable>
            </el-table-column>
            <el-table-column prop="description" label="名称" width="120" sortable>
            </el-table-column>
            <el-table-column label="操作" width="80">
              <template slot-scope="scope">
                <el-button :plain="true" type="primary" size="small" icon="edit" @click="selectitem(scope.row)">选择</el-button>
              </template>
            </el-table-column>
          </el-table>
                  </td>
                </tr>
              </table>
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
  name: "CategoryManager",
  permit: 9,
  data() {
    return {
      catList:[],
      dataList:[],
      dataListFull:[],
      inputkey:"",
      loading:false,
      loading2:false,
      currrentType:"",
      tableHeight: window.innerHeight-320,
      formLabelWidth: "120px"
    };
  },
   created(){ 
    let _self = this;
      _self.loading2 = true;
      axios.get('/admin/getAttribute')
      .then(function(response) {
        _self.dataList = response.data.data;
        _self.dataListFull = response.data.data;
        _self.loading2 = false;
      })
      .catch(function(error) {
        console.log(error);
        _self.loading2 = false;
      });

    },
  methods: {
    refreshData() {
      let _self = this;
      _self.loading = true;
      axios.get('/admin/getCategories')
      .then(function(response) {
        _self.catList = response.data.data;
        _self.loading = false;
      })
      .catch(function(error) {
        console.log(error);
        _self.loading = false;
      });
    },
    showitem(indata) {
      let _self = this;
      _self.loading2 = true;
      _self.currentType = indata.name;
      axios.get('/admin/getAttribute')
      .then(function(response) {
        _self.dataList = response.data.data;
        _self.dataListFull = response.data.data;
        _self.loading2 = false;
      })
      .catch(function(error) {
        console.log(error);
        _self.loading2 = false;
      });
    },
    selectitem(indata) {
      let _self = this;
      //indata.typeName=_self.currentType;
      _self.$emit('onselected',indata);
    },
    search() {
      let _self = this;
      _self.dataList = [];
      if (_self.inputkey != "" || _self.parentid != "") {
        _self.dataList = _self.dataListFull.filter(function(item){
          return item.name.match(_self.inputkey) || item.description.match(_self.inputkey);
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
</style>
