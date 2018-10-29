<template>
  <div class="error-page error-403">
    <div class="columns is-centered">
      <div class="column is-narrow has-text-centered">
        <div class="title is-size-1">403</div>
        <p class="subtitle has-text-grey-light is-size-5">{{$t('errorPage.tip.403')}}</p>
        <a class="button is-primary" href="/index">{{$t('errorPage.btnContent')}}</a>
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
  .error-403 {
    background-image: url("../../assets/403.svg");
  }
</style>