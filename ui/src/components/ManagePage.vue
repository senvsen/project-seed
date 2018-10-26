<template>
  <div class="manage-page">
    <page-breadcrumb class="mb-3"/>
    <data-table :data="data" :change="onTableChange" :on-select-change="onSelectChange" :pagination="pager"
                row-key="id" checkable show-index bordered striped>
      <table-toolbar has-refresh has-columns-control>
        <template slot="left">
          <div class="level-item">
            <button class="button" @click="add">
              <i class="fa fa-plus mr-1"></i>创建
            </button>
          </div>
          <div class="level-item">
            <popover title="确认删除" placement="rightTop" :disabled="selectedKeys.length <= 0" ref="batchDeletePopover">
              <button class="button" :class="{'is-disabled': selectedKeys.length <= 0}">
                <i class="fa fa-trash mr-1"></i>批量删除
              </button>
              <div slot="content">
                <p>确认批量删除选中的记录吗？</p>
                <button class="button is-primary is-pulled-right is-small mt-1 mb-1" @click="handleBatchDelete">
                  <i class="fa fa-check"></i>确认
                </button>
              </div>
            </popover>
          </div>
          <slot name="ext-toolbar"></slot>
        </template>
        <template slot="right">
          <div class="control is-horizontal is-paddingless is-marginless">
            <p class="control has-addons has-addons-right mr-1">
              <input type="text" class="input mr-1" placeholder="请填写关键字">
              <span>
                <button class="button">
                  <i class="fa fa-search mr-1" @click="handleSearch"></i>查找
                </button>
              </span>
            </p>
            <button class="button">
              <i class="fa fa-filter mr-1"></i>高级查找
            </button>
          </div>
        </template>
      </table-toolbar>
      <column label="ID" field="id"></column>
      <column label="创建时间">
        <template scope="row">
          {{$utils.date(row.createdAt).format('YYYY-MM-DD HH:mm:ss')}}
        </template>
      </column>
      <column label="更新时间">
        <template scope="row">
          {{$utils.date(row.updatedAt).format('YYYY-MM-DD HH:mm:ss')}}
        </template>
      </column>
      <column label="操作">
        <template scope="row">
          <button class="button is-small mr-1">编辑</button>
          <popover title="确认删除">
            <button class="button is-small">删除</button>
            <div slot="content">
              <p>确认批量删除选中的记录吗？</p>
              <button class="button is-primary is-pulled-right is-small mt-1 mb-1">
                <i class="fa fa-check"></i>确认
              </button>
            </div>
          </popover>
          <slot name="ext-operation"></slot>
        </template>
      </column>
    </data-table>
  </div>
</template>

<script>
  import PageBreadcrumb from "./PageBreadcrumb";
  export default {
    name: "ManagePage",
    components: {PageBreadcrumb},
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
    mounted() {
      this.$refs.batchDeletePopover.$refs.popper.hidden = false;
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
      onTableChange(state) {
        console.log(state.pagination);
        console.log(this.pager);
        this.fetchData();
      },
      onSelectChange(selectedKeys) {
        this.$refs.batchDeletePopover.$refs.popper.hidden = selectedKeys.length <= 0;
        this.selectedKeys = selectedKeys;
      }
    }
  }
</script>

<style scoped>

</style>