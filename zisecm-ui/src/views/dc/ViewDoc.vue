<template>
  <el-container>
    <el-header style="height: 40px;padding-top:8px;">
      <el-col :span="20">
        {{doc.code}} &nbsp;&nbsp;{{doc.revision}}&nbsp;&nbsp;{{doc.title}}
      </el-col>
      <el-col :span="4" style="float:right; text-align:right;">
        <template v-if="docObj!=null">
          <el-button size="mini" icon="el-icon-shopping-cart-2" @click="menuClick('借阅')">借阅</el-button>
          <el-button v-if="doc.permit>=4" size="mini" icon="el-icon-download" @click="download()">下载</el-button>
        </template>
      </el-col>
    </el-header>
    <el-main>
      <el-row>
        <el-col :span="22" class="doccontent">
           <template v-if="docObj==null">
             {{message}}
           </template>
           <template v-else-if="doc.permit<3">
             您没有查看当前文档内容权限，如果需要查看，请点击右上角借阅按钮进行申请授权。
           </template>
           <template v-else>
             <OfficeDocViewer v-if="viewerType==1" v-bind:id="doc.id" v-bind:format="doc.format"></OfficeDocViewer>
             <ImageViewer v-else-if="viewerType==2" v-bind:id="doc.id" v-bind:format="doc.format"></ImageViewer>
             <VideoPlayer v-else-if="viewerType==3" v-bind:id="doc.id" v-bind:format="doc.format"></VideoPlayer>
             <AudioPlayer v-else-if="viewerType==4" v-bind:id="doc.id" v-bind:format="doc.format"></AudioPlayer>
             <div v-else>当前格式：{{doc.format}}不支持在线查看，请借阅下载查看。</div>
           </template>
        </el-col>
        <el-col :span="2" class="aside-rigth">
            <div style="padding-top:10px;">
              
            </div>
            <br/>
            <div>
              <template v-if="docObj!=null">
              <el-button type="primary" plain @click="menuClick('文档属性')">文档属性</el-button><br/>
              <el-button type="primary" plain @click="menuClick('关联文档')">关联文档</el-button><br/>
              <el-button type="primary" plain @click="menuClick('文档版本')">文档版本</el-button><br/>
              <el-button type="primary" plain @click="menuClick('格式副本')">格式副本</el-button><br/>
              <el-button type="primary" plain @click="menuClick('利用信息')">利用信息</el-button>
              </template>
            </div>
        </el-col>
      </el-row>
    </el-main>

    <el-dialog :title="dialog.title" :visible.sync="dialog.visible" width="80%" :before-close="handleClose">      
      <template v-if="dialog.title=='文档属性'">
        <ShowProperty ref="ShowProperty" :itemId="doc.id" :typeName="doc.typeName" :folderId="doc.folderId"></ShowProperty>
      </template>
      <template v-if="dialog.title=='关联文档'">
       <RelationDocs :docId="docId"></RelationDocs>
      </template>
       <template v-if="dialog.title=='文档版本'">
        <DocVersion :docId="docId"></DocVersion>
      </template>
       <template v-if="dialog.title=='格式副本'">
        <ViewRedition :docId="docId"></ViewRedition>
      </template>
      <template v-if="dialog.title=='利用信息'">
        <UseInfo :docId="docId"></UseInfo>
      </template>
      <template v-if="dialog.title=='借阅'">
        借阅
      </template>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialog.visible = false">取 消</el-button>
        <el-button type="primary" @click="dialogSubmit()">确 定</el-button>
      </span>
    </el-dialog>
  </el-container>
</template>

<script>

import ShowProperty from '@/components/ShowProperty.vue'
import "@/assets/js/watermark.js"
import DocAttrs from './DocAttrs.vue'
import RelationDocs from './RelationDocs.vue'
import DocVersion from './DocVersion.vue'
import UseInfo from './UseInfo.vue'
import ViewRedition from './ViewRedition.vue'

import OfficeDocViewer from './OfficeDocViewer.vue'
import ImageViewer from './ImageViewer.vue'
import VideoPlayer from './VideoPlayer.vue'
import AudioPlayer from './AudioPlayer.vue'

