# 监听语言切换

语言保存路径 src/store/modules/app.js

```javascript
const app = {
    state:{
        language:Cookies.get('language') || 'zh' //从cookeies中获取默认为zh
        //其它值忽略
    },
    mutations:{
        //略
    },
    actions:{
         //略
    }
}
```

获取language的值方法是

```javascript
this.$store.state.app.language
```

## watch监听

```vue
<script>
export default{
    watch:{
        /**
        *'$store.state.app.language' 获取语言
        * newValue : 新值
        * oldValue ：旧值
        */
        '$store.state.app.language':function(newValue,oldValue){
            //DataGrid处理方法
            this.currentLanguage=newValue
            this.refs.mainTable.loadGridInfo()
            //自定义表处理方法
            this.table.columns=[
                {prop:"name",label:this.$t('application.name')}
                //....
            ]
        }
    }
}
</script>
```

