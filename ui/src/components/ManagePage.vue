<template>
  <div>
    <breadcrumb/>
    <a class="navbar-divider"></a>
    <nav class="level mt-1">
      <div class="level-left">
        <button class="button is-success is-small mr-1">
          <i class="fas fa-plus mr-1"></i>创建
        </button>
        <button class="button is-danger is-small">
          <i class="fas fa-trash-alt mr-1"></i>批量删除
        </button>
      </div>
      <div class="level-right">
        <p class="control is-size-7">编号：</p>
        <p class="control mr-1">
          <input type="text" class="input is-small" placeholder="请填写ID">
        </p>
        <button class="button is-primary is-small">
          <i class="fas fa-search mr-1"></i>查找
        </button>
      </div>
    </nav>
  </div>
</template>

<script>
  import Breadcrumb from "./Breadcrumb";
  export default {
    name: "ManagePage",
    components: {Breadcrumb}
  }
</script>

<style scoped>

</style>