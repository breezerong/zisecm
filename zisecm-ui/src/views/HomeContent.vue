<template>
  <el-container>
    <el-main>
      <el-row>
        <el-col :span="16">
          <el-card :body-style="{ height: '130px' }">
            <div slot="header" class="clearfix">
              <span style="float: left;">全文搜索</span>
            </div>
            <div>
              <el-row>
                <el-autocomplete
                  class="inline-input"
                  prefix-icon="el-icon-search"
                  :placeholder="$t('application.placeholderSearch')"
                  :trigger-on-focus="false"
                  @keyup.enter.native="jumpToFullSearch"
                  style="width:50%;"
                  v-model="inputkey"
                ></el-autocomplete>
                <el-checkbox :label="$t('application.propertyOnly')" v-model="propertyOnly"></el-checkbox>
              </el-row>
              <el-row>
                <span>{{$t('application.docTypeName')}}</span>
                <el-checkbox
                  :indeterminate="isIndeterminate"
                  v-model="checkAll"
                  @change="handleCheckAllChange"
                >{{$t('application.selectAll')}}</el-checkbox>
                <el-checkbox-group v-model="checkedCards">
                  <el-checkbox
                    v-for="card in cards"
                    :label="card.label"
                    :key="card.id"
                    checked
                    @change="handleCheckedTypeChange"
                  >{{card.label}}</el-checkbox>
                </el-checkbox-group>
              </el-row>
            </div>
          </el-card>
          <el-card :body-style="{ height: '260px' }">
            <div slot="header" class="clearfix">
              <span style="float: left;">待办任务<el-badge :value="totalCount" class="item"></el-badge>
              </span>
              <el-link
                :underline="false"
                style="float: right; padding: 3px 0"
                @click="$router.push(jumpPath.todolist)"
                type="primary"
              >更多</el-link>
            </div>
            <el-table :data="dataList.todoData" v-loading="loadingTodoData" style="width:100%;" :show-header="false">
              <el-table-column label="任务名称">
                <el-link slot-scope="scope" type="primary" @click="showFile(scope.row.id)">{{(scope.row.name)}}</el-link>
             </el-table-column>
              <el-table-column prop="startUser" label="发送人"></el-table-column>
              <el-table-column label="发送时间" align="right">
                <template slot-scope="scope">{{dateFormat(scope.row.createTime)}}</template>
              </el-table-column>
            </el-table>
          </el-card>
          <el-card :body-style="{ height: '300px' }">
            <div slot="header" class="clearfix">
              <span style="float: left;">最新文档</span>
            </div>
            <el-table
              v-loading="loadingNewDocData"
              :data="dataList.newdocData"
              style="width:100%;"
              :show-header="false"
            >
              <el-table-column prop="CODING" label="编号"></el-table-column>
              <el-table-column width="90" prop="REVISION" label="版本"></el-table-column>
              <el-table-column prop="NAME" label="文件名"></el-table-column>
              <el-table-column label="编制日期" align="right">
                <template slot-scope="scope">{{dateFormat(scope.row.C_DOC_DATE)}}</template>
              </el-table-column>
            </el-table>
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card :body-style="{ height: '80px' }">
            <div slot="header" class="clearfix">
              <span style="float: center;">个人中心</span>
              <el-link style="float: right; padding: 3px 0; visibility: hidden;">更多</el-link>
            </div>
            <el-col :span="12">
              <i style="font-size : 50px" class="el-icon-user"></i>
              <el-link :underline="false" @click="$router.push(jumpPath.userCenter)">我的信息</el-link>
            </el-col>
            <el-col :span="12">
              <i style="font-size : 50px" class="el-icon-s-claim"></i>
              <el-link :underline="false" @click="$router.push({path:''})">我的授权</el-link>
            </el-col>
          </el-card>
          <el-card :body-style="{ height: '200px' }">
            <div slot="header" class="clearfix">
              <span style="float: center;">通知公告</span>
              <el-link :underline="false" style="float: right; padding: 3px 0" type="primary">更多</el-link>
            </div>
            <el-table
              v-loading="loadingNoticeData"
              :data="dataList.notiData"
              style="width:100%;"
              :show-header="false"
            >
              <el-table-column :show-overflow-tooltip="true" width="400" label="标题">
                <el-link slot-scope="scope" type="primary" @click="showFile(scope.row.id)">{{(scope.row.NAME)}}</el-link>
              </el-table-column>
              <el-table-column align="right">
                <template slot-scope="scope">{{dateFormat(scope.row.CREATION_DATE)}}</template>
              </el-table-column>
            </el-table>
          </el-card>
          <el-card :body-style="{ height: '350px' }">
            <div slot="header" class="clearfix">
              <span style="float: center;">馆藏状态</span>
              <el-link style="float: right; padding: 3px 0; visibility: hidden;">更多</el-link>
            </div>
            <div id="collectionChart" style="height: 100%;width: 100%;float: right;"></div>
          </el-card>
        </el-col>
      </el-row>
    </el-main>
  </el-container>
