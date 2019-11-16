import Vue from 'vue'
import App from './App.vue'
import router from '@/router/index.js'
import store from './store'
import i18n from './assets/locales'
import './plugins/element.js'
import './plugins/axios.js'
import './plugins/core.js'
import './plugins/formatter.js'
import './assets/styles/main.css'
import echarts from 'echarts'

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
