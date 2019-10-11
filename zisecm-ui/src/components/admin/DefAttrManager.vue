<template>
  <div>
    <el-dialog title="选择属性" :visible.sync="categoryVisible" >
      <CategoryManager ref="CategoryManager" @onselected="onselected" width="560" v-bind:categoryVisible="categoryVisible"></CategoryManager>
      <div slot="footer" class="dialog-footer">
        <el-button @click="categoryVisible = false">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog title="表单校验" :visible.sync="checkVisible" >
      <FormItemCheck ref="FormItemCheck" width="560" :parentformid="parentid"></FormItemCheck>
      <div slot="footer" class="dialog-footer">
        <el-button @click="checkVisible = false">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog title="选择查询条件" :visible.sync="queryVisible" >
      <QuerySelector ref="QuerySelector" @onqueryselected="onqueryselected" width="560"></QuerySelector>
      <div slot="footer" class="dialog-footer">
        <el-button @click="queryVisible = false">取 消</el-button>
      </div>
    </el-dialog>
        <el-dialog :title.sync="dialogtitle" :visible.sync="dialogVisible">
          <el-form :model="form">
            <el-form-item label="父Id" :label-width="formLabelWidth">
              <el-input v-model="form.parentId" auto-complete="off"></el-input>
            </el-form-item>
            <el-row>
              <el-col :span="10">
                <el-form-item label="属性名称" :label-width="formLabelWidth">
                  <el-input v-model="form.attrName" auto-complete="off"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="标签" :label-width="formLabelWidth">
                  <el-input v-model="form.label" auto-complete="off"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="4">
                <el-button @click="showcategory('')">选择属性</el-button>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
            <el-form-item label="控件类型" :label-width="formLabelWidth2">
              <el-select v-model="form.controlType">
                  <el-option label="TextBox" value="TextBox"></el-option>
                  <el-option label="TextArea" value="TextArea"></el-option>
                  <el-option label="Select" value="Select"></el-option>
                  <el-option label="ValueSelect" value="ValueSelect"></el-option>
                  <el-option label="SQLSelect" value="SQLSelect"></el-option>
                  <el-option label="Department" value="Department"></el-option>
                  <el-option label="Date" value="Date"></el-option>
                  <el-option label="Boolean" value="Boolean"></el-option>
                  <el-option label="Integer" value="Integer"></el-option>
                  <el-option label="Classification" value="Classification"></el-option>
              </el-select>
            </el-form-item>
              </el-col>
              <el-col :span="12">
            <el-form-item label="宽度类型" :label-width="formLabelWidth2">
              <el-select v-model="form.widthType">
                  <el-option label="1" value="1"></el-option>
                  <el-option label="2" value="2"></el-option>
                  <el-option label="3" value="3"></el-option>
                  <el-option label="4" value="4"></el-option>
              </el-select>
            </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="必填" :label-width="formLabelWidth2">
                    <el-select v-model="form.required">
                      <el-option label="否" value="false"></el-option>
                      <el-option label="是" value="true"></el-option>
                    </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="只读" :label-width="formLabelWidth2">
                  <el-select v-model="form.readOnly">
                    <el-option label="否" value="false"></el-option>
                    <el-option label="是" value="true"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="是否可查询" :label-width="formLabelWidth2">
                  <el-select v-model="form.searchable">
                    <el-option label="否" value="false"></el-option>
                    <el-option label="是" value="true"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="默认值" :label-width="formLabelWidth2">
                  <el-input :autosize="true" v-model="form.defaultValue"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="序号" :label-width="formLabelWidth">
                  <el-input v-model="form.orderIndex" auto-complete="off"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="隐藏" :label-width="formLabelWidth">
                    <el-select v-model="form.isHide">
                      <el-option label="否" value="false"></el-option>
                      <el-option label="是" value="true"></el-option>
                    </el-select>
                </el-form-item>
              </el-col>
            </el-row>
             <el-row>
              <el-col :span="12">
                <el-form-item label="SQL查询名" :label-width="formLabelWidth2">
                  <el-input v-model="form.queryName" auto-complete="off"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                  <el-button @click="queryVisible = true">选择查询</el-button>
              </el-col>
            </el-row>
             <el-row>
              <el-col :span="24">
                <el-form-item label="列选值" :label-width="formLabelWidth2">
                  <el-input v-model="form.valueList" auto-complete="off"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="additem(form)">确 定</el-button>
          </div>
        </el-dialog>
      <table border="0" width="100%" >
          <tr>
            <td class="navbar">
              <el-breadcrumb>
                <el-breadcrumb-item>系统管理</el-breadcrumb-item>
                <el-breadcrumb-item>界面配置</el-breadcrumb-item>
                <el-breadcrumb-item>表单项配置</el-breadcrumb-item>
              </el-breadcrumb>
            </td>
          </tr>
          <tr>
            <td>
              <table border="0" width="100%" class="topbar">
                <tr>
                  <td width="160px">
                    类型：{{typename}}
                  </td>
                  <td width="80px">
                    <el-input  v-model="parentid" placeholder="请输入父Id" @change="search" prefix-icon="el-icon-search"></el-input>
                  </td>
                  <td align="left" width="160px">
                    <el-input  v-model="inputkey" placeholder="请输入属性名关键字" @change="search" prefix-icon="el-icon-search"></el-input>
                  </td>
                  <td width="40px">
                    <el-tooltip  class="item" effect="dark" content="新增" placement="top">
                      <el-button type="primary" icon="el-icon-edit" circle @click="newitem()"></el-button>
                    </el-tooltip> 
                  </td>
                  <td>
                    <el-tooltip class="item" effect="dark" content="表单验证" placement="top">
                     <el-button type="primary" icon="el-icon-check" circle @click="startcheck()"></el-button>
                    </el-tooltip>
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
            style="width: 100%">
              <el-table-column label="属性名" width="150">
                <template slot-scope="scope">
                  <el-input  v-model="scope.row.attrName"></el-input>
                </template>
              </el-table-column>
              <el-table-column label="标签" width="120">
                <template slot-scope="scope">
                  <el-input  v-model="scope.row.label"></el-input>
                </template>
              </el-table-column> 
              <el-table-column label="控件类型" width="120">
                <template slot-scope="scope">
                  <el-input  v-model="scope.row.controlType"></el-input>
                </template>
              </el-table-column>
              <el-table-column label="宽度类型" width="90">
                <template slot-scope="scope">
                  <el-select v-model="scope.row.widthType">
                        <el-option label="1" value="1"></el-option>
                        <el-option label="2" value="2"></el-option>
                        <el-option label="3" value="3"></el-option>
                        <el-option label="4" value="4"></el-option>
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
                    <el-input  v-model="scope.row.defaultValue"></el-input>
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
                  <el-button :plain="true" type="primary" size="small" icon="edit" @click="edititem(scope.row)">编辑</el-button>
                  <el-button :plain="true" type="primary" size="small" icon="edit" @click="saveitem(scope.row)">保存</el-button>
                  <el-button :plain="true" type="danger" size="small" icon="delete" @click="delitem(scope.row)">删除</el-button>
                </template>
              </el-table-column>
          </el-table>
        </td>
      </tr>
    </table>
  </div>
