<template>
    <div>
        回复延误反馈
    </div>
</template>
<script type="text/javascript">
import ShowProperty from "@/components/ShowProperty";
import DataGrid from "@/components/DataGrid";
import ExcelUtil from '@/utils/excel.js'
import DataSelect from '@/components/ecm-data-select'
import DataLayout from '@/components/ecm-data-layout'
import AddCondition from '@/views/record/AddCondition.vue'
export default {
    name: "ICMFeedback",
    data(){
        return{
            tables:{
                main:{
                    dataList:[],
                    height:"",
                    isshowoption:true,
                    isshowCustom:false,
                    isShowPropertyButton:true,
                    isShowMoreOption:false,
                    isShowChangeList:false,
                    isInitData:false,
                    isshowicon:false
                },
               itemDataList: [],
               loading: false,
               status : '',
            },
            processStatus:[{
                label : "新建",
                value : "新建",
            },
            {
                label : "已确认",
                value : "已确认",
            }],
            selectedItems: [],
            selectedItemId: "",
            value:'',
            Cstatus:'新建',
            hiddenInput:'hidden',
            typeName:"ICM",
        }
    },

 created(){

    },
 mounted(){
     
    },

    methods: {
    }
}
</script>
<style scoped>
.el-header{
    height: auto;
}
.el-form-item{
    margin:0px
}
</style>