import request from './request'

export default {
  login(loginForm) {
    return request.post('/login', loginForm);
  }
}