</template>

<script type="text/javascript">
import CategoryManager from "@/components/admin/CategoryManager";
import QuerySelector from "@/components/controls/QuerySelector";
import FormItemCheck from "@/components/admin/FormItemCheck";

export default {
  name: "DefAttrManager",
  data() {
    return {
      dataList: [],
      dataListFull: [],
      tableHeight: window.innerHeight - 140,
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
        name: "",
        description: "",
        value: "",
        searchable: "true",
        required: "false",
        readOnly: "false",
        controlType: "TextBox",
        widthType: "2",
        orderIndex: "1",
        isHide: "false",
        valueList: ""
      },
      formLabelWidth: "120px",
      formLabelWidth2: "100px"
    };
  },
  components: {
    CategoryManager: CategoryManager,
    QuerySelector: QuerySelector,
    FormItemCheck: FormItemCheck
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
    _self
      .axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        method: "post",
        data: pid,
        url: "/zisecm/admin/getFormItem"
      })
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
      _self
        .axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data: pid,
          url: "/zisecm/admin/getFormItem"
        })
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
      _self
        .axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          datatype: "json",
          method: "post",
          data: JSON.stringify(indata),
          url: "/zisecm/admin/updateFormItem"
        })
        .then(function(response) {
          _self.$message("保存成功!");
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    delitem(indata) {
      let _self = this;
      _self
        .axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          datatype: "json",
          method: "post",
          data: JSON.stringify(indata),
          url: "/zisecm/admin/deleteFormItem"
        })
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
        _self
          .axios({
            headers: {
              "Content-Type": "application/json;charset=UTF-8"
            },
            datatype: "json",
            method: "post",
            data: JSON.stringify(indata),
            url: "/zisecm/admin/newFormItem"
          })
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
          _self.form.attrName = indata.field;
          _self.form.label = indata.comment;
        } else {
          _self.currentItem.attrName = indata.field;
          _self.currentItem.label = indata.comment;
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
        _self.dataList = _self.dataListFull.filter(function(item){
          return item.attrName.match(_self.inputkey) || item.label.match(_self.inputkey);
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
</style>
