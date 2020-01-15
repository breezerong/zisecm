
<template>
<div>
  <el-dialog
      :visible.sync="visible"
      :append-to-body="true"
      :close-on-click-modal="false"
      @open="getTypeNames('AdvSearchTypes')"
      @close="closeDialog"
      title="查询条件"
      width="60%"
    >
    <el-row>
      <el-col>请选择类型<font style="color:red;">*</font>：</el-col>
      <el-col>
        <el-select v-model="typeName" @change="refreshData(typeName)">
                <div v-for="options in typeNameOptions">
                  <el-option :label="options" :value="options"></el-option>
                </div>
              </el-select>
      </el-col>
    </el-row>
    <el-row>
      <el-button @click="addplus" icon="el-icon-plus">添加</el-button>
      <el-button @click="removeminus" icon="el-icon-minus">删除</el-button>
    </el-row>
      <el-row :key="index" v-for="(item,index) in formtips">
            <el-col :span='3' class="topbar-button">
              <el-select v-model="item.relation" v-if="index!=0">
                <div v-for="relation in relations">
                  <el-option :label="relation.label" :value="relation.val"></el-option>
                </div>
              </el-select>
            </el-col>
            <el-col :span="5" class="topbar-button">
              <el-select v-model="item.column">
                <div v-for="col in columns">
                  <el-option v-if="col.isHide==false" :label="col.label" :value="col.attrName"></el-option>
                </div>
              </el-select>
            </el-col>
            <el-col :span="3" class="topbar-button">
              <el-select v-model="item.condition">
                    <div v-for="condition in changeColumn(item.column,item)">
                      <el-option :label="condition.label" :value="condition.val"></el-option>
                    </div>
                </el-select>
            </el-col>
            <el-col :span="8" class="topbar-button">
              <el-input v-model="item.val"></el-input>
            </el-col>
        </el-row>
    
    
    <div style="text-align:right">
        <el-button   @click="onOk">确定</el-button>
    </div>
         
  </el-dialog>
  <el-row>
    <el-col :span="18" :style="{display:inputType=='hidden'?'none':''}">
      <el-input v-model="inputValue" :type="inputType"></el-input>
      <input value="value1" type="hidden" />
    </el-col>
    <el-col :span="6">
      <el-button icon="el-icon-search" @click="clickShowDialog">高级搜索</el-button>
    </el-col>
  </el-row>
