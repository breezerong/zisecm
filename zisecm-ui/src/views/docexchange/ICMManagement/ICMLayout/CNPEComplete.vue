<template>
  <DataLayout>
    <template v-slot:header style="height: auto"></template>
    <template v-slot:main="{ layout }">
      <el-container>
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
            <el-table-column type="index" width="40" fixed></el-table-column>
            <el-table-column
              v-for="item in tables.mainTable.columns"
              :key="item.prop"
              v-bind="item"
            ></el-table-column>
          </el-table>
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
  name: "CNPECompleteReport",
  data() {
    return {
      loading: false,
      tables: {
        mainTable: {
          data: [],
          columns: [
            {
              prop: "sponsorName",
              label: this.$t("application.sponsorName"),
              fixed: true,
              width: 130,
            },
            {
              prop: "planOpen",
              label: this.$t("application.planOpen"),
              width: 100,
            },
            {
              prop: "acturOpen",
              label: this.$t("application.acturOpen"),
              width: 100,
            },
            {
              prop: "notOpen",
              label: this.$t("application.notOpen"),
              width: 100,
            },
            {
              prop: "delayOpenYr",
              label: this.$t("application.delayOpenYr"),
              width: 110,
            },
            {
              prop: "delayOpenNr",
              label: this.$t("application.delayOpenNr"),
              width: 110,
            },
            {
              prop: "schOpen",
              label: this.$t("application.schOpen"),
              width: 100,
            },
            {
              prop: "perSchOpen",
              label: this.$t("application.perSchOpen"),
              width: 100,
              formatter: function (row, column) {
                let p = row.perSchOpen;
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
              prop: "perOpen",
              label: this.$t("application.perOpen"),
              width: 100,
              formatter: function (row, column) {
                let p = row.perOpen;
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
              prop: "planReply",
              label: this.$t("application.planReply"),
              width: 100,
            },
            {
              prop: "acturReply",
              label: this.$t("application.acturReply"),
              width: 100,
            },
            {
              prop: "notReply",
              label: this.$t("application.notReply"),
              width: 100,
            },
            {
              prop: "delayReplyYr",
              label: this.$t("application.delayReplyYr"),
              width: 110,
            },
            {
              prop: "delayReplyNr",
              label: this.$t("application.delayReplyNr"),
              width: 110,
            },
            {
              prop: "schReply",
              label: this.$t("application.schReply"),
              width: 100,
            },
            {
              prop: "perSchReply",
              label: this.$t("application.perSchReply"),
              width: 100,
              formatter: function (row, column) {
                let p = row.perSchReply;
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
              prop: "perReply",
              label: this.$t("application.perReply"),
              width: 100,
              formatter: function (row, column) {
                let p = row.perReply;
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
              prop: "planClose",
              label: this.$t("application.planClose"),
              width: 100,
            },
            {
              prop: "acturClose",
              label: this.$t("application.acturClose"),
              width: 100,
            },
            {
              prop: "notClose",
              label: this.$t("application.notClose"),
              width: 100,
            },
            {
              prop: "delayCloseYr",
              label: this.$t("application.delayCloseYr"),
              width: 110,
            },
            {
              prop: "delayCloseNr",
              label: this.$t("application.delayCloseNr"),
              width: 110,
            },
            {
              prop: "schClose",
              label: this.$t("application.schClose"),
              width: 100,
            },
            {
              prop: "perSchClose",
              label: this.$t("application.perSchClose"),
              width: 100,
              formatter: function (row, column) {
                let p = row.perSchClose;
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
              prop: "perClose",
              label: this.$t("application.perClose"),
              width: 100,
              formatter: function (row, column) {
                let p = row.perClose;
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

      selectedItems: [],
    };
  },
  created() {},

  watch: {
    "$store.state.app.language": function (nv, ov) {
      this.tables.mainTable.columns = [
        {
          prop: "sponsorName",
          label: this.$t("application.sponsorName"),
          fixed: true,
          width: 130,
        },
        {
          prop: "planOpen",
          label: this.$t("application.planOpen"),
          width: 100,
        },
        {
          prop: "acturOpen",
          label: this.$t("application.acturOpen"),
          width: 100,
        },
        { prop: "notOpen", label: this.$t("application.notOpen"), width: 100 },
        {
          prop: "delayOpenYr",
          label: this.$t("application.delayOpenYr"),
          width: 110,
        },
        {
          prop: "delayOpenNr",
          label: this.$t("application.delayOpenNr"),
          width: 110,
        },
        { prop: "schOpen", label: this.$t("application.schOpen"), width: 100 },
        {
          prop: "perSchOpen",
          label: this.$t("application.perSchOpen"),
          width: 100,
          formatter: function (row, column) {
            let p = row.perSchOpen;
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
          prop: "perOpen",
          label: this.$t("application.perOpen"),
          width: 100,
          formatter: function (row, column) {
            let p = row.perOpen;
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
          prop: "planReply",
          label: this.$t("application.planReply"),
          width: 100,
        },
        {
          prop: "acturReply",
          label: this.$t("application.acturReply"),
          width: 100,
        },
        {
          prop: "notReply",
          label: this.$t("application.notReply"),
          width: 100,
        },
        {
          prop: "delayReplyYr",
          label: this.$t("application.delayReplyYr"),
          width: 110,
        },
        {
          prop: "delayReplyNr",
          label: this.$t("application.delayReplyNr"),
          width: 110,
        },
        {
          prop: "schReply",
          label: this.$t("application.schReply"),
          width: 100,
        },
        {
          prop: "perSchReply",
          label: this.$t("application.perSchReply"),
          width: 100,
          formatter: function (row, column) {
            let p = row.perSchReply;
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
          prop: "perReply",
          label: this.$t("application.perReply"),
          width: 100,
          formatter: function (row, column) {
            let p = row.perReply;
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
          prop: "planClose",
          label: this.$t("application.planClose"),
          width: 100,
        },
        {
          prop: "acturClose",
          label: this.$t("application.acturClose"),
          width: 100,
        },
        {
          prop: "notClose",
          label: this.$t("application.notClose"),
          width: 100,
        },
        {
          prop: "delayCloseYr",
          label: this.$t("application.delayCloseYr"),
          width: 110,
        },
        {
          prop: "delayCloseNr",
          label: this.$t("application.delayCloseNr"),
          width: 110,
        },
        {
          prop: "schClose",
          label: this.$t("application.schClose"),
          width: 100,
        },
        {
          prop: "perSchClose",
          label: this.$t("application.perSchClose"),
          width: 100,
          formatter: function (row, column) {
            let p = row.perSchClose;
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
          prop: "perClose",
          label: this.$t("application.perClose"),
          width: 100,
          formatter: function (row, column) {
            let p = row.perClose;
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

    handleReport() {
      let _self = this;
      _self.loading = true;

      var m = new Map();
      m.set("icmReportStatistc", _self.icmReportStatistc);
      m.set("startDate", _self.info.startDate);
      m.set("endDate", _self.info.endDate);

      axios
        .post("/exchange/icm/ICMRNeweport", JSON.stringify(m))
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
          filename: "ICM_CNPECompleteReport_" + new Date().Format("yyyy-MM-dd"),
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
