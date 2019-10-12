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
            <el-form-item label="角色" :label-width="formLabelWidth">
              <el-input v-model="form.roleName" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="对象权限" :label-width="formLabelWidth">
              <el-input v-model="form.objectPermission" auto-complete="off"></el-input>
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
              /事件管理
            </td>
          </tr>
          <tr>
            <td>
              <table border="0" width="100%" class="topbar">
                <tr>
                  <td align="left" width="160px">
                    <el-input  v-model="inputkey" placeholder="请输入关键字" @change="search" prefix-icon="el-icon-search"></el-input>
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
                <el-table-column prop="name" label="名称" width="240" sortable>
                </el-table-column>
                <el-table-column label="说明" width="280">
                  <template slot-scope="scope">
                    <el-input  v-model="scope.row.description"></el-input>
                  </template>
                </el-table-column>
                <el-table-column label="角色" width="240">
                  <template slot-scope="scope">
                    <el-input  v-model="scope.row.roleName"></el-input>
                  </template>
                </el-table-column>
                <el-table-column label="对象权限" width="80">
                  <template slot-scope="scope">
                    <el-input  v-model="scope.row.objectPermission"></el-input>
                  </template>
                </el-table-column> 
                <el-table-column label="操作" width="160">
                  <template slot-scope="scope">
                    <el-button :plain="true" type="primary" size="small" icon="edit" @click="save(scope.row)">保存</el-button>
                    <el-button :plain="true" type="danger" size="small" icon="delete" @click="del(scope.row)">删除</el-button>
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
  name: "ActionManager",
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
        name: "",
        description: "",
        value: ""
      },
      formLabelWidth: "120px"
    };
  },
  beforeCreate()
  {
    
  },
   created(){ 
     
    let _self = this;
    _self.loading = true;
    _self.axios({
      headers: {
        "Content-Type": "application/json;charset=UTF-8"
      },
      method: 'get',
      url: '/zisecm/admin/getAction'
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
      _self.axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        method: 'get',
        url: '/zisecm/admin/getAction'
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
    save(indata) {
      let _self = this;
      _self.axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        datatype: 'json',
        method: 'post',
        data: JSON.stringify(indata),
        url: '/zisecm/admin/updateAction'
      })
      .then(function(response) {
        _self.$message("保存成功!");
      })
      .catch(function(error) {
        console.log(error);
      });
    },
    del(indata) {
      let _self = this;
      _self.axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        datatype: 'json',
        method: 'post',
        data: JSON.stringify(indata),
        url: '/zisecm/admin/deleteAction'
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
      _self.axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        datatype: 'json',
        method: 'post',
        data: JSON.stringify(indata),
        url: '/zisecm/admin/newAction'
      })
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
      let tab = _self.dataListFull;
      if (_self.inputkey != "") {
        _self.dataList = [];
        var i;
        for (i in tab) {
          if (tab[i].name.indexOf(_self.inputkey) >= 0||tab[i].description.indexOf(_self.inputkey) >= 0) {
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
