<template>
    <DataLayout>
        <template v-slot:header>
            <el-form :inline="true" :model="forms.headForm">
                <el-form-item >
                    <DataSelect v-model="forms.headForm.project" dataUrl="/exchange/project/myproject" dataValueField="name" dataTextField="name" includeAll></DataSelect>                  
                </el-form-item>
                <el-form-item>
                    <el-input style="width:200px" v-model="inputValueNum" placeholder="外部编码、内部编码和中文标题"></el-input>
                    <el-button type="primary" @click="search()">查询</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="default" @click.native="exportData">Excel下载</el-button>
                </el-form-item>
                <el-form-item>
                    <AddCondition v-bind:typeName="typeName" :inputType='hiddenInput' @change="onSearchConditionChange"></AddCondition>
                </el-form-item>
            </el-form>
        </template>
        <template v-slot:main="{layout}">
            <el-row>
                <el-col :span="24">
                    <DataGrid ref="mainDataGrid" v-bind="tables.main"  :tableHeight="layout.height/2-115" @rowclick="onDataGridRowClick"></DataGrid>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-tabs v-model="tabs.active">
                        <el-tab-pane label="相关文件" name="relationFiles">
                            <DataGrid ref="rfDg" v-bind="tables.rfDg" :tableHeight="layout.height/2-155"></DataGrid>
                        </el-tab-pane>
                        <el-tab-pane label="设计文件" name="designFile">
                            <DataGrid ref="dfDg"  v-bind="tables.dfDg" :tableHeight="layout.height/2-155"></DataGrid>
                        </el-tab-pane>
                        <el-tab-pane label="传递单" name="transmitals">
                            <DataGrid ref="tfDg"  v-bind="tables.tfDg" :tableHeight="layout.height/2-155"></DataGrid>
                        </el-tab-pane>
                    </el-tabs>
                </el-col>
            </el-row>
        </template>
    </DataLayout>
</template>
<script type="text/javascript">
import ShowProperty from "@/components/ShowProperty";
import DataGrid from "@/components/DataGrid";
import DataSelect from '@/components/ecm-data-select'
import DataLayout from '@/components/ecm-data-layout'
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
                    condition:"  TYPE_NAME='IED' and IS_CURRENT=1 and C_IS_RELEASED=1",                    
                    isshowOption:true,
                    isshowCustom:true,
                    isshowicon:false,
                    isInitData:false
                },
                rfDg:{
                    gridViewName:"IEDGrid",
                    dataUrl:"/dc/getDocuments",
                    condition:"",
                    isshowOption:true,
                    isshowCustom:true,
                    isInitData:false,
                    isshowicon:false
                },
                dfDg:{
                    gridViewName:"DrawingGrid",
                    dataUrl:"/dc/getDocuments",
                    condition:"",
                    isshowOption:true,
                    isshowCustom:true,
                    isInitData:false,
                    isshowicon:false
                },
                tfDg:{
                    gridViewName:"TransferGrid",
                    dataUrl:"/dc/getDocuments",
                    condition:"",
                    isshowOption:true,
                    isshowCustom:true,
                    isInitData:false,
                    isshowicon:false,
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
    mounted(){
        this.search()
    },
    methods: {
        onSearchConditionChange:function(val){
            console.log(val)
        },
        onDataGridRowClick:function(row){
            
           // this.$refs.rfDg.loadGridInfo()
           // this.$refs.rfDg.loadGridData()

            this.tables.dfDg.condition="CODING = '"+row.CODING+"'"
            this.$refs.dfDg.condition=this.tables.dfDg.condition
            this.$refs.dfDg.itemDataList=[]
            this.$refs.dfDg.loadGridInfo()
            this.$refs.dfDg.loadGridData()

            let dfDGCondition ="select C_REF_CODING from ecm_document where TYPE_NAME='设计文件' and "+ this.tables.dfDg.condition;
            this.tables.tfDg.condition = "CODING IN ("+ dfDGCondition+")"
            this.$refs.tfDg.condition= this.tables.tfDg.condition
            this.$refs.tfDg.itemDataList=[]
            this.$refs.tfDg.loadGridInfo()
            this.$refs.tfDg.loadGridData()
        },
        exportData(){
            let dataUrl = "/exchange/doc/export"
            var fileDate = new Date()
            let fileDateStr = fileDate.getFullYear()+""+fileDate.getMonth()+""+ fileDate.getDate()
            let params = {
                gridName:this.tables.main.gridViewName,
                lang:"zh-cn",
                condition: this.$refs.mainDataGrid.condition,
                filename:"IED_Published_"+fileDateStr+".xlsx",
                sheetname:"Result"
            }
            ExcelUtil.export(params)
        },
        search(){
            let _self = this
            let wheres = ["TITLE","C_WBS_CODING","CODING","C_IN_CODING"]
            let orS = ""
            var k1=" TYPE_NAME='IED' and IS_CURRENT=1 and C_IS_RELEASED=1 "
            if(_self.inputValueNum.trim().length>0){
                wheres.forEach(function(item){
                    if(orS.length>0){
                        orS+=" OR "
                    }
                    orS+=item + " LIKE '%"+ _self.inputValueNum+"%'"
                })
                k1+=" AND (" + orS + ")"
            }

            if(_self.forms.headForm.project != undefined && _self.forms.headForm.project.length>0){
                k1+=" AND C_PROJECT_NAME in ("+_self.forms.headForm.project +")"
            }

            let user = this.currentUser();
            if(user.userType==2 && user.company!=null){
                k1+=" AND C_COMPANY='"+user.company +"'"
            }

            console.log(k1)
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
        AddCondition:AddCondition,
        DataLayout:DataLayout
    }
}
</script>