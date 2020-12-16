<template>
  <div>
    <el-row>
      <el-button type="primary" icon="el-icon-refresh" @click="refreshCache()">刷新缓存</el-button>
      <el-button type="primary" icon="el-icon-refresh" @click="getSession()">查看Session</el-button>
    </el-row>
    <el-row>
        <el-table
          :data="dataList"
          border
          :height="tableHeight"
          v-loading="loading"
          :row-style="rowStyle"
          :cell-style="cellStyle"
          style="width: 100%"
        >
          <el-table-column label="行号" type="index" width="60" />
          <el-table-column label="用户名" prop="userName" width="220" sortable>
          </el-table-column>
          <el-table-column label="登录名" prop="loginName" width="220" sortable>
          </el-table-column>
          <el-table-column label="登录时间" prop="loginTime" width="200" :formatter="dateFormatter" sortable>
          </el-table-column>
          <el-table-column label="更新时间" prop="updateTime" width="200" :formatter="dateFormatter" sortable>
          </el-table-column>
          <el-table-column label="操作" width="90">
            <template slot-scope="scope">
              <el-button
                :plain="true"
                type="warning"
                size="small"
                @click="logoutUser(scope.row)"
              >注销</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-row>
  </div>
</template>

<script type="text/javascript">

export default {
  name: "SystemManager",
  permit: 3,
  data() {
    return {
      userPermit:"",
      dialogVisible: false,
      dataList:[]
    };
  },
   created(){ 
     let _self = this;
    let systemPermission = Number(
        this.currentUser().systemPermission
      );
    if(systemPermission<5){
      //跳转至权限提醒页
      _self.$nextTick(()=>{
         _self.$router.push({ path: '/NoPermission' })
      })     
    }

    },
  methods: {
    refreshCache() {
      let _self = this;
      _self.loading = true;
      axios.post('/admin/initAllCache')
      .then(function(response) {
        _self.$message("刷新成功!");
        _self.loading = false;
      })
      .catch(function(error) {
        _self.$message("刷新失败!");
        console.log(error);
        _self.loading = false;
      });
    },
    dateFormatter(row, column) {
      let datetime = row[column.property];
      return this.datetimeFormat(datetime);
    },
    getSession(){
      let _self = this;
      _self.loading = true;
      axios.get('/admin/getAllSession')
      .then(function(response) {
        if(response.data.code ==1){
          _self.dataList = response.data.data;
          _self.loading = false;
        }else{
          _self.$message("您没有超级用户权限!");
        }
      })
      .catch(function(error) {
        _self.$message(error);
        console.log(error);
        _self.loading = false;
      });
    },
    logoutUser(indata) {
      let _self = this;
      _self.loading = true;
      axios.post('/admin/removeSession',indata.token)
      .then(function(response) {
        if(response.data.code == 1){
          _self.getSession();
        }else{
           _self.$message("您没有超级用户权限!");
        }
        _self.loading = false;
      })
      .catch(function(error) {
        console.log(error);
        _self.loading = false;
      });
    },
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
