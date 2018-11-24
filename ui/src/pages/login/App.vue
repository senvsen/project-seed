<template>
  <div class="login-page">
    <a-row type="flex" justify="center">
      <a-col>
        <a-card class="login-card">
          <div slot="title">
            <img src="/favicon.ico" alt="logo" height="32" width="32">
            <span class="ml-1">{{$messages.loginPage.title}}</span>
          </div>
          <a-form ref="loginForm">
            <a-form-item hasFeedback :validateStatus="validate.username.status" :help="validate.username.help">
              <a-input v-model="loginForm.username" :placeholder="$messages.loginPage.username.placeholder" @keyup.enter="login">
                <span slot="prefix">
                  <a-icon type="user"/>
                </span>
              </a-input>
            </a-form-item>
            <a-form-item hasFeedback :validateStatus="validate.password.status" :help="validate.password.help">
              <a-input type="password" v-model="loginForm.password" :placeholder="$messages.loginPage.password.placeholder" @keyup.enter="login">
                <span slot="prefix">
                  <a-icon type="lock"/>
                </span>
              </a-input>
            </a-form-item>
            <a-form-item class="is-marginless">
              <a-checkbox v-model="loginForm.rememberMe">{{$messages.loginPage.rememberMe}}</a-checkbox>
              <a class="login-forget" href="">{{$messages.loginPage.forget}}</a>
              <a-button type="primary" block @click="login">{{$messages.loginPage.login}}</a-button>
              <a href="">{{$messages.loginPage.register}}</a>
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
          this.validate.username = {status: 'error', help: this.$messages.loginPage.username.emptyTip};
          result = false;
        } else {
          this.validate.username = {status: 'success', help: ''};
        }
        if (!this.loginForm.password) {
          this.validate.password = {status: 'error', help: this.$messages.loginPage.password.emptyTip};
          result = false;
        } else if (this.loginForm.password.length < 6) {
          this.validate.password = {status: 'error', help: this.$messages.loginPage.password.lengthTip};
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
    width: 25rem;
  }
  .login-forget {
    float: right;
  }
  .other-tip {
    font-size: 14px;
  }
</style>