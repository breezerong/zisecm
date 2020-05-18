<template>
    <iframe key="#1" :src="url" frameborder="0" width="100%" :height="itemHeight" ></iframe>
</template>

<script type="text/javascript">
import 'url-search-params-polyfill'

export default {
  name: "PdfViewer",
  data() {
    return {
      url:"",
      itemHeight: window.innerHeight - 50
    };
  },
  props: {
    id:{type:String},
	format:{type:String},
	fileName:{type:String}
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
    //   let getfileUrl =  _self.axios.defaults.baseURL+"/dc/getContent?id="+_self.id+"&token="+sessionStorage.getItem('access-token')+"&format=pdf";
	 _self.url = "./static/jtviewer/jtViewer.html?fileName="+ _self.fileName
	//   _self.url = "./static/pdfviewer/web/viewer.html?file="+encodeURIComponent(getfileUrl)+"&.pdf"
    }
  },
  
	
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
