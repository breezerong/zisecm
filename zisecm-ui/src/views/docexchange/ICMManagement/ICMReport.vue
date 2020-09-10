<template>
  <el-tabs v-model="activeName">
    <el-tab-pane label="基本接口报表" name="ICMBase">
      <ICMBaseReport></ICMBaseReport>
    </el-tab-pane>
    <el-tab-pane label="CNPE各所接口完成统计" name="CNPEComplete">
      <CNPECompleteReport></CNPECompleteReport>
    </el-tab-pane>
  </el-tabs>
</template>
<script type="text/javascript">
import { ICMBaseReport, CNPECompleteReport } from "./ICMLayout/";
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
    CNPECompleteReport,
  },
};
</script>
<style scoped>
.el-header {
  height: auto;
}
</style>