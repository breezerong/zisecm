<template>
  <div>
    <el-dialog title="添加菜单项" :visible.sync="dialogVisible" width="60%">
      <el-row>
        <el-form :model="form">
          <el-col :span="12">
            <el-form-item label="Id" :label-width="formLabelWidth">
              <el-input v-model="form.id" auto-complete="off" :disabled="isEdit"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="父Id" :label-width="formLabelWidth">
              <el-input v-model="form.parentId" auto-complete="off"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="名称" :label-width="formLabelWidth">
              <el-input v-model="form.name" auto-complete="off"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="图标" :label-width="formLabelWidth">
              <el-input v-model="form.icon" auto-complete="off"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="标签" :label-width="formLabelWidth">
              <LangSelector
                v-model="form.label"
                v-bind:inputValue="form.label"
              ></LangSelector>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="菜单名" :label-width="formLabelWidth">
              <el-input v-model="form.menuName" auto-complete="off"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="角色" :label-width="formLabelWidth">
              <RoleSelectInput v-model="form.roleName" v-bind:inputValue="form.roleName" v-bind:isRepeat="true" ></RoleSelectInput>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="组件名称" :label-width="formLabelWidth">
              <!--
              <el-input v-model="form.componentName" auto-complete="off"></el-input>
              -->
              <ComponentSelector
                v-model="form.componentName"
                v-bind:inputValue="form.componentName"
              ></ComponentSelector>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="调用函数" :label-width="formLabelWidth">
              <el-input v-model="form.callFunction" auto-complete="off"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="排序" :label-width="formLabelWidth">
              <el-input v-model="form.orderIndex" auto-complete="off"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="说明" :label-width="formLabelWidth">
              <el-input v-model="form.description" auto-complete="off"></el-input>
            </el-form-item>
          </el-col>
        </el-form>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">{{$t('application.cancel')}}</el-button>
        <el-button type="primary" @click="additem(form)">确 定</el-button>
      </div>
    </el-dialog>
    <el-row>
      <!-- <el-breadcrumb class="navbar">
        <el-breadcrumb-item>{{$t('menu.systemManager')}}</el-breadcrumb-item>
        <el-breadcrumb-item>菜单项管理</el-breadcrumb-item>
        <el-breadcrumb-item>{{menuName}}</el-breadcrumb-item>
      </el-breadcrumb> -->
    </el-row>
    <el-row class="topbar">
      <el-col :span="4">
        <el-input
          v-model="inputkey"
          placeholder="请输入关键字"
          @change="search"
          prefix-icon="el-icon-search"
        ></el-input>
      </el-col>
      <el-col :span="3">
        &nbsp;
        <el-button type="primary" plain icon="el-icon-edit"  @click="newItem()">{{$t('application.new')}}</el-button>
      </el-col>
    </el-row>
    <el-table :data="dataList" border :height="tableHeight" v-loading="loading" style="width: 100%">
      <el-table-column prop="id" label="Id" width="180" sortable></el-table-column>
      <el-table-column label="父ID" prop="parentId" width="180"></el-table-column>
      <el-table-column label="名称" prop="name" sortable min-width="20%" ></el-table-column>
      <el-table-column label="角色" prop="roleName" min-width="20%"></el-table-column>
      <el-table-column label="标签" prop="label" sortable min-width="20%"></el-table-column>
      <el-table-column label="组件名" prop="componentName" sortable min-width="20%"></el-table-column>
      <el-table-column label="排序" prop="orderIndex" sortable width="90"></el-table-column>
      <el-table-column label="操作" width="160">
        <template slot-scope="scope">
          <el-button
            :plain="true"
            type="primary"
            size="small"
            icon="edit"
            @click="editItem(scope.row)"
          >编辑</el-button>
          <el-button
            :plain="true"
            type="danger"
            size="small"
            icon="delete"
            @click="del(scope.row)"
          >{{$t('application.delete')}}</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script type="text/javascript">
// $.ajaxSetup({
//   contentType: "application/json"
// });
import ComponentSelector from "@/components/controls/ComponentSelector";
import LangSelector from "@/components/controls/LangSelector";
import RoleSelectInput from '@/components/controls/RoleSelectInput'

export default {
  name: "MenuItemManager",
  permit: 9,
  components: {
    ComponentSelector: ComponentSelector,
    LangSelector: LangSelector,
    RoleSelectInput: RoleSelectInput
  },
  data() {
    return {
      dataList: [],
      dataListFull: [],
      menuName: "",
      inputkey: "",
      loading: false,
      dialogVisible: false,
      isEdit: false,
      tableHeight: window.innerHeight - 135,
      form: {
        id:"",
        name: "",
        icon:"",
        parentId:"",
        label:"",
        description: "",
        menuName: "",
        orderIndex: 1,
        roleName: "",
        componentName: "",
        callFunction:""
      },
      formLabelWidth: "120px"
    };
  },
  created() {
    let _self = this;
    _self.menuName = _self.$route.query.name;
    _self.refreshData();
  },
  methods: {
    refreshData() {
      let _self = this;
      _self.loading = true;
      axios.post("/admin/getMenuItem",_self.menuName)
        .then(function(response) {
          _self.dataListFull = response.data.data;
          _self.dataList = response.data.data;
          console.log(JSON.stringify(_self.dataList));
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    newItem() {
      this.isEdit = false;
      this.form.label = "";
      this.form.name = "";
      this.form.description = "";
      this.form.icon = "";
      this.form.componentName = "";
      this.form.menuName = this.menuName;
      this.dialogVisible = true;
    },
    save(indata) {
      let _self = this;
      axios.post("/admin/updateMenuItem",JSON.stringify(indata))
        .then(function(response) {
          _self.dialogVisible = false;
          _self.isEdit = false;
          _self.$message("保存成功!");
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    editItem(indata) {
      this.isEdit = true;
      this.form = indata;
      this.dialogVisible = true;
    },
    del(indata) {
      let _self = this;
      axios.post("/admin/deleteMenuItem",JSON.stringify(indata))
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
      if (_self.isEdit) {
        _self.save(indata);

        return;
      }
      axios.post("/admin/newMenuItem",JSON.stringify(indata))
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
          item.componentName.match(_self.inputkey) ||
          item.label.match(_self.inputkey)
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
</style>
