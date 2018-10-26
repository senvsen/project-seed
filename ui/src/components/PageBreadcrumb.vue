<template>
  <div>
    <span>
      <router-link to="/">
        <span class="icon is-small breadcrumb-home">
          <i class="fa fa-home"></i>
        </span>
        主页
      </router-link>
    </span>
    <span v-for="item in $store.getters.breadcrumb">
      <span v-if="separator" class="separator">{{separator}}</span>
      {{item}}
    </span>
  </div>
</template>

<script>
  export default {
    name: "PageBreadcrumb",
    props: {
      separator: {
        type: String,
        required: false,
        default: '/'
      }
    }
  }
</script>

<style scoped>
  .separator {
    margin: 0 2px;
  }
  .icon.breadcrumb-home {
    vertical-align: middle;
  }
</style>