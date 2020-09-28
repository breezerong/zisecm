<template>
    <DataLayout>
        <template v-slot:main="{layout}">
            <div :style="{position:'relative',height: layout.height-startHeight+45+'px'}">
                <split-pane split="vertical" @resize="onHorizontalSplitResize" :min-percent='1' :default-percent='leftPercent'>
                    <template slot="paneL">
                        <el-container :style="{height:layout.height-startHeight+45+'px',width:asideWidth,overflow:'auto'}">
                            <el-tree
                                class="filter-tree"
                                :data="data"
                                :props="defaultProps"
                                :filter-node-method="filterNode"
                                highlight-current
                                ref="tree"
                                @node-click="nodeClick">
                            </el-tree>
                        </el-container>
                    </template>
                    <template slot="paneR">
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
                            <div :style="{position:'relative',height: layout.height-startHeight+'px'}">
                                <split-pane v-on:resize="onSplitResize" :min-percent='20' :default-percent='topPercent' split="horizontal">
                                    <template slot="paneL">
                                        <DataGrid
                                            ref="mainDataGrid"
                                            key="main"
                                            dataUrl="/dc/getDocuments"
                                            v-bind:tableHeight="(layout.height-startHeight)*topPercent/100-topbarHeight"
                                            v-bind:isshowOption="true"
                                            gridViewName="DCTransferGrid"
                                            :isshowCustom="true"
                                            :isInitData="false"
                                            @rowclick="rowClick"
                                            :isEditProperty="false"
                                            :isshowSelection="false"
                                            showOptions="查看内容"
                                            >
                                                
                                            </DataGrid>
                                    </template>
                                    <template slot="paneR">
                                        <el-tabs  v-model="selectedTabName">
                                            <el-tab-pane :label="$t('application.TransferDoc')" name="t01" v-if="isShowDesgin">
                                                <el-button type="primary" @click="packDownloadSubFile(selectedTransferDocItems)">{{$t('application.PackToDownload')}}</el-button>
                                            <!--列表-->
                                            <DataGrid
                                                    ref="transferDoc"
                                                    key="transferDocKey"
                                                    dataUrl="/dc/getDocuByRelationParentId"
                                                    v-bind:tableHeight="(layout.height-startHeight)*(100-topPercent)/100-bottomHeight"
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
                                                    v-bind:tableHeight="(layout.height-startHeight)*(100-topPercent)/100-bottomHeight"
                                                    v-bind:isshowOption="true"
                                                    gridViewName="DrawingGrid"
                                                    condition=" and a.NAME='相关文件'"
                                                    :isshowCustom="true"
                                                    :isEditProperty="false"
                                                    @selectchange="relevantDocSelect"
                                                    ></DataGrid>
                                            
                                            </el-tab-pane>
                                            <el-tab-pane :label="$t('application.Attachment')" name="t03" >
                                            <!-- 打包下载 -->
                                            <el-button type="primary" @click="packDownloadSubFile(selectedAttachment)">{{$t('application.PackToDownload')}}</el-button>
                                            
                                            <!--列表-->
                                            <DataGrid
                                                    ref="attachmentDoc"
                                                    key="attachmentDocKey"
                                                    dataUrl="/dc/getDocuByRelationParentId"
                                                    v-bind:tableHeight="(layout.height-startHeight)*(100-topPercent)/100-bottomHeight"
                                                    v-bind:isshowOption="true"
                                                    gridViewName="AttachmentGrid"
                                                    condition=" and a.NAME='附件'"
                                                    :isshowCustom="true"
                                                    :isEditProperty="false"
                                                    @selectchange="attachmentDocSelect"
                                                    ></DataGrid>
                                            </el-tab-pane>
                                            <el-tab-pane :label="$t('application.ContentItems')" name="t04" v-if="isShowMeet">
                                                <el-button type="primary" @click="packDownloadSubFile(selectedTransferDocItems)">{{$t('application.PackToDownload')}}</el-button>
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
                                                    :isEditProperty="true"
                                                    :isShowChangeList="false"
                                                    :isshowicon="false"
                                                    @selectchange="MeetDocSelect"
                                                ></DataGrid>
                                            
                                            </el-tab-pane>
                                            <el-tab-pane :label="$t('application.MaterialChangeList')" name="t05" v-if="isShowMaterial">
                                                <el-button type="primary" @click="packDownloadSubFile(selectedTransferDocItems)">{{$t('application.PackToDownload')}}</el-button>
                                                <!--列表-->
                                                <DataGrid
                                                    ref="MaterialDoc"
                                                    key="MaterialDocKey"
                                                    dataUrl="/dc/getDocuByRelationParentId"
                                                    v-bind:tableHeight="(layout.height-startHeight)*(100-topPercent)/100-bottomHeight"
                                                    v-bind:isshowOption="true" v-bind:isshowSelection ="true"
                                                    gridViewName="MaterialChangeGrid"
                                                    condition=" and a.NAME='材料变更清单'"
                                                    :optionWidth = "1"
                                                    :isShowMoreOption="false"
                                                    :isshowCustom="false"
                                                    :isEditProperty="true"
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
                        <template v-if="isIED">
                            <IEDPublishedView ref="iedgrid" :view="true" :project="'\''+projectName+'\''"></IEDPublishedView>
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
                            <div :style="{position:'relative',height: layout.height-startHeight+'px'}">
                                <split-pane v-on:resize="onSplitResize" :min-percent='20' :default-percent='topPercent' split="horizontal">
                                    <template slot="paneL">
                                        <DataGrid
                                            ref="PlanDataGrid"
                                            key="PlanDataGrid"
                                            dataUrl="/dc/getDocuments"
                                            v-bind:tableHeight="(layout.height-startHeight)*topPercent/100-topbarHeight"
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
                                    </template>
                                    <template slot="paneR">
                                        <el-tabs  value="t01">
                                            <el-tab-pane label="相关IED" name="t01">
                                                <el-button type="primary" v-on:click="exportDataByObj($refs.IEDGrid)">{{$t('application.ExportExcel')}}</el-button>
                                                    <!--列表-->
                                                    <DataGrid
                                                            ref="IEDGrid"
                                                            key="IEDGrid"
                                                            dataUrl="/dc/getDocuments"
                                                            v-bind:tableHeight="(layout.height-startHeight)*(100-topPercent)/100-bottomHeight"
                                                            v-bind:isshowOption="true"
                                                            gridViewName="IEDGrid"
                                                            :isInitData="false"
                                                            :isshowCustom="true"
                                                            :isEditProperty="false"
                                                            :isShowMoreOption="false"
                                                            @selectchange="icmTransferSelect"
                                                            ></DataGrid>
                                            </el-tab-pane>
                                        
                                        </el-tabs>
                                    </template>
                                </split-pane>
                            </div>
                        </template>
                        <template v-if="isICM">
                            <el-row>
                                <el-col :span="24">
                                <el-form :inline="true" :model="filters" @submit.native.prevent>
                                    <el-form-item>
                                        <el-input width="100px" v-model="filtersICM.title" :placeholder="$t('application.codingDesc')"  @keyup.enter.native='searchItemICM'></el-input>
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
                            <div :style="{position:'relative',height: layout.height-startHeight+'px'}">
                                <split-pane v-on:resize="onSplitResize" :min-percent='20' :default-percent='topPercent' split="horizontal">
                                    <template slot="paneL">
                                        <DataGrid
                                            ref="ICMDataGrid"
                                            key="ICMDataGrid"
                                            dataUrl="/dc/getDocuments"
                                            v-bind:tableHeight="(layout.height-startHeight)*topPercent/100-topbarHeight"
                                            v-bind:isshowOption="true"
                                            gridViewName="ICMGrid"
                                            :isshowCustom="true"
                                            :isInitData="false"
                                            :isshowicon="false"
                                            :isShowMoreOption="false"
                                            :isEditProperty="false"
                                            @rowclick="rowClickICM"
                                            :isshowSelection="false"
                                            >
                                        </DataGrid>
                                    </template>
                                    <template slot="paneR">
                                <el-tabs  value="t01">
                                    <el-tab-pane label="接口传递" name="t01">
                                        <el-button type="primary" v-on:click="exportDataSubTable($refs.ICMTransfer)">{{$t('application.ExportExcel')}}</el-button>
                                            <!--列表-->
                                            <DataGrid
                                                    ref="ICMTransfer"
                                                    key="ICMTransfer"
                                                    dataUrl="/dc/getDocuByRelationParentId"
                                                    v-bind:tableHeight="(layout.height-startHeight)*(100-topPercent)/100-bottomHeight"
                                                    v-bind:isshowOption="true"
                                                    gridViewName="ICMPassGrid"
                                                    condition=" and a.NAME='传递接口'"
                                                    :isshowCustom="true"
                                                    :isEditProperty="false"
                                                    @selectchange="icmTransferSelect"
                                                    showOptions="查看内容"
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
                                                            v-bind:tableHeight="(layout.height-startHeight)*(100-topPercent)/100-bottomHeight"
                                                            v-bind:isshowOption="true"
                                                            gridViewName="ICMCommentsGrid"
                                                            condition=" and a.NAME='接口意见'"
                                                            :isshowCustom="true"
                                                            :isEditProperty="false"
                                                            @selectchange="icmCommentsSelect"
                                                            showOptions="查看内容"
                                                            ></DataGrid>
                                                    
                                            </el-tab-pane>
                                        </el-tabs>
                                    </template>
                                </split-pane>
                            </div>
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
                            <div :style="{position:'relative',height: layout.height-startHeight+'px'}">
                                <split-pane v-on:resize="onSplitResize" :min-percent='20' :default-percent='topPercent' split="horizontal">
                                    <template slot="paneL">
                                <DataGrid
                                    ref="projDesignDoc"
                                    key="projDesignDoc"
                                    dataUrl="/exchange/project/getDesignAtProjectView"
                                    v-bind:tableHeight="(layout.height-startHeight)*topPercent/100-topbarHeight"
                                    v-bind:isshowOption="true" v-bind:isshowSelection ="false"
                                    gridViewName="DrawingGrid"
                                    :isEditProperty="false"
                                    :isshowCustom="true"
                                    :isInitData="false"
                                    showOptions="查看内容"
                                    ></DataGrid>
                            </template>
                                    <template slot="paneR">
                                <el-tabs  value="t01">
                                    <el-tab-pane :label="$t('application.relevant')" name="t01">
                                        <el-button type="primary" v-on:click="exportDataSubTable($refs.projRelevantDoc)">{{$t('application.ExportExcel')}}</el-button>
                                        <!--列表-->
                                        <DataGrid
                                                ref="projRelevantDoc"
                                                key="projRelevantDoc"
                                                dataUrl="/dc/getDocuByRelationParentId"
                                                v-bind:tableHeight="(layout.height-startHeight)*(100-topPercent)/100-bottomHeight"
                                                v-bind:isshowOption="true"
                                                gridViewName="DrawingGrid"
                                                condition=" and a.NAME='相关文件'"
                                                :isshowCustom="true"
                                                :isEditProperty="false"
                                                @selectchange="icmTransferSelect"
                                                ></DataGrid>
                                    </el-tab-pane>
                                </el-tabs>
                            </template>
                                </split-pane>
                            </div>
                        </template>
                    </template>
                </split-pane>
            </div>
        </template>
    </DataLayout>
