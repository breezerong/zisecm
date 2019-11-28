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
  created() {
    this.id = this.$route.query.id;
    this.format = this.$route.query.format;
    this.videoType = "video/" + this.format;
    this.videoUrl =
      "/dc/getContent?id=" +
      this.id +
      "&token=" +
      sessionStorage.getItem("access-token");
    //this.initVideo();
  },
  methods: {
    initVideo() {
      //初始化视频方法
      let myPlayer = this.$video("myVideo", {
        //确定播放器是否具有用户可以与之交互的控件。没有控件，启动视频播放的唯一方法是使用autoplay属性或通过Player API。
        controls: true,
        //自动播放属性,muted:静音播放
        autoplay: "muted",
        //建议浏览器是否应在<video>加载元素后立即开始下载视频数据。
        preload: "auto",
        //设置视频播放器的显示宽度（以像素为单位）
        width: "1024px",
        //设置视频播放器的显示高度（以像素为单位）
        height: "768px"
      });
    }
  }
};
</script>

<style scoped>
</style>