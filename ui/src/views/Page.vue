<template>
  <div>
    <div v-if="$store.getters.pageType === 'iframe'">
      <i-frame-page :src="$route.path"/>
    </div>
    <div v-else-if="$store.getters.pageType === 'manage'">
      Manage
    </div>
    <div v-else>
      Custom
    </div>
  </div>
</template>

<script>
  import IFramePage from "../components/IFramePage";
  export default {
    name: "Page",
    components: {IFramePage},
  }
</script>

<style scoped>

</style>