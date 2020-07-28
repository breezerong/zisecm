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
                    <el-button @click="save()">{{$t('application.SearchData')}}</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button @click="save()">保存</el-button>
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
                    list:[

                    ]
                }
            },
            form:{}
        }
    },
    mounted(){
        console.log(this.table);
    },
    methods:{
        save(){
            console.log("save");
            console.log(this.form);

        }
    }
}
</script>

<style scoped>
.el-header{
    background-color: #ffffff;
    color: #333;
    text-align: left;
    padding: 0px;
    margin-top: 0px;
    margin-left: 0px;
    margin-right: 0px;
    margin-bottom: 0px;
    height: auto;
  }
</style>