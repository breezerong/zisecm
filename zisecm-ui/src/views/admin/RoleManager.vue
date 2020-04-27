<template>
  <div>
    <el-dialog title="选择用户" :visible.sync="selectUserDialogVisible" width="820px">
      <UserSelector
        ref="UserSelector"
        v-on:onuserselected="onuserselected"
        :noGroup="1"
        :groupId="selectedItemId"
      ></UserSelector>
      <div slot="footer" class="dialog-footer">
        <el-button @click="selectUserDialogVisible = false">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog :title.sync="dialogtitle" :visible.sync="dialogVisible">
      <el-form :model="form">
        <el-row>
          <el-col :span="24">
            <el-form-item
              label="名称"
              :label-width="formLabelWidth"
              :rules="[{ required: true, message: '必填', trigger: 'blur'}]"
            >
              <el-input v-model="form.name" auto-complete="off" :disabled="isReadOnly"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="说明" :label-width="formLabelWidth">
              <el-input v-model="form.description" auto-complete="off"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
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
          <el-breadcrumb-item>角色管理</el-breadcrumb-item>
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
          <el-col :span="8" style="text-align:left;">
            <el-button type="primary" plain icon="el-icon-edit" @click="newitem()">新建角色</el-button>
          </el-col>
          <el-col :span="4">
            <el-input
              v-model="userInputkey"
              placeholder="请输入关键字"
              @change="searchUser"
              prefix-icon="el-icon-search"
            ></el-input>
          </el-col>
          <el-col :span="8" style="text-align:left;">
            <el-button
              type="primary"
              plain
              icon="el-icon-circle-plus-outline"
              @click="onUserSelector()"
            >添加用户</el-button>
          </el-col>
        </el-row>
      </el-header>
      <el-main>
        <el-row>
          <el-col :span="12">
            <el-table :data="dataList" border :height="tableHeight" style="width: 100%">
              <!--
                    <el-table-column type="selection" width="40" @selection-change="selectChange">
                    </el-table-column>
              -->

              <el-table-column label width="60">
                <template slot-scope="scope">
                  <el-radio
                    v-model="radioSelected"
                    :label="scope.row.id"
                    @change.native="selectChange(scope.row)"
                  >&nbsp;</el-radio>
                </template>
              </el-table-column>
              <el-table-column label="序号" width="60">
                <template slot-scope="scope">
                  <span>{{(currentPage-1) * pageSize + scope.$index+1}}</span>
                </template>
              </el-table-column>
              <el-table-column label="名称" min-width="30%" sortable>
                <template slot-scope="scope">
                  <span>{{scope.row.name}}</span>
                </template>
              </el-table-column>
              <el-table-column label="说明" min-width="30%">
                <template slot-scope="scope">
                  <span>{{scope.row.description}}</span>
                </template>
              </el-table-column>
              <el-table-column label="操作" width="160">
                <template slot-scope="scope">
                  <el-button
                    :plain="true"
                    type="primary"
                    size="small"
                    icon="edit"
                    @click="edititem(scope.row)"
                  >编辑</el-button>
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
            <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="currentPage"
              :page-sizes="[10, 20, 50, 100, 200]"
              :page-size="pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="itemCount"
            ></el-pagination>
          </el-col>
          <el-col :span="12">
            <el-table :data="userList" border :height="tableHeight" style="width: 100%">
              <el-table-column type="selection" width="40" @selection-change="selectChange"></el-table-column>
              <el-table-column label="序号" width="60">
                <template slot-scope="scope">
                  <span>{{ scope.$index+1}}</span>
                </template>
              </el-table-column>
              <el-table-column label="用户名" width="150" sortable>
                <template slot-scope="scope">
                  <span>{{scope.row.name}}</span>
                </template>
              </el-table-column>
              <el-table-column label="登录名" min-width="20%" sortable>
                <template slot-scope="scope">
                  <span>{{scope.row.loginName}}</span>
                </template>
              </el-table-column>
              <el-table-column label="部门" min-width="20%">
                <template slot-scope="scope">
                  <span>{{scope.row.groupName}}</span>
                </template>
              </el-table-column>
              <el-table-column label="操作" width="160">
                <template slot-scope="scope">
                  <el-button
                    :plain="true"
                    type="danger"
                    size="small"
                    icon="delete"
                    @click="removeUser(scope.row)"
                  >移出</el-button>
                </template>
              </el-table-column>
            </el-table>
            <el-pagination
              @size-change="handleUserSizeChange"
              @current-change="handleUserCurrentChange"
              :current-page="userCurrentPage"
              :page-sizes="[10, 20, 50, 100, 200]"
              :page-size="userPageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="userCount"
            ></el-pagination>
          </el-col>
        </el-row>
      </el-main>
    </el-container>
  </div>
</template>

