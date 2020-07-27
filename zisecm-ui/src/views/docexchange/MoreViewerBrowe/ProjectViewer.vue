<template>
    <div>
        项目视图
        <el-container>
            <el-aside>
                <el-tree
                    class="filter-tree"
                    :data="data"
                    :props="defaultProps"
                    :filter-node-method="filterNode"
                    ref="tree"
                    @node-click="nodeClick">
                </el-tree>
            </el-aside>
            <el-main>
                <template>
                    <el-row>
                        <DataGrid
                            ref="mainDataGrid"
                            key="main"
                            dataUrl="/dc/getDocuments"
                            v-bind:tableHeight="rightTableHeight"
                            v-bind:isshowOption="true"
                            gridViewName="DCTransferGrid"
                            :isshowCustom="true"
                            :isInitData="false"
                            @rowclick="rowClick"
                            :isshowSelection="false"
                            >
                                
                            </DataGrid>
                    </el-row>
                    <el-row>
                        <el-tabs  v-model="selectedTabName">
                            <el-tab-pane label="传递文件" name="t01" v-if="isShowDesgin">
                            <!--列表-->
                            <DataGrid
                                    ref="transferDoc"
                                    key="transferDocKey"
                                    dataUrl="/dc/getDocuByRelationParentId"
                                    v-bind:tableHeight="rightTableHeight"
                                    v-bind:isshowOption="true"
                                    gridViewName="DrawingGrid"
                                    condition=" and a.NAME='设计文件'"
                                    :isshowCustom="true"
                                    ></DataGrid>
                            </el-tab-pane>
                            <el-tab-pane label="相关文件" name="t02" v-if="isShowRelevant" ref="relevantTab">
                            <!--列表-->
                            <DataGrid
                                    ref="relevantDoc"
                                    key="relevantDocKey"
                                    dataUrl="/dc/getDocuByRelationParentId"
                                    v-bind:tableHeight="rightTableHeight"
                                    v-bind:isshowOption="true"
                                    gridViewName="DrawingGrid"
                                    condition=" and a.NAME='相关文件'"
                                    :isshowCustom="true"
                                    ></DataGrid>
                            
                            </el-tab-pane>
                            <el-tab-pane label="附件" name="t03" v-if='isShowAttachmentDoc'>
                            <!--列表-->
                            <DataGrid
                                    ref="attachmentDoc"
                                    key="attachmentDocKey"
                                    dataUrl="/dc/getDocuByRelationParentId"
                                    v-bind:tableHeight="rightTableHeight"
                                    v-bind:isshowOption="true"
                                    gridViewName="AttachmentGrid"
                                    condition=" and a.NAME='附件'"
                                    :isshowCustom="true"
                                    ></DataGrid>
                            </el-tab-pane>
                        </el-tabs>
                    </el-row>
                </template>
            </el-main>
        </el-container>
    </div>
