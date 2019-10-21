<template>
  <div>
    <div class="navbar">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item >测试</el-breadcrumb-item>
        <el-breadcrumb-item>测试2</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    
    <div>
    </div>
  </div>
</template>

<script type="text/javascript">
export default {
  name: "Test2",
  permit: 1,
  data() {
    return {
      dataList: [],
      dataListFull: [],
      inputkey: "",
      tableHeight: window.innerHeight - 170,
      formLabelWidth: "120px"
    };
  },
  created(){ 
    let _self = this;
    
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
