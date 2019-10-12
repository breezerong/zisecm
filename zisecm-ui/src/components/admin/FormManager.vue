<template>
  <div>
        <el-dialog title="添加" :visible.sync="dialogVisible">
          <el-form :model="form">
            <el-form-item label="类型名称" :label-width="formLabelWidth">
              <el-input v-model="form.typeName" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="说明" :label-width="formLabelWidth">
              <el-input v-model="form.description" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="事件" :label-width="formLabelWidth">
                <el-select v-model="form.action">
                  <el-option label="NEW" value="NEW"></el-option>
                  <el-option label="EDIT" value="EDIT"></el-option>
                  <el-option label="VIEW" value="VIEW"></el-option>
                  <el-option label="SEARCH" value="SEARCH"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="列数" :label-width="formLabelWidth">
              <el-input v-model="form.columnCount" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="是否默认" :label-width="formLabelWidth">
                <el-select v-model="form.isDefault">
                  <el-option label="否" value="false"></el-option>
                  <el-option label="是" value="true"></el-option>
              </el-select>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="additem(form)">确 定</el-button>
          </div>
        </el-dialog>
      <table border="0" width="100%" >
          <tr>
            <td class="navbar">
              /表单管理
            </td>
          </tr>
          <tr>
            <td>
              <table border="0" width="100%" class="topbar">
                <tr>
                  <td align="left" width="160px">
                    <el-input  v-model="inputkey" placeholder="请输入关键字" @change="searchform" prefix-icon="el-icon-search"></el-input>
                  </td>
                  <td>
                    <el-button type="primary" icon="el-icon-edit" circle @click="dialogVisible = true"></el-button>
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
                v-loading="loading"
                row-style="height: 0"
                cell-style="padding:0"
                style="width: 100%">
        <el-table-column
                label="行号"
                type="index"
                width="60">
              </el-table-column>
        <el-table-column label="类型名" width="180" >
           <template slot-scope="scope">
            <el-input  v-model="scope.row.typeName"></el-input>
          </template>
        </el-table-column>
        <el-table-column label="说明" width="240">
          <template slot-scope="scope">
            <el-input  v-model="scope.row.description"></el-input>
          </template>
        </el-table-column>
        <el-table-column label="事件" width="120">
          <template slot-scope="scope">
           <el-select v-model="scope.row.action">
                  <el-option label="NEW" value="NEW"></el-option>
                  <el-option label="EDIT" value="EDIT"></el-option>
                  <el-option label="VIEW" value="VIEW"></el-option>
              </el-select>
          </template>
        </el-table-column> 
        <el-table-column label="列数" width="80">
          <template slot-scope="scope">
            <el-input  v-model="scope.row.columnCount"></el-input>
          </template>
        </el-table-column>
        <el-table-column label="查询字段" width="80">
          <template slot-scope="scope">
            <el-input  v-model="scope.row.searchField"></el-input>
          </template>
        </el-table-column>
        <el-table-column label="是否默认" width="80">
          <template slot-scope="scope">
            <el-select v-model="scope.row.isDefault">
                  <el-option label="否" value="false"></el-option>
                  <el-option label="是" value="true"></el-option>
              </el-select>
          </template>
        </el-table-column> 
        <el-table-column label="操作" width="320">
          <template slot-scope="scope">
            <router-link :to="{path:'/managercenter/formitemmanager',query:{parentid:scope.row.id,name:scope.row.typeName}}"><el-button :plain="true" type="info" size="small" icon="edit">查看</el-button></router-link>
            &nbsp; 
            <el-button :plain="true" type="primary" size="small" icon="edit" @click="saveitem(scope.row)">保存</el-button>
            <el-button :plain="true" type="warning" size="small" icon="edit" @click="copyitem(scope.row)">复制</el-button>
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
// $.ajaxSetup({
//   contentType: "application/json"
// });

export default {
  name: "FormManager",
  permit: 9,
  data() {
    return {
      dataList: [],
      dataListFull: [],
      inputkey: "",
      loading: false,
      dialogVisible: false,
      tableHeight: window.innerHeight - 140,
      form: {
        typeName: "",
        description: "",
        action: "",
        columnCount: 2,
        isDefault: 0
      },
      formLabelWidth: "120px"
    };
  },
  created() {
    let _self = this;
    _self.loading = true;
    _self
      .axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        method: "get",
        url: "/zisecm/admin/getForm"
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
  methods: {
    refreshData() {
      let _self = this;
      _self.loading = true;
      _self
        .axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: "get",
          url: "/zisecm/admin/getForm"
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
          url: "/zisecm/admin/updateForm"
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
          url: "/zisecm/admin/deleteForm"
        })
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
      _self
        .axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          datatype: "json",
          method: "post",
          data: JSON.stringify(indata),
          url: "/zisecm/admin/newForm"
        })
        .then(function(response) {
          _self.dialogVisible = false;
          _self.refreshData();
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    copyitem(indata) {
      let _self = this;
      _self
        .axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          datatype: "json",
          method: "post",
          data: JSON.stringify(indata),
          url: "/zisecm/admin/copyForm"
        })
        .then(function(response) {
          _self.$message("复制成功!");
          _self.dialogVisible = false;
          _self.refreshData();
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    searchform() {
      let _self = this;
      let tab = _self.dataListFull;
      
      if (_self.inputkey != "") {
        _self.dataList = [];
        var i;
        for (i in tab) {
          if (
            tab[i].typeName.indexOf(_self.inputkey) >= 0 || 
            (tab[i].description && tab[i].description.indexOf(_self.inputkey) >= 0)
          ) {
            _self.dataList.push(tab[i]);
          }
        }
      } else {
        _self.dataList = _self.dataListFull;
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
