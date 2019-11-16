<template>
  <div>
    <div class="navbar">/系统管理/系统缓存</div>
    <div>
      <el-button type="primary" icon="el-icon-refresh" @click="refreshCache()">刷新缓存</el-button>
    </div>
  </div>
</template>

<script type="text/javascript">

export default {
  name: "SystemManager",
  permit: 3,
  data() {
    return {
      userPermit:"",
      dialogVisible: false
    };
  },
   created(){ 
    

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
    startTest() {
      let _self = this;
      _self.loading = true;
      _self.axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        method: 'get',
        url: '/auditTest'
      })
      .then(function(response) {
        _self.$message(response.data);
        _self.loading = false;
      })
      .catch(function(error) {
        _self.$message("失败!");
        console.log(error);
        _self.loading = false;
      });
    },
    save(indata) {
      
    },
    del(indata) {
     
    },
    additem(indata) {
      
    },
    search() {
      
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
