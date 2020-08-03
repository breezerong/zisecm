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

  Vue.prototype.validateData =function(data,callback){
    let _self=this;
    let url="/dc/validateOnly";
    axios.post(url,JSON.stringify(data)).then(function(response) {
        
        if(response.data.code=='1'){
          let isOk=response.data.isOk;
          if(callback){
            callback(isOk);
          }
        }
    }).catch(function(error){
        console.log(error);
    })
  }
  
// 删除文档事件
Vue.prototype.onDeleteByIds= function onDeleleItem(url,selectedItems,dataGridObj) {
  let _self = this;
  if(selectedItems.length==0){
    _self.$message({
                showClose: true,
                message: "请选择一条或多条要删除的数据！",
                duration: 2000,
                type: 'warning'
              });
            return;
  }
  
  _self.$confirm(
    _self.$t("message.deleteInfo"),
    _self.$t("application.info"),
    {
      confirmButtonText: _self.$t("application.ok"),
      cancelButtonText: _self.$t("application.cancel"),
      type: "warning"
    }
  )
    .then(() => {
      deleteItem(url,selectedItems,dataGridObj,_self);
    })
    .catch(() => {
      // this.$message({
      //   type: 'info',
      //   message: '已取消删除'
      // });
    });
}
// 删除文档
function deleteItem(url,selectedItems,dataGridObj,_this) {
  let _self = _this;
  var m = [];
  let tab = selectedItems;

  var i;
  for (i in tab) {
    m.push(tab[i]["ID"]);
  }
 
  axios.post(url,JSON.stringify(m),{//"/dc/delDocumentAndRelationCommon"
      headers: {
          "Content-Type": "application/json;charset=UTF-8"
        }
    })
    .then(function(response) {
      if(dataGridObj!=null&&dataGridObj!=undefined){
          dataGridObj.forEach(element => {
              if(element){
                  element.loadGridData();
              }
              
          });
      }
      _self.$message({
          showClose: true,
          message: _self.$t("message.deleteSuccess")+";"+response.data['msg'],
          duration: 2000,
          type: 'success'
      });
    })
    .catch(function(error) {
      
      _self.$message({
          showClose: true,
          message: _self.$t("message.deleteFailured"),
          duration: 5000,
          type: 'error'
      });
      console.log(error);
    });
}
  
