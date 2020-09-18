<template>
  <el-col :span="24">
    <el-tag
      :key="tag"
      v-for="tag in value"
      closable
      :disable-transitions="false"
      @close="handleClose(tag)">
      {{tag}}
    </el-tag>
    <el-input
      class="input-new-tag"
      v-if="inputVisible"
      v-model="inputValue"
      ref="saveTagInput"
      @keyup.enter.native="handleInputConfirm"
      @blur="handleInputConfirm"
    >
    </el-input>
    <el-button v-else class="button-new-tag" size="small" @click="showInput">+</el-button>
  </el-col>
</template>

<script>
export default {
  model: {
    prop: "value",
    event: "change"
  },
  props:{
    value:{
      type:[Array],
      required: false
    }
  },
  data(){
    return {
      // dynamicTags:[],
      inputVisible:false,
      inputValue: "",
      labelString: this.$t('application.add')
    }
  },
   watch: {
    
    // value(newVal) {
    //   if(newVal != null){
    //     this.dynamicTags = newVal.split(";");
    //   }
    //   else{
    //     this.dynamicTags = [];
    //   }
    // },
    // dynamicTags(newVal, oldVal) {
    //   if (newVal !== oldVal) {
    //     this.$emit("input", this.dynamicTags);
    //   }
    // }
  },
  methods:{
   
    handleClose(tag) {
        this.value.splice(this.value.indexOf(tag), 1);
        
      },

      showInput() {
        this.inputVisible = true;
        this.$nextTick(_ => {
          this.$refs.saveTagInput.$refs.input.focus();
        });
      },

      handleInputConfirm() {
        let inputValue = this.inputValue;
        if (inputValue) {
          if(!this.value || this.value == null){
            this.value = [];
          }
          this.value.push(inputValue);
        }
        this.inputVisible = false;
        this.inputValue = '';
      }
  },
  mounted(){
    
  }
}
</script>

<style>

</style>
