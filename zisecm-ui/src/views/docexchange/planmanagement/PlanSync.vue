<template>
   <DataLayout>
        <template v-slot:header>
            <el-form :inline="true" >
                 <el-form-item >
                    <DataSelect v-model="value" dataUrl="/exchange/project/myproject"
                     dataValueField="name" dataTextField="name" includeAll  @onLoadnDataSuccess="onLoadnDataSuccess"></DataSelect>
                </el-form-item>
                <el-form-item>
                <el-input style="width:200px" v-model="input" :placeholder="$t('application.Coding')+$t('application.or')+$t('application.Title')"></el-input>
                <el-button type="primary" @click="search()">{{$t('application.SearchData')}}</el-button>
                </el-form-item>
                <el-form-item>
                <el-button type="primary" @click="create()">{{$t('application.new')}}</el-button>
                </el-form-item>
                 <el-form-item><el-button type="primary" @click="syncing()">{{$t('application.sync')}}</el-button></el-form-item>
                <el-form-item>
                <el-button type="primary"  @click.native="exportData">{{$t('application.ExportExcel')}}</el-button>
                </el-form-item>
                </el-form>
 
    <el-dialog title="新建" :visible.sync="dialogCreatevisual">
    <el-form :model="P6form" :inline="true">
    <el-form-item label="项目号" :label-width="formLabelWidth" >
    <el-input v-model="P6form.ID" width='120px' style="width:200px"></el-input>
    <el-button type="primary" @click="selectfromP6()">从P6选择</el-button>
    </el-form-item>
    <el-row >
    <el-form-item label="计划代码" :label-width="formLabelWidth" style="text-align:left;">
    <el-input v-model="P6form.CODING" width='120px' style="width:200px" disabled="true"></el-input>
    </el-form-item>
    <el-form-item label="计划名称" :label-width="formLabelWidth"  >
    <el-input v-model="P6form.C_PROJECT_NAME" width='120px' style="width:200px" disabled="true"></el-input>
    </el-form-item>
    </el-row>
    <el-form-item label="项目" :label-width="formLabelWidth">
    <DataSelect v-model="P6form.C_value" dataUrl="/exchange/project/myproject" dataValueField="name" dataTextField="name"
    ></DataSelect>
    </el-form-item>
    <el-form-item label="分包商" :label-width="formLabelWidth" style="">
                <el-select
                    name="selectSubContractor"
                    v-model="P6form.C_TO"
                    placeholder="分包商"
                    style="display:block;"
                >
                <div v-for="(name,nameIndex) in contractors" :key="'T2_'+nameIndex">
                <el-option :label="name" :value="name" :key="nameIndex"></el-option>
                </div>
    </el-select>
    </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
    <el-button @click="dialogCreatevisual = false">{{$t('application.cancel')}}</el-button>
    <el-button type="primary" @click="createNewPlan()">{{$t('application.ok')}}</el-button>
    </div>
    </el-dialog>
    <el-dialog>

    </el-dialog>





    <el-dialog title="从P6选择" :visible.sync="dialogP6visual">
       <el-row>
       <DataSelect v-model="P6form.value" dataUrl="/exchange/project/myproject"
       dataValueField="name" dataTextField="name" includeAll></DataSelect>
       <el-input style="width:200px" v-model="P6input" :placeholder="$t('application.Coding')+$t('application.or')+$t('application.Title')"></el-input>
       </el-row>
       <el-table :data="P6data" ref="P6" row-key="id" border>
        <el-table-column type="index" width="50" label="序号" align='center'>
    
      <template slot-scope="scope">
      <span>{{(pages.page - 1) * pages.size + scope.$index + 1}}</span>
      </template>
      </el-table-column>
       <el-table-column v-for="item in P6columns" v-bind="item" :key="item.prop" highlight-current-row></el-table-column>
       <el-table-column width="120" fixed="left">
       <template slot-scope="scope">
        <el-button
          @click="selectP6(scope.row)"
          size="small">
          选择
        </el-button>
      </template>
       </el-table-column>
       </el-table>
    <div slot="footer" class="dialog-footer">
    <el-button @click="closeDialog() ">{{$t('application.cancel')}}</el-button>
    </div>
    </el-dialog>
        </template>
           <template v-slot:main="{layout}">
                <el-row>
                <el-col :span="24">
                    <DataGrid ref="mainDataGrid" dataUrl="/dc/getDocuments"
                    condition="TYPE_NAME='计划'"
                    gridViewName="PlanGrid"
                    isshowOption
                    v-bind="tables.main"
                    @rowclick="rowClick" 
                     @selectchange="selectChange"
                    :tableHeight="layout.height/2-115" 
                    ></DataGrid>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                <el-tabs v-model="tabs.active">
                <el-tab-pane label="同步日志" name="sync">
                <el-table  
                :data="tabledata"
                style="width: 100%"
                :height="layout.height/2-124"
                @row-click="onRowClick">
                    
        <el-table-column :label="$t('field.indexNumber')" key="#1" width="70">
        <template slot-scope="scope">
        <slot name="sequee" :data="scope">
        <span>{{(currentPage-1) * pageSize + scope.$index+1}}</span>
        </slot>
        </template>
        </el-table-column>
        <!--应用名称--->
        <el-table-column
        prop="appName"
        :label="$t('application.appname')"
        width="150">
      </el-table-column>
      <el-table-column
        prop="newCount"
        :label="$t('application.appname')"
        width="100">
      </el-table-column>
      <el-table-column
        prop="updateCount"
        :label="$t('application.newcount')"
        width="100">
      </el-table-column>
      <el-table-column
        prop="creationDate"
        :label="$t('application.SyncCreationDate')"
        width="200">
      </el-table-column>
      <el-table-column
        prop="executeDate"
        :label=="$t('application.SyncEexecuteDate')"
        width="200">
      </el-table-column>
      </el-table>
         <el-pagination
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[10, 20, 50, 100, 200]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="itemCount"
        ></el-pagination>
        </el-tab-pane>
        </el-tabs>
        </el-col>
        </el-row>
        </template>
        </DataLayout>
    
