<template>    
      <DataLayout @onLayoutResize="onLayoutResize">
        <template v-slot:header>
        <!-- 待提交文函 -->
        <!-- 批量导入 -->
        <el-dialog title="批量导入IED" :visible.sync="batchDialogVisible" width="80%" >
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
            <DataSelect v-model="value" dataUrl="/exchange/project/myproject" dataValueField="name" dataTextField="name" includeAll></DataSelect>
            <el-input v-model="input" placeholder="内部编码或标题" style="width:200px"></el-input>
            <el-button type="primary" @click="search()" >查询</el-button>
            <el-button type="success" @click="submit()">提交</el-button>
            <el-button type="primary" @click="newArchiveItem('IED',selectedOneTransfer)" >新建</el-button>
            <el-button type="primary" @click="beforImport($refs.mainDataGrid,false,'')">导入</el-button>
             <el-button type="primary" @click.native="exportData">Excel下载</el-button>
            <el-button type="warning" v-on:click="onDeleleItem(selectedItems,$refs.mainDataGrid)">删除</el-button>
            </el-row>
        </template>
        
        <template v-slot:main="{layout}">
            <el-row>
                <el-col :span="24">                   
                    <DataGrid ref="mainDataGrid"  dataUrl="/dc/getDocuments" isshowOption :tableHeight="tables.main.height"
                    isshowCustom gridViewName="IEDGrid" condition="TYPE_NAME='IED' AND STATUS='新建' "
                    @cellMouseEnter="cellMouseEnter"
                    @cellMouseleave="cellMouseleave"
                    @rowclick="rowClick" 
                    @selectchange="selectChange"
                ></DataGrid>
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
                    height:""
                },
              status:'',
            },
            

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
            propertyVisible:false

        }
    },
    created(){
        window.addEventListener("resize",this.getHeight);   //打开浏览器，当窗体发生变化，自动修改高度
     
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
        this.getHeight();
        this.fresh()
    },
    methods: {
        onLayoutResize(size){
            console.log(size)
            this.tables.main.height = size - 180    
        },
         getHeight() {
            this.tables.main.tableHeight = window.innerHeight - 180+"px"  
        },
        fresh(){
          let _self = this
          console.log("123123")
        _self.$refs.mainDataGrid.loadGridData();
       },

        onBatchImported(){
            this.$refs.mainDataGrid.loadGridData();
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
    },
    search(){
         let _self = this
        let wheres = ["TITLE","C_IN_CODING"]
        let orS = ""
        var k1="TYPE_NAME='IED' AND STATUS='新建'"
          if(_self.input.trim().length>0){
                wheres.forEach(function(item){
                    if(orS.length>0){
                        orS+=" OR "
                    }
                    orS+=item + " LIKE '%"+ _self.input+"%'"
                })
                k1+=" AND (" + orS + ")"
            }
            if(_self.value != undefined ){
                k1+=" AND C_PROJECT_NAME in ("+_self.value +")"
            }
            let user = this.currentUser();
            if(user.userType==2 && user.company!=null){
                k1+=" AND C_COMPANY='"+user.company +"'"
            }
            console.log(k1)

        _self.$refs.mainDataGrid.condition=k1
        _self.$refs.mainDataGrid.loadGridData();
    },
    submit(){
      let _self = this
      this.onNextStatus(this.selectedItems,_self.$refs.mainDataGrid)
        this.fresh()
        
    },
        exportData(){
            let dataUrl = "/exchange/doc/export"
            var fileDate = new Date()
            let fileDateStr = fileDate.getFullYear()+""+fileDate.getMonth()+""+ fileDate.getDate()
            let params = {
                gridName:"IEDGrid",
                lang:"zh-cn",
                condition:_self.$refs.mainDataGrid.condition,
                filename:"IED_Pending_Submit_"+fileDateStr+".xlsx",
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