<template>
  <div>
    <a-row :gutter="16">
      <a-col :span="6">
        <div class="page-content">
          <div class="has-text-centered">
            <a-avatar src="https://avatars3.githubusercontent.com/u/12194490?s=460&v=4" :size="96"></a-avatar>
            <h3>CodeAnimal No.1</h3>
          </div>
        </div>
      </a-col>
      <a-col :span="18">
        <div class="page-content"></div>
      </a-col>
    </a-row>
  </div>
</template>

<script>
  import Breadcrumb from "../../../../components/Breadcrumb";
  export default {
    name: "Profile",
    components: {Breadcrumb}
  }
</script>

<style scoped>

</style>