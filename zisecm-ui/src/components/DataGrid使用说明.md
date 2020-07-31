# DataGrid使用说明

## DataGrid 属性

| 参数 | 说明 | 类型 | 可选值 | 默认值 |
| :--- | ---- | :--- | :----- | :----- |
|  isInitData    | 是否初始查询数据 | Boolean |        | true |
|  itemDataList    | 数据传递数组 | Array |        | null |
|  isEditProperty    | 是否可以编辑保存属性 | Boolean |        | true |
|  isshowicon    | 是否显示文件图标列 | Boolean |        | true |
|  isshowOption    | 是否显示操作列 | Boolean |        | false |
|  isshowSelection    | 是否显示数据checkbox选择列 | Boolean |        | true |
|   tableHeight   | 表高度 | [String, Number] |        | window.innerHeight - 408 |
|  tableWidth    | 表宽度 | [String, Number] |        | 100% |
|  isshowPage    | 是否显示分页 | Boolean |        | true |
| loading | 加载loading显示 | Boolean |        | false |
| gridViewName | 表头名称 | String |        | ‘’ |
| isshowCustom | 是否显示自定义表头功能 | Boolean |        | false |
| condition | 查询条件 | String |        | ‘’ |
| dataUrl | 为后台传递查询链接 | String |        | ‘’ |
| parentId |      | String |        | ‘’ |
| isShowMoreOption | 是否显示功能菜单 | Boolean |        | true |
| isShowPropertyButton | 是否显示属性按钮 | Boolean |        | true |
| showOptions | 功能菜单显示控制 | String |  | 查看内容,查看属性,加入购物车,升版 |
| isShowChangeList | 是否显示列表选择 | Boolean | | true |



## DataGrid 事件

| 事件名称        | 事件说明                      | 参数                  |
| --------------- | ----------------------------- | --------------------- |
| upgradeFun      |                               | id                    |
| refreshdatagrid | 更新后刷新dataGrid事件        | -                     |
| pagesizechange  | 页数变更事件                  | pageSize              |
| rowclick        | 行单击事件                    | row                   |
| dbclick         | 行双击事件                    | row                   |
| selectchange    | 表格中单行的CheckBox 选择事件 | val                   |
| cellMouseEnter  | 鼠标移入事件                  | row,column,cell,event |
| cellMouseLeave  | 鼠标移除事件                  | row,column,cell,event |



##  DataGrid 插槽

| 名称         | 说明           |
| ------------ | -------------- |
| sequee       | 自定义列序号   |
| optionButton | 自定义功能菜单 |



# 举个栗子

```vue
<template>
	<DataGrid v-bind="table.attrs" @rowclick="onRowClick"></DataGrid>
</template>
<script>
    import DataGrid from '@/components/DataGrid'
    export default{
        compnents:{DataGrid},
        data(){
            return{
                table:{
                    attrs:{
                        isInitData:true,
                        dataUrl:"/dc/getDocuments",
                        condition:"TYPE_NAME='IED'"
                    }
                }
            }
        },
        methods:{
            onRowClick(row){
                console.log(row)
            }
        }
    }
</script>
```

