<template>
  <el-container>
    <div>
       <el-dialog
      title="借阅"
      :visible.sync="borrowDialogVisible"
      @close="borrowDialogVisible = false"
      width="95%"
      style="width:100%"
      custom-class="customWidth"
    >
         <router-view @showOrHiden="showOrHiden" ref="ShowShopingCart"></router-view>
     </el-dialog>
</div>
    <el-header style="height: 40px;padding-top:8px;">
      <el-col :span="20">
        {{doc.code}} &nbsp;&nbsp;{{doc.revision}}&nbsp;&nbsp;{{doc.title}}
      </el-col>
      <el-col :span="4" style="float:right; text-align:right;">
        <template v-if="docObj!=null">
          <el-button size="mini" icon="el-icon-shopping-cart-2" @click="borrowItem(docObj)">借阅</el-button>
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
           <InnerItemViewer v-else-if="viewerType==100" v-bind:id = "doc.id" v-bind:tableHeight="innerTableHeight"></InnerItemViewer>
           <template v-else-if="doc.permit<3">
             <div style="padding-top:40px;">
              您没有查看当前文档内容权限，如果需要查看，请点击右上角借阅按钮进行申请授权。
             </div>
           </template>
           <template v-else>
             <PdfViewer v-if="viewerType==1" v-bind:id="doc.id" v-bind:format="doc.format"></PdfViewer>
             <OfficeDocViewer v-else-if="viewerType==2" v-bind:id="doc.id" v-bind:format="doc.format"></OfficeDocViewer>
             <ImageViewer v-else-if="viewerType==3" v-bind:id="doc.id" v-bind:format="doc.format"></ImageViewer>
             <VideoPlayer v-else-if="viewerType==4" v-bind:id="doc.id" v-bind:format="doc.format"></VideoPlayer>
             <AudioPlayer v-else-if="viewerType==5" v-bind:id="doc.id" v-bind:format="doc.format"></AudioPlayer>
             
             <div v-else-if="doc.contentSize==0" style="padding-top:40px;">
                当前文件没有电子文件。
            </div>
             <div v-else style="padding-top:40px;">
               当前格式：{{doc.format}}不支持在线查看，请借阅下载查看。
              </div>
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
              <el-button type="primary" plain @click="menuClick('利用信息')">利用信息</el-button><br/>
              <el-button v-if="doc.typeName=='图纸文件'" type="primary" plain @click="menuClick('变更信息')">变更( {{doc.changeCount}} )</el-button>
              </template>
            </div>
        </el-col>
      </el-row>
    </el-main>

    <el-dialog :title="dialog.title" :visible.sync="dialog.visible" width="90%" :before-close="handleClose">      
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
        <ViewRedition :docId="docId" :downloadEnable="doc.permit>=4"></ViewRedition>
      </template>
      <template v-if="dialog.title=='利用信息'">
        <UseInfo :docId="docId"></UseInfo>
      </template>
       <template v-if="dialog.title=='变更信息'">
        <ChangeDocViewer :coding="doc.code" :revision="doc.revision"></ChangeDocViewer>
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
import Watermark from "@/assets/js/watermark.js"
import DocAttrs from './DocAttrs.vue'
import RelationDocs from './RelationDocs.vue'
import DocVersion from './DocVersion.vue'
import UseInfo from './UseInfo.vue'
import ViewRedition from './ViewRedition.vue'

