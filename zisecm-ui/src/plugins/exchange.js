// 删除文档事件
export function onDeleleItem(selectedItems,dataGridObj) {
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
        deleleItem(selectedItems,dataGridObj);
      })
      .catch(() => {
        // this.$message({
        //   type: 'info',
        //   message: '已取消删除'
        // });
      });
  }
  // 删除文档
  function deleleItem(selectedItems,dataGridObj) {
    let _self = this;
    var m = [];
    let tab = selectedItems;

    var i;
    for (i in tab) {
      m.push(tab[i]["ID"]);
    }
   
    axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        method: "post",
        data: JSON.stringify(m),
        url: "/dc/delDocumentAndRelationCommon"
      })
      .then(function(response) {
        dataGridObj.loadGridData();

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