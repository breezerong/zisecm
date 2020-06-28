<template>
      <el-container>
        <el-aside width="120px">
          <div v-bind:style="{height: menuHeight +'px'}">
          <!--左侧导航-->
          <el-menu class="el-menu-vertical-ecm" >
            <el-menu-item index="200">
                      <i class="el-icon-caret-right"></i>
                      <span slot="title"><router-link to="/record/check4">四性检查</router-link></span>
                    </el-menu-item>
                    <el-menu-item index="201">
                      <i class="el-icon-caret-right"></i>
                      <span slot="title"><router-link to="/record/batchupdate">{{$t('route.batchUpdate')}}</router-link></span>
                    </el-menu-item>
                    <el-menu-item index="202">
                      <i class="el-icon-caret-right"></i>
                      <span slot="title"><router-link to="/record/batchmount">{{$t('route.batchmount')}}</router-link></span>
                    </el-menu-item>
                    <!--
                    <el-menu-item index="202">
                      <i class="el-icon-caret-right"></i>
                      <span slot="title"><router-link to="/record/batchserverimport">服务端批量导入</router-link></span>
                    </el-menu-item>
                    -->
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
  name: "ArchiveTool",
  permit: 1,
  data() {
    return {
      menuHeight: window.innerHeight -64
    }
        
  },
  methods: {

  }
};
</script>
<style scoped>

</style>