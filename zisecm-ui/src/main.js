import Vue from 'vue'
import App from './App.vue'

import router from '@/router/index.js'
import store from './store'
import i18n from './assets/locales'
import './plugins/element.js'
import './plugins/axios.js'
import './plugins/formatter.js'
import './plugins/userInfo.js'
import './plugins/packageDownload.js'
import echarts from 'echarts'
import addToShoppingCar from './plugins/shoppingcar'
import validataPermission from './plugins/validatapermission'
import {onDeleleItem,nextStatus,previousStatus,withdraw,previousStatusCnpe,nextStatusCnpe} from './plugins/exchange'

import ImgViewer from 'v-viewer'
import 'viewerjs/dist/viewer.css'
import Video from 'video.js'
import 'video.js/dist/video-js.css'

import "babel-polyfill"
import splitPane from 'vue-splitpane'

import 'normalize.css/normalize.css'// A modern alternative to CSS resets
import '@/styles/index.scss' // global css
import '@/icons'
import { global } from '@/global/global'

 import '@/assets/icons/iconfont.css'
// import '@/assets/icons2/iconfont.css'
// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18
  
Date.prototype.Format = function (fmt) { // author: meizz
  var o = {
    "M+": this.getMonth() + 1, // 月份
    "d+": this.getDate(), // 日
    "h+": this.getHours(), // 小时
    "m+": this.getMinutes(), // 分
    "s+": this.getSeconds(), // 秒
    "q+": Math.floor((this.getMonth() + 3) / 3), // 季度
    "S": this.getMilliseconds() // 毫秒
  };
  if (/(y+)/.test(fmt))
    fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
  for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
      return fmt;
}



Vue.component('split-pane', splitPane);
Vue.prototype.$video = Video
Vue.prototype.addToShoppingCar=addToShoppingCar;
Vue.prototype.validataPermission=function(){return validataPermission};
Vue.prototype.onDeleleItem=onDeleleItem;
Vue.prototype.onNextStatus=nextStatus;
Vue.prototype.onPreviousStatus=previousStatus;
Vue.prototype.onWithdraw=withdraw;
Vue.prototype.onPreviousStatusCnpe=previousStatusCnpe;
Vue.prototype.onNextStatusCnpe=nextStatusCnpe;
Vue.prototype.ownerCompany='中国核电工程有限公司';

Vue.prototype.setStorageNumber = function(key,val){
  localStorage.setItem("ziecm-"+key, val);
}

Vue.prototype.getStorageNumber = function(key,defaultVal){
  let n = localStorage.getItem("ziecm-"+key);
  if(n && n != null){
    return Number(n);
  }
  return defaultVal;
}

Vue.use(ImgViewer)
Vue.config.productionTip = false
Vue.prototype.getLang = function (){
  let lang = localStorage.getItem("localeLanguage") || "zh-cn"
  return lang == ''?'zh-cn':lang
}
// 加载用户主题
if (localStorage.getItem('themeValue')) {
  global.changeTheme(localStorage.getItem('themeValue'))
} else {
  global.changeTheme('default')
}
Vue.prototype.echarts = echarts
//动态标题
Vue.directive('title', {
  inserted: function (el, binding) {
    document.title = el.dataset.title
  }
})

new Vue({
  router,
  store,
  i18n,
  render: h => h(App)
}).$mount('#app')
