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
                  type="date"
                  align="right"
                  placeholder="结束日期"
                  value-format="yyyy-MM-dd">
                </el-date-picker>
              </el-form-item>
              <el-form-item>
                  <el-button type="primary" @click="handleReport()">查询</el-button>
              </el-form-item>
            </el-form>
          </el-row>
        </template>
        <template v-slot:main="{layout}">
          <el-table :data="reportData" style="width: 100%;" border :height="layout.height"> 
                <el-table-column type="index" width="50"></el-table-column>
                <el-table-column prop="projectName" label="项目名称"></el-table-column>
                <el-table-column prop="shopenICMcount" label="应打开接口"></el-table-column>
                <el-table-column prop="haopenICMcount" label="已打开接口"></el-table-column>
                <el-table-column prop="odopenICMcount" label="逾期未打开接口"></el-table-column>
                <el-table-column prop="shshutdownICMcount" label="应关闭接口"></el-table-column>
                <el-table-column prop="hashutdownICMcount" label="已关闭接口"></el-table-column>
                <el-table-column prop="odshutdownICMcount" label="逾期未关闭接口"></el-table-column>
                <el-table-column prop="shreplyICMcount" label="应回复接口"></el-table-column>
                <el-table-column prop="hareplyICMcount" label="已回复接口"></el-table-column>
                <el-table-column prop="odreplyICMcount" label="逾期未回复接口"></el-table-column>
          </el-table>
        </template>
    </DataLayout>
</template>
<script type="text/javascript">
import ShowProperty from "@/components/ShowProperty";
import DataGrid from "@/components/DataGrid";
import DataSelect from '@/components/ecm-data-select';
import DataLayout from '@/components/ecm-data-layout';
import ExcelUtil from '@/utils/excel.js';
export default {
    name: "ICMReport",
    data(){
        return{
            main:{
                datalist: [],
                height:"",
            },

            reportData:[],
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
        percentFormatter (row, column) {
            let p = row.completedPercent;
            if(p){
                return Math.round(p*10000)/100+'%';
            }
            return ''
        },

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
                _self.reportData = response.data.data;
                _self.loading = false;
              })
            .catch(function(error) {
                console.log(error);
            });
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