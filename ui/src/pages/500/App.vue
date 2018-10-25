<template>
  <div class="error-page error-500">
    <div class="columns is-centered">
      <div class="column is-narrow has-text-centered">
        <div class="title is-size-1">500</div>
        <p class="subtitle has-text-grey-light is-size-6">抱歉，服务器出错了</p>
        <a class="button is-primary" href="/index">返回主页</a>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "App"
  }
</script>

<style scoped>
  .error-500 {
    background-image: url("../../assets/500.svg");
  }
</style>