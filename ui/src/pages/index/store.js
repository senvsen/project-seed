import Vue from 'vue'
import Vuex from 'vuex'
import i18n from '../../i18n'

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    locale: 'zh_CN',
    pageType: '',
    key: i18n.t('sidebar')[0].link,
    menuKey: '',
  },
  getters: {
    locale(state) {
      return state.locale;
    },
    pageType(state) {
      return state.pageType;
    },
    key(state) {
      return state.key;
    },
    menuKey(state) {
      return state.menuKey;
    },
  },
  mutations: {
    setLocale(state, locale) {
      state.locale = locale;
    },
    setPageType(state, pageType) {
      state.pageType = pageType;
    },
    setKey(state, key) {
      state.key = key;
    },
    setMenuKey(state, menuKey) {
      state.menuKey = menuKey;
    },
  },
  actions: {
    setLocale({commit}, locale) {
      commit('setLocale', locale);
    },
    setPageType({commit}, pageType) {
      commit('setPageType', pageType);
    },
    setKey({commit}, key) {
      commit('setKey', key);
    },
    setMenuKey({commit}, menuKey) {
      commit('setMenuKey', menuKey);
    },
  }
})
