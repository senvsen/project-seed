import Vue from 'vue'
import App from './App'
import VueBlu from 'vue-blu'

import 'bulma/css/bulma.min.css'
import '../../styles/index.css'

Vue.config.productionTip = false;

Vue.use(VueBlu);

new Vue({
  render: h => h(App)
}).$mount('#app');