<template>
  <div id="app">
    <img src="https://gw.alipayobjects.com/zos/rmsportal/KpnpchXsobRgLElEozzI.svg" alt="">
    <h1 class="title">404</h1>
    <h3 class="sub-title text-mute">抱歉，你访问的页面不存在</h3>
    <a-button type="primary" @click="gotoIndex">返回主页</a-button>
  </div>
</template>

<script>
  export default {
    methods: {
      gotoIndex() {
        window.location.replace('/index');
      }
    }
  }
</script>

<style>

</style>
