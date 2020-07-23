<template>
    <div>
        <DataGrid
                ref="transferDoc"
                key="transferDocKey"
                dataUrl="/dc/getDocuByRelationParentId"
                v-bind:tableHeight="rightTableHeight"
                v-bind:isshowOption="true" v-bind:isshowSelection ="false"
                gridViewName="DrawingGrid"
                condition=" and a.NAME='相关文件'"
                :parentId="docId"
                :isshowCustom="true"
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