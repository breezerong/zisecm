<template>
  <div>
    <el-dialog title="选择属性" :visible.sync="formitemVisible" width="68%">
      <FormItemSelector
        ref="FormItemSelector"
        @onSelected="onSelected"
        width="640"
        v-bind:formitemVisible="formitemVisible"
      ></FormItemSelector>
      <div slot="footer" class="dialog-footer">
        <el-button @click="formitemVisible = false">{{$t('application.cancel')}}</el-button>
      </div>
    </el-dialog>
    <el-dialog title="卡片表单校验" :visible.sync="checkVisible" width="80%">
      <CardSearchItemCheck ref="CardSearchItemCheck" width="100%" :parentformid="parentId"></CardSearchItemCheck>
      <div slot="footer" class="dialog-footer">
        <el-button @click="checkVisible = false">{{$t('application.cancel')}}</el-button>
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
        <el-form-item label="说明" :label-width="formLabelWidth">
          <el-input v-model="form.description" auto-complete="off"></el-input>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="序号" :label-width="formLabelWidth2">
              <el-input v-model="form.orderIndex" auto-complete="off"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">{{$t('application.cancel')}}</el-button>
        <el-button type="primary" @click="addItem(form)">确 定</el-button>
      </div>
    </el-dialog>
    <el-row class="navbar">
      <el-breadcrumb>
        <el-breadcrumb-item>系统管理</el-breadcrumb-item>
        <el-breadcrumb-item>界面配置</el-breadcrumb-item>
        <el-breadcrumb-item>卡片查询字段管理</el-breadcrumb-item>
      </el-breadcrumb>
    </el-row>
    <el-row class="topbar">
      <el-col :span="4">名称：{{typeName}}</el-col>
      <el-col :span="4">
        <el-input
          v-model="inputkey"
          placeholder="请输入属性名关键字"
          @change="search"
          prefix-icon="el-icon-search"
        ></el-input>
      </el-col>
      <el-col :span="16" style="text-algin:left;panding-left:20px;">
        &nbsp;
          <el-button type="primary" plain icon="el-icon-edit" @click="newItem()">{{$t('application.new')}}</el-button>
          <el-button type="primary" plain icon="el-icon-check" @click="startCheck()">表单验证</el-button>
      </el-col>
    </el-row>
    <el-table
      :data="dataList"
      border
      :height="tableHeight"
      v-loading="loading"
      size="small"
      style="width: 100%"
    >
      <el-table-column
                label="行号"
                type="index"
                width="60"> </el-table-column>
      <el-table-column label="表单ID" width="260">
        <template slot-scope="scope">
          <el-input v-model="scope.row.formItemId"></el-input>
        </template>
      </el-table-column>
      <el-table-column label="说明" width="260">
        <template slot-scope="scope">
          <el-input v-model="scope.row.description"></el-input>
        </template>
      </el-table-column>
      <el-table-column label="序号" width="80">
        <template slot-scope="scope">
          <el-input v-model="scope.row.orderIndex" auto-complete="off"></el-input>
        </template>
      </el-table-column>

      <el-table-column label="操作" width="240">
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
import FormItemSelector from "@/components/admin/FormItemSelector";
import CardSearchItemCheck from "@/components/admin/CardSearchItemCheck";

export default {
  name: "CardSearchItemManager",
  permit: 9,
  data() {
    return {
      dataList: [],
      dataListFull: [],
      tableHeight: window.innerHeight - 135,
      inputkey: "",
      parentId: "",
      typeName: "",
      loading: false,
      dialogVisible: false,
      dialogtitle: "",
      formitemVisible: false,
      queryVisible: false,
      checkVisible: false,
      currentItem: "",
      form: {
        id: "",
        parentId: "",
        formItemId: "",
        description:"",
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
    _self.parentId = _self.$route.query.parentid;
    _self.typeName = _self.$route.query.name;
    _self.refreshData();
  },
  methods: {
    startCheck() {
      let _self = this;
      _self.checkVisible = true;
      _self.$refs.CardSearchItemCheck.loaddata();
    },
    refreshData() {
      let _self = this;
      _self.loading = true;
      axios
        .post("/admin/getCardSearchItem", _self.parentId)
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
    saveItem(indata) {
      let _self = this;
      axios
        .post("/admin/updateCardSearchItem", JSON.stringify(indata))
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
        .post("/admin/deleteCardSearchItem", JSON.stringify(indata))
        .then(function(response) {
          _self.$message("删除成功!");
          _self.refreshData();
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    newItem() {
      this.dialogtitle = "添加";
      this.dialogVisible = true;
      this.form = {
        parentId: this.parentId,
        formItemId: "",
        orderIndex: "1"
      };
    },
    editItem(indata) {
      this.dialogtitle = "编辑";
      this.dialogVisible = true;
      this.form = indata;
    },
    addItem(indata) {
      let _self = this;
      if (_self.dialogtitle == "编辑") {
        _self.saveItem(indata);
        this.dialogVisible = false;
      } else {
        axios
          .post("/admin/newCardSearchItem", JSON.stringify(indata))
          .then(function(response) {
            _self.dialogVisible = false;
            _self.refreshData();
          })
          .catch(function(error) {
            console.log(error);
          });
      }
    },
    showformitem(indata) {
      this.formitemVisible = true;
      this.currentItem = indata;
    },
    onSelected(indata) {
      let _self = this;
      _self.formitemVisible = false;
      if (indata) {
        _self.form.formItemId = indata.id;
      }
    },
    search() {
       let _self = this;
      // _self.dataList = [];
      // if (_self.inputkey != "" || _self.parentid != "") {
      //   _self.dataList = _self.dataListFull.filter(function(item){
      //     return item.name.match(_self.inputkey) || item.description.match(_self.inputkey);
      //   });
      // }
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
