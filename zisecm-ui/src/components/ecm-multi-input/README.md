# Ecm Multi Input (多值输入)
## 使用方法
### 引用
```javascript
import MultiInput from '@components/ecm-multi-input'
```

### 使用EcmQuery方式查询
```html
<el-form v-model="form">
  <el-form-item>
    <MultiInput v-if="item.controlType=='TextBox' && item.isRepeat" v-model="item.defaultValue"></MultiInput>
  </el-form-item>
</el-form>

```
```html
</script>
```
