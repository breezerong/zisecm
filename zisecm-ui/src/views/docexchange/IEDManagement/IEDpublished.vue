<template>
    <DataLayout>
        <template v-slot:header>
        <el-dialog title="批量导入IED" :visible.sync="batchDialogVisible" width="80%" >
            <BatchImport ref="BatchImport"  @onImported="onBatchImported" v-bind:deliveryId="parentId" width="100%"></BatchImport>
            <div slot="footer" class="dialog-footer">
            <el-button @click="batchDialogVisible=false" size="medium">{{$t('application.close')}}</el-button>
            </div>
        </el-dialog>
        <el-dialog :title="$t('application.IedFeedBackExcel')" :visible.sync="importdialogVisible" width="70%">
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

            <el-dialog :title="$t('application.iedfeedback')" :visible.sync="feedbackVisual" width="300">
                <el-form :model="forms.feedForm" :rules="rule" ref="feedForm" label-width="100px">
                <el-form-item :label="$t('application.EstimDate')" prop="date" >
                <el-date-picker type="date" :placeholder="$t('application.selectDate')" v-model="forms.feedForm.date"></el-date-picker>
                </el-form-item>
                <el-form-item :label="$t('application.ProgIntro')" prop="comment">
                <el-input type="textarea" v-model="forms.feedForm.comment"></el-input>
                </el-form-item>   
                </el-form>
                <div slot="footer" class="dialog-footer">
                <el-button @click="feedbackVisual = false">{{$t('application.cancel')}}</el-button>
                <el-button type="primary" @click="submit('feedForm')">{{$t('application.ok')}}</el-button>
                </div>
            </el-dialog>

            <el-dialog title="查看版本" :visible.sync="iedVersionVisual" fullscreen @open="initDocVersion">
                <IEDVersionView ref="ivvViewer" :versionDocId="id"></IEDVersionView>
            </el-dialog>
            <el-form :inline="true" :model="forms.headForm">
                <el-form-item v-show="view==false">
                    <DataSelect v-model="forms.headForm.project" dataUrl="/exchange/project/myproject"
                     dataValueField="name" dataTextField="name" includeAll @onLoadnDataSuccess="onLoadnDataSuccess"></DataSelect>                  
                </el-form-item>
                <el-form-item>
                    <el-input style="width:200px" v-model="inputValueNum" :placeholder="$t('message.iedPublishedInputPlaceholder')"></el-input>
                    <el-button type="primary" @click="search()">{{$t('application.SearchData')}}</el-button>
                </el-form-item>
                <el-form-item>
                    <AddCondition v-bind:typeName="typeName" :inputType='hiddenInput' @change="onSearchConditionChange"></AddCondition>
                </el-form-item>
                <el-form-item v-if="view==false && changeEnable">
                    <el-button type="primary" @click="onIEDChange()">{{$t('application.change')}}</el-button>
                </el-form-item>
                <el-form-item>
                <el-button type="primary" @click="beforImport($refs.mainDataGrid,false,'')">{{$t('application.IedFeedBackExcel')}}</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="exportData()">{{$t('application.ExportExcel')}}</el-button>
                </el-form-item>
            </el-form>
        </template>
        <template v-slot:main="{layout}">
            <div :style="{position:'relative',height: layout.height-startHeight+'px'}">
            <split-pane v-on:resize="onSplitResize" :min-percent='20' :default-percent='topPercent' split="horizontal">
                <template slot="paneL">
                    <DataGrid ref="mainDataGrid" v-bind="tables.main" :tableHeight="(layout.height-startHeight)*topPercent/100-topbarHeight" 
                    :optionWidth = "3.5"
                    @rowclick="onDataGridRowClick"  @selectchange="onSelectChange">
                        <template slot="customMoreOption" slot-scope="scope" v-if="view==false">
                        <el-button type="primary" @click="IEDfeedback(scope.data.row)" size="mini">{{$t('application.feedback')}}</el-button>
                        
                        <el-button type="default" :title="$t('application.viewRevision')" @click="IEDVersion(scope.data.row)" size="mini" icon="el-icon-s-order" ></el-button>
                       
                        </template>
                        <template slot="sequee" slot-scope="scope">
                                            <span :style="(scope.data.row['STATUS']!=null
                                            &&scope.data.row['STATUS']=='变更中')?{'background':'#409EFF'}:''">{{scope.data.$index+1}}</span>
                        </template>
                        <template slot="saveButton" slot-scope="scope">
                           <el-button v-if='isNotCnpe' @click='change(scope.data.row)'>变更</el-button>
                        </template>
                    </DataGrid>
                        
                </template>
                <template slot="paneR">
                    <el-tabs v-model="tabs.active">
                        <el-tab-pane :label="$t('application.relevant')" name="relationFiles">
                            <DataGrid ref="rfDg" v-bind="tables.rfDg" :tableHeight="(layout.height-startHeight)*(100-topPercent)/100-bottomHeight"></DataGrid>
                        </el-tab-pane>
                        <el-tab-pane :label="$t('application.designdoc')" name="designFile">
                            <DataGrid ref="dfDg"  v-bind="tables.dfDg" :tableHeight="(layout.height-startHeight)*(100-topPercent)/100-bottomHeight"></DataGrid>
                        </el-tab-pane>
                        <el-tab-pane :label="$t('application.transmitaldoc')" name="transmitals">
                            <DataGrid ref="tfDg"  v-bind="tables.tfDg" :tableHeight="(layout.height-startHeight)*(100-topPercent)/100-bottomHeight"></DataGrid>
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
import DataLayout from '@/components/ecm-data-layout'
import ExcelUtil from '@/utils/excel.js'
import AddCondition from '@/views/record/AddCondition.vue'
import BatchImport from '@/components/controls/ImportIED';
import IEDVersionView from './IEDVersionView'
export default {
    name: "IEDpublished",
    props:{
        view:{type:Boolean,default:false},
        project:{type:String,default:""}
    },
    data(){
        return{
            isNotCnpe:true,
            batchDialogVisible:false , //导入对话框可见性
            // 本地存储高度名称
            topStorageName: 'PublishIEDTopHeight',
            // 非split pan 控制区域高度
            startHeight: 140,
            // 顶部百分比*100
            topPercent: 65,
            // 顶部除列表高度
            topbarHeight: 35,
            // 底部除列表高度
            bottomHeight: 80,

            tables:{
                main:{
                    gridViewName:"IEDGrid",
                    dataUrl:"/dc/getDocuments",
                    condition:'',                  
                    isshowOption:true,
                    isshowCustom:true,
                    isshowicon:false,
                    isInitData:false,
                    isShowMoreOption:false,
                    isEditProperty:false
                },
                rfDg:{
                    gridViewName:"IEDRelationGrid",
                    dataUrl:"/dc/getDocuments",
                    condition:"",
                    isshowOption:true,
                    isshowCustom:true,
                    isInitData:false,
                    isshowicon:false,
                    isEditProperty:false,
                    showOptions:'查看内容'
                },
                dfDg:{
                    gridViewName:"DrawingGrid",
                    dataUrl:"/dc/getDocuments",
                    condition:"",
                    isshowOption:true,
                    isshowCustom:true,
                    isInitData:true,
                    isshowicon:false,
                    isEditProperty:false,
                    showOptions:'查看内容'
                },
                tfDg:{
                    gridViewName:"TransferGrid4IED",
                    dataUrl:"/dc/getDocuments",
                    condition:"",
                    isshowOption:true,
                    isshowCustom:true,
                    isInitData:false,
                    isshowicon:true,
                    isEditProperty:false,
                    showOptions:'查看内容'
                },
                iedVersionDg:{
                    gridViewName:"IEDVersionGrid",
                    dataUrl:"/dc/getDocuments",
                    condition:"",
                    isshowOption:false,
                    isshowCustom:false,
                    isInitData:true,
                    isshowicon:false,
                    isEditProperty:false,
                    //showOptions:'查看内容'
                },
            },
            tabs:{
                active:"relationFiles"
            },
            forms:{
                headForm:{
                    project:"",
                },
                feedForm:{
                comment:"",
                date:"",
                user:"",
                },

            },
            rule:{
                date:[
                     { required: true, message: '请选择预计日期', trigger: 'blur' },
                ],
                comment:[
                     { required: true, message: '请输入进展说明', trigger: 'blur' },
                ]
            },
            relation:{},
            inputValueNum:'',
            hiddenInput:'hidden',
            typeName:"IED",
            selectedItems:[],
            changeEnable:false,
            editPropAble:false,
            feedbackVisual:false,
            iedVersionVisual:false,
            id:"",
            gridObj:'',
        }
    },
    mounted(){
        this.init()
        setTimeout(() => {
            this.topPercent = this.getStorageNumber(this.topStorageName,60)
        }, 300);
    },
    methods: {
        getUserRole(){
            let user = this.currentUser();
            if(user.userType != 2 ){
                this.isNotCnpe=false
            }
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
        change(){
            let _self=this;
            let formData= _self.$refs.mainDataGrid.$refs.ShowProperty.getFormData();
            let mp = formData.get('metaData')
            let m= JSON.parse(formData.get('metaData'))
            let status
            let itemStatus
            for(let i = 0 ;i < m.length;i++){
                if(m[i][0]=='STATUS'){
                status = m[i][1]}
                if(m[i][0]=='C_ITEM_STATUS2'){
                    itemStatus=m[i][1]
                }
            }              
            if(itemStatus=='Y'){
            let msg = _self.$t('message.publishedChangeForY')
            _self.$message({ showClose: true, message: msg, duration: 2000, type: "warning"})
            return
            }
            if(status=='变更中'){
            let msg = _self.$t('message.publishedChangeForChanging')
            _self.$message({ showClose: true, message: msg, duration: 2000, type: "warning"})
            return
            }
            let alert = _self.$t('message.publishedAlert')
            let msg1 = _self.$t('message.publishedChangeConfirm')
            _self.$confirm(msg1,alert, {confirmButtonText: this.$t('application.ok'),cancelButtonText: this.$t('application.cancel'),type: 'warning'}).then(() => {
                 axios.post("/exchange/ied/changeIEDSingle",mp).then(function(response){
                     let code = response.data.code
                     if(code==3){
                     let msg = _self.$t('message.publishedFailedForCoding')
                     _self.$message({showClose: true, message: msg, duration: 2000, type: "error"})
                     }
                     if(code==1){
                      let msg = _self.$t('message.publishedChangeSuccessed')
                     _self.$message({showClose: true, message: msg, duration: 2000, type: "success"})
                     _self.$refs.mainDataGrid.propertyVisible = false
                     _self.$refs.mainDataGrid.loadGridData()}
                  }).catch(function(error){
                     console.log(error)
                 })
                }).catch(() => {
                
             })
        },
        async init(){
            let _self =  this
            let url="/admin/uirelation/get"
            var m = new Map();
            m.set("typeName", "IED");
            await axios.post(url,JSON.stringify(m)).then(function(response) {
                let relationItem = response.data.data
                _self.relation = relationItem
            }).catch(function(error){
                console.log(error);
            })

            let user = this.currentUser();
            let role = user.company+"_计划人员"
            if(user.company!="CNPE"){               
                user.roles.forEach(function(item){
                    if(item=='分包商计划人员'){
                        _self.changeEnable = true
                    }
                })
            }else{
                user.roles.forEach(function(item){
                    if(item==role){
                        _self.tables.main.isEditProperty=true
                        _self.tables.rfDg.isEditProperty=true
                    }
                })
            }
            
            // console.log(role)
        },
        // 上下分屏事件
        initDocVersion(){
            this.$nextTick(()=>{
                console.log(this.id)
                this.$refs.ivvViewer.seach(this.id)
                console.log(this.$refs.ivvViewer)
            })
        },
        onSplitResize(topPercent){
            // 顶部百分比*100
            this.topPercent = topPercent
            this.setStorageNumber(this.topStorageName, topPercent)
            //console.log(JSON.stringify(topPercent))
        },
        onIEDChange(){
            let _self =  this
            if(this.selectedItems.length<1){
                let msg = this.$t('message.publishedNonSelected')
                this.$message({ showClose: true, message: msg, duration: 2000, type: "warning"})
                return
            }
            let include = false
            let ids = []
            let hasChanging = false
            this.selectedItems.forEach(function(item){
                if(item.C_ITEM_STATUS2=='Y'){
                    include = true
                }
                if(item.STATUS=='变更中'){
                    hasChanging=true
                }
                ids.push(item.ID)
            })
            if(include){
                let msg = this.$t('message.publishedChangeForY')
                this.$message({ showClose: true, message: msg, duration: 2000, type: "warning"})
                return
            }
            if(hasChanging){
                let msg = this.$t('message.publishedChangeForChanging')
                this.$message({ showClose: true, message: msg, duration: 2000, type: "warning"})
                return
            }
            let alert = this.$t('message.publishedAlert')
            let msg1 = this.$t('message.publishedChangeConfirm')
            this.$confirm(msg1,alert, {confirmButtonText: this.$t('application.ok'),cancelButtonText: this.$t('application.cancel'),type: 'warning'}).then(() => {
                axios.post("/exchange/ied/changeIED",ids).then(function(response){
                     let msg = _self.$t('message.publishedChangeSuccessed')
                    _self.$message({showClose: true, message: msg, duration: 2000, type: "success"})
                    _self.search()
                }).catch(function(error){
                    console.log(error)
                })
            }).catch(() => {
                
            })
        },
        onSelectChange(val) {
            this.selectedItems = val;
        },
        onLoadnDataSuccess(select,options){
            // console.log(this.view)
            // console.log(this.project)
            if(this.view==true && this.project.length>0){
                this.forms.headForm.project = this.project
            }
            this.getUserRole()
            this.search("")
        },
        onSearchConditionChange:function(val){
            this.search(val)
        },
        onDataGridRowClick:function(row){
            console.log(row)
             var cond=""
            var type = row.SUB_TYPE
            let rfDGCondition = "SELECT CHILD_ID from ecm_relation where PARENT_ID  in (SELECT ID from ecm_document where TYPE_NAME ='IED' and CODING = '"+row.CODING+"')"
            this.tables.rfDg.condition=" ID IN ("+ rfDGCondition +")"
            this.$refs.rfDg.condition=this.tables.rfDg.condition
            this.tables.rfDg.gridViewName="IEDRelationGrid"
            this.$refs.rfDg.gridViewName=this.tables.rfDg.gridViewName
            this.$refs.rfDg.itemDataList=[]
            this.$refs.rfDg.loadGridInfo()
            this.$refs.rfDg.loadGridData()
            
            if(type=='图册'){
                cond="TYPE_NAME='设计文件' and C_FROM_CODING='"+row.CODING+"' and is_current='1'"
            }
            if(type!='图册'){
                cond="type_name='设计文件' and CODING='"+row.CODING+"' and REVISION ='"+row.REVISION+"' AND IS_CURRENT='1'"
            }
            this.$refs.dfDg.condition=cond
            this.$refs.dfDg.itemDataList=[]
            this.$refs.dfDg.loadGridInfo()
            this.$refs.dfDg.loadGridData()
            console.log(this.$refs.dfDg.condition)
            //this.tables.tfDg.condition = "Type_name='文件传递单' and CODING='"+ row.C_REF_CODING+"'"
            
            let dfDGCondition ="select PARENT_ID from ecm_relation where NAME='设计文件' and CHILD_ID in (select ID from ecm_document where type_name='设计文件' and CODING='"+row.CODING+"' and REVISION ='"+row.REVISION+"')";
            this.tables.tfDg.condition = "Type_name='文件传递单' and ID IN ("+ dfDGCondition+")"
            this.$refs.tfDg.condition= this.tables.tfDg.condition
            this.$refs.tfDg.itemDataList=[]
            //this.$refs.tfDg.loadGridInfo()
            this.$refs.tfDg.loadGridData()
            this.id=row.ID

            this.tables.iedVersionDg.condition="VERSION_ID='"+this.id+"'"
        },
        
        search(condition){
            this.$refs.rfDg.itemDataList=[]
            this.$refs.dfDg.itemDataList=[]
            this.$refs.tfDg.itemDataList=[]
            
            let _self = this
            let wheres = ["TITLE","C_WBS_CODING","CODING","C_IN_CODING"]
            let orS = ""
            var k1=" TYPE_NAME='IED' and IS_CURRENT=1 and C_IS_RELEASED=1 AND (STATUS='已生效' OR STATUS='变更中')"// AND FOLDER_ID  in (select id from ecm_folder where folder_path='/设计分包/IED')"
            if(_self.inputValueNum.trim().length>0){
                wheres.forEach(function(item){
                    if(orS.length>0){
                        orS+=" OR "
                    }
                    orS+=item + " LIKE '%"+ _self.inputValueNum+"%'"
                })
                k1+=" AND (" + orS + ")"
            }

            if(this.view==true && this.project.length>0){
                this.forms.headForm.project = this.project
            }
            if(_self.forms.headForm.project != undefined){
                k1+=" AND C_PROJECT_NAME in ("+_self.forms.headForm.project +")"
            }
            console.log(this.forms.headForm.project)
            let user = this.currentUser();
            if(user.userType==2 && user.company!=null){
                k1+=" AND C_COMPANY='"+user.company +"'"
            }
            if(condition != undefined && condition.length>0){
                k1 += " and "+condition 
            }
            _self.$refs.mainDataGrid.condition=k1
            _self.tables.main.condition=k1;
            _self.$refs.mainDataGrid.loadGridData();
        },
        exportData(){
            //let dataUrl = "/exchange/doc/export"
            let info = this.$refs.mainDataGrid.getGridViewInfo()
            let fileDate = new Date()
            let fileDateStr = fileDate.getFullYear()+""+fileDate.getMonth()+""+ fileDate.getDate()
            let params = {
                gridName:info.gridviewName,
                isCustom:info.isCustom,
                lang:"zh-cn",
                condition: this.tables.main.condition,
                filename:"IED_Published_"+fileDateStr+".xlsx",
                sheetname:"Result"
            }
            ExcelUtil.export(params)
        },
        IEDfeedback(row){
            this.feedbackVisual=true
            this.id = row.ID
            console.log(this.id)
        },
        IEDVersion(row){
            this.iedVersionVisual=true
            //console.log(row.VERSION_ID)
            this.id = row.VERSION_ID
            let viewer = this.$refs.ivvViewer
            if(viewer!=undefined){
                viewer.docId = row.VERSION_ID
                viewer.search(row.VERSION_ID)
            }else{
                setTimeout(() => {
                    viewer = this.$refs.ivvViewer
                    if(viewer!=undefined){
                        viewer.docId = row.VERSION_ID
                        viewer.search(row.VERSION_ID)
                    }
                }, 300);
            }
           
        },
        submit(feedForm){           //时间在后台方法中获取
             this.$refs[feedForm].validate((valid) => {
          if (valid) {
            let _self = this
            var user = this.currentUser()
            
            var m = new Map();
            m.set("username",user.userName)
            m.set("comment",this.forms.feedForm.comment)
            m.set("date",this.forms.feedForm.date)
            m.set("id",this.id)                     //id,后台更新数据用
            let formdata = new FormData();
            formdata.append("metaData",JSON.stringify(m));
            this.feedbackVisual=false
             axios.post("/exchange/ied/iedFeedback",formdata,{
                'Content-Type': 'multipart/form-data'
            }).then(function(response) {
            let code = response.data.code;
            if (code == 1) {
                _self.$message({
                    showClose: true,
                    message: "反馈成功!",
                    duration: 2000,
                    type: "success"
                });
                _self.feedbackVisual=false;
                }
            else{
                _self.$message({
                    showClose: true,
                    message: "反馈失败!",
                    duration: 2000,
                    type: "error"
                });
            }
                })
                }})
        } 
    },
    components: {
        ShowProperty:ShowProperty,
        DataGrid:DataGrid,
        DataSelect:DataSelect,
        AddCondition:AddCondition,
        DataLayout:DataLayout,
        BatchImport:BatchImport,
        IEDVersionView:IEDVersionView
    }
}
</script>
