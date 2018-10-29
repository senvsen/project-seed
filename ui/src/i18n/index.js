import Vue from 'vue'
import VueI18n from 'vue-i18n'
import zh_CN from './zh_CN'
import en from './en'

Vue.use(VueI18n);

const messages = {
  zh_CN: zh_CN,
  en: en
};

const locale = localStorage.getItem('locale');
const i18n = new VueI18n({
  locale: locale || 'zh_CN',
  messages: messages
});

export default i18n