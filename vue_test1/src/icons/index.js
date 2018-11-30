import Vue from 'vue'
import SvgIcon from '@/components/SvgIcon'// svg组件

// register globally
Vue.component('svg-icon', SvgIcon)

const req = require.context('./svg', false, /\.svg$/)
console.log(req);
const requireAll = requireContext =>{
  requireContext.keys().map(requireContext)
}

const iconMap = requireAll(req);

