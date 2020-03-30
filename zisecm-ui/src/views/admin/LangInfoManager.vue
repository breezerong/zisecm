<template>
  <div>
    <LangItemManager ref="LangItemManager" width="60%" v-bind:showDialog="showLangItemDialog"
        v-bind:messageKey="currentItem.messageKey">
    </LangItemManager>
    <el-dialog title="添加" :visible.sync="dialogVisible">
      <el-form :model="form">
        <el-form-item label="标签名" :label-width="formLabelWidth">
          <el-input v-model="form.messageKey" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="说明" :label-width="formLabelWidth">
          <el-input v-model="form.description" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="标签类型" :label-width="formLabelWidth">
          <el-select v-model="form.messageType">
            <el-option label="DocType" value="DocType"></el-option>
            <el-option label="System" value="System"></el-option>
            <el-option label="Portal" value="Portal"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addItem(form)">确 定</el-button>
      </div>
    </el-dialog>
    <el-container>
      <el-header>
        <el-breadcrumb separator="/" class="navbar">
          <el-breadcrumb-item>系统管理</el-breadcrumb-item>
          <el-breadcrumb-item>语言标签管理</el-breadcrumb-item>
        </el-breadcrumb>
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
            <el-button
              type="primary"
              icon="el-icon-edit"
              plain
              @click="dialogVisible = true"
            >新建</el-button>
          </el-col>
        </el-row>
      </el-header>
      <el-main>
        <el-table
          :data="dataList"
          border
          :height="tableHeight"
          v-loading="loading"
          style="width: 100%"
        >
          <el-table-column label="行号" type="index" width="70"></el-table-column>
          <el-table-column label="标签名" min-width="20%" sortable>
            <template slot-scope="scope">
              <el-input v-model="scope.row.messageKey"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="说明" min-width="20%">
            <template slot-scope="scope">
              <el-input v-model="scope.row.description"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="标签类型" width="140">
            <template slot-scope="scope">
                <!--<el-input v-model="scope.row.messageType"></el-input>-->
              <el-select v-model="scope.row.messageType">
                <el-option label="DocType" value="DocType"></el-option>
                <el-option label="System" value="System"></el-option>
                <el-option label="Portal" value="Portal"></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="标签值" min-width="40%">
            <template slot-scope="scope">
              <span v-for="(litem,idx) in scope.row.langItems" :key="idx+'_L'">
                  {{litem.langKey}}:&nbsp; {{litem.messageLabel}}; &nbsp;
              </span>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="270">
            <template slot-scope="scope">
              <el-button
                :plain="true"
                type="primary"
                size="small"
                icon="edit"
                @click="editItem(scope.row)"
              >编辑标签</el-button>
              <el-button
                :plain="true"
                type="primary"
                size="small"
                icon="edit"
                @click="saveItem(scope.row)"
              >保存</el-button>
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
              background
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
import LangItemManager from "@/components/admin/LangItemManager";
// $.ajaxSetup({
//   contentType: "application/json"
// });

export default {
  name: "LangInfoManager",
  permit: 9,
  components: {
    LangItemManager: LangItemManager
  },
  data() {
    return {
      dataList: [],
      dataListFull: [],
      inputkey: "",
      loading: false,
      dialogVisible: false,
      currentPage: 1,
      pageSize: 20,
      itemCount:0,
      tableHeight: window.innerHeight - 145,
      form: {
        messageKey: "attr_",
        description: "",
        messageType: "DocType"
      },
      currentItem:[],
      showLangItemDialog : false,
      formLabelWidth: "120px"
    };
  },
  mounted() {
    let _self = this;
    var psize = localStorage.getItem("langInfoPageSize");
    if (psize) {
      _self.pageSize = parseInt(psize);
    }
    this.refreshData();
  },
  methods: {
    refreshData() {
      let _self = this;
      _self.loading = true;
      var key = _self.inputkey;
      if (key != "") {
        key = "MESSAGE_KEY like '%" + key + "%' or DESCRIPTION like '%" + key + "%'";
      }
      var m = new Map();
      m.set("condition", key);
      m.set("pageSize", _self.pageSize);
      m.set("pageIndex", _self.currentPage - 1);
      axios
        .post("/lang/getLangInfos",JSON.stringify(m))
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
    handleSizeChange(val) {
      this.pageSize = val;
      localStorage.setItem("langInfoPageSize", val);
      this.refreshData();
      //console.log('handleSizeChange', val);
    },
    // 分页 当前页改变
    handleCurrentChange(val) {
      this.currentPage = val;
      this.refreshData();
      //console.log('handleCurrentChange', val);
    },
    saveItem(indata) {
      let _self = this;
      axios
        .post("/lang/updateLangInfo", JSON.stringify(indata))
        .then(function(response) {
          _self.$message("保存成功!");
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    delItem(indata) {
      let _self = this;
      axios
        .post("/lang/deleteLangInfo", JSON.stringify(indata))
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
      axios
        .post("/lang/newLangInfo", JSON.stringify(indata))
        .then(function(response) {
          _self.dialogVisible = false;
          _self.refreshData();
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    editItem(indata) {
      let _self = this;
      _self.currentItem = indata;
      _self.showLangItemDialog = true;
      if(_self.$refs.LangItemManager){
        _self.$refs.LangItemManager.messageKey = indata.messageKey;
        _self.$refs.LangItemManager.showDialog = true;
        _self.$refs.LangItemManager.refreshData();
      }
    },
    search() {
      this.refreshData();
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
