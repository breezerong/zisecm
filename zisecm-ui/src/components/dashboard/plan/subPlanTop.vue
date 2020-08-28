<template>
     <div>  
        <el-form inline="true" >
        <el-row>
          <el-col :span="4" v-if ="isOnlySub || isOnlySubjh">
          <el-form-item><ecm-data-icons ref="dataProjectNum" :option="projectDataProjectNum"></ecm-data-icons></el-form-item>
          </el-col>
          <el-col :span="4" v-if="isOnlySubjh || isSubjkjh" >
          <el-form-item><ecm-data-icons ref="dataPlanNum" :option="projectDataPlanNum"></ecm-data-icons></el-form-item>
          </el-col>
          <el-col :span="4" v-if="isOnlySubjh|| isSubjkjh">
          <el-form-item><ecm-data-icons ref="dataTPLAN" :option="projectDataTPLAN"></ecm-data-icons></el-form-item>
          </el-col>
          <el-col :span="4" v-if="isOnlySubjh">
          <el-form-item><ecm-data-icons ref="dataPublishedIED" :option="projectDataPublishedIED"></ecm-data-icons></el-form-item>
          </el-col>
          <el-col :span="4" v-if="isSubjh">
          <el-form-item><ecm-data-icons ref="dataRejectIED" :option="projectDataRejectIED"></ecm-data-icons></el-form-item>
          </el-col>
          <el-col :span="4" v-if="isSubjh">
          <el-form-item><ecm-data-icons ref="dataPendingSubmitIED" :option="projectDataPendingSubmitIED"></ecm-data-icons></el-form-item>
          </el-col>
      <el-col :span="4" v-if="isOnlySubwk">
      <el-form-item><ecm-data-icons :option="projectData"></ecm-data-icons></el-form-item>
      </el-col>
      <el-col :span="4" v-if="isSubwk">
      <el-form-item><ecm-data-icons :option="projectDataDCnum"></ecm-data-icons></el-form-item>
      </el-col>
      <el-col :span="4" v-if="isSubwk">
      <el-form-item><ecm-data-icons :option="projectDataReceivingdc"></ecm-data-icons></el-form-item>
      </el-col>
      <el-col :span="4" v-if="isSubwk">
      <el-form-item><ecm-data-icons :option="projectDataSubmissiondc"></ecm-data-icons></el-form-item>
      </el-col>
      <el-col :span="4" v-if="isSubwk">
      <el-form-item><ecm-data-icons :option="projectDataDispensedc"></ecm-data-icons></el-form-item>
      </el-col>
      
      <el-col :span="4" v-if="isOnlySubjk">
      <el-form-item>
      <ecm-data-icons ref="p1" :option="projectDataICM"></ecm-data-icons>
      </el-form-item>
        </el-col>
        <el-col :span="4" v-if="isOnlySubjk">
          <el-form-item>
            <ecm-data-icons ref="p2" :option="projectData2"></ecm-data-icons>
          </el-form-item>
        </el-col>
        <el-col :span="4" v-if="isOnlySubjk">
          <el-form-item>
            <ecm-data-icons ref="p3" :option="projectData3"></ecm-data-icons>
          </el-form-item>
        </el-col>
        <el-col :span="4" v-if="isSubjk">
          <el-form-item>
            <ecm-data-icons ref="p4" :option="projectData4"></ecm-data-icons>
          </el-form-item>
        </el-col>
        <el-col  :span="4" v-if="isSubjk"> 
          <el-form-item>
            <ecm-data-icons ref="p5" :option="projectData5"></ecm-data-icons>
          </el-form-item>
        </el-col>
        


      </el-row> 
      </el-form>
      
      
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
      },
      tempJKJH:[],
      isSubjkjh:false,
      isOnlySub:false,
      isOnlySubjk:false,
      isSubjk:false,
      isOnlySubjh:false,
      isSubjh:false,
      isSubwk:false,
      isOnlySubwk:false,
      tempRoles:[],
      userRoles:[],
      docChart1: Object,
      docChartData1: {
        xAxisData: [],
        yAxisData: []
      },
      divWidth: '500px',
      divHeight: '500px',
      dataReport: [],
      count:'',

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
                count: 0,
                color: 'rgb(63, 161, 255)',
                icon: 'el-icon-s-unfold',
                url: 'cnpe/DCManagement/ReceivedDC4Cnpe'}],
        },
        projectDataTPLAN: {
        color: 'rgb(63, 161, 255)',
        span: 6,
        data:[{title: '三级计划',
                count: 11,
                color: 'rgb(63, 161, 255)',
                icon: 'el-icon-s-unfold',
                url: '/cnpe/plan/threelevelplan'}],
        },
        projectDataProjectNum: {
        color: 'rgb(63, 161, 255)',
        span: 6,
        data:[{title: '计划数',
                count: 11,
                color: 'rgb(63, 161, 255)',
                icon: 'el-icon-s-flag',
                url:''}],
        },

        projectDataPublishedIED: {
        color: 'rgb(63, 161, 255)',
        span: 6,
        data:[{title: '计划数',
                count: 11,
                color: 'rgb(63, 161, 255)',
                icon: 'el-icon-s-flag',}],
        },
        projectDataPendingSubmitIED:{
        color: 'rgb(63, 161, 255)',
        span: 6,
        data:[{title: '计划数',
                count: 11,
                color: 'rgb(63, 161, 255)',
                icon: 'el-icon-s-flag',
                url:'/cnpe/iedmanagement/pendingsubmit'}],
        },
        projectDataRejectIED:{
        color: 'rgb(63, 161, 255)',
        span: 6,
        data:[{title: '计划数',
                count: 11,
                color: 'rgb(63, 161, 255)',
                icon: 'el-icon-s-flag',
              }],
        },
        projectDataPlanNum:{
        color: 'rgb(63, 161, 255)',
        span: 6,
        data:[{title: '计划数',
                count: 11,
                color: 'rgb(63, 161, 255)',
                icon: 'el-icon-s-flag',
                url:''}],
        },
        projectDataICM: {
        data: [
          {
            title: "项目",
            count: 0,
            color: "rgb(63, 161, 255)",
            icon: "el-icon-warning",
            url: "",
          },
        ],
      },
      projectData2: {
        data: [
          {
            title: "计划",
            count: 0,
            color: "rgb(63, 161, 255)",
            icon: "el-icon-document",
            url: "",
          },
        ],
      },
      projectData3: {
        data: [
          {
            title: "三级计划",
            count: 0,
            color: "rgb(63, 161, 255)",
            icon: "el-icon-document-checked",
            url: "/cnpe/DCManagement/receivingdc",
          },
        ],
      },
      projectData4: {
        data: [
          {
            title: "已生效IED",
            count: 0,
            color: "rgb(63, 161, 255)",
            icon: "el-icon-document-checked",
            url: "/cnpe/iedmanagement/IEDpublished",
          },
        ],
      },
      projectData5: {
        data: [
          {
            title: "ICM",
            count: 0,
            color: "rgb(63, 161, 255)",
            icon: "el-icon-document-delete",
            url: "/cnpe/icmmanagement/interfacemanual",
          },
        ],
      },
     
      projectData: {
        color: 'rgb(63, 161, 255)',
        span: 24,
        data: [
          {
            title: '项目',
            count:0,
            color: 'rgb(63, 161, 255)',
            icon: 'el-icon-warning',
            url: ''
          },
        ]
      },
      projectDataDCnum: {
        color: 'rgb(63, 161, 255)',
        span: 24,
        data: [
          {
            title: '文函',
            count: 15,
            color: 'rgb(63, 161, 255)',
            icon: 'el-icon-document',
            url: '/cnpe/DCManagement/receivedDC'
          },
        ]
      },
      projectDataReceivingdc: {
        color: 'rgb(63, 161, 255)',
        span: 24,
        data: [
          {
            title: '待接收文函',
            count: 0,
            color: 'rgb(255, 0, 0)',
            icon: 'el-icon-document-checked',
            url: '/cnpe/DCManagement/receivingdc'
          },
        ]
      },
      projectDataSubmissiondc: {
        color: 'rgb(63, 161, 255)',
        span: 24,
        data: [
          {
            title: '待提交文函',
            count: 0,
            color: 'rgb(255, 0, 0)',
            icon: 'el-icon-document-checked',
            url: '/cnpe/DCManagement/submissiondc'
          },
        ]
      },
      projectDataDispensedc: {
        color: 'rgb(63, 161, 255)',
        span: 24,
        data: [
         {
            title: '驳回文函',
            count: 0,
            color: 'rgb(255, 0, 0)',
            icon: 'el-icon-document-delete',
            url: '/cnpe/DCManagement/rejectedDC'
          }
        ]
      }
    };
  },

  mounted() {
     let _self = this;
    //_self.docChart1 = _self.echarts.init(document.getElementById('docChart1'));
    //_self.initChart();
    _self.getIEDNum()
    _self.getTPLANNum()
    _self.getProjectNum()
    _self.getPublishedIED()
    _self.getPendingSubmitIED()
    _self.getRejectIED()
    _self.getPlanNum()
    this.getUserRole()
    this.loadStatisticWK()
    this.loadStatistic()
    this.getDCNum()
    this.language = localStorage.getItem("localeLanguage") || "zh-cn";
  },

  methods: {
    getUserRole(){    //获取文件类型，进行本地验证
        var k=0;
        this.tempRoles=this.currentUser().roles
        for(var i = 0;i < this.tempRoles.length;i++){
          if(this.tempRoles[i] == '分包商文控人员'||this.tempRoles[i] =='CNPE_文控人员'||this.tempRoles[i] =='CNPE_计划人员'||
          this.tempRoles[i] =='CNPE_接口人员'||this.tempRoles[i] =='分包商接口人员'||this.tempRoles[i] =='分包商计划人员'){
          this.userRoles[k] = this.tempRoles[i] 
          k++;
          }
        }
        if(this.userRoles.length==1 && this.userRoles[0]=='分包商接口人员'){
          this.isOnlySubjk=true
          this.isSubjk=true
          }
        if(this.userRoles.length==1 && this.userRoles[0]=='分包商计划人员'){
          this.isOnlySubjk=true
          this.isSubjk=true
          }
          if(this.userRoles.length==1 && this.userRoles[0]=='分包商文控人员'){
          this.isOnlySubwk=true
          this.isSubwk=true
          }
          for(var i = 0;i < this.userRoles.length;i++){
            if(this.userRoles[i] == '分包商接口人员')
            this.isSubjk = true
            this.tempJKJH[0]=this.userRoles[i]
            if(this.userRoles[i] =='分包商计划人员')
            this.isSubjh = true
            this.tempJKJH[1]=this.userRoles[i]
            if(this.userRoles[i] =='分包商文控人员')
            this.isSubwk = true
          }
          if(this.userRoles.length>1 ){
            this.isOnlySub  =true
        
          }
          if(this.tempJKJH.length==2)
          this.isSubjkjh=true
         console.log(this.isSubjkjh+"/jkjh")
          
        console.log(this.userRoles)
      },
    loadStatistic() {
      let _self = this;
      let mp = new Map();
       mp.set('projectName','@project');
      axios
        .post("/dc/getSubIcm", JSON.stringify(mp))
        .then(function (response) {
          _self.projectDataICM.data[0].count = response.data.projectNum;
          _self.projectData2.data[0].count = response.data.planNum;
          _self.projectData3.data[0].count = response.data.thereplanNum;
          _self.projectData4.data[0].count = response.data.iedNum;
          _self.projectData5.data[0].count = response.data.icmNum;
          _self.$refs.p1.refresh()
          _self.$refs.p2.refresh()
          _self.$refs.p3.refresh()
          _self.$refs.p4.refresh()
          _self.$refs.p5.refresh()
          console.log(response.data)
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    loadStatisticWK(){
      let _self = this;
      let mp=new Map();
       mp.set('projectName','@project');
      axios
        axios.post("/exchange/docTop/docSumNum",JSON.stringify(mp))
        .then(function (response) {
          if(response.data.code==1){
            console.log(response.data)
              _self.projectData.data[0].count=response.data.sumNum;
              _self.projectDataDCnum.data[0].count=response.data.dcNum;
              _self.projectDataReceivingdc.data[0].count=response.data.receivedNum;
              _self.projectDataSubmissiondc.data[0].count=response.data.submissiondcNum;
              _self.projectDataDispensedc.data[0].count=response.data.dispenseNum;
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
        mp.set('projectName','@project');
        axios.post("/dc/getDCNum",JSON.stringify(mp))
            .then(function(response) {
                
                if(response.data.code==1){
                dataDC = [{
                title: '文函',
                count: response.data.data.num,
                color: 'rgb(63, 161, 255)',
                icon: 'el-icon-s-order',
                url: '/cnpe/iedmanagement/IEDpublished'
              
              }]
            _self.projectDataDC.data = dataDC
            //console.log(_self.projectDataDC.data )
            _self.$refs.dataDC.refresh()//.option=_self.projectData1;
      }
            })

      },
      getPublishedIED(){
        let _self=this;
        let mp=new Map();
        let datas
        mp.set('projectName','@project');
        axios.post("/dc/getSubPublishedIED",JSON.stringify(mp))
            .then(function(response) {
                if(response.data.code==1){
                datas = [{
                title: '已生效IED',
                count: response.data.data.num,
                color: 'rgb(63, 161, 255)',
                icon: 'el-icon-s-claim',
                url: '/cnpe/iedmanagement/IEDpublished'
              }]
            _self.projectDataPublishedIED.data = datas
            console.log(response.data)
            _self.$refs.dataPublishedIED.refresh()
      }
            })
      },
      getPendingSubmitIED(){
         let _self=this;
        let mp=new Map();
        let datas
        mp.set('projectName','@project');
        axios.post("/dc/getPendingSubmitIED",JSON.stringify(mp))
            .then(function(response) {
                if(response.data.code==1){
                datas = [{
                title: '待提交IED',
                count: response.data.data.num,
                color: 'rgb(63, 161, 255)',
                icon: 'el-icon-s-claim',
                url: '/cnpe/iedmanagement/PendingSubmit'
              }]
              console.log(response.data.data)
            _self.projectDataPendingSubmitIED.data = datas
            _self.$refs.dataPendingSubmitIED.refresh()
      }
      })
      },
      getRejectIED(){
         let _self=this;
        let mp=new Map();
        let datas
        mp.set('projectName','@project');
        axios.post("/dc/getRejectIED",JSON.stringify(mp))
            .then(function(response) {
                if(response.data.code==1){
                datas = [{
                title: '已驳回IED',
                count: response.data.data.num,
                color: 'rgb(255,0,0)',
                icon: 'el-icon-s-claim',
                 url:'/cnpe/iedmanagement/IEDRejectied'
              }]
              console.log(response.data.data)
            _self.projectDataRejectIED.data = datas
            _self.$refs.dataRejectIED.refresh()
      }
      })
      },
      getPlanNum(){                 //获得计划数
         let _self=this;
        let mp=new Map();
        let datas
        mp.set('projectName','@project');
        axios.post("/dc/getSubPlanNum",JSON.stringify(mp))
            .then(function(response) {
                if(response.data.code==1){
                datas = [{
                title: '计划数',
                count: response.data.data.num,
                color: 'rgb(63, 161, 255)',
                icon: 'el-icon-s-claim',
              }]
              console.log(response.data.data)
            _self.projectDataPlanNum.data = datas
            _self.$refs.dataPlanNum.refresh()
      }
      })
      },
      getIEDNum(){      //获取IED数量
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
            _self.$refs.dataIED.refresh()
      }
            })
      },
      getTPLANNum(){
        let _self=this;
        let mp=new Map();
        let data
        mp.set('projectName','@project');
        axios.post("/dc/getTPLANNum",JSON.stringify(mp))
            .then(function(response) {
                if(response.data.code==1){
                data = [{
                title: '三级计划',
                count: response.data.data.num,
                color: 'rgb(63, 161, 255)',
                icon: 'el-icon-s-order',
                url: '/cnpe/plan/threelevelplan'
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
        mp.set('projectName','@project');
        axios.post("/dc/getProjectNum",JSON.stringify(mp))
            .then(function(response) {
                if(response.data.code==1){
                data = [{
                title: '计划',
                count: response.data.data.num,
                color: 'rgb(63, 161, 255)',
                icon: 'el-icon-warning',
                url:''
              }]
            _self.projectDataProjectNum.data = data
            _self.$refs.dataProjectNum.refresh()
      }
      })
      },




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