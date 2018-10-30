<template>
  <span>
    User Operation
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