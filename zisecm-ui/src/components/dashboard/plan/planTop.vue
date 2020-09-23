<template>
     <div>  
        <el-form inline="true" >
        <el-row>
          <el-col :span="6" v-if="isOnlyCNPE || isOnlyCNPEjh">
            <el-form-item ><ecm-data-icons ref="dataProjectNum" :option="projectDataProjectNum"></ecm-data-icons></el-form-item>
          </el-col>
          <el-col :span="6" v-if="isOnlyCNPEjh">
          <el-form-item><ecm-data-icons  ref="dataPlanNum" :option="projectDataPlanNum"></ecm-data-icons></el-form-item>
          </el-col>
          <el-col :span="6" v-if="isOnlyCNPEjh">
          <el-form-item><ecm-data-icons ref="dataTPLAN" :option="projectDataTPLAN"></ecm-data-icons></el-form-item>
          </el-col>
          <el-col :span="6" v-if="isOnlyCNPEjh">
          <el-form-item><ecm-data-icons  ref="dataPublishedIED" :option="projectDataPublishedIED"></ecm-data-icons></el-form-item>
          </el-col>
          <el-col :span="6" v-if="isCNPEjh">
          <el-form-item><ecm-data-icons  ref="dataPendingIED" :option="projectDataPendingIED"></ecm-data-icons></el-form-item>
          </el-col>
          <el-col :span="6" v-if="isOnlyCNPEjk">
          <el-form-item>
          <ecm-data-icons ref="p1" :option="projectData1" ></ecm-data-icons>
          </el-form-item>
          </el-col>
          <el-col :span="6" v-if="isOnlyCNPEjk">
          <el-form-item>
            <ecm-data-icons ref="p2" :option="projectData2" ></ecm-data-icons>
          </el-form-item>
        </el-col>
        <el-col :span="6" v-if="isOnlyCNPEjk">
        <el-form-item>
          <ecm-data-icons ref="p3" :option="projectData3" v-if="isOnlyCNPEjk"></ecm-data-icons>
        </el-form-item>
        </el-col>
        <el-col :span="6"v-if="isCNPEjk||isCNPEjkjh">
        <el-form-item>
          <ecm-data-icons ref="p4" :option="projectData4" v-if="isCNPEjk"></ecm-data-icons>
        </el-form-item>
        </el-col>
        <el-col  :span="6"v-if="isCNPEjk||isCNPEjkjh">
        <el-form-item>
          <ecm-data-icons ref="p5" :option="projectData5" v-if="isCNPEjk"></ecm-data-icons>
        </el-form-item>
        </el-col>
        <el-col :span="6"v-if="isCNPEjk||isCNPEjkjh">
          <el-form-item>
            <ecm-data-icons ref="p6" :option="projectData6" v-if="isCNPEjk"></ecm-data-icons>
          </el-form-item>
        </el-col>
        <el-col :span="6"v-if="isCNPEjk">
      <el-form-item>
      <ecm-data-icons ref="p7" :option="projectData7" v-if="isCNPEjk"></ecm-data-icons>
      </el-form-item>
      </el-col>
      <el-col :span="6" ref="p8" v-if="isCNPEjk">
      <el-form-item><ecm-data-icons :option="projectDataDCC"></ecm-data-icons></el-form-item>
      </el-col>
      <el-col :span="6" ref="p9" v-if="isCNPEjk">
      <el-form-item><ecm-data-icons :option="projectDataDelayOpenConfirm"></ecm-data-icons></el-form-item>
      </el-col>
       <el-col :span="6" ref="p10" v-if="isCNPEjk">
      <el-form-item><ecm-data-icons :option="projectDataDelayNum"></ecm-data-icons></el-form-item>
      </el-col>
       <el-col :span="6" ref="p11" v-if="isCNPEjk">
      <el-form-item><ecm-data-icons :option="projectDataDelayConfirm"></ecm-data-icons></el-form-item>
      </el-col>


      <el-col :span="6" ref="wk1" v-if="isOnlyCNPEwk">
      <el-form-item><ecm-data-icons :option="projectData"></ecm-data-icons></el-form-item>
      </el-col>
      <el-col :span="6" ref="wk2" v-if="isCNPEwk">
      <el-form-item><ecm-data-icons :option="projectDataWKDC"></ecm-data-icons></el-form-item>
      </el-col>
      <el-col :span="6" ref="wk3" v-if="isCNPEwk">
      <el-form-item><ecm-data-icons :option="projectDataReceived"></ecm-data-icons></el-form-item>
      </el-col>
      <el-col :span="6" ref="wk4" v-if="isCNPEwk">
      <el-form-item><ecm-data-icons :option="projectDataDeBlocking"></ecm-data-icons></el-form-item>
      </el-col>
      <el-col :span="6" ref="wk5" v-if="isCNPEwk">
      <el-form-item><ecm-data-icons :option="projectDataDispense"></ecm-data-icons></el-form-item>
      </el-col>
      <el-col :span="6" ref="wk6" v-if="isCNPEwk">
      <el-form-item><ecm-data-icons :option="projectDataReject"></ecm-data-icons></el-form-item>
      </el-col>
      <el-col :span="6" ref="wk7" v-if="isCNPEwk">
      <el-form-item><ecm-data-icons :option="projectDataMyApplyReject"></ecm-data-icons></el-form-item>
      </el-col>
      <el-col :span="6" ref="wk8" v-if="isCNPEwk">
      <el-form-item><ecm-data-icons :option="projectDataApplyReject"></ecm-data-icons></el-form-item>
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
      isOnlyCNPEjk:false,
      isCNPEjk:false,
      isOnlyCNPE:false,
      isCNPEjh:false,
      isCNPEwk:false,
      isOnlyCNPEjh:false,
      isCNPEjkjh:false,
      isOnlyCNPEwk:false,
      CNPEjkjh:[],
      tempRoles:[],
      userRoles:[],

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
      divWidth: '500px',
      divHeight: '500px',
      dataReport: [],
      count:'',

      projectDataIED: {
        color: 'rgb(63, 161, 255)',
        span: 6,
        data:[{title: '计划作业',
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
        data:[{title: '计划作业',
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
                icon: 'el-icon-s-flag',}],
        },
        projectDataRejectIED:{
        color: 'rgb(63, 161, 255)',
        span: 6,
        data:[{title: '计划数',
                count: 11,
                color: 'rgb(63, 161, 255)',
                icon: 'el-icon-s-flag',}],
        },
        projectDataPlanNum:{
        color: 'rgb(63, 161, 255)',
        span: 6,
        data:[{title: '计划数',
                count: 11,
                color: 'rgb(63, 161, 255)',
                icon: 'el-icon-s-flag',}],
        },
        projectDataPendingIED:{
        color: 'rgb(63, 161, 255)',
        span: 6,
        data:[{title: '计划数',
                count: 11,
                color: 'rgb(63, 161, 255)',
                icon: 'el-icon-s-flag',}],
        },
        projectData1: {
        data: [
          {
            title: this.$t('application.Projects'),
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
            title: this.$t('application.Plannum'),
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
            title: this.$t('application.TPlan'),
            count: 0,
            color: "rgb(63, 161, 255)",
            icon: "el-icon-document-checked",
            url: "/cnpe/MoreViewerBrowe/projectviewer",
          },
        ],
      },
      projectData4: {
        data: [
          {
            title: this.$t('application.IEDPublished'),
            count: 0,
            color: "rgb(63, 161, 255)",
            icon: "el-icon-document-checked",
            url: "/cnpe/MoreViewerBrowe/projectviewer",
          },
        ],
      },
      projectData5: {
        data: [
          {
            title: this.$t("application.Document"),
            count: 0,
            color: "rgb(63, 161, 255)",
            icon: "el-icon-document-checked",
            url: "/cnpe/MoreViewerBrowe/projectviewer",
          },
        ],
      },
      projectData6: {
        data: [
          {
            title: this.$t('application.ICM'),
            count: 0,
            color: "rgb(63, 161, 255)",
            icon: "el-icon-document-delete",
            url: "/cnpe/icmmanagement/interfacemanual",
          },
        ],
      },
      projectData7: {
        data: [
          {
            title: this.$t('application.FeedbackICM'),
            count: 0,
            color: "rgb(255, 0, 0)",
            icon: "el-icon-document-delete",
            url: "/cnpe/icmmanagement/icmfeedback",
          },
        ],
      },
      projectDataDelayNum: {
        data: [
          {
            
            title: this.$t('application.delayNum'),
            count: 0,
            color: 'rgb(63, 161, 255)',
            icon: 'el-icon-s-order',
            url: ''
          }
          
        ],
      },
  
 

        projectDataDelayConfirm: {
        data: [
          {
              title: this.$t('application.delayConfirm'),
            count: 0,
             color: 'rgb(255, 0, 0)',
            icon: 'el-icon-s-order',
            url: '/cnpe/icmmanagement/delayconfirm'
          },
        ]
      },


      projectData: {
        color: 'rgb(63, 161, 255)',
        span: 24,
        data: [
          {
            title: this.$t('application.Projects'),
            count:0,
            color: 'rgb(63, 161, 255)',
            icon: 'el-icon-warning',
            url: ''
          },
        ]
      },
      projectDataWKDC: {
        color: 'rgb(63, 161, 255)',
        span: 24,
        data: [
          {
            title: this.$t('application.Document'),
            count: 0,
            color: 'rgb(63, 161, 255)',
            icon: 'el-icon-document',
            url: '/cnpe/DCManagement/receivedDC'
          },
        ]
      },
      projectDataReceived: {
        color: 'rgb(63, 161, 255)',
        span: 24,
        data: [
          {
            title: this.$t('application.ReceivedDC'),
            count: 0,
            color: 'rgb(255, 0, 0)',
            icon: 'el-icon-document-checked',
            url: '/cnpe/DCManagement/receivingdc'
          },
        ]
      },
      projectDataDeBlocking: {
        color: 'rgb(63, 161, 255)',
        span: 24,
        data: [
          {
            title: this.$t('application.DeblockingDC'),
            count: 0,
            color: 'rgb(255, 0, 0)',
            icon: 'el-icon-document-checked',
            url: '/cnpe/DCManagement/deblockingdc'
          },
        ]
      },
      projectDataDispense: {
        color: 'rgb(63, 161, 255)',
        span: 24,
        data: [
          {
            title: this.$t('application.ToDistributedDC'),
            count: 0,
            color: 'rgb(255, 0, 0)',
            icon: 'el-icon-document-checked',
            url: '/cnpe/DCManagement/dispenseDc'
          },
        ]
      },
      projectDataReject: {
        color: 'rgb(63, 161, 255)',
        span: 24,
        data: [
          {
            title: this.$t('application.RejectedDC'),
            count: 0,
            color: 'rgb(255, 0, 0)',
            icon: 'el-icon-document-delete',
            url: 'cnpe/DCManagement/Reject4Cnpe'
          }
        ]
      },
    projectDataDCC: {
        color: 'rgb(63, 161, 255)',
        span: 24,
        data: [
          {
            title: this.$t('route.delayCloseConfirm'),
            count: 0,
            color: 'rgb(255, 0, 0)',
            icon: 'el-icon-document-delete',
            url: 'cnpe/icmmanagement/delaycloseconfirm'
          }
        ]
      },
      projectDataDelayOpenConfirm: {
        color: 'rgb(63, 161, 255)',
        span: 24,
        data: [
              {
            title: this.$t('application.delayOpenConfirm'),
            count: 0,
            color: 'rgb(255, 0,0)',
            icon: 'el-icon-s-order',
            url: '/cnpe/icmmanagement/icmfeedback'
          },
        ]
      },
      projectDataMyApplyReject: {
        color: 'rgb(63, 161, 255)',
        span: 24,
        data: [
              {
            title: this.$t('route.MyApplyReject'),
            count: 0,
            color: 'rgb(63, 161, 255)',
            icon: 'el-icon-s-order',
            url: '/cnpe/DCManagement/myApplyReject'
          },
        ]
      },
      projectDataApplyReject: {
        color: 'rgb(63, 161, 255)',
        span: 24,
        data: [
              {
            title: this.$t('route.applyReject'),
            count: 0,
            color: 'rgb(63, 161, 255)',
            icon: 'el-icon-s-order',
            url: '/cnpe/DCManagement/applyRejectCnpe'
          },
        ]
      }
    };
  },

  mounted() {
     let _self = this;
    //_self.docChart1 = _self.echarts.init(document.getElementById('docChart1'));
    //_self.initChart();
    _self.getIEDNum()
    _self.getDCNum()
    _self.getTPLANNum()
    _self.getProjectNum()
    _self.getPublishedIED()
    _self.getPendingSubmitIED()
    _self.getRejectIED()
    _self.getPlanNum()
    _self.getPendingIED()
    this.loadStatistic()
    this.loadStatisticWK()
    this.getUserRole()
    this.language = localStorage.getItem("localeLanguage") || "zh-cn";
  },

  methods: {
    onSelectChange(val){
      this.filters.projectCode = val
      this.initChart()
    },
     getUserRole(){    //获取文件类型，进行本地验证
        var k = 0;
        var s = 0;
        this.tempRoles=this.currentUser().roles
        for(var i = 0;i < this.tempRoles.length;i++){
          if(this.tempRoles[i] == '分包商文控人员'||this.tempRoles[i] =='CNPE_文控人员'||this.tempRoles[i] =='CNPE_计划人员'||
          this.tempRoles[i] =='CNPE_接口人员'||this.tempRoles[i] =='分包商接口人员'||this.tempRoles[i] =='分包商计划人员'){
          this.userRoles[k] = this.tempRoles[i]
          k++; 
          }
        }
        if(this.userRoles.length==1 && this.userRoles[0]=='CNPE_接口人员'){
          this.isOnlyCNPEjk=true
          this.isCNPEjk=true
          }
        if(this.userRoles.length==1 && this.userRoles[0]=='CNPE_计划人员'){
          this.isOnlyCNPEjh=true
          this.isCNPEjh=true
          }
          if(this.userRoles.length==1 && this.userRoles[0]=='CNPE_文控人员'){
          this.isOnlyCNPEwk=true
          this.isCNPEwk=true
          }
          for(var i = 0;i < this.userRoles.length;i++){
            if(this.userRoles[i] == 'CNPE_计划人员')
            this.isCNPEjh = true
            this.CNPEjkjh[i]=='CNPE_计划人员'
            if(this.userRoles[i] =='CNPE_接口人员')
            this.isCNPEjk = true
            this.CNPEjkjh[i]=='CNPE_接口人员'
            if(this.userRoles[i] =='CNPE_文控人员')
            this.isCNPEwk = true
          }
          if(this.userRoles.length>1 ){
            this.isOnlyCNPE =true
          }
          if(this.CNPEjkjh.length==2){
            this.isCNPEjkjh=true
          }
         console.log(this.userRoles)
      },
      loadStatisticWK(){
      let _self = this;
      let mp=new Map()
      axios
        axios.post("/exchange/docTop/docSumNum",JSON.stringify(mp))
        .then(function (response) {
          if(response.data.code==1){
            console.log(response.data)
              _self.projectData.data[0].count=response.data.sumNum;
              _self.projectDataWKDC.data[0].count=response.data.dcNum;
              _self.projectDataReceived.data[0].count=response.data.receivedNum;
              _self.projectDataDeBlocking.data[0].count=response.data.deBlockingNum;
              _self.projectDataDispense.data[0].count=response.data.dispenseNum;
              _self.projectDataReject.data[0].count=response.data.RejectNum;
              _self.projectDataApplyReject.data[0].count=response.data.ApplyReject;
              _self.projectDataMyApplyReject.data[0].count=response.data.MyApplyReject;
          }
          
        })
        .catch(function (error) {
          console.log(error);
        });
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
      getDCNum(){         //获取文函数量
        let _self=this;
        let mp=new Map();
        let dataDC
        mp.set('projectName','@project');
        axios.post("/dc/getDCNum",JSON.stringify(mp))
            .then(function(response) {
                if(response.data.code==1){
                dataDC = [{
                title: _self.$t('application.DC'),
                count: response.data.data.num,
                color: 'rgb(63, 161, 255)',
                icon: 'el-icon-s-order',
                url: 'cnpe/MoreViewerBrowe/projectviewer'
              }]
            _self.projectDataDC.data = dataDC
            _self.$refs.dataDC.refresh()//.option=_self.projectData1;
      }
            })

      },
      getPublishedIED(){
        let _self=this;
        let mp=new Map();
        let datas
        mp.set('projectName','@project');
        axios.post("/dc/getCNPEIEDNum",JSON.stringify(mp))
            .then(function(response) {
                if(response.data.code==1){
                datas = [{
                title: _self.$t('application.IEDPublished'),
                count: response.data.data.num,
                color: 'rgb(63, 161, 255)',
                icon: 'el-icon-s-claim',
                url: '/cnpe/iedmanagement/IEDpublished'
              }]
            _self.projectDataPublishedIED.data = datas
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
                title: _self.$t('application.PendingSubmit'),
                count: response.data.data.num,
                color: 'rgb(63, 161, 255)',
                icon: 'el-icon-s-claim',
                url: '/cnpe/iedmanagement/PendingSubmit'
              }]
            _self.projectDataPendingSubmitIED.data = datas
            _self.$refs.dataPendingSubmitIED.refresh()
      }
      })
      },
       getPendingIED(){
         let _self=this;
        let mp=new Map();
        let datas
        mp.set('projectName','@project');
        axios.post("/dc/getPendingIED",JSON.stringify(mp))
            .then(function(response) {
                if(response.data.code==1){
                datas = [{
                title: _self.$t('application.PendingIED'),
                count: response.data.data.num,
                color: 'rgb(255, 0, 0)',
                icon: 'el-icon-s-claim',
                url: '/cnpe/iedmanagement/PendingIED'
              }]
            _self.projectDataPendingIED.data = datas
            _self.$refs.dataPendingIED.refresh()
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
                title: _self.$t('application.IEDRejected'),
                count: response.data.data.num,
                color: 'rgb(255,0,0)',
                icon: 'el-icon-s-claim',
                url: '/cnpe/iedmanagement/RejectIED'
              }]
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
        axios.post("/dc/getPlanNum",JSON.stringify(mp))
            .then(function(response) {
                if(response.data.code==1){
                datas = [{
                title: _self.$t('application.Plannum'),
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
        axios.post("/dc/getCNPEIEDNum",JSON.stringify(mp))
            .then(function(response) {
                if(response.data.code==1){
                datas = [{
                title: _self.$t('application.IEDPublished'),
                count: response.data.data.num,
                color: 'rgb(63, 161, 255)',
                icon: 'el-icon-s-unfold',
                url: '/cnpe/iedmanagement/IEDpublished'
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
        mp.set('projectName','@project');
        axios.post("/dc/getCNPETPLANNum",JSON.stringify(mp))
            .then(function(response) {
                if(response.data.code==1){
                data = [{
                title: _self.$t('application.TPlan'),
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
      loadStatistic() {
      let _self = this;
      let mp = new Map();
      axios;
      axios
        .post("/exchange/homeTop/homeSumNum", JSON.stringify(mp))
        .then(function (response) {
          _self.projectData1.data[0].count = response.data.projectNum;
          _self.projectData2.data[0].count = response.data.planNum;
          _self.projectData3.data[0].count = response.data.thereplanNum;
          _self.projectData4.data[0].count = response.data.iedNum;
          _self.projectData5.data[0].count = response.data.dcNum;
          _self.projectData6.data[0].count = response.data.icmNum;
          _self.projectData7.data[0].count = response.data.feedbackicmNum;
          _self.projectDataDelayOpenConfirm.data[0].count=response.data.delayCloseNum
          _self.projectDataDCC.data[0].count=response.data.delayCloseNum;
          _self.projectDataDelayConfirm.data[0].count=response.data.delayReplyConfirm
          _self.projectDataDelayNum.data[0].count=response.data.delayNum
          
          _self.$refs.p1.refresh()
          _self.$refs.p2.refresh()
          _self.$refs.p3.refresh()
          _self.$refs.p4.refresh()
          _self.$refs.p5.refresh()
          _self.$refs.p6.refresh()
          _self.$refs.p7.refresh()
        })
        .catch(function (error) {
          console.log(error);
        });
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