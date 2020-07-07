export default {
  getAccount:function(){
    return sessionStorage.getItem("access-user");
  },
  getUserName:function(){
    return sessionStorage.getItem("access-userName");
  },
  getUserType:function(){
    return sessionStorage.getItem("access-userType");
  },
  getDepartment:function(){
    return sessionStorage.getItem("access-department");
  },
  getCompany:function(){
    return sessionStorage.getItem("access-company");
  },
  getToken:function(){
    return sessionStorage.getItem("access-token");
  },
  getClientPermission:function(){
    return sessionStorage.getItem("access-clientPermission");
  },
  getSystemPermission:function(){
    return sessionStorage.getItem("access-systemPermission");
  }
}