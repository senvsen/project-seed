import Vue from 'vue'
import Vuex from 'vuex'
import config from './config'

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    theme: 'is-primary',
    locale: 'zh_CN',
    pageType: '',
    currentPage: '/',
    menuKey: 'system',
    sidebar: config.nav.menu.system.sidebar,
    breadcrumb: []
  },
  getters: {
    theme(state) {
      return state.theme;
    },
    locale(state) {
      return state.locale;
    },
    pageType(state) {
      return state.pageType;
    },
    currentPage(state) {
      return state.currentPage;
    },
    menuKey(state) {
      return state.menuKey;
    },
    sidebar(state) {
      return state.sidebar;
    },
    breadcrumb(state) {
      return state.breadcrumb;
    }
  },
  mutations: {
    setTheme(state, theme) {
      state.theme = theme;
    },
    setLocale(state, locale) {
      state.locale = locale;
    },
    setPageType(state, pageType) {
      state.pageType = pageType;
    },
    setCurrentPage(state, page) {
      state.currentPage = page;
    },
    setMenuKey(state, menuKey) {
      state.menuKey = menuKey;
    },
    setSidebar(state, sidebar) {
      state.sidebar = sidebar;
    },
    setBreadcrumb(state, breadcrumb) {
      state.breadcrumb = breadcrumb;
    }
  },
  actions: {
    setTheme({commit}, theme) {
      commit('setTheme', theme);
    },
    setLocale({commit}, locale) {
      commit('setLocale', locale);
    },
    setPageType({commit}, pageType) {
      commit('setPageType', pageType);
    },
    setCurrentPage({commit}, page) {
      commit('setCurrentPage', page);
    },
    setMenuKey({commit}, menuKey) {
      commit('setMenuKey', menuKey);
    },
    setSidebar({commit}, sidebar) {
      commit('setSidebar', sidebar);
    },
    setBreadcrumb({commit}, breadcrumb) {
      commit('setBreadcrumb', breadcrumb);
    }
  }
})
