<template>
  <div>
    
        <el-form label-position="right" label-width="100px">
          <el-row>
            <el-collapse v-model="activeNames">
              <el-collapse-item
                v-for="(citem,cindex) in dataList"
                :title="citem.label"
                :name="citem.label"
                :id="citem.label"
                :key="cindex"
              >
                <template v-for="(item,itemIndex) in citem.ecmFormItems">
                  <el-col
                    :span="showCellValue(item)"
                    :xs="24"
                    :sm="12"
                    :md="showCellValue(item)"
                    :lg="showCellValue(item)"
                    :xl="showCellValue(item)"
                    v-bind:key="itemIndex"
                    style="text-align:left;"
                  >
                    <el-form-item
                      :hidden="item.isHide"
                      :label="item.label"
                      :rules="[{required:validateValue(item),message:$t('application.requiredInput'),trigger:'blur'}]"
                      :label-width="formLabelWidth"
                    >
                      <el-input
                        v-if="item.controlType=='TextBox' && !item.isRepeat"
                        type="text"
                        :name="item.attrName"
                        v-model="item.defaultValue"
                        :disabled="item.readOnly"
                      ></el-input>
                      <MultiInput
                        v-if="item.controlType=='TextBox' && item.isRepeat"
                        v-model="item.defaultValue"
                      ></MultiInput>
                      <el-input
                        v-if="item.controlType=='TextArea'"
                        type="textarea"
                        :name="item.attrName"
                        v-model="item.defaultValue"
                        :disabled="item.readOnly"
                      ></el-input>
                      <el-input
                        v-else-if="item.controlType=='Integer'"
                        :min="0"
                        type="number"
                        :name="item.attrName"
                        v-model="item.defaultValue"
                        :disabled="item.readOnly"
                      ></el-input>
                    <el-input v-else-if="item.controlType=='InputSelect'" type="text" :name="item.attrName" v-model="item.defaultValue" @input="handleInputChange(item)" :disabled="item.readOnly"></el-input>
                    <!--
                    <el-autocomplete v-else-if="item.controlType=='InputSelect'" class="inline-input" v-model="item.defaultValue" :fetch-suggestions="queryInputSearch(queryString, cb,item)" @select="handleInputSelect(vitem,item)" ></el-autocomplete>
                    --> 
                      <el-checkbox
                        v-else-if="item.controlType=='Boolean'"
                        :name="item.attrName"
                        v-model="item.defaultValue"
                        :disabled="item.readOnly"
                      ></el-checkbox>
                      <template v-else-if="item.controlType=='Date'">
                        <span v-if="item.readOnly">{{datetimeFormat(item.defaultValue)}}</span>
                        <el-date-picker
                          v-else
                          :name="item.attrName"
                          v-model="item.defaultValue"
                          type="date"
                          :placeholder="$t('application.selectDate')"
                          style="display:block;"
                          format="yyyy-MM-dd"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          :readonly="item.readOnly"
                        ></el-date-picker>
                      </template>
                      <el-select
                        filterable
                        :name="item.attrName"
                        v-else-if="item.controlType=='Select' || item.controlType=='ValueSelect'||item.controlType=='Department' || item.controlType=='SQLSelect'"
                        
                        v-model="item.defaultValue"
                        :placeholder="$t('application.pleaseSelect')"
                        :disabled="item.readOnly"
                        :multiple="item.isRepeat"
                        style="display:block;"
                        @change="((val)=>{onSelectChange(val, item)})"
                      >
                        <div v-for="(name,nameIndex) in item.validValues" :key="nameIndex+'N'">
                          <el-option :label="name" :value="name" :key="nameIndex"></el-option>
                        </div>
                      </el-select>
                      <UserSelectInput
                        v-else-if="item.controlType=='UserSelect'"
                        v-model="item.defaultValue"
                        v-bind:inputValue="item.defaultValue"
                        v-bind:roleName="item.queryName"
                        v-bind:isRepeat="item.isRepeat"
                      ></UserSelectInput>
                      <RoleSelectInput
                        v-else-if="item.controlType=='RoleSelect'"
                        v-model="item.defaultValue"
                        v-bind:inputValue="item.defaultValue"
                        v-bind:roleName="item.queryName"
                        v-bind:isRepeat="item.isRepeat"
                      ></RoleSelectInput>
                      <!-- <DepartmentSelector
                        v-else-if="item.controlType=='Department'"
                        ref="DepartmentSelector"
                        v-on:departmentSelected="departmentSelected"
                        width="360px"
                      ></DepartmentSelector> -->
                      <!-- <DeptSelector
                        v-else-if="item.controlType=='Department'"
                        v-model="item.defaultValue"
                        v-bind:inputValue="item.defaultValue"
                        v-bind:roleName="item.queryName"
                        v-bind:isRepeat="item.isRepeat"
                      ></DeptSelector> -->
                    </el-form-item>
                  </el-col>
                </template>
              </el-collapse-item>
            </el-collapse>
            <el-row v-if="showTypeName">
              <el-form-item style="float:left" :label="$t('application.type')">{{myTypeName}}</el-form-item>
            </el-row>
          </el-row>
          <el-row>
            <div
              v-if="(itemId  == undefined || itemId == '0' || itemId == '') && showUploadFile "
              style="float:left;margin-left:120px;"
            >
              <el-upload
                :limit="1"
                :file-list="fileList"
                action
                :on-change="handleChange"
                :auto-upload="false"
                :multiple="false"
              >
                <el-button
                  slot="trigger"
                  size="small"
                  type="primary" plain 
                >{{$t('application.selectFile')}}</el-button>
              </el-upload>
            </div>
          </el-row>
        </el-form>
      
  </div>
