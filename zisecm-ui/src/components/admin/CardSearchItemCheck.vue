<template>
  
    <el-form>
      <div v-for="item in dataList">
        <el-row>
               <div v-for="subitem in item.rowdata">
                <el-col :span="8" style="align:left;">
                  <el-form-item :label="subitem.label" 
                  :rules="[{ required: subitem.required=='1', message: '必填'}]"
                  >
                    <div v-if="subitem.controlType === 'TextBox'">
                      <el-input style="width:12em" v-model="subitem.defaultValue"></el-input>
                    </div>
                    <div v-if="subitem.controlType=='Integer'">
                      <el-input style="width:12em" type="number" v-model.number="subitem.defaultValue"></el-input>
                    </div>
                    <div v-if="subitem.controlType=='Boolean'">
                      <el-checkbox style="width:5em" v-model="subitem.defaultValue"></el-checkbox>
                    </div>
                    <div v-else-if="subitem.controlType === 'TextArea'">
                      <el-input style="width:12em" type="textarea" v-model="subitem.defaultValue"></el-input>
                    </div>
                    <div v-else-if="subitem.controlType ==='Date'">
                      <el-date-picker style="width:12em" v-model="subitem.defaultValue" type="date" placeholder="选择日期">
                      </el-date-picker>
                    </div>
                    <div v-else-if="subitem.controlType === 'Select'|| subitem.controlType=='Department'
                      || subitem.controlType=='SQLSelect'">
                      <el-select style="width:12em" v-model="subitem.defaultValue">
                        <div v-for="name in subitem.validValues">
                          <el-option :label="name" :value="name"></el-option>
                        </div>
                      </el-select>
                    </div>
                  </el-form-item>
                </el-col>
               </div>
        </el-row>
      </div>
    </el-form>
  </div>
</template>

<script type="text/javascript">

export default {
  name: "CardSearchItemCheck",
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
  beforeCreate()
  {
    
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
      axios.post("/admin/getCardSearchItem",_self.parentformid)
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
        
           rowdata.push(tab[i]);
           ci = ci+1;
           if(ci==3)
           {
             ci = 0;
             newrow = true;
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
          let emptydata = {
              controlType:"",
              label:"",
              validValues:[],
              orderIndex: 1,
              defaultValue: ""
            }
            rowdata.push(emptydata);
          if(ci==2)
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
