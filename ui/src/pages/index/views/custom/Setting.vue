<template>
  <div>
    <a-row :gutter="16">
      <a-col :span="4">
        <a-menu theme="light" mode="inline"
                :defaultSelectedKeys="['basicSetting']" @click="handleSelect">
          <a-menu-item v-for="(item, key) in settingItems" :key="key">{{item.label}}</a-menu-item>
        </a-menu>
      </a-col>
      <a-col :span="20">
        <a-card class="setting-content">
          <component :is="selectedComponent" class="setting-content"></component>
        </a-card>
      </a-col>
    </a-row>
  </div>
</template>

<script>
  import Breadcrumb from "../../../../components/Breadcrumb";
  import BasicSetting from "../setting/BasicSetting";
  import ModifyPassword from "../setting/ModifyPassword";
  import AccountBinding from "../setting/AccountBinding";
  import NotifySetting from "../setting/NotifySetting";

  export default {
    name: "Setting",
    components: {Breadcrumb},
    data() {
      return {
        settingItems: {
          basicSetting: {label: '基本设置', component: BasicSetting},
          modifyPassword: {label: '修改密码', component: ModifyPassword},
          accountBinding: {label: '账号绑定', component: AccountBinding},
          notifySetting: {label: '新消息通知', component: NotifySetting},
        },
        selectedComponent: undefined,
      }
    },
    created() {
      this.selectedComponent = this.settingItems.basicSetting.component;
    },
    methods: {
      handleSelect({key}) {
        this.selectedComponent = this.settingItems[key].component;
      }
    }
  }
</script>

<style scoped>
  .setting-content {
    background: #fff;
    min-height: calc(100vh - 260px);
  }
</style>