<template>
  <div >
     <table border="0" width="100%" >
          <tr>
            <td>
              <table border="0" width="100%" class="topbar">
                <tr>
                  <td width="160px">
                     表单字段数据
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
                      <el-table-column label="表单名称" sortable align="left">
                        <template slot-scope="scope">
                          <el-button type="primary"  @click="showItem(scope.row.id)">{{scope.row.typeName}}</el-button>
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
                              style="width: 100%">
                      <el-table-column prop="id" label="ID" min-width="25%" >
                      </el-table-column>
                      <el-table-column prop="attrName" label="名称" min-width="20%" sortable>
                      </el-table-column>
                      <el-table-column prop="label" label="标签" min-width="20%" sortable>
                      </el-table-column>
                      <el-table-column  label="类型" min-width="10%"  sortable>
                         <template slot-scope="scope">
                           {{scope.row.controlType}}
                        </template>
                      </el-table-column>
                      <el-table-column label="操作" width="80">
                        <template slot-scope="scope">
                          <el-button :plain="true" type="primary" size="small" icon="edit" @click="selectItem(scope.row)">{{$t('application.select')}}</el-button>
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

export default {
  name: "FormItemSelector",
  data() {
    return {
      catList:[],
      dataList:[],
      dataListFull:[],
      inputkey:"",
      loading:false,
      loading2:false,
      currrentType:"",
      tableHeight: window.innerHeight-420,
      formLabelWidth: "120px"
    };
  },
  mounted(){ 
    this.refreshData();
  },
  methods: {
    refreshData() {
      let _self = this;
      _self.loading = true;
      axios.post('/admin/getDefaultForm')
      .then(function(response) {
        _self.catList = response.data.data;
        _self.loading = false;
      })
      .catch(function(error) {
        console.log(error);
        _self.loading = false;
      });
    },
    showItem(indata) {
      let _self = this;
      _self.loading2 = true;
      _self.currentType = indata.name;
      axios.post('/admin/getFormItem',JSON.stringify(indata))
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
    selectItem(indata) {
      let _self = this;
      _self.$emit('onSelected',indata);
    },
    search() {
      let _self = this;
      _self.dataList = [];
      if (_self.inputkey != null) {
        _self.dataList = _self.dataListFull.filter(function(item){
          return item.attrName.match(_self.inputkey) || item.label.match(_self.inputkey)|| item.id.match(_self.inputkey);
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
