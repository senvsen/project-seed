<template>
  <a-form>
    <a-form-item label="权限Key" :labelCol="$style.modalForm.label" :wrapperCol="$style.modalForm.wrapper" required>
      <a-input v-model="privilege.privilegeKey" placeholder="请填写权限Key，不能与其他权限Key重复"></a-input>
    </a-form-item>
    <a-form-item label="权限名称" :labelCol="$style.modalForm.label" :wrapperCol="$style.modalForm.wrapper" required>
      <a-input v-model="privilege.name" placeholder="请填写权限名称"></a-input>
    </a-form-item>
    <a-form-item label="权限类型" :labelCol="$style.modalForm.label" :wrapperCol="$style.modalForm.wrapper" required>
      <a-select :value="$messages.enums.privilegeType[privilege.privilegeType]" @change="handlePrivilegeTypeChange" placeholder="请选择权限类型">
        <a-select-option v-for="(label, code) in $messages.enums.privilegeType" :key="code" :value="code">{{label}}</a-select-option>
      </a-select>
    </a-form-item>
    <a-form-item label="权限描述" :labelCol="$style.modalForm.label" :wrapperCol="$style.modalForm.wrapper" required>
      <a-textarea v-model="privilege.description" placeholder="请填写权限描述"></a-textarea>
    </a-form-item>
  </a-form>
</template>

<script>
  export default {
    name: "PrivilegeForm",
    computed: {
      privilege() {
        return this.$store.getters.record;
      }
    },
    watch: {
      privilege(val) {
        this.$store.dispatch('setRecord', val);
      }
    },
    methods: {
      handlePrivilegeTypeChange(value) {
        this.$set(this.privilege, 'privilegeType', value);
      }
    }
  }
</script>

<style scoped>

</style>