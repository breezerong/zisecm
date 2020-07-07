<template>
  <el-container >
    <el-dialog
      :visible.sync="visible"
      :append-to-body="true"
      :close-on-click-modal="false"
      @open="refreshData"
      @close="closeDialog"
      :title="$t('application.selectRole')+'('+isRepeat?$t('application.multSelector'):$t('application.singleSelector')+')'"
      width="60%"
    >
      <div>
        <el-header>
          <el-row>
             <el-col :span="2">
              {{$t('application.type')}}
            </el-col>
            <el-col :span="2">
              <el-select
                v-model="groupType" :placeholder="$t('application.pleaseSelect')" @change="refreshData" style="display:block;">
                <div v-for="(item,index) in groupTypes">
                  <el-option :label="item.name" :value="item.value" :key="index"></el-option>
                </div>
              </el-select>
            </el-col>
            <el-col :span="20">
              <el-input :placeholder="$t('application.placeholderSearch')" @keyup.enter.native="refreshData" v-model="findValue"></el-input>
            </el-col>
          </el-row>
        </el-header>
        <el-main>
          <el-row>
            <el-col :span="11">
              <el-table
                height="320"
                :data="dataList"
                ref="leftTable"
                stripe
                border
                size="mini"
                @row-click="leftClick"
                @row-dblclick="leftDbClick"
                @selection-change="handleSelectionChange"
              >
                <el-table-column type="selection" width="60"></el-table-column>
                <el-table-column prop="name" :label="$t('field.name')" width="160"></el-table-column>
                <el-table-column prop="description" :label="$t('field.description')"></el-table-column>
              </el-table>
              <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="currentPage"
                    :page-sizes="[10, 20, 50, 100, 200]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="itemCount">
                  </el-pagination>
            </el-col>
            <el-col :span="2">
              <div style="text-align:center;padding-top: 40px;">
                <el-button
                  size="mini"
                  type="infor"
                  @click="addToRight()"
                  :disabled="(!isRepeat&&rightList.length==1)||(isRepeat&&rightList.length>=maxCount)"
                >
                  <i class="el-icon-arrow-right"></i>
                </el-button>
                <br />
                <br />
                <br />
                <el-button size="mini" type="infor" @click="addToLeft()">
                  <i class="el-icon-arrow-left"></i>
                </el-button>
              </div>
            </el-col>
            <el-col :span="11">
              <el-table
                height="320"
                :data="rightList"
                ref="rightTable"
                stripe
                border
                size="mini"
                 @row-click="rightClick"
                @row-dblclick="rightDbClick"
                @selection-change="handleRightSelectionChange"
              >
                <el-table-column type="selection" width="60"></el-table-column>
                <el-table-column prop="name" :label="$t('field.name')" width="160"></el-table-column>
                <el-table-column prop="description" :label="$t('field.description')"></el-table-column>
              </el-table>
            </el-col>
          </el-row>
        </el-main>
        <el-footer>
          <el-button
            style="height: 35px;width: 70px;float: right;"
            type="primary"
            @click="addToParent"
          >{{$t('application.ok')}}</el-button>
        </el-footer>
      </div>
    </el-dialog>
    <el-col :span="19" v-if="showInputText">
      <el-input type="text" :placeholder="$t('application.selectRole')" readonly="readonly" v-model="inputValue"></el-input>
      <input value="value1" type="hidden" />
    </el-col>
    <el-col :span="4">
      <el-button icon="el-icon-ecm-friend" @click="clickShowDialog">{{$t('application.select')}}</el-button>
    </el-col>
  </el-container>
