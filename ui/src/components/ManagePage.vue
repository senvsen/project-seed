<template>
  <div>
    <breadcrumb/>
    <a class="navbar-divider"></a>
    <nav class="level mt-1">
      <div class="level-left">
        <button class="button is-success is-small mr-1" @click="add">
          <i class="fas fa-plus mr-1"></i>创建
        </button>
        <button class="button is-danger is-small" @click="batchDelete">
          <i class="fas fa-trash-alt mr-1"></i>批量删除
        </button>
      </div>
      <div class="level-right">
        <p class="control is-size-7">编号：</p>
        <p class="control mr-1">
          <input type="text" class="input is-small" placeholder="请填写编号">
        </p>
        <p class="control is-size-7">关键字：</p>
        <p class="control mr-1">
          <input type="text" class="input is-small" placeholder="请填写查找关键字">
        </p>
        <button class="button is-primary is-small">
          <i class="fas fa-search mr-1"></i>查找
        </button>
      </div>
    </nav>
    <data-table/>
  </div>
</template>

<script>
  import Breadcrumb from "./Breadcrumb";
  import DataTable from "./DataTable";
  export default {
    name: "ManagePage",
    components: {DataTable, Breadcrumb},
    methods: {
      add() {
        alert('创建');
      },
      batchDelete() {
        alert('批量删除');
      }
    }
  }
</script>

<style scoped>

</style>