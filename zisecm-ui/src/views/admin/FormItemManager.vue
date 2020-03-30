<template>
  <div>
    <el-dialog title="表单校验" :visible.sync="checkVisible"  width="80%">
      <FormItemCheck ref="FormItemCheck" :parentformid="parentid"></FormItemCheck>
      <div slot="footer" class="dialog-footer">
        <el-button @click="checkVisible = false">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog title="选择查询条件" :visible.sync="queryVisible">
      <QuerySelector ref="QuerySelector" @onqueryselected="onqueryselected" width="560"></QuerySelector>
      <div slot="footer" class="dialog-footer">
        <el-button @click="queryVisible = false">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog :title.sync="dialogtitle" :visible.sync="dialogVisible" modal-append-to-body="false" width="80%">
      <el-form :model="form">
        <el-form-item label="父Id" :label-width="formLabelWidth">
          <el-input v-model="form.parentId" auto-complete="off"></el-input>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="属性名称" :label-width="formLabelWidth">
              <AttributeSelector ref="AttributeSelector" v-model="form.attrName"
                v-bind:inputValue="form.attrName"></AttributeSelector>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="标签" :label-width="formLabelWidth">
              <LangSelector
                v-model="form.label"
                v-bind:inputValue="form.label"
              ></LangSelector>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="控件类型" :label-width="formLabelWidth2">
              <el-select v-model="form.controlType">
                <el-option label="TextBox" value="TextBox"></el-option>
                <el-option label="TextArea" value="TextArea"></el-option>
                <el-option label="Select" value="Select"></el-option>
                <el-option label="ValueSelect" value="ValueSelect"></el-option>
                <el-option label="SQLSelect" value="SQLSelect"></el-option>
                <el-option label="UserSelect" value="UserSelect"></el-option>
                <el-option label="Department" value="Department"></el-option>
                <el-option label="Date" value="Date"></el-option>
                <el-option label="Boolean" value="Boolean"></el-option>
                <el-option label="Integer" value="Integer"></el-option>
                <el-option label="Classification" value="Classification"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="宽度类型" :label-width="formLabelWidth2">
              <el-select v-model="form.widthType">
                <el-option label="1" value="1"></el-option>
                <el-option label="2" value="2"></el-option>
                <el-option label="3" value="3"></el-option>
                <el-option label="4" value="4"></el-option>
                <el-option label="6" value="6"></el-option>
                <el-option label="8" value="8"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="必填" :label-width="formLabelWidth2">
              <el-select v-model="form.required">
                <el-option label="否" value="false"></el-option>
                <el-option label="是" value="true"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="只读" :label-width="formLabelWidth2">
              <el-select v-model="form.readOnly">
                <el-option label="否" value="false"></el-option>
                <el-option label="是" value="true"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="是否可查询" :label-width="formLabelWidth2">
              <el-select v-model="form.searchable">
                <el-option label="否" value="false"></el-option>
                <el-option label="是" value="true"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="默认值" :label-width="formLabelWidth2">
              <el-input :autosize="true" v-model="form.defaultValue"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="序号" :label-width="formLabelWidth">
              <el-input v-model="form.orderIndex" auto-complete="off"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="隐藏" :label-width="formLabelWidth">
              <el-select v-model="form.isHide">
                <el-option label="否" value="false"></el-option>
                <el-option label="是" value="true"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="SQL查询名" :label-width="formLabelWidth2">
              <el-input v-model="form.queryName" auto-complete="off"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-button @click="queryVisible = true">选择查询</el-button>
          </el-col>
          <el-col :span="24">
            <el-form-item label="列选值" :label-width="formLabelWidth2">
              <el-input v-model="form.valueList" auto-complete="off"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="是否多值" :label-width="formLabelWidth">
              <el-select v-model="form.isRepeat">
                <el-option label="否" value="false"></el-option>
                <el-option label="是" value="true"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="多值最少数" :label-width="formLabelWidth">
              <el-input v-model="form.minCount" auto-complete="off"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="多值最多数" :label-width="formLabelWidth">
              <el-input v-model="form.maxCount" auto-complete="off"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="启用变更事件" :label-width="formLabelWidth">
              <el-select v-model="form.enableChange">
                <el-option label="否" value="false"></el-option>
                <el-option label="是" value="true"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="依赖属性" :label-width="formLabelWidth">
              <el-input v-model="form.dependName" auto-complete="off"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="additem(form)">确 定</el-button>
      </div>
    </el-dialog>
    <el-container>
      <el-header>
        <el-breadcrumb separator="/" class="navbar">
          <el-breadcrumb-item>系统管理</el-breadcrumb-item>
          <el-breadcrumb-item>表单项配置</el-breadcrumb-item>
        </el-breadcrumb>
        <el-row class="topbar">
          <el-col :span="4">类型：{{typename}}</el-col>
          <el-col :span="4">
            <el-input
              v-model="inputkey"
              placeholder="请输入关键字"
              @change="search"
              prefix-icon="el-icon-search"
            ></el-input>
          </el-col>
          <el-col :span="16" style="text-align:left;">
            &nbsp; 
            <el-button type="primary" plain icon="el-icon-edit" @click="newitem()">新建</el-button>
            <el-button type="primary" plain icon="el-icon-check" @click="startcheck()">表单验证</el-button>
          </el-col>
        </el-row>
      </el-header>
      <el-main>
        <el-table :data="dataList" border :height="tableHeight" style="width: 100%" v-loading="loading">
          <el-table-column label="属性名" width="180" sortable>
            <template slot-scope="scope">
              <el-input v-model="scope.row.attrName"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="标签" width="120" sortable>
            <template slot-scope="scope">
              <el-input v-model="scope.row.label"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="控件类型" width="120">
            <template slot-scope="scope">
              <el-input v-model="scope.row.controlType"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="宽度类型" width="90">
            <template slot-scope="scope">
              <el-select v-model="scope.row.widthType">
                <el-option label="1" value="1"></el-option>
                <el-option label="2" value="2"></el-option>
                <el-option label="3" value="3"></el-option>
                <el-option label="4" value="4"></el-option>
                <el-option label="6" value="6"></el-option>
                <el-option label="8" value="8"></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="隐藏" width="90">
            <template slot-scope="scope">
              <el-select v-model="scope.row.isHide">
                <el-option label="否" value="false"></el-option>
                <el-option label="是" value="true"></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="必填" width="90">
            <template slot-scope="scope">
              <el-select v-model="scope.row.required">
                <el-option label="否" value="false"></el-option>
                <el-option label="是" value="true"></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="只读" width="90">
            <template slot-scope="scope">
              <el-select v-model="scope.row.readOnly">
                <el-option label="否" value="false"></el-option>
                <el-option label="是" value="true"></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="是否可查询" width="90">
            <template slot-scope="scope">
              <el-select v-model="scope.row.searchable">
                <el-option label="否" value="false"></el-option>
                <el-option label="是" value="true"></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="默认值" width="100">
            <template slot-scope="scope">
              <el-input v-model="scope.row.defaultValue"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="序号" width="80">
            <template slot-scope="scope">
              <el-input v-model="scope.row.orderIndex" auto-complete="off"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="查询名" width="100">
            <template slot-scope="scope">
              <el-input v-model="scope.row.queryName" auto-complete="off"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="240" fixed="right">
            <template slot-scope="scope">
              <el-button
                :plain="true"
                type="primary"
                size="small"
                icon="edit"
                @click="edititem(scope.row)"
              >编辑</el-button>
              <el-button
                :plain="true"
                type="primary"
                size="small"
                icon="edit"
                @click="saveitem(scope.row)"
              >保存</el-button>
              <el-button
                :plain="true"
                type="danger"
                size="small"
                icon="delete"
                @click="delitem(scope.row)"
              >删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-main>
    </el-container>
  </div>
