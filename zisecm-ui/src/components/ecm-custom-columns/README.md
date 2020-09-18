# Ecm Custom Columns(自定义列表)
## 使用方法
### 引用

```html
<script>
import EcmCustomColumns from '@components/ecm-custom-columns'
export default{
  components:{
    EcmCustomColumns
  }
}
</script>
```
### 使用方式
```html
<EcmCustomColumns @onClose="vs = false">
</EcmCustomColumns>
```

### 属性
|名称|说明|类型|
|---|---|---|
|gridViewName|列表名称|String|
|sysColumnInfo|系统列表信息|Array []|

### 事件
|事件名称|事件说明|事件参数|
|---|---|---|
|onClose|界面关闭，在点击保存和取消时被触发||