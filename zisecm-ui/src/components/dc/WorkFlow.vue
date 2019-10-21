<template>
    <el-form label-position="right" label-width="100px">
      <!--
      <div>
        <el-button :plain="true" type="primary" size="small" icon="edit" @click="loaddata()">刷新数据</el-button>
      </div>
      -->
      
        <el-row>
            <template v-for="(item,itemIndex) in dataList">
        <el-col :span="showCellValue(item)" v-bind:key="itemIndex">
          <el-form-item :hidden="item.isHide" :label="item.label" :rules="[{required:item.required,message:'必填',trigger:'blur'}]">
                <el-input v-if="item.controlType=='TextBox'" type="text" :name="item.attrName" v-model="item.defaultValue"></el-input>
                <el-input v-else-if="item.controlType=='Integer'" type="number" :name="item.attrName" v-model="item.defaultValue"></el-input>
                <el-checkbox v-else-if="item.controlType=='Boolean'"  :name="item.attrName" v-model="item.defaultValue"></el-checkbox>
                <el-date-picker v-else-if="item.controlType=='Date'" :name="item.attrName" v-model="item.defaultValue" type="date" placeholder="选择日期" style="display:block;"></el-date-picker>
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
      </div>
      
    </el-form>
  </div>
</template>

<script type="text/javascript">
import UserSelectInput from '@/components/controls/UserSelectInput'
import UsersSelector from '@/components/controls/UsersSelector'
export default {
  name: "ShowProperty",
  components: {
    UserSelectInput:UserSelectInput,
    UsersSelector:UsersSelector
  },
  data() {
    return {
      tableHeight: window.innerHeight - 98,
      dataAll:[],
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
      myFolderId: this.folderId,
      workflowForm:{
        name: "文件生效流程",
        description: "我的测试流程说明",
        processId: 1,
        docId:[],
        formId:""
      }
    };
  },
  mounted() {
    this.loadFormInfo();
  },
  props: {
    itemId: {type:[String,Number],required:true},
    typeName: {type:String,required:true},
    folderId: {type:[String,Number]},
    docsId:{type:Array}
  },
  methods: {
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
      m.set('lang',_self.getLang());
      //console.log(_self.itemId+","+_self.myItemId+","+_self.myTypeName+","+_self.folderId);
      _self.axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data: JSON.stringify(m),
          url: "/zisecm/dc/getFormItem"
        })
        .then(function(response) {
          _self.dataList = response.data.data;
          //_self.bindData(response.data.data);
          console.log(JSON.stringify(response.data.data));
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
    startWorkflow(){
        // alert('startworkflow');
        
        ////保存///

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
        .then((response)=> {
          let code = response.data.code;
          //console.log(JSON.stringify(response));
          // alert("typeName:"+_self.$parent.typeName);
          // alert("formId:"+_self.workflowForm.formId);
          _self.workflowForm.formId=response.data.id;
          _self.workflowForm.docId=_self.docsId;
            _self.loading = true;
            //起流程
            _self.axios({
                headers: {
                "Content-Type": "application/json;charset=UTF-8"
                },
                method: 'post',
                data: JSON.stringify(_self.workflowForm),
                url: '/zisecm/workflow/startWrokflow'
            })
            .then((response)=> {
                // alert("this:"+this.$parent.typeName);
                // alert(this.$parent.$parent.propertyVisible);
                // _self.$parent.propertyVisible = false;
                this.$parent.$parent.propertyVisible=false;
                // alert(_self.$parent.propertyVisible);
                // _self.$parent.refreshData();
                this.$parent.$parent.refreshData();
                _self.$message("发起流程成功!");
                _self.loading = false;
            })
            .catch(function(error) {
                console.log(error);
                _self.loading = false;
            });
        })
        .catch(function(error) {
          _self.$message("新建失败!");
          console.log(error);
        });
      }
      
        ////end
    //   let _self = this;
      
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
          workflowForm.formId=response.data.id;
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
        _self.dataList = indata;
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
