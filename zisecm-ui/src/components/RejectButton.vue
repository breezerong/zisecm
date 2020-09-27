<template>
    <div>
        <el-dialog :title="$t('application.Rejected')" :visible.sync="showDialog" width="50%" @close="showDialog=false">
            <el-input
              type="textarea"
              :rows="5"
              :placeholder="$t('message.pleaseInput')+$t('application.Rejected')+' '+$t('message.reason')"
              v-model="rejectComment">
            </el-input>
            <div slot="footer" class="dialog-footer">
                <el-button
                @click="onOk()"
                >{{$t('application.ok')}}</el-button>
            </div>
        </el-dialog>
        <el-button type="warning" @click="clickShowDialog">{{$t('application.Rejected')}}</el-button>

    </div>
</template>
<script>
export default {
    name: "Reject",
    data() {
        
        return {
            rejectComment:"",
            showDialog:false,
        }
    },
    mounted() {
        // this.getTypeNames("AdvSearchTypes");
    },
    methods:{
        clickShowDialog(){
            let _self=this;
           
            _self.showDialog=true;
        },
        onOk(){
            let _self=this;
            if(_self.isSubObj){
                _self.rejectSub();
            }else if(_self.isIED){
                _self.rejectByCnpe();
            }else{
                if(_self.isRejectByContractor){
                    _self.rejectByContractor();
                }else{
                    _self.rejectByCnpe();
                }
            }
            _self.rejectComment = "";
            _self.showDialog=false;
        },
        rejectByContractor(){
            let _self = this;
            var m = [];
            let tab = _self.selectedItems;
            
            var i;
            for (i in tab) {
                m.push(tab[i]["ID"]);
            }
            let mp=new Map();
            mp.set("ids",m);
            mp.set("rejectCommon",_self.rejectComment);
            axios.post("/dc/previousStatusCnpe",JSON.stringify(mp),{
                headers: {
                    "Content-Type": "application/json;charset=UTF-8"
                }
            })
            .then(function(response) {
                if(response.data.code==1){
                   if(_self.refreshDataGrid!=null&&_self.refreshDataGrid!=undefined){
                        _self.refreshDataGrid.loadGridData();
                    }
                     if(_self.cleanSubDataGrids!=null){
                        
                         _self.cleanSubDataGrids.forEach(element => {
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
        },
        rejectSub(){
            
            let _self = this;
            var m = [];
            let tab = _self.selectedItems;
            
            var i;
            for (i in tab) {
                m.push(tab[i]["ID"]);
            }
            let mp=new Map();
            mp.set("ids",m);
            mp.set("rejectCommon",_self.rejectComment);
            axios.post("/dc/previousDrawingFile",JSON.stringify(mp),{
                headers: {
                    "Content-Type": "application/json;charset=UTF-8"
                }
            })
            .then(function(response) {
                if(response.data.code==1){
                    if(_self.refreshDataGrid!=null&&_self.refreshDataGrid!=undefined){
                        _self.refreshDataGrid.loadGridData();
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
        },
        rejectByCnpe(){
            let _self = this;
            
            if(_self.selectedItems==null||_self.selectedItems.length==0){
                 _self.$message({
                            showClose: true,
                            message: _self.$t("message.pleaseSelectDC"),
                            duration: 2000,
                            type: 'warning' 
                        });
                        return
            }
            var m = [];
            let tab = _self.selectedItems;
            var i;
            for (i in tab) {
                m.push(tab[i]["ID"]);
            }
            let mp=new Map();
            mp.set("isCnpeSend","false");
            mp.set("ids",m);
            mp.set("rejectCommon",_self.rejectComment);
            axios.post("/dc/previousStatus",JSON.stringify(mp),{
                headers: {
                    "Content-Type": "application/json;charset=UTF-8"
                }
            })
            .then(function(response) {
                if(response.data.code==1){
                    if(_self.refreshDataGrid!=null&&_self.refreshDataGrid!=undefined){
                        _self.refreshDataGrid.loadGridData();
                        
                    }
                    if(_self.cleanSubDataGrids!=null){
                        
                         _self.cleanSubDataGrids.forEach(element => {
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
        

    },
    components:{

    },
    props: {
        selectedItems:{ type: Array, default: null },
        refreshDataGrid:{type:Object,default:null},
        isSubObj:{type:Boolean,default:false},
        isRejectByContractor:{type:Boolean,default:false},
        isIED:{type:Boolean,default:false},
        cleanSubDataGrids:{type:Array,default:null}
    }

}
</script>
<style scoped>

</style>