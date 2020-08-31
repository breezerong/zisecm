<template>
 <div>
    <ecm-data-icons :option="projectData"></ecm-data-icons>
 </div>
</template>


<script type="text/javascript">
import ecmDataIcons from '@/components/ecm-data-icons/ecm-data-icons'
export default {
 name: "generalTopDashBoard",
 data() {
    return {  
      projectData: {
        color: 'rgb(63, 161, 255)',
        span: 4,
        data: [
          {
            title: this.$t('application.Projects'),
            count:0,
            color: 'rgb(63, 161, 255)',
            icon: 'el-icon-warning',
            url: ''
          },
          {
            title: this.$t('application.Plannum'),
            count: 0,
            color: 'rgb(63, 161, 255)',
            icon: 'el-icon-document',
            url: '/cnpe/DCManagement/receivedDC'
          },
          {
            title: this.$t('application.TPlan'),
            count: 0,
            color: 'rgb(63, 161, 255)',
            icon: 'el-icon-document-checked',
            url: '/cnpe/MoreViewerBrowe/projectviewer'
          },
          {
            title: this.$t('application.IEDPublished'),
            count: 0,
            color: 'rgb(63, 161, 255)',
            icon: 'el-icon-document-checked',
            url: '/cnpe/MoreViewerBrowe/projectviewer'
          },
          {
            title: this.$t('application.ICM'),
            count: 0,
            color: 'rgb(63, 161, 255)',
            icon: 'el-icon-document-delete',
            url: '/cnpe/MoreViewerBrowe/projectviewer'
          },
          {
            title: this.$t('application.Document'),
            count: 0,
            color: 'rgb(63, 161, 255)',
            icon: 'el-icon-document-checked',
            url: '/cnpe/MoreViewerBrowe/projectviewer'
          },
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
              _self.a[0]=response.data.projectNum;
              _self.a[1]=response.data.planNum;
              _self.a[2]=response.data.thereplanNum;
              _self.a[3]=response.data.iedNum;
              _self.a[4]=response.data.icmNum;
              _self.a[5]=response.data.dcNum;
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
  },
  
  components: {
    ecmDataIcons:ecmDataIcons
  },
};

</script>