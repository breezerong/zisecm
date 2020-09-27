<template>
    <el-tabs v-model="activeName">
        <el-tab-pane :label="$t('route.synfromtc')" name="TCSync">
            <TCSyncGrid></TCSyncGrid>
        </el-tab-pane>
        <el-tab-pane :label="$t('route.pushtotc')" name="TCPush">
            <TCPushGrid></TCPushGrid>
        </el-tab-pane>
        <el-tab-pane :label="$t('route.in&exsyn')" name="NetSync">
            <NetSyncGrid></NetSyncGrid>
        </el-tab-pane>
    </el-tabs>
</template>
<script type="text/javascript">
import {TCSyncGrid, TCPushGrid, NetSyncGrid} from './LogLayout/'
export default {
    data(){
        return{
            activeName: "TCSync",
        }
    },

    methods: {

    },
    props: {
        
    },
    components: {
        TCSyncGrid, 
        TCPushGrid, 
        NetSyncGrid
    }
}
</script>
<style scoped>

</style>