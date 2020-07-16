<template>
    <div class="app-container">
        <!-- 待解锁文函 -->
        
        <el-row>
            <el-col :span="24" style="padding-top: 0px; padding-bottom: 0px;">
                <el-form :inline="true" :model="filters" @submit.native.prevent>
                <el-form-item>
                    <el-select v-model="filters.projectCode">
                    <el-option label="所有项目" value></el-option>
                    <el-option
                        v-for="item in projects"
                        :key="item+'_option'"
                        :label="item"
                        :value="item">
                    </el-option>
                    
                    </el-select>
                </el-form-item>
                
                <el-form-item>
                    <el-input v-model="filters.title" placeholder="编码或标题" @keyup.enter.native='searchItem'></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" v-on:click="searchItem">查询</el-button>
                </el-form-item>
                
                <el-form-item>
                    <el-button type="success" @click="unlock()">解锁</el-button>
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
                    <el-button type="primary" v-on:click="exportData">导出Excel</el-button>
                </el-form-item>
                </el-form>
            </el-col>
        </el-row>
        <el-row>
            <DataGrid
                ref="mainDataGrid"
                key="main"
                dataUrl="/dc/getDocuments"
                v-bind:tableHeight="rightTableHeight"
                v-bind:isshowOption="true" v-bind:isshowSelection ="true"
                gridViewName="DrawingGrid"
                condition=" C_PROCESS_STATUS='申请解锁'"
                :isshowCustom="true"
                @rowclick="rowClick"
                :isEditProperty="false"
                @selectchange="selectChange"
                ></DataGrid>
        </el-row>
         
    </div>
</template>
<script type="text/javascript">
import ShowProperty from "@/components/ShowProperty";
import DataGrid from "@/components/DataGrid";
import BatchImport from '@/components/controls/ImportDocument';
import ExcelUtil from '@/utils/excel.js';
import AddCondition from '@/views/record/AddCondition';
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
            rightTableHeight: (window.innerHeight)/2,
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
                            message: "请选择一条或多条要解锁的数据！",
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
                            message: "请选择未解锁的数据",
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
                filename:"exportExcel"+new Date().Format("yyyy-MM-dd hh:mm:ss")+".xlsx",
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
                key+=" and C_PROJECT_NAME = '"+_self.filters.projectCode+"'";
            }
            
            if(_self.filters.title!=''){
                key+=" and (C_CONTENT like '%"+_self.filters.title+"%' "
                +"or C_FROM like '%"+_self.filters.title+"%' "
                +"or C_TO like '%"+_self.filters.title+"%' "
                +"or CODING like '%"+_self.filters.title+"%' "
                +"or C_OTHER_COIDNG like '%"+_self.filters.title+"%' "
                +")";
            }
            if(key!=''){
                _self.$refs.mainDataGrid.condition=key;
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
        BatchImport:BatchImport
    }
}
</script>
<style scoped>

</style>