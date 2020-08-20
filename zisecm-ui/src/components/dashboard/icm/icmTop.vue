<template>
 <div>
   <el-form inline="true">
     <el-row>
       <el-col :span="3">
         <el-form-item><ecm-data-icons :option="projectData1"></ecm-data-icons></el-form-item>
       </el-col>
       <el-col :span="3">
         <el-form-item><ecm-data-icons :option="projectData2"></ecm-data-icons></el-form-item>
       </el-col>
       <el-col :span="3">
         <el-form-item><ecm-data-icons :option="projectData3"></ecm-data-icons></el-form-item>
       </el-col>
       <el-col :span="3">
         <el-form-item><ecm-data-icons :option="projectData4"></ecm-data-icons></el-form-item>
       </el-col>
       <el-col :span="3">
         <el-form-item><ecm-data-icons :option="projectData5"></ecm-data-icons></el-form-item>
       </el-col>
       <el-col :span="3">
         <el-form-item><ecm-data-icons :option="projectData6"></ecm-data-icons></el-form-item>
       </el-col>
       <el-col :span="3">
         <el-form-item><ecm-data-icons :option="projectData7"></ecm-data-icons></el-form-item>
       </el-col>
     </el-row>
   </el-form>
 </div>
</template>

<script type="text/javascript">
import ecmDataIcons from '@/components/ecm-data-icons/ecm-data-icons'
export default {
 name: "planTopDashBoard",
 data() {
    return {  
      projectData1: {
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
      projectData2: {
        data: [
          {
            title: '计划',
            count: 0,
            color: 'rgb(63, 161, 255)',
            icon: 'el-icon-document',
            url: ''
          },
        ]
      },
      projectData3: {
        data: [
          {
            title: '三级计划',
            count: 0,
            color: 'rgb(63, 161, 255)',
            icon: 'el-icon-document-checked',
            url: '/cnpe/DCManagement/receivingdc'
          },
        ]
      },
      projectData4: {
        data: [
          {
            title: '已生效IED',
            count: 0,
            color: 'rgb(63, 161, 255)',
            icon: 'el-icon-document-checked',
            url: '/cnpe/iedmanagement/IEDpublished'
          },
        ]
      },
      projectData5: {
        data: [
          {
            title: '文函',
            count: 0,
            color: 'rgb(63, 161, 255)',
            icon: 'el-icon-document-checked',
            url: '/cnpe/iedmanagement/pendingied'
          },
        ]
      },
      projectData6: {
        data: [
          {
            title: 'ICM',
            count: 0,
            color: 'rgb(63, 161, 255)',
            icon: 'el-icon-document-delete',
            url: ''
          },
        ]
      },
      projectData7: {
        data: [
          {
            title: '反馈ICM',
            count: 0,
            color: 'rgb(255, 0, 0)',
            icon: 'el-icon-document-delete',
            url: ''
          }
        ]
      },
      a:[]
    };
  },
  mounted() {
    this.loadStatistic();
  },

  methods: {
    loadStatistic(){
      let _self = this;
      let mp=new Map();
      axios
        axios.post("/exchange/homeTop/homeSumNum",JSON.stringify(mp))
        .then(function (response) {
          if(response.data.code==1){
            console.log(response.data)
              _self.a[0]=response.data.projectNum;
              _self.a[1]=response.data.planNum;
              _self.a[2]=response.data.thereplanNum;
              _self.a[3]=response.data.iedNum;
              _self.a[4]=response.data.dcNum;
              _self.a[5]=response.data.icmNum;
              _self.a[6]=response.data.feedbackicmNum;
              _self.projectData1.data.forEach(function(item){
                item.count=_self.a[0];
              })
              _self.projectData2.data.forEach(function(item){
                item.count=_self.a[1];
              })
              _self.projectData3.data.forEach(function(item){
                item.count=_self.a[2];
              })
              _self.projectData4.data.forEach(function(item){
                item.count=_self.a[3];
              })
              _self.projectData5.data.forEach(function(item){
                item.count=_self.a[4];
              })
              _self.projectData6.data.forEach(function(item){
                item.count=_self.a[5];
              })
              _self.projectData7.data.forEach(function(item){
                item.count=_self.a[6];
              })
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