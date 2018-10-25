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
                     :class="{'is-danger': firstTime ? false: !usernameValid}" @keyup.enter="login">
              <i class="fa fa-user"></i>
              <span class="help is-danger" v-if="firstTime ? false: !usernameValid">用户名不能为空</span>
            </p>
            <label class="label">密码</label>
            <p class="control has-icon">
              <input class="input" type="password" name="password" v-model="loginForm.password" placeholder="请输入登录密码"
                     :class="{'is-danger': firstTime ? false: !passwordValid}" @keyup.enter="login">
              <i class="fa fa-lock"></i>
              <span class="help is-danger" v-if="firstTime ? false: !passwordValid">密码不能为空</span>
            </p>
            <p class="control">
              <label class="checkbox">
                <input type="checkbox" v-model="loginForm.rememberMe" class="mr-1">记住我
              </label>
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
        firstTime: true
      }
    },
    computed: {
      usernameValid() {
        if (this.firstTime) {
          this.firstTime = false;
        }
        return this.loginForm.username;
      },
      passwordValid() {
        if (this.firstTime) {
          this.firstTime = false;
        }
        return this.loginForm.password;
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
      loginValidate() {
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