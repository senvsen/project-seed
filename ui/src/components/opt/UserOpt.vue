<template>
  <span>
    <a-button size="small">分配角色</a-button>
  </span>
</template>

<script>
  export default {
    name: "UserOpt",
    props: {
      rowData: {
        type: Object,
        required: true
      }
    },
    created() {
      console.log(this.rowData);
    }
  }
</script>

<style scoped>

</style>