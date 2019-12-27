<template>
  <div>
    <div class="navbar">/报表中心/工作量报表</div>
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
          </el-col>
        </el-row>
      </el-header>
      <el-main>
        <el-table :data="workloadData" v-loading="wordLoading">
          <el-table-column label="人员" prop="userName"></el-table-column>
          <el-table-column label="档案类型" prop="typeName"></el-table-column>
          <el-table-column label="卷盒数" prop="boxNum"></el-table-column>
          <el-table-column label="图册数" prop="atlasNum"></el-table-column>
          <el-table-column label="文件数" prop="fileNum"></el-table-column>
        </el-table>
      </el-main>
    </el-container>
  </div>
</template>

<script type="text/javascript">
export default {
  data() {
    return {
      workloadData: [],
      wordLoading: false,
      firstTime:'',
      endTime:'',
      findType : 'first'
    };
  },
  mounted() {
    let _self = this;
    var user = sessionStorage.getItem("access-user");
    if (user) {
      _self.clientPermission = sessionStorage.getItem(
        "access-clientPermission"
      );
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
    _self.refreshWorkloadData();
  },
  methods: {
    refreshWorkloadData() {
      let _self = this;
      var m = new Map();
      m.set("firstTime",_self.firstTime)
      m.set("endTime",_self.endTime)
      m.set("findType",_self.findType)
      _self.wordLoading = true;
      axios
        .post("/report/getWorkloadData",JSON.stringify(m))
        .then(function(response) {
          _self.workloadData = response.data.data;
          _self.wordLoading = false;
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
        _self.refreshWorkloadData();
      }
    }
  }
};
</script>


<style scoped>
.el-header{
  background-color: #E9EEF3;
  text-align: left;
}
</style>
