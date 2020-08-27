<template>
 <div>
    <el-row>
      <el-col :span="4">
        <ecm-data-icons :option="projectData"></ecm-data-icons>
      </el-col>
      <el-col :span="4">
        <ecm-data-icons :option="projectDataDC"></ecm-data-icons>
      </el-col>
      <el-col :span="4">
        <ecm-data-icons :option="projectDataReceivingdc"></ecm-data-icons>
      </el-col>
      <el-col :span="4">
        <ecm-data-icons :option="projectDataSubmissiondc"></ecm-data-icons>
      </el-col>
      <el-col :span="4">
        <ecm-data-icons :option="projectDataDispensedc"></ecm-data-icons>
      </el-col>
    </el-row>
    
 </div>
</template>
<script type="text/javascript">
import ecmDataIcons from '@/components/ecm-data-icons/ecm-data-icons'
export default {
 name: "planTopDashBoard",
 data() {
    return {  
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
      projectDataDC: {
        color: 'rgb(63, 161, 255)',
        span: 24,
        data: [
          {
            title: '文函',
            count: 0,
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
            url: '/cnpe/iedmanagement/pendingied'
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
            url: ''
          }
        ]
      },
    };
  },
  mounted() {
    let _self = this;
    this.loadStatistic();
    
  },
 
  methods: {
    loadStatistic(){
      let _self = this;
      let mp=new Map();
      axios
        axios.post("/exchange/docTop/docSumNum",JSON.stringify(mp))
        .then(function (response) {
          if(response.data.code==1){
            console.log(response.data)
              _self.projectData.data[0].count=response.data.sumNum;
              _self.projectDataDC.data[0].count=response.data.dcNum;
              _self.projectDataReceivingdc.data[0].count=response.data.receivedNum;
              _self.projectDataSubmissiondc.data[0].count=response.data.submissiondcNum;
              _self.projectDataDispensedc.data[0].count=response.data.dispenseNum;
          }
          
        })
        .catch(function (error) {
          console.log(error);
        });
    },
  },
  components: {
    ecmDataIcons:ecmDataIcons
  },
  
};

</script>