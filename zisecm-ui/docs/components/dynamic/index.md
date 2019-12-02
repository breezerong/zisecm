# [动态组件](https://cn.vuejs.org/v2/guide/components.html#动态组件)
## 使用场景
    在一个界面中需要切换不同的组件显示数据内容
    例如：查看文档中文档属性、文档版本、文档关联等，需要切换不同界面

    以前的做法是在dialog中加入iframe或其它手法来引入界面内容
    现在只是通过<component>标签来引入组件即可

## component标签

~~~ html
<template>
    <div>
        <component v-bind:is="currentComponent"></component>
        <button @click="changeView('A')">切换到A</button>
        <button @click="changeView('B')">切换到B</button>
        <button @click="changeView('C')">切换到C</button>
    </div>
</template>
<script>
import comA from './ComA'
import comB from './ComB'
import comC from './ComC'
export default{
    data(){
        return {
            currentComponent: 'comA' //默认组件
        }
    },
    methods:{
        changeView(txt){
            this.currentView = 'com'+ txt //动态地改变currentView的值就可以动态挂载组件了
        }
    },
    components:{
        comA,
        comB,
        comC
    }
}
</script>
~~~

## keep-alive
### 动态组件的问题
    在每次切换组件时组件就会重新加载、渲染并获取数据，如果每个组件在created时都会重新获取数据的话，那么在系统在有一定量的用户群后就会加大数据库的访问压力。
    从官方的说法是： https://cn.vuejs.org/v2/cookbook/avoiding-memory-leaks.html
    简单的说会用以导致内存泄漏，俗称浏览器崩溃
### 处理方法
~~~ html
<keep-alive>
    <component v-bind:is="currentComponent"></component>
</keep-alive>
~~~
