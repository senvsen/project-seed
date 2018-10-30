<template>
  <div class="error-page error-404">
    <div class="columns is-centered">
      <div class="column is-narrow has-text-centered">
        <div class="title is-size-1">404</div>
        <p class="subtitle has-text-grey-light is-size-5">{{$messages.errorPage.tip.error404}}</p>
        <a class="button is-primary" href="/index">{{$messages.errorPage.btnContent}}</a>
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