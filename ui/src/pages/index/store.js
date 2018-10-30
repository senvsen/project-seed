import Vue from 'vue'
import Vuex from 'vuex'
import messages from '../../messages'

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    pageType: '',
    key: messages.sidebar[0].link,
    menuKey: '',
  },
  getters: {
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
