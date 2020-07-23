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
                :isshowCustom="true"
                :parentId="docId"
                ></DataGrid>
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