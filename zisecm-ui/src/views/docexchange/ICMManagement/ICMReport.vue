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
    <el-tab-pane label="CNPE各所接口完成统计" name="CNPEComplete">
      <CNPECompleteReport></CNPECompleteReport>
    </el-tab-pane>
    <el-tab-pane label="合同商接口完成统计" name="ContrICM">
      <ContactorReport></ContactorReport>
    </el-tab-pane>
  </el-tabs>
</template>
<script type="text/javascript">
import { ICMBaseReport, ICMReportGrid, ICMExchangeGrid, CNPECompleteReport, ContactorReport} from "./ICMLayout/";
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