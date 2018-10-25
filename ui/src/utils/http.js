const http = {
  //在新的标签页打开url
  openPage: (url) => {
    window.open(url).location;
  },
  //用目标url替换当前页面
  goPage(url) {
    window.location.replace(url);
  },
  //跳转至url
  jumpPage(url) {
    window.location.href = url;
  }
};

export default http