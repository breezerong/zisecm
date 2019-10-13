// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import './assets/icons/iconfont.css'
import './assets/styles/main.css'
import axios from 'axios'
import i18n from './assets/locales'
import ImageViewer from 'v-viewer'
import 'viewerjs/dist/viewer.css'
import echarts from 'echarts'

// Vue.prototype.baseURL = 'http://WIN-AMOKGB2RO5G:8080'
Vue.prototype.echarts = echarts
Vue.prototype.axios = axios
Vue.config.productionTip = false
Vue.use(ElementUI)
//Vue.use(echarts)
Vue.use(ImageViewer)
Vue.prototype.getLang = function (){
  return localStorage.getItem("localeLanguage") || "zh-cn"
}
Vue.prototype.dateFormat = function(val){
  let datetime = val;
  // console.log(val);
  if (datetime){
    if(typeof datetime === 'string'){
      datetime = new Date(datetime.replace(/-/g,'/').replace(/T|Z/g,' ').replace('.000+0000','').trim());
    }else if(typeof datetime === 'object'){
      datetime = new Date(datetime);
    }
    let y = datetime.getFullYear() + "-"
    let mon = datetime.getMonth() + 1
    mon = mon<10 ? '0' + mon : mon
    mon = mon + "-"
    let d = datetime.getDate()
    d = d<10 ? '0' + d : d
    return y + mon + d 
  }
  return ""
}
Vue.prototype.datetimeFormat = function(val){
  let datetime = val;
  // console.log(val);
  if (datetime){
    if(typeof datetime === 'string'){
      datetime = new Date(datetime.replace(/-/g,'/').replace(/T|Z/g,' ').replace('.000+0000','').trim());
    }else if(typeof datetime === 'object'){
      datetime = new Date(datetime);
    }
    let y = datetime.getFullYear() + "-"
    let mon = datetime.getMonth() + 1
    mon = mon<10 ? '0' + mon : mon
    mon = mon + "-"
    let d = datetime.getDate()
    d = d<10 ? '0' + d : d
    let hour = datetime.getHours() 
    hour = hour<10 ? '0' + hour : hour
    hour = hour + ':'
    let min = datetime.getMinutes()
    min = min<10 ? '0' + min : min
    min = min + ":"
    let sec = datetime.getSeconds()
    sec = sec<10 ? '0' + sec : sec
    return y + mon + d + ' ' + hour + min + sec
  }
  return ""
}
ImageViewer.setDefaults({
  Options: {
    inline: true,
    button: true,
    navbar: true,
    title: true,
    toolbar: true,
    tooltip: true,
    movable: true,
    zoomable: true,
    rotatable: true,
    scalable: true,
    transition: true,
    fullscreen: true,
    keyboard: true,
    url: 'data-source',
    zIndex: 9999
  }
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  i18n,
  router,
  template: '<App/>',
  components: {App}
})

// http request 拦截器
axios.interceptors.request.use(function (config) {
  // 将接口返回的token信息配置到接口请求中
  config.headers.token = sessionStorage.getItem('access-token')
  config.headers['Access-Control-Allow-Origin'] = '*'
  config.headers['Access-Control-Allow-Credentials'] = 'true'
  // config.headers['Access-Control-Allow-Headers'] = 'X-Requested-With,Content-Type'
  // res.header("Access-Control-Allow-Methods","PUT,POST,GET,DELETE,OPTIONS");
  // console.log('axios request:' + config.headers.token)
  return config
}, function (error) {
  return Promise.reject(error)
})
// http response 拦截器
axios.interceptors.response.use(function (response) {
  // 未登录跳转到登录页面
  if (response.data && response.data.code && response.data.code === '1001') {
    // console.info(response.data)
    // alert(response)
    sessionStorage.removeItem('access-token')
    sessionStorage.removeItem('access-user')
    sessionStorage.removeItem('access-userName')
    sessionStorage.removeItem('access-clientPermission')
    window.location.href = '/Login'
  } else {
    return response
  }
}, function (error) {
  return Promise.reject(error)
})
