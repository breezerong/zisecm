<template>
  <div id="app">
	<router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'app'
}
</script>

<style>
.el-table--striped .el-table__body tr.el-table__row--striped td{background:rgb(233, 233, 233)}.el-table--striped .el-table__body tr.el-table__row--striped.current-row td,.el-table__body tr.current-row>td,.el-table__body tr.hover-row.current-row>td,.el-table__body tr.hover-row.el-table__row--striped.current-row>td,.el-table__body tr.hover-row.el-table__row--striped>td,.el-table__body tr.hover-row>td{background-color:rgb(232, 232, 232)!important;}
.el-date-editor.el-input, .el-date-editor.el-input__inner {
    width: 140px;
}
.el-input__inner {
  height: 36px;
}
</style>
