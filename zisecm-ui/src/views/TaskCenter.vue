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
              <el-menu-item index="11" @click="clickRouter('/workflow/todotask')">
                <i class="el-icon-time"></i>
                <span slot="title" >
                  <router-link to="/workflow/todotask">待办工作</router-link>
                </span>
              </el-menu-item>
              <el-menu-item index="12" @click="clickRouter('/workflow/donetask')">
                <i class="el-icon-circle-check"></i>
                <span slot="title"><router-link to="/workflow/donetask">已办工作</router-link></span>
              </el-menu-item>
              <el-menu-item index="13" @click="clickRouter('/workflow/myworkflow')">
                <i class="el-icon-help"></i>
                <span slot="title"><router-link to="/workflow/myworkflow">我的流程</router-link></span>
              </el-menu-item>
              <el-menu-item index="14" @click="clickRouter('/record/archiveoutgoing/archivegivebackmine')">
                <i class="el-icon-top"></i>
                <span slot="title"><router-link to="/record/archiveoutgoing/archivegivebackmine">待归还</router-link></span>
              </el-menu-item>
              <el-menu-item index="15" @click="clickRouter('/record/showdrawingtask')">
                <i class="el-icon-printer"></i>
                <span slot="title"><router-link to="/record/showdrawingtask">晒图记录</router-link></span>
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
      username:'',
      menuHeight: window.innerHeight -104
    };
  },
   created(){ 
    let _self = this;
     _self.refreshCount();
    },
  methods: {
    refreshCount(){ 
    let _self = this;
        // _self.totalCount = response.data.totalCount;
        // _self.loading = false;


    },
    
      clickRouter(pathVal){
    let _self = this;
    _self.$router.push({ 
      path: pathVal,
    });

  },
  }
}
</script>

<style scoped>
  body > .el-container {
    padding: 0px;
    margin-top: 0px;
    margin-left: 0px;
    margin-right: 0px;
    margin-bottom: 0px;
  }
</style>
