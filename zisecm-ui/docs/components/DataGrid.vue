
<template>
  <!-- 
    Div 内分两个部分：表格和分页
  -->
  <div>
    <el-table></eltable>
    <el-pagination></<el-pagination>
  </div>
</template>

<script>

export default {
  /* 
    输入参数
      功能：1.输入访问url地址；2.支持分页处理；3.调用组件可自定义列
  */
  props:{
    dataUrl:"",//异步获取数据URL地址
    columns:{
        /**
         * 自定义表列
         * columns:[
                {prop:'id',label:'ID',width:235},
                {prop:'name',label:'姓名',width:150}
                ]*/
        type:Array,
        default:function(){
            return [ ];
        }            
    }
  },
  data(){
    return{
    }
  },
  methods:{
  },
  mounted(){
  },
  created(){
  }
}
</script>

<style>

</style>