</template>
<script type="text/javascript">
import ShowProperty from "@/components/ShowProperty";
import DataGrid from "@/components/DataGrid";
import ExcelUtil from '@/utils/excel.js';
import AddCondition from '@/views/record/AddCondition';
import IEDPublishedView from '../IEDManagement/IEDpublished'
import DataLayout from '@/components/ecm-data-layout'

export default {
    name: "ProjectViewer",
    data(){
        return{
            leftStorageName: 'ProjectViewerWidth',
            leftPercent: 20,

            // 本地存储高度名称
            topStorageName: 'ProjectViewerHeight',
            // 非split pan 控制区域高度
            startHeight: 135,
            // 顶部百分比*100
            topPercent: 65,
            // 顶部除列表高度
            topbarHeight: 35,
            // 底部除列表高度
            bottomHeight: 120,

            asideWidth: '100%',
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
            isShowMeet:true,
            isShowMaterial:true,
            parentId:"",
            selectedTabName:'t01',
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
            
            MeetDocSelected:[],
            MaterialDocSelected:[],
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
        setTimeout(() => {
            this.topPercent = this.getStorageNumber(this.topStorageName,60)
            this.leftPercent = this.getStorageNumber(this.leftStorageName,20)
        }, 300);
    },
    methods: {
        // 水平分屏事件
        onHorizontalSplitResize(leftPercent){
            // 左边百分比*100
            this.leftPercent = leftPercent
            this.setStorageNumber(this.leftStorageName, leftPercent)
            
        },
        // 上下分屏事件
        onSplitResize(topPercent){
            // 顶部百分比*100
            this.topPercent = topPercent
            this.setStorageNumber(this.topStorageName, topPercent)
            //console.log(JSON.stringify(topPercent))
        },
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
        MeetDocSelect(val){
            this.MeetDocSelected=val;
        },
        MaterialDocSelect(val){
            this.MaterialDocSelected=val;
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
                this.isShowDesgin=true;
                this.isShowRelevant=true;
                this.isShowAttachmentDoc=true;
                this.isShowMeet=true;
                this.isShowMaterial=true
                this.isDC=true;
                this.isDesign=false;
                this.isIED=false;
                this.isProject=false;
                this.isICM=false;
                this.projectName=node.parent.data.name;
                let user = this.currentUser();
                if(user.userType==2 && user.company!=null){
                    this.$refs.mainDataGrid.condition=this.condition=" C_ITEM_TYPE = '文函' and C_IS_RELEASED =1"
                    // +"and (ID  in (select DOC_ID from exc_transfer) or STATUS='已确认')"
                    +" and C_PROJECT_NAME = '"+this.projectName+"' AND (C_COMPANY='"+user.company +"'"
                    +" or C_TO like'%"+user.company+"%')";
                    this.$refs.mainDataGrid.loadGridData();
                }else{
                    this.$refs.mainDataGrid.condition=this.condition=" C_ITEM_TYPE = '文函' and C_IS_RELEASED =1 "
                    // +"and (ID  in (select DOC_ID from exc_transfer) or STATUS='已确认') "
                    +"and C_PROJECT_NAME = '"+this.projectName+"' ";
                    this.$refs.mainDataGrid.loadGridData();
                }
                _self.$refs.transferDoc.itemDataList=[];
                _self.$refs.relevantDoc.itemDataList=[];
                _self.$refs.attachmentDoc.itemDataList=[];
                _self.$refs.MeetDoc.itemDataList=[];
                _self.$refs.MaterialDoc.itemDataList=[];
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
                    this.$refs.mainDataGrid.condition=this.condition=" C_IS_RELEASED = 1 AND TYPE_NAME='"+this.typeName+"' "
                    // +"and (ID  in (select DOC_ID from exc_transfer) or STATUS='已确认')"
                    +" and C_PROJECT_NAME = '"+this.projectName+"' AND (C_COMPANY='"+user.company +"'"
                    +" or C_TO like'%"+user.company+"%')";
                    this.$refs.mainDataGrid.loadGridData();
                }else{
                    this.$refs.mainDataGrid.condition=this.condition=" C_IS_RELEASED = 1 AND TYPE_NAME='"+this.typeName+"' "
                    // +"and (ID  in (select DOC_ID from exc_transfer) or STATUS='已确认') "
                    +"and C_PROJECT_NAME = '"+this.projectName+"' ";
                    this.$refs.mainDataGrid.loadGridData();
                }
                
                
                let _self=this;
               
                if(_self.typeName=='文件传递单'){
                    _self.isShowDesgin=true;
                    _self.isShowRelevant=false;
                    _self.isShowAttachmentDoc=false;
                    _self.selectedTabName='t01';
                    _self.isShowMeet=false;
                    _self.isShowMaterial=false
                }
                if(("FU申请、FU通知单、作废通知单、CR澄清要求申请单、CR澄清要求答复单、CR澄清要求关闭单、FCR现场变更申请单、FCR现场变更答复单、FCR现场变更关闭单、NCR不符合项报告单、NCR不符合项报告答复单、NCR不符合项报告关闭单、"+
                "DCR设计变更申请单、DCR设计变更答复单、DCR设计变更关闭单、TCR试验澄清申请单、TCR试验澄清答复单、"+
                "TCR试验澄清关闭单、DEN设计变更通知单、DEN设计变更通知关闭单、设计审查意见、设计审查意见答复").indexOf(_self.typeName)!=-1){
                    _self.selectedTabName='t02';
                    _self.isShowDesgin=false;
                    _self.isShowRelevant=true;
                    _self.isShowAttachmentDoc=false;
                    _self.isShowMeet=false;
                    if(_self.typeName=='DEN设计变更通知单' || _self.typeName=='FCR现场变更申请单'){
                        _self.isShowMaterial=true
                    }else{
                        _self.isShowMaterial=false
                    }
                }
                if("图文传真,会议纪要,接口信息意见单,接口信息传递单".indexOf(_self.typeName)!=-1){
                    _self.isShowDesgin=false;
                    _self.isShowRelevant=false;
                _self.isShowAttachmentDoc=true;
                _self.selectedTabName='t03';
                _self.isShowMaterial=false
                if(_self.typeName=='会议纪要'){
                        _self.isShowMeet=true;
                    }else{
                        
                        _self.isShowMeet=false;
                    }
                }
                if(_self.$refs.transferDoc){
                    _self.$refs.transferDoc.itemDataList=[];
                }
                if(_self.$refs.relevantDoc){
                    _self.$refs.relevantDoc.itemDataList=[];
                }
                if(_self.$refs.attachmentDoc){
                    _self.$refs.attachmentDoc.itemDataList=[];
                }
                if(_self.$refs.MeetDoc){
                    _self.$refs.MeetDoc.itemDataList=[];
                }
                if(_self.$refs.MaterialDoc){
                    _self.$refs.MaterialDoc.itemDataList=[];
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
                    this.$refs.iedgrid.search()
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
            _self.$refs.projRelevantDoc.itemDataList=[];
            
        },
        searchItemPlan(){
            let _self=this;
            let key=" "+_self.condition;
           
            if(_self.filtersPLAN.title!=''){
                key+=" and (C_WBS_CODING like '%"+_self.filtersPLAN.title+"%' "
                +"or NAME like '%"+_self.filtersPLAN.title+"%' "
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
            _self.$refs.IEDGrid.itemDataList=[];
            
        },

        searchItemICM(){
            let _self=this;
            let key=" "+_self.condition;
           
            if(_self.filtersICM.title!=''){
                // key+=" and (C_WBS_CODING like '%"+_self.filtersICM.title+"%' "
                // +"or C_CODE1 like '%"+_self.filtersICM.title+"%' "
                // +"or C_CODE2 like '%"+_self.filtersICM.title+"%' "
                // +"or C_CODE3 like '%"+_self.filtersICM.title+"%' "
                // +"or C_CODE4 like '%"+_self.filtersICM.title+"%' "
                // +"or C_CODE5 like '%"+_self.filtersICM.title+"%' "
                // +"or C_CODE6 like '%"+_self.filtersICM.title+"%' "
                // +"or CODING like '%"+_self.filtersICM.title+"%' "
                // +"or TITLE like '%"+_self.filtersICM.title+"%' "
                // +")";

                key+=" and (CODING like '%"+_self.filtersICM.title+"%' "
                +"or C_COMMENT like '%"+_self.filtersICM.title+"%' "
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
            _self.$refs.ICMComments.itemDataList=[];
            _self.$refs.ICMTransfer.itemDataList=[];
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
               _self.isShowMeet=false;
               _self.isShowMaterial=false
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
               _self.isShowMaterial=false
               if(row.TYPE_NAME=='会议纪要'){
                    _self.isShowMeet=true;
                    _self.$nextTick(()=>{
                    _self.$refs.MeetDoc.parentId=row.ID;
                    _self.$refs.MeetDoc.loadGridInfo();
                    _self.$refs.MeetDoc.loadGridData();
                    });
                }else{
                    
                    _self.isShowMeet=false;
                }
               
            }
            _self.$nextTick(()=>{
                    _self.$refs.attachmentDoc.parentId=row.ID;
                    _self.$refs.attachmentDoc.loadGridData();
               });
            
        },
    },
    
    props: {
        
    },
    components: {
        ShowProperty:ShowProperty,
        DataGrid:DataGrid,
        AddCondition:AddCondition,
        IEDPublishedView:IEDPublishedView,
        DataLayout:DataLayout
    }
}
</script>
<style>

.el-tree--highlight-current .el-tree-node.is-current > .el-tree-node__content {
    background-color: #d3d3d3 !important;
}
</style>