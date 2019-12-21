<template>
  <div>
    <el-dialog title="添加" :visible.sync="dialogVisible">
      <el-form :model="form">
        <el-form-item label="标签" :label-width="formLabelWidth">
          <el-input v-model="form.label" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="值" :label-width="formLabelWidth">
          <el-input v-model="form.value" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="类型名" :label-width="formLabelWidth">
          <el-input v-model="form.typeName" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="启用" :label-width="formLabelWidth">
          <el-select v-model="form.enabled">
            <el-option label="否" value="false"></el-option>
            <el-option label="是" value="true"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="排序" :label-width="formLabelWidth">
          <el-input v-model="form.orderIndex" auto-complete="off" :rows="2"></el-input>
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
          <el-breadcrumb-item>选择项管理</el-breadcrumb-item>
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
        <el-table
          :data="dataList"
          border
          :height="tableHeight"
          v-loading="loading"
          style="width: 100%"
        >
          <el-table-column prop="id" label="ID" width="80" sortable></el-table-column>
          <el-table-column label="标签" width="160">
            <template slot-scope="scope">
              <el-input v-model="scope.row.label"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="值" width="160">
            <template slot-scope="scope">
              <el-input v-model="scope.row.value"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="类型名" width="160">
            <template slot-scope="scope">
              <el-input v-model="scope.row.typeName"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="是否启用" width="100">
            <template slot-scope="scope">
              <el-select v-model="scope.row.enabled">
                <el-option label="否" value="false"></el-option>
                <el-option label="是" value="true"></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="排序" width="80">
            <template slot-scope="scope">
              <el-input v-model="scope.row.orderIndex"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="160">
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
// $.ajaxSetup({
//   contentType: "application/json"
// });

export default {
  name: "SelectValueManager",
  data() {
    return {
      dataList: [],
      dataListFull: [],
      inputkey: "",
      loading: false,
      dialogVisible: false,
      fromparent: false,
      tableHeight: window.innerHeight - 115,
      form: {
        value: "",
        label: "",
        enabled: "true",
        typeName: "",
        orderIndex: 1
      },
      formLabelWidth: "120px"
    };
  },
  created() {
    let _self = this;
    if (_self.$route.query.name) {
      _self.inputkey = _self.$route.query.name;
      _self.fromparent = true;
    }
    _self.loading = true;
    axios
      .get("/admin/getSelectValue")
      .then(function(response) {
        _self.dataListFull = response.data.data;
        _self.dataList = response.data.data;
        _self.loading = false;
        if (_self.inputkey != "") {
          _self.search();
        }
      })
      .catch(function(error) {
        console.log(error);
        _self.loading = false;
      });
  },
  methods: {
    refreshData() {
      let _self = this;
      _self.loading = true;
      axios
        .get("/admin/getSelectValue")
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
    save(indata) {
      let _self = this;
      axios
        .post("/admin/updateSelectValue", JSON.stringify(indata))
        .then(function(response) {
          _self.$message("保存成功!");
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    del(indata) {
      let _self = this;
      axios
        .post("/admin/deleteSelectValue", JSON.stringify(indata))
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
        .post("/admin/newSelectValue", JSON.stringify(indata))
        .then(function(response) {
          _self.dialogVisible = false;
          _self.refreshData();
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    search() {
      let _self = this;
      _self.dataList = _self.dataListFull.filter(function(item) {
        return (
          item.label.match(_self.inputkey) ||
          item.value.match(_self.inputkey) ||
          item.typeName.match(_self.inputkey)
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
