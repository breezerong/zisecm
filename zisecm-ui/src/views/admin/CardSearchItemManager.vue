<template>
  <div>
    <el-dialog title="选择属性" :visible.sync="formitemVisible" >
      <FormItemSelector ref="FormItemSelector" @onselected="onselected" width="640" v-bind:formitemVisible="formitemVisible"></FormItemSelector>
      <div slot="footer" class="dialog-footer">
        <el-button @click="formitemVisible = false">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog title="卡片表单校验" :visible.sync="checkVisible" >
      <CardSearchItemCheck ref="CardSearchItemCheck" width="560" :parentformid="parentid"></CardSearchItemCheck>
      <div slot="footer" class="dialog-footer">
        <el-button @click="checkVisible = false">取 消</el-button>
      </div>
    </el-dialog>
        <el-dialog :title.sync="dialogtitle" :visible.sync="dialogVisible">
          <el-form :model="form">
            <el-form-item label="父Id" :label-width="formLabelWidth">
              <el-input v-model="form.parentId" auto-complete="off"></el-input>
            </el-form-item>
            <el-row>
              <el-col :span="10">
                <el-form-item label="表单ID" :label-width="formLabelWidth">
                  <el-input v-model="form.formItemId" auto-complete="off"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="4">
                <el-button @click="formitemVisible=true">选择字段</el-button>
              </el-col>
            </el-row>
            
            <el-row>
              <el-col :span="12">
                <el-form-item label="序号" :label-width="formLabelWidth2">
                  <el-input v-model="form.orderIndex" auto-complete="off"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="additem(form)">确 定</el-button>
          </div>
        </el-dialog>
    <table border="0" width="100%" >
          <tr>
            <td class="navbar">
              /卡片查询字段管理
            </td>
          </tr>
          <tr>
            <td>
              <table border="0" width="100%" class="topbar">
                <tr>
                  <td width="160px">
                    名称：{{typename}}
                  </td>
                  <td width="80px">
                    <el-input  v-model="parentid" placeholder="请输入父Id" @change="search" prefix-icon="el-icon-search"></el-input>
                  </td>
                  <td align="left" width="160px">
                    <el-input  v-model="inputkey" placeholder="请输入属性名关键字" @change="search" prefix-icon="el-icon-search"></el-input>
                  </td>
                  <td width="40px">
                    <el-tooltip  class="item" effect="dark" content="新增" placement="top">
                      <el-button type="primary" icon="el-icon-edit" circle @click="newitem()"></el-button>
                    </el-tooltip> 
                  </td>
                  <td>
                    <el-tooltip  class="item" effect="dark" content="表单验证" placement="top">
                     <el-button type="primary" icon="el-icon-check" circle @click="startcheck()"></el-button>
                    </el-tooltip>
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
        <el-table-column prop="id" label="ID" width="60" sortable>
        </el-table-column>
        <el-table-column label="父ID" width="60">
          <template slot-scope="scope">
            <el-input  v-model="scope.row.parentId"></el-input>
          </template>
        </el-table-column>
        <el-table-column label="表单ID" width="150">
          <template slot-scope="scope">
            <el-input  v-model="scope.row.formItemId"></el-input>
          </template>
        </el-table-column>
        <el-table-column prop="attrName" label="属性名" width="160" sortable>
        </el-table-column>
        <el-table-column prop="label" label="标签" width="160" sortable>
        </el-table-column>
        <el-table-column label="序号" width="60">
          <template slot-scope="scope">
              <el-input v-model="scope.row.orderIndex" auto-complete="off"></el-input>
          </template>
        </el-table-column>
        
        <el-table-column label="操作" width="240">
          <template slot-scope="scope">
            <el-button :plain="true" type="primary" size="small" icon="edit" @click="edititem(scope.row)">编辑</el-button>
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
import FormItemSelector from '@/components/admin/FormItemSelector'
import CardSearchItemCheck from '@/components/admin/CardSearchItemCheck'

export default {
  name: "CardSearchItemManager",
  permit: 9,
  data() {
    return {
      dataList: [],
      dataListFull: [],
      tableHeight: window.innerHeight - 140,
      inputkey: "",
      parentid: "",
      typename: "",
      loading: false,
      dialogVisible: false,
      dialogtitle:"",
      formitemVisible: false,
      queryVisible: false,
      checkVisible: false,
      currentItem: "",
      form: {
        id:"",
        parentId:"",
        formItemId: "",
        orderIndex: 1
      },
      formLabelWidth: "120px",
      formLabelWidth2: "100px"
    };
  },
  components: {
     FormItemSelector: FormItemSelector,
     CardSearchItemCheck: CardSearchItemCheck
  },
  created() {
    let _self = this;
    _self.loading = true;
    _self.parentid = _self.$route.query.parentid;
    _self.typename = _self.$route.query.name;
    axios.get("/admin/getCardSearchItem",_self.parentid)
      .then(function(response) {
        _self.dataListFull = response.data.data;
        _self.dataList = response.data.data;
        // console.log(JSON.stringify(_self.dataList));
        _self.loading = false;
      })
      .catch(function(error) {
        console.log(error);
        _self.loading = false;
      });
  },
  methods: {
    startcheck()
    {
      let _self = this;
      _self.checkVisible = true;
      _self.$refs.CardSearchItemCheck.loaddata();
    },
    refreshData() {
      let _self = this;
      _self.loading = true;
      axios.get("/admin/getCardSearchItem",_self.parentid)
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
    saveitem(indata) {
      let _self = this;
      axios.post("/admin/updateCardSearchItem",JSON.stringify(indata))
        .then(function(response) {
          _self.$message("保存成功!");
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    delitem(indata) {
      let _self = this;
      axios.poset("/admin/deleteCardSearchItem",JSON.stringify(indata))
        .then(function(response) {
          _self.$message("删除成功!");
          _self.refreshData();
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    newitem()
    {
      this.dialogtitle = "添加";
      this.dialogVisible =true;
      this.form = {
        parentId: this.parentid,
        formItemId: "",
        orderIndex: "1"
      };
    },
    edititem(indata)
    {
      this.dialogtitle = "编辑";
      this.dialogVisible =true;
      this.form = indata;
    },
    additem(indata) {
      let _self = this;
      if(_self.dialogtitle == "编辑")
      {
        _self.saveitem(indata);
        this.dialogVisible =false;
      }
      else
      {
      axios.post("/admin/newCardSearchItem",JSON.stringify(indata))
        .then(function(response) {
          _self.dialogVisible = false;
          _self.refreshData();
        })
        .catch(function(error) {
          console.log(error);
        });
      }
    },
    showformitem(indata)
    {
      this.formitemVisible = true;
      this.currentItem = indata;
    },
    onselected(indata) {
      let _self = this;
      _self.formitemVisible = false;
      if(indata)
      {
        _self.form.formItemId = indata.id;
      }
    },
    search() {
      let _self = this;
      let tab = _self.dataListFull;
      _self.dataList = [];
      var i;
      if (_self.inputkey != "" || _self.parentid != "") {
        for (i in tab) {
          if (_self.inputkey != "") {
            if (
              tab[i].attrName.indexOf(_self.inputkey) >= 0 ||
              tab[i].label.indexOf(_self.inputkey) >= 0
            ) {
              if (_self.parentid != "") {
                if (tab[i].parentId == _self.parentid) {
                  _self.dataList.push(tab[i]);
                }
              } else {
                _self.dataList.push(tab[i]);
              }
            }
          } else if (_self.parentid != "") {
            //_self.$message(tab[i].parentId+","+_self.parentid);
            if (tab[i].parentId == _self.parentid) {
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
