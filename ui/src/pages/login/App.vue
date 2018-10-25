<template>
  <div class="login-page">
    <div class="container">
      <div class="columns is-centered">
        <div class="column is-one-third">
          <div class="box login-box">
            <p class="title">登录</p>
            <label class="label">用户名</label>
            <p class="control has-icon">
              <input class="input" type="text" name="username" v-model="loginForm.username" placeholder="请输入登录用户名"
                     :class="{'is-danger': !usernameValid}" @blur="checkUsername" @keyup.enter="login">
              <i class="fa fa-user"></i>
              <span class="help is-danger" v-if="!usernameValid">用户名不能为空</span>
            </p>
            <label class="label">密码</label>
            <p class="control has-icon">
              <input class="input" type="password" name="password" v-model="loginForm.password" placeholder="请输入登录密码"
                     :class="{'is-danger': !passwordValid}" @blur="checkPassword" @keyup.enter="login">
              <i class="fa fa-lock"></i>
              <span class="help is-danger" v-if="!passwordValid">密码不能为空</span>
            </p>
            <p class="control">
              <checkbox v-model="loginForm.rememberMe" val="true">记住我</checkbox>
            </p>
            <p class="control">
              <button class="button is-primary is-fullwidth" @click="login">登录</button>
            </p>
          </div>
        </div>
      </div>
    </div>

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
        usernameValid: true,
        passwordValid: true,
      }
    },
    methods: {
      login() {
        if (this.loginValidate()) {
          this.$api.web.login(this.loginForm).then(() => {
            this.$utils.http.goPage('/index');
          });
        }
      },
      checkUsername() {
        this.usernameValid = this.loginForm.username;
      },
      checkPassword() {
        this.passwordValid = this.loginForm.password;
      },
      loginValidate() {
        this.checkUsername();
        this.checkPassword();
        return this.usernameValid && this.passwordValid;
      }
    }
  }
</script>

<style scoped>
  .login-page {
    min-height: 100vh;
    background-image: url("../../assets/loginbg.jpg");
  }

  .box.login-box {
    background: rgba(255, 255, 255, .75);
    margin-top: 5rem;
  }
</style>