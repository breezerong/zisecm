<template>
  <el-container>
    <el-main>
      <el-row>
        <el-col :span="16">
          <el-card>
            <div slot="header" class="clearfix" style="padding-bottom:5px;">
              <span style="float: left;" class="ecmtitle">{{$t('route.userInfo')}}</span>
            </div>
            <div>
              <planTop v-if="isCNPE"></planTop>
              <subPlanTop v-if="isSub"></subPlanTop>
              <generalTop v-if="isCNPEGen"></generalTop>
              <subGeneralTop v-if="issubGen"></subGeneralTop>
            </div>
          </el-card>
          <el-card :body-style="{ height: '320px',width:'100%' }">
            <div slot="header" class="clearfix" style="padding-bottom:5px;">
              <span style="float: left;" class="ecmtitle">{{$t('application.projectInfo')}}</span>
            </div>
            
              <subIcmProject v-if="isSub"></subIcmProject>
              <icmProject v-if='isCNPE'></icmProject>       

          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card :body-style="{ height: '48px' }">
            <div slot="header" class="clearfix" style="padding-bottom:5px;">
              <span style="float: left;" class="ecmtitle">{{$t('application.userCenter')}}</span>
            </div>
            <el-col :span="12">
              <i style="padding-bottom:25px;font-size : 28px" class="el-icon-user"></i>
              <el-link
                :underline="false"
                @click="$router.push(jumpPath.userCenter)"
              >{{$t('application.myInformation')}}</el-link>
            </el-col>
            <el-col :span="12">
              <i style="padding-bottom:25px;font-size : 28px" class="el-icon-s-claim"></i>
              <el-link
                :underline="false"
                @click="$router.push('/user/userroleinfo')"
              >{{$t('application.myAuth')}}</el-link>
            </el-col>
          </el-card>
          <el-card :body-style="{ height: '400px' }">
            <div slot="header" class="clearfix" style="padding-bottom:5px;">
              <span style="float: left;" class="ecmtitle">{{$t('application.myNotice')}}</span>
              <el-link
                :underline="false"
                @click="routerJump('通知公告')"
                style="float: right; padding: 3px 0"
                type="primary"
              >{{$t('application.more')}}>></el-link>
            </div>
            <el-table
              v-loading="loadingNoticeData"
              :data="dataList.notiData"
              style="width:100%;"
              size="small"
              :show-header="false"
            >
              <el-table-column :show-overflow-tooltip="true" min-width="60%" label="标题">
                <el-link
                  slot-scope="scope"
                  type="primary"
                  @click="showFile(scope.row)"
                >{{(scope.row.NAME)}}</el-link>
              </el-table-column>
              <el-table-column align="right" width="120">
                <template slot-scope="scope">{{dateFormat(scope.row.CREATION_DATE)}}</template>
              </el-table-column>
            </el-table>
          </el-card>
          <!--
          <el-card :body-style="{ height: '310px' }">
            <div slot="header" class="clearfix" style="padding-bottom:5px;">
              <span style="float: left;" class="ecmtitle">管理文件</span>
              <el-link :underline="false" @click="routerJump('管理文件')" style="float: right; padding: 3px 0" type="primary">更多>></el-link>
            </div>
            <el-table
              v-loading="loadingReData"
              :data="dataList.regulationData"
              style="width:100%;"
              size="small"
              :show-header="false"
            >
              <el-table-column :show-overflow-tooltip="true" min-width="60%" label="标题">
                <el-link slot-scope="scope" type="primary" @click="showFile(scope.row)">{{(scope.row.NAME)}}</el-link>
              </el-table-column>
              <el-table-column align="right" width="120">
                <template slot-scope="scope">{{dateFormat(scope.row.CREATION_DATE)}}</template>
              </el-table-column>
            </el-table>
          </el-card>
          -->
          <!-- <el-card :body-style="{ height: '310px',width:'100%' }">
            <div slot="header" class="clearfix" style="padding-bottom:5px;">
              <span style="float: left;" class="ecmtitle">馆藏状态</span>
            </div>
            <div id="collectionChart" style="height: 100%;width: 100%;padding-bottom:20px;"></div>
          </el-card>-->
        </el-col>
      </el-row>
    </el-main>
  </el-container>
