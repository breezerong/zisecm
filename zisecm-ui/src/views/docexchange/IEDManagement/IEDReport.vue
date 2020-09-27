<template>
  <DataLayout>
    <template v-slot:header style="height: auto"></template>
    <template v-slot:main="{layout}">
      <el-tabs v-model="activeName">
        <el-tab-pane :label="$t('route.extendied')" name="first">
          <el-container>
            <el-header style="height:auto;">
              <el-form :inline="true">
                <el-form-item>
                  <DataSelect
                    v-model="overdueIED"
                    data-url="/exchange/project/myproject"
                    data-value-field="name"
                    data-text-field="name"
                    includeAll
                  ></DataSelect>
                </el-form-item>
                <el-form-item>
                  <el-date-picker
                    v-model="startDate"
                    type="date"
                    :placeholder="$t('application.startDate')"
                    value-format="yyyy-MM-dd"
                  ></el-date-picker>
                </el-form-item>
                <el-form-item>
                  <el-date-picker
                    v-model="endDate"
                    type="date"
                    align="right"
                    :placeholder="$t('application.endDate')"
                    value-format="yyyy-MM-dd"
                  ></el-date-picker>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="search1()">{{$t('application.SearchData')}}</el-button>
                </el-form-item>
                <el-form-item>
                  <el-button
                    type="primary"
                    @click.native="exportDataOverdue"
                  >{{$t('application.ExportExcel')}}</el-button>
                </el-form-item>
              </el-form>
            </el-header>
            <el-main>
              <el-row>
                <el-col :span="24">
                  <DataGrid
                    ref="mainDataGrid1"
                    data-url="/dc/getDocuments"
                    :isShowMoreOption="false"
                    :isshowOption="true"
                    :isshowCustom="false"
                    :isshowicon="false"
                    gridViewName="IEDReportGrid"
                    condition="TYPE_NAME='' and C_PROJECT_NAME = '@project'"
                    :tableHeight="layout.height-210"
                  ></DataGrid>
                </el-col>
              </el-row>
            </el-main>
          </el-container>
        </el-tab-pane>
        <el-tab-pane :label="$t('route.unfied')" name="second">
          <el-container>
            <el-header style="height:auto;">
              <el-form :inline="true">
                <el-form-item>
                  <DataSelect
                    v-model="uncompletedIED"
                    data-url="/exchange/project/myproject"
                    data-value-field="name"
                    data-text-field="name"
                    includeAll
                  ></DataSelect>
                </el-form-item>
                <el-form-item>
                  <el-date-picker
                    v-model="startDate"
                    type="date"
                    :placeholder="$t('application.startDate')"
                    value-format="yyyy-MM-dd"
                  ></el-date-picker>
                </el-form-item>
                <el-form-item>
                  <el-date-picker
                    v-model="endDate"
                    type="date"
                    align="right"
                    :placeholder="$t('application.endDate')"
                    value-format="yyyy-MM-dd"
                  ></el-date-picker>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="search2()">{{$t('application.SearchData')}}</el-button>
                </el-form-item>
                <el-form-item>
                  <el-button
                    type="primary"
                    @click.native="exportDataunCompleted"
                  >{{$t('application.ExportExcel')}}</el-button>
                </el-form-item>
              </el-form>
            </el-header>
            <el-main>
              <el-row>
                <el-col :span="24">
                  <DataGrid
                    ref="mainDataGrid2"
                    data-url="/dc/getDocuments"
                    :isShowMoreOption="false"
                    :isshowOption="true"
                    :isshowCustom="false"
                    :isshowicon="false"
                    gridViewName="IEDReportGrid"
                    condition="TYPE_NAME='' and C_PROJECT_NAME = '@project'"
                    :tableHeight="layout.height-210"
                  ></DataGrid>
                </el-col>
              </el-row>
            </el-main>
          </el-container>
        </el-tab-pane>
        <el-tab-pane :label="$t('route.compied')" name="third">
          <el-container>
            <el-header style="height:auto;">
              <el-form :inline="true">
                <el-form-item>
                  <DataSelect
                    v-model="completedIED"
                    data-url="/exchange/project/myproject"
                    data-value-field="name"
                    data-text-field="name"
                    includeAll
                  ></DataSelect>
                </el-form-item>
                <el-form-item>
                  <el-date-picker
                    v-model="startDate"
                    type="date"
                    :placeholder="$t('application.startDate')"
                    value-format="yyyy-MM-dd"
                  ></el-date-picker>
                </el-form-item>
                <el-form-item>
                  <el-date-picker
                    v-model="endDate"
                    type="date"
                    align="right"
                    :placeholder="$t('application.endDate')"
                    value-format="yyyy-MM-dd"
                  ></el-date-picker>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="search3()">{{$t('application.SearchData')}}</el-button>
                </el-form-item>
                <el-form-item>
                  <el-button
                    type="primary"
                    @click.native="exportDataCompleted"
                  >{{$t('application.ExportExcel')}}</el-button>
                </el-form-item>
              </el-form>
            </el-header>
            <el-main>
              <el-row>
                <el-col :span="24">
                  <DataGrid
                    ref="mainDataGrid3"
                    data-url="/dc/getDocuments"
                    :isShowMoreOption="false"
                    :isshowOption="true"
                    :isshowCustom="false"
                    :isshowicon="false"
                    gridViewName="IEDReportGrid"
                    condition="TYPE_NAME='' and C_PROJECT_NAME = '@project'"
                    :tableHeight="layout.height - 210"
                  ></DataGrid>
                </el-col>
              </el-row>
            </el-main>
          </el-container>
        </el-tab-pane>
        <el-tab-pane :label="$t('route.comstaied')" name="forth">
          <el-container>
            <el-header style="height:auto;">
              <el-form :inline="true">
                <el-form-item>
                  <DataSelect
                    v-model="iedPlanStatistic"
                    data-url="/exchange/project/myproject"
                    data-value-field="name"
                    data-text-field="name"
                    includeAll
                  ></DataSelect>
                </el-form-item>
                <el-form-item>
                  <el-date-picker
                    v-model="startDate"
                    type="date"
                    :placeholder="$t('application.startDate')"
                    value-format="yyyy-MM-dd"
                  ></el-date-picker>
                </el-form-item>
                <el-form-item>
                  <el-date-picker
                    v-model="endDate"
                    type="date"
                    align="right"
                    :placeholder="$t('application.endDate')"
                    value-format="yyyy-MM-dd"
                  ></el-date-picker>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="handleReport()">{{$t('application.SearchData')}}</el-button>
                </el-form-item>
                <el-form-item>
                  <el-button
                    type="primary"
                    @click.native="exportDataStatistic"
                  >{{$t('application.ExportExcel')}}</el-button>
                </el-form-item>
              </el-form>
            </el-header>
            <el-main>
              <el-table :data="reportData" style="width: 100%;" border>
                <el-table-column type="index" width="60"></el-table-column>
                <el-table-column prop="projectName" :label="$t('application.projectName')" sortable width="220"></el-table-column>
                <el-table-column prop="iedCount" :label="$t('application.Plannum')" sortable width="160"></el-table-column>
                <el-table-column prop="completedCount" :label="$t('application.Completenum')" sortable width="160"></el-table-column>
                <el-table-column
                  prop="completedPercent"
                  :label="$t('application.perComplete')"
                  sortable
                  width="160"
                  :formatter="percentFormatter"
                ></el-table-column>
              </el-table>
            </el-main>
          </el-container>
        </el-tab-pane>
      </el-tabs>
    </template>
  </DataLayout>
