import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'
import index from '../../i18n/index'
import api from '../../api'
import utils from '../../utils'
import config from '../../config'
import Antd from 'ant-design-vue'

import 'ant-design-vue/dist/antd.min.css'
import '../../styles/index.css'

Vue.config.productionTip = false;

Vue.use(Antd);

Vue.prototype.$api = api;
Vue.prototype.$config = config;
Vue.prototype.$utils = utils;

new Vue({
  router,
  store,
  i18n: index,
  render: h => h(App)
}).$mount('#app');
