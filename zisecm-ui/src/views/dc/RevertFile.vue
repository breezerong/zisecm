<template>
    <div>
        <DataGrid
                ref="relevantDoc"
                key="relevantDocKey"
                dataUrl="/dc/getDocuByOtherCode"
                v-bind:tableHeight="rightTableHeight"
                v-bind:isshowOption="true"
                gridViewName="DCTransferGrid"
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