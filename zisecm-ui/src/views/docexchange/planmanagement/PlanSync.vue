<template>
   <DataLayout>
        <template v-slot:header>
            <el-form :inline="true" >
                 <el-form-item >
                    <DataSelect v-model="value" dataUrl="/exchange/project/myproject"
                     dataValueField="name" dataTextField="name" includeAll></DataSelect>
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
        </template>
           <template v-slot:main="{layout}">
                <el-row>
                <el-col :span="24">
                    <DataGrid ref="mainDataGrid" dataUrl="/dc/getDocuments"
                    condition="TYPE_NAME='计划'"
                    gridViewName="PlanGrid"
                    isshowOption
                    :tableHeight="layout.height/2-115" 
                    ></DataGrid>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-tabs v-model="tabs.active">
                     <el-tab-pane label="同步计划" name="sync">
                <el-table  
                :data="tableData"
                style="width: 100%"
                :height="layout.height/2-115">
                       
       <el-table-column :label="$t('field.indexNumber')" key="#1" width="70">
        <template slot-scope="scope">
              <slot name="sequee" :data="scope">
                <span>{{(currentPage-1) * pageSize + scope.$index+1}}</span>
              </slot>
            </template>
          </el-table-column>


        <el-table-column
        prop="date"
        label="日期"
        width="180">
      </el-table-column>
      <el-table-column
        prop="name"
        label="姓名"
        width="180">
      </el-table-column>
      <el-table-column
        prop="address"
        label="地址">
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
            tableData: [{
            date: '2016-05-02',
            name: '王小虎',
            address: '上海市普陀区金沙江路 1518 弄'
          }, {
            date: '2016-05-04',
            name: '王小虎',
            address: '上海市普陀区金沙江路 1517 弄'
          }, {
            date: '2016-05-01',
            name: '王小虎',
            address: '上海市普陀区金沙江路 1519 弄'
          }, {
            date: '2016-05-03',
            name: '王小虎',
            address: '上海市普陀区金沙江路 1516 弄'
          }],

            value:'',
            input:'',
            currentPage: 1,
            itemCount:0,
            pageSize: 20,
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
    },
    methods: {
        getIndex($index) {
        //表格序号
        return (this.page.currentPage - 1) * this.page.pageSize + $index + 1
      },
      handleSizeChange(val) {
      this.pageSize = val;
      localStorage.setItem("docPageSize", val);
      //console.log('handleSizeChange', val);
      // this.$emit("pagesizechange", this.pageSize);
      this.loadGridData();
      },  
                        
      handleCurrentChange(val) {
      this.currentPage = val;
      //console.log('handleCurrentChange', val);
      // this.$emit("pagechange", this.currentPage);
      this.freshtable();
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

</style>