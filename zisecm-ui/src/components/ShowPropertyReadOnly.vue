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
        <el-collapse v-model="activeNames">
          <el-collapse-item v-for="(citem,cindex) in dataList" :title="citem.label" :name="citem.label"  :id="citem.label" :key="cindex"> 
          <template v-for="(item,itemIndex) in citem.ecmFormItems">
            <el-col v-show="itemId || (!itemId && !item.readOnly)" :span="showCellValue(item)" v-bind:key="itemIndex" style="text-align:left;">
              <el-form-item :hidden="item.isHide" :label="item.label">
              {{item.defaultValue}}
              </el-form-item>
            </el-col>
          </template>
          </el-collapse-item>
        </el-collapse>
    </el-row>
      <el-form-item style="float:left"  :label="$t('application.type')" >{{myTypeName}}</el-form-item>
      
    </el-form>
  </div>
</template>

<script type="text/javascript">
import UserSelectInput from '@/components/controls/UserSelectInput'
import AddCondition from '@/views/record/AddCondition'

export default {
  name: "ShowPropertyReadOnly",
  components: {
    UserSelectInput:UserSelectInput,
    AddCondition:AddCondition
  },
  data() {
    return {
      tableHeight: window.innerHeight - 98,
      currentLanguage: "zh-cn",
      permit:5,
      activeNames:'',
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
          defaultValue: "",
          validatePolicy:""
        }
      },
      file:"",
      fileList:[],
      myItemId: this.itemId,
      myTypeName: this.typeName,
      myFolderId: this.folderId,
      formName:"",
      parentDocId:'',
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
    itemId: {type:String},
    typeName: {type:String},
    folderId: {type:String},
    folderPath:{type:String}
  },
  methods: {
    validateValue(itemData){
      if(itemData.required){
        if(itemData.validatePolicy != null && itemData.validatePolicy != ""){
          var p = itemData.validatePolicy.split(":");
          if(this.getValueByAttr(p[0]) == p[1]){
            return true;
          }
        }else{
          return true;
        }
      }
      return false;
    },
    getValueByAttr(attrName){
      var c;
      for(c in this.dataList){
        let dataRows = this.dataList[c].ecmFormItems;
        var i;
        for (i in dataRows) {
          if(dataRows[i].attrName == attrName){
            return dataRows[i].defaultValue;
          }
        }
      }
      return "";
    },
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
      m.set('itemInfo',_self.myItemId+_self.myTypeName);//ID 或类型
      m.set('formName',_self.formName);
      m.set('lang',_self.getLang());
      //console.log(_self.itemId+","+_self.myItemId+","+_self.myTypeName+","+_self.folderId);
      axios.post("/dc/getFormClassifications",JSON.stringify(m))
        .then(function(response) {
         
          _self.bindData(response.data.data);
          if(response.data.data[0]){
             //console.log(JSON.stringify(response.data.data[0].label));
            _self.activeNames = [response.data.data[0].label];
          }
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
      var ret = true;
      var msg = "";
      var c;
      for(c in _self.dataList){
        let dataRows = _self.dataList[c].ecmFormItems;
        var i;
        for (i in dataRows) {
          // if(dataRows[i].required){
          //    console.log(dataRows[i].label+":"+dataRows[i].defaultValue+":"+typeof(dataRows[i].defaultValue));
          // }
          if(_self.validateValue(dataRows[i]) && (typeof(dataRows[i].defaultValue)==='undefined' || dataRows[i].defaultValue==null||dataRows[i].defaultValue==""))
          {
            msg += "["+dataRows[i].label+"] ";
            ret = false;
          }
        }
      }
      if(!ret)
      {
        _self.$message(msg+_self.$t('application.cannotEmpty')+"!");
      }
      return ret;
    },
    getFormData(){
      let _self = this;
      var m = new Map();
      var c;
      for(c in _self.dataList){
        let dataRows = _self.dataList[c].ecmFormItems;
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
                //console.log(val);
              }
              m.set(dataRows[i].attrName, val);
            }
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
      return formdata;
    },

    saveItem()
    {
      let _self = this;
      if(!_self.validFormValue()){
        return;
      }
      var m = new Map();
      var c;
      for(c in _self.dataList){
        let dataRows = _self.dataList[c].ecmFormItems;
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
                //console.log(val);
              }
              m.set(dataRows[i].attrName, val);
            }
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
      console.log(JSON.stringify(m));
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
            _self.$emit("onSaveSuccess",m);
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
          _self.$message(_self.$t('message.hasnoPermssion'));
          return ;
        }
        axios.post("/dc/saveDocument",JSON.stringify(m))
        .then(function(response) {
          let code = response.data.code;
          //console.log(JSON.stringify(response));
          if(code==1){
            _self.$emit('onSaved','update');
            _self.$emit("onSaveSuccess",m);
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
            if(_self.typeName!=response.data.data.TYPE_NAME){
              _self.myTypeName=response.data.data.TYPE_NAME;
            }
            _self.permit =  response.data.permit;
            
            var c;
            for(c in indata){
              let frmItems = indata[c].ecmFormItems;
              //console.log(JSON.stringify(frmItems));
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
            }
            _self.dataList = indata;
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
