<template>
  <div>
     <el-dialog :title="$t('message.Batch')+' '+$t('application.Import')+$t('application.document')" :visible.sync="importDialogVisible" width="60%" >
        <ImportDocument ref="ImportDocument"  @onImported="onImported" width="100%" v-bind:deliveryId="deliveryId"></ImportDocument>
        <div slot="footer" class="dialog-footer">
          <el-button @click="importDialogVisible=false" size="medium">{{$t('application.close')}}</el-button>
         </div>
      </el-dialog>
    <el-form label-width="120px" @submit.native.prevent>
      <el-row>
         <el-col :span="8">
           取号测试
         </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="文档ID">
            <el-input type="text"  v-model="numData.id" ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="编码">
            <el-input type="text" v-model="numData.number"></el-input>
          </el-form-item>
        </el-col>
         <el-col :span="8">
           <el-button type="primary" plain icon="save" @click="newNumber()">取号</el-button> 
         </el-col>
      </el-row>
    </el-form>

    <el-form label-width="120px" @submit.native.prevent>
       <el-row>
         <el-col :span="8">
           目录测试
         </el-col>
      </el-row>
      <el-row>
        <el-col :span="6">
          <el-form-item label="文档ID">
            <el-input type="text"  v-model="pathData.id" ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="类型">
            <el-input type="text" v-model="pathData.type"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="路径">
            <el-input type="text" v-model="pathData.path"></el-input>
          </el-form-item>
        </el-col>
         <el-col :span="6">
           <el-button type="primary" plain icon="save" @click="getPath()">{{$t('application.close')}}</el-button> 
         </el-col>
      </el-row>
    </el-form>
    <el-form label-width="120px" @submit.native.prevent>
       <el-row>
         <el-col :span="8">
           文件夹创建测试
         </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="路径">
            <el-input type="text"  v-model="fullPathData.path" ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="结果">
            <el-input type="text" v-model="fullPathData.result"></el-input>
          </el-form-item>
        </el-col>
         <el-col :span="6">
           <el-button type="primary" plain icon="save" @click="buildFullPath()">创建路径</el-button> 
         </el-col>
      </el-row>
    </el-form>
    <el-form label-width="120px" @submit.native.prevent>
       <el-row>
         <el-col :span="8">
           重建fullpath
         </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="文件夹ID">
            <el-input type="text"  v-model="fullData.id" ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="结果">
            <el-input type="text" v-model="fullData.result"></el-input>
          </el-form-item>
        </el-col>
         <el-col :span="8">
           <el-button type="primary" plain icon="save" @click="buildPath()">重建路径</el-button> 
         </el-col>
      </el-row>
    </el-form>
    <el-form label-width="120px" @submit.native.prevent>
       <el-row>
         <el-col :span="8">
          批量导入
         </el-col>
      </el-row>
      <el-row>
        <el-input type="text"  v-model="deliveryId" ></el-input>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item :label="'Excel'+$t('message.file')">
            <el-upload
              :limit="1"
              :file-list="fileList1" 
              action=""
              :on-change="handleChange1"
              :auto-upload="false"
              :multiple="false">
              <el-button slot="trigger" size="small" type="primary">{{$t('application.selectFile')}}</el-button>
            </el-upload>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item :label="'Excel'+$t('message.ElectronicFiles')">
             <el-upload
              :limit="100"
              :file-list="fileList2" 
              action=""
              :on-change="handleChange2"
              :auto-upload="false"
              :multiple="true">
              <el-button slot="trigger" size="small" type="primary">{{$t('application.selectFile')}}</el-button>
            </el-upload>
          </el-form-item>
        </el-col>
         <el-col :span="8">
           <el-button type="primary" plain icon="save" @click="batchImport()">{{$t('application.Import')}}</el-button> 
           <el-button type="primary" plain icon="save" @click="importDialogVisible=true">批量导入</el-button> 
         </el-col>
      </el-row>
      <el-row>
         <el-col>
           <el-input type="textarea" :rows="6" v-model="importMessage"></el-input>
         </el-col>
      </el-row>
    </el-form>
    <el-form label-width="120px" @submit.native.prevent>
      <el-row>
         <el-col :span="8">
           浏览测试
         </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="文档ID">
            <el-input type="text"  v-model="viewData.id" ></el-input>
          </el-form-item>
          <el-form-item label="格式">
            <el-input type="text"  v-model="viewData.format" ></el-input>
          </el-form-item>
        </el-col>
         <el-col :span="8">
           <router-link :to="{path:'/dc/officedocviewer',query:{id:viewData.id,format:viewData.format}}"><el-button :plain="true" type="info" size="small" icon="edit">查看</el-button></router-link> 
         </el-col>
      </el-row>
    </el-form>
    <el-form label-width="120px" @submit.native.prevent>
      <el-row>
         <el-col :span="8">
           图像查看
         </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="文档ID">
            <el-input type="text"  v-model="imageId" ></el-input>
          </el-form-item>
        </el-col>
         <el-col :span="8">
           <router-link :to="{path:'/dc/imageviewer',query:{id:imageId}}"><el-button :plain="true" type="info" size="small" icon="edit">查看</el-button></router-link> 
         </el-col>
      </el-row>
    </el-form>
    <el-form label-width="120px" @submit.native.prevent>
      <el-row>
         <el-col :span="8">
           视频播放
         </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="视频ID">
            <el-input type="text"  v-model="videoData.id" ></el-input>
          </el-form-item>
          <el-form-item label="格式">
            <el-input type="text"  v-model="videoData.format" ></el-input>
          </el-form-item>
        </el-col>
         <el-col :span="8">
           <router-link :to="{path:'/dc/videoplayer',query:{id:videoData.id,format:videoData.format}}"><el-button :plain="true" type="info" size="small" icon="edit">查看</el-button></router-link> 
         </el-col>
      </el-row>
    </el-form>
    <el-form label-width="120px" @submit.native.prevent>
      <el-row>
         <el-col :span="8">
           音频播放
         </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="音频ID">
            <el-input type="text"  v-model="audioData.id" ></el-input>
          </el-form-item>
          <el-form-item label="格式">
            <el-input type="text"  v-model="audioData.format" ></el-input>
          </el-form-item>
        </el-col>
         <el-col :span="8">
           <router-link :to="{path:'/dc/audioplayer',query:{id:audioData.id,format:audioData.format}}"><el-button :plain="true" type="info" size="small" icon="edit">查看</el-button></router-link> 
         </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script type="text/javascript">
