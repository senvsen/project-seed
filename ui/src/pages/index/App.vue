<template>
  <a-locale-provider :locale="locale">
    <a-layout style="min-height: 100vh" v-if="true">
      <a-layout-sider :trigger="null" collapsible v-model="collapsed" width="256">
        <div class="logo mb-2">
          <img src="../../assets/logo.png" class="logo-img"/>
          <span class="logo-title">{{$messages.appName}}</span>
        </div>
        <sidebar/>
      </a-layout-sider>
      <a-layout>
        <a-layout-header class="header" style="background: #fff;">
          <a-button size="small" @click="collapsed = !collapsed">
            <a-icon :type="collapsed ? 'menu-unfold' : 'menu-fold'"/>
          </a-button>
          <div class="is-pulled-right">
            <a-dropdown placement="bottomRight" class="mr-1">
              <a-button icon="desktop">
                <span>{{$messages.sidebars[$store.getters.sidebarKey].label}}</span>
                <a-icon type="down"/>
              </a-button>
              <a-menu slot="overlay" class="header-box">
                <a-menu-item v-for="(sidebar, key) in $messages.sidebars" :key="key"
                             @click.native="handleSidebarSelect(key)">
                  <a-icon type="desktop"/>{{sidebar.label}}
                </a-menu-item>
              </a-menu>
            </a-dropdown>
            <a-dropdown placement="bottomRight" class="mr-1">
              <a-button :icon="$messages.nav.notify.icon">
                <a-badge class="message-dot" dot>{{$messages.nav.notify.label}}</a-badge>
                <a-icon type="down"/>
              </a-button>
              <div slot="overlay">
                <a-card class="message-box">
                  {{$messages.nav.notify.tip.prefix}}99+{{$messages.nav.notify.tip.suffix}}
                </a-card>
              </div>
            </a-dropdown>
            <a-dropdown placement="bottomRight">
              <a-button>
                <a-avatar src="https://avatars3.githubusercontent.com/u/12194490?s=460&v=4" size="small" class="mr-1"/>
                <span>yupaits</span>
                <a-icon type="down"/>
              </a-button>
              <a-menu slot="overlay" class="header-box">
                <template v-for="option in $messages.nav.userMenu">
                  <a-menu-divider v-if="option.isDivided"/>
                  <a-menu-item :key="option.label"
                               @click.native="handleUserMenuSelect(option)">
                    <a-icon :type="option.icon"/>{{option.label}}
                  </a-menu-item>
                </template>
              </a-menu>
            </a-dropdown>
          </div>
        </a-layout-header>
        <a-layout-content>
          <breadcrumb/>
          <router-view/>
        </a-layout-content>
        <a-layout-footer style="text-align: center">
          {{$messages.appName}} ©2018 Created by <a href="https://github.com/YupaiTS" target="_blank">YupaiTS</a>
        </a-layout-footer>
      </a-layout>
    </a-layout>
    <a-layout v-else>

    </a-layout>
  </a-locale-provider>
</template>

<script>
  import Sidebar from "../../components/Sidebar"
  import ManagePage from "../../components/ManagePage"
  import zhCN from 'ant-design-vue/lib/locale-provider/zh_CN'
  import Breadcrumb from "../../components/Breadcrumb";

  export default {
    name: 'App',
    components: {Breadcrumb, Sidebar, ManagePage},
    data() {
      return {
        collapsed: false,
        locale: zhCN,
      }
    },
    created() {
      this.fetchSystemNotices();
    },
    methods: {
      fetchSystemNotices() {
        this.$api.msg.getSystemNoticeList({type: 'active'}).then(res => {
          const notices = res.data;
          if (notices.length > 0) {
            const len = notices.length;
            let index = 0;
            const flag = setInterval(() => {
              if (index > len - 1) {
                clearInterval(flag);
              }
              this.notify(notices[index]);
              index++;
            }, 0);
          }
        });
      },
      notify(notice) {
        const type = notice.msgLevel === 1 ? 'info' : (notice.msgLevel === 2 ? 'warning' : 'error');
        this.$notification[type]({
          key: notice.id,
          message: this.$messages.enums.msgLevel[notice.msgLevel],
          description: notice.msgContent
        });
      },
      handleSidebarSelect(key) {
        this.$store.dispatch('setSidebarKey', key);
      },
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
  .logo {
    height: 64px;
    position: relative;
    line-height: 64px;
    padding-left: 24px;
    transition: all .3s;
    overflow: hidden;
    color: #fff;
    font-size: 24px;
    background: #002140;
  }
  .logo-img {
    display: inline-block;
    vertical-align: middle;
    height: 32px;
  }
  .logo-title {
    display: inline-block;
    vertical-align: middle;
    font-size: 24px;
    margin: 0 0 0 12px;
  }
  .header {
    padding: 0 16px!important;
  }
  .header-box {
    min-width: 160px;
  }
  .message-dot {
    vertical-align: initial!important;
  }
  .message-box > .ant-card-body {
    padding: 16px!important;
  }
</style>
