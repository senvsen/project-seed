<template>
  <div class="manage-page">
    <breadcrumb/>
    <div class="manage-content">
      <a-row class="table-toolbar">
        <div class="is-pulled-right">
          <a-button icon="reload" class="mr-1" @click="refreshTable">
            {{$messages.toolbar.refreshBtn}}
          </a-button>
          <a-button icon="search" @click="showSearch">
            {{$messages.toolbar.searchBtn}}
          </a-button>
        </div>
        <div>
          <a-button icon="plus" class="mr-1" @click="addRecord">{{$messages.toolbar.createBtn}}</a-button>
          <a-popconfirm :title="$messages.operation.batchDeleteTip" trigger="click" placement="rightBottom" @confirm="handleBatchDelete"
                        :okText="$messages.operation.confirmText" :cancelText="$messages.operation.cancelText" v-if="selectedKeys.length > 0">
            <a-button icon="delete" class="mr-1">{{$messages.toolbar.batchDeleteBtn}}</a-button>
          </a-popconfirm>
        </div>
      </a-row>
      <a-table size="small"
               :columns="$messages.columns[pageKey].tableColumns"
               :dataSource="data"
               :rowKey="record => record.id"
               :rowSelection="{selectedRowKeys: selectedKeys, onChange: onSelectChange}"
               :pagination="false" :loading="loading" :scroll="{y: 'calc(100vh - 365px)'}">
        <template slot="expandedRowRender" slot-scope="record">
          <a-row :gutter="16">
            <a-col :span="12" v-for="column in $messages.columns[pageKey].expandedColumns" :key="column.title">
              <a-row>
                <a-col :span="6" class="has-text-right">
                  {{column.title}}：
                </a-col>
                <a-col :span="18">
                  {{record[column.dataIndex]}}
                </a-col>
              </a-row>
            </a-col>
          </a-row>
        </template>
        <template slot="createdAt" slot-scope="record">
          {{$utils.date(record.createdAt).format('YYYY-MM-DD HH:mm:ss')}}
        </template>
        <template slot="updatedAt" slot-scope="record">
          {{$utils.date(record.updatedAt).format('YYYY-MM-DD HH:mm:ss')}}
        </template>
        <template slot="opt" slot-scope="record">
          <a-button size="small" class="mr-1" @click="editRecord(record)">{{$messages.operation.editBtn}}</a-button>
          <a-popconfirm :title="$messages.operation.deleteTip" trigger="click" placement="top" @confirm="handleDeleteRecord(record)"
                        :okText="$messages.operation.confirmText" :cancelText="$messages.operation.cancelText">
            <a-button size="small" class="mr-1">{{$messages.operation.deleteBtn}}</a-button>
          </a-popconfirm>
          <slot name="ext-opt" v-bind:record="record"></slot>
        </template>
      </a-table>
      <a-pagination size="small" class="mt-2 is-pulled-right"
                    v-model="pager.current"
                    :total="pager.total"
                    :pageSize.sync="pager.pageSize"
                    :pageSizeOptions="$messages.pager.pageSizeOptions"
                    :showQuickJumper="$messages.pager.showQuickJumper"
                    :showSizeChanger="$messages.pager.showSizeChanger"
                    @change="handlePagerChange"/>
    </div>

    <a-modal :title="modal.title"
             :maskClosable="false"
             :visible="modal.visible"
             @ok="modal.ok"
             @cancel="() => {modal.visible = false}">
      <slot name="form"></slot>
    </a-modal>

    <a-drawer placement="right"
              :closable="false"
              @close="searchClose"
              :visible="searchVisible"
              width="560">
      <template slot="title">
        <a-icon type="search"/> {{$messages.search.title}}
      </template>
      <a-form layout="vertical" hide-required-mark>
        <slot name="advanced-search"></slot>
      </a-form>
      <div class="drawer-opt">
        <a-button class="mr-1" @click="searchReset">
          {{$messages.search.resetBtn}}
        </a-button>
        <a-button type="primary" class="mr-1" @click="handleSearch">
          {{$messages.search.confirmBtn}}
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
        data: [{id: '123', name: '张三'}],
        advancedSearch: false,
        pager: {
          current: 1,
          total: 1,
          pageSize: this.$messages.pager.pageSize
        },
        loading: false,
        searchVisible: false,
        query: {},
        sortable: {
          ascs: [],
          descs: []
        },
        selectedKeys: [],
        modal: {
          title: '',
          visible: false,
          ok: () => {}
        }
      }
    },
    created() {
      this.fetchData();
    },
    computed: {
      pageKey() {
        return this.$store.getters.key;
      }
    },
    watch: {
      pageKey() {
        this.$store.dispatch('setSearch', {});
        this.fetchData();
      }
    },
    methods: {
      fetchData() {
        this.loading = true;
        this.$handler[this.pageKey].fetchData(this.pager.current, this.pager.pageSize,
          this.$store.getters.search, this.sortable.ascs, this.sortable.descs).then(res => {
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
      searchReset() {
        this.$store.dispatch('setSearch', {});
      },
      addRecord() {
        this.$store.dispatch('setRecord', {});
        this.modal = {
          title: this.$messages.modal.createTitle + this.$messages.pageLabel[this.pageKey],
          visible: true,
          ok: this.handleAddRecord
        };
      },
      editRecord(record) {
        this.$store.dispatch('setRecord', JSON.parse(JSON.stringify(record)));
        this.modal = {
          title: this.$messages.modal.editTitle + this.$messages.pageLabel[this.pageKey],
          visible: true,
          ok: this.handleEditRecord
        };
      },
      handleAddRecord() {
        this.$handler[this.pageKey].handleAdd(this.$store.getters.record).then(() => {
          this.$message.success(this.$messages.successResult.create);
        });
      },
      handleEditRecord() {
        this.$handler[this.pageKey].handleEdit(this.$store.getters.record.id, this.$store.getters.record).then(() => {
          this.$message.success(this.$messages.successResult.edit);
        });
      },
      handleDeleteRecord(record) {
        this.$handler[this.pageKey].handleDelete(record.id).then(() => {
          this.$message.success(this.$messages.successResult.delete);
        });
      },
      handleBatchDelete() {
        this.$handler[this.pageKey].handleBatchDelete(this.selectedKeys).then(() => {
          this.$message.success(this.$messages.successResult.batchDelete);
        });
      },
      refreshTable() {
        this.fetchData();
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