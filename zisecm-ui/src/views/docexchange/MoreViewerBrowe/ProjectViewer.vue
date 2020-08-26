<template>
    <div>
        <el-container>
            <el-aside>
                <el-tree
                    class="filter-tree"
                    :data="data"
                    :props="defaultProps"
                    :filter-node-method="filterNode"
                    ref="tree"
                    @node-click="nodeClick">
                </el-tree>
            </el-aside>
            <el-main>
                <template v-if="isDC">
                    <el-row>
                        <el-col :span="24">
                        <el-form :inline="true" :model="filters" @submit.native.prevent>
                            <el-form-item>
                                <el-input width="100px" v-model="filters.title" 
                                :placeholder="$t('application.PostNumber')+$t('application.or')+$t('application.Title')" 
                                @keyup.enter.native='searchItem'></el-input>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="primary" v-on:click="searchItem">{{$t('application.SearchData')}}</el-button>
                            </el-form-item>
                            <el-form-item>
                                <AddCondition @sendMsg='searchItem' :typeName="typeName" v-model="advCondition" v-bind:inputValue="advCondition" inputType='hidden'></AddCondition>
                            </el-form-item> 
                            <el-form-item>
                                <el-button type="primary" v-on:click="exportData">{{$t('application.ExportExcel')}}</el-button>
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
                            v-bind:isshowOption="true"
                            gridViewName="DCTransferGrid"
                            :isshowCustom="true"
                            :isInitData="false"
                            @rowclick="rowClick"
                            :isEditProperty="false"
                            :isshowSelection="false"
                            >
                                
                            </DataGrid>
                    </el-row>
                    <el-row>
                        <el-tabs  v-model="selectedTabName">
                            <el-tab-pane :label="$t('application.TransferDoc')" name="t01" v-if="isShowDesgin">
                                <el-button type="primary" @click="packDownloadSubFile(selectedTransferDocItems)">{{$t('application.PackToDownload')}}</el-button>
                            <!--列表-->
                            <DataGrid
                                    ref="transferDoc"
                                    key="transferDocKey"
                                    dataUrl="/dc/getDocuByRelationParentId"
                                    v-bind:tableHeight="rightTableHeight"
                                    v-bind:isshowOption="true"
                                    gridViewName="DrawingGrid"
                                    condition=" and a.NAME='设计文件'"
                                    :isshowCustom="true"
                                    :isEditProperty="false"
                                    @selectchange="selectChangeTransferDoc"
                                    ></DataGrid>
                            </el-tab-pane>
                            <el-tab-pane :label="$t('application.relevant')" name="t02" v-if="isShowRelevant" ref="relevantTab">
                            <!-- 打包下载 -->
                            <el-button type="primary" @click="packDownloadSubFile(relevantDocSelected)">{{$t('application.PackToDownload')}}</el-button>
                            
                            <!--列表-->
                            <DataGrid
                                    ref="relevantDoc"
                                    key="relevantDocKey"
                                    dataUrl="/dc/getDocuByRelationParentId"
                                    v-bind:tableHeight="rightTableHeight"
                                    v-bind:isshowOption="true"
                                    gridViewName="DrawingGrid"
                                    condition=" and a.NAME='相关文件'"
                                    :isshowCustom="true"
                                    :isEditProperty="false"
                                    @selectchange="relevantDocSelect"
                                    ></DataGrid>
                            
                            </el-tab-pane>
                            <el-tab-pane :label="$t('application.Attachment')" name="t03" v-if='isShowAttachmentDoc'>
                            <!-- 打包下载 -->
                            <el-button type="primary" @click="packDownloadSubFile(selectedAttachment)">{{$t('application.PackToDownload')}}</el-button>
                            
                            <!--列表-->
                            <DataGrid
                                    ref="attachmentDoc"
                                    key="attachmentDocKey"
                                    dataUrl="/dc/getDocuByRelationParentId"
                                    v-bind:tableHeight="rightTableHeight"
                                    v-bind:isshowOption="true"
                                    gridViewName="AttachmentGrid"
                                    condition=" and a.NAME='附件'"
                                    :isshowCustom="true"
                                    :isEditProperty="false"
                                    @selectchange="attachmentDocSelect"
                                    ></DataGrid>
                            </el-tab-pane>
                        </el-tabs>
                    </el-row>
                </template>
                <template v-if="isIED">
                    <IEDPublishedView :view=true :project="projectName"></IEDPublishedView>
                </template>
                <template v-if="isProject">
                    <el-row>
                        <el-col :span="24">
                        <el-form :inline="true" :model="filters" @submit.native.prevent>
                            <el-form-item>
                                <el-input width="100px" v-model="filtersPLAN.title" placeholder="WBS编码或标题" @keyup.enter.native='searchItemPlan'></el-input>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="primary" v-on:click="searchItemPlan">{{$t('application.SearchData')}}</el-button>
                            </el-form-item>
                            <el-form-item>
                                <AddCondition @sendMsg='searchItemPlan' :typeName="typeName" v-model="advCondition" v-bind:inputValue="advCondition" inputType='hidden'></AddCondition>
                            </el-form-item> 
                            <el-form-item>
                                <el-button type="primary" v-on:click="exportDataByObj($refs.PlanDataGrid)">{{$t('application.ExportExcel')}}</el-button>
                            </el-form-item> 
                         </el-form>
                        </el-col>
                    </el-row>
                    <el-row>
                        <DataGrid
                            ref="PlanDataGrid"
                            key="PlanDataGrid"
                            dataUrl="/dc/getDocuments"
                            v-bind:tableHeight="rightTableHeight"
                            v-bind:isshowOption="true"
                            gridViewName="PlanTaskGrid"
                            condition=" TYPE_NAME='计划任务' "
                            :isshowCustom="true"
                            :isInitData="false"
                            :isEditProperty="false"
                            @rowclick="rowClickPlan"
                            :isshowSelection="false"
                            >
                        </DataGrid>
                    </el-row>
                    <el-row>
                        <el-tabs  value="t01">
                            <el-tab-pane label="相关IED" name="t01">
                                <el-button type="primary" v-on:click="exportDataByObj($refs.IEDGrid)">{{$t('application.ExportExcel')}}</el-button>
                                    <!--列表-->
                                    <DataGrid
                                            ref="IEDGrid"
                                            key="IEDGrid"
                                            dataUrl="/dc/getDocuments"
                                            v-bind:tableHeight="rightTableHeight"
                                            v-bind:isshowOption="true"
                                            gridViewName="IEDGrid"
                                            :isInitData="false"
                                            :isshowCustom="true"
                                            :isEditProperty="false"
                                            @selectchange="icmTransferSelect"
                                            ></DataGrid>
                            </el-tab-pane>
                           
                        </el-tabs>
                    </el-row>
                </template>
                <template v-if="isICM">
                    <el-row>
                        <el-col :span="24">
                        <el-form :inline="true" :model="filters" @submit.native.prevent>
                            <el-form-item>
                                <el-input width="100px" v-model="filtersICM.title" :placeholder="$t('application.PostNumber')+$t('application.or')+$t('application.Title')"  @keyup.enter.native='searchItemICM'></el-input>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="primary" v-on:click="searchItemICM">{{$t('application.SearchData')}}</el-button>
                            </el-form-item>
                            <el-form-item>
                                <AddCondition @sendMsg='searchItemICM' :typeName="typeName" v-model="advCondition" v-bind:inputValue="advCondition" inputType='hidden'></AddCondition>
                            </el-form-item> 
                            <el-form-item>
                                <el-button type="primary" v-on:click="exportDataByObj($refs.ICMDataGrid)">{{$t('application.ExportExcel')}}</el-button>
                            </el-form-item> 
                         </el-form>
                        </el-col>
                    </el-row>
                    <el-row>
                        <DataGrid
                            ref="ICMDataGrid"
                            key="ICMDataGrid"
                            dataUrl="/dc/getDocuments"
                            v-bind:tableHeight="rightTableHeight"
                            v-bind:isshowOption="true"
                            gridViewName="ICMGrid"
                            :isshowCustom="true"
                            :isInitData="false"
                            :isEditProperty="false"
                            @rowclick="rowClickICM"
                            :isshowSelection="false"
                            >
                        </DataGrid>
                    </el-row>
                    <el-row>
                        <el-tabs  value="t01">
                            <el-tab-pane label="接口传递" name="t01">
                                <el-button type="primary" v-on:click="exportDataSubTable($refs.ICMTransfer)">{{$t('application.ExportExcel')}}</el-button>
                                    <!--列表-->
                                    <DataGrid
                                            ref="ICMTransfer"
                                            key="ICMTransfer"
                                            dataUrl="/dc/getDocuByRelationParentId"
                                            v-bind:tableHeight="rightTableHeight"
                                            v-bind:isshowOption="true"
                                            gridViewName="ICMPassGrid"
                                            condition=" and a.NAME='传递接口'"
                                            :isshowCustom="true"
                                            :isEditProperty="false"
                                            @selectchange="icmTransferSelect"
                                            ></DataGrid>
                                    </el-tab-pane>
                            <el-tab-pane label="接口意见" name="t02">
                                <!-- 导出Excel -->
                                <el-button type="primary" v-on:click="exportDataSubTable($refs.ICMComments)">{{$t('application.ExportExcel')}}</el-button>
                                
                                <!--列表-->
                                <DataGrid
                                        ref="ICMComments"
                                        key="ICMComments"
                                        dataUrl="/dc/getDocuByRelationParentId"
                                        v-bind:tableHeight="rightTableHeight"
                                        v-bind:isshowOption="true"
                                        gridViewName="ICMCommentsGrid"
                                        condition=" and a.NAME='接口意见'"
                                        :isshowCustom="true"
                                        :isEditProperty="false"
                                        @selectchange="icmCommentsSelect"
                                        ></DataGrid>
                                
                            </el-tab-pane>
                        </el-tabs>
                    </el-row>
                </template>
                <template v-if="isDesign">
                    <el-row>
                        <el-col :span="24">
                        <el-form :inline="true" :model="filters" @submit.native.prevent>
                            <el-form-item>
                                <el-input width="100px" v-model="filtersDesign.title" :placeholder="$t('application.Coding')+$t('application.or')+$t('application.Title')" @keyup.enter.native='searchItemDesign'></el-input>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="primary" v-on:click="searchItemDesign">{{$t('application.SearchData')}}</el-button>
                            </el-form-item>
                            <el-form-item>
                                <!-- <el-button type="success" >{{$t('application.AdvSearch')}}</el-button> -->
                                <AddCondition @sendMsg='searchItemDesign' :typeName="typeName" v-model="advCondition" v-bind:inputValue="advCondition" inputType='hidden'></AddCondition>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="primary" v-on:click="exportDataDesign($refs.projDesignDoc)">{{$t('application.ExportExcel')}}</el-button>
                            </el-form-item> 
                         </el-form>
                        </el-col>
                    </el-row>
                    <el-row>
                        <DataGrid
                            ref="projDesignDoc"
                            key="projDesignDoc"
                            dataUrl="/exchange/project/getDesignAtProjectView"
                            v-bind:tableHeight="rightTableHeight"
                            v-bind:isshowOption="true" v-bind:isshowSelection ="false"
                            gridViewName="DrawingGrid"
                            :isEditProperty="false"
                            :isshowCustom="true"
                            :isInitData="false"
                            ></DataGrid>
                    </el-row>
                    <el-row>
                        <el-tabs  value="t01">
                            <el-tab-pane :label="$t('application.relevant')" name="t01">
                                <el-button type="primary" v-on:click="exportDataSubTable($refs.projRelevantDoc)">{{$t('application.ExportExcel')}}</el-button>
                                <!--列表-->
                                <DataGrid
                                        ref="projRelevantDoc"
                                        key="projRelevantDoc"
                                        dataUrl="/dc/getDocuByRelationParentId"
                                        v-bind:tableHeight="rightTableHeight"
                                        v-bind:isshowOption="true"
                                        gridViewName="DrawingGrid"
                                        condition=" and a.NAME='相关文件'"
                                        :isshowCustom="true"
                                        :isEditProperty="false"
                                        @selectchange="icmTransferSelect"
                                        ></DataGrid>
                            </el-tab-pane>
                        </el-tabs>
                    </el-row>
                </template>
            </el-main>
        </el-container>
    </div>
