<template>
  <div class="error-page error-500">
    <div class="has-text-centered">
      <div class="error-title">500</div>
      <p class="error-subtitle has-text-mute">{{$messages.errorPage.tip.error500}}</p>
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
  .error-500 {
    background-image: url("../../assets/500.svg");
  }
</style>