
function addToShoppingCar(selectedItemList){
    let _self = this;
      var m = new Map();
      var addItemId = [];
      if (selectedItemList.length > 0) {
        
        for (var i = 0; i <selectedItemList.length; i++) {
            addItemId.push(selectedItemList[i].ID);
          }

        axios
          .post("/dc/addToShopingCart", JSON.stringify(addItemId))
          .then(function(response) {
            if (response.data.code) {
              _self.$message({
                showClose: true,
                message: "添加成功!",
                duration: 2000,
                type: "success"
              });
            } else {
              _self.$message({
                showClose: true,
                message: "添加失败!",
                duration: 2000,
                type: "warning"
              });
            }
          });
      } else {
        this.$message({
          showClose: true,
          message: "请勾选待添加文件!",
          duration: 2000
        });
      }
}
export default addToShoppingCar

