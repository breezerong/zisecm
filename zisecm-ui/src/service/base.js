/**
 * 服务基类
 * 处理最基本的增删改查
 * 使用的前提条件：
 * 1.在初始化时都需要定义一个数据的路径，如用户的/user,文件夹的/folder new UserService('/user')
 * 2.所有继承的子类在调用url上是有一定规律，如都有save、get、delete、update等相同名称的方法,那么ajax访问的路径是 '/user/get'
 * 
 */
class BaseService{
    constructor(baseurl){
        this.baseurl = baseurl;
        console.log("BaseService");      
    }
    get(id,callback){
        let params={id:id};
        let url = this.baseurl+"/get"
        axios.post(url,params).then(function(response){
          if(undefined != callback && typeof callback == 'function'){
            callback(response.data);
          }
        })
    }
    save(obj,callback){
        let url = this.baseurl+"/save"
        axios.post(url,obj).then(function(response){
          if(undefined != callback && typeof callback == 'function'){
            callback(response.data);
          }
        })
    }
    delete(id,callback){
        let params={id:id};
        let url = this.baseurl+"/delete"
        axios.post(url,params).then(function(response){
          if(undefined != callback && typeof callback == 'function'){
            callback(response.data);
          }
        })
    }
  /**
  *查询
  */
  find(params,callback){
    let url = this.baseurl+"/find"
    axios.post(url,params).then(function(response){
      if(undefined != callback && typeof callback == 'function'){
        callback(response.data);
      }
    })
  }
  /**
  *分页查询
  */
  page(params,callback){
        let url = this.baseurl+"/page"
        axios.post(url,params).then(function(response){
          if(undefined != callback && typeof callback == 'function'){
            callback(response.data);
          }
        })
    }
}

export {
    BaseService
}
