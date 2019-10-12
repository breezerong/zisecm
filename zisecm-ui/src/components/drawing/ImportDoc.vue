<template>
  <div>
    <el-dialog title="发起流程" :visible.sync="propertyVisible" @close="propertyVisible = false">
      <WorkFlowDialog ref="flowDialog"  width="560" 
       v-bind:itemId="selectedItemId" v-bind:typeName="typeName" v-bind:docsId="docsIDs"></WorkFlowDialog>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="startworkflow()">启动流程</el-button>
        <el-button @click="propertyVisible = false">{{$t('application.cancel')}}</el-button>
      </div>
    </el-dialog>
        <el-dialog title="导入" :visible.sync="dialogVisible" width="70%">
          <DynamicForm ref="formObj" typeName="图纸" isUpload></DynamicForm>
          <!-- <el-form size="mini" :label-width="formLabelWidth">
            <el-row>
            <template v-for="item in formDataList">
              <el-col :span="12">
              <el-form-item :hidden="item.isHide" :label="item.label" :rules="[{required:item.required,message:'必填',trigger:'blur'}]">
                <el-input v-if="item.controlType=='TextBox'" type="text" :name="item.attrName" v-model="item.defaultValue"></el-input>
                <el-input v-else-if="item.controlType=='Integer'" type="number" :name="item.attrName" v-model="item.defaultValue"></el-input>
                <el-checkbox v-else-if="item.controlType=='Boolean'"  :name="item.attrName" v-model="item.defaultValue"></el-checkbox>
                <el-date-picker v-else-if="item.controlType=='Date'" :name="item.attrName" v-model="item.defaultValue" type="date" placeholder="选择日期" style="display:block;"></el-date-picker>
                <el-select  :name="item.attrName"
                v-else-if="item.controlType=='Select' || item.controlType=='Department' || item.controlType=='SQLSelect'" 
                v-model="item.defaultValue" :placeholder="'请选择'+item.label" :disabled="item.readOnly" style="display:block;">
                    <div v-for="name in item.validValues">
                      <el-option  :label="name" :value="name"></el-option>
                    </div>
                  </el-select>
                  <template v-else-if="item.controlType=='UserSelect'">
                      <el-input type="text" :name="item.attrName" v-model="item.defaultValue"/>
                  </template>
              </el-form-item>
              </el-col>
            </template>
            </el-row>

            <div style="height:200px;overflow-y:scroll; overflow-x:scroll;">
              <el-upload
                :limit="100"
                :file-list="fileList" 
                action=""
                :on-change="handleChange"
                :auto-upload="false"
                :multiple="true">
                <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
              </el-upload>
            </div>
          </el-form> -->
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="uploadData()">开始导入</el-button>
          </div>
        </el-dialog>
      <table border="0" width="100%">
          <tr>
            <td class="navbar">
              /图纸管理/导入图纸
            </td>
          </tr>
          <tr>
            <td>
              <table border="0" width="100%" class="topbar">
                <tr>
                  <td align="left" width="300px">
                    <el-input v-model="inputkey" placeholder="请输入关键字" @change="search()" prefix-icon="el-icon-search"></el-input>
                  </td>
                  <td>
                    <el-button type="primary" icon="el-icon-upload" @click="openUploadPage()">导入</el-button>
                    <el-button type="primary" icon="el-icon-s-check" @click="showWorkflow()">发起流程</el-button>
                    <el-button type="primary" icon="el-icon-delete" @click="deleteDrawing()">删除</el-button>
                  </td>
                  <td>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        <tr>
          <td>
            <el-table
                :data="dataList"
                border
                :height="tableHeight"
                v-loading="loading"
                @selection-change="selectChange"
                style="width: 100%">
              <el-table-column type="selection" width="40" @selection-change="selectChange">
              </el-table-column> 
              <el-table-column type="index" width="50">
              </el-table-column>      
              <el-table-column prop="NAME" label="名称" sortable min-width="30%">
              </el-table-column>
              <el-table-column prop="C_PROJECT" label="项目名称" sortable min-width="20%" >
              </el-table-column>
              <el-table-column prop="CREATION_DATE" label="创建时间" sortable :formatter="dateFormat" width="180">
              </el-table-column>
              <el-table-column label="操作"  width="120">
                <template slot-scope="scope">
                  <el-button :plain="true" type="success" size="small" icon="save" @click="showItemContent(scope.row)" >查看</el-button> 
                </template>
              </el-table-column>
            </el-table>
            <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="currentPage"
              :page-sizes="[20, 50, 100, 200]"
              :page-size="pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="itemCount">
            </el-pagination>
            </td>
          </tr>
        </table>
  </div>
