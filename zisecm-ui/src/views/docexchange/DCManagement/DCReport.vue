<template>
  <div class="app-container">
    <el-col :span="24">
      <el-tabs value="tab01">
        <el-tab-pane label="文函统计" name="tab01">
          <el-col :span="24" style="padding-top: 0px; padding-bottom: 0px;">
            <el-form :inline="true" :model="filters" @submit.native.prevent>
              <el-form-item>
                <DataSelect v-model="filters.projectCode" :includeAll="true" dataUrl="/exchange/project/myproject" 
                    dataValueField="name" dataTextField="name"></DataSelect>
              </el-form-item>
              <el-form-item>
               <el-date-picker v-model="filters.startDate" type="date" placeholder="选择开始日期" style="display:block;" value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
              </el-form-item>
              <el-form-item>
               <el-date-picker v-model="filters.endDate" type="date" placeholder="选择结束日期" style="display:block;" value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
              </el-form-item>
              <el-form-item>
               <el-button :plain="true" @click="initChart">生成报表</el-button>
              </el-form-item>
            </el-form>
          </el-col>
          <el-col :span="24" >
            <div id="docChart1" :style="{height: divHeight, border:'0px solid  #CFC4CC','border-radius': '4px','margin':'5px'}"></div>
          </el-col>
        </el-tab-pane>
        <el-tab-pane label="漏号统计" name="tab02">
          <el-col :span="24" style="padding-top: 0px; padding-bottom: 0px;">
            <el-form :inline="true" :model="filters" @submit.native.prevent>
              <el-form-item>
                <!-- <el-select v-model="filters.projectCode" placeholder="选择项目">
                  <el-option label="三门3、4号机组" value="1"></el-option>
                  <el-option label="福清5、6号机组" value="2"></el-option>
                  <el-option label="海南5、6号机组" value="3"></el-option>
                  <el-option label="台山3、4号机组" value="4"></el-option>
                  <el-option label="田湾7、8号机组" value="5"></el-option>
                </el-select> -->
                <DataSelect v-model="filters.projectCode" defaultIsNull :includeAll="true" dataUrl="/exchange/project/myproject" 
                    dataValueField="name" dataTextField="name"></DataSelect>
              </el-form-item>
              <el-form-item>
               <el-select v-model="filters.supplier" placeholder="请选择分包商">
                <el-option label="分包商1" value="1"></el-option>
                <el-option label="分包商2" value="2"></el-option>
                <el-option label="分包商3" value="3"></el-option>
                <el-option label="分包商4" value="4"></el-option>
                <el-option label="分包商5" value="5"></el-option>
              </el-select>
              </el-form-item>
              <el-form-item>
                <el-select v-model="filters.docType">
                    <!-- <el-option label="所有文函" value></el-option>
                    <el-option label="传递单" value="传递单"></el-option>
                    <el-option label="图文传真" value="图文传真"></el-option>
                    <el-option label="会议纪要" value="会议纪要"></el-option>
                    <el-option label="接口传递" value="接口传递"></el-option> -->
                    <el-option label="所有文函" value></el-option>
                    <el-option v-for="(name,nameIndex) in childrenTypes" :key="'Type2_'+nameIndex" :label="name" :value="name"></el-option>
                  </el-select>
              </el-form-item>
              <el-form-item>
               <el-button :plain="true">{{$t('application.startStatistics')}}</el-button>
              </el-form-item>
              <el-form-item>
               <el-button :plain="true">{{$t('application.ExportExcel')}}</el-button>
              </el-form-item>
            </el-form>
          </el-col>
        </el-tab-pane>
        <el-tab-pane label="未回函报表" name="tab03">
          <el-col :span="24" style="padding-top: 0px; padding-bottom: 0px;">
            <el-form :inline="true" :model="noReceiveData" @submit.native.prevent>
              <el-form-item>
                <DataSelect v-model="noReceiveData.projectCode" :includeAll="true" dataUrl="/exchange/project/myproject" 
                    dataValueField="name" dataTextField="name"></DataSelect>
              </el-form-item>
              
              <el-form-item>
               <el-date-picker v-model="noReceiveData.startDate" type="date" placeholder="选择开始日期" style="display:block;" value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
              </el-form-item>
              <el-form-item>
               <el-date-picker v-model="noReceiveData.endDate" type="date" placeholder="选择结束日期" style="display:block;" value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
              </el-form-item>
              <el-form-item>
               <el-button :plain="true" @click="searchNoReceiveData">{{$t('application.startStatistics')}}</el-button>
              </el-form-item>
              <el-form-item>
               <el-button :plain="true" @click="exportData">{{$t('application.ExportExcel')}}</el-button>
              </el-form-item>
            </el-form>
          </el-col>
          <DataGrid
                ref="noReceiveGrid"
                key="main"
                dataUrl="/dc/getNoReceiveData"
                v-bind:tableHeight="tableHeight"
                v-bind:isshowOption="true" v-bind:isshowSelection ="true"
                gridViewName="DCReportNoReceive"
                :isshowCustom="false"
                :isEditProperty="false"
                showOptions="查看内容"
                :isShowChangeList="false"
                @rowclick="rowClick"
                @selectchange="selectChange"
                >
                    
                </DataGrid>
        </el-tab-pane>
      </el-tabs>
    </el-col>
  </div>

