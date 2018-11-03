<template>
  <div>
    <div v-if="$store.getters.pageType === 'iframe'">
      <i-frame-page :src="$store.getters.key"></i-frame-page>
    </div>
    <div v-else-if="$store.getters.pageType === 'manage'">
      <manage-page ref="page">
        <component slot="toolbar" slot-scope="{selectedKeys}" :keys="selectedKeys" :is="toolbarComponent" @updated="handleUpdated"></component>
        <component slot="form" :is="formComponent"></component>
        <component slot="ext-opt" slot-scope="{record}" :row-data="record" :is="optComponent"></component>
        <component slot="search" :is="searchComponent" @updated="handleUpdated"></component>
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
        toolbarComponent: undefined,
        formComponent: undefined,
        optComponent: undefined,
        searchComponent: undefined,
      }
    },
    mounted() {
      this.setComponents();
    },
    computed: {
      pageKey() {
        return this.$store.getters.key;
      }
    },
    watch: {
      pageKey() {
        this.setComponents();
      }
    },
    methods: {
      setComponents() {
        const components = this.$page[this.pageKey];
        if (components) {
          this.toolbarComponent = components.toolbarComponent;
          this.formComponent = components.formComponent;
          this.optComponent = components.optComponent;
          this.searchComponent = components.searchComponent;
        }
      },
      handleUpdated() {
        this.$refs.page.fetchData();
      }
    }
  }
</script>

<style scoped>

</style>