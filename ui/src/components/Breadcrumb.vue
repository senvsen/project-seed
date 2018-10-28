<template>
  <a-breadcrumb class="mb-2">
    <a-breadcrumb-item>
      <a-icon type="home"/> 主页
    </a-breadcrumb-item>
    <a-breadcrumb-item v-for="item in $store.getters.breadcrumb" :key="item">{{item}}</a-breadcrumb-item>
  </a-breadcrumb>
</template>

<script>
  export default {
    name: "Breadcrumb"
  }
</script>

<style scoped>

</style>