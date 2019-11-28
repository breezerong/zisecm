<template>
    <video
      id="myVideo"
      class="vjs-default-skin vjs-big-play-centered"
      controls
      autoplay="muted"
      preload="auto"
      width="1024" 
      height="768"
    >
      <source :src="videoUrl" :type="videoType" />
    </video>
</template>
<script>
import "url-search-params-polyfill";
import videojs from "video.js";
import "videojs-contrib-hls";

export default {
  name: "VideoPlayer",
  data() {
    return {
      videoUrl: "",
      videoType: "video/mp4",
      itemHeight: window.innerHeight - 50
    };
  },
  props: {
    id:{type:String},
    format:{type:String}
  },
  created() {
    if(this.id==null && this.$route.query.id){
      this.id = this.$route.query.id;
    }
    if(this.format==null && this.$route.query.format){
      this.format = this.$route.query.format;
    }
    this.videoType = "video/" + this.format;
    this.videoUrl =
      this.axios.defaults.baseURL+"/dc/getContent?id=" +
      this.id +
      "&token=" +
      sessionStorage.getItem("access-token");
   // console.log(this.videoUrl);
   // console.log(this.format);
  },
  methods: {
 
  }
};
</script>

<style scoped>
</style>