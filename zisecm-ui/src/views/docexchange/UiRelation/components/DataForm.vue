<template>
  <el-form>

  </el-form>
</template>

<script>
export default {
  methods:{
    loadForm:function(){
      var url="/admin/getFormObj/相关类型设置/NEW";
      axios.post(url).then(function(response) {
         console.log(response.data.data)
        })
        .catch(function(error) {
          console.log(error);
        });
    }
  },
  mounted(){
    this.loadForm()
  }
}
</script>

<style>

</style>