<template>
  <a-breadcrumb class="mb-2">
    <a-breadcrumb-item>
      <a-icon type="home"/> {{$messages.breadcrumb.label}}
    </a-breadcrumb-item>
    <a-breadcrumb-item v-for="item in $messages.breadcrumb.options[$route.path]" :key="item">{{item}}</a-breadcrumb-item>
  </a-breadcrumb>
</template>

<script>
  export default {
    name: "Breadcrumb"
  }
</script>

<style scoped>

</style>