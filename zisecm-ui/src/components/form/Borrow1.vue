     <template>
  <div>
    <el-form ref="borrowForm" :model="borrowForm" style="width:100%" :rules="rules">
      <el-row style="width:100%">
        <div v-if="formEditPermision==1">
          <el-col>
            <el-form-item
              label="借阅单号"
              :label-width="formLabelWidth"
              style="float:left"
            >{{borrowForm.CODING}}</el-form-item>
          </el-col>
          <el-col>
            <el-form-item label="姓名" :label-width="formLabelWidth" style="float:left">
              <el-input v-model="borrowForm.C_DRAFTER" auto-complete="off" readonly></el-input>
            </el-form-item>
            <el-form-item label="电话" :label-width="formLabelWidth" style="float:left">
              <el-input v-model="borrowForm.TITLE" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item
              prop="C_DESC1"
              label="用户部门"
              :label-width="formLabelWidth"
              style="float:left"
            >
              <el-input v-model="borrowForm.C_DESC1" auto-complete="off"></el-input>
            </el-form-item>
          </el-col>
          <el-col>
            <el-form-item label="借阅类型" :label-width="formLabelWidth" style="float:left">
              <el-radio-group v-model="borrowForm.SUB_TYPE">
                <el-radio-button label="在线浏览">在线浏览</el-radio-button>
                <el-radio-button label="纸质借阅">纸质借阅</el-radio-button>
                <el-radio-button label="下载">下载</el-radio-button>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col>
            <el-form-item
              prop="C_ITEM1_DATE"
              label="借阅开始时间"
              :label-width="formLabelWidth"
              style="float:left"
            >
              <el-date-picker
                :picker-options="expireTimeOption"
                v-model="borrowForm.C_ITEM1_DATE"
                auto-complete="off"
                value-format="yyyy-MM-dd HH:mm:ss"
              ></el-date-picker>
            </el-form-item>
            <el-form-item
              prop="C_ITEM2_DATE"
              label="借阅结束时间"
              :label-width="formLabelWidth"
              style="float:left"
            >
              <el-date-picker
                :picker-options="expireTimeOption"
                v-model="borrowForm.C_ITEM2_DATE"
                auto-complete="off"
                value-format="yyyy-MM-dd HH:mm:ss"
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col>
            <el-table :data="tabledata" border v-loading="loading" @selection-change="selectChange">
              <el-table-column type="selection" width="40"></el-table-column>
              <el-table-column type="index" label="#" width="50"></el-table-column>
              <el-table-column prop="id" label="id" v-if="1==2" min-width="15%" sortable></el-table-column>
              <el-table-column width="40">
                <template slot-scope="scope">
                  <img
                    v-if="scope.row.TYPE_NAME=='图册'"
                    :src="'./static/img/drawing.gif'"
                    :title="scope.row.TYPE_NAME"
                    border="0"
                  />
                  <img
                    v-else-if="scope.row.TYPE_NAME=='卷盒'"
                    :src="'./static/img/box.gif'"
                    :title="scope.row.TYPE_NAME"
                    border="0"
                  />
                  <img
                    v-else-if="scope.row.FORMAT_NAME==null || scope.row.FORMAT_NAME==''"
                    :src="'./static/img/format/f_undefined_16.gif'"
                    title="无电子文件"
                    border="0"
                  />
                  <img
                    v-else
                    :src="'./static/img/format/f_'+scope.row.FORMAT_NAME+'_16.gif'"
                    :title="scope.row.FORMAT_NAME"
                    border="0"
                  />
                </template>
              </el-table-column>>
              <template v-for="item in gridList">
                <el-table-column :key="item.id" :label="item.label" :prop="item.attrName">
                  <template slot-scope="scope">
                    <template
                      v-if="item.attrName=='C_ARCHIVE_DATE'"
                    >{{dateFormat(scope.row.C_ARCHIVE_DATE)}}</template>
                    <template v-else>{{scope.row[item.attrName]}}</template>
                  </template>
                </el-table-column>
              </template>
              <el-table-column align="left">
                <template slot-scope="scope">
                  <el-button size="mini" @click="viewdoc(scope.row)">查看</el-button>
                  <el-button
                    v-show="!startFlowNodeJudge"
                    size="mini"
                    @click="removeItemFromForm(scope.row)"
                  >移除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-col>
          <!-- 是否添加购物车 -->
          <el-col v-show="!startFlowNodeJudge">
            <template>
              <el-button @click="showOrCloseShopingCart()">{{showOrCloseShopingCartLabel}}</el-button>
            </template>
            <div v-if="vshowShopingCart==true">
              <ShowShopingCart
                ref="ShowShopingCart"
                width="100%"
                v-bind:formId="formId"
                v-bind:excludeRows="tabledata"
                showFooter="fasle"
              ></ShowShopingCart>
              <el-button ref="add" style="float:left" @click="addToFormFromShopingCart()">添加到表单</el-button>
            </div>
          </el-col>
          <el-col style="padding-top:3px;">
            <!-- <div v-for="(approver,index)  in approvalUserList" :key="'approver_'+index">
              <el-form-item
                :label="approver.activityName"
                :label-width="formLabelWidth"
                style="float:left"
              >
                <UserSelectInput
                  v-model="borrowForm[approver.formAttribute]"
                  v-bind:inputValue="borrowForm[approver.formAttribute]"
                  v-bind:roleName="approver.roleName"
                ></UserSelectInput>
              </el-form-item>
            </div>-->
            <el-row>
              <el-col :span="8">
                <el-form-item label="申请人领导" :label-width="formLabelWidth">
                  <UserSelectInput
                    v-model="borrowForm.C_REVIEWER1"
                    v-bind:inputValue="borrowForm.C_REVIEWER1"
                    v-bind:roleName="系统管理员"
                    :isRepeat = true
                  ></UserSelectInput>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="形成部门领导" :label-width="formLabelWidth">
                  <UserSelectInput
                    v-model="borrowForm.C_REVIEWER2"
                    v-bind:inputValue="borrowForm.C_REVIEWER2"
                    v-bind:roleName="系统管理员"
                  ></UserSelectInput>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="分管领导" :label-width="formLabelWidth">
                  <UserSelectInput
                    v-model="borrowForm.C_REVIEWER3"
                    v-bind:inputValue="borrowForm.C_REVIEWER3"
                    v-bind:roleName="系统管理员"
                  ></UserSelectInput>
                </el-form-item>
              </el-col>
            </el-row>
          </el-col>
          <el-col>
            <el-form-item label="借阅目的" :label-width="formLabelWidth" style="text-align:left">
              <el-input
                type="textarea"
                :autosize="{minRows:3}"
                v-model="borrowForm.C_COMMENT"
                auto-complete="off"
              ></el-input>
            </el-form-item>
          </el-col>
        </div>
        <!-- 分界线，以下为查看视图 -->
        <div v-else>
          <el-col>
            <el-form-item
              label="借阅单号"
              :label-width="formLabelWidth"
              style="float:left"
            >{{borrowForm.CODING}}</el-form-item>
          </el-col>
          <el-col>
            <el-form-item
              label="姓名"
              :label-width="formLabelWidth"
              style="float:left"
            >{{borrowForm.C_DRAFTER}}</el-form-item>
            <el-form-item
              label="电话"
              :label-width="formLabelWidth"
              style="float:left"
            >{{borrowForm.TITLE}}</el-form-item>
            <el-form-item
              label="用户部门"
              :label-width="formLabelWidth"
              style="float:left"
            >{{borrowForm.C_DESC1}}</el-form-item>
          </el-col>
          <el-col>
            <el-form-item
              label="借阅类型"
              :label-width="formLabelWidth"
              style="float:left"
            >{{borrowForm.SUB_TYPE}}</el-form-item>
          </el-col>
          <el-col class="topbar-button">
            <el-form-item
              label="借阅开始时间"
              :label-width="formLabelWidth"
              style="float:left"
            >{{dateFormat(borrowForm.C_ITEM1_DATE)}}</el-form-item>
            <el-form-item
              label="借阅结束时间"
              :label-width="formLabelWidth"
              style="float:left"
            >{{dateFormat(borrowForm.C_ITEM2_DATE)}}</el-form-item>
            <el-form-item :label-width="formLabelWidth" style="float:right">
              <el-button
                v-show="borrowForm.SUB_TYPE=='下载' && borrowForm.STATUS=='已完成' "
                ref="downloadAllFile"
                @click="downloadAllFile()"
              >打包下载</el-button>
            </el-form-item>
          </el-col>
          <el-col>
            <el-table :data="tabledata">
              <el-table-column type="index" label="#" width="50"></el-table-column>
              <el-table-column prop="id" label="id" v-if="1==2" min-width="15%" sortable></el-table-column>
              <el-table-column width="40">
                <template slot-scope="scope">
                  <img
                    v-if="scope.row.TYPE_NAME=='图册'"
                    :src="'./static/img/drawing.gif'"
                    :title="scope.row.TYPE_NAME"
                    border="0"
                  />
                  <img
                    v-else-if="scope.row.TYPE_NAME=='卷盒'"
                    :src="'./static/img/box.gif'"
                    :title="scope.row.TYPE_NAME"
                    border="0"
                  />
                  <img
                    v-else-if="scope.row.FORMAT_NAME==null || scope.row.FORMAT_NAME==''"
                    :src="'./static/img/format/f_undefined_16.gif'"
                    title="无电子文件"
                    border="0"
                  />
                  <img
                    v-else
                    :src="'./static/img/format/f_'+scope.row.FORMAT_NAME+'_16.gif'"
                    :title="scope.row.FORMAT_NAME"
                    border="0"
                  />
                </template>
              </el-table-column>>
              <template v-for="item in gridList">
                <el-table-column :key="item.id" :label="item.label" :prop="item.attrName">
                  <template slot-scope="scope">
                    <template
                      v-if="item.attrName=='C_ARCHIVE_DATE'"
                    >{{dateFormat(scope.row.C_ARCHIVE_DATE)}}</template>
                    <template v-else>{{scope.row[item.attrName]}}</template>
                  </template>
                </el-table-column>
              </template>
              <el-table-column align="right">
                <template slot-scope="scope">
                  <el-button size="mini" @click="viewdoc(scope.row)">查看</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-col>
          <el-col>
            <el-form-item
              label="申请人领导"
              :label-width="formLabelWidth"
              style="float:left"
            >{{borrowForm.C_REVIEWER1}}</el-form-item>
            <el-form-item
              label="形成部门领导"
              :label-width="formLabelWidth"
              style="float:left"
            >{{borrowForm.C_REVIEWER2}}</el-form-item>
            <el-form-item
              label="分管领导"
              :label-width="formLabelWidth"
              style="float:left"
            >{{borrowForm.C_REVIEWER3}}</el-form-item>
          </el-col>
          <el-col>
            <el-form-item
              label="借阅目的"
              :label-width="formLabelWidth"
              style="text-align:left"
            >{{borrowForm.C_COMMENT}}</el-form-item>
          </el-col>
        </div>
      </el-row>
    </el-form>

    <div slot="footer" class="dialog-footer" style="text-align:center" v-show="startFlowNodeJudge">
      <el-button ref="borrowCancel" type="primary" @click="cancel()">{{$t('application.cancel')}}</el-button>
      <el-button ref="borrowStartwf" @click="startWorkflow(borrowForm)">启动流程</el-button>
    </div>
  </div>
