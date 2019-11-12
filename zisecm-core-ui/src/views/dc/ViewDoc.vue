<template>
  <el-container>
    <el-header>
        {{doc.code}} &nbsp;&nbsp;&nbsp;&nbsp;{{doc.title}}
    </el-header>
    <el-main>
      <el-row>
        <el-col :span="20" class="doccontent">
           
        </el-col>
        <el-col :span="4" class="aside-rigth">
            <div style="padding-top:10px;">
              <el-button size="mini">借阅</el-button>
              <el-button size="mini">下载</el-button>
            </div>
            <br/>
            <div>
              <el-button type="primary" plain @click="menuClick('文档属性')">文档属性</el-button><br/>
              <el-button type="primary" plain @click="menuClick('关联文档')">关联文档</el-button><br/>
              <el-button type="primary" plain @click="menuClick('文档版本')">文档版本</el-button><br/>
              <el-button type="primary" plain @click="menuClick('格式副本')">格式副本</el-button><br/>
              <el-button type="primary" plain @click="menuClick('利用信息')">利用信息</el-button>
            </div>
        </el-col>
      </el-row>
    </el-main>

    <el-dialog :title="dialog.title" :visible.sync="dialog.visible" width="50%" :before-close="handleClose">      
      <template v-if="dialog.title=='文档属性'">
        <DocAttrs :docId="docId"></DocAttrs>
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
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialog.visible = false">取 消</el-button>
        <el-button type="primary" @click="dialog.visible = false">确 定</el-button>
      </span>
    </el-dialog>
  </el-container>
</template>

<script>

import DocAttrs from './DocAttrs.vue'
import RelationDocs from './RelationDocs.vue'
import DocVersion from './DocVersion.vue'
import UseInfo from './UseInfo.vue'
import ViewRedition from './ViewRedition.vue'

export default {
  components:{
    DocAttrs:DocAttrs,
    RelationDocs:RelationDocs,
    DocVersion:DocVersion,
    UseInfo:UseInfo,
    ViewRedition:ViewRedition
  },
  data(){
    return {
      user:{},
      docId:"",
      doc:{
        code:"J-123354",
        title:"查看文档"
      },
      dialog:{
        title:"",
        visible:false
      }
    }
  },
  created(){
    this.docId = this.$route.params.id;
    var user = sessionStorage.getItem("access-user");
    this.user = JSON.parse(user);
    console.log(this.user);
    var token = sessionStorage.getItem("access-token");
    console.log(user);
    console.log(token);
   
    this.axios.post("/zisecm/user/getUserByName",JSON.stringify(this.user.username)).then(function(response){

    });
  },
  methods:{
    menuClick(type){
      this.dialog.title=type;
      switch (type) {
        case '文档属性':
          
          break;
       case '关联文档':
          
          break;
      case '文档版本':
    
          break;
      case '格式副本':
    
          break;      
      case '利用信息':
    
          break;
      }
      this.dialog.visible=true
    },
    handleClose(done){
      this.dialog.visible = false
    }
  }
}
</script>

<style scoped>
.aside-rigth .el-button{
  margin-bottom: 5px;
}
.doccontent{
  min-height: 300px;
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