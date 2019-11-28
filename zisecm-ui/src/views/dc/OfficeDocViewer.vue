<template>
    <iframe key="#1" :src="owaUrl" frameborder="0" width="100%" :height="itemHeight" ></iframe>
</template>

<script type="text/javascript">
import 'url-search-params-polyfill'

export default {
  name: "OfficeDocViewer",
  data() {
    return {
      owaUrl:"",
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
      _self.loading = true;
      let key = "OwaPdfUrl";
      if(_self.format=="doc" || _self.format=="docx"){
        key = "OwaWordUrl";
      }else if(_self.format=="xlsx" || _self.format=="xls"){
        key = "OwaExcelUrl";
      }else if(_self.format=="ppt" || _self.format=="pptx"){
        key = "OwaPowerpointUrl";
      }
      axios.post('/dc/getOneParameterValue',key)
        .then(function(response) {
         
          let url = response.data.data[0].replace("{0}",_self.id).replace("{1}",_self.format);
          _self.owaUrl = url;
           console.log(url);
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
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
