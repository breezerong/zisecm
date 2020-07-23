<template>
  <el-container>
    <el-header style="height: auto;">
      <slot name="header"></slot>
    </el-header>
    <el-main>
      <slot style="height:auto;"></slot>
      <slot name="main" v-bind:layout="layout"></slot>
    </el-main>
  </el-container>
</template>

<script>
export default {
  data(){
    return {
      layout:{
        height:0,
        step:250,
        split:1
      }
    }
  },
  methods:{
    getLayoutHeight(){
      this.layout.height = window.innerHeight/this.layout.split
      return this.layout.height;
    }
  },
  created(){
    let _self = this
    window.addEventListener("resize",function(){
      let size = _self.getLayoutHeight()
      _self.$emit("onLayoutResize",size)
    })
    this.layout.height = window.innerHeight/this.layout.split

  },
  mounted(){
    /**
     * 跳转至权限提醒页
     */
    if(!this.validataPermission()){
        let _self=this;
        _self.$nextTick(()=>{
            _self.$router.push({ path: '/NoPermission' })
        })
    }
  }
}
</script>

<style scoped>
.el-header{
    height: auto;
}
</style>
