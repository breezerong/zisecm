<template>
  <div>
    <el-tooltip effect="dark" placement="bottom">
      <div slot="content">
        <a class="link" :href="url" target="_blank">转到 Github</a>
      </div>
      <el-button class="btn-text can-hover" type="text" @click="handleClick">
        <ecm-icon name="github" style="font-size: 20px"/>
      </el-button>
    </el-tooltip>
  </div>
</template>

<script>
import ecmIcon from '@/components/ecm-icon'
export default {
  name: 'ecm-github',
  components: { ecmIcon },
  data() {
    return {
      url: 'https://github.com/mgbq/ecm-admin'
    }
  },
  methods: {
    handleClick() {
      window.open(this.url)
    }
  }
}
</script>


<style lang="scss" scoped>
.link {
  color: #FFF;
  text-decoration: none;
}
</style>
