<template>
  <el-select v-model="svalue" @change="sChange" filterable >
    <el-option v-for="item in options" :key="item[valueColumn]" :label="item[labelColumn]" :value="item[valueColumn]">

    </el-option>
  </el-select>
</template>

<script>
export default {
  name:"SqlSelect",
  props:{
    value:{
      type:[String],
      required: true
    },
    queryName:{
      type:String,
      default:""
    }
  },
  data(){
    return {
      options:[],
      svalue : this.value,
      labelColumn:"",
      valueColumn:""
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
    initOptions(){
      let queryObj={}
      let _self = this;
      console.log("queryName"+this.queryName)
      queryObj.queryName = this.queryName;
      console.log(queryObj)
      axios.post("/query/getquery", JSON.stringify(queryObj)).then(function(resp){
        console.log(resp.data)
        _self.labelColumn = resp.data.labelField
        _self.valueColumn = resp.data.valueField
        _self.options = resp.data.data
      }).catch(function(error) {
        console.log(error);
      });
    },
    sChange() {
      this.$emit("sChange");
    }
  },
  mounted(){
    this.initOptions();
  }

}
</script>

<style>

</style>