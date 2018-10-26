<template>
  <div>
    <nav class="navbar" :class="$store.getters.navTheme" role="navigation" aria-label="main navigation">
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
        <!--更换主题-->
        <div class="navbar-end">
          <div class="navbar-item has-dropdown is-hoverable">
            <a class="navbar-link">
              <i class="mr-1" :class="$config.nav.theme.icon" v-if="$config.nav.theme.icon"></i>{{$config.nav.theme.label}}
            </a>
            <div class="navbar-dropdown is-right is-size-6">
              <a v-for="option in $config.nav.theme.options.nav" :key="option.label"
                 @click="changeTheme('navTheme', option.theme)" class="navbar-item">
                <span class="color-box" :class="option.theme"></span>
                <i class="mr-1" :class="option.icon" v-if="option.icon"></i>{{option.label}}
              </a>
              <a v-for="option in $config.nav.theme.options.sidebar" :key="option.label"
                 @click="changeTheme('sideTheme', option.theme)" class="navbar-item">
                <span class="color-box" :class="option.theme"></span>
                <i class="mr-1" :class="option.icon" v-if="option.icon"></i>{{option.label}}
              </a>
            </div>
          </div>

          <!--切换语言-->
          <div class="navbar-item has-dropdown is-hoverable">
            <a class="navbar-link">
              <i class="mr-1" :class="$config.nav.language.icon" v-if="$config.nav.language.icon"></i>{{$config.nav.language.label}}
            </a>
            <div class="navbar-dropdown is-right is-size-6">
              <a v-for="option in $config.nav.language.options" :key="option.label" @click="changeLocale(option.link)" class="navbar-item">
                <i class="mr-1" :class="option.icon" v-if="option.icon"></i>{{option.label}}
              </a>
            </div>
          </div>

          <!--消息中心-->
          <div class="navbar-item has-dropdown is-hoverable">
            <a class="navbar-link">
              <i class="mr-1" :class="$config.nav.notify.icon" v-if="$config.nav.notify.icon"></i>{{$config.nav.notify.label}}
              <span :class="$store.getters.theme === 'is-danger' ? 'has-text-warning' : 'has-text-danger'" >(99+)</span>
            </a>
            <div class="navbar-dropdown is-right is-size-6">
              <div class="notify-box">
                <div class="has-text-dark">你有<span class="has-text-danger">99+</span>条消息</div>
                <a class="navbar-divider"></a>
              </div>
            </div>
          </div>

          <!--用户下拉菜单-->
          <div class="navbar-item has-dropdown is-hoverable">
            <a class="navbar-link">
              <img :src="$config.nav.avatar" class="mr-1" height="24" width="24">
              <span>User</span>
            </a>
            <div class="navbar-dropdown is-right is-size-6">
              <div v-for="option in $config.nav.userMenu" :key="option.label">
                <a class="navbar-divider" v-if="option.isDivided"></a>
                <a class="navbar-item" :href="option.link" v-if="option.isDirect">
                  <i class="mr-1" :class="option.icon" v-if="option.icon"></i>{{option.label}}
                </a>
                <router-link class="navbar-item" :to="option.link" v-else>
                  <i class="mr-1" :class="option.icon" v-if="option.icon"></i>{{option.label}}
                </router-link>
              </div>
            </div>
          </div>
        </div>
      </div>
    </nav>

    <div class="columns is-marginless">
      <!--侧边栏菜单-->
      <div class="column is-paddingless is-2-desktop is-3-tablet is-mobile">
        <div class="menu sidebar" :class="$store.getters.sideTheme">
          <menus v-for="menu in $config.sidebar" :key="menu.label" :label="menu.label">
            <menu-item v-for="option in menu.options" :key="option.key" :icon="option.icon"
                       :is-active="$store.getters.menuKey === option.label" @click.native.prevent="menuSelect(option)">
              {{option.label}}
              <menus slot="sub" v-if="option.children">
                <menu-item v-for="item in option.children" :key="item.label" :icon="item.icon"
                           :is-active="$store.getters.key === item.label" @click.native.prevent="menuSelect(item)">
                  {{item.label}}
                </menu-item>
              </menus>
            </menu-item>
          </menus>
        </div>
      </div>

      <!--内容区-->
      <div class="column is-paddingless">
        <div class="page-content">
          <router-view/>
        </div>

        <!--页脚-->
        <div class="footer has-text-centered">
          {{$config.appName}} ©2018 Created by <a href="https://github.com/YupaiTS" target="_blank">YupaiTS</a>
        </div>
      </div>
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
        const navTheme = localStorage.getItem('navTheme');
        if (navTheme) {
          this.$store.dispatch('setNavTheme', navTheme);
        }
        const sideTheme = localStorage.getItem('sideTheme');
        if (sideTheme) {
          this.$store.dispatch('setSideTheme', sideTheme);
        }
      },
      initLocale() {
        const locale = localStorage.getItem('locale');
        if (locale) {
          this.$store.dispatch('setLocale', locale);
        }
      },
      changeTheme(type, theme) {
        localStorage.setItem(type, theme);
        if (type === 'navTheme') {
          this.$store.dispatch('setNavTheme', theme);
        } else if (type === 'sideTheme') {
          this.$store.dispatch('setSideTheme', theme);
        }
      },
      changeLocale(locale) {
        localStorage.setItem('locale', locale);
        this.$store.dispatch('setLocale', locale);
      },
      menuSelect(obj) {
        if (!obj.children && obj.link) {
          if (obj.isDirect) {
            this.$utils.http.openPage(obj.link);
          } else {
            this.$router.push(obj.link);
          }
        }
      }
    }
  }
</script>

<style>

</style>
