<template>
    <div>
      <el-dialog width="80%" :title="$t('application.property')" :visible.sync="archiveBackupVisible" @close="archiveBackupVisible = false">
        <ArchiveBackupCreate ref="archiveBackupCreate"></ArchiveBackupCreate>
        <div slot="footer" class="dialog-footer">
          <el-button @click="saveItem">{{$t('application.save')}}</el-button> 
          <el-button @click="archiveBackupVisible = false">{{$t('application.cancel')}}</el-button>
        </div>
      </el-dialog>
        <el-row>
            
            <el-col :span="12" class="topbar-button">
                <el-button type="primary" plain
                size="small" icon="el-icon-circle-plus-outline" @click="onCreate">{{$t('application.new')}}</el-button>
                <el-button type="primary" plain
                size="small" icon="el-icon-upload2" @click="release('制作中')">发布</el-button>
                <el-button type="primary" plain
                size="small" icon="el-icon-folder-delete" @click="onDeleleFileItem">{{$t('application.delete')}}</el-button>
            </el-col>
            
        </el-row>
        <el-row>
            <el-col>
                <DataGrid ref="orderGrid" key="main" v-bind:itemDataList="itemDataList"
                      v-bind:columnList="gridList" @pagesizechange="pageSizeChange"
                      @pagechange="pageChange" v-bind:itemCount="itemCount"
                      v-bind:tableHeight="rightTableHeight" :isshowOption="true"
                      :loading="orderLoading"
                       @selectchange="orderSelectChange" @refreshdatagrid="loadGridData"></DataGrid>
            </el-col>

        </el-row>
        
    </div>
