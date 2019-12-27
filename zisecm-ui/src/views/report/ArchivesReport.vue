<template>
  <div>
    <div class="navbar">/报表中心/系统报表</div>
    <el-container>
      <el-header>
        <el-row>
          <el-col :span="24" class="topbar-button">
            <el-date-picker
              style="width:14em"
              type="date"
              v-model="firstTime"
              value-format="yyyy-MM-dd"
              placeholder="开始时间"
            ></el-date-picker>
            <el-date-picker
              style="width:14em"
              type="date"
              v-model="endTime"
              value-format="yyyy-MM-dd"
              placeholder="结束时间"
            ></el-date-picker>
            <el-button type="primary" plain size="small" @click="search">点击搜索</el-button>
            <!--
            <el-button type="primary" @click="searchAll">所有数据</el-button>
            -->
          </el-col>
        </el-row>
      </el-header>
      <el-main>
        <el-table :data="brrowData" v-loading="loading">
          <el-table-column label="借阅类型" prop="brrowType"></el-table-column>
          <el-table-column label="利用人次" prop="subCount"></el-table-column>
          <el-table-column label="文件数量" prop="subChildCount"></el-table-column>
          </el-table>
      </el-main>
    </el-container>
  </div>
</template>

<script type="text/javascript">
export default {
  data() {
    return { 
    loading:false,
    brrowData:[],
    firstTime:'first',
    endTime:'',
    findType:''
    };
  },
  mounted(){ 
    let _self = this;
    var user = sessionStorage.getItem('access-user');
    if(user)
    {
      _self.clientPermission = sessionStorage.getItem('access-clientPermission');
    }
    let date = new Date();
    let startDate =new Date();
    startDate.setTime(date.getTime() - 3600 * 1000 * 24 * 90);
    let startYear = startDate.getFullYear();
    let year = date.getFullYear();
    let startMonth = startDate.getMonth()+1;
    let startDay = startDate.getDate()
    let month = date.getMonth()+1;
    let day = date.getDate();
    _self.firstTime = startYear+"-"+startMonth+"-"+startDay
    _self.endTime = year+"-"+month+"-"+day
    _self.refreshBrrowData();
  },
  methods: {
    refreshBrrowData() {
      let _self = this;
      _self.loading = true
      var m = new Map();
      m.set("firstTime",_self.firstTime)
      m.set("endTime",_self.endTime)
      m.set("findType",_self.findType)
      axios.post('/report/getBrrowData',JSON.stringify(m))
      .then(function(response) {
        _self.brrowData = response.data.data
        _self.loading = false;
        if(_self.findType=='first'){
          _self.$message({
          showClose: true,
          message: '已查找近三个月数据',
          type: 'success',
          duration:2000
        });
        }else if(_self.findType=='search'){
          _self.$message({
          showClose: true,
          message: '已查找指定日期数据',
          type: 'success',
          duration:2000
        });
        }else{
          _self.$message({
          showClose: true,
          message: '已查找所有数据',
          type: 'success',
          duration:2000
        });
        }
      })
      .catch(function(error) {
        console.log(error);
      });
    },
    search(){
      let _self = this
      if(_self.firstTime==''||_self.firstTime==null){
        this.$message({
          showClose: true,
          message: '请选择起始时间',
          type: 'warning',
          duration:1000
        });
        return
      }else if(_self.endTime==''||_self.endTime==null){
        this.$message({
          showClose: true,
          message: '请选择终止时间',
          type: 'warning',
          duration:1000
        });
        return
      }else{
        _self.findType = 'search'
        _self.refreshBrrowData();
      }}
  }
};
</script>


<style scoped>
.el-header{
  background-color: #E9EEF3;
  text-align: left;
}
</style>
