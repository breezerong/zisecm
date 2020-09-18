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
            title: '项目',
            count:0,
            color: 'rgb(63, 161, 255)',
            icon: 'el-icon-warning',
            url: ''
          },
          {
            title: '计划',
            count: 0,
            color: 'rgb(63, 161, 255)',
            icon: 'el-icon-document',
            url: ''
          },
          {
            title: '计划作业',
            count: 0,
            color: 'rgb(63, 161, 255)',
            icon: 'el-icon-document-checked',
            url: '/cnpe/MoreViewerBrowe/projectviewer'
          },
          {
            title: 'IED',
            count: 0,
            color: 'rgb(63, 161, 255)',
            icon: 'el-icon-document-checked',
            url: '/cnpe/MoreViewerBrowe/projectviewer'
          },
          {
            title: 'ICM',
            count: 0,
            color: 'rgb(63, 161, 255)',
            icon: 'el-icon-document-delete',
            url: '/cnpe/MoreViewerBrowe/projectviewer'
          },
          {
            title: '文函',
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
          console.log(response)
          if(response.data.code==1){
            console.log(response.data)
              _self.a[0]=response.data.projectNum;
              _self.a[1]=response.data.complanNum;
              _self.a[2]=response.data.comthereplanNum;
              _self.a[3]=response.data.comiedNum;
              _self.a[4]=response.data.comicmNum;
              _self.a[5]=response.data.comdcNum;
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
  },
  
};

</script>