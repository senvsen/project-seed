<template>
  <a-form>
    <a-form-item label="角色Key" :labelCol="$style.form.label" :wrapperCol="$style.form.wrapper" required>
      <a-input v-model="role.roleKey" placeholder="请填写角色Key，不能与其他角色Key重复"></a-input>
    </a-form-item>
    <a-form-item label="角色名称" :labelCol="$style.form.label" :wrapperCol="$style.form.wrapper" required>
      <a-input v-model="role.name" placeholder="请填写角色名称"></a-input>
    </a-form-item>
    <a-form-item label="角色描述" :labelCol="$style.form.label" :wrapperCol="$style.form.wrapper" required>
      <a-textarea v-model="role.description" placeholder="请填写角色描述"></a-textarea>
    </a-form-item>
  </a-form>
</template>

<script>
  export default {
    name: "RoleForm",
    computed: {
      role() {
        return this.$store.getters.record;
      }
    },
    watch: {
      role(val) {
        this.$store.dispatch('setRecord', val);
      }
    }
  }
</script>

<style scoped>

</style>