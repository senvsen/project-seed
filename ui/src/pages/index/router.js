import Vue from 'vue'
import Router from 'vue-router'
import store from './store'
import config from '../../config'

import Page from './views/Page'
import Dashboard from './views/custom/Dashboard'
import CodeGen from './views/custom/CodeGen'

const customPage = {
  'dashboard': Dashboard,
  'code-gen': CodeGen
};

Vue.use(Router);

const route = {
  path: '/',
  component: Page,
  redirect: '/dashboard',
  children: []
};

config.sidebar.forEach(menu => {
  if (menu.children) {
    menu.children.forEach(item => {
      if (item.link) {
        const page = {
          path: item.link,
          component: Page,
          meta: {
            key: item.link,
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
  } else if (menu.link) {
    const page = {
      path: menu.link,
      component: Page,
      meta: {
        menuKey: menu.key
      }
    };
    if (menu.pageType) {
      page.meta.pageType = menu.pageType;
    } else if (menu.custom) {
      page.component = customPage[menu.custom];
    }
    route.children.push(page);
  }
});

const router = new Router({
  routes: [route]
});

router.beforeEach((to, from, next) => {
  store.dispatch('setPageType', to.meta.pageType || '');
  store.dispatch('setCurrentPage', to.path);
  store.dispatch('setKey', to.meta.key || to.path);
  store.dispatch('setMenuKey', to.meta.menuKey || '');
  store.dispatch('setBreadcrumb', to.meta.breadcrumb || []);
  next();
});

export default router