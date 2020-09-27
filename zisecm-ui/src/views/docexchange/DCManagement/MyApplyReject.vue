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
                    :placeholder="$t('application.feedback')+' '+$t('field.status')"
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
        </template>
        <template v-slot:main="{layout}">
                <el-row>
                <el-col :span="24">
            <DataGrid ref="mainDataGrid" 
            dataUrl="/dc/getDocuments4Cnpe"
            isshowOption
            gridViewName="我的驳回申请"
            :optionWidth = "2"
            condition=""
            v-bind="tables.main" 
            :tableHeight="layout.height-166"
            :isEditProperty="false"
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
            isCNPE:false,
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
                label : this.$t('application.Pending'),
                value : "待确认",
            },
            {
                label : this.$t('application.Rejecteded'),
                value : "已驳回",
            },
            {
                label : this.$t('application.RefuseRejected'),
                value : "拒绝驳回",
            },
            ],
            selectedItems: [],
            selectedItemId: "",
            value:'',
            input:'',
            Cstatus:'待确认',
            hiddenInput:'hidden',
            typeName:"ICM",
            language:"",
        }
    },

 created(){


    },

    watch:{
    '$store.state.app.language':function(nv,ov){
        this.processStatus=[{
                label : this.$t('application.Pending'),
                value : "待确认",
            },
            {
                label : this.$t('application.Rejecteded'),
                value : "已驳回",
            },
            {
                label : this.$t('application.RefuseRejected'),
                value : "拒绝驳回",
            },
            ]
    }
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
      //this.search()  
      this.language = localStorage.getItem("localeLanguage") || "zh-cn";
    },

    methods: {

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
    search(){
        let orS = ""
        let wheres = ["CODING"]
        let _self = this
        var k1="C_EX5_STRING='"+this.currentUser().userName+"'"
        if(_self.value != null &&_self.value!='所有项目'){
                k1+=" AND C_PROJECT_NAME in ("+_self.value +")"
            }
        if(_self.Cstatus != undefined && _self.Cstatus != null){
                k1+="AND C_EX7_STRING = '"+_self.Cstatus+"'"
        }
        if(_self.input.trim().length>0){
                wheres.forEach(function(item){
                    if(orS.length>0){
                        orS+=" OR "
                    }
                    orS+=item + " LIKE '%"+ _self.input+"%'"
                })
                k1+=" AND (" + orS + ")"
            }
        _self.$refs.mainDataGrid.condition=k1
        _self.$refs.mainDataGrid.loadGridData();
    },
    },
    search() {
      let orS = "";
      let wheres = ["CODING"];
      let _self = this;
      var k1 = "C_EX5_STRING='" + this.currentUser().userName + "'";
      if (_self.value != null && _self.value != "所有项目") {
        k1 += " AND C_PROJECT_NAME in (" + _self.value + ")";
      }
      if (_self.Cstatus != undefined && _self.Cstatus != null) {
        k1 += "AND C_EX7_STRING = '" + _self.Cstatus + "'";
      }
      if (_self.input.trim().length > 0) {
        wheres.forEach(function (item) {
          if (orS.length > 0) {
            orS += " OR ";
          }
          orS += item + " LIKE '%" + _self.input + "%'";
        });
        k1 += " AND (" + orS + ")";
      }
      _self.$refs.mainDataGrid.condition = k1;
      _self.$refs.mainDataGrid.loadGridData();
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