<template>
  <el-select v-model="svalue" @change="sChange" filterable >
    <el-option v-for="item in options" :key="item[valueField]" :label="item[valueField]" :value="item[textField]">
    </el-option>
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
      type:[String]
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
    onSelectChange:{
      type:Function,
      default:function(){

      }
    }
  },
  data(){
    return {
      options:[],
      svalue : this.value,
      valueField:"",
      textField:""
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
    initDataUrlOptions(){
       let _self = this;
       axios.post(this.dataUrl, JSON.stringify(this.dataObj)).then(function(resp){
        console.log(resp.data)
        _self.textField = _self.dataTextField
        _self.valueField = _self.dataValueField
        _self.options = resp.data.data
      }).catch(function(error) {
        console.log(error);
      });
    },
    initGridViewOptions(){
      let queryObj={}
      let _self = this;
      console.log("queryName"+this.queryName)
      queryObj.queryName = this.queryName;
      console.log(queryObj)
      axios.post("/query/getquery", JSON.stringify(queryObj)).then(function(resp){
        console.log(resp.data)
        _self.textField = resp.data.labelField
        _self.valueField = resp.data.valueField
        _self.options = resp.data.data
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
      this.initGridViewOptions();
    }else{
      this.initDataUrlOptions();
    }
    
  }

}
</script>

<style>

</style>