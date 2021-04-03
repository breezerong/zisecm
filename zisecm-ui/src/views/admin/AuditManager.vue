<template>
  <div>
      <el-header>
        <el-form inline="true">
            <el-form-item>
            <el-select v-model="eventName">
              <el-option label="All" key="all" value=""></el-option>
               <div v-for="(item,idx) in dataEvent" :key="'E_'+idx">
                  <el-option :label="item.name" :key="item.name" :value="item.name"></el-option>
                </div>
            </el-select>
            </el-form-item>
            <el-form-item>
            <el-date-picker
              v-model="startDate"
              type="date"
              :placeholder="$t('application.startDate')">
            </el-date-picker>
            </el-form-item>
            <el-form-item>
            <el-date-picker
              v-model="endDate"
              type="date"
              :placeholder="$t('application.endDate')">
            </el-date-picker>
            </el-form-item>
            <el-form-item>
            <el-input
              v-model="inputKey"
              placeholder="请输入关键字"
              prefix-icon="el-icon-search"
            ></el-input>
            </el-form-item>
            <el-form-item>
            <el-button
              type="primary"
              icon="el-icon-search"
              plain
              @click="search"
            >{{$t('application.SearchData')}}</el-button>
            </el-form-item>
        </el-form>
      </el-header>
      <el-main>
        <el-table
          :data="dataList"
          border
          :height="tableHeight"
          v-loading="loading"
          style="width: 100%"
        >
          <el-table-column label="行号" type="index" width="60"></el-table-column>
          <el-table-column label="应用名" prop="appName" width="90">
          </el-table-column>
          <el-table-column label="用户名" prop="userName" width="180" sortable>
          </el-table-column>
          <el-table-column label="事件名" prop="actionName" width="160">
          </el-table-column>
          <el-table-column label="对象ID" prop="docId" width="260">
          </el-table-column>
          <el-table-column label="执行时间" prop="excuteDate" :formatter="dateFormatter" width="180">
          </el-table-column>
           <el-table-column label="详细信息" prop="message" min-width="20%" >
          </el-table-column>
          <!--
          <el-table-column label="操作" width="90">
            
            <template slot-scope="scope">
              <el-button
                :plain="true"
                type="primary"
                size="small"
                icon="delete"
                @click="delItem(scope.row)"
              >{{$t('application.delete')}}</el-button>
            </template>
          </el-table-column>
          -->
        </el-table>
         <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[10, 20, 50, 100, 200]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="itemCount"
        ></el-pagination>
      </el-main>
  </div>
</template>

<script type="text/javascript">
// $.ajaxSetup({
//   contentType: "application/json"
// });

export default {
  name: "Auditanager",
  permit: 9,
  data() {
    return {
      dataList: [],
      dataEvent: [],
      eventName: "",
      startDate: null,
      endDate: null,
      inputKey: "",
      pageSize: 20,
      currentPage: 1,
      itemCount: 0,
      loading: false,
      dialogVisible: false,
      tableHeight: window.innerHeight - 155,
      formLabelWidth: "120px"
    };
  },
  mounted() {
     let _self = this;
    let systemPermission = Number(
        this.currentUser().systemPermission
      );
    if(systemPermission<5){
      _self.$router.push({ path: '/NoPermission' });    
    }
    var psize = localStorage.getItem("auditPageSize");
    if (psize) {
      _self.pageSize = parseInt(psize);
    }
    this.bindEvent();
  },
  methods: {
    bindEvent(){
       let _self = this;
      _self.loading = true;
      axios
        .get("/admin/sysevent")
        .then(function(response) {
          _self.dataEvent = response.data.data;
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    dateFormatter(row, column) {
      //
      let datetime = row[column.property];
      console.log(datetime);
      return this.datetimeFormat(datetime);
    },
    // 分页 页数改变
    handleSizeChange(val) {
      this.pageSize = val;
      localStorage.setItem("auditPageSize", val);
      this.refreshData();
    },
    // 分页 当前页改变
    handleCurrentChange(val) {
      this.currentPage = val;
      this.refreshData();
    },
    refreshData() {
      let _self = this;
      var m = new Map();
      var condition= "";
      if(_self.eventName.length>0){
        condition = "ACTION_NAME ='"+_self.eventName+"'";
      }
      if(_self.inputKey != "" && _self.inputKey != null){
        if(condition != ""){
          condition += " and USER_NAME like '%"+_self.inputKey+"%'";
        }else{
          condition = "USER_NAME like '%"+_self.inputKey+"%'";
        }
      }
      if(_self.startDate != null){
         if(condition != ""){
          condition += " and EXCUTE_DATE >= '"+_self.dateFormat(_self.startDate)+"'";
        }else{
          condition = "EXCUTE_DATE >= '"+_self.dateFormat(_self.startDate)+"'";
        }
      }
      if(_self.endDate != null){
         if(condition != ""){
          condition += " and EXCUTE_DATE <= '"+_self.dateFormat(_self.endDate)+"'";
        }else{
          condition = "EXCUTE_DATE <= '"+_self.dateFormat(_self.endDate)+"'";
        }
      }
      m.set("pageIndex", _self.currentPage-1);
      m.set("pageSize", _self.pageSize);
      m.set("condition", condition);
      _self.loading = true;
      axios
        .post("/admin/searchAudit",JSON.stringify(m))
        .then(function(response) {
          _self.dataList = response.data.data;
          _self.itemCount = response.data.pager.total;
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    delItem(indata) {
      let _self = this;
      axios.delete("/admin/audit", indata.id)
        .then(function(response) {
          _self.$message("删除成功!");
          _self.refreshData();
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    search() {
      this.refreshData();
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.el-header {
  padding-top: 0px;
  padding-left: 5px;
  height: 42px !important;
}
</style>
