<template>
      <el-container>
        <el-aside width="180px">
          <!--左侧导航-->
          <el-menu default-active="11" class="el-menu-vertical-ecm" :open="10">
            <el-submenu index="10">
              <template slot="title">
                <i class="el-icon-news"></i>
                <span>任务中心</span>
              </template>
              <el-menu-item index="11">
                <i class="el-icon-time"></i>
                <span slot="title">
                  <router-link to="/workflow/todotask">待办工作</router-link>
                  <el-badge :value="todoCount" class="item">
                  </el-badge>
                </span>
              </el-menu-item>
              <el-menu-item index="12">
                <i class="el-icon-circle-check"></i>
                <span slot="title"><router-link to="/workflow/donetask">已办工作</router-link></span>
              </el-menu-item>
              <el-menu-item index="13">
                <i class="el-icon-help"></i>
                <span slot="title"><router-link to="/workflow/myworkflow">我的流程</router-link></span>
              </el-menu-item>
            </el-submenu>
          </el-menu>
        </el-aside>
        <el-main>
          <transition name="fade" mode="out-in">
            <router-view v-on:refreshCount="refreshCount"></router-view>
          </transition>
        </el-main>
      </el-container>
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
    axios.get('/workflow/getMyAllTodoCount')
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
