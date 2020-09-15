<template>
  <DataLayout>
    <template v-slot:header style="height: auto"></template>
    <template v-slot:main="{layout}">
      <el-container>
        <el-header style="height:auto;">
          <el-form :inline="true">
            <el-form-item>
              <DataSelect
                v-model="icmReportStatistc"
                data-url="/exchange/project/myproject"
                data-value-field="name"
                data-text-field="name"
                includeAll
              ></DataSelect>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="search1()">{{$t('application.SearchData')}}</el-button>
            </el-form-item>
            <el-form-item>
              <el-button
                type="primary"
                @click.native="exportData"
              >{{$t('application.ExportExcel')}}</el-button>
            </el-form-item>
          </el-form>
        </el-header>
        <el-main>
          <el-row>
            <el-col :span="24">
              <DataGrid
                ref="mainDataGrid"
                data-url="/dc/getDocuments"
                :isShowMoreOption="false"
                :isshowOption="true"
                :isshowCustom="false"
                :isshowicon="false"
                gridViewName="ICMExchangeList"
                condition="TYPE_NAME='' and C_PROJECT_NAME = '@project'"
                :tableHeight="layout.height-210"
              ></DataGrid>
            </el-col>
          </el-row>
        </el-main>
      </el-container>
    </template>
  </DataLayout>
</template>
<script type="text/javascript">
import ShowProperty from "@/components/ShowProperty";
import DataGrid from "@/components/DataGrid";
import DataSelect from "@/components/ecm-data-select";
import DataLayout from "@/components/ecm-data-layout";
import FileSaver from "file-saver";
import XLSX from "xlsx";
export default {
  name: "ICMExchangeGrid",
  data() {
    return {
      tables: {
        main: {
          gridName: "ICMExchangeList",
          datalist: [],
          height: "",
        },
      },

      icmReportStatistc: "",
      
    };
  },

  methods: {
    search1() {
      let _self = this;

      var k1 =
        "TYPE_NAME='ICM'";

      if (_self.icmReportStatistc != undefined && _self.icmReportStatistc != "所有项目") {
        k1 += " AND C_PROJECT_NAME in (" + _self.icmReportStatistc + ")";
      }

      let user = this.currentUser();
      if (user.userType == 2 && user.company != null) {
        k1 += " AND C_COMPANY='" + user.company + "'";
      }

      _self.$refs.mainDataGrid.condition = k1;
      _self.$refs.mainDataGrid.loadGridData();
    },

    exportData() {
      let dataUrl = "/exchange/doc/export";
      var fileDate = new Date();
      let fileDateStr =
        fileDate.getFullYear() +
        "" +
        fileDate.getMonth() +
        "" +
        fileDate.getDate();
      let params = {
        gridName: "ICMReportGrid",
        lang: "zh-cn",
        condition: this.$refs.mainDataGrid.condition,
        filename: "ICM_ExchangeList_" + fileDateStr + ".xlsx",
        sheetname: "ICM_ExchangeList",
      };
      ExcelUtil.export(params);
    },

  },

  components: {
    ShowProperty: ShowProperty,
    DataGrid: DataGrid,
    DataSelect: DataSelect,
    DataLayout: DataLayout,
  },
};
</script>
<style scoped>
.el-header {
  height: auto;
}
</style>