import PdfViewer from './PdfViewer.vue'
import OfficeDocViewer from './OfficeDocViewer.vue'
import ImageViewer from './ImageViewer.vue'
import VideoPlayer from './VideoPlayer.vue'
import AudioPlayer from './AudioPlayer.vue'
import InnerItemViewer from "./InnerItemViewer.vue"
import ChangeDocViewer from "./ChangeDocViewer.vue"
import { timeout } from 'q'

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
    AudioPlayer:AudioPlayer,
    InnerItemViewer:InnerItemViewer,
    ChangeDocViewer:ChangeDocViewer,
    PdfViewer:PdfViewer
  },
  data(){
    return {
      user:{},
      token:"",
      docId:"",
      docObj:null,
      viewerType: 0,
      ip:"",
      doc:{
        id:"",
        code:"",
        revision:"",
        title:"",
        folderId:"",
        typeName:"",
        format:"",
        permit:0,
        contentSize:0,
        hasPdf:false,
        changeCount:0
      },
      message:"加载中。。。",
      watermarkText:"",
      innerTableHeight:window.innerHeight - 75,
      dialog:{
        title:"",
        visible:false
      },
      borrowDialogVisible:false,
    }
  },
  created(){
    var _self = this;
    _self.getUserIP((ip) => {
      _self.ip = ip;
    });
  },
  computed:{
    downloadEnable(){
      return this.doc.permit>=4;
    }
  },
  mounted(){
    var _self = this;
    this.docId = this.$route.query.id;
    var user = sessionStorage.getItem("access-user");
    this.user = JSON.parse(user);
    this.token = sessionStorage.getItem("access-token");
    axios.post("/dc/getDocument",this.docId).then(function(response) {
        _self.docObj=response.data.data;
        _self.doc.permit = response.data.permit;
        _self.doc.hasPdf = response.data.hasPdf;
        _self.doc.changeCount = response.data.changeCount;
        // console.log(_self.docObj);
        _self.doc.id=_self.docObj.ID;
        _self.doc.code=_self.docObj.CODING;
        _self.doc.revision=_self.docObj.REVISION;
        _self.doc.title=_self.docObj.NAME;
        _self.doc.contentSize = _self.docObj.CONTENT_SIZE;
        _self.doc.folderId=_self.docObj.FOLDER_ID;
        if(!_self.doc.hasPdf){
          _self.doc.format=_self.docObj.FORMAT_NAME;
        }else{
          _self.doc.format ="pdf";
        }
        _self.doc.typeName= _self.docObj.TYPE_NAME;
        _self.initViewerType();
        if(_self.viewerType>0 && _self.viewerType<100){
          _self.watermarkText =  sessionStorage.getItem("access-userName");
          var showText = _self.watermarkText +' '+ _self.ip
            +' '+ _self.datetimeFormat(new Date());
          Watermark.set(showText);
        }
      }).catch(function(error) {
        console.log(error);
    });

		// setInterval(function() {
		// 	var showText1 = _self.watermarkText +' '+ _self.ip
		// 		+' '+ _self.datetimeFormat(new Date());
		// 	Watermark.set(showText1);
		// }, 10000); //每10秒刷新一次  3000的单位是毫秒  
  },
  methods:{
    //office文档:1,图片：2，视频：3，音频：4
    initViewerType(){
      let _self = this;
      if(_self.doc){
        console.log("typename:"+_self.doc.typeName);
        if(_self.doc.typeName == "卷盒" || _self.doc.typeName=="图册"){
          _self.viewerType = 100;
        } else if(_self.doc.format == "pdf"){
          _self.viewerType = 1; 
        }else if(_self.doc.format == "doc" || _self.doc.format == "docx" ||
        _self.doc.format == "ppt" ||_self.doc.format == "pptx" ||
        _self.doc.format == "xls" ||_self.doc.format == "xlsx"){
          _self.viewerType = 2;
        }else if(_self.doc.format == "jpg"||_self.doc.format == "png"||
        _self.doc.format == "gif" || _self.doc.format == "bpm" ||
        _self.doc.format == "jpeg"
        ){
          _self.viewerType = 3;
        }else if(_self.doc.format == "mp4" || _self.doc.format == "ogg" ||_self.doc.format == "webm"){
          _self.viewerType = 4;
        }else if(_self.doc.format == "mp3" || _self.doc.format == "wav"){
          _self.viewerType = 5;
        }
      }
      //console.log(_self.viewerType);
    },
    download(){
      let url = this.axios.defaults.baseURL+"/dc/getContent?id="+this.doc.id+"&token="+sessionStorage.getItem('access-token')+"&action=download";
      window.open(url, '_blank');
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
        
        this.$refs.ShowProperty.saveItem();
        
        this.dialog.visible = false
      }else if(this.dialog.title=='借阅'){
        this.$message("借阅");
      }else{
        this.dialog.visible = false
      }
    },
    getUserIP(onNewIP) {
        let MyPeerConnection = window.RTCPeerConnection || window.mozRTCPeerConnection || window.webkitRTCPeerConnection;
        let pc = new MyPeerConnection({
            iceServers: []
          });
        let noop = () => {
          };
        let localIPs = {};
        let ipRegex = /([0-9]{1,3}(\.[0-9]{1,3}){3}|[a-f0-9]{1,4}(:[a-f0-9]{1,4}){7})/g;
        let iterateIP = (ip) => {
          if (!localIPs[ip]) onNewIP(ip);
          localIPs[ip] = true;
        };
        pc.createDataChannel('');
        pc.createOffer().then((sdp) => {
          sdp.sdp.split('\n').forEach(function (line) {
            if (line.indexOf('candidate') < 0) return;
            line.match(ipRegex).forEach(iterateIP);
          });
          pc.setLocalDescription(sdp, noop, noop);
        }).catch((reason) => {
        });
        pc.onicecandidate = (ice) => {
          if (!ice || !ice.candidate || !ice.candidate.candidate || !ice.candidate.candidate.match(ipRegex)) return;
          ice.candidate.candidate.match(ipRegex).forEach(iterateIP);
        };
      },
      borrowItem(obj) {
        let _self = this;
        let rowData=[];
        _self.borrowDialogVisible=true;
        rowData.push(obj);
        if(typeof(obj.C_ARCHIVE_UNIT)=="undefined"){
              _self.$message({
                showClose: true,
                message: "所借阅档案，归档单位为空，不能外借!",
                duration: 5000,
                type: "warning"
              });
              return;
        }
          setTimeout(() => {
          _self.$router.replace({
            path:'/viewDoc_borrow',
            query: { tabledata: rowData,
            C_ARCHIVE_UNIT:obj.C_ARCHIVE_UNIT
            }
          });
    
        }, 100);
      },
    showOrHiden(b){
      this.borrowDialogVisible=b;
    },

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
  padding-top: 15px;
  padding-left: 15px;
}
</style>