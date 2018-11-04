<template>
  <div>
    <a-form hide-required-mark>
      <a-form-item label="ID" :labelCol="$style.searchForm.label" :wrapperCol="$style.searchForm.wrapper">
        <a-input v-model="scheduleSearch.id" placeholder="请填写要查找的ID"></a-input>
      </a-form-item>
      <a-form-item label="关键字" :labelCol="$style.searchForm.label" :wrapperCol="$style.searchForm.wrapper">
        <a-input v-model="scheduleSearch.keyword" placeholder="请填写查找关键字"></a-input>
      </a-form-item>
      <a-form-item label="状态" :labelCol="$style.searchForm.label" :wrapperCol="$style.searchForm.wrapper">
        <a-select v-model="scheduleSearch.paused" allowClear placeholder="请选择目标定时任务的状态">
          <a-select-option :value="1">已暂停</a-select-option>
          <a-select-option :value="0">运行中</a-select-option>
        </a-select>
      </a-form-item>
    </a-form>
  </div>
</template>

<script>
  export default {
    name: "ScheduleSearch",
    computed: {
      scheduleSearch() {
        return this.$store.getters.search;
      },
      watch: {
        scheduleSearch(val) {
          this.$store.dispatch('setSearch', val);
        }
      }
    }
  }
</script>

<style scoped>

</style>