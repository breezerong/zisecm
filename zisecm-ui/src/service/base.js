class BaseService{
    constructor(baseurl){
        this.baseurl = baseurl;
        console.log("BaseService");      
    }
    get(id,callback){
        let params={id:id};
        let url = this.baseurl+"/get"
        if(undefined != callback && typeof callback == 'function'){
            callback({msg:"abc"});
        }
    }
    save(obj,callback){
        let url = this.baseurl+"/save"
        console.log(url)
        if(undefined != callback && typeof callback == 'function'){
           callback({msg:"abc"});
        }
    }
    delete(id,callback){
        let params={id:id};
        let url = this.baseurl+"/delete"
        if(undefined != callback && typeof callback == 'function'){
            callback({msg:"abc"});
        }
    }
}

export {
    BaseService
}
