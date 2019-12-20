<template>
  <el-form label-width="120px" @submit.native.prevent>
    <el-row>
      <template v-for="(item,itemIndex) in formColumnsList">
        <el-col :span="showCellValue(item)" v-bind:key="itemIndex">
          <el-form-item :hidden="item.isHide" :label="item.label" :rules="[{required:item.required,message:'必填',trigger:'blur'}]">
                <el-input v-if="item.controlType=='TextBox'" type="text" :name="item.attrName" v-model="item.defaultValue"></el-input>
                <el-input v-else-if="item.controlType=='Integer'" type="number" :name="item.attrName" v-model="item.defaultValue"></el-input>
                <el-checkbox v-else-if="item.controlType=='Boolean'"  :name="item.attrName" v-model="item.defaultValue"></el-checkbox>
                <el-date-picker v-else-if="item.controlType=='Date'" :name="item.attrName" v-model="item.defaultValue" type="date" placeholder="选择日期" style="display:block;" value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
                <el-select  :name="item.attrName"
                v-else-if="item.controlType=='Select' || item.controlType=='Department' || item.controlType=='SQLSelect'" 
                v-model="item.defaultValue" :placeholder="'请选择'+item.label" :disabled="item.readOnly" style="display:block;">
                      <div v-for="(name,nameIndex) in item.validValues">
                        <el-option :label="name" :value="name" :key="nameIndex"></el-option>
                      </div>
                  </el-select>
                <UserSelectInput v-else-if="item.controlType=='UserSelect'" v-model="item.defaultValue" v-bind:inputValue="item.defaultValue" v-bind:isRepeat="item.isRepeat"></UserSelectInput>
                <!-- <UserSelectInput v-else-if="item.controlType=='UserSelect'" v-model="item.defaultValue"></UserSelectInput> -->
          </el-form-item>
        </el-col>
      </template>
    </el-row>
    <div v-if="isUpload" style="height:200px;overflow-y:scroll; overflow-x:scroll;">
        <el-upload
          :limit="100"
          :file-list="fileList" 
          action=""
          :on-change="onFileSelectChange"
          :auto-upload="false"
          :multiple="true">
          <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
        </el-upload>
      </div>
  </el-form>
</template>

<script>
//动态表单
import UserSelectInput from '@/components/controls/UserSelectInput'

export default {
  data(){
    return {
      formObj:{},         //表单基本信息
      formColumnsList:[], //表单字段信息
      fileList:[],        //选择文件内存
      showColumns:2       //表单默认列数
    }
  },
  components: {
    UserSelectInput:UserSelectInput
  },
  props:{
    isUpload:{//是否拥有上传文件功能
      type:Boolean,
      default:false
    },
    actionType:{//表单ActionType，默认为NEW
      type:String,
      default:"NEW"
    },
    typeName:{//表单TypeName
      type:String,
      default:""
    },
    formNumber:{//表单号，预备做编辑读取表单信息的编号使用，暂无此功能
      type:String,
      default:""
    }
  },
  methods:{
    showCellValue(item){
      console.log(item);
      var v = 24/ parseInt(item.widthType);
      console.log(v);
      return v;
    },
    /*
    文件选择事件响应，将选择文件信息赋给fileList
    */
    onFileSelectChange(file, fileList){
      this.fileList = fileList;      
    },
    /*
    获取表单数据
    */
    getFormData(){
      let _self = this;
      let formdata = new FormData();
      var data = {};
      _self.formColumnsList.forEach(function(item){
        //console.log(item);
        data[item.attrName]=item.defaultValue;
      });
      formdata.append("metaData",JSON.stringify(data));      
      _self.fileList.forEach(function (file) {
        //console.log(file.name);
        formdata.append("files", file.raw, file.name);
      });
      return formdata;
    },
    /*
    获取表单和表单字段信息，用于初始化created
    */
    getFormInfo(){
      let _self = this;
      var url="zisecm/admin/getFormObj";
      var param = this.typeName;
      url+="/"+param+"/"+this.actionType;
      if(param.length>0){
        _self.axios({
          method: "get",
          url: url
        }).then(function(response){
          _self.formObj = response.data.data;
          //console.log(_self.formObj);
          _self.showColumns = _self.formObj.columnCount;
          _self.getFormItems(_self.typeName);
        });
      }
    },
    /*
    获取表单字段信息，被getFormInfo调用
    */
    getFormItems(name){
      console.log("getFormItems");
      let _self = this;
      var m = new Map();
      m.set('itemInfo',name);//ID 或类型
      m.set('lang',_self.getLang());
      
      //console.log(name);
      _self.axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        method: "post",
        data: JSON.stringify(m),
        url: "/zisecm/dc/getFormItem"
      }).then(function(response){
        //console.log(response.data.data);
        _self.formColumnsList = response.data.data;
      });
    }
  },
  computed:{
    /*通过created中获取的表单内容计算col的宽度*/
    
  },
  mounted(){
    this.fileList=[];
    this.formColumnsList.forEach(function(item){
      if(item.attrName!='TYPE_NAME'){
        item.defaultValue="";
      }
    });
  },
  created(){
    console.log("DynamicForm Created")
    this.getFormInfo();
  }
}
</script>

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
}
li {
  /* display: inline-block; */
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>