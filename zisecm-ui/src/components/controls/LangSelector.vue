<template>
  <div>
    <LangItemManager ref="LangItemManager" width="60%" v-bind:showDialog="showLangItemDialog"
        v-bind:messageKey="currentItem.messageKey">
    </LangItemManager>
    <el-dialog title="添加" :visible.sync="dialogVisible"
      :append-to-body="true"
      :close-on-click-modal="false"
      @open="refreshData"
      @close="dialogVisible=false">
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
        <el-button @click="dialogVisible = false">{{$t('application.cancel')}}</el-button>
        <el-button type="primary" @click="addItem(form)">确 定</el-button>
      </div>
    </el-dialog>
    <el-container>
    <el-dialog :visible.sync="showLangDialog" width="80%" 
      :append-to-body="true"
      :close-on-click-modal="false"
      @close="showLangDialog=false">
      <el-row>
        <el-col :span="16">
          <el-input placeholder="输入搜索内容" @change="refreshData()" v-model="inputkey"></el-input>
        </el-col>
        <el-col :span="8">
          &nbsp; 
            <el-button
              type="primary"
              icon="el-icon-edit"
              plain
              @click="dialogVisible = true"
            >{{$t('application.new')}}</el-button>
        </el-col>
      </el-row>
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
              size="medium"
              icon="edit"
              @click="handleSelect(scope.row)"
            >{{$t('application.select')}}</el-button>
              <el-button
                :plain="true"
                type="info"
                size="small"
                icon="edit"
                @click="editItem(scope.row)"
              >标签</el-button>
              <el-button
                :plain="true"
                type="info"
                size="small"
                icon="edit"
                @click="saveItem(scope.row)"
              >保存</el-button>
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
    </el-dialog>
    <el-col :span="18">
      <el-input type="text" placeholder="请选择组件" v-model="inputValue" @change="valueChange"></el-input>
      <input value="value1" type="hidden" />
    </el-col>
    <el-col :span="4">
      <el-button icon="el-icon-s-unfold" @click="handleLangShowDialog">{{$t('application.select')}}</el-button>
    </el-col>
  </el-container>
  </div>
</template>

<script>
import LangItemManager from "@/components/admin/LangItemManager";
export default {
  components: {
    LangItemManager: LangItemManager
  },
  data() {
    return {
      inputkey: "",
      visible: false,
      dataList: [],
      dataListFull: [],
      currentPage: 1,
      pageSize: 20,
      form: {
        messageKey: "attr_",
        description: "",
        messageType: "DocType"
      },
      currentItem:[],
      tableHeight: "400px",
      formLabelWidth: "120px",
      showLangItemDialog : false,
      showLangDialog: false,
      dialogVisible: false
    };
  },
  model: {
    prop: "value1",
    event: "change"
  },
  props: {
    //输入框默认显示值
    inputValue: {
      type: String,
      default: ""
    }
  },
  mounted() {
    let _self = this;
    var psize = localStorage.getItem("langInfoPageSize");
    if (psize) {
      _self.pageSize = parseInt(psize);
    }
    _self.inputkey = _self.inputValue;
    _self.refreshData();
  },
  methods: {
    valueChange(){
      this.$emit("change", this.inputValue);
      // console.log("change:" + this.inputValue);
    },
    //获取选人框体数据
    refreshData() {
      let _self = this;
      _self.showLangItemDialog = false;
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
    handleSelect(indata) {
      this.inputValue = indata.messageKey;
      this.$emit("change", indata.messageKey);
      this.showLangDialog = false;
      this.showLangItemDialog = false;
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
    handleLangShowDialog() {
      this.showLangDialog = true;
      this.refreshData();
    }
  }
};
</script>

<style scoped>
.el-footer {
  background-color: white;
}
</style>
