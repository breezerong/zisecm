<template>
    <DataLayout>
        <template v-slot:header>
            <!-- 已接收文函 -->
            <!-- 创建附件 -->
            <el-dialog :title="$t('application.Import')" :visible.sync="importdialogVisible" width="70%">
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
                        <el-button slot="trigger" size="small" type="primary">{{$t('application.selectFile')}}</el-button>
                    </el-upload>
                    </div>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="importdialogVisible = false">{{$t('application.cancel')}}</el-button>
                    <el-button type="primary" @click="uploadData()">{{$t('application.start')+$t('application.Import')}}</el-button>
                </div>
            </el-dialog>
            <!-- 创建类型选择 -->
            <el-dialog :visible.sync="childrenTypeSelectVisible">
                <el-form>
                    <el-form-item :label="$t('application.fileType')" :rules="[{required:true,message:'必填',trigger:'blur'}]">
                    <el-select
                        name="selectName"
                        v-model="selectedChildrenType"
                        :placeholder="$t('application.selectFileType')"
                        style="display:block;"
                    >
                        <div v-for="(name,nameIndex) in childrenTypes" :key="'T2_'+nameIndex">
                        <el-option :label="name" :value="name" :key="nameIndex"></el-option>
                        </div>
                    </el-select>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button
                    @click="childrenTypeSelectVisible=false;beforeCreateDocItem(selectedChildrenType,'')"
                    >{{$t('application.ok')}}</el-button>
                </div>
            </el-dialog>

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
                    folderPath=""
                    itemId=""
                    v-bind:typeName="typeName"
                ></ShowProperty>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="saveItem">{{$t('application.save')}}</el-button>
                    <el-button @click="propertyVisible = false">{{$t('application.cancel')}}</el-button>
                </div>
            </el-dialog>
            <el-form :inline="true" :model="filters" @submit.native.prevent>
                <el-form-item>
                    <DataSelect v-model="filters.projectCode" defaultIsNull :includeAll="true" dataUrl="/exchange/project/myproject" 
                    dataValueField="name" dataTextField="name"></DataSelect>
                </el-form-item>
                <el-form-item>
                    <el-select v-model="filters.docType">
                    <!-- <el-option :label="$t('application.all')+' '+$t('application.subDC')" value></el-option>
                    <el-option label="传递单" value="传递单"></el-option>
                    <el-option label="图文传真" value="图文传真"></el-option>
                    <el-option label="会议纪要" value="会议纪要"></el-option>
                    <el-option label="接口传递" value="接口传递"></el-option> -->
                    <el-option :label="$t('application.all')+' '+$t('application.subDC')" value></el-option>
                    <el-option v-for="(name,nameIndex) in childrenTypes" :key="'Type2_'+nameIndex" :label="name" :value="name"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-input v-model="filters.title" :placeholder="$t('application.Coding')+$t('application.or')+$t('application.Title')" @keyup.enter.native='searchItem'></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" v-on:click="searchItem">{{$t('application.SearchData')}}</el-button>
                </el-form-item>
                <el-form-item>
                    <!-- <el-button type="success" >{{$t('application.AdvSearch')}}</el-button> -->
                    <AddCondition @sendMsg='searchItem' v-model="advCondition" v-bind:inputValue="advCondition" :inputType='hiddenInput'></AddCondition>
                </el-form-item>
                
                <!-- 打包下载
               <el-form-item>
                    <el-button type="primary" @click="packDownloadByMain(selectedItems)">{{$t('application.PackToDownload')}}</el-button>
                </el-form-item> -->
                <!-- 驳回 -->
                <el-form-item>
                    <RejectButton v-if="showReject()" :selectedItems="selectedItems" :refreshDataGrid="$refs.mainDataGrid" isRejectByContractor
                    :cleanSubDataGrids="[$refs.transferDoc,$refs.relevantDoc,$refs.attachmentDoc,$refs.MeetDoc,$refs.MaterialDoc]"></RejectButton>
                </el-form-item>
                <el-form-item>
                     <el-button type="primary" :title="$t('application.ExportExcel')" v-on:click="exportData">{{$t('application.exportExcel')}}</el-button>
                </el-form-item>
                <!-- <el-form-item>
                    <el-button type="warning" 
                    v-on:click="onDeleleItem(selectedItems,[$refs.mainDataGrid,$refs.transferDoc,
                    $refs.relevantDoc])">{{$t('application.delete')}}</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" v-on:click="getData">导出Excel</el-button>
                </el-form-item> -->
                </el-form>
		
        </template>
        <template v-slot:main="{layout}">
            <div :style="{position:'relative',height: layout.height-startHeight+'px'}">
                <split-pane v-on:resize="onSplitResize" :min-percent='20' :default-percent='topPercent' split="horizontal">
                    <template slot="paneL">
                        <DataGrid
                            ref="mainDataGrid"
                            key="main"
                            dataUrl="/dc/getDocuments4Cnpe"
                            v-bind:tableHeight="(layout.height-startHeight)*topPercent/100-topbarHeight"
                            v-bind:isshowOption="true" v-bind:isshowSelection ="true"
                            gridViewName="ApplyRejectGrid"
                            condition=" ITEM_TYPE=1 and C_EX7_STRING='待确认' and TO_NAME='@company' "
                            @rowclick="rowClick"
                            :optionWidth = "2"
                            :isshowCustom="false"
                            :isEditProperty="false"
                            showOptions="查看内容"
                            :isShowChangeList="false"
                            @selectchange="selectChange"
                        ></DataGrid>
                    </template>
                    <template slot="paneR">
                        <el-tabs v-model="selectedTabName">
                            <el-tab-pane :label="$t('application.TransferDoc')" name="t01" v-if="isShowDesgin">
                                <!--列表-->
                                <DataGrid
                                    ref="transferDoc"
                                    key="transferDocKey"
                                    dataUrl="/dc/getDocuByRelationParentId"
                                    v-bind:tableHeight="(layout.height-startHeight)*(100-topPercent)/100-bottomHeight"
                                    v-bind:isshowOption="true" v-bind:isshowSelection ="true"
                                    gridViewName="DrawingGrid"
                                    condition=" and a.NAME='设计文件'"
                                    :optionWidth = "2"
                                    :isshowCustom="false"
                                    :isEditProperty="false"
                                    showOptions="查看内容"
                                    :isShowChangeList="false"
                                    @selectchange="selectChangeTransferDoc"
                                    >
                                        <template slot="sequee" slot-scope="scope">
                                            <span :style="(scope.data.row['C_PROCESS_STATUS']!=null
                                            &&scope.data.row['C_PROCESS_STATUS']=='已解锁')?{'background':'red'}:''">{{scope.data.$index+1}}</span>
                                        </template>
                                </DataGrid>
                            </el-tab-pane>
                            <el-tab-pane :label="$t('application.relevant')" name="t02" v-if="isShowRelevant">
                                <!--列表-->
                                <DataGrid
                                    ref="relevantDoc"
                                    key="relevantDocKey"
                                    dataUrl="/dc/getDocuByRelationParentId"
                                    v-bind:tableHeight="(layout.height-startHeight)*(100-topPercent)/100-bottomHeight"
                                    v-bind:isshowOption="true" v-bind:isshowSelection ="true"
                                    gridViewName="DrawingGrid"
                                    condition=" and a.NAME='相关文件'"
                                    :optionWidth = "1"
                                    :isShowMoreOption="false"
                                    :isshowCustom="false"
                                    :isEditProperty="false"
                                    :isShowChangeList="false"
                                    :isshowicon="false"
                                    @selectchange="relevantDocSelect"
                                ></DataGrid>
                            
                            </el-tab-pane>
                            <el-tab-pane :label="$t('application.Attachment')" name="t03" >
                                <!--列表-->
                                <DataGrid
                                    ref="attachmentDoc"
                                    key="attachmentDocKey"
                                    dataUrl="/dc/getDocuByRelationParentId"
                                    v-bind:tableHeight="(layout.height-startHeight)*(100-topPercent)/100-bottomHeight"
                                    v-bind:isshowOption="true" v-bind:isshowSelection ="true"
                                    gridViewName="AttachmentGrid"
                                    condition=" and a.NAME='附件'"
                                    :optionWidth = "2"
                                    :isshowCustom="false"
                                    :isEditProperty="false"
                                    showOptions="查看内容"
                                    :isShowChangeList="false"
                                    @selectchange="attachmentDocSelect"
                                    ></DataGrid>
                            </el-tab-pane>
                            <el-tab-pane :label="$t('application.ContentItems')" name="t04" v-if="isShowMeet">
                                <!--列表-->
                                <DataGrid
                                        ref="MeetDoc"
                                        key="MeetDocKey"
                                        dataUrl="/dc/getDocuByRelationParentId"
                                        v-bind:tableHeight="(layout.height-startHeight)*(100-topPercent)/100-bottomHeight"
                                        v-bind:isshowOption="true" v-bind:isshowSelection ="true"
                                        gridViewName="MOMContentGrid"
                                        condition=" and a.NAME='会议纪要内容项'"
                                        :optionWidth = "1"
                                        :isShowMoreOption="false"
                                        :isshowCustom="false"
                                        :isEditProperty="false"
                                        :isShowChangeList="false"
                                        :isshowicon="false"
                                        @selectchange="MeetDocSelect"
                                        ></DataGrid>
                            
                            </el-tab-pane>
                            <el-tab-pane :label="$t('application.MaterialChangeList')" name="t05" v-if="isShowMaterial">
                                <!--列表-->
                                <DataGrid
                                    ref="MaterialDoc"
                                    key="MaterialDocKey"
                                    dataUrl="/dc/getDocuByRelationParentId"
                                    v-bind:tableHeight="(layout.height-startHeight)*(100-topPercent)/100-bottomHeight"
                                    v-bind:isshowOption="true" v-bind:isshowSelection ="true"
                                    gridViewName="MaterialChangeGrid"
                                    condition=" and a.NAME='材料变更清单'"
                                    :optionWidth = "2"
                                    :isShowMoreOption="false"
                                    :isshowCustom="false"
                                    :isEditProperty="false"
                                    :isShowChangeList="false"
                                    :isshowicon="false"
                                    @selectchange="MaterialDocSelect"
                                ></DataGrid>
                            </el-tab-pane>
                        </el-tabs>
                    </template>
                </split-pane>
            </div>
        </template>
    </DataLayout>
