import Vue from 'vue'
import App from './App'
import index from '../../i18n/index'

import 'bulma/css/bulma.min.css'
import '../../styles/index.css'

Vue.config.productionTip = false;

new Vue({
  i18n: index,
  render: h => h(App)
}).$mount('#app');