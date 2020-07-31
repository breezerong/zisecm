<template>
    <DataLayout>
        <template v-slot:header>
          <el-row>
            <el-form :inline="true" :model="filters">
              <el-form-item>
                 <DataSelect v-model="icmReportStatistc" dataUrl="/exchange/project/myproject" 
                    dataValueField="name" dataTextField="name" includeAll></DataSelect>
              </el-form-item>
              <el-form-item>
                <el-date-picker
                  v-model="startDate"
                  type="date"
                  placeholder="开始日期"
                  value-format="yyyy-MM-dd">
                </el-date-picker>
              </el-form-item>
              <el-form-item>
                <el-date-picker 
                v-model="endDate" 
                type="date" align="right" 
                :placeholder="$t('application.endDate')"
                value-format="yyyy-MM-dd"> 
                </el-date-picker>
              </el-form-item>
              <el-form-item>
                  <el-button type="primary" @click="handleReport()">{{$t('application.SearchData')}}</el-button>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="icmDataStatistic()">{{$t('application.ExportExcel')}}</el-button>
              </el-form-item>
            </el-form>
          </el-row>
        </template>
        <template v-slot:main="{layout}">
          <el-table ref="mainTable" :data="tables.mainTable.data" border stripe :height="layout.height-160"> 
                <el-table-column type="index" width="30" fixed></el-table-column>
                <el-table-column v-for="item in tables.mainTable.columns" :key="item.prop" v-bind="item"></el-table-column>
          </el-table>
        </template>
    </DataLayout>
</template>
<script type="text/javascript">
import ShowProperty from "@/components/ShowProperty";
import DataGrid from "@/components/DataGrid";
import DataSelect from '@/components/ecm-data-select';
import DataLayout from '@/components/ecm-data-layout';
import FileSaver from "file-saver";
import XLSX from "xlsx";
export default {
    name: "ICMReport",
    data(){
        return{
            tables:{
              mainTable:{
                data:[],
                columns:[
                    {prop:"projectName",label:"项目名称",fixed:true},
                    {prop:"shopenICMcount",label:"应打开接口",fixed:true},
                    {prop:"haopenICMcount",label:"已打开接口",fixed:true},
                    {prop:"odopenICMcount",label:"逾期未打开接口",fixed:true},
                    {prop:"shshutdownICMcount",label:"应关闭接口",fixed:true},
                    {prop:"hashutdownICMcount",label:"已关闭接口",fixed:true},
                    {prop:"odshutdownICMcount",label:"逾期未关闭接口",fixed:true},
                    {prop:"shreplyICMcount",label:"应回复接口",fixed:true},
                    {prop:"hareplyICMcount",label:"已回复接口",fixed:true},
                    {prop:"odreplyICMcount",label:"逾期未回复接口",fixed:true}
                ]
              }
            },
            icmReportStatistc: '',
            startDate: '',
            endDate: '',
            selectedItems: [],
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
    },
    methods: {
        handleReport(){
            let _self = this;
            _self.loading = true;

            var m = new Map();
            m.set("icmReportStatistc", _self.icmReportStatistc);
            m.set("startDate", _self.startDate);
            m.set("endDate", _self.endDate);
            
            axios
              .post("/exchange/icm/ICMReport", JSON.stringify(m))
              .then(function(response) {
                _self.tables.mainTable.data = response.data.data
                _self.loading = false;
              })
            .catch(function(error) {
                console.log(error);
            });
        },
        
        icmDataStatistic(){
          let _self = this;

          import('@/utils/Export2Excel').then(excel => {
            let tHeader = []
            let filterVal = []
            _self.tables.mainTable.columns.forEach(function(item){
              tHeader.push(item.label)
              filterVal.push(item.prop)
            })
            
            const list = _self.tables.mainTable.data
            const data = this.formatJson(filterVal, list)
            excel.export_json_to_excel({
              header: tHeader,
              data,
              filename: "ICM_Report_" +new Date().Format("yyyy-MM-dd")+ ".xlsx"
            })
          })
        },

        formatJson(filterVal, jsonData) {
          return jsonData.map(v => filterVal.map(j => v[j]))
        }
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