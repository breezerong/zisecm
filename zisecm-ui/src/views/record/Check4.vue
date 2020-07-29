<template>
  <div>
    <el-container>
      <el-header>
        <el-row>
          <el-col :span="24" class="topbar-button">
            <el-select v-model="classic" placeholder="选择档案分类">
                <div v-for="item in classicData" :key="item.NAME">
                  <el-option :label="item.NAME" :key="item.NAME" :value="item.NAME"></el-option>
                </div>
              </el-select>
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
            <el-button type="primary" plain  @click="startCheck">开始检查</el-button>
            &nbsp;
            <el-button type="primary" plain  @click="exortExcel">{{$t('application.ExportExcel')}}</el-button>
            <!--
            <el-button type="primary" @click="searchAll">所有数据</el-button>
            -->
          </el-col>
        </el-row>
      </el-header>
      <el-main>
        <el-table id="outTable" :data="checkData" v-loading="loading">
          <el-table-column type="index" width="50">
          </el-table-column>
          <el-table-column
              prop="coding"
              label="编码"
              width="200">
            </el-table-column>
            <el-table-column
              prop="revision"
              label="版本"
              width="100">
            </el-table-column>
            <el-table-column
              prop="archiveCoding"
              label="档案号"
              width="200">
            </el-table-column>
            <el-table-column
              prop="title"
              label="标题"
              width="220">
            </el-table-column>
          <el-table-column label="真实性">
            <el-table-column
              prop="trueSource"
              label="来源"
              width="100">
            </el-table-column>
            <el-table-column
              prop="trueMetaData"
              label="元数据"
              width="100">
            </el-table-column>
            <el-table-column
              prop="trueContent"
              label="内容"
              width="100">
            </el-table-column>
          </el-table-column>
          <el-table-column label="完整性">
            <el-table-column
              prop="integrityMetaData"
              label="元数据"
              width="100">
            </el-table-column>
            <el-table-column
              prop="integrityContent"
              label="内容"
              width="100">
            </el-table-column>
            <el-table-column
              prop="integrityPackage"
              label="信息包"
              width="100">
            </el-table-column>
          </el-table-column>
          <el-table-column label="可用性">
            <el-table-column
              prop="useMetaData"
              label="元数据"
              width="100">
            </el-table-column>
            <el-table-column
              prop="useContent"
              label="内容"
              width="100">
            </el-table-column>
          </el-table-column>
          <el-table-column label="安全性">
            <el-table-column
              prop="securityVirus"
              label="病毒检测"
              width="100">
            </el-table-column>
          </el-table-column>
          <el-table-column label="总体结果" prop="totalResult"></el-table-column>
          </el-table>
      </el-main>
    </el-container>
  </div>
</template>

<script type="text/javascript">
import FileSaver from 'file-saver'
import XLSX from 'xlsx'

export default {
  data() {
    return { 
    loading:false,
    checkData:[],
    classicData: [],
    classic: "",
    firstTime:'',
    endTime:'',
    findType:''
    };
  },
  mounted(){ 
    let _self = this;
    _self.loadClassic();
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
    //_self.refreshData();
  },
  methods: {
    loadClassic() {
      let _self = this;
      _self.loading = true
      axios.post('/record/getArchiveClassic')
      .then(function(response) {
        _self.classicData = response.data.data
        _self.loading = false;
      })
      .catch(function(error) {
        console.log(error);
      });
    },
    exortExcel(){
       var wb = XLSX.utils.table_to_book(document.querySelector('#outTable'))
         /* get binary string as output */
         var wbout = XLSX.write(wb, { bookType: 'xlsx', bookSST: true, type: 'array' })
         try {
             FileSaver.saveAs(new Blob([wbout], { type: 'application/octet-stream' }), 'docCheckData.xlsx')
         } catch (e) { if (typeof console !== 'undefined') console.log(e, wbout) }
         return wbout
    },
    refreshData() {
      let _self = this;
      _self.loading = true
      var m = new Map();
      m.set("startDate",_self.firstTime)
      m.set("endDate",_self.endTime)
      m.set("classic",_self.classic)
      axios.post('/record/getCheck4Data',JSON.stringify(m))
      .then(function(response) {
        _self.checkData = response.data.data
        _self.loading = false;
          _self.$message({
          showClose: true,
          message: '已查找数据',
          type: 'success',
          duration:2000
        });
      })
      .catch(function(error) {
        console.log(error);
      });
    },
    startCheck(){
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
        _self.refreshData();
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
