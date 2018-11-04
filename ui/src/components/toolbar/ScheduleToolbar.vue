<template>
  <span>
    <span v-if="keys.length > 0">
      <a-popconfirm title="确定批量暂停选中的定时任务吗？" trigger="click" placement="top" @confirm="handleBatchPause"
                    :okText="$messages.operation.confirmText" :cancelText="$messages.operation.cancelText">
        <a-button icon="pause-circle-o" class="mr-1">批量暂停</a-button>
      </a-popconfirm>
      <a-popconfirm title="确定批量恢复选中的定时任务吗？" trigger="click" placement="top" @confirm="handleBatchResume"
                    :okText="$messages.operation.confirmText" :cancelText="$messages.operation.cancelText">
        <a-button icon="play-circle-o">批量恢复</a-button>
      </a-popconfirm>
    </span>
  </span>
</template>

<script>
  export default {
    name: "ScheduleToolbar",
    props: {
      keys: {
        type: Array,
        required: false,
        default() {
          return [];
        }
      }
    },
    methods: {
      handleBatchPause() {
        const jobs = [];
        this.keys.forEach(key => {
          jobs.push({id: key, paused: true});
        });
        this.$api.schedule.batchSaveJob(jobs).then(() => {
          this.$message.success(this.$messages.successResult.operation);
          this.$emit('updated');
        });
      },
      handleBatchResume() {
        const jobs = [];
        this.keys.forEach(key => {
          jobs.push({id: key, paused: false});
        });
        this.$api.schedule.batchSaveJob(jobs).then(() => {
          this.$message.success(this.$messages.successResult.operation);
          this.$emit('updated');
        });
      }
    }
  }
</script>

<style scoped>

</style>