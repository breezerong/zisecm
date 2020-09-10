<template>
  
    <el-form label-position="right" label-width="100px">
      <el-row>
        <el-form-item label="语言">
          <el-select v-model="currentLanguage" @change="loaddata">
            <el-option label="中文" value="zh-cn"></el-option>
            <el-option label="英文" value="en"></el-option>
          </el-select>
        </el-form-item>
      </el-row>
        <el-collapse v-model="activeNames">
          <el-collapse-item v-for="(citem,cindex) in dataList" :title="citem.label" :name="citem.label"  :id="citem.label" :key="cindex"> 
          <template v-for="(item,itemIndex) in citem.ecmFormItems">
            <el-col :span="showCellValue(item)" v-bind:key="itemIndex" style="text-align:left;">
              <el-form-item :hidden="item.isHide" :label="item.label" :rules="[{required:validateValue(item),message:$t('application.requiredInput'),trigger:'blur'}]">
                    <el-input v-if="item.controlType=='TextBox' && !item.isRepeat" type="text" :name="item.attrName" v-model="item.defaultValue" :disabled="item.readOnly"></el-input>
                    <MultiInput v-if="item.controlType=='TextBox' && item.isRepeat" v-model="item.defaultValue"></MultiInput>
                    <el-input v-if="item.controlType=='TextArea'" type="textarea" :name="item.attrName" v-model="item.defaultValue" :disabled="item.readOnly"></el-input>
                    <el-input v-else-if="item.controlType=='Integer'" type="number" :name="item.attrName" v-model="item.defaultValue" :disabled="item.readOnly"></el-input>
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
    </el-form>
</template>

<script type="text/javascript">
import UserSelectInput from '@/components/controls/UserSelectInput'
import MultiInput from '@/components/ecm-multi-input'

export default {
  name: "FormItemCheck",
  permit: 9,
  components: {
    UserSelectInput : UserSelectInput,
    MultiInput : MultiInput
  },
  data() {
    return {
      parentid: "",
       currentLanguage: "zh-cn",
       activeNames:'',
      
      dataList: {
        rowdata: {
          parentId:"",
          label:"",
          name: "",
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
      formLabelWidth: "100px",
      formLabelWidth2: "100px"
    };
  },
  props: {
    parentformid:""
  },
  mounted() {
    this.loaddata();
  },
  methods: {
    showCellValue(item){
      //console.log(item);
      var v = 24/ parseInt(item.widthType);
      //console.log(v);
      return v;
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
    loaddata(){
      let _self = this;
      _self.loading = true;
      if(_self.parentformid == '')
      {
        _self.$message("ParentID不能为空!");
        return;
      }
      var m = new Map();
      m.set('id',_self.parentformid);//ID 或类型
      m.set('lang',_self.currentLanguage);
      axios.post("/admin/getFormItemByLang",JSON.stringify(m))
        .then(function(response) {
           _self.dataList = response.data.data;
          if(response.data.data[0]){
             //console.log(JSON.stringify(response.data.data[0].label));
            _self.activeNames = [response.data.data[0].label];
          }
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
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
