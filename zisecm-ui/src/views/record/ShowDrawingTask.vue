<template>
      <el-container>
        <el-aside width="200px">
          <div v-bind:style="{height: menuHeight +'px'}">
          <!--左侧导航-->
          <el-menu default-active="201" class="el-menu-vertical-ecm"  :open="201">
                <div>
                  <el-submenu index="200">
                    <template slot="title">
                      <i class="el-icon-menu"></i>
                      <span style="font-size:16px">晒图状态</span>
                    </template>
                    <el-menu-item index="201">
                      <i class="el-icon-bottom"></i>
                      <span slot="title"><router-link :to="{path: '/record/showdrawing/showdrawingpendingouttask', query: {'finishedShowDrawing': '待晒图','C_DRAFTER':C_DRAFTER}}">待晒图</router-link></span>
                    </el-menu-item>
                    <el-menu-item index="202">
                      <i class="el-icon-finished"></i>
                      <span slot="title"><router-link :to="{path: '/record/showdrawing/showdrawingfinishtask', query: {'finishedShowDrawing': '已晒图' ,'C_DRAFTER':C_DRAFTER}}">已完成</router-link></span>
                    </el-menu-item>
                    
                  </el-submenu>
                </div>
          </el-menu>
          </div>
        </el-aside>
        <el-main>
          <transition name="fade" mode="out-in">
            <router-view></router-view>
          </transition>
        </el-main>
      </el-container>
</template>
<script type="text/javascript">
export default {
  name: "archiveoutgoing",
  permit: 1,
  data() {
        return {
            menuHeight: window.innerHeight -64,
            'C_DRAFTER':sessionStorage.getItem('access-userName')
        }
        
    },
    mounted(){
      this.$router.replace({
        path:"/record/showdrawing/showdrawingpendingouttask",
        query: {'finishedShowDrawing': '待晒图',
        C_DRAFTER:this.C_DRAFTER,
         }
      });
    },
    methods: {

    }
};
</script>
<style scoped>
</style>