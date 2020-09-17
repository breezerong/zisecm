<template>
<DataLayout>
    <template v-slot:header style="height: auto"></template>
    <template v-slot:main="{layout}">
  <el-tabs v-model="activeName">
    <el-tab-pane label="基本接口报表" name="ICMBase">
      <ICMBaseReport :tableHeight="layout.height-210"></ICMBaseReport>
    </el-tab-pane>
    <el-tab-pane label="ICM手册导出报表" name="ICMExport">
      <ICMReportGrid :tableHeight="layout.height-210"></ICMReportGrid>
    </el-tab-pane>
    <el-tab-pane label="ICM接口及交换信息完整清单" name="ICMExchange">
      <ICMExchangeGrid :tableHeight="layout.height-210"></ICMExchangeGrid>
    </el-tab-pane>
    <el-tab-pane label="接口打开完成情况报表" name="ICMOpen">
      <ICMOpenGrid :tableHeight="layout.height-210"></ICMOpenGrid>
    </el-tab-pane>
    <el-tab-pane label="接口关闭完成情况报表" name="ICMClose">
      <ICMCloseGrid :tableHeight="layout.height-210"></ICMCloseGrid>
    </el-tab-pane>
    <el-tab-pane label="CNPE各所接口完成统计" name="CNPEComplete">
      <CNPECompleteReport :tableHeight="layout.height-210"></CNPECompleteReport>
    </el-tab-pane>
    <el-tab-pane label="合同商接口完成统计" name="ContrICM">
      <ContactorReport :tableHeight="layout.height-210"></ContactorReport>
    </el-tab-pane>
  </el-tabs>
    </template>
</DataLayout>
</template>
<script type="text/javascript">
import { ICMBaseReport, ICMReportGrid, ICMExchangeGrid, ICMOpenGrid, ICMCloseGrid, CNPECompleteReport, ContactorReport} from "./ICMLayout/";
import DataLayout from "@/components/ecm-data-layout";
export default {
  name: "ICMReport",
  data() {
    return {
      activeName: "ICMBase",
    };
  },

  methods: {},

  props: {},
  components: {
    DataLayout: DataLayout,
    ICMBaseReport,
    ICMReportGrid,
    ICMExchangeGrid,
    ICMOpenGrid,
    ICMCloseGrid,
    CNPECompleteReport,
    ContactorReport,
  },
};
</script>
<style scoped>
.el-header {
  height: auto;
}
</style>