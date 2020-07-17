<template>
  <el-tabs v-model="activeName" @tab-click="handleClick">
    <el-tab-pane label="已超期IED" name="first">
      <el-container>
        <el-header>
          <el-row>
            <el-select v-model="value" placeholder="请选择">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
            <el-input v-model="input" placeholder="内部编码或标题" style="width:200px"></el-input>
            <el-button type="primary" @click="search1()">查询</el-button>
          </el-row>
        </el-header>
        <el-main>
          <el-row>
            <el-col :span="24">
              <DataGrid
                ref="mainDataGrid1"
                tableHeight="350"
                data-url="/dc/getDocuments"
                isshowOption
                isshowCustom
                gridViewName="IEDReportGrid"
                condition="TYPE_NAME='IED'"
                @cellMouseEnter="cellMouseEnter"
                @cellMouseleave="cellMouseleave"
                @rowclick="rowClick"
                @selectchange="selectChange"
              ></DataGrid>
            </el-col>
          </el-row>
        </el-main>
      </el-container>
    </el-tab-pane>
    <el-tab-pane label="未完成IED" name="second">
      <el-container>
        <el-header>
          <el-row>
            <el-select v-model="value" placeholder="所有项目">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
            <el-input v-model="input" placeholder="内部编码或标题" style="width:200px"></el-input>
            <el-button type="primary" @click="search2()">查询</el-button>
          </el-row>
        </el-header>
        <el-main>
          <el-row>
            <el-col :span="24">
              <DataGrid
                ref="mainDataGrid2"
                tableHeight="350"
                data-url="/dc/getDocuments"
                isshowOption
                isshowCustom
                gridViewName="IEDReportGrid"
                condition="TYPE_NAME='IED'"
                @cellMouseEnter="cellMouseEnter"
                @cellMouseleave="cellMouseleave"
                @rowclick="rowClick"
                @selectchange="selectChange"
              ></DataGrid>
            </el-col>
          </el-row>
        </el-main>
      </el-container>
    </el-tab-pane>
    <el-tab-pane label="已完成IED" name="third">
      <el-container>
        <el-header>
          <el-row>
            <el-select v-model="value" placeholder="所有项目">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
            <el-input v-model="input" placeholder="内部编码或标题" style="width:200px"></el-input>
            <el-button type="primary" @click="search3()">查询</el-button>
          </el-row>
        </el-header>
        <el-main>
          <el-row>
            <el-col :span="24">
              <DataGrid
                ref="mainDataGrid3"
                tableHeight="350"
                data-url="/dc/getDocuments"
                isshowOption
                isshowCustom
                gridViewName="IEDReportGrid"
                condition="TYPE_NAME='IED'"
                @cellMouseEnter="cellMouseEnter"
                @cellMouseleave="cellMouseleave"
                @rowclick="rowClick"
                @selectchange="selectChange"
              ></DataGrid>
            </el-col>
          </el-row>
        </el-main>
      </el-container>
    </el-tab-pane>
    <el-tab-pane label="IED完成情况统计" name="forth">
      <el-container>
        <el-header>
          <el-row>
 
            <el-button type="primary" @click="handleReport()">查询</el-button>
          </el-row>
        </el-header>
        <el-main>
          <!-- <el-table 
            :data="tabledata"
            border
            style="width: 100%"
            :default-sort = "{prop: 'index', order: 'descending'}">
                <el-table-column
                type="index"
                :index="indexMethod">
                <el-table-column>
                <el-table-column
                prop="proname"
                label="项目名"
                sortable
                width="120">
                <el-table-column>
                <el-table-column
                prop="pronum"
                label="计划数"
                sortable
                width="120">
                <el-table-column>
                <el-table-column
                prop="completed"
                label="完成数"
                sortable
                width="120">
                <el-table-column>
                <el-table-column
                prop="comppercent"
                label="完成百分比"
                sortable
                width="120">
                <el-table-column>
          <el-table>-->

          <el-table :data="reportData" style="width: 100%;" border>
            <el-table-column type="index" width="60"></el-table-column>
            <el-table-column prop="projectName" label="项目名" sortable width="220"></el-table-column>
            <el-table-column prop="iedCount" label="计划数" sortable width="160"></el-table-column>
            <el-table-column prop="completedCount" label="完成数" sortable width="160"></el-table-column>
            <el-table-column
              prop="completedPercent"
              label="完成百分比"
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
<script type="text/javascript">
import ShowProperty from "@/components/ShowProperty";
import DataGrid from "@/components/DataGrid";
import DataSelect from "@/components/ecm-data-select";
export default {
  name: "IEDReport",
  data() {
    return {
      tables: {
        main: {
          gridName: "IEDReportGrid",
          datalist: []
        }
      },

      activeName: "first",
      input: "",
      ct_month: null,
      startDate: "",
      endDate: "",
      reportData: [],
      options: [
        {
          value: "所有项目",
          label: "所有项目"
        },
        {
          value: "三门3、4号机组",
          label: "三门3、4号机组"
        },
        {
          value: "福清5、6号机组",
          label: "福清5、6号机组"
        },
        {
          value: "海南5、6号机组",
          label: "海南5、6号机组"
        },
        {
          value: "海阳5、6号机组",
          label: "海阳5、6号机组"
        },
        {
          value: "田湾7、8号机组",
          label: "田湾7、8号机组"
        }
      ],
      value: ""
    };
  },
  created() {},
  mounted() {
    if (!this.validataPermission()) {
      //跳转至权限提醒页
      let _self = this;
      _self.$nextTick(() => {
        _self.$router.push({ path: "/NoPermission" });
      });
    }
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab, event);
    },
    percentFormatter (row, column) {
        let p = row.completedPercent;
        if(p){
          return Math.round(p*10000)/100+'%';
        }
        return ''
    },
    search1() {
      let _self = this;
      this.ct_month = new Date();

      this.ct_month.setTime(this.ct_month.getTime() - 3600 * 1000 * 24 * 30);

      var now = this.ct_month;

      var year = now.getFullYear(); //年
      var month = now.getMonth(); //月
      var day = now.getDate(); //日

      var clock = year + "-";

      if (month < 10) clock += "0";

      clock += month + "-";

      if (day < 10) clock += "0";

      clock += day + " ";

      var k1 =
        "TYPE_NAME='IED' AND C_PROJECT_NAME =" +
        "'" +
        this.value +
        "'" +
        "AND (C_IN_CODING LIKE '%" +
        this.input +
        "%' OR TITLE LIKE '%" +
        this.input +
        "%')" +
        " AND C_ITEM4_DATE < ''" +
        "'" +
        this.clock +
        "'''" +
        " AND C_ITEM_STATUS2 IS NULL";

      if (this.value != "" && this.input == "")
        k1 =
          "TYPE_NAME='IED' AND C_PROJECT_NAME =" +
          "'" +
          this.value +
          "'" +
          " AND C_ITEM4_DATE < " +
          "'" +
          this.clock +
          "'" +
          " AND C_ITEM_STATUS2 IS NULL";
      if ((this.value = "" && this.input == ""))
        k1 =
          "TYPE_NAME='IED'" +
          "AND C_ITEM4_DATE < " +
          "'" +
          this.clock +
          "'" +
          " AND C_ITEM_STATUS2 IS NULL";
      if ((this.value = "" && this.input != ""))
        k1 =
          "TYPE_NAME='IED' AND (C_IN_CODING LIKE '%" +
          this.input +
          "%' OR TITLE LIKE '%" +
          this.input +
          "%')" +
          " AND C_ITEM4_DATE < " +
          "'" +
          this.clock +
          "'" +
          " AND C_ITEM_STATUS2 IS NULL";

      console.log(k1);
      _self.$refs.mainDataGrid1.condition = k1;
      _self.$refs.mainDataGrid1.loadGridData();
    },

    search2() {
      let _self = this;
      var k2 =
        "TYPE_NAME='IED' AND C_PROJECT_NAME =" +
        "'" +
        this.value +
        "'" +
        "AND (C_IN_CODING LIKE '%" +
        this.input +
        "%' OR TITLE LIKE '%" +
        this.input +
        "%')" +
        "AND C_ITEM_STATUS2 IS NULL";

      if (this.value != "" && this.input == "")
        k2 =
          "TYPE_NAME='IED' AND C_PROJECT_NAME =" +
          "'" +
          this.value +
          "'" +
          "AND C_ITEM_STATUS2 IS NULL";
      if ((this.value = "" && this.input == ""))
        k2 = "TYPE_NAME='IED'" + "AND C_ITEM_STATUS2 IS NULL";
      if ((this.value = "" && this.input != ""))
        k2 =
          "TYPE_NAME='IED' AND (C_IN_CODING LIKE '%" +
          this.input +
          "%' OR TITLE LIKE '%" +
          this.input +
          "%')" +
          "AND C_ITEM_STATUS2 IS NULL";

      console.log(k2);
      _self.$refs.mainDataGrid2.condition = k2;
      _self.$refs.mainDataGrid2.loadGridData();
    },

    search3() {
      let _self = this;
      var k3 =
        "TYPE_NAME='IED' AND C_PROJECT_NAME =" +
        "'" +
        this.value +
        "'" +
        "AND (C_IN_CODING LIKE '%" +
        this.input +
        "%' OR TITLE LIKE '%" +
        this.input +
        "%')" +
        "AND C_ITEM_STATUS2 = 'Y'";

      if (this.value != "" && this.input == "")
        k3 =
          "TYPE_NAME='IED' AND C_PROJECT_NAME =" +
          "'" +
          this.value +
          "'" +
          "AND C_ITEM_STATUS2 = 'Y'";
      if ((this.value = "" && this.input == ""))
        k3 = "TYPE_NAME='IED'" + "AND C_ITEM_STATUS2 = 'Y'";
      if ((this.value = "" && this.input != ""))
        k3 =
          "TYPE_NAME='IED' AND (C_IN_CODING LIKE '%" +
          this.input +
          "%' OR TITLE LIKE '%" +
          this.input +
          "%')" +
          "AND C_ITEM_STATUS2 = 'Y'";

      console.log(k3);
      _self.$refs.mainDataGrid3.condition = k3;
      _self.$refs.mainDataGrid3.loadGridData();
    },

    handleReport() {
      let _self = this;
      _self.loading = true;
      var m = new Map();
      m.set("startDate", _self.startDate);
      m.set("endDate", _self.endDate);
      axios
        .post("/exchange/ied/IEDReport", JSON.stringify(m))
        .then(function(response) {
          _self.reportData = response.data.data;
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
        });
    },

    statistic() {
      let _self = this;
      _self.loading = true;

      let url = "/admin/";

      var m = new Map();
      m.set("gridName", _self.gridViewName);
      m.set("lang", _self.currentLanguage);

      _self.axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },

        method: "post",
        data: JSON.stringify(m)
      });
    },

    //selectChange(val){
    //   this.selectedItems = val;
    //   console.log("");
    //},

    cellMouseEnter(row, column, cell, event) {
      this.selectRow = row;
      console.log(row);
    },

    rowClick(row) {
      this.selectRow = row;
      console.log("123123123");
    },

    indexMethod(index) {
      return index * 1;
    }
  },
  props: {},
  components: {
    ShowProperty: ShowProperty,
    DataGrid: DataGrid,
    DataSelect: DataSelect
  }
};
</script>
<style scoped>
</style>