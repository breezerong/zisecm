<template>    
    <el-container>
        <el-header>
            <el-form :inline="true" :model="forms.headForm">
                <el-form-item >
                    <DataSelect v-model="forms.headForm.project" dataUrl="/exchange/project/myproject" 
                    dataValueField="code" dataTextField="name"></DataSelect>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary">查询</el-button>
                    <el-button type="default" @click.native="exportData">Excel下载</el-button>
                </el-form-item>
            </el-form>
        </el-header>
        <el-main>
            <el-row>
                <el-col :span="24">
                    <!-- condition="FOLDER_ID IN (select ID from ecm_folder where NAME='IED' and PARENT_ID in (select ID from ecm_folder where NAME='设计分包'))" -->
                    <DataGrid ref="mainDataGrid" tableHeight="350"
                                :dataUrl="tables.main.dataUrl" :condition="tables.main.condition" :gridViewName="tables.main.gridViewName"
                                isshowOption isshowCustom></DataGrid>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-tabs v-model="tabs.active">
                        <el-tab-pane label="相关文件" name="relationFiles">
                            <DataGrid ref="rfDg" tableHeight="350"
                                :dataUrl="tables.rfDg.dataUrl" :condition="tables.rfDg.condition" :gridViewName="tables.rfDg.gridViewName"
                                isshowOption isshowCustom></DataGrid>
                        </el-tab-pane>
                        <el-tab-pane label="设计文件" name="designFile">
                            <DataGrid ref="dfDg" tableHeight="350"
                            :dataUrl="tables.dfDg.dataUrl" :condition="tables.dfDg.condition" :gridViewName="tables.dfDg.gridViewName"                                
                                isshowOption isshowCustom></DataGrid>
                        </el-tab-pane>
                        <el-tab-pane label="传递单" name="transmitals">
                            <DataGrid ref="tfDg" tableHeight="350"
                                :dataUrl="tables.tfDg.dataUrl" :condition="tables.tfDg.condition" :gridViewName="tables.tfDg.gridViewName"
                                isshowOption isshowCustom></DataGrid>
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
export default {
    name: "IEDpublished",
    data(){
        return{
            tables:{
                main:{
                    dataUrl:"/dc/getDocuments",
                    gridViewName:"IEDGrid",
                    condition:" TYPE_NAME='图纸文件' "
                },
                rfDg:{
                    gridViewName:"IEDGrid",
                    dataUrl:"/dc/getDocuments",
                    condition:""
                },
                dfDg:{
                    gridViewName:"DesignPhaseGrid",
                    dataUrl:"/dc/getDocuments",
                    condition:""
                },
                tfDg:{
                    gridViewName:"TransferGrid",
                    dataUrl:"/dc/getDocuments",
                    condition:""
                }
            },
            tabs:{
                active:"relationFiles"
            },
            forms:{
                headForm:{
                    project:""
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
        onDbRowSelect:function(val){
            this.tables.rfDg.condition="";
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
        }
    },
    props: {
        
    },
    components: {
        ShowProperty:ShowProperty,
        DataGrid:DataGrid,
        DataSelect:DataSelect
    }
}
</script>
<style scoped>
.el-header{
    height: auto;
}
</style>