# 组件继承
## 使用场景
    有多个页面或组件的vue文件，拥有很多相似甚至相同的页面内容或功能，只是个别数据及显示内容不同
    例如项目管理、用户管理、部门管理，都有添加、修改、删除和查询的基本功能，包含了表单、表格查询分页等相同的功能
## 组件基类
BaseCurd.vue
~~~ html
<template>
    <el-container style="padding:5px;">
        <el-header style="height:auto;">
            <el-form :inline="true" size="mini">
                <el-form-item prop="abc" label="ABC">
                    <el-input></el-input>
                </el-form-item>
                 <el-form-item prop="abc" label="ABC">
                    <el-input></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button @click="query()">查询</el-button>
                </el-form-item>                
            </el-form>
        </el-header>
        <el-main>
            <slot></slot>
            <el-table size="mini">
                <el-table-column prop="name" label="A"></el-table-column>
                <el-table-column prop="name" label="B"></el-table-column>
                <el-table-column prop="name" label="C"></el-table-column>
            </el-table>
        </el-main>
    </el-container>
</template>

<script>
export default {
    data(){
        return {
            table:{
                header:{
                    url:"",
                    list:[
                        {id:"1",prop:"name",label:"姓名"}
                    ]
                },
                data:{
                    url:"",
                    list:[]
                }
            },
            form:{}
        }
    },
    mounted(){
        console.log("BaseCrud");
        console.log(this.table);
    },
    methods:{
        query(){
            console.log("query");
            console.log(this.form);
        }
    }
}
</script>
~~~

简单定义一个表单和一个表格，定义一个较为普遍的组件基类一个集成CRUD的全部功能组件页面
包括表头table.header，其中url是获取表头信息的地址，list是用于获取表头信息
表数据table.data,url是获取表数据地址内容，list是用于获取表数据
form是用于存放表单数据

在el-main中临时先放入了一个插槽[slot](https://cn.vuejs.org/v2/guide/components-slots.html)
## 1. vue文件继承方式
ProjectTable.vue
~~~ html
<template>
    <div>
    <!-- 插槽显示内容，如果没有可以不用输出template -->
    </div>
</template>
<script>
/*
1.引入继承组件：相当于java类里在package下面import指定类
*/
import BaseCurd from './BaseCurd.vue' 
export default{
    name:'ProjectTable',//组件名称
    extends: BaseCurd, // 2. 继承组件
}
</script>
~~~ 

## 2. js继承方式

    组件不单单只是体现在Vue文件中还可以直接在js中直接继承
    继承的写法同上面


## 3. 备注
当前相应目录下有测试的vue文件仅供参考