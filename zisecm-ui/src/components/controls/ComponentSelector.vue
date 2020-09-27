<template>
  <el-container>
    <el-dialog :visible.sync="showChooseDialog" :append-to-body="true" width="60%">
      <el-input placeholder="输入搜索内容" @change="onFilter()" v-model="inputkey"></el-input>
      <el-table :data="dataList" border height="350px" fit stripe style="width: 100%">
        <el-table-column type="index" width="50"></el-table-column>
        <el-table-column prop="name" label="名称"></el-table-column>
        <el-table-column prop="description" label="说明"></el-table-column>
        <el-table-column prop="url" label="URL"></el-table-column>
        <el-table-column width="100" label="操作">
          <template slot-scope="scope">
            <el-button
              :plain="true"
              type="primary"
              size="medium"
              icon="edit"
              @click="handleSelect(scope.row)"
            >添加</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
    <el-col :span="18">
      <el-input type="text" placeholder="请选择组件" readonly="readonly" v-model="inputValue"></el-input>
    </el-col>
    <el-col :span="4">
      <el-button icon="el-icon-s-unfold" @click="handleShowDialog">{{$t('application.select')}}</el-button>
    </el-col>
  </el-container>
</template>

<script>
export default {
  data() {
    return {
      inputkey: "",
      visible: false,
      dataList: [],
      dataListFull: [],
      showChooseDialog: false
    };
  },
  model: {
    event: "change"
  },
  props: {
    //输入框默认显示值
    inputValue: {
      type: String,
      default: ""
    }
  },
  mounted() {
    this.refreshData();
  },
  methods: {
    //获取选人框体数据
    refreshData() {
      let _self = this;
      axios.get("/admin/getComponent")
        .then(function(response) {
          //console.log(response);
          _self.dataList = response.data.data;
          _self.dataListFull = response.data.data;
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    handleSelect(indata) {
      this.inputValue = indata.name;
      this.$emit("change", indata.name);
      this.showChooseDialog = false;
    },
    //筛选查询方法
    onFilter() {
      let _self = this;
      _self.dataList = _self.dataListFull.filter(function(item) {
        return (
          item.name.match(_self.inputkey) ||
          item.description.match(_self.inputkey)
        );
      });
    },
    handleShowDialog() {
      this.showChooseDialog = true;
      this.refreshData();
    }
  }
};
</script>

<style scoped>
.el-footer {
  background-color: white;
}
</style>
