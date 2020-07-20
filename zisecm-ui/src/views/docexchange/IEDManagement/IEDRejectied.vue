<template>
    <DataLayout>
    
        <template v-slot:header>
            <el-row>
              <DataSelect v-model="value" dataUrl="/exchange/project/myproject" dataValueField="name" dataTextField="name" includeAll></DataSelect>
               <el-input v-model="input" placeholder="内部编码或标题" style="width:200px"></el-input>
            <el-button type="primary" @click="search()">查询</el-button>
            <el-button type="success" @click="submit()">提交</el-button>
            <el-button type="warning" v-on:click="onDeleleItem(selectedItems,[$refs.mainDataGrid])">删除</el-button>
            <el-button type="primary" @click.native="exportData">Excel下载</el-button>
            
            </el-row>
        </template>
        <template v-slot:main="{layout}">
                <el-row>
                <el-col :span="24">
                    <!--condition="creator='@currentuser' AND company='@company' AND status='已驳回'">
                    <!-- condition="FOLDER_ID IN (select ID from ecm_folder where NAME='IED' and PARENT_ID in (select ID from ecm_folder where NAME='设计分包'))" -->
            <DataGrid ref="mainDataGrid" 
            dataUrl="/dc/getDocuments"
            isshowOption
            isshowCustom
            gridViewName="IEDGrid"
            condition="TYPE_NAME='IED' AND STATUS='已驳回'  " v-bind="tables.main":tableHeight="layout.height-180"
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
import ExcelUtil from '@/utils/excel.js'
import DataSelect from '@/components/ecm-data-select'
import DataLayout from '@/components/ecm-data-layout'
export default {
    name: "IEDRejected",
    data(){
        return{
            tables:{
                main:{
                    gridName:"IEDGrid",
                    dataList:[],
                    height:"",
                },
               itemDataList: [],
               loading: false,
               status : '已完成',
               selectedItems: [],
               selectedItemId: "",
    
            },
            value:'',
            input:''
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
        

            this.fresh()
    },

    methods: {
        fresh(){
          let _self = this
          console.log("123123")
        _self.$refs.mainDataGrid.loadGridData();
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
      this.onNextStatus(this.selectedItems,this.$refs.mainDataGrid)
        this.fresh()
    },
       onSearchConditionChange:function(val){
            console.log(val)
        },
    search(){
        let _self = this
        let wheres = ["TITLE","C_IN_CODING"]
        let orS = ""
        var k1="TYPE_NAME='IED' AND STATUS='已驳回'"
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
                gridName:"IEDGrid",
                lang:"zh-cn",
                condition:_self.$refs.mainDataGrid.condition,
                filename:"IED_Rejected_"+fileDateStr+".xlsx",
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
        DataLayout:DataLayout
    }
}
</script>
<style scoped>
.el-header{
    height: auto;
}
</style>