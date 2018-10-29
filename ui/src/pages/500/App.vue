<template>
  <div class="error-page error-500">
    <div class="columns is-centered">
      <div class="column is-narrow has-text-centered">
        <div class="title is-size-1">500</div>
        <p class="subtitle has-text-grey-light is-size-5">{{$t('errorPage.tip.500')}}</p>
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
  .error-500 {
    background-image: url("../../assets/500.svg");
  }
</style>