<template>
    <DataLayout>
    
        <template v-slot:header>
            <el-form inline="true">
            <el-form-item>
            <DataSelect v-model="value" dataUrl="/exchange/project/myproject" 
            dataValueField="name" dataTextField="name" includeAll
            @onLoadnDataSuccess="onLoadnDataSuccess"
            ></DataSelect></el-form-item>
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
                </div>
            </el-select>
            </el-form-item>
            <el-form-item><el-button type="primary" @click="search()">{{$t('application.SearchData')}}</el-button></el-form-item>
            <el-form-item><el-button type="success" @click="submit()">{{$t('application.ok')}}</el-button></el-form-item>
            <el-form-item><el-button type="primary" @click.native="exportData">{{$t('application.ExportExcel')}}</el-button></el-form-item>
            <el-form-item><AddCondition v-bind:typeName="typeName" :inputType='hiddenInput' @change="onSearchConditionChange"></AddCondition></el-form-item>
            </el-form>
        </template>
        <template v-slot:main="{layout}">
                <el-row>
                <el-col :span="24">
                    <!--condition="creator='@currentuser' AND company='@company' AND status='已驳回'">
                    condition="FOLDER_ID IN (select ID from ecm_folder where NAME='IED' and PARENT_ID in (select ID from ecm_folder where NAME='设计分包'))" -->
            <DataGrid ref="mainDataGrid" 
            dataUrl="/dc/getDocuments"
            isshowOption
            gridViewName="反馈确认"
            condition='TYPE_NAME="ICM" AND C_PROCESS_STATUS="新建"'
            :optionWidth = "2"
            v-bind="tables.main" 
            :tableHeight="layout.height-166"
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
import ExcelUtil from '@/utils/excel.js'
import DataSelect from '@/components/ecm-data-select'
import DataLayout from '@/components/ecm-data-layout'
import AddCondition from '@/views/record/AddCondition.vue'
export default {
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
            processStatus:[{
                label : "新建",
                value : "新建",
            },
            {
                label : "已确认",
                value : "已确认",
            }],
            selectedItems: [],
            selectedItemId: "",
            value:'',
            Cstatus:'新建',
            hiddenInput:'hidden',
            typeName:"ICM",
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
      this.search()  
    },

    methods: {

          onSearchConditionChange:function(val){
            this.search(val)
        },

        onLoadnDataSuccess(v,o){
            this.search();
        },
        cellMouseEnter(row, column, cell, event){
        this.selectRow=row;
 
        },
     rowClick(row){
      this.selectRow=row;
      console.log(row)
    },
     selectChange(val) {
      // console.log(JSON.stringify(val));
      this.selectedItems = val;
    },
    submit(){
        var ids =[]
        var k = this.selectedItems.length           //获取当前Checkbox数组的长度
        let _self = this
        console.log("长度："+k)
         if(_self.selectedItems.length==0){
             let msg = this.$t('message.pleaseSelectICM')
            _self.$message({message:msg,duration: 2000,showClose: true,type: "info"})
        }


        for(var i=0;i<k;i++)
        {
            ids[i] = this.selectedItems[i].ID
        }
        axios.post("/exchange/ICM/AcceptICMFeedback",JSON.stringify(ids)).then(function(response){
            let code = response.data.code;
            console.log("取到的数据"+code)
             if (code == 1 &&_self.selectedItems.length!=0) {
                _self.$message({
                    showClose: true,
                    message: "确认成功!",
                    duration: 2000,
                    type: "success"
                });
            _self.search()
                }})
        //this.search()
    },
    search(cond){
        let _self = this
        var k1="TYPE_NAME='ICM'"
         if(_self.value != undefined &&_self.value!='所有项目'){
                k1+=" AND C_PROJECT_NAME in ("+_self.value +")"
            }
        if(_self.Cstatus != undefined && _self.Cstatus != null){
                k1+="AND C_PROCESS_STATUS = '"+_self.Cstatus+"'"
        }
        if(cond != undefined && cond.length>0){
                k1 += " and "+cond
            }
        console.log(k1)
        _self.$refs.mainDataGrid.condition=k1
        _self.$refs.mainDataGrid.loadGridData();
    },
      exportData(){
            let _self =this
            let dataUrl = "/exchange/doc/export"
            var fileDate = new Date()
            let fileDateStr = fileDate.getFullYear()+""+fileDate.getMonth()+""+ fileDate.getDate()
            let params = {
                gridName:"反馈确认",
                lang:"zh-cn",
                condition:_self.$refs.mainDataGrid.condition,
                filename:"ICM_FeedBack_"+fileDateStr+".xlsx",
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
        DataLayout:DataLayout,
        AddCondition:AddCondition,
    }
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