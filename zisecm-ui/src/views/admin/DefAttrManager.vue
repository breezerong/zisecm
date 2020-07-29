<template>
  <div>
    <el-dialog title="选择属性" :visible.sync="categoryVisible">
      <CategoryManager
        ref="CategoryManager"
        @onselected="onselected"
        width="560"
        v-bind:categoryVisible="categoryVisible"
      ></CategoryManager>
      <div slot="footer" class="dialog-footer">
        <el-button @click="categoryVisible = false">{{$t('application.cancel')}}{{$t('application.cancel')}}</el-button>
      </div>
    </el-dialog>
    <el-dialog title="表单校验" :visible.sync="checkVisible">
      <FormItemCheck ref="FormItemCheck" width="560" :parentformid="parentid"></FormItemCheck>
      <div slot="footer" class="dialog-footer">
        <el-button @click="checkVisible = false">{{$t('application.cancel')}}</el-button>
      </div>
    </el-dialog>
    <el-dialog title="选择查询条件" :visible.sync="queryVisible">
      <QuerySelector ref="QuerySelector" @onqueryselected="onqueryselected" width="560"></QuerySelector>
      <div slot="footer" class="dialog-footer">
        <el-button @click="queryVisible = false">{{$t('application.cancel')}}</el-button>
      </div>
    </el-dialog>
    <el-dialog :title.sync="dialogtitle" :visible.sync="dialogVisible" modal-append-to-body="false" width="80%">
      <el-form :model="form">
        <el-form-item label="父Id" :label-width="formLabelWidth">
          <el-input v-model="form.typeId" auto-complete="off" readonly></el-input>
        </el-form-item>
        <el-row>
          <el-col :span="8">
            <el-form-item label="属性名称" :label-width="formLabelWidth">
              <AttributeSelector ref="AttributeSelector" v-model="form.name"
                v-bind:inputValue="form.name"></AttributeSelector>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="标签" :label-width="formLabelWidth">
              <LangSelector
                v-model="form.label"
                v-bind:inputValue="form.label"
              ></LangSelector>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="说明" :label-width="formLabelWidth2">
              <el-input v-model="form.description" auto-complete="off"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="是否可查询" :label-width="formLabelWidth2">
              <el-select v-model="form.searchable">
                <el-option label="否" :value="false"></el-option>
                <el-option label="是" :value="true"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
           <el-col :span="8">
            <el-form-item label="最大长度" :label-width="formLabelWidth2">
              <el-input :autosize="true" v-model="form.maxLength"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="默认值" :label-width="formLabelWidth2">
              <el-input :autosize="true" v-model="form.defaultValue"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="是否多值" :label-width="formLabelWidth">
              <el-select v-model="form.isRepeat">
                <el-option label="否" :value="false"></el-option>
                <el-option label="是" :value="true"></el-option>
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
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">{{$t('application.cancel')}}</el-button>
        <el-button type="primary" @click="additem(form)">确 定</el-button>
      </div>
    </el-dialog>
    <el-container>
      <el-header>
        <el-row class="topbar">
          <el-col :span="4" style="text-align:left;">类型：{{typename}}</el-col>
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
            <el-button type="primary" plain icon="el-icon-edit" @click="newItem()">{{$t('application.new')}}</el-button>
          </el-col>
        </el-row>
      </el-header>
      <el-main>
        <el-table :data="dataList" border :height="tableHeight" style="width: 100%">
          <el-table-column label="属性名" width="150" sortable>
            <template slot-scope="scope">
              <el-input v-model="scope.row.name"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="标签" width="180" sortable>
            <template slot-scope="scope">
              <el-input v-model="scope.row.label"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="说明" min-width="20%" sortable>
            <template slot-scope="scope">
              <el-input v-model="scope.row.description"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="是否可查询" width="110">
            <template slot-scope="scope">
              <el-select v-model="scope.row.searchable">
                <el-option label="否" :value="false"></el-option>
                <el-option label="是" :value="true"></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="默认值" width="160">
            <template slot-scope="scope">
              <el-input v-model="scope.row.defaultValue"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="是否多值" width="110">
            <template slot-scope="scope">
              <el-select v-model="scope.row.isRepeat">
                <el-option label="否" :value="false"></el-option>
                <el-option label="是" :value="true"></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="240" fixed="right">
            <template slot-scope="scope">
              <el-button
                :plain="true"
                type="primary"
                size="small"
                icon="edit"
                @click="editItem(scope.row)"
              >编辑</el-button>
              <el-button
                :plain="true"
                type="primary"
                size="small"
                icon="edit"
                @click="saveItem(scope.row)"
              >保存</el-button>
              <el-button
                :plain="true"
                type="danger"
                size="small"
                icon="delete"
                @click="delItem(scope.row)"
              >{{$t('application.delete')}}</el-button>
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
import LangSelector from "@/components/controls/LangSelector";

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
        typeId: "",
        name: "",
        description: "",
        defaultValue: "",
        searchable: true,
        maxLength: 0,
        isRepeat: false,
        minCount: 0,
        maxCount: 0
      },
      formLabelWidth: "120px",
      formLabelWidth2: "100px"
    };
  },
  components: {
    AttributeSelector: AttributeSelector,
    QuerySelector: QuerySelector,
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
    _self.refreshData();
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
          url: "/admin/getDefAttributeByTypeId"
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
    saveItem(indata) {
      let _self = this;
      _self
        .axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          datatype: "json",
          method: "post",
          data: JSON.stringify(indata),
          url: "/admin/updateDefAttribute"
        })
        .then(function(response) {
          _self.$message("保存成功!");
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    delItem(indata) {
      let _self = this;
      _self
        .axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          datatype: "json",
          method: "post",
          data: JSON.stringify(indata),
          url: "/admin/deleteDefAttribute"
        })
        .then(function(response) {
          _self.$message("删除成功!");
          _self.refreshData();
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    newItem() {
      this.dialogtitle = "添加";
      this.dialogVisible = true;
      this.form = {
        typeId: this.parentid,
        name: "",
        description: "",
        defaultValue: "",
        searchable: true,
        isRepeat: false,
        maxLength: 0
      };
    },
    editItem(indata) {
      this.dialogtitle = "编辑";
      this.dialogVisible = true;
      this.form = indata;
    },
    additem(indata) {
      let _self = this;
      if (_self.dialogtitle == "编辑") {
        _self.saveItem(indata);
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
            url: "/admin/newDefAttribute"
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
  height: 42px !important;
}
.el-main{
  padding:5px;
}
.el-row {
  padding-bottom: 10px;
}
</style>