</template>
<script type="text/javascript">
import ShowProperty from "@/components/ShowProperty";
import DataGrid from "@/components/DataGrid";
import DataSelect from '@/components/ecm-data-select'
import DataLayout from '@/components/ecm-data-layout'
import ExcelUtil from '@/utils/excel.js'
export default {
    name: "PlanSync",
    data(){
        return{
   tables:{
                main:{
                isInitData:false,
                isshowoption:true,
                isshowCustom:false,
                isShowPropertyButton:true,
                isShowMoreOption:false,
                isShowChangeList:false,
                isshowicon:false
                }
                },
                IEDcontrast:false,

        P6columns:[
						{prop:"C_PROJECT_ID",label:"项目号", width:'100'},
						{prop:"C_CODING",label:"计划编码",width:'240'},
						{prop:"C_PROJECT_NAME",label:"计划名称",width:'240'},
            ],
        P6data:[{
          C_PROJECT_NAME:'第一计划',
          C_CODING:'FSK45',
          C_PROJECT_ID:'MK25'
        }],
      P6form:{
        C_PROJECT_NAME:'',
        CODING:'',
        ID:'',
        C_TO:'',
        C_value:''
      },
          value:'',
          dialogP6visual:false,
          tabledata: [{
          appName:'123',
          }
        ],
        form:[{
          id:''
        }],
            value:'',
            input:'',
            currentPage: 1,
            itemCount:0,
            pageSize: 10,
            
            dialogCreatevisual:false,
            formLabelWidth: '120px',
            contractors:[],
            Subcontractors:[{ 
                 name:'',
                }],
             tabs:{
                active:"sync"
            },
            selectedItems:[],
            ID:'',
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
        }
        this.getSubContractors()
    },
    methods: {
    handleSizeChange(val){
      this.pageSize = val;
      this.freshtable()
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      //console.log('handleCurrentChange', val);
      // this.$emit("pagechange", this.currentPage);
      this.freshtable();
    },
    freshtable(){
      let _self = this;
      var m = new Map();
      m.set("ID",this.ID)
      m.set("pagesize",this.pageSize)
      m.set("currentpage",this.currentPage-1)
      //console.log(m)
      axios
        .post("/exchange/ied/getBatch", JSON.stringify(m)).then(function(response){
          console.log(response.data.itemCount)
           _self.tabledata = response.data.data
           _self.itemCount = response.data.itemCount
        }).catch(function(error) {
          console.log(error);
        });
    },
      createNewPlan(){                   //新建方法
        let _self = this
        var m = new Map();
        var temp = this.P6form.C_value
        var c;
        m.set("C_TO",this.P6form.C_TO)
        m.set("CODING",this.P6form.CODING)
        m.set("ID",this.P6form.ID)
        m.set("NAME",this.P6form.C_PROJECT_NAME)
        m.set("C_PROJECT_NAME",temp.replace(/\'/g,""))
        m.set("TYPE_NAME","计划")
        console.log(m)
        this.dialogCreatevisual=false
        let formdata = new FormData();
        formdata.append("metaData",JSON.stringify(m));
         axios.post("/dc/newDocumentOrSubDoc",formdata,{
                'Content-Type': 'multipart/form-data'
            })
            .then(function(response) {
            let code = response.data.code;
            console.log("取到的数据"+code)
             if (code == 1) {
                _self.$message({
                    showClose: true,
                    message: _self.$t('message.operationSuccess'),
                    duration: 2000,
                    type: "success"
                });
                _self.$refs.mainDataGrid.loadGridData()
                }})
            
            },
            rowClick(row){
              console.log(row)            
              },
            syncing(){          //新建同步日志方法
            let _self = this;
            var m = [];
            var mess
            if(this.selectedItems.length==0){
              let msg = this.$t('message.pleaseSelectSync')
              _self.$message({message:msg,duration: 2000,showClose: true,type: "warning"})
              return
            }
            let tab = _self.selectedItems;
            console.log(_self.selectedItems)
            var i;
            for (i in tab) {
                m.push(tab[i]["ID"]);
            }
            let mp=new Map();
            mp.set("ids",m);
            axios.post("/exchange/exchange/createBatch",JSON.stringify(mp),{
                headers: {
                    "Content-Type": "application/json;charset=UTF-8"
                }
            }).then(function(response) {
             console.log(response)
             mess = response.data
              _self.$alert(mess,{dangerouslyUseHTMLString:true});
              _self.search()
              
            }
            )
            },
      selectP6(row){
        console.log(row.C_PROJECT_NAME)
        this.P6form.ID=row.C_PROJECT_ID
        this.P6form.CODING=row.C_CODING
        this.P6form.C_PROJECT_NAME=row.C_PROJECT_NAME
        this.dialogP6visual=false
        this.dialogCreatevisual=true
      },
      closeDialog(){
        this.dialogP6visual=false
        this.dialogCreatevisual=true
      },
      onRowClick(row){
        console.log(row)
      },
        getIndex($index) {
        //表格序号
        return (this.page.currentPage - 1) * this.page.pageSize + $index + 1
      }, 
    rowClick(row){
      console.log(row)
      let _self = this;
      var m = new Map();
      this.ID = row['ID']
      m.set("ID",row['ID']);
      m.set("pageSize",this.pageSize)
      this.freshtable()
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
              _self.Subcontractors= response.data.data;
              for(i=0;i<_self.Subcontractors.length;i++){
                  _self.contractors[i]=_self.Subcontractors[i].NAME
              }
            })
            .catch(function(error) {
              console.log(error);
            });

        },
        selectfromP6(){
        this.dialogCreatevisual=false
        this.dialogP6visual=true

        },
   search(){
        let _self = this
        var k1 = "TYPE_NAME='计划'"
        let wheres = ["C_PROJECT_NAME","CODING"]
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
            /*if(_self.value != undefined &&_self.value!='所有项目'){
                k1+=" AND C_PROJECT_NAME in ("+_self.value +")"
            }/*/
        console.log(k1)
        _self.$refs.mainDataGrid.condition=k1
        _self.$refs.mainDataGrid.loadGridData();
        _self.getSubContractors()
    },

    create(){
    let _self = this
    this.dialogCreatevisual=true
    },
         exportData(){
            let _self =this
            let dataUrl = "/exchange/doc/export"
            var fileDate = new Date()
            let fileDateStr = fileDate.getFullYear()+""+fileDate.getMonth()+""+ fileDate.getDate()
            let params = {
                gridName:"PlanGrid",
                lang:"zh-cn",
                condition:_self.$refs.mainDataGrid.condition,
                filename:"Plan_Sync_"+fileDateStr+".xlsx",
                sheetname:"Result"
            }
            ExcelUtil.export(params)
        },
      selectChange(val) {
      // console.log(JSON.stringify(val));
      this.selectedItems = val;
    },

     onLoadnDataSuccess(select,options){
            console.log(select)
            this.search()
            this.getSubContractors()
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
.el-form-item{
  margin:0px
}
</style>