</template>
<script type="text/javascript">
import ShowProperty from "@/components/ShowProperty";
import DataGrid from "@/components/DataGrid";
export default {
    name: "ProjectViewer",
    data(){
        return{
            defaultProps: {
                children: 'children',
                label: 'name'
            },
            data:[],
            typeName:"",
            projectName:"",
            isShowDesgin:true,
            isShowRelevant:true,
            isShowAttachmentDoc:true,
            parentId:"",
            selectedTabName:'t01',
            rightTableHeight: (window.innerHeight - 60)/2,
            filters: {
                title: ""
            },
        }
    },
    created(){

    },
    mounted(){
        if(!this.validataPermission()){
            //跳转至权限提醒页
            let _self=this;
            _self.$nextTick(()=>{
                _self.$router.push({ path: '/NoPermission' })
            })
            
        }
        this.getTreeData();
    },
    methods: {
        getTreeData(){
            let _self=this;
            axios
                .post("/exchange/project/getProjectView")
                .then(function(response) {
                _self.data = response.data.data;
                })
                .catch(function(error) {
                console.log(error);
                });
        },
        nodeClick(data,node,current){
            
            if(node.parent.data.name==undefined){
                return;
            }
            if(node.data.name=='文函'){
                return;
            }
            if(node.parent.data.name=="文函"){//文函查询
                this.projectName=node.parent.parent.data.name;
                this.typeName=data.name;
                let user = this.currentUser();
                if(user.userType==2 && user.company!=null){
                    this.$refs.mainDataGrid.condition=" TYPE_NAME='"+this.typeName+"' "
                    +"and (STATUS is not null and STATUS!='' and STATUS!='新建')"
                    +" and C_PROJECT_NAME = '"+this.projectName+"' AND (C_COMPANY='"+user.company +"'"
                    +" or C_TO like'%"+user.company+"%')";
                }else{
                    this.$refs.mainDataGrid.condition=" TYPE_NAME='"+this.typeName+"' "
                    +"and (STATUS is not null and STATUS!='' and STATUS!='新建') "
                    +"and C_PROJECT_NAME = '"+this.projectName+"' ";
                }
                
                this.$refs.mainDataGrid.loadGridData();
                let _self=this;
                if(_self.$refs.transferDoc){
                    _self.$refs.transferDoc.itemDataList=[];
                }
                if(_self.$refs.relevantDoc){
                    _self.$refs.relevantDoc.itemDataList=[];
                }
                if(_self.$refs.attachmentDoc){
                    _self.$refs.attachmentDoc.itemDataList=[];
                }
            }else{
                this.projectName=node.parent.data.name
                if(node.data.name=='ICM'){

                }else if(node.data.name=='计划'){

                }else if(node.data.name=='IED'){

                }else if(node.data.name=='设计文件'){

                }
            }
            
        },
        searchItem(){
            let _self=this;
            let key=" ";
           
            if(_self.filters.title!=''){
                key+=" and (C_CONTENT like '%"+_self.filters.title+"%' "
                +"or C_FROM like '%"+_self.filters.title+"%' "
                +"or C_TO like '%"+_self.filters.title+"%' "
                +"or CODING like '%"+_self.filters.title+"%' "
                +"or C_OTHER_COIDNG like '%"+_self.filters.title+"%' "
                +")";
            }
            if(key!=''){
                _self.$refs.mainDataGrid.condition=key;
            }
            _self.$refs.mainDataGrid.loadGridData();
        },
        rowClick(row){
            this.parentId=row.ID;
            let _self=this;
            if(row.TYPE_NAME=='文件传递单'){
                _self.isShowDesgin=true;
                _self.isShowRelevant=false;
               _self.isShowAttachmentDoc=false;
               _self.selectedTabName='t01';
               _self.$nextTick(()=>{
                   _self.$refs.transferDoc.parentId=row.ID;
                    _self.$refs.transferDoc.loadGridData();
               });
               
            }
            if(("FU申请、FU通知单、作废通知单、CR澄清要求申请单、CR澄清要求答复单、CR澄清要求关闭单、FCR现场变更申请单、FCR现场变更答复单、FCR现场变更关闭单、NCR不符合项报告单、NCR不符合项报告答复单、NCR不符合项报告关闭单、"+
            "DCR设计变更申请单、DCR设计变更答复单、DCR设计变更关闭单、TCR试验澄清申请单、TCR试验澄清答复单、"+
            "TCR试验澄清关闭单、DEN设计变更通知单、DEN设计变更通知关闭单、设计审查意见、设计审查意见答复").indexOf(row.TYPE_NAME)!=-1){
                _self.selectedTabName='t02';
                _self.isShowDesgin=false;
                _self.isShowRelevant=true;
                _self.isShowAttachmentDoc=false;
                
                _self.$nextTick(()=>{
                    _self.$refs.relevantDoc.parentId=row.ID;
                    _self.getRelatinItemByTypeName(row.TYPE_NAME,_self.$refs.relevantDoc,function(val){
                    _self.relation=val;
                    // _self.$refs.relevantDoc.loadGridInfo();
                    // _self.$refs.relevantDoc.loadGridData();
                    });
                })
                
            }
            if("图文传真,会议纪要".indexOf(row.TYPE_NAME)!=-1){
                _self.isShowDesgin=false;
                _self.isShowRelevant=false;
               _self.isShowAttachmentDoc=true;
               _self.selectedTabName='t03';
               _self.$nextTick(()=>{
               _self.$refs.attachmentDoc.parentId=row.ID;
                 _self.$refs.attachmentDoc.loadGridData();
               });
            }
            
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