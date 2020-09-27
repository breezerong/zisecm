<template>
    <div class="app-container">
        <!-- 项目管理 -->
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
        <el-row>
            <el-col :span="24" style="padding-top: 0px; padding-bottom: 0px;">
                <el-form :inline="true" :model="filters" @submit.native.prevent>
                
                <el-form-item>
                    <el-input v-model="filters.title" 
                    :placeholder="$t('application.Coding')+$t('application.or')+$t('application.Title')" @keyup.enter.native='searchItem'></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" v-on:click="searchItem">{{$t('application.SearchData')}}</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="clickNewItem">{{$t('application.new')}}</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="warning" 
                    v-on:click="onDeleteByIds('/exchange/project/deleteProject',selectedItems,[$refs.mainDataGrid,$refs.transferDoc,
                    $refs.relevantDoc])">{{$t('application.delete')}}</el-button>
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
                v-bind:isshowOption="true" v-bind:isshowSelection ="true"
                gridViewName="ProjectGrid"
                :isshowCustom="false"
                :isEditProperty="false"
                :isShowMoreOption="false"
                :isShowChangeList="false"
                @rowclick="rowClick"
                @selectchange="selectChange"
                >
                    
                </DataGrid>
        </el-row>
         
    </div>
</template>
<script type="text/javascript">
import ShowProperty from "@/components/ShowProperty";
import DataGrid from "@/components/DataGrid";
import BatchImport from '@/components/controls/ImportDocument';
import ExcelUtil from '@/utils/excel.js'
import DataSelect from '@/components/ecm-data-select'
import MountFile from '@/components/MountFile.vue';
export default {
    name: "ProjectManagement",
    data(){
        return{
            filters: {
                
                title: ""
            },
            projects:[],
            typeName:"项目",
            dialogName:"项目",
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
            rightTableHeight: (window.innerHeight - 150),
            relation:{},
           
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
    },
    methods: {
        
       
        handleChange(file, fileList) {
            this.fileList = fileList;
        },
        clickNewItem(){
            let _self=this;
            _self.propertyVisible=true;
            _self.dialogName = typeName;
                setTimeout(()=>{
                    if(_self.$refs.ShowProperty){
                        _self.$refs.ShowProperty.myItemId = "";
                        _self.dialogName=typeName;
                        _self.$refs.ShowProperty.myTypeName =typeName;
                        _self.$refs.ShowProperty.loadFormInfo();
                    }
                },10);
        },
       
        
        searchItem(){
            let _self=this;
            let key=" TYPE_NAME='项目' ";
         
            if(_self.filters.title!=''){
                key+=" and (NAME like '%"+_self.filters.title+"%' "
                +"or CODING like '%"+_self.filters.title+"%' "
                +")";
            }
            if(key!=''){
                _self.$refs.mainDataGrid.condition=key;
            }
            _self.$refs.mainDataGrid.loadGridData();
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
            axios.post("/exchange/project/saveProject",formdata,{
                'Content-Type': 'multipart/form-data'
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
        
    },
    props: {
        
    },
    components: {
        ShowProperty:ShowProperty,
        DataGrid:DataGrid,
        DataSelect:DataSelect,
        BatchImport:BatchImport,
        MountFile:MountFile
    }
}
</script>
<style scoped>

</style>