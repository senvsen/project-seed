<template>
  <span>
    <a-popconfirm title="确定恢复该定时任务吗？" trigger="click" placement="top" @confirm="handleResume"
                  :okText="$messages.operation.confirmText" :cancelText="$messages.operation.cancelText" v-if="rowData.paused">
      <a-button size="small" class="mr-1">恢复</a-button>
    </a-popconfirm>
    <a-popconfirm title="确定暂停该定时任务吗？" trigger="click" placement="top" @confirm="handlePause"
                  :okText="$messages.operation.confirmText" :cancelText="$messages.operation.cancelText" v-else>
      <a-button size="small" class="mr-1">暂停</a-button>
    </a-popconfirm>
  </span>
</template>

<script>
  export default {
    name: "ScheduleOpt",
    props: {
      rowData: {
        type: Object,
        required: true
      }
    },
    computed: {
      job() {
        return this.rowData;
      }
    },
    methods: {
      handleResume() {
        this.$api.schedule.resumeJob(this.job.id).then(() => {
          this.$message.success(this.$messages.successResult.operation);
          this.$emit('updated');
        });
      },
      handlePause() {
        this.$api.schedule.pauseJob(this.job.id).then(() => {
          this.$message.success(this.$messages.successResult.operation);
          this.$emit('updated');
        });
      }
    }
  }
</script>

<style scoped>

</style>