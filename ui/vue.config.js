const appName = 'Seed';

module.exports = {
  pages: {
    index: {
      entry: 'src/pages/index/main.js',
      title: appName
    },
    login: {
      entry: 'src/pages/login/main.js',
      title: '登录 | ' + appName
    },
    403: {
      entry: 'src/pages/403/main.js',
      title: '403 | ' + appName
    },
    404: {
      entry: 'src/pages/404/main.js',
      title: '404 | ' + appName
    },
    500: {
      entry: 'src/pages/500/main.js',
      title: '500 | ' + appName
    }
  },
  baseUrl: "/"
};