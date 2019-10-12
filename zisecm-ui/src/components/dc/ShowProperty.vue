<template>
    <el-form label-position="right" label-width="100px">
      <!--
      <div>
        <el-button :plain="true" type="primary" size="small" icon="edit" @click="loaddata()">刷新数据</el-button>
      </div>
      -->
      <div v-for="item in dataList">
        <el-row>
            <div v-if="item.colcount === 1">
              <el-col>
                <el-form-item :label="item.rowdata[0].label" 
                :rules="[{ required: item.rowdata[0].required, message: '必填', trigger: 'blur'}]">
                  <div v-if="item.rowdata[0].controlType=='TextBox'">
                    <el-input  style="width:42em" v-model="item.rowdata[0].defaultValue" :disabled="item.rowdata[0].readOnly"></el-input>
                  </div>
                  <div v-if="item.rowdata[0].controlType=='Integer'">
                    <el-input  style="width:18em" type="number" v-model.number="item.rowdata[0].defaultValue" :disabled="item.rowdata[0].readOnly"></el-input>
                  </div>
                  <div v-if="item.rowdata[0].controlType=='Boolean'">
                    <el-checkbox  style="width:5em;float:left" v-model="item.rowdata[0].defaultValue" :disabled="item.rowdata[0].readOnly"></el-checkbox>
                  </div>
                  <div v-else-if="item.rowdata[0].controlType=='TextArea'">
                    <el-input style="width:42em" type="textarea"  v-model="item.rowdata[0].defaultValue" :disabled="item.rowdata[0].readOnly"></el-input>
                  </div>
                  <div v-else-if="item.rowdata[0].controlType=='Date'">
                    <el-date-picker  style="width:18em" v-model="item.rowdata[0].defaultValue" type="date" placeholder="选择日期" :disabled="item.rowdata[0].readOnly">
                    </el-date-picker>
                  </div>
                  <div v-else-if="item.rowdata[0].controlType=='Select' || item.rowdata[0].controlType=='Department'
                  || item.rowdata[0].controlType=='SQLSelect'">
                    <el-select  style="width:18em" v-model="item.rowdata[0].defaultValue" :disabled="item.rowdata[0].readOnly">
                      <div v-for="name in item.rowdata[0].validValues">
                        <el-option  :label="name" :value="name"></el-option>
                      </div>
                    </el-select>
                  </div>
                  <UserSelectInput v-else-if="item.rowdata[0].controlType=='UserSelect'" v-model="item.rowdata[0].defaultValue"></UserSelectInput>
                </el-form-item>
              </el-col>
            </div>
            <div v-else-if="item.colcount === 2">
               <div v-for="subitem in item.rowdata">
                <el-col :span="12" style="align:left;">
                  <el-form-item :label="subitem.label" 
                  :rules="[{ required: subitem.required, message: '必填', trigger: 'blur'}]"
                  >
                    <div v-if="subitem.controlType === 'TextBox'">
                      <el-input style="width:18em" v-model="subitem.defaultValue" :disabled="subitem.readOnly"></el-input>
                    </div>
                    <div v-if="subitem.controlType=='Integer'">
                      <el-input style="width:18em" type="number" v-model.number="subitem.defaultValue" :disabled="subitem.readOnly"></el-input>
                    </div>
                    <div v-if="subitem.controlType=='Boolean'">
                      <el-checkbox style="width:5em;float:left" v-model="subitem.defaultValue" :disabled="subitem.readOnly"></el-checkbox>
                    </div>
                    <div v-else-if="subitem.controlType === 'TextArea'">
                      <el-input style="width:18em" type="textarea" v-model="subitem.defaultValue" :disabled="subitem.readOnly"></el-input>
                    </div>
                    <div v-else-if="subitem.controlType ==='Date'">
                      <el-date-picker style="width:18em" v-model="subitem.defaultValue" type="date" placeholder="选择日期" :disabled="subitem.readOnly">
                      </el-date-picker>
                    </div>
                    <div v-else-if="subitem.controlType === 'Select'|| subitem.controlType=='Department'
                      || subitem.controlType=='SQLSelect'">
                      <el-select style="width:18em" v-model="subitem.defaultValue" :disabled="subitem.readOnly">
                        <div v-for="name in subitem.validValues">
                          <el-option :label="name" :value="name"></el-option>
                        </div>
                      </el-select>
                    </div>
                    <UserSelectInput v-else-if="subitem.controlType=='UserSelect'" v-model="subitem.defaultValue" v-bind:inputValue="subitem.defaultValue"></UserSelectInput>
                  </el-form-item>
                </el-col>
               </div>
              </div>
              <div v-else-if="item.colcount === 3">
               <div v-for="subitem in item.rowdata">
                <el-col :span="8" style="align:left;">
                  <el-form-item :label="subitem.label" 
                  :rules="[{ required: subitem.required, message: '必填', trigger: 'blur'}]"
                  >
                    <div v-if="subitem.controlType === 'TextBox'">
                      <el-input style="width:15em" v-model="subitem.defaultValue" :disabled="subitem.readOnly"></el-input>
                    </div>
                    <div v-if="subitem.controlType=='Integer'">
                      <el-input style="width:15em" type="number" v-model.number="subitem.defaultValue" :disabled="subitem.readOnly"></el-input>
                    </div>
                    <div v-if="subitem.controlType=='Boolean'">
                      <el-checkbox style="width:5em" v-model="subitem.defaultValue" :disabled="subitem.readOnly"></el-checkbox>
                    </div>
                    <div v-else-if="subitem.controlType === 'TextArea'">
                      <el-input style="width:15em" type="textarea" v-model="subitem.defaultValue" :disabled="subitem.readOnly"></el-input>
                    </div>
                    <div v-else-if="subitem.controlType ==='Date'">
                      <el-date-picker style="width:15em" v-model="subitem.defaultValue" type="date" placeholder="选择日期" :disabled="subitem.readOnly">
                      </el-date-picker>
                    </div>
                    <div v-else-if="subitem.controlType === 'Select'|| subitem.controlType=='Department'
                      || subitem.controlType=='SQLSelect'">
                      <el-select style="width:15em" v-model="subitem.defaultValue" :disabled="subitem.readOnly">
                        <div v-for="name in subitem.validValues">
                          <el-option :label="name" :value="name"></el-option>
                        </div>
                      </el-select>
                    </div>
                    <UserSelectInput v-else-if="subitem.controlType=='UserSelect'" v-model="subitem.defaultValue"></UserSelectInput>
                  </el-form-item>
                </el-col>
               </div>
              </div>
              <div v-else-if="item.colcount === 4">
               <div v-for="subitem in item.rowdata">
                <el-col :span="8" style="align:left;">
                  <el-form-item :label="subitem.label" 
                  :rules="[{ required: subitem.required, message: '必填', trigger: 'blur'}]"
                  >
                    <div v-if="subitem.controlType === 'TextBox'">
                      <el-input style="width:10em" v-model="subitem.defaultValue" :disabled="subitem.readOnly"></el-input>
                    </div>
                    <div v-if="subitem.controlType=='Integer'">
                      <el-input style="width:10em" type="number" v-model.number="subitem.defaultValue" :disabled="subitem.readOnly"></el-input>
                    </div>
                    <div v-if="subitem.controlType=='Boolean'">
                      <el-checkbox style="width:5em" v-model="subitem.defaultValue" :disabled="subitem.readOnly"></el-checkbox>
                    </div>
                    <div v-else-if="subitem.controlType === 'TextArea'">
                      <el-input style="width:10em" type="textarea" v-model="subitem.defaultValue" :disabled="subitem.readOnly"></el-input>
                    </div>
                    <div v-else-if="subitem.controlType ==='Date'">
                      <el-date-picker style="width:10em" v-model="subitem.defaultValue" type="date" placeholder="选择日期" :disabled="subitem.readOnly">
                      </el-date-picker>
                    </div>
                    <div v-else-if="subitem.controlType === 'Select'|| subitem.controlType=='Department'
                      || subitem.controlType=='SQLSelect'">
                      <el-select style="width:10em" v-model="subitem.defaultValue" :disabled="subitem.readOnly">
                        <div v-for="name in subitem.validValues">
                          <el-option :label="name" :value="name"></el-option>
                        </div>
                      </el-select>
                    </div>
                    <UserSelectInput v-else-if="subitem.controlType=='UserSelect'" v-model="subitem.defaultValue"></UserSelectInput>
                  </el-form-item>
                </el-col>
              </div>
            </div>
        </el-row>
      </div>
      <div v-if="itemId  == undefined || itemId == 0 " style="float:left;margin-left:120px;">
        <el-upload
        :limit="1"
        :file-list="fileList" 
        action=""
        :on-change="handleChange"
        :auto-upload="false"
        :multiple="false">
        <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
        </el-upload>
        
      </div>
    </el-form>
  </div>
