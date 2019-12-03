<template>
  <div>
      <div style="width:30%;display:inline-block;position: absolute; left:30px;">
        <el-select
            name="selectName"
            v-model="selectedPx"
            placeholder="'请选择背脊尺寸'"
            style="display:block;">
            <el-option label="2cm背脊" value="56" key='2'></el-option>
            <el-option label="3cm背脊" value="84" key='3'></el-option>
            <el-option label="4cm背脊" value="112" key='4'></el-option>
            <el-option label="5cm背脊" value="140" key='5'></el-option>
            <el-option label="6cm背脊" value="168" key='6'></el-option>
          </el-select>
          
      </div>
      <div style="display:inline-block;position: absolute;left:210px;">
        <button @click="printCode" v-print="'#print'">打印</button>
      </div>
      <div id='print' ref='print' :style="'height:100%;width:'+selectedPx+'px;position: absolute; top:130px;'">
        <div v-for="(citem,idx) in gridList">
          
            <div style="text-align:center;border:1px solid #030002;height:5%;font-family:'微软雅黑';font-size:17px;">
              {{citem.label}}
            </div>
            
               <div v-if="citem.attrName.indexOf('DATE')>0" style="text-align:center;border-bottom:1px solid #030002;
                 border-left:1px solid #030002;border-right:1px solid #030002;
                 height:200px;">
                    {{dateFormat(ridgeData.attributes[citem.attrName])}}&nbsp;
                </div>
                <div v-else-if="idx!=gridList.length-1" style="text-align:center;
                 border-left:1px solid #030002;border-right:1px solid #030002;height:200px;">
                    {{ridgeData.attributes[citem.attrName]}}&nbsp;
                </div> 
                <div v-else style="text-align:center;border-bottom:1px solid #030002;
                 border-left:1px solid #030002;border-right:1px solid #030002;height:200px;">
                    {{ridgeData.attributes[citem.attrName]}}&nbsp;
                </div> 
            
          </div>
  　　</div>
  </div>
</template>

<script type="text/javascript">
import Print from '@/plugins/print'
import Vue from 'vue';
import QRCode from 'qrcodejs2'// 引入qrcode
Vue.use(Print)
export default {
   name: 'test',
    
  // name: "printPage",
  data() {
    return {
      archiveTitle:"",
      archiveCode:"",
      innerDataList:[],
      dialogQrcodeVisible: true,
      currentLanguage: "zh-cn",
      gridList:[],
      volumeTitle:"",
      ridgeData:[],
      selectedPx:'112'
    };
  },
  mounted() {
    // 需要先显示出来，然后再隐藏掉；  否则动态生成的二维码，第一次会报错，对象找不到。可能是跟初始化有关系，没有显示出来的时候并没有初始化HTML
      // this.dialogQrcodeVisible = false
    this.currentLanguage = localStorage.getItem("localeLanguage") || "zh-cn";
    
    // this.loadFormInfo();
    // this.getArchiveObj(this.archiveId,this.gridName); 
    
  },
  props: {
    archiveId: {type:[String,Number]},
    currentFolderId:{type:[String,Number]},
    tableHeight:{type:Number},
    gridName:{type:String}
  },
  methods: {

      // 加载表格样式
    loadGridInfo(gridName) 
    {
      let _self = this;
      _self.loading = true;
      var m = new Map();
      m.set('gridName',gridName);
      m.set('lang',_self.currentLanguage);
      _self.axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        method: 'post',
        data: JSON.stringify(m),
        url: "/dc/getGridViewInfo"
      })
        .then(function(response) {
          
          _self.gridList = response.data.data;
         
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
      InnerFile(){
      let _self = this;
      
      var m = new Map();
      m.set('id',_self.archiveId);
      
      // console.log('pagesize:', _self.pageSize);
      _self
      .axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        method: "post",
        data: JSON.stringify(m),
        url: "/dc/getAllDocuByRelationParentId"
      })
      .then(function(response) {
        
        _self.innerDataList = response.data.data;
        
        _self.loading = false;
      })
      .catch(function(error) {
        console.log(error);
        _self.loading = false;
      });
    },
    getArchiveObj(id,gridName,volumeTitle){
      let _self=this;
      _self.volumeTitle=volumeTitle;
      _self.loadGridInfo(gridName); 
      var m = new Map();
      m.set('itemInfo',id);//ID 或类型
      m.set('lang',_self.currentLanguage);
      console.log(_self.itemId+","+_self.myItemId+","+_self.myTypeName+","+_self.folderId);
      _self.axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data: JSON.stringify(m),//_self.myItemId+_self.myTypeName,
          url: "/dc/getArchiveObj"
        })
        .then(function(response) {
          _self.ridgeData=response.data.data;
          _self.archiveCode= response.data.data.coding;
          _self.archiveTitle= response.data.data.title;
          _self.genarateQrcode(_self.archiveCode);
          
          // _self.InnerFile();
          //console.log(JSON.stringify(response.data.data));
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    printCode(){
      this.$print(this.$refs.print);
    },
    
    checkEwmClick (url) {
      let vm = this
      vm.$nextTick(() => {
        vm.dialogQrcodeVisible = true
        let obj = document.getElementById('qrcodeshow')
        obj.innerHTML = ''
        vm.genarateQrcode(url)
      })
    },
    handleDialogQrcodeClose () {
      this.dialogQrcodeVisible = false
    },
    genarateQrcode (url) {
      this.$refs.qrCodeUrl2.innerHTML='';
      let qrcode = new QRCode(this.$refs.qrCodeUrl2, {
        width: 50,
        height: 50,
        text: url, // 二维码地址
        colorDark: '#000',
        colorLight: '#fff',
        correctLevel: QRCode.CorrectLevel.H
      })
      console.log('qrcode = ' + JSON.stringify(qrcode))
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
/* .table-a table{border:1px solid #212121}  */
.v-auto-out .auto-in {
position: absolute;
top: 50%;
border: 1px dashed #ddd;
/* 这里有兼容性问题 */
-webkit-transform: translateY(-50%);
-ms-transform: translateY(-50%);
-o-transform: translateY(-50%);
transform: translateY(-50%);
}

  .bg-purple-dark {
    background: #99a9bf;
  }
  .bg-purple {
    background: #d3dce6;
  }
  .bg-purple-light {
    background: #e5e9f2;
  }
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }
  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
  }
/* 样式 */
  .table, .table * {margin: 0 auto; padding: 0;font-size: 14px;
  font-family: Arial, 宋体, Helvetica, sans-serif;}   
.table {display: table; width: 80%; border-collapse: collapse;/*border-bottom: 1px solid gray;*/}   
  
.table-tr {display: table-row; height: 30px;}   
.table-th {display: table-cell;font-weight: bold;height: 100%;/*border: 1px solid gray;*/text-align: center;vertical-align: middle;}   
.table-td {display: table-cell; height: 100%;}   
  
.sub-table {width: 100%;height: 100%;display: table;}   
.sub-table-tr {display: table-row; height: 100%;}   
.sub-table-td {display: table-cell; height: 100%;
/* border-top: 1px solid gray; 
border-left: 1px solid gray;
border-right: 1px solid gray; */
border-top: 1px solid #212121; 
border-left: 1px solid #212121;
border-right: 1px solid #212121;
/* border:1px solid #212121; */
text-align: center;vertical-align: middle;}
.sub-table-td1 {display: table-cell; height: 100%;
/* border-left: 1px solid #212121; */
text-align: center;vertical-align: middle;}
</style>
