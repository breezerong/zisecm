<template>
  <DataLayout>
    <template v-slot:header style="height: auto"></template>
    <template v-slot:main="{ layout }">
      <container>
        <el-header style="height: auto">
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
              <el-date-picker
                v-model="info.startDate"
                type="date"
                :placeholder="$t('application.startDate')"
                value-format="yyyy-MM-dd"
                :picker-options="pickerOptions"
              ></el-date-picker>
            </el-form-item>
            <el-form-item>
              <el-date-picker
                v-model="info.endDate"
                type="date"
                align="right"
                :placeholder="$t('application.endDate')"
                value-format="yyyy-MM-dd"
              ></el-date-picker>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleReport()">{{
                $t("application.SearchData")
              }}</el-button>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="icmDataStatistic()">{{
                $t("application.ExportExcel")
              }}</el-button>
            </el-form-item>
          </el-form>
        </el-header>
        <el-main>
          <el-table
            ref="mainTable"
            :data="tables.mainTable.data"
            border
            stripe
            size="mini"
            v-loading="loading"
            :height="layout.height - 160"
          >
            <el-table-column type="index" width="30" fixed></el-table-column>
            <el-table-column
              v-for="item in tables.mainTable.columns"
              :key="item.prop"
              v-bind="item"
            ></el-table-column>
          </el-table>
        </el-main>
      </container>
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
  name: "ContactorReport",
  data() {
    return {
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        },
      },
      loading: false,
      tables: {
        mainTable: {
          data: [],
          columns: [
            {
              prop: "externalICM",
              label: this.$t("application.externalICM"),
              fixed: true,
              width: 110,
            },
            {
              prop: "publishPro",
              label: this.$t("application.toNum"),
              width: 110,
            },
            {
              prop: "acceptPro",
              label: this.$t("application.fromNum"),
              width: 110,
            },
            {
              prop: "planPro",
              label: this.$t("application.subPlan"),
              width: 110,
            },
            {
              prop: "acturComplete",
              label: this.$t("application.acturComplete"),
              width: 110,
            },
            {
              prop: "notComplete",
              label: this.$t("application.notComplete"),
              width: 110,
            },
            {
              prop: "delayNr",
              label: this.$t("application.delayNr"),
              width: 110,
            },
            {
              prop: "schComplete",
              label: this.$t("application.schComplete"),
              width: 110,
            },
            {
              prop: "schComPer",
              label: this.$t("application.schComPer"),
              width: 110,
              formatter: function (row, column) {
                let p = row.schComPer;
                if (p > 0.0) {
                  return Math.round(p * 10000) / 100 + "%";
                }
                if (p == 0.0) {
                  return p + "%";
                }
                return "";
              },
            },
            {
              prop: "cumComPer",
              label: this.$t("application.cumComPer"),
              width: 110,
              formatter: function (row, column) {
                let p = row.cumComPer;
                if (p > 0.0) {
                  return Math.round(p * 10000) / 100 + "%";
                }
                if (p == 0.0) {
                  return p + "%";
                }
                return "";
              },
            },
          ],
        },
      },

      icmReportStatistc: "",
      info: {
        startDate: this.getNowTime(),
        endDate: this.getPeriodTime(),
      },
    };
  },
  created() {},

  watch: {
    "$store.state.app.language": function (nv, ov) {
      this.tables.mainTable.columns = [
        {
          prop: "externalICM",
          label: this.$t("application.externalICM"),
          fixed: true,
          width: 110,
        },
        { prop: "publishPro", label: this.$t("application.toNum"), width: 110 },
        {
          prop: "acceptPro",
          label: this.$t("application.fromNum"),
          width: 110,
        },
        { prop: "planPro", label: this.$t("application.subPlan"), width: 110 },
        {
          prop: "acturComplete",
          label: this.$t("application.acturComplete"),
          width: 110,
        },
        {
          prop: "notComplete",
          label: this.$t("application.notComplete"),
          width: 110,
        },
        { prop: "delayNr", label: this.$t("application.delayNr"), width: 110 },
        {
          prop: "schComplete",
          label: this.$t("application.schComplete"),
          width: 110,
        },
        {
          prop: "schComPer",
          label: this.$t("application.schComPer"),
          width: 110,
          formatter: function (row, column) {
            let p = row.schComPer;
            if (p > 0.0) {
              return Math.round(p * 10000) / 100 + "%";
            }
            if (p == 0.0) {
              return p + "%";
            }
            return "";
          },
        },
        {
          prop: "cumComPer",
          label: this.$t("application.cumComPer"),
          width: 110,
          formatter: function (row, column) {
            let p = row.cumComPer;
            if (p > 0.0) {
              return Math.round(p * 10000) / 100 + "%";
            }
            if (p == 0.0) {
              return p + "%";
            }
            return "";
          },
        },
      ];
    },
  },

  mounted() {
    if (!this.validataPermission()) {
      //跳转至权限提醒页
      let _self = this;
      _self.$nextTick(() => {
        _self.$router.push({ path: "/NoPermission" });
      });
    }
    this.language = localStorage.getItem("localeLanguage") || "zh-cn";
  },
  methods: {
    getNowTime() {
      var now = new Date();
      var year = now.getFullYear(); //得到年份
      var month = now.getMonth(); //得到月份
      var date = now.getDate(); //得到日期
      month = month.toString().padStart(2, "0");
      date = date.toString().padStart(2, "0");
      var defaultDate = `${year}-${month}-${date}`;
      return defaultDate;
      this.$set(this.info, "startDate", defaultDate);
    },

    getPeriodTime() {
      var now = new Date();
      var year = now.getFullYear(); //得到年份
      var month = now.getMonth(); //得到月份
      var date = now.getDate(); //得到日期
      month = month + 1;
      month = month.toString().padStart(2, "0");
      date = date.toString().padStart(2, "0");
      var defaultDate = `${year}-${month}-${date}`;
      return defaultDate;
      this.$set(this.info, "endDate", defaultDate);
    },

    handleReport() {
      let _self = this;
      _self.loading = true;

      var m = new Map();
      m.set("icmReportStatistc", _self.icmReportStatistc);
      m.set("startDate", _self.info.startDate);
      m.set("endDate", _self.info.endDate);

      axios
        .post("/exchange/icm/ICMContReport", JSON.stringify(m))
        .then(function (response) {
          _self.tables.mainTable.data = response.data.data;
          _self.loading = false;
        })
        .catch(function (error) {
          console.log(error);
        });
    },

    icmDataStatistic() {
      let _self = this;

      import("@/utils/Export2Excel").then((excel) => {
        let tHeader = [];
        let filterVal = [];
        _self.tables.mainTable.columns.forEach(function (item) {
          tHeader.push(item.label);
          filterVal.push(item.prop);
        });

        const list = _self.tables.mainTable.data;
        const data = this.formatJson(filterVal, list);
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename:
            "ICM_Contactor_Statistic_" + new Date().Format("yyyy-MM-dd"),
        });
      });
    },

    formatJson(filterVal, jsonData) {
      return jsonData.map((v) => filterVal.map((j) => v[j]));
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
