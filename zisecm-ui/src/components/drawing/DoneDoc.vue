<template>
  <div>
      <table border="0" width="100%">
          <tr>
            <td class="navbar">
              /图纸管理/已完成导出
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
                    <el-button type="primary" icon="el-icon-download" @click="downloadModel()">批量导出</el-button>
                    <el-button type="primary" icon="el-icon-delete" @click="deleteDrawing()">删除</el-button>
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
              <el-table-column prop="NAME" label="名称" sortable min-width="30%" >
              </el-table-column>
              <el-table-column prop="C_PROJECT" label="项目" sortable min-width="20%" >
              </el-table-column>
              <el-table-column prop="CREATION_DATE" label="创建时间" sortable :formatter="dateFormatter" width="180">
              </el-table-column>
              <el-table-column label="操作"  width="120">
                <template slot-scope="scope">
                  <el-button :plain="true" type="success" size="small" icon="save" @click="downloadByOne(scope.row)">导出</el-button>
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
  name: "DoneDoc",
  permit: 1,
  data() {
    return {
      currentData: [],
      dataList: [],
      dataListFull: [],
      selectedItems:[],
      inputkey: "",
      loading: false,
      itemCount:0,
      dialogVisible: false,
      tableHeight: window.innerHeight - 170,
      form: {
        taskId: 0,
        result: "通过",
        message: ""
      },
      currentPage: 1,
      pageSize:20,
      formLabelWidth: "120px"
    };
  },
   created(){ 
    let _self = this;
    _self.refreshData();
    },
  methods: {
    refreshData() {
      
      let _self = this;
      _self.loading = true;
      var m = new Map();
      m.set("status","已导出");
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
        _self.dataListFull = response.data.data;
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
    dateFormatter(row, column) {
        let datetime = row.CREATION_DATE;
        return this.datetimeFormat(datetime);
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
      //下载
    downloadByOne: function (row) {

            console.log(row);
            let _self = this;
           

            _self.axios({
                headers: {
                  "Content-Type": "application/json;charset=UTF-8"
                },
                datatype: 'json',
                method: 'post',
                data: "["+row.ID+"]",
                url: '/zisecm/down',
                responseType: 'blob'
              }).then(response =>{
                    let data = response.data;
                if (!data) {
                    return
                }
                console.log(response);
                let contentDisposition = response.headers['content-disposition'];
                let fileName = contentDisposition.substring(contentDisposition.indexOf('=')+1);
                console.log('fileName=' + fileName);
                let url = window.URL.createObjectURL(new Blob([data]))
                let a = document.createElement('a')
                a.style.display = 'none'
                a.href = url
                a.setAttribute('download',fileName)
                document.body.appendChild(a)
                //点击下载
                a.click()
                // 下载完成移除元素
                document.body.removeChild(a);
                // 释放掉blob对象
                window.URL.revokeObjectURL(url);
                console.log("下载完成");
                }).catch(function (error) {
                console.log(error);
            });
        },
      //下载
    downloadModel: function () {

            let _self = this;
            console.log("down:"+JSON.stringify(_self.selectedItems));
            
            var list=new Array();
            for(var i=0;i< _self.selectedItems.length;i++){
              list.push(_self.selectedItems[i].ID);
            }

            _self.axios({
                headers: {
                  "Content-Type": "application/json;charset=UTF-8"
                },
                datatype: 'json',
                method: 'post',
                data: JSON.stringify(list),
                url: '/zisecm/down',
                responseType: 'blob'
              })
            // _self.axios.post('/zisecm/down'
            //     ,JSON.stringify(list),
            //     {responseType: 'blob'})
                .then(response =>{
                    let data = response.data;
                if (!data) {
                    return
                }
                console.log(response);
                let contentDisposition = response.headers['content-disposition'];
                let fileName = contentDisposition.substring(contentDisposition.indexOf('=')+1);
                console.log('fileName=' + fileName);
                let url = window.URL.createObjectURL(new Blob([data]))
                let a = document.createElement('a')
                a.style.display = 'none'
                a.href = url
                a.setAttribute('download',fileName)
                document.body.appendChild(a)
                //点击下载
                a.click()
                // 下载完成移除元素
                document.body.removeChild(a);
                // 释放掉blob对象
                window.URL.revokeObjectURL(url);
                console.log("下载完成");
                }).catch(function (error) {
                console.log(error);
            });
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
