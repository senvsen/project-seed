<template>
  <div>
    <a-form hide-required-mark>
      <a-form-item label="ID" :labelCol="$style.searchForm.label" :wrapperCol="$style.searchForm.wrapper">
        <a-input v-model="accountSearch.id" placeholder="请填写要查找的ID"></a-input>
      </a-form-item>
      <a-form-item label="关键字" :labelCol="$style.searchForm.label" :wrapperCol="$style.searchForm.wrapper">
        <a-input v-model="accountSearch.keyword" placeholder="请填写查找关键字"></a-input>
      </a-form-item>
    </a-form>
  </div>
</template>

<script>
  export default {
    name: "MpAccountSearch",
    computed: {
      accountSearch() {
        return this.$store.getters.search;
      }
    },
    watch: {
      accountSearch(val) {
        this.$store.dispatch('setSearch', val);
      }
    }
  }
</script>

<style scoped>

</style>