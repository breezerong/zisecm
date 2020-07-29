<template>
  <div>

     <!-- <el-table
      :data="tableData"
      style="width: 100%">
      <el-table-column
        prop="id"
        label="id"
        width="180">
      </el-table-column>
      <el-table-column
        prop="name"
        label="姓名"
        width="180">
      </el-table-column>
      <el-table-column
        prop="address"
        label="地址">
      </el-table-column>
      <el-table-column
        fixed="right"
        label="二维码"
        width="100">
        <template slot-scope="scope">
          <el-button @click="checkEwmClick(scope.row)" type="text" size="small">查看</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog
      title="二维码"
      :visible.sync="dialogQrcodeVisible"
      width="30%"
      :before-close="handleDialogQrcodeClose">
      <div style="text-align:center; margin-left:auto; margin-right:auto;">
        <div id="qrcodeshow" ref="qrCodeUrl"></div> 创建一个div，并设置id为qrcode -->
      <!-- </div>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogQrcodeVisible = false">{{$t('application.close')}}</el-button>
      </span>
    </el-dialog> -->

      <div id='print' ref='print' style="height:100%;">

        <!-- <div class="table-a"> 
          <table width="100%" cellspacing="0" cellpadding="0"> 
          <tr> 
          <td width="105" style="border:1px solid #212121;">案卷号</td> 
          <td width="181" style="border-right:#212121 solid 1px;border-bottom:#212121 solid 1px;">11111111</td> 
          </tr> 
          <tr> 
          <td colspan="2" style="height:600px;">案卷111111111111111</td> 
          
          </tr> 
           
          </table> 
          </div>  -->

        <div class="table">
          <div class="table-tr" style="height:80px;">  
              
          </div> 
          <div class="table-tr">  
              <div class="table-td">  
                  <div class="sub-table">  
                      <div class="sub-table-tr">  
                          <div class="sub-table-td" style="width: 40%;">案卷号</div>  
                          <div class="sub-table-td" style="width: 30%;">{{archiveCode}}</div>  
                          
                      </div>  
                  </div>  
              </div>  
          </div>  
          <div class="table-tr">  
              <div class="table-td">  
                  <div class="sub-table">  
                      <div class="sub-table-tr">  
                          <div class="sub-table-td" style="width: 70%;height:800px;">{{archiveTitle}}</div>  
                          
                      </div>  
                  </div>  
              </div>  
          </div>  
      </div>    
             
            <!-- <el-table
                :data="tableData"
                style="width: 100%">
                <el-table-column
                  prop="id"
                  label="id"
                  width="180">
                </el-table-column>
                <el-table-column
                  prop="name"
                  label="姓名"
                  width="180">
                </el-table-column>
                <el-table-column
                  prop="address"
                  label="地址">
                </el-table-column>
                <el-table-column
                  fixed="right"
                  label="二维码"
                  width="100">
                  <template slot-scope="scope">
                    <el-button @click="checkEwmClick(scope.row)" type="text" size="small">查看</el-button>
                  </template>
                </el-table-column>
              </el-table> -->
          <div ref='qrCodeUrl2'></div>
  　　　　</div>

  　　　　<button @click="printCode" v-print="'#print'">打印</button>
  </div>
</template>

<script type="text/javascript">
import Print from '@/plugins/print'
import Vue from 'vue';
import QRCode from 'qrcodejs2'// 引入qrcode
Vue.use(Print)
export default {
   name: 'test',
    mounted () {
      // 需要先显示出来，然后再隐藏掉；  否则动态生成的二维码，第一次会报错，对象找不到。可能是跟初始化有关系，没有显示出来的时候并没有初始化HTML
      this.dialogQrcodeVisible = false
    },
  // name: "printPage",
  data() {
    return {
      archiveTitle:"",
      archiveCode:"",
      dialogQrcodeVisible: true,
      currentLanguage: "zh-cn"
    };
  },
  mounted() {
    this.currentLanguage = localStorage.getItem("localeLanguage") || "zh-cn";
    
    // this.loadFormInfo();
    this.getArchiveObj(this.archiveId); 
    
  },
  props: {
    archiveId: {type:[String,Number],required:true}
  },
  methods: {
    getArchiveObj(id){
      let _self=this;
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
          url: "/zisecm/dc/getArchiveObj"
        })
        .then(function(response) {

          _self.archiveCode= response.data.data.coding;
          _self.archiveTitle= response.data.data.title;
          _self.genarateQrcode(_self.archiveCode);
          //console.log(JSON.stringify(response.data.data));
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    printCode(){
      this.$print(this.$refs.print)
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
.table-a table{border:1px solid #212121} 
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
.table {display: table; width: 80%; border-collapse: collapse;border-bottom: 1px solid gray;}   
  
.table-tr {display: table-row; height: 30px;}   
.table-th {display: table-cell;font-weight: bold;height: 100%;border: 1px solid gray;text-align: center;vertical-align: middle;}   
.table-td {display: table-cell; height: 100%;}   
  
.sub-table {width: 100%;height: 100%;display: table;}   
.sub-table-tr {display: table-row; height: 100%;}   
.sub-table-td {display: table-cell; height: 100%;
border-top: 1px solid gray; 
border-left: 1px solid gray;
border-right: 1px solid gray;
text-align: center;vertical-align: middle;} 
</style>