</template>


<script type="text/javascript">
import ShowShopingCart from "@/components/form/ShopingCart";
import UserSelectInput from "@/components/controls/UserSelectInput";
export default {
  components: {
    ShowShopingCart: ShowShopingCart,
    UserSelectInput: UserSelectInput,
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
        C_ITEM1_DATE: new Date(),
        C_ITEM2_DATE: new Date(),
        C_REVIEWER1: "",
        C_REVIEWER2: "",
        C_REVIEWER3: "",
        C_COMMENT: "",
        C_CREATION_UNIT: "",
        STATUS: "",
      },
      rules: {
        C_DESC1: [
          {
            required: true,
            message: this.$t("message.pleaseInput") + "用户部门",
            trigger: "blur",
          },
          // { validator: validaePass }
        ],
        // C_CREATION_UNIT: [
        //   {required: true, message: this.$t("message.pleaseInput")+"编制部门", trigger: 'blur'},
        //     // { validator: validaePass2 }
        // ],
        C_ITEM1_DATE: [
          {
            required: true,
            message: this.$t("message.pleaseInput") + "借阅开始时间",
            trigger: "blur",
          },
          // { validator: validaePass2 }
        ],
        C_ITEM2_DATE: [
          {
            required: true,
            message: this.$t("message.pleaseInput") + "借阅结束时间",
            trigger: "blur",
          },
          // { validator: validaePass2 }
        ],
      },
      // formId: "",
      // istask: 0,
      // formEditPermision: 0,
      // processDefinitionId: "",
      // activityName: "",
      vshowShopingCart: false,
      showOrCloseShopingCartLabel: "从借阅单添加",
      expireTimeOption: this.dateCheck(),
      defaultProps: {
        children: "children",
        label: "name",
      },
      deptList: [],
      showDepartMentList: 0,
      currentGroupData: "",
      selectedGroupItemId: "",
      currentRootGroupData: "",
      //startFlowNodeJudge：判读当前为启动借阅流程，还是处于流程中显示界面内容。
      startFlowNodeJudge: false
    };
  },

  props: {
    activityName: {
      type: String,
      default: "",
    },
    formEnableType: {
      type: String,
      default: "",
      required: false,
    },
    docId: {
      type: String,
      default: "",
    },
    processDefinitionId: {
      type: String,
      default: "",
    },
    formEditPermision: {
      type: Number,
      default: 0,
    },
    istask: {
      type: Number,
      default: 0,
    },
    formId: {
      type: String,
      default: "",
    },
    borrowItemList: {
      type: Array,
      default:function(){
				return [];
			}
    },
  },
  created() {
    let _self = this;
    // _self.formId = _self.$route.query.borrowFormId;
    // _self.formId = _self.formId1;
    //   if (typeof _self.$route.query.istask != "undefined") {
    //     _self.formEditPermision = _self.$route.query.formEditPermision;
    //     _self.istask = _self.$route.query.istask;
    //     _self.processDefinitionId = _self.$route.query.processDefinitionId;
    //     _self.activityName = _self.$route.query.activityName;
    // }
    //   if (typeof _self.istask1 != "undefined") {
    //     _self.formEditPermision = _self.formEditPermision1;
    //     _self.istask = _self.istask1;
    //     _self.processDefinitionId = _self.processDefinitionId1;
    //     _self.activityName = _self.activityName1;
    // }

    //购物车触发借阅单传值给borrowItemList，在此认定有传值即为启动流程，其余为流程中
    if(_self.borrowItemList.length>0){
      _self.startFlowNodeJudge = true
    }else{
      //启动借阅流程无需获取审批用户列表
      _self.getApprovalUserList();
    }
    _self.loadGridView();
  },
  mounted() {
    let _self = this;
    _self.bindDepartment();
  },

  methods: {
    getApprovalUserList() {
      let _self = this;
      var m = new Map();
      m.set("processDefinitionId", _self.processDefinitionId);
      m.set("activityName", _self.activityName);
      axios
        .post("/workflow/getApprovalUserList", JSON.stringify(m))
        .then(function (response) {
          _self.approvalUserList = response.data.data;
        });
    },
    dateCheck() {
      let _self = this;
      return {
        disabledDate(date) {
          return date.getTime() + 86400000 < Date.now();
        },
      };
    },

    loadGridView() {
      let _self = this;
      var m = new Map();
      m.set("gridName", _self.gridviewName);
      m.set("lang", _self.currentLanguage);
      axios
        .post("/dc/getGridViewInfo", JSON.stringify(m))
        .then(function (response) {
          _self.gridList = response.data.data;
          _self.loadData();
          // if (typeof _self.formId == "undefined") {
          //   _self.tabledata = _self.$route.query.tabledata;
          //   _self.borrowForm.C_CREATION_UNIT =
          //     _self.$route.query.C_ARCHIVE_UNIT;
          // } else {

          // }
        });
    },
    loadData(val) {
      let _self = this;
      if (_self.formId != "") {
        axios
          .post("/dc/getDocumentMapById", _self.formId)
          .then(function (response) {
            let result = response.data;
            if (result.code == 1) {
              _self.borrowForm = result.data;
            }
          });
      }
     
      if(_self.borrowItemList.length > 0){
        _self.tabledata = _self.borrowItemList;
      }
      else{
        axios
          .post("/dc/getFormRelateDocument", _self.formId)
          .then(function (response) {
            let result = response.data;
            if (result.code == 1) {
              _self.tabledata = result.data;
            }
          });
      }
      if(val != undefined){
        _self.tabledata = val;
      } 
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
      let formMap = new Map();
      _self.$refs.borrowForm.validate((valid) => {
        if (valid) {
          formMap = _self.validateBorrowForm(_self);
          if (formMap == null) {
            return;
          }
          _self.loading = true;
          axios
            .post("/dc/saveBorrowForm", JSON.stringify(formMap))
            .then(function (response) {
              _self.formId = response.data.data;
              console.log(response);
              let m = new Map();
              m.set("formId", _self.formId);
              // m.set(
              //   "fileTopestSecurityLevel",
              //   formMap.get("fileTopestSecurityLevel")
              // );
              // m.set("drawingNumber", formMap.get("drawingNumber"));
              // m.set("fileNumber", formMap.get("fileNumber"));
              m.set("processName","借阅流程");
              m.set("processInstanceKey","process_borrow")
              axios
                .post("/workflow/startWorkflow", JSON.stringify(m))
                .then(function (response) {
                  console.log(response);
                  _self.$message({
                    showClose: true,
                    message: "流程发起成功!",
                    duration: 2000,
                    type: "success",
                  });

                  _self.loading = false;
                  _self.cancel();
                })
                .catch(function (error) {
                  _self.$message({
                    showClose: true,
                    message: "流程发起失败!",
                    duration: 2000,
                    type: "warning",
                  });
                  console.log(error);
                  _self.loading = false;
                });
            })
            .catch(function (error) {
              console.log(error);
              _self.loading = false;
            });
        }
      });
    },
    validateBorrowForm(gougou) {
      let _self = this;
      let drawingNumber = 0;
      let fileNumber = 0;
      let beyondLeaderPermision = false;
      let isValidedForm = false;
      let isStoreStatus = "在库";
      let m = new Map();

      let documentIds = "";
      let fileTopestSecurityLevel = "内部公开";
      for (let index = 0; index < _self.tabledata.length; index++) {
        if (
          typeof _self.tabledata[index].C_STORE_STATUS == "undefined" ||
          (typeof _self.tabledata[index].C_STORE_STATUS != "undefined" &&
            _self.tabledata[index].C_STORE_STATUS != isStoreStatus)
        ) {
          isStoreStatus = "不在库";
        }
        _self.borrowForm.C_CREATION_UNIT =
          _self.tabledata[index].C_ARCHIVE_UNIT;
        let element = _self.tabledata[index];
        fileTopestSecurityLevel =
          fileTopestSecurityLevel + element.C_SECURITY_LEVEL;
        if (index == 0) {
          documentIds = documentIds + element.ID;
        } else {
          documentIds = documentIds + "," + element.ID;
        }
        if (_self.tabledata.TYPE_NAME == "图册") {
          drawingNumber++;
        } else {
          fileNumber++;
        }
      }

      if (fileTopestSecurityLevel.indexOf("核心商密") > 0) {
        fileTopestSecurityLevel = "核心商密";
      } else if (fileTopestSecurityLevel.indexOf("普通商密") > 0) {
        fileTopestSecurityLevel = "普通商密";
      } else if (fileTopestSecurityLevel.indexOf("受限") > 0) {
        fileTopestSecurityLevel = "受限";
      } else {
        fileTopestSecurityLevel = "内部公开";
      }

      switch (fileTopestSecurityLevel) {
        case "普通商密":
        case "核心商密":
          // 20个图册或100个文件以上
          if (drawingNumber > 20 || fileNumber > 100) {
            beyondLeaderPermision = true;
          }
          break;

        case "受限":
          // 30个图册或150个文件以上
          if (drawingNumber > 30 || fileNumber > 150) {
            beyondLeaderPermision = true;
          }
          break;

        default:
          beyondLeaderPermision = false;
          break;
      }

      if (
        !(
          _self.borrowForm.SUB_TYPE == "纸质借阅" &&
          fileTopestSecurityLevel == "内部公开"
        )
      ) {
        let alertStr = "";
        if (_self.borrowForm.C_REVIEWER1 == "") alertStr = "'申请人领导' ";
        if (_self.borrowForm.C_CREATION_UNIT != _self.borrowForm.C_DESC1) {
          if (_self.borrowForm.C_REVIEWER2 == "")
            alertStr = alertStr + "'形成部门领导' ";
        }
        if (
          beyondLeaderPermision ||
          _self.borrowForm.SUB_TYPE == "纸质借阅" ||
          _self.borrowForm.SUB_TYPE == "下载"
        ) {
          if (_self.borrowForm.C_REVIEWER3 == "")
            alertStr = alertStr + "'分管领导' ";
        }

        if (
          new Date(_self.borrowForm.C_ITEM1_DATE).getTime() >
          new Date(_self.borrowForm.C_ITEM2_DATE).getTime()
        ) {
          gougou.$message({
            showClose: true,
            message: "结束日期不能小于开始日期！",
            duration: 5000,
            type: "error",
          });
          return;
        }

        if (alertStr == "") {
          isValidedForm = true;
        } else {
          gougou.$message({
            showClose: true,
            message: "根据您借阅档案的信息：" + alertStr + "  必填",
            duration: 5000,
            type: "warning",
          });
          return;
        }
      }

      // if (isStoreStatus != "在库" && _self.borrowForm.SUB_TYPE == "纸质借阅") {
      //   gougou.$message({
      //     showClose: true,
      //     message: "所借阅文件包含不在库文件，不能发起借阅流程",
      //     duration: 5000,
      //     type: "warning",
      //   });
      //   return;
      // }

      m.set("fileTopestSecurityLevel", fileTopestSecurityLevel);
      m.set("formId", _self.formId);
      m.set("beyondLeaderPermision", beyondLeaderPermision);
      m.set("drawingNumber", drawingNumber);
      m.set("fileNumber", fileNumber);
      m.set("formData", _self.borrowForm);
      m.set("tabledata", _self.tabledata);
      m.set("documentIds", documentIds);
      return m;
    },
    getFormdataMap() {
      let _self = this;
      let m = new Map();

      let documentIds = "";
      let fileTopestSecurityLevel = "内部公开";
      let drawingNumber = _self.tabledata.length;
      for (let index = 0; index < _self.tabledata.length; index++) {
        let element = _self.tabledata[index];
        fileTopestSecurityLevel =
          fileTopestSecurityLevel + element.C_SECURITY_LEVEL;
        if (index == 0) {
          // _self.borrowForm.C_CREATION_UNIT =
          //   _self.tabledata[index].C_ARCHIVE_UNIT;
          documentIds = documentIds + element.ID;
        } else {
          documentIds = documentIds + "," + element.ID;
        }
      }
      if (fileTopestSecurityLevel.indexOf("核心商密") > 0) {
        fileTopestSecurityLevel = "核心商密";
      } else if (fileTopestSecurityLevel.indexOf("普通商密") > 0) {
        fileTopestSecurityLevel = "普通商密";
      } else if (fileTopestSecurityLevel.indexOf("受限") > 0) {
        fileTopestSecurityLevel = "受限";
      } else {
        fileTopestSecurityLevel = "内部公开";
      }
      m.set("fileTopestSecurityLevel", fileTopestSecurityLevel);
      m.set("formData", _self.borrowForm);
      m.set("tabledata", documentIds);
      m.set("documentIds", _self.tabledata);
      m.set("formId", _self.formId);
      return m;
    },
    saveCurrentForm(m) {
      let _self = this;
      m = _self.getFormdataMap();
      let judgeMap = new Map();
      // judgeMap = m.get("formData")
      // console.log(judgeMap.get(""))
      axios
        .post("/dc/saveBorrowForm", m)
        .then(function (response) {
          console.log(response.data.data);
        })
        .catch(function (error) {
          console.log(error);
          _self.loading = false;
        });
    },

    addFromShopingCart() {
      let _self = this;
      var arg = [];
      axios
        .post("/dc/openShopingCart", JSON.stringify(arg))
        .then(function (response) {
          if (response.data.code) {
            // _self.shopingCartDialogVisible = true;
            // setTimeout(()=>{
            // _self.$refs.ShopingCart.dataList = response.data.data;
            // _self.$refs.ShopingCart.dataList = response.data.data;
            // _self.$router.push({
            //   path:'/ShopingCart',
            //    query: { tabledata: response.data.data }
            // });
            if (
              _self.$refs.ShowShopingCart &&
              _self.$refs.ShowShopingCart.componentName == "shopingCart"
            ) {
              _self.$refs.ShowShopingCart.openShopingCart();
            }
            // },10);
          } else {
            _self.$message({
              showClose: true,
              message: "打开失败!",
              duration: 2000,
              type: "warning",
            });
          }
        });
    },
    showOrCloseShopingCart() {
      let _self = this;
      if (_self.vshowShopingCart == true) {
        _self.vshowShopingCart = false;
        _self.showOrCloseShopingCartLabel = "从借阅单添加";
      } else {
        _self.vshowShopingCart = true;
        _self.showOrCloseShopingCartLabel = "收起借阅单";
        if (
          _self.$refs.ShowShopingCart &&
          _self.$refs.ShowShopingCart.componentName == "shopingCart"
        ) {
          _self.$refs.ShowShopingCart.openShopingCart();
        }
      }
    },
    addToFormFromShopingCart() {
      let _self = this;
      let allTableData = [];
      allTableData = allTableData
        .concat(_self.tabledata)
        .concat(_self.$refs.ShowShopingCart.selectedItemList);
      // var C_ARCHIVE_UNIT = "";
      if (allTableData.length > 0) {
        // for (var i = 0; i < allTableData.length; i++) {
        //   if (i == 0) {
        //     if (typeof allTableData[i].C_ARCHIVE_UNIT == "undefined") {
        //       _self.$message({
        //         showClose: true,
        //         message: _self.$t('message.EmptyPlaceOnFile'),
        //         duration: 5000,
        //         type: "warning"
        //       });
        //       return;
        //     }
        //     C_ARCHIVE_UNIT = allTableData[i].C_ARCHIVE_UNIT;
        //   } else {
        //     if (C_ARCHIVE_UNIT != allTableData[i].C_ARCHIVE_UNIT) {
        //       _self.$message({
        //         showClose: true,
        //         message: _self.$t('message.SameFiledUnit'),
        //         duration: 5000,
        //         type: "warning"
        //       });
        //       return;
        //     }
        //   }
        // }
      } else {
        _self.$message({
          showClose: true,
          message:_self.$t('message.PleaseSelectOneBorrow'),
          duration: 5000,
          type: "warning",
        });
        return;
      }

      _self.$refs.ShowShopingCart.addToFormFromShopingCart(_self.tabledata);
      setTimeout(() => {
        axios
          .post("/dc/getFormRelateDocument", _self.formId)
          .then(function (response) {
            let result = response.data;
            if (result.code == 1) {
              _self.tabledata = result.data;
              _self.$refs.ShowShopingCart.loadGridView();
              if (_self.borrowForm.C_CREATION_UNIT != C_ARCHIVE_UNIT) {
                _self.borrowForm.C_CREATION_UNIT = C_ARCHIVE_UNIT;
                _self.borrowForm.C_REVIEWER2 = "";
                _self.saveCurrentForm();
              }
            }
          });
      }, 2500);
    },

    removeItemFromForm(varArg) {
      let _self = this;
      if (typeof _self.formId == "undefined" || _self.formId == "") {
        let a = [];
        a = _self.tabledata.filter(function (item) {
          return item.ID != varArg.ID;
        });
        _self.tabledata = a;
      } else {
        let _self = this;
        let m = new Map();
        m.set("relateId", varArg.RELATE_ID);
        axios.post("/dc/removeItemFromForm", m).then(function (response) {
          let result = response.data;
          if (result.code == 1) {
            _self.$message({
              showClose: true,
              message: "操作成功!",
              duration: 2000,
              type: "success",
            });
          }
        });
        setTimeout(() => {
          axios
            .post("/dc/getFormRelateDocument", _self.formId)
            .then(function (response) {
              let result = response.data;
              _self.$refs.ShowShopingCart.loadGridView();
              if (result.code == 1) {
                _self.tabledata = result.data;
              }
            });
        }, 1000);
      }
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
          id: condition,
          //token: sessionStorage.getItem('access-token')
        },
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
        .then(function (response) {
          _self.deptList = response.data.data;
          _self.currentRootGroupData = response.data.data;
          //_self.handleNodeClick(_self.deptList[0]);
          //console.log(JSON.stringify(_self.deptList));
        })
        .catch(function (error) {
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
          .then(function (response) {
            // _self.$message("获取子节点成功!");
            indata.children = response.data.data;
            indata.extended = true;
            //console.log(JSON.stringify(indata));
          })
          .catch(function (error) {
            console.log(error);
          });
      }
    },
  },
};
</script>