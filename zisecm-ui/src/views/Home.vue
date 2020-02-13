<template>
  <el-container>
    <el-container>
      <el-aside width="180px" style="overflow-x:hidden;overflow-y:hidden">
        <div v-bind:style="{height: menuHeight +'px'}">
          <!--左侧导航-->
          <el-menu  class="el-menu-vertical-ecm" :default-openeds="opens" style="background-color:#D3DCE6">
            <el-submenu index="10">
              <template slot="title">
                <i class="el-icon-news"></i>
                <span  style="font-size:16px">任务中心</span>
              </template>
              <el-menu-item index="11" @click="clickRouter('/workflow/todotask')" >
                <i class="el-icon-time"></i>
                <span slot="title">
                  <router-link to="/workflow/todotask">待办工作</router-link>
                  <!-- <el-badge :value="todoCount" class="item"></el-badge> -->
                </span>
              </el-menu-item>
              <el-menu-item index="12"  @click="clickRouter('/workflow/donetask')">
                <i class="el-icon-circle-check"></i>
                <span slot="title">
                  <router-link to="/workflow/donetask">已办工作</router-link>
                </span>
              </el-menu-item>
              <el-menu-item index="13"  @click="clickRouter('/workflow/myworkflow')">
                <i class="el-icon-help"></i>
                <span slot="title">
                  <router-link to="/workflow/myworkflow">我的流程</router-link>
                </span>
              </el-menu-item>
              <el-menu-item index="14"  @click="clickRouter('/record/archiveoutgoing/archivegivebackmine')">
                <i class="el-icon-top"></i>
                <span slot="title">
                  <router-link to="/record/archiveoutgoing/archivegivebackmine">待归还</router-link>
                  <!-- <el-badge :value="todoCount" class="item"></el-badge> -->
                </span>
              </el-menu-item>
              <el-menu-item index="15" @click="clickRouter('/dc/folderviewer?folderId=c27feb8346de4dc094827ff62ab3259a')">
                <i class="el-icon-setting"></i>
                <span slot="title"><router-link to="/dc/folderviewer?folderId=c27feb8346de4dc094827ff62ab3259a">模板配置</router-link></span>
              </el-menu-item>
              <el-menu-item index="16" @click="clickRouter('/dc/folderviewer?cfgName=FolderPolicy')">
                <i class="el-icon-setting"></i>
                <span slot="title"><router-link to="/dc/folderviewer?cfgName=FolderPolicy">目录规则</router-link></span>
              </el-menu-item>

              

            </el-submenu>
          </el-menu>
        </div>
      </el-aside>
      <el-main>
        <transition name="fade" mode="out-in">
          <router-view v-on:refreshCount="refreshCount" :key="$route.fullPath"></router-view>
        </transition>
      </el-main>
    </el-container>
  </el-container>
</template>
<script>
export default {
  name: "Home",
  data() {
    return {
      todoCount: 0,
      exportCount: 0,
      errorCount: 0,
      opens: ["10", "20"],
      username: "",
      clientPermission: 0,
      menuHeight: window.innerHeight - 280
    };
  },
  created() {
    let _self = this;
    var user = sessionStorage.getItem("access-user");
    if (user) {
      _self.clientPermission = sessionStorage.getItem(
        "access-clientPermission"
      );
    }
    var externalUser = sessionStorage.getItem('access-externalUser');
    if(externalUser && externalUser=='true'){
      _self.$router.push({path: '/record/showdrawing'});
    }
    _self.refreshCount();
    //定时刷新数量
    // _self.$nextTick(function() {
    //   setInterval(_self.refreshCount, 30000);
    // });
  },
  methods: {
    // 刷新数量
    refreshCount() {
      this.refreshTodo();
    },
    // 刷新待办数量
    refreshTodo() {
      let _self = this;
      // _self.loading = true;
      // axios.get("/workflow/getMyAllTodoCount")
      //   .then(function(response) {
      //     _self.todoCount = response.data.data;
      //     _self.loading = false;
      //   })
      //   .catch(function(error) {
      //     console.log(error);
      //     _self.loading = false;
      //   });
    },
          clickRouter(pathVal){
    let _self = this;
    _self.$router.push({ 
      path: pathVal,
    });

  },
  }
};
</script>

<style>
body > .el-container {
  padding: 0px;
  margin-top: 0px;
  margin-left: 0px;
  margin-right: 0px;
  margin-bottom: 0px;
}
.el-header, .el-footer {
    background-color: #36a9e1;
    color: #333;
    text-align: center;
    padding: 0px;
    margin-top: 0px;
    margin-left: 0px;
    margin-right: 0px;
    margin-bottom: 0px;
  }
</style>
