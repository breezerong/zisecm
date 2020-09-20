<template>
    <DataLayout>
    
        <template v-slot:header>
            <el-row>
              <DataSelect v-model="value" dataUrl="/exchange/project/myproject" dataValueField="name" dataTextField="name" includeAll
              @onLoadnDataSuccess="onLoadnDataSuccess"></DataSelect>
               <el-input v-model="input" :placeholder="$t('message.iedPublishedInputPlaceholder')" style="width:200px"></el-input>
            <el-button type="primary" @click="search()">{{$t('application.SearchData')}}</el-button>
            <el-button type="success" v-if='isNotCNPE' @click="submit()">{{$t('application.Submit')}}</el-button>
            <el-button type="warning" v-if='isNotCNPE&&del' @click="Delete()">{{$t('application.delete')}}</el-button>
            <el-button type="primary" @click.native="exportData">{{$t('application.ExportExcel')}}</el-button>
            
            </el-row>
        </template>
        <template v-slot:main="{layout}">
                <el-row>
                <el-col :span="24">
            <DataGrid ref="mainDataGrid" 
            dataUrl="/dc/getDocuments"
            isshowOption
            :optionWidth = "1"
            gridViewName="IEDRejectGrid"
            v-bind="tables.main"
            :tableHeight="layout.height-166"
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
                tempRoles:[],
                userRoles:[],
                isNotCNPE:true,
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
               itemDataList: [],
               loading: false,
               status : '已完成',
               selectedItemId: "",
    
            },
            selectedItems: [],
            value:'',
            input:'',
            del:true
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
        this.getUserRole()
    },

    methods: {
        getUserRole(){    //获取文件类型，进行本地验证
        var k = 0;
        var s = 0;
        this.tempRoles=this.currentUser().roles
        for(var i = 0;i < this.tempRoles.length;i++){
          if(this.tempRoles[i] == '分包商文控人员'||this.tempRoles[i] =='CNPE_文控人员'||this.tempRoles[i] =='CNPE_计划人员'||
          this.tempRoles[i] =='CNPE_接口人员'||this.tempRoles[i] =='分包商接口人员'||this.tempRoles[i] =='分包商计划人员'){
          this.userRoles[k] = this.tempRoles[i]
          k++; 
          }
        }
        if(this.userRoles.length==1 && this.userRoles[0]=='CNPE_计划人员'){
          this.isNotCNPE=false
          }
          console.log(this.isNotCNPE)
      },
        fresh(){
          let _self = this
        _self.$refs.mainDataGrid.loadGridData();
       },
        cellMouseEnter(row, column, cell, event){
        this.selectRow=row;
        },
     rowClick(row){
      this.selectRow=row;
    },
    selectChange(val) {
        // console.log(JSON.stringify(val));
        this.selectedItems = val;
        let tab = this.selectedItems;
        let i;
        for (i in tab) {
            var Rdata =new Date(tab[i]["C_REJECT_DATE"]).getTime()+(90*24*60*60*1000)
            var nowDate = new Date().getTime();
            if(Rdata>nowDate){
                this.$message({
                        showClose: true,
                        message: "当前IED于"+tab[i]["C_REJECT_DATE"]+"驳回，需要驳回后3个月才能删除",
                        duration: 2000,
                        type: "warning"
                    });
                    this.del=false
                    return
            }
            this.del=true
        }
        this.del=true
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
 
    if(this.selectedItems.length==0){
    let msg = this.$t('message.pleaseSelectIED')
    this.$message({ showClose: true, message: msg, duration: 2000, type: "warning"})
    return
    }
        this.onNextStatus(this.selectedItems,this.$refs.mainDataGrid)
        this.fresh()
    },
    onLoadnDataSuccess(select,options){
            this.search()
        },

       onSearchConditionChange:function(val){
            console.log(val)
        },
    search(condition){
        let _self = this
        let wheres = ["TITLE","C_IN_CODING","CODING"]
        let orS = ""
        var k1="TYPE_NAME='IED' AND STATUS='已驳回' "
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
                gridName:"IEDRejectGrid",
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