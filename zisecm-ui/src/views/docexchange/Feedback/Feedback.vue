<template>
     <div>
       <div>
       <ecm-data-icons ref="dataIcons" :option="projectData1"></ecm-data-icons>
       </div>
      <DataSelect v-model="filters.projectCode" :includeAll="true" dataUrl="/exchange/project/myproject" 
      dataValueField="name" dataTextField="name" @onSelectChange="onSelectChange"></DataSelect> 
      <el-col :span="24" >
      <div id="docChart1" :style="{height: divHeight,width:divWidth,border:'0px solid  #CFC4CC','border-radius': '4px','margin':'5px',}"></div>
      </el-col> 
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

      docChart1: Object,
      docChartData1: {
        xAxisData: [],
        yAxisData: []
      },
      divWidth: '1000px',
      divHeight: '540px',
      dataReport: [],
      count:'',

      projectData1: {
        color: 'rgb(63, 161, 255)',
        span: 6,
        data:[{title: 'IED',
                count: 11,
                color: 'rgb(63, 161, 255)',
                icon: 'el-icon-s-unfold',
                url: '/ied/releaseied'}],
        
      },
      inputValueNum: "",

      formLabelWidth: "120px",
    };
  },

  mounted() {
     let _self = this;
    _self.docChart1 = _self.echarts.init(document.getElementById('docChart1'));
    _self.initChart();
    _self.getIEDNum()
    console.log(this.projectData1.data)
    this.language = localStorage.getItem("localeLanguage") || "zh-cn";
  },

  methods: {
    onSelectChange(val){
      this.filters.projectCode = val
      this.initChart()
      this.getIEDNum()
      console.log(this.projectData1)
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
                    _self.loadDocChart(_self.docChart1, _self.docChartData1);
                }
            })
            .catch(function(error) {
                console.log(error);
            });
      },
      getIEDNum(){
        let _self=this;
        let mp=new Map();
        let datas
        mp.set('projectName','@project');
        axios.post("/dc/getIEDNum",JSON.stringify(mp))
            .then(function(response) {
                if(response.data.code==1){
                datas = [{
                title: 'IED',
                count: response.data.data.num,
                color: 'rgb(63, 161, 255)',
                icon: 'el-icon-s-unfold',
                url: '/cnpe/iedmanagement/IEDpublished'
              }]
            _self.projectData1.data = datas
            _self.$refs.dataIcons.refresh()//.option=_self.projectData1;
      }
            })
      },

    loadDocChart(chartObj, indata){
      chartObj.setOption({
            title: { text: 'IED统计' },
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