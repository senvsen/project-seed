<template>
  <div>
    <div v-if="$store.getters.pageType === 'iframe'">
      <i-frame-page :src="$route.path"/>
    </div>
    <div v-else-if="$store.getters.pageType === 'manage'">
      <manage-page/>
    </div>
    <div v-else>
      <router-view/>
    </div>
  </div>
</template>

<script>
  import IFramePage from "../components/IFramePage";
  import ManagePage from "../components/ManagePage";
  export default {
    name: "Page",
    components: {ManagePage, IFramePage}
  }
</script>

<style scoped>

</style>