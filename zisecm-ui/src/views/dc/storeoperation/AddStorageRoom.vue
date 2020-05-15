<template>
    <div>
        <el-form label-position="right" label-width="100px">
            <el-form-item label="库房代码">
                <el-input type="text" v-model="form.CODING"></el-input>
            </el-form-item>
            <el-form-item label="列数">
                <el-input type="text" v-model="form.COLUMN_COUNT"></el-input>
            </el-form-item>
            <el-form-item label="面积">
                <el-input type="text" v-model="form.ROOM_AREA"></el-input>
            </el-form-item>
            <el-form-item label="功能">
                <el-input type="text" v-model="form.ROOM_FUNCTION"></el-input>
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
                ID:"",
                CODING:"",
                ROOM_TYPE:"",
                ROOM_AREA:0,
                ROOM_FUNCTION:'',
                DESCRIPTION:'',
                COLUMN_COUNT:0
            }
        }
    },
    methods:{
        loadGridData(roomId){
            let _self=this;
           _self
            .axios({
            headers: {
                "Content-Type": "application/json;charset=UTF-8"
            },
            method: "post",
            data: roomId,
            url: "/dc/getRoomById"
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
            url: "/dc/createStorageRoom"
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