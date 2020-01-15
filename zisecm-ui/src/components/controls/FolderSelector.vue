<template>
  <el-container>
    <el-dialog
      :visible.sync="visible"
      :append-to-body="true"
      :close-on-click-modal="false"
      @close="closeDialog"
      title="文件夹选择"
      width="70%"
    >
      <div>
        <el-main>
            <el-tree
              :props="defaultProps"
              :data="dataList"
              node-key="id"
              :render-content="renderContent"
              default-expand-all
              highlight-current
              @node-click="handleNodeClick"
              style="overflow:scroll;height:320px;"
            ></el-tree>
        </el-main>
        <el-footer>
          <el-button
            style="height: 35px;width: 70px;float: right;"
            type="primary"
            @click="addToFather"
          >{{$t('application.ok')}}</el-button>
        </el-footer>
      </div>
    </el-dialog>
    <el-col :span="16">
      <!-- readonly="readonly" -->
      <el-input type="text" placeholder="选择文件夹"  v-model="inputValue"></el-input>
      <input value="value1" type="hidden" />
    </el-col>
    <el-col :span="4">
      <el-button icon="el-icon-folder" @click="clickShowDialog">{{$t('application.select')}}</el-button>
    </el-col>
  </el-container>
</template>
<script>
export default {
  data() {
    return {
      visible: false,
      currentFolder: "",
      dataList: [],
      defaultProps: {
        children: "children",
        label: "name"
      }
    };
  },
  model: {
    prop: "value1",
    event: "change"
  },
  mounted() {
    this.loadRoot();
  },
  props: {
    //输入框默认显示值
    parentId: {
      type: String,
      default: "0"
    },
    inputValue: {
      type: String,
      default: ""
    }
  },
  methods: {
    // 文件夹节点点击事件
    handleNodeClick(indata) {
      let _self = this;
      _self.currentFolder = indata;
      //console.log(JSON.stringify(indata));
      // 没有加载，逐级加载
      if (indata.extended == false) {
        _self.loading = true;
        axios
          .post("/admin/getFolder", indata.id)
          .then(function(response) {
            indata.children = response.data.data;
            //console.log(JSON.stringify(indata));
            indata.extended = true;
            _self.loading = false;
          })
          .catch(function(error) {
            console.log(error);
            _self.loading = false;
          });
      }
    },
    renderContent: function(h, { node, data, store }) {
      //console.log(data);
      if (data.extended) {
        return (
          <span>
            <i class="el-icon-folder-opened"></i>
            <span style="padding-left: 4px;">{node.label}</span>
          </span>
        );
      } else {
        return (
          <span>
            <i class="el-icon-folder"></i>
            <span style="padding-left: 4px;">{node.label}</span>
          </span>
        );
      }
    },
    loadRoot() {
      let _self = this;
      _self.loading = true;
      axios
        .post("/admin/getFolder", _self.parentId)
        .then(function(response) {
          _self.dataList = response.data.data;
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    addToFather() {
      let _self = this;
      _self.$emit("change", _self.currentFolder.id);
      //_self.inputValue = _self.currentFolder.id;
      _self.visible = false;
    },
    clickShowDialog() {
      this.visible = true;
    },
    closeDialog() {
      this.visible = false;
    }
  }
};
</script>
<style scoped>
.el-header {
  background-color: white;
  text-align: left;
}
.el-main {
  background-color: white;
}
.el-footer {
  background-color: white;
}
</style>
