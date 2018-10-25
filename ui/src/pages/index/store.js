import Vue from 'vue'
import Vuex from 'vuex'
import config from '../../config'

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    theme: 'is-primary',
    locale: 'zh_CN',
    pageType: '',
    currentPage: '/',
    key: '',
    menuKey: config.sidebar[0].options[0].label,
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
    key(state) {
      return state.key;
    },
    menuKey(state) {
      return state.menuKey;
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
    setKey(state, key) {
      state.key = key;
    },
    setMenuKey(state, menuKey) {
      state.menuKey = menuKey;
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
    setKey({commit}, key) {
      commit('setKey', key);
    },
    setMenuKey({commit}, menuKey) {
      commit('setMenuKey', menuKey);
    },
    setBreadcrumb({commit}, breadcrumb) {
      commit('setBreadcrumb', breadcrumb);
    }
  }
})
