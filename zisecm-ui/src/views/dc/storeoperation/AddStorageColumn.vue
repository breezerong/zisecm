<template>
    <div>
        <el-form label-position="right" label-width="100px">
             <el-form-item label="库房编码">
                <el-input type="text" v-model="form.PARENT_CODING"></el-input>
            </el-form-item>
            <el-form-item label="编码">
                <el-input type="text" v-model="form.CODING"></el-input>
            </el-form-item>
            <el-form-item label="总长度">
                <el-input type="text" v-model="form.TOTAL_LENGTH"></el-input>
            </el-form-item>
            <el-form-item label="剩余长度">
                <el-input type="text" v-model="form.REMAIN_LENGTH"></el-input>
            </el-form-item>
            <el-form-item label="卷盒数">
                <el-input type="text" v-model="form.ARCHIVE_COUNT"></el-input>
            </el-form-item>
            <el-form-item label="备注">
                <el-input type="text" v-model="form.DESCRIPTION"></el-input>
            </el-form-item>
        </el-form>
        
    </div>
</template>
<script>
export default {
    name:'AddStorageRoom',
    data(){
        return{
            form:{
                ID:'',
                PARENT_CODING:this.roomCoding,
                CODING:"",
                TOTAL_LENGTH:0,
                REMAIN_LENGTH:0,
                ARCHIVE_COUNT:0,
                DESCRIPTION:''
            }
        }
    },
    mounted(){
        // this.PARENT_CODING=this.roomCoding;
    },
    props:{
        roomCoding:{type:String,default:""}
    },
    methods:{
        loadGridData(columnId){
            let _self=this;
           _self
            .axios({
            headers: {
                "Content-Type": "application/json;charset=UTF-8"
            },
            method: "post",
            data: columnId,
            url: "/dc/getColumnById"
            })
            .then(function(response) {
                if(response.data.code==1){
                    _self.form=response.data.data;
                }else{
                    _self.$message({
                        showClose: true,
                        message: response.data.msg,
                        duration: 5000,
                        type: 'error'
                    });
                }
            
            })
            .catch(function(error) {
            // _self.$message(_self.$t("message.deleteFailured"));
            _self.$message({
                showClose: true,
                message: _self.$t("message.operationFaild"),
                duration: 5000,
                type: 'error'
            });
            console.log(error);
            });
        },
        saveItem(){
            let _self=this;
           _self
            .axios({
            headers: {
                "Content-Type": "application/json;charset=UTF-8"
            },
            method: "post",
            data: JSON.stringify(_self.form),
            url: "/dc/createStorageColumn"
            })
            .then(function(response) {
           
            // _self.$message(_self.$t("message.deleteSuccess"));
            _self.$message({
                showClose: true,
                message: _self.$t("message.saveSuccess"),
                duration: 2000,
                type: 'success'
            });
            _self.$emit('onSaved');
            })
            .catch(function(error) {
            // _self.$message(_self.$t("message.deleteFailured"));
            _self.$message({
                showClose: true,
                message: _self.$t("message.saveFailured"),
                duration: 5000,
                type: 'error'
            });
            console.log(error);
            });
        }
    }
}
</script>
<style scoped>
    
    
</style>