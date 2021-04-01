<template>
  <el-container >
    <el-dialog v-dialogDrag 
      :visible.sync="visible"
      :append-to-body="true"
      :close-on-click-modal="false"
      @open="refreshData"
      @close="closeDialog"
      :title="$t('application.selectUser')"
      width="70%"
    >
      <div>
          <el-row v-if="roleName == ''">
            <el-col :span="12">
              <el-popover
                ref="deptTree"
                placement="bottom"
                width="400"
                trigger="click">
                  <el-container style="height:320px;width:100%;overflow:auto;">
                    <el-tree
                      :props="defaultProps"
                      :data="deptList"
                      node-key="id"
                      lazy
                      @node-click="handleNodeClick"
                    ></el-tree>
                  </el-container>
                <el-input placeholder="请选择部门" slot="reference" v-model="departmentName" suffix-icon="el-icon-arrow-down"></el-input>
              </el-popover>
            </el-col>
            <el-col :span="2">
              <el-button icon="el-icon-close" @click="cleanDepartment" style="height:36px">清空部门</el-button>
            </el-col>
          </el-row>
          <el-row class="topbar-button-nopadding-nofloat">
            <el-input :placeholder="$t('application.placeholderSearch')" @keyup.enter.native="handleSearch" v-model="findValue"></el-input>
          </el-row>
          <el-row>
            <el-col :span="11">
              <el-table
                height="250"
                :data="dataList"
                ref="leftTable"
                stripe
                border
                size="mini"
                 @row-dblclick="leftDbClick"
                 @row-click="leftClick"
                @selection-change="handleSelectionChange"
              >
                <el-table-column type="selection" width="60"></el-table-column>
                <el-table-column prop="loginName" label="工号"></el-table-column>
                <el-table-column prop="name" :label="$t('application.userName')" width="120"></el-table-column>
                <el-table-column prop="departmentName" :label="$t('application.group')" width="120"></el-table-column>
              </el-table>
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
                <el-button 
                size="mini" 
                type="infor" 
                :disabled="rightList.length==0"
                @click="addToLeft()">
                  <i class="el-icon-arrow-left"></i>
                </el-button>
              </div>
            </el-col>
            <el-col :span="11">
              <el-table
                height="250"
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
                <el-table-column prop="loginName" label="工号"></el-table-column>
                <el-table-column prop="name" :label="$t('application.userName')" width="120"></el-table-column>
                <el-table-column prop="departmentName" :label="$t('application.group')" width="120"></el-table-column>
              </el-table>
            </el-col>
          </el-row>
        <el-footer>
          <el-button
            style="height: 35px;width: 70px;float: right;margin:5px"
            type="primary" plain 
            @click="addToFather"
          >{{$t('application.ok')}}</el-button>
        </el-footer>
      </div>
    </el-dialog>
    <el-col :span="16">
      <el-input type="text" :placeholder="$t('application.selectUser')" v-model="inputValue" @focus="clickShowDialog"></el-input>
      <input value="value1" type="hidden" />
    </el-col>
    <el-col :span="4">
      <el-button :disabled="buttonType" icon="el-icon-user-solid" @click="clickShowDialog">{{$t('application.select')}}</el-button>
    </el-col>
  </el-container>
