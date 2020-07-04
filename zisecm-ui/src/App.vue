<template>
  <div id="app" v-title :data-title="$t('application.name')">
    <transition name="rotate-fall">
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
  background-color: rgb(187, 187, 187) !important;
}
.el-date-editor.el-input,
.el-date-editor.el-input__inner {
  width: 140px;
}
.el-table td,.el-table th{
  padding:4px 0 !important;
}
.el-input__inner {
  height: 36px;
}
/* .el-tree-node.is-current{
  color: #ffffff !important;
} */
.el-tree-node:focus > .el-tree-node__content {
    background-color: #92c6fd;
    font-weight:bold;
    color: #000000 !important;
}
.el-main {
    padding: 2px !important;
  }
</style>
