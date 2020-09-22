<template>
  <DataLayout>
    <template v-slot:header>
      <el-form inline>
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
          <el-button type="primary" @click.native="exportData">{{$t('application.ExportExcel')}}</el-button>
        </el-form-item>
        <el-form-item>
          <AddCondition
            v-bind:typeName="typeName"
            :inputType="hiddenInput"
            @change="onSearchConditionChange"
          ></AddCondition>
        </el-form-item>
      </el-form>
    </template>
    <template v-slot:main="{layout}">
      <DataGrid
        ref="mainDataGrid"
        data-url="/dc/getDocuments"
        :isShowMoreOption="false"
        :isshowOption="true"
        :isshowCustom="false"
        :isshowicon="false"
        gridViewName="ICMReportGrid"
        condition="TYPE_NAME='' and C_PROJECT_NAME = '@project'"
        :tableHeight="layout.height-210"
      ></DataGrid>
    </template>
  </DataLayout>
</template>
<script type="text/javascript">
import ShowProperty from "@/components/ShowProperty";
import DataGrid from "@/components/DataGrid";
import DataSelect from "@/components/ecm-data-select";
import DataLayout from "@/components/ecm-data-layout";
import AddCondition from "@/views/record/AddCondition.vue";
import ExcelUtil from "@/utils/excel.js";
import FileSaver from "file-saver";
import XLSX from "xlsx";
export default {
  name: "ICMReportP",
  data() {
    return {
      icmReportStatistc: "",
      hiddenInput: "hidden",
      typeName: "ICM",
    };
  },

  methods: {
    search1() {
      let _self = this;

      var k1 = "TYPE_NAME = 'ICM'";

      if (
        _self.icmReportStatistc != undefined &&
        _self.icmReportStatistc != "所有项目"
      ) {
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
        filename: "ICM_Export_" + fileDateStr + ".xlsx",
        sheetname: "ICM_Export",
      };
      ExcelUtil.export(params);
    },

    onSearchConditionChange: function (val) {
      this.search(val);
    },
  },

  components: {
    ShowProperty: ShowProperty,
    DataGrid: DataGrid,
    DataSelect: DataSelect,
    DataLayout: DataLayout,
    AddCondition: AddCondition,
  },
};
</script>
<style scoped>
.el-form-item {
  margin-bottom: 0px;
}
</style>
