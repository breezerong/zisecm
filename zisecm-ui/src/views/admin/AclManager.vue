<template>
  <div>
    <el-dialog
      :visible.sync="permissionVisible"
      :append-to-body="true"
      :title="$t('application.viewPermission')"
      @close="permissionVisible = false"
      width="70%"
    >
      <AclForm  ref="PermissionDialog" v-bind:id="currentId"></AclForm>
      <div slot="footer" class="dialog-footer">
        <el-button @click="permissionVisible = false">关闭</el-button>
      </div>
    </el-dialog>
    <el-dialog title="添加" :visible.sync="dialogVisible">
      <el-form :model="form">
        <el-form-item label="名称" :label-width="formLabelWidth">
          <el-input v-model="form.name" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="说明" :label-width="formLabelWidth">
          <el-input v-model="form.description" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="类型名" :label-width="formLabelWidth">
          <el-input v-model="form.typeName" auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveItem(form)">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="复制Acl" :visible.sync="dialogCopyVisible">
      <el-form :model="formCopy">
        <el-form-item label="名称" :label-width="formLabelWidth">
          <el-input v-model="formCopy.name" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="说明" :label-width="formLabelWidth">
          <el-input v-model="formCopy.description" auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogCopyVisible = false">取 消</el-button>
        <el-button type="primary" @click="copyItem(formCopy)">确 定</el-button>
      </div>
    </el-dialog>
    <!-- <div class="navbar">
      <el-breadcrumb>
        <el-breadcrumb-item>{{$t('menu.systemManager')}}</el-breadcrumb-item>
        <el-breadcrumb-item>ACL管理</el-breadcrumb-item>
      </el-breadcrumb>
    </div> -->
    <el-row>
      <el-col :span="6">
        <el-input
          v-model="inputKey"
          placeholder="请输入关键字"
          @change="refreshData"
          prefix-icon="el-icon-search"
        ></el-input>
      </el-col>
      <el-col :span="18" style="text-align:left;">
        &nbsp; 
        <el-button type="primary" icon="el-icon-edit" plain @click="newItem()">新建</el-button>
      </el-col>
    </el-row>
    <el-table
      :data="dataList"
      border
      :height="tableHeight"
      v-loading="loading"
      size="mini"
      style="width: 100%"
    >
      <el-table-column label="行号" type="index" width="60"></el-table-column>
      <el-table-column label="名称" prop="name" min-width="20%"></el-table-column>
      <el-table-column label="说明" prop="description" min-width="30%"></el-table-column>
      <el-table-column label="创建人" prop="creator" width="140"></el-table-column>
      <el-table-column label="创建时间" prop="creationDate" :formatter="dateFormatter" width="160"></el-table-column>
      <el-table-column label="操作" width="320">
        <template slot-scope="scope">
          <el-button
            :plain="true"
            type="primary"
            size="small"
            icon="edit"
            @click="showItem(scope.row)"
          >属性</el-button>
          <el-button
            :plain="true"
            type="primary"
            size="small"
            icon="edit"
            @click="showPermission(scope.row)"
          >权限</el-button>
          <el-button
            :plain="true"
            type="warning"
            size="small"
            icon="edit"
            @click="showCopy(scope.row)"
          >复制</el-button>
          <el-button
            :plain="true"
            type="danger"
            size="small"
            icon="delete"
            @click="delItem(scope.row)"
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
  </div>
</template>

<script type="text/javascript">
import AclForm from '@/components/controls/AclForm'

export default {
  name: "AclManager",
  permit: 3,
  components:{
    AclForm: AclForm
  },
  data() {
    return {
      dataList: [],
      dataListFull: [],
      inputKey: "",
      loading: false,
      pageSize: 20,
      currentId:"",
      currentPage: 1,
      itemCount: 0,
      dialogVisible: false,
      permissionVisible: false,
      dialogCopyVisible: false,
      tableHeight: window.innerHeight - 150,
      isEdit: false,
      formCopy:{
        id:"",
        name:""
      },
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
  mounted() {
    let _self = this;
    var psize = localStorage.getItem("aclPageSize");
    if (psize) {
      _self.pageSize = parseInt(psize);
    }
    _self.refreshData();
  },
  methods: {
    // 分页 页数改变
    handleSizeChange(val) {
      this.pageSize = val;
      localStorage.setItem("aclPageSize", val);
      this.refreshData();
    },
    // 分页 当前页改变
    handleCurrentChange(val) {
      this.currentPage = val;
      this.refreshData();
    },
    showPermission(inData){
      this.permissionVisible = true;
      this.currentId = inData.id;
      if(this.$refs.PermissionDialog){
        this.$refs.PermissionDialog.id = inData.id ;
        this.$refs.PermissionDialog.loadAcl();
      }
    },
    refreshData() {
      let _self = this;
      var m = new Map();
      if(this.inputKey.length>0){
        m.set("name", "%"+this.inputKey+"%");
      }else{
        m.set("name", this.inputKey);
      }
      m.set("pageIndex", _self.currentPage-1);
      m.set("pageSize", _self.pageSize);
      _self.loading = true;
      axios.post("/acl/getAcls",JSON.stringify(m))
        .then(function(response) {
          _self.dataListFull = response.data.data;
          _self.dataList = response.data.data;
          _self.itemCount = response.data.pager.total;
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    dateFormatter(row, column) {
      //console.log(column);
      let datetime = row[column.property];
      return this.datetimeFormat(datetime);
    },
    saveItem(indata) {
      if(!this.isEdit){
        this.addItem(indata);
        this.dialogVisible = false;
        return;
      }
      let _self = this;
      axios.post("/acl/updateAcl",SON.stringify(indata))
        .then(function(response) {
          _self.dialogVisible = false;
          _self.$message("保存成功!");
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    newItem(){
      this.isEdit = false;
      this.dialogVisible = true;
    },
    showCopy(indata){
      this.formCopy = indata;
      this.dialogCopyVisible = true;
    },
    showItem(indata){
      this.isEdit = true;
      this.form = indata;
      this.dialogVisible = true;
    },
    delItem(indata) {
      let _self = this;
      axios.post("/acl/deleteAcl",JSON.stringify(indata))
        .then(function(response) {
          _self.$message("删除成功!");
          _self.refreshData();
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    addItem(indata) {
      let _self = this;
      axios.post("/acl/newAcl",JSON.stringify(indata))
        .then(function(response) {
          _self.dialogVisible = false;
          _self.refreshData();
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    copyItem(indata) {
      let _self = this;
      axios.post("/acl/copyAcl",indata)
        .then(function(response) {
          _self.$message("复制成功!");
          _self.dialogCopyVisible =false;
          _self.refreshData();
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    searchform() {
      let _self = this;

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
