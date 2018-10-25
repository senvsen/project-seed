const appname = '种子应用';

module.exports = {
  pages: {
    index: {
      entry: 'src/pages/index/main.js',
      title: appname
    },
    login: {
      entry: 'src/pages/login/main.js',
      title: `登录 | ${appname}`
    },
    403: {
      entry: 'src/pages/403/main.js',
      title: `错误 403 | ${appname}`
    },
    404: {
      entry: 'src/pages/404/main.js',
      title: `错误 404 | ${appname}`
    },
    500: {
      entry: 'src/pages/500/main.js',
      title: `错误 500 | ${appname}`
    }
  },
  baseUrl: '/',
  devServer: {
    port: 3000
  }
};