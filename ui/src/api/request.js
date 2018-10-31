import axios from 'axios'
import utils from '../utils'
import {message} from 'ant-design-vue'

const request = axios.create({
  baseURL: '',
  headers: {'X-Requested-With': 'XMLHttpRequest'}
});

request.interceptors.response.use(res => {
  if (res.status !== 200) {
    return Promise.reject(res);
  }
  if (res.data.code === 200) {
    return Promise.resolve(res.data);
  }
  message.error(res.data.msg);
  return Promise.reject(res);
}, error => {
  if (error.response === undefined || error.response === null) {
    message.error(error);
    return Promise.reject(error);
  }
  if (error.response.status === 401) {
    utils.http.goPage('/login');
  }
  if (error.response.data.code) {
    message.error(`${error.response.data.code} - ${error.response.data.msg}`);
    return Promise.reject(error.response.data);
  }
  message.error(`${error} - ${error.response.statusText}`);
  return Promise.reject(error.response);
});

export default request