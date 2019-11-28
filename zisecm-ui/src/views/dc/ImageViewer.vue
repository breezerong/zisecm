<template>
  <viewer :images="imageArray" @inited="inited" class="viewer" ref="viewer" >
    <img v-for="src in imageArray" :src="src" :key="src" width="240" style="cursor:hand;width:auto;
        height:auto;
        max-width:70%;
        max-height:100%;">
  </viewer>
</template>

<script type="text/javascript">
import 'url-search-params-polyfill'

export default {
  name: "ImageViewer",
  
  data() {
    return {
      imageArray:[],
      imageViewer: Object,
      tableHeight: window.innerHeight - 50
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
    console.log(this.axios.defaults.baseURL);
    this.imageArray[0] =  this.axios.defaults.baseURL+"/dc/getContent?id="+this.id+"&token="+sessionStorage.getItem('access-token');
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
