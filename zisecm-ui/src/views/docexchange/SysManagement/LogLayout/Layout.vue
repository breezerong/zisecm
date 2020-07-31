<template>
    <DataLayout>
        <template v-slot:header>
            <el-row>
                <el-form inline>
                <el-form-item>
                    <el-date-picker
                        v-model="startDate"
                        type="date"
                        placeholder="开始日期"
                        value-format="yyyy-MM-dd">
                    </el-date-picker>
                </el-form-item>
                <el-form-item>
                    <el-date-picker
                        v-model="endDate"
                        type="date"
                        align="right"
                        placeholder="结束日期"
                        value-format="yyyy-MM-dd">
                    </el-date-picker>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="handleReport()">查询</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="tcDataExcel()">Excel导出</el-button>
                </el-form-item>
                </el-form>
            </el-row>
        </template>
        <template v-slot:main="{layout}">
            <el-table ref="mainTable" :data="tables.mainTable.data" border stripe :height="layout.height-160"> 
                <el-table-column :label="$t('field.indexNumber')" key="#1" width="50">
                    <template slot-scope="scope">
                        <slot name="sequee" :data="scope">
                            <span>{{(currentPage-1) * pageSize + scope.$index+1}}</span>
                        </slot>
                    </template>
                </el-table-column>
                <el-table-column v-for="item in tables.mainTable.columns" :key="item.prop" v-bind="item"></el-table-column>
                <el-table-column label="操作">
                   <template slot-scope="scope">
                       <el-button @click="handClick(scope.row)"  size="small">查看文件</el-button>
                   </template>
                </el-table-column>
            </el-table>
            <el-pagination
                background
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="currentPage"
                :page-sizes="[10, 20, 50, 100, 200]"
                :page-size="pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="itemCount">
            </el-pagination>
        </template>
    </DataLayout>
</template>
<script type="text/javascript">
import ShowProperty from "@/components/ShowProperty";
import DataLayout from '@/components/ecm-data-layout';
import ExcelUtil from '@/utils/excel.js';
export default {
    data(){
        return{
            tables:{
                mainTable:{
                    data:[],
                    columns:[
                        {prop:"typeName", label:"事件名称"},
                        {prop:"createdTime", label:"创建时间", sortable:true},
                        {prop:"executedTime", label:"执行时间", sortable:true},
                        {prop:"finishedTime", label:"完成时间", sortable:true},
                        {prop:"logStatus", label:"状态"},
                        {prop:"errorMessage", label:"错误信息"}
                    ]
                }
            },
            dataType:'TCSync',
            viewDocField:'',
            startDate: '',
            endDate: '',
            currentPage: 1,
            pageSize: 10,
            itemCount:0,
        }
    },
    methods:{
        LoadTable(){

        },

        handleReport(){
            let _self = this;
            _self.loading = true;
            this.setCondition();
            let m = new Map();
            m.set("dataType", this.dataType);
            m.set("startDate", _self.startDate);
            m.set("endDate", _self.endDate);
            m.set("pageSize", _self.pageSize);
            m.set("pageIndex", _self.currentPage - 1);
           console.log("handleReport")
           console.log(m)

            axios.post("/doc/tc/TCMonitor", JSON.stringify(m))
              .then(function(response) {
                _self.tables.mainTable.data = response.data.data
                _self.itemCount = response.data.pager.total;
                _self.loading = false;
              })
            .catch(function(error) {
                console.log(error);
            }); 
        },
        setCondition(){   

        },
        tcDataExcel(){
            let _self = this
            _self.loading = true
            let dataUrl = "/exchange/doc/exportTC"
            this.setCondition()
            
            let m = new Map()
            m.set("dataType", this.dataType);
            m.set("startDate", this.startDate);
            m.set("endDate", this.endDate);
            console.log(m)
            let tAttr = []
            let tHeader = [] 
            _self.tables.mainTable.columns.forEach(function(item){
                console.log(item)
                tAttr.push(item.prop)
                tHeader.push(item.label)
            }) 
            console.log(tAttr)
            m.set("titlename", tAttr);
            m.set("titlecnname", tHeader);
            m.set("filename", "LOG_Monitor_"+this.dataType+"_"+new Date().Format("yyyy-MM-dd")+".xlsx");
            m.set("sheetname", "Result");
            console.log(m)

            let url = "/exchange/doc/export"
            axios.post(dataUrl,JSON.stringify(m),{ responseType: "blob"}).then(function(res){
            console.log(res)
            let fileName = res.headers["content-disposition"].split(";")[1].split("=")[1].replace(/\"/g, "");
            let type = res.headers["content-type"];
            let blob = new Blob([res.data], { type: type });
            // IE
            if (window.navigator.msSaveBlob) {
                window.navigator.msSaveBlob(blob, fileName);
            } else {
                var link = document.createElement("a");
                link.href = window.URL.createObjectURL(blob);
                link.download = fileName;
                link.click();
                //释放内存
                window.URL.revokeObjectURL(link.href);
            }
            }).catch(function(error){
            console.log(error)
            })




            /* axios.post(dataUrl, JSON.stringify(m),{ responseType: "blob"})
                .then(function(res){
                    console.log("xxx="+res)
                    let fileName = res.headers["content-disposition"].split(";")[1].split("=")[1].replace(/\"/g, "");
                    let type = res.headers["content-type"];
                    let blob = new Blob([res.data], { type: type });
                    // IE
                    if (window.navigator.msSaveBlob) {
                        window.navigator.msSaveBlob(blob, fileName);
                    } else {
                        var link = document.createElement("a");
                        link.href = window.URL.createObjectURL(blob);
                        link.download = fileName;
                        link.click();
                        //释放内存
                        window.URL.revokeObjectURL(link.href);
                    }
                }).catch(function(error) {
                    console.log(error);
                });  */
        },

        showItemContent(id) {
              let href = this.$router.resolve({
                path: "/viewdoc",
                query: {
                id: id
                }
            });
            window.open(href.href, "_blank");
        },
        handClick(row){
            console.log(this.viewDocField)
            console.log(row[this.viewDocField])
            console.log(row)
            if(row[this.viewDocField].length>0){
                this.showItemContent(row[this.viewDocField])
            }else{
                this.$message({showClose: true,message: "文档ID为空",duration: 2000,type: "warning"})
            }
            
        },

        handleSizeChange(val) {
            this.pageSize = val;
            localStorage.setItem("docPageSize", val);
            this.handleReport();
        },
        handleCurrentChange(val) {
            this.currentPage = val;
            this.handleReport();
        }
    },

    components: {
        DataLayout:DataLayout
    }
}
</script>
<style scoped>

</style>