<template>
  <el-select v-model="svalue" @change="sChange" filterable >
    <el-option v-if="includeAll" :label="allLabelString" :value="allvalue"></el-option>
    <el-option v-for="item in options" :key="item[valueField]" v-bind="item"></el-option>
  </el-select>
</template>

<script>
export default {
  props:{
    value:{
      type:[String],
      required: true
    },
    queryName:{
      type:[String],
      default:""
    },
    dataUrl:{
      type:String,
      default:""
    },
    dataObj:{
      type:Object,
      default:function(){
        return {}
      }
    },
    dataValueField:{
      type:String,
      default:""
    },
    dataTextField:{
      type:String,
      default:""
    },
    allLabel:{
      type:String,
      default:""
    },
    includeAll:{
      type:Boolean,
      default:false
    },
    defaultIsNull:{
      type:Boolean,default:false
    }
  },
  data(){
    return {
      options:[],
      svalue : this.value,
      valueField:"",
      textField:"",
      allvalue:"",
      allLabelString: this.$t('application.allProjects')
    }
  },
   watch: {
    value(newVal) {
      this.svalue = newVal;
    },
    svalue(newVal, oldVal) {
      if (newVal !== oldVal) {
        this.$emit("input", this.svalue);
      }
    }
  },
  methods:{
    async initDataUrlOptions(){
       let _self = this;
       await axios.post(this.dataUrl, JSON.stringify(this.dataObj)).then(function(resp){
        _self.textField = _self.dataTextField
        _self.valueField = _self.dataValueField
        let getOptions = resp.data.data
        _self.options = []
        _self.allvalue=""
        if(_self.includeAll){
          getOptions.forEach(function(item){
            if(_self.allvalue.length>0){
              _self.allvalue+=","
            }
            _self.allvalue+="'"+item[_self.dataValueField]+"'"
          })
        }
        getOptions.forEach(function(item){
           _self.options.push({label:item[_self.dataTextField],value:"'"+item[_self.dataValueField]+"'"})
        })
        if(_self.includeAll && _self.defaultIsNull==false){
          _self.svalue = _self.allvalue
          _self.$emit("input", _self.svalue);
        }else if(_self.defaultIsNull){
          _self.svalue = ""
          _self.allvalue=""
          _self.$emit("input", _self.svalue);
        }
        _self.$emit("onLoadnDataSuccess",_self.svalue,_self.options)
      }).catch(function(error) {
        console.log(error);
      });
    },
    initGridViewOptions(){
      let queryObj={}
      let _self = this;
      queryObj.queryName = this.queryName;
      axios.post("/query/getquery", JSON.stringify(queryObj)).then(function(resp){
        _self.textField = resp.data.labelField
        _self.valueField = resp.data.valueField
         let getOptions = resp.data.data
        getOptions.forEach(function(item){
           _self.options.push({label:item[_self.dataTextField],value:item[_self.dataValueField]})
        })
        
         console.log(_self.options);
        _self.$emit("onLoadnDataSuccess",_self.svalue,_self.options)
      }).catch(function(error) {
        console.log(error);
      });
    },
    sChange(val) {
      this.$emit("onSelectChange",val);
    }
  },
  mounted(){
    if(this.queryName.length>0){
      console.log("initGridViewOptions");
      this.initGridViewOptions();
    }else{
      console.log("initDataUrlOptions");
      this.initDataUrlOptions();
    }
    if(this.allLabel != ""){
      this.allLabelString = this.allLabel;
    }
  }
}
</script>

<style>

</style>
