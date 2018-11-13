<template>
  <a-menu theme="dark" mode="inline"
          :defaultOpenKeys="[$store.getters.menuKey]"
          :selectedKeys="[$store.getters.key]"
          @select="item => $router.push(item.key)">
    <template v-for="menu in $messages.sidebars[$store.getters.sidebarKey].options">
      <a-sub-menu v-if="menu.children" :key="menu.key">
        <span slot="title">
          <a-icon :type="menu.icon"/>
          <span>{{menu.label}}</span>
        </span>
        <a-menu-item v-for="item in menu.children" :key="item.link">
          <a-icon :type="item.icon"/>
          <span>{{item.label}}</span>
        </a-menu-item>
      </a-sub-menu>
      <a-menu-item v-else :key="menu.link">
          <a-icon :type="menu.icon"/>
          <span>{{menu.label}}</span>
      </a-menu-item>
    </template>
  </a-menu>
</template>

<script>
  export default {
    name: "Sidebar"
  }
</script>

<style scoped>

</style>