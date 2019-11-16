<template>
  <div class="pdfviewer">
   <div class="center">
     <div class="contor">
      <el-button @click="prev">{{$t('application.prev')}}</el-button>
      <el-button @click="next">{{$t('application.next')}}</el-button>    
      <span>Page: <span v-text="page_num"></span> / <span v-text="page_count"></span></span>
      <el-button @click="addscale" icon="plus"></el-button>
      <el-button @click="minus" icon="minus"></el-button>
      <el-button @click="closepdf">{{$t('application.close')}}</el-button>
     </div>
     <canvas class="canvasstyle" id="the-canvas"></canvas>
   </div>
  </div>
</template>

<script type="text/javascript">
import PDFJS from 'pdfjs-dist'

export default {
  name: "PDFViewer",
  data() {
    return {
      pdfDoc: null, //pdfjs 生成的对象
      pageNum: 1,//
      pageRendering: false,
      pageNumPending: null,
      scale: 1.2,//放大倍数
      page_num: 0,//当前页数
      page_count: 0,//总页数
      maxscale: 10,//最大放大倍数
      minscale: 0.2//最小放大倍数
    };
  },
  props: {
    pdfurl: {type:[String],required:false}
  },
  computed: {
    ctx() {
     let id = document.getElementById('the-canvas');
     return id.getContext('2d');
    }
  },
  mounted() {
    
    this.showPDF(this.pdfurl);
  },

  methods: {
    showPDF (url) {
      console.log("url:"+url);
      if(url && url!=""){
        let _this = this;
        PDFJS.getDocument(url).then(function(pdfDoc_) { //初始化pdf
          _this.pdfDoc = pdfDoc_;
          _this.page_count = _this.pdfDoc.numPages;
          _this.renderPage(_this.pageNum);
        });
      }
    },
     renderPage(num) { //渲染pdf
     let _this = this;
     this.pageRendering = true;
     let canvas = document.getElementById('the-canvas');
     // Using promise to fetch the page
     this.pdfDoc.getPage(num).then(function(page) {
       var viewport = page.getViewport(_this.scale);
       //alert(_this.canvas.height)
       canvas.height = viewport.height;
       canvas.width = viewport.width;
 
       // Render PDF page into canvas context
       var renderContext = {
        canvasContext: _this.ctx,
        viewport: viewport
       };
       var renderTask = page.render(renderContext);
 
       // Wait for rendering to finish
       renderTask.promise.then(function() {
        _this.pageRendering = false;
        if(_this.pageNumPending !== null) {
          // New page rendering is pending
          _this.renderPage(_this.pageNumPending);
          _this.pageNumPending = null;
        }
       });
     });
     _this.page_num = _this.pageNum;
    },
    addscale() {//放大
     if(this.scale >= this.maxscale) {
       return;
     }
     this.scale += 0.1;
     this.queueRenderPage(this.pageNum);
    },
    minus() {//缩小
     if(this.scale <= this.minscale) {
       return;
     }
     this.scale -= 0.1;
     this.queueRenderPage(this.pageNum);
    },
    prev() {//上一页
     let _this = this
     if(_this.pageNum <= 1) {
       return;
     }
     _this.pageNum--;
     _this.queueRenderPage(_this.pageNum);
    },
    next() {//下一页
     let _this = this
     if(_this.pageNum >= _this.page_count) {
       return;
     }
     _this.pageNum++;
     _this.queueRenderPage(_this.pageNum);
    },
    closepdf() {//关闭PDF
     this.$emit('closepdf');
    },
    queueRenderPage(num) {
     if(this.pageRendering) {
       this.pageNumPending = num;
     } else {
       this.renderPage(num);
     }
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.pdfviewer {
  position: fixed;
  top: 0;
  left: 0;
  background-color: rgba(0, 0, 0, .5);
  width: 100%;
  height: 100%;
  z-index: 99999;
  display: flex;
  justify-content: center;
  align-items: center
  
 }
 .center {
  text-align: center;
  height: 100%;
  overflow: auto;
  padding-top: 20px
}
.contor {
  margin-bottom: 10px;
}
</style>
