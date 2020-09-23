<template>
  <el-col :span="24">
    <el-tag
      :key="tag"
      v-for="tag in value"
      closable
      :disable-transitions="false"
      @click="handleClick(tag)"
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
    event: "change",
    isEdit: false,
    editIndex : 0
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
    handleClick(tag) {
        this.isEdit = true;
        this.editIndex = this.value.indexOf(tag);
        this.inputValue = tag;
        this.inputVisible = true;
        this.$nextTick(_ => {
          this.$refs.saveTagInput.$refs.input.focus();
        });
      },

      showInput() {
        this.isEdit = false;
        this.inputVisible = true;
        this.$nextTick(_ => {
          this.$refs.saveTagInput.$refs.input.focus();
        });
      },

      handleInputConfirm() {
        //debugger
        let inputValue = this.inputValue;
        if(this.isEdit){
          this.value.fill(inputValue, this.editIndex,this.editIndex+1);
        }
        else{
          
          if (inputValue) {
            if(!this.value || this.value == null){
              this.value = [];
            }
            this.value.push(inputValue);
          }
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
