//获取数据服务的基类
import { BaseService } from './base.js'

/**
* 子类继承
* 类中可自定义服务方法
*/
class UserService extends BaseService{
    //构造函数
    //默认可不用写，但如需要在初始化做更多的事情时可以补充构造函数,用super来获取基类构造函数
    //写法和Java类似
    constructor(baseurl){
        super(baseurl)
    }
}

let service = new UserService('/user');

export default service;
