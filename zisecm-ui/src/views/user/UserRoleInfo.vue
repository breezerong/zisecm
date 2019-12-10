<template>
  <div>
    <div class="navbar">
      <el-breadcrumb>
        <el-breadcrumb-item>{{$t('menu.userCenter')}}</el-breadcrumb-item>
        <el-breadcrumb-item>{{$t('menu.userRoleInfo')}}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div>
      <el-table
      :data="dataList"
      style="width: 60%">
      <el-table-column
        prop="name"
        :label="$t('field.name')"
        min-width="50%">
      </el-table-column>
      <el-table-column
        prop="description"
        :label="$t('field.description')"
        min-width="50%">
      </el-table-column>
    </el-table>
    </div>
  </div>
</template>

<script type="text/javascript">
export default {
  name: "UserRoleInfo",
  permit: 1,
  data() {
    return {
      userName:"",
      dataList:[]
    };
  },
  mounted(){
    let _self = this;
    _self.userName = sessionStorage.getItem('access-userName');
    axios.post("/user/getGroupByUserName",_self.userName).then(function(response){
      _self.dataList = response.data.data;	   
      _self.loading = false;
    }).catch(function(error){
      console.log(error);
      _self.loading = false;
    });   
  },
  methods: {

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
