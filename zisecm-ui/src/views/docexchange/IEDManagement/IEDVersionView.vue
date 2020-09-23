<template>
  <DataLayout>
    <template v-slot:main="{layout}">
      <div :style="{position:'relative',height: layout.height-startHeight+'px'}">
        <split-pane v-on:resize="onSplitResize" :min-percent='20' :default-percent='topPercent' split="horizontal">
          <template slot="paneL">
              <DataGrid ref="mainDg" v-bind="tables.mainDg" 
              :optionWidth = "3"
              :tableHeight="(layout.height-startHeight)*topPercent/100-topbarHeight"
              @rowclick="onDataGridRowClick"></DataGrid>
          </template>
          <template slot="paneR">
            <el-tabs v-model="tabs.active">
                <el-tab-pane :label="$t('application.relevant')" name="relationFiles">
                    <DataGrid ref="rfDg" v-bind="tables.rfDg" :tableHeight="(layout.height-startHeight)*(100-topPercent)/100-bottomHeight"></DataGrid>
                </el-tab-pane>
                <el-tab-pane :label="$t('application.designdoc')" name="designFile">
                    <DataGrid ref="dfDg"  v-bind="tables.dfDg" :tableHeight="(layout.height-startHeight)*(100-topPercent)/100-bottomHeight"></DataGrid>
                </el-tab-pane>
                <el-tab-pane :label="$t('application.transmitaldoc')" name="transmitals">
                    <DataGrid ref="tfDg"  v-bind="tables.tfDg" :tableHeight="(layout.height-startHeight)*(100-topPercent)/100-bottomHeight"></DataGrid>
                </el-tab-pane>
            </el-tabs>
          </template>
        </split-pane>
      </div>
    </template>
  </DataLayout>
</template>

<script>
import DataGrid from "@/components/DataGrid"
import DataLayout from '@/components/ecm-data-layout'
export default {
  props:{
    versionDocId:{
      type:String,default:""
    }
  },
  data(){
    return{
      docId:"",
      topStorageName: 'IEDVersionViewHeight',
      startHeight: 140,
      topPercent: 65,
      topbarHeight: 35,
      bottomHeight: 80,
      tables:{
        mainDg:{
            gridViewName:"IEDVersionGrid",
            dataUrl:"/dc/getDocuments",
            condition:"VERSION_ID=''",
            isshowOption:true,
            isShowMoreOption: false,
            isshowCustom:false,
            isInitData:false,
            isshowicon:false,
            isEditProperty:false
        },
        rfDg:{
            gridViewName:"IEDRelationGrid",
            dataUrl:"/dc/getDocuments",
            condition:"",
            isshowOption:true,
            isshowCustom:true,
            isInitData:false,
            isshowicon:false,
            isEditProperty:false,
            showOptions:'查看内容'
        },
        dfDg:{
            gridViewName:"DrawingGrid",
            dataUrl:"/dc/getDocuments",
            condition:"",
            isshowOption:true,
            isshowCustom:true,
            isInitData:true,
            isshowicon:false,
            isEditProperty:false,
            showOptions:'查看内容'
        },
        tfDg:{
            gridViewName:"TransferGrid4IED",
            dataUrl:"/dc/getDocuments",
            condition:"",
            isshowOption:true,
            isshowCustom:true,
            isInitData:false,
            isshowicon:true,
            isEditProperty:false,
            showOptions:'查看内容'
        }
      },
      tabs:{
        active:"relationFiles"
      },
    }
  },
  components:{
    DataLayout,DataGrid
  },
  methods:{
    onSplitResize(topPercent){
      // 顶部百分比*100
      if(topPercent != undefined){
        this.topPercent = topPercent
      }
      
      this.setStorageNumber(this.topStorageName, this.topPercent)      
    },
    onDataGridRowClick:function(row){
        //console.log(row)
        let cond=""
        let type = row.SUB_TYPE
        let rfDGCondition = "SELECT CHILD_ID from ecm_relation where NAME='相关文件' AND PARENT_ID  in ('"+row.ID+"')"
        this.tables.rfDg.condition=" ID IN ("+ rfDGCondition +") and TYPE_NAME<>'设计文件' AND TYPE_NAME<>'IED'"
        this.$refs.rfDg.condition=this.tables.rfDg.condition
        this.tables.rfDg.gridViewName="IEDRelationGrid"
        this.$refs.rfDg.gridViewName=this.tables.rfDg.gridViewName
        this.$refs.rfDg.itemDataList=[]
        this.$refs.rfDg.loadGridInfo()
        this.$refs.rfDg.loadGridData()
        
        if(type=='图册'){
            cond="TYPE_NAME='设计文件' and C_FROM_CODING='"+row.CODING+"' and is_current='1'"
        }
        if(type!='图册'){
            cond="type_name='设计文件' and CODING='"+row.CODING+"' and REVISION ='"+row.REVISION+"' AND IS_CURRENT='1'"
        }
        this.$refs.dfDg.condition=cond
        this.$refs.dfDg.itemDataList=[]
        this.$refs.dfDg.loadGridInfo()
        this.$refs.dfDg.loadGridData()
       //this.tables.tfDg.condition = "Type_name='文件传递单' and CODING='"+ row.C_REF_CODING+"'"
            
        let dfDGCondition ="select PARENT_ID from ecm_relation where NAME='设计文件' and CHILD_ID in (select ID from ecm_document where type_name='设计文件' and CODING='"+row.CODING+"' and REVISION ='"+row.REVISION+"')";
        this.tables.tfDg.condition = "Type_name='文件传递单' and ID IN ("+ dfDGCondition+")"

        this.$refs.tfDg.condition= this.tables.tfDg.condition
        this.$refs.tfDg.itemDataList=[]
        //this.$refs.tfDg.loadGridInfo()
        this.$refs.tfDg.loadGridData()
        this.id=row.ID
        this.tables.mainDg.condition="VERSION_ID='"+this.id+"'"
    },
    search(versionId){
      // console.log("IEDVersionView")
      // console.log(versionId)
      this.$refs.rfDg.itemDataList=[]
      this.$refs.dfDg.itemDataList=[]
      this.$refs.tfDg.itemDataList=[]

      if(versionId != undefined){
        this.docId = versionId
      }

      this.$refs.mainDg.condition = "VERSION_ID ='"+this.docId+"'"
      this.$refs.mainDg.loadGridData()
      //this.onSplitResize(this.topPercent)
    }
  },
  mounted(){
    this.search()
    setTimeout(() => {
            this.topPercent = this.getStorageNumber(this.topStorageName,60)
    }, 300);
  }
}
</script>

<style>

</style>