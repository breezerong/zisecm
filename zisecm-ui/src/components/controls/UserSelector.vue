<template>
  <div>
        <table border="0" width="100%" >
          <tr>
            <td>
              <table border="0" width="100%" class="topbar">
                <tr>
                  <td align="left" width="160px">
                    <el-input  v-model="inputkey" placeholder="请输入属性名关键字" @change="search" prefix-icon="el-icon-search"></el-input>
                  </td>
                  <td>
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
            height="320px"
            style="width: 100%">
             <el-table-column label="序号" width="60">
              <template slot-scope="scope">
                <span>{{(currentPage-1) * pageSize + scope.$index+1}}</span>
              </template>
            </el-table-column>
            <el-table-column label="用户名" width="150">
              <template slot-scope="scope">
                <span>{{scope.row.name}}</span>
              </template>
            </el-table-column>
            <el-table-column label="登录名" width="120">
              <template slot-scope="scope">
                <span>{{scope.row.loginName}}</span>
              </template>
            </el-table-column> 
            <el-table-column label="邮件" width="240">
              <template slot-scope="scope">
                <span>{{scope.row.email}}</span>
              </template>
            </el-table-column>
            <el-table-column label="说明" width="120">
              <template slot-scope="scope">
                <span>{{scope.row.description}}</span>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="80">
              <template slot-scope="scope">
                <el-button :plain="true" type="primary" size="small" icon="edit" @click="selectitem(scope.row)">选择</el-button>
              </template>
            </el-table-column>
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
        </td>
      </tr>
    </table>
  </div>
</template>

<script type="text/javascript">
export default {
  name: "UserSelector",
  data() {
    return {
      dataList: [],
      dataListFull: [],
      inputkey: "",
      loading: false,
      currentItem: "",
      pageSize: 20,
      itemCount: 0,
      currentPage: 1
    };
  },
  props: {
    noGroup: {type:[String,Number],required:false},
    groupId: {type:[String,Number],required:false}
  },
  mounted() {
    let _self = this;
    var psize = localStorage.getItem("docPageSize");
    if (psize) {
      _self.pageSize = parseInt(psize);
    }
    _self.refreshData();
  },
  methods: {
    refreshData() {
      let _self = this;
      // console.log("_self.noGroup:"+_self.noGroup);
      var m = new Map();
      m.set("condition", _self.inputkey);
      m.set("noGroup",_self.noGroup);
      m.set("groupId",_self.groupId);
      m.set("pageSize", _self.pageSize);
      m.set("pageIndex", (_self.currentPage - 1) * _self.pageSize);
      let urlStr = "/zisecm/admin/getUsers";
      if(_self.groupId&&_self.groupId!="")
      {
        urlStr = "/zisecm/admin/getRoleUsers";
      }
      // console.log('pagesize:', _self.pageSize);
      _self
        .axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data: JSON.stringify(m),
          url: urlStr
        })
        .then(function(response) {
          _self.dataList = response.data.datalist;
          _self.dataListFull = response.data.datalist;
          _self.loadPageInfo();
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    // 分页 页数改变
    handleSizeChange(val) {
      this.pageSize = val;
      localStorage.setItem("docPageSize", val);
      this.refreshData();
      //console.log('handleSizeChange', val);
    },
    // 分页 当前页改变
    handleCurrentChange(val) {
      this.currentPage = val;
      this.refreshData();
    },
    // 加载页数 暂时未处理查询条件
    loadPageInfo() {
      let _self = this;
      var m = new Map();
      m.set("noGroup",_self.noGroup);
      m.set("groupId",_self.groupId);
      m.set("condition", _self.inputkey);
      let urlStr = "/zisecm/admin/getUserCount";
      if(_self.groupId&&_self.groupId!="")
      {
        urlStr = "/zisecm/admin/getRoleUserCount";
      }
      _self
        .axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data: JSON.stringify(m),
          url: urlStr
        })
        .then(function(response) {
          _self.itemCount = response.data.itemCount;
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    selectitem(indata) {
      let _self = this;
      _self.$emit('onuserselected',indata);
      
    },
    search() {
      let _self = this;
      let tab = _self.dataListFull;
      _self.dataList = [];
      var i;
      if (_self.inputkey != "") {
        for (i in tab) {
          if (_self.inputkey != "") {
            if (
              tab[i].name.indexOf(_self.inputkey) >= 0 ||
              tab[i].loginName.indexOf(_self.inputkey) >= 0
            ) {
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
