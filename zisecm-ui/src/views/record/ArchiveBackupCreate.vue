<template>
<div>
    <el-form label-position="right" label-width="100px">
      <!--
      <div>
        <el-button :plain="true" type="primary" size="small" icon="edit" @click="loaddata()">刷新数据</el-button>
      </div>
      -->
      <el-row>
        <!-- <el-col style="padding:3px;text-align:left">
          <el-form-item>{{typeName}}</el-form-item></el-col> -->
        
      <template v-for="(item,itemIndex) in dataList">
        <el-col :span="showCellValue(item)" v-bind:key="itemIndex">
          <el-form-item :hidden="item.isHide" :label="item.label" :rules="[{required:item.required,message:'必填',trigger:'blur'}]">
                <el-input v-if="item.controlType=='TextBox'" type="text" :name="item.attrName" v-model="item.defaultValue"></el-input>
                <el-input v-if="item.controlType=='TextArea'" type="textarea" :name="item.attrName" v-model="item.defaultValue"></el-input>
                <el-input v-else-if="item.controlType=='Integer'" type="number" :name="item.attrName" v-model="item.defaultValue"></el-input>
                <el-checkbox v-else-if="item.controlType=='Boolean'"  :name="item.attrName" v-model="item.defaultValue"></el-checkbox>
                <el-date-picker v-else-if="item.controlType=='Date'" :name="item.attrName" v-model="item.defaultValue" type="date" placeholder="选择日期" style="display:block;"></el-date-picker>
                <el-select  :name="item.attrName"
                v-else-if="item.controlType=='Select' || item.controlType=='ValueSelect' || item.controlType=='Department' || item.controlType=='SQLSelect'" 
                v-model="item.defaultValue" :placeholder="'请选择'+item.label" :disabled="item.readOnly" :multiple="item.isRepeat" style="display:block;"
                @change="((val)=>{onSelectChange(val, item)})">
                      <div v-for="(name,nameIndex) in item.validValues" :key="nameIndex+'N'">
                        <el-option :label="name" :value="name" :key="nameIndex"></el-option>
                      </div>
                  </el-select>
                <UserSelectInput v-else-if="item.controlType=='UserSelect'" v-model="item.defaultValue" v-bind:inputValue="item.defaultValue" v-bind:isRepeat="item.isRepeat"></UserSelectInput>
                <!-- <UserSelectInput v-else-if="item.controlType=='UserSelect'" v-model="item.defaultValue"></UserSelectInput> -->
                <AddCondition v-if="item.controlType=='advSearch'" v-model="item.defaultValue" v-bind:inputValue="item.defaultValue" ></AddCondition>
          </el-form-item>
        </el-col>
      </template>
    </el-row>
      <!-- <el-form-item style="float:left"  label="查询条件" >
        <el-input ref='conditionsSql' :value="sqlStr"></el-input> <AddCondition ref='addCondition' @showsql='showsql'></AddCondition>
      </el-form-item> -->
      
    </el-form>
  </div>
</template>

