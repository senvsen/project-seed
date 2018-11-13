<template>
  <div>
    <a-row>
      <a-col :span="18" :offset="3">
        <div class="page-content">
          <a-form>
            <a-form-item label="问题概述" required>
              <a-input v-model="report.title" placeholder="请填写问题概述"></a-input>
            </a-form-item>
            <a-form-item label="问题描述" required>
              <a-input type="textarea" :autosize="{minRows: 5}" v-model="report.details" placeholder="请详细描述出现的问题"></a-input>
            </a-form-item>
            <a-form-item label="上传截图 / 附件">
              <a-upload name="file" :multiple="true" action="" :headers="{}" @change="handleUploadChange">
                <a-button>
                  <a-icon type="upload"/> 点击上传
                </a-button>
              </a-upload>
            </a-form-item>
            <a-form-item>
              <a-button type="primary" @click="submitReport">提交报告</a-button>
            </a-form-item>
          </a-form>
        </div>
      </a-col>
    </a-row>
  </div>
</template>

<script>
  import Breadcrumb from "../../../../components/Breadcrumb";
  export default {
    name: "BugReport",
    components: {Breadcrumb},
    data() {
      return {
        report: {}
      }
    },
    methods: {
      handleUploadChange(info) {

      },
      submitReport() {
        alert('提交报告');
      }
    }
  }
</script>

<style scoped>

</style>