export default {
  components:{
    ShowProperty:ShowProperty,
    DocAttrs:DocAttrs,
    RelationDocs:RelationDocs,
    DocVersion:DocVersion,
    UseInfo:UseInfo,
    ViewRedition:ViewRedition,
    OfficeDocViewer:OfficeDocViewer,
    ImageViewer:ImageViewer,
    VideoPlayer:VideoPlayer,
    AudioPlayer:AudioPlayer
  },
  data(){
    return {
      user:{},
      token:"",
      docId:"",
      docObj:null,
      viewerType: 0,
      
      doc:{
        id:"",
        code:"",
        revision:"",
        title:"",
        folderId:"",
        typeName:"",
        format:"",
        permit:0,
        hasPdf:false,
      },
      message:"加载中。。。",
      watermarkText:"",
      dialog:{
        title:"",
        visible:false
      }
    }
  },
  created(){
    var _self = this;
    this.docId = this.$route.query.id;
    var user = sessionStorage.getItem("access-user");
    this.user = JSON.parse(user);
    this.token = sessionStorage.getItem("access-token");
    axios.post("/dc/getDocument",this.docId).then(function(response) {
        _self.docObj=response.data.data;
        _self.doc.permit = response.data.permit;
        _self.doc.hasPdf = response.data.hasPdf;
        console.log(_self.docObj);
        _self.doc.id=_self.docObj.ID;
        _self.doc.code=_self.docObj.CODING;
        _self.doc.revision=_self.docObj.REVISION;
        _self.doc.title=_self.docObj.NAME;
        _self.doc.folderId=_self.docObj.FOLDER_ID;
        if(!_self.doc.hasPdf){
          _self.doc.typeName=_self.docObj.TYPE_NAME;
        }else{
          _self.doc.typeName="pdf";
        }
        _self.doc.format = _self.docObj.FORMAT_NAME;
        //console.log(_self.doc);
        _self.initViewerType();
        //_self.writeReadLog()
      }).catch(function(error) {
        console.log(error);
    });
    _self.watermarkText =  sessionStorage.getItem("access-userName");
		var showText = _self.watermarkText +' '+ window.location.host
				+' '+ _self.datetimeFormat(new Date());
		watermark.init({
			watermark_txt : showText,
			watermark_width : 200
		});
		setInterval(function() {
			var showText1 = _self.watermarkText +' '+ window.location.host
				+' '+ _self.datetimeFormat(new Date());
			watermark.load({
				watermark_txt : showText1,
				watermark_width : 200
			});
		}, 3000); //每3秒刷新一次  3000的单位是毫秒  
  },
  methods:{
    //office文档:1,图片：2，视频：3，音频：4
    initViewerType(){
      let _self = this;
      //console.log(_self.viewerType);
      if(_self.doc){
        if(_self.doc.format == "doc" || _self.doc.format == "docx" ||
        _self.doc.format == "ppt" ||_self.doc.format == "pptx" ||
        _self.doc.format == "xls" ||_self.doc.format == "xlsx"||_self.doc.format == "pdf"){
          _self.viewerType = 1;
        }else if(_self.doc.format == "jpg"||_self.doc.format == "png"||
        _self.doc.format == "gif" || _self.doc.format == "bpm" ||
        _self.doc.format == "jpeg"
        ){
          _self.viewerType = 2;
        }else if(_self.doc.format == "mp4" || _self.doc.format == "ogg" ||_self.doc.format == "webm"){
          _self.viewerType = 3;
        }else if(_self.doc.format == "mp3" || _self.doc.format == "wav"){
          _self.viewerType = 4;
        }
      }
      //console.log(_self.viewerType);
    },
    download(){
      let url = "/dc/getContent?id="+this.doc.id+"&token="+sessionStorage.getItem('access-token');
      window.open(url, '_blank');
    },
    writeReadLog(){
      axios.post("/dc/newAudit",this.docId).then(function(response) {
        console.log("writeReadLog");
        console.log(response);
      });
      
    },

    
    menuClick(type){
      this.dialog.title=type;
      this.dialog.visible=true
    },
    handleClose(done){
      this.dialog.visible = false
    },
    dialogSubmit(){
      if(this.dialog.title=='文档属性'){
        this.$message("文档属性");
        this.$refs.ShowProperty.saveItem();
        this.dialog.visible = false
      }else if(this.dialog.title=='借阅'){
        this.$message("借阅");
      }else{
        this.dialog.visible = false
      }
    }
  }
}
</script>

<style scoped>
.aside-rigth .el-button{
  margin-bottom: 5px;
}
.doccontent{
  min-height: 640px;
  border-style:solid;
	border-width:1px;
  border-color: grey;
}
.el-header{
  color: white;
  text-align: left;
  padding-top: 15px;
  padding-left: 15px;
}
</style>