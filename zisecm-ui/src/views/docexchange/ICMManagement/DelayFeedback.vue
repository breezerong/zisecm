<template>
   <DataLayout>

        <template v-slot:header>
            <el-form inline="true">
            <el-form-item>    
            <DataSelect v-model="value" dataUrl="/exchange/project/myproject" dataValueField="name" dataTextField="name" includeAll
            @onLoadnDataSuccess="onLoadnDataSuccess"></DataSelect></el-form-item>
            <el-form-item>
                  <el-select
                    name="selectCProcessStatus"
                    v-model="Ctypes"
                    placeholder="反馈状态"
                    style="display:block;"
                    >
            <el-option
             v-for="item in types"
            :key="item.value"
            :label="item.label"
            :value="item.value">
            </el-option>
            </el-select>
            </el-form-item>
            <el-form-item>
                  <el-select
                    name="selectCProcessStatus"
                    v-model="Cstatus"
                    placeholder="反馈状态"
                    style="display:block;"
                >
            <el-option
             v-for="item in processStatus"
            :key="item.value"
            :label="item.label"
            :value="item.value">
            </el-option>
            </el-select>
            </el-form-item>

            <el-form-item><el-input v-model="input" :placeholder="$t('message.selectByCoding')" style="width:200px"></el-input></el-form-item>
            <el-form-item><el-button type="primary" @click="search()">{{$t('application.SearchData')}}</el-button></el-form-item>
            </el-form>
            <el-dialog
            :title="dialogName+$t('application.property')"
            :visible.sync="formVisual"
            @close="formVisual = false"
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
        </template>
    <template v-slot:main="{layout}">    
    <DataGrid ref="mainDataGrid" dataUrl="/dc/getDocuments"
                    gridViewName="ICM延误回复反馈"
                    isshowOption
                    :optionWidth = "2.8"
                    v-bind="tables.main"
                    @rowclick="rowClick" 
                     @selectchange="selectChange"
                    :tableHeight="layout.height-166" 
                    :isEditProperty="false"
                    >
                <template slot="customMoreOption" slot-scope="scope">
                <el-button v-if="CstatusType" type="primary" @click="feedback(scope.data.row)" size="mini">回复反馈</el-button>
                </template>
    </DataGrid>
    </template>
  </DataLayout>   
</template>

<script type="text/javascript">
import ShowProperty from "@/components/ShowProperty";
import DataGrid from "@/components/DataGrid";
import ExcelUtil from '@/utils/excel.js'
import DataSelect from '@/components/ecm-data-select'
import DataLayout from '@/components/ecm-data-layout'
import AddCondition from '@/views/record/AddCondition.vue'
export default {
    components: {
        ShowProperty:ShowProperty,
        DataGrid:DataGrid,
        DataSelect:DataSelect,
        DataLayout:DataLayout,
        AddCondition:AddCondition,
    },
    name: "ICMFeedback",
    data(){
        
        return{
            tables:{
                main:{
                    dataList:[],
                    height:"",
                    isshowoption:true,
                    isshowCustom:false,
                    isShowPropertyButton:true,
                    isShowMoreOption:false,
                    isShowChangeList:false,
                    isInitData:false,
                    isshowicon:false
                },
               itemDataList: [],
               loading: false,
               status : '',
            },
             processStatus:[
            {
                label : "未反馈",
                value : "未反馈",
            },
            
            {
                label : "新建",
                value : "新建",
            },
            {
                label : "已确认",
                value : "已确认",
            }],


            types:[
                {
                label : "所有类型",
                value : "所有类型",
            },

                {
                label : "接口信息传递单",
                value : "接口信息传递单",
            },
            {
                label : "接口信息意见单",
                value : "接口信息意见单",
            }],
            formVisual:false,
            selectedItems: [],
            selectedItemId: "",
            value:'',
            input:null,
            coding:'',
            CstatusType:true,
            dialogName:'',
            Cstatus:'未反馈',
            Ctypes:'所有类型',
            hiddenInput:'hidden',
            typeName:"延误回复反馈",
        }
    },

 created(){

    },
 mounted(){
   
     
    },

    methods: {
        onLoadnDataSuccess(v,o){
            this.search()
        },
        feedback(row){
        this.coding=row.ID
        this.dialogName='延误回复反馈'
        this.formVisual=true
        },
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
        var cd = this.coding
        m.set("id",cd)                                      //以上为取出ShowProperty中的所有信息，写进map里
        let formdata = new FormData();      
        formdata.append("metaData",JSON.stringify(m));
        axios.post("/exchange/ICM/DelayConfirm",formdata,{
                'Content-Type': 'multipart/form-data'
            })
            .then(function(response){
        let code = response.data.code
        if(code==1){
            _self.$message({
                    showClose: true,
                    message:_self.$t('message.operationSuccess') ,
                    duration: 2000,
                    type: "success"
                });
            _self.$refs.mainDataGrid.loadGridData();
            _self.formVisual=false    
            _self.search()
            }
            else{
                _self.$message({
                    showClose: true,
                    message:_self.$t('message.operationFaild') ,
                    duration: 2000,
                    type: "error"
                });
            }
            })
        },
        search(){
        let _self = this
        let orS = ""
        var k1="C_IS_RELEASED='1' and "
             if(_self.Cstatus=='未反馈'){
                k1+="(C_PROCESS_STATUS not in ('新建','已确认') or C_PROCESS_STATUS is null) "
            }
             if(_self.Cstatus!='未反馈'){
                k1+="C_PROCESS_STATUS ='"+_self.Cstatus+"'"
            }

            if(_self.value != null &&_self.value!='所有'){
                k1+=" AND C_PROJECT_NAME in ("+_self.value +")"
            }
            if(_self.Ctypes=='所有类型'){
                k1+="and (TYPE_NAME='接口信息传递单' or TYPE_NAME='接口信息意见单')"
            }
            if(_self.Ctypes!='所有类型'){
                k1+="and TYPE_NAME ='"+_self.Ctypes+"'"
            }
            if(_self.input!=null){
                k1+="and CODING LIKE '%"+_self.input+"%'"
            }
            if(_self.Cstatus=='新建'||_self.Cstatus=='已确认'){
                _self.CstatusType=false
            }
            if(_self.Cstatus=='未反馈'){
                _self.CstatusType=true
            }
            //k1+="and c_company='"+this.currentUser().company+"'"
        console.log(k1)
        _self.$refs.mainDataGrid.condition=k1
        _self.$refs.mainDataGrid.loadGridData();
        }
    },

}
</script>
<style scoped>
.el-header{
    height: auto;
}
.el-form-item{
    margin:0px
}
</style>