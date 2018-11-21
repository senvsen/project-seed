<template>
  <div class="modify-password-container">
    <h3 class="header-title">修改密码</h3>
    <a-form>
      <a-form-item label="原密码" required>
        <a-input type="password" v-model="modifyPasswordForm.oldPassword" placeholder="请填写原来的密码"></a-input>
      </a-form-item>
      <a-form-item label="新密码" required>
        <a-input type="password" v-model="modifyPasswordForm.newPassword" placeholder="请填写新的密码"></a-input>
      </a-form-item>
      <a-form-item label="确认密码" required>
        <a-input type="password" v-model="modifyPasswordForm.confirmPassword" placeholder="请再次填写新密码以进行确认"></a-input>
      </a-form-item>
      <a-form-item>
        <a-button type="primary" @click="handleModify">确认修改</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<script>
  export default {
    name: "ModifyPassword",
    data() {
      return {
        modifyPasswordForm: {}
      }
    },
    methods: {
      handleModify() {
        this.$api.auth.modifyPassword(this.modifyPasswordForm).then(() => {
          this.$message.success(this.$messages.successResult.operation);
          this.modifyPasswordForm = {};
        });
      }
    }
  }
</script>

<style scoped>
  .modify-password-container {
    width: 25%;
  }
</style>