</template>
<script>
export default {
  data() {
    return {
      visible: false,
      findValue: "",
      dataList: [],
      rightList: [],
      tranList2: [],
      tranList: [],
      rightNameList: "",
      rightListId: [],
      userNameArr:[],
      pageSize: 20,
      currentPage: 1,
      itemCount: 0,
      groupType:"2",
      groupTypes:
        [
          {"name":this.$t('application.all'),
          "value":""},
          {"name":this.$t('application.group'),
          "value":"1"},
          {"name":this.$t('application.role'),
          "value":"2"}
        ]
    };
  },
  model: {
    prop: "value1",
    event: "change"
  },
  created() {
    let _self = this;
    var psize = localStorage.getItem("groupPageSize");
    if (psize) {
      _self.pageSize = parseInt(psize);
    }
  },
  mounted() {
    
  },
  props: {
    //输入框默认显示值
    inputValue: {
      type: String,
      default: ""
    },
    isRepeat: {
      type: Boolean,
      default: false
    },
    showInputText: {
      type: Boolean,
      default: true
    },
    maxCount: {
      type: Number,
      default: 50
    }
  },
  methods: {
    
    // 分页 页数改变
    handleSizeChange(val) {
      this.pageSize = val;
      localStorage.setItem("groupPageSize", val);
      this.search();
    },
    // 分页 当前页改变
    handleCurrentChange(val) {
      this.currentPage = val;
      this.search();
    },
    refreshData() {
      let _self = this;
      for (var i = 0; i < _self.rightList.length; i++) {
        _self.rightListId[i] = _self.rightList[i].id;
      }
      var m = new Map();
      m.set("groupType", this.groupType);
      m.set("id", "");
      m.set("condition", "name like '%" + this.findValue + "%' or description like '%" + this.findValue + "%'");
      m.set("pageIndex", _self.currentPage-1);
      m.set("pageSize", _self.pageSize);
      _self
        .axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data: m,
          url: "/group/getGroups"
        })
        .then(function(response) {
          _self.dataList = response.data.data;
          _self.itemCount = response.data.pager.total;
          _self.to
          for (var i = 0; i < _self.rightListId.length; i++) {
            var item = _self.rightListId[i];
            _self.dataList.forEach(function(val, index, arr) {
              if (val.id == item) {
                arr.splice(index, 1);
              }
            });
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    clickShowDialog() {
      this.visible = true;
    },
    addToParent() {
      let _self = this;
      _self.rightList.forEach(function(val, index, arr) {
        _self.rightNameList += val.name + ";";
      });
      _self.rightNameList = _self.rightNameList.substring(
        0,
        _self.rightNameList.length - 1
      );
      _self.$emit("change", _self.rightNameList);
      _self.$emit("onRoleSelected", _self.rightNameList);
      _self.rightNameList = "";
      _self.visible = false;
    },
    handleSelectionChange(selection) {
      this.tranList = [];
      if (selection.length > 0) {
        for (var i = 0; i < selection.length; i++) {
          this.tranList.push(selection[i]);
        }
      }
    },
    handleRightSelectionChange(selection) {
      this.tranList2 = [];
      if (selection.length > 0) {
        for (var i = 0; i < selection.length; i++) {
          this.tranList2.push(selection[i]);
        }
      }
    },
     leftClick(row){
      this.$refs.leftTable.toggleRowSelection(row);
    },
    rightClick(row){
      this.$refs.rightTable.toggleRowSelection(row);
    },
    leftDbClick(row){
      this.tranList = [];
      this.tranList.push(row);
      this.addToRight();
    },
    rightDbClick(row){
      this.tranList2 = [];
      this.tranList2.push(row);
      this.addToLeft();
      this.refreshData();
    },
    addToRight() {
      for (var i = 0; i < this.tranList.length; i++) {
        this.rightList.push(this.tranList[i]);
        if(!this.isRepeat){
          break;
        }
      }
      for (var i = 0; i < this.tranList.length; i++) {
        var item = this.tranList[i];
        this.dataList.forEach(function(val, index, arr) {
          if (item.id == val.id) {
            arr.splice(index, 1);
          }
        });
         if(!this.isRepeat){
          break;
        }
      }
    },
    addToLeft() {
      for (var i = 0; i < this.tranList2.length; i++) {
        this.dataList.push(this.tranList2[i]);
      }
      for (var i = 0; i < this.tranList2.length; i++) {
        var item = this.tranList2[i];
        this.rightList.forEach(function(val, index, arr) {
          if (item.id == val.id) {
            arr.splice(index, 1);
          }
        });
      }
    },
    closeDialog() {
      this.visible = false;
      this.findValue = "";
      this.rightList = [];
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
