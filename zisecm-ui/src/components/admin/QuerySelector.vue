<template>
  <div>
    <table border="0" width="100%" >
          <tr>
            <td>
              <table border="0" width="100%" class="topbar">
                <tr>
                  <td align="left" width="160px">
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
            <el-table
                :data="dataList"
                border
                :height="tableHeight"
                v-loading="loading"
                row-style="height: 0"
                cell-style="padding:0"
                style="width: 100%">
              <el-table-column prop="name" label="名称" width="160" sortable>
              </el-table-column>
              <el-table-column prop="description" label="说明" width="160" sortable>
              </el-table-column>
              <el-table-column prop="labelColumn" label="标签列" width="100" sortable>
              </el-table-column>
              <el-table-column prop="valueColumn" label="值列" width="100" sortable>
              </el-table-column>
              <el-table-column label="操作"  width="80">
              <template slot-scope="scope">
                <el-button :plain="true" type="primary" size="small" icon="edit" @click="selectitem(scope.row)">选择</el-button>
              </template>
              </el-table-column>
              </el-table>
     </td>
                </tr>
              </table>
  </div>
</template>

<script type="text/javascript">
export default {
  name: "QuerySelector",
  data() {
    return {
      dataList: [],
      dataListFull: [],
      inputkey: "",
      tableHeight: window.innerHeight - 328,
      loading: false
    };
  },
   mounted(){ 
    this.refreshData();
  },
  methods: {
    refreshData() {
      let _self = this;
      _self.loading = true;
      axios.get('/admin/getQuery')
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
    selectitem(indata) {
      let _self = this;
      _self.$emit('onqueryselected',indata);
    },
    search() {
      let _self = this;
      _self.dataList = _self.dataListFull.filter(function(item){
        return item.name.match(_self.inputkey) || item.description.match(_self.inputkey);
      });
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
