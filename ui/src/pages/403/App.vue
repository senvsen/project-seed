<template>
  <div id="app">
    <img src="https://gw.alipayobjects.com/zos/rmsportal/wZcnGqRDyhPOEYFcZDnb.svg" alt="">
    <h1 class="title">403</h1>
    <h3 class="sub-title text-mute">抱歉，你无权访问该页面</h3>
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