<script type="text/javascript">
import UserSelectInput from '@/components/controls/UserSelectInput'
import AddCondition from '@/views/record/AddCondition'
export default {
  name: "ShowProperty",
  components: {
    UserSelectInput:UserSelectInput,
    AddCondition:AddCondition
  },
  data() {
    return {
      tableHeight: window.innerHeight - 98,
      currentLanguage: "zh-cn",
      permit:5,
      dataList: {
        rowdata: {
          parentId:"",
          label:"",
          attrName: "",
          searchable: "1",
          required: "0",
          readOnly: "0",
          controlType: "TextBox",
          widthType: "2",
          validValues:[],
          orderIndex: 1,
          defaultValue: ""
        }
      },
      file:"",
      fileList:[],
      myItemId: '',
      myTypeName: "备份批次",
      myFolderId: this.folderId,
      parentDocId:'',
      sqlStr:'',
      clientPermission: 1
    };
  },
  mounted() {
    this.currentLanguage = localStorage.getItem("localeLanguage") || "zh-cn";
    this.loadFormInfo();
    this.clientPermission = sessionStorage.getItem(
        "access-clientPermission"
      );
  },
  props: {
   
  },
  methods: {
    // showsql(sqlWhere){
    //   this.sqlStr=sqlWhere;
    // },
    showCellValue(item){
      //console.log(item);
      var v = 24/ parseInt(item.widthType);
      //console.log(v);
      return v;
    },
    handleChange(file, fileList){
      this.file = file;
        //console.log(file);
       // console.log(fileList);
    },
    onSelectChange(val, item){
      if(item.enableChange){
        let i =0;
        for(i in this.dataList){
          let row = this.dataList[i];
          if(row.dependName == item.attrName){
            row.defaultValue = "";
            this.loadChildList(row, item.defaultValue);
          }
        }
      }
    },
    loadChildList(item, val){
      let _self = this;
      var m = new Map();
      m.set("queryName", item.queryName);
      m.set("dependValue", val);
      _self.loading = true;
      axios.post("/dc/getSelectList",JSON.stringify(m))
        .then(function(response) {
          if(response.data.code == 1){
            item.validValues = response.data.data;
          }
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    // 加载表单
    loadFormInfo()
    {
      let _self = this;
      _self.loading = true;
      if(_self.myItemId != '')
      {
        _self.myTypeName = "";
      }
      var m = new Map();
      m.set('itemInfo',_self.myTypeName+_self.myItemId);//ID 或类型
      m.set('lang',_self.getLang());
      //console.log(_self.itemId+","+_self.myItemId+","+_self.myTypeName+","+_self.folderId);
      axios.post("/dc/getFormItem",JSON.stringify(m))
        .then(function(response) {

          _self.bindData(response.data.data);
          _self.file=[];
          _self.fileList = [];
          //console.log(JSON.stringify(response.data.data));
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    // 数据必填校验
    validFormValue()
    {
      let _self = this;
      let dataRows = _self.dataList;
      var i;
      var ret = true;
      var msg = "";
      for (i in dataRows) {
        if(dataRows[i].required && (dataRows[i].defaultValue==null||dataRows[i].defaultValue==""))
        {
          msg += "["+dataRows[i].label+"] ";
          ret = false;
        }
      }
      if(!ret)
      {
        _self.$message(msg+"不能为空！");
      }
      return ret;
    },
    saveItem()
    {
      let _self = this;
      if(!_self.validFormValue()){
        return;
      }
      var m = new Map();
      let dataRows = _self.dataList;
      var i;
      for (i in dataRows) {
        if(dataRows[i].attrName && dataRows[i].attrName !='')
        {
          if(dataRows[i].attrName !='FOLDER_ID'&&dataRows[i].attrName !='ID')
          {
            var val = dataRows[i].defaultValue;
            if(val && dataRows[i].isRepeat){
              var temp = "";
             // console.log(val);
              for(let j=0,len=val.length;j<len;j++){
                temp = temp + val[j]+";";
                //console.log(temp);
              }
              temp = temp.substring(0,temp.length-1);
              val = temp;
              console.log(val);
            }
            m.set(dataRows[i].attrName, val);
          }
        }
      }
      if(_self.myItemId!='')
      {
        m.set('ID',_self.myItemId);
      }
      if(_self.myTypeName!='')
      {
        m.set('TYPE_NAME',_self.myTypeName);
        m.set('FOLDER_ID',_self.myFolderId);
      }
      let formdata = new FormData();
      formdata.append("metaData",JSON.stringify(m));
      
      if(_self.file!="")
      {
        //console.log(_self.file);
        formdata.append("uploadFile",_self.file.raw);
      }
      // console.log(JSON.stringify(m));
      if(_self.myItemId=='')
      {
        axios.post("/dc/newDocument",formdata,{
            'Content-Type': 'multipart/form-data'
          })
        .then(function(response) {
          let code = response.data.code;
          //console.log(JSON.stringify(response));
          if(code==1){
            _self.$emit('onSaved','new');
          }
          else{
             _self.$message(_self.$t('message.newFailured'));
          }
        })
        .catch(function(error) {
          _self.$message(_self.$t('message.newFailured'));
          console.log(error);
        });
      }
      else
      {
        if(_self.permit<5){
          _self.$message("您没有修改当前文件属性的权限!");
          return ;
        }
        if(_self.clientPermission && _self.clientPermission<4){
          if(m.get("STATUS")&&(m.get("STATUS")=="利用"||m.get("STATUS")=="销毁")){
            _self.$message("请先下架后再修改属性!");
            return ;
          }
        }
        axios.post("/dc/saveDocument",JSON.stringify(m))
        .then(function(response) {
          let code = response.data.code;
          //console.log(JSON.stringify(response));
          if(code==1){
            _self.$emit('onSaved','update');
          }
          else{
             _self.$message(_self.$t('message.saveFailured'));
          }
        })
        .catch(function(error) {
          _self.$message(_self.$t('message.saveFailured'));
          console.log(error);
        });
      }
    },
    bindData(indata)
    {
      let _self = this;
      if(_self.myItemId =='')
      {
        _self.dataList = indata;
      }
      else
      {
        axios.post("/dc/getDocument",_self.myItemId)
          .then(function(response) {
            let tab = response.data.data;
            _self.permit =  response.data.permit;
            //console.log(JSON.stringify(tab));
            let frmItems = indata;
            var i;
            for (i in frmItems) {
              let val = tab[frmItems[i].attrName];
              if(val && frmItems[i].isRepeat){
                val = val.split(";");
              }
              frmItems[i].defaultValue = val;
              // if("TYPE_NAME"==frmItems[i].attrName){
              //   _self.typeName=frmItems[i].attrName；
              // }
              //console.log(JSON.stringify(frmItems[i].attrName)+":"+frmItems[i].defaultValue);
            }
            _self.dataList = frmItems;
          })
          .catch(function(error) {
            console.log(error);
        });
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
.el-date-editor.el-input, .el-date-editor.el-input__inner {
    width: 140px;
}
</style>
