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
            <el-form-item label="存储名称" :label-width="formLabelWidth">
              <el-select v-model="form.storeName">
                <el-option v-for="item in storeList"
                  :label="item.name"
                  :key="item.name"
                  :value="item.name">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="标识" :label-width="formLabelWidth">
              <el-input v-model="form.typeTag" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="状态" :label-width="formLabelWidth">
              <el-input v-model="form.status" auto-complete="off"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="additem(form)">确 定</el-button>
          </div>
        </el-dialog>
      <table border="0" width="100%">
          <tr>
            <td class="navbar">
              /类型管理
            </td>
          </tr>
          <tr>
            <td>
              <table border="0" width="100%" class="topbar">
                <tr>
                  <td align="left" width="160px">
                    <el-input v-model="inputkey" placeholder="请输入关键字" @change="search" prefix-icon="el-icon-search"></el-input>
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
                style="width: 100%">
        <el-table-column
                label="行号"
                type="index"
                width="60">
              </el-table-column>
        <el-table-column label="名称" width="180" >
           <template slot-scope="scope">
            <el-input  v-model="scope.row.name"></el-input>
          </template>
        </el-table-column>
        <el-table-column label="说明" width="180">
          <template slot-scope="scope">
            <el-input  v-model="scope.row.description"></el-input>
          </template>
        </el-table-column>
        <el-table-column label="存储名称" width="240">
          <template slot-scope="scope">
            <el-input  v-model="scope.row.storeName"></el-input>
          </template>
        </el-table-column>
        <el-table-column label="标识" width="240">
          <template slot-scope="scope">
            <el-input  v-model="scope.row.typeTag"></el-input>
          </template>
        </el-table-column>
        <el-table-column label="状态" width="240">
          <template slot-scope="scope">
            <el-input  v-model="scope.row.status"></el-input>
          </template>
        </el-table-column>
        <el-table-column label="操作"  width="320">
          <template slot-scope="scope">
            <el-button :plain="true" type="primary" size="small" icon="edit" @click="saveitem(scope.row)">保存</el-button>
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
  name: "TypeManager",
  permit: 5,
  data() {
    return {
      dataList: [],
      dataListFull: [],
      storeList:[],
      inputkey: "",
      loading: false,
      dialogVisible: false,
      tableHeight: window.innerHeight - 140,
      form: {
        name: "",
        description: "",
        storeType: 0,
        storeClass:"",
        typeTag:"",
        storePath:""
      },
      formLabelWidth: "120px"
    };
  },
   created(){ 
     
    let _self = this;
    _self.loading = true;
    _self.axios({
      headers: {
        "Content-Type": "application/json;charset=UTF-8"
      },
      method: 'get',
      url: '/zisecm/admin/getType'
    })
      .then(function(response) {
        _self.dataList = response.data.data;
        _self.dataListFull = response.data.data;
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
      url: '/zisecm/admin/getStore'
    })
      .then(function(response) {
        _self.storeList = response.data.data;
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
      _self.axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        method: 'get',
        url: '/zisecm/admin/getType'
      })
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
      _self.axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        datatype: 'json',
        method: 'post',
        data: JSON.stringify(indata),
        url: '/zisecm/admin/updateType'
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
      _self.axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        datatype: 'json',
        method: 'post',
        data: JSON.stringify(indata),
        url: '/zisecm/admin/deleteType'
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
        url: '/zisecm/admin/newType'
      })
      .then(function(response) {
          _self.dialogVisible = false;
          _self.refreshData();
      })
      .catch(function(error) {
        console.log(error);
      });
    },
    showitem(indata) {
      let _self = this;
    },
    copyitem(indata) {
      let _self = this;
      _self.axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        datatype: 'json',
        method: 'post',
        data: JSON.stringify(indata),
        url: '/zisecm/admin/copyType'
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
    search() {
      let _self = this;
      _self.dataList = _self.dataListFull.filter(function(item){
          return item.name.match(_self.inputkey) || item.description.match(_self.inputkey);
        }
      );
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
