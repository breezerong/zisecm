<template>
  <viewer :images="imageArray" @inited="inited" class="viewer" ref="viewer" >
    <img v-for="src in imageArray" :src="src" :key="src" width="240" style="cursor:hand">
  </viewer>
</template>

<script type="text/javascript">
import 'url-search-params-polyfill'

export default {
  name: "ImageViewer",
  
  data() {
    return {
      id:"",
      imageArray:[],
      imageViewer: Object,
      tableHeight: window.innerHeight - 50
    };
  },
  created() {
    this.id = this.$route.query.id;
    this.imageArray[0] =  "/dc/getContent?id="+this.id+"&token="+sessionStorage.getItem('access-token');
  },
  methods: {
    inited (viewer) {
      this.imageViewer = viewer;
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1,
h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
