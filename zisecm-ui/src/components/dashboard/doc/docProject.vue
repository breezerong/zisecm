<template>
  <div>
    <DataSelect @onSelectChange='onSelectChange' v-model="filters.projectCode" :includeAll="true" dataUrl="/exchange/project/myproject" 
                    dataValueField="name" dataTextField="name"></DataSelect>
    <el-row>
      <el-col :span="4">
        <ecm-data-icons :option="projectData"></ecm-data-icons>
      </el-col>
      <el-col  :span="20">
        <div id="docChart1" :style="{height: divHeight, width:divWidth,border:'0px solid  #CFC4CC','border-radius': '4px','margin':'5px'}"></div>
      </el-col>
    </el-row>
 </div>
</template>


<script type="text/javascript">
import ecmDataIcons from '@/components/ecm-data-icons/ecm-data-icons'
import DataSelect from '@/components/ecm-data-select'
export default {
 name: "planProjDashBoard",
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
      divWidth: '500px',
      divHeight: '300px',
      docChart1: Object,
      docChartData1: {
        xAxisData: [],
        yAxisData: []
      },
      projectData: {
        color: 'rgb(63, 161, 255)',
        span: 24,
        data: [
          {
            title: '三级计划',
            count: 0,
            color: 'rgb(63, 161, 255)',
            icon: 'el-icon-s-order',
            url: '/cnpe/plan/threelevelplan'
          },
          {
            title: 'IED',
            count: 0,
            color: 'rgb(63, 161, 255)',
            icon: 'el-icon-s-unfold',
            url: '/cnpe/iedmanagement/IEDpublished'
          },
          {
            title: 'ICM',
            count: 0,
            color: 'rgb(255, 0, 0)',
            icon: 'el-icon-document',
            url: '/cnpe/MoreViewerBrowe/projectviewer'
          }
        ]
      },
      a:[]
    };
  },
  created() {
    let _self = this;
    _self.initChart();
  },
  mounted(){
    let _self = this
     _self.docChart1 = _self.echarts.init(document.getElementById('docChart1'));
    
  },
  methods: {
    onSelectChange(val){
      this.filters.projectCode = val
      this.initChart()
    },
    initChart(){
      let _self=this;
      let mp=new Map();
      if(_self.filters.projectCode){
          mp.set('projectName',_self.filters.projectCode);
      }
      axios.post("/exchange/docTop/getDCData",JSON.stringify(mp))
      .then(function(response) {
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
      axios.post("/exchange/docTop/docSumNum",JSON.stringify(mp))
      .then(function (response) {
          if(response.data.code==1){
            console.log(response.data)
              _self.a[0]=response.data.ThreePlanNum;
              _self.a[1]=response.data.IEDNum;
              _self.a[2]=response.data.ICMNum;
              let i=0
              _self.projectData.data.forEach(function(item){
                item.count=_self.a[i++];
              })
          }
          
        })
        .catch(function (error) {
          console.log(error);
        });
    },
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
    },
  },
  components: {
    ecmDataIcons:ecmDataIcons,
    DataSelect:DataSelect,
  },
};

</script>