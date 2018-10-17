<template>
  <div id="app">
    <img src="https://gw.alipayobjects.com/zos/rmsportal/RVRUAYdCGeYNBWoKiIwB.svg" alt="">
    <h1 class="title">500</h1>
    <h3 class="sub-title text-mute">抱歉，服务器出错了</h3>
    <a-button type="primary" @click="gotoIndex">返回主页</a-button>
  </div>
</template>

<script>
  export default {
    methods: {
      gotoIndex() {
        window.location.replace('/');
      }
    }
  }
</script>

<style>

</style>
