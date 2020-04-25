import Vue from 'vue'
import ecmSvgIcon from '@/components/ecm-svg-icon'// svg组件
import generateIconsView from '@/views/icons/svg-icons/generateIconsView.js'// just for @/views/icons , you can delete it
// register globally
Vue.component('svg-icon', ecmSvgIcon)

const requireAll = requireContext => requireContext.keys().map(requireContext)
const req = require.context('./svg', false, /\.svg$/)
const iconMap = requireAll(req)
generateIconsView.generate(iconMap)
