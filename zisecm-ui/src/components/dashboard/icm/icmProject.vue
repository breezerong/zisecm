<template>
 <div>
    <DataSelect @onSelectChange='onSelectChange' v-model="filters.projectCode" :includeAll="true" dataUrl="/exchange/project/myproject" 
                    dataValueField="name" dataTextField="name"></DataSelect>
    <ecm-data-icons :option="projectData"></ecm-data-icons>
 </div>
</template>
<script type="text/javascript">
import ecmDataIcons from '@/components/ecm-data-icons/ecm-data-icons'
import DataSelect from '@/components/ecm-data-select'
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
      projectData: {
        color: 'rgb(63, 161, 255)',
        span: 6,
        data: [
          {
            title: this.$t('application.ICM'),
            count: 0,
            color: 'rgb(63, 161, 255)',
            icon: 'el-icon-s-order',
            url: '/cnpe/icmmanagement/interfacemanual'
          },
          {
            title: this.$t('application.FeedbackICM'),
            count: 0,
            color: 'rgb(63, 161, 255)',
            icon: 'el-icon-s-unfold',
            url: '/cnpe/icmmanagement/icmfeedback'
          },
           {
            title: this.$t('application.delayOpenConfirm'),
            count: 0,
            color: 'rgb(63, 161, 255)',
            icon: 'el-icon-s-order',
            url: ''
          },
          {
            title: this.$t('application.delayNum'),
            count: 0,
            color: 'rgb(63, 161, 255)',
            icon: 'el-icon-s-order',
            url: ''
          },
            {
            title: this.$t('application.delayConfirm'),
            count: 0,
            color: 'rgb(63, 161, 255)',
            icon: 'el-icon-s-order',
            url: ''
          },
        ]
      },
      a:[]
    };
  },
  created() {
    let _self = this;
    this.loadStatistic()
  },
  methods: {
    onSelectChange(val){
      this.filters.projectCode = val
      this.loadStatistic()
    },
    loadStatistic(){
      let _self = this;
      let mp=new Map();
       if(_self.filters.projectCode){
              mp.set('projectName',_self.filters.projectCode);
          }else{
              mp.set('projectName','@project');
          }
      axios
        axios.post("/exchange/homeTop/homeSumNum",JSON.stringify(mp))
        .then(function (response) {
          if(response.data.code==1){
            console.log(response.data)
              _self.a[0]=response.data.icmNum;
              _self.a[1]=response.data.feedbackicmNum;
              _self.a[2]=response.data.delayConfirmNum
              _self.a[3]=response.data.delayNum
              _self.a[4]=response.data.delayReplyConfirm
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
    ecmDataIcons:ecmDataIcons,
    DataSelect:DataSelect,
  },
};

</script>