<template>
  <div>
    仪表盘
    <manage-page>
      <template slot="ext-toolbar">
        <button class="button">批量删除</button>
      </template>
    </manage-page>
  </div>
</template>

<script>
  import ManagePage from "../../../../components/ManagePage";
  export default {
    name: "Dashboard",
    components: {ManagePage}
  }
</script>

<style scoped>

</style>