import Vue from 'vue'
import Vuex from 'vuex'
import messages from '../../messages'

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    pageType: '',
    key: messages.sidebar[0].link,
    menuKey: '',
    record: {},
    search: {}
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
    record(state) {
      return state.record;
    },
    search(state) {
      return state.search;
    }
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
    setRecord(state, record) {
      state.record = record;
    },
    setSearch(state, search) {
      state.search = search;
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
    setRecord({commit}, record) {
      commit('setRecord', record);
    },
    setSearch({commit}, search) {
      commit('setSearch', search);
    },
  }
})
