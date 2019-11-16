<template>
  <div>
    <div class="navbar">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item >{{$t('menu.searchCenter')}}</el-breadcrumb-item>
        <el-breadcrumb-item>{{$t('menu.mySearch')}}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div>
      <el-input v-model="inputkey" placeholder="请输入关键字" @change="search" prefix-icon="el-icon-search" style="width:70%"></el-input>
    </div>
    <div>
    </div>
  </div>
</template>

<script type="text/javascript">
export default {
  name: "MySearch",
  permit: 1,
  data() {
    return {
      currentData: [],
      dataList: [],
      dataListFull: [],
      taskList:[],
      inputkey: "",
      pageSize: 20,
      itemCount: 0,
      selectedItems:[],
      currentPage: 1,
      loading: false,
      dialogVisible: false,
      tableHeight: window.innerHeight - 170,
      formLabelWidth: "120px"
    };
  },
  created(){ 
    let _self = this;
    var psize = localStorage.getItem("taskPageSize");
    if (psize) {
      _self.pageSize = parseInt(psize);
    }
  },
  methods: {
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
