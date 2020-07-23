<template>
    <DataLayout >
        <template v-slot:header>
            <el-dialog title="批量导入ICM" :visible.sync="batchDialogVisible" width="80%" >
                <BatchImport ref="BatchImport"  @onImported="onBatchImported" v-bind:deliveryId="parentId" width="100%"></BatchImport>
                <div slot="footer" class="dialog-footer">
                <el-button @click="batchDialogVisible=false" size="medium">关闭</el-button>
                </div>
            </el-dialog>
            <el-dialog title="导入" :visible.sync="importdialogVisible" width="70%">
                <el-form size="mini" :label-width="formLabelWidth" v-loading='uploading'>
                    <div style="height:200px;overflow-y:scroll; overflow-x:scroll;">
                    <el-upload
                        :limit="100"
                        :file-list="fileList"
                        action
                        :on-change="handleChange"
                        :auto-upload="false"
                        :multiple="true"
                    >
                        <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
                    </el-upload>
                    </div>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="importdialogVisible = false">取 消</el-button>
                    <el-button type="primary" @click="uploadData()">开始导入</el-button>
                </div>
            </el-dialog>
            <!-- 新建窗口 -->
            <el-dialog
                :title="dialogName+$t('application.property')"
                :visible.sync="propertyVisible"
                @close="propertyVisible = false"
                width="80%"
                >
                <ShowProperty
                    ref="ShowProperty"
                    @onSaved="onSaved"
                    width="100%"
                    :folderPath="foldtemerPath"
                    v-bind:itemId="selectedItemId"
                    v-bind:typeName="typeName"
                ></ShowProperty>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="saveItem">{{$t('application.save')}}</el-button>
                    <el-button @click="propertyVisible = false">{{$t('application.cancel')}}</el-button>
                </div>
            </el-dialog>
            <el-form :inline="true" :model="forms.headForm">
                <el-form-item >
                    <DataSelect @onSelectChange='onSelectChange' v-model="forms.headForm.project"  includeAll dataUrl="/exchange/project/myproject" 
                    dataValueField="name" dataTextField="name"></DataSelect>
                </el-form-item>
                <el-form-item>
                    <el-input style="width:200px" v-model="inputValueNum" placeholder="请输入流水号"></el-input>
                    <el-button type="primary" @click="search()">查询</el-button>
                </el-form-item>
                <el-form-item>
                    <AddCondition @sendMsg='searchItem' v-model="advCondition" v-bind:typeName="typeName" :inputValue="advCondition" :inputType='hiddenInput'></AddCondition>
                </el-form-item>
                <el-form-item>
                    <el-button type="default" @click.native="exportData('ICM','ICMGrid')">Excel下载</el-button>
                    <el-button type="primary" @click="newArchiveItem('ICM',selectedOneTransfer)" >新建</el-button>
                    <el-button type="primary" @click="beforImport($refs.mainDataGrid,false,'','/系统配置/导入模板/ICM')">导入</el-button>
                </el-form-item>
            </el-form>
        </template>
        <template v-slot:main="{layout}">
            <el-row>
                <el-col :span="24">
                    <DataGrid ref="mainDataGrid" v-bind="tables.main" :tableHeight="layout.height/2-155" @rowclick="onDataGridRowClick"></DataGrid>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-tabs v-model="tabs.active">
                        <el-tab-pane label="接口传递" name="passICM">
                            <el-button type="default" @click.native="exportData('ICMPass','ICMPassGrid')">Excel下载</el-button>
                            <DataGrid ref="PassICM" v-bind="tables.PassICM" :tableHeight="layout.height/2-155"></DataGrid>
                        </el-tab-pane>
                        <el-tab-pane label="接口意见" name="SICM">
                            <el-button type="default" @click.native="exportData('ICMComments','ICMCommentsGrid')">Excel下载</el-button>
                            <DataGrid ref="SICM" v-bind="tables.SICM" :tableHeight="layout.height/2-155"></DataGrid>
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
import BatchImport from '@/components/controls/ImportDocument.vue'

