<template>
<el-tooltip effect="dark" content="语言设置" placement="bottom">
  <el-dropdown trigger="click" class='international' @command="handleSetLanguage">
    <div>
      <i class="el-icon-info"></i>
    </div>
    <el-dropdown-menu slot="dropdown">
      <el-dropdown-item command="zh-cn" :disabled="language==='zh-cn'">中文</el-dropdown-item>
      <el-dropdown-item command="en" :disabled="language==='en'">English</el-dropdown-item>
    </el-dropdown-menu>
  </el-dropdown>
</el-tooltip>
</template>

<script>
import ecmSvgIcon from '@/components/ecm-svg-icon/index'
export default {
  name: 'ecm-lang-select',
  components: { ecmSvgIcon },
  computed: {
    language() {
      return this.$store.getters.language
    }
  },
  methods: {
    handleSetLanguage(lang) {
      this.$i18n.locale = lang
      this.localStorage.setItem('localeLanguage', lang)
      this.$store.dispatch('setLanguage', lang)
      this.$message({
        message: 'switch language success',
        type: 'success'
      })
    }
  }
}
</script>

<style scoped>
.international-icon {
  font-size: 22px;
  cursor: pointer;
  vertical-align: -5px!important;
}
.el-dropdown{
  color:#5a5e66;
}
</style>


