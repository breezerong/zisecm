import Vue from 'vue'
Vue.prototype.currentUser = function(){
    let user = sessionStorage.getItem("ecm-current-user");
    if(user){
      return JSON.parse(user);
    }else{
      return null;
    }
  }

  Vue.prototype.setCurrentUser = function(data){
    sessionStorage.setItem(
      "ecm-current-user",
      JSON.stringify(data)
    );
  }

  Vue.prototype.userLogout = function(){
    sessionStorage.removeItem("ecm-current-user");
    sessionStorage.removeItem("access-token");
  }
  
