<template>
    <div>
        <DataGridSub
                ref="attachmentDoc"
                key="attachmentDocKey"
                dataUrl="/dc/getDocuByRelationParentId"
                v-bind:tableHeight="rightTableHeight"
                v-bind:isshowOption="true"
                gridViewName="AttachmentGrid"
                condition=" and a.NAME='附件'"
                :isshowCustom="false"
                :isEditProperty="allowEdit"
                showOptions="查看内容"
                :isShowChangeList="false"
                :parentId="docId"
                >
                    <template v-if="allowEdit" slot="dropdownItem" slot-scope="scope">
                        <el-dropdown-item icon="el-icon-delete" @click.native="onDeleleItem([scope.data.row],[$refs.attachmentDoc])">删除</el-dropdown-item>
                    </template>
                </DataGridSub>
    </div>
</template>
<script>
import DataGridSub from "@/components/DataGridSub";
export default {
    data(){
        return {
            rightTableHeight:window.innerHeight - 300,
            timer: null
        }
    },
    mounted(){

    },
    methods:{
       
        refresh(){
            let _self = this;
            clearInterval(_self.timer);
            _self.timer = setTimeout(()=>{
               _self.$refs.attachmentDoc.loadGridData(null);
            },200);
            
        }
    },
    components:{
        DataGridSub:DataGridSub
    },
    props:{
        docId:{type:String,default:''},
        allowEdit: {type:Boolean, default:true}
    }
}
</script>
<style scoped>

</style>