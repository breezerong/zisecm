
import Vue from 'vue';
<template>
  <div id="app">
    <el-container>
      <el-container>
        <el-main>
          <transition name="fade" mode="out-in">
            <router-view></router-view>
          </transition>
        </el-main>
      </el-container>
    </el-container>
   </div>
</template>
<script>
import enLocale from 'element-ui/lib/locale/lang/en'
import zhLocale from 'element-ui/lib/locale/lang/zh-CN'
import locale from 'element-ui/lib/locale'

export default {
  name: 'App',
  data() {
    return {
      
    };
  }
}
</script>

<style>
 body{
    padding: 0px;
    margin-top: 0px;
    margin-left: 0px;
    margin-right: 0px;
    margin-bottom: 0px;
    background: #f5f5f5;
    color: #383e4b;
  }
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 0px;
  margin-left: 0px;
  margin-right: 0px;
  margin-bottom: 0px;
  padding: 0px;
}

  body > .el-container {
    padding: 0px;
    margin-top: 0px;
    margin-left: 0px;
    margin-right: 0px;
    margin-bottom: 0px;
  }

  .searchhighlight  {
    background-color: rgb(245, 241, 1);
    font-size: 14px;
    font-weight: bold;
  }

</style>
