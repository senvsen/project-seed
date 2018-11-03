<template>
  <span>
    <span v-show="keys.length > 0">
      <a-popconfirm title="确定批量禁用选中的用户吗？" trigger="click" placement="top" @confirm="handleBatchDisabled"
                    :okText="$messages.operation.confirmText" :cancelText="$messages.operation.cancelText">
        <a-button icon="close-circle-o" class="mr-1">批量禁用</a-button>
      </a-popconfirm>
      <a-popconfirm title="确定批量解禁选中的用户吗？" trigger="click" placement="top" @confirm="handleBatchEnabled"
                    :okText="$messages.operation.confirmText" :cancelText="$messages.operation.cancelText">
        <a-button icon="check-circle-o">批量解禁</a-button>
      </a-popconfirm>
    </span>
  </span>
</template>

<script>
  export default {
    name: "UserToolbar",
    props: {
      keys: {
        type: Array,
        required: false,
        default() {
          return [];
        }
      }
    },
    methods: {
      handleBatchDisabled() {
        const users = [];
        this.keys.forEach(key => {
          users.push({id: key, enabled: false});
        });
        this.$api.auth.batchSaveUser(users).then(() => {
          this.$message.success(this.$messages.successResult.operation);
          this.$emit('updated');
        });
      },
      handleBatchEnabled() {
        const users = [];
        this.keys.forEach(key => {
          users.push({id: key, enabled: true});
        });
        this.$api.auth.batchSaveUser(users).then(() => {
          this.$message.success(this.$messages.successResult.operation);
          this.$emit('updated');
        });
      }
    }
  }
</script>

<style scoped>

</style>