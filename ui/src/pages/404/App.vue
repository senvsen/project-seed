<template>
  <div class="error-page error-404">
    <div class="columns is-centered">
      <div class="column is-narrow has-text-centered">
        <div class="title is-size-1">404</div>
        <p class="subtitle has-text-grey-light is-size-6">抱歉，你访问的页面不存在</p>
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
  .error-404 {
    background-image: url("../../assets/404.svg");
  }
</style>