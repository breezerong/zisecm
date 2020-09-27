<template>
  <div>
    <el-container>
  <el-dialog :visible.sync="showDialog" width="60%" 
      :append-to-body="true"
      :close-on-click-modal="false"
      @close="showDialog=false">
      <el-row>
          <el-input  v-model="inputKey" placeholder="请输入关键字" @change="search" prefix-icon="el-icon-search"></el-input>
      </el-row>
            <el-table
                :data="dataList"
                border
                :height="tableHeight"
                v-loading="loading"
                row-style="height: 0"
                cell-style="padding:0"
                style="width: 100%">
              <el-table-column type="index" label="序号" width="70">
              </el-table-column>
              <el-table-column prop="name" label="名称" width="160" sortable>
              </el-table-column>
              <el-table-column prop="description" label="说明" min-width="20%" sortable>
              </el-table-column>
              <el-table-column prop="labelColumn" label="标签列" width="100" sortable>
              </el-table-column>
              <el-table-column prop="valueColumn" label="值列" width="100" sortable>
              </el-table-column>
              <el-table-column label="操作"  width="80">
              <template slot-scope="scope">
                <el-button :plain="true" type="primary" size="small" icon="edit" @click="selectItem(scope.row)">{{$t('application.select')}}</el-button>
              </template>
              </el-table-column>
              </el-table>
     </el-dialog>
    <el-col :span="18">
      <el-input type="text" placeholder="请选择" v-model="inputValue" @change="valueChange"></el-input>
      <input value="value1" type="hidden" />
    </el-col>
    <el-col :span="4">
      <el-button icon="el-icon-s-unfold" @click="showDialog = true">{{$t('application.select')}}</el-button>
    </el-col>
  </el-container>
  </div>
</template>

<script type="text/javascript">
export default {
  name: "QuerySelector",
  data() {
    return {
      dataList: [],
      dataListFull: [],
      inputKey: "",
      showDialog: false,
      tableHeight: 360,
      loading: false
    };
  },
   model: {
    prop: "value1",
    event: "change"
  },
  props: {
    //输入框默认显示值
    inputValue: {
      type: String,
      default: ""
    }
  },
   mounted(){ 
    this.refreshData();
  },
  methods: {
    valueChange(){
      this.$emit("change", this.inputValue);
      // console.log("change:" + this.inputValue);
    },
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
    selectItem(indata) {
      this.inputValue = indata.name;
      this.$emit("change", indata.name);
      this.showDialog = false;
    },
    search() {
      let _self = this;
      _self.dataList = _self.dataListFull.filter(function(item){
        return item.name.match(_self.inputKey) || (item.description && item.description.match(_self.inputKey));
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
