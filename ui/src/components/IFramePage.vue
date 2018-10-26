<template>
  <div>
    <iframe :src="src" class="iframe-content"></iframe>
  </div>
</template>

<script>
  export default {
    name: "IFramePage",
    props: {
      src: {
        type: String,
        required: true
      }
    }
  }
</script>

<style scoped>
  .iframe-content {
    width: 100%;
    min-height: calc(100vh - 7.33rem);
  }
</style>