<template>
  <div class="data-icons">
    <el-row :span="24">
      <template v-for="item in data">
        <el-col :span="span">
          <router-link :to="item.url?item.url:'/'">
          <div class="item" :class="[{'item--easy':discount}]">
            <div class="item-icon" :style="{color:color}">
              <i :class="item.icon"></i>
            </div>
            <div class="item-info">
              <span style="white-space:nowrap;">{{item.title}}</span>
              <span :style="{color:item.color}"><ecm-count-up :start="0" :end="item.count"/></span>
            </div>
          </div>
          </router-link>
        </el-col>
      </template>
    </el-row>
  </div>
</template>

<script>
import ecmCountUp from '@/components/ecm-count-up/index.vue'
export default {
  name: 'ecm-data-icons',
  components: {
    ecmCountUp
  },
  data() {
    return {
      span: this.option.span || 6,
      data: this.option.data,
      color: this.option.color || 'rgb(63, 161, 255)',
      discount: this.option.discount || false,
      url: this.option.url || '/'
    }
  },
  methods:{
    refresh(){
       this.span= this.option.span || 6;
      this.data= this.option.data;
      this.color= this.option.color || 'rgb(63, 161, 255)';
      this.discount= this.option.discount || false;
      this.url= this.option.url || '/';
    }
  },
  props: {
    option: {
      type: Object,
      default: () => {}
    }
  }
}
</script>

<style lang="scss" scoped>
.data-icons .item-icon > i {
    font-size: 46px !important;
}
span{
  font-size: 14px !important;
}
.data-icons .item-info {
    line-height: 20px !important;
}
.data-icons .item {
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    -webkit-box-pack: justify;
    -ms-flex-pack: justify;
    justify-content: space-between;
    width: 80px;
    margin: 0 auto;
}
</style>
