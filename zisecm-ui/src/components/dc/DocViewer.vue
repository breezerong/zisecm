<template>
  <div>
    <el-dialog :title="$t('application.property')" :visible.sync="propertyVisible" @close="propertyVisible = false">
      <ShowProperty ref="ShowProperty"  @onSaved="onSaved" width="560" v-bind:itemId="selectedItemId" v-bind:folderId="currentFolder.id" v-bind:typeName="currentFolder.typeName"></ShowProperty>
      <div slot="footer" class="dialog-footer">
        <el-button @click="saveItem">{{$t('application.save')}}</el-button> <el-button @click="propertyVisible = false">{{$t('application.cancel')}}</el-button>
      </div>
    </el-dialog>
    <el-row>
      {{$t('field.name')}}: {{objectName}}
    </el-row>
    <el-row>
      <el-col :span="20" style="align:left;">
        <el-tabs @tab-click="handleTabClick">
          <el-tab-pane label="$t('application.viewContent')" name="viewcontent">
            <el-main>
              <transition name="fade" mode="out-in">
                <router-view :key="$route.fullPath"></router-view>
              </transition>
            </el-main>
          </el-tab-pane>
          <el-tab-pane label="$t('application.viewProperty')" name="viewproperty">

          </el-tab-pane>
          <el-tab-pane label="$t('application.viewRevision')" name="viewrevision">

          </el-tab-pane>
          <el-tab-pane label="$t('application.viewRedition')" name="viewredition">

          </el-tab-pane>
          <el-tab-pane label="$t('application.viewAttachment')" name="viewattachment">

          </el-tab-pane>
        </el-tabs>
      </el-col>
      <el-col :span="4" style="align:left;">
        <el-collapse v-model="activeName" accordion>
          <el-collapse-item title="$t('application.relationTo')" name="relationto">
            
          </el-collapse-item>
          <el-collapse-item title="$t('application.relationFrom')" name="relationfrom">
            
          </el-collapse-item>
        </el-collapse>
      </el-col>
    </el-row>
  </div>
</template>

<script type="text/javascript">
import ShowProperty from '@/components/dc/ShowProperty'

export default {
  name: "DocViewer",
  components: {
    ShowProperty: ShowProperty
  },
  data() {
    return {
      objectName: "",
      activeName: ["relationto","relationfrom"],
      imageFormat: 'jpg,jpeg,bmp,gif,png',
      baseServerUrl: this.baseURL,
      gridList: [],
      dataListFull: "",
      inputkey: "",
      loading: false,
      dialogVisible: false,
      propertyVisible: false,
      contentHeight: window.innerHeight - 178,
      paraName:""
    };
  },
  created() {
    let _self = this;
    _self.paraName = _self.$route.query.paraName;
    console.log(_self.paraName);
  },
  methods: {
    handleTabClick(tab, event) {
      console.log(tab, event);
    },
    // 查看属性
    showItemProperty(indata){
      let _self = this;
      _self.selectedItemId = indata.ID ;
      _self.propertyVisible = true;
      if(_self.$refs.ShowProperty){
        _self.$refs.ShowProperty.myItemId = indata.ID ;
        _self.$refs.ShowProperty.loadFormInfo();
      }
    },
    // 查看内容
    showItemContent(indata){
      let _self = this;
      _self.imageArray = [];
      _self.currentType = indata.FORMAT_NAME;
      // 拦截器会自动替换成目标url
      _self.imageArray[0] =  "/zisecm/dc/getContent?id="+indata.ID+"&token="+sessionStorage.getItem('access-token');
      if(_self.currentType == "pdf"){
         window.open("./static/pdfviewer/web/viewer.html?file="+encodeURIComponent(_self.imageArray[0])+"&.pdf");
      }else{
         _self.imageViewVisible =true;
      }
    },
    closepdf(){
      this.imageViewVisible=false
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