import ImportDocument from '@/components/controls/ImportDocument'

export default {
  name: "TestComp1",
  components: {
    ImportDocument:ImportDocument
  },
  permit: 1,
  data() {
    return {
      loading:false,
      imageId:"e99fda050aca4142b627cc4e7969586a",
      videoData:{
        id:"69c746af4cb1454486aff87a586b1275",
        format:"mp4"
      },
      audioData:{
        id:"ef4568f4e4ac40f9a9748d9682057a34",
        format:"mp3"
      },
      numData:{
        id:"",
        number:""
      },
      pathData:{
        id:"",
        type:"3",
        path:""
      },
      viewData:{
        id:"e5e61e08910241c69d3dd484cad98cf9",
        format:"docx"
      },
      fullData:{
        id:"",
        result:""
      },
      fullPathData:{
        path:"/移交文档/2019/12/12/01",
        result:""
      },
      fileList1:[],
      fileList2:[],
      importMessage:"",
      deliveryId:"",
      formLabelWidth: "120px",
      importDialogVisible: false
    };
  },
  methods: {
    newNumber(){
      let _self = this;
      _self.loading =true;
      axios.get("/test/newNumber/"+_self.numData.id).then(function(response){
        console.log(response);
        _self.numData.number = response.data.data;	   
        _self.loading = false;
      }).catch(function(error){
        console.log(error);
        _self.loading = false;
      });
    },
    buildFullPath(){
      let _self = this;
      _self.loading =true;
      let formdata = new FormData();
      formdata.append("folderPath", _self.fullPathData.path);
      axios.post("/test/createFolder",formdata).then(function(response){
        console.log(response);
        _self.fullPathData.path = response.data.data;	   
        _self.loading = false;
      }).catch(function(error){
        console.log(error);
        _self.loading = false;
      });
    },
    getPath(){
      let _self = this;
      _self.loading =true;
      axios.get("/test/getPath/"+_self.pathData.id+"/"+_self.pathData.type).then(function(response){
        console.log(response);
        _self.pathData.path = response.data.data;	   
        _self.loading = false;
      }).catch(function(error){
        console.log(error);
        _self.loading = false;
      });
    },
    buildPath(){
      let _self = this;
      _self.loading =true;
      axios.get("/test/builderPath/"+_self.fullData.id).then(function(response){
        _self.fullData.result = response.data.data;	   
        _self.loading = false;
      }).catch(function(error){
        console.log(error);
        _self.loading = false;
      });
    },
    handleChange1(file, fileList){
      this.fileList1 = fileList;
    },
    handleChange2(file, fileList){
      this.fileList2 = fileList;
    },
    batchImport(){
      let _self = this;
      let formdata = new FormData();
      let m = new Map();
      m.set("id",_self.deliveryId);
      formdata.append("metaData",JSON.stringify(m));
      formdata.append("excel",_self.fileList1[0].raw);
      _self.fileList2.forEach(function (file) {
          formdata.append("files", file.raw, file.name);
      });
      _self.loading =true;
       axios.post("/import/batchImport",formdata,{
            'Content-Type': 'multipart/form-data'
          })
        .then(function(response) {
          _self.importMessage = response.data.data;
          _self.loading = false;
        })
        .catch(function(error) {
          _self.$message(_self.$t('application.importFailed'));
          console.log(error);
        });
    },
    onImported(){
      console.log("onImported");
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