<script type="text/javascript">
import UserSelector from "@/components/controls/UserSelector";
export default {
  name: "RoleManager",
  permit: 9,
  components: {
    UserSelector: UserSelector
  },
  data() {
    return {
      radioSelected: "",
      dataList: [],
      dataListFull: [],
      userList: [],
      userListFull: [],
      tableHeight: window.innerHeight - 155,
      inputkey: "",
      userInputkey: "",
      loading: false,
      dialogVisible: false,
      selectUserDialogVisible: false,
      dialogtitle: "",
      currentItem: "",
      isReadOnly: false,
      pageSize: 20,
      userPageSize: 20,
      itemCount: 0,
      userCount: 0,
      selectedItemId: 0,
      currentPage: 1,
      userCurrentPage: 1,
      form: {
        id: "",
        name: "",
        description: "",
        groupType: "1",
        coding: ""
      },
      formLabelWidth: "120px",
      formLabelWidth2: "100px"
    };
  },
  created() {
    let _self = this;
    var psize = localStorage.getItem("groupPageSize");
    if (psize) {
      _self.pageSize = parseInt(psize);
    }
    var usize = localStorage.getItem("userPageSize");
    if (usize) {
      _self.userPageSize = parseInt(usize);
    }
    _self.refreshData();
  },
  methods: {
    onUserSelector() {
      if (this.selectedItemId == 0) {
        this.$message("请选择角色!");
        return;
      }
      this.selectUserDialogVisible = true;
      if (this.$refs.UserSelector) {
        this.$refs.UserSelector.refreshData();
      }
    },
    onuserselected(indata) {
      let _self = this;
      if (_self.selectedItemId) {
        var m = new Map();
        m.set("userId", indata.id);
        m.set("deptId", _self.selectedItemId);
        axios
          .post("/admin/addToGroup", JSON.stringify(m))
          .then(function(response) {
            _self.refreshUserData();
            _self.$message("添加用户成功!");
            _self.selectUserDialogVisible = false;
          })
          .catch(function(error) {
            console.log(error);
            _self.selectUserDialogVisible = false;
          });
      }
    },
    // 移出用户
    removeUser(indata) {
      let _self = this;
      var m = new Map();
      m.set("userId", indata.id);
      m.set("roleId", _self.selectedItemId);
      axios
        .post("/admin/removeUserRole", JSON.stringify(m))
        .then(function(response) {
          _self.$message("移出成功!");
          _self.refreshUserData();
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    refreshData() {
      let _self = this;
      var m = new Map();
      m.set("groupType", 2);
      m.set("id", "");
      m.set("condition", _self.inputkey);
      m.set("pageSize", _self.pageSize);
      m.set("pageIndex", _self.currentPage - 1);
      // console.log('pagesize:', _self.pageSize);
      axios
        .post("/admin/getGroups", JSON.stringify(m))
        .then(function(response) {
          _self.dataList = response.data.data;
          _self.dataListFull = response.data.data;
          _self.itemCount = response.data.total;
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    // 分页 页数改变
    handleSizeChange(val) {
      this.pageSize = val;
      localStorage.setItem("groupPageSize", val);
      this.refreshData();
      //console.log('handleSizeChange', val);
    },
    // 分页 当前页改变
    handleCurrentChange(val) {
      this.currentPage = val;
      this.refreshData();
    },
    // 表格行选择
    selectChange(val) {
      this.selectedItemId = val.id;
      this.refreshUserData();
    },
    refreshUserData() {
      let _self = this;
      var m = new Map();
      m.set("groupId", _self.selectedItemId);
      m.set("condition", _self.userInputkey);
      m.set("pageSize", _self.userPageSize);
      m.set("pageIndex", (_self.userCurrentPage - 1) * _self.userPageSize);
      //console.log('id:', _self.selectedItemId);
      axios
        .post("/admin/getRoleUsers", JSON.stringify(m))
        .then(function(response) {
          _self.userList = response.data.data;
          _self.userListFull = response.data.data;
          _self.loadUserPageInfo(response.data.pager);
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    // 分页 页数改变
    handleUserSizeChange(val) {
      this.userPageSize = val;
      localStorage.setItem("userPageSize", val);
      this.refreshUserData();
      //console.log('handleSizeChange', val);
    },
    // 分页 当前页改变
    handleUserCurrentChange(val) {
      this.userCurrentPage = val;
      this.refreshUserData();
    },
    loadUserPageInfo(pager) {
      let _self = this;
      _self.userCount = pager.total;
    },

    saveitem(indata) {
      let _self = this;
      axios
        .post("/admin/updateGroup", JSON.stringify(indata))
        .then(function(response) {
          _self.$message("保存成功!");
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    delitem(indata) {
      let _self = this;
      if (indata.loginName == "admin") {
        _self.$message("Admin不允许删除!");
        return;
      }
      axios
        .post("/admin/deleteGroup", JSON.stringify(indata))
        .then(function(response) {
          _self.$message("删除成功!");
          _self.refreshData();
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    newitem() {
      this.isReadOnly = false;
      this.dialogtitle = "添加";
      this.dialogVisible = true;
      this.form = {
        id: "",
        name: "",
        description: "",
        groupType: "2",
        coding: ""
      };
    },
    edititem(indata) {
      this.dialogtitle = "编辑";
      this.isReadOnly = true;
      this.dialogVisible = true;
      this.form = indata;
    },
    additem(indata) {
      let _self = this;
      if (_self.dialogtitle == "编辑") {
        _self.saveitem(indata);
        _self.dialogVisible = false;
      } else {
        axios
          .post("/admin/newGroup", JSON.stringify(indata))
          .then(function(response) {
            _self.dialogVisible = false;
            _self.refreshData();
          })
          .catch(function(error) {
            console.log(error);
          });
      }
    },
    moveUser() {},
    search() {
      let _self = this;
      _self.dataList = _self.dataListFull.filter(function(item) {
        return (
          item.name.match(_self.inputkey) ||
          item.description.match(_self.inputkey)
        );
      });
    },
    searchUser() {
      let _self = this;
      _self.userList = _self.userListFull.filter(function(item) {
        return (
          item.name.match(_self.userInputkey) ||
          item.loginName.match(_self.userInputkey)
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