</template>
<script type="text/javascript">
import ShowProperty from "@/components/ShowProperty";
import DataGrid from "@/components/DataGrid";
import ExcelUtil from '@/utils/excel.js';
import AddCondition from '@/views/record/AddCondition';
import IEDPublishedView from '../IEDManagement/IEDpublished'
export default {
    name: "ProjectViewer",
    data(){
        return{
            defaultProps: {
                children: 'children',
                label: 'name'
            },
            data:[],
            typeName:"",
            projectName:"",
            isShowDesgin:true,
            isShowRelevant:true,
            isShowAttachmentDoc:true,
            parentId:"",
            selectedTabName:'t01',
            rightTableHeight: window.innerHeight/2-150,
            rightTableHeightIED:window.innerHeight - 175,
            advCondition:"",
            filters: {
                title: ""
            },
            filtersIED: {
                title: ""
            },
            filtersICM: {
                title: ""
            },
            filtersPLAN: {
                title: ""
            },
            filtersDesign: {
                title: ""
            },
            condition:"",
            selectedAttachment:[],
            relevantDocSelected:[],
            selectedTransferDocItems:[],
            isDC:true,
            isDesign:false,
            isIED:false,
            isProject:false,
            isICM:false,
            tables:{
                main:{
                    gridName:"IEDGrid",
                    dataList:[],
                    height:"",
                    isInitData:false

                },
               loading: false,
               status : '已完成',
               selectedItems: [],
               selectedItemId: "",
            },
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
        this.getTreeData();
    },
    methods: {
        cellMouseEnter(row, column, cell, event){
        this.selectRow=row;
 
        },
        selectChangeTransferDoc(val) {
            this.selectedTransferDocItems = val;
        },
        relevantDocSelect(val){
            this.relevantDocSelected=val;
        },
        attachmentDocSelect(val){
            this.selectedAttachment=val;
        },
        getTreeData(){
            let _self=this;
            axios
                .post("/exchange/project/getProjectView")
                .then(function(response) {
                _self.data = response.data.data;
                })
                .catch(function(error) {
                console.log(error);
                });
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
        exportDataByObj(gridObj){
            let dataUrl = "/exchange/doc/export"
            let params = {
                gridName:gridObj.gridViewName,
                lang:"zh-cn",
                condition:gridObj.condition,
                filename:"exportExcel"+new Date().Format("yyyy-MM-dd hh:mm:ss")+".xlsx",
                sheetname:"Result"
            }
            ExcelUtil.export(params)
        },
        //导出字表文件
        exportDataSubTable(gridObj){
            let dataUrl = "/exchange/doc/exportByParentId"
            let params = {
                gridName:gridObj.gridViewName,
                lang:"zh-cn",
                condition:gridObj.condition,
                filename:"exportExcel"+new Date().Format("yyyy-MM-dd hh:mm:ss")+".xlsx",
                sheetname:"Result",
                parentId:this.parentId,
                URL:"/exchange/doc/exportByParentId"
            }
            ExcelUtil.export4Cnpe(params)
        },
        //导出设计文件
        exportDataDesign(gridObj){
            let dataUrl = "/exchange/doc/exportByParentId"
            let params = {
                gridName:gridObj.gridViewName,
                lang:"zh-cn",
                condition:gridObj.condition,
                filename:"exportExcel"+new Date().Format("yyyy-MM-dd hh:mm:ss")+".xlsx",
                sheetname:"Result",
                parentId:this.parentId,
                URL:"/exchange/doc/exportDesignInProjView"
            }
            ExcelUtil.export4Cnpe(params)
        },
        nodeClick(data,node,current){
            
            if(node.parent.data.name==undefined){
                return;
            }
            if(node.data.name=='文函'){
                return;
            }
            if(node.parent.data.name=="文函"){//文函查询
                this.isDC=true;
                this.isDesign=false;
                this.isIED=false;
                this.isProject=false;
                this.isICM=false;
                this.projectName=node.parent.parent.data.name;
                this.typeName=data.name;
                let user = this.currentUser();
                if(user.userType==2 && user.company!=null){
                    this.$refs.mainDataGrid.condition=this.condition=" TYPE_NAME='"+this.typeName+"' "
                    +"and (STATUS is not null and STATUS!='' and STATUS!='新建')"
                    +" and C_PROJECT_NAME = '"+this.projectName+"' AND (C_COMPANY='"+user.company +"'"
                    +" or C_TO like'%"+user.company+"%')";
                    this.$refs.mainDataGrid.loadGridData();
                }else{
                    this.$refs.mainDataGrid.condition=this.condition=" TYPE_NAME='"+this.typeName+"' "
                    +"and (STATUS is not null and STATUS!='' and STATUS!='新建') "
                    +"and C_PROJECT_NAME = '"+this.projectName+"' ";
                    this.$refs.mainDataGrid.loadGridData();
                }
                
                
                let _self=this;
                if(_self.$refs.transferDoc){
                    _self.$refs.transferDoc.itemDataList=[];
                }
                if(_self.$refs.relevantDoc){
                    _self.$refs.relevantDoc.itemDataList=[];
                }
                if(_self.$refs.attachmentDoc){
                    _self.$refs.attachmentDoc.itemDataList=[];
                }
            }else{
                this.projectName=node.parent.data.name
                if(node.data.name=='ICM'){
                    this.isDC=false;
                    this.isDesign=false;
                    this.isIED=false;
                    this.isProject=false;
                    this.isICM=true;
                    this.typeName=data.name;
                    let _self=this;
                    _self.$nextTick(()=>{
                        let user = _self.currentUser();
                        if(user.userType==2 && user.company!=null){
                            _self.$refs.ICMDataGrid.condition=_self.condition=" TYPE_NAME='"+_self.typeName+"' "
                            +" and C_PROJECT_NAME = '"+_self.projectName+"' AND (C_COMPANY='"+user.company +"'"
                            +" or C_TO like'%"+user.company+"%')";
                            _self.$refs.ICMDataGrid.loadGridData();
                        }else{
                            _self.$refs.ICMDataGrid.condition=_self.condition=" TYPE_NAME='"+_self.typeName+"' "
                            +"and C_PROJECT_NAME = '"+_self.projectName+"' ";
                            _self.$refs.ICMDataGrid.loadGridData();
                        }
                    
                        
                    });
                    
                }else if(node.data.name=='计划'){
                    this.isDC=false;
                    this.isDesign=false;
                    this.isIED=false;
                    this.isProject=true;
                    this.isICM=false;
                    this.typeName=data.name;
                    let user = this.currentUser();
                    let _self=this;
                    _self.$nextTick(()=>{
                        if(user.userType==2 && user.company!=null){
                            _self.$refs.PlanDataGrid.condition=_self.condition=" TYPE_NAME='计划任务' "
                            +" and C_PROJECT_NAME = '"+_self.projectName+"' AND (C_COMPANY='"+user.company +"'"
                            +" or C_TO like'%"+user.company+"%')";
                            _self.$refs.PlanDataGrid.loadGridData();
                        }else{
                            _self.$refs.PlanDataGrid.condition=_self.condition=" TYPE_NAME='计划任务' "
                            +"and C_PROJECT_NAME = '"+_self.projectName+"' ";
                            _self.$refs.PlanDataGrid.loadGridData();
                        }
                        
                        
                    });
                }else if(node.data.name=='IED'){
                    this.isDC=false;
                    this.isDesign=false;
                    this.isIED=true;
                    this.isProject=false;
                    this.isICM=false;
                    this.typeName=data.name;
                    let user = this.currentUser();
                    let _self=this;
                    /* _self.$nextTick(()=>{
                        if(user.userType==2 && user.company!=null){
                            _self.condition=" TYPE_NAME='"+_self.typeName+"' "
                            +"and (STATUS is not null and STATUS!='' and STATUS!='新建')"
                            +" and C_PROJECT_NAME = '"+_self.projectName+"' AND (C_COMPANY='"+user.company +"'"
                            +" or C_TO like'%"+user.company+"%')";
                            _self.$refs.mainDataGridIED.condition=_self.condition;
                            _self.$refs.mainDataGridIED.loadGridData();
                        }else{
                            _self.condition=" TYPE_NAME='"+_self.typeName+"' "
                            +"and (STATUS is not null and STATUS!='' and STATUS!='新建') "
                            +"and C_PROJECT_NAME = '"+_self.projectName+"' ";
                            _self.$refs.mainDataGridIED.condition=_self.condition;
                            _self.$refs.mainDataGridIED.loadGridData();
                        }
                        
                        
                    }); */
                }else if(node.data.name=='设计文件'){
                    this.isDC=false;
                    this.isDesign=true;
                    this.isIED=false;
                    this.isProject=false;
                    this.isICM=false;
                    this.typeName=data.name;
                    let user = this.currentUser();
                    let _self=this;
                    _self.$nextTick(()=>{
                        if(user.userType==2 && user.company!=null){
                            _self.$refs.projDesignDoc.condition=_self.condition=" and TYPE_NAME='"+_self.typeName+"' "
                            +" and C_PROJECT_NAME = '"+_self.projectName+"' AND (C_COMPANY='"+user.company +"'"
                            +" or C_TO like'%"+user.company+"%')";
                            _self.$refs.projDesignDoc.loadGridData();
                        }else{
                            _self.$refs.projDesignDoc.condition=_self.condition=" and TYPE_NAME='"+_self.typeName+"' "
                            +"and C_PROJECT_NAME = '"+_self.projectName+"' ";
                            _self.$refs.projDesignDoc.loadGridData();
                        }
                        
                        
                    });
                }
            }
            
        },
        searchItemDesign(){
            let _self=this;
            let key=" "+_self.condition;
           
            if(_self.filtersDesign.title!=''){
                key+=" and (CODING like '%"+_self.filtersDesign.title+"%' "
                +"or TITLE like '%"+_self.filtersDesign.title+"%' "
                +")";
            }
            if(_self.advCondition!=''){
                key+="and ("+_self.advCondition+")";
                _self.advCondition='';
            }
            if(key!=''){
                _self.$refs.projDesignDoc.condition=key;
            }
            _self.$refs.projDesignDoc.loadGridData();
        },
        searchItemPlan(){
            let _self=this;
            let key=" "+_self.condition;
           
            if(_self.filtersPLAN.title!=''){
                key+=" and (C_WBS_CODING like '%"+_self.filtersPLAN.title+"%' "
                +"or TITLE like '%"+_self.filtersPLAN.title+"%' "
                +")";
            }
            if(_self.advCondition!=''){
                key+="and ("+_self.advCondition+")";
                _self.advCondition='';
            }
            if(key!=''){
                _self.$refs.PlanDataGrid.condition=key;
            }
            _self.$refs.PlanDataGrid.loadGridData();
        },

        searchItemICM(){
            let _self=this;
            let key=" "+_self.condition;
           
            if(_self.filtersICM.title!=''){
                key+=" and (C_WBS_CODING like '%"+_self.filtersICM.title+"%' "
                +"or C_CODE1 like '%"+_self.filtersICM.title+"%' "
                +"or C_CODE2 like '%"+_self.filtersICM.title+"%' "
                +"or C_CODE3 like '%"+_self.filtersICM.title+"%' "
                +"or C_CODE4 like '%"+_self.filtersICM.title+"%' "
                +"or C_CODE5 like '%"+_self.filtersICM.title+"%' "
                +"or C_CODE6 like '%"+_self.filtersICM.title+"%' "
                +"or CODING like '%"+_self.filtersICM.title+"%' "
                +"or TITLE like '%"+_self.filtersICM.title+"%' "
                +")";
            }
            if(_self.advCondition!=''){
                key+="and ("+_self.advCondition+")";
                _self.advCondition='';
            }
            if(key!=''){
                _self.$refs.ICMDataGrid.condition=key;
            }
            _self.$refs.ICMDataGrid.loadGridData();
        },
        searchItemIED(){
            let _self=this;
            let key=" "+_self.condition;
           
            if(_self.filtersIED.title!=''){
                key+=" and (C_WBS_CODING like '%"+_self.filtersIED.title+"%' "
                +"or C_IN_CODING like '%"+_self.filtersIED.title+"%' "
                +"or C_FROM_CODING like '%"+_self.filtersIED.title+"%' "
                +"or CODING like '%"+_self.filtersIED.title+"%' "
                +"or TITLE like '%"+_self.filtersIED.title+"%' "
                +")";
            }
            if(_self.advCondition!=''){
                key+="and ("+_self.advCondition+")";
                _self.advCondition='';
            }
            if(key!=''){
                _self.$refs.mainDataGridIED.condition=key;
            }
            _self.$refs.mainDataGridIED.loadGridData();
        },
        searchItem(){
            let _self=this;
            if(_self.condition==''){
                _self.condition=" C_PROJECT_NAME ='@project'"
            }
            let key=" "+_self.condition;
           
            if(_self.filters.title!=''){
                key+=" and (C_CONTENT like '%"+_self.filters.title+"%' "
                +"or C_FROM like '%"+_self.filters.title+"%' "
                +"or C_TO like '%"+_self.filters.title+"%' "
                +"or CODING like '%"+_self.filters.title+"%' "
                +"or C_OTHER_COIDNG like '%"+_self.filters.title+"%' "
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
        },
        rowClickPlan(row){
             let _self=this;
            let user = _self.currentUser();
            if(user.userType==2 && user.company!=null){
                _self.$refs.IEDGrid.condition="C_WBS_CODING='"+row.C_WBS_CODING
                +"' TYPE_NAME='IED' AND C_PROJECT_NAME = '"+_self.projectName+"' AND (C_COMPANY='"+user.company +"'"
                +" or C_TO like'%"+user.company+"%')";
            }else{
                _self.$refs.IEDGrid.condition=" C_WBS_CODING='"+row.C_WBS_CODING+"' and  TYPE_NAME='IED' "
                +"and C_PROJECT_NAME = '"+_self.projectName+"' ";
            }

            _self.$nextTick(()=>{
                _self.$refs.IEDGrid.loadGridData();
            });
        },
        rowClickICM(row){
            this.parentId=row.ID;
            let _self=this;
             _self.$nextTick(()=>{
                   _self.$refs.ICMTransfer.parentId=row.ID;
                    _self.$refs.ICMTransfer.loadGridData();
                    _self.$refs.ICMComments.parentId=row.ID;
                    _self.$refs.ICMComments.loadGridData();
               });
        },
        rowClick(row){
            this.parentId=row.ID;
            let _self=this;
            if(row.TYPE_NAME=='文件传递单'){
                _self.isShowDesgin=true;
                _self.isShowRelevant=false;
               _self.isShowAttachmentDoc=false;
               _self.selectedTabName='t01';
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
                
                _self.$nextTick(()=>{
                    _self.$refs.relevantDoc.parentId=row.ID;
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
               _self.$nextTick(()=>{
               _self.$refs.attachmentDoc.parentId=row.ID;
                 _self.$refs.attachmentDoc.loadGridData();
               });
            }
            
        },
    },
    
    props: {
        
    },
    components: {
        ShowProperty:ShowProperty,
        DataGrid:DataGrid,
        AddCondition:AddCondition,
        IEDPublishedView:IEDPublishedView
    }
}
</script>
<style scoped>

</style>