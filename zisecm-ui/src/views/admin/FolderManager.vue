<template>
  <div>
        <el-dialog title="复制" :visible.sync="copyDialogVisible">
          <el-form :model="copyForm">
            <el-form-item label="ID" :label-width="formLabelWidth">
              {{copyForm.sourceId}}
            </el-form-item>
          
            <el-col :span="24">
            <el-form-item label="目标Id" :label-width="formLabelWidth">
                <el-input v-model="copyForm.targetId" auto-complete="off"></el-input>
            </el-form-item>
            </el-col>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="copyDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="copyFolder(copyForm)">保存</el-button>
          </div>
        </el-dialog>
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
            <el-col :span="24">
            <el-form-item label="父Id" :label-width="formLabelWidth">
                <el-input v-model="form.parentId" auto-complete="off"></el-input>
            </el-form-item>
            </el-col>
            <el-col :span="12">
            <el-form-item label="类型名称" :label-width="formLabelWidth">
              <el-select v-model="form.typeName">
                <el-option v-for="item in typeList"
                  :label="item.name"
                  :key="item.name"
                  :value="item.name">
                </el-option>
              </el-select>
            </el-form-item>
            </el-col>
            <el-col :span="12">
            <el-form-item label="视图" :label-width="formLabelWidth">
              <el-input v-model="form.gridView" auto-complete="off"></el-input>
            </el-form-item>
            </el-col>
            <el-col :span="12">
             <el-form-item label="代码" :label-width="formLabelWidth">
              <el-input v-model="form.coding" auto-complete="off"></el-input>
            </el-form-item>
            </el-col>
            <el-col :span="12">
             <el-form-item label="完整代码" :label-width="formLabelWidth">
              <el-input v-model="form.fullCoding" auto-complete="off"></el-input>
            </el-form-item>
            </el-col>
            <el-col :span="12">
            <el-form-item label="ACL" :label-width="formLabelWidth">
              <el-input v-model="form.aclName" auto-complete="off"></el-input>
            </el-form-item>
            </el-col>
            <el-col :span="12">
            <el-form-item label="排序" :label-width="formLabelWidth">
              <el-input v-model="form.orderIndex" auto-complete="off"></el-input>
            </el-form-item>
            </el-col>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="additem(form)">添加</el-button>
            <el-button type="primary" @click="saveitem(form)">保存</el-button>
          </div>
        </el-dialog>
             <el-container>
          <el-header>
             <el-breadcrumb separator="/" class="navbar">
               <el-breadcrumb-item>系统管理</el-breadcrumb-item>
               <el-breadcrumb-item>文件夹管理</el-breadcrumb-item>
             </el-breadcrumb>
            <el-row class="topbar">
              <el-col :span="4">
                <el-input  v-model="inputkey" placeholder="请输入关键字" @change="searchFolder" prefix-icon="el-icon-search"></el-input>
              </el-col>
              <el-col :span="20" style="text-align:left;">
                &nbsp; 
                <el-button type="primary" plain icon="el-icon-edit"  @click="dialogVisible = true">查看</el-button>
                    <el-button type="primary" plain icon="el-icon-delete"  @click="delitem(currentData)">删除</el-button>
                    <el-button type="primary" plain icon="el-icon-copy-document"  @click="showCopy()">复制</el-button>
              </el-col>
            </el-row>
          </el-header>
          <el-main>
            <el-tree
              :props="defaultProps"
              :data="dataList"
              node-key="id"
              lazy
              @node-click="handleNodeClick">
            </el-tree>
          </el-main>
             </el-container>
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
      copyDialogVisible: false,
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
      copyForm: {
        sourceId:"",
        targetId:""
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
    axios.post("/admin/getFolder",0)
      .then(function(response) {
        _self.dataList = response.data.data;
        _self.loading = false;
      })
      .catch(function(error) {
        console.log(error);
        _self.loading = false;
      });

      _self.loading = true;
      axios.post('/admin/getType')
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
    showCopy(){
      this.copyForm.sourceId = this.currentData.id;
      this.copyDialogVisible = true;
    },
    copyFolder(inData){
      let _self = this;
      var m = new Map();
      m.set("sourceId",inData.sourceId);
      m.set("targetId",inData.targetId);
      axios.post("/folder/copyFolders",JSON.stringify(m))
        .then(function(response) {
          _self.copyDialogVisible = false;
          _self.refreshData();
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    refreshData() {
    let _self = this;
    _self.loading = true;
    axios.post("/admin/getFolder",0)
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
      _self.form = indata;
      // _self.form.id = indata.id;
      // _self.form.name = indata.name;
      // _self.form.description = indata.description;
      // _self.form.parentId = indata.parentId;
      // _self.form.typeName = indata.typeName;
      // _self.form.gridView = indata.gridView;
      // _self.form.aclName = indata.aclName;
     // console.log(JSON.stringify(indata));
      if(indata.extended == false)
      {
        _self.loading = true;
        axios.post("/admin/getFolder",indata.id)
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
      axios.post("/admin/updateFolder",JSON.stringify(indata))
        .then(function(response) {
          _self.$message("保存成功!");
           _self.dialogVisible = false;
          // _self.currentData.name = indata.name;
          // _self.currentData.description = indata.description;
          // _self.currentData.parentId = indata.parentId;
          // _self.currentData.typeName = indata.typeName;
          // _self.currentData.gridView = indata.gridView;
          // _self.currentData.aclName = indata.aclName;
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    delitem(indata) {
      let _self = this;
      axios.post("/admin/deleteFolder",JSON.stringify(indata))
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
      axios.post("/admin/newFolder",JSON.stringify(indata))
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
      axios.post("/admin/copyFolder",JSON.stringify(indata))
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
      _self.dataList = [];
      if (_self.inputkey != "" || _self.parentid != "") {
        _self.dataList = _self.dataListFull.filter(function(item){
          return item.name.match(_self.inputkey) || item.description.match(_self.inputkey);
        });
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
.el-header {
  background-color: #e8eaeb;
  height: 68px !important;
}
.el-row {
  padding-bottom: 10px;
}
</style>
