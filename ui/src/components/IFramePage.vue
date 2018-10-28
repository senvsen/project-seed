<template>
  <div>
    <iframe :src="src" class="iframe-content" frameborder="0"></iframe>
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
    border-top: 1px solid #e8e8e8;
    width: 100%;
    min-height: calc(100vh - 138px);
  }
</style>