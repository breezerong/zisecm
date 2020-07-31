<template>
  <DataLayout>
    <el-dialog :title="dialog.new.title" :visible.sync="dialog.new.visible" v-loading="dialog.new.loading">
      <el-form :model="form" label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('application.relationName')">
              <el-input v-model="form.relationName" auto-complete="off"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('application.gridName')">
              <SQLSelect v-model="form.gridName" queryName="表格列表"></SQLSelect>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('application.isReadOnly')">
              <el-switch v-model="form.readonly"></el-switch>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('application.type')">
              <SQLSelect v-model="form.typeName" queryName="文档类型"></SQLSelect>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('application.formName')">
              <SQLSelect v-model="form.formName" queryName="表单列表"></SQLSelect>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item :label="$t('application.description')">
              <el-input type="textarea" v-model="form.description" auto-complete="off"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialog.new.visible = false">{{$t('application.cancel')}}</el-button>
        <el-button type="primary" @click="additem(form)">{{$t('application.ok')}}</el-button>
      </div>
    </el-dialog>

    <template v-slot:header>
      <el-form :inline="true" size="medium">
        <el-form-item>
          <el-input v-model="inputkey" prefix-icon="el-icon-search" @input="changeValue"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-edit" plain @click="addView()">{{$t('application.new')}}</el-button>
        </el-form-item>
      </el-form>
    </template>
    <template v-slot:main="{layout}">
      <el-main>
        <el-table :data="table.datalist" v-loading="loading" v-bind="table.info" :height="layout.height-190">
          <el-table-column :label="$t('field.indexNumber')" key="#1" width="50">
            <template slot-scope="scope">
              <span>{{(table.pager.currentPage-1) * table.pager.pageSize + scope.$index+1}}</span>
            </template>
          </el-table-column>
          <el-table-column v-for="item in table.columns" v-bind="item" :key="item.prop"></el-table-column>
          <el-table-column :label="$t('application.operation')" width="180">
            <template slot-scope="scope">
                <el-button type="info" size="mini" icon="edit" @click="editView(scope.row)">{{$t('application.view')}}</el-button>
                <el-button type="danger" size="mini" icon="delete" @click="deleteItem(scope.row.id)">{{$t('application.delete')}}</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination v-bind="table.pager" :page-sizes="[10,20, 50, 100]"  layout="total, sizes, prev, pager, next, jumper" @size-change="onPagerSizeChange" @current-change="onPagerCurrentChange"></el-pagination>
      </el-main>
    </template>
  </DataLayout>
</template>

<script>
import { SQLSelect } from './components'
import DataLayout from '@/components/ecm-data-layout'
var components = { SQLSelect,DataLayout }
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
        info:{
          border:true,
          stripe:true,
          highlightCurrentRow:true
        },
        columns:[
          {prop:"relationName",label:this.$t('application.relationName')},
          {prop:"gridName",label:this.$t('application.gridName')},
          {prop:"readonly",label:this.$t('application.isReadOnly'),formatter:this.formatterReadOnly},
          {prop:"typeName",label:this.$t('application.type')},
          {prop:"formName",label:this.$t('application.formName')},
          {prop:"description",label:this.$t('application.description')}
        ],
        datalist:[],
        pager:{
          pageSize:20,
          currentPage:1,
          total:0
        }
      },
      language:""
    }
  },
  methods:{
    formatterReadOnly:function(row, column, cellValue){
      return cellValue?this.$t('application.yes'):this.$t('application.no');
    },
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
      this.dialog.new.title=this.$t('application.new')
      this.dialog.new.visible=true
    },
    editView:function(row){
      this.dialog.new.title=this.$t('application.edit')
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
      let url = _self.dialog.new.title==this.$t('application.new')?"/admin/uirelation/save":"/admin/uirelation/update"
      axios.post(url,JSON.stringify(formObj)).then(function(response) {
          if(response.data.code == 1){
            _self.$message({message:_self.$('message.saveSuccess'), type:'success'});
            _self.loadTable();
          }else{
            _self.$message.error(response.data.code.message);
          }
          _self.dialog.new.loading = false
          _self.dialog.new.visible = false
        }).catch(function(error) {
          console.log(error);
          _self.dialog.new.loading = false
        });

    },
    deleteItem:function(id){
      console.log(id)
      let _self = this;
      axios.post("/admin/uirelation/delete", id)
        .then(function(response) {
          _self.$message(_self.$('message.deleteSuccess'));
          _self.loadTable();
        })
        .catch(function(error) {
          console.log(error);
          _self.dialog.new.loading = false
        });
    },
    changeValue: function(val) {
      console.log(val)
      this.inputkey = val
      this.loadTable()
    },
    loadTable:function(){
      let _self = this;
      let condition = " GRID_NAME like '%"+_self.inputkey+"%' or TYPE_NAME like '%"+_self.inputkey+"%' or FORM_NAME like '%"+_self.inputkey+"%' or RELATION_NAME like '%"+_self.inputkey+"%' ";
      let pagerParam = _self.table.pager;
      pagerParam.currentPage=pagerParam.currentPage-1
      var m = new Map();
      m.set("condition", condition);
      m.set("pager",pagerParam)
      axios.post('/admin/uirelation/list',JSON.stringify(m)).then(function(response) {
        _self.table.datalist =[]
        let resultlist = response.data.data
        let pager = response.data.pager
        resultlist.forEach(function(item){
          item.readonly = item.readonly==1?true:false
          _self.table.datalist.push(item)
        })
        _self.table.pager.pageSizes=pager.pageSize
        _self.table.pager.currentPage=pager.pageIndex+1
        _self.table.pager.total=pager.total
        _self.loading = false;
      })
      .catch(function(error) {
        _self.$message(_self.$('message.operationFaild'));
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
    },
    onPagerCurrentChange(val) {
      this.table.pager.currentPage = val
      this.loadTable();
    },
    // 分页 页数改变
    onPagerSizeChange(val) {
      this.table.pager.pageSize = val
      this.loadTable();
    }
  },
  watch:{
    '$store.state.app.language':function(nv,ov){
      this.table.columns=[
          {prop:"relationName",label:this.$t('application.relationName')},
          {prop:"gridName",label:this.$t('application.gridName')},
          {prop:"readonly",label:this.$t('application.isReadOnly'),formatter:this.formatterReadOnly},
          {prop:"typeName",label:this.$t('application.type')},
          {prop:"formName",label:this.$t('application.formName')},
          {prop:"description",label:this.$t('application.description')}
        ]
    }
  },
  mounted(){
    this.language = localStorage.getItem("localeLanguage") || "zh-cn";
    this.loadTable()
  }
}
</script>

<style>

</style>
