<template>
  <div>
      利用信息
  </div>
</template>

<script>
export default {
    props:{
        docId:{
            type:String,
            default:""
        }
    },
    name:"UseInfo",
    created(){
      console.log("利用信息 created");
    },
    mounted(){
      console.log("利用信息 mounted");
    }
}
</script>

<style>

</style>