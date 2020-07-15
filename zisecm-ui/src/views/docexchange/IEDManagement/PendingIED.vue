<template>    
        <DataLayout @onLayoutResize="onLayoutResize">
        <template v-slot:header>
            <el-row>
             <el-form :inline="true" :model="filters">
                 <el-form-item>
                 <DataSelect v-model="value" dataUrl="/exchange/project/myproject" dataValueField="name" dataTextField="name" includeAll></DataSelect>
                 </el-form-item>
                <el-form-item>  
          <el-select v-model="Subcontractors" placeholder="请选择">
              <el-option
                v-for="item in options1"
                :key="item.value"
                :label="item.label"
                :value="item.value"
                >
                </el-option>
                </el-select>
                </el-form-item>
          <el-form-item>  
          <el-input v-model="input" placeholder="内部编码或标题" style="width:200px"></el-input>
          </el-form-item>
            <el-form-item>  
                <el-button type="primary" @click="search()">查询</el-button>
            </el-form-item>
                <el-form-item>  
            <el-button type="success" @click="submit()">接收</el-button>
                </el-form-item>
                <el-form-item>
            <el-button type="primary" @click.native="exportData">Excel下载</el-button>
            </el-form-item>
        <el-form-item>  
        <RejectButton 
        :selectedItems="selectedItems" 
        :refreshDataGrid="$refs.mainDataGrid"
        :isIED="true"
        > 
        </RejectButton></el-form-item>
        </el-form>
        </el-row>
        </template>
    
             <template v-slot:main="{layout}">
        <el-row>
                <el-col :span="24">
                    <!--condition="TYPE_NAME='IED' AND C_COMPANY='@company' AND STATUS='审核中'">
                    <!-- condition="FOLDER_ID IN (select ID from ecm_folder where NAME='IED' and PARENT_ID in (select ID from ecm_folder where NAME='设计分包'))" -->
              <DataGrid ref="mainDataGrid" 
            dataUrl="/dc/getDocuments"
            isshowOption
            isshowCustom
            gridViewName="IEDGrid"
            condition="TYPE_NAME='IED'  " :tableHeight="tables.main.height"
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
import RejectButton from "@/components/RejectButton";
import ShowProperty from "@/components/ShowProperty";
import DataGrid from "@/components/DataGrid";
import ExcelUtil from '@/utils/excel.js'
import DataSelect from '@/components/ecm-data-select'
import DataLayout from '@/components/ecm-data-layout'
export default {
    name: "PendingIED",
    data(){
        return{
            tables:{
                main:{
                    gridName:"IEDGrid",
                    dataList:[],
                    height:""
                },
               itemDataList: [],
               loading: false,
               status : '已完成',
               selectedItems: [],
               selectedItemId: "",
            
             
            },
            options1:[{ 
                value:'动力院',
                label:'动力院'},
                {
                value:'国核院',
                label:'国核院'
                }],

            value:'所有项目',
            Subcontractors:'',
            input:'',
            batchDialogVisible:false,
        }
    },
    created(){
      window.addEventListener("resize",this.getHeight);
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
        window.addEventListener("resize",this.getHeight);
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
    search(){
        let _self = this
        var k1 = "TYPE_NAME='IED' AND STATUS='审核中'"
        let wheres = ["TITLE","C_IN_CODING"]
        let orS = ""
           if(_self.input.trim().length>0){
                wheres.forEach(function(item){
                    if(orS.length>0){
                        orS+=" OR "
                    }
                    orS+=item + " LIKE '%"+ _self.input+"%'"
                })
                k1+=" AND (" + orS + ")"
            }
            if(_self.value != undefined && _self.value>0){
                k1+=" AND C_PROJECT_NAME in ("+_self.value +")"
            }
            if(_self.Subcontractors !=undefined && _self.Subcontractors>0){
                k1+=" AND C_COMPANY = '"+_self.Subcontractors+"'"
            }
            console.log(k1)
      

        console.log(k1)
        _self.$refs.mainDataGrid.condition=k1
        _self.$refs.mainDataGrid.loadGridData();
    },
      exportData(){
            let dataUrl = "/exchange/doc/export"
            var fileDate = new Date()
            let fileDateStr = fileDate.getFullYear()+""+fileDate.getMonth()+""+ fileDate.getDate()
            let params = {
                gridName:"IEDGrid",
                lang:"zh-cn",
                condition:_self.$refs.mainDataGrid.condition,
                filename:"IED_Pending_"+fileDateStr+".xlsx",
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
        RejectButton:RejectButton,
        DataLayout:DataLayout,
    }
}
</script>
<style scoped>
.el-header{
    height: auto;
}
</style>