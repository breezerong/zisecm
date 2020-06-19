
function validataPermission(){
    let _self = this;
    var m = new Map();
    var username = sessionStorage.getItem("access-userName")
    m.set("username",username);
     axios.post("/user/validatapermission",JSON.stringify(m)).then(function(response){
         let code= response.data.code;
         let haspermission=false;
         if(code==1){
            haspermission = response.data.data;
         }
         
        return haspermission;
        
    })
}
export default validataPermission