export default {
    name: "InterfaceManual",
    data(){
        return{
            tables:{
                main:{
                    gridViewName:"ICMGrid",
                    dataUrl:"/dc/getDocuments",
                    condition:"TYPE_NAME='ICM'",
                    isshowOption:true,
                    isshowCustom:true,
                },
                PassICM:{
                    gridViewName:"ICMPassGrid",
                    dataUrl:"/dc/getDocuments",
                    condition:"",
                    isshowOption:true,
                    isshowCustom:true,
                    // isInitData:false,
                    // isshowicon:false,
                    tableHeight:"350"
                },
                SICM:{
                    gridViewName:"ICMCommentsGrid",
                    dataUrl:"/dc/getDocuments",
                    condition:"",
                    isshowOption:true,
                    isshowCustom:true,
                    // isInitData:false,
                    // isshowicon:false,
                    tableHeight:"350"
                }
            },
            tabs:{
                active:"passICM"
            },
            forms:{
                headForm:{
                    project:""
                }
            },
            dialogName:"ICM",
            advCondition:'',
            inputValueNum:'',
            hiddenInput:'hidden',
            typeName:"ICM",
            selectedOneTransfer:'',
            batchDialogVisible:false,//导入窗口
            propertyVisible:false,//新建窗口
            fileList: [],//导入文件列表
            parentId:"",
            gridObj:[],
            importdialogVisible:false,
            uploading:false,
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
        //单击行
        onDataGridRowClick:function(row){
            this.$alert(row)
            this.$refs.PassICM.condition="CODING='"+row.CODING+"'"
            this.$alert(this.$refs.PassICM.condition)
            this.$refs.PassICM.loadGridInfo()
            this.$refs.PassICM.loadGridData()
        },
        //下拉菜单
        onSelectChange(val){
            let _self = this
            //  _self.$alert(val)
            _self.$refs.mainDataGrid.condition="TYPE_NAME='ICM' and C_PROJECT_NAME in ("+val+")";
            // _self.$alert(_self.$refs.mainDataGrid.condition)
            _self.$refs.mainDataGrid.loadGridData();
            _self.$refs.passICM.itemDataList=[]
            _self.$refs.SICM.itemDataList=[]
        },
        //Excel下载
        exportData(typeName,gridViewName){
            let dataUrl = "/exchange/doc/export"
            var fileDate = new Date()
            let fileDateStr = fileDate.getFullYear()+""+fileDate.getMonth()+""+ fileDate.getDate()
            let params = {
                gridName:gridViewName,
                lang:"zh-cn",
                // condition: this.$refs.mainDataGrid.condition,
                // condition:this.tables.main.condition,
                filename:typeName+fileDateStr+".xlsx",
                sheetname:"Result"
            }
            ExcelUtil.export(params)
        },
        //接口号模糊查询
        search(){
            // this.$refs.passICM.itemDataList=[]
            // this.$refs.SICM.itemDataList=[]
            let _self = this
            // _self.$alert("555")
            var k1="TYPE_NAME='ICM' AND C_PROJECT_NAME = '@project'"
            k1+=" AND C_CODE4 LIKE '%"+ _self.inputValueNum+"%'"
            // _self.$alert(k1)
            _self.$refs.mainDataGrid.condition=k1
            _self.$refs.mainDataGrid.loadGridInfo();
            _self.$refs.mainDataGrid.loadGridData();
        },
        //高级搜索
        searchItem(){
            let _self = this
            let key="";
            key = _self.advCondition;
            _self.$refs.mainDataGrid.condition=key;
            // _self.$alert(_self.$refs.mainDataGrid.condition)
            _self.$refs.mainDataGrid.loadGridInfo()
            _self.$refs.mainDataGrid.loadGridData()
            _self.$refs.rfDg.itemDataList=[]
        },
        //新建按钮
        newArchiveItem(typeName, selectedRow) {
            let _self = this;
            _self.selectedItemId = "";
            _self.dialogName = typeName;
            _self.propertyVisible = true;
            setTimeout(()=>{
                if(_self.$refs.ShowProperty){
                    _self.$refs.ShowProperty.myItemId = "";
                    _self.dialogName=typeName;
                    _self.$refs.ShowProperty.myTypeName =typeName;
                    _self.typeName=typeName;
                    _self.$refs.ShowProperty.parentDocId=selectedRow.ID;
                    _self.$refs.ShowProperty.folderPath = '/设计分包/传递单管理/ATOS';
                    // _self.$refs.ShowProperty.myFolderId = _self.selectTransferRow.id;
                    _self.$refs.ShowProperty.loadFormInfo();
                }
            },10);
            //_self.$alert("111")
        },
        //新建保存
        saveItem(){
            let _self = this;
            if(!this.$refs.ShowProperty.validFormValue()){
                return;
            }
            var m = new Map();
            var c;
            for(c in _self.$refs.ShowProperty.dataList){
                let dataRows = _self.$refs.ShowProperty.dataList[c].ecmFormItems;
                var i;
                for (i in dataRows) {
                    if(dataRows[i].attrName && dataRows[i].attrName !=''){
                        if(dataRows[i].attrName !='FOLDER_ID'&&dataRows[i].attrName !='ID'){
                            var val = dataRows[i].defaultValue;
                            if(val && dataRows[i].isRepeat){
                                var temp = "";
                                // console.log(val);
                                for(let j=0,len=val.length;j<len;j++){
                                    temp = temp + val[j]+";";
                                    //console.log(temp);
                                }
                                temp = temp.substring(0,temp.length-1);
                                val = temp;
                                console.log(val);
                            }
                            m.set(dataRows[i].attrName, val);
                        }
                    }
                }
            }
            if(_self.$refs.ShowProperty.myItemId!=''){
                m.set('ID',_self.$refs.ShowProperty.myItemId);
            }
            if(_self.$refs.ShowProperty.myTypeName!=''){
                m.set('TYPE_NAME',_self.$refs.ShowProperty.myTypeName);
                m.set('FOLDER_ID',_self.$refs.ShowProperty.myFolderId);
                m.set("parentDocId", _self.parentId);
                m.set("relationName",_self.relationName);
            }
            let formdata = new FormData();
            formdata.append("metaData",JSON.stringify(m));
            if(_self.$refs.ShowProperty.file!=""){
                //console.log(_self.file);
                formdata.append("uploadFile",_self.$refs.ShowProperty.file.raw);
            }
            // console.log(JSON.stringify(m));
            if(_self.$refs.ShowProperty.myItemId==''){
                axios.post("/exchange/ied/newIED",formdata,{
                    'Content-Type': 'multipart/form-data'
                })
                .then(function(response) {
                    let code = response.data.code;
                    //console.log(JSON.stringify(response));
                    if (code == 1) {
                        // _self.$message("创建成功!");
                        _self.$message({
                            showClose: true,
                            message: "创建成功!",
                            duration: 2000,
                            type: "success"
                        });
                        _self.propertyVisible = false;
                        // _self.loadTransferGridData();
                        _self.$refs.mainDataGrid.loadGridData();
                        
                    } 
                    else{
                    _self.$message({
                            showClose: true,
                            message: _self.$t('message.newFailured'),
                            duration: 2000,
                            type: "warning"
                        });
                        
                    }
                })
                .catch(function(error) {
                    _self.$message(_self.$t('message.newFailured'));
                    console.log(error);
                });
            }
            else{
                if(_self.$refs.ShowProperty.permit<5){
                    _self.$message(_self.$t('message.hasnoPermssion'));
                    return ;
                }
                axios.post("/dc/saveDocument",JSON.stringify(m))
                .then(function(response) {
                    let code = response.data.code;
                    //console.log(JSON.stringify(response));
                    if(code==1){
                        _self.$emit('onSaved','update');
                    }
                    else{
                        _self.$message(_self.$t('message.saveFailured'));
                    }
                })
                .catch(function(error) {
                    _self.$message(_self.$t('message.saveFailured'));
                    console.log(error);
                });
            }
        },
        onBatchImported(){
            this.gridObj.loadGridData();
        },
        handleChange(file, fileList) {
            this.fileList = fileList;
        },
        //导入按钮
        beforImport(obj,isSub,relationName,path){
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
                this.$refs.BatchImport.tmpPath=path;
                this.$refs.BatchImport.loadTemplate();
            })
        },
        getFormData() {
            let _self = this;
            let formdata = new FormData();
            var data = {};
            data["parentDocId"] = _self.parentId;//_self.selectedInnerItems[0].ID;//_self.selectedFileId;
            data["relationName"]='ICM';
            data["TYPE_NAME"]='ICM';
            formdata.append("metaData", JSON.stringify(data));
            _self.fileList.forEach(function(file) {
                //console.log(file.name);
                formdata.append("uploadFile", file.raw, file.name);
            });
            return formdata;
        },
        uploadData() {
            let _self = this;
            let formdata = _self.getFormData();
            console.log("UploadData getData");
            console.log(formdata);
            _self.uploading=true;
            _self
            .axios({
            headers: {
                "Content-Type": "application/json;charset=UTF-8"
            },
            datatype: "json",
            method: "post",
            data: formdata,
            url: _self.uploadUrl
            })
            .then(function(response) {
            _self.importdialogVisible = false;
            // _self.refreshData();
            _self.uploading=false;
            _self.$refs.mainDataGrid.loadGridData();
            // _self.$message("导入成功!");
            _self.$message({
                    showClose: true,
                    message: "导入成功!",
                    duration: 2000,
                    type: 'success'
                });
            })
            .catch(function(error) {
            _self.uploading=false;
            console.log(error);
            });
        }
    },
    props: {
        
    },
    components: {
        ShowProperty:ShowProperty,
        DataGrid:DataGrid,
        DataSelect:DataSelect,
        AddCondition:AddCondition,
        DataLayout:DataLayout,
        BatchImport:BatchImport,
    }
}
</script>