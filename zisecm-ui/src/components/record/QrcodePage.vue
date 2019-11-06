<template>
  <div>
    <el-table
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
        <div id="qrcodeshow" ref="qrCodeUrl"></div> <!-- 创建一个div，并设置id为qrcode -->
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogQrcodeVisible = false">关闭</el-button>
      </span>
    </el-dialog>
  </div>
</template>
 
<script>
import QRCode from 'qrcodejs2'// 引入qrcode
export default {
  name: 'test',
  mounted () {
    // 需要先显示出来，然后再隐藏掉；  否则动态生成的二维码，第一次会报错，对象找不到。可能是跟初始化有关系，没有显示出来的时候并没有初始化HTML
    this.dialogQrcodeVisible = false
  },
  data () {
    return {
      dialogQrcodeVisible: true,
      tableData: [{
        id: '1',
        name: '百度',
        address: '上海市普陀区金沙江路 1518 弄',
        url: 'http://www.baidu.com'
      }, {
        id: '2',
        name: 'QQ',
        address: '上海市普陀区金沙江路 1517 弄',
        url: 'http://www.qq.com'
      }, {
        id: '3',
        name: '163.COM',
        address: '上海市普陀区金沙江路 1519 弄',
        url: 'http://www.163.com'
      }, {
        id: '4',
        name: '淘宝',
        address: '上海市普陀区金沙江路 1516 弄',
        url: 'http://www.taobao.com'
      }]
    }
  },
  methods: {
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
      let qrcode = new QRCode(this.$refs.qrCodeUrl, {
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
}
</script>