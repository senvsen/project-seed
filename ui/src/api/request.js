import Vue from 'vue'
import axios from 'axios'

const request = axios.create({
  baseURL: ''
});

request.interceptors.response.use(res => {
  if (res.status !== 200) {
    return Promise.reject(res);
  }
  if (res.data.code === 200) {
    return Promise.resolve(res.data);
  }
  Vue.toasted.error(res.data.msg, {icon: 'times-circle'});
  return Promise.reject(res);
}, error => {
  if (error.response === undefined || error.response === null) {
    Vue.toasted.error(error, {icon: 'times-circle'});
    return Promise.reject(error);
  }
  if (error.response.status === 401) {
    window.location.replace('/login');
  }
  if (error.response.data.code) {
    Vue.toasted.error(`${error.response.data.code} - ${error.response.data.msg}`, {icon: 'times-circle'});
    return Promise.reject(error.response.data);
  }
  Vue.toasted.error(`${error} - ${error.response.statusText}`, {icon: 'times-circle'});
  return Promise.reject(error.response);
});

export default request