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
                <!-- :end-date="tables.mainGrid.endDate" -->
				<el-col :span="24">
                    <ecm-gantt v-if="tables.mainGrid.enabled" ref="mainGrid" :data="tables.mainGrid.data" :edit="false" width="100%"
                    border stripe lazy  highlight-current-row
                    :start-date="tables.mainGrid.startDate" :end-date="tables.mainGrid.endDate"
                    :load="loadData" :height="layout.height/2-15"
                    :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
                     @row-click="onRowClick">
                        <el-table-column prop="wbs" :label="$t('application.wbs')" fixed prv="name" width="280"></el-table-column>
                    </ecm-gantt>
				</el-col>
			</el-row>
			 <el-row>
				<el-col :span="24">
					<el-tabs v-model="tabs.tabAcitve">
						<el-tab-pane name="relationIED" label="相关IED">
							<DataGrid ref="relationIEDGrid" v-bind="tables.relationIEDGrid" :tableHeight="layout.height/2-195"></DataGrid>
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
import EcmGantt from '@/components/ecm-gantt'
export default {
    name: "Gantt",
    data(){
        return{
            tables:{
                mainGrid:{
                    enabled:false,
                    columns:[
                        {prop:"wbs",label: this.$t('application.tcWbs'),width:"400"},
                        {prop:"name",label:this.$t('application.tcName'),width:"320"},
                        {prop:"projectName",label:this.$t('application.projectName'),width:"200"},
                        {prop:"startDate",label:this.$t('application.startDate'),width:"120"},
                        {prop:"endDate",label:this.$t('application.EndDate'),width:"120"}
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
                    isInitData:false,
                    isShowMoreOption:false,
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
            this.tables.mainGrid.doLayout()
        },
        onSelectChange(val){
            this.forms.headForm.project = val
            this.$refs.relationIEDGrid.itemDataList=[]
            this.search()
            this.tables.mainGrid.doLayout()
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
            _self.tables.mainGrid.data=[]
            axios.post(url,param).then(function(result){
                _self.tables.mainGrid.data = result.data.data
                _self.tables.mainGrid.startDate = result.data.startDate
                _self.tables.mainGrid.endDate = result.data.endDate                
                _self.tables.mainGrid.enabled=true

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
        DataSelect:DataSelect,
        EcmGantt:EcmGantt
    }
}
</script>
<style scoped>

</style>
