<template>
  <div>
    <div v-if="$store.getters.pageType === 'iframe'">
      <i-frame-page :src="$store.getters.key"></i-frame-page>
    </div>
    <div v-else-if="$store.getters.pageType === 'manage'">
      <manage-page>
        <component slot="form" :is="formComponent"></component>
        <component slot="ext-opt" slot-scope="{record}" :is="optComponent"></component>
        <component slot="advanced-search" :is="searchComponent"></component>
      </manage-page>
    </div>
    <div v-else>
      <router-view/>
    </div>
  </div>
</template>

<script>
  import IFramePage from "../../../components/IFramePage";
  import ManagePage from "../../../components/ManagePage";
  export default {
    name: "Page",
    components: {ManagePage, IFramePage},
    data() {
      return {
        formComponent: undefined,
        optComponent: undefined,
        searchComponent: undefined
      }
    },
    created() {
      this.setComponents();
    },
    computed: {
      pageKey() {
        return this.$store.getters.key;
      }
    },
    watch: {
      pageKey: function () {
        this.setComponents();
      }
    },
    methods: {
      setComponents() {
        this.formComponent = this.$page[this.pageKey].formComponent;
        this.optComponent = this.$page[this.pageKey].optComponent;
        this.searchComponent = this.$page[this.pageKey].searchComponent;
      }
    }
  }
</script>

<style scoped>

</style>