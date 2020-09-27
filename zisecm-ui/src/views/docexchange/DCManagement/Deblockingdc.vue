<template>
<!-- 待解锁文函 -->
    <DataLayout>
        <template v-slot:header>
            <el-form :inline="true" :model="filters" @submit.native.prevent>
                <el-form-item>
                    <DataSelect v-model="filters.projectCode" defaultIsNull :includeAll="true" dataUrl="/exchange/project/myproject" 
                    dataValueField="name" dataTextField="name"></DataSelect>
                </el-form-item>
                
                <el-form-item>
                    <el-input v-model="filters.title" :placeholder="$t('application.Coding')+$t('application.or')+$t('application.Title')" @keyup.enter.native='searchItem'></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" v-on:click="searchItem">{{$t('application.SearchData')}}</el-button>
                </el-form-item>
                
                <el-form-item>
                    <el-button type="success" @click="unlock()">{{$t('application.unlock')}}</el-button>
                </el-form-item>
                
                <el-form-item>
                    <AddCondition @sendMsg='searchItem' 
                    v-model="advCondition" 
                    v-bind:inputValue="advCondition" 
                    inputType='hidden'></AddCondition>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="packDownloadByMain(selectedItems)">{{$t('application.PackToDownload')}}</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" v-on:click="exportData">{{$t('application.ExportExcel')}}</el-button>
                </el-form-item>
                </el-form>
        </template>
        <template v-slot:main="{layout}">
            <el-row>
                <el-col :span="24">
                    <DataGrid
                        ref="mainDataGrid"
                        key="main"
                        dataUrl="/dc/getDocuments"
                        v-bind:tableHeight="layout.height-166"
                        v-bind:isshowOption="true" v-bind:isshowSelection ="true"
                        gridViewName="DrawingGrid"
                        condition=" C_PROCESS_STATUS='申请解锁'"
                        :optionWidth = "2"
                        @rowclick="rowClick"
                        :isshowCustom="false"
                        :isEditProperty="false"
                        showOptions="查看内容"
                        :isShowChangeList="false"
                        @selectchange="selectChange"
                        ></DataGrid>
                </el-col>
            </el-row>
            
        </template>
    </DataLayout>
