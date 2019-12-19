<template>
  <el-container>
    <el-header>
        <el-input size="small" style="width: 150px;height: 35px;" @keyup.enter.native='on_filter(findValue)' v-model="findValue"
			 suffix-icon="el-icon-search"></el-input>&nbsp;
		<el-button size="mini" type="primary" style="height: 35px;" icon="el-icon-search" @click="on_filter(findValue)">搜索</el-button>
    </el-header>
    <el-main>
      <el-row>
				<el-col :span="11">
					<el-table height="250" :data="dataList"  stripe border size="mini" @selection-change="handleSelectionChange">
						<el-table-column type="selection"></el-table-column>
						<el-table-column v-for="(item,index) in columns" :key="index" :prop="item.prop" :label="item.label"></el-table-column>
					</el-table>
				</el-col>
				<el-col :span="2">
					<div style="text-align:center;padding-top: 40px;">
						<el-button size="mini" type="infor" @click="addToRight()">></el-button><br /><br /><br />
						<el-button size="mini" type="infor" @click="addToLeft()"><</el-button> 
          </div> 
        </el-col> 
        <el-col :span="11">
						<el-table height="250" :data="RightList" stripe border size="mini"  @selection-change="handleRightSelectionChange">
							<el-table-column type="selection"></el-table-column>
							<el-table-column v-for="(item,id) in columns" :key="id" :prop="item.prop" :label="item.label"></el-table-column>
						</el-table>
				</el-col>
		</el-row>
    </el-main>
    <el-footer height="60px">
        <el-button style="height: 35px;width: 70px;float: right;" type="primary" @click="addToFather">添加</el-button>
    </el-footer>
  </el-container>
</template>

<script>


export default {
  data() {
    return {
      dataList: [],
      TranList: [],
			TranList2: [],
			RightList: [],
			rightListId: [],
      findValue:''
    };
  },
  props: {
    columns: {
      type: Array,
      default: []
    },
    groupId: {
      type: [String, Number],
      required: false
    },
    noGroup: {
      type: [String, Number],
      required: false
    }
  },
  mounted(){
    this.refreshData();
  },
  methods: {
    refreshData(){
      let _self = this;
      var n = {
      "condition":_self.findValue,
      "noGroup":_self.noGroup,
      "groupId":_self.groupId
      }
      _self.axios({
         headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data: n,
          url: '/zisecm/users/getUsersByRole'
      }).then(function(response){
        console.log(response)
        _self.dataList = response.data.datalist
      }).catch(function(error){
        console.log(error)
      })
    },
    init() {
      this.RightList = [];
    },
    handleSelectionChange(selection) {
      this.TranList = [];
      if (selection.length > 0) {
        for (var i = 0; i < selection.length; i++) {
          this.TranList.push(selection[i]);
        }
      }
    },
    handleRightSelectionChange(selection) {
      this.TranList2 = [];
      if (selection.length > 0) {
        for (var i = 0; i < selection.length; i++) {
          this.TranList2.push(selection[i]);
        }
      }
    },
    addToRight() {
      for (var i = 0; i < this.TranList.length; i++) {
        this.RightList.push(this.TranList[i]);
      }
      for (var i = 0; i < this.TranList.length; i++) {
        var item = this.TranList[i];
        this.dataList.forEach(function(val, index, arr) {
          if (item.id == val.id) {
            arr.splice(index, 1);
          }
        });
      }
    },
    addToLeft() {
      for (var i = 0; i < this.TranList2.length; i++) {
        this.dataList.push(this.TranList2[i]);
      }
      for (var i = 0; i < this.TranList2.length; i++) {
        var item = this.TranList2[i];
        this.RightList.forEach(function(val, index, arr) {
          if (item.id == val.id) {
            arr.splice(index, 1);
          }
        });
      }
    },
    on_filter() {
      let _self = this;
      for (var i = 0; i < this.RightList.length; i++) {
					this.rightListId[i] = this.RightList[i].id
        }
      var n = {
      "condition":_self.findValue,
      "noGroup":_self.noGroup,
      "groupId":_self.groupId,
      "rightListId":_self.rightListId
      }
      _self.axios({
         headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data: n,
          url: '/zisecm/users/getUsersExceptRightId'
      }).then(function(response){
        _self.dataList = response.data.data
      }).catch(function(error){
        console.log(error)
      })
      this.findValue = "";
    },
    addToFather() {
      console.log(this.RightList)
      this.$emit("onUserSelected", this.RightList);
      this.RightList = [];
    }
  },
  onuserselected(){
    console.log(this.test)
  }
};
</script>

<style scoped>
.el-header{
  background-color: white;
  text-align: left;
  padding-top: 12px;
  padding-bottom: 5px;
  padding-left: 5px;
}
.el-footer{
  background-color: white;
}
</style>>
</style>