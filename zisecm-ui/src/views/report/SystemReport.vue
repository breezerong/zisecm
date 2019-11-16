<template>
  <div>
    <div class="navbar">/报表中心/系统报表</div>
    <div>
      <el-col :span="12" style="align:left;">
        <div id="systemChart" :style="{height: divHeight, border:'2px solid  #C0C4CC','border-radius': '4px','margin':'5px'}"></div>
      </el-col>
      <el-col :span="12" style="align:left;">
        <div id="userChart" :style="{height: divHeight, border:'2px solid  #C0C4CC','border-radius': '4px','margin':'5px'}"></div>
      </el-col>
      <el-col :span="24" style="align:left;">
        <div id="docChart" :style="{height: divHeight, border:'2px solid  #C0C4CC','border-radius': '4px','margin':'5px'}"></div>
      </el-col>
    </div>  
  </div>
</template>

<script type="text/javascript">
export default {
  name: "SystemReport",
  permit: 2,
  data() {
    return {
      clientPermission: 0,
      systemChartData: {
        xAxisData: [],
        yAxisData: []
      },
      userChartData:  {
        xAxisData: [],
        yAxisData: []
      },
      docChartData: {
        xAxisData: [],
        yAxisData: []
      },
      inputkey: "",
      systemChart:Object,
      userChart:Object,
      docChart:Object,
      divHeight: (window.innerHeight - 120)/2+"px",
    };
  },
  mounted(){ 
    let _self = this;
    _self.systemChart = _self.echarts.init(document.getElementById('systemChart'));
    _self.userChart = _self.echarts.init(document.getElementById('userChart'));
    _self.docChart = _self.echarts.init(document.getElementById('docChart'));
    var user = sessionStorage.getItem('access-user');
    if(user)
    {
      _self.clientPermission = sessionStorage.getItem('access-clientPermission');
    }
    _self.refreshSystemData();
    _self.refreshUserData();
    _self.refreshDocData();
  },
  methods: {
    refreshSystemData() {
      let _self = this;
      _self.loading = true;
      axios.get('/report/systemData')
      .then(function(response) {
        //console.log(JSON.stringify(response.data.data.xAxisData));
        //console.log(JSON.stringify(response.data.data.yAxisData));
        _self.systemChartData = response.data.data;
        // 绘制图表
        _self.systemChart.setOption({
            title: { text: '系统基本情况' },
            tooltip: {},
            xAxis: {
                data: _self.systemChartData.xAxisData
            },
            yAxis: {},
            series: [{
                name: '数量',
                type: 'bar',
                data: _self.systemChartData.yAxisData,
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
    refreshUserData() {
      let _self = this;
      _self.loading = true;
      axios.get('/report/userLoginData')
      .then(function(response) {
        _self.userChartData = response.data.data;
        // 绘制图表
        _self.userChart.setOption({
          title: { text: '用户登录情况' },
          xAxis: {
              type: 'category',
              data: _self.userChartData.xAxisData
          },
          yAxis: {
              type: 'value'
          },
          series: [{
              data: _self.userChartData.yAxisData,
              type: 'line',
              symbol: 'circle',
              symbolSize: 14,
              lineStyle: {
                  normal: {
                      color: function(d){return "#"+Math.floor(Math.random()*(256*256*256-1)).toString(16);},
                      width: 4
                  }
              },
              itemStyle: {
                  normal: {
                      borderWidth: 3,
                      label: {
                        show: true
                      },
                      color: function(d){return "#"+Math.floor(Math.random()*(256*256*256-1)).toString(16);}
                  }
                 
              }
          }]
            /*
            title: { text: '用户登录情况' },
            tooltip: {},
            xAxis: {
                data: _self.userChartData.xAxisData
            },
            yAxis: {},
            series: [{
                name: '数量',
                type: 'bar',
                data: _self.userChartData.yAxisData,
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
            }]*/
        });
        _self.loading = false;
      })
      .catch(function(error) {
        console.log(error);
        _self.loading = false;
      });
    },
    refreshDocData() {
      let _self = this;
      _self.loading = true;
      axios.get('/report/lastYearDocData')
      .then(function(response) {
        //console.log(JSON.stringify(response.data.data.xAxisData));
        //console.log(JSON.stringify(response.data.data.yAxisData));
        _self.docChartData = response.data.data;
        // 绘制图表
        _self.docChart.setOption({
            title: { text: '近一年文件创建情况' },
            tooltip: {},
            xAxis: {
                data: _self.docChartData.xAxisData
            },
            yAxis: {},
            series: [{
                name: '数量',
                type: 'bar',
                data: _self.docChartData.yAxisData,
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
