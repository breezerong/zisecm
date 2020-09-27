<template>
<div>
    <el-form label-position="right" label-width="100px">
       <!--
      <el-row>
        <el-form-item style="float:left"  :label="$t('application.type')" >{{typeName}}</el-form-item>
      </el-row>
     
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
            <el-col :span="showCellValue(item)" v-bind:key="itemIndex" style="text-align:left;">
              <el-form-item :hidden="item.isHide" :label="item.label" :rules="[{required:validateValue(item),message:$t('application.requiredInput'),trigger:'blur'}]" :label-width="formLabelWidth">
                    <el-input v-if="item.controlType=='TextBox' && !item.isRepeat" type="text" :name="item.attrName" v-model="item.defaultValue" :disabled="item.readOnly"></el-input>
                    <MultiInput v-if="item.controlType=='TextBox' && item.isRepeat" v-model="item.defaultValue"></MultiInput>
                    <el-input v-if="item.controlType=='TextArea'" type="textarea" :name="item.attrName" v-model="item.defaultValue" :disabled="item.readOnly"></el-input>
                    <el-input v-else-if="item.controlType=='Integer'" :min="0" type="number" :name="item.attrName" v-model="item.defaultValue" :disabled="item.readOnly"></el-input>
                    <el-checkbox v-else-if="item.controlType=='Boolean'"  :name="item.attrName" v-model="item.defaultValue" :disabled="item.readOnly"></el-checkbox>
                    <template v-else-if="item.controlType=='Date'">
                      <span v-if="item.readOnly" >{{datetimeFormat(item.defaultValue)}}</span>
                      <el-date-picker v-else :name="item.attrName" v-model="item.defaultValue" type="date" :placeholder="$t('application.selectDate')" style="display:block;" value-format="yyyy-MM-dd HH:mm:ss" :readonly="item.readOnly"></el-date-picker>
                    </template>
                    <el-select  :name="item.attrName"
                    v-else-if="item.controlType=='Select' || item.controlType=='ValueSelect' || item.controlType=='Department' || item.controlType=='SQLSelect'" 
                    v-model="item.defaultValue" :placeholder="$t('application.pleaseSelect')+item.label" :disabled="item.readOnly" :multiple="item.isRepeat" style="display:block;"
                    @change="((val)=>{onSelectChange(val, item)})" >
                          <div v-for="(name,nameIndex) in item.validValues" :key="nameIndex+'N'">
                            <el-option :label="name" :value="name" :key="nameIndex"></el-option>
                          </div>
                      </el-select>
                    <UserSelectInput v-else-if="item.controlType=='UserSelect'" v-model="item.defaultValue" v-bind:inputValue="item.defaultValue" v-bind:isRepeat="item.isRepeat"></UserSelectInput>
                    <!-- <UserSelectInput v-else-if="item.controlType=='UserSelect'" v-model="item.defaultValue"></UserSelectInput> 
                    <AddCondition v-if="item.controlType=='advSearch'" v-model="item.defaultValue" v-bind:inputValue="item.defaultValue" ></AddCondition>-->
              </el-form-item>
            </el-col>
          </template>
          </el-collapse-item>
        </el-collapse>
    </el-row>
    <el-row>
      
      <div v-if="(itemId  == undefined || itemId == '0' || itemId == '') && showUploadFile " style="float:left;margin-left:120px;">
        <el-upload
        :limit="1"
        :file-list="fileList" 
        action=""
        :on-change="handleChange"
        :auto-upload="false"
        :multiple="false">
        <el-button slot="trigger" size="small" type="primary">{{$t('application.selectFile')}}</el-button>
        </el-upload>
        
      </div>
    </el-row>
    </el-form>
  </div>
</template>

<script type="text/javascript">
import UserSelectInput from '@/components/controls/UserSelectInput'
import AddCondition from '@/views/record/AddCondition'
import MultiInput from '@/components/ecm-multi-input'

