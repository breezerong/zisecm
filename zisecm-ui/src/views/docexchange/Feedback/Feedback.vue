<template>
  <DataLayout>
    <el-dialog :title="dialogName" :visible.sync="propertyVisible" width="80%">
      <ShowProperty ref="ShowProperty" width="100%" itemId="1" v-bind:typeName="dialogtypeName"></ShowProperty>
      <div slot="footer" class="dialog-footer">
        <el-button @click="saveItem">{{$t('application.save')}}</el-button>
        <el-button @click="propertyVisible = false">{{$t('application.cancel')}}</el-button>
      </div>
    </el-dialog>
    <template v-slot:header>
      <el-form :inline="true">
        <el-button type="text" @click="iedType">{{$t('application.subIED')}}({{options.iedNum.count}})</el-button>
        <el-button type="text" @click="icmType">{{$t('application.subICM')}}({{options.icmNum.count}})</el-button>
        <el-button type="text" @click="planType">{{$t('application.subPlan')}}({{options.planNum.count}})</el-button>
        <el-button type="text" @click="dcType">{{$t('application.subDC')}}({{options.dcNum.count}})</el-button>
        <el-button type="text" @click="docType">{{$t('application.subDesign')}}({{options.designNum.count}})</el-button>
      </el-form>
      <el-form :inline="true">
        <el-form-item>
          <DataSelect v-model="toCompany" defaultIsNull :includeAll="true" @onSelectChange='onSelectChange'
            queryName="收发文公司"  dataValueField="NAME"  dataTextField="NAME" :allLabel="$t('application.all')"></DataSelect>
        </el-form-item>
        <el-form-item>
          <el-input style="width:200px" v-model="inputValueNum" :placeholder="$t('message.inputQuestionLimit')"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="FuzzySearch">{{$t('application.clientPermission1')}}</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="beforeCreateDocItem('问题沟通')">{{$t('application.new')}}</el-button>
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
import DataSelect from '@/components/ecm-data-select';

export default {
  name: "FeedBackGrid",
  data() {
    return {
      generalCondition:"",
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
      toCompany: "",
      dataReport: [],
      typeName: "问题沟通",
      dialogName: "问题沟通",
      dialogtypeName: "",
      propertyVisible: false,

      options:{
        iedNum:{count:""},
        icmNum:{count:""},
        planNum:{count:""},
        dcNum:{count:""},
        designNum:{count:""}
      },
      inputValueNum: "",

      formLabelWidth: "120px",
    };
  },

  mounted() {
    this.language = localStorage.getItem("localeLanguage") || "zh-cn";
    this.loadStatistic();
  },

  methods: {
    //下拉菜单
        onSelectChange(val){
            let _self = this
            this.toCompany = val
            this.FuzzySearch()
        },
    loadStatistic() {
      let _self = this;
      _self.loading = true;
      var m = new Map();

      let iedTy = "IED";
      let icmTy = "ICM";
      let planTy = "计划";
      let dcNumTy = "文函";
      let designTy = "设计文件";

      m.set("iedType", iedTy);
      m.set("icmType", icmTy);
      m.set("planType", planTy);
      m.set("dcNum", dcNumTy);
      m.set("designType", designTy);

      axios
        .post("/exchange/ques/FeedBack", JSON.stringify(m))
        .then(function (response) {
          _self.options.iedNum.count = response.data.iedst;
          _self.options.icmNum.count = response.data.icmst;
          _self.options.planNum.count = response.data.planst;
          _self.options.dcNum.count = response.data.dcst;
          _self.options.designNum.count = response.data.designst;
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
              _self.$refs.feedbackGrid.loadGridData();
              _self.loadStatistic();
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
    },

    showItemContent(indata) {
      let currentRow = indata.data.row;
      let condition = currentRow.ID
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
      var k1 = "SUB_TYPE='IED' AND C_ITEM_STATUS = '新建'";
      _self.tables.feedbackGrid.condition = k1;
      _self.$refs.feedbackGrid.condition = this.tables.feedbackGrid.condition;
      _self.$refs.feedbackGrid.loadGridData();
    },

    icmType() {
      let _self = this;
      var k2 = "SUB_TYPE='ICM' AND C_ITEM_STATUS = '新建'";
      _self.tables.feedbackGrid.condition = k2;
      _self.$refs.feedbackGrid.condition = this.tables.feedbackGrid.condition;
      _self.$refs.feedbackGrid.loadGridData();
    },

    planType() {
      let _self = this;
      var k3 = "SUB_TYPE='计划' AND C_ITEM_STATUS = '新建'";
      _self.tables.feedbackGrid.condition = k3;
      _self.$refs.feedbackGrid.condition = this.tables.feedbackGrid.condition;
      _self.$refs.feedbackGrid.loadGridData();
    },

    dcType() {
      let _self = this;
      var k4 = "SUB_TYPE='文函' AND C_ITEM_STATUS = '新建'";
      _self.tables.feedbackGrid.condition = k4;
      _self.$refs.feedbackGrid.condition = this.tables.feedbackGrid.condition;
      _self.$refs.feedbackGrid.loadGridData();
    },

    docType() {
      let _self = this;
      var k5 = "SUB_TYPE='设计文件' AND C_ITEM_STATUS = '新建'";
      _self.tables.feedbackGrid.condition = k5;
      _self.$refs.feedbackGrid.condition = this.tables.feedbackGrid.condition;
      _self.$refs.feedbackGrid.loadGridData();
    },

    FuzzySearch() {
      let _self = this;
      //console.log(_self.$refs.feedbackGrid.condition);
      var Fuzzyk = " TITLE LIKE '%" + _self.inputValueNum + "%'";
      if( _self.toCompany != ""){
        Fuzzyk += " AND C_TO='"+ _self.toCompany+"'";
      }
      _self.$refs.feedbackGrid.condition = Fuzzyk;
      _self.$refs.feedbackGrid.loadGridData();
    },
  },

  watch:{
    '$store.state.app.language':function(){
    }
  },

  components: {
    ShowProperty: ShowProperty,
    DataGrid: DataGrid,
    DataLayout: DataLayout,
    DataSelect:DataSelect
  },
};
</script>
<style scoped>
</style>