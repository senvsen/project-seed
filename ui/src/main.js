import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import config from './config'
import api from './api'
import Toasted from 'vue-toasted'

import 'bulma/css/bulma.min.css'
import '@fortawesome/fontawesome-free/css/all.css'
import './styles/index.css'

Vue.config.productionTip = false;

Vue.use(Toasted, {
  theme: 'primary',
  position: 'bottom-right',
  duration: 5000,
  action: [
    {
      text: '清除', onClick: (e, toastObject) => {
        toastObject.goAway(0);
      }
    }
  ],
  iconPack: 'fontawesome'
});

Vue.prototype.$config = config;
Vue.prototype.$api = api;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app');
