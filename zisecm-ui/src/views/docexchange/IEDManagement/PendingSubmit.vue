<template>    
    <el-container>
        <el-header>
            <el-row>
              <el-select v-model="value" placeholder="请选择">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
                >
              </el-option>
              </el-select>
            <el-input v-model="input" placeholder="内部编码或标题" style="width:200px"></el-input>
            <el-button type="primary" @click="fresh()">刷新数据</el-button>
            <el-button type="primary" @click="search()" >查询</el-button>
            <el-button type="success" @click="submit()">提交</el-button>
             <el-button type="primary" @click.native="exportData">Excel下载</el-button>
            <el-button type="warning" v-on:click="onDeleleItem(selectedItems,$refs.mainDataGrid)">删除</el-button>
            </el-row>
        </el-header>
        <el-main>
                <el-col :span="24">
                    <!--condition="creator='@currentuser' AND company='@company' AND status='已驳回'">
                    <!-- condition="FOLDER_ID IN (select ID from ecm_folder where NAME='IED' and PARENT_ID in (select ID from ecm_folder where NAME='设计分包'))" -->
            <DataGrid ref="mainDataGrid" 
            dataUrl="/dc/getDocuments"
            isshowOption
            isshowCustom
            gridViewName="IEDGrid"
            condition="TYPE_NAME='IED' AND STATUS='新建' "
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
import ShowProperty from "@/components/ShowProperty";
import DataGrid from "@/components/DataGrid";
import DataSelect from '@/components/ecm-data-select'
import ExcelUtil from '@/utils/excel.js'
import PrintVolumes from "@/views/record/PrintVolumes";
import PrintPage from "@/views/record/PrintPage";
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
               propertyVisible: false,
               folderDialogVisible:false,
               printObjId:"",
               archiveId: "",
               printGridName:"",
               dialogName:'',
               selectedItemId:'',
               typeName:'IED',
               
               
            },
             options:[
                 {
          value: '所有项目',
          label: '所有项目',
            },
                 
                 {
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
            value:'所有项目',
            selectedOneTransfer:'',
            input:'',
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
     rowClick(row){
      this.selectRow=row;
      console.log(row)
    },
     selectChange(val) {
      // console.log(JSON.stringify(val));
      this.selectedItems = val;
    },
    search(){
         let _self = this
        var k1 ="TYPE_NAME='IED' AND STATUS='新建'AND C_PROJECT_NAME ="+"'"+this.value+"'"+"AND (C_IN_CODING LIKE '%"+this.input+"%' OR TITLE LIKE '%"+this.input+"%')"
        if(this.value=='所有项目'&&this.input=='')//所有条件为空
        k1="TYPE_NAME='IED' AND STATUS='新建'"
        if(this.value=='所有项目'&&this.input!='')//项目为空，标题不为空
        k1="TYPE_NAME='IED' AND STATUS='新建'"+"AND (C_IN_CODING LIKE '%"+this.input+"%' OR TITLE LIKE '%"+this.input+"%')"
        if(this.value!='所有项目'&&this.input=='')//项目不为空，标题为空
        k1="TYPE_NAME='IED' AND STATUS='新建'"+"AND (C_IN_CODING LIKE '%"+this.input+"%' OR TITLE LIKE '%"+this.input+"%')"

        _self.$refs.mainDataGrid.condition=k1
        _self.$refs.mainDataGrid.loadGridData();
    },
    submit(){
      this.onNextStatus(this.selectedItems,[this.$refs.mainDataGrid])
        this.fresh()
        
    },
        exportData(){
            let dataUrl = "/exchange/doc/export"
            let params = {
                gridName:"IEDGrid",
                lang:"zh-cn",
                condition:"TYPE_NAME='IED' AND STATUS ='新建'",
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
        PrintVolumes: PrintVolumes,
        PrintPage: PrintPage,
    }
}
</script>
<style scoped>
.el-header{
    height: auto;
}
</style>