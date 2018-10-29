<template>
  <div class="login-page">
    <a-row type="flex" justify="center">
      <a-col :lg="5" :md="9" :sm="15" :xs="18">
        <a-card class="login-card">
          <div slot="title">
            <img src="/favicon.ico" alt="logo" height="32" width="32"> {{$t('loginPage.title')}}
          </div>
          <a-form ref="loginForm">
            <a-form-item hasFeedback :validateStatus="validate.username.status" :help="validate.username.help">
              <a-input v-model="loginForm.username" :placeholder="$t('loginPage.username.placeholder')" @keyup.enter="login">
                <span slot="prefix">
                  <a-icon type="user"/>
                </span>
              </a-input>
            </a-form-item>
            <a-form-item hasFeedback :validateStatus="validate.password.status" :help="validate.password.help">
              <a-input type="password" v-model="loginForm.password" :placeholder="$t('loginPage.password.placeholder')" @keyup.enter="login">
                <span slot="prefix">
                  <a-icon type="lock"/>
                </span>
              </a-input>
            </a-form-item>
            <a-form-item class="is-marginless">
              <a-checkbox v-model="loginForm.rememberMe">{{$t('loginPage.rememberMe')}}</a-checkbox>
              <a class="login-forget" href="">{{$t('loginPage.forget')}}</a>
              <a-button type="primary" block @click="login">{{$t('loginPage.login')}}</a-button>
              <a href="">{{$t('loginPage.register')}}</a>
            </a-form-item>
          </a-form>
        </a-card>
      </a-col>
    </a-row>
  </div>
</template>

<script>
  export default {
    name: "App",
    data() {
      return {
        loginForm: {
          username: '',
          password: '',
          rememberMe: false
        },
        validate: {
          username: {
            status: '',
            help: ''
          },
          password: {
            status: '',
            help: ''
          }
        }
      }
    },
    methods: {
      login() {
        if (this.validateForm()) {
          this.$api.web.login(this.loginForm).then(() => {
            this.$utils.http.goPage('/index');
          });
        }
      },
      validateForm() {
        let result = true;
        if (!this.loginForm.username) {
          this.validate.username = {status: 'error', help: this.$t('loginPage.username.emptyTip')};
          result = false;
        } else {
          this.validate.username = {status: 'success', help: ''};
        }
        if (!this.loginForm.password) {
          this.validate.password = {status: 'error', help: this.$t('loginPage.password.emptyTip')};
          result = false;
        } else if (this.loginForm.password.length < 6) {
          this.validate.password = {status: 'error', help: this.$t('loginPage.password.lengthTip')};
          result = false;
        } else {
          this.validate.password = {status: 'success', help: ''};
        }
        return result;
      }
    }
  }
</script>

<style scoped>
  .login-page {
    min-height: 100vh;
    /*background-image: url("../../assets/loginbg.jpg");*/
  }
  .login-card {
    background: rgba(255, 255, 255, .90);
    margin-top: 5rem;
  }
  .login-forget {
    float: right;
  }
</style>