<template>
     <div>
      <DataSelect v-model="filters.projectCode" :includeAll="true" dataUrl="/exchange/project/myproject" 
      dataValueField="name" dataTextField="name" @onSelectChange="onSelectChange"></DataSelect> 
      
      <el-row>
      <el-col :span="4">
      <ecm-data-icons ref="dataIED" :option="projectDataIED"></ecm-data-icons>
      <ecm-data-icons ref="dataDC" :option="projectDataDC"></ecm-data-icons>
      <ecm-data-icons ref="dataTPLAN" :option="projectDataTPLAN"></ecm-data-icons>
      </el-col>
      <el-col :span="10" >
      <div id="docChart2" :style="{height: divHeight,width:divWidth,border:'0px solid  #CFC4CC','border-radius': '4px','margin':'5px',}"></div>
      </el-col>
      </el-row> 
     </div>
</template>
<script type="text/javascript">
import ecmDataIcons from '@/components/ecm-data-icons/ecm-data-icons'
import ShowProperty from "@/components/ShowProperty";
import DataGrid from "@/components/DataGrid";
import DataLayout from "@/components/ecm-data-layout";
import ExcelUtil from '@/utils/excel.js';
import util from '@/utils/table.js';
import DataSelect from '@/components/ecm-data-select';
export default {
  name: "FeedBackGrid",
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

      docChart2: Object,
      docChartData1: {
        xAxisData: [],
        yAxisData: []
      },
      divWidth: '500px',
      divHeight: '300px',
      dataReport: [],
      count:'',
      projectDataIED:{
        color: 'rgb(63, 161, 255)',
        span: 6,
        data:[{title: '三级计划',
                count: 11,
                color: 'rgb(63, 161, 255)',
                icon: 'el-icon-s-unfold',
                url: '/ied/releaseied'}],},
      projectData1: {
        color: 'rgb(63, 161, 255)',
        span: 6,
        data:[{title: '三级计划',
                count: 11,
                color: 'rgb(63, 161, 255)',
                icon: 'el-icon-s-unfold',
                url: '/ied/releaseied'}],
        
      },
      projectDataDC: {
        color: 'rgb(63, 161, 255)',
        span: 6,
        data:[{title: '文函',
                count: 11,
                color: 'rgb(63, 161, 255)',
                icon: 'el-icon-s-unfold',
                url: '/ied/releaseied'}],
        },
        projectDataTPLAN: {
        color: 'rgb(63, 161, 255)',
        span: 6,
        data:[{title: '三级计划',
                count: 11,
                color: 'rgb(63, 161, 255)',
                icon: 'el-icon-s-unfold',
                url: '/ied/releaseied'}],
        },
        projectDataProjectNum: {
        color: 'rgb(63, 161, 255)',
        span: 6,
        data:[{title: '计划数',
                count: 11,
                color: 'rgb(63, 161, 255)',
                icon: 'el-icon-s-flag',}],
        },


      inputValueNum: "",

      formLabelWidth: "120px",
    };
  },

  mounted() {
     let _self = this;
    _self.docChart2 = _self.echarts.init(document.getElementById('docChart2'));
    _self.initChart();
    _self.getIEDNum()
    _self.getDCNum()
    _self.getTPLANNum()
    this.language = localStorage.getItem("localeLanguage") || "zh-cn";
  },

  methods: {
    onSelectChange(val){
    let _self = this
    this.filters.projectCode = val
    _self.getIEDNum()
    _self.getDCNum()
    _self.getTPLANNum()
      this.initChart()
    },
    initChart(){
          let _self=this;
          let mp=new Map();
          if(_self.filters.projectCode){
              mp.set('projectName',_self.filters.projectCode);
          }else{
              mp.set('projectName','@project');
          }
          axios.post("/dc/getIEDDash",JSON.stringify(mp))
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
                    _self.loadDocChart(_self.docChart2, _self.docChartData1);
                }
            })
            .catch(function(error) {
                console.log(error);
            });
      },
      getDCNum(){         //获取文函数量
        let _self=this;
        let mp=new Map();
        let dataDC
        if(_self.filters.projectCode){
              mp.set('projectName',_self.filters.projectCode);
          }else{
              mp.set('projectName','@project');
          }
          console.log(mp)
        axios.post("/dc/getDCNum",JSON.stringify(mp))
            .then(function(response) {
                if(response.data.code==1){
                dataDC = [{
                title: '文函',
                count: response.data.data.num,
                color: 'rgb(63, 161, 255)',
                icon: 'el-icon-s-order',
                url: '/cnpe/MoreViewerBrowe/projectviewer'
              }]
            _self.projectDataDC.data = dataDC
            _self.$refs.dataDC.refresh()//.option=_self.projectData1;
      }
            })

      },


      getIEDNum(){      //获取IED数量
        let _self=this;
        let mp=new Map();
        let datas
        if(_self.filters.projectCode){
              mp.set('projectName',_self.filters.projectCode);
          }else{
              mp.set('projectName','@project');
          }
        axios.post("/dc/getIEDNum",JSON.stringify(mp))
            .then(function(response) {
                if(response.data.code==1){
                datas = [{
                title: 'IED',
                count: response.data.data.num,
                color: 'rgb(63, 161, 255)',
                icon: 'el-icon-s-unfold',
                url: '/cnpe/MoreViewerBrowe/projectviewer'
              }]
            _self.projectDataIED.data = datas
            _self.$refs.dataIED.refresh()
      }
            })
      },
      getTPLANNum(){
        let _self=this;
        let mp=new Map();
        let data
         if(_self.filters.projectCode){
              mp.set('projectName',_self.filters.projectCode);
          }else{
              mp.set('projectName','@project');
          }
        axios.post("/dc/getTPLANNum",JSON.stringify(mp))
            .then(function(response) {
                if(response.data.code==1){
                data = [{
                title: '三级计划',
                count: response.data.data.num,
                color: 'rgb(63, 161, 255)',
                icon: 'el-icon-s-order',
                url: '/cnpe/MoreViewerBrowe/projectviewer'
              }]
            _self.projectDataTPLAN.data = data
            _self.$refs.dataTPLAN.refresh()
      }
      })
      },
      getProjectNum(){
        let _self=this;
        let mp=new Map();
        let data
         if(_self.filters.projectCode){
              mp.set('projectName',_self.filters.projectCode);
          }else{
              mp.set('projectName','@project');
          }
        axios.post("/dc/getProjectNum",JSON.stringify(mp))
            .then(function(response) {
                if(response.data.code==1){
                data = [{
                title: '计划',
                count: response.data.data.num,
                color: 'rgb(63, 161, 255)',
                icon: 'el-icon-s-flag',
              }]
            _self.projectDataProjectNum.data = data
            _self.$refs.dataProjectNum.refresh()
      }
      })
      },

    loadDocChart(chartObj, indata){
      chartObj.setOption({
            title: { text: 'IED统计' },
            tooltip: {},
            grid: {  
              left: '8%',
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

  watch:{
    '$store.state.app.language':function(){
    }
  },

  components: {
    ShowProperty: ShowProperty,
    DataSelect:DataSelect,
    DataGrid: DataGrid,
    DataLayout: DataLayout,
    ecmDataIcons,
  },
};
</script>
<style scoped>


.wrap {
/* 可无需设置高度，靠图片高度 或者 文字的上下padding撑开高度 */
   
    width: 200px;
}
.wrap span{
    display: inline-block;
    vertical-align: middle;
    padding: 20px 0; /* 撑开高度 */
}
.wrap img{
    width: auto;
    height: auto;
    vertical-align: middle;
}
</style>