"use strict";

import Vue from 'vue';
import axios from "axios";

// Full config:  https://github.com/axios/axios#request-config
// axios.defaults.baseURL = process.env.baseURL || process.env.apiUrl || '';
// axios.defaults.headers.common['Authorization'] = AUTH_TOKEN;
 axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8';
let config = {
  baseURL: '/zisecm'
  // baseURL: ''
  //'/zisecm'

  // baseURL: process.env.baseURL || process.env.apiUrl || ""
  // timeout: 60 * 1000, // Timeout
  // withCredentials: true, // Check cross-site Access-Control
};

const _axios = axios.create(config);

_axios.interceptors.request.use(function(config) {
   // 将接口返回的token信息配置到接口请求中
   config.headers.token = sessionStorage.getItem('access-token')
   config.headers['Access-Control-Allow-Origin'] = '*'
   config.headers['Access-Control-Allow-Credentials'] = 'true'
   
   return config
  },
  function(error) {
    // Do something with request error
    return Promise.reject(error);
  }
);

// Add a response interceptor
_axios.interceptors.response.use(function(response) {
	// 未登录跳转到登录页面
	if (response.data && response.data.code && response.data.code === '1001') {
	  // console.info(response.data)
	  // alert(response)
	  sessionStorage.removeItem('access-token')
	  sessionStorage.removeItem('access-user')
	  sessionStorage.removeItem('access-userName')
	  sessionStorage.removeItem('access-clientPermission')
	  window.location.href = '#/login'
	} else {
	  return response
	}
  },
  function(error) {
    // Do something with response error
    return Promise.reject(error);
  }
);

Plugin.install = function(Vue, options) {
  Vue.axios = _axios;
  window.axios = _axios;
  Object.defineProperties(Vue.prototype, {
    axios: {
      get() {
        return _axios;
      }
    },
    $axios: {
      get() {
        return _axios;
      }
    },
  });
};

Vue.use(Plugin)

export default Plugin;
