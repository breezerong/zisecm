<template>
  <div>
    <el-dialog
      :visible.sync="visible"
      :append-to-body="true"
      :close-on-click-modal="false"
      @open="getTypeNames('AdvSearchTypes')"
      @close="closeDialog"
      :title="$t('application.SearchData')"
      width="70%"
    >
      <el-row>
        <el-col>
          {{$t('application.selectFileType')}}
          <font style="color:red;">*</font>：
        </el-col>
        <el-col>
          <el-select v-model="typeName" @change="refreshData(typeName)">
            <div v-for="options in typeNameOptions">
              <el-option :label="options" :value="options"></el-option>
            </div>
          </el-select>
        </el-col>
      </el-row>
      <el-row>
        <el-button @click="addplus" icon="el-icon-plus">{{$t('application.Add')}}</el-button>
        <el-button @click="removeminus" icon="el-icon-minus">{{$t('application.delete')}}</el-button>
      </el-row>
      <el-row :key="index" v-for="(item,index) in formtips">
        <el-col :span="3" v-model="item.relation1" class="topbar-button">
          <el-select v-model="item.relation" v-if="index!=0">
            <div v-for="relation in relations">
              <el-option :label="relation.label" :value="relation.val"></el-option>
            </div>
          </el-select>
        </el-col>
        <el-col :span="3" class="topbar-button">
          <el-select v-model="item.relation1">
            <div v-for="relation1 in relations1">
              <el-option :label="relation1.label" :value="relation1.val"></el-option>
            </div>
          </el-select>
        </el-col>

        <el-col :span="4" class="topbar-button">
          <el-select v-model="item.column" v-if="item.relation1!=')'">
            <div v-for="col in columns">
              <el-option v-if="col.isHide==false" :label="col.label" :value="col.attrName"></el-option>
            </div>
          </el-select>
        </el-col>
        <el-col :span="3" class="topbar-button">
          <el-select v-model="item.condition" v-if="item.relation1!=')'">
            <div v-for="condition in changeColumn(item.column,item)">
              <el-option :label="condition.label" :value="condition.val"></el-option>
            </div>
          </el-select>
        </el-col>
        <el-col :span="7" class="topbar-button" v-model="item.condition" v-if="item.relation1!=')'">
          <div v-for="ConditionsType in ConditionType(item.column,item)">
            <el-input v-if="ConditionsType=='2'" v-model="item.val" :type="inputType1"></el-input>
            <el-date-picker
              v-if="ConditionsType=='1'"
              value-format="yyyy-MM-dd"
              v-model="item.val"
              type="date"
              placeholder="选择日期"
              @change="dateChangebirthday"
            ></el-date-picker>
          </div>
        </el-col>
        <el-col :span="3" class="topbar-button">
          <el-select v-model="item.relation2">
            <div v-for="relation2 in relations2">
              <el-option :label="relation2.label" :value="relation2.val"></el-option>
            </div>
          </el-select>
        </el-col>
      </el-row>

      <div style="text-align:right">
        <el-button @click="onOk">{{$t('application.ok')}}</el-button>
      </div>
    </el-dialog>
    <el-row>
      <el-col :span="18" :style="{display:inputType=='hidden'?'none':''}">
        <el-input v-model="inputValue" :type="inputType"></el-input>
        <input value="value1" type="hidden" />
      </el-col>
      <el-col :span="6">
        <el-button
          icon="el-icon-search"
          @click="clickShowDialog(typeName)"
        >{{$t('route.advSearch')}}</el-button>
      </el-col>
    </el-row>
  </div>
