import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    theme: 'is-primary',
    locale: 'zh_CN',
    pageType: '',
    currentPage: '/',
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
    }
  }
})
