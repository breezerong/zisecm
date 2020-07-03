<template>    
    <el-container>
        <el-header></el-header>
        <el-main>
            <DataGrid ref="mainDataGrid" key="main" 
            :itemDataList="tables.main.datalist"
            ></DataGrid>
        </el-main>
    </el-container>
</template>
<script type="text/javascript">
import ShowProperty from "@/components/ShowProperty";
import DataGrid from "@/components/DataGrid";
export default {
    name: "IEDpublished",
    data(){
        return{
            tables:{
                main:{
                    gridName:"IEDGrid",
                    dataList:[]
                }
            }
        }
    },
    created(){

    },
    mounted(){
        if(!this.validataPermission()){
            //跳转至权限提醒页
            let _self=this;
            _self.$nextTick(()=>{
                _self.$router.push({ path: '/NoPermission' })
            })
            
        }
    },
    methods: {

    },
    props: {
        
    },
    components: {
        ShowProperty:ShowProperty,
        DataGrid:DataGrid
    }
}
</script>
<style scoped>
.el-header{
    height: auto;
}
</style>