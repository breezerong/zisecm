var excel = {
  /**
   * 
   * @param {*} params 
   */
  export:function(params){
    let url = "/exchange/doc/export"
    axios.post(url,params,{ responseType: "blob"}).then(function(res){
      let fileName = res.headers["content-disposition"].split(";")[1].split("=")[1].replace(/\"/g, "");
      let type = res.headers["content-type"];
      let blob = new Blob([res.data], { type: type });
      // IE
      if (window.navigator.msSaveBlob) {
        window.navigator.msSaveBlob(blob, fileName);
      } else {
        var link = document.createElement("a");
        link.href = window.URL.createObjectURL(blob);
        link.download = fileName;
        link.click();
        //释放内存
        window.URL.revokeObjectURL(link.href);
      }
    }).catch(function(error){
      console.log(error)
    })
  }
}

export default excel