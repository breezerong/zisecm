<template>
  <div id="app" v-title :data-title="$t('application.name')">
    <transition name="moveToTop">
    <router-view></router-view>
    </transition>
  </div>
</template>

<script>
import 'vue-transition.css'
export default {
  name: "app"
};
</script>

<style>
.el-table--striped .el-table__body tr.el-table__row--striped td {
  background: rgb(233, 233, 233);
}
.el-table--striped .el-table__body tr.el-table__row--striped.current-row td,
.el-table__body tr.current-row > td,
.el-table__body tr.hover-row.current-row > td,
.el-table__body tr.hover-row.el-table__row--striped.current-row > td,
.el-table__body tr.hover-row.el-table__row--striped > td,
.el-table__body tr.hover-row > td {
  background-color: #ebe6de !important;
}
.el-date-editor.el-input,
.el-date-editor.el-input__inner {
  width: 140px;
}
.el-date-editor.el-input__inner {
  padding-left: 25px !important;
}
.el-table td{
  padding:1px 0 !important;
}
.el-form-item {
  margin-top: 3px !important;
  margin-bottom: 2px !important;
}
.el-input__inner {
  height: 36px;
  padding-left: 10px;
}
.el-table th {
  padding:1px 0 !important;
  background-color: #eef1f6 !important;
}
.el-dialog__body {
    padding: 5px 20px !important;
}
.el-dialog__header {
    padding: 10px 10px !important;
}
.el-dialog__footer {
    padding: 10px 10px !important;
} 
.el-tree-node:focus > .el-tree-node__content {
    background-color: #92c6fd;
    font-weight:bold;
    color: #000000 !important;
}
.el-main {
    padding: 2px !important;
  }
  .el-header {
    padding: 0 15px !important;
    box-sizing: border-box;
}
.el-tabs__header {
    padding: 0;
    position: relative;
    margin: 0 0 2px !important;
}
.el-card__header {
    padding: 8px 5px !important;
    border-bottom: 1px solid #ebeef5;
    box-sizing: border-box;
}
.el-button {
    padding: 12px 12px !important;
}
</style>
