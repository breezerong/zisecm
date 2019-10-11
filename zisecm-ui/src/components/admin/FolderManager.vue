<template>
  <div>
        <el-dialog title="添加" :visible.sync="dialogVisible">
          <el-form :model="form">
            <el-form-item label="ID" :label-width="formLabelWidth">
              {{form.id}}
            </el-form-item>
            <el-form-item label="名称" :label-width="formLabelWidth">
              <el-input v-model="form.name" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="说明" :label-width="formLabelWidth">
              <el-input v-model="form.description" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="父Id" :label-width="formLabelWidth">
                <el-input v-model="form.parentId" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="类型名称" :label-width="formLabelWidth">
              <el-select v-model="form.typeName">
                <el-option v-for="item in typeList"
                  :label="item.name"
                  :key="item.name"
                  :value="item.name">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="视图" :label-width="formLabelWidth">
              <el-input v-model="form.gridView" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="ACL" :label-width="formLabelWidth">
              <el-input v-model="form.aclName" auto-complete="off"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="additem(form)">添加</el-button>
            <el-button type="primary" @click="saveitem(form)">保存</el-button>
          </div>
        </el-dialog>
      <table border="0" width="100%" >
          <tr>
            <td class="navbar">
              /文件夹管理
            </td>
          </tr>
          <tr>
            <td>
              <table border="0" width="100%" class="topbar">
                <tr>
                  <td align="left" width="160px">
                    <el-input  v-model="inputkey" placeholder="请输入关键字" @change="searchFolder" prefix-icon="el-icon-search"></el-input>
                  </td>
                  <td>
                    <el-button type="primary" icon="el-icon-edit" circle @click="dialogVisible = true"></el-button>
                    <el-button type="primary" icon="el-icon-delete" circle @click="delitem(currentData)"></el-button>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        <tr>
          <td>
            <el-tree
              :props="defaultProps"
              :data="dataList"
              node-key="id"
              lazy
              @node-click="handleNodeClick">
            </el-tree>
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
  name: "FolderManager",
  permit: 3,
  data() {
    return {
      dataList: [],
      typeList: [],
      currentData: "",
      dataListFull: "",
      inputkey: "",
      loading: false,
      dialogVisible: false,
      form: {
        id: 0,
        name: "",
        description: "",
        parentId: 0,
        typeName: "Folder",
        gridView: "",
        aclName: ""
      },
      formLabelWidth: "120px",
      defaultProps: {
        children: 'children',
        label: 'name'
      }
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
        method: "post",
        data: 0,
        url: "/zisecm/admin/getFolder"
      })
      .then(function(response) {
        _self.dataList = response.data.data;
        _self.loading = false;
      })
      .catch(function(error) {
        console.log(error);
        _self.loading = false;
      });

      _self.loading = true;
      _self.axios({
      headers: {
        "Content-Type": "application/json;charset=UTF-8"
      },
      method: 'get',
      url: '/zisecm/admin/getType'
    })
      .then(function(response) {
        _self.typeList = response.data.data;
        _self.loading = false;
        console.log(_self.storeList );
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
        method: "post",
        data: 0,
        url: "/zisecm/admin/getFolder"
      })
      .then(function(response) {
        _self.dataList = response.data.data;
        _self.loading = false;
      })
      .catch(function(error) {
        console.log(error);
        _self.loading = false;
      });
  },
    loadChildNode(data)
    {
    },
    handleNodeClick(indata) {
      let _self = this;
      _self.currentData = indata;
      _self.form.id = indata.id;
      _self.form.name = indata.name;
      _self.form.description = indata.description;
      _self.form.parentId = indata.parentId;
      _self.form.typeName = indata.typeName;
      _self.form.gridView = indata.gridView;
      _self.form.aclName = indata.aclName;
     // console.log(JSON.stringify(indata));
      if(indata.extended == false)
      {
        _self.loading = true;
        _self
        .axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data: indata.id,
          url: "/zisecm/admin/getFolder"
        })
        .then(function(response) {
          // _self.$message("获取子节点成功!");
          indata.children = response.data.data;
          indata.extended = true;
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
      }
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
          url: "/zisecm/admin/updateFolder"
        })
        .then(function(response) {
          _self.$message("保存成功!");
           _self.dialogVisible = false;
          _self.currentData.name = indata.name;
          _self.currentData.description = indata.description;
          _self.currentData.parentId = indata.parentId;
          _self.currentData.typeName = indata.typeName;
          _self.currentData.gridView = indata.gridView;
          _self.currentData.aclName = indata.aclName;
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
          url: "/zisecm/admin/deleteFolder"
        })
        .then(function(response) {
          if(response.data.code==1)
          {
            _self.$message("删除成功!");
            _self.refreshData();
          }
          else
          {
            _self.$message(response.data.msg);
          }
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
          url: "/zisecm/admin/newFolder"
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
          url: "/zisecm/admin/copyFolder"
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
    searchFolder() {
      let _self = this;
      let tab = _self.dataListFull;
      
      if (_self.inputkey != "") {
        _self.dataList = [];
        var i;
        for (i in tab) {
          if (
            tab[i].name.indexOf(_self.inputkey) >= 0 || 
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
