<template>    
      <DataLayout >
        <template v-slot:header>
        <!-- 批量导入 -->
        <el-dialog :title="$t('route.batchserverimport')" :visible.sync="batchDialogVisible" width="80%" >
            <BatchImport ref="BatchImport"  @onImported="onBatchImported" v-bind:deliveryId="parentId" width="100%"></BatchImport>
            <div slot="footer" class="dialog-footer">
            <el-button @click="batchDialogVisible=false" size="medium">{{$t('application.close')}}</el-button>
            </div>
        </el-dialog>
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
            <el-row>
            <DataSelect v-model="value" dataUrl="/exchange/project/myproject" dataValueField="name" dataTextField="name" includeAll
             @onLoadnDataSuccess="onLoadnDataSuccess"></DataSelect>
            <el-input v-model="input" :placeholder="$t('message.iedPublishedInputPlaceholder')" style="width:200px"></el-input>
            <el-button type="primary" @click="search()" >{{$t('application.SearchData')}}</el-button>
            <el-button type="primary" @click="newArchiveItem('IED',selectedOneTransfer)" >{{$t('application.new')}}</el-button>
            <el-button type="primary" @click="beforImport($refs.mainDataGrid,false,'')">{{$t('application.Import')}}</el-button>
            <el-button type="success" @click="submit()">{{$t('application.Submit')}}</el-button>
             <el-button type="primary" @click.native="exportData">{{$t('application.ExportExcel')}}</el-button>
            <el-button type="warning" @click="Delete()">{{$t('application.delete')}}</el-button>
            </el-row>


       <el-dialog 
        :title="$t('application.iedContrast')"
        :visible.sync="IEDcontrast"
        @close="IEDcontrast = false"
        width="80%">
        <el-table
        :data="ContrastData"
        ref="IEDCon" row-key="id" >
        <el-table-column
        fixed
        prop="C_IN_CODING"
        :label="$t('application.IN_CODING')"
        width="150"
        ></el-table-column>
        <el-table-column
        fixed
        prop="CODING"
        :label="$t('application.CODING')"
        width="150"
        ></el-table-column>
        <el-table-column
        fixed
        prop="TITLE"
        :label="$t('application.TITLE')"
        width="150"
        ></el-table-column>
        <el-table-column v-for="item in IEDcolumns" v-bind="item" :key="item"
        :label="item.label"
        :prop="item.attrName"></el-table-column>    
        </el-table>
         <div slot="footer" class="dialog-footer">
         <el-button @click="IEDcontrast= false">{{$t('application.cancel')}}</el-button>
         </div>
        </el-dialog>
        </template>
        
        <template v-slot:main="{layout}">
            <el-row>
                <el-col :span="24">                   
                    <DataGrid ref="mainDataGrid"  dataUrl="/dc/getDocuments" 
                    isshowOption v-bind="tables.main" :tableHeight="layout.height-163"
                    :optionWidth = "2.5"
                    gridViewName="IEDGrid" 
                    @cellMouseEnter="cellMouseEnter"
                    @cellMouseleave="cellMouseleave"
                    @rowclick="rowClick" 
                    @selectchange="selectChange"
                >
                <template slot="customMoreOption" slot-scope="scope">
                <el-button type="primary" @click="goContrast(scope.data.row)" size="mini">{{$t('application.iedContrast')}}</el-button>
                </template>
                
                </DataGrid>
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
import BatchImport from '@/components/controls/ImportIED';
import DataLayout from '@/components/ecm-data-layout'
export default {
    name: "IEDpublished",
    data(){
        return{
            tables:{
                main:{
                    gridName:"IEDGrid",
                    dataList:[],
                    height:"",
                    isInitData:false,
                    isshowCustom:false,
                    isShowPropertyButton:true,
                    isShowMoreOption:false,
                    isShowChangeList:false,
                    isshowicon:false
                },
              status:'',
            },
            IEDcolumns:[],
            ContrastData:[{
                C_IN_CODING:'',
                CODING:'',
                TITLE:'',
            }],
            IEDcontrast:false,
            value:'',
            selectedOneTransfer:'',
            input:'',
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
            propertyVisible:false,
            id:"",
            selectedItems:[]
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
            console.log(sessionStorage.data.data.groupname)
        }
    },
    methods: {
        goContrast(row){
            console.log(row)
            var ver_id
            let _self = this
            this.IEDcontrast=true
            this.id = row.ID
            ver_id = row.VERSION_ID
            var m = new Map();
            m.set("ID",this.id);
            m.set("Version_id",ver_id)
            m.set('lang',_self.getLang());
            let formdata = new FormData();
            formdata.append("metaData",JSON.stringify(m));
             axios.post("/exchange/ied/iedContrast",formdata,{
                'Content-Type': 'multipart/form-data'
            }).then(function(response){
                console.log(response.data)
                _self.ContrastData=response.data
                _self.getColumn()
            })
        },

        getColumn(){
             var _self = this
            axios.post("/exchange/ied/getColumn").then(function(response){
                 console.log(response.data)
                _self.IEDcolumns=response.data
            })
        },

        fresh(){
        let _self = this
        _self.$refs.mainDataGrid.loadGridData();
       },
       onLoadnDataSuccess(select,options){
            console.log(select)
            this.search()
        },
        

        onBatchImported(){
            this.gridObj.loadGridData();
        },
          handleChange(file, fileList) {
            this.fileList = fileList;
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
         getFormData() {
            let _self = this;
            let formdata = new FormData();
            var data = {};
            data["parentDocId"] = _self.parentId;//_self.selectedInnerItems[0].ID;//_self.selectedFileId;
            data["relationName"]='IED';
            data["TYPE_NAME"]='IED';
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
 onLoadDataSuccess(select,options){
            console.log(select)
            this.search()
            },


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
          _self.$refs.ShowProperty.showUploadFile = false;
          _self.typeName=typeName;
          _self.$refs.ShowProperty.parentDocId=selectedRow.ID;
          _self.$refs.ShowProperty.folderPath = '/设计分包/传递单管理/ATOS';
          // _self.$refs.ShowProperty.myFolderId = _self.selectTransferRow.id;
          _self.$refs.ShowProperty.loadFormInfo();
        }
        },10);
    },
        saveItem()
        {
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
            axios.post("/exchange/ied/newIED",formdata,{
                'Content-Type': 'multipart/form-data'
            })
            .then(function(response) {
            console.log(response)
            let code = response.data.code;
            var mess = response.data.mess;
            //console.log(JSON.stringify(response));
            if (code == 1) {
                // _self.$message(_self.$t('message.newSuccess'));
                _self.$message({
                    showClose: true,
                    message:_self.$t('message.operationSuccess') ,
                    duration: 2000,
                    type: "success"
                });
                _self.propertyVisible = false;

                // _self.loadTransferGridData();
                //_self.$refs.mainDataGrid.conditon=_self.k2
                _self.$refs.mainDataGrid.loadGridData();
                
                }
            else if(code == 0){
                _self.$message({
                    showClose: true,
                    message: mess,
                    duration: 2000,
                    type: "error"
                });
            } 
             else if(code == 3){
                _self.$message({
                    showClose: true,
                    message: mess,
                    duration: 2000,
                    type: "error"
                });
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
        else
        {
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

     selectChange(val) {
      // console.log(JSON.stringify(val));
      this.selectedItems = val;
      console.log(this.selectedItems)
    },
    search(condition){
        let _self = this
        let wheres = ["TITLE","C_IN_CODING","CODING"]
        let orS = ""
        var k1="TYPE_NAME='IED' AND STATUS='新建' "
          if(_self.input.trim().length>0){
                wheres.forEach(function(item){
                    if(orS.length>0){
                        orS+=" OR "
                    }
                    orS+=item + " LIKE '%"+ _self.input+"%'"
                })
                k1+=" AND (" + orS + ")"
            }
        
             if(_self.value != undefined &&_self.value!='所有项目'){
                k1+=" AND C_PROJECT_NAME in ("+_self.value +")"
            }
            let user = this.currentUser();
            if(user.userType==2 && user.company!=null){
                k1+=" AND C_COMPANY='"+user.company +"'"
            }
            console.log(k1);
        _self.$refs.mainDataGrid.condition=k1
        _self.$refs.mainDataGrid.loadGridData();
    },
    Delete(){
        let _self = this
    if(this.selectedItems.length==0){
    let msg = this.$t('message.pleaseSelectIED')
    this.$message({ showClose: true, message: msg, duration: 2000, type: "warning"})
    return
    }
    this.onDeleleItem(this.selectedItems,[_self.$refs.mainDataGrid])
    
    },


    submit(){
    let _self = this
    if(this.selectedItems.length==0){
    let msg = this.$t('message.pleaseSelectIED')
    this.$message({ showClose: true, message: msg, duration: 2000, type: "warning"})
    return
    }
    this.onNextStatus(this.selectedItems,_self.$refs.mainDataGrid)
  
        
    },
        exportData(){
            let _self =this
            var fileDate = new Date()
            let fileDateStr = fileDate.getFullYear()+""+fileDate.getMonth()+""+ fileDate.getDate()
            let params = {
                gridName:"IEDGrid",
                lang:"zh-cn",
                condition:_self.$refs.mainDataGrid.condition,
                filename:"IED_PendingSubmit_"+fileDateStr+".xlsx",
                sheetname:"Result"
            }
            ExcelUtil.export(params)
        },
    



    },
    props: {
        
    },
    components: {
        ShowProperty:ShowProperty,
        DataGrid:DataGrid,
        DataSelect:DataSelect,
        BatchImport:BatchImport,
        DataLayout:DataLayout
    }
}
</script>
<style scoped>
.el-header{
    height: auto;
}
</style>