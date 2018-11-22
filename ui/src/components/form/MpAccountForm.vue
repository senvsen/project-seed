<template>
  <div>
    <a-form>
      <a-form-item label="账户名称" :labelCol="$style.form.label" :wrapperCol="$style.form.wrapper" required>
        <a-input v-model="account.accountName" placeholder="请填写账户名称"></a-input>
      </a-form-item>
      <a-form-item label="关联类型" :labelCol="$style.form.label" :wrapperCol="$style.form.wrapper">
        <a-select :value="$messages.enums.accountType[account.relatedType]" @change="handleAccountTypeChange" placeholder="请选择关联类型">
          <a-select-option v-for="(label, code) in $messages.enums.accountType" :key="code" :value="code">{{label}}</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="关联ID" :labelCol="$style.form.label" :wrapperCol="$style.form.wrapper">
        <a-input v-model="account.relatedId" placeholder="请填写关联ID"></a-input>
      </a-form-item>
      <a-form-item label="公众号名称" :labelCol="$style.form.label" :wrapperCol="$style.form.wrapper" required>
        <a-input v-model="account.appName" placeholder="请填写公众号名称"></a-input>
      </a-form-item>
      <a-form-item label="AppId" :labelCol="$style.form.label" :wrapperCol="$style.form.wrapper" required>
        <a-input v-model="account.appId" placeholder="请填写公众号AppId"></a-input>
      </a-form-item>
      <a-form-item label="Secret" :labelCol="$style.form.label" :wrapperCol="$style.form.wrapper" required>
        <a-input v-model="account.appSecret" placeholder="请填写公众号Secret"></a-input>
      </a-form-item>
      <a-form-item label="Token" :labelCol="$style.form.label" :wrapperCol="$style.form.wrapper" required>
        <a-input v-model="account.token" placeholder="请填写公众号Token"></a-input>
      </a-form-item>
      <a-form-item label="AesKey" :labelCol="$style.form.label" :wrapperCol="$style.form.wrapper">
        <a-input v-model="account.encodingAesKey" placeholder="请填写公众号EncodingAesKey"></a-input>
      </a-form-item>
    </a-form>
  </div>
</template>

<script>
  export default {
    name: "MpAccountForm",
    computed: {
      account() {
        return this.$store.getters.record;
      }
    },
    watch: {
      account(val) {
        this.$store.dispatch('setRecord', val);
      }
    },
    methods: {
      handleAccountTypeChange(value) {
        this.$set(this.account, 'accountType', value);
      }
    }
  }
</script>

<style scoped>

</style>