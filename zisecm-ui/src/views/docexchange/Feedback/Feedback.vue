<template>
  <DataLayout>
    <template v-slot:header>
      <el-form :inline="true">
        <el-button v-text="iedNum" v-show="textShow" type="text" @click="iedType"></el-button>
        <el-button v-text="icmNum" v-show="textShow" type="text" @click="icmType"></el-button>
        <el-button v-text="planNum" v-show="textShow" type="text" @click="planType"></el-button>
        <el-button v-text="dcNum" v-show="textShow" type="text" @click="dcType"></el-button>
        <el-button v-text="designNum" v-show="textShow" type="text" @click="docType"></el-button>
      </el-form>
      <el-form :inline="true" :model="filters">
        <el-form-item >
          <el-input style="width:200px" v-model="inputValueNum" placeholder="标题"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="FuzzySearch">查询</el-button>
        </el-form-item>
      </el-form>
    </template>
    <template v-slot:main="{layout}">
      <el-row>
        <el-col :span="24">
          <DataGrid
            ref="feedbackGrid"
            v-bind="tables.feedbackGrid"
            :tableHeight="layout.height/2-115"
            @rowclick="onDataGridRowClick"
            @selectchange="onSelectChange"
          ></DataGrid>
        </el-col>
      </el-row>
    </template>
  </DataLayout>
</template>
<script type="text/javascript">
import ShowProperty from "@/components/ShowProperty";
import DataGrid from "@/components/DataGrid";
import DataLayout from "@/components/ecm-data-layout";
export default {
  name: "FeedBackGrid",
  data() {
    return {
      tables: {
        feedbackGrid: {
          gridViewName: "FeedBackGrid",
          dataUrl: "/dc/getDocuments",
          condition: "",
          isshowOption: true,
          isshowCustom: true,
          isshowicon: false,
          isInitData: false,
          isShowMoreOption: false,
        },
      },

      dataReport: [],
      textShow: true,
      iedNum: "",
      icmNum: "",
      planNum: "",
      dcNum: "",
      designNum: "",
      inputValueNum:'',

    };
  },

  mounted(){
    this.loadStatistic()
  },

  methods: {
    loadStatistic(){
      let _self = this;
      _self.loading = true;
      var m = new Map();

      _self.iedNum = "IED"
      _self.icmNum = "ICM"
      _self.planNum = "计划"
      _self.dcNum = "文函"
      _self.designNum = "设计文件"

      m.set("iedType", _self.iedNum);
      m.set("icmType", _self.icmNum);
      m.set("planType", _self.planNum);
      m.set("dcNum", _self.dcNum);
      m.set("designType", _self.designNum);

      axios
        .post("/exchange/ques/FeedBack", JSON.stringify(m))
        .then(function(response) {
          _self.iedNum = "IED(" + response.data.iedst + ")";
          _self.icmNum = "ICM(" + response.data.icmst + ")";
          _self.planNum = "计划(" + response.data.planst + ")";
          _self.dcNum = "文函(" + response.data.dcst + ")";
          _self.designNum = "设计文件(" + response.data.designst + ")";
          _self.loading = false;
        }).catch(function(error) {
          console.log(error);
        });
    },

    iedType(){
      let _self = this;
      var k1 = "TYPE_NAME='IED' AND C_ITEM_STATUS IS NOT NULL";
      _self.tables.feedbackGrid.condition = k1;
      _self.$refs.feedbackGrid.condition=this.tables.feedbackGrid.condition;
      _self.$refs.feedbackGrid.loadGridData();
    },

    icmType(){
      let _self = this;
      var k2 = "TYPE_NAME='ICM' AND C_ITEM_STATUS IS NOT NULL";
      _self.tables.feedbackGrid.condition = k2;
      _self.$refs.feedbackGrid.condition=this.tables.feedbackGrid.condition;
      _self.$refs.feedbackGrid.loadGridData();
    },

    planType(){
      let _self = this;
      var k3 = "TYPE_NAME='计划' AND C_ITEM_STATUS IS NOT NULL";
      _self.tables.feedbackGrid.condition = k3;
      _self.$refs.feedbackGrid.condition=this.tables.feedbackGrid.condition;
      _self.$refs.feedbackGrid.loadGridData();
    },

    dcType(){
      let _self = this;
      var k4 = "TYPE_NAME='文函' AND C_ITEM_STATUS IS NOT NULL";
      _self.tables.feedbackGrid.condition = k4;
      _self.$refs.feedbackGrid.condition=this.tables.feedbackGrid.condition;
      _self.$refs.feedbackGrid.loadGridData();
    },

    docType(){
      let _self = this;
      var k5 = "TYPE_NAME='设计文件' AND C_ITEM_STATUS IS NOT NULL";
      _self.tables.feedbackGrid.condition = k5;
      _self.$refs.feedbackGrid.condition=this.tables.feedbackGrid.condition;
      _self.$refs.feedbackGrid.loadGridData();
    },

    FuzzySearch() {
      let _self = this
      console.log(_self.$refs.feedbackGrid.condition)
      var Fuzzyk = " AND TITLE LIKE '%" + _self.inputValueNum + "%'"
      _self.$refs.feedbackGrid.condition += Fuzzyk;
      _self.$refs.feedbackGrid.loadGridData();
    },
  },
  components: {
    ShowProperty: ShowProperty,
    DataGrid: DataGrid,
    DataLayout: DataLayout,
  },
};
</script>
<style scoped>
</style>