</template>
<script type="text/javascript">
import DataGrid from'@/components/DataGrid';
import ArchiveBackupCreate from '@/views/record/ArchiveBackupCreate'
export default {
    name:'archiveBackupNew',
    permit:1,
    data(){
        return{
          archiveBackupVisible:false,
             gridList:[],
             itemDataList:[],
             itemDataListFull:[],
             pageSize: 20,
             currentPage: 1,
             itemCount: 0,
             archiveBackupVisible:false,
             orderLoading:false,
             selectedOrder:[],
            form: {
              coding:"",
              title:"",
              createDate:"",
              endDate:"",
              size:0,
              director:"",
              condition:""
            },
             rightTableHeight: window.innerHeight-130
        }
       
    },
    mounted(){
        this.loadGridInfo();
        this.loadGridData();
    },
    components:{
        DataGrid:DataGrid,
        ArchiveBackupCreate:ArchiveBackupCreate
    },
    methods:{
       onCreate(){
         let _self=this;
         _self.archiveBackupVisible=true;
         setTimeout(()=>{
           _self.$refs.archiveBackupCreate.loadFormInfo();
         });
       },
        pageSizeChange(val){
            this.pageSize = val;
            localStorage.setItem("docPageSize",val);
            _self.loadGridData();
        },
        orderSelectChange(val){
          this.selectedOrder = val;
        },
        // 删除文档事件
        onDeleleFileItem() {
          let _self = this;
          if(_self.selectedOrder.length==0){
            // _self.$message("请选择一条要删除的文件数据！")
            _self.$message({
                  showClose: true,
                  message: "请选择一条或多条要删除的文件数据！",
                  duration: 2000,
                  type: 'warning'
              });
            return;
          }
          this.$confirm(
            _self.$t("message.deleteInfo"),
            _self.$t("application.info"),
            {
              confirmButtonText: _self.$t("application.ok"),
              cancelButtonText: _self.$t("application.cancel"),
              type: "warning"
            }
          )
            .then(() => {
              _self.deleleFileItem();
            })
            .catch(() => {
              // this.$message({
              //   type: 'info',
              //   message: '已取消删除'
              // });
            });
        },
        
        // 删除文档
        deleleFileItem() {
          let _self = this;
          var m = [];
          let tab = _self.selectedOrder;
          var i;
          for (i in tab) {
            m.push(tab[i]["ID"]);
          }

          _self
            .axios({
              headers: {
                "Content-Type": "application/json;charset=UTF-8"
              },
              method: "post",
              data:JSON.stringify(m),
              url: "/dc/delDocumentAndRelation"
            })
            .then(function(response) {
              _self.loadGridData();
              _self.$message(_self.$t("message.deleteSuccess"));
            })
            .catch(function(error) {
              // _self.$message(_self.$t("message.deleteFailured"));
              _self.$message({
                  showClose: true,
                  message: _self.$t("message.deleteFailured"),
                  duration: 5000,
                  type: 'error'
              });
              console.log(error);
            });
        },
        release(statusStep){
          let _self=this;
          if(_self.selectedOrder.length==0){
            _self.$message({
                showClose: true,
                message: "请选择一条或多条数据！",
                duration: 2000,
                type: "warning"
              });
              return;
          }
          let tab = _self.selectedOrder;
          var params = new Map();
          var m = [];
          var i;
          for (i in tab) {
            m.push(tab[i]["ID"]);
          }
          params.set("ids", m);
          params.set("status", statusStep);
          console.log(JSON.stringify(m));
          _self.orderLoading=true;
          _self
            .axios({
              headers: {
                "Content-Type": "application/json;charset=UTF-8"
              },
              method: "post",
              data: JSON.stringify(params),
              url: "/dc/updateBackupStatus"
            })
            .then(function(response) {
              _self.orderLoading=false;
              _self.loadGridData();
             
              // _self.$message("操作成功");
              _self.$message({
                  showClose: true,
                  message: "操作成功！",
                  duration: 2000,
                  type: 'success'
                });
            })
            .catch(function(error) {
              // _self.$message("操作失败");
              _self.$message({
                  showClose: true,
                  message: "操作失败",
                  duration: 5000,
                  type: 'error'
                });
              console.log(error);
            });

        },
        // 保存文档
    saveItem(){
      if(!this.$refs.archiveBackupCreate.validFormValue()){
        return;
      }
      let _self=this;
      var m = new Map();
      let dataRows = this.$refs.archiveBackupCreate.dataList;
      var i;
      for (i in dataRows) {
        if(dataRows[i].attrName && dataRows[i].attrName !='')
        {
          if(dataRows[i].attrName !='FOLDER_ID'&&dataRows[i].attrName !='ID')
          {
            m.set(dataRows[i].attrName, dataRows[i].defaultValue);
          }
        }
      }
      if(_self.$refs.archiveBackupCreate.myItemId!='')
      {
        m.set('ID',_self.$refs.archiveBackupCreate.myItemId);
      }
      if(_self.$refs.archiveBackupCreate.myTypeName!='')
      {
        m.set('TYPE_NAME',_self.$refs.archiveBackupCreate.myTypeName);
        // m.set('C_DESC1',_self.$refs.archiveBackupCreate.sqlStr);
        m.set('STATUS','新建');
        
      }
      let formdata = new FormData();
      formdata.append("metaData",JSON.stringify(m));
      
      if(_self.$refs.archiveBackupCreate.file!="")
      {
        //console.log(_self.file);
        formdata.append("uploadFile",_self.$refs.archiveBackupCreate.file.raw);
      }
      // console.log(JSON.stringify(m));
      if(_self.$refs.archiveBackupCreate.myItemId=='')
      {
        _self.axios({
          headers: {
            'Content-Type': 'multipart/form-data'
            // x-www-form-urlencoded'
            //"Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data: formdata,
          url: "/dc/newArchiveOrDocument"
        })
        .then(function(response) {
          let code = response.data.code;
          //console.log(JSON.stringify(response));
          if(code==1){
            // _self.$message(_self.$t('message.newSuccess'));
            _self.$message({
              showClose: true,
              message: _self.$t('message.newSuccess'),
              duration: 2000,
              type: 'success'
            });
            _self.archiveBackupVisible=false;
            
            _self.loadGridData();
           
          }
          else{
            //  _self.$message(_self.$t('message.newFailured'));
             _self.$message({
                  showClose: true,
                  message: _self.$t('message.newFailured'),
                  duration: 5000,
                  type: "error"
                });
          }
        })
        .catch(function(error) {
          // _self.$message(_self.$t('message.newFailured'));
          _self.$message({
                  showClose: true,
                  message: _self.$t('message.newFailured'),
                  duration: 5000,
                  type: "error"
                });
          console.log(error);
        });
      }
      else
      {
        _self.axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data: JSON.stringify(m),
          url: "/dc/saveDocument"
        })
        .then(function(response) {
          let code = response.data.code;
          //console.log(JSON.stringify(response));
          if(code==1){
            _self.$emit('onSaved','update');
          }
          else{
            //  _self.$message(_self.$t('message.saveFailured'));
             _self.$message({
                  showClose: true,
                  message: _self.$t('message.saveFailured'),
                  duration: 5000,
                  type: "error"
                });
          }
        })
        .catch(function(error) {
          // _self.$message(_self.$t('message.saveFailured'));
          _self.$message({
                  showClose: true,
                  message: _self.$t('message.saveFailured'),
                  duration: 5000,
                  type: "error"
                });
          console.log(error);
        });
      }
    },
    // 保存结果事件
    onSaved(indata){
      let _self=this;
      if(indata=='update')
      {
        // this.$message(this.$t("message.saveSuccess"));
        
        _self.$message({
          showClose: true,
          message: _self.$t("message.saveSuccess"),
          duration: 2000,
          type: 'success'
            });
      }
      else
      {
        // this.$message("新建成功!");
        _self.$message({
          showClose: true,
          message: _self.$t('message.operationSuccess'),
          duration: 2000,
          type: 'success'
            });
      }
      _self.propertyVisible = false;
      _self.loadGridData();
      
    },
        // 分页 当前页改变
        pageChange(val) {
            this.currentPage = val;
            this.loadGridData();
            //console.log('handleCurrentChange', val);
        },
        // 加载借阅单表格数据
        loadGridData() {
          let _self = this;
          _self.orderLoading=true;
          var key0 = "";
          if (key0 != "") {
            key0 = " (coding like '%" + key0 + "%' or C_DRAFTER like '%" + key0 + "%') and STATUS='新建' ";
          }else{
              key0=" STATUS='新建' "
          }
        
          var m = new Map();
          m.set("gridName", "ArchiveBackup");
          // m.set('folderId',indata.id);
          m.set("condition", key0);
          
          m.set("pageSize", _self.pageSize);
          m.set("pageIndex", (_self.currentPage - 1) * _self.pageSize);
          m.set("orderBy", "");
          // console.log('pagesize:', _self.pageSize);
          axios.post("/dc/getBorrowOrder",JSON.stringify(m))
            .then(function(response) {
              _self.itemDataList = response.data.data;
              _self.itemDataListFull = response.data.data;
              _self.itemCount = response.data.pager.total;
              //console.log(JSON.stringify(response.data.datalist));
              _self.orderLoading = false;
            })
            .catch(function(error) {
              console.log(error);
              _self.orderLoading = false;
            });
        },
        
            // 加载表格样式
        loadGridInfo() {
          let _self = this;
          _self.orderLoading = true;
          var m = new Map();
          m.set("gridName", "ArchiveBackup");
          m.set("lang", _self.getLang());
          axios.post("/dc/getGridViewInfo",JSON.stringify(m))
            .then(function(response) {
              _self.gridList = response.data.data;
              _self.rightTableHeight = "100%";
              _self.orderLoading = false;
            })
            .catch(function(error) {
              console.log(error);
              _self.orderLoading = false;
            });
        }

        }

};
</script>
<style scoped>

</style>