</template>

<script type="text/javascript">
import AttributeSelector from "@/components/controls/AttributeSelector";
import QuerySelector from "@/components/admin/QuerySelector";
import FormItemCheck from "@/components/admin/FormItemCheck";
import LangSelector from "@/components/controls/LangSelector";

export default {
  name: "FormItemManager",
  data() {
    return {
      dataList: [],
      dataListFull: [],
      tableHeight: window.innerHeight - 115,
      inputkey: "",
      parentid: "",
      typename: "",
      loading: false,
      dialogVisible: false,
      dialogtitle: "",
      categoryVisible: false,
      queryVisible: false,
      checkVisible: false,
      currentItem: "",
      form: {
        parentId: "",
        attrName: "",
        description: "",
        value: "",
	      label:"",
        searchable: "true",
        required: "false",
        readOnly: "false",
        controlType: "TextBox",
        widthType: "2",
	      defaultValue:"",
        orderIndex: "1",
        isHide: "false",
	      queryName:"",
        valueList: "",
        isRepeat: false,
        enableChange: false,
        dependName: "",
        minCount: 0,
        maxCount: 0
      },
      formLabelWidth: "140px",
      formLabelWidth2: "100px"
    };
  },
  components: {
    AttributeSelector: AttributeSelector,
    QuerySelector: QuerySelector,
    FormItemCheck: FormItemCheck,
    LangSelector: LangSelector
  },
  created() {
    let _self = this;
    _self.loading = true;
    var pid = _self.$route.query.parentid;
    _self.typename = _self.$route.query.name;
    if (pid) {
      _self.parentid = pid;
      _self.form.parentId = pid;
    } else {
      pid = "0";
    }
    axios
      .post("/admin/getFormItem", pid)
      .then(function(response) {
        _self.dataListFull = response.data.data;
        _self.dataList = response.data.data;
        console.log(JSON.stringify(_self.dataListFull));
        _self.loading = false;
      })
      .catch(function(error) {
        console.log(error);
        _self.loading = false;
      });
  },
  methods: {
    startcheck() {
      let _self = this;
      _self.checkVisible = true;
      _self.$refs.FormItemCheck.loaddata();
    },
    refreshData() {
      let _self = this;
      _self.loading = true;
      var pid = _self.parentid;
      if (pid == "") {
        pid = "0";
      }
      axios
        .post("/admin/getFormItem", pid)
        .then(function(response) {
          _self.dataListFull = response.data.data;
          _self.dataList = response.data.data;

          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    saveitem(indata) {
      let _self = this;
      axios
        .post("/admin/updateFormItem", JSON.stringify(indata))
        .then(function(response) {
          _self.$message("保存成功!");
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    delitem(indata) {
      let _self = this;
      axios
        .post("/admin/deleteFormItem", JSON.stringify(indata))
        .then(function(response) {
          _self.$message("删除成功!");
          _self.refreshData();
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    newitem() {
      this.dialogtitle = "添加";
      this.dialogVisible = true;
      this.form = {
        parentId: this.parentid,
        name: "",
        description: "",
        value: "",
        searchable: "true",
        required: "false",
        readOnly: "false",
        controlType: "TextBox",
        widthType: "2",
        orderIndex: "1",
        valueList: "",
        isHide: "false"
      };
    },
    edititem(indata) {
      this.dialogtitle = "编辑";
      this.dialogVisible = true;
      this.form = indata;
    },
    additem(indata) {
      let _self = this;
      if (_self.dialogtitle == "编辑") {
        _self.saveitem(indata);
        _self.dialogVisible = false;
      } else {
        axios
          .post("/admin/newFormItem", JSON.stringify(indata))
          .then(function(response) {
            _self.dialogVisible = false;
            _self.refreshData();
          })
          .catch(function(error) {
            console.log(error);
          });
      }
    },
    showcategory(indata) {
      this.categoryVisible = true;
      this.currentItem = indata;
    },
    onselected(indata) {
      // alert(indata.fullName);
      let _self = this;
      _self.categoryVisible = false;
      if (indata) {
        if (_self.currentItem == "") {
          _self.form.attrName = indata.name;
          _self.form.label = indata.description;
        } else {
          _self.currentItem.attrName = indata.name;
          _self.currentItem.label = indata.description;
        }
      }
    },
    onqueryselected(indata) {
      // alert(indata.fullName);
      let _self = this;
      _self.queryVisible = false;
      if (indata) {
        _self.form.queryName = indata.name;
      }
    },
    search() {
      let _self = this;
      _self.dataList = [];
      if (_self.inputkey != "" || _self.parentid != "") {
        _self.dataList = _self.dataListFull.filter(function(item) {
          return (
            item.attrName.match(_self.inputkey) ||
            item.label.match(_self.inputkey)
          );
        });
      }
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
.el-header {
  background-color: #e8eaeb;
  height: 68px !important;
}
.el-row {
  padding-bottom: 10px;
}
</style>
