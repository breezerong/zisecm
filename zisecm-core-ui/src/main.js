import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import i18n from './assets/locales'
import './plugins/element.js'
import './plugins/axios.js'
import './plugins/core.js'
import './assets/styles/main.css'

Vue.config.productionTip = false
Vue.prototype.getLang = function (){
  return localStorage.getItem("localeLanguage") || "zh-cn"
}

new Vue({
  router,
  store,
  i18n,
  render: h => h(App)
}).$mount('#app')
