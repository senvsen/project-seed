import Vue from 'vue'
import Router from 'vue-router'
import store from './store'
import config from './config'

import Page from './views/Page'
import Dashboard from './views/custom/Dashboard'
import CodeGen from './views/custom/CodeGen'

Vue.use(Router);

const customPage = {
  'dashboard': Dashboard,
  'code-gen': CodeGen
};

const route = {
  path: '/',
  component: Page,
  redirect: '/dashboard',
  children: []
};
for (let key in config.nav.menu) {
  if (config.nav.menu.hasOwnProperty(key)) {
    config.nav.menu[key].sidebar.forEach(subMenu => {
      subMenu.options.forEach(option => {
        if (option.children) {
          option.children.forEach(item => {
            if (item.link) {
              const page = {
                path: item.link,
                component: Page,
                meta: {
                  menuKey: item.menuKey,
                  breadcrumb: item.breadcrumb
                }
              };
              if (item.pageType) {
                page.meta.pageType = item.pageType;
              } else if (item.custom) {
                page.component = customPage[item.custom];
              }
              route.children.push(page);
            }
          });
        } else if (option.link) {
          const page = {
            path: option.link,
            component: Page,
            meta: {
              menuKey: option.menuKey
            }
          };
          if (option.pageType) {
            page.meta.pageType = option.pageType;
          } else if (option.custom) {
            page.component = customPage[option.custom];
          }
          route.children.push(page);
        }
      });
    });
  }
}


const router = new Router({
  routes: [route]
});

router.beforeEach((to, from, next) => {
  store.dispatch('setPageType', to.meta.pageType || '');
  store.dispatch('setCurrentPage', to.path);
  store.dispatch('setMenuKey', to.meta.menuKey || 'system');
  store.dispatch('setBreadcrumb', to.meta.breadcrumb || []);
  next();
});

export default router
