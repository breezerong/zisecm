<template>
  <div>
    <ecm-data-icons :option="projectData"></ecm-data-icons>
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
        span: 4,
        data: [
          {
            title: '项目',
            count:0,
            color: 'rgb(63, 161, 255)',
            icon: 'el-icon-warning',
            url: ''
          },
          {
            title: '文函',
            count: 0,
            color: 'rgb(63, 161, 255)',
            icon: 'el-icon-document',
            url: '/cnpe/DCManagement/receivedDC'
          },
          {
            title: '待接收文函',
            count: 0,
            color: 'rgb(255, 0, 0)',
            icon: 'el-icon-document-checked',
            url: '/cnpe/DCManagement/receivingdc'
          },
          {
            title: '待解锁文函',
            count: 0,
            color: 'rgb(255, 0, 0)',
            icon: 'el-icon-document-checked',
            url: '/cnpe/iedmanagement/IEDpublished'
          },
          {
            title: '待分发文函',
            count: 0,
            color: 'rgb(255, 0, 0)',
            icon: 'el-icon-document-checked',
            url: '/cnpe/iedmanagement/pendingied'
          },
          {
            title: '驳回文函',
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
              _self.a[0]=response.data.sumNum;
              _self.a[1]=response.data.dcNum;
              _self.a[2]=response.data.receivedNum;
              _self.a[3]=response.data.deBlockingNum;
              _self.a[4]=response.data.dispenseNum;
              _self.a[5]=response.data.RejectNum;
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