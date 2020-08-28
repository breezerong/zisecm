     <template>
  <div>
    <el-form  :model="taskForm" style="width:100%" >
      <el-row style="width:100%">
        <div >
           <el-col style="padding-top:3px;">
            <div v-for="(approver,index)  in approvalUserList" :key="'approver_'+index">
            <el-form-item :label="approver.activityName"  :label-width="formLabelWidth" style="float:left">
              <UserSelectInput
                v-model="taskForm[approver.formAttribute]"
                v-bind:inputValue="taskForm[approver.formAttribute]"
                v-bind:roleName="approver.roleName"
                :buttonType ="formEnableType != 'TodoTask'"
              ></UserSelectInput>
            </el-form-item>
          </div>
          </el-col>
          <el-col>
            <!-- <el-form-item label="备注" :label-width="formLabelWidth" style="text-align:left">
              <el-input
                type="textarea"
                :autosize="{minRows:3}"
                v-model="taskForm.TITLE"
                auto-complete="off"
                :disabled="formEnableType != 'TodoTask'"
              ></el-input>
            </el-form-item> -->
            
            <div>
              <!-- <template v-if="inputTypeName=='借阅单'">
                <Borrow
                ref="borrow1"
                :formId1 ="formId"
                :activityName1 ="activityName"
                :processDefinitionId1 ="processDefinitionId"
                :istask1 ="istask"
                :formEditPermision1 ="formEditPermision"
                ></Borrow>
              </template> -->
              
                <ShowProperty
                ref="ShowProperty"
                width="100%"
                :itemId ="formId"
                :typeName="inputTypeName"
                ></ShowProperty>
            </div>
          </el-col>   
          <el-col>
             <el-form-item label="文件" :label-width="formLabelWidth" style="text-align:left">
                <el-button @click="viewdoc(docId)">查看文件</el-button>
                <el-button v-if="formEnableType == 'TodoTask' && inputTypeName!='借阅单'" @click="importdialogVisible=true">上传文件</el-button>
            </el-form-item>
          </el-col>   

        </div>
      </el-row>
           </el-form>

    <div slot="footer" class="dialog-footer" style="text-align:center" v-if="istask==false">
      <el-button ref="borrowCancel" type="primary" @click="cancel()">{{$t('application.cancel')}}</el-button>
      <el-button ref="borrowStartwf" @click="startWorkflow(taskForm)">启动流程</el-button>
    </div>
    <el-dialog :title="$t('application.Import')" :visible.sync="importdialogVisible" width="70%" append-to-body>
          
          <el-form size="mini" :label-width="formLabelWidth">
            
            <div style="height:200px;overflow-y:scroll; overflow-x:scroll;">
              <el-upload
                :limit="100"
                :file-list="fileList" 
                action=""
                :on-change="handleChange"
                :auto-upload="false"
                :multiple="false">
                <el-button slot="trigger" size="small" type="primary">{{$t('application.selectFile')}}</el-button>
              </el-upload>
            </div>
          </el-form> 
          <div slot="footer" class="dialog-footer">
            <el-button @click="importdialogVisible = false">{{$t('application.cancel')}}</el-button>
            <el-button type="primary" @click="uploadData(docId)">{{$t('application.start')+$t('application.Import')}}</el-button>
          </div>
        </el-dialog>
  </div>
</template>


