<template>
  <div >
    <el-dialog :visible.sync="showAttrDialog" width="70%" title="选择属性" 
      :append-to-body="true"
      :close-on-click-modal="false"
      @open="refreshData"
      >
      <el-row>
        <el-input  v-model="inputkey" placeholder="请输入关键字" @change="search" prefix-icon="el-icon-search"></el-input>
      </el-row>
      <el-row>
          <el-table
                              :data="dataList"
                              border
                              :height="tableHeight"
                              v-loading="loading2"
                              style="width: 100%">
              <el-table-column label="行号" type="index" width="60"></el-table-column>
          <el-table-column prop="name" label="字段名称" width="180" sortable></el-table-column>
          <el-table-column prop="description" label="说明" min-width="20%" sortable></el-table-column>
          <el-table-column prop="fieldType" label="类型" width="120" sortable></el-table-column>
          <el-table-column prop="length" label="长度" width="120" sortable></el-table-column>
                      <el-table-column label="操作" width="80">
                        <template slot-scope="scope">
                          <el-button :plain="true" type="primary" size="small" icon="edit" @click="handleSelect(scope.row)">{{$t('application.select')}}</el-button>
                        </template>
                      </el-table-column>
                    </el-table>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button @click="closeDialog()">{{$t('application.cancel')}}</el-button>
      </div>
    </el-dialog>
    <el-container>
    <el-col :span="18">
      <el-input type="text" placeholder="请选择" v-model="inputValue"></el-input>
      <input value="value1" type="hidden" />
    </el-col>
    <el-col :span="4">
      <el-button icon="el-icon-s-unfold" @click="handleShowAttrDialog">{{$t('application.select')}}</el-button>
    </el-col>
  </el-container>
  </div>
</template>

<script type="text/javascript">

export default {
  name: "AttributeSelector",
  data() {
    return {
      showAttrDialog:false,
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
    this.inputkey = this.inputValue;
  },
  methods: {
    refreshData() {
      let _self = this;
      _self.inputkey = _self.inputValue;
      _self.loading = true;
      axios.get('/admin/getAttribute')
      .then(function(response) {
        _self.dataList = response.data.data;
        _self.dataListFull = response.data.data;
        _self.loading = false;
      })
      .catch(function(error) {
        console.log(error);
        _self.loading = false;
      });
    },
    handleSelect(indata) {
      this.inputValue = indata.name;
      this.$emit("change", indata.name);
      this.showAttrDialog = false;
    },
    handleShowAttrDialog() {
      this.showAttrDialog = true;
      this.refreshData();
    },
    closeDialog() {
      this.showAttrDialog = false;
      
    },
    search() {
      let _self = this;
      _self.dataList = [];
      if (_self.inputkey != null) {
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
