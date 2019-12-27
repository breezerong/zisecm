<template>
    <table border="0" width="100%">
      <tr>
        <td>
          <el-container>
            <el-aside width="200px">
              <div v-bind:style="{height: menuHeight +'px'}">
              <!--左侧导航-->
              <el-menu default-active="11" class="el-menu-vertical-ecm" :open="11">
                <el-submenu index="10">
                  <template slot="title">
                    <i class="el-icon-news"></i>
                    <span>报表清单</span>
                  </template>
                  <el-menu-item index="11">
                    <i class="el-icon-collection"></i>
                    <span slot="title"><router-link to="/report/collectionreport">馆藏报表</router-link></span>
                  </el-menu-item>
                  <el-menu-item index="12">
                    <i class="el-icon-reading"></i>
                    <span slot="title"><router-link to="/report/workloadreport">工作量报表</router-link></span>
                  </el-menu-item>
                  <el-menu-item index="13">
                    <i class="el-icon-data-analysis"></i>
                    <span slot="title"><router-link to="/report/archivesreport">利用统计报表</router-link></span>
                  </el-menu-item>
                   <el-menu-item index="14">
                    <i class="el-icon-s-marketing"></i>
                    <span slot="title"><router-link to="/report/systemreport">系统报表</router-link></span>
                  </el-menu-item>
                </el-submenu>
              </el-menu>
              </div>
            </el-aside>
            <el-main>
              <transition name="fade" mode="out-in">
                <router-view :key="$route.fullPath"></router-view>
              </transition>
            </el-main>
          </el-container>
    </td>
    </tr>
  </table>
</template>
<script>
export default {
  name: 'ReportCenter',
  data() {
    return {
      clientPermission:0,
      username:'',
      menuHeight: window.innerHeight -70
    };
  },
  mounted()
  {
    let _self = this;
    var user = sessionStorage.getItem('access-user');
    if(user)
    {
      _self.clientPermission = sessionStorage.getItem('access-clientPermission');
    }
    _self.$router.push({path: "/report/collectionreport"});
  },
  methods: {
  
  }
}
</script>

<style>
  body > .el-container {
    padding: 0px;
    margin-top: 0px;
    margin-left: 0px;
    margin-right: 0px;
    margin-bottom: 0px;
  }
</style>
