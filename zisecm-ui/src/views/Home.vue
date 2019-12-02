<template>
  <el-container>
    <el-container>
      <el-aside width="180px">
        <div v-bind:style="{height: menuHeight +'px'}">
          <!--左侧导航-->
          <el-menu default-active="11" class="el-menu-vertical-ecm" :default-openeds="opens" style="background-color:#D3DCE6">
            <el-submenu index="10">
              <template slot="title">
                <i class="el-icon-news"></i>
                <span>任务中心</span>
              </template>
              <el-menu-item index="11">
                <i class="el-icon-time"></i>
                <span slot="title">
                  <router-link to="/workflow/todotask">待办工作</router-link>
                  <el-badge :value="todoCount" class="item"></el-badge>
                </span>
              </el-menu-item>
              <el-menu-item index="12">
                <i class="el-icon-circle-check"></i>
                <span slot="title">
                  <router-link to="/workflow/donetask">已办工作</router-link>
                </span>
              </el-menu-item>
              <el-menu-item index="13">
                <i class="el-icon-help"></i>
                <span slot="title">
                  <router-link to="/workflow/myworkflow">我的流程</router-link>
                </span>
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
      menuHeight: window.innerHeight - 100
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
      _self.loading = true;
      axios.get("/workflow/getMyAllTodoCount")
        .then(function(response) {
          _self.todoCount = response.data.data;
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
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
</style>
