<template>
    <iframe key="#1" :src="cadUrl" frameborder="0" width="100%" :height="itemHeight" ></iframe>
</template>

<script type="text/javascript">
import 'url-search-params-polyfill'

export default {
  name: "CADViewerHtml5",
  data() {
    return {
      cadUrl:"",
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
    this.loadUrl();
  },
  methods: {
    loadUrl() {
      let _self = this;
      let getfileUrl =  _self.axios.defaults.baseURL+"/dc/getContent?id="+_self.id+"&token="+sessionStorage.getItem('access-token')+"&format=ocf";
    //   _self.cadUrl = "./static/pdfviewer/web/viewer.html?file="+encodeURIComponent(getfileUrl)+"&.pdf"
        _self.cadUrl="./static/cadviewerh5/viewerh5.html?file="+encodeURIComponent(getfileUrl)+"&.ocf";
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