</template>
<script>
export default {
  data() {
    return {
      jumpPath: {
        search: "",
        todolist: "/workflow/todotask",
        notification: "",
        userCenter: "/user/userinfo"
      },
      collectionChartData: {
        xAxisData: [],
        yAxisData: []
      },
      dataList: {
        todoData: [],
        newdocData: [],
        notiData: []
      },
      inputkey: "",
      loadingTodoData: false,
      loadingNewDocData :false,
      loadingNoticeData:false,
      checkAll: true,
      isIndeterminate: false,
      checkedCards: [],
      propertyOnly: false,
      cards: [],
      cardsLabel: [],
      totalCount:0,
      collectionChart: Object
    };
  },
  created() {
    let _self = this;
    _self.getToDoList();
    _self.loadCards();
    _self.getNewsList();
    _self.getDocument();
  },
  mounted() {
    let _self = this;
    _self.collectionChart = _self.echarts.init(
      document.getElementById("collectionChart")
    );
    _self.getCollectionData();
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
    //获取待办任务列表，最多五条
    getToDoList() {
      let _self = this;
      var m = new Map();
      _self.loadingTodoData = true
      m.set("condition", "");
      m.set("pageSize", 7);
      m.set("pageIndex", 0);
      m.set("userId", sessionStorage.getItem("access-userName"));
      axios
        .post("/workflow/todoTask", JSON.stringify(m))
        .then(function(response) {
          _self.dataList.todoData = response.data.data;
          _self.totalCount = response.data.totalCount;
          _self.loadingTodoData = false
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    //获取通知公告栏信息列表
    getNewsList() {
      let _self = this;
      _self.loadingNoticeData = true
      var m = new Map();
      m.set("gridName", "NewsGrid");
      m.set("folderId", "");
      m.set("condition", " type_name='通知公告' ");
      m.set("pageSize", 5);
      m.set("pageIndex", 0);
      m.set("orderBy", " CREATION_DATE DESC");
      axios
        .post("/dc/getDocuments", JSON.stringify(m))
        .then(function(response) {
          _self.dataList.notiData = response.data.data;
          _self.loadingNoticeData = false
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    //获取馆藏状态数据
    getCollectionData() {
      let _self = this;
      axios.get("/dc/getCollectionData").then(function(response) {
        _self.collectionChartData = response.data.data;
        //绘制图表
        _self.collectionChart.setOption({
          title: { text: "馆藏" },
          tooltip: {},
          xAxis: {},
          yAxis: {
            data: _self.collectionChartData.xAxisData.reverse()
          },
          series: [
            {
              name: "数量",
              type: "bar",
              data: _self.collectionChartData.yAxisData.reverse(),
              itemStyle: {
                normal: {
                  color: function(d) {
                    return (
                      "#" +
                      Math.floor(
                        Math.random() * (256 * 256 * 256 - 1)
                      ).toString(16)
                    );
                  },
                  label: {
                    show: true, //开启显示
                    position: "right", //在上方显示
                    textStyle: {
                      //数值样式
                      color: "black",
                      fontSize: 16
                    }
                  }
                }
              }
            }
          ]
        });
      });
    },
    //获取最新的5条新建文档
    getDocument() {
      let _self = this;
      _self.loadingNewDocData = true
      var m = new Map();
      m.set("pageSize", 7);
      m.set("pageIndex", 0);
      m.set("orderBy", " C_DOC_DATE DESC");
      axios
        .post("/dc/getNewDocuments", JSON.stringify(m))
        .then(function(response) {
          _self.dataList.newdocData = response.data.data;
          _self.loadingNewDocData = false
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    //加载全文搜索的勾选项
    loadCards() {
      let _self = this;
      var m = new Map();
      axios
        .post("/search/getCardSearchs", _self.getLang())
        .then(function(response) {
          _self.cards = response.data.data;
          var i = 0;
          for (i = 0; i < _self.cards.length; i++) {
            _self.cardsLabel[i] = _self.cards[i].label;
          }
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    //点击checkbox的事件
    handleCheckedTypeChange() {
      let checkedCount = this.checkedCards.length;
      this.checkAll = checkedCount === this.cardsLabel.length;
      this.isIndeterminate =
        checkedCount > 0 && checkedCount < this.cards.length;
    },
    handleCheckAllChange(val) {
      this.checkedCards = val ? this.cardsLabel : [];
      this.isIndeterminate = false;
    },
    jumpToFullSearch() {
      var map = new Map();
      let _self = this;
      map.set("inputkey", _self.inputkey);
      map.set("propertyOnly", _self.propertyOnly);
      map.set("checkedCards", _self.checkedCards);
      _self.$router.push({
        name: "全文搜索",
        params: {
          map: map
        }
      });
    },
    showFile(indata){
      alert("通过id跳转到文件展示界面")
    }
  }
};
</script>
<style scoped>
.el-col {
  padding-right: 10px;
}
.el-card {
  /* padding-bottom: 30px; */
  margin-bottom: 10px;
}
.el-table {
  width: 100%;
}
.el-link {
  font-size: 16px;
}
.searchInput {
  display: inline;
}
.docType {
  display: inline;
  font-size: 14px;
}
.search {
  padding-right: 20%;
}
</style>