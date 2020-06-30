<template>
    <div class="app-container">
        <!-- 待提交文函 -->
        <el-dialog
            :title="dialogName+$t('application.property')"
            :visible.sync="propertyVisible"
            @close="propertyVisible = false"
            width="80%"
            >
            <ShowProperty
                ref="ShowProperty"
                @onSaved="onSaved"
                width="100%"
                folderPath=""
                itemId=""
                v-bind:typeName="typeName"
            ></ShowProperty>
            <div slot="footer" class="dialog-footer">
                <el-button @click="saveItem">{{$t('application.save')}}</el-button>
                <el-button @click="propertyVisible = false">{{$t('application.cancel')}}</el-button>
            </div>
        </el-dialog>
        <el-row>
            <el-col :span="24" style="padding-top: 0px; padding-bottom: 0px;">
                <el-form :inline="true" :model="filters" @submit.native.prevent>
                <el-form-item>
                    <el-select v-model="filters.projectCode">
                    <el-option label="所有项目" value></el-option>
                    <el-option
                        v-for="item in projects"
                        :key="item+'_option'"
                        :label="item"
                        :value="item">
                    </el-option>
                    
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-select v-model="filters.docType">
                    <el-option label="所有文函" value></el-option>
                    <el-option label="传递单" value="传递单"></el-option>
                    <el-option label="图文传真" value="图文传真"></el-option>
                    <el-option label="会议纪要" value="会议纪要"></el-option>
                    <el-option label="接口传递" value="接口传递"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-input v-model="filters.title" placeholder="编码或标题"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" v-on:click="getData">查询</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="propertyVisible = true">新建</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="importVisible = true">导入</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="success" v-on:click="getData">提交</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="warning" v-on:click="onDeleleItem(selectedItems,$refs.mainDataGrid)">删除</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" v-on:click="getData">导出Excel</el-button>
                </el-form-item>
                </el-form>
            </el-col>
        </el-row>
        <el-row>
            <DataGrid
                ref="mainDataGrid"
                key="main"
                dataUrl="/dc/getDocuments"
                v-bind:tableHeight="rightTableHeight"
                v-bind:isshowOption="true" v-bind:isshowSelection ="true"
                gridViewName="DCTransferGrid"
                :isshowCustom="true"
                @dbclick="dbclick"
                @rowclick="rowClick"
                @selectchange="selectChange"
                ></DataGrid>
        </el-row>
    </div>
</template>
<script type="text/javascript">
import ShowProperty from "@/components/ShowProperty";
import DataGrid from "@/components/DataGrid";
export default {
    name: "Submissiondc",
    data(){
        return{
            filters: {
                projectCode: "",
                docType: "",
                coding: "",
                title: "",
                limit: 10
            },
            projects:[],
            typeName:"文件传递单",
            dialogName:"文件传递单",
            propertyVisible:false,
            selectedItems: [],
        }
    },
    created(){
        this.loadOptionList("项目","");
    },
    mounted(){
        if(!this.validataPermission()){
            //跳转至权限提醒页
            let _self=this;
            _self.$nextTick(()=>{
                _self.$router.push({ path: '/NoPermission' })
            })
            
        }
    },
    methods: {
        // 表格行选择
        selectChange(val) {
            this.selectedItems = val;
        },
        // 保存文档
        saveItem() {
        if (!this.$refs.ShowProperty.validFormValue()) {
            return;
        }
        let _self = this;
        var m = new Map();
        let dataRows = this.$refs.ShowProperty.dataList;
        var i;
        for (i in dataRows) {
            if (dataRows[i].attrName && dataRows[i].attrName != "") {
            if (
                dataRows[i].attrName != "FOLDER_ID" &&
                dataRows[i].attrName != "ID"
            ) {
                m.set(dataRows[i].attrName, dataRows[i].defaultValue);
            }
            }
        }
        if (_self.$refs.ShowProperty.myItemId != "") {
            m.set("ID", _self.$refs.ShowProperty.myItemId);
        }
        if (_self.$refs.ShowProperty.myTypeName != "") {
            m.set("TYPE_NAME", _self.$refs.ShowProperty.myTypeName);
            m.set("folderPath", _self.$refs.ShowProperty.folderPath);
            m.set("transferId", _self.$refs.ShowProperty.parentDocId);
        }
        let formdata = new FormData();
        formdata.append("metaData", JSON.stringify(m));

        if (_self.$refs.ShowProperty.file != "") {
            //console.log(_self.file);
            formdata.append("uploadFile", _self.$refs.ShowProperty.file.raw);
        }
        // console.log(JSON.stringify(m));
        if (_self.$refs.ShowProperty.myItemId == "") {
            _self
            .axios({
                headers: {
                "Content-Type": "multipart/form-data"
                // x-www-form-urlencoded'
                //"Content-Type": "application/json;charset=UTF-8"
                },
                method: "post",
                data: formdata,
                url: "/dc/newDocumentSaveToFolder"
            })
            .then(function(response) {
                let code = response.data.code;
                //console.log(JSON.stringify(response));
                if (code == 1) {
                // _self.$message("创建成功!");
                _self.$message({
                    showClose: true,
                    message: "创建成功!",
                    duration: 2000,
                    type: "success"
                });
                _self.propertyVisible = false;

                // _self.loadTransferGridData();
                _self.$refs.mainDataGrid.loadGridData();
                } else {
                // _self.$message("新建失败!");
                _self.$message({
                    showClose: true,
                    message: "新建失败!",
                    duration: 2000,
                    type: "warning"
                });
                }
            })
            .catch(function(error) {
                // _self.$message("新建失败!");
                _self.$message({
                    showClose: true,
                    message: "新建失败!",
                    duration: 5000,
                    type: "error"
                });
                console.log(error);
            });
        } else {
            _self
            .axios({
                headers: {
                "Content-Type": "application/json;charset=UTF-8"
                },
                method: "post",
                data: JSON.stringify(m),
                url: "/dc/saveDocument"
            })
            .then(function(response) {
                let code = response.data.code;
                //console.log(JSON.stringify(response));
                if (code == 1) {
                _self.$emit("onSaved", "update");
                } else {
                // _self.$message("保存失败!");
                _self.$message({
                    showClose: true,
                    message: "保存失败!",
                    duration: 5000,
                    type: "error"
                });
                }
            })
            .catch(function(error) {
                // _self.$message("保存失败!");
                _self.$message({
                    showClose: true,
                    message: "保存失败!",
                    duration: 5000,
                    type: "error"
                });
                console.log(error);
            });
        }
        },
        // 保存结果事件
        onSaved(indata) {
        let _self=this;
        if (indata == "update") {
            // _self.$message(_self.$t("message.saveSuccess"));
            _self.$message({
                showClose: true,
                message: _self.$t("message.saveSuccess"),
                duration: 2000,
                type: 'success'
            });
        } else {
            // _self.$message("新建成功!");
            _self.$message({
                showClose: true,
                message: "新建成功",
                duration: 2000,
                type: 'success'
            });
        }
        _self.propertyVisible = false;
        
        },
        loadOptionList(queryName,val){
            let _self = this;
            var m = new Map();
            m.set("queryName", queryName);
            m.set("dependValue", val);
            axios.post("/dc/getSelectList",JSON.stringify(m))
                .then(function(response) {
                if(response.data.code == 1){
                    _self.projects = response.data.data;
                }
                })
                .catch(function(error) {
                console.log(error);
                });
            },
    },
    props: {
        
    },
    components: {
        ShowProperty:ShowProperty,
        DataGrid:DataGrid
    }
}
</script>
<style scoped>

</style>