export default {
  name: "ShowProperty",
  components: {
    UserSelectInput : UserSelectInput,
    AddCondition : AddCondition,
    MultiInput : MultiInput
  },
  data() {
    return {
      formLabelWidth: "110px",
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
      clientPermission: 1,
      mainObject:[],
      mainSubRelation:[]
    };
  },
  mounted() {
    this.currentLanguage = localStorage.getItem("localeLanguage") || "zh-cn";
    if( this.mainObject == null || this.mainObject.length ==0){
      this.loadFormInfo();
    }
    this.clientPermission = sessionStorage.getItem(
        "access-clientPermission"
      );
  },
  props: {
    itemId: {type:String},
    typeName: {type:String},
    folderId: {type:String},
    folderPath:{type:String},
    showUploadFile: {type:Boolean, default:true}
  },
  methods: {
    setMainObject(obj){
      this.mainObject=obj;
    },
    setMainSubRelation(obj){
      this.mainSubRelation=obj;
    },
    validateValueByPolicy(policy){
        if(policy != null && policy != ""){
          var p = policy.split(";");
          if(p[0] != ""){
            var p1 = p[0].split(":");
            if(this.getValueByAttr(p1[0]) == p1[1]){
              return true;
            }
          }else{
            return true;
          }
        }
        return false;
    },
    validateValue(itemData){
      if(itemData.required){
        if(itemData.validatePolicy != null && itemData.validatePolicy != ""){
          var p = itemData.validatePolicy.split(";");
          if(p[0] != ""){
            var p1 = p[0].split(":");
            if(this.getValueByAttr(p1[0]) == p1[1]){
              return true;
            }
          }else{
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
          }else if(dataRows[i].validatePolicy != null && dataRows[i].validatePolicy != ""){
            if((_self.myItemId == null || _self.myItemId =="") && dataRows[i].validatePolicy.indexOf(";")>-1){
              let p = dataRows[i].validatePolicy.split(";")[1];
              if(_self.validateValueByPolicy(dataRows[i].validatePolicy.split(";")[0])){
                let fun = p.split(":");
                if(p.indexOf("now")>-1){
                  let dt = new Date();
                  if(p.indexOf(">=") > -1){
                    if(_self.validateInputDate(dt, dataRows[i].defaultValue,fun[2])<0){
                      msg += "["+dataRows[i].label+"] 必需大于等于当前日期+" + fun[2]+"天";
                      ret = false;
                    }
                  }else if(p.indexOf("<=") > -1){
                    if(_self.validateInputDate(dt,dataRows[i].defaultValue,fun[2])>0){
                      msg += "["+dataRows[i].label+"] 必需小于等于当前日期+" + fun[2] + "天";
                      ret = false;
                    }
                  }else if(p.indexOf(">") > -1){
                    if(_self.validateInputDate(dt,dataRows[i].defaultValue,fun[2])>=0){
                      msg += "["+dataRows[i].label+"] 必需大于当前日期+" + fun[2]+"天";
                      ret = false;
                    }
                  }else if(p.indexOf("<") > -1){
                    if(_self.validateInputDate(dt,dataRows[i].defaultValue,fun[2])>=0){
                      msg += "["+dataRows[i].label+"] 必需小于当前日期+" + fun[2]+"天";
                      ret = false;
                    }
                  }
                }
              }
            }
          }
        }
      }
      if(!ret)
      {
        _self.$message(msg+_self.$t('application.cannotEmpty')+"!");
      }
      return ret;
    },
    // 日期比较，输入日期-当前日期 -天数
    validateInputDate(fromDate, dateTime, addDay){
      let now = fromDate;
      now=now.setDate(now.getDate()+parseInt(addDay));
      var today = new Date(now).Format("yyyyMMdd");
      var dt = new Date(dateTime).Format("yyyyMMdd");
      return parseInt(dt) - parseInt(today);
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
        var c;
            for(c in indata){
              let frmItems = indata[c].ecmFormItems;
              //console.log(JSON.stringify(frmItems));
              var i;
              for (i in frmItems) {
                let val =frmItems[i].defaultValue;
                
                if(_self.mainSubRelation&&_self.mainSubRelation.size>0
                &&_self.mainObject&&(val==null||val==undefined||val=='')){
                   val=_self.mainObject[_self.mainSubRelation.get(frmItems[i].attrName)];
                   frmItems[i].defaultValue = val;
                }
                if(frmItems[i].isRepeat){
                  if(frmItems[i].controlType=='TextBox' || frmItems[i].controlType=='Select' || frmItems[i].controlType=='SQLSelect' || frmItems[i].controlType=='ValueSelect'){
                    if(frmItems[i].defaultValue==null){
                      frmItems[i].defaultValue =[]
                    }else{
                      frmItems[i].defaultValue =  frmItems[i].defaultValue.split(";");
                    }
                  }
                }
                
                // if("TYPE_NAME"==frmItems[i].attrName){
                //   _self.typeName=frmItems[i].attrName；
                // }
                //console.log(JSON.stringify(frmItems[i].attrName)+":"+frmItems[i].defaultValue);
              }
            }
          
        _self.dataList = indata;
        _self.mainObject = null;  
          _self.mainSubRelation = null;
      }
      else
      {
        axios.post("/dc/getDocument",_self.myItemId)
          .then(function(response) {
            let tab = response.data.data;
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
