<template>
    <el-dialog :visible.sync="showDialog" 
    :append-to-body="true"
    :close-on-click-modal="false"
     width="60%">
      <el-row>
        {{messageKey}}
      </el-row>
      <el-row>
          <el-col :span="6">
            <el-select
                v-model="langItem.langKey"
              >
                <div v-for="(item,idx)  in languageList" :key="idx+'_L'">
                  <el-option :label="item.description" :key="item.name" :value="item.name"></el-option>
                </div>
              </el-select>
          </el-col>
          <el-col :span="12">
            <el-input v-model="langItem.messageLabel"></el-input>
          </el-col>
          <el-col :span="6">
            &nbsp;
            <el-button
                :plain="true"
                type="primary"
                size="small"
                icon="el-icon-circle-plus-outline"
                @click="addItem()"
              >添加</el-button>
          </el-col>
      </el-row>
      <el-row>
      <el-table :data="dataList" border height="350px" fit stripe style="width: 100%">
        <el-table-column type="index" width="60">序号</el-table-column>
        <el-table-column prop="langKey" label="语言标识" width="100"></el-table-column>
        <el-table-column label="标签值" min-width="20%">
          <template slot-scope="scope">
              <el-input v-model="scope.row.messageLabel"></el-input>
            </template>
        </el-table-column>
        <el-table-column width="180" label="操作">
          <template slot-scope="scope">
            <el-button
                :plain="true"
                type="primary"
                size="small"
                icon="edit"
                @click="saveItem(scope.row)"
              >保存</el-button>
            <el-button
              :plain="true"
              type="primary"
              size="medium"
              icon="edit"
              @click="delItem(scope.row)"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      </el-row>
      <el-row style="v-align:right">
         <el-button @click="showDialog=false">关闭</el-button>
      </el-row>
    </el-dialog>
</template>

<script>
export default {
  data() {
    return {
      inputkey: "",
      dataList: [],
      dataListFull: [],
      languageList:[],
      langItem:{
        langKey:"",
        messageKey:"",
        messageLabel:"",
      }
    };
  },
  model: {
    event: "onItemChange"
  },
  props: {
    messageKey: {
      type: String,
      default: null
    },
    showDialog:{
      type: Boolean,
      default:false
    }
  },
  mounted() {
    this.getLanguage();
    this.langItem.messageKey = this.messageKey;
    this.refreshData();
  },
  methods: {
    getLanguage(){
      let _self = this;
      axios.get("/lang/getLanguage")
        .then(function(response) {
          //console.log(response);
          _self.languageList = response.data.data;
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    //获取选人框体数据
    refreshData() {
      let _self = this;
      axios.post("/lang/getLangInfoByKey",_self.messageKey)
        .then(function(response) {
          console.log(response.data.data);
          _self.dataList = response.data.data.langItems;
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    saveItem(indata) {
      let _self = this;
      axios
        .post("/lang/updateLangItem", JSON.stringify(indata))
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
        .post("/lang/deleteLangItem", indata.id)
        .then(function(response) {
          _self.$message("删除成功!");
          _self.refreshData();
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    addItem() {
      let _self = this;
      _self.langItem.messageKey = _self.messageKey;
      console.log(JSON.stringify(_self.langItem));
      axios
        .post("/lang/newLangItem", JSON.stringify(_self.langItem))
        .then(function(response) {
          _self.refreshData();
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    //筛选查询方法
    onFilter() {
      let _self = this;
      _self.dataList = _self.dataListFull.filter(function(item) {
        return (
          item.name.match(_self.inputkey) ||
          item.description.match(_self.inputkey)
        );
      });
    },
    handleShowDialog() {
      this.showChooseDialog = true;
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