</template>

<script type="text/javascript">
import UserSelectInput from '@/components/controls/UserSelectInput'

export default {
  name: "ShowProperty",
  components: {
    UserSelectInput:UserSelectInput
  },
  data() {
    return {
      tableHeight: window.innerHeight - 98,
      dataAll:[],
      currentLanguage: "zh-cn",
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
      formLabelWidth: "100px",
      formLabelWidth2: "100px",
      myItemId: this.itemId,
      myTypeName: this.typeName,
      myFolderId: this.folderId
    };
  },
  mounted() {
    this.currentLanguage = localStorage.getItem("localeLanguage") || "zh-cn";
    this.loadFormInfo();
  },
  props: {
    itemId: {type:[String,Number],required:true},
    typeName: {type:String,required:true},
    folderId: {type:[String,Number],required:true}
  },
  methods: {
    handleChange(file, fileList){
      this.file = file;
        //console.log(file);
       // console.log(fileList);
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
      m.set('lang',_self.currentLanguage);
      console.log(_self.itemId+","+_self.myItemId+","+_self.myTypeName+","+_self.folderId);
      _self.axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data: JSON.stringify(m),//_self.myItemId+_self.myTypeName,
          url: "/zisecm/dc/getFormItem"
        })
        .then(function(response) {

          _self.bindData(response.data.data);
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
      let dataRows = _self.dataAll;
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
      let dataRows = _self.dataAll;
      var i;
      for (i in dataRows) {
        if(dataRows[i].attrName && dataRows[i].attrName !='')
        {
          if(dataRows[i].attrName !='FOLDER_ID'&&dataRows[i].attrName !='ID')
          {
            m.set(dataRows[i].attrName, dataRows[i].defaultValue);
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
        _self.axios({
          headers: {
            'Content-Type': 'multipart/form-data'
            // x-www-form-urlencoded'
            //"Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data: formdata,
          url: "/zisecm/dc/newDocument"
        })
        .then(function(response) {
          let code = response.data.code;
          //console.log(JSON.stringify(response));
          if(code==1){
            _self.$emit('onSaved','new');
          }
          else{
             _self.$message("新建失败!");
          }
        })
        .catch(function(error) {
          _self.$message("新建失败!");
          console.log(error);
        });
      }
      else
      {
        _self.axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data: JSON.stringify(m),
          url: "/zisecm/dc/saveDocument"
        })
        .then(function(response) {
          let code = response.data.code;
          //console.log(JSON.stringify(response));
          if(code==1){
            _self.$emit('onSaved','update');
          }
          else{
             _self.$message("保存失败!");
          }
        })
        .catch(function(error) {
          _self.$message("保存失败!");
          console.log(error);
        });
      }
    },
    bindData(indata)
    {
      let _self = this;
      if(_self.myItemId =='')
      {
        _self.refreshData(indata);
      }
      else
      {
        _self.axios({
            headers: {
              "Content-Type": "application/json;charset=UTF-8"
            },
            method: "post",
            data: _self.myItemId,
            url: "/zisecm/dc/getDocument"
          })
          .then(function(response) {
            let tab = response.data.data;
          
            //console.log(JSON.stringify(tab));
            let frmItems = indata;
            var i;
            for (i in frmItems) {
              frmItems[i].defaultValue = tab[frmItems[i].attrName];
              console.log(JSON.stringify(frmItems[i].attrName)+":"+frmItems[i].defaultValue);
            }
            _self.refreshData(frmItems);
          })
          .catch(function(error) {
            console.log(error);
        });
      }
    },
    refreshData(data) {
      let tab = data;
      let _self = this;
      _self.dataList = [];
      _self.dataAll = [];
      var newrow = false;
      var ci = 0;
      var i;
      let rowdata = [];
     
      for (i in tab) {
        _self.dataAll.push(tab[i]);
        // console.log('-----------------------------');
        //console.log(JSON.stringify(tab[i]));
        if(tab[i].isHide == false)
        {
          if (tab[i].widthType=='1')
          {
            //单行前面类型为：2，只有一个控件的需要增加空控件
            if(ci==1)
            {
              let emptydata = {
                attrName:"",
                readOnly:false,
                controlType:"",
                label:"",
                validValues:[],
                orderIndex: 1,
                defaultValue: ""
              }
              rowdata.push(emptydata);
              let rowdatas = {
                colcount: rowdata.length,
                rowdata: rowdata
              }
              _self.dataList.push(rowdatas);
              ci=0;
              rowdata = [];
            }
            // console.log("i:"+i);
            rowdata.push(tab[i]);
            newrow = true;
          }
          else 
          {
            // console.log("i:"+i);
            rowdata.push(tab[i]);
            ci = ci+1;
            if(ci==2)
            {
              ci = 0;
              newrow = true;
            }
          }
          
          if(newrow)
          {
            let rowdatas = {
              colcount: rowdata.length,
              rowdata: rowdata
            }
            _self.dataList.push(rowdatas);
            newrow = false;
            rowdata = [];
          }
          
        }
        if((tab.length-1)==i) // 最后一行处理
          {
            if(ci==1)
            {
              let emptydata = {
                attrName:"",
                controlType:"",
                label:"",
                validValues:[],
                orderIndex: 1,
                defaultValue: ""
              }
              rowdata.push(emptydata);
            }
            let rowdatas = {
              colcount: rowdata.length,
              rowdata: rowdata
            }
            _self.dataList.push(rowdatas);
          }
      }
      //console.log(JSON.stringify(_self.dataList));
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
