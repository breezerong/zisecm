<template>    
    <el-container>
        <el-header>
            <el-row>
             <el-form :inline="true" :model="filters">
                 <el-form-item>
            <el-select v-model="value" placeholder="请选择">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
                >
              </el-option>
          </el-select>
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
            <el-button type="primary" @click="fresh()">刷新数据</el-button>
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
        </el-header>
        <el-main>
        
                <el-col :span="24">
                    <!--condition="TYPE_NAME='IED' AND C_COMPANY='@company' AND STATUS='审核中'">
                    <!-- condition="FOLDER_ID IN (select ID from ecm_folder where NAME='IED' and PARENT_ID in (select ID from ecm_folder where NAME='设计分包'))" -->
            <DataGrid ref="mainDataGrid" 
            dataUrl="/dc/getDocuments"
            isshowOption
            isshowCustom
            gridViewName="IEDGrid"
            condition="TYPE_NAME='IED' AND STATUS='审核中' "
            @cellMouseEnter="cellMouseEnter"
            @cellMouseleave="cellMouseleave"
            @rowclick="rowClick"
            @selectchange="selectChange"
           ></DataGrid>
                </el-col>
       
        </el-main>
    </el-container>
</template>
<script type="text/javascript">
import RejectButton from "@/components/RejectButton";
import ShowProperty from "@/components/ShowProperty";
import DataGrid from "@/components/DataGrid";
import ExcelUtil from '@/utils/excel.js'
import DataSelect from '@/components/ecm-data-select'
export default {
    name: "IEDpublished",
    data(){
        return{
            tables:{
                main:{
                    gridName:"IEDGrid",
                    dataList:[]
                },
               itemDataList: [],
               loading: false,
               status : '已完成',
               selectedItems: [],
               selectedItemId: "",
            
                userData: {},
                loading: false,
                userName: "",
                token:"",
                ticket:12345
            },
            options:[
                {value:'所有项目',
                 label:'所有项目',
                },{
          value: '福清5、6号机组',
          label: '福清5、6号机组',
            },
            {
          value: '海南5、6号机组',
          label: '海南5、6号机组',
            },
            {
                value:'海阳5、6号机组',
                label:'海阳5、6号机组'
            },
            {
                value:'田湾7、8号机组',
                label:'田湾7、8号机组'
            }],


            options1:[{ 
                value:'动力院',
                label:'动力院'},
                {
                value:'国核院',
                label:'国核院'
                }],
            value:'所有项目',
            Subcontractors:'',
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
    },
    methods: {
        fresh(){
          let _self = this
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
      this.onNextStatus(this.selectedItems,[this.$refs.mainDataGrid])
        this.fresh()
    },
    search(){
        let _self = this
        var k1 = "TYPE_NAME='IED' AND STATUS='审核中' AND C_PROJECT_NAME ="+"'"+this.value+"'" +"AND C_COMPANY="+"'"+this.Subcontractors+"'"+"AND (C_IN_CODING LIKE '%"+this.input+"%' OR TITLE LIKE '%"+this.input+"%')"
        if(this.value!='所有项目'&&this.Subcontractors!=''&&this.input=='')//项目不为空、分包商不为空、模糊搜索为空 AB
        k1="TYPE_NAME='IED' AND STATUS='审核中' AND C_PROJECT_NAME ="+"'"+this.value+"'"+"AND C_COMPANY="+"'"+this.Subcontractors+"'"
        if(this.input==''&&this.value=='所有项目'&&this.Subcontractors=='')//所有条件都为空
        k1="TYPE_NAME='IED' AND STATUS='审核中'"
        if(this.value=='所有项目'&&this.Subcontractors!=''&&this.input!='')//项目为空，分包商和标题不为空 BC
        k1= "TYPE_NAME='IED' AND STATUS='审核中' " +"AND C_COMPANY="+"'"+this.Subcontractors+"'"+"AND (C_IN_CODING LIKE '%"+this.input+"%' OR TITLE LIKE '%"+this.input+"%')"
        if(this.value!=='所有项目'&&this.Subcontractors==''&&this.input!='')//项目不为空，标题不为空，分包商为空 AC
        k1= "TYPE_NAME='IED' AND STATUS='审核中' "+"AND C_PROJECT_NAME ="+"'"+this.value+"'"+"AND (C_IN_CODING LIKE '%"+this.input+"%' OR TITLE LIKE '%"+this.input+"%')"
        if(this.value!=='所有项目'&&this.Subcontractors==''&&this.input=='')//项目不为空，标题和分包商为空A
        k1="TYPE_NAME='IED' AND STATUS='审核中' " +"AND C_PROJECT_NAME ="+"'"+this.value+"'"
        if(this.value=='所有项目'&&this.Subcontractors==''&&this.input!='') //项目为空，分包商为空，标题不为空C
          k1="TYPE_NAME='IED' AND STATUS='审核中' "+"AND (C_IN_CODING LIKE '%"+this.input+"%' OR TITLE LIKE '%"+this.input+"%')"
            if(this.value=='所有项目'&&this.Subcontractors!=''&&this.input=='') //项目为空，分包商为空，标题不为空C
          k1="TYPE_NAME='IED' AND STATUS='审核中' "+"AND C_COMPANY='"+this.Subcontractors+"'"
        console.log(k1)
        _self.$refs.mainDataGrid.condition=k1
        _self.$refs.mainDataGrid.loadGridData();
    },
      exportData(){
            let dataUrl = "/exchange/doc/export"
            let params = {
                gridName:"IEDGrid",
                lang:"zh-cn",
                condition:"TYPE_NAME='IED' AND STATUS ='审核中'",
                filename:"Res.xlsx",
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
    }
}
</script>
<style scoped>
.el-header{
    height: auto;
}
</style>