</template>

<script type="text/javascript">
import UserSelectInput from "@/components/controls/UserSelectInput";
import RoleSelectInput from "@/components/controls/RoleSelectInput";
import AddCondition from "@/views/record/AddCondition";
import MultiInput from "@/components/ecm-multi-input";
import FileAcl from "@/components/controls/FileAcl.vue"
export default {
  name: "ShowProperty",
  components: {
    UserSelectInput: UserSelectInput,
    RoleSelectInput: RoleSelectInput,
    AddCondition: AddCondition,
    MultiInput: MultiInput,
    FileAcl:FileAcl,
    // DepartmentSelector:DepartmentSelector
  },
  data() {
    return {
      formLabelWidth: "150px",
      currentLanguage: "zh-cn",
      permit: 5,
      activeNames: [],
      dataList: {
        rowdata: {
          parentId: "",
          label: "",
          attrName: "",
          searchable: "1",
          required: "0",
          readOnly: "0",
          controlType: "TextBox",
          widthType: "2",
          validValues: [],
          orderIndex: 1,
          defaultValue: "",
          validatePolicy: ""
        }
      },
      file: "",
      fileList: [],
      myItemId: this.itemId,
      myTypeName: this.typeName,
      myFolderId: this.folderId,

      parentDocId: "",
      clientPermission: 1,
      mainObject: [],
      mainSubRelation: [],
      aclName:"",
      dataMap:new Map(),
      selectDeptId:""
    };
  },
  watch: {
    typeName() {
      this.myTypeName = this.typeName;
    }
  },
  mounted() {
    this.currentLanguage = localStorage.getItem("localeLanguage") || "zh-cn";
    if (this.mainObject == null || this.mainObject.length == 0) {
      this.loadFormInfo();
    }
    this.clientPermission = sessionStorage.getItem("access-clientPermission");
    this.systemPermission = Number(sessionStorage.getItem("access-systemPermission"));
  },
  props: {
    itemId: { type: String, default: "" },
    typeName: { type: String, default: "" },
    formName: { type: String, default: "" },
    folderId: { type: String, default: "" },
    folderPath: { type: String, default: "" },
    showUploadFile: { type: Boolean, default: true },
    extendAllTab: { type: Boolean, default: true },
    showTypeName: { type: Boolean, default: false },
    userPermission:0,
    systemPermission:1
  },
  methods: {
    handleInputChange(item){
      console.log(item);
      var c;
      for(c in this.dataList){
        let dataRows = this.dataList[c].ecmFormItems;
        var i;
        for (i in dataRows) {
          let row = dataRows[i];
          if(row.dependName == item.attrName){
            //row.defaultValue = "";
            this.loadChildValue(row, item.defaultValue);
          }
        }
      }
    },
    
    loadChildValue(item, val){
      let _self = this;
      var m = new Map();
      m.set("queryName", item.queryName);
      m.set("dependValue", val);
      _self.loading = true;
      axios.post("/dc/getSelectList",JSON.stringify(m))
        .then(function(response) {
          if(response.data.code == 1){
            if(response.data.data && response.data.data.length>0){
              item.defaultValue = response.data.data[0];
            }
          }
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    queryInputSearch(queryString, cb,item)
    {
      console.log(queryString);
      console.log(cb);
      console.log(item);
    },
    handleInputSelect(vitem,item){
      console.log(vitem);
      console.log(item);
    },
    departmentSelected(indata) {
      this.selectDeptId = indata;
      //console.log("this.selectDeptId:"+this.selectDeptId);
    },
    setMainObject(obj) {
      this.mainObject = obj;
    },
    setMainSubRelation(obj) {
      this.mainSubRelation = obj;
    },
    validateValueByPolicy(policy) {
      if (policy != null && policy != "") {
        var p = policy.split(";");
        if (p[0] != "") {
          var p1 = p[0].split(":");
          if (this.getValueByAttr(p1[0]) == p1[1]) {
            return true;
          }
        } else {
          return true;
        }
      }
      return false;
    },
    validateValue(itemData) {
      if (itemData.required) {
        if (itemData.validatePolicy != null && itemData.validatePolicy != "") {
          var p = itemData.validatePolicy.split(";");
          if (p[0] != "") {
            var p1 = p[0].split(":");
            if (this.getValueByAttr(p1[0]) == p1[1]) {
              return true;
            }
          } else {
            return true;
          }
        } else {
          return true;
        }
      }
      return false;
    },
    getValueByAttr(attrName) {
      var c;
      for (c in this.dataList) {
        let dataRows = this.dataList[c].ecmFormItems;
        var i;
        for (i in dataRows) {
          if (dataRows[i].attrName == attrName) {
            return dataRows[i].defaultValue;
          }
        }
      }
      return "";
    },
    showCellValue(item) {
      //console.log(item);
      var v = 24 / parseInt(item.widthType);
      //console.log(v);
      return v;
    },
    handleChange(file, fileList) {
      this.file = file;
      //console.log(file);
      // console.log(fileList);
    },
    onSelectChange(val, item) {
      let _self=this;
      if (item.enableChange) {
        var c;
        for(c in this.dataList){
          let dataRows = this.dataList[c].ecmFormItems;
          var i;
          for (i in dataRows) {
            let row = dataRows[i];
            if(row.dependName == item.attrName){
              row.defaultValue = "";
              // this.loadChildList(row, item.defaultValue);
              if(row.validatePolicy&&row.validatePolicy.indexOf('function(')>=0){
                var f=eval("(function(){ return "+
                row.validatePolicy
                +"})()");
                f(_self,row,item.defaultValue);
              }else{
                this.loadChildList(row, item.defaultValue);
              }
              
            }
              
            }
          }
          
        }
      
    },
    //根据项目名称找项目编码
    loadChildVal2(_self,item, val,param) {
      
      // var m = new Map();
      // m.set("column", "ITEM_SCOPE");
      // m.set("condition", " NAME='"+val+"' ");
      _self.loading = true;
      axios
        .post("/dc/getDataByRefColumn", JSON.stringify(param))
        .then(function(response) {
          if (response.data.code == 1) {
            if(response.data.data.length>=1){
              item.defaultValue=response.data.data[0][param.get('column')];
            }
          }
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    //根据项目名称找知悉范围
    loadChildVal(_self,item, val) {
      
      var m = new Map();
      m.set("column", "ITEM_SCOPE");
      m.set("condition", " NAME='"+val+"' ");
      _self.loading = true;
      axios
        .post("/dc/getDataByRefColumn", JSON.stringify(m))
        .then(function(response) {
          if (response.data.code == 1) {
            if(response.data.data.length>=1){
              item.defaultValue=response.data.data[0].ITEM_SCOPE;
            }
          }
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    //根据项目编码找项目名称
    loadVal(_self,item, val){
      var m = new Map();
      m.set("column", "NAME");
      m.set("condition", " CODING='"+val+"' and TYPE_NAME='项目' ");
      _self.loading = true;
      axios
        .post("/dc/getDataByRefColumn", JSON.stringify(m))
        .then(function(response) {
          if (response.data.code == 1) {
            if(response.data.data.length>=1){
              item.defaultValue=response.data.data[0].NAME;
            }
          }
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        }); 
    },
    loadChildList(item, val) {
      let _self = this;
      var m = new Map();
      m.set("queryName", item.queryName);
      m.set("dependValue", val);
      _self.loading = true;
      axios
        .post("/dc/getSelectList", JSON.stringify(m))
        .then(function(response) {
          if (response.data.code == 1) {
            item.validValues = response.data.data;
          }
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    // 加载表单
    loadFormInfo() {
      let _self = this;
      _self.loading = true;
      if (!_self.myItemId && !_self.myTypeName) {
        return;
      }
      if (_self.myItemId && _self.myItemId != "") {
        _self.myTypeName = "";
      }

      var m = new Map();
      m.set("itemInfo", _self.myItemId + _self.myTypeName); //ID 或类型
      m.set("formName", _self.formName);
      m.set("lang", _self.getLang());
      //console.log(_self.itemId+","+_self.myItemId+","+_self.myTypeName+","+_self.folderId);
      axios
        .post("/dc/getFormClassifications", JSON.stringify(m))
        .then(function(response) {
          _self.bindData(response.data.data);
          
          _self.activeNames = [];
          if (_self.extendAllTab && response.data.data) {
            for (var i = 0; i < response.data.data.length; i++) {
              _self.activeNames.push(response.data.data[i].label);
            }
          } else {
            if (response.data.data[0]) {
              _self.activeNames.push(response.data.data[0].label);
            }
          }
          _self.myTypeName = response.data.typeName;
          _self.file = [];
          _self.fileList = [];
          //console.log(JSON.stringify(response.data.data));
          
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    // 数据必填校验
    validFormValue() {
      let _self = this;
      var ret = true;
      var msg = "";
      var c;
      for (c in _self.dataList) {
        let dataRows = _self.dataList[c].ecmFormItems;
        var i;
        for (i in dataRows) {
          // if(dataRows[i].required){
          //    console.log(dataRows[i].label+":"+dataRows[i].defaultValue+":"+typeof(dataRows[i].defaultValue));
          // }
          if (
            _self.validateValue(dataRows[i]) &&
            (typeof dataRows[i].defaultValue === "undefined" ||
              dataRows[i].defaultValue == null ||
              dataRows[i].defaultValue == "")
          ) {
            if (
              typeof dataRows[i].defaultValue === "number" &&
              dataRows[i].defaultValue > -1
            ) {
            } else {
              msg += "[" + dataRows[i].label + "] ";
              ret = false;
            }
            // console.log(typeof(dataRows[i].defaultValue)==='undefined');
            // console.log(dataRows[i].defaultValue==null);
            // console.log(dataRows[i].defaultValue=="");
            // console.log(dataRows[i]);
            // console.log(dataRows[i].label+":"+dataRows[i].defaultValue+":"+typeof(dataRows[i].defaultValue));
          } else if (
            dataRows[i].validatePolicy != null &&
            dataRows[i].validatePolicy != ""
          ) {
            if (
              (_self.myItemId == null || _self.myItemId == "") &&
              dataRows[i].validatePolicy.indexOf(";") > -1
            ) {
              let p = dataRows[i].validatePolicy.split(";")[1];
              if (
                _self.validateValueByPolicy(
                  dataRows[i].validatePolicy.split(";")[0]
                )
              ) {
                let fun = p.split(":");
                if (p.indexOf("now") > -1) {
                  let dt = new Date();
                  if (p.indexOf(">=") > -1) {
                    if (
                      _self.validateInputDate(
                        dt,
                        dataRows[i].defaultValue,
                        fun[2]
                      ) < 0
                    ) {
                      msg +=
                        "[" +
                        dataRows[i].label +
                        "] 必需大于等于当前日期+" +
                        fun[2] +
                        "天";
                      ret = false;
                    }
                  } else if (p.indexOf("<=") > -1) {
                    if (
                      _self.validateInputDate(
                        dt,
                        dataRows[i].defaultValue,
                        fun[2]
                      ) > 0
                    ) {
                      msg +=
                        "[" +
                        dataRows[i].label +
                        "] 必需小于等于当前日期+" +
                        fun[2] +
                        "天";
                      ret = false;
                    }
                  } else if (p.indexOf(">") > -1) {
                    if (
                      _self.validateInputDate(
                        dt,
                        dataRows[i].defaultValue,
                        fun[2]
                      ) >= 0
                    ) {
                      msg +=
                        "[" +
                        dataRows[i].label +
                        "] 必需大于当前日期+" +
                        fun[2] +
                        "天";
                      ret = false;
                    }
                  } else if (p.indexOf("<") > -1) {
                    if (
                      _self.validateInputDate(
                        dt,
                        dataRows[i].defaultValue,
                        fun[2]
                      ) >= 0
                    ) {
                      msg +=
                        "[" +
                        dataRows[i].label +
                        "] 必需小于当前日期+" +
                        fun[2] +
                        "天";
                      ret = false;
                    }
                  }
                }
              }
            }
          }
        }
      }
      if (!ret) {
          _self.$message({
              showClose: true,
              message: msg + _self.$t("application.cannotEmpty") + "！",
              duration: 2000,
              type: 'warning'
          });
      }
      return ret;
    },
    // 日期比较，输入日期-当前日期 -天数
    validateInputDate(fromDate, dateTime, addDay) {
      let now = fromDate;
      now = now.setDate(now.getDate() + parseInt(addDay));
      var today = new Date(now).Format("yyyyMMdd");
      var dt = new Date(dateTime).Format("yyyyMMdd");
      return parseInt(dt) - parseInt(today);
    },
    getFormData() {
      let _self = this;
      var m = new Map();
      var c;
      for (c in _self.dataList) {
        let dataRows = _self.dataList[c].ecmFormItems;
        var i;
        for (i in dataRows) {
          if (dataRows[i].attrName && dataRows[i].attrName != "") {
            if (
              dataRows[i].attrName != "FOLDER_ID" &&
              dataRows[i].attrName != "ID"
            ) {
              var val = dataRows[i].defaultValue;
              // if(val && dataRows[i].isRepeat){
              //   var temp = "";
              // // console.log(val);
              //   for(let j=0,len=val.length;j<len;j++){
              //     temp = temp + val[j]+";";
              //     //console.log(temp);
              //   }
              //   temp = temp.substring(0,temp.length-1);
              //   val = temp;
              //   //console.log(val);
              // }
              m.set(dataRows[i].attrName, val);
            }
          }
        }
      }
      if (_self.myItemId != null && _self.myItemId != "") {
        m.set("ID", _self.myItemId);
      } else {
        if (_self.myFolderId != "" && _self.myFolderId != null) {
          m.set("FOLDER_ID", _self.myFolderId);
        }
      }
      if (_self.myTypeName != "" && _self.myTypeName != null) {
        m.set("TYPE_NAME", _self.myTypeName);
      }
      // let formdata = new FormData();
      // formdata.append("metaData", JSON.stringify(m));

      // if (_self.file != "") {
      //   //console.log(_self.file);
      //   formdata.append("uploadFile", _self.file.raw);
      // }
      let formdata=new Map();
      formdata.set("metaData", JSON.stringify(m));
      return formdata;
    },

    saveItem() {
      let _self = this;
      if (!_self.validFormValue()) {
        return;
      }
      var m = new Map();
      var c;
      for (c in _self.dataList) {
        let dataRows = _self.dataList[c].ecmFormItems;
        var i;
        for (i in dataRows) {
          if (dataRows[i].attrName && dataRows[i].attrName != "") {
            if (
              dataRows[i].attrName != "FOLDER_ID" &&
              dataRows[i].attrName != "ID"
            ) {
              var val = dataRows[i].defaultValue;
              if (val && dataRows[i].isRepeat) {
                var temp = "";
                // console.log(val);
                for (let j = 0, len = val.length; j < len; j++) {
                  temp = temp + val[j] + ";";
                  //console.log(temp);
                }
                temp = temp.substring(0, temp.length - 1);
                val = temp;
                //console.log(val);
              }
              m.set(dataRows[i].attrName, val);
            }
          }
        }
      }
      if (_self.myItemId != null && _self.myItemId != "") {
        m.set("ID", _self.myItemId);
      } else {
        if (_self.myFolderId != null && _self.myFolderId != "") {
          m.set("FOLDER_ID", _self.myFolderId);
        }
      }
      if (_self.myTypeName != null && _self.myTypeName != "") {
        m.set("TYPE_NAME", _self.myTypeName);
      }
      let formdata = new FormData();
      formdata.append("metaData", JSON.stringify(m));

      if (_self.file != "") {
        //console.log(_self.file);
        formdata.append("uploadFile", _self.file.raw);
      }
      console.log(JSON.stringify(m));
      if (_self.myItemId == "") {
        axios
          .post("/dc/newDocument", formdata, {
            "Content-Type": "multipart/form-data"
          })
          .then(function(response) {
            let code = response.data.code;
            //console.log(JSON.stringify(response));
            if (code == 1) {
              _self.$emit("onSaved", "new");
              _self.$emit("onSaveSuccess", m);
            } else {
             _self.$message({
                showClose: true,
                message: _self.$t('message.newFailured'),
                duration: 2000,
                type: "error",
              });
            }
          })
          .catch(function(error) {
           _self.$message({
                showClose: true,
                message: _self.$t('message.newFailured'),
                duration: 2000,
                type: "error",
              });
            console.log(error);
          });
      } else {
        if (_self.permit < 5) {
             _self.$message({
                showClose: true,
                message: _self.$t('message.hasnoPermssion'),
                duration: 2000,
                type: "warning",
              });
          return;
        }
        axios
          .post("/dc/saveDocument", JSON.stringify(m))
          .then(function(response) {
            let code = response.data.code;
            //console.log(JSON.stringify(response));
            if (code == 1) {
              _self.$emit("onSaved", "update");
              _self.$emit("onSaveSuccess", m);
            } else {
              _self.$message({
                showClose: true,
                message: _self.$t('message.saveFailured'),
                duration: 2000,
                type: "error",
              });
            }
          })
          .catch(function(error) {
              _self.$message({
                showClose: true,
                message: _self.$t('message.saveFailured'),
                duration: 2000,
                type: "error",
              });
            console.log(error);
          });
      }
    },
    bindData(indata) {
      let _self = this;
      _self.dataMap=new Map();
      if (_self.myItemId == "") {
        _self.aclName="acl_all_write";
        
        _self.userPermission=_self.clientPermission
        var c;
        for (c in indata) {
          let frmItems = indata[c].ecmFormItems;
          //console.log(JSON.stringify(frmItems));
          var i;
          for (i in frmItems) {
            let val = frmItems[i].defaultValue;

            if (
              _self.mainSubRelation &&
              _self.mainSubRelation.size > 0 &&
              _self.mainObject &&
              (val == null || val == undefined || val == "")
            ) {
              val =
                _self.mainObject[
                  _self.mainSubRelation.get(frmItems[i].attrName)
                ];
              // frmItems[i].defaultValue = val;
            }
            if (frmItems[i].isRepeat) {
              if (
                frmItems[i].controlType == "TextBox" ||
                frmItems[i].controlType == "Select" ||
                frmItems[i].controlType == "SQLSelect" ||
                frmItems[i].controlType == "ValueSelect"
              ) {
                if (val == null) {
                  val = [];
                } else if(val.indexOf('[')==0
                &&val.indexOf(']')==val.length-1){
                  val=JSON.parse(val);
                } else {
                  val =val.split(
                    ";"
                  );
                }
              }
            }
            frmItems[i].defaultValue = val;
            _self.dataMap.set(frmItems[i].attrName,frmItems[i]);
            // if("TYPE_NAME"==frmItems[i].attrName){
            //   _self.typeName=frmItems[i].attrName；
            // }
            //console.log(JSON.stringify(frmItems[i].attrName)+":"+frmItems[i].defaultValue);
          }
        }

        _self.dataList = indata;
        _self.mainObject = null;
        _self.mainSubRelation = null;
      } else {
        axios
          .post("/dc/getDocument", _self.myItemId)
          .then(function(response) {
            let tab = response.data.data;
            _self.permit = response.data.permit;
            if(_self.systemPermission>_self.permit){
              _self.userPermission=_self.systemPermission
            }else{
              _self.userPermission=_self.permit
            }
            _self.aclName=tab.ACL_NAME;
            
            var c;
            for (c in indata) {
              let frmItems = indata[c].ecmFormItems;
              //console.log(JSON.stringify(frmItems));
              var i;
              for (i in frmItems) {
                let val = tab[frmItems[i].attrName];
                if (val && frmItems[i].isRepeat) {
                  if (
                    frmItems[i].controlType == "TextBox" ||
                    frmItems[i].controlType == "Select" ||
                    frmItems[i].controlType == "SQLSelect" ||
                    frmItems[i].controlType == "ValueSelect"
                  ) {
                    if (val == null) {
                      // frmItems[i].defaultValue =[]
                      val = [];
                    }else if(val.indexOf('[')==0
                    &&val.indexOf(']')==val.length-1){
                      val=JSON.parse(val);
                    } else {
                      // frmItems[i].defaultValue =  frmItems[i].defaultValue.split(";");
                      val = val.split(";");
                    }
                  }
                }

                frmItems[i].defaultValue = val;
                // if("TYPE_NAME"==frmItems[i].attrName){
                //   _self.typeName=frmItems[i].attrName；
                // }
                //console.log(JSON.stringify(frmItems[i].attrName)+":"+frmItems[i].defaultValue);
              _self.dataMap.set(frmItems[i].attrName,frmItems[i]);
              }
              
            }
            _self.dataList = indata;
          })
          .catch(function(error) {
            console.log(error);
          });
      }
    }
  }
}
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
.el-date-editor.el-input,
.el-date-editor.el-input__inner {
  width: 140px;
}
</style>