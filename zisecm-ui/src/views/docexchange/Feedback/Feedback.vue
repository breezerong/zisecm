<template>
  <DataLayout>
    <el-dialog :title="dialogName" :visible.sync="propertyVisible" width="80%">
      <ShowProperty ref="ShowProperty" width="100%" itemid="1" v-bind:typeName="dialogtypeName"></ShowProperty>
      <div slot="footer" class="dialog-footer">
        <el-button @click="saveItem">{{$t('application.save')}}</el-button>
        <el-button @click="propertyVisible = false">{{$t('application.cancel')}}</el-button>
      </div>
    </el-dialog>
    <template v-slot:header>
      <el-form :inline="true">
        <el-button v-text="iedNum" v-show="textShow" type="text" @click="iedType"></el-button>
        <el-button v-text="icmNum" v-show="textShow" type="text" @click="icmType"></el-button>
        <el-button v-text="planNum" v-show="textShow" type="text" @click="planType"></el-button>
        <el-button v-text="dcNum" v-show="textShow" type="text" @click="dcType"></el-button>
        <el-button v-text="designNum" v-show="textShow" type="text" @click="docType"></el-button>
      </el-form>
      <el-form :inline="true">
        <el-form-item>
          <el-input style="width:200px" v-model="inputValueNum" placeholder="标题"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="FuzzySearch">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="beforeCreateDocItem('问题沟通')">新建</el-button>
        </el-form-item>
      </el-form>
    </template>
    <template v-slot:main="{layout}">
      <el-row>
        <el-col :span="24">
          <DataGrid
            ref="feedbackGrid"
            v-bind="tables.feedbackGrid"
            :tableHeight="layout.height-210"
          >
            <template slot="optionButton" slot-scope="scope">
              <el-button icon="el-icon-reading" @click.native="showItemContent(scope)">查看</el-button>
            </template>
          </DataGrid>
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
          condition: "TYPE_NAME = '问题沟通'",
          isshowOption: true,
          isshowCustom: true,
          isshowicon: false,
          isInitData: true,
          isShowMoreOption: true,
        },
      },

      dataReport: [],
      typeName: "问题沟通",
      dialogName: "问题沟通",
      dialogtypeName: "",
      propertyVisible: false,

      form: {
        options: [{}],
      },

      textShow: true,
      iedNum: "",
      icmNum: "",
      planNum: "",
      dcNum: "",
      designNum: "",
      inputValueNum: "",

      formLabelWidth: "120px",
    };
  },

  mounted() {
    this.loadStatistic();
  },

  methods: {
    loadStatistic() {
      let _self = this;
      _self.loading = true;
      var m = new Map();

      _self.iedNum = "IED";
      _self.icmNum = "ICM";
      _self.planNum = "计划";
      _self.dcNum = "文函";
      _self.designNum = "设计文件";

      m.set("iedType", _self.iedNum);
      m.set("icmType", _self.icmNum);
      m.set("planType", _self.planNum);
      m.set("dcNum", _self.dcNum);
      m.set("designType", _self.designNum);

      axios
        .post("/exchange/ques/FeedBack", JSON.stringify(m))
        .then(function (response) {
          _self.iedNum = "IED(" + response.data.iedst + ")";
          _self.icmNum = "ICM(" + response.data.icmst + ")";
          _self.planNum = "计划(" + response.data.planst + ")";
          _self.dcNum = "文函(" + response.data.dcst + ")";
          _self.designNum = "设计文件(" + response.data.designst + ")";
          _self.loading = false;
        })
        .catch(function (error) {
          console.log(error);
        });
    },

    clickNewItem() {
      let _self = this;
      _self.dialogFormVisible = true;
    },

    beforeCreateDocItem(typeName) {
      let _self = this;
      _self.propertyVisible = true;
      setTimeout(() => {
        if (_self.$refs.ShowProperty) {
          _self.$refs.ShowProperty.myItemId = "";
          _self.dialogName = typeName;
          _self.$refs.ShowProperty.myTypeName = typeName;
          _self.dialogtypeName = typeName;
          _self.$refs.ShowProperty.loadFormInfo();
        }
      }, 10);
    },

    saveItem() {
      let _self = this;
      if (!this.$refs.ShowProperty.validFormValue()) {
        return;
      }
      var m = new Map();
      var c;
      for (c in _self.$refs.ShowProperty.dataList) {
        let dataRows = _self.$refs.ShowProperty.dataList[c].ecmFormItems;
        var i;
        for (i in dataRows) {
          if (dataRows[i].attrName && dataRows[i].attrName != "") {
            if (
              dataRows[i].attrName != "FOLDER_ID" &&
              dataRows[i].attrName != "ID"
            ) {
              var val = dataRows[i].defaultValue;
              if (val && dataRows[i].isRepeat) {
                var temp = "";
                for (let j = 0, len = val.length; j < len; j++) {
                  temp = temp + val[j] + ";";
                }
                temp = temp.substring(0, temp.length - 1);
                val = temp;
              }
              m.set(dataRows[i].attrName, val);
            }
          }
        }
      }
      if (_self.$refs.ShowProperty.myItemId != "") {
        m.set("ID", _self.$refs.ShowProperty.myItemId);
      }
      m.set("TYPE_NAME", "问题沟通");
      let formdata = new FormData();
      formdata.append("metaData", JSON.stringify(m));
      if (_self.$refs.ShowProperty.myItemId == "") {
        axios
          .post("/exchange/Ques/newQues", formdata, {
            "Content-Type": "multipart/form-data",
          })
          .then(function (response) {
            let code = response.data.code;
            if (code == 1) {
              _self.$message({
                showClose: true,
                message: _self.$t("message.newSuccess"),
                duration: 2000,
                type: "success",
              });
              _self.propertyVisible = false;
              // _self.loadTransferGridData();
              _self.$refs.mainDataGrid.loadGridData();
            } else {
              _self.$message({
                showClose: true,
                message: _self.$t("message.newFailured"),
                duration: 2000,
                type: "warning",
              });
            }
          })
          .catch(function (error) {
            _self.$message(_self.$t("message.newFailured"));
            console.log(error);
          });
      }
      _self.$refs.feedbackGrid.loadGridData();
    },

    showItemContent(indata) {
      console.log(indata)
      let condition = indata.ID
      console.log(indata.ID)
      let href = this.$router.resolve({
        path: "/FeedBackDetail",
        query: {
          id: condition,
        },
      });
      window.open(href.href, "_blank");
    },

    iedType() {
      let _self = this;
      var k1 = "SUB_TYPE='IED' AND C_ITEM_STATUS IS NOT NULL";
      _self.tables.feedbackGrid.condition = k1;
      _self.$refs.feedbackGrid.condition = this.tables.feedbackGrid.condition;
      _self.$refs.feedbackGrid.loadGridData();
    },

    icmType() {
      let _self = this;
      var k2 = "SUB_TYPE='ICM' AND C_ITEM_STATUS IS NOT NULL";
      _self.tables.feedbackGrid.condition = k2;
      _self.$refs.feedbackGrid.condition = this.tables.feedbackGrid.condition;
      _self.$refs.feedbackGrid.loadGridData();
    },

    planType() {
      let _self = this;
      var k3 = "SUB_TYPE='计划' AND C_ITEM_STATUS IS NOT NULL";
      _self.tables.feedbackGrid.condition = k3;
      _self.$refs.feedbackGrid.condition = this.tables.feedbackGrid.condition;
      _self.$refs.feedbackGrid.loadGridData();
    },

    dcType() {
      let _self = this;
      var k4 = "SUB_TYPE='文函' AND C_ITEM_STATUS IS NOT NULL";
      _self.tables.feedbackGrid.condition = k4;
      _self.$refs.feedbackGrid.condition = this.tables.feedbackGrid.condition;
      _self.$refs.feedbackGrid.loadGridData();
    },

    docType() {
      let _self = this;
      var k5 = "SUB_TYPE='设计文件' AND C_ITEM_STATUS IS NOT NULL";
      _self.tables.feedbackGrid.condition = k5;
      _self.$refs.feedbackGrid.condition = this.tables.feedbackGrid.condition;
      _self.$refs.feedbackGrid.loadGridData();
    },

    FuzzySearch() {
      let _self = this;
      console.log(_self.$refs.feedbackGrid.condition);
      var Fuzzyk = " AND TITLE LIKE '%" + _self.inputValueNum + "%'";
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