<script type="text/javascript">
import UserSelectInput from "@/components/controls/UserSelectInput";
import ShowProperty from "@/components/ShowProperty"
// import Borrow from "@/components/form/Borrowcopy.vue";
export default {
  components: {
    UserSelectInput : UserSelectInput,
    ShowProperty : ShowProperty,
    // Borrow : Borrow
  },
  name: "EditTask",
  data() {
    return {
      gridviewName: "borrowGrid",
      test:true,
      inputTypeName:'',
      gridList: [],
      currentLanguage: "zh-cn",
      dataList: [],
      loading: false,
      formLabelWidth: "120px",
      borrowData: [],
      approvalUserList: [],
      dialogTitle: "借阅",
      borrowDialogVisible: false,
      importdialogVisible:false,
       fileList:[],
     componentName: "borrow", 
      expireTimeOption: this.dateCheck(),
      defaultProps: {
        children: "children",
        label: "name"
      },
      deptList: [],
      showDepartMentList: 0,
      currentGroupData: "",
      selectedGroupItemId: "",
      currentRootGroupData: ""
   };
  },
  
    props: {
      //输入框默认显示值
      formId: {
        type: String,
        default: ""
      },
      docId: {
        type: String,
        default: ""
      },
      istask: {
        type: Number,
        default: 0
      },
      processDefinitionId: {
        type: String,
        default: ""
      },
      activityName: {
        type: String,
        default: ""
      },
      formEditPermision: {
        type: Number ,
        default: 0
      },
      typeName:{
        type: String
      },
      formEnableType:{
        type : String,
        default: "",
        required: true
      },
      taskForm:{
        type: Object  ,
        default: function () {
            return {}
        }
      }
    },
  created() {
    let _self = this
    _self.getApprovalUserList();
    // _self.loadGridView();
    
    _self.loadData();
  },
  mounted() {
   let _self = this;
   _self.$nextTick(()=>{
     _self.getDocument();
   });
  //  _self.getDocument();
    _self.bindDepartment();
  },

  methods: {
     getApprovalUserList(){
            let _self = this;
       var m = new Map();
      m.set("processDefinitionId", _self.processDefinitionId);
      m.set("activityName", _self.activityName);
      axios
        .post("/workflow/getApprovalUserList", JSON.stringify(m))
        .then(function(response) {
         _self.approvalUserList = response.data.data;
        });
    },
    getDocument(){
      let _self = this;
      axios.post("/dc/getDocument",_self.formId)
      .then(
        function(response){
          _self.inputTypeName = response.data.data.TYPE_NAME;
        }
      )
    },
    dateCheck() {
      let _self = this;
      return {
        disabledDate(date) {
          return date.getTime() + 86400000 < Date.now();
        }
      };
    },
    loadGridView() {
      let _self = this;
      var m = new Map();
      m.set("gridName", _self.gridviewName);
      m.set("lang", _self.currentLanguage);
      axios
        .post("/dc/getGridViewInfo", JSON.stringify(m))
        .then(function(response) {
          _self.gridList = response.data.data;
            _self.loadData();
        });
    },
    loadData() {
      let _self = this;
      // axios.post("/dc/getDocumentById", _self.formId).then(function(response) {
      //   let result = response.data;
      //   if (result.code == 1) {
      //     _self.taskForm = result.data;
      //   }
      // });
      // axios
      //   .post("/dc/getFormRelateDocument", _self.formId)
      //   .then(function(response) {
      //     let result = response.data;
      //     if (result.code == 1) {
      //       _self.tabledata = result.data;
      //     }
      //   });
    },
    selectChange(selection) {
      this.selectedItemList = [];
      if (selection.length > 0) {
        for (var i = 0; i < selection.length; i++) {
          this.selectedItemList.push(selection[i]);
        }
      }
    },
    dateFormatter(row, column) {
      let datetime = row[column.property];
      return this.datetimeFormat(datetime);
    },
    cancel() {
      this.$emit("showOrHiden", false);
    },
    startWorkflow() {
      let _self = this;
      let m = new Map();
      axios
        .post("/workflow/startWorkflow", JSON.stringify(m))
        .then(function(response) {
          console.log(response);
          _self.$message({
            showClose: true,
            message: "流程发起成功!",
            duration: 2000,
            type: "success"
          });

          _self.loading = false;
          _self.cancel();
        })
        .catch(function(error) {
          _self.$message({
            showClose: true,
            message: "流程发起失败!",
            duration: 2000,
            type: "warning"
          });
          console.log(error);
          _self.loading = false;
        });
    },

    saveCurrentForm(m) {
      let _self = this;
      m = _self.getFormdataMap();

      axios
        .post("/dc/saveTaskForm", m)
        .then(function(response) {
          console.log(response.data.data);
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    getFormdataMap() {
      let _self = this;
      return  _self.taskForm;
    },

    downloadAllFile() {
      let _self = this;
      let ids = new Array();
      for (let index = 0; index < _self.tabledata.length; index++) {
        ids.push(_self.tabledata[index].ID);
      }
      window.open(
        _self.axios.defaults.baseURL +
          "/workflow/downloadAllFile?objectIds=" +
          ids,
        "_blank"
      );
    },
    viewdoc(indata) {
      let href = this.$router.resolve({
        path: "/viewdoc",
        query: {
          id: indata
          //token: sessionStorage.getItem('access-token')
        }
      });
      //console.log(href);
      window.open(href.href, "_blank");
    },
     handleChange(file, fileList){
      this.fileList = fileList;
    },
getFormData(selId){
      let _self = this;
      let formdata = new FormData();
      var data = {};
      data["ID"]=selId;
      formdata.append("metaData",JSON.stringify(data));
      _self.fileList.forEach(function (file) {
        //console.log(file.name);
        formdata.append("uploadFile", file.raw, file.name);
      });
      return formdata;
    },
      //上传文件
    uploadData(selId){
      let _self = this;
      let formdata = _self.getFormData(selId);
      //console.log("UploadData getData");
      //console.log(formdata);
      _self.axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        datatype: 'json',
        method: 'post',
        data: formdata,
        url: '/dc/mountFile'
      })
      .then(function(response) {
        _self.importdialogVisible = false;
        // _self.$message("导入成功!");
        _self.$message({
              showClose: true,
              message: "导入成功!",
              duration: 2000,
              type: 'success'
            });
      })
      .catch(function(error) {
        console.log(error);
      });
    },
       // 绑定部门
    bindDepartment() {
      let _self = this;
      _self.showDepartMentList = 1;
      var m = new Map();
      m.set("id", 0);
      m.set("groupType", 1);
      axios
        .post("/admin/getGroups", JSON.stringify(m))
        .then(function(response) {
          _self.deptList = response.data.data;
          _self.currentRootGroupData = response.data.data;
          //_self.handleNodeClick(_self.deptList[0]);
          //console.log(JSON.stringify(_self.deptList));
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    // 部门点击事件
    handleNodeClick(indata) {
      let _self = this;
      _self.currentGroupData = indata;
      if (_self.currentRootGroupData[0].name != _self.currentGroupData.name) {
        _self.taskForm.C_CREATION_UNIT = _self.currentGroupData.name;
      }
      _self.selectedGroupItemId = indata.id;
      var m = new Map();
      m.set("id", indata.id);
      m.set("groupType", 1);
      if (indata.extended == false) {
        axios
          .post("/admin/getGroups", JSON.stringify(m))
          .then(function(response) {
            // _self.$message("获取子节点成功!");
            indata.children = response.data.data;
            indata.extended = true;
            //console.log(JSON.stringify(indata));
          })
          .catch(function(error) {
            console.log(error);
          });
      }
    }
  }
};
</script>