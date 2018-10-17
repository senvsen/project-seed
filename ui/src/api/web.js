import request from './request'

const web = {
  login(loginForm) {
    return request.post('/login', loginForm);
  },
}

export default web