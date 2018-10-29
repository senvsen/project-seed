<template>
  <div class="manage-page">
    <breadcrumb/>
    <div class="manage-content">
      <a-row class="table-toolbar">
        <div class="is-pulled-right">
          <a-button icon="search" @click="showSearch">
            {{$t('toolbar.searchBtn')}}
          </a-button>
        </div>
        <div>
          <a-button icon="plus" class="mr-1" @click="addRecord">{{$t('toolbar.createBtn')}}</a-button>
          <a-popconfirm :title="$t('operation.batchDeleteTip', {label: $t('pageLabel')[$route.path]})" trigger="click" placement="rightBottom" @confirm="handleBatchDelete"
                     :okText="$t('operation.confirmText')" :cancelText="$t('operation.cancelText')" v-if="selectedKeys.length > 0">
            <a-button icon="delete" class="mr-1">{{$t('toolbar.batchDeleteBtn')}}</a-button>
          </a-popconfirm>
        </div>
      </a-row>
      <a-table size="small"
               :columns="$t('columns')[$route.path]"
               :dataSource="data"
               :rowKey="record => record.id"
               :rowSelection="{selectedRowKeys: selectedKeys, onChange: onSelectChange}"
               :pagination="false" :loading="loading" :scroll="{y: 'calc(100vh - 365px)'}">
        <template slot="createdAt" slot-scope="record">
          {{$utils.date(record.createdAt).format('YYYY-MM-DD HH:mm:ss')}}
        </template>
        <template slot="updatedAt" slot-scope="record">
          {{$utils.date(record.updatedAt).format('YYYY-MM-DD HH:mm:ss')}}
        </template>
        <template slot="opt" slot-scope="record">
          <a-button size="small" class="mr-1" @click="editRecord(record)">{{$t('operation.editBtn')}}</a-button>
          <a-popconfirm :title="$t('operation.deleteTip', {label: $t('pageLabel')[$route.path]})" trigger="click" placement="top" @confirm="handleDeleteRecord(record)"
                        :okText="$t('operation.confirmText')" :cancelText="$t('operation.cancelText')">
            <a-button size="small" class="mr-1">{{$t('operation.deleteBtn')}}</a-button>
          </a-popconfirm>
          <slot name="ext-opt" v-bind:record="record"></slot>
        </template>
      </a-table>
      <a-pagination size="small" class="mt-2 is-pulled-right"
                    v-model="pager.current"
                    :total="pager.total"
                    :pageSize.sync="pager.pageSize"
                    :pageSizeOptions="$t('pager').pageSizeOptions"
                    :showQuickJumper="$t('pager').showQuickJumper"
                    :showSizeChanger="$t('pager').showSizeChanger"
                    @change="handlePagerChange"/>
    </div>

    <a-modal :title="modal.title"
             :maskClosable="false"
             :visible="modal.visible"
             @ok="modal.ok"
             @cancel="() => {modal.visible = false}">
      <a-form>
      </a-form>
    </a-modal>

    <a-drawer placement="right"
              :closable="false"
              @close="searchClose"
              :visible="searchVisible"
              width="560">
      <template slot="title">
        <a-icon type="search"/> {{$t('search.title')}}
      </template>
      <a-form layout="vertical" hide-required-mark>
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item :label="$t('search.idLabel')">
              <a-input :placeholder="$t('search.idPlaceholder')" class="search-input mr-1"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item :label="$t('search.keywordLabel')">
              <a-input :placeholder="$t('search.keywordPlaceholder')" class="search-input mr-1"></a-input>
            </a-form-item>
          </a-col>
          <slot name="advanced-search"></slot>
        </a-row>
      </a-form>
      <div class="drawer-opt">
        <a-button class="mr-1" @click="searchClose">
          {{$t('search.closeBtn')}}
        </a-button>
        <a-button type="primary" class="mr-1" @click="handleSearch">
          {{$t('search.confirmBtn')}}
        </a-button>
      </div>
    </a-drawer>
  </div>
</template>

<script>
  import Breadcrumb from "./Breadcrumb";
  export default {
    name: "ManagePage",
    components: {Breadcrumb},
    data() {
      return {
        data: [],
        advancedSearch: false,
        pager: {
          current: 1,
          total: 1,
          pageSize: this.$t('pager').pageSize
        },
        loading: false,
        searchVisible: false,
        selectedKeys: [],
        modal: {
          title: '',
          visible: false,
          ok: () => {}
        },
        record: {}
      }
    },
    created() {
      let data = [];
      for (let i = 0; i < 10; i++) {
        data.push(
          {id: i, name: 'Mr.' + i, createdAt: this.$utils.date().subtract(1, 'hour')},
        )
      }
      this.data = data;
    },
    methods: {
      fetchData() {
        this.loading = true;
        this.$config[this.$route.path].fetchData().then(res => {
          this.data = res.data.records;
          this.pager.current = res.data.current;
          this.pager.total = res.data.total;
          this.loading = false;
        }).catch(() => {
          this.loading = false;
        });
      },
      showSearch() {
        this.searchVisible = true;
      },
      searchClose() {
        this.searchVisible = false;
      },
      addRecord() {
        this.modal = {
          title: this.$t('modal.createTitle', {label: this.$t('pageLabel')[this.$route.path]}),
          visible: true,
          ok: this.handleAddRecord
        };
      },
      editRecord(record) {
        this.record = JSON.parse(JSON.stringify(record));
        this.modal = {
          title: this.$t('modal.editTitle', {label: this.$t('pageLabel')[this.$route.path]}),
          visible: true,
          ok: this.handleEditRecord
        };
      },
      handleAddRecord() {
        this.$config[this.$route.path].handleAdd();
      },
      handleEditRecord() {
        this.$config[this.$route.path].handleEdit();
      },
      handleDeleteRecord(record) {
        alert('删除');
      },
      handleBatchDelete() {
        alert('批量删除');
      },
      handleSearch() {
        this.searchVisible = false;
        this.fetchData();
      },
      onSelectChange(keys) {
        this.selectedKeys = keys;
      },
      handlePagerChange(page, pageSize) {
        this.pager.current = page;
        this.pager.pageSize = pageSize;
        this.fetchData();
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
  .table-toolbar {
    line-height: 3rem;
  }
  .drawer-opt {
    position: absolute;
    bottom: 0;
    width: 100%;
    border-top: 1px solid #e8e8e8;
    padding: 10px 16px;
    text-align: right;
    left: 0;
    background: #fff;
    border-radius: 0 0 4px 4px;
  }
</style>