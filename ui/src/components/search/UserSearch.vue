<template>
  <div>
    User Search
    <a-input v-model="userSearch.phone" placeholder="请填写手机号码"></a-input>
  </div>
</template>

<script>
  export default {
    name: "UserSearch",
    computed: {
      userSearch() {
        return this.$store.getters.search;
      }
    },
    watch: {
      userSearch(val) {
        this.$store.dispatch('setSearch', val);
      }
    }
  }
</script>

<style scoped>

</style>