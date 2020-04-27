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
        <el-form-item label="标签列" :label-width="formLabelWidth">
          <el-input v-model="form.labelColumn" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="值列" :label-width="formLabelWidth">
          <el-input v-model="form.valueColumn" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="SQL" :label-width="formLabelWidth">
          <el-input v-model="form.sqlString" auto-complete="off" :rows="2"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="additem(form)">确 定</el-button>
      </div>
    </el-dialog>
    <el-container>
      <el-header>
        <!-- <el-breadcrumb separator="/" class="navbar">
          <el-breadcrumb-item>系统管理</el-breadcrumb-item>
          <el-breadcrumb-item>查询管理</el-breadcrumb-item>
        </el-breadcrumb> -->
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
          row-style="height: 0"
          cell-style="padding:0"
          style="width: 100%"
        >
          <el-table-column prop="name" label="名称" width="160" sortable></el-table-column>
          <el-table-column label="说明" width="160">
            <template slot-scope="scope">
              <el-input v-model="scope.row.description"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="标签列" width="160">
            <template slot-scope="scope">
              <el-input v-model="scope.row.labelColumn"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="值列" width="160">
            <template slot-scope="scope">
              <el-input v-model="scope.row.valueColumn"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="SQL" min-width="30%">
            <template slot-scope="scope">
              <el-input v-model="scope.row.sqlString" :rows="2"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="240">
            <template slot-scope="scope">
              <router-link :to="{path:'/SelectValueManager',query:{name:scope.row.name}}">
                <el-button :plain="true" type="info" size="small" icon="edit">查看</el-button>
              </router-link>&nbsp;
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
  name: "QueryManager",
  permit: 3,
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
        .get("/admin/getQuery")
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
        .post("/admin/updateQuery", JSON.stringify(indata))
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
        .post("/admin/deleteQuery", JSON.stringify(indata))
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
        .post("/admin/newQuery", JSON.stringify(indata))
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
  height: 42px !important;
}
.el-main{
  padding:5px;
}
.el-row {
  padding-bottom: 10px;
}
</style>
