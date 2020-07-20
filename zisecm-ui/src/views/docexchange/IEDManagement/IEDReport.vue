<template>
  <DataLayout @onLayoutResize="onLayoutResize">
  <el-tabs v-model="activeName" @tab-click="handleClick">
    <el-tab-pane label="已超期IED" name="first">
      <el-container>
        <el-header>
          <el-row>
            <el-form :inline="true" :model="filters">
              <el-form-item>
                 <DataSelect v-model="value" dataUrl="/exchange/project/myproject" dataValueField="name" dataTextField="name" includeAll></DataSelect>
              </el-form-item>
              <el-form-item>
                <el-date-picker
                  v-model="startDate"
                  type="date"
                placeholder="开始日期">
                </el-date-picker>
              </el-form-item>
              <el-form-item>
                <el-date-picker
                  v-model="endDate"
                  type="date"
                  align="right"
                placeholder="结束日期">
                </el-date-picker>
              </el-form-item>
              <el-form-item>
                  <el-button type="primary" @click="search1()">查询</el-button>
              </el-form-item>
            </el-form>
          </el-row>
        </el-header>
        <el-main>
          <el-row>
            <el-col :span="24">
              <DataGrid
                ref="mainDataGrid1"
                data-url="/dc/getDocuments"
                isshowOption
                isshowCustom
                gridViewName="IEDReportGrid"
                condition="TYPE_NAME='IED'"
                :tableHeight="tables.main.height"
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
            <el-form :inline="true" :model="filters">
              <el-form-item>
                 <DataSelect v-model="value" dataUrl="/exchange/project/myproject" dataValueField="name" dataTextField="name" includeAll></DataSelect>
              </el-form-item>
              <el-form-item>
                <el-date-picker
                  v-model="startDate"
                  type="date"
                placeholder="开始日期">
                </el-date-picker>
              </el-form-item>
              <el-form-item>
                <el-date-picker
                  v-model="endDate"
                  type="date"
                  align="right"
                placeholder="结束日期">
                </el-date-picker>
              </el-form-item>
              <el-form-item>
                  <el-button type="primary" @click="search2()">查询</el-button>
              </el-form-item>
            </el-form>
          </el-row>
        </el-header>
        <el-main>
          <el-row>
            <el-col :span="24">
              <DataGrid
                ref="mainDataGrid2"
                data-url="/dc/getDocuments"
                isshowOption
                isshowCustom
                gridViewName="IEDReportGrid"
                condition="TYPE_NAME='IED'"
                :tableHeight="tables.main.height"
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
            <el-form :inline="true" :model="filters">
              <el-form-item>
                 <DataSelect v-model="value" dataUrl="/exchange/project/myproject" dataValueField="name" dataTextField="name" includeAll></DataSelect>
              </el-form-item>
              <el-form-item>
                <el-date-picker
                  v-model="startDate"
                  type="date"
                placeholder="开始日期">
                </el-date-picker>
              </el-form-item>
              <el-form-item>
                <el-date-picker
                  v-model="endDate"
                  type="date"
                  align="right"
                placeholder="结束日期">
                </el-date-picker>
              </el-form-item>
              <el-form-item>
                  <el-button type="primary" @click="search3()">查询</el-button>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click.native="exportData">Excel下载</el-button>
              </el-form-item>
            </el-form>
          </el-row>
        </el-header>
        <el-main>
          <el-row>
            <el-col :span="24">
              <DataGrid
                ref="mainDataGrid3"
                data-url="/dc/getDocuments"
                isshowOption
                isshowCustom
                gridViewName="IEDReportGrid"
                condition="TYPE_NAME='IED'" 
                :tableHeight="tables.main.height"
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
            <el-form :inline="true" :model="filters">
              <el-form-item>
                 <DataSelect v-model="value" dataUrl="/exchange/project/myproject" dataValueField="name" dataTextField="name" includeAll></DataSelect>
              </el-form-item>
              <el-form-item>
                <el-date-picker
                  v-model="startDate"
                  type="date"
                placeholder="开始日期">
                </el-date-picker>
              </el-form-item>
              <el-form-item>
                <el-date-picker
                  v-model="endDate"
                  type="date"
                  align="right"
                placeholder="结束日期">
                </el-date-picker>
              </el-form-item>
              <el-form-item>
                  <el-button type="primary" @click="handleReport()">查询</el-button>
              </el-form-item>
            </el-form>
          </el-row>
        </el-header>
        <el-main>
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
  </DataLayout>
