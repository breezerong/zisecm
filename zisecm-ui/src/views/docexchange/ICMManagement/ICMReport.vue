<template>
  <DataLayout>
    <template v-slot:header style="height: auto"></template>
    <template v-slot:main>
      <el-tabs v-model="activeName">
        <el-tab-pane label="基本接口报表" name="ICMBase">
          <ICMBaseReport ></ICMBaseReport>
        </el-tab-pane>
        <el-tab-pane label="ICM手册导出报表" name="ICMExport">
          <ICMReportP ></ICMReportP>
        </el-tab-pane>
        <el-tab-pane label="ICM接口及交换信息完整清单" name="ICMExchange">
          <ICMExchangeP ></ICMExchangeP>
        </el-tab-pane>
        <el-tab-pane label="接口打开完成情况报表" name="ICMOpen">
          <ICMOpenP ></ICMOpenP>
        </el-tab-pane>
        <el-tab-pane label="接口关闭完成情况报表" name="ICMClose">
          <ICMCloseP ></ICMCloseP>
        </el-tab-pane>
        <el-tab-pane label="接口回复完成情况报表" name="ICMReply">
          <ICMReplyP ></ICMReplyP>
        </el-tab-pane>
        <el-tab-pane label="CNPE各所接口完成统计" name="CNPEComplete">
          <CNPECompleteReport ></CNPECompleteReport>
        </el-tab-pane>
        <el-tab-pane label="合同商接口完成统计" name="ContrICM">
          <ContactorReport ></ContactorReport>
        </el-tab-pane>
      </el-tabs>
    </template>
  </DataLayout>
</template>
<script type="text/javascript">
import {
  ICMBaseReport,
  ICMReportP,
  ICMExchangeP,
  ICMOpenP,
  ICMCloseP,
  ICMReplyP,
  CNPECompleteReport,
  ContactorReport,
} from "./ICMLayout/";
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
    ICMReportP,
    ICMExchangeP,
    ICMOpenP,
    ICMCloseP,
    ICMReplyP,
    CNPECompleteReport,
    ContactorReport,
  },
};
</script>
<style scoped>
.el-form-item {
  margin-bottom: 0px;
}
</style>