</template>

<script type="text/javascript">
import WorkFlowDialog from '@/components/dc/WorkFlow'
import DynamicForm from '@/components/controls/DynamicForm'

export default {
  name: "ImportDoc",
  permit: 1,
  data() {
    return {
      currentData: [],
      dataList: [],
      formDataList:[],
      dataListFull: [],
      inputkey: "",
      pageSize: 20,
      itemCount: 0,
      selectedItems:[],
      fileList:[],
      currentPage: 1,
      selectedItemId: 0,
      propertyVisible:false,
      loading: false,
      dialogVisible: false,
      dialogWfVisible: false,
      tableHeight: window.innerHeight - 170,
      typeName:"生效表单",
      form: {
        NAME: "",
        TYPE_NAME:"图纸",
        C_PROJECT:"",
        C_APPROVER:""
      },
      docsIDs:[],
      formLabelWidth: "120px",
      projectOptions:[]
    };
  },components: {
    WorkFlowDialog: WorkFlowDialog,
    DynamicForm : DynamicForm
  },
   created(){ 
    let _self = this;
    var psize = localStorage.getItem("docPageSize");
    if (psize) {
      _self.pageSize = parseInt(psize);
    }
    _self.refreshData();

    _self.axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        method: "post",
        data: '4',
        url: "/zisecm/admin/getFormItem"
      }).then(function(response){
        _self.formDataList = response.data.data;
        console.log(_self.formDataList)
        for (let index = 0; index < _self.formDataList.length; index++) {
          const element = _self.formDataList[index];
          var attrName = element.attrName;
          var attrValue = element.defaultValue==null?"":element.defaultValue;
          _self.form[attrName]=attrValue;
        }
        _self.loading = false;
      })
      .catch(function(error){
        console.log(error);
        _self.loading = false;
      });

    },
  methods: {
    handleChange(file, fileList){
      this.fileList = fileList;
    },
    //启动流程
    startworkflow() {
      
      this.$refs.flowDialog.startWorkflow();
    },
    // 显示流程表单
    showWorkflow()
    {
      let _self = this;
      if(_self.selectedItems.length<1){
        _self.$message("请选择一条或多条数据");
        return;
      }
      for(var i=0;i<_self.selectedItems.length;i++){
        _self.docsIDs.push(_self.selectedItems[i].ID);
      }
       _self.selectedItemId = "";
        _self.propertyVisible = true; 
        if(_self.$refs.WorkFlowDialog){
          _self.$refs.WorkFlowDialog.myItemId = "";
          _self.$refs.WorkFlowDialog.myTypeName = "生效表单";
          // _self.$refs.WorkFlowDialog.myFolderId = _self.currentFolder.id;
          _self.$refs.WorkFlowDialog.loadFormInfo();
        }
    },
    refreshData() {
      let _self = this;
      _self.loading = true;
      var m = new Map();
      m.set("status","新建");
      m.set("condition", _self.inputkey);
      m.set("pageSize", _self.pageSize);
      m.set("pageIndex", (_self.currentPage - 1) * _self.pageSize);
      _self.axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        method: 'post',
        data: JSON.stringify(m),
        url: '/zisecm/drawing/getDrawing'
      })
      .then(function(response) {
        _self.dataList = response.data.data;
        //console.log("getDrawing DataList:"+JSON.stringify(_self.dataList));
        _self.dataListFull = response.data.data;
        //加载分页信息
        _self.itemCount = response.data.pager.total;
        //console.log("getDrawing Count:"+_self.itemCount);
        _self.loading = false;
      })
      .catch(function(error) {
        console.log(error);
        _self.loading = false;
      });
    },
    // 表格行选择
    selectChange(val) 
    {
      
      this.selectedItems = val;
    },
    // 分页 页数改变
    handleSizeChange(val) {
      this.pageSize = val;
      localStorage.setItem("docPageSize", val);
      this.refreshData();
      //console.log('handleSizeChange', val);
    },
    // 分页 当前页改变
    handleCurrentChange(val) {
      this.currentPage = val;
      this.refreshData();
    },
    //打开上传页面
    openUploadPage(){
      
      let _self = this;
      _self.loading = true;
      
      _self.axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        method: 'post',
        url: '/zisecm/dc/getProjects'
      })
      .then(function(response) {
        _self.projectOptions = response.data.data;
        //console.log("List:"+JSON.stringify(_self.projectOptions));
        _self.dialogVisible=true;
        _self.loading = false;
      })
      .catch(function(error) {
        console.log(error);
        _self.loading = false;
      });
    },
    uploadData(){
      let _self = this;
      let formdata = this.$refs.formObj.getFormData();
      console.log("UploadData getData");
      console.log(formdata);
      _self.axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        datatype: 'json',
        method: 'post',
        data: formdata,
        url: '/zisecm/drawing/uploadDrawing'
      })
      .then(function(response) {
        _self.dialogVisible = false;
        _self.refreshData();
        _self.$message("导入成功!");
      })
      .catch(function(error) {
        console.log(error);
      });
    },
    uploaddrawing() {
      let _self = this;
      let formdata = new FormData();
      formdata.append("metaData",JSON.stringify(_self.form));
      _self.fileList.forEach(function (file) {
                formdata.append("files", file.raw, file.name);
            });
      _self.axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        datatype: 'json',
        method: 'post',
        data: formdata,
        url: '/zisecm/drawing/uploadDrawing'
      })
      .then(function(response) {
        _self.dialogVisible = false;
        _self.refreshData();
        _self.$message("导入成功!");
      })
      .catch(function(error) {
        console.log(error);
      });
    },
    // 查看内容

    showItemContent(indata){
      let _self = this;
      _self.imageArray = [];
      _self.currentType = indata.FORMAT_NAME;
      // 拦截器会自动替换成目标url
      _self.imageArray[0] =  "/zisecm/dc/getContent?id="+indata.ID+"&token="+sessionStorage.getItem('access-token');
      if(_self.currentType == "pdf"){
         window.open("./static/pdfviewer/web/viewer.html?file="+encodeURIComponent(_self.imageArray[0])+"&.pdf");
      }else{
         _self.imageViewVisible =true;
      }
    },
    dateFormat(row, column) {
        let datetime = row.CREATION_DATE;
        if(datetime){
          datetime = new Date(datetime);
          let y = datetime.getFullYear() + '-';
          let mon = datetime.getMonth()+1 + '-';
          let d = datetime.getDate();
          return y + mon + d + " "+datetime.getHours()+":"+datetime.getMinutes()+":"+datetime.getSeconds();
        }
        return ''
      },
    search() {
      let _self = this;
      _self.dataList = _self.dataListFull.filter(function(item){
          return item.NAME.match(_self.inputkey);
        }
      );
    },
    deleteDrawing(){
      
      let _self = this;
      var ids=new Array();
      for(var i=0;i<_self.selectedItems.length;i++){
        ids.push(_self.selectedItems[i].ID);
      }
      console.log("ids=="+JSON.stringify(ids))
      _self.axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        datatype: 'json',
        method: 'post',
        data: JSON.stringify(ids),
        url: '/zisecm/drawing/delDrawing'
      })
      .then(function(response) {
        _self.refreshData();
        _self.$message("删除成功!");
      })
      .catch(function(error) {
        console.log(error);
      });
    },
    updateStatus(){//已审批
      let _self = this;
      var obj={"STATUS":"已审批","list":""};
      var ids=new Array();
      for(var i=0;i<_self.selectedItems.length;i++){
        ids.push(_self.selectedItems[i].ID);
      }
      obj.list=JSON.stringify(ids);
      _self.axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        datatype: 'json',
        method: 'post',
        data: obj,
        url: '/zisecm/drawing/updateStatus'
      })
      .then(function(response) {
        _self.refreshData();
        _self.$message("流程发起成功!");
      })
      .catch(function(error) {
        console.log(error);
      });
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1,
h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
