<template>
    <DataLayout >


            <!-- 回复窗口 -->
        <el-dialog
            :title="$t('application.Reply')"
            :visible.sync="replyVisible"
            width="90%"
            >
            <ShowProperty
                ref="replyProperty"
                width="100%"
                itemId=""
                v-bind:typeName="replyTypeName"
            ></ShowProperty>
            <div slot="footer" class="dialog-footer">
                <el-button @click="saveReplyItem">{{$t('application.save')}}</el-button>
                <el-button @click="replyVisible = false">{{$t('application.cancel')}}</el-button>
            </div>
        </el-dialog>



        <el-dialog :title="$t('application.Import') + ' ICM'" :visible.sync="batchDialogVisible" width="80%" >
            <BatchImport ref="BatchImport"  @onImported="onBatchImported" v-bind:deliveryId="parentId" width="100%"></BatchImport>
            <div slot="footer" class="dialog-footer">
            <el-button @click="batchDialogVisible=false" size="medium">{{$t('application.close')}}</el-button>
            </div>
        </el-dialog>
        <!-- 新建窗口 -->
        <el-dialog
            :title="dialogName"
            :visible.sync="propertyVisible"
            width="80%"
            >
            <ShowProperty
                ref="ShowProperty"
                width="100%"
                itemId=""
                v-bind:typeName="dialogtypeName"
            ></ShowProperty>
            <div slot="footer" class="dialog-footer">
                <el-button @click="saveItem">{{$t('application.save')}}</el-button>
                <el-button @click="propertyVisible = false">{{$t('application.cancel')}}</el-button>
            </div>
        </el-dialog>
        <!-- 打开延误反馈 -->
        <el-dialog :title="延误打开反馈" :visible.sync="Visible1" width="80%">
            <ShowProperty
                ref="ShowProperty"
                width="100%"
                itemId="1"
                v-bind:typeName="dialogtypeName"
            ></ShowProperty>
            <div slot="footer" class="dialog-footer">
                <el-button @click="SaveOpenFeedBack">{{$t('application.ok')}}</el-button>
                <el-button @click="Visible1 = false">{{$t('application.cancel')}}</el-button>
            </div>
        </el-dialog>
        <!-- 关闭延误反馈 -->
        <el-dialog :title="延误关闭反馈" :visible.sync="Visible2" width="80%">
            <ShowProperty
                ref="ShowProperty"
                width="100%"
                itemId="1"
                v-bind:typeName="dialogtypeName"
            ></ShowProperty>
            <div slot="footer" class="dialog-footer">
                <el-button @click="SaveCloseFeedBack">{{$t('application.ok')}}</el-button>
                <el-button @click="Visible2 = false">{{$t('application.cancel')}}</el-button>
            </div>
        </el-dialog>
        <template v-slot:header>
            <el-form :inline="true" :model="forms.headForm">
                <el-form-item >
                    <DataSelect @onSelectChange='onSelectChange' v-model="forms.headForm.project"  includeAll dataUrl="/exchange/project/myproject" 
                    dataValueField="name" dataTextField="name"></DataSelect>
                </el-form-item>
                <el-form-item>
                    <el-input style="width:200px" v-model="inputValueNum" :placeholder="$t('message.pleaseInput')+$t('application.codingDesc')"></el-input>
                    <el-button type="primary" @click="search()">{{$t('application.SearchData')}}</el-button>
                </el-form-item>
                <el-form-item>
                    <AddCondition @sendMsg='searchItem' v-model="advCondition" v-bind:typeName="typeName" :inputValue="advCondition" :inputType='hiddenInput'></AddCondition>
                </el-form-item>
                <el-form-item v-if="roles1">
                    <el-button type="default" @click.native="exportData('ICM','ICMGrid')">{{$t('application.ExportExcel')}}</el-button>
                    <el-button type="primary" @click="newArchiveItem('ICM')" >{{$t('application.new')}}</el-button>
                    <el-button type="primary" @click="beforImport($refs.mainDataGrid,'/系统配置/导入模板/ICM')">{{$t('application.Import')}}</el-button>
                </el-form-item>
                <el-form-item v-if="roles2">
                    <el-button type="primary" @click="icmOpenfeedback('延误打开反馈',selectedItems)" >延误打开反馈</el-button>
                </el-form-item>
                <el-form-item v-if="roles2">
                    <el-button type="primary" @click="icmClosefeedback('延误关闭反馈',selectedItems)" >延误关闭反馈</el-button>
                </el-form-item>
            </el-form>
        </template>
        <template v-slot:main="{layout}">
           <div :style="{position:'relative',height: layout.height-startHeight+'px'}">
                <split-pane v-on:resize="onSplitResize" :min-percent='20' :default-percent='topPercent' split="horizontal">
                    <template slot="paneL">
                        <DataGrid ref="mainDataGrid"
                        v-bind="tables.main" 
                        :tableHeight="(layout.height-startHeight)*topPercent/100-topbarHeight" 
                        :isshowCustom="false"
                        @selectchange="selectChange"
                        @rowclick="onDataGridRowClick"
                        @onPropertiesSaveSuccess="onPropertiesSaveSuccess">
                         <template slot="customMoreOption" slot-scope="scope">
                             <el-button type="primary" @click.native="showReplyDialog(scope.data.row)" size="mini">{{$t('application.transmit')}}</el-button>
                         </template>
                        </DataGrid>
                    </template>
                    <template slot="paneR">
                        <el-tabs v-model="tabs.active">
                            <el-tab-pane :label="$t('application.InterfaceTransfer')" name="ICMPass">
                                <el-button type="default" @click.native="exportData('ICMPass','ICMPassGrid')">{{$t('application.ExportExcel')}}</el-button>
                                <DataGrid ref="ICMPass" showOptions="查看内容" v-bind="tables.ICMPass" :tableHeight="(layout.height-startHeight)*(100-topPercent)/100-bottomHeight"></DataGrid>
                            </el-tab-pane>
                            <el-tab-pane :label="$t('application.InterfaceOpinion')" name="ICMComments">
                                <el-button type="default" @click.native="exportData('ICMComments','ICMCommentsGrid')">{{$t('application.ExportExcel')}}</el-button>
                                <DataGrid ref="ICMComments" showOptions="查看内容" v-bind="tables.ICMComments" :tableHeight="(layout.height-startHeight)*(100-topPercent)/100-bottomHeight"></DataGrid>
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
import DataSelect from '@/components/ecm-data-select'
import ExcelUtil from '@/utils/excel.js'
import AddCondition from '@/views/record/AddCondition.vue'
import DataLayout from '@/components/ecm-data-layout'
import BatchImport from '@/components/controls/ImportICM'

