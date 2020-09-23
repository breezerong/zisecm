<template>
        <div id="docChart2" :style="{height: divHeight, width:divWidth,border:'0px solid  #CFC4CC','border-radius': '4px','margin':'5px'}"></div>
</template>


<script type="text/javascript">
import ecmDataIcons from '@/components/ecm-data-icons/ecm-data-icons'
import DataSelect from '@/components/ecm-data-select'
import DataLayout from '@/components/ecm-data-layout'
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
      isCNPEwk:false,
      showHidden: false,
      tableHeight: window.innerHeight - 210,
      dataList: [],
      total: 0,
      page: 1,
      divWidth: '100%',
      divHeight: '300px',
      docChart2: Object,
      docChartData2: {
        xAxisData: [],
        yAxisData: []
      },
      projectDataTPLAN: {
        color: 'rgb(63, 161, 255)',
        span: 6,
        data: [{
            title:this.$t('application.TPlan'),
            count: 0,
            color: 'rgb(63, 161, 255)',
            icon: 'el-icon-s-order',
            url: '/cnpe/plan/threelevelplan'}]
      },
      projectDataIED: {
        color: 'rgb(63, 161, 255)',
        span: 6,
        data: [{
            title: this.$t('application.IEDPublished'),
            count: 0,
            color: 'rgb(63, 161, 255)',
            icon: 'el-icon-s-unfold',
            url: '/cnpe/iedmanagement/IEDpublished'}]
      },
      projectDataICM: {
        color: 'rgb(63, 161, 255)',
        span: 6,
        data: [{
            title: this.$t('application.ICM'),
            count: 0,
            color: 'rgb(255, 0, 0)',
            icon: 'el-icon-document',
            url: '/cnpe/MoreViewerBrowe/projectviewer'
        }]
      },
       projectDataDC: {
        color: 'rgb(63, 161, 255)',
        span: 6,
        data: [{
            title: this.$t('application.Document'),
            count: 0,
            color: 'rgb(63, 161, 255)',
            icon: 'el-icon-document',
            url: '/cnpe/MoreViewerBrowe/projectviewer'
        }]
      },
    };
  },
  created() {
    let _self = this;
    _self.initChart();
  },
  mounted(){
    let _self = this
     _self.docChart2 = _self.echarts.init(document.getElementById('docChart2'));
    
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
      axios.post("/exchange/docTop/getCNPEDCData",JSON.stringify(mp))
      .then(function(response) {
          if(response.data.code==1){
              let result=response.data.data;
              let xArray=new Array();
              let yArray=new Array();
              for (let key in result){
                  xArray.push(key);
                  yArray.push(result[key]);
              }
              _self.docChartData2.xAxisData=xArray;
              _self.docChartData2.yAxisData=yArray;
              _self.loadDocChart(_self.docChart2, _self.docChartData2);
        }
      })
      .catch(function(error) {
          console.log(error);
      });
      axios.post("/exchange/docTop/docSumNum",JSON.stringify(mp))
      .then(function (response) {
          if(response.data.code==1){
           
              console.log(response.data)
              _self.projectDataTPLAN.data[0].count=response.data.ThreePlanNum;
              _self.projectDataIED.data[0].count=response.data.IEDNum;
              _self.projectDataICM.data[0].count=response.data.ICMNum;
              _self.projectDataDC.data[0].count=response.data.dcNum
              _self.$refs.T1.refresh()
              _self.$refs.T2.refresh()
              _self.$refs.T3.refresh()
          }
          
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    loadDocChart(chartObj, indata){
      chartObj.setOption({
            title: { text: this.$t('application.DCDash') },
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
                name: this.$t('application.nums'),
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
    DataLayout:DataLayout
  },
};

</script>