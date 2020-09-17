<template>
  <el-tabs v-model="activeName">
    <el-tab-pane label="基本接口报表" name="ICMBase">
      <ICMBaseReport></ICMBaseReport>
    </el-tab-pane>
    <el-tab-pane label="ICM手册导出报表" name="ICMExport">
      <ICMReportGrid></ICMReportGrid>
    </el-tab-pane>
    <el-tab-pane label="ICM接口及交换信息完整清单" name="ICMExchange">
      <ICMExchangeGrid></ICMExchangeGrid>
    </el-tab-pane>
    <el-tab-pane label="接口打开完成情况报表" name="ICMOpen">
      <ICMOpenGrid></ICMOpenGrid>
    </el-tab-pane>
    <el-tab-pane label="接口关闭完成情况报表" name="ICMClose">
      <ICMCloseGrid></ICMCloseGrid>
    </el-tab-pane>
    <el-tab-pane label="CNPE各所接口完成统计" name="CNPEComplete">
      <CNPECompleteReport></CNPECompleteReport>
    </el-tab-pane>
    <el-tab-pane label="合同商接口完成统计" name="ContrICM">
      <ContactorReport></ContactorReport>
    </el-tab-pane>
  </el-tabs>
</template>
<script type="text/javascript">
import { ICMBaseReport, ICMReportGrid, ICMExchangeGrid, ICMOpenGrid, ICMCloseGrid, CNPECompleteReport, ContactorReport} from "./ICMLayout/";
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