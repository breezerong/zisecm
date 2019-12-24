
<template>
<div>
  <el-dialog
      :visible.sync="visible"
      :append-to-body="true"
      :close-on-click-modal="false"
      @open="refreshData"
      @close="closeDialog"
      title="备份条件"
      width="60%"
    >
    
    <el-row>
      <el-button @click="addplus" icon="el-icon-plus">添加</el-button>
      <el-button @click="removeminus" icon="el-icon-minus">删除</el-button>
        
    </el-row>
      <el-row :key="index" v-for="(item,index) in formtips">
            <el-col :span='3'>
              <el-select v-model="item.relation" v-if="index!=0">
                <div v-for="relation in relations">
                  <el-option :label="relation.label" :value="relation.val"></el-option>
                </div>
              </el-select>
            </el-col>
            <el-col :span="3">
              <el-select v-model="item.column">
                <div v-for="col in columns">
                  <el-option :label="col.label" :value="col.attrName"></el-option>
                </div>
              </el-select>
            </el-col>
            <el-col :span="3">
              <el-select v-model="item.condition">
                    <div v-for="condition in changeColumn(item.column,item)">
                      <el-option :label="condition.label" :value="condition.val"></el-option>
                    </div>
                </el-select>
            </el-col>
            <el-col :span="4">
              <el-input v-model="item.val"></el-input>
            </el-col>
        </el-row>
    
    
    <div class="formsearch">
        <button type="button" @click="onOk">确定</button>
    </div>
         
  </el-dialog>
  <el-row>
    <el-col>
      <el-input v-model="inputValue"></el-input>
      <input value="value1" type="hidden" />
    </el-col>
    <el-col>
      <el-button icon="el-icon-user-solid" @click="clickShowDialog">高级搜索</el-button>
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
    }
  },
  
  methods: {
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
      let sql="";
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
      _self.inputValue=sql;
      _self.$emit("change", _self.inputValue);
      _self.visible=false;
      
    },
    refreshData() {
      this.loadColumnInfo();

    },
    clickShowDialog() {
      this.visible = true;
    },
    closeDialog() {
      this.visible = false;
    },

    // 加载表单
    loadColumnInfo()
    {
      let _self = this;
      _self.loading = true;
      if(_self.myItemId != '')
      {
        _self.myTypeName = "";
      }
      var m = new Map();
      m.set('itemInfo',"卷盒");//_self.myItemId+_self.myTypeName);//ID 或类型
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

/*临时样式*/
  ul,li{
    list-style: none;
    margin:0;
    padding:0;
  }
    .retrievalmian{
        margin: 20px;
        width:75%;
    }
    /**/
    /*.retrievaltitle{*/
        /*background: #FCFCFC;*/
    /*}*/
    .retrievaltitle .tabbtn:first-child{
        margin-right: -5px;
    }
    .retrievaltitle .tabbtn:hover{
       text-decoration: none;
    }
    .retrievaltitle .tabbtn{
        padding: 2px 8px;
        background: #FBFBFB;
        border: 1px solid #DFDFDF;
        margin-bottom: -1px;
    }
    .retrievaltitle .tabbtn.checked{
        color: #E50F10;
        padding-top: 8px;
        border-bottom: 1px solid #FBFBFB;
    }
    .retrievalbar{
        border: 1px solid #E5E5E5;
        background: #FCFCFC;
    }
    .formbody{
        position: relative;
        margin: 10px 0px;
    }
    .formoperate{
        position: absolute;
        top:10px;
        right: 20px;
    }
 
    .formoperate .tipsicon{
        color: #59A4D2;
        display: inline-block;
        line-height: 15px;
        cursor: pointer;
        margin-left: 15px;
        width: 20px;
        height: 20px;
        /*临时*/
        text-align: center;
        border: 1px solid #59A4D2;
    }
    
    .formline{
        padding: 5px 30px;
    }
    .formtips{
        display: inline-block;
        margin-right: 22px;
    }
    .formgroup{
        display: inline-block;
    }
    .formcontrol{
        border: 1px solid #999;
        width: 80px;
        height: 25px;
    }
    .forminp{
        border: 1px solid #146AC4;
        width: 120px;
        height: 25px;
    }
    .formcontrollarg{
        width:120px;
        height: 25px;
    }
    .formsearch{
        position: absolute;
        bottom:10px;
        right: 20px;
    }
    .retrievalsearch{
        padding: 5px 20px;
    }
    .formgroup .catalog{
        border: 1px solid #999;
        width:120px;
        height: 25px;
        cursor: pointer;
        background-position: 95% 45%;
    }
    .cataloghint{
        position: relative;
    }
    .cataloglist{
        position: absolute;
        top: -1px;
        left: 0;
        padding: 0;
        border: 1px solid #999;
        background: #fff;
        z-index: 10;
        width: 120px;
    }
    .cataloglist li{
        padding:2px 5px;
    }
    .cataloglist li:hover{
        background: #1e90ff;
    }
    .checkbox {
        margin:0px;
    }


</style>
