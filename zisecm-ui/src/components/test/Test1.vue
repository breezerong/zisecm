<template>
  <div>
    <div class="navbar">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item >测试</el-breadcrumb-item>
        <el-breadcrumb-item>测试1</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
      <el-row>
         <el-col :span="8">
          单选测试：<RoleSelectInput v-model="roleData1.value" v-bind:inputValue="roleData1.value" v-bind:isRepeat="roleData1.isRepeat"></RoleSelectInput>
        </el-col>
      </el-row>
      <el-row>
         <el-col :span="8">
          多选测试：<RoleSelectInput v-model="roleData2.value" v-bind:inputValue="roleData2.value" v-bind:isRepeat="roleData2.isRepeat"></RoleSelectInput>
        </el-col>
      </el-row>

    <div>
    </div>
  </div>
</template>

<script type="text/javascript">
import RoleSelectInput from '@/components/controls/RoleSelectInput'

export default {
  name: "Test1",
  components: {
    RoleSelectInput:RoleSelectInput
  },
  permit: 1,
  data() {
    return {
      dataList: [],
      dataListFull: [],
      roleData1:{
        value:"",
        isRepeat: false
      },
      roleData2:{
        value:"",
        isRepeat: true
      },
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
