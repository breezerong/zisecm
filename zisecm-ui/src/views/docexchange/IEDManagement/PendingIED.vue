<template>    
        <DataLayout >
        <template v-slot:header>

     <el-dialog title="驳回备注" :visible.sync="showDialog" width="80%" @close="showDialog=false">
            <el-input
              type="textarea"
              :rows="5"
              placeholder="请输入内容"
              v-model="rejectComment">
            </el-input>
            <div slot="footer" class="dialog-footer">
                <el-button
                @click="rejectByCnpe()"
                >{{$t('application.ok')}}</el-button>
            </div>
        </el-dialog>




            <el-row>
             <el-form :inline="true" :model="filters">
                 <el-form-item>
                 <DataSelect v-model="value" dataUrl="/exchange/project/myproject" dataValueField="name" dataTextField="name" includeAll
                 @onLoadnDataSuccess="onLoadnDataSuccess"></DataSelect>
                 </el-form-item>
                <el-form-item>  
        <el-select
                    name="selectSubContractor"
                    v-model="Subcontractor"
                    placeholder="'分包商'"
                    style="display:block;"
                >
                 <div v-for="(name,nameIndex) in contractors" :key="'T2_'+nameIndex">
                <el-option :label="name" :value="name" :key="nameIndex"></el-option>
                </div>
        </el-select>
                </el-form-item>
          <el-form-item>  
          <el-input v-model="input" placeholder="外部编码、内部编码或标题" style="width:200px"></el-input>
          </el-form-item>
            <el-form-item>  
                <el-button type="primary" @click="search()">{{$t('application.SearchData')}}</el-button>
            </el-form-item>
                <el-form-item>  
            <el-button type="success" @click="submit()">接收</el-button>
                </el-form-item>
                <el-form-item>
            <el-button type="primary" @click.native="exportData">{{$t('application.ExportExcel')}}</el-button>
            </el-form-item>
        <el-form-item>  
        <el-button icon="el-icon-back" @click="clickShowDialog">驳回</el-button>
        </el-form-item>
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
            condition="TYPE_NAME='IED' AND STATUS='审核中' " :v-bind="tables.main":tableHeight="layout.height-180"
            @cellMouseEnter="cellMouseEnter"
            @cellMouseleave="cellMouseleave"
            @rowclick="rowClick" 
            @selectchange="selectChange"
           >
            <template slot="sequee" slot-scope="scope">
                  <el-popover trigger="hover" placement="top" width="50">
                <div slot="reference" >
            <span :style="(scope.data.row['C_ITEM_STATUS2']=='变更中')?{'background':'	#00FF00'}:''">{{scope.data.$index+1}}</span>
                </div>
                  
                         <span>{{scope.data.row.C_ITEM_STATUS2}}</span>
            </el-popover>
            </template>
           </DataGrid>
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
                    height:"",
                    isInitData:false

                },
               loading: false,
               status : '已完成',
               selectedItems: [],
               selectedItemId: "",
            },
             Subcontractors:[{ 
                 name:'',
                }],

            value:'所有项目',
            Subcontractor:'',
            input:'',
            batchDialogVisible:false,
            rejectComment:"",
            showDialog:false,
            contractors:[],
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
        this.getSubContractors()
    },
    methods: {    
        fresh(){
          let _self = this
        _self.$refs.mainDataGrid.loadGridData();
       },
        getSubContractors(){
        let _self = this   
        let pm = new Map();
        pm.set('configName', 'GetSubContractor');
        // pm.set('parentId',"'"+p+"'");
         _self
            .axios({
              headers: {
                "Content-Type": "application/json;charset=UTF-8"
              },
              method: "post",
              data: JSON.stringify(pm),
              url: "/dc/getObjectsByConfigClauseNoPage"
            })
            .then(function(response) {
                var i 
                console.log(response.data.data)
              _self.Subcontractors= response.data.data;
              for(i=0;i<_self.Subcontractors.length;i++){
                  _self.contractors[i]=_self.Subcontractors[i].NAME
              }
              console.log(_self.contractors)
            })
            .catch(function(error) {
              console.log(error);
            });

        },



        cellMouseEnter(row, column, cell, event){
        this.selectRow=row;
 
        },
        onLoadnDataSuccess(select,options){
            console.log(select)
            this.search()
            this.getSubContractors()
        },
        
     rowClick(row){
      this.selectRow=row;
      console.log(row)
    },
     selectChange(val) {
      // console.log(JSON.stringify(val));
      this.selectedItems = val;
      console.log(this.selectedItems)
    },
    submit(){
      this.onNextStatus(this.selectedItems,this.$refs.mainDataGrid)
        this.fresh()
    },
    search(){
        let _self = this
        var k1 = "TYPE_NAME='IED' AND STATUS='审核中'"
        let wheres = ["TITLE","C_IN_CODING","CODING"]
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
            if(_self.value != undefined &&_self.value!='所有项目'){
                k1+=" AND C_PROJECT_NAME in ("+_self.value +")"
            }
            if(_self.Subcontractor !='' ){
                k1+=" AND C_COMPANY = '"+_self.Subcontractor+"'"
            }
             let user = this.currentUser();

        console.log(k1)
        _self.$refs.mainDataGrid.condition=k1
        _self.fresh()
        _self.getSubContractors()
    },
      exportData(){
            let _self = this
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


         clickShowDialog(){
            let _self=this;
            _self.showDialog=true;
        },

    rejectByCnpe(){
            let _self = this;
            var m = [];
            let tab = _self.selectedItems;
            console.log(_self.selectedItems)
            var i;
            for (i in tab) {
                m.push(tab[i]["ID"]);
            }
            let mp=new Map();
            mp.set("ids",m);
            mp.set("rejectCommon",_self.rejectComment);
            axios.post("/dc/previousStatus",JSON.stringify(mp),{
                headers: {
                    "Content-Type": "application/json;charset=UTF-8"
                }
            })
            .then(function(response) {
                if(response.data.code==1){
                        _self.selectedItems=''
                        _self.$refs.mainDataGrid.loadGridData()
                        
                    _self.$message({
                        showClose: true,
                        message: _self.$t("message.rollbackSuccess"),
                        duration: 2000,
                        type: 'success'
                    });
                    
                }else{
                    
                    _self.$message({
                        showClose: true,
                        message: _self.$t("message.operationFaild"),
                        duration: 5000,
                        type: 'error'
                    });
                }
                
            })
            .catch(function(error) {
                
                _self.$message({
                    showClose: true,
                    message: _self.$t("message.operationFaild"),
                    duration: 5000,
                    type: 'error'
                });
                console.log(error);
            });
            _self.showDialog=false
        }




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