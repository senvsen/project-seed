<template>
  <div>
    <nav class="breadcrumb is-small" aria-label="breadcrumbs">
      <ul>
        <li>
          <a @click="$router.push('/')">
            <span class="icon is-small">
              <i class="fas fa-home mr-1"></i>
            </span>
            主页
          </a>
        </li>
        <li v-for="item in $store.getters.breadcrumb" :key="item" class="is-active"><a href="#">{{item}}</a></li>
      </ul>
    </nav>
  </div>
</template>

<script>
  export default {
    name: "Breadcrumb"
  }
</script>

<style scoped>

</style>