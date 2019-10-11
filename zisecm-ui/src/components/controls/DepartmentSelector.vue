<template>
  <div>
    <div style="text-align:left"><span>选择部门：{{selectedItemName}}</span></div>
    <el-tree
      :props="defaultProps"
      :data="deptList"
      node-key="id"
      lazy
      @node-click="handleNodeClick">
    </el-tree>
  </div>
</template>

<script type="text/javascript">
export default {
  name: "DepartmentSelector",
  data() {
    return {
      selectedItemName: "",
      selectedItemId: "",
      deptList:"",
      defaultProps: {
        children: 'children',
        label: 'name'
      }
    };
  },
  created(){ 
    this.bindDepartment();
  },
  methods: {
    // 部门点击事件
    handleNodeClick(indata) {
      let _self = this;
      _self.selectedItemName = indata.name;
      _self.selectedItemId = indata.id;
      this.$emit('departmentSelected', _self.selectedItemId);
      var m = new Map();
      m.set("id", indata.id);
      m.set("groupType", 1);
      if(indata.extended == false)
      {
        _self.loading = true;
        _self
        .axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data: JSON.stringify(m),
          url: "/zisecm/admin/getGroups"
        })
        .then(function(response) {
          // _self.$message("获取子节点成功!");
          indata.children = response.data.datalist;
          indata.extended = true;
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
      }
    },
    // 绑定部门
    bindDepartment() {
      let _self = this;
      _self.loading = true;
      var m = new Map();
      m.set("id", 0);
      m.set("groupType", 1);
      _self
        .axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data: JSON.stringify(m),
          url: "/zisecm/admin/getGroups"
        })
        .then(function(response) {
          _self.deptList = response.data.datalist;
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
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
