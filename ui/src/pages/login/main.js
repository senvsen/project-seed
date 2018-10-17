import Vue from 'vue'
import App from './App.vue'
import Antd from 'ant-design-vue'
import config from '../../app.config'
import api from '../../api'

import 'ant-design-vue/dist/antd.min.css'
import '../../styles/app.css'

Vue.config.productionTip = false

Vue.use(Antd)

Vue.prototype.$config = config
Vue.prototype.$api = api

new Vue({
  render: h => h(App)
}).$mount('#app')
