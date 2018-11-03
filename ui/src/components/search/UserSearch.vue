<template>
  <div>
    <a-form hide-required-mark>
      <a-form-item label="ID" :labelCol="$style.searchForm.label" :wrapperCol="$style.searchForm.wrapper">
        <a-input v-model="userSearch.id" placeholder="请填写要查找的ID"></a-input>
      </a-form-item>
      <a-form-item label="关键字" :labelCol="$style.searchForm.label" :wrapperCol="$style.searchForm.wrapper">
        <a-input v-model="userSearch.keyword" placeholder="请填写查找关键字"></a-input>
      </a-form-item>
      <a-form-item label="性别" :labelCol="$style.searchForm.label" :wrapperCol="$style.searchForm.wrapper">
        <a-select v-model="userSearch.gender" allowClear placeholder="请选择目标用户的性别">
          <a-select-option v-for="(label, code) in $messages.enums.gender" :key="code">{{label}}</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="状态" :labelCol="$style.searchForm.label" :wrapperCol="$style.searchForm.wrapper">
        <a-select v-model="userSearch.enabled" allowClear placeholder="请选择目标用户的状态">
          <a-select-option :value="1">可用</a-select-option>
          <a-select-option :value="0">禁用</a-select-option>
        </a-select>
      </a-form-item>
    </a-form>
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
    },
  }
</script>

<style scoped>

</style>