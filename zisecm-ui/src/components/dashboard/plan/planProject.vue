<template>
  <div id="docChart4" :style="{height: divHeight,width:divWidth,border:'0px solid  #CFC4CC','border-radius': '4px','margin':'5px',}"></div>
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

      docChart4: Object,
      docChartData4: {
        xAxisData: [],
        yAxisData: []
      },
      divWidth: '100%',
      divHeight: '300px',
      dataReport: [],
      count:'',
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


      inputValueNum: "",

      formLabelWidth: "120px",
    };
  },

  mounted() {
     let _self = this;
    _self.docChart4 = _self.echarts.init(document.getElementById('docChart4'));
    _self.initChart();
    this.loadStaitic()
    this.language = localStorage.getItem("localeLanguage") || "zh-cn";
  },

  methods: {
    onSelectChange(val){
    let _self = this
    this.filters.projectCode = val
    this.loadStaitic()
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
                    _self.docChartData4.xAxisData=xArray;
                    _self.docChartData4.yAxisData=yArray;
                    _self.loadDocChart(_self.docChart4, _self.docChartData4);
                }
            })
            .catch(function(error) {
                console.log(error);
            });
      },
      loadStaitic(){
        let mp=new Map();
        let _self=this
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
        axios.post("/exchange/docTop/docSumNum",JSON.stringify(mp))
      .then(function (response) {
          if(response.data.code==1){
           
              console.log(response.data)
              _self.projectDataTPLAN.data[0].count=response.data.ThreePlanNum;
              _self.projectDataIED.data[0].count=response.data.IEDNum;
              _self.projectDataICM.data[0].count=response.data.ICMNum;
              _self.projectDataDC.data[0].count=response.data.dcNum
          }
          
        })
        .catch(function (error) {
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
                title: _self.$t('application.Document'),
                count: response.data.data.num,
                color: 'rgb(63, 161, 255)',
                icon: 'el-icon-s-order',
                url: '/cnpe/MoreViewerBrowe/projectviewer'
              }]
              console.log(response.data)
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
        axios.post("/dc/getCNPEIEDNum",JSON.stringify(mp))
            .then(function(response) {
                if(response.data.code==1){
                datas = [{
                title: _self.$t('application.IEDPublished'),
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
                title: _self.$t('application.Projects'),
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
            title: { text: this.$t('application.IEDDash') },
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