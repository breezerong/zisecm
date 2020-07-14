# Ecm Data Layout(数据布局)
## 使用方法
### 引用
```javascript
import DataLayout from '@components/ecm-data-layout'
```

### 使用方式
```html
<DataLayout ref="dataLayout">
  <template v-slot:header>
  </template>
  <template v-slot:main="{layout}">
      <DataGrid :tableHeight="layout.height-180"></DataGrid>
  </template>
</DataLayout>
```

### slot具名说明
|属性名称|属性说明|举例|
|---|---|---|
|header|页首||
|main|内容||

### 事件
|事件名称|事件说明|事件参数|
|---|---|---|
|onLayoutResize|大小变更事件|size 被选值 onLayoutResize(size)|