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
        <el-form-item label="类型" :label-width="formLabelWidth">
          <el-select v-model="form.fieldType">
            <el-option label="String" :value="1"></el-option>
            <el-option label="Int" :value="2"></el-option>
            <el-option label="Double" :value="3"></el-option>
            <el-option label="Long" :value="4"></el-option>
            <el-option label="DateTime" :value="5"></el-option>
            <el-option label="Boolean" :value="6"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="允许为空" :label-width="formLabelWidth">
          <el-select v-model="form.isNull">
            <el-option label="NO" value="NO"></el-option>
            <el-option label="YES" value="YES"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="长度" :label-width="formLabelWidth">
          <el-input v-model="form.length" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="默认值" :label-width="formLabelWidth">
          <el-input v-model="form.defaultValue" auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">{{$t('application.cancel')}}</el-button>
        <el-button type="primary" @click="saveitem(form)">确 定</el-button>
      </div>
    </el-dialog>
    <el-container>
      <el-header>
        <!-- <el-breadcrumb separator="/" class="navbar">
          <el-breadcrumb-item>系统管理</el-breadcrumb-item>
          <el-breadcrumb-item>系统元数据</el-breadcrumb-item>
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
            <el-button type="primary" plain icon="el-icon-edit" @click="newitem()">{{$t('application.new')}}</el-button>
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
          <el-table-column prop="name" label="字段名称" width="180" sortable></el-table-column>
          <el-table-column prop="description" label="说明" width="180" sortable></el-table-column>
          <el-table-column prop="fieldType" label="类型" width="120" sortable></el-table-column>
          <el-table-column prop="length" label="长度" width="120" sortable></el-table-column>
          <el-table-column label="操作" width="320">
            <template slot-scope="scope">
              <div v-if="scope.row.name.indexOf('C_')==0">
                <el-button
                  :plain="true"
                  type="success"
                  size="small"
                  icon="edit"
                  @click="showitem(scope.row)"
                >编辑</el-button>
                <el-button
                  :plain="true"
                  type="danger"
                  size="small"
                  icon="delete"
                  @click="delitem(scope.row)"
                >{{$t('application.delete')}}</el-button>
              </div>
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
  name: "DocAttrManager",
  permit: 9,
  data() {
    return {
      dataList: [],
      dataListFull: [],
      inputkey: "",
      loading: false,
      dialogVisible: false,
      isNew: false,
      tableHeight: window.innerHeight - 135,
      form: {
        name: "",
        description: "",
        fieldType: 1,
        length: 0,
        defaultValue: "",
        isNull: "YES"
      },
      formLabelWidth: "120px"
    };
  },
  created() {
    let _self = this;
    _self.loading = true;
    axios
      .get("/admin/getAttribute")
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
  methods: {
    refreshData() {
      let _self = this;
      _self.loading = true;
      axios
        .get("/admin/getAttribute")
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
      if (_self.isNew) {
        _self.additem(indata);
      } else {
        _self.updateitem(indata);
      }
      _self.dialogVisible = false;
    },
    updateitem(indata) {
      let _self = this;
      axios
        .post("/admin/updateAttribute", JSON.stringify(indata))
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
        .post("/admin/deleteAttribute", JSON.stringify(indata))
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
      _self.isNew = true;
      axios
        .post("/admin/newAttribute", JSON.stringify(indata))
        .then(function(response) {
          _self.dialogVisible = false;
          _self.refreshData();
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    newitem() {
      let _self = this;
      _self.isNew = true;
      _self.form.id = null;
      _self.form.description = "";
      _self.form.fieldType = 1;
      _self.form.length = 0;
      _self.form.defaultValue = "";
      _self.form.isNull = "YES";
      _self.dialogVisible = true;
    },
    showitem(indata) {
      let _self = this;
      _self.form = indata;
      _self.isNew = false;
      _self.dialogVisible = true;
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
