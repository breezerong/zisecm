<template>
    <div>
        <DataGrid
                ref="attachmentDoc"
                key="attachmentDocKey"
                dataUrl="/dc/getDocuByRelationParentId"
                v-bind:tableHeight="rightTableHeight"
                v-bind:isshowOption="true"
                gridViewName="AttachmentGrid"
                condition=" and a.NAME='附件'"
                :isshowCustom="false"
                :isEditProperty="true"
                showOptions="查看内容"
                :isShowChangeList="false"
                :parentId="docId"
                >
                    <template slot="dropdownItem" slot-scope="scope">
                        <el-dropdown-item icon="el-icon-delete" @click.native="onDeleleItem([scope.data.row],[$refs.attachmentDoc])">删除</el-dropdown-item>

                    </template>
                </DataGrid>
    </div>
</template>
<script>
import DataGrid from "@/components/DataGrid";
export default {
    data(){
        return {
            rightTableHeight:window.innerHeight - 300
        }
    },
    mounted(){

    },
    methods:{
       
        refresh(){
            this.$refs.attachmentDoc.loadGridData();
        }
    },
    components:{
        DataGrid:DataGrid
    },
    props:{
        docId:{type:String,default:''},
    }
}
</script>
<style scoped>

</style>