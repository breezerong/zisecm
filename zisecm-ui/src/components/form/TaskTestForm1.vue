     <template>
  <div>
    <el-form ref="borrowForm" :model="borrowForm" style="width:100%" >
      <el-row style="width:100%">
        <div >
           <el-col style="padding-top:3px;">
            <div v-for="(approver,index)  in approvalUserList" :key="'approver_'+index">
            <el-form-item :label="approver.activityName"  :label-width="formLabelWidth" style="float:left">
              <UserSelectInput
                v-model="borrowForm[approver.formAttribute]"
                v-bind:inputValue="borrowForm[approver.formAttribute]"
                v-bind:roleName="approver.roleName"
              ></UserSelectInput>
            </el-form-item>
          </div>
          </el-col>
        </div>
      </el-row>
    </el-form>

    <div slot="footer" class="dialog-footer" style="text-align:center" v-if="istask==false">
      <el-button ref="borrowCancel" type="primary" @click="cancel()">取 消</el-button>
      <el-button ref="borrowStartwf" @click="startWorkflow(borrowForm)">启动流程</el-button>
    </div>
  </div>
</template>


<script type="text/javascript">
import UserSelectInput from "@/components/controls/UserSelectInput";
export default {
  components: {
    UserSelectInput: UserSelectInput
  },
  name: "BorrwoForm",
  data() {
    return {
      gridviewName: "borrowGrid",
      gridList: [],
      currentLanguage: "zh-cn",
      tabledata: [],
      dataList: [],
      loading: false,
      formLabelWidth: "120px",
      borrowData: [],
      approvalUserList: [],
      dialogTitle: "借阅",
      borrowDialogVisible: false,
      componentName: "borrow", 
      borrowForm: {
        C_DRAFTER: sessionStorage.getItem("access-userName"),
        C_DESC1: sessionStorage.getItem("access-department"),
        TITLE: "",
        SUB_TYPE: "在线浏览",
        C_START_DATE: new Date(),
        C_END_DATE: new Date(),
        C_REVIEWER1: "",
        C_REVIEWER2: "",
        C_REVIEWER3: "",
        C_COMMENT: "",
        C_CREATION_UNIT: "",
        STATUS: ""
      },
      formId: "",
      istask: 0,
      formEditPermision: 0,
      processDefinitionId: "",
      activityName: "",
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

  created() {
    let _self = this;
    _self.formId = _self.$route.query.formId;
    if (typeof _self.$route.query.istask != "undefined") {
      _self.formEditPermision = _self.$route.query.formEditPermision;
      _self.istask = _self.$route.query.istask;
      _self.processDefinitionId = _self.$route.query.processDefinitionId;
      _self.activityName = _self.$route.query.activityName;
  }
    _self.getApprovalUserList();
    // _self.loadGridView();
  },
  mounted() {
    let _self = this;
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
      axios.post("/dc/getDocumentById", _self.formId).then(function(response) {
        let result = response.data;
        if (result.code == 1) {
          _self.borrowForm = result.data;
        }
      });
      axios
        .post("/dc/getFormRelateDocument", _self.formId)
        .then(function(response) {
          let result = response.data;
          if (result.code == 1) {
            _self.tabledata = result.data;
          }
        });
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
        .post("/dc/saveBorrowForm", m)
        .then(function(response) {
          console.log(response.data.data);
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
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
      let condition = indata.ID;
      let href = this.$router.resolve({
        path: "/viewdoc",
        query: {
          id: condition
          //token: sessionStorage.getItem('access-token')
        }
      });
      //console.log(href);
      window.open(href.href, "_blank");
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
    addCreationUnit() {
      let _self = this;
      _self.borrowForm.C_CREATION_UNIT = _self.currentGroupData.name;
    },
    // 部门点击事件
    handleNodeClick(indata) {
      let _self = this;
      _self.currentGroupData = indata;
      if (_self.currentRootGroupData[0].name != _self.currentGroupData.name) {
        _self.borrowForm.C_CREATION_UNIT = _self.currentGroupData.name;
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