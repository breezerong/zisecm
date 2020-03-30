<template>
  <div>
    
    <el-dialog width="80%" title="列表校验" :visible.sync="checkVisible">
      <GridViewItemCheck ref="GridViewItemCheck" width="560" :parentgridid="parentid"></GridViewItemCheck>
      <div slot="footer" class="dialog-footer">
        <el-button @click="checkVisible = false">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog title="添加" :visible.sync="dialogVisible" :append-to-body="true" width="80%">
      <el-form :model="form">
        <el-form-item label="父Id" :label-width="formLabelWidth">
          <el-input v-model="form.parentId" auto-complete="off"></el-input>
        </el-form-item>
        <el-row>
          <el-col :span="10">
            <el-form-item label="属性名称" :label-width="formLabelWidth">
              <AttributeSelector ref="AttributeSelector" v-model="form.attrName"
                v-bind:inputValue="form.attrName"></AttributeSelector>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="标签" :label-width="formLabelWidth">
              <LangSelector
                v-model="form.label"
                v-bind:inputValue="form.label"
              ></LangSelector>
            </el-form-item>
          </el-col>
      
        </el-row>
        <el-form-item label="宽度" :label-width="formLabelWidth">
          <el-input v-model="form.width" auto-complete="off"></el-input>
        </el-form-item>
        <el-col :span="12">
          <el-form-item label="显示类型" :label-width="formLabelWidth">
            <el-select v-model="form.visibleType">
              <el-option label="显示" value="1"></el-option>
              <el-option label="可选" value="2"></el-option>
              <el-option label="隐藏" value="3"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="是否排序" :label-width="formLabelWidth">
            <el-select v-model="form.allowOrderby">
              <el-option label="是" :value="true"></el-option>
              <el-option label="否" :value="false"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-form-item label="序号" :label-width="formLabelWidth">
          <el-input v-model="form.orderIndex" auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveItem(form)">确 定</el-button>
      </div>
    </el-dialog>
    <el-container>
      <el-header>
        <el-breadcrumb separator="/" class="navbar">
          <el-breadcrumb-item>系统管理</el-breadcrumb-item>
          <el-breadcrumb-item>列表项管理</el-breadcrumb-item>
        </el-breadcrumb>
        <el-row class="topbar">
          <el-col :span="4">列表名：{{typename}}</el-col>
        
          <el-col :span="4">
            <el-input
              v-model="inputkey"
              placeholder="请输入关键字"
              @change="search"
              prefix-icon="el-icon-search"
            ></el-input>
          </el-col>
          <el-col :span="12" style="text-align:left;">
            &nbsp; 
            <el-button
              type="primary"
              icon="el-icon-edit"
              plain
              @click="newItem"
            >新建</el-button>
            <el-button type="primary" plain icon="el-icon-check" @click="startcheck()">验证</el-button>
          </el-col>
        </el-row>
      </el-header>
      <el-main>
        <el-table
          :data="dataList"
          border
          :height="tableHeight"
          v-loading="loading"
          style="width: 100%"
        >
          <el-table-column label="行号" type="index" width="60"></el-table-column>
          <el-table-column label="属性名" width="200" sortable>
            <template slot-scope="scope">
              <el-input v-model="scope.row.attrName"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="标签" min-width="20%" sortable>
            <template slot-scope="scope">
              <el-input v-model="scope.row.label"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="宽度" width="100">
            <template slot-scope="scope">
              <el-input v-model="scope.row.width"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="显示类型" width="120">
            <template slot-scope="scope">
              <el-select v-model="scope.row.visibleType">
                <el-option label="显示" value="1"></el-option>
                <el-option label="可选" value="2"></el-option>
                <el-option label="隐藏" value="3"></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="可排序" width="100">
            <template slot-scope="scope">
              <el-select v-model="scope.row.allowOrderby">
                <el-option label="是" :value="true"></el-option>
                <el-option label="否" :value="false"></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="序号" width="80">
            <template slot-scope="scope">
              <el-input v-model="scope.row.orderIndex"></el-input>
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
              >删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-main>
    </el-container>
  </div>
</template>

<script type="text/javascript">
// $.ajaxSetup({
//   contentType: "application/json"
// });
// });
import AttributeSelector from "@/components/controls/AttributeSelector";
import LangSelector from "@/components/controls/LangSelector";
import GridViewItemCheck from "@/components/admin/GridViewItemCheck";

export default {
  name: "GridViewItemManager",
  permit: 9,
  data() {
    return {
      dataList: [],
      dataListFull: [],
      inputkey: "",
      loading: false,
      dialogVisible: false,
      checkVisible: false,
      typename: "",
      parentid: "",
      idEdit: false,
      tableHeight: window.innerHeight - 115,
      form: {
        id:"",
        parentId:"",
        name: "",
        description: "",
        value: "",
        visibleType: "1",
        width: "120",
        orderIndex: 1,
        allowOrderby: false
      },
      formLabelWidth: "120px",
      formLabelWidth2: "60px"
    };
  },
  components: {
    AttributeSelector: AttributeSelector,
    LangSelector: LangSelector,
    GridViewItemCheck: GridViewItemCheck
  },
  mounted() {
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
    //alert(_self.parentid);
    axios
      .post("/admin/getGridViewItem", pid)
      .then(function(response) {
        _self.dataListFull = response.data.data;
        _self.dataList = response.data.data;
        _self.loading = false;
        _self.form.orderIndex = _self.dataListFull.length;
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
      _self.$refs.GridViewItemCheck.loaddata();
    },
    refreshData() {
      let _self = this;
      _self.loading = true;
      var pid = _self.parentid;
      if (pid == "") {
        pid = "0";
      }
      axios
        .post("/admin/getGridViewItem", pid)
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
    updateItem(indata) {
      let _self = this;
      axios
        .post("/admin/updateGridViewItem", JSON.stringify(indata))
        .then(function(response) {
          _self.dialogVisible = false;
          _self.$message("保存成功!");
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    delItem(indata) {
      let _self = this;
      axios
        .post("/admin/deleteGridViewItem", JSON.stringify(indata))
        .then(function(response) {
          _self.$message("删除成功!");
          _self.refreshData();
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    newItem(){
      this.idEdit = false;
      this.form.id = "";
      this.form.name = "";
      this.form.description = "";
      this.form.value = "";
      this.dialogVisible = true;
    },
    saveItem(indata){
      if(this.idEdit){
        this.updateItem(indata);
      }else{
        this.addItem(indata);
      }
    },
    editItem(indata){
      this.form = indata;
      this.idEdit = true;
      this.dialogVisible = true;
    },
    addItem(indata) {
      let _self = this;
      axios
        .post("/admin/newGridViewItem", JSON.stringify(indata))
        .then(function(response) {
          _self.dialogVisible = false;
          _self.refreshData();
        })
        .catch(function(error) {
          console.log(error);
        });
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
    search() {
      let _self = this;
      let tab = _self.dataListFull;
      _self.dataList = [];
      var i;
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
