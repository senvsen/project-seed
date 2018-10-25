import Vue from 'vue'
import Vuex from 'vuex'
import config from '../../config'

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    navTheme: 'is-primary',
    sideTheme: 'is-dark',
    locale: 'zh_CN',
    pageType: '',
    currentPage: '/',
    key: '',
    menuKey: config.sidebar[0].options[0].label,
    breadcrumb: []
  },
  getters: {
    navTheme(state) {
      return state.navTheme;
    },
    sideTheme(state) {
      return state.sideTheme;
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
    setNavTheme(state, theme) {
      state.navTheme = theme;
    },
    setSideTheme(state, theme) {
      state.sideTheme = theme;
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
    setNavTheme({commit}, theme) {
      commit('setNavTheme', theme);
    },
    setSideTheme({commit}, theme) {
      commit('setSideTheme', theme);
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
