<template>
	<iframe src="http://visual.poolparty.biz/?project=cocktails" border=0 :style="{height:webHeight+'px',width:'100%'}"></iframe>
</template>
<script>
	
	export default {
		data() {
			return {
				webHeight: window.innerHeight-90
			}
		},
		mounted() {
			
		window.onresize = this.onWindowResize;
  },
  methods: {
    onWindowResize(){
      this.webHeight = window.innerHeight-90;
    }
		}
	}
</script>
<style lang="less" scoped>
	
</style>