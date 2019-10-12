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
                :rules="[{ required: item.rowdata[0].required=='1', message: '必填'}]">
                  <div v-if="item.rowdata[0].controlType=='TextBox'">
                    <el-input  style="width:42em" v-model="item.rowdata[0].defaultValue"></el-input>
                  </div>
                  <div v-if="item.rowdata[0].controlType=='Integer'">
                    <el-input  style="width:18em" type="number" v-model.number="item.rowdata[0].defaultValue"></el-input>
                  </div>
                  <div v-if="item.rowdata[0].controlType=='Boolean'">
                    <el-checkbox  style="width:5em;float:left" v-model="item.rowdata[0].defaultValue"></el-checkbox>
                  </div>
                  <div v-else-if="item.rowdata[0].controlType=='TextArea'">
                    <el-input style="width:42em" type="textarea"  v-model="item.rowdata[0].defaultValue"></el-input>
                  </div>
                  <div v-else-if="item.rowdata[0].controlType=='Date'">
                    <el-date-picker  style="width:18em" v-model="item.rowdata[0].defaultValue" type="date" placeholder="选择日期">
                    </el-date-picker>
                  </div>
                  <div v-else-if="item.rowdata[0].controlType=='Select' || item.rowdata[0].controlType=='Department'
                  || item.rowdata[0].controlType=='SQLSelect'">
                    <el-select  style="width:18em" v-model="item.rowdata[0].defaultValue">
                      <div v-for="name in item.rowdata[0].validValues">
                        <el-option  :label="name" :value="name"></el-option>
                      </div>
                    </el-select>
                  </div>
                </el-form-item>
              </el-col>
            </div>
            <div v-else v-if="item.colcount === 2">
               <div v-for="subitem in item.rowdata">
                <el-col :span="12" style="align:left;">
                  <el-form-item :label="subitem.label" 
                  :rules="[{ required: subitem.required=='1', message: '必填'}]"
                  >
                    <div v-if="subitem.controlType === 'TextBox'">
                      <el-input style="width:18em" v-model="subitem.defaultValue"></el-input>
                    </div>
                    <div v-if="subitem.controlType=='Integer'">
                      <el-input style="width:18em" type="number" v-model.number="subitem.defaultValue"></el-input>
                    </div>
                    <div v-if="subitem.controlType=='Boolean'">
                      <el-checkbox style="width:5em;float:left" v-model="subitem.defaultValue"></el-checkbox>
                    </div>
                    <div v-else-if="subitem.controlType === 'TextArea'">
                      <el-input style="width:18em" type="textarea" v-model="subitem.defaultValue"></el-input>
                    </div>
                    <div v-else-if="subitem.controlType ==='Date'">
                      <el-date-picker style="width:18em" v-model="subitem.defaultValue" type="date" placeholder="选择日期">
                      </el-date-picker>
                    </div>
                    <div v-else-if="subitem.controlType === 'Select'|| subitem.controlType=='Department'
                      || subitem.controlType=='SQLSelect'">
                      <el-select style="width:18em" v-model="subitem.defaultValue">
                        <div v-for="name in subitem.validValues">
                          <el-option :label="name" :value="name"></el-option>
                        </div>
                      </el-select>
                    </div>
                  </el-form-item>
                </el-col>
               </div>
              </div>
              <div v-else v-if="item.colcount === 3">
               <div v-for="subitem in item.rowdata">
                <el-col :span="8" style="align:left;">
                  <el-form-item :label="subitem.label" 
                  :rules="[{ required: subitem.required=='1', message: '必填'}]"
                  >
                    <div v-if="subitem.controlType === 'TextBox'">
                      <el-input style="width:15em" v-model="subitem.defaultValue"></el-input>
                    </div>
                    <div v-if="subitem.controlType=='Integer'">
                      <el-input style="width:15em" type="number" v-model.number="subitem.defaultValue"></el-input>
                    </div>
                    <div v-if="subitem.controlType=='Boolean'">
                      <el-checkbox style="width:5em" v-model="subitem.defaultValue"></el-checkbox>
                    </div>
                    <div v-else-if="subitem.controlType === 'TextArea'">
                      <el-input style="width:15em" type="textarea" v-model="subitem.defaultValue"></el-input>
                    </div>
                    <div v-else-if="subitem.controlType ==='Date'">
                      <el-date-picker style="width:15em" v-model="subitem.defaultValue" type="date" placeholder="选择日期">
                      </el-date-picker>
                    </div>
                    <div v-else-if="subitem.controlType === 'Select'|| subitem.controlType=='Department'
                      || subitem.controlType=='SQLSelect'">
                      <el-select style="width:15em" v-model="subitem.defaultValue">
                        <div v-for="name in subitem.validValues">
                          <el-option :label="name" :value="name"></el-option>
                        </div>
                      </el-select>
                    </div>
                  </el-form-item>
                </el-col>
               </div>
              </div>
              <div v-else v-if="item.colcount === 4">
               <div v-for="subitem in item.rowdata">
                <el-col :span="8" style="align:left;">
                  <el-form-item :label="subitem.label" 
                  :rules="[{ required: subitem.required=='1', message: '必填'}]"
                  >
                    <div v-if="subitem.controlType === 'TextBox'">
                      <el-input style="width:10em" v-model="subitem.defaultValue"></el-input>
                    </div>
                    <div v-if="subitem.controlType=='Integer'">
                      <el-input style="width:10em" type="number" v-model.number="subitem.defaultValue"></el-input>
                    </div>
                    <div v-if="subitem.controlType=='Boolean'">
                      <el-checkbox style="width:5em" v-model="subitem.defaultValue"></el-checkbox>
                    </div>
                    <div v-else-if="subitem.controlType === 'TextArea'">
                      <el-input style="width:10em" type="textarea" v-model="subitem.defaultValue"></el-input>
                    </div>
                    <div v-else-if="subitem.controlType ==='Date'">
                      <el-date-picker style="width:10em" v-model="subitem.defaultValue" type="date" placeholder="选择日期">
                      </el-date-picker>
                    </div>
                    <div v-else-if="subitem.controlType === 'Select'|| subitem.controlType=='Department'
                      || subitem.controlType=='SQLSelect'">
                      <el-select style="width:10em" v-model="subitem.defaultValue">
                        <div v-for="name in subitem.validValues">
                          <el-option :label="name" :value="name"></el-option>
                        </div>
                      </el-select>
                    </div>
                  </el-form-item>
                </el-col>
              </div>
            </div>
        </el-row>
      </div>
    </el-form>
  </div>
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
    loaddata(){
      let _self = this;
      _self.loading = true;
      if(_self.parentformid == '')
      {
        _self.$message("ParentID不能为空!");
        return;
      }
      _self
        .axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data: _self.parentformid,
          url: "/zisecm/admin/getFormItem"
        })
        .then(function(response) {
          _self.refreshData(response.data.data);
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    refreshData(data) {
      let tab = data;
      let _self = this;
      _self.dataList = [];
      var newrow = false;
      var ci = 0;
      var i;
      let rowdata = [];
     
      for (i in tab) {
        if (tab[i].widthType=='1')
        {
          if(ci==1)
          {
            let emptydata = {
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
          rowdata.push(tab[i]);
          newrow = true;
        }
        else 
        {
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
        else if((tab.length-1)==i) // 最后一行处理
        {
          if(ci==1)
          {
            let emptydata = {
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
      console.log(JSON.stringify(_self.dataList));
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