</template>
<script type="text/javascript">
import ShowProperty from "@/components/ShowProperty";
import DataGrid from "@/components/DataGrid";
import BatchImport from '@/components/controls/ImportDocument';
import ExcelUtil from '@/utils/excel.js';
import AddCondition from '@/views/record/AddCondition';
import DataSelect from '@/components/ecm-data-select';
import DataLayout from '@/components/ecm-data-layout'
export default {
    name: "Submissiondc",
    data(){
        return{
            filters: {
                projectCode: "",
                docType: "",
                coding: "",
                title: "",
                limit: 10
            },
            projects:[],
            typeName:"文件传递单",
            dialogName:"文件传递单",
            propertyVisible:false,
            selectedItems: [],
            childrenTypes:[],
            childrenTypeSelectVisible:false,
            selectedChildrenType:'',
            parentId:"",
            selectRow:[],
            relationName:"",
            importdialogVisible:false,
            fileList: [],
            uploading:false,
            uploadUrl:'',
            batchDialogVisible:false,
            gridObj:[],
            rightTableHeight: (window.innerHeight-180),
            advCondition:"",
        }
    },
    created(){
        this.loadOptionList("项目","");
        this.getTypeNamesByMainList("DCTypeConfig");
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
        unlock(){
            let _self=this;
            // _self.selectedTransferDocItems;

            if(_self.selectedItems.length==0){
                _self.$message({
                            showClose: true,
                            message: _self.$t('message.PleaseSelectUnlock'),
                            duration: 2000,
                            type: 'warning'
                            });
                        return;
            }

            let i;
            let m = [];
            let tab=_self.selectedItems;
            for (i in tab) {
                
                m.push(tab[i]["ID"]);
            }
            if(m.length>0){
                axios.post("/dc/unlock",JSON.stringify(m),{
                        headers: {
                            "Content-Type": "application/json;charset=UTF-8"
                        }
                    })
                    .then(function(response) {
                        _self.$refs.mainDataGrid.loadGridData();
                        
                        _self.$message({
                            showClose: true,
                            message: _self.$t("message.ApplySuccess"),
                            duration: 2000,
                            type: 'success'
                        });
                    })
                    .catch(function(error) {
                        
                        _self.$message({
                            showClose: true,
                            message: _self.$t("message.ApplyFaild"),
                            duration: 5000,
                            type: 'error'
                        });
                        console.log(error);
                    });
            }else{
                    _self.$message({
                            showClose: true,
                            message: _self.$t("message.PleaseSelectNotunlocked"),
                            duration: 5000,
                            type: 'error'
                        });
            }
                
        },
        exportData(){
            let dataUrl = "/exchange/doc/export"
            let params = {
                gridName:this.$refs.mainDataGrid.gridViewName,
                lang:"zh-cn",
                condition:this.$refs.mainDataGrid.condition,
                filename:"DeblockingDC_"+new Date().Format("yyyy-MM-dd hh:mm:ss")+".xlsx",
                sheetname:"Result"
            }
            ExcelUtil.export(params)
        },
        beforImport(obj,isSub,relationName){
            this.gridObj=obj;
            this.batchDialogVisible=true;
            this.$nextTick(()=>{
                if(isSub){
                    this.$refs.BatchImport.deliveryId=this.parentId;
                    this.$refs.BatchImport.relationName=relationName;
                }else{
                    this.$refs.BatchImport.deliveryId='';
                    this.$refs.BatchImport.relationName='';
                }
            })
        },
        //批量导入完成
        onBatchImported(){
            this.gridObj.loadGridData();
        },
        handleChange(file, fileList) {
            this.fileList = fileList;
        },
        
        getFormData() {
            let _self = this;
            let formdata = new FormData();
            var data = {};
            data["parentDocId"] = _self.parentId;//_self.selectedInnerItems[0].ID;//_self.selectedFileId;
            data["relationName"]='附件';
            data["TYPE_NAME"]='附件';
            formdata.append("metaData", JSON.stringify(data));
            _self.fileList.forEach(function(file) {
                //console.log(file.name);
                formdata.append("uploadFile", file.raw, file.name);
            });
            return formdata;
            },
        
        rowClick(row){
            this.selectRow=row;
        },
        
        clickNewItem(){
            let _self=this;
            _self.childrenTypeSelectVisible=true;
        },
        
        //获取类型
        getTypeNamesByMainList(keyName) {
            let _self = this;
            axios
                .post("/dc/getOneParameterValue", keyName)
                .then(function(response) {
                _self.childrenTypes = response.data.data;
                })
                .catch(function(error) {
                console.log(error);
                });
            },
        searchItem(){
            let _self=this;
            let key=" C_PROCESS_STATUS='申请解锁'";
            if(_self.filters.projectCode!=''){
                key+=" and C_PROJECT_NAME = "+_self.filters.projectCode;
            }else{
                key+=" and C_PROJECT_NAME = '@project'";
            }
            
            if(_self.filters.title!=''){
                key+=" and (C_CONTENT like '%"+_self.filters.title+"%' "
                +"or C_FROM like '%"+_self.filters.title+"%' "
                +"or C_TO like '%"+_self.filters.title+"%' "
                +"or CODING like '%"+_self.filters.title+"%' "
                +"or C_OTHER_CODING like '%"+_self.filters.title+"%' "
                +")";
            }
            if(key!=''){
                if(_self.advCondition!=''){
                    _self.$refs.mainDataGrid.condition=key+" and "+_self.advCondition;
                    _self.advCondition=''
                }else{
                    _self.$refs.mainDataGrid.condition=key
                }
            }
            _self.$refs.mainDataGrid.loadGridData();
        },
        // 表格行选择
        selectChange(val) {
            this.selectedItems = val;
        },
        
        loadOptionList(queryName,val){
            let _self = this;
            var m = new Map();
            m.set("queryName", queryName);
            m.set("dependValue", val);
            axios.post("/dc/getSelectList",JSON.stringify(m))
                .then(function(response) {
                if(response.data.code == 1){
                    _self.projects = response.data.data;
                }
                })
                .catch(function(error) {
                console.log(error);
                });
        },
        
            
    },
    props: {
        
    },
    components: {
        ShowProperty:ShowProperty,
        DataGrid:DataGrid,
        AddCondition:AddCondition,
        BatchImport:BatchImport,
        DataSelect:DataSelect,
        DataLayout:DataLayout
    }
}
</script>
<style scoped>

</style>