</div>
</template>
<script>
export default {
  data() {
    return {
      currentLanguage: this.getLang(),
      visible: false,
      findValue: "",
      dataList: [],
      rightList: [],
      tranList2: [],
      tranList: [],
      rightNameList: "",
      rightListId: [],
      formtips:[],
      columns:[],
      typeNameOptions:[],
      typeName:"",
      relations:[{"label":"且","val":"and"},{"label":"或","val":"or"}],
      intCondition:[{"label":"大于","val":">"},{"label":"小于","val":"<"},
      {"label":"大于等于","val":">="},{"label":"小于等于","val":"<="},{"label":"不等于","val":"!="}
      ,{"label":"等于","val":"="}],
      textCondition:[{"label":"包含","val":"like"},{"label":"等于","val":"="},
      {"label":"不等于","val":"!="},{"label":"以...开始","val":"...like"},
      {"label":"以...结束","val":"like..."}],
      dateCondition:[{"label":"大于","val":">"},{"label":"小于","val":"<"},
      {"label":"大于等于","val":">="},{"label":"小于等于","val":"<="},{"label":"不等于","val":"!="},{"label":"等于","val":"="}],
      boolCondition:[{"label":"不等于","val":"!="},{"label":"等于","val":"="}],
      //conditions:[],
      /*[{"label":"包含","val":"like"},{"label":"等于","val":"="},{"label":"不等于","val":"!="},{"label":"大于","val":">"},
      {"label":"小于","val":"<"}],*/
      
      // form:{
      //   relation:"",
      //   column:"",
      //   condition:"",
      //   val:""
      // }
    };
  },
  model: {
    prop: "value1",
    event: "change"
  },
  mounted() {
    // this.getTypeNames("AdvSearchTypes");
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
    inputType:{
      type:String,
      default:'text'
    }
  },
  
  methods: {
    getTypeNames(keyName) {
      let _self = this;
      axios
        .post("/dc/getParameters", keyName)
        .then(function(response) {
          _self.typeNameOptions = response.data.data.AdvSearchTypes;
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    changeColumn(column,item){
      let conditions=[];
      let obj=null;
      this.columns.forEach(e=>{
        if(e.attrName==column){
          obj=e;
        }
      });
      if(obj==null){
        return null;
      }
      item.dataType=obj.controlType;
      if(obj.controlType=='Integer'){
        conditions=this.intCondition
      }else if(obj.controlType=='TextBox'||obj.controlType=='TextArea'||obj.controlType=='Select'){
        conditions=this.textCondition;
      }else if(obj.controlType=='Date'){
        conditions=this.dateCondition;
      }else{
        conditions=this.boolCondition;
      }
      return conditions;
    },
    addplus:function () {
        this.formtips.push(
          {
            relation:"",
            column:"",
            dataType:"",
            condition:"",
            conditions:[],
            val:""
          }
      );
    },
   removeminus:function () {
        this.formtips.splice(-1);
    },
    
    onOk(){
      let _self=this;
      let sql=" ";
      if(_self.typeName==null
      ||_self.typeName==''){
        _self.$message("请选择类型名称");
        return;
      }
      if(_self.typeName!='所有'){
        sql+=" TYPE_NAME='"+_self.typeName+"' and (";
      }
      _self.formtips.forEach(element => {
        sql=sql+element.relation+' '+element.column+' ';

        if(element.condition=='like'){
          sql=sql+element.condition+' \'%'+element.val+'%\' '
        }else if(element.condition=='='||element.condition=='!='){
          if(element.dataType=='Integer'){
             sql=sql+element.condition+' '+element.val+' '
          }else{
             sql=sql+element.condition+' \''+element.val+'\' '
          }
         
        }else if(element.condition=='>'||element.condition=='<'
        ||element.condition=='<='||element.condition=='>='){
          if(element.dataType=='Date'){
            sql=sql+element.condition+' \''+element.val+'\' '
          }else{
            sql=sql+element.condition+' '+element.val+' '
          }
        }else if(element.condition=='...like'){
          sql=sql+" like '"+element.val+"%' "
        }else if(element.condition=='like...'){
          sql=sql+" like '%"+element.val+"' "
        }
        else{
          sql=sql+element.condition+' '+element.val+' '
        }
        // +element.condition+' '+element.val
      });
      if(_self.typeName!='所有'){
        sql=sql+")";
      }
      
      if(_self.formtips.length==0){
        sql=" TYPE_NAME='"+_self.typeName+"'";
      }
      _self.inputValue=sql;
      _self.$emit("change", _self.inputValue);
      _self.$emit("sendMsg",_self.inputValue);
      _self.visible=false;
      
    },
    refreshData(name) {
      if(name){
        this.loadColumnInfo(name);
      }
      

    },
    clickShowDialog() {
      this.visible = true;
    },
    closeDialog() {
      this.visible = false;
    },

    // 加载表单
    loadColumnInfo(name)
    {
      let _self = this;
      _self.loading = true;
      if(_self.myItemId != '')
      {
        _self.myTypeName = "";
      }
      var m = new Map();
      m.set('itemInfo',name);//_self.myItemId+_self.myTypeName);//ID 或类型
      m.set('lang',_self.getLang());
      //console.log(_self.itemId+","+_self.myItemId+","+_self.myTypeName+","+_self.folderId);
      axios.post("/dc/getFormItem",JSON.stringify(m))
        .then(function(response) {

          _self.columns=response.data.data;
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },




    // loadColumnInfo(){
    //   let _self = this;
    //   _self.loading = true;
    //   var m = new Map();
    //   m.set('gridName','ArrangeInnerGrid');
    //   m.set('lang',_self.currentLanguage);
    //   _self.axios({
    //     headers: {
    //       "Content-Type": "application/json;charset=UTF-8"
    //     },
    //     method: 'post',
    //     data: JSON.stringify(m),
    //     url: "/dc/getGridViewInfo"
    //   })
    //     .then(function(response) {
          
    //       _self.columns = response.data.data;
          
    //     })
    //     .catch(function(error) {
    //       console.log(error);
         
    //     });
    // },
  }
};
</script>
<style scoped>
    li{
        padding-left: 10px;
    }
</style>
