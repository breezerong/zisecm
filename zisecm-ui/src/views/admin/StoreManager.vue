<template>
  <div>
    <el-dialog title="添加" :visible.sync="dialogVisible">
      <el-form :model="form">
        <el-form-item label="名称" :label-width="formLabelWidth">
          <el-input v-model="form.name" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="说明" :label-width="formLabelWidth">
          <el-input v-model="form.description" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="存储类型" :label-width="formLabelWidth">
          <el-select v-model="form.storeType">
            <el-option label="路径存储" :value="0"></el-option>
            <el-option label="扩展存储" :value="1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="执行类" :label-width="formLabelWidth">
          <el-input v-model="form.storeClass" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="存储路径" :label-width="formLabelWidth">
          <el-input v-model="form.storePath" auto-complete="off"></el-input>
        </el-form-item>
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
          <el-breadcrumb-item>存储管理</el-breadcrumb-item>
        </el-breadcrumb>

        <el-form :inline="true" style="text-align: left;" class="topbar">
          <el-form-item>
            <el-input
              v-model="inputkey"
              placeholder="请输入关键字"
              @change="search"
              prefix-icon="el-icon-search"
            ></el-input>
          </el-form-item>
          <el-form-item>
            &nbsp;
            <el-button type="primary" plain icon="el-icon-edit" @click="dialogVisible = true">新建</el-button>
          </el-form-item>
        </el-form>
      </el-header>
      <el-main>
        <el-table
          :data="dataList"
          border
          :height="tableHeight"
          v-loading="loading"
          :row-style="rowStyle"
          :cell-style="cellStyle"
          style="width: 100%"
        >
          <el-table-column label="行号" type="index" width="60" />
          <el-table-column label="名称" width="160" sortable>
            <template slot-scope="scope">
              <el-input v-model="scope.row.name"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="说明" min-width="15%">
            <template slot-scope="scope">
              <el-input v-model="scope.row.description"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="存储类型" width="120">
            <template slot-scope="scope">
              <el-input v-model="scope.row.storeType"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="执行类" min-width="20%">
            <template slot-scope="scope">
              <el-input v-model="scope.row.storeClass"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="路径" min-width="20%">
            <template slot-scope="scope">
              <el-input v-model="scope.row.storePath"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="170">
            <template slot-scope="scope">
              <el-button
                :plain="true"
                type="success"
                size="small"
                icon="save"
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
// $.ajaxSetup({
//   contentType: "application/json"
// });

export default {
  name: "StoreManager",
  permit: 5,
  data() {
    return {
      dataList: [],
      dataListFull: [],
      inputkey: "",
      loading: false,
      dialogVisible: false,
      tableHeight: window.innerHeight - 115,
      form: {
        name: "",
        description: "",
        condition: "",
        orderBy: ""
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
        .get("/admin/getStore")
        .then(function(response) {
          _self.dataList = response.data.data;
          _self.dataListFull = response.data.data;
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
        .post("/admin/updateStore", JSON.stringify(indata))
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
        .post("/admin/deleteStore", JSON.stringify(indata))
        .then(function(response) {
          _self.$message("删除成功!");
          _self.refreshData();
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    additem(indata) {
      let _self = this;
      axios
        .post("/admin/newStore", JSON.stringify(indata))
        .then(function(response) {
          _self.dialogVisible = false;
          _self.refreshData();
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    showitem(indata) {
      let _self = this;
    },
    search() {
      let _self = this;
      _self.dataList = _self.dataListFull.filter(function(item) {
        return (
          item.name.match(_self.inputkey) ||
          item.description.match(_self.inputkey)
        );
      });
    },
    rowStyle({ row, rowIndex }) {
      return "height: 0";
    },
    cellStyle({ row, column, rowIndex, columnIndex }) {
      return "padding:0";
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
.el-row{
  padding-bottom:10px;
}
</style>
