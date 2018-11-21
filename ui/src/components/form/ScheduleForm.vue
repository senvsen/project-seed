<template>
  <div>
    <a-form>
      <a-form-item label="执行类路径" :labelCol="$style.form.label" :wrapperCol="$style.form.wrapper" required>
        <a-input v-model="job.className" placeholder="请填写执行类路径"></a-input>
      </a-form-item>
      <a-form-item label="CRON表达式" :labelCol="$style.form.label" :wrapperCol="$style.form.wrapper" required>
        <a-input v-model="job.cronExpression" placeholder="请填写CRON表达式"></a-input>
      </a-form-item>
      <a-form-item label="任务名称" :labelCol="$style.form.label" :wrapperCol="$style.form.wrapper" required>
        <a-input v-model="job.jobName" placeholder="请填写任务名称"></a-input>
      </a-form-item>
      <a-form-item label="任务组" :labelCol="$style.form.label" :wrapperCol="$style.form.wrapper" required>
        <a-input v-model="job.jobGroup" placeholder="请填写任务组"></a-input>
      </a-form-item>
      <a-form-item label="触发器名称" :labelCol="$style.form.label" :wrapperCol="$style.form.wrapper" required>
        <a-input v-model="job.triggerName" placeholder="请填写触发器名称"></a-input>
      </a-form-item>
      <a-form-item label="触发器组" :labelCol="$style.form.label" :wrapperCol="$style.form.wrapper" required>
        <a-input v-model="job.triggerGroup" placeholder="请填写触发器组"></a-input>
      </a-form-item>
      <a-form-item label="定时任务描述" :labelCol="$style.form.label" :wrapperCol="$style.form.wrapper">
        <a-input type="textarea" v-model="job.description" placeholder="请填写定时任务描述"></a-input>
      </a-form-item>
    </a-form>
  </div>
</template>

<script>
  export default {
    name: "ScheduleForm",
    computed: {
      job() {
        return this.$store.getters.record;
      }
    },
    watch: {
      job(val) {
        this.$store.dispatch('setRecord', val);
      }
    }
  }
</script>

<style scoped>

</style>