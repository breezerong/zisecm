# JS服务定义
## 文件说明
### base.js
  定义服务基类
  除了默认BaseService以外还可以定义其它服务的基类
### UserService.js
  以base.js中定义的BaseService作为基类继承的子类
  
## 服务使用
```javascript
import userService from '@/service/UserService.js'

export default{
  mounted(){
    userService.save({name:"abc"},function(result){
      console.log(result);
    })
  }
}
```
  
