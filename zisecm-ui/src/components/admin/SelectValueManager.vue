<template>
  <div>
        <el-dialog title="添加" :visible.sync="dialogVisible">
          <el-form :model="form">
            <el-form-item label="标签" :label-width="formLabelWidth">
              <el-input v-model="form.label" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="值" :label-width="formLabelWidth">
              <el-input v-model="form.value" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="类型名" :label-width="formLabelWidth">
              <el-input v-model="form.typeName" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="启用" :label-width="formLabelWidth">
              <el-select v-model="form.enabled">
                <el-option label="否" value="false"></el-option>
                <el-option label="是" value="true"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="排序" :label-width="formLabelWidth">
              <el-input v-model="form.orderIndex" auto-complete="off" :rows="2"></el-input>
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
              /选择项管理
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
            style="width: 100%">
              <el-table-column prop="id" label="ID" width="80" sortable>
              </el-table-column>
              <el-table-column label="标签" width="160" >
                <template slot-scope="scope">
                  <el-input  v-model="scope.row.label"></el-input>
                </template>
              </el-table-column>
              <el-table-column label="值" width="160">
                <template slot-scope="scope">
                  <el-input  v-model="scope.row.value"></el-input>
                </template>
              </el-table-column>
              <el-table-column label="类型名" width="160">
                <template slot-scope="scope">
                  <el-input  v-model="scope.row.typeName"></el-input>
                </template>
              </el-table-column> 
              <el-table-column label="是否启用" width="100">
                <template slot-scope="scope">
                  <el-select v-model="scope.row.enabled">
                      <el-option label="否" value="false"></el-option>
                      <el-option label="是" value="true"></el-option>
                    </el-select>
                </template>
              </el-table-column> 
              <el-table-column label="排序" width="80">
                <template slot-scope="scope">
                  <el-input  v-model="scope.row.orderIndex"></el-input>
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
  name: "SelectValueManager",
  data() {
    return {
      dataList: [],
      dataListFull: [],
      inputkey: "",
      loading: false,
      dialogVisible: false,
      fromparent:false,
      tableHeight: window.innerHeight - 140,
      form: {
        value:"",
        label:"",
        enabled: "true",
        typeName: "",
        orderIndex: 1
      },
      formLabelWidth: "120px"
    };
  },
   created(){ 
    let _self = this;
    if(_self.$route.query.name)
    {
      _self.inputkey = _self.$route.query.name;
      _self.fromparent = true;
    }
    _self.loading = true;
    _self.axios({
      headers: {
        "Content-Type": "application/json;charset=UTF-8"
      },
      method: 'get',
      url: '/zisecm/admin/getSelectValue'
    })
      .then(function(response) {
        _self.dataListFull = response.data.data;
        _self.dataList = response.data.data;
        _self.loading = false;
        if(_self.inputkey!="")
        {
          _self.search();
        }
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
        url: '/zisecm/admin/getSelectValue'
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
        url: '/zisecm/admin/updateSelectValue'
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
        url: '/zisecm/admin/deleteSelectValue'
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
        url: '/zisecm/admin/newSelectValue'
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
          if(_self.fromparent)
          {
            if (tab[i].typeName == _self.inputkey) {
              _self.dataList.push(tab[i]);
            }
          }
          else
          {
            if (tab[i].label.indexOf(_self.inputkey) >= 0||tab[i].value.indexOf(_self.inputkey) >= 0||tab[i].typeName.indexOf(_self.inputkey) >= 0) {
              _self.dataList.push(tab[i]);
            }
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