</template>
<script type="text/javascript">
import ShowProperty from "@/components/ShowProperty";
import DataGrid from "@/components/DataGrid";
import DataSelect from "@/components/ecm-data-select";
import DataLayout from "@/components/ecm-data-layout";
import ExcelUtil from "@/utils/excel.js";
export default {
  name: "IEDReport",
  data() {
    return {
      tables: {
        main: {
          gridName: "IEDReportGrid",
          datalist: [],
          height: "",
        },
      },

      activeName: "first",
      value: "",
      uncompletedIED: "",
      completedIED: "",
      overdueIED: "",
      iedPlanStatistic: "",
      ct_month: null,
      reportData: [],
      Subcontractors: "",
      selectedItems: [],
      selectedItemId: "",
      startDate: "",
      endDate: "",
      language: "",
    };
  },
  mounted() {
    this.language = localStorage.getItem("localeLanguage") || "zh-cn";
  },
  methods: {
    onLayoutResize(size) {
      console.log(size);
      this.tables.main.height = size - 180;
    },

    percentFormatter(row, column) {
      let p = row.completedPercent;
      if (p > 0.0) {
        return Math.round(p * 10000) / 100 + "%";
      }
      if (p == 0.0) {
        return p + "%";
      }
      return "";
    },

    search1() {
      let _self = this;
      this.ct_month = new Date();

      this.ct_month.setTime(this.ct_month.getTime());

      var now = this.ct_month;

      var year = now.getFullYear(); //年
      var month = now.getMonth() + 1; //月
      var day = now.getDate(); //日

      var clock = year + "-";

      if (month < 10) clock += "0";

      clock += month + "-";

      if (day < 10) clock += "0";

      clock += day + " ";

      var k1 =
        "TYPE_NAME='IED' AND C_ITEM4_DATE < '" +
        clock +
        "'" +
        " AND C_ITEM_STATUS2 IS NULL AND C_ITEM_DATE IS NOT NULL AND IS_CURRENT=1 AND C_IS_RELEASED=1";

      if (_self.overdueIED != undefined && _self.overdueIED != "所有项目") {
        k1 += " AND C_PROJECT_NAME in (" + _self.overdueIED + ")";
      }

      if (_self.startDate != "" && _self.endDate != "") {
        k1 += "";
      }

      if (
        _self.startDate != undefined &&
        _self.endDate != undefined &&
        _self.startDate != null &&
        _self.endDate != null &&
        _self.startDate.length > 0 &&
        _self.endDate.length > 0
      ) {
        k1 +=
          " AND (C_ITEM_DATE BETWEEN '" +
          _self.startDate +
          "'" +
          " AND '" +
          _self.endDate +
          "'" +
          ")";
      }

      if (_self.startDate == undefined && _self.endDate != undefined) {
        k1 += " AND (C_ITEM_DATE < '" + _self.endDate + "'" + ")";
      }

      if (_self.startDate != undefined && _self.endDate == undefined) {
        k1 += " AND (C_ITEM_DATE > '" + _self.startDate + "'" + ")";
      }

      let user = this.currentUser();
      if (user.userType == 2 && user.company != null) {
        k1 += " AND C_COMPANY='" + user.company + "'";
      }

      console.log(k1);
      _self.$refs.mainDataGrid1.condition = k1;
      _self.$refs.mainDataGrid1.loadGridData();
    },

    search2() {
      let _self = this;

      var k2 =
        "TYPE_NAME='IED' AND C_ITEM_STATUS2 IS NULL AND C_ITEM_DATE IS NOT NULL AND IS_CURRENT=1 AND C_IS_RELEASED=1";

      if (this.uncompletedIED != undefined && this.uncompletedIED.length > 0) {
        k2 += " AND C_PROJECT_NAME in (" + _self.uncompletedIED + ")";
      }

      if (_self.startDate != "" && _self.endDate != "") {
        k2 += "";
      }

      if (
        _self.startDate != undefined &&
        _self.endDate != undefined &&
        _self.startDate != null &&
        _self.endDate != null &&
        _self.startDate.length > 0 &&
        _self.endDate.length > 0
      ) {
        k2 +=
          " AND (C_ITEM_DATE BETWEEN '" +
          _self.startDate +
          "'" +
          " AND '" +
          _self.endDate +
          "'" +
          ")";
      }

      if (_self.startDate == undefined && _self.endDate != undefined) {
        k2 += " AND (C_ITEM_DATE < '" + _self.endDate + "'" + ")";
      }

      if (_self.startDate != undefined && _self.endDate == undefined) {
        k2 += " AND (C_ITEM_DATE > '" + _self.startDate + "'" + ")";
      }

      let user = this.currentUser();
      if (user.userType == 2 && user.company != null) {
        k2 += " AND C_COMPANY='" + user.company + "'";
      }

      console.log(k2);
      _self.$refs.mainDataGrid2.condition = k2;
      _self.$refs.mainDataGrid2.loadGridData();
    },

    search3() {
      let _self = this;

      var k3 =
        "TYPE_NAME='IED' AND C_ITEM_STATUS2 = 'Y' AND C_ITEM_DATE IS NOT NULL AND IS_CURRENT=1 AND C_IS_RELEASED=1";

      if (_self.startDate != "" && _self.endDate != "") {
        k3 += "";
      }

      if (
        _self.startDate != undefined &&
        _self.endDate != undefined &&
        _self.startDate != null &&
        _self.endDate != null &&
        _self.startDate.length > 0 &&
        _self.endDate.length > 0
      ) {
        k3 +=
          " AND (C_ITEM_DATE BETWEEN '" +
          _self.startDate +
          "'" +
          " AND '" +
          _self.endDate +
          "'" +
          ")";
      }

      if (_self.startDate == undefined && _self.endDate != undefined) {
        k3 += " AND (C_ITEM_DATE < '" + _self.endDate + "'" + ")";
      }

      if (_self.startDate != undefined && _self.endDate == undefined) {
        k3 += " AND (C_ITEM_DATE > '" + _self.startDate + "'" + ")";
      }

      if (this.completedIED != undefined && this.completedIED.length > 0) {
        k3 += " AND C_PROJECT_NAME in (" + _self.completedIED + ")";
      }

      let user = this.currentUser();
      if (user.userType == 2 && user.company != null) {
        k3 += " AND C_COMPANY='" + user.company + "'";
      }

      console.log(k3);
      _self.$refs.mainDataGrid3.condition = k3;
      _self.$refs.mainDataGrid3.loadGridData();
    },

    handleReport() {
      let _self = this;
      _self.loading = true;
      var m = new Map();
      m.set("iedPlanStatistic", _self.iedPlanStatistic);
      m.set("startDate", _self.startDate);
      m.set("endDate", _self.endDate);
      axios
        .post("/exchange/ied/IEDReport", JSON.stringify(m))
        .then(function (response) {
          _self.reportData = response.data.data;
          _self.loading = false;
        })
        .catch(function (error) {
          console.log(error);
        });
    },

    exportDataOverdue() {
      let dataUrl = "/exchange/doc/export";
      var fileDate = new Date();
      let fileDateStr =
        fileDate.getFullYear() +
        "" +
        fileDate.getMonth() +
        "" +
        fileDate.getDate();
      let params = {
        gridName: "IEDReportGrid",
        lang: "zh-cn",
        condition: this.$refs.mainDataGrid1.condition,
        filename: "IED_Report_Overdue_" + fileDateStr + ".xlsx",
        sheetname: "Result",
      };
      ExcelUtil.export(params);
    },

    exportDataunCompleted() {
      let dataUrl = "/exchange/doc/export";
      var fileDate = new Date();
      let fileDateStr =
        fileDate.getFullYear() +
        "" +
        fileDate.getMonth() +
        "" +
        fileDate.getDate();
      let params = {
        gridName: "IEDReportGrid",
        lang: "zh-cn",
        condition: this.$refs.mainDataGrid2.condition,
        filename: "IED_Report_unCompleted_" + fileDateStr + ".xlsx",
        sheetname: "Result",
      };
      ExcelUtil.export(params);
    },

    exportDataCompleted() {
      let dataUrl = "/exchange/doc/export";
      var fileDate = new Date();
      let fileDateStr =
        fileDate.getFullYear() +
        "" +
        fileDate.getMonth() +
        "" +
        fileDate.getDate();
      let params = {
        gridName: "IEDReportGrid",
        lang: "zh-cn",
        condition: this.$refs.mainDataGrid3.condition,
        filename: "IED_Report_Completed_" + fileDateStr + ".xlsx",
        sheetname: "Result",
      };
      ExcelUtil.export(params);
    },

    exportDataStatistic() {
      let _self = this;

      import("@/utils/Export2Excel").then((excel) => {
        const tHeader = ["项目名", "计划数", "完成数", "完成百分比"];
        const filterVal = [
          "projectName",
          "iedCount",
          "completedCount",
          "completedPercent",
        ];
        const list = _self.reportData;
        const data = this.formatJson(filterVal, list);
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: "IED_Report_" + new Date().Format("yyyy-MM-dd"),
        });
      });
    },

    formatJson(filterVal, jsonData) {
      return jsonData.map((v) => filterVal.map((j) => v[j]));
    },

    indexMethod(index) {
      return index * 1;
    },
  },
  props: {},
  components: {
    ShowProperty: ShowProperty,
    DataGrid: DataGrid,
    DataSelect: DataSelect,
    DataLayout: DataLayout,
  },
};
</script>
<style scoped>
.el-form-item {
  margin-bottom: 0px;
}
</style>