export default {
    name: "InterfaceManual",
    data(){
        return{
            replyTypeName:"",
            replyVisible:false,
            // 本地存储高度名称
            topStorageName: 'ICMHeight',
            // 非split pan 控制区域高度
            startHeight: 135,
            // 顶部百分比*100
            topPercent: 65,
            // 顶部除列表高度
            topbarHeight: 35,
            // 底部除列表高度
            bottomHeight: 120,
            userCondition: '',
            tables:{
                main:{
                    gridViewName:"ICMGrid",
                    dataUrl:"/dc/getDocuments",
                    condition:" C_PROJECT_NAME = '@project' ",
                    isshowicon:false,
                    isshowOption:true,
                    isshowCustom:true,
                    isInitData:false,
                    isShowMoreOption:false,
                    isEditProperty:false
                },
                ICMPass:{
                    gridViewName:"ICMPassGrid",
                    dataUrl:"/dc/getDocuments",
                    condition:"C_PROJECT_NAME = '@project'",
                    isshowOption:true,
                    isshowCustom:true,
                    isInitData:false,
                    isshowSelection:false,
                    tableHeight:"260"
                },
                ICMComments:{
                    gridViewName:"ICMCommentsGrid",
                    dataUrl:"/dc/getDocuments",
                    condition:"C_PROJECT_NAME = '@project'",
                    isshowOption:true,
                    isshowCustom:true,
                    isInitData:false,
                    isshowSelection:false,
                    tableHeight:"260"
                }
            },
            tabs:{
                active:"ICMPass"
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
            condition1:"",
            selectedItems:'',
            Visible1:false,
            Visible2:false,
            roles1:false,
            roles2:false,
            dialogtypeName:'',
            selectiteam:[],
            selectCondition:'',
            searchCondition:''
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
        let roles=this.GetUserRoles()
        if(roles==1){
            this.tables.main.isEditProperty=true
            this.roles1=true
        }else if(roles==2){
            this.roles2=true
        }
        this.loadsuccess();
        setTimeout(() => {
            this.topPercent = this.getStorageNumber(this.topStorageName,60)
        }, 300);
    },
    methods: {
    showReplyDialog(row){
            let _self = this;
            _self.replyVisible = true;
            _self.replyDocId = row.ID;
            this.$nextTick(()=>{
                if(_self.$refs.replyProperty){
                    axios.post("/exchange/doc/getReplyInfo",_self.replyDocId)
                    .then(function(response) {
                    if(response.data.code == 1){
                        _self.includeRefDoc = response.data.includeRefDoc;
                        _self.$refs.replyProperty.typeName = response.data.typeName;
                        _self.$refs.replyProperty.myTypeName = response.data.typeName;
                        let mp=new Map();
                        for (const key in response.data.data) {
                                mp.set(key,key);
                        }
                        _self.$refs.replyProperty.setMainSubRelation(mp);
                        _self.$refs.replyProperty.setMainObject(response.data.data);
                        _self.$refs.replyProperty.loadFormInfo();
                    }else{
                        _self.replyVisible=false;
                    }
                    
                })
                .catch(function(error) {
                    _self.replyVisible=false;
                    console.log(error);
                    _self.$message({
                            showClose: true,
                            message: _self.$t('message.newFailured'),
                            duration: 2000,
                            type: "warning"
                        });
                });
                }
            },200);
            
        },


        saveReplyItem()
        {
            let _self = this;
            if(!_self.$refs.replyProperty.validFormValue()){
                return;
            }
            var m = new Map();
            var c;
            for(c in _self.$refs.replyProperty.dataList){
                let dataRows = _self.$refs.replyProperty.dataList[c].ecmFormItems;
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
                        //console.log(val);
                    }
                    m.set(dataRows[i].attrName, val);
                    }
                }
                }
            }
            m.set('TYPE_NAME',_self.$refs.replyProperty.myTypeName);
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
                formdata.append("replyDocId",_self.replyDocId);
                formdata.append("includeRefDoc",_self.includeRefDoc);
                if(_self.file!="")
                {
                    //console.log(_self.file);
                    formdata.append("uploadFile",_self.$refs.replyProperty.file.raw);
                }
            
                axios.post("/exchange/doc/newReplyDoc",formdata,{
                    'Content-Type': 'multipart/form-data'
                })
                .then(function(response) {
                    let code = response.data.code;
                    //console.log(JSON.stringify(response));
                    if(code==1){
                        _self.$message({
                                showClose: true,
                                message: _self.$t('message.newSuccess'),
                                duration: 2000,
                                type: "success"
                            });
                    }
                    else{
                        _self.$message(_self.$t('message.newFailured'));
                    }
                    _self.replyVisible=false;
                })
                .catch(function(error) {
                    _self.$message(_self.$t('message.newFailured'));
                    console.log(error);
                    _self.replyVisible=false;
                });
            });
        },


        // 上下分屏事件
        onSplitResize(topPercent){
            // 顶部百分比*100
            this.topPercent = topPercent
            this.setStorageNumber(this.topStorageName, topPercent)
            //console.log(JSON.stringify(topPercent))
        },
        loadsuccess(){
            if(this.currentUser().company!=this.ownerCompany){
                this.tables.main.condition+=" AND (C_CODE5='"+this.currentUser().companyCode1+"' OR C_CODE6='"+this.currentUser().companyCode1+"')"
                this.userCondition = " (C_CODE5='"+this.currentUser().companyCode1+"' OR C_CODE6='"+this.currentUser().companyCode1+"') and "
            }
            this.$refs.mainDataGrid.condition = this.tables.main.condition
            this.$refs.mainDataGrid.loadGridData()
        },
        //角色判断
        GetUserRoles(){
            let result = 0
            let CurrentUser=JSON.parse(sessionStorage.getItem("ecm-current-user"))
                CurrentUser.roles.forEach((item)=>{
                    if(item=="CNPE_接口人员"){
                        result = 1
                        return
                    }else if(item=="分包商接口人员"){
                        result = 2
                        return
                    }
                })
            return result
        },
        //单击行
        onDataGridRowClick:function(row){
            var condition1 = "SELECT CHILD_ID from ecm_relation where TYPE_NAME='接口信息传递单'and PARENT_ID ='"+row.ID+"'"
            var key1="ID IN ("+condition1+")"
            this.$refs.ICMPass.condition = key1
            this.$refs.ICMPass.gridViewName="ICMPassGrid"
            this.$refs.ICMPass.itemDataList=[]
            this.$refs.ICMPass.loadGridInfo()
            this.$refs.ICMPass.loadGridData()

            var condition2 = "SELECT CHILD_ID from ecm_relation where TYPE_NAME='接口信息意见单'and PARENT_ID ='"+row.ID+"'"
            var key2="ID IN ("+condition2+")"
            this.$refs.ICMComments.condition = key2
            this.$refs.ICMComments.gridViewName="ICMCommentsGrid"
            this.$refs.ICMComments.itemDataList=[]
            this.$refs.ICMComments.loadGridInfo()
            this.$refs.ICMComments.loadGridData()

        },
        //下拉菜单
        onSelectChange(val){
            let _self = this
            this.selectiteam = val
            this.search()
        },
        //Excel下载
        exportData(typeName,gridViewName){
            let dataUrl = "/exchange/doc/export"
            var fileDate = new Date()
            let fileDateStr = fileDate.getFullYear()+""+fileDate.getMonth()+""+ fileDate.getDate()
            var condition1
            if(typeName =="ICM"){
                condition1 = this.$refs.mainDataGrid.condition
            }
            if(typeName =="ICMPass"){
                condition1 = this.$refs.ICMPass.condition
            }
            if(typeName =="ICMComments"){
                condition1 = this.$refs.ICMComments.condition
            }
            let params = {
                gridName:gridViewName,
                lang:"zh-cn",
                condition:condition1,
                filename:typeName+"_"+fileDateStr+".xlsx",
                sheetname:"Result"
            }
            ExcelUtil.export(params)
        },
        //接口号模糊查询
        search(){
            this.$refs.ICMPass.itemDataList=[]
            this.$refs.ICMComments.itemDataList=[]
            let _self = this
            //_self.tables.main.condition+="and C_PROJECT_NAME in ("+this.selectiteam+")"+"and C_PROJECT_NAME='@project' AND CODING LIKE '%"+ _self.inputValueNum+"%'";
            if(_self.selectiteam  && _self.selectiteam.length>0){
                _self.$refs.mainDataGrid.condition = _self.userCondition + " TYPE_NAME='ICM' and C_PROJECT_NAME in("+_self.selectiteam+") AND (CODING LIKE '%"+ _self.inputValueNum+"%' OR C_COMMENT LIKE '%"+ _self.inputValueNum+"%')";
            }else
            {
                _self.$refs.mainDataGrid.condition = _self.userCondition + " TYPE_NAME='ICM' and C_PROJECT_NAME = '@project' AND (CODING LIKE '%"+ _self.inputValueNum+"%' OR C_COMMENT LIKE '%"+ _self.inputValueNum+"%')";
            }
            
            _self.tables.main.condition=_self.$refs.mainDataGrid.condition
            _self.$refs.mainDataGrid.currentPage=1
            console.log(_self.tables.main.condition)
            _self.$refs.mainDataGrid.loadGridInfo();
            _self.$refs.mainDataGrid.loadGridData();
        },
        //高级搜索
        searchItem(){
            let _self = this
            _self.$refs.mainDataGrid.condition=_self.tables.main.condition+" and "+_self.advCondition
            _self.advCondition=''
            _self.$refs.mainDataGrid.loadGridInfo()
            _self.$refs.mainDataGrid.loadGridData()
            _self.$refs.ICMPass.itemDataList=[]
            _self.$refs.ICMComments.itemDataList=[]
        },
        //新建按钮
        newArchiveItem(typeName) {
            let _self = this;
            _self.propertyVisible = true;
            setTimeout(()=>{
                    if(_self.$refs.ShowProperty){
                        _self.$refs.ShowProperty.myItemId = "";
                        _self.dialogName=typeName;
                        _self.$refs.ShowProperty.myTypeName =typeName;
                        _self.dialogtypeName=typeName;
                        _self.$refs.ShowProperty.showUploadFile = false;
                        _self.$refs.ShowProperty.loadFormInfo();
                    }
                },10);
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
                                for(let j=0,len=val.length;j<len;j++){
                                    temp = temp + val[j]+";";
                                }
                                temp = temp.substring(0,temp.length-1);
                                val = temp;
                            }
                            m.set(dataRows[i].attrName, val);
                        }
                    }
                }
            }
            if(_self.$refs.ShowProperty.myItemId!=''){
                m.set('ID',_self.$refs.ShowProperty.myItemId);
            }
            m.set('TYPE_NAME','ICM');
            let formdata = new FormData();
            formdata.append("metaData",JSON.stringify(m));
            if(_self.$refs.ShowProperty.myItemId==''){
                axios.post("/exchange/ICM/newICM",formdata,{
                    'Content-Type': 'multipart/form-data'
                })
                .then(function(response) {
                    let code = response.data.code;
                    if (code == 1) {
                        _self.$message({
                            showClose: true,
                            message: _self.$t('message.newSuccess'),
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
        },
        onBatchImported(){
            this.gridObj.loadGridData();
        },
        handleChange(file, fileList) {
            this.fileList = fileList;
        },
        //导入按钮
        beforImport(obj,path){
            this.gridObj=obj;
            this.batchDialogVisible=true;
            this.$nextTick(()=>{
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
        //打开延误反馈
        icmOpenfeedback(typeName, selectedItems) {
            
            let _self = this;
            let selectid=''
            let status=''
            if(selectedItems.length==1){
                selectedItems.forEach(function(item){
                    selectid=item.ID
                    status=item.C_PROCESS_STATUS
                })
                if(status=='新建'){
                    _self.$message({
                    showClose: true,
                    message: "此接口已提交反馈",
                    duration: 2000,
                    type: "warring"
                });
                }else{
                    _self.Visible1 = true;
                    setTimeout(()=>{
                        if(_self.$refs.ShowProperty){
                            _self.$refs.ShowProperty.myItemId = "";
                            _self.dialogName=typeName;
                            _self.$refs.ShowProperty.myTypeName =typeName;
                            _self.dialogtypeName=typeName;
                            _self.$refs.ShowProperty.parentDocId=selectid
                            _self.$refs.ShowProperty.loadFormInfo();
                        }
                    },10);
                }
                
            }
            else{
                _self.$message({
                    showClose: true,
                    message: "请选择一条数据",
                    duration: 2000,
                    type: "warring"
                });
            }
            
        },
        SaveOpenFeedBack(){
            this.Visible1 = false
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
                                for(let j=0,len=val.length;j<len;j++){
                                    temp = temp + val[j]+";";
                                }
                                temp = temp.substring(0,temp.length-1);
                                val = temp;
                            }
                            m.set(dataRows[i].attrName, val);
                        }
                    }
                }
                
            }
            m.set("ID",_self.$refs.ShowProperty.parentDocId)
            let formdata = new FormData();
            formdata.append("metaData",JSON.stringify(m));
            if(_self.$refs.ShowProperty.myItemId==''){
                axios.post("/exchange/ICM/OpenFeedBack",formdata,{
                    'Content-Type': 'multipart/form-data'
                })
                .then(function(response) {
                    let code = response.data.code;
                    if (code == 1) {
                        _self.$message({
                            showClose: true,
                            message: _self.$t("message.commitSuccess"),
                            duration: 2000,
                            type: "success"
                        });
                        _self.propertyVisible = false;
                        _self.$refs.mainDataGrid.loadGridData();
                    } 
                    else{
                    _self.$message({
                            showClose: true,
                            message: _self.$t('message.failured'),
                            duration: 2000,
                            type: "warning"
                        });
                        
                    }
                })
                .catch(function(error) {
                    _self.$message(_self.$t('message.failured'));
                    console.log(error);
                });
            }
        },
        //关闭延误反馈
        icmClosefeedback(typeName, selectedItems) {
            
            let _self = this;
            let selectid=''
            let status=''
            if(selectedItems.length==1){
                selectedItems.forEach(function(item){
                    selectid=item.ID
                    status=item.C_PROCESS_STATUS
                })
                
                _self.Visible2 = true;
                setTimeout(()=>{
                    if(_self.$refs.ShowProperty){
                        _self.$refs.ShowProperty.myItemId = "";
                        _self.dialogName=typeName;
                        _self.$refs.ShowProperty.myTypeName =typeName;
                        _self.dialogtypeName=typeName;
                        _self.$refs.ShowProperty.parentDocId=selectid
                        _self.$refs.ShowProperty.loadFormInfo();
                    }
                },10);
                
            }
            else{
                _self.$message({
                    showClose: true,
                    message: "请选择一条数据",
                    duration: 2000,
                    type: "warring"
                });
            }
            
        },
        SaveCloseFeedBack(){
            this.Visible2 = false
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
                                for(let j=0,len=val.length;j<len;j++){
                                    temp = temp + val[j]+";";
                                }
                                temp = temp.substring(0,temp.length-1);
                                val = temp;
                            }
                            m.set(dataRows[i].attrName, val);
                        }
                    }
                }
                
            }
            m.set("ID",_self.$refs.ShowProperty.parentDocId)
            let formdata = new FormData();
            formdata.append("metaData",JSON.stringify(m));
            if(_self.$refs.ShowProperty.myItemId==''){
                axios.post("/exchange/ICM/CloseFeedBack",formdata,{
                    'Content-Type': 'multipart/form-data'
                })
                .then(function(response) {
                    let code = response.data.code;
                    if (code == 1) {
                        _self.$message({
                            showClose: true,
                            message: _self.$t("message.commitSuccess"),
                            duration: 2000,
                            type: "success"
                        });
                        _self.propertyVisible = false;
                        _self.$refs.mainDataGrid.loadGridData();
                    } 
                    else{
                    _self.$message({
                            showClose: true,
                            message: _self.$t('message.failured'),
                            duration: 2000,
                            type: "warning"
                        });
                        
                    }
                })
                .catch(function(error) {
                    _self.$message(_self.$t('message.failured'));
                    console.log(error);
                });
            }
        },
        onPropertiesSaveSuccess(data){
            let formdata = new FormData();
            formdata.append("metaData",JSON.stringify(data));
            axios.post("/exchange/ICM/UpdataICMLogger",formdata)
                .catch(function(error) {
                    _self.$message(_self.$t('message.failured'));
                    console.log(error);
                });
        },
        selectChange(val) {
            this.selectedItems = val;
        },
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
<style scoped>
    .el-form-item{
        margin-bottom: 0px;
    }
</style>