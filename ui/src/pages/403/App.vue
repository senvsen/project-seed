<template>
  <div class="error-page error-403">
    <div class="has-text-centered">
      <div class="error-title">403</div>
      <p class="error-subtitle has-text-mute">{{$messages.errorPage.tip.error403}}</p>
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
  .error-403 {
    background-image: url("../../assets/403.svg");
  }
</style>