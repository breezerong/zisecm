<template>
    <DataLayout>
        <template v-slot:header>
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
                    <el-button type="primary" @click.native="exportData">{{$t('application.ExportExcel')}}</el-button>
                </el-form-item>
            </el-form>
        </template>
        <template v-slot:main="{layout}">
            <el-row>
                <el-col :span="24">
                    <DataGrid ref="mainDataGrid" v-bind="tables.main" :tableHeight="layout.height/2-100" 
                    @rowclick="onDataGridRowClick"  @selectchange="onSelectChange">
                    <template slot="customMoreOption" slot-scope="scope" v-if="view==false">
                    <el-button type="primary" @click="IEDfeedback(scope.data.row)" size="mini">{{$t('application.feedback')}}</el-button>
                    </template>
                    
                    </DataGrid>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-tabs v-model="tabs.active">
                        <el-tab-pane :label="$t('application.relevant')" name="relationFiles">
                            <DataGrid ref="rfDg" v-bind="tables.rfDg" :tableHeight="layout.height/2-140"></DataGrid>
                        </el-tab-pane>
                        <el-tab-pane :label="$t('application.designdoc')" name="designFile">
                            <DataGrid ref="dfDg"  v-bind="tables.dfDg" :tableHeight="layout.height/2-140"></DataGrid>
                        </el-tab-pane>
                        <el-tab-pane :label="$t('application.transmitaldoc')" name="transmitals">
                            <DataGrid ref="tfDg"  v-bind="tables.tfDg" :tableHeight="layout.height/2-140"></DataGrid>
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
import DataLayout from '@/components/ecm-data-layout'
import ExcelUtil from '@/utils/excel.js'
import AddCondition from '@/views/record/AddCondition.vue'
export default {
    name: "IEDpublished",
    props:{
        view:{type:Boolean,default:false},
        project:{type:String,default:""}
    },
    data(){
        return{
            tables:{
                main:{
                    gridViewName:"IEDGrid",
                    dataUrl:"/dc/getDocuments",                    
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
                    isInitData:false,
                    isshowicon:false,
                    isEditProperty:false,
                    showOptions:'查看内容'
                },
                tfDg:{
                    gridViewName:"TransferGrid",
                    dataUrl:"/dc/getDocuments",
                    condition:"",
                    isshowOption:true,
                    isshowCustom:true,
                    isInitData:false,
                    isshowicon:true,
                    isEditProperty:false,
                    showOptions:'查看内容'
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
            id:"",
        }
    },
    mounted(){
        this.init()
    },
    methods: {
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
            console.log(role)
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
            console.log(this.view)
            console.log(this.project)
            if(this.view==true && this.project.length>0){
                this.forms.headForm.project = this.project
            }
            this.search()
        },
        onSearchConditionChange:function(val){
            this.search(val)
        },
        onDataGridRowClick:function(row){
            let rfDGCondition = "SELECT CHILD_ID from ecm_relation where PARENT_ID  in (SELECT ID from ecm_document where TYPE_NAME ='设计文件' and CODING = '"+row.CODING+"')"
            this.tables.rfDg.condition=" ID IN ("+ rfDGCondition +")"
            this.$refs.rfDg.condition=this.tables.rfDg.condition
            this.tables.rfDg.gridViewName="IEDRelationGrid"
            this.$refs.rfDg.gridViewName=this.tables.rfDg.gridViewName
            this.$refs.rfDg.itemDataList=[]
            this.$refs.rfDg.loadGridInfo()
            this.$refs.rfDg.loadGridData()
            
            this.tables.dfDg.condition="CODING = '"+row.CODING+"'"
            this.$refs.dfDg.condition=this.tables.dfDg.condition
            this.$refs.dfDg.itemDataList=[]
            this.$refs.dfDg.loadGridInfo()
            this.$refs.dfDg.loadGridData()

            let dfDGCondition ="select C_REF_CODING from ecm_document where TYPE_NAME='设计文件' and "+ this.tables.dfDg.condition;
            this.tables.tfDg.condition = "CODING IN ("+ dfDGCondition+")"
            this.$refs.tfDg.condition= this.tables.tfDg.condition
            this.$refs.tfDg.itemDataList=[]
            this.$refs.tfDg.loadGridInfo()
            this.$refs.tfDg.loadGridData()
        },
        exportData(){
            let dataUrl = "/exchange/doc/export"
            var fileDate = new Date()
            let fileDateStr = fileDate.getFullYear()+""+fileDate.getMonth()+""+ fileDate.getDate()
            let params = {
                gridName:this.tables.main.gridViewName,
                lang:"zh-cn",
                condition: this.$refs.mainDataGrid.condition,
                filename:"IED_Published_"+fileDateStr+".xlsx",
                sheetname:"Result"
            }
            ExcelUtil.export(params)
        },
        search(condition){
            this.$refs.rfDg.itemDataList=[]
            this.$refs.dfDg.itemDataList=[]
            this.$refs.tfDg.itemDataList=[]
            
            let _self = this
            let wheres = ["TITLE","C_WBS_CODING","CODING","C_IN_CODING"]
            let orS = ""
            var k1=" TYPE_NAME='IED' and IS_CURRENT=1 and C_IS_RELEASED=1 AND STATUS='已生效'"// AND FOLDER_ID  in (select id from ecm_folder where folder_path='/设计分包/IED')"
            if(_self.inputValueNum.trim().length>0){
                wheres.forEach(function(item){
                    if(orS.length>0){
                        orS+=" OR "
                    }
                    orS+=item + " LIKE '%"+ _self.inputValueNum+"%'"
                })
                k1+=" AND (" + orS + ")"
            }
            if(_self.forms.headForm.project != undefined){
                k1+=" AND C_PROJECT_NAME in ("+_self.forms.headForm.project +")"
            }

            let user = this.currentUser();
            if(user.userType==2 && user.company!=null){
                k1+=" AND C_COMPANY='"+user.company +"'"
            }
            if(condition != undefined && condition.length>0){
                k1 += " and "+condition 
            }
            _self.$refs.mainDataGrid.condition=k1
            _self.$refs.mainDataGrid.loadGridData();

            _self.$refs.mainDataGrid.condition=k1
            _self.$refs.mainDataGrid.loadGridData();
        },
        IEDfeedback(row){
        this.feedbackVisual=true
        this.id = row.ID
        console.log(this.id)
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
        DataLayout:DataLayout
    }
}
</script>
