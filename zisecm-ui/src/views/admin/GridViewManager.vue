<template>
  <div>
        <el-dialog title="添加" :visible.sync="dialogVisible">
          <el-form :model="form">
            <el-form-item label="名称" :label-width="formLabelWidth">
              <el-input v-model="form.name" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="说明" :label-width="formLabelWidth">
              <el-input v-model="form.description" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="条件" :label-width="formLabelWidth">
              <el-input v-model="form.condition" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="排序" :label-width="formLabelWidth">
              <el-input v-model="form.orderBy" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="类型" :label-width="formLabelWidth">
              <!-- <el-input v-model="form.typeName" auto-complete="off"></el-input> -->
            
              <el-select v-model="form.typeName" filterable >
              <div v-for="item in typeNameList" :key="item.NAME">
                <el-option :label="item.NAME" :value="item.NAME"></el-option>
              </div>
            </el-select>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">{{$t('application.cancel')}}</el-button>
            <el-button type="primary" @click="additem(form)">确 定</el-button>
          </div>
        </el-dialog>
              <el-container>
      <el-header>
        <el-row class="topbar">
          <el-col :span="4">
            <el-input
              v-model="inputkey"
              placeholder="请输入关键字"
              @change="search"
              prefix-icon="el-icon-search"
            ></el-input>
          </el-col>
          <el-col :span="20" style="text-align:left;">
            &nbsp; 
            <el-button
              type="primary"
              icon="el-icon-edit"
              plain
              @click="dialogVisible = true"
            >{{$t('application.new')}}</el-button>
          </el-col>
        </el-row>
      </el-header>
      <el-main>
    <el-table
                :data="dataList"
                border
                :height="tableHeight"
                v-loading="loading"
                row-style="height: 0"
                cell-style="padding:0"
                style="width: 100%">
        <el-table-column
                label="行号"
                type="index"
                width="60">
              </el-table-column>
        <el-table-column label="名称" width="180" >
           <template slot-scope="scope">
            <el-input  v-model="scope.row.name"></el-input>
          </template>
        </el-table-column>
        <el-table-column label="说明" width="180">
          <template slot-scope="scope">
            <el-input  v-model="scope.row.description"></el-input>
          </template>
        </el-table-column>
        <el-table-column label="条件"  min-width="20%">
          <template slot-scope="scope">
            <el-input  v-model="scope.row.condition"></el-input>
          </template>
        </el-table-column>
        <el-table-column label="排序"  min-width="20%">
          <template slot-scope="scope">
            <el-input  v-model="scope.row.orderBy"></el-input>
          </template>
        </el-table-column>
        <el-table-column label="类型" width="200">
          <template slot-scope="scope">
            <!-- <el-input  v-model="scope.row.typeName"></el-input> -->
            <el-select v-model="scope.row.typeName" filterable >
              <div v-for="item in typeNameList" :key="item.NAME">
                <el-option :label="item.NAME" :value="item.NAME"></el-option>
              </div>
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="操作"  width="320">
          <template slot-scope="scope">
            <router-link :to="{path:'/managercenter/gridviewitemmanager',query:{parentid:scope.row.id,name:scope.row.name}}"><el-button :plain="true" type="info" size="small" icon="edit">查看</el-button></router-link>
            &nbsp; 
            <el-button :plain="true" type="primary" size="small" icon="edit" @click="saveitem(scope.row)">保存</el-button>
            <el-button :plain="true" type="warning" size="small" icon="edit" @click="copyitem(scope.row)">复制</el-button>
            <el-button :plain="true" type="danger" size="small" icon="delete" @click="delitem(scope.row)">{{$t('application.delete')}}</el-button>
          </template>
        </el-table-column>
    </el-table>
      </el-main>
              </el-container>
  </div>
</template>

<script type="text/javascript">
import EcmDataSelect from '@/components/ecm-data-select'
export default {
  name: "GridViewManager",
  permit: 9,
  components:{
    EcmDataSelect
  },
  data() {
    return {
      dataList: [],
      dataListFull: [],
      inputkey: "",
      loading: false,
      dialogVisible: false,
      tableHeight: window.innerHeight - 135,
      form: {
        name: "",
        description: "",
        condition: "",
        orderBy:""
      },
      formLabelWidth: "120px",
      typeNameList:[]
    };
  },
   created(){
     
    let _self = this;
    _self.loading = true;
    axios.get('/admin/getGridView')
      .then(function(response) {
        _self.dataListFull = response.data.data;
        _self.dataList = response.data.data;
        _self.loading = false;
      })
      .catch(function(error) {
        console.log(error);
        _self.loading = false;
      });
      let queryObj={"queryName":"类型列表"}
    axios.post("/query/getquery",JSON.stringify(queryObj)).then(function(resp){
      _self.typeNameList = resp.data.data
    }).catch(function(error) {
        console.log(error);
    });
  },
  methods: {
    refreshData() {
      let _self = this;
      _self.loading = true;
      axios.get('/admin/getGridView')
      .then(function(response) {
        _self.dataListFull = response.data.data;
        _self.dataList = response.data.data;
        _self.loading = false;
      })
      .catch(function(error) {
        console.log(error);
        _self.loading = false;
      });
    },
    saveitem(indata) {
      let _self = this;
      axios.post('/admin/updateGridView',JSON.stringify(indata))
      .then(function(response) {
        _self.$message("保存成功!");
      })
      .catch(function(error) {
        console.log(error);
      });
    },
    delitem(indata) {
      let _self = this;
      axios.post('/admin/deleteGridView',JSON.stringify(indata))
      .then(function(response) {
        _self.$message("删除成功!");
        _self.refreshData();
      })
      .catch(function(error) {
        console.log(error);
      });
    },
    additem(indata) {
      let _self = this;
      axios.post('/admin/newGridView',JSON.stringify(indata))
      .then(function(response) {
          _self.dialogVisible = false;
          _self.refreshData();
      })
      .catch(function(error) {
        console.log(error);
      });
    },
    showitem(indata) {
      let _self = this;
    },
    copyitem(indata) {
      let _self = this;
      axios.post('/admin/copyGridView',JSON.stringify(indata))
      .then(function(response) {
          _self.$message("复制成功!");
          _self.dialogVisible = false;
          _self.refreshData();
      })
      .catch(function(error) {
        console.log(error);
      });
    },
    search() {
      let _self = this;
      _self.dataList = _self.dataListFull.filter(function(item){
          return item.name.match(_self.inputkey) || item.description.match(_self.inputkey);
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
.el-header {
  background-color: #e8eaeb;
  height: 42px !important;
}
.el-main{
  padding:5px;
}
.el-row {
  padding-bottom: 10px;
}
</style>
