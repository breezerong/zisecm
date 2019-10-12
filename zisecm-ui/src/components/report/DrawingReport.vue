<template>
  <div>
    <div class="navbar">/图纸管理/图纸报表</div>
      <div>
      <el-col :span="16" style="align:left">
        <div id="projectChart" :style="{ height: divHeight, border:'2px solid  #C0C4CC','border-radius': '4px','margin':'5px'}"></div>
      </el-col>
      <el-col :span="8" style="align:left;">
        <div id="statusChart" :style="{ height: divHeight, border:'2px solid  #C0C4CC','border-radius': '4px','margin':'5px'}"></div>
      </el-col>
      <el-col :span="24" style="align:left;">
        <div id="dateChart" :style="{ height: divHeight, border:'2px solid  #C0C4CC','border-radius': '4px','margin':'5px'}"></div>
      </el-col>
    </div>  
  </div>
</template>

<script type="text/javascript">
export default {
  name: "DrawingReport",
  permit: 2,
  data() {
    return {
      clientPermission: 0,
      projectChart:Object,
      statusChart:Object,
      dateChart:Object,
      currentProject:"",
      projectChartData: {
        xAxisData: [],
        yAxisData: []
      },
      statusChartData:  {
        xAxisData: [],
        yAxisData: []
      },
      dateChartData: {
        xAxisData: [],
        yAxisData: []
      },
      divHeight: (window.innerHeight - 120)/2+"px"
    };
  },
  mounted(){ 
    let _self = this;
    _self.projectChart = _self.echarts.init(document.getElementById('projectChart'));
    _self.statusChart = _self.echarts.init(document.getElementById('statusChart'));
    _self.dateChart = _self.echarts.init(document.getElementById('dateChart'));
    var user = sessionStorage.getItem('access-user');
    if(user)
    {
      _self.clientPermission = sessionStorage.getItem('access-clientPermission');
    }
    _self.refreshProjectData();
    _self.projectChart.on('click', function (params) {
      _self.currentProject = _self.projectChartData.xAxisData[params.dataIndex];
      _self.refreshStatusData();
      _self.refreshDateData();
      //console.log(_self.projectChartData.xAxisData[params.dataIndex]);
    });
  },
  methods: {
    refreshProjectData() {
      let _self = this;
      _self.loading = true;
      _self.axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        method: 'get',
        url: '/zisecm/report/drawingPorjectData'
      })
      .then(function(response) {
        //console.log(JSON.stringify(response.data.data.xAxisData));
        //console.log(JSON.stringify(response.data.data.yAxisData));
       
        _self.projectChartData = response.data.data;
         if(_self.currentProject ==''&& _self.projectChartData.xAxisData.length>0)
         {
           _self.currentProject = _self.projectChartData.xAxisData[0];
         }
        // 绘制图表
        _self.projectChart.setOption({
            title: { text: '项目文件数据' },
            tooltip: {},
            xAxis: {
                data: _self.projectChartData.xAxisData
            },
            yAxis: {triggerEvent:true},
            series: [{
                name: '数量',
                type: 'bar',
                data: _self.projectChartData.yAxisData,
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
        _self.refreshStatusData();
        _self.refreshDateData();
        _self.loading = false;
      })
      .catch(function(error) {
        console.log(error);
        _self.loading = false;
      });
    },
    refreshStatusData() {
      let _self = this;
      if(_self.currentProject ==''){
        return;
      }
      _self.loading = true;
      _self.axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        method: 'post',
        data: _self.currentProject,
        url: '/zisecm/report/drawingStatusData'
      })
      .then(function(response) {
        //console.log(JSON.stringify(response.data.data.xAxisData));
        //console.log(JSON.stringify(response.data.data.yAxisData));
        _self.statusChartData = response.data.data;
        // 绘制图表
        _self.statusChart.setOption({
            title: { text: '项目文件状态统计',subtext: _self.currentProject},
            tooltip: {},
            xAxis: {
                data: _self.statusChartData.xAxisData
            },
            yAxis: {triggerEvent:true},
            series: [{
                name: '数量',
                type: 'bar',
                data: _self.statusChartData.yAxisData,
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
        
        _self.loading = false;
      })
      .catch(function(error) {
        console.log(error);
        _self.loading = false;
      });
    },
    refreshDateData() {
      let _self = this;
      if(_self.currentProject ==''){
        return;
      }
      
      _self.loading = true;
      _self.axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        method: 'post',
        data: _self.currentProject,
        url: '/zisecm/report/drawingtDateData'
      })
      .then(function(response) {
        //console.log(JSON.stringify(response.data.data.xAxisData));
        //console.log(JSON.stringify(response.data.data.yAxisData));
        _self.dateChartData = response.data.data;
        // 绘制图表
        _self.dateChart.setOption({
            title: { text: '项目文件创建情况统计' ,subtext: _self.currentProject},
            tooltip: {},
            xAxis: {
                data: _self.dateChartData.xAxisData
            },
            yAxis: {triggerEvent:true},
            series: [{
                name: '数量',
                type: 'bar',
                data: _self.dateChartData.yAxisData,
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
        
        _self.loading = false;
      })
      .catch(function(error) {
        console.log(error);
        _self.loading = false;
      });
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1,
h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
