<template>
  <div>
        <el-dialog title="导入" :visible.sync="dialogVisible">
          <el-form :model="form">
            <el-form-item label="名称" :label-width="formLabelWidth">
              <el-input v-model="form.name" auto-complete="off" placeholder="不输入名称，系统自动设置名称为电子文件名"></el-input>
            </el-form-item>
            <div style="height:200px;overflow-y:scroll; overflow-x:scroll;">
              <el-upload
                :limit="100"
                :file-list="fileList" 
                action=""
                :on-change="handleChange"
                :auto-upload="false"
                :multiple="true">
                <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
              </el-upload>
            </div>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="uploaddrawing()">开始导入</el-button>
          </div>
        </el-dialog>
      <table border="0" width="100%">
          <tr>
            <td class="navbar">
              /图纸管理/错误处理
            </td>
          </tr>
          <tr>
            <td>
              <table border="0" width="100%" class="topbar">
                <tr>
                  <td align="left" width="300px">
                    <el-input v-model="inputkey" placeholder="请输入关键字" @change="search()" prefix-icon="el-icon-search"></el-input>
                  </td>
                  <td>
                    <el-button type="primary" icon="el-icon-video-play" @click="updateStatus()">重新签章</el-button>
                    <el-button type="primary" icon="el-icon-delete" @click="deleteDrawing()">删 除</el-button>
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
                :height="tableHeight"
                v-loading="loading"
                @selection-change="selectChange"
                style="width: 100%">
              <el-table-column type="selection" width="40" @selection-change="selectChange">
              </el-table-column> 
              <el-table-column type="index" width="50">
              </el-table-column>      
              <el-table-column prop="NAME" label="名称" sortable min-width="20%"  >
              </el-table-column>
              <el-table-column prop="C_PROJECT" label="项目" sortable min-width="15%" >
              </el-table-column>
              <el-table-column prop="C_COMMENT" label="错误信息" min-width="20%" >
              </el-table-column>
              <el-table-column prop="CREATION_DATE" label="创建时间" sortable :formatter="dateFormat" width="180">
              </el-table-column>
              <el-table-column label="操作"  width="120">
                <template slot-scope="scope">
                  <el-button :plain="true" type="success" size="small" icon="save" @click="showitem(scope.row)">查看</el-button>
                </template>
              </el-table-column>
            </el-table>
            <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="currentPage"
              :page-sizes="[20, 50, 100, 200]"
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
  name: "ImportDoc",
  permit: 1,
  data() {
    return {
      currentData: [],
      dataList: [],
      dataListFull: [],
      inputkey: "",
      pageSize: 20,
      itemCount: 0,
      selectedItems:[],
      fileList:[],
      currentPage: 1,
      loading: false,
      dialogVisible: false,
      dialogWfVisible: false,
      tableHeight: window.innerHeight - 170,
      form: {
        NAME: "",
        TYPE_NAME:"图纸"
      },
      formLabelWidth: "120px"
    };
  },
   created(){ 
    let _self = this;
    var psize = localStorage.getItem("docPageSize");
    if (psize) {
      _self.pageSize = parseInt(psize);
    }
    _self.refreshData();
    },
  methods: {
    handleChange(file, fileList){
      this.fileList = fileList;
    },
    refreshData() {
      let _self = this;
      _self.loading = true;
      var m = new Map();
      m.set("status","错误");
      m.set("condition", _self.inputkey);
      m.set("pageSize", _self.pageSize);
      m.set("pageIndex", (_self.currentPage - 1) * _self.pageSize);
      _self.axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        method: 'post',
        data: JSON.stringify(m),
        url: '/zisecm/drawing/getDrawing'
      })
      .then(function(response) {
        _self.dataList = response.data.data;
        console.log(JSON.stringify(_self.dataList));
        _self.dataListFull = response.data.data;
        //加载分页信息
        _self.itemCount = response.data.pager.total;
        _self.loading = false;
      })
      .catch(function(error) {
        console.log(error);
        _self.loading = false;
      });
    },
    // 表格行选择
    selectChange(val) 
    {
      this.selectedItems = val;
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
    uploaddrawing() {
      let _self = this;
      let formdata = new FormData();
      formdata.append("metaData",JSON.stringify(_self.form));
      _self.fileList.forEach(function (file) {
                formdata.append("files", file.raw, file.name);
            });
      _self.axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        datatype: 'json',
        method: 'post',
        data: formdata,
        url: '/zisecm/drawing/uploadDrawing'
      })
      .then(function(response) {
        _self.dialogVisible = false;
        _self.refreshData();
        _self.$message("导入成功!");
      })
      .catch(function(error) {
        console.log(error);
      });
    },
    updateStatus(){//重签
      let _self = this;
      var obj={"STATUS":"已审批","list":""};
      var ids=new Array();
      for(var i=0;i<_self.selectedItems.length;i++){
        ids.push(_self.selectedItems[i].ID);
      }
      obj.list=JSON.stringify(ids);
      _self.axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        datatype: 'json',
        method: 'post',
        data: obj,
        url: '/zisecm/drawing/updateStatus'
      })
      .then(function(response) {
        _self.refreshData();
        _self.$message("流程发起成功!");
      })
      .catch(function(error) {
        console.log(error);
      });
    },
    //删除
      deleteDrawing(){
      let _self = this;
      var ids=new Array();
      for(var i=0;i<_self.selectedItems.length;i++){
        ids.push(_self.selectedItems[i].ID);
      }
      console.log("ids=="+JSON.stringify(ids))
      _self.axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        datatype: 'json',
        method: 'post',
        data: JSON.stringify(ids),
        url: '/zisecm/drawing/delDrawing'
      })
      .then(function(response) {
        _self.refreshData();
        _self.$message("删除成功!");
      })
      .catch(function(error) {
        console.log(error);
      });
    },
    dateFormat(row, column) {
        let datetime = row.CREATION_DATE;
        if(datetime){
          datetime = new Date(datetime);
          let y = datetime.getFullYear() + '-';
          let mon = datetime.getMonth()+1 + '-';
          let d = datetime.getDate();
          return y + mon + d + " "+datetime.getHours()+":"+datetime.getMinutes()+":"+datetime.getSeconds();
        }
        return ''
      },
    search() {
      let _self = this;
      _self.dataList = _self.dataListFull.filter(function(item){
          return item.NAME.match(_self.inputkey);
        }
      );
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