</template>

<script>
import util from '@/utils/table.js'
import DataGrid from "@/components/DataGrid";
import ExcelUtil from '@/utils/excel.js'
import DataSelect from '@/components/ecm-data-select'
export default {
  data() {
    return {
      filters: {
        projectCode: '',
        startDate: null,
        endDate: null,
        supplier: '',
        docType: '',
        limit: 10
      },
      noReceiveData:{
           projectCode: '',
            startDate: null,
            endDate: null,
      },
      showHidden: false,
      tableHeight: window.innerHeight - 210,
      dataList: [],
      total: 0,
      page: 1,
      divHeight: '540px',
      docChart1: Object,
      docChartData1: {
        xAxisData: [],
        yAxisData: []
      }
    }
  },
  mounted(){
    let _self = this;
    _self.docChart1 = _self.echarts.init(document.getElementById('docChart1'));
    _self.initChart();
    // _self.$nextTick(()=>{
    //     _self.loadDocChart(_self.docChart1, _self.docChartData1);
    // });
    
    
    window.onresize = this.onWindowResize;
  },
  methods: {
      exportData(){
            let params = {
                gridName:this.$refs.noReceiveGrid.gridViewName,
                lang:"zh-cn",
                condition:this.$refs.noReceiveGrid.condition,
                filename:"DCReport_"+new Date().Format("yyyy-MM-dd hh:mm:ss")+".xlsx",
                sheetname:"Result",
                URL:"/exchange/doc/export4Report"
            }
            ExcelUtil.export4Cnpe(params)
        },
      searchNoReceiveData(){
           let _self=this;
            let key="";
            if(_self.noReceiveData.projectCode!=''){
                key+=" and C_PROJECT_NAME = "+_self.filters.projectCode;
            }else{
                key+=" and C_PROJECT_NAME = '@project'";
            }
            if(_self.noReceiveData.startDate){
                key+=" and C_ITEM1_DATE >= '"+_self.noReceiveData.startDate+"'";
            }
            if(_self.noReceiveData.endDate){
                 key+=" and C_ITEM1_DATE <= '"+_self.noReceiveData.startDate+"'";
            }
            
            if(key!=''){
                _self.$refs.noReceiveGrid.condition=key;
            }
            _self.$refs.noReceiveGrid.loadGridData();

      },
      initChart(){
          let _self=this;
          let mp=new Map();
          if(_self.filters.projectCode){
              mp.set('projectName',_self.filters.projectCode);
          }else{
              mp.set('projectName','@project');
          }
          if(_self.filters.startDate){
              mp.set('startDate',_self.filters.startDate);
          }
          if(_self.filters.endDate){
              mp.set('endDate',_self.filters.endDate);
          }
          axios.post("/dc/getDCReportData",JSON.stringify(mp))
            .then(function(response) {
                // docChartData1: {
                //     xAxisData: [],
                //     yAxisData: []
                // }
                if(response.data.code==1){
                    let result=response.data.data;
                    let xArray=new Array();
                    let yArray=new Array();
                    for (let key in result){
                        xArray.push(key);
                        yArray.push(result[key]);
                    }
                    _self.docChartData1.xAxisData=xArray;
                    _self.docChartData1.yAxisData=yArray;
                    _self.loadDocChart(_self.docChart1, _self.docChartData1);
                }
            })
            .catch(function(error) {
                console.log(error);
            });
      },
    onWindowResize(){
      this.tableHeight = window.innerHeight - 210;
    },
    showDoc(inData){
      this.$router.push({ path: '/doc/showdoc' });
    },
    dateFormat: function(row, column) {
      // console.log(column);
      return util.formatDate.format(new Date(row[column.property]), 'yyyy-MM-dd')
    },
    handleCurrentChange(val) {
      this.page = val
      this.getData()
    },
    handleSizeChange(val) {
      this.filters.limit = val
      this.getData()
    },
    confirmShow() {
      this.showHidden = true
      this.colshowDialogVisible = false
    },
    // getData() {
    //   const para = {
    //     projectCode: this.filters.projectCode,
    //     coding: '',
    //     title: '',
    //     page: this.page,
    //     limit: this.filters.limit
    //   }      
    // //   getDataPage(para).then(res => {
    // //     this.total = res.data.total
    // //     this.dataList = res.data.data
    // //   })
    // },
    loadDocChart(chartObj, indata){
      chartObj.setOption({
            title: { text: '文函统计' },
            tooltip: {},
            grid: {  
              left: '10%',
              bottom:'35%'
            },
            xAxis: {
              data: indata.xAxisData,
              axisLabel: {  
                interval:0,
                rotate:40  
              }  
            },
            yAxis: {},
            series: [{
                name: '数量',
                type: 'bar',
                data: indata.yAxisData,
                itemStyle: {
                  normal: {
                    color:function(d){return "#"+Math.floor(Math.random()*(256*256*256-1)).toString(16);},
                    label: {
                      show: true, //开启显示
                      position: 'top', //在上方显示
                      textStyle: { //数值样式
                        color: 'black',
                        fontSize: 16
                      }
                    }
                  }
                }
            }]
        });
    }
  },
  components:{
      DataSelect:DataSelect,
      DataGrid:DataGrid
  }
}
</script>

<style scoped>
.line{
  text-align: center;
}
</style>

