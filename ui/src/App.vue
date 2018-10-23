<template>
  <div id="app">
    <nav class="navbar is-light" role="navigation" aria-label="main navigation">
      <div class="navbar-brand">
        <a class="navbar-item" href="/index">
          <img :src="$config.nav.logo" width="112" height="28">
        </a>
        <a role="button" class="navbar-burger" aria-label="menu" aria-expanded="false" data-target="app-navbar">
          <span aria-hidden="true"></span>
          <span aria-hidden="true"></span>
          <span aria-hidden="true"></span>
        </a>
      </div>

      <div id="app-navbar" class="navbar-menu">
        <div class="navbar-start">
          <a class="navbar-item">
            Home
          </a>
          <a class="navbar-item">
            Documentation
          </a>
          <div class="navbar-item has-dropdown is-hoverable">
            <a class="navbar-link">
              More
            </a>
            <div class="navbar-dropdown">
              <a class="navbar-item">
                About
              </a>
              <a class="navbar-item">
                Jobs
              </a>
              <a class="navbar-item">
                Contact
              </a>
              <hr class="navbar-divider">
              <a class="navbar-item">
                Report an issue
              </a>
            </div>
          </div>
        </div>
        <div class="navbar-end">
          <div class="navbar-item has-dropdown is-hoverable">
            <a class="navbar-link">
              <img :src="$config.nav.avatar" class="mr-1" height="28" width="28">
              <span>User</span>
            </a>
            <div class="navbar-dropdown is-right">
              <a class="navbar-item">
                <i class="fas fa-user mr-1"></i>个人中心
              </a>
              <a class="navbar-item">
                <i class="fas fa-lock mr-1"></i>修改密码
              </a>
              <hr class="navbar-divider">
              <a class="navbar-item" href="/logout">
                <i class="fas fa-sign-out-alt mr-1"></i>退出登录
              </a>
              <hr class="navbar-divider">
              <a class="navbar-item">
                <i class="fas fa-bug mr-1"></i>报告错误
              </a>
            </div>
          </div>
        </div>
      </div>
    </nav>

    <div class="layout">
      <div class="columns">
        <div class="column is-2">
          <aside class="menu">
            <div v-for="menu in $config.sidebar">
              <p class="menu-label">{{menu.label}}</p>
              <ul class="menu-list" v-if="menu.options">
                <li v-for="option in menu.options">
                  <a>
                    <i :class="option.icon" class="fa-sm mr-1" v-if="option.icon"></i>{{option.label}}
                  </a>
                  <ul v-if="option.children">
                    <li v-for="item in option.children">
                      <a>
                        <i :class="item.icon" class="fa-sm mr-1" v-if="item.icon"></i>{{item.label}}
                      </a>
                    </li>
                  </ul>
                </li>
              </ul>
            </div>
          </aside>
        </div>
        <div class="column">
          <div class="box"></div>
        </div>
      </div>
    </div>

    <!--<span class="icon is-large">
      <i class="fas fa-home fa-lg"></i>
    </span>
    <button class="button" @click="testToast">Toast</button>-->
  </div>
</template>

<script>

  export default {
    name: 'App',
    created() {
      this.initToggle();
      this.$api.auth.getUserPage();
    },
    methods: {
      initToggle() {
        document.addEventListener('DOMContentLoaded', () => {
          const $navbarBurgers = Array.prototype.slice.call(document.querySelectorAll('.navbar-burger'), 0);
          if ($navbarBurgers.length > 0) {
            $navbarBurgers.forEach( el => {
              el.addEventListener('click', () => {
                const target = el.dataset.target;
                const $target = document.getElementById(target);
                el.classList.toggle('is-active');
                $target.classList.toggle('is-active');
              });
            });
          }
        });
      },
      testToast() {
        this.$toasted.show('测试Toast！！！', {icon: 'comment-dots'});
        this.$toasted.success('测试Toast！！！', {icon: 'check-circle'});
        this.$toasted.error('测试Toast！！！', {icon: 'times-circle'});
        this.$toasted.info('测试Toast！！！', {icon: 'info-circle'});
      }
    }
  }
</script>

<style>

</style>
