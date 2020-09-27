<template>
  <div>
            <!-- 材料变更清单Excel导入 -->
            <el-dialog :append-to-body="true" :title="$t('application.Import')" :visible.sync="MeetMaterialDialogVisible" width="80%" >
                <MeetMaterialImport ref="MeetMaterialImport"  @onImported="onMeetMaterialImport" v-bind:deliveryId="parentId" width="100%"></MeetMaterialImport>
                <div slot="footer" class="dialog-footer">
                <el-button @click="ImportClose()" size="medium">{{$t('application.close')}}</el-button>
                </div>
            </el-dialog>
            <!-- 创建设计文件附件 -->
            <el-dialog :append-to-body="true" :title="$t('application.Import')" :visible.sync="importSubVisible" width="70%">
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
                    <el-button @click="importSubVisible = false">{{$t('application.cancel')}}</el-button>
                    <el-button type="primary" @click="uploadDataSub()">{{$t('application.start')+$t('application.Import')}}</el-button>
                </div>
            </el-dialog>
            <!-- 相关文件创建选择IED-->
            <el-dialog :append-to-body="true" :title="dialog.title" :visible.sync="propertyrela" width="80%" :before-close="handleClose" > 
                <DataLayout>
                    <template v-slot:header>
                        <el-form>
                        <el-form-item>
                            <el-input style="width:200px" v-model="inputValueNum" :placeholder="$t('message.iedPublishedInputPlaceholder')"></el-input>
                            <el-button type="primary" @click="searchIED()">{{$t('application.SearchData')}}</el-button>
                        </el-form-item> 
                        </el-form>  
                        </template>
                    <template v-slot:main>  
                        <DataGrid ref="DialogDataGrid" v-bind="tables.DialogDataGrid"  :tableHeight="360" :isshowCustom="false" :isShowChangeList="false">
                                <template slot="customMoreOption" slot-scope="scope">
                                <el-button type="primary" @click="IEDChoose(scope.data.row)" size="mini">{{$t('application.select')}}</el-button>
                                </template>
                        </DataGrid>
                    </template>
                </DataLayout>
            </el-dialog>
            <!-- 设计文件附件 -->
            <el-dialog :title="$t('application.Attachment')" :append-to-body="true" :visible.sync="dialog.visible" width="50%" :before-close="handleClose">      
                <AttachmentFile ref="subAttachment" :docId="docId" :allowEdit="allowEdit"></AttachmentFile>
            </el-dialog>
            <!-- 创建分发 -->
        <el-dialog title="分包商选择" :append-to-body="true" :visible.sync="contractorCheckVisible" width="60%" >
            <el-checkbox-group v-model="checkList">
                <template v-for="(itm,idx) in subContractor" >
                    <el-checkbox :label="itm" :key="idx+'contractorCheck'"></el-checkbox>
                </template>
            </el-checkbox-group>
            <div slot="footer" class="dialog-footer">
                <el-button
                @click="contractorCheckVisible=false;onDispenseDc()"
                >{{$t('application.ok')}}</el-button>
            </div>
        </el-dialog>
        <!-- 批量导入 -->
        <el-dialog :append-to-body="true" :title="$t('message.Batch')+' '+$t('application.Import')+$t('application.document')" :visible.sync="batchDialogVisible" width="80%" >
            <BatchImport ref="BatchImport"  @onImported="onBatchImported"  
            tmpPath='/系统配置/导入模板/文函' v-bind:deliveryId="parentId" width="100%"></BatchImport>
            <div slot="footer" class="dialog-footer">
            <el-button @click="batchDialogVisible=false" size="medium">{{$t('application.close')}}</el-button>
            </div>
        </el-dialog>
        <!-- 创建附件 -->
        <el-dialog :title="$t('application.Import')" :append-to-body="true" :visible.sync="importdialogVisible" width="70%">
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
        <el-dialog
            :title="dialogName+$t('application.property')"
            :visible.sync="propertyVisible"
            @close="propertyVisible = false"
            :append-to-body="true"
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
                <el-button @click="saveItem" :loading="butt">{{$t('application.save')}}</el-button>
                <el-button @click="propertyVisible = false">{{$t('application.cancel')}}</el-button>
            </div>
        </el-dialog>
                        <el-tabs value="t01" v-model="selectedTabName">
                            <el-tab-pane :label="$t('application.TransferDoc')" name="t01" v-if="isShowDesgin">
                                <el-row v-if="allowEdit">
                                    <el-col :span="24" style="text-align:left;">
                                    <el-form :inline="true" :model="filters" @submit.native.prevent>
                                        <el-form-item>
                                        <el-button type="primary" @click="beforeCreateDocItem('设计文件','设计文件')">{{$t('application.new')}}</el-button>
                                        </el-form-item>
                                        <el-form-item>
                                        <el-button type="primary" @click="beforImport($refs.transferDoc,true,'设计文件','/系统配置/导入模板/设计文件')">{{$t('application.Import')}}</el-button>
                                        </el-form-item>
                                        <el-form-item>
                                            <el-button type="primary" @click="beforeUploadSubFile('/dc/addAttachment')">{{$t('application.Add')+$t('application.Attachment')}}</el-button>
                                        </el-form-item>
                                        <el-form-item>
                                            <MountFile :selectedItem="selectedTransferDocItems" @refresh='refreshTransferDocData'>{{$t('application.ReplaceDoc')}}</MountFile>
                                        </el-form-item>
                                        <el-form-item>
                                        <el-button type="warning" @click="onDeleleItem(selectedTransferDocItems,[$refs.transferDoc])">{{$t('application.delete')}}</el-button>
                                        </el-form-item>
                                    </el-form>
                                    </el-col>
                                </el-row>
                                <!--列表-->
                                <DataGrid
                                        ref="transferDoc"
                                        key="transferDocKey"
                                        dataUrl="/dc/getDocuByRelationParentId"
                                        v-bind:tableHeight="tableHeight"
                                        v-bind:isshowOption="true" v-bind:isshowSelection ="true"
                                        gridViewName="DrawingGrid"
                                        condition=" and a.NAME='设计文件'"
                                        :optionWidth = "2"
                                        :isshowCustom="false"
                                        :isEditProperty="allowEdit"
                                        showOptions="查看内容"
                                        :isShowChangeList="false"
                                        @dbclick="dbClick"
                                        @selectchange="selectChangeTransferDoc"
                                        >
                                            <template slot="dropdownItem" slot-scope="scope">
                                                <el-dropdown-item icon="el-icon-paperclip" @click.native="dbClick(scope.data.row)">{{$t('application.viewAttachment')}}</el-dropdown-item>
                                            </template>
                                        </DataGrid>
                            </el-tab-pane>
                            <el-tab-pane :label="$t('application.relevant')" name="t02" v-if="isShowRelevant">
                                <el-row v-if="allowEdit">
                                    <el-col :span="24" style="text-align:left;">
                                    <el-form :inline="true" :model="filters" @submit.native.prevent>
                                        <el-form-item>
                                        <el-button type="primary" @click="beforeCreateDocItem('相关文件','相关文件')">{{$t('application.new')}}</el-button>
                                        </el-form-item>
                                        <!-- <el-form-item>
                                        <el-button type="primary" @click="beforImport($refs.relevantDoc,true,'相关文件')">{{$t('application.Import')}}</el-button>
                                        </el-form-item> -->
                                        <!-- <el-form-item>
                                            <MountFile :selectedItem="relevantDocSelected" @refresh='refreshReleventDocData'>{{$t('application.ReplaceDoc')}}</MountFile>
                                        </el-form-item> -->
                                        <el-form-item>
                                        <el-button type="warning" @click="onDeleleItem(relevantDocSelected,[$refs.relevantDoc])">{{$t('application.delete')}}</el-button>
                                        </el-form-item>
                                    </el-form>
                                    </el-col>
                                </el-row>
                                <!--列表-->
                                <DataGrid
                                        ref="relevantDoc"
                                        key="relevantDocKey"
                                        dataUrl="/dc/getDocuByRelationParentId"
                                        v-bind:tableHeight="tableHeight"
                                        v-bind:isshowOption="true" v-bind:isshowSelection ="true"
                                        gridViewName="DrawingGrid"
                                        condition=" and a.NAME='相关文件'"
                                        :optionWidth = "1"
                                        :isShowMoreOption="false"
                                        :isshowCustom="false"
                                        :isEditProperty="allowEdit"
                                        :isShowChangeList="false"
                                        :isshowicon="false"
                                        @selectchange="relevantDocSelect"
                                        ></DataGrid>
                            
                            </el-tab-pane>
                            
                            <el-tab-pane :label="$t('application.ContentItems')" name="t03" v-if="isShowMeet">
                                <el-row v-if="allowEdit">
                                    <el-col :span="24" style="text-align:left;">
                                    <el-form :inline="true" :model="filters" @submit.native.prevent>
                                        <el-form-item>
                                        <el-button type="primary" @click="beforeCreateDocItem('会议纪要内容项','会议纪要内容项')">{{$t('application.new')}}</el-button>
                                        </el-form-item>
                                        <el-form-item>
                                        <el-button type="primary" @click="beforMeetMaterialImport($refs.MeetDoc,true,'会议纪要内容项','/系统配置/导入模板/会议纪要内容项')">{{$t('application.Import')}}</el-button>
                                        </el-form-item>
                                        <!-- <el-form-item>
                                        <el-button type="primary" @click="beforImport($refs.relevantDoc,true,'相关文件')">{{$t('application.Import')}}</el-button>
                                        </el-form-item> -->
                                        <!-- <el-form-item>
                                            <MountFile :selectedItem="relevantDocSelected" @refresh='refreshReleventDocData'>{{$t('application.ReplaceDoc')}}</MountFile>
                                        </el-form-item> -->
                                        <el-form-item>
                                        <el-button type="warning" @click="onDeleleItem(MeetDocSelected,[$refs.MeetDoc])">{{$t('application.delete')}}</el-button>
                                        </el-form-item>
                                    </el-form>
                                    </el-col>
                                </el-row>
                                <!--列表-->
                                <DataGrid
                                        ref="MeetDoc"
                                        key="MeetDocKey"
                                        dataUrl="/dc/getDocuByRelationParentId"
                                        v-bind:tableHeight="tableHeight"
                                        v-bind:isshowOption="true" v-bind:isshowSelection ="true"
                                        gridViewName="MOMContentGrid"
                                        condition=" and a.NAME='会议纪要内容项'"
                                        :optionWidth = "1"
                                        :isShowMoreOption="false"
                                        :isshowCustom="false"
                                        :isEditProperty="allowEdit"
                                        :isShowChangeList="false"
                                        :isshowicon="false"
                                        @selectchange="MeetDocSelect"
                                        ></DataGrid>
                            
                            </el-tab-pane>
                            <el-tab-pane :label="$t('application.MaterialChangeList')" name="t05" v-if="isShowMaterial">
                                <el-row v-if="allowEdit">
                                    <el-col :span="24" style="text-align:left;">
                                    <el-form :inline="true" :model="filters" @submit.native.prevent>
                                        <el-form-item>
                                        <el-button type="primary" @click="beforeCreateDocItem('材料变更清单','材料变更清单')">{{$t('application.new')}}</el-button>
                                        </el-form-item>
                                        <el-form-item>
                                        <el-button type="primary" @click="beforMeetMaterialImport($refs.MaterialDoc,true,'材料变更清单','/系统配置/导入模板/材料变更清单')">{{$t('application.Import')}}</el-button>
                                        </el-form-item>
                                        <!-- <el-form-item>
                                        <el-button type="primary" @click="beforImport($refs.relevantDoc,true,'相关文件')">{{$t('application.Import')}}</el-button>
                                        </el-form-item> -->
                                        <!-- <el-form-item>
                                            <MountFile :selectedItem="relevantDocSelected" @refresh='refreshReleventDocData'>{{$t('application.ReplaceDoc')}}</MountFile>
                                        </el-form-item> -->
                                        <el-form-item>
                                        <el-button type="warning" @click="onDeleleItem(MaterialDocSelected,[$refs.MaterialDoc])">{{$t('application.delete')}}</el-button>
                                        </el-form-item>
                                    </el-form>
                                    </el-col>
                                </el-row>
                                <!--列表-->
                                <DataGrid
                                    ref="MaterialDoc"
                                    key="MaterialDocKey"
                                    dataUrl="/dc/getDocuByRelationParentId"
                                    v-bind:tableHeight="tableHeight"
                                    v-bind:isshowOption="true" v-bind:isshowSelection ="true"
                                    gridViewName="MaterialChangeGrid"
                                    condition=" and a.NAME='材料变更清单'"
                                    :optionWidth = "1"
                                    :isShowMoreOption="false"
                                    :isshowCustom="false"
                                    :isEditProperty="allowEdit"
                                    :isShowChangeList="false"
                                    :isshowicon="false"
                                    @selectchange="MaterialDocSelect"
                                ></DataGrid>
                            
                            </el-tab-pane>
                        </el-tabs>
    </div>
