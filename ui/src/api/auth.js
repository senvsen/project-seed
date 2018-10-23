import request from './request'

const prefix = '/auth';
const auth = {
  getUserPage() {
    return request.post(`${prefix}/user/page`);
  }
};

export default auth