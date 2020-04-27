<template>
  <div>
    <el-dialog title="选择用户" :visible.sync="selectUserDialogVisible" width="820px">
      <UserSelector ref="UserSelector" v-on:onuserselected="onuserselected" :noGroup="1"></UserSelector>
      <div slot="footer" class="dialog-footer">
        <el-button @click="selectUserDialogVisible = false">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog title="选择部门" :visible.sync="deptdialogVisible" width="360px">
      <DepartmentSelector
        ref="DepartmentSelector"
        v-on:departmentSelected="departmentSelected"
        width="360px"
      ></DepartmentSelector>
      <div slot="footer" class="dialog-footer">
        <el-button @click="deptdialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="deptSelect()">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog :title.sync="userdialogtitle" :visible.sync="userdialogVisible">
      <el-form :model="userform">
        <el-row>
          <el-col :span="12">
            <el-form-item
              label="用户名"
              :label-width="formLabelWidth"
              :rules="[{ required: true, message: '必填', trigger: 'blur'}]"
            >
              <el-input v-model="userform.name" auto-complete="off" :disabled="isReadOnly"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="登录名"
              :label-width="formLabelWidth"
              :rules="[{ required: true, message: '必填', trigger: 'blur'}]"
            >
              <el-input v-model="userform.loginName" auto-complete="off" :disabled="isReadOnly"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item
              label="密码"
              :label-width="formLabelWidth"
              :rules="[{ required: true, message: '必填', trigger: 'blur'}]"
            >
              <el-input v-model="userform.password" auto-complete="off"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮箱" :label-width="formLabelWidth">
              <el-input v-model="userform.email" auto-complete="off"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="电话" :label-width="formLabelWidth">
              <el-input v-model="userform.phone" auto-complete="off"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="权限"
              :label-width="formLabelWidth"
              :rules="[{ required: true, message: '必填', trigger: 'blur'}]"
            >
              <el-select v-model="userform.userPermit">
                <el-option label="普通用户" value="1"></el-option>
                <el-option label="业务管理员" value="2"></el-option>
                <el-option label="系统管理员" value="9"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="是否启用" :label-width="formLabelWidth2">
              <el-select v-model="userform.isActived">
                <el-option label="否" value="false"></el-option>
                <el-option label="是" value="true"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="默认组" :label-width="formLabelWidth2">
              <el-input :autosize="true" v-model="userform.groupName"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="24">
            <el-form-item label="说明" :label-width="formLabelWidth">
              <el-input v-model="userform.description" auto-complete="off"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="userdialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addUser(userform)">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog :title.sync="dialogtitle" :visible.sync="dialogVisible">
      <el-form :model="form">
        <el-row>
          <el-col :span="12">
            <el-form-item
              label="名称"
              :label-width="formLabelWidth"
              :rules="[{ required: true, message: '必填', trigger: 'blur'}]"
            >
              <el-input v-model="form.name" auto-complete="off" :disabled="isReadOnly"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="代码" :label-width="formLabelWidth">
              <el-input v-model="form.coding" auto-complete="off"></el-input>
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
          <el-breadcrumb-item>部门管理</el-breadcrumb-item>
        </el-breadcrumb> -->
        <el-row class="topbar">
          <el-col :span="4" style="text-align:left;">
            <el-tooltip class="item" effect="dark" content="新增部门" placement="top">
              <el-button type="primary" icon="el-icon-edit" circle @click="newitem()"></el-button>
            </el-tooltip>
            <el-tooltip class="item" effect="dark" content="编辑部门" placement="top">
              <el-button type="primary" icon="el-icon-info" circle @click="edititem(currentData)"></el-button>
            </el-tooltip>
            <el-tooltip class="item" effect="dark" content="删除部门" placement="top">
              <el-button
                type="primary"
                icon="el-icon-delete"
                circle
                @click="onDeleleItem(currentData)"
              ></el-button>
            </el-tooltip>
          </el-col>
          <el-col :span="4">
            <el-input
              v-model="inputkey"
              placeholder="请输入属性名关键字"
              @change="search"
              prefix-icon="el-icon-search"
            ></el-input>
          </el-col>
          <el-col :span="16" style="text-align:left;">
            &nbsp; 
            <el-button type="primary" plain icon="el-icon-edit" @click="newUser()">创建用户</el-button>
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
          <el-col :span="4">
            <el-tree
              :props="defaultProps"
              :data="deptList"
              node-key="id"
              lazy
              @node-click="handleNodeClick"
            ></el-tree>
          </el-col>
          <el-col :span="20">
            <el-table :data="dataList" border :height="tableHeight" style="width: 100%">
              <el-table-column type="selection" width="40" @selection-change="selectChange"></el-table-column>
              <el-table-column label="序号" width="60">
                <template slot-scope="scope">
                  <span>{{ scope.$index+1}}</span>
                </template>
              </el-table-column>
              <el-table-column label="用户名" width="140">
                <template slot-scope="scope">
                  <span>{{scope.row.name}}</span>
                </template>
              </el-table-column>
              <el-table-column label="登录名" width="140">
                <template slot-scope="scope">
                  <span>{{scope.row.loginName}}</span>
                </template>
              </el-table-column>
              <el-table-column label="邮件" min-width="20%">
                <template slot-scope="scope">
                  <span>{{scope.row.email}}</span>
                </template>
              </el-table-column>
              <el-table-column label="启用" width="60">
                <template slot-scope="scope">
                  <span>{{scope.row.isActived}}</span>
                </template>
              </el-table-column>
              <el-table-column label="操作" width="240">
                <template slot-scope="scope">
                  <el-button
                    :plain="true"
                    type="primary"
                    size="small"
                    icon="edit"
                    @click="editUser(scope.row)"
                  >编辑</el-button>
                  <el-button
                    :plain="true"
                    type="primary"
                    size="small"
                    icon="edit"
                    @click="moveUser(scope.row)"
                  >移到</el-button>
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
          </el-col>
        </el-row>
      </el-main>
    </el-container>
  </div>
