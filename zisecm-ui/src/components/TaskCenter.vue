<template>
    <table border="0" width="100%">
      <tr>
        <td>
      <el-breadcrumb>
        <el-breadcrumb-item>任务中心</el-breadcrumb-item>
      </el-breadcrumb>
        </td>
      </tr>
      <tr>
        <td>
      <el-container>
        <el-aside width="180px">
          <div v-bind:style="{height: menuHeight +'px'}">
          <!--左侧导航-->
          <el-menu default-active="11" :open="10">
            <el-submenu index="10">
              <template slot="title">
                <i class="el-icon-news"></i>
                <span>任务中心</span>
              </template>
              <el-menu-item index="11">
                <i class="el-icon-time"></i>
                <span slot="title">
                  <el-badge :value="todoCount" class="item">
                    <router-link to="/taskcenter/todotask">待办工作</router-link>
                  </el-badge>
                </span>
              </el-menu-item>
              <el-menu-item index="12">
                <i class="el-icon-ecm-roundcheck"></i>
                <span slot="title"><router-link to="/taskcenter/donetask">已办工作</router-link></span>
              </el-menu-item>
              <el-menu-item index="13">
                <i class="el-icon-ecm-round_transfer"></i>
                <span slot="title"><router-link to="/taskcenter/myworkflow">我的流程</router-link></span>
              </el-menu-item>
              <el-menu-item index="14">
                <i class="el-icon-ecm-round_transfer"></i>
                <span slot="title"><router-link to="/taskcenter/startworkflow">发起流程</router-link></span>
              </el-menu-item>
            </el-submenu>
          </el-menu>
          </div>
        </el-aside>
        <el-main>
          <transition name="fade" mode="out-in">
            <router-view v-on:refreshCount="refreshCount"></router-view>
          </transition>
        </el-main>
      </el-container>
        </td>
      </tr>
    </table>
</template>
<script>
export default {
  name: 'TaskCenter',
  data() {
    return {
      todoCount:0,
      username:'',
      menuHeight: window.innerHeight -64
    };
  },
   created(){ 
    let _self = this;
    _self.refreshCount();
    },
  methods: {
    refreshCount(){ 
    let _self = this;
    _self.loading = true;
    _self.axios({
      headers: {
        "Content-Type": "application/json;charset=UTF-8"
      },
      method: 'get',
      url: '/zisecm/workflow/getMyAllTodoCount'
    })
      .then(function(response) {
        _self.todoCount = response.data.data;
        _self.loading = false;
      })
      .catch(function(error) {
        console.log(error);
        _self.loading = false;
      });

    }
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
