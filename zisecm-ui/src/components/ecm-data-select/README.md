# Ecm Data Select(数据选择器)
## 使用方法
### 引用
```javascript
import SQLSelect from '@components/ecm-data-select'
```

### 使用EcmQuery方式查询
```html
<el-form v-model="form">
  <el-form-item>
    <SQLSelect :model="form.item" queryName="表格列表"></SQLSelect>
  </el-form-item>
</el-form>

```

### 使用自定义URL方式查询
```html
<el-form v-model="form">
  <el-form-item>
    <SQLSelect :model="form.item" dataUrl="/admin/uiralation/list" :dataObj="dataObj" dataValueField="NAME" dataTextField="NAME" @onSelectChange="onSelectChange"></SQLSelect>
  </el-form-item>
</el-form>

```
```javascript
export default{
  data(){
    return{
      dataObj:{//查询条件

      }
    }
  },
  methods:{
    onSelectChange:function(val){

    }
  }
}
```

### 输入属性
|属性名称|属性说明|默认值|
|---|---|---|
|queryName|查询名称|-|
|dataUrl|查询地址|-|
|dataObj|查询参数，配合dataUrl，具体dataObj的使用需要以dataUrl调用的后台来决定|-|
|dataValueField|dataUrl查询中定义option的value字段名|-|
|dataTextField|dataUrl查询中定义option的显示字段名|-|
|includeAll|默认全选，并将全部内容作为第一选项|false|
|allDataOptionText|默认全选显示内容|-|
|defaultIsNull|默认全选显示内容|false|

### 事件
|事件名称|事件说明|事件参数|
|---|---|---|
|onSelectChange|选择修改事件|val 被选值 onSelectChange(val)|


### 完整例子

```html
<template>
  <el-form v-model="form">
    <el-form-item>
      <SQLSelect :model="form.item" queryName="表格列表" @onSelectChage="handlerSelect"></SQLSelect>
    </el-form-item>
</el-form>
</template>
</script>
```
```javascript
import SQLSelect from '@components/ecm-data-select'
export default{
  components:{SQLSelect},
  data(){
    return {
      form:{
        item:""
      }
    }
  },
  methods:{
    handlerSelect(val){
      console.log(val)
    }
  }
}
```
```html
</script>
```
