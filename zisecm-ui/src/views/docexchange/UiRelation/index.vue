<template>
  <div>
    <el-dialog :title="dialog.new.title" :visible.sync="dialog.new.visible" v-loading="dialog.new.loading">     
      <el-form :model="form" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="相关名称">
              <el-input v-model="form.relationName" auto-complete="off"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">            
            <el-form-item label="表格名称">
              <SQLSelect v-model="form.gridName" queryName="表格列表"></SQLSelect>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否只读">     
              <el-switch v-model="form.readonly"></el-switch>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="类型名称">
               <SQLSelect v-model="form.typeName" queryName="文档类型"></SQLSelect>              
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="表单名称">
              <SQLSelect v-model="form.formName" queryName="表单列表"></SQLSelect>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="描述">
              <el-input type="textarea" v-model="form.description" auto-complete="off"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialog.new.visible = false">取 消</el-button>
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
                prefix-icon="el-icon-search"
              ></el-input>
            </el-col>
            <el-col :span="20" style="text-align:left;">
              &nbsp; 
              <el-button
                type="primary"
                icon="el-icon-edit"
                plain
                @click="dialog.new.visible = true"
              >新建</el-button>
            </el-col>
          </el-row>
      </el-header>
      <el-main>
        <el-table :data="table.datalist" border v-loading="loading">

          <el-table-column prop="relationName" label="相关名称"></el-table-column>
          <el-table-column prop="gridName" label="表格名称"></el-table-column>
          <el-table-column prop="readonly" label="是否只读" :formatter="formatBoolean"></el-table-column>
          <el-table-column prop="typeName" label="类型名称"></el-table-column>
          <el-table-column prop="formName" label="表单名称"></el-table-column>
          <el-table-column prop="description" label="描述"></el-table-column>
         
          <el-table-column label="操作" width="320">
            <template slot-scope="scope">
                <el-button type="info" size="small" icon="edit" @click="editView(scope.row)">查看</el-button>
                <el-button type="danger" size="small" icon="delete" @click="deleteItem(scope.row.id)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import { SQLSelect } from './components'
var components = { SQLSelect }
export default {
  components:components,
  data(){
    return {
      loading: false,
      dialogVisible: false,
      dialog:{
        new:{
          visible: false,
          loading: false,
          title:'新建'
        }
      },
      inputkey:"",
      form: {
        id: "",
        relationName: "",
        gridName: "",
        readonly: true,
        typeName: "",
        formName: "",
        description: ""
      },
      table:{
        datalist:[]
      }
    }
  },
  methods:{
    addView:function(){      
      this.form={
        id: "",
        relationName: "",
        gridName: "",
        readonly: true,
        typeName: "",
        formName: "",
        description: ""
      }
      this.dialog.new.title='新建'
      this.dialog.new.visible=true
    },
    editView:function(row){
      this.dialog.new.title='编辑'
      this.dialog.new.visible=true
      this.form = row
    },
    additem:function(form){
      let _self = this;
      _self.dialog.new.loading = true
      let formObj = form;
      if(formObj.readonly==true){
        formObj.readonly=1
      }else{
        formObj.readonly=0
      }
      let url = _self.dialog.new.title=='新建'?"/admin/uirelation/save":"/admin/uirelation/update"

      axios.post(url, JSON.stringify(formObj))
        .then(function(response) {
          if(response.data.code == 1){
            _self.$message({message:"保存成功!", type:'success'});
            _self.loadTable();
          }else{
            _self.$message.error('保存失败!');
          }
         
          _self.dialog.new.loading = false
          _self.dialog.new.visible = false
        })
        .catch(function(error) {
          console.log(error);
          _self.dialog.new.loading = false
        });
    },
    deleteItem:function(id){
      console.log(id)
      let _self = this;
      axios.post("/admin/uirelation/delete", id)
        .then(function(response) {
          _self.$message("删除成功!");
          _self.loadTable();
        })
        .catch(function(error) {
          console.log(error);
          _self.dialog.new.loading = false
        });
    },
    formatBoolean: function (row, column, cellValue) {
        var ret = ''  //你想在页面展示的值
        if (cellValue) {
            ret = "是"  //根据自己的需求设定
        } else {
            ret = "否"
        }
        return ret;
    },
    loadTable:function(){
      let _self = this;
      axios.post('/admin/uirelation/list').then(function(response) {
        _self.$message("刷新成功!")
        _self.table.datalist =[]
        let resultlist = response.data.data
        resultlist.forEach(function(item){          
          item.readonly = item.readonly==1?true:false
          _self.table.datalist.push(item)
        })
        console.log(_self.table.datalist)
        _self.loading = false;
      })
      .catch(function(error) {
        _self.$message("刷新失败!");
        console.log(error);
        _self.loading = false;
      });
    },
    initForm:function(){
      this.form={
        id: "",
        relationName: "",
        gridName: "",
        readonly: true,
        typeName: "",
        formName: "",
        description: ""
      }
    }
  },
  mounted(){
    this.loadTable()
  }
}
</script>

<style>

</style>