</template>
<script type="text/javascript">
import ShowProperty from "@/components/ShowProperty";
import DataGrid from "@/components/DataGrid";
import AddCondition from '@/views/record/AddCondition';
import RejectButton from "@/components/RejectButton";
import ExcelUtil from '@/utils/excel.js'
import DataSelect from '@/components/ecm-data-select'
import DataLayout from '@/components/ecm-data-layout'
export default {
    name: "ReceivedDC",
    data(){
        return{
            // 本地存储高度名称
            topStorageName: 'ReceivedDCHeight',
            // 非split pan 控制区域高度
            startHeight: 135,
            // 顶部百分比*100
            topPercent: 65,
            // 顶部除列表高度
            topbarHeight: 35,
            // 底部除列表高度
            bottomHeight: 80,

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
            selectedTransferDocItems:[],
            parentId:"",
            selectRow:[],
            relationName:"",
            relevantDocSelected:[],
            importdialogVisible:false,
            fileList: [],
            uploading:false,
            selectedAttachment:[],
            uploadUrl:'',
            advCondition:'',
            hiddenInput:'hidden',
            isShowDesgin:true,
            isShowRelevant:true,
            isShowAttachmentDoc:true,
            selectedTabName:'t01',
            isShowMeet:true,
            MeetDocSelected:[],
            isShowMaterial:true,
            MaterialDocSelected:[],
        }
    },
    created(){
        this.loadOptionList("项目","");
        // this.getTypeNamesByMainList("DCTypeConfig");
        this.getTypeNamesByMainList("DCTypeSubContractor");
    },
    mounted(){
        if(!this.validataPermission()){
            //跳转至权限提醒页
            let _self=this;
            _self.$nextTick(()=>{
                _self.$router.push({ path: '/NoPermission' })
            })
            
        }
        setTimeout(() => {
            this.topPercent = this.getStorageNumber(this.topStorageName,60)
        }, 300);
    },
    methods: {
        // 上下分屏事件
        onSplitResize(topPercent){
            // 顶部百分比*100
            this.topPercent = topPercent
            this.setStorageNumber(this.topStorageName, topPercent)
            //console.log(JSON.stringify(topPercent))
        },
        showReject:function(){
            /*let roles= this.currentUser().roles;
            for(let i in roles){
                if(roles[i]=='CNPE_文控人员'){
                    return true;
                }
            }
            
            return false;*/
            return true;
        },
        exportData(){
            let dataUrl = "/exchange/doc/export"
            let params = {
                gridName:this.$refs.mainDataGrid.gridViewName,
                lang:"zh-cn",
                condition:this.$refs.mainDataGrid.condition,
                filename:"ReceivedDC_"+new Date().Format("yyyy-MM-dd hh:mm:ss")+".xlsx",
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
        MeetDocSelect(val){
            this.MeetDocSelected=val;
        },
        //批量导入完成
        onBatchImported(){
            this.gridObj.loadGridData();
        },
        attachmentDocSelect(val){
            this.selectedAttachment=val;
        },
        handleChange(file, fileList) {
            this.fileList = fileList;
        },
        beforeUploadFile(uploadpath){
            let _self=this;
            if(_self.parentId==undefined||_self.parentId==''){
                // _self.$message('请选择一条文件数据');
                _self.$message({
                        showClose: true,
                        message: _self.$t('message.PleaseSelectOneFile'),
                        duration: 2000,
                        type: "warning"
                    });
                return;
            }
            _self.uploadUrl=uploadpath;
            _self.fileList=[];
            _self.importdialogVisible=true;
            
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
        //上传文件
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
                _self.$refs.attachmentDoc.loadGridData();
                // _self.$message(_self.$t('application.Import')+_self.$t('message.success'));
                _self.$message({
                        showClose: true,
                        message: _self.$t('application.Import')+_self.$t('message.success'),
                        duration: 2000,
                        type: 'success'
                    });
                })
                .catch(function(error) {
                _self.uploading=false;
                console.log(error);
                });
            },
        rowClick(row){
            
            this.selectRow=row;
            this.parentId=row.ID;
            let _self=this;
            if(row.TYPE_NAME=='文件传递单'){
                _self.isShowDesgin=true;
                _self.isShowRelevant=false;
               _self.isShowAttachmentDoc=false;
               _self.selectedTabName='t01';
               _self.isShowMeet=false;
               _self.isShowMaterial=false;
               _self.$nextTick(()=>{
                   _self.$refs.transferDoc.parentId=row.ID;
                    _self.$refs.transferDoc.loadGridData();
               });
               
            }
            if(("FU申请、FU通知单、作废通知单、CR澄清要求申请单、CR澄清要求答复单、CR澄清要求关闭单、FCR现场变更申请单、FCR现场变更答复单、FCR现场变更关闭单、NCR不符合项报告单、NCR不符合项报告答复单、NCR不符合项报告关闭单、"+
            "DCR设计变更申请单、DCR设计变更答复单、DCR设计变更关闭单、TCR试验澄清申请单、TCR试验澄清答复单、"+
            "TCR试验澄清关闭单、DEN设计变更通知单、DEN设计变更通知关闭单、设计审查意见、设计审查意见答复").indexOf(row.TYPE_NAME)!=-1){
                _self.selectedTabName='t02';
                _self.isShowDesgin=false;
                _self.isShowRelevant=true;
                _self.isShowAttachmentDoc=false;
                _self.isShowMeet=false;
                if(row.TYPE_NAME=='DEN设计变更通知单' || row.TYPE_NAME=='FCR现场变更申请单'){
                    _self.isShowMaterial=true
                }else{
                    _self.isShowMaterial=false
                }
                _self.$nextTick(()=>{
                    _self.$refs.relevantDoc.parentId=row.ID;
                    if(row.TYPE_NAME=='DEN设计变更通知单' || row.TYPE_NAME=='FCR现场变更申请单'){
                        _self.$refs.MaterialDoc.parentId=row.ID;
                        _self.$refs.MaterialDoc.loadGridInfo();
                        _self.$refs.MaterialDoc.loadGridData();
                    }
                    _self.getRelatinItemByTypeName(row.TYPE_NAME,_self.$refs.relevantDoc,function(val){
                    _self.relation=val;
                    // _self.$refs.relevantDoc.loadGridInfo();
                    // _self.$refs.relevantDoc.loadGridData();
                    });
                })
                
            }
            if("图文传真,会议纪要,接口信息意见单,接口信息传递单".indexOf(row.TYPE_NAME)!=-1){
                _self.isShowDesgin=false;
                _self.isShowRelevant=false;
               _self.isShowAttachmentDoc=true;
               _self.selectedTabName='t03';
               _self.isShowMaterial=false;
               if(row.TYPE_NAME=='会议纪要'){
                    _self.isShowMeet=true;
                }else{
                    
                    _self.isShowMeet=false;
                }
               _self.$nextTick(()=>{
                   
                _self.$refs.MeetDoc.parentId=row.ID;
                 _self.$refs.MeetDoc.loadGridData();
               });
            }
            _self.$nextTick(()=>{
                _self.$refs.attachmentDoc.parentId=row.ID;
                _self.$refs.attachmentDoc.loadGridData();
            });
            
            
        },
        MaterialDocSelect(val){
            this.MaterialDocSelected=val;
        },
        relevantDocSelect(val){
            this.relevantDocSelected=val;
        },
        clickNewItem(){
            let _self=this;
            
            _self.childrenTypeSelectVisible=true;
        },
        beforeCreateDocItem(typeName,relationName) {
                let _self = this;
                if(typeName!='设计文件'){
                    _self.parentId='';
                                     
                }
                _self.relationName=relationName;
                _self.dialogName = typeName;
                _self.propertyVisible = true;
                
                setTimeout(()=>{
                    if(_self.$refs.ShowProperty){
                        _self.$refs.ShowProperty.myItemId = "";
                        _self.dialogName=typeName;
                        _self.$refs.ShowProperty.myTypeName =typeName;
                        _self.typeName=typeName;
                        // _self.$refs.ShowProperty.myFolderId = _self.selectTransferRow.id;
                        _self.$refs.ShowProperty.loadFormInfo();
                    }
                },10);

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
            let key=" ITEM_TYPE=1 and C_EX7_STRING='待确认' and TO_NAME='@company' " 
            if(_self.filters.projectCode!=''){
                key+=" and C_PROJECT_NAME = "+_self.filters.projectCode;
            }else{
                key+=" and C_PROJECT_NAME = '@project'";
            }
            if(_self.filters.docType!=''){
                key+=" and TYPE_NAME = '"+_self.filters.docType+"'";
            }
            if(_self.filters.title!=''){
                key+=" and (C_CONTENT like '%"+_self.filters.title+"%' "
                +"or C_FROM like '%"+_self.filters.title+"%' "
                +"or C_TO like '%"+_self.filters.title+"%' "
                +"or CODING like '%"+_self.filters.title+"%' "
                +"or C_OTHER_CODING like '%"+_self.filters.title+"%' "
                +")";
            }
            if(_self.advCondition!=''){
                key+="and ("+_self.advCondition+")";
                _self.advCondition='';
            }
            if(key!=''){
                _self.$refs.mainDataGrid.condition=key;
            }
            
            _self.$refs.mainDataGrid.loadGridData();
            if(_self.$refs.transferDoc!=undefined){
                _self.$refs.transferDoc.itemDataList=[];
            }
            if(_self.$refs.relevantDoc!=undefined){
                _self.$refs.relevantDoc.itemDataList=[];
            }
            if(_self.$refs.attachmentDoc!=undefined){
                _self.$refs.attachmentDoc.itemDataList=[];
            }
            if(_self.$refs.MaterialDoc!=undefined){
                _self.$refs.MaterialDoc.itemDataList=[];
            }
            if(_self.$refs.MeetDoc!=undefined){
                _self.$refs.MeetDoc.itemDataList=[];
            }
        },
        // 表格行选择
        selectChange(val) {
            this.selectedItems = val;
        },
        selectChangeTransferDoc(val) {
            this.selectedTransferDocItems = val;
        },
        // 保存文档
        saveItem() {
        if (!this.$refs.ShowProperty.validFormValue()) {
            return;
        }
        let _self = this;
        var m = new Map();
        let dataRows = this.$refs.ShowProperty.dataList;
        var i;
        for (i in dataRows) {
            if (dataRows[i].attrName && dataRows[i].attrName != "") {
            if (
                dataRows[i].attrName != "FOLDER_ID" &&
                dataRows[i].attrName != "ID"
            ) {
                m.set(dataRows[i].attrName, dataRows[i].defaultValue);
            }
            }
        }
        if (_self.$refs.ShowProperty.myItemId != "") {
            m.set("ID", _self.$refs.ShowProperty.myItemId);
        }
        if (_self.$refs.ShowProperty.myTypeName != "") {
            m.set("TYPE_NAME", _self.$refs.ShowProperty.myTypeName);
            m.set("folderPath", _self.$refs.ShowProperty.folderPath);
            m.set("parentDocId", _self.parentId);
            m.set("relationName",_self.relationName);
        }
        let formdata = new FormData();
        formdata.append("metaData", JSON.stringify(m));

        if (_self.$refs.ShowProperty.file != "") {
            //console.log(_self.file);
            formdata.append("uploadFile", _self.$refs.ShowProperty.file.raw);
        }
        // console.log(JSON.stringify(m));
        if (_self.$refs.ShowProperty.myItemId == "") {
            _self
            .axios({
                headers: {
                "Content-Type": "multipart/form-data"
                // x-www-form-urlencoded'
                //"Content-Type": "application/json;charset=UTF-8"
                },
                method: "post",
                data: formdata,
                url: "/dc/newDocumentOrSubDoc"
            })
            .then(function(response) {
                let code = response.data.code;
                //console.log(JSON.stringify(response));
                if (code == 1) {
                // _self.$message(_self.$t('message.newSuccess'));
                _self.$message({
                    showClose: true,
                    message: _self.$t('message.newSuccess'),
                    duration: 2000,
                    type: "success"
                });
                _self.propertyVisible = false;

                // _self.loadTransferGridData();
                if(_self.$refs.mainDataGrid){
                    _self.$refs.mainDataGrid.loadGridData();
                }
                if(_self.$refs.transferDoc){
                    _self.$refs.transferDoc.loadGridData();
                }
                if(_self.$refs.relevantDoc){
                    _self.$refs.relevantDoc.loadGridData();
                }
                if(_self.$refs.attachmentDoc){
                    _self.$refs.attachmentDoc.loadGridData();
                }
                if(_self.$refs.MaterialDoc){
                    _self.$refs.MaterialDoc.loadGridData();
                }
                if(_self.$refs.MeetDoc){
                    _self.$refs.MeetDoc.loadGridData();
                }
                } else {
                // _self.$message(_self.$t('message.newFailured'));
                _self.$message({
                    showClose: true,
                    message: _self.$t('message.newFailured'),
                    duration: 2000,
                    type: "warning"
                });
                }
            })
            .catch(function(error) {
                // _self.$message(_self.$t('message.newFailured'));
                _self.$message({
                    showClose: true,
                    message: _self.$t('message.newFailured'),
                    duration: 5000,
                    type: "error"
                });
                console.log(error);
            });
        } else {
            _self
            .axios({
                headers: {
                "Content-Type": "application/json;charset=UTF-8"
                },
                method: "post",
                data: JSON.stringify(m),
                url: "/dc/saveDocument"
            })
            .then(function(response) {
                let code = response.data.code;
                //console.log(JSON.stringify(response));
                if (code == 1) {
                    
                    _self.$emit("onSaved", "update");
                } else {
                // _self.$message(_self.$t('message.saveFailured'));
                _self.$message({
                    showClose: true,
                    message: _self.$t('message.saveFailured'),
                    duration: 5000,
                    type: "error"
                });
                }
            })
            .catch(function(error) {
                // _self.$message(_self.$t('message.saveFailured'));
                _self.$message({
                    showClose: true,
                    message: _self.$t('message.saveFailured'),
                    duration: 5000,
                    type: "error"
                });
                console.log(error);
            });
        }
        },
        // 保存结果事件
        onSaved(indata) {
        let _self=this;
        if (indata == "update") {
            // _self.$message(_self.$t("message.saveSuccess"));
            _self.$message({
                showClose: true,
                message: _self.$t("message.saveSuccess"),
                duration: 2000,
                type: 'success'
            });
        } else {
            // _self.$message("新建成功!");
            _self.$message({
                showClose: true,
                message: _self.$t('message.operationSuccess'),
                duration: 2000,
                type: 'success'
            });
        }
        _self.propertyVisible = false;
        
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
        DataSelect:DataSelect,
        RejectButton:RejectButton,
        DataLayout:DataLayout
    }
}
</script>
<style scoped>

</style>