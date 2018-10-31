<template>
  <div class="page">
    <breadcrumb/>
    <div class="page-content">
      <a-table size="small"
               :columns="$messages.columns[pageKey].tableColumns"
               :dataSource="sessions"
               :rowKey="record => record.id"
               :pagination="false" :loading="loading" :scroll="{y: 'calc(100vh - 365px)'}">
        <template slot="expandedRowRender" slot-scope="record">
          <a-row :gutter="16">
            <a-col :span="12">
              用户名：{{record.attributes["org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY"].primaryPrincipal.username}}
            </a-col>
            <a-col :span="12">
              用户ID：{{record.attributes["org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY"].primaryPrincipal.id}}
            </a-col>
          </a-row>
        </template>
        <template slot="startTimestamp" slot-scope="record">
          {{$utils.date(record.startTimestamp).format('YYYY-MM-DD HH:mm:ss')}}
        </template>
        <template slot="stopTimestamp" slot-scope="record">
          {{record.stopTimestamp === null ? '' : $utils.date(record.stopTimestamp).format('YYYY-MM-DD HH:mm:ss')}}
        </template>
        <template slot="lastAccessTime" slot-scope="record">
          {{$utils.date(record.lastAccessTime).format('YYYY-MM-DD HH:mm:ss')}}
        </template>
        <template slot="expired" slot-scope="record">
          {{record.expired ? '已过期' : '有效'}}
        </template>
        <template slot="opt" slot-scope="record">
          <a-popconfirm title="确定要强制结束该会话吗？" trigger="click" placement="topRight" @confirm="invalidSession(record.id)"
                        :okText="$messages.operation.confirmText" :cancelText="$messages.operation.cancelText">
            <a-button size="small" icon="disconnect">强制结束</a-button>
          </a-popconfirm>
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
  </div>
</template>

<script>
  import Breadcrumb from "../../../../components/Breadcrumb";
  export default {
    name: "Session",
    components: {Breadcrumb},
    data() {
      return {
        pageKey: this.$store.getters.key,
        sessions: [],
        pager: {
          current: 1,
          total: 1,
          pageSize: this.$messages.pager.pageSize
        },
        loading: false,
      }
    },
    created() {
      this.fetchData();
    },
    methods: {
      fetchData() {
        this.loading = true;
        this.$api.sys.getSessionPage(this.pager.current, this.pager.pageSize).then(res => {
          this.sessions = res.data.records;
          this.pager.current = res.data.current;
          this.pager.total = res.data.total;
          this.loading = false;
        }).catch(() => {
          this.loading = false;
        });
      },
      invalidSession(sessionId) {
        this.$api.sys.invalidSession(sessionId).then(() => {
          this.$message.success('强制结束会话成功');
          this.fetchData();
        });
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

</style>