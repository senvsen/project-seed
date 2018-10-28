<template>
  <div>
    <a-layout style="min-height: 100vh">
      <a-layout-sider collapsible v-model="collapsed">
        <div class="logo"/>
        <sidebar/>
      </a-layout-sider>
      <a-layout>
        <a-layout-header class="header" :style="{background: '#fff'}">
          <div class="is-pulled-right">
            <!--<a-dropdown class="mr-1">
              <a-button :icon="$config.nav.theme.icon">
                {{$config.nav.theme.label}} <a-icon type="down"/>
              </a-button>
              <a-menu slot="overlay">
                <a-menu-item v-for="option in $config.nav.theme.options.nav" :key="option.theme"
                             @click.native="changeTheme('navTheme', option.theme)">
                  {{option.label}}
                </a-menu-item>
                <a-menu-item v-for="option in $config.nav.theme.options.sidebar" :key="option.theme"
                             @click.native="changeTheme('sideTheme', option.theme)">
                  {{option.label}}
                </a-menu-item>
              </a-menu>
            </a-dropdown>-->
            <a-dropdown class="mr-1">
              <a-button :icon="$config.nav.language.icon">
                {{$config.nav.language.label}} <a-icon type="down"/>
              </a-button>
              <a-menu slot="overlay">
                <a-menu-item v-for="lang in $config.nav.language.options" :key="lang.label">{{lang.label}}</a-menu-item>
              </a-menu>
            </a-dropdown>
            <a-dropdown placement="bottomRight" class="mr-1">
              <a-button icon="message">
                <a-badge class="message-dot" dot>消息</a-badge>
                <a-icon type="down"/>
              </a-button>
              <div slot="overlay">
                <a-card>
                  你有99+条消息
                </a-card>
              </div>
            </a-dropdown>
            <a-dropdown placement="bottomRight">
              <a-button>
                yupaits <a-icon type="down"/>
              </a-button>
                <a-menu slot="overlay">
                  <a-menu-item>
                    <div class="has-text-centered">
                      <img src="https://avatars3.githubusercontent.com/u/12194490?s=460&v=4" alt="avatar" width="48px">
                    </div>
                  </a-menu-item>
                  <a-menu-item v-for="option in $config.nav.userMenu" :key="option.label">
                    <a-icon :type="option.icon"/>{{option.label}}
                  </a-menu-item>
                </a-menu>
            </a-dropdown>
          </div>
        </a-layout-header>
        <a-layout-content>
          <router-view/>
        </a-layout-content>
        <a-layout-footer style="text-align: center">
          {{$config.appName}} ©2018 Created by <a href="https://github.com/YupaiTS" target="_blank">YupaiTS</a>
        </a-layout-footer>
      </a-layout>
    </a-layout>
    <!--<nav class="navbar" :class="$store.getters.navTheme" role="navigation" aria-label="main navigation">
          &lt;!&ndash;消息中心&ndash;&gt;
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

          &lt;!&ndash;用户下拉菜单&ndash;&gt;
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
    </div>-->
  </div>
</template>

<script>
  import Sidebar from "../../components/Sidebar";
  import ManagePage from "../../components/ManagePage";

  export default {
    name: 'App',
    components: {ManagePage, Sidebar},
    data() {
      return {
        collapsed: false,
      }
    },
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
            $navbarBurgers.forEach(el => {
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
  .header {
    padding: 0 16px!important;
  }
  .logo {
    height: 32px;
    background: rgba(255, 255, 255, .2);
    margin: 16px;
  }

  .user-menu {
    cursor: pointer;
  }
</style>