</template>

<script type="text/javascript">
import DepartmentSelector from "@/components/controls/DepartmentSelector";
import UserSelector from "@/components/controls/UserSelector";
export default {
  name: "GroupManager",
  components: {
    DepartmentSelector: DepartmentSelector,
    UserSelector: UserSelector
  },
  permit: 9,
  data() {
    return {
      dataList: [],
      dataListFull: [],
      tableHeight: window.innerHeight - 125,
      inputkey: "",
      loading: false,
      dialogVisible: false,
      deptdialogVisible: false,
      selectUserDialogVisible: false,
      dialogtitle: "",
      isReadOnly: false,
      itemCount: 0,
      selectedItemId: -1,
      selectedItem: [],
      currentPage: 1,
      deptList: [],
      moveitem: "",
      currentData: "",
      selectDeptId: 0,
      form: {
        id: "",
        parentId: 0,
        name: "",
        description: "",
        groupType: "1",
        coding: ""
      },
      formLabelWidth: "120px",
      formLabelWidth2: "100px",
      defaultProps: {
        children: "children",
        label: "name"
      },
      isReadOnly: false,
      userdialogtitle: "",
      userdialogVisible: false,
      userform: {
        id: "",
        name: "",
        description: "",
        userLognName: "",
        email: "",
        phone: "",
        userPermit: "1",
        groupName: "",
        isActived: "true"
      }
    };
  },
  mounted() {
    let _self = this;
    _self.bindDepartment();
  },
  methods: {
    onuserselected(indata) {
      let _self = this;
      if (_self.selectedItemId) {
        var m = new Map();
        m.set("userId", indata.id);
        m.set("deptId", _self.selectedItemId);
        axios
          .post("/admin/addToGroup", JSON.stringify(m))
          .then(function(response) {
            _self.bindUserData();
            _self.$message("添加用户成功!");
            _self.selectUserDialogVisible = false;
          })
          .catch(function(error) {
            console.log(error);
            _self.selectUserDialogVisible = false;
          });
      }
    },
    onUserSelector() {
      this.selectUserDialogVisible = true;
      if (this.$refs.UserSelector) {
        this.$refs.UserSelector.refreshData();
      }
    },
    moveUser(indata) {
      this.selectDeptId = 0;
      this.moveitem = indata;
      this.deptdialogVisible = true;
    },
    departmentSelected(indata) {
      this.selectDeptId = indata;
      //console.log("this.selectDeptId:"+this.selectDeptId);
    },
    deptSelect() {
      let _self = this;
      //console.log("this.selectDeptId2:"+this.selectDeptId);
      if (_self.selectDeptId > 0) {
        _self.moveitem.groupId = _self.selectDeptId;
        axios
          .post("/admin/moveUserDept", JSON.stringify(_self.moveitem))
          .then(function(response) {
            _self.bindUserData();
            _self.$message("移动用户成功!");
            _self.deptdialogVisible = false;
          })
          .catch(function(error) {
            console.log(error);
            _self.deptdialogVisible = false;
          });
      }
    },
    // 绑定用户
    bindUserData() {
      let _self = this;
      var m = new Map();
      m.set("deptId", _self.selectedItemId);
      axios
        .post("/admin/getGroupUsers", JSON.stringify(m))
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
    // 部门点击事件
    handleNodeClick(indata) {
      let _self = this;
      _self.currentData = indata;
      _self.selectedItemId = indata.id;
      var m = new Map();
      m.set("id", indata.id);
      m.set("groupType", 1);
      if (indata.extended == false) {
        _self.loading = true;
        axios
          .post("/admin/getGroups", JSON.stringify(m))
          .then(function(response) {
            // _self.$message("获取子节点成功!");
            indata.children = response.data.data;
            indata.extended = true;
            //console.log(JSON.stringify(indata));
            _self.loading = false;
          })
          .catch(function(error) {
            console.log(error);
            _self.loading = false;
          });
      }
      _self.bindUserData();
    },
    // 绑定部门
    bindDepartment() {
      let _self = this;
      _self.loading = true;
      var m = new Map();
      m.set("id", 0);
      m.set("groupType", 1);
      axios
        .post("/admin/getGroups", JSON.stringify(m))
        .then(function(response) {
          _self.deptList = response.data.data;
          //_self.handleNodeClick(_self.deptList[0]);
          //console.log(JSON.stringify(_self.deptList));
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    // 表格行选择
    selectChange(val) {
      this.currentData = val;
      this.selectedItemId = val.id;
    },
    // 保存部门
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
    // 删除部门事件
    onDeleleItem(indata) {
      let _self = this;
      this.$confirm("此操作将永久删除该部门, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          _self.delitem(indata);
        })
        .catch(() => {});
    },
    // 删除部
    delitem(indata) {
      let _self = this;
      axios
        .post("/admin/deleteGroup", JSON.stringify(indata))
        .then(function(response) {
          _self.$message("删除成功!");
          _self.bindDepartment();
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    // 新增部门
    newitem() {
      this.isReadOnly = false;
      this.dialogtitle = "添加";
      this.dialogVisible = true;
      this.form = {
        id: "",
        name: "",
        parentId: this.selectedItemId,
        description: "",
        groupType: "1",
        coding: ""
      };
    },
    // 编辑部门
    edititem(indata) {
      this.dialogtitle = "编辑";
      this.isReadOnly = true;
      this.dialogVisible = true;
      this.form = indata;
    },
    // 添加部门
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
            _self.bindDepartment();
          })
          .catch(function(error) {
            console.log(error);
          });
      }
    },
    // 保存用户
    saveUser(indata) {
      let _self = this;
      axios
        .post("/admin/updateUser", JSON.stringify(indata))
        .then(function(response) {
          _self.$message("保存成功!");
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    // 移出用户
    removeUser(indata) {
      let _self = this;
      axios
        .post("/admin/removeUserGroup", JSON.stringify(indata))
        .then(function(response) {
          _self.$message("移出成功!");
          _self.bindUserData();
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    // 新用户
    newUser() {
      this.isReadOnly = false;
      this.userdialogtitle = "添加";
      this.userdialogVisible = true;
      this.userform = {
        id: "",
        name: "",
        description: "",
        userLognName: "",
        email: "",
        phone: "",
        userPermit: "1",
        groupName: this.currentData.name,
        isActived: "true",
        groupId: this.selectedItemId
      };
    },
    // 编辑用户
    editUser(indata) {
      this.userdialogtitle = "编辑";
      this.isReadOnly = true;
      this.userdialogVisible = true;
      this.userform = indata;
    },
    // 添加用户
    addUser(indata) {
      let _self = this;
      if (_self.userdialogtitle == "编辑") {
        _self.saveUser(indata);
        _self.userdialogVisible = false;
      } else {
        axios
          .post("/admin/newUser", JSON.stringify(indata))
          .then(function(response) {
            _self.userdialogVisible = false;
            _self.bindUserData();
          })
          .catch(function(error) {
            console.log(error);
          });
      }
    },
    search() {
      let _self = this;
      _self.dataList = _self.dataListFull.filter(function(item) {
        return (
          item.name.match(_self.inputkey) ||
          item.loginName.match(_self.inputkey)
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
