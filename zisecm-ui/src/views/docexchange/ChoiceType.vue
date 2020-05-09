<template>
    <div>
        <el-form ref="form" :model="form" label-width="80px">
            <el-row>
                <el-form-item label="文件类型">
                    <el-radio-group v-model="form.choiceTypeName" v-for="(name,nameIndex) in typeNames" 
                    :key="'T_'+nameIndex" @change="radioGroupChange()">
                        <el-radio :label="name">{{name}}</el-radio>
                    
                    </el-radio-group>
                </el-form-item>
            </el-row>
            <el-row>
                <el-form-item label="文件模板">
                    <el-radio-group v-model="form.choiceTemplate" v-for="(item,itemIndex) in templates" :key="'T_'+itemIndex">
                        <el-radio :label="item.ID">{{item.NAME}}</el-radio>
                    </el-radio-group>
                </el-form-item>
            </el-row>
           
        </el-form>
         <div slot="footer" class="dialog-footer">
                
                <el-button type="primary" @click="next()">下一步</el-button>
            </div>
    </div>
</template>
<script>
export default {
    name:"ChoiceType",
    data(){
        return{
            typeNames:[],
            // choiceTypeName:"",
            templates:[],
            // choiceTemplate:[],
            form:{
                choiceTypeName:"",
                choiceTemplate:[]
            }
        }
    },
    mounted() {
        this.getTypeNames("docTypeName");
    },
    methods:{
        next(){
            let _self = this;

            var m = new Map();
            
            m.set("templateId", _self.form.choiceTemplate);
            m.set("TYPE_NAME",_self.form.choiceTypeName);
            m.set("folderPath","/设计分包/传递单管理/ATOS");
            
            // console.log('pagesize:', _self.pageSize);
            axios
                .post("/dc/newNoPropertiesDocument", JSON.stringify(m))
                .then(function(response) {
                
                     _self.$router.push({ 
                        path: '/docexchange/CreateDoc',
                        query:{ docId:response.data.id}
                        });
                })
                .catch(function(error) {
                console.log(error);
                _self.loading = false;
                });

           
        },
        getTemplate(typeName){
            let _self = this;
            axios
                .post("/dc/getTemplates", typeName)
                .then(function(response) {
                    
                _self.templates = response.data.data;
                })
                .catch(function(error) {
                console.log(error);
                });
        },
        radioGroupChange(){
            this.getTemplate(this.form.choiceTypeName);
        },
        getTypeNames(keyName) {
            let _self = this;
            axios
                .post("/dc/getParameters", keyName)
                .then(function(response) {
                    if(response.data.data.docTypeName!=null&&
                    response.data.data.docTypeName.length>0){
                        _self.form.choiceTypeName=response.data.data.docTypeName[0];
                        _self.getTemplate(_self.form.choiceTypeName);
                    }
                _self.typeNames = response.data.data.docTypeName;
                })
                .catch(function(error) {
                console.log(error);
                });
            },
    }
}
</script>
<style scoped>

</style>