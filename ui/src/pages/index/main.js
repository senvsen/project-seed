import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'
import config from '../../config'
import api from '../../api'
import utils from '../../utils'
import VueBlu from 'vue-blu'

import 'bulma/css/bulma.min.css'
import 'vue-blu/dist/css/vue-blu.min.css'
import '../../styles/index.css'

Vue.config.productionTip = false;

Vue.use(VueBlu);

Vue.prototype.$config = config;
Vue.prototype.$api = api;
Vue.prototype.$utils = utils;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app');
