# Vue3调研
## 1.app的创建
```javascript
// src/main.js
import { createApp } from "vue";
const app = createApp({
  //定义
});

app.mount("#app");
```

## 2.state属性
```javascript
// src/main.js
import { createApp } from "vue";
const app = createApp({
  data: () => ({
    
  })
});

app.mount("#app");
```
## 3.多根模版
```vue
// src/Main.vue
<template>
  <button>ABC</button>
  <div>
    test
  </div>
</template>
```
不再强制要求一个单元的根元素

## 4.emits声明事件
```vue
<template></template>
<script>
  export default{
    emits:["close"]
  }
</script>
```
除了提供自说明代码外，你还可以使用事件声明来验证你的事件有效载荷，虽然我在这个例子中找不到理由来验证。
