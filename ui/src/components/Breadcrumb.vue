<template>
  <a-breadcrumb v-if="showBreadcrumb" class="breadcrumb">
    <a-breadcrumb-item>
      <a-icon type="home"/> {{$messages.breadcrumb.label}}
    </a-breadcrumb-item>
    <a-breadcrumb-item v-for="item in $messages.breadcrumb.options[$route.path]" :key="item">{{item}}</a-breadcrumb-item>
  </a-breadcrumb>
</template>

<script>
  export default {
    name: "Breadcrumb",
    computed: {
      showBreadcrumb() {
        return this.$messages.breadcrumb.options[this.$route.path] !== undefined
          && this.$messages.breadcrumb.options[this.$route.path].length > 0;
      }
    }
  }
</script>

<style scoped>
  .breadcrumb {
    background: #fff;
    border-top: 2px solid #f5f5f5;
    padding: 16px;
  }
</style>