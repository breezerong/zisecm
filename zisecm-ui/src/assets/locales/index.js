import Vue from 'vue'
import VueI18n from 'vue-i18n'
import locale from 'element-ui/lib/locale'
import enLocale from 'element-ui/lib/locale/lang/en'
import zhLocale from 'element-ui/lib/locale/lang/zh-CN'

Vue.use(VueI18n)

const DEFAULT_LANG = 'zh-cn'
const LOCALE_KEY = 'localeLanguage'

// 'zh-cn': Object.assign(require('./ecm_zh.json'), zhLocale),
// 'en': Object.assign(require('./ecm_en.json'), enLocale)
const locales = {
  'zh-cn': require('./ecm_zh.json'),
  'en': require('./ecm_en.json')
}

const i18n = new VueI18n({
  locale: window.localStorage.getItem(LOCALE_KEY) || DEFAULT_LANG,
  messages: locales
})
Vue.prototype.locale=locale
Vue.prototype.enLocale=enLocale
Vue.prototype.zhLocale=zhLocale
export const setup = lang => {
  if (lang === undefined) {
    lang = window.localStorage.getItem(LOCALE_KEY)
    if (locales[lang] === undefined) {
      lang = DEFAULT_LANG
    }
  }
  window.localStorage.setItem(LOCALE_KEY, lang)

  Object.keys(locales).forEach(lang => {
    document.body.classList.remove(`lang-${lang}`)
  })
  document.body.classList.add(`lang-${lang}`)
  document.body.setAttribute('lang', lang)
  Vue.config.lang = lang
  // 初始化elementui语言
  if (lang === 'zh-cn') {
    locale.use(zhLocale)
  } else {
    locale.use(enLocale)
  }
  i18n.locale = lang
}

setup()
export default i18n
