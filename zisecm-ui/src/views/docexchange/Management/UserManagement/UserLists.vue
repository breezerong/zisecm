<template>
  <div>
    <el-dialog :title="dialogtitle" :visible.sync="dialogVisible" width="70%">
      <el-form :model="form" label-position="right" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item
              label="用户名"
              :label-width="formLabelWidth"
              :rules="[{ required: true, message: '必填', trigger: 'blur'}]"
            >
              <el-input v-model="form.name" auto-complete="off" :disabled="isReadOnly"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="登录名"
              :label-width="formLabelWidth"
              :rules="[{ required: true, message: '必填', trigger: 'blur'}]"
            >
              <el-input v-model="form.loginName" auto-complete="off" :disabled="isReadOnly"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="密码"
              :label-width="formLabelWidth"
              :rules="[{ required: form.loginType==1, message: '必填', trigger: 'blur'}]"
            >
              <el-input
                type="password"
                v-model="form.password"
                auto-complete="off"
                :disabled="clientPermission<form.clientPermission"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="确认密码"
              :label-width="formLabelWidth"
              :rules="[{ required: (form.loginType>1) || (form.loginType==1 && form.password==form.passwordConfirm), message: '确认密码不匹配', trigger: 'blur'}]"
            >
              <el-input
                type="password"
                v-model="form.passwordConfirm"
                auto-complete="off"
                :disabled="clientPermission<form.clientPermission"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮箱" :label-width="formLabelWidth">
              <el-input
                v-model="form.email"
                auto-complete="off"
                :disabled="clientPermission<form.clientPermission"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="电话" :label-width="formLabelWidth">
              <el-input
                v-model="form.phone"
                auto-complete="off"
                :disabled="clientPermission<form.clientPermission"
              ></el-input>
            </el-form-item>
          </el-col>
          <!-- <el-col :span="12">
            <el-form-item
              label="登录类型"
              style="float:left;"
              :label-width="formLabelWidth"
              :rules="[{ required: true, message: '必填', trigger: 'blur'}]"
            >
              <el-select
                v-model="form.loginType"
                :disabled="clientPermission<form.clientPermission"
              >
                <div v-for="item in loginOptions">
                  <el-option :label="item.label" :key="item.value" :value="item.value"></el-option>
                </div>
              </el-select>
            </el-form-item>
          </el-col> -->
          <!-- <el-col :span="12">
            <el-form-item
              label="客户端权限"
              style="float:left;"
              :label-width="formLabelWidth"
              :rules="[{ required: true, message: '必填', trigger: 'blur'}]"
            >
              <el-select
                v-model="form.clientPermission"
                :disabled="clientPermission<form.clientPermission"
              >
                <div v-for="item in clientOptions">
                  <div v-if="item.value<=clientPermission">
                    <el-option :label="item.label" :key="item.value" :value="item.value"></el-option>
                  </div>
                </div>
              </el-select>
            </el-form-item>
          </el-col> -->
          <!-- <el-col :span="12" v-if="clientPermission>4">
            <el-form-item
              label="系统权限"
              style="float:left;"
              :label-width="formLabelWidth"
              :rules="[{ required: true, message: '必填', trigger: 'blur'}]"
            >
              <el-select
                v-model="form.systemPermission"
                :disabled="clientPermission<form.clientPermission"
              >
                <div v-for="item in sysOptions">
                  <el-option :label="item.label" :key="item.value" :value="item.value"></el-option>
                </div>
              </el-select>
            </el-form-item>
          </el-col> -->
          <el-col :span="12">
            <el-form-item label="是否启用" style="float:left;" :label-width="formLabelWidth">
              <el-select
                v-model="form.isActived"
                :disabled="clientPermission<form.clientPermission"
              >
                <el-option label="否" :value="false"></el-option>
                <el-option label="是" :value="true"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="公司名称" :label-width="formLabelWidth">
              <el-input
                :autosize="true"
                v-model="form.companyName"
                :disabled="clientPermission<form.clientPermission"
                readonly
              ></el-input>
            </el-form-item>
          </el-col>
          <!-- <el-col :span="12">
            <el-form-item label="默认组" :label-width="formLabelWidth">
              <el-input
                :autosize="true"
                v-model="form.groupName"
                :disabled="clientPermission<form.clientPermission"
              ></el-input>
            </el-form-item>
          </el-col>
           <el-col :span="8">
            <el-form-item label="代理用户" :label-width="formLabelWidth">
              <el-input
                :autosize="true"
                v-model="form.delegateUser"
              ></el-input>
            </el-form-item>
          </el-col>
           <el-col :span="8">
            <el-form-item label="代理开始日期" :label-width="formLabelWidth">
              <el-date-picker v-model="form.delegateStart" type="date" placeholder="选择日期" style="display:block;" value-format="yyyy-MM-dd HH:mm:ss" ></el-date-picker>
            </el-form-item>
          </el-col>
           <el-col :span="8">
            <el-form-item label="代理结束日期" :label-width="formLabelWidth">
              <el-date-picker v-model="form.delegateEnd" type="date" placeholder="选择日期" style="display:block;" value-format="yyyy-MM-dd HH:mm:ss" ></el-date-picker>
            </el-form-item>
          </el-col> -->
          <el-col :span="24">
            <el-form-item label="说明" :label-width="formLabelWidth">
              <el-input
                v-model="form.description"
                auto-complete="off"
                :disabled="clientPermission<form.clientPermission"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24" v-if="!isReadOnly">
            <el-form-item label="签名图片" :label-width="formLabelWidth">
              <el-upload
                :limit="1"
                :file-list="fileList"
                action
                :on-change="handleChange"
                :auto-upload="false"
                :multiple="false"
              >
                <el-button slot="trigger" size="small" type="primary">{{$t('application.selectFile')}}</el-button>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div
        slot="footer"
        class="dialog-footer"
        v-if="clientPermission>=form.clientPermission || !isReadOnly"
      >
        <el-button @click="dialogVisible = false">{{$t('application.cancel')}}</el-button>
        <el-button type="primary" @click="additem(form)">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="更新签名图片" :visible.sync="sdialogVisible">
      <el-form :model="form" label-position="right" label-width="100px">
        <el-col :span="24">
          <el-form-item label="签名图片" :label-width="formLabelWidth">
            <el-upload
              :limit="1"
              :file-list="fileList"
              action
              :on-change="handleChange"
              :auto-upload="false"
              :multiple="false"
            >
              <el-button slot="trigger" size="small" type="primary">{{$t('application.selectFile')}}</el-button>
            </el-upload>
          </el-form-item>
        </el-col>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="sdialogVisible = false">{{$t('application.cancel')}}</el-button>
        <el-button type="primary" @click="updateSignImage()">确 定</el-button>
      </div>
    </el-dialog>
    <el-container>
      <el-header>
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
            <el-button type="primary" plain="true" icon="el-icon-edit" @click="newitem()">{{$t('application.new')}}</el-button>
          </el-col>
        </el-row>
      </el-header>
      <el-main>
        <el-table :data="dataList" border :height="tableHeight" style="width: 100%">
          <el-table-column type="selection" width="40" @selection-change="selectChange"></el-table-column>
          <el-table-column label="序号" width="60">
            <template slot-scope="scope">
              <span>{{(currentPage-1) * pageSize + scope.$index+1}}</span>
            </template>
          </el-table-column>
          <el-table-column label="用户名" width="150">
            <template slot-scope="scope">
              <span>{{scope.row.name}}</span>
            </template>
          </el-table-column>
          <el-table-column label="登录名" width="120">
            <template slot-scope="scope">
              <span>{{scope.row.loginName}}</span>
            </template>
          </el-table-column>
          <el-table-column label="邮件" min-width="15%">
            <template slot-scope="scope">
              <span>{{scope.row.email}}</span>
            </template>
          </el-table-column>
          <el-table-column label="电话" min-width="10%">
            <template slot-scope="scope">
              <span>{{scope.row.phone}}</span>
            </template>
          </el-table-column>
          <el-table-column label="说明" min-width="15%">
            <template slot-scope="scope">
              <span>{{scope.row.description}}</span>
            </template>
          </el-table-column>
          <!--
            <el-table-column label="客户端权限" width="90">
              <template slot-scope="scope">
                <span>{{scope.row.clientPermission}}</span>
              </template>
            </el-table-column>
          -->
          <el-table-column label="是否启用" width="90">
            <template slot-scope="scope">
              <span>{{scope.row.isActived?"是":"否"}}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="240">
            <template slot-scope="scope">
                <template v-if="isShow(scope.row)">
                    <el-button
                    type="primary"
                    plain
                    icon="el-icon-info"
                    title="属性"
                    @click="edititem(scope.row)"
                    ></el-button>
                    <el-button
                        type="primary"
                        plain
                        icon="el-icon-refresh"
                        title="更新签名"
                        @click="addimage(scope.row)"
                    ></el-button>
                    <el-button
                        type="danger"
                        plain
                        icon="el-icon-delete"
                        :title="$t('application.delete')"
                        @click="delitem(scope.row)"
                    ></el-button>
                </template>
              
              
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
      </el-main>
    </el-container>
  </div>
