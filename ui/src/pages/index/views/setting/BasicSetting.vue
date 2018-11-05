<template>
  <div>
    <h2>基本设置</h2>
    <a-row>
      <a-col :span="12">
        <a-form>
          <a-row :gutter="24">
            <a-col :span="12">
              <a-form-item label="用户名">
                <a-input v-model="user.username" disabled></a-input>
              </a-form-item>
              <a-form-item label="姓名">
                <a-input v-model="user.name" placeholder="请填写姓名"></a-input>
              </a-form-item>
              <a-form-item label="手机号码">
                <a-input v-model="user.phone" placeholder="请填写手机号码"></a-input>
              </a-form-item>
              <a-form-item label="Email">
                <a-input v-model="user.email" placeholder="请填写Email"></a-input>
              </a-form-item>
              <a-form-item label="性别">
                <a-select :value="$messages.enums.gender[user.gender]" @change="handleGenderChange" placeholder="请选择性别" class="gender-select">
                  <a-select-option v-for="(label, code) in $messages.enums.gender" :key="code" :value="code">{{label}}</a-select-option>
                </a-select>
              </a-form-item>
              <a-form-item label="生日">
                <a-date-picker :value="birthday" :disabledDate="disabledDate" allowClear format="YYYY-MM-DD" @change="handleBirthdayChange"></a-date-picker>
              </a-form-item>
              <a-form-item>
                <a-button type="primary" @click="handleUpdate">更新基本信息</a-button>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label="头像">
                <div>
                  <a-avatar src="https://avatars3.githubusercontent.com/u/12194490?s=460&v=4" :size="96"></a-avatar>
                </div>
                <a-button icon="upload">更换头像</a-button>
              </a-form-item>
            </a-col>
          </a-row>
        </a-form>
      </a-col>
    </a-row>
  </div>
</template>

<script>
  import moment from 'moment';
  export default {
    name: "BasicSetting",
    data() {
      return {
        user: {},
      }
    },
    created() {
      this.fetchData();
    },
    computed: {
      birthday() {
        return this.user.birthday ? moment(this.user.birthday) : undefined;
      }
    },
    methods: {
      fetchData() {
        this.$api.auth.getCurrentUser().then(res => {
          this.user = res.data;
        });
      },
      handleGenderChange(value) {
        this.$set(this.user, 'gender', value);
      },
      handleBirthdayChange(date, dateString) {
        this.$set(this.user, 'birthday', dateString);
      },
      disabledDate(current) {
        return current >= moment().endOf('day');
      },
      handleUpdate() {
        this.$api.auth.updateUser(this.user.id, this.user).then(() => {
          this.$message.success(this.$messages.successResult.update);
        });
      }
    }
  }
</script>

<style scoped>
  .gender-select {
    width: 174px;
  }
</style>