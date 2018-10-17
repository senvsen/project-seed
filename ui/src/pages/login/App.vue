<template>
  <div id="app">
    <a-row type="flex" justify="center">
      <a-col>
        <a-card :bordered="false" style="margin-top: 100px; width: 380px;">
          <h3>登录 | {{$config.appName}}</h3>
          <a-form class="mt-1">
            <a-form-item label="用户名" required :validateStatus="validate.username.status" :help="validate.username.help">
              <a-input v-model="loginForm.username" placeholder="请输入用户名" @keyup.enter="login"></a-input>
            </a-form-item>
            <a-form-item label="密码" required :validateStatus="validate.password.status" :help="validate.password.help">
              <a-input type="password" v-model="loginForm.password" placeholder="请输入密码" @keyup.enter="login"></a-input>
            </a-form-item>
            <a-button type="primary" icon="login" @click="login">登录</a-button>
          </a-form>
        </a-card>
      </a-col>
    </a-row>
  </div>
</template>

<script>
  export default {
    name: 'App',
    data() {
      return {
        loginForm: {
          username: '',
          password: ''
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
        if (this.validateLoginForm()) {
          this.$api.web.login(this.loginForm).then(() => {
            window.location.replace("/");
          });
        }
      },
      validateLoginForm() {
        let valid = true;
        if (this.loginForm.username) {
          this.validate.username.status = '';
          this.validate.username.help = '';
        } else {
          this.validate.username.status = 'error';
          this.validate.username.help = '用户名不能为空';
          valid = false;
        }
        if (this.loginForm.password) {
          this.validate.password.status = '';
          this.validate.password.help = '';
        } else {
          this.validate.password.status = 'error';
          this.validate.password.help = '密码不能为空';
          valid = false;
        }
        return valid;
      }
    }
  }
</script>

<style>
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    height: 100%;
    background: url("http://area.sinaapp.com/bingImg");
  }

  #app .ant-card {
    background: rgba(255,255,255,.75);
  }
</style>
