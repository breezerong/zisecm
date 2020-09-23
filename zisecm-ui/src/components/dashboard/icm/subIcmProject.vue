<template>
 <div>
      <DataSelect v-model="filters.projectCode" :includeAll="true" dataUrl="/exchange/project/myproject" 
      dataValueField="name" dataTextField="name" @onSelectChange="onSelectChange"></DataSelect>     
      <el-row>
      <el-col :span="4">
      <ecm-data-icons ref="T1" :option="projectDataTPLAN"></ecm-data-icons>
        <ecm-data-icons ref="T2" :option="projectDataIED"></ecm-data-icons>
        <ecm-data-icons ref="T3" :option="projectDataICM"></ecm-data-icons>
        <ecm-data-icons ref="T4" :option="projectDataDC"></ecm-data-icons>
         <ecm-data-icons ref="T5" :option="projectDataDE"></ecm-data-icons>
      </el-col>
       <el-col  :span="20">
        <planProject v-if="isSubPlan"  ref="plan"></planProject>
        <docProject v-if="isSubWK" ref="doc"></docProject>
        <div id="docChart1" :style="{height: divHeight, width:divWidth,border:'0px solid  #CFC4CC','border-radius': '4px','margin':'5px'}"></div>
      </el-col>
      </el-row> 
     </div>
</template>


<script type="text/javascript">
import ecmDataIcons from '@/components/ecm-data-icons/ecm-data-icons'
import DataSelect from '@/components/ecm-data-select';
import docProject from '@/components/dashboard/doc/subDocProject';
import planProject from '@/components/dashboard/plan/subPlanProject';
export default {
  components: {
  DataSelect:DataSelect,
  ecmDataIcons,
  docProject:docProject,
  planProject:planProject,
  },
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
      isSubPlan:false,
      isSubWK:false,
      divWidth: '100%',
      divHeight: '300px',
      tempRoles:[],
      userRoles:[],
      docChart1: Object,
      docChartData1: {
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
            color: 'rgb(63, 161, 255)',
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
            icon: 'el-icon-s-unfold',
            url: '/cnpe/MoreViewerBrowe/projectviewer'}]
      },
      projectDataDE: {
        color: 'rgb(63, 161, 255)',
        span: 6,
        data: [{
            title: this.$t('application.designdoc'),
            count: 0,
            color: 'rgb(63, 161, 255)',
            icon: 'el-icon-document',
            url: ''
        }]
      },
    };
  },
  created() {
    let _self = this;
    this.initChart()
    this.loadStatistic()
    this.getRoles()
  },
  mounted(){
    let _self = this
     _self.docChart1 = _self.echarts.init(document.getElementById('docChart1'));
    
  },
  methods: {
    onSelectChange(val){
    let _self = this
    this.filters.projectCode = val
    _self.loadStatistic()
    this.initChart()
    if(this.isSubWK==true)
    this.$refs.doc.onSelectChange(val)
    if(this.isSubPlan==true)
    this.$refs.plan.onSelectChange(val)
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
getRoles() {
      //用户类型判断
      this.tempRoles = this.currentUser().roles;
      for (var i = 0; i < this.tempRoles.length; i++) {
        if (
          this.tempRoles[i] == "分包商文控人员" ||
          this.tempRoles[i] == "CNPE_文控人员" ||
          this.tempRoles[i] == "CNPE_计划人员" ||
          this.tempRoles[i] == "CNPE_接口人员" ||
          this.tempRoles[i] == "分包商接口人员" ||
          this.tempRoles[i] == "分包商计划人员"
        ) {
          this.userRoles[i] = this.tempRoles[i];
        }
      }
      for (var i = 0; i < this.userRoles.length; i++) {
        if (this.userRoles[i] == "分包商文控人员") {
          this.isSubWK = true;
          this.isSub = true;
           console.log("SUBwk:"+this.isSubWK)
        }
        if (this.userRoles[i] == "分包商接口人员") {
          this.isSubJK = true;
          this.isSub = true;
          //console.log("SubJK:"+this.isSubJK)
        }
        if (this.userRoles[i] == "分包商计划人员") {
          this.isSubPlan = true;
          this.isSub = true;
          console.log("SUBPLAN:"+this.isSubPlan)
        }
        if (this.userRoles[i] == "分包商设总") {
          this.issubGen = true;
          this.isSub=true
          //console.log("SUBPLAN:"+this.isSubPlan)
        }
        if (this.userRoles[i] == "CNPE_文控人员") {
          this.isCNPEWK = true;
          this.isCNPE = true;
          console.log("CNPEWK:"+this.isCNPEWK)
        } else if (this.userRoles[i] == "CNPE_计划人员") {
          this.isCNPEPlan = true;
          this.isCNPE = true;
          console.log("CNPEPL:"+this.isCNPEPlan)
        } else if (this.userRoles[i] == "CNPE_接口人员") {
          this.isCNPEJK = true;
          this.isCNPE = true;
          //console.log("CNPEJK:"+this.isCNPEJK)
        } else if (this.userRoles[i] == "CNPE_设总") {
          this.isCNPEGen = true;
          this.isCNPE = true;
          //console.log("CNPEJK:"+this.isCNPEJK)
        }
      }
    },

      loadStatistic(){
      let _self = this;
      let mp=new Map();
      if(_self.filters.projectCode){
              mp.set('projectName',_self.filters.projectCode);
          }else{
              mp.set('projectName','@project');
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

        axios.post("/dc/getSubDesignData",JSON.stringify(mp))
        .then(function (response) {
          if(response.data.code==1){
            console.log(response.data.data)
           _self.projectDataDE.data[0].count=response.data.data.DEnum
        }
        })
        .catch(function (error) {
          console.log(error);
        });   
    }


  },
  
};

</script>