</template>
<script type="text/javascript">
import ShowProperty from "@/components/ShowProperty";
import DataGrid from "@/components/DataGrid";
import BatchImport from '@/components/controls/ImportDocument';
import AttachmentFile from "@/views/dc/AttachmentFile.vue"
import ExcelUtil from '@/utils/excel.js'
import MountFile from '@/components/MountFile.vue';
import DataSelect from '@/components/ecm-data-select'
import DataLayout from '@/components/ecm-data-layout'
import MeetMaterialImport from '@/components/controls/ImportMeetMaterial';
export default {
    // 
    name: "RelationViewer",
    data(){
        return{
            tableHeight: "240px",
            dialog:{
                title:"",
                visible:false
            },
            projects:[],
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
            batchDialogVisible:false,
            gridObj:[],
            subContractor:[],
            checkList:[],
            contractorCheckVisible:false,
            relation:{},
            isShowDesgin:true,
            isShowRelevant:true,
            isShowAttachmentDoc:true,
            selectedTabName:'t01',
            importSubVisible:false,
            docId:"",
            butt:false,
            propertyrela:false,
            inputValueNum:'',
            isShowMeet:true,
            MeetDocSelected:[],
            isShowMaterial:true,
            MaterialDocSelected:[],
            MeetMaterialDialogVisible:false,
            timer:null
        }
    },
    created(){
        
        
    },
    mounted(){
       
    },
    methods: {
        ImportClose(){
            let _self=this
            _self.MeetMaterialDialogVisible=false
            _self.$refs.MeetMaterialImport.ImportClose()
        },
        beforMeetMaterialImport(obj,isSub,relationName,path){
            this.gridObj=obj;
            this.MeetMaterialDialogVisible=true;
            this.$nextTick(()=>{
                if(isSub){
                    this.$refs.MeetMaterialImport.deliveryId=this.parentId;
                    this.$refs.MeetMaterialImport.relationName=relationName;
                    
                }else{
                    this.$refs.MeetMaterialImport.deliveryId='';
                    this.$refs.MeetMaterialImport.relationName='';
                }
                this.$refs.MeetMaterialImport.tmpPath=path;
                this.$refs.MeetMaterialImport.loadTemplate();
            })
            
            
        },
        searchIED(){
            let _self = this
            let wheres = ["TITLE","C_WBS_CODING","CODING","C_IN_CODING"]
            let orS = ""
            var k1=""
            if(_self.inputValueNum.trim().length>0){
                wheres.forEach(function(item){
                    if(orS.length>0){
                        orS+=" OR "
                    }
                    orS+=item + " LIKE '%"+ _self.inputValueNum+"%'"
                })
                k1+=" AND (" + orS + ")"
            }
            // _self.tables.DialogDataGrid.condition+=k1
            let key = _self.tables.DialogDataGrid.condition
            _self.$refs.DialogDataGrid.condition=key+k1
            _self.$refs.DialogDataGrid.loadGridInfo()
            _self.$refs.DialogDataGrid.loadGridData()
        },
        IEDChoose(row){
            let _self = this;
            let relationName="相关文件"
            let typeName="相关文件"
            _self.relationName=relationName;
            _self.dialogName = typeName;
            _self.propertyVisible = true;
            setTimeout(()=>{
                if(_self.$refs.ShowProperty){
                    _self.$refs.ShowProperty.myItemId = "";
                    _self.dialogName=typeName;
                    _self.$refs.ShowProperty.myTypeName =typeName;
                    
                    if(typeName=='相关文件'){
                        _self.$refs.ShowProperty.showUploadFile = false;
                        _self.$refs.ShowProperty.formName=_self.relation.formName;
                    }else{
                        _self.$refs.ShowProperty.showUploadFile = true;
                        _self.$refs.ShowProperty.formName="";
                    }
                    _self.typeName=typeName;
                    
                    _self.$refs.ShowProperty.setMainObject(row);
                    let mp=new Map();
                    mp.set("CODING",'CODING');
                    mp.set("C_IN_CODING",'C_IN_CODING');
                    mp.set("TITLE",'TITLE');
                    mp.set("REVISION","REVISION")

                    _self.$refs.ShowProperty.setMainSubRelation(mp);
                    _self.$refs.ShowProperty.loadFormInfo();
                }
            },10);
        },
        beforeUploadSubFile(uploadpath){
            let _self=this;
            if(_self.selectedTransferDocItems==undefined||_self.selectedTransferDocItems.length!=1){
                // _self.$message('请选择一条文件数据');
                _self.$message({
                        showClose: true,
                        message:  _self.$t('message.pleaseSelectOneDesigndoc'),
                        duration: 2000,
                        type: "warning"
                    });
                return;
            }
            _self.uploadUrl=uploadpath;
            _self.fileList=[];
            _self.importSubVisible=true;
            
        },
        //上传文件
        uploadDataSub() {
            let _self = this;
            let formdata = _self.getSubFormData();
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
                _self.importSubVisible = false;
                // _self.refreshData();
                _self.uploading=false;
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
        getSubFormData() {
                let _self = this;
                let formdata = new FormData();
                var data = {};
                data["parentDocId"] = _self.selectedTransferDocItems[0].ID;//_self.selectedInnerItems[0].ID;//_self.selectedFileId;
                data["relationName"]='附件';
                data["TYPE_NAME"]='附件';
                formdata.append("metaData", JSON.stringify(data));
                _self.fileList.forEach(function(file) {
                    //console.log(file.name);
                    formdata.append("uploadFile", file.raw, file.name);
                });
                return formdata;
            },
        dbClick(row){
          let _self = this;
            _self.docId=row.ID;
            _self.dialog.visible=true;
            clearInterval(_self.timer);
            _self.timer = setTimeout(()=>{
                _self.$refs.subAttachment.refresh();
                // this.$refs.subAttachment.docId=row.ID;
            },200);

            // this.$nextTick(()=>{
            //     this.$refs.subAttachment.docId=row.ID;
            // this.$refs.subAttachment.docId=row.ID;
            // });
        },
        // 上下分屏事件
        onSplitResize(topPercent){
            // 顶部百分比*100
            this.topPercent = topPercent
            this.setStorageNumber(this.topStorageName, topPercent)
            //console.log(JSON.stringify(topPercent))
        },
        refreshTransferDocData(){
             this.$refs.transferDoc.loadGridData();
        },
        refreshReleventDocData(){
           
            this.$refs.relevantDoc.loadGridData();
        },
        beforeDispense(){
            let _self=this;
            _self.contractorCheckVisible=true;
            _self.$nextTick(()=>{
                _self.subContractor=_self.loadSelectOption("分包商","");
            });

            
        },
        exportData(){
            let dataUrl = "/exchange/doc/export"
            let params = {
                gridName:this.$refs.mainDataGrid.gridViewName,
                lang:"zh-cn",
                condition:this.$refs.mainDataGrid.condition,
                filename:"DispenseDC_"+new Date().Format("yyyy-MM-dd hh:mm:ss")+".xlsx",
                sheetname:"Result"
            }
            ExcelUtil.export(params)
        },
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
        onBatchImported(){
            this.gridObj.loadGridData();
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
        loadData(row){
            this.parentId=row.ID;
            let _self=this;
            _self.selectRow = row;
            let id = row.ID;
            let typeName = row.TYPE_NAME;
            if(typeName =='文件传递单'){
              _self.isShowDesgin=true;
              _self.isShowRelevant=false;
              _self.isShowAttachmentDoc=false;
              _self.selectedTabName='t01';
              _self.isShowMeet=false;
              _self.isShowMaterial=false;
              clearInterval(_self.timer);
              _self.timer = setTimeout(()=>{
                  _self.$refs.transferDoc.parentId=id;
                  _self.$refs.transferDoc.loadGridData();
               },200);
               
            }
            else if(("FU申请、FU通知单、作废通知单、CR澄清要求申请单、CR澄清要求答复单、CR澄清要求关闭单、FCR现场变更申请单、FCR现场变更答复单、FCR现场变更关闭单、NCR不符合项报告单、NCR不符合项报告答复单、NCR不符合项报告关闭单、"+
            "DCR设计变更申请单、DCR设计变更答复单、DCR设计变更关闭单、TCR试验澄清申请单、TCR试验澄清答复单、"+
            "TCR试验澄清关闭单、DEN设计变更通知单、DEN设计变更通知关闭单、设计审查意见、设计审查意见答复").indexOf(typeName)!=-1){
                _self.selectedTabName='t02';
                _self.isShowDesgin=false;
                _self.isShowRelevant=true;
                _self.isShowAttachmentDoc=false;
                _self.isShowMeet=false;
                if(typeName=='DEN设计变更通知单' || typeName=='FCR现场变更申请单'){
                    _self.isShowMaterial=true
                }else{
                    _self.isShowMaterial=false
                }
                 clearInterval(_self.timer);
              _self.setTimeout(()=>{
                    _self.$refs.relevantDoc.parentId=id;
                    if(typeName=='DEN设计变更通知单' || typeName=='FCR现场变更申请单'){
                        _self.$refs.MaterialDoc.parentId=id;
                        _self.$refs.MaterialDoc.loadGridInfo();
                        _self.$refs.MaterialDoc.loadGridData();
                    }
                    _self.getRelatinItemByTypeName(typeName,_self.$refs.relevantDoc,function(val){
                    _self.relation=val;
                    // _self.$refs.relevantDoc.loadGridInfo();
                    // _self.$refs.relevantDoc.loadGridData();
                    });
                },200)
                
            }
        },
        MaterialDocSelect(val){
            this.MaterialDocSelected=val;
        },
        relevantDocSelect(val){
            this.relevantDocSelected=val;
        },
        MeetDocSelect(val){
            this.MeetDocSelected=val;
        },
        clickNewItem(){
            let _self=this;
            
            _self.childrenTypeSelectVisible=true;
        },
        beforeCreateDocItem(typeName,relationName) {
                let _self = this;
                if(typeName!='设计文件'&&typeName!='相关文件'&&typeName!='会议纪要内容项'&&typeName!='材料变更清单'){
                    _self.parentId='';
                                     
                }
                if(typeName=='相关文件'){
                    var m = new Map();
                    m.set('parentDocId',_self.parentId);
                    let formdata = new FormData();
                    let ID=''
                    formdata.append("metaData",JSON.stringify(m));
                        axios.post("/dc/checkRelationDocument",formdata,{
                        'Content-Type': 'multipart/form-data'
                    })
                    .then(function(response) {
                        let code = response.data.code;
                        console.log(ID)
                        ID=response.data.ID;
                        if (code == 1) {
                            _self.tables.DialogDataGrid.condition+=" and ID NOT IN ("+ID+")"
                            _self.tables.DialogDataGrid.condition+=" and C_PROJECT_NAME='"+_self.selectRow.C_PROJECT_NAME+"'"
                            _self.$refs.DialogDataGrid.condition=_self.tables.DialogDataGrid.condition
                            console.log(_self.$refs.DialogDataGrid.condition)
                            _self.$refs.DialogDataGrid.loadGridInfo()
                            _self.$refs.DialogDataGrid.loadGridData()
                            _self.propertyrela=true
                            return
                        }else{
                            _self.tables.DialogDataGrid.condition+=" and C_PROJECT_NAME='"+_self.selectRow.C_PROJECT_NAME+"'"
                            _self.$refs.DialogDataGrid.condition=_self.tables.DialogDataGrid.condition
                            _self.$refs.DialogDataGrid.loadGridInfo()
                            _self.$refs.DialogDataGrid.loadGridData()
                            _self.propertyrela=true
                            return
                        }
                    })
                    .catch(function(error) {
                    _self.$message(_self.$t('message.newFailured'));
                    console.log(error);
                    });
                    _self.propertyrela=true
                    return;
                }
                _self.relationName=relationName;
                _self.dialogName = typeName;
                _self.propertyVisible = true;
                
                setTimeout(()=>{
                    if(_self.$refs.ShowProperty){
                        _self.$refs.ShowProperty.myItemId = "";
                        _self.dialogName=typeName;
                        _self.$refs.ShowProperty.myTypeName =typeName;
                        
                        if(typeName=='相关文件'){
                            _self.$refs.ShowProperty.showUploadFile = false;
                            _self.$refs.ShowProperty.formName=_self.relation.formName;
                        }else if(typeName=='会议纪要内容项'||typeName=='材料变更清单'){
                            _self.$refs.ShowProperty.showUploadFile = false;
                        }
                        else{
                            _self.$refs.ShowProperty.showUploadFile = true;
                            _self.$refs.ShowProperty.formName="";
                        }
                        _self.typeName=typeName;
                        
                        if('设计文件'==typeName){
                            _self.$refs.ShowProperty.setMainObject(_self.selectRow);
                            let mp=new Map();
                            mp.set("C_PROJECT_NAME",'C_PROJECT_NAME');
                            mp.set("C_REF_CODING",'CODING');
                            mp.set("C_ITEM_DATE",'C_ITEM_DATE');

                            _self.$refs.ShowProperty.setMainSubRelation(mp);
                        }

                        // _self.$refs.ShowProperty.myFolderId = _self.selectTransferRow.id;
                        _self.$refs.ShowProperty.loadFormInfo();
                    }
                },100);

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
        // 表格行选择
        selectChange(val) {
            this.selectedItems = val;
        },
        selectChangeTransferDoc(val) {
            this.selectedTransferDocItems = val;
        },
        // 保存文档
        saveItem()
        {
            let _self = this;
            if(!this.$refs.ShowProperty.validFormValue()){
                return;
            }
            _self.butt=true
            var m = new Map();
            var c;
            for(c in _self.$refs.ShowProperty.dataList){
                let dataRows = _self.$refs.ShowProperty.dataList[c].ecmFormItems;
                var i;
                for (i in dataRows) {
                if(dataRows[i].attrName && dataRows[i].attrName !='')
                {
                    if(dataRows[i].attrName !='FOLDER_ID'&&dataRows[i].attrName !='ID')
                    {
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
            if(_self.$refs.ShowProperty.myItemId!='')
            {
                m.set('ID',_self.$refs.ShowProperty.myItemId);
            }
            if(_self.$refs.ShowProperty.myTypeName!='')
            {
                m.set('TYPE_NAME',_self.$refs.ShowProperty.myTypeName);
                m.set('FOLDER_ID',_self.$refs.ShowProperty.myFolderId);
                m.set("parentDocId", _self.parentId);
                m.set("relationName",_self.relationName);
            }
            _self.validateData(m,function(isOk){
                if(isOk==false){
                    _self.$message({
                        showClose: true,
                        message: _self.$t('message.dataIsnotOnly'),
                        duration: 2000,
                        type: 'error'
                    });
                    _self.butt=false;
                    return;
                }
                let formdata = new FormData();
                formdata.append("metaData",JSON.stringify(m));
                
                if(_self.$refs.ShowProperty.file!="")
                {
                    //console.log(_self.file);
                    formdata.append("uploadFile",_self.$refs.ShowProperty.file.raw);
                }
                // console.log(JSON.stringify(m));
                if(_self.$refs.ShowProperty.myItemId=='')
                {
                    axios.post("/dc/newDocumentOrSubDoc",formdata,{
                        'Content-Type': 'multipart/form-data'
                    })
                    .then(function(response) {
                        let code = response.data.code;
                        //console.log(JSON.stringify(response));
                        if (code == 1) {
                            // _self.$message(_self.$t('message.newSuccess'));
                            _self.$message({
                                showClose: true,
                                message: _self.$t('message.newSuccess'),//_self.$t('message.newSuccess')
                                duration: 2000,
                                type: "success"
                            });
                            _self.butt=false;
                            _self.propertyVisible = false;
                            _self.propertyrela=false

                            // _self.loadTransferGridData();
                            if(_self.$refs.ShowProperty.myTypeName !="设计文件"&&
                            _self.$refs.ShowProperty.myTypeName !="相关文件"&&
                            _self.$refs.ShowProperty.myTypeName !="附件"&&
                            _self.$refs.ShowProperty.myTypeName !="会议纪要内容项"&&
                            _self.$refs.ShowProperty.myTypeName !="材料变更清单"){
                                _self.$refs.mainDataGrid.loadGridData();
                                _self.$refs.transferDoc.itemDataList=[];
                                _self.$refs.relevantDoc.itemDataList=[];
                               
                                _self.$refs.MeetDoc.itemDataList=[];
                                _self.$refs.MaterialDoc.itemDataList=[];
                            }
                            else{
                                if(_self.$refs.transferDoc!=undefined){
                                    _self.$refs.transferDoc.loadGridData();
                                }
                                if(_self.$refs.relevantDoc!=undefined){
                                    _self.$refs.relevantDoc.loadGridData();
                                }
                                if(_self.$refs.MaterialDoc!=undefined){
                                    _self.$refs.MaterialDoc.loadGridData();
                                }
                                if(_self.$refs.MeetDoc!=undefined){
                                    _self.$refs.MeetDoc.loadGridData();
                                }
                            }
                        } 
                        else if(response.data.MES!=""){
                            _self.$message({
                                showClose: true,
                                message: response.data.MES,
                                duration: 2000,
                                type: "warning"
                            });
                            _self.butt=false;
                        
                        }else{
                            _self.$message({
                                showClose: true,
                                message: _self.$t('message.newFailured'),
                                duration: 2000,
                                type: "warning"
                            });
                            _self.butt=false;
                        }
                    })
                    .catch(function(error) {
                    _self.$message(_self.$t('message.newFailured'));
                    console.log(error);
                    _self.butt=false;
                    });
                }
                else
                {
                    if(_self.$refs.ShowProperty.permit<5){
                    _self.$message(_self.$t('message.hasnoPermssion'));
                    _self.butt=false;
                    return ;
                    }
                    axios.post("/dc/saveDocument",JSON.stringify(m))
                    .then(function(response) {
                    let code = response.data.code;
                    //console.log(JSON.stringify(response));
                    if(code==1){
                        _self.butt=false;
                        _self.$emit('onSaved','update');
                    }
                    else{
                        _self.butt=false;
                        _self.$message(_self.$t('message.saveFailured'));
                    }
                    })
                    .catch(function(error) {
                    _self.$message(_self.$t('message.saveFailured'));
                    _self.butt=false;
                    console.log(error);
                    });
                }
            });
        
        },
        
        // 保存结果事件
        onSaved(indata) {
        let _self=this;
        
        _self.butt=true
        if (indata == "update") {
            // _self.$message(_self.$t("message.saveSuccess"));
            _self.$message({
                showClose: true,
                message: _self.$t("message.saveSuccess"),
                duration: 2000,
                type: 'success'
            });
            _self.butt=false
        } else {
            // _self.$message("新建成功!");
            _self.$message({
                showClose: true,
                message: _self.$t('message.operationSuccess'),
                duration: 2000,
                type: 'success'
            });
            _self.butt=false
        }
        _self.propertyVisible = false;
        _self.propertyrela=false
        _self.butt=false
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
        loadSelectOption(queryName,val){
            let _self = this;
            var m = new Map();
            m.set("queryName", queryName);
            m.set("dependValue", val);
            axios.post("/dc/getSelectList",JSON.stringify(m))
                .then(function(response) {
                if(response.data.code == 1){
                    _self.subContractor= response.data.data;
                }
                })
                .catch(function(error) {
                console.log(error);
                });
            },
    },
    props: {
        allowEdit: {type:Boolean, default:true}
    },
    components: {
        ShowProperty:ShowProperty,
        DataGrid:DataGrid,
        MountFile:MountFile,
        BatchImport:BatchImport,
        DataSelect:DataSelect,
        DataLayout:DataLayout,
        AttachmentFile:AttachmentFile,
        MeetMaterialImport:MeetMaterialImport
    }
}
</script>
<style scoped>

</style>