<template>
    <DataLayout>
        <template v-slot:header>
            <el-form :inline="true" :model="forms.headForm">
                <el-form-item >
                    <DataSelect @onSelectChange='onSelectChange' v-model="forms.headForm.project"  includeAll dataUrl="/exchange/project/myproject" 
                    dataValueField="name" dataTextField="name"></DataSelect>
                </el-form-item>
                <el-form-item>
                    <el-input style="width:200px" v-model="inputValueNum" placeholder="请输入WBS编码或名称"></el-input>
                    <el-button type="primary" @click="search()">{{$t('application.SearchData')}}</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="default" @click.native="exportData">{{$t('application.ExportExcel')}}</el-button>
                </el-form-item>
                <el-form-item>
                    <AddCondition @sendMsg='searchItem' v-model="advCondition" v-bind:typeName="typeName" :inputValue="advCondition" :inputType='hiddenInput'></AddCondition>
                </el-form-item>
            </el-form>
        </template>
        <template v-slot:main="{layout}">
            <el-row>
                <el-col :span="24">
                    <DataGrid ref="mainDataGrid" v-bind="tables.main" :tableHeight="layout.height/2-115" @rowclick="onDataGridRowClick"></DataGrid>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-tabs v-model="tabs.active">
                        <el-tab-pane label="相关IED" name="relationFiles">
                            <DataGrid ref="rfDg" v-bind="tables.rfDg" :tableHeight="layout.height/2-155"></DataGrid>
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
import ExcelUtil from '@/utils/excel.js'
import AddCondition from '@/views/record/AddCondition.vue'
import DataLayout from '@/components/ecm-data-layout'
export default {
    name: "ThreeLevelPlan",
    data(){
        return{
            tables:{
                main:{
                    gridViewName:"PlanTaskGrid",
                    dataUrl:"/dc/getDocuments",
                    condition:"TYPE_NAME='计划任务' AND C_PROJECT_NAME = '@project'",
                    isshowOption:true,
                    isshowCustom:true,
                },
                rfDg:{
                    gridViewName:"IEDGrid",
                    dataUrl:"/dc/getDocuments",
                    condition:"",
                    isshowOption:true,
                    isshowCustom:true,
                    isInitData:false,
                    isshowicon:false,
                    tableHeight:"350"
                }
            },
            tabs:{
                active:"relationFiles"
            },
            forms:{
                headForm:{
                    project:""
                }
            },
            advCondition:'',
            inputValueNum:'',
            hiddenInput:'hidden',
            typeName:"计划任务",
        }
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
            this.$refs.rfDg.condition="C_WBS_CODING='"+row.C_WBS_CODING+"' AND C_PROJECT_NAME = '@project' and C_COMPANY='@company'"
            this.$refs.rfDg.loadGridInfo()
            this.$refs.rfDg.loadGridData()
            //this.$alert(row);
        },
        exportData(){
            let dataUrl = "/exchange/doc/export"
            var fileDate = new Date()
            let fileDateStr = fileDate.getFullYear()+""+fileDate.getMonth()+""+ fileDate.getDate()
            let params = {
                gridName:this.tables.main.gridViewName,
                lang:"zh-cn",
                condition: this.$refs.mainDataGrid.condition,
                condition:this.tables.main.condition,
                filename:"ThreeLevelPlan_"+fileDateStr+".xlsx",
                sheetname:"Result"
            }
            ExcelUtil.export(params)
        },
        //编码和标题模糊查询
        search(){
            let _self = this
            _self.$refs.rfDg.itemDataList=[]
            let wheres = ["C_WBS_CODING","NAME"]
            let orS = ""
            var k1=" TYPE_NAME='计划任务' AND C_PROJECT_NAME = '@project'"
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

            _self.$refs.mainDataGrid.condition=k1
            // _self.$alert(_self.$refs.mainDataGrid.condition)
           
            _self.$refs.mainDataGrid.loadGridData();
        },
        //高级搜索
        searchItem(){
            let _self = this
            let key="";
            key = _self.advCondition;
            _self.$refs.mainDataGrid.condition=key+" AND C_PROJECT_NAME='@project'";
            // _self.$alert(_self.$refs.mainDataGrid.condition)
            _self.$refs.mainDataGrid.loadGridInfo()
            _self.$refs.mainDataGrid.loadGridData()
            _self.$refs.rfDg.itemDataList=[]
        },
        //下拉菜单
        onSelectChange(val){
            let _self = this
            // _self.$alert(val)
            _self.$refs.mainDataGrid.condition="TYPE_NAME='计划任务' and C_PROJECT_NAME in ("+val+")";
            // _self.$alert(_self.$refs.mainDataGrid.condition)
            _self.$refs.mainDataGrid.loadGridData();
            _self.$refs.rfDg.itemDataList=[]
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
<style scoped>
.el-form-item{
    margin-bottom: 0px;
}
</style>s