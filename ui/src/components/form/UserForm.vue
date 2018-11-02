<template>
  <div>
    <a-form>
      <a-form-item label="用户名" :labelCol="$style.modalForm.label" :wrapperCol="$style.modalForm.wrapper" required>
        <a-input v-model="user.username" placeholder="请输入用户名" :disabled="user.id !== undefined"></a-input>
      </a-form-item>
      <a-form-item label="姓名" :labelCol="$style.modalForm.label" :wrapperCol="$style.modalForm.wrapper">
        <a-input v-model="user.name" placeholder="请输入姓名"></a-input>
      </a-form-item>
      <a-form-item label="手机号码" :labelCol="$style.modalForm.label" :wrapperCol="$style.modalForm.wrapper">
        <a-input v-model="user.phone" placeholder="请输入手机号码"></a-input>
      </a-form-item>
      <a-form-item label="Email" :labelCol="$style.modalForm.label" :wrapperCol="$style.modalForm.wrapper">
        <a-input v-model="user.email" placeholder="请输入Email"></a-input>
      </a-form-item>
      <a-form-item label="性别" :labelCol="$style.modalForm.label" :wrapperCol="$style.modalForm.wrapper">
        <a-select v-model="user.gender" placeholder="请选择性别">
          <a-select-option v-for="(label, code) in $messages.enums.gender" :key="code">{{label}}</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="生日" :labelCol="$style.modalForm.label" :wrapperCol="$style.modalForm.wrapper">
        <a-date-picker :value="birthday" :disabledDate="disabledDate" allowClear format="YYYY-MM-DD" @change="handleBirthdayChange"></a-date-picker>
      </a-form-item>
    </a-form>
  </div>
</template>

<script>
  import moment from 'moment';
  export default {
    name: "UserForm",
    computed: {
      user() {
        return this.$store.getters.record;
      },
      birthday() {
        return this.user.birthday ? moment(this.user.birthday) : undefined;
      }
    },
    watch: {
      user(val) {
        this.$store.dispatch('setRecord', val);
      }
    },
    methods: {
      handleBirthdayChange(date, dateString) {
        this.$set(this.user, 'birthday', dateString);
      },
      disabledDate(current) {
        return current >= moment().endOf('day');
      }
    }
  }
</script>

<style scoped>

</style>