import Vue from 'vue'
import Vuex from 'vuex'
import './plugins/axios'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
	token:'',
	workflow:{
		todocount:0,
		docount:0
	}
  },
  mutations: {
	set_token(state,token){
		state.token = token
		sessionStorage.token = token
	},
	del_token(state){
		state.token = ''
		sessionStorage.removeItem('access-token')
		sessionStorage.removeItem('access-user')
	}
  },
  actions: {		
	
  }
})