</template>
<script>
import ecmDataIcons from "@/components/ecm-data-icons/ecm-data-icons";
import planTop from "@/components/dashboard/plan/planTop";
import subPlanTop from "@/components/dashboard/plan/subPlanTop";
import docTop from "@/components/dashboard/doc/docTop";
import subDocTop from "@/components/dashboard/doc/subDocTop";
import subIcmTop from "@/components/dashboard/icm/subIcmTop";
import icmTop from "@/components/dashboard/icm/icmTop";
import icmProject from "@/components/dashboard/icm/icmProject";
import subIcmProject from "@/components/dashboard/icm/subIcmProject";
import subDocProject from "@/components/dashboard/doc/subDocProject";
import subPlanProject from "@/components/dashboard/plan/subPlanProject";
import planProject from "@/components/dashboard/plan/planProject";
import docProject from "@/components/dashboard/doc/docProject";
import generalProject from "@/components/dashboard/generalProject";
import generalTop from "@/components/dashboard/generalTop";
import subGeneralTop from "@/components/dashboard/subGeneralTop";
import subGeneralProject from "@/components/dashboard/subGeneralProject";

export default {
  components: {
    ecmDataIcons,
    planTop,
    planProject,
    subPlanProject,
    subPlanTop,
    docTop,
    subDocTop,
    subIcmTop,
    icmTop,
    icmProject,
    subIcmProject,
    subDocProject,
    docProject,
    generalProject,
    generalTop,
    subGeneralProject,
    subGeneralTop,
  },
  data() {
    return {
      isSubJK: false,
      isSubWK: false,
      isSubPlan: false,
      isCNPEJK: false,
      isCNPEWK: false,
      isCNPEPlan: false,
      isCNPE: false,
      isSub: false,
      issubGen: false,
      isCNPEGen: false,
      tempRoles: [],
      userRoles: [],

      projectData1: {
        color: "rgb(63, 161, 255)",
        span: 6,
        data: [
          {
            title: "计划作业",
            count: 125,
            color: "rgb(63, 161, 255)",
            icon: "el-icon-s-order",
            url: "/proj/planview",
          },
          {
            title: "IED",
            count: 876,
            color: "rgb(63, 161, 255)",
            icon: "el-icon-s-unfold",
            url: "/ied/releaseied",
          },
          {
            title: "ICM",
            count: 26871,
            color: "rgb(255, 0, 0)",
            icon: "el-icon-document",
            url: "/doc/donedoc",
          },
        ],
      },
      jumpPath: {
        search: "",
        todolist: "/workflow/todotask",
        notification: "/dc/folderviewer",
        userCenter: "/user/userinfo",
      },
      imagesBox: [],
      collectionChartData: {
        xAxisData: [],
        yAxisData: [],
      },
      dataList: {
        todoData: [],
        newdocData: [],
        notiData: [],
        carouselData: [],
        regulationData: [],
      },
      inputkey: "",
      scroll: true,
      loading: false,
      loadingTodoData: false,
      loadingNewDocData: false,
      loadingNoticeData: false,
      loadingReData: false,
      checkAll: true,
      isIndeterminate: false,
      checkedCards: [],
      propertyOnly: false,
      cards: [],
      cardsLabel: [],
      keywords: [],
      totalCount: 0,
      groupData: [],
      collectionChart: Object,
      dialogVisible: false,
    };
  },
  created() {
    let _self = this;
    //_self.getToDoList();
    //_self.loadCards();
    _self.getNewsList();
    //_self.getDocument();
    //_self.getCarousel();
    //_self.getRegulation();
    _self.getRoles();
  },
  methods: {
    getRoles() {
      //用户类型判断
      this.tempRoles = this.currentUser().roles;
      for (let i = 0; i < this.tempRoles.length; i++) {
        if (
          this.tempRoles[i] == "分包商文控人员" ||
          this.tempRoles[i] == "CNPE_文控人员" ||
          this.tempRoles[i] == "CNPE_计划人员" ||
          this.tempRoles[i] == "CNPE_接口人员" ||
          this.tempRoles[i] == "分包商接口人员" ||
          this.tempRoles[i] == "分包商计划人员" ||
          this.tempRoles[i] == "CNPE_设总"  ||
          this.tempRoles[i] == "分包商设总"
  
        ) {
          this.userRoles[i] = this.tempRoles[i];
        }
      }
      for (let i = 0; i < this.userRoles.length; i++) {
        if (this.userRoles[i] == "分包商文控人员") {
          this.isSubWK = true;
          this.isSub = true;
        }
        if (this.userRoles[i] == "分包商接口人员") {
          this.isSubJK = true;
          this.isSub = true;
          //console.log("SubJK:"+this.isSubJK)
        }
        if (this.userRoles[i] == "分包商计划人员") {
          this.isSubPlan = true;
          this.isSub = true;
          //console.log("SUBPLAN:"+this.isSubPlan)
        }
        if (this.userRoles[i] == "分包商设总") {
          this.issubGen = true;
          this.isSub=true
          //console.log("SUBPLAN:"+this.isSubPlan)
        }
        if (this.userRoles[i] == "CNPE_文控人员") {
          this.isCNPEWK = true;
          this.isCNPE = true;
          //console.log("CNPEWK:"+this.isCNPEWK)
        } else if (this.userRoles[i] == "CNPE_计划人员") {
          this.isCNPEPlan = true;
          this.isCNPE = true;
          //console.log("CNPEPL:"+this.isCNPEPlan)
        } else if (this.userRoles[i] == "CNPE_接口人员") {
          this.isCNPEJK = true;
          this.isCNPE = true;
          //console.log("CNPEJK:"+this.isCNPEJK)
        } else if (this.userRoles[i] == "CNPE_设总") {
          this.isCNPEGen = true;
          this.isCNPE = true;
          //console.log("CNPEJK:"+this.isCNPEJK)
        }
      }
    },

    //获取待办任务列表，最多五条
    getToDoList() {
      let _self = this;
      var m = new Map();
      _self.loadingTodoData = true;
      m.set("condition", "");
      m.set("pageSize", 5);
      m.set("pageIndex", 0);
      m.set("userId", sessionStorage.getItem("access-userName"));
      axios
        .post("/workflow/todoTask", JSON.stringify(m))
        .then(function (response) {
          _self.dataList.todoData = response.data.data;
          _self.totalCount = response.data.totalCount;
          _self.loadingTodoData = false;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    //获取通知公告栏信息列表
    getNewsList() {
      let _self = this;
      _self.loadingNoticeData = true;
      var m = new Map();
      m.set("gridName", "NewsGrid");
      m.set("folderId", "");
      m.set("condition", " type_name='通知公告' ");
      m.set("pageSize", 5);
      m.set("pageIndex", 0);
      m.set("orderBy", " CREATION_DATE DESC");
      axios
        .post("/dc/getDocuments", JSON.stringify(m))
        .then(function (response) {
          _self.dataList.notiData = response.data.data;
          _self.loadingNoticeData = false;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    //获取最新的5条新建文档
    getDocument() {
      let _self = this;
      _self.loadingNewDocData = true;
      var m = new Map();
      m.set("pageSize", 6);
      m.set("pageIndex", 0);
      m.set("orderBy", " C_DOC_DATE DESC");
      axios
        .post("/dc/getNewDocuments", JSON.stringify(m))
        .then(function (response) {
          _self.dataList.newdocData = response.data.data;
          _self.loadingNewDocData = false;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    //获取法律法规数据
    getRegulation() {
      let _self = this;
      var m = new Map();
      _self.loadingReData = true;
      m.set("gridName", "GeneralDocGrid");
      m.set("folderPath", "/表单/管理文件");
      m.set("condition", "");
      m.set("pageSize", 8);
      m.set("pageIndex", 0);
      m.set("orderBy", " CREATION_DATE DESC");
      axios
        .post("/dc/getDocsByFolderPathName", JSON.stringify(m))
        .then(function (response) {
          _self.dataList.regulationData = response.data.data;
          _self.loadingReData = false;
        })
        .catch(function (error) {
          console.log(error);
          _self.loadingReData = false;
        });
    },
    //获取轮播图数据
    getCarousel() {
      let _self = this;
      var m = new Map();
      var imgArr = [];
      m.set("gridName", "GeneralDocGrid");
      m.set("folderPath", "/表单/轮播图");
      m.set("pageSize", 5);
      m.set("condition", "");
      m.set("pageIndex", 0);
      m.set("orderBy", " CREATION_DATE DESC");
      axios
        .post("/dc/getDocsByFolderPathName", JSON.stringify(m))
        .then(function (response) {
          _self.dataList.carouselData = response.data.data;
          // var mycars=new Array(response.data.data.size)
          _self.dataList.carouselData.forEach(function (val, index, arr) {
            imgArr[index] =
              _self.axios.defaults.baseURL +
              "/dc/getContent?id=" +
              val.ID +
              "&token=" +
              sessionStorage.getItem("access-token");
          });
          _self.imagesBox = imgArr;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    //加载全文搜索的勾选项
    loadCards() {
      let _self = this;      
      axios
        .post("/admin/getArchivesFolder", 0)
        .then(function (response) {
          _self.cards = response.data.data;
          for (let i = 0; i < _self.cards.length; i++) {
            _self.cardsLabel[i] = _self.cards[i].name;
          }
        })
        .catch(function (error) {
          console.log(error);
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
      // console.log(_self.checkedCards)
      _self.$router.push({
        name: "全文搜索",
        params: {
          map: map,
        },
      });
    },
    showFile(indata) {
      let condition = indata.ID;
      let href = this.$router.resolve({
        path: "/viewdoc",
        query: {
          id: condition,
          //token: sessionStorage.getItem('access-token')
        },
      });
      //console.log(href);
      window.open(href.href, "_blank");
    },
    getMyGroup() {
      this.dialogVisible = true;
      let _self = this;
      var userName = sessionStorage.getItem("access-userName");
      console.log(sessionStorage);
      axios
        .post("/user/getGroupByUserName", userName)
        .then(function (response) {
          _self.groupData = response.data.data;
        });
    },
    querySearch(queryString, cb) {
      let _self = this;
      _self.loading = true;
      axios
        .post("/search/getSuggestion", JSON.stringify(_self.inputkey))
        .then(function (response) {
          _self.keywords = response.data.data;
          if (_self.keywords) {
            var list = [{}];
            var i;
            for (i = 0; i < _self.keywords.length; i++) {
              var item = new Object();
              item.value = _self.keywords[i];
              list[i] = item;
            }
            _self.keywords = list;
          }
          cb(_self.keywords);
          //console.log(JSON.stringify(_self.taskList));
          _self.loading = false;
        })
        .catch(function (error) {
          console.log(error);
          _self.loading = false;
        });
    },
    openTask(taskId) {
      let _self = this;
      _self.$router.push({
        path: _self.jumpPath.todolist,
        query: {
          openTaskFromMainPage: 1,
          taskId: taskId,
        },
      });
    },
    routerJump(folderName) {
      let _self = this;
      _self.$router.push({
        path: "/dc/folderviewer",
        query: { folderName: folderName },
      });
    },
  },
};
</script>
<style scoped>
.el-col {
  padding-right: 5px;
  padding-left: 5px;
  margin-bottom: 5px !important;
}
.el-card {
  /* padding-bottom: 30px; */
  margin-bottom: 5px !important;
}
.el-card__header {
  padding: 8px 10px !important;
  border-bottom: 1px solid #ebeef5;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
}
.el-table {
  width: 100%;
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

<style>
.el-card__header {
  background-color: rgb(235, 235, 235);
}
.el-carousel__item {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}
.carousel-image {
  max-width: 100%;
  max-height: 100%;
}
</style>