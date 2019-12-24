<template>
  <div>
    <el-dialog title="选择属性" :visible.sync="categoryVisible">
      <div slot="footer" class="dialog-footer">
        <el-button @click="categoryVisible = false">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog title="添加" :visible.sync="dialogVisible">
      <el-form :model="form">
        <el-form-item label="名称" :label-width="formLabelWidth" sortable>
          <el-input v-model="form.name" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="说明" :label-width="formLabelWidth">
          <el-input v-model="form.description" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="类型" :label-width="formLabelWidth">
          <el-input v-model="form.itemType" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="值" :label-width="formLabelWidth">
          <el-input v-model="form.value" auto-complete="off"></el-input>
        </el-form-item>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
        <el-button @click="showcategory('')">选择属性</el-button>
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
          <el-breadcrumb-item>参数设置</el-breadcrumb-item>
        </el-breadcrumb>
        <el-row class="topbar">
          <el-col :span="4">
            <el-input
              v-model="inputkey"
              placeholder="请输入关键字"
              @change="search"
              prefix-icon="el-icon-search"
            ></el-input>
          </el-col>
          <el-col :span="20" style="text-align:left;">
            &nbsp; 
            <el-button
              type="primary"
              icon="el-icon-edit"
              plain="true"
              @click="dialogVisible = true"
            >新建</el-button>
          </el-col>
        </el-row>
      </el-header>
      <el-main>
        <el-table :data="dataList" border :height="tableHeight" v-loading="loading" width="98%">
          <el-table-column label="行号" type="index" width="60"></el-table-column>
          <el-table-column prop="name" label="名称" width="240" align="left" sortable></el-table-column>
          <el-table-column label="说明" width="240">
            <template slot-scope="scope">
              <el-input v-model="scope.row.description"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="值" min-width="20%">
            <template slot-scope="scope">
              <el-input v-model="scope.row.value"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="类型" width="180">
            <template slot-scope="scope">
              <el-input v-model="scope.row.itemType"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="170">
            <template slot-scope="scope">
              <el-button
                :plain="true"
                type="primary"
                size="small"
                icon="edit"
                @click="save(scope.row)"
              >保存</el-button>
              <el-button
                :plain="true"
                type="danger"
                size="small"
                icon="delete"
                @click="del(scope.row)"
              >删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-main>
    </el-container>
  </div>
</template>
<script type="text/javascript">
export default {
  name: "ParameterManager",
  permit: 9,
  data() {
    return {
      dataList: [],
      dataListFull: [],
      inputkey: "",
      loading: false,
      dialogVisible: false,
      categoryVisible: false,
      currentIndex: -1,
      currentItem: "",
      tableHeight: window.innerHeight - 115,
      form: {
        name: "",
        description: "",
        value: ""
      },
      formLabelWidth: "120px"
    };
  },

  mounted() {
    this.refreshData();
  },
  methods: {
    refreshData() {
      let _self = this;
      _self.loading = true;
      axios
        .get("/admin/getSysParameter")
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
    openmsg(msg) {
      this.$message("提示: " + msg);
    },
    save(indata) {
      let _self = this;
      axios
        .post("/admin/updateSysParameter", JSON.stringify(indata))
        .then(function(response) {
          _self.openmsg("保存成功!");
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    del(indata) {
      let _self = this;
      axios
        .post("/admin/deleteSysParameter", JSON.stringify(indata))
        .then(function(response) {
          _self.openmsg("删除成功!");
          _self.refreshData();
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    additem(indata) {
      let _self = this;
      axios
        .post("/admin/newSysParameter", JSON.stringify(indata))
        .then(function(response) {
          _self.dialogVisible = false;
          _self.refreshData();
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    showcategory(indata) {
      this.categoryVisible = true;
      this.currentItem = indata;
    },
    onselected(indata) {
      let _self = this;
      _self.categoryVisible = false;
      if (indata) {
        if (_self.currentItem == "") {
          _self.form.value = indata.field;
          _self.form.description = indata.comment;
        } else {
          _self.currentItem.value = indata.fullName;
          _self.currentItem.description = indata.typeName + ":" + indata.name;
        }
      }
    },
    search() {
      let _self = this;
      _self.dataList = _self.dataListFull.filter(function(item) {
        return (
          item.name.match(_self.inputkey) ||
          item.description.match(_self.inputkey)
        );
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
.el-header {
  background-color: #e8eaeb;
  height: 68px !important;
}
.el-row {
  padding-bottom: 10px;
}
</style>
