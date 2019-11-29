<template>
  
    <el-form label-position="right" label-width="100px">
        <el-row>
          <template v-for="(item,itemIndex) in dataList">
            <el-col :span="showCellValue(item)" v-bind:key="itemIndex" style="align:left;">
              <el-form-item :hidden="item.isHide" :label="item.label" :rules="[{required:item.required,message:'必填',trigger:'blur'}]">
                <el-input v-if="item.controlType=='TextBox'" type="text" :name="item.attrName" v-model="item.defaultValue"></el-input>
                <el-input v-if="item.controlType=='TextArea'" type="textarea" :name="item.attrName" v-model="item.defaultValue"></el-input>
                <el-input v-else-if="item.controlType=='Integer'" type="number" :name="item.attrName" v-model="item.defaultValue"></el-input>
                <el-checkbox v-else-if="item.controlType=='Boolean'"  :name="item.attrName" v-model="item.defaultValue"></el-checkbox>
                <el-date-picker v-else-if="item.controlType=='Date'" :name="item.attrName" v-model="item.defaultValue" type="date" placeholder="选择日期" style="display:block;"></el-date-picker>
                <el-select  :name="item.attrName"
                v-else-if="item.controlType=='Select' || item.controlType=='ValueSelect' || item.controlType=='Department' || item.controlType=='SQLSelect'" 
                v-model="item.defaultValue" :placeholder="'请选择'+item.label" :disabled="item.readOnly" style="display:block;">
                      <div v-for="(name,nameIndex) in item.validValues" :key="nameIndex+'N'">
                        <el-option :label="name" :value="name" :key="nameIndex"></el-option>
                      </div>
                  </el-select>
                <UserSelectInput v-else-if="item.controlType=='UserSelect'" v-model="item.defaultValue" v-bind:inputValue="item.defaultValue" v-bind:isRepeat="item.isRepeat"></UserSelectInput>
                <!-- <UserSelectInput v-else-if="item.controlType=='UserSelect'" v-model="item.defaultValue"></UserSelectInput> -->
              </el-form-item>
            </el-col>
          </template>
        </el-row>
    </el-form>
</template>

<script type="text/javascript">

export default {
  name: "FormItemCheck",
  permit: 9,
  data() {
    return {
      parentid: "",
      tableHeight: window.innerHeight - 98,
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
    loaddata(){
      let _self = this;
      _self.loading = true;
      if(_self.parentformid == '')
      {
        _self.$message("ParentID不能为空!");
        return;
      }
      axios.post("/admin/getFormItem",_self.parentformid)
        .then(function(response) {
          _self.dataList = response.data.data;
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
