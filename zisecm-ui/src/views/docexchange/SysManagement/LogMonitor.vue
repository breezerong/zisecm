<template>
    <el-tabs v-model="activeName">
        <el-tab-pane label="从TC同步" name="TCSync">
            <TCSyncGrid></TCSyncGrid>
        </el-tab-pane>
        <el-tab-pane label="推送到TC" name="TCPush">
            <TCPushGrid></TCPushGrid>
        </el-tab-pane>
        <el-tab-pane label="内外网同步" name="NetSync">
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
    created(){

    },
    mounted(){
        
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