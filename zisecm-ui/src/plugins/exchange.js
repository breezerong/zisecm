// 删除文档事件
export function onDeleleItem(selectedItems,dataGridObj) {
    let _self = this;
    if(selectedItems.length==0){
      _self.$message({
                  showClose: true,
                  message: _self.$t("message.PleaseSelectDelete"),
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
        deleleItem(selectedItems,dataGridObj,_self);
      })
      .catch(() => {
        // this.$message({
        //   type: 'info',
        //   message: '已取消删除'
        // });
      });
  }
  // 删除文档
  function deleleItem(selectedItems,dataGridObj,_this) {
    let _self = _this;
    var m = [];
    let tab = selectedItems;

    var i;
    for (i in tab) {
      m.push(tab[i]["ID"]);
    }
   
    axios.post("/dc/delDocumentAndRelationCommon",JSON.stringify(m),{
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
            message: _self.$t("message.deleteSuccess"),
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

  export function nextStatus(selectedItems,mainDataGridObj,dataGridObj){
        let _self = this;
        var m = [];
        let tab = selectedItems;
        
        var i;
        for (i in tab) {
            m.push(tab[i]["ID"]);
        }
        let mp=new Map();
        mp.set("ids",m);
        mp.set("isCnpeSend","false");
        axios.post("/dc/nextStatus",JSON.stringify(mp),{
            headers: {
                "Content-Type": "application/json;charset=UTF-8"
            }
        })
        .then(function(response) {
            if(response.data.code==1){
                mainDataGridObj.loadGridData();
                if(dataGridObj!=null&&dataGridObj!=undefined){
                    dataGridObj.forEach(element => {
                        if(element){
                            element.itemDataList=[];
                        }
                        
                    });
                }
                _self.$message({
                    showClose: true,
                    message: _self.$t("message.operationSuccess"),
                    duration: 2000,
                    type: 'success'
                });
            }else{
                
                _self.$message({
                    showClose: true,
                    message: response.data.message,//_self.$t("message.operationFaild"),
                    duration: 5000,
                    type: 'error'
                });
            }
            
        })
        .catch(function(error) {
            
            _self.$message({
                showClose: true,
                message: _self.$t("message.operationFaild"),
                duration: 5000,
                type: 'error'
            });
            console.log(error);
        });
    }
    ///撤回
    export function withdraw(selectedItems,mainDataGridObj,dataGridObj){
        let _self = this;
        var m = [];
        let tab = selectedItems;

        var i;
        for (i in tab) {
            m.push(tab[i]["ID"]);
        }
        let mp=new Map();
        mp.set("ids",m);
        axios.post("/dc/withdraw",JSON.stringify(mp),{
            headers: {
                "Content-Type": "application/json;charset=UTF-8"
            }
        })
        .then(function(response) {
            if(response.data.code==1){
                mainDataGridObj.loadGridData();
                if(dataGridObj!=null||dataGridObj!=undefined){
                    dataGridObj.forEach(element => {
                        element.itemDataList=[];
                    });
                }
                _self.$message({
                    showClose: true,
                    message: _self.$t("message.rollbackSuccess"),
                    duration: 2000,
                    type: 'success'
                });
                
            }else{
                
                _self.$message({
                    showClose: true,
                    message: _self.$t("message.operationFaild"),
                    duration: 5000,
                    type: 'error'
                });
            }
            
        })
        .catch(function(error) {
            
            _self.$message({
                showClose: true,
                message: _self.$t("message.operationFaild"),
                duration: 5000,
                type: 'error'
            });
            console.log(error);
        });
    }

    export function previousStatus(selectedItems,mainDataGridObj,dataGridObj){
        let _self = this;
        var m = [];
        let tab = selectedItems;
        
        var i;
        for (i in tab) {
            m.push(tab[i]["ID"]);
        }
        let mp=new Map();
        mp.set("ids",m);
        mp.set("isCnpeSend","false");
        axios.post("/dc/previousStatus",JSON.stringify(mp),{
            headers: {
                "Content-Type": "application/json;charset=UTF-8"
            }
        })
        .then(function(response) {
            if(response.data.code==1){
                mainDataGridObj.loadGridData();
                if(dataGridObj!=null&&dataGridObj!=undefined){
                    dataGridObj.forEach(element => {
                        if(element){
                            element.itemDataList=[];
                        }
                        
                    });
                }
                _self.$message({
                    showClose: true,
                    message: _self.$t("message.rollbackSuccess"),
                    duration: 2000,
                    type: 'success'
                });
                
            }else{
                
                _self.$message({
                    showClose: true,
                    message: _self.$t("message.operationFaild"),
                    duration: 5000,
                    type: 'error'
                });
            }
            
        })
        .catch(function(error) {
            
            _self.$message({
                showClose: true,
                message: _self.$t("message.operationFaild"),
                duration: 5000,
                type: 'error'
            });
            console.log(error);
        });
    }
    //cnpe分发上一状态
    export function previousStatusCnpe(selectedItems,mainDataGridObj,dataGridObj)
    {
        let _self = this;
        var m = [];
        let tab = selectedItems;
        
        var i;
        for (i in tab) {
            m.push(tab[i]["ID"]);
        }
        let mp=new Map();
        mp.set("ids",m);
        axios.post("/dc/previousStatusCnpe",JSON.stringify(mp),{
            headers: {
                "Content-Type": "application/json;charset=UTF-8"
            }
        })
        .then(function(response) {
            if(response.data.code==1){
                mainDataGridObj.loadGridData();
                if(dataGridObj!=null&&dataGridObj!=undefined){
                    dataGridObj.forEach(element => {
                        if(element){
                            element.itemDataList=[];
                        }
                        
                    });
                }
                _self.$message({
                    showClose: true,
                    message: _self.$t("message.rollbackSuccess"),
                    duration: 2000,
                    type: 'success'
                });
                
            }else{
                
                _self.$message({
                    showClose: true,
                    message: _self.$t("message.operationFaild"),
                    duration: 5000,
                    type: 'error'
                });
            }
            
        })
        .catch(function(error) {
            
            _self.$message({
                showClose: true,
                message: _self.$t("message.operationFaild"),
                duration: 5000,
                type: 'error'
            });
            console.log(error);
        });
    }
    //cnpe分发下一状态
    export function nextStatusCnpe(selectedItems,mainDataGridObj,dataGridObj)
    {
        let _self = this;
        var m = [];
        let tab = selectedItems;
        
        var i;
        for (i in tab) {
            m.push(tab[i]["ID"]);
        }
        let mp=new Map();
        mp.set("ids",m);
        axios.post("/dc/nextStatusCnpe",JSON.stringify(mp),{
            headers: {
                "Content-Type": "application/json;charset=UTF-8"
            }
        })
        .then(function(response) {
            if(response.data.code==1){
                mainDataGridObj.loadGridData();
                if(dataGridObj!=null&&dataGridObj!=undefined){
                    dataGridObj.forEach(element => {
                        if(element){
                            element.itemDataList=[];
                        }
                        
                    });
                }
                
                _self.$message({
                    showClose: true,
                    message: _self.$t("message.commitSuccess"),
                    duration: 2000,
                    type: 'success'
                });
            }else{
                
                _self.$message({
                    showClose: true,
                    message: _self.$t("message.operationFaild"),
                    duration: 5000,
                    type: 'error'
                });
            }
            
        })
        .catch(function(error) {
            
            _self.$message({
                showClose: true,
                message: _self.$t("message.operationFaild"),
                duration: 5000,
                type: 'error'
            });
            console.log(error);
        });
    }

    