<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
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
.el-menu-item.is-active {
  color: #000000 !important;
  font-weight:bold;
  background-color: #92c6fd !important;
}
.router-link-active {
  color: #000000 !important;
}
</style>