</template>
<script>
export default {
  data() {
    return {
      loading:false,
      visible: false,
      findValue: "",
      dataList: [],
      dataListFull: [],
      rightList: [],
      tranList2: [],
      tranList: [],
      rightNameList: "",
      rightListId: [],
      deptList:[],
      departmentName:'',
      selectedItemId: '',
      defaultProps: {
        children: "children",
        label: "name"
      },
      num:true
    };
  },
  model: {
    prop: "value1",
    event: "change"
  },
  created() {
    if(this.roleName ==""){
      this.bindDepartment();
    }
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
    maxCount: {
      type: Number,
      default: 50
    },
    roleName: {
      type: String,
      default: ""
    },
    noGroup: {
      type: String,
      noGroup: "0"
    },
    buttonType:{
      type: Boolean,
      default:false
    }
  },
  methods: {
    handleNodeClick(indata) {
      let _self = this;
      _self.currentData = indata;
      _self.selectedItemId = indata.id;
      var m = new Map();
      m.set("id", indata.id);
      m.set("groupType", 1);
      if (indata.extended == false) {
        _self.loading = true;
        axios
          .post("/admin/getGroups", JSON.stringify(m))
          .then(function(response) {
            indata.children = response.data.data;
            indata.extended = true;
            //console.log(JSON.stringify(indata));
            _self.loading = false;
          })
          .catch(function(error) {
            console.log(error);
            _self.loading = false;
          });
      }
      _self.departmentName = indata.name;
      _self.refreshDeptData();
      //_self.bindUserData();
    },
    // 绑定部门
    bindDepartment() {
      let _self = this;
      _self.loading = true;
      var m = new Map();
      m.set("id", 0);
      m.set("groupType", 1);
      axios
        .post("/admin/getGroups", JSON.stringify(m))
        .then(function(response) {
          _self.deptList = response.data.data;
          console.log(_self.deptList);
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    clickShowDialog() {
      this.visible = true;
    },
    cleanDepartment(){
      this.departmentName='';
      this.refreshRoleData();
    },
    handleSearch(){
      if(this.departmentName == ""){
        this.refreshRoleData();
      }else{
        this.search();
      }
    },
    search() {
      let _self = this;
      for (var i = 0; i < _self.rightList.length; i++) {
        _self.rightListId[i] = _self.rightList[i].id;
      }
      _self.dataList = _self.dataListFull.filter(function(item) {
        return (
          item.name.match(_self.findValue) ||
          item.loginName.match(_self.findValue)
        );
      });
      for (var i = 0; i < _self.rightListId.length; i++) {
            var item = _self.rightListId[i];
            _self.dataList.forEach(function(val, index, arr) {
              if (val.id == item) {
                arr.splice(index, 1);
              }
            });
          }
    },
    refreshData() {
      if(this.departmentName == ""){
        if(this.roleName == "" ){
          this.bindDepartment();
        }
        this.refreshRoleData();
      }else{
        this.refreshDeptData();
      }
    },
    refreshDeptData(){
      let _self = this;
      for (var i = 0; i < _self.rightList.length; i++) {
        _self.rightListId[i] = _self.rightList[i].id;
      }
      var m = new Map();
      m.set("deptId", _self.selectedItemId);
      axios
        .post("/admin/getGroupUsers", JSON.stringify(m))
        .then(function(response) {
          _self.dataList = response.data.data;
          _self.dataListFull = response.data.data;
          for (var i = 0; i < _self.rightListId.length; i++) {
            var item = _self.rightListId[i];
            _self.dataList.forEach(function(val, index, arr) {
              if (val.id == item) {
                arr.splice(index, 1);
              }
            });
          }
          
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    refreshRoleData(){
      let _self = this;
      for (var i = 0; i < _self.rightList.length; i++) {
        _self.rightListId[i] = _self.rightList[i].id;
      }
      var m = new Map();
      m.set("noGroup", _self.noGroup);
      m.set("condition", "name like '%" + this.findValue + "%' or login_name like '%" + this.findValue + "%'");
      m.set("pageIndex", 0);
      m.set("pageSize", 50);
      m.set("roleName", _self.roleName);
      axios.post("/admin/getUsersByGroupName",m)
        .then(function(response) {
          _self.dataList = response.data.data;
          if(_self.num){
            var a=[]
            if(_self.inputValue.indexOf(";")!=-1){
              a=_self.inputValue.split(";")
            }
            else{
              a[0]=_self.inputValue
            }
            for (var i = 0; i < _self.dataList.length; i++) {
              for(var j=0;j<a.length;j++)
              {
                if(a[j]==_self.dataList[i].name){
                  _self.rightList.push(_self.dataList[i]);
                  _self.dataList.splice(i, 1);
                  i--
                }
              }
            }
            _self.num=false
          }
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
    addToFather() {
      let _self = this;
      _self.rightList.forEach(function(val, index, arr) {
        _self.rightNameList += val.name + ";";
      });
      _self.rightNameList = _self.rightNameList.substring(
        0,
        _self.rightNameList.length - 1
      );
      _self.$emit("change", _self.rightNameList);
      _self.rightNameList = "";
      _self.visible = false;
      _self.num=true
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
      if(this.departmentName == ""){
       this.refreshData();
      }else{
        this.search();
      }
    },
    addToRight() {
      if(!this.isRepeat && this.rightList.length>0){
          return;
      }
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
      this.tranList=[]
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
      this.tranList2=[]
    },
    closeDialog() {
      this.visible = false;
      this.findValue = "";
      this.rightList = [];
      this.num=true
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
