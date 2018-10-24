<template>
  <div id="app">
    <nav class="navbar border-bottom" :class="$store.getters.theme" role="navigation" aria-label="main navigation">
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
          <div class="navbar-item has-dropdown is-hoverable">
            <a class="navbar-link is-size-7">
              <i class="mr-1" :class="$config.nav.theme.icon" v-if="$config.nav.theme.icon"></i>{{$config.nav.theme.label}}
            </a>
            <div class="navbar-dropdown is-right">
              <a v-for="option in $config.nav.theme.options" :key="option.label" @click="changeTheme(option.theme)" class="navbar-item is-size-7">
                <span class="color-box" :class="option.theme"></span>
                <i class="mr-1" :class="option.icon" v-if="option.icon"></i>{{option.label}}
              </a>
            </div>
          </div>

          <div class="navbar-item has-dropdown is-hoverable">
            <a class="navbar-link is-size-7">
              <i class="mr-1" :class="$config.nav.language.icon" v-if="$config.nav.language.icon"></i>{{$config.nav.language.label}}
            </a>
            <div class="navbar-dropdown is-right">
              <a v-for="option in $config.nav.language.options" :key="option.label" @click="changeLocale(option.link)" class="navbar-item is-size-7">
                <i class="mr-1" :class="option.icon" v-if="option.icon"></i>{{option.label}}
              </a>
            </div>
          </div>

          <div class="navbar-item has-dropdown is-hoverable">
            <a class="navbar-link is-size-7">
              <i class="mr-1" :class="$config.nav.notify.icon" v-if="$config.nav.notify.icon"></i>{{$config.nav.notify.label}}
              <span :class="$store.getters.theme === 'is-danger' ? 'has-text-warning' : 'has-text-danger'" >(99+)</span>
            </a>
            <div class="navbar-dropdown is-right">
              <div class="notify-box">
                <div class="has-text-dark">你有<span class="has-text-danger">99+</span>条消息</div>
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
                <a class="navbar-item is-size-7" :href="option.link" v-if="option.isDirect">
                  <i class="mr-1" :class="option.icon" v-if="option.icon"></i>{{option.label}}
                </a>
                <a class="navbar-item is-size-7" @click="goPage(option.link)" v-else>
                  <i class="mr-1" :class="option.icon" v-if="option.icon"></i>{{option.label}}
                </a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </nav>

    <div class="columns is-marginless">
      <div class="column sidebar is-2-desktop is-3-touch">
        <aside class="menu">
          <div v-for="menu in $config.sidebar" :key="menu.label">
            <p class="menu-label">{{menu.label}}</p>
            <ul class="menu-list" v-if="menu.options">
              <li v-for="option in menu.options" :key="option.label">
                <a class="is-size-7" :href="option.link" v-if="option.isDirect">
                  <i :class="option.icon" class="fa-sm mr-1" v-if="option.icon"></i>{{option.label}}
                </a>
                <a class="is-size-7" :class="{'is-active': $store.getters.currentPage === option.link}" @click="goPage(option.link)" v-else>
                  <i :class="option.icon" class="fa-sm mr-1" v-if="option.icon"></i>{{option.label}}
                </a>
                <ul v-if="option.children">
                  <li v-for="item in option.children" :key="item.label">
                    <a class="is-size-7" :href="item.link" v-if="item.isDirect">
                      <i :class="item.icon" class="fa-sm mr-1" v-if="item.icon"></i>{{item.label}}
                    </a>
                    <a class="is-size-7" :class="{'is-active': $store.getters.currentPage === item.link}" @click="goPage(item.link)" v-else>
                      <i :class="item.icon" class="fa-sm mr-1" v-if="item.icon"></i>{{item.label}}
                    </a>
                  </li>
                </ul>
              </li>
            </ul>
          </div>
        </aside>
      </div>
      <div class="column is-marginless" :class="{'is-paddingless': $store.getters.pageType === 'iframe'}">
        <router-view/>
      </div>
    </div>
    <div class="footer has-text-centered is-size-7">
      {{$config.appName}} ©2018 Created by <a href="https://github.com/YupaiTS" target="_blank">YupaiTS</a>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'App',
    created() {
      this.init();
    },
    methods: {
      init() {
        this.initToggle();
        this.initTheme();
        this.initLocale();
        this.$api.auth.getUserPage();
      },
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
      initTheme() {
        const theme = localStorage.getItem('theme');
        if (theme) {
          this.$store.dispatch('setTheme', theme);
        }
      },
      initLocale() {
        const locale = localStorage.getItem('locale');
        if (locale) {
          this.$store.dispatch('setLocale', locale);
        }
      },
      changeTheme(theme) {
        localStorage.setItem('theme', theme);
        this.$store.dispatch('setTheme', theme);
      },
      changeLocale(locale) {
        localStorage.setItem('locale', locale);
        this.$store.dispatch('setLocale', locale);
      },
      goPage(link) {
        this.$router.push(link);
      }
    }
  }
</script>

<style>
</style>
