<template>
  <div class="manage-page">
    <breadcrumb/>
    <div class="manage-content">
      <a-table :columns="[]" :dataSource="data" @change="handleChange"></a-table>
    </div>
  </div>
</template>

<script>
  import Breadcrumb from "./Breadcrumb";
  export default {
    name: "ManagePage",
    components: {Breadcrumb},
    data() {
      return {
        data: [
          {id: '12345678987654321', createdAt: this.$utils.date().subtract(1, 'hour'), updatedAt: this.$utils.date()},
          {id: '12345678987654322', createdAt: new Date()},
          {id: '12345678987654323', createdAt: new Date()},
          {id: '12345678987654324', createdAt: new Date()},
        ],
        pager: {
          total: 80
        },
        selectedKeys: [],
      }
    },
    created() {
      console.log(this.$store.getters);
    },
    methods: {
      fetchData() {
        console.log('fetch data');
      },
      add() {
        alert('创建');
      },
      handleBatchDelete() {
        this.$refs.batchDeletePopover.$refs.popper.hidden = false;
        alert('批量删除');
      },
      handleSearch() {
        this.fetchData();
      },
      handleChange() {
        
      }
    }
  }
</script>

<style scoped>
  .manage-page {
    padding: 16px 16px 0;
  }
  .manage-content {
    padding: 24px;
    background: #fff;
    min-height: calc(100vh - 186px);
  }
</style>