</template>
<script type="text/javascript">
export default {
  name: "UserManager",
  permit: 9,
  data() {
     
    return {
      dataList: [],
      dataListFull: [],
      fileList: [],
      file: "",
      tableHeight: window.innerHeight - 190,
      inputkey: "",
      loading: false,
      dialogVisible: false,
      sdialogVisible: false,
      dialogtitle: "",
      currentItem: "",
      isReadOnly: false,
      pageSize: 20,
      itemCount: 0,
      selectedItemId: 0,
      selectedItems: [],
      currentPage: 1,
      clientPermission: 1,
      systemPermission: 0,
      token: "",
      form: {
        id: "",
        name: "",
        description: "",
        userLognName: "",
        email: "",
        phone: "",
        groupName: "",
        companyName: "",
        password: "",
        passwordConfirm: "",
        delegateUser: "",
        delegateStart: null,
        delegateEnd: null
      },
      sysOptions: [
        { label: "无", value: 0 },
        { label: "参数配置", value: 1 },
        { label: "用户角色配置", value: 2 },
        { label: "流程配置", value: 3 },
        { label: "界面配置", value: 4 },
        { label: "系统配置", value: 5 },
        { label: "超级用户", value: 9 }
      ],
      loginOptions: [
        { label: "系统密码", value: 1 },
        { label: "AD域", value: 2 },
        { label: "接口", value: 3 }
      ],
      clientOptions: [
        { label: "无", value: 0 },
        { label: "查询用户", value: 1 },
        { label: "新建文档", value: 2 },
        { label: "执行流程", value: 3 },
        { label: "文件管理员", value: 4 },
        { label: "系统管理员", value: 5 }
      ],
      formLabelWidth: "120px",
      formLabelWidth2: "100px"
    };
  },
  mounted() {
    let _self = this;
    var psize = localStorage.getItem("userPageSize");
    if (psize) {
      _self.pageSize = parseInt(psize);
    }
    if (this.currentUser()) {
        this.clientPermission = Number(
          this.currentUser().clientPermission
        );
        this.systemPermission = Number(
          this.currentUser().systemPermission
        );
      }
      
    _self.refreshData();
  },
  methods: {
      isShow(row){
          return row.companyName==this.currentUser().company;
      },
    handleChange(file, fileList) {
      this.file = file;
    },
    refreshData() {
      let _self = this;
      var m = new Map();
      var cond = " (COMPANY_NAME ='"+this.currentUser().company+"' or COMPANY_NAME='"+_self.ownerCompany+"')";
      if (_self.inputkey && _self.inputkey.length > 0) {
        cond +=" and NAME like '%" +
          _self.inputkey +
          "%' or LOGIN_NAME like '%" +
          _self.inputkey +
          "%'";
      }
      m.set("condition", cond);
      m.set("pageSize", _self.pageSize);
      m.set("pageIndex", (_self.currentPage - 1) * _self.pageSize);
      // console.log('pagesize:', _self.pageSize);
      axios
        .post("/admin/getUsers", JSON.stringify(m))
        .then(function(response) {
          _self.dataList = response.data.data;
          _self.dataListFull = response.data.data;
          _self.itemCount = response.data.pager.total;
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    imageFormat(row, column) {
      let id = row.id;
      let _self = this;
      axios
        .post("/admin/getUserImage", JSON.stringify(id))
        .then(function(response) {
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    // 表格行选择
    selectChange(val) {
      this.selectedItems = val;
    },
    // 分页 页数改变
    handleSizeChange(val) {
      this.pageSize = val;
      localStorage.setItem("userPageSize", val);
      this.refreshData();
      //console.log('handleSizeChange', val);
    },
    // 分页 当前页改变
    handleCurrentChange(val) {
      this.currentPage = val;
      this.refreshData();
    },
    saveitem(indata) {
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
    addimage(indata) {
      this.currentItem = indata;
      this.fileList = [];
      this.sdialogVisible = true;
    },
    delitem(indata) {
      let _self = this;
      if (indata.loginName == "admin") {
        _self.$message("Admin不允许删除!");
        return;
      }
      axios
        .post("/admin/deleteUser", JSON.stringify(indata))
        .then(function(response) {
          if (response.data.code == 0) {
            _self.$message("删除失败!\r\n" + response.data.message);
          } else {
            _self.$message("删除成功!");
            _self.refreshData();
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    newitem() {
      this.isReadOnly = false;
      this.dialogtitle = "添加用户";
      this.form = {
        id: "",
        name: "",
        description: "",
        userLognName: "",
        loginType: 1,
        email: "",
        phone: "",
        systemPermissioin: "0",
        clientPermissioin: "1",
        groupName: "",
        companyName: this.currentUser().company,
        isActived: true
      };
      this.fileList = [];
      this.dialogVisible = true;
    },
    edititem(indata) {
      this.dialogtitle = "编辑用户";
      this.isReadOnly = true;
      indata.passwordConfirm = '';
      this.form = indata;
      this.form.passwordConfirm = indata.password + "";
      //this.form.passwordConfirm = this.form.password+"";
      this.dialogVisible = true;
      this.fileList = [];
      console.log(indata);
    },
    additem(indata) {
      let _self = this;
      if (indata.password != indata.passwordConfirm) {
        _self.$message("密码跟确认密码不同!");
        return;
      }
      if (_self.dialogtitle == "编辑用户") {
        _self.saveitem(indata);
        _self.dialogVisible = false;
      } else {
        let formdata = new FormData();
        formdata.append("user", JSON.stringify(indata));
        if (_self.file != "") {
          //console.log(_self.file);
          formdata.append("uploadFile", _self.file.raw);
        }
        axios
          .post("/admin/newUser", formdata, {
            "Content-Type": "multipart/form-data"
          })
          .then(function(response) {
            _self.dialogVisible = false;
            _self.refreshData();
            _self.$message("添加成功!");
          })
          .catch(function(error) {
            console.log(error);
          });
      }
    },
    updateSignImage() {
      let _self = this;
      let formdata = new FormData();
      formdata.append("id", JSON.stringify(_self.currentItem.id));
      if (_self.file != "") {
        //console.log(_self.file);
        formdata.append("uploadFile", _self.file.raw);
      }

      axios
        .post("/admin/updateSignImage", formdata, {
          "Content-Type": "multipart/form-data"
        })
        .then(function(response) {
          _self.sdialogVisible = false;
          _self.$message("更新成功!");
          _self.refreshData();
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    search() {
      this.refreshData();
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
