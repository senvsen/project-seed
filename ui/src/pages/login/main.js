import Vue from 'vue'
import App from './App'
import VueBlu from 'vue-blu'
import api from '../../api'
import utils from '../../utils'

import 'vue-blu/dist/css/vue-blu.min.css'
import '../../styles/index.css'

Vue.config.productionTip = false;

Vue.use(VueBlu);

Vue.prototype.$api = api;
Vue.prototype.$utils = utils;

new Vue({
  render: h => h(App)
}).$mount('#app');