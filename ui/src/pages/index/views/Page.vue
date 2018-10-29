<template>
  <div>
    <div v-if="$store.getters.pageType === 'iframe'">
      <i-frame-page :src="$store.getters.key"></i-frame-page>
    </div>
    <div v-else-if="$store.getters.pageType === 'manage'">
      <manage-page>
        <template slot="advanced-search">
          <component :is="searchComponent"></component>
        </template>
        <template slot="ext-opt" slot-scope="{record}">
          Hello, {{record.id}}!
        </template>
      </manage-page>
    </div>
    <div v-else>
      <router-view/>
    </div>
  </div>
</template>

<script>
  import UserSearch from '../../../components/search/UserSearch'
  import IFramePage from "../../../components/IFramePage";
  import ManagePage from "../../../components/ManagePage";
  export default {
    name: "Page",
    components: {ManagePage, IFramePage},
    data() {
      return {
        searchComponent: UserSearch
      }
    }
  }
</script>

<style scoped>

</style>