</template>
<script>
export default {
  data() {
    return {
      left: 0,
      right: 0,
      currentLanguage: this.getLang(),
      visible: false,
      findValue: "",
      dataList: [],
      rightList: [],
      tranList2: [],
      tranList: [],
      rightNameList: "",
      rightListId: [],
      formtips: [],
      columns: [],
      typeNameOptions: [],
      typeName: "",
      relations1: [
        { label: "(", val: "(" },
        { label: ")", val: ")" },
      ],
      relations2: [
        { label: "(", val: "(" },
        { label: ")", val: ")" },
      ],
      relations: [
        { label: "且", val: "and" },
        { label: "或", val: "or" },
      ],
      intCondition: [
        { label: "大于", val: ">" },
        { label: "小于", val: "<" },
        { label: "大于等于", val: ">=" },
        { label: "小于等于", val: "<=" },
        { label: "不等于", val: "!=" },
        { label: "等于", val: "=" },
      ],
      textCondition: [
        { label: "包含", val: "like" },
        { label: "等于", val: "=" },
        { label: "不等于", val: "!=" },
        { label: "以...开始", val: "...like" },
        { label: "以...结束", val: "like..." },
      ],
      dateCondition: [
        { label: "大于", val: ">" },
        { label: "小于", val: "<" },
        { label: "大于等于", val: ">=" },
        { label: "小于等于", val: "<=" },
        { label: "不等于", val: "!=" },
        { label: "等于", val: "=" },
      ],
      boolCondition: [
        { label: "不等于", val: "!=" },
        { label: "等于", val: "=" },
      ],
      //conditions:[],
      /*[{"label":"包含","val":"like"},{"label":"等于","val":"="},{"label":"不等于","val":"!="},{"label":"大于","val":">"},
      {"label":"小于","val":"<"}],*/

      // form:{
      //   relation:"",
      //   column:"",
      //   condition:"",
      //   val:""
      // }
    };
  },
  model: {
    prop: "value1",
    event: "change",
  },
  mounted() {
    // this.getTypeNames("AdvSearchTypes");
  },
  props: {
    //输入框默认显示值
    inputValue: {
      type: String,
      default: "",
    },
    isRepeat: {
      type: Boolean,
      default: false,
    },
    maxCount: {
      type: Number,
      default: 50,
    },
    roleName: {
      type: String,
      default: "",
    },
    noGroup: {
      type: String,
      noGroup: "0",
    },
    inputType: {
      type: String,
      default: "text",
    },
    typeName: {
      type: String,
      default: "所有",
    },
  },

  methods: {
    getTypeNames(keyName) {
      let _self = this;
      axios
        .post("/dc/getParameters", keyName)
        .then(function (response) {
          _self.typeNameOptions = response.data.data.AdvSearchTypes;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    changeColumn(column, item) {
      let conditions = [];
      let obj = null;
      this.columns.forEach((e) => {
        if (e.attrName == column) {
          obj = e;
        }
      });
      if (obj == null) {
        return null;
      }
      item.dataType = obj.controlType;
      if (obj.controlType == "Integer") {
        conditions = this.intCondition;
      } else if (
        obj.controlType == "TextBox" ||
        obj.controlType == "TextArea" ||
        obj.controlType == "Select"
      ) {
        conditions = this.textCondition;
      } else if (obj.controlType == "Date") {
        conditions = this.dateCondition;
      } else {
        conditions = this.boolCondition;
      }
      return conditions;
    },
    ConditionType(column, item) {
      let ConditionsType;
      let obj = null;
      this.columns.forEach((e) => {
        if (e.attrName == column) {
          obj = e;
        }
      });
      if (obj == null) {
        return null;
      }
      item.dataType = obj.controlType;
      if (obj.controlType == "Date") {
        ConditionsType = "1";
      } else {
        ConditionsType = "2";
      }
      return ConditionsType;
    },
    addplus: function () {
      this.formtips.push({
        relation1: "",
        relation: "",
        column: "",
        dataType: "",
        condition: "",
        conditions: [],
        val: "",
      });
    },
    removeminus: function () {
      this.formtips.splice(-1);
    },
    onOk() {
      let _self = this;
      let sql = " ";
      if (_self.typeName == null || _self.typeName == "") {
        _self.$message("请选择类型名称");
        return;
      }
      if (_self.typeName != "所有") {
        sql += " TYPE_NAME='" + _self.typeName + "' and (";
      }
      _self.formtips.forEach((element) => {
        sql = sql + element.relation + " ";
        if (element.relations1 != undefined) {
          sql = sql + element.relation1;
        }
        if (element.relations1 == undefined) {
          sql = sql + " ";
        }
        _self.left++;
        sql = sql + element.column + " ";
        if (element.condition == "like") {
          sql = sql + element.condition + " '%" + element.val + "%' ";
        } else if (element.condition == "=" || element.condition == "!=") {
          if (element.dataType == "Integer") {
            sql = sql + element.condition + " " + element.val + " ";
          } else {
            sql = sql + element.condition + " '" + element.val + "' ";
          }
        } else if (
          element.condition == ">" ||
          element.condition == "<" ||
          element.condition == "<=" ||
          element.condition == ">="
        ) {
          if (element.dataType == "Date") {
            sql = sql + element.condition + " '" + element.val + "' ";
          } else {
            sql = sql + element.condition + " " + element.val + " ";
          }
        } else if (element.condition == "...like") {
          sql = sql + " like '" + element.val + "%' ";
        } else if (element.condition == "like...") {
          sql = sql + " like '%" + element.val + "' ";
        } else {
          sql = sql + element.condition + " " + element.val + " ";
        }

        if (element.relations2 != undefined) {
          sql = sql + element.relation2;
        }
        if (element.relations2 == undefined) {
          sql = sql + " ";
        }
        _self.right++;
        // +element.condition+' '+element.val
      });
      if (_self.typeName != "所有") {
        sql = sql + ")";
      }
      if (_self.left != _self.right) {
        sql = " TYPE_NAME='" + _self.typeName + "'";
        _self.right = 0;
        _self.left = 0;
        _self.$message({
          showClose: true,
          message: "查询条件有误",
          duration: 2000,
          type: "warring",
        });
        return;
      }
      if (_self.formtips.length == 0) {
        sql = " TYPE_NAME='" + _self.typeName + "'";
        _self.right = 0;
        _self.left = 0;
        _self.$message({
          showClose: true,
          message: "未添加查询条件",
          duration: 2000,
          type: "warring",
        });
        return;
      }
      _self.inputValue = sql;
      _self.$emit("change", _self.inputValue);
      _self.$emit("sendMsg", _self.inputValue);
      _self.visible = false;
    },
    refreshData(name) {
      if (name) {
        this.loadColumnInfo(name);
      }
    },
    clickShowDialog(name) {
      this.visible = true;
      this.refreshData(name);
    },
    closeDialog() {
      this.visible = false;
    },

    // 加载表单
    loadColumnInfo(name) {
      let _self = this;
      _self.loading = true;
      if (_self.myItemId != "") {
        _self.myTypeName = "";
      }
      var m = new Map();
      m.set("itemInfo", name); //_self.myItemId+_self.myTypeName);//ID 或类型
      m.set("lang", _self.getLang());
      //console.log(_self.itemId+","+_self.myItemId+","+_self.myTypeName+","+_self.folderId);
      axios
        .post("/dc/getFormItem", JSON.stringify(m))
        .then(function (response) {
          _self.columns = response.data.data;
          _self.loading = false;
        })
        .catch(function (error) {
          console.log(error);
          _self.loading = false;
        });
    },
  },
};
</script>
<style scoped>
li {
  padding-left: 10px;
}
</style>