</template>
<script type="text/javascript">
import ShowProperty from "@/components/ShowProperty";
import DataGrid from "@/components/DataGrid";
import DataSelect from "@/components/ecm-data-select";
import DataLayout from '@/components/ecm-data-layout'
import ExcelUtil from '@/utils/excel.js'
export default {
  name: "IEDReport",
  data() {
    return {
      tables: {
        main: {
          gridName: "IEDReportGrid",
          datalist: [],
          height:"",
        }
      },

      activeName: "first",
      input: "",
      value: "",
      ct_month: null,
      startDate: "",
      endDate: "",
      reportData: [],
      Subcontractors:'',
      selectedItems: [],
      selectedItemId: "",
      startDate: '',
      endDate: '',

    };
  },
  created() {
    window.addEventListener("resize",this.getHeight);
  },
  mounted() {
    if(!this.validataPermission()){
      //跳转至权限提醒页
      let _self=this;
      _self.$nextTick(()=>{
        _self.$router.push({ path: '/NoPermission' })
      })
      console.log(sessionStorage.data.data.groupname)
    }   
    this.getHeight();
    this.fresh()
  },
  methods: {
    onLayoutResize(size){
      console.log(size)
      this.tables.main.height = size - 180    
    },
    
    getHeight() {
      this.tables.main.tableHeight = window.innerHeight - 180+"px"  
    },
    
    fresh(){
      let _self = this
      window.addEventListener("resize",this.getHeight);
      _self.$refs.mainDataGrid.loadGridData();
    },

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

      var k1 = "TYPE_NAME='IED'"
      let wheres = ["TITLE","C_IN_CODING"]
      let orS = ""

      if(_self.input.trim().length>0){
        wheres.forEach(function(item){
        if(orS.length>0){
          orS+=" OR "
        }
        orS+=item + " LIKE '%"+ _self.input+"%'"
        })
        k1+=" AND (" + orS + ")"
      }

      if(_self.value != undefined && _self.value!='所有项目'){
        k1+=" AND C_PROJECT_NAME in ("+_self.value +")" + " AND C_ITEM4_DATE < '" + this.clock + "'" + " AND C_ITEM_STATUS2 IS NULL";
      }

      console.log(k1);
      _self.$refs.mainDataGrid1.condition = k1;
      _self.$refs.mainDataGrid1.loadGridData();
    },

    search2() {
      let _self = this;

      var k2 = "TYPE_NAME='IED'"
      let wheres = ["TITLE","C_IN_CODING"]
      let orS = ""

      if(_self.input.trim().length>0){
        wheres.forEach(function(item){
        if(orS.length>0){
          orS+=" OR "
        }
        orS+=item + " LIKE '%"+ _self.input+"%'"
        })
        k2+=" AND (" + orS + ")"
      }

      if(this.value != undefined && this.value!='所有项目'){
        k2+=" AND C_PROJECT_NAME in ("+_self.value +")" + " AND C_ITEM_STATUS2 IS NULL"
      }

      if(this.value = undefined || this.value == '所有项目'){
        k2+=" AND C_ITEM_STATUS2 IS NULL"
      }

      console.log(k2);
      _self.$refs.mainDataGrid1.condition = k2;
      _self.$refs.mainDataGrid1.loadGridData();
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

    exportData(){
        let dataUrl = "/exchange/doc/export"
        var fileDate = new Date()
        let fileDateStr = fileDate.getFullYear()+""+fileDate.getMonth()+""+ fileDate.getDate()
        let params = {
          gridName:this.tables.main.gridViewName,
          lang:"zh-cn",
          condition: this.$refs.mainDataGrid.condition,
          filename:"IED_Report_"+fileDateStr+".xlsx",
          sheetname:"Result"
        }
        ExcelUtil.export(params)
    },

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
    DataSelect: DataSelect,
    DataLayout:DataLayout,
  }
};
</script>
<style scoped>
</style>