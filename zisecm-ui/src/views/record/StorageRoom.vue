<template>
    <div>
        <el-dialog title="添加库房" :visible.sync="reuseVisible" width="80%">
            <AddStorageRoom ref="addStorageRoom" @onSaved='getAllStorageRoom'></AddStorageRoom>
            <div slot="footer" class="dialog-footer">
                <el-button @click="reuseVisible = false">{{$t('application.cancel')}}</el-button>
                <el-button type="primary" @click="saveStorageRoom()">确定</el-button>
            </div>
        </el-dialog>
        <el-dialog title="添加库房列" :visible.sync="columnVisible" width="80%">
            <AddStorageColumn ref="addStorageColumn" @onSaved="refreshColumn" 
            :roomCoding="selectRoomCoding"></AddStorageColumn>
            <div slot="footer" class="dialog-footer">
                <el-button @click="columnVisible = false">{{$t('application.cancel')}}</el-button>
                <el-button type="primary" @click="saveStorageColumn()">确定</el-button>
            </div>
        </el-dialog>
        <el-dialog title="添加库房连" :visible.sync="rowVisible" width="80%">
            <AddStorageRow ref="addStorageRow" @onSaved="refreshRow" 
            :columnCoding="selectColumnCoding"></AddStorageRow>
            <div slot="footer" class="dialog-footer">
                <el-button @click="rowVisible = false">{{$t('application.cancel')}}</el-button>
                <el-button type="primary" @click="saveStorageRow()">确定</el-button>
            </div>
        </el-dialog>
        
        <el-container :style="{border: '1px', solid: '#eee',height: mainheight}" >
            <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
                <el-container>
                    <el-header style="height:24px;background-color:#D4D4D4">
                        <el-col :span="16" style="font-size: 16px"> <span>库房</span></el-col>
                        <el-col :span="8" style="text-align: right">
                        <el-dropdown>
                            <i class="el-icon-caret-bottom" style="margin-right: 15px"></i>
                            <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item @click.native="beforeCreateStorageRoom()">新增</el-dropdown-item>
                            <el-dropdown-item @click.native="beforeUpdateRoom()">查看/修改</el-dropdown-item>
                            <el-dropdown-item @click.native="confirmDeleteStorageRoom()">{{$t('application.delete')}}</el-dropdown-item>
                            </el-dropdown-menu>
                        </el-dropdown>
                        </el-col>
                    </el-header>
                    
                    <el-main>

                    <div class='items' v-for="(item,key) in storageRoomList" :key='key' 
                    :class="{itemschange:selectIndex==key}"
                     @click="searchByStore(item.CODING,key,item.ID)">
                        <svg-icon icon-class="store"></svg-icon>
                        <span class="name">{{item.CODING}}</span>
                    </div>
                    <!-- <div class='item' style="background-color:#CDB5CD" >
                        <svg-icon icon-class="store"></svg-icon>
                        <span class="name">库房02</span>
                    </div>
                    <div class='item' style="background-color:#CDB5CD" >
                        <svg-icon icon-class="store"></svg-icon>
                        <span class="name">库房03</span>
                    </div>
                    <div class='item' style="background-color:#CDB5CD" >
                        <svg-icon icon-class="store"></svg-icon>
                        <span class="name">库房04</span>
                    </div>
                    <div class='item' style="background-color:#CDB5CD" >
                        <svg-icon icon-class="store"></svg-icon>
                        <span class="name">库房05</span>
                    </div> -->
                    </el-main>
                </el-container>
                <el-container style="background-color: #F0F8FF">
                <el-header style="height:24px; background-color:#D4D4D4">
                    <el-col :span="16" style="font-size: 16px"> <span>列</span></el-col>
                    <el-col :span="8" style="text-align: right">
                    <el-dropdown>
                            <i class="el-icon-caret-bottom" style="margin-right: 15px"></i>
                            <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item @click.native="columnVisible=true">新增</el-dropdown-item>
                            <el-dropdown-item @click.native="beforeUpdateColumn">查看/修改</el-dropdown-item>
                            <el-dropdown-item @click.native="confirmDeleteStorageColumn">{{$t('application.delete')}}</el-dropdown-item>
                            </el-dropdown-menu>
                        </el-dropdown>
                    </el-col>
                </el-header>
                <el-main>
                    <div class='line' v-for="(item,key) in columnList" 
                    :class="{linechange:selectColumnIndex==key}"
                    :key="key" @click="selectColumn(item.CODING,key,item.ID)">
                        <svg-icon icon-class="bookCol"></svg-icon>
                        {{item.CODING}}
                    </div>
                    
                </el-main>
            </el-container>
            </el-aside>
            
            <el-container style="background-color:#D4D4D4">
                <el-header style="height:20px;background-color:#D4D4D4">
                    <el-col :span="16" style="font-size: 16px"> <span>排</span></el-col>
                    <el-col :span="8" style="text-align: right">
                     <el-dropdown>
                            <i class="el-icon-caret-bottom" style="margin-right: 15px;font-size:18px"></i>
                            <el-dropdown-menu slot="dropdown">
                                <el-dropdown-item @click.native="beforeCreateRow()">新增</el-dropdown-item>
                                <el-dropdown-item @click.native="beforeUpdateRow">查看/修改</el-dropdown-item>
                                <el-dropdown-item @click.native="confirmDeleteStorageRow">{{$t('application.delete')}}</el-dropdown-item>
                            </el-dropdown-menu>
                        </el-dropdown>
                    </el-col>
                </el-header>
                <el-main>
                    <DataGrid
                        ref="rowDataGrid"
                        key="rowDataGrid"
                        v-bind:itemDataList="rowList"
                        v-bind:columnList="gridList"
                        @pagesizechange="pageSizeChange"
                        @pagechange="pageChange"
                        v-bind:itemCount="itemCount"
                        v-bind:tableHeight="height"
                        v-bind:isshowOption="false" v-bind:isshowSelection ="false"
                        @rowclick="selectOneFile"
                        @refreshdatagrid="refreshRow"
                        ></DataGrid>
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>
<script>
import AddStorageRoom from '@/views/dc/storeoperation/AddStorageRoom.vue';
import AddStorageColumn from '@/views/dc/storeoperation/AddStorageColumn.vue';
import AddStorageRow from '@/views/dc/storeoperation/AddStorageRow.vue';

