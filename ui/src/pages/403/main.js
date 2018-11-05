import Vue from 'vue'
import App from './App'
import messages from '../../messages'

import '../../styles/index.css'

Vue.config.productionTip = false;

Vue.prototype.$messages = messages;

new Vue({
  render: h => h(App)
}).$mount('#app');