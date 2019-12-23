import Vue from 'vue'
import App from './App.vue'
import router from '@/router/index.js'
import store from './store'
import i18n from './assets/locales'
import './plugins/element.js'
import './plugins/axios.js'
import './plugins/formatter.js'
import echarts from 'echarts'
import './assets/icons/iconfont.css'
import ImgViewer from 'v-viewer'
import 'viewerjs/dist/viewer.css'
import Video from 'video.js'
import 'video.js/dist/video-js.css'
import './assets/styles/main.css'
import "babel-polyfill"
import splitPane from 'vue-splitpane'

Vue.component('split-pane', splitPane);
Vue.prototype.$video = Video
Vue.use(ImgViewer)
Vue.config.productionTip = false
Vue.prototype.getLang = function (){
  let lang = localStorage.getItem("localeLanguage") || "zh-cn"
  return lang == ''?'zh-cn':lang
}
Vue.prototype.echarts = echarts

new Vue({
  router,
  store,
  i18n,
  render: h => h(App)
}).$mount('#app')