import DataGrid from "@/components/DataGrid";
export default {
    name:'StorageRoom',
    data(){
        return{
            currentLanguage: "zh-cn",
            reuseVisible:false,
            columnVisible:false,
            storageRoomList:[],
            columnList:[],
            selectRoomCoding:'',
            selectIndex:-1,
            selectColumnIndex:-1,
            selectColumnCoding:'',
            rowList:[],
            gridList:[],
            pageSize: 20,
            itemCount: 0,
            currentPage:1,
            selectedItem:[],
            rowVisible:false,
            showFields: [],
            selectedRoomId:'',
            selectedColumnId:'',
            height: window.innerHeight - 170,
            mainheight: window.innerHeight - 160,
        }
    },
    watch: {
        showFields(val, oldVal) {
        //普通的watch监听
        //console.log("a: "+val, oldVal);
        let _self = this;
        _self.gridList.forEach(element => {
            element.visibleType = 2;
        });
        val.forEach(element => {
            let item = _self.getgriditem(element);
            if (item) {
            //console.log(element);
            item.visibleType = 1;
            }
        });
        }
    },
    mounted(){
        this.getAllStorageRoom();
        this.loadGridInfo();
    },
    methods:{
        beforeUpdateRoom(){
            let _self=this;
            if(_self.selectedRoomId==''){
                 _self.$message({
                    showClose: true,
                    message: "请选择一个库房数据！",
                    duration: 5000,
                    type: 'error'
                });
                return;
            }
            _self.reuseVisible=true;
            
            _self.$nextTick(() => {
                _self.$refs.addStorageRoom.loadGridData(_self.selectedRoomId);
            })
        },
        beforeUpdateColumn(){
            let _self=this;
            if(_self.selectedColumnId==''){
                 _self.$message({
                    showClose: true,
                    message: "请选择一个列数据！",
                    duration: 5000,
                    type: 'error'
                });
                return;
            }
            _self.columnVisible=true;
            
            _self.$nextTick(() => {
                _self.$refs.addStorageColumn.loadGridData(_self.selectedColumnId);
            })
        },
        beforeUpdateRow(){
            let _self=this;
            if(_self.selectedItem.ID==''){
                 _self.$message({
                    showClose: true,
                    message: "请选择一个连数据！",
                    duration: 5000,
                    type: 'error'
                });
                return;
            }
            _self.rowVisible=true;
            
            _self.$nextTick(() => {
                _self.$refs.addStorageRow.loadGridData(_self.selectedItem.ID);
            })
        },
        beforeCreateRow(){
            this.rowVisible=true;
            let _self=this;
            // setTimeout(()=>{
            //     _self.$refs.addStorageRow.columnCoding=_self.selectColumnCoding;

            // },10);
            this.$nextTick(() => {
                _self.$refs.addStorageRow.form.PARENT_CODING=_self.selectColumnCoding;
            })
        },
        pageSizeChange(val) {
            this.pageSize = val;
            localStorage.setItem("docPageSize", val);
            this.selectColumn(this.selectColumnCoding,this.selectColumnIndex,this.selectedColumnId);
        },
        // 分页 当前页改变
        pageChange(val) {
            this.currentPage = val;
            this.selectColumn(this.selectColumnCoding,this.selectColumnIndex,this.selectedColumnId);
        },
        selectOneFile(row) {
            this.selectedItem=row;
            
        },
        beforeCreateStorageRoom(){
            this.reuseVisible=true;
        },
        saveStorageRoom(){
            this.$refs.addStorageRoom.saveItem();
        },
        saveStorageColumn(){
            this.$refs.addStorageColumn.saveItem();
        },
        saveStorageRow(){
            this.$refs.addStorageRow.saveItem();
        },
        refreshRow(){
            this.selectedItem=[];
            this.selectColumn(this.selectColumnCoding,this.selectColumnIndex,this.selectedColumnId);
        },
        refreshColumn(){
            this.selectedColumnId='';
            this.selectColumnCoding='';
            this.refreshRow();
            this.searchByStore(this.selectRoomCoding,this.selectIndex,this.selectedRoomId);
        },
        getgriditem(attrName) {
            let _self = this;
            let ret = null;
            _self.gridList.forEach(element => {
                if (element.attrName == attrName) {
                ret = element;
                return;
                }
            });
            return ret;
        },
        loadGridInfo() {
            let _self = this;
            axios
                .post("/dc/getJsonParam", "rowGridInfo")
                .then(function(response) {
                    _self.showFields = [];
                    _self.gridList=response.data.data;
                    _self.gridList.forEach(element => {
                        if (element.visibleType == 1) {
                        _self.showFields.push(element.attrName);
                        }
                    });
                })
                .catch(function(error) {
                    console.log(error);
                });
        },
        deleteStorageRow(){
            let _self=this;
            _self
                .axios({
                headers: {
                    "Content-Type": "application/json;charset=UTF-8"
                },
                method: "post",
                data:_self.selectedItem.ID,
                url: "/dc/deleteStorageRow"
                })
                .then(function(response) {
                    _self.$message({
                        showClose: true,
                        message: _self.$t('message.deleteSuccess'),
                        duration: 5000,
                        type: 'success'
                    });
                    _self.refreshRow();
                })
                .catch(function(error) {
                // _self.$message(_self.$t("message.deleteFailured"));
                _self.$message({
                    showClose: true,
                    message: "删除失败！",
                    duration: 5000,
                    type: 'error'
                });
                console.log(error);
                });
        },
        confirmDeleteStorageRow(){
            let _self=this;
            if(_self.selectedItem.ID==''){
                _self.$message({
                    showClose: true,
                    message: "请选择一条要删除的数据",
                    duration: 5000,
                    type: 'error'
                });
                return;
            }
            _self.$confirm(
                    _self.$t("message.deleteInfo"),
                    _self.$t("application.info"),
                    {
                        confirmButtonText: _self.$t("application.ok"),
                        cancelButtonText: _self.$t("application.cancel"),
                        type: "warning"
                    }
                )
                .then(() => {
                 _self.deleteStorageRow();
                })
                .catch(() => {
                // this.$message({
                //   type: 'info',
                //   message: '已取消删除'
                // });
                });
        },
        deleteStorageColumn(){
            let _self=this;
            _self
                .axios({
                headers: {
                    "Content-Type": "application/json;charset=UTF-8"
                },
                method: "post",
                data:_self.selectedColumnId,
                url: "/dc/deleteStorageColumn"
                })
                .then(function(response) {
                    _self.$message({
                        showClose: true,
                        message: _self.$t('message.deleteSuccess'),
                        duration: 5000,
                        type: 'success'
                    });
                    _self.refreshColumn();
                })
                .catch(function(error) {
                // _self.$message(_self.$t("message.deleteFailured"));
                _self.$message({
                    showClose: true,
                    message: "删除失败！",
                    duration: 5000,
                    type: 'error'
                });
                console.log(error);
                });
        },
        confirmDeleteStorageColumn(){
            let _self=this;
            if(_self.selectedColumnId==''){
                _self.$message({
                    showClose: true,
                    message: "请选择一条要删除的数据",
                    duration: 5000,
                    type: 'error'
                });
                return;
            }
            _self.$confirm(
                    _self.$t("message.deleteInfo"),
                    _self.$t("application.info"),
                    {
                        confirmButtonText: _self.$t("application.ok"),
                        cancelButtonText: _self.$t("application.cancel"),
                        type: "warning"
                    }
                )
                .then(() => {
                 _self.deleteStorageColumn();
                })
                .catch(() => {
                // this.$message({
                //   type: 'info',
                //   message: '已取消删除'
                // });
                });
        },
        deleteStorageRoom(){
            let _self=this;
            _self
                .axios({
                headers: {
                    "Content-Type": "application/json;charset=UTF-8"
                },
                method: "post",
                data:_self.selectedRoomId,
                url: "/dc/deleteStorageRoom"
                })
                .then(function(response) {
                    _self.$message({
                        showClose: true,
                        message: _self.$t('message.deleteSuccess'),

                        duration: 5000,
                        type: 'success'
                    });
                    _self.searchByStore();
                    _self.refreshColumn();
                    _self.refreshRow();

                })
                .catch(function(error) {
                // _self.$message(_self.$t("message.deleteFailured"));
                _self.$message({
                    showClose: true,
                    message: "删除失败！",
                    duration: 5000,
                    type: 'error'
                });
                console.log(error);
                });
        },
        confirmDeleteStorageRoom(){
            let _self=this;
            if(_self.selectedRoomId==''){
                _self.$message({
                    showClose: true,
                    message: "请选择一条要删除的数据",
                    duration: 5000,
                    type: 'error'
                });
                return;
            }
            _self.$confirm(
                    _self.$t("message.deleteInfo"),
                    _self.$t("application.info"),
                    {
                        confirmButtonText: _self.$t("application.ok"),
                        cancelButtonText: _self.$t("application.cancel"),
                        type: "warning"
                    }
                )
                .then(() => {
                 _self.deleteStorageRoom();
                })
                .catch(() => {
                // this.$message({
                //   type: 'info',
                //   message: '已取消删除'
                // });
                });
        },
        selectColumn(coding,key,id){
            let _self=this;
            _self.selectColumnIndex=key;
            _self.selectColumnCoding=coding;
            _self.selectedColumnId=id;
            var m = new Map();
            // m.set("gridName", "ArchiveGrid");
            // m.set('folderId',indata.id);
            m.set("pageSize", _self.pageSize);
            m.set("pageIndex", (_self.currentPage - 1) * _self.pageSize);
            m.set("columnCoding",_self.selectColumnCoding);
           _self
            .axios({
            headers: {
                "Content-Type": "application/json;charset=UTF-8"
            },
            method: "post",
            data:JSON.stringify(m),
            url: "/dc/getRowByColumn"
            })
            .then(function(response) {
                
                _self.rowList=response.data.data;
                _self.itemCount = response.data.pager.total;
            })
            .catch(function(error) {
            // _self.$message(_self.$t("message.deleteFailured"));
            _self.$message({
                showClose: true,
                message: "查询失败！",
                duration: 5000,
                type: 'error'
            });
            console.log(error);
            });
        },
        searchByStore(roomCoding,key,roomID){
            let _self=this;
            _self.rowList=[];
            _self.selectColumnIndex=-1;
            _self.selectIndex=key;
            _self.selectRoomCoding=roomCoding;
            _self.selectedRoomId=roomID;
           _self
            .axios({
            headers: {
                "Content-Type": "application/json;charset=UTF-8"
            },
            method: "post",
            data:roomCoding,
            url: "/dc/getColumnsByStorageRoom"
            })
            .then(function(response) {
           
                _self.columnList=response.data.data;
            })
            .catch(function(error) {
            // _self.$message(_self.$t("message.deleteFailured"));
            _self.$message({
                showClose: true,
                message: "查询失败！",
                duration: 5000,
                type: 'error'
            });
            console.log(error);
            });
        },
        getAllStorageRoom(){
            let _self=this;
           _self
            .axios({
            headers: {
                "Content-Type": "application/json;charset=UTF-8"
            },
            method: "post",
            url: "/dc/getAllStorageRoom"
            })
            .then(function(response) {
           
                _self.storageRoomList=response.data.data;
            })
            .catch(function(error) {
            // _self.$message(_self.$t("message.deleteFailured"));
            _self.$message({
                showClose: true,
                message: "查询失败！",
                duration: 5000,
                type: 'error'
            });
            console.log(error);
            });
        }
    },
    components: {
        AddStorageRoom:AddStorageRoom,
        AddStorageColumn:AddStorageColumn,
        AddStorageRow:AddStorageRow,
        DataGrid:DataGrid
    }
}
</script>
<style scoped>
    
    .icon{
        display: block;
        margin:0 auto;
    }
    .store{
        width: 3rem;
        height: 3rem;
        background: url('/static/img/store2.png') no-repeat -7.6rem -10.4rem;
        background-size:21rem  21rem;
    }
    .name{
        display: block;
        font-size: 0.6rem;
        color: #080808;
    }
    .items{
        /* margin-top:1.5rem; */
        cursor:pointer;
        background-color:#CDB5CD;
        margin-top: 0.25rem;
        margin-left: 0.25rem;
        display: inline-block;
        width:22%;
        /*这里的 text-align:center; 能让 每个li.item 里面的 文本居中显示*/
        text-align: center;
    }
    .itemschange{
        /* margin-top:1.5rem; */
        cursor:pointer;
        background-color:rgb(124, 8, 231);
        margin-top: 0.25rem;
        margin-left: 0.25rem;
        display: inline-block;
        width:22%;
        /*这里的 text-align:center; 能让 每个li.item 里面的 文本居中显示*/
        text-align: center;
    }
    .line{
        /* margin-top:1.5rem; */
        background-color: aqua;
        margin: 0.25rem;
        cursor:pointer;
        display: inline-block;
        width:22%;
        /*这里的 text-align:center; 能让 每个li.item 里面的 文本居中显示*/
        text-align: center;
    }
    .linechange{
        /* margin-top:1.5rem; */
        background-color: rgb(55, 0, 255);
        margin: 0.25rem;
        cursor:pointer;
        display: inline-block;
        width:22%;
        /*这里的 text-align:center; 能让 每个li.item 里面的 文本居中显示*/
        text-align: center;
    }
</style>