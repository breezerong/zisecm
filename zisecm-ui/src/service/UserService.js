import { BaseService } from './base.js'

class UserService extends BaseService{
    constructor(baseurl){
        super(baseurl)
    }
}

let service = new UserService('/user');
export default service;
