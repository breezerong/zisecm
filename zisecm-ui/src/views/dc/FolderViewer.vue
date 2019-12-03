<template>
  <el-container>
    <el-row style="height:30px">
      <el-breadcrumb style="padding-top:10px;padding-bottom:10px;padding-top:5px">
        <el-breadcrumb-item>&nbsp; 通知公告</el-breadcrumb-item>
      </el-breadcrumb>
    </el-row>
    <el-header style="padding-top:5px;height:50px;">
      <el-col :span="5">
        <el-input v-model="inputKey" :placeholder="$t('application.placeholderSearch')" @keyup.enter.native="search"></el-input>
      </el-col>
    </el-header>
    <el-main>
      <el-table v-loading="loadingNoticeData" style="width:100%;height:100%"  :data="dataList.notiData">
        <el-table-column :show-overflow-tooltip="true" width="400" label="标题">
          <el-link slot-scope="scope" type="primary" @click="showFile(scope.row)">{{(scope.row.NAME)}}</el-link>
        </el-table-column>
        <el-table-column align="center" label="创建人" prop="CREATOR"></el-table-column>
        <el-table-column align="left" label="创建时间">
          <template slot-scope="scope">{{dateFormat(scope.row.CREATION_DATE)}}</template>
        </el-table-column>
      </el-table>
    </el-main>
    <el-footer>
      <el-pagination
      background
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-sizes="[1, 20, 50, 100, 200]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="itemCount"
    ></el-pagination>
    </el-footer>
  </el-container>
</template>

<script type="text/javascript">

export default {

  data() {
    return {
        loadingNoticeData:false,
        dataList:{
          notiData:[]
        },
        inputKey:'',
        itemCount: 0,
        currentPage: 1,
        pageSize:20,
        loadingNoticeData:false
    };
  },
  created() {
   let _self = this
   _self.getNewsList()
  },
  methods: {
    //格式化时间
    dateFtt(fmt, date) {
      var o = {
        "M+": date.getMonth() + 1, //月份
        "d+": date.getDate(), //日
        "h+": date.getHours(), //小时
        "m+": date.getMinutes(), //分
        "s+": date.getSeconds(), //秒
        "q+": Math.floor((date.getMonth() + 3) / 3), //季度
        S: date.getMilliseconds() //毫秒
      };
      if (/(y+)/.test(fmt))
        fmt = fmt.replace(
          RegExp.$1,
          (date.getFullYear() + "").substr(4 - RegExp.$1.length)
        );
      for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
          fmt = fmt.replace(
            RegExp.$1,
            RegExp.$1.length == 1
              ? o[k]
              : ("00" + o[k]).substr(("" + o[k]).length)
          );
      return fmt;
    },
    //格式化时间方法
    dateFormat(value) {
      var crtTime = new Date(value);
      return this.dateFtt("yyyy-MM-dd", crtTime);
    },
    getNewsList() {
      let _self = this;
      _self.loadingNoticeData = true
      var m = new Map();
      m.set("gridName", "NewsGrid");
      m.set("folderId", "");
      m.set("condition", " type_name='通知公告' and NAME like '%"+_self.inputKey+"%'");
      m.set("pageSize", _self.pageSize);
      m.set("pageIndex", (_self.currentPage - 1) * _self.pageSize);
      m.set("orderBy", " CREATION_DATE DESC");
      axios
        .post("/dc/getDocuments", JSON.stringify(m))
        .then(function(response) {
          _self.dataList.notiData = response.data.data;
          _self.itemCount = response.data.pager.total;
          _self.loadingNoticeData = false
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    search(){
      this.getNewsList()
    },
    showFile(indata){
       let condition = indata.ID;
      let href = this.$router.resolve({
        path: '/viewdoc',
        query: {
          id: condition
        }
      });
      window.open(href.href, '_blank');
    },
    //分页 页数改变
    handleSizeChange(val) {
      let _self = this;
      this.pageSize = val;
      localStorage.setItem("docPageSize", val);
      _self.getNewsList()
    },
    // 分页 当前页改变
    handleCurrentChange(val) {
      let _self = this;
      this.currentPage = val;
      _self.getNewsList()
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.el-header {
  background-color: white;
}
.el-container {
  height: 100%;
}
.el-footer {
  background-color: white;
}
</style>
