<template>
    <DataLayout>
        <template v-slot:header>
            <el-form :inline="true" :model="forms.headForm">
                <el-form-item>
                    <DataSelect v-model="forms.headForm.project" dataUrl="/exchange/project/myproject"
                     dataValueField="name" dataTextField="name" includeAll @onLoadnDataSuccess="onLoadnDataSuccess" @onSelectChange="onSelectChange"></DataSelect>
                </el-form-item>
            </el-form>
        </template>
        <template v-slot:main="{layout}">
            <el-row>
				<el-col :span="24">
                    <wl-gantt ref="mainGrid" :data="tables.mainGrid.data" :edit="false" 
                    border stripe lazy  highlight-current-row 
                    :start-date="tables.mainGrid.startDate" :end-date="tables.mainGrid.endDate"
                    :load="loadData" :height="layout.height/2-115" 
                    :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
                     @row-click="onRowClick">
                        <el-table-column prop="wbs" label="WBS" fixed prv="name"></el-table-column>
                    </wl-gantt>	
				</el-col>
			</el-row>
			 <el-row>
				<el-col :span="24">
					<el-tabs v-model="tabs.tabAcitve">
						<el-tab-pane name="relationIED" label="相关IED">
							<DataGrid ref="relationIEDGrid" v-bind="tables.relationIEDGrid" :tableHeight="layout.height/2-115"></DataGrid>
						</el-tab-pane>
					</el-tabs>
				</el-col>
			</el-row>
        </template>
    </DataLayout>
</template>
<script type="text/javascript">
import DataGrid from "@/components/DataGrid";
import DataLayout from '@/components/ecm-data-layout'
import DataSelect from '@/components/ecm-data-select'
export default {
    name: "Gantt",
    data(){
        return{
            tables:{
                mainGrid:{
                    columns:[
						{prop:"wbs",label:"WBS编码",width:"380"},
                        {prop:"name",label:"名称",width:"250"},
                        {prop:"projectName",label:"项目名称",width:"200"},
						{prop:"startDate",label:"开始时间",width:"200"},
						{prop:"endDate",label:"结束时间",width:"200"}
                    ],
                    data:[],
                    startDate:"",
                    endDate:""
                },
                relationIEDGrid:{
                    gridViewName:"PlantRelationIED",
                    dataUrl:"/dc/getDocuments",
                    condition:"",                    
                    isshowOption:true,
                    isshowCustom:true,
                    isshowicon:false,
                    isInitData:false                
                }
            },
            forms:{
                headForm:{
                    project:"",
                }
            },
            tabs:{
                tabAcitve:"relationIED"
            }
        }
    },
    created(){

    },
    mounted(){
       
    },
    methods: {
        onLoadnDataSuccess(select,options){
            this.search()
        },
        onSelectChange(val){
            this.forms.headForm.project = val
            this.search()
        },
        onRowClick(row, column, event){
            console.log(row)
            this.tables.relationIEDGrid.condition = " C_WBS_CODING like '"+row.wbs +"%'"
            this.$refs.relationIEDGrid.condition=this.tables.relationIEDGrid.condition
            this.$refs.relationIEDGrid.itemDataList=[]            
            this.$refs.relationIEDGrid.loadGridData()
        },
        loadData(tree, treeNode, resolve){
            let url = "/exchange/plant/list"
            let parentId = tree.id
            let param = {
                id:parentId,
                condition : " C_PROJECT_NAME in ("+this.forms.headForm.project+") "
            }
            axios.post(url,param).then(function(result){
                resolve(result.data.data)
            })
        },
        search(){
            let _self = this
            let url = "/exchange/plant/list"
            let param = {
                condition : " C_PROJECT_NAME in ("+this.forms.headForm.project+") "
            }
            axios.post(url,param).then(function(result){
                _self.tables.mainGrid.data = result.data.data
                console.log("[Gantt.search]")
                _self.tables.mainGrid.startDate = result.data.startDate
                _self.tables.mainGrid.endDate = result.data.endDate
                console.log(result.data.startDate)
                console.log(result.data.endDate)
            }).catch(function(error){
                console.log(error)
            })
        }
    },
    props: {
        
    },
    components: {
        DataLayout:DataLayout,
        DataGrid:DataGrid,
        DataSelect:DataSelect
    }
}
</script>
<style scoped>

</style>