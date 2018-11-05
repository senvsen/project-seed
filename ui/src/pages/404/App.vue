<template>
  <div class="error-page error-404">
    <div class="has-text-centered">
      <div class="error-title">404</div>
      <p class="error-subtitle has-text-mute">{{$messages.errorPage.tip.error404}}</p>
      <a class="error-back-btn" href="/index">{{$messages.errorPage.btnContent}}</a>
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