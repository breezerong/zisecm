<template>
  <el-container>
	  <el-dialog :visible.sync="showChooseDialog" :append-to-body="true" width="40%"> 
            <el-input placeholder="输入搜索内容，点击回车进行搜索" @keyup.enter.native="on_filter" v-model="findValue"></el-input>				
            <el-table :data="userList" border height="350px" fit stripe style="width: 100%">
              <el-table-column prop="name" label="用户名"></el-table-column>
              <el-table-column prop="email" label="邮箱"></el-table-column>
              <el-table-column width="100" label="操作">
                <template slot-scope="scope">
                  <el-button
                    :plain="true"
                    type="primary"
                    size="medium"
                    icon="edit"
                    @click="handleInput(scope.row)"
                  >添加</el-button>
                </template>
              </el-table-column>
            </el-table>
	  </el-dialog>
      <el-col :span="18">
        <el-input type="text" placeholder="请选择用户" readonly="readonly" v-model="inputValue"></el-input>
        <input value="value1" type="hidden" />
      </el-col>
      <el-col :span="4">
          <el-button icon="el-icon-user-solid" @click="clickShowDialog">选择</el-button>
      </el-col>
  </el-container>
</template>

<script>
export default {
  data() {
    return {
      findValue: "",
      showInput: "",
      visible: false,
	  userList: [],
	  showChooseDialog:false
    };
  },
  model: {
    prop: "value1",
    event: "change"
  },
  props: {
    //是否隐藏
    isHiden: {
      type: Boolean,
      default: false
    },
    //输入框默认显示值
    inputValue: {
      type: [String, Number],
      default: ""
    },
    //获取数据的url地址
    dataUrl: {
      type: String,
      default: "/zisecm/admin/getUsers"
    },
    //获取数据的请求方式，默认为post
    getmethods: {
      type: String,
      default: "post"
    },
    //属性名
    atrName: {
      type: String,
      default: ""
    },
    dataList: {
      type: Array
    }
  },
  mounted() {
    this.showInput = this.inputValue;
    //console.log("this.inputValue:"+this.inputValue);
    this.refreshData;
  },
  methods: {
    //获取选人框体数据
    refreshData() {
      var m = new Map();
      m.set("noGroup", "");
      m.set("condition", "name like '%"+this.findValue+"%'");
      m.set("pageIndex", 0);
      m.set("pageSize", 50);
      let _self = this;
      _self
        .axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: _self.getmethods,
          data: m,
          url: _self.dataUrl
        })
        .then(function(response) {
          //console.log(response);
          _self.userList = response.data.data;
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    //改变value1的值
    handleInput: function(indata) {
      this.showInput = indata.name;
      this.$emit("change", indata.name);
      this.showChooseDialog = false;
    },
    //筛选查询方法
    on_filter: function() {
      //console.log("inputValue:"+this.inputValue);
      this.refreshData();
	},
	clickShowDialog(){
		this.showChooseDialog = true
		this.refreshData()
	}
  },
};
</script>

<style scoped>
.el-footer {
  background-color: white;
}

</style>
