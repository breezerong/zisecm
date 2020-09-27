<template>
  <DataLayout>
    <template v-slot:header style="height: auto"></template>
    <template v-slot:main>
      <el-tabs v-model="activeName">
        <el-tab-pane :label="$t('route.ICMBase')" name="ICMBase">
          <ICMBaseReport ></ICMBaseReport>
        </el-tab-pane>
        <el-tab-pane :label="$t('route.ICMExport')" name="ICMExport">
          <ICMReportP ></ICMReportP>
        </el-tab-pane>
        <el-tab-pane :label="$t('route.ICMExchange')" name="ICMExchange">
          <ICMExchangeP ></ICMExchangeP>
        </el-tab-pane>
        <el-tab-pane :label="$t('route.ICMOpen')" name="ICMOpen">
          <ICMOpenP ></ICMOpenP>
        </el-tab-pane>
        <el-tab-pane :label="$t('route.ICMClose')" name="ICMClose">
          <ICMCloseP ></ICMCloseP>
        </el-tab-pane>
        <el-tab-pane :label="$t('route.ICMReply')" name="ICMReply">
          <ICMReplyP ></ICMReplyP>
        </el-tab-pane>
        <el-tab-pane :label="$t('route.CNPEComplete')" name="CNPEComplete">
          <CNPECompleteReport ></CNPECompleteReport>
        </el-tab-pane>
        <el-tab-pane :label="$t('route.ContrICM')" name="ContrICM">
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