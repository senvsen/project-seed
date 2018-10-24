import Vue from 'vue'
import Router from 'vue-router'
import store from './store'
import config from './config'

import Page from './views/Page'

Vue.use(Router);

const route = {
  path: '/',
  component: Page,
  children: []
};
config.sidebar.forEach(subMenu => {
  subMenu.options.forEach(option => {
    if (option.children) {
      option.children.forEach(item => {
        if (item.link) {
          const page = {
            path: item.link,
            component: Page,
            meta: {}
          };
          if (item.pageType) {
            page.meta.pageType = item.pageType;
          }
          route.children.push(page);
        }
      });
    } else if (option.link) {
      const page = {
        path: option.link,
        component: Page,
        meta: {}
      };
      if (option.pageType) {
        page.meta.pageType = option.pageType;
      }
      route.children.push(page);
    }
  });
});

const router = new Router({
  routes: [route]
});

router.beforeEach((to, from, next) => {
  store.dispatch('setPageType', to.meta.pageType ? to.meta.pageType : '');
  store.dispatch('setCurrentPage', to.path);
  next();
});

export default router
