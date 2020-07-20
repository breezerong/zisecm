//打包下载
import Vue from 'vue'
Vue.prototype.packDownloadByMain = function(selectedItems){
    var m = [];
    let tab = selectedItems;
    let _self=this;
    var i;
    for (i in tab) {
      m.push(tab[i]["ID"]);
    }
    window.open(
        _self.axios.defaults.baseURL +
          "/dc/downloadFileByMain?objectIds=" +
          m+"&token="+sessionStorage.getItem('access-token'),
        "_blank"
      );
    // axios.post("/downloadFileByMain",JSON.stringify(m),{
    //     headers: {
    //         "Content-Type": "application/json;charset=UTF-8"
    //       }
    //   })
    //   .then(function(response) {
        
    //     _self.$message({
    //         showClose: true,
    //         message: _self.$t("message.DownLoadSuccess"),
    //         duration: 2000,
    //         type: 'success'
    //     });
    //   })
    //   .catch(function(error) {
        
    //     _self.$message({
    //         showClose: true,
    //         message: _self.$t("message.DownLoadFaild"),
    //         duration: 5000,
    //         type: 'error'
    //     });
    //     console.log(error);
    //   });
}

    //打包下载子文件/dc/downloadSubFile
    Vue.prototype.packDownloadSubFile = function(selectedItems){
        var m = [];
        let tab = selectedItems;
        let _self=this;
        var i;
        for (i in tab) {
          m.push(tab[i]["ID"]);
        }
        window.open(
            _self.axios.defaults.baseURL +
              "/dc/downloadSubFile?objectIds=" +
              m+"&token="+sessionStorage.getItem('access-token'),
            "_blank"
          );
        // axios.post("/dc/downloadSubFile",JSON.stringify(m),{
        //     headers: {
        //         "Content-Type": "application/json;charset=UTF-8"
        //       }
        //   })
        //   .then(function(response) {
            
        //     _self.$message({
        //         showClose: true,
        //         message: _self.$t("message.DownLoadSuccess"),
        //         duration: 2000,
        //         type: 'success'
        //     });
        //   })
        //   .catch(function(error) {
            
        //     _self.$message({
        //         showClose: true,
        //         message: _self.$t("message.DownLoadFaild"),
        //         duration: 5000,
        //         type: 'error'
        //     });
        //     console.log(error);
        //   });
    }
    Vue.prototype.getRelatinItemByTypeName =function(typeName,gridObj,callback){
      let _self =  this;
      let url="/admin/uirelation/get"
      var m = new Map();
      m.set("typeName", typeName);
      axios.post(url,JSON.stringify(m)).then(function(response) {
          let relationItem = response.data.data;
          // _self.relation = relationItem;
          gridObj.gridViewName=relationItem.gridName;
          gridObj.formName=relationItem.formName;
          if(callback){
            callback(relationItem);
          }
          gridObj.loadGridInfo();
          gridObj.loadGridData();
      }).catch(function(error){
          console.log(error);
      })
  }