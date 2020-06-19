<template>
<div>
    <object v-if="browerName=='Chrome'||browerName=='Safari'||browerName=='FF'" 
    type="application/x-shockwave-flash" id="WebCAD" 
    data="/static/flash/cadViewer.swf" width="100%" :height="itemHeight">
      <param name="movie" value="/static/flash/cadViewer.swf" />
      <param name="quality" value="high" /><param name="bgcolor" value="#ffffff" />
      <param name="allowFullScreen" value="true">
      <param name="play" value="true" /><param name="loop" value="true" />
      <param name="wmode" value="Opaque">
      <param name="scale" value="showall" />
      <param name="menu" value="true" /><param name="devicefont" value="false" />
      <param name="salign" value="" />
      <param name="allowScriptAccess" value="always" />
      <param name="FlashVars" :value="NanJingBankUrl" />
    </object>
    <object v-else-if="browerName=='IE'" classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" id="WebCAD" 
    width="100%" :height="itemHeight" align="middle">
      <param name="movie" value="/static/flash/cadViewer.swf" />
      <param name="quality" value="high" />
      <param name="bgcolor" value="#ffffff" />
      <param name="allowFullScreen" value="true">
      <param name="play" value="true" />
      <param name="loop" value="true" /><param name="wmode" value="transparent" />
      <param name="scale" value="showall" />
      <param name="menu" value="true" />
      <param name="devicefont" value="false" />
      <param name="salign" value="" />
      <param name="allowScriptAccess" value="always" />
      <param name="FlashVars" :value="NanJingBankUrl" />
    </object>
    <embed v-else id="WebCAD" width="100%" :height="itemHeight" 
      :flashvars="flashVars" 
      allowscriptaccess="always"
      allowfullscreen="true" bgcolor="#000000" wmode="Opaque" 
      src="/static/flash/cadViewer.swf" type="application/x-shockwave-flash" 
      style="outline:none;" name="krpanoSWFObject"/>
    
      


  </div>
</template>

<script type="text/javascript">
import 'url-search-params-polyfill'

export default {
  name: "CADViewer",
  data() {
    return {
      browerName:'',
            dwgname:"",
      itemHeight: window.innerHeight - 50
    };
  },
  computed: {
        NanJingBankUrl() {
            return "showToolBar=0&runmode=0&ze=1&OnLoadedEvent=OnFlashLoaded"
          + "&fileId=1001&fileName=test&cacheSize=1024&ocfSizeLimit=100&extData=null"
          + "&languagePage=/static/flash/cn.xml&lan=cn";
        }
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
    this.whatBrower();
    let me = this;
         window['OnFlashLoaded'] = () => {
             me.OnFlashLoaded(); // 这个也就是我定义的方法
         }
    // this.loadUrl();
  },
  methods: {
    thisMovie(movieName) {
			if (navigator.appName.indexOf("Microsoft") != -1) {
				return window[movieName];
			} else {
				return document[movieName];
			}
		},
    GetQueryString(name) {
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
			var r = window.location.search.substr(1).match(reg);
			if (r != null)
				dwgname= unescape(r[2]);
			
		},
    OnFlashLoaded:function() {
      let _self=this;
			//下面这个传递参数只是参考，本样例代码中为了简化后台并没有使用.后台代码中只实现了transform.do,这个是必须的，另外一个是将图纸转化成pdf，如果不需要就可以不管它。
			let urlData = "{\"transformPage\":\""+_self.axios.defaults.baseURL+"/dc/getFlashParam?id="+ _self.id+"&token="+sessionStorage.getItem('access-token')+"\",\"saveAsPdf\":\"./saveAsPdf.do\"}";
			this.thisMovie('WebCAD').setDataUrl("urlData", urlData);
			
			//上面两行实现的是向后台发送请求，然后返回json数据，里面包含ocf文件的url地址。如果已经知道了ocf文件的url地址，则可以注释掉上面两行代码，使用下面两行代码。
			//其中http://127.0.0.1/file/ocf/1001/test.ocf就是ocf的地址，根据实际图纸的改成不同的url。type=GstarCADSupperMark这个就是默认值，不用修改。
		   //thisMovie("WebCAD").setDataUrl("urlData" , "{}");  
           //thisMovie("WebCAD").setData("http://127.0.0.1/file/ocf/1001/test.ocf","?type=GstarCADSupperMark"); 			

			v = 1;
		},
    whatBrower() {
      var mb = this.myBrowser();
      this.browerName=mb;
    },
    myBrowser() {

      var userAgent = navigator.userAgent;
      var isOpera = userAgent.indexOf("Opera") > -1;
      if (isOpera) {
          return "Opera"
      };
      if (userAgent.indexOf("Firefox") > -1) {
          return "FF";
      }
      if (userAgent.indexOf("Chrome") > -1) {
          return "Chrome";
      }
      if (userAgent.indexOf("Safari") > -1) {
          return "Safari";
      }
      if (userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera) {
          return "IE";
      };

    },
    loadUrl() {
      let _self = this;
      let getfileUrl =  _self.axios.defaults.baseURL+"/dc/getContent?id="+_self.id+"&token="+sessionStorage.getItem('access-token')+"&format=pdf";
      _self.pdfUrl = "./static/pdfviewer/web/viewer.html?file="+encodeURIComponent(getfileUrl)+"&.pdf"
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
