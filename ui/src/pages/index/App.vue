<template>
  <a-locale-provider :locale="locale">
    <a-layout style="min-height: 100vh">
      <a-layout-sider collapsible v-model="collapsed">
        <div class="logo"/>
        <sidebar/>
      </a-layout-sider>
      <a-layout>
        <a-layout-header class="header" style="background: #fff;">
          <div class="is-pulled-right">
            <a-dropdown placement="bottomRight" class="mr-1">
              <a-button :icon="$messages.nav.notify.icon">
                <a-badge class="message-dot" dot>{{$messages.nav.notify.label}}</a-badge>
                <a-icon type="down"/>
              </a-button>
              <div slot="overlay">
                <a-card>
                  {{$messages.nav.notify.tip.prefix}}99+{{$messages.nav.notify.tip.suffix}}
                </a-card>
              </div>
            </a-dropdown>
            <a-dropdown placement="bottomRight">
              <a-button icon="user">
                yupaits <a-icon type="down"/>
              </a-button>
                <a-menu slot="overlay">
                  <a-menu-item>
                    <div class="has-text-centered">
                      <img src="https://avatars3.githubusercontent.com/u/12194490?s=460&v=4" alt="avatar" width="48px">
                    </div>
                  </a-menu-item>
                  <a-menu-item v-for="option in $messages.nav.userMenu" :key="option.label"
                               @click.native="handleUserMenuSelect(option)">
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
          {{$messages.appName}} ©2018 Created by <a href="https://github.com/YupaiTS" target="_blank">YupaiTS</a>
        </a-layout-footer>
      </a-layout>
    </a-layout>
  </a-locale-provider>
</template>

<script>
  import Sidebar from "../../components/Sidebar"
  import ManagePage from "../../components/ManagePage"
  import zhCN from 'ant-design-vue/lib/locale-provider/zh_CN'

  export default {
    name: 'App',
    components: {Sidebar, ManagePage},
    data() {
      return {
        collapsed: false,
        locale: zhCN
      }
    },
    methods: {
      handleUserMenuSelect(option) {
        if (option.isDirect) {
          this.$utils.http.goPage(option.link);
        } else {
          this.$router.push(option.link);
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
  .message-dot {
    vertical-align: initial!important;
  }
</style>
