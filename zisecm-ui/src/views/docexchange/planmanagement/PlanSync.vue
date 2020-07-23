<template>
   <DataLayout>
        <template v-slot:header>
            <el-form :inline="true" >
                 <el-form-item >
                    <DataSelect v-model="value" dataUrl="/exchange/project/myproject"
                     dataValueField="name" dataTextField="name" includeAll  @onLoadnDataSuccess="onLoadnDataSuccess"></DataSelect>
                </el-form-item>
                <el-form-item>
                <el-input style="width:200px" v-model="input" placeholder="编码或标题"></el-input>
                <el-button type="primary" @click="search()">查询</el-button>
                </el-form-item>
                <el-form-item>
                <el-button type="primary" @click="create()">新建计划</el-button>
                </el-form-item>
                <el-form-item>
                <el-button type="primary" @click="exportdata()">导出Excel</el-button>
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
    <el-button @click="dialogCreatevisual = false">取 消</el-button>
    <el-button type="primary" @click="createNewPlan()">确 定</el-button>
    </div>
    </el-dialog>

    <el-dialog title="从P6选择" :visible.sync="dialogP6visual">
       <el-row>
       <DataSelect v-model="P6form.value" dataUrl="/exchange/project/myproject"
       dataValueField="name" dataTextField="name" includeAll></DataSelect>
       <el-input style="width:200px" v-model="P6input" placeholder="编码或标题"></el-input>
       </el-row>
       <el-table :data="P6data" ref="P6" row-key="id" border>
        <el-table-column type="index" width="50" label="序号" align='center'>
    
      <template slot-scope="scope">
      <span>{{(pages.page - 1) * pages.size + scope.$index + 1}}</span>
      </template>
      </el-table-column>
       <el-table-column v-for="item in P6columns" v-bind="item" :key="item.prop" highlight-current-row></el-table-column>
       <el-table-column width="120">
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
    <el-button @click="closeDialog() ">取 消</el-button>
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
                    :v-bind="tables.main"
                    @rowclick="rowClick" 
                    :tableHeight="layout.height/2-115" 
                    ></DataGrid>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                <el-tabs v-model="tabs.active">
                <el-tab-pane label="同步计划" name="sync">
                <el-table  
                :data="tabledata"
                style="width: 100%"
                :height="layout.height/2-115"
                @row-click="onRowClick">
                    
        <el-table-column :label="$t('field.indexNumber')" key="#1" width="70">
        <template slot-scope="scope">
        <slot name="sequee" :data="scope">
        <span>{{(currentPage-1) * pageSize + scope.$index+1}}</span>
        </slot>
        </template>
        </el-table-column>
        <el-table-column
        prop="appName"
        label="应用名称"
        width="150">
      </el-table-column>
      <el-table-column
        prop="newCount"
        label="新增数量"
        width="100">
      </el-table-column>
      <el-table-column
        prop="updateCount"
        label="更新数量"
        width="100">
      </el-table-column>
      <el-table-column
        prop="creationDate"
        label="同步开始时间"
        width="200">
      </el-table-column>
      <el-table-column
        prop="executeDate"
        label="同步结束时间"
        width="200">
      </el-table-column>
      <el-table-column width='100'>
      <el-button size="mini" @click="check()">查看</el-button>
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
export default {
    name: "PlanSync",
    data(){
        return{
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
        pages: {
        page: 1,
        size: 10,
        total: 100
      },
      P6form:{
        C_PROJECT_NAME:'',
        CODING:'',
        ID:'',
        C_TO:'',
        C_value:''
      },
          value:'',
          dialogP6visual:false,
        tables:{
                main:{
                isInitData:false
                }
                },
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
            pageSize: 20,
            dialogCreatevisual:false,
            formLabelWidth: '120px',
            contractors:[],
           Subcontractors:[{ 
                 name:'',
                }],
             tabs:{
                active:"sync"
            },
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
                    message: "创建成功!",
                    duration: 2000,
                    type: "success"
                });
                _self.$refs.mainDataGrid.loadGridData()
                }})
            
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
      handleSizeChange(val) {
      this.pageSize = val;
      localStorage.setItem("docPageSize", val);
      //console.log('handleSizeChange', val);
      // this.$emit("pagesizechange", this.pageSize);
      this.freshtable();
      },  
                        
      handleCurrentChange(val) {
      this.currentPage = val;
      //console.log('handleCurrentChange', val);
      // this.$emit("pagechange", this.currentPage);
      this.freshtable();
    },
    rowClick(row){
      let _self = this;
      var m = new Map();
      m.set("C_PROJECTNAME",row['C_PROJECT_NAME']);
      m.set("NAME", row['NAME']);
       axios
        .post("/exchange/ied/getBatch", JSON.stringify(m)).then(function(response){
           _self.tabledata = response.data.data
           
           //_self.tabledata.push({"appName":'123123124'})
           console.log("response.data")
           console.log(response.data.data)
           console.log(_self.tabledata)
        }).catch(function(error) {
          console.log(error);
        });
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

</style>