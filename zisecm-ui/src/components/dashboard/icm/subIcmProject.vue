<template>
 <div>
      <DataSelect v-model="filters.projectCode" :includeAll="true" dataUrl="/exchange/project/myproject" 
      dataValueField="name" dataTextField="name" @onSelectChange="onSelectChange"></DataSelect>     
      <el-row>
      <el-col :span="4">
      <ecm-data-icons ref="dataICM" :option="projectDataIcm"></ecm-data-icons>
      <ecm-data-icons ref="dataDC" :option="projectDataDC"></ecm-data-icons>
      </el-col>
      </el-row> 
     </div>
 </div>
</template>


<script type="text/javascript">
import ecmDataIcons from '@/components/ecm-data-icons/ecm-data-icons'
import DataSelect from '@/components/ecm-data-select';
export default {
  components: {
  DataSelect:DataSelect,
  ecmDataIcons,
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
      projectDataDC: {
        color: 'rgb(63, 161, 255)',
        span: 6,
        data:[{title: '三级计划',
                count: 11,
                color: 'rgb(63, 161, 255)',
                icon: 'el-icon-s-unfold',
                url: '/ied/releaseied'}],
      },
      projectDataIcm: {
        color: 'rgb(63, 161, 255)',
        span: 6,
        data:[{title: '三级计划',
                count: 11,
                color: 'rgb(63, 161, 255)',
                icon: 'el-icon-s-unfold',
                url: '/ied/releaseied'}],
      },
    };
  },
  created() {
    let _self = this;
    this.getDCNum();
    this.getIcmNum();
  },
  methods: {
    onSelectChange(val){
    let _self = this
    this.filters.projectCode = val
    _self.getIcmNum()
    _self.getDCNum()
      this.initChart()
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
                title: '文函',
                count: response.data.data.num,
                color: 'rgb(63, 161, 255)',
                icon: 'el-icon-s-order',
                url: '/cnpe/iedmanagement/IEDpublished'
              }]
            _self.projectDataDC.data = dataDC
            _self.$refs.dataDC.refresh()//.option=_self.projectData1;
      }
            })

      },
      getIcmNum(){         //获取文函数量
        let _self=this;
        let mp=new Map();
        let dataDC
        if(_self.filters.projectCode){
              mp.set('projectName',_self.filters.projectCode);
          }else{
              mp.set('projectName','@project');
          }
          console.log(mp)
        axios.post("/dc/getIcmNum",JSON.stringify(mp))
            .then(function(response) {
                if(response.data.code==1){
                dataDC = [{
                title: 'ICM',
                count: response.data.data.num,
                color: 'rgb(63, 161, 255)',
                icon: 'el-icon-s-order',
                url: '/cnpe/iedmanagement/IEDpublished'
              }]
            _self.projectDataIcm.data = dataDC
            _self.$refs.dataICM.refresh()//.option=_self.projectData1;
                  }
                  })

      }


  },
  
};

</script>