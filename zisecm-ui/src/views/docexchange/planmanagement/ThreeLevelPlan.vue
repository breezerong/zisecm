<template>
  <DataLayout>
    <template v-slot:header>
      <el-form :inline="true" :model="forms.headForm">
        <el-form-item>
          <DataSelect
            @onSelectChange="onSelectChange"
            v-model="forms.headForm.project"
            includeAll
            data-url="/exchange/project/myproject"
            data-value-field="name"
            data-text-field="name"
          ></DataSelect>
        </el-form-item>
        <el-form-item>
          <el-input style="width:200px" v-model="inputValueNum" placeholder="请输入WBS编码或名称"></el-input>
          <el-button type="primary" @click="search()">{{$t('application.SearchData')}}</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="default" @click.native="exportData">{{$t('application.ExportExcel')}}</el-button>
        </el-form-item>
        <el-form-item>
          <AddCondition
            @sendMsg="searchItem"
            v-model="advCondition"
            v-bind:typeName="typeName"
            :inputValue="advCondition"
            :inputType="hiddenInput"
          ></AddCondition>
        </el-form-item>
      </el-form>
    </template>
    <template v-slot:main="{layout}">
      <div :style="{position:'relative',height: layout.height-startHeight+'px'}">
        <split-pane v-on:resize="onSplitResize" :min-percent="20" :default-percent="topPercent" split="horizontal">
          <template slot="paneL">
            <DataGrid
              ref="mainDataGrid"
              v-bind="tables.main"
              :tableHeight="(layout.height-startHeight)*topPercent/100-topbarHeight"
              @rowclick="onDataGridRowClick"
            ></DataGrid>
          </template>
          <template slot="paneR">
            <el-tabs v-model="tabs.active">
              <el-tab-pane :label="$t('application.RelationIED')" name="relationFiles">
                <DataGrid
                  ref="rfDg"
                  v-bind="tables.rfDg"
                  :tableHeight="(layout.height-startHeight)*(100-topPercent)/100-bottomHeight"
                ></DataGrid>
              </el-tab-pane>
            </el-tabs>
          </template>
        </split-pane>
      </div>
    </template>
  </DataLayout>
</template>
<script type="text/javascript">
import ShowProperty from "@/components/ShowProperty";
import DataGrid from "@/components/DataGrid";
import DataSelect from "@/components/ecm-data-select";
import ExcelUtil from "@/utils/excel.js";
import AddCondition from "@/views/record/AddCondition.vue";
import DataLayout from "@/components/ecm-data-layout";
export default {
  name: "ThreeLevelPlan",
  data() {
    return {
      // 本地存储高度名称
      topStorageName: "ThreeLevelPlanTopHeight",
      // 非split pan 控制区域高度
      startHeight: 135,
      // 顶部百分比*100
      topPercent: 65,
      // 顶部除列表高度
      topbarHeight: 35,
      // 底部除列表高度
      bottomHeight: 80,

      tables: {
        main: {
          gridViewName: "PlanTaskGrid",
          dataUrl: "/dc/getDocuments",
          condition: "TYPE_NAME='计划任务' AND C_PROJECT_NAME = '@project' and SUB_TYPE='Activity'",
          isshowOption: true,
          isShowMoreOption: false,
          isshowCustom: true,
          isshowicon: false,
          isEditProperty: false,
        },
        rfDg: {
          gridViewName: "IEDGrid",
          dataUrl: "/dc/getDocuments",
          condition: "",
          isshowOption: true,
          isShowMoreOption: false,
          isshowCustom: true,
          isInitData: false,
          tableHeight: "350",
          isEditProperty: false,
        },
      },
      tabs: {
        active: "relationFiles",
      },
      forms: {
        headForm: {
          project: "",
        },
      },
      advCondition: "",
      inputValueNum: "",
      hiddenInput: "hidden",
      typeName: "计划任务",
    };
  },

  mounted() {
    if (!this.validataPermission()) {
      //跳转至权限提醒页
      let _self = this;
      _self.$nextTick(() => {
        _self.$router.push({ path: "/NoPermission" });
      });
    }
    if (this.currentUser().company == "CNPE") {
      this.tables.main.isEditProperty = true;
    }

    this.topPercent = this.getStorageNumber(this.topStorageName, 60);
  },
  methods: {
    // 上下分屏事件
    onSplitResize(topPercent) {
      // 顶部百分比*100
      this.topPercent = topPercent;
      this.setStorageNumber(this.topStorageName, topPercent);
      //console.log(JSON.stringify(topPercent))
    },
    onDataGridRowClick: function (row) {
      this.$refs.rfDg.condition = "C_WBS_CODING='" + row.C_WBS_CODING + "'";
      this.$refs.rfDg.loadGridInfo();
      this.$refs.rfDg.loadGridData();
    },
    /**
      导出数据
    */
    exportData() {
      let fileDate = new Date();
      let fileDateStr = fileDate.getFullYear() + "" + fileDate.getMonth() + "" + fileDate.getDate();
      let key = this.$refs.mainDataGrid.condition;
      let params = {
        gridName: this.tables.main.gridViewName,
        lang: "zh-cn",
        condition: key,
        filename: "ThreeLevelPlan_" + fileDateStr + ".xlsx",
        sheetname: "Result",
      };
      ExcelUtil.export(params);
    },
    //编码和标题模糊查询
    search() {
      let _self = this;
      _self.$refs.rfDg.itemDataList = [];
      let wheres = ["C_WBS_CODING", "NAME"];
      let orS = "";
      var k1 = " TYPE_NAME='计划任务' AND C_PROJECT_NAME = '@project' and SUB_TYPE='Activity'";
      if (_self.inputValueNum.trim().length > 0) {
        wheres.forEach(function (item) {
          if (orS.length > 0) {
            orS += " OR ";
          }
          orS += item + " LIKE '%" + _self.inputValueNum + "%'";
        });
        k1 += " AND (" + orS + ")";
      }
      if (
        _self.forms.headForm.project != undefined &&
        _self.forms.headForm.project.length > 0
      ) {
        k1 += " AND C_PROJECT_NAME in (" + _self.forms.headForm.project + ")";
      }
      _self.$refs.mainDataGrid.condition = k1;
      _self.$refs.mainDataGrid.currentPage = 1;
      _self.$refs.mainDataGrid.loadGridData();
    },
    //高级搜索
    searchItem() {
      let _self = this;
      _self.$refs.mainDataGrid.condition =
        _self.tables.main.condition + " and " + _self.advCondition;
      _self.advCondition = "";
      _self.$refs.mainDataGrid.loadGridInfo();
      _self.$refs.mainDataGrid.loadGridData();
      _self.$refs.ICMPass.itemDataList = [];
      _self.$refs.ICMComments.itemDataList = [];
    },
    //下拉菜单
    onSelectChange(val) {
      let _self = this;
      _self.$refs.mainDataGrid.condition =
        "TYPE_NAME='计划任务' and C_PROJECT_NAME in (" + val + ")  and SUB_TYPE='Activity'";
      _self.$refs.mainDataGrid.loadGridData();
      _self.$refs.rfDg.itemDataList = [];
    },
  },
  props: {},
  components: {
    ShowProperty: ShowProperty,
    DataGrid: DataGrid,
    DataSelect: DataSelect,
    AddCondition: AddCondition,
    DataLayout: DataLayout,
  },
};
</script>
<style scoped>
.el-form-item {
  margin-bottom: 0px;
}
</style>