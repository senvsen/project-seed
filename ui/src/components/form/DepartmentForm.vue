<template>
  <a-form>
    <a-form-item label="部门Key" :labelCol="$style.form.label" :wrapperCol="$style.form.wrapper" required>
      <a-input v-model="department.departmentKey" placeholder="请填写部门Key，不能与其他部门Key重复"></a-input>
    </a-form-item>
    <a-form-item label="部门名称" :labelCol="$style.form.label" :wrapperCol="$style.form.wrapper" required>
      <a-input v-model="department.name" placeholder="请填写部门名称"></a-input>
    </a-form-item>
  </a-form>
</template>

<script>
  export default {
    name: "DepartmentForm",
    computed: {
      department() {
        return this.$store.getters.record;
      }
    },
    watch: {
      department(val) {
        this.$store.dispatch('setRecord', val);
      }
    }
  }
</script>

<style scoped>

</style>