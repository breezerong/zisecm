<template>    
    <el-container>
        <el-header>
            <el-form :inline="true" :model="forms.headForm">
                <el-form-item >
                    <DataSelect v-model="forms.headForm.project" dataUrl="/exchange/project/myproject" 
                    dataValueField="code" dataTextField="name"></DataSelect>
                    <DataSelect v-model="forms.headForm.project" dataUrl="/exchange/project/myproject" 
                    dataValueField="code" dataTextField="name"></DataSelect>
                </el-form-item>
                <el-form-item>
                    <el-input style="width:200px" v-model="inputValueNum" placeholder="请输入WBS编码或标题"></el-input>
                    <el-button type="primary" @click="search()">查询</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="default" @click.native="exportData">Excel下载</el-button>
                </el-form-item>
                <el-form-item>
                    <AddCondition v-bind:typeName="typeName" :inputType='hiddenInput'></AddCondition>
                </el-form-item>
            </el-form>
        </el-header>
        <el-main>
            <el-row>
                <el-col :span="24">
                    <DataGrid ref="mainDataGrid" v-bind="tables.main" @rowclick="onDataGridRowClick"></DataGrid>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-tabs v-model="tabs.active">
                        <el-tab-pane label="相关文件" name="relationFiles">
                            <DataGrid ref="rfDg" v-bind="tables.rfDg"></DataGrid>
                        </el-tab-pane>
                        <el-tab-pane label="设计文件" name="designFile">
                            <DataGrid ref="dfDg"  v-bind="tables.dfDg"></DataGrid>
                        </el-tab-pane>
                        <el-tab-pane label="传递单" name="transmitals">
                            <DataGrid ref="tfDg"  v-bind="tables.tfDg"></DataGrid>
                        </el-tab-pane>
                    </el-tabs>
                </el-col>
            </el-row>
        </el-main>
    </el-container>
</template>
<script type="text/javascript">
import ShowProperty from "@/components/ShowProperty";
import DataGrid from "@/components/DataGrid";
import DataSelect from '@/components/ecm-data-select'
import ExcelUtil from '@/utils/excel.js'
import AddCondition from '@/views/record/AddCondition.vue'
export default {
    name: "IEDpublished",
    data(){
        return{
            tables:{
                main:{
                    gridViewName:"IEDGrid",
                    dataUrl:"/dc/getDocuments",
                    condition:" TYPE_NAME='图纸文件' ",
                    //condition="FOLDER_ID IN (select ID from ecm_folder where NAME='IED' and PARENT_ID in (select ID from ecm_folder where NAME='设计分包'))"
                    isshowOption:true,
                    isshowCustom:true,
                    tableHeight:"350"
                },
                rfDg:{
                    gridViewName:"IEDGrid",
                    dataUrl:"/dc/getDocuments",
                    condition:"",
                    isshowOption:true,
                    isshowCustom:true,
                    tableHeight:"350"
                },
                dfDg:{
                    gridViewName:"DrawingGrid",
                    dataUrl:"/dc/getDocuments",
                    condition:"",
                    isshowOption:true,
                    isshowCustom:true,
                    tableHeight:"350"
                },
                tfDg:{
                    gridViewName:"TransferGrid",
                    dataUrl:"/dc/getDocuments",
                    condition:"",
                    isshowOption:true,
                    isshowCustom:true,
                    tableHeight:"350"
                },
            },
            tabs:{
                active:"relationFiles"
            },
            forms:{
                headForm:{
                    project:"",
                }
            },
            inputValueNum:'',
            hiddenInput:'hidden',
            typeName:"IED"
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
        onDataGridRowClick:function(row){
            console.log(row)
            
            this.$refs.rfDg.loadGridInfo()
            this.$refs.rfDg.loadGridData()

            this.$refs.dfDg.gridViewName='DesignPhaseGrid'
            this.$refs.dfDg.loadGridInfo()
            this.$refs.dfDg.loadGridData()
            
            this.$refs.tfDg.loadGridInfo()
            this.$refs.tfDg.loadGridData()
        },
        exportData(){
            let dataUrl = "/exchange/doc/export"
            let params = {
                gridName:this.tables.main.gridViewName,
                lang:"zh-cn",
                condition:this.tables.main.condition,
                filename:"abc.xlsx",
                sheetname:"Result"
            }
            ExcelUtil.export(params)
        },
        search(){
            let _self = this
            
            var k1="TYPE_NAME='IED' AND (TITLE LIKE '%"+this.inputValueNum+"%' OR C_WBS_CODING LIKE '%"+this.inputValueNum+"%')"
            _self.$refs.mainDataGrid.condition=k1
            _self.$refs.mainDataGrid.loadGridData();
        }
    },
    props: {

    },
    components: {
        ShowProperty:ShowProperty,
        DataGrid:DataGrid,
        DataSelect:DataSelect,
        AddCondition:AddCondition
    }
}
</script>
<style scoped>
.el-header{
    height: auto;
}
</style>