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
        <div class="navbar-end">
          <div v-for="item in $config.nav.menu" :key="item.label" class="navbar-item has-dropdown is-hoverable">
            <a class="navbar-link is-size-7">
              <i class="mr-1" :class="item.icon" v-if="item.icon"></i>{{item.label}}
            </a>
            <div class="navbar-dropdown is-right">
              <a v-for="option in item.options" :key="option.label" :href="option.link" class="navbar-item is-size-7">
                <i class="mr-1" :class="option.icon" v-if="option.icon"></i>{{option.label}}
              </a>
            </div>
          </div>

          <div class="navbar-item has-dropdown is-hoverable">
            <a class="navbar-link is-size-7">
              <i class="mr-1" :class="$config.nav.notify.icon" v-if="$config.nav.notify.icon"></i>{{$config.nav.notify.label}}
            </a>
            <div class="navbar-dropdown is-right">
              <div class="notify-box">
                <p>你有0条消息</p>
                <a class="navbar-divider"></a>
              </div>
            </div>
          </div>

          <div class="navbar-item has-dropdown is-hoverable">
            <a class="navbar-link is-size-7">
              <img :src="$config.nav.avatar" class="mr-1" height="28" width="28">
              <span>User</span>
            </a>
            <div class="navbar-dropdown is-right">
              <div v-for="option in $config.nav.userMenu" :key="option.label">
                <a class="navbar-divider" v-if="option.isDivided"></a>
                <a class="navbar-item is-size-7" :href="option.link">
                  <i class="mr-1" :class="option.icon" v-if="option.icon"></i>{{option.label}}
                </a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </nav>

    <div class="layout">
      <div class="columns">
        <div class="column is-2">
          <aside class="menu">
            <div v-for="menu in $config.sidebar" :key="menu.label">
              <p class="menu-label">{{menu.label}}</p>
              <ul class="menu-list" v-if="menu.options">
                <li v-for="option in menu.options" :key="option.label">
                  <a class="is-size-7" @click="goPage(option.link)">
                    <i :class="option.icon" class="fa-sm mr-1" v-if="option.icon"></i>{{option.label}}
                  </a>
                  <ul v-if="option.children">
                    <li v-for="item in option.children" :key="item.label">
                      <a class="is-size-7" @click="goPage(item.link)">
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
      goPage(link) {
        this.$router.push(link);
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
.notify-box {
  min-width: 20rem;
  padding: 8px;
}
</style>
