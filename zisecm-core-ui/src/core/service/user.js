
var service = {
	/**
	 * ABC
	 */
	getInfo:function(callback){
		callback({success:'success'});
	},
	
	list:function(params,callback){
		var url="/user/list";
		axios.post(url,params).then(res => {
			callback(res);
		});
	}
}

export default service;
