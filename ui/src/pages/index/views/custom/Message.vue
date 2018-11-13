<template>
  <div>
    <a-row :gutter="16">
      <a-col :span="14">
        <div class="page-content">
          <h3>新的消息</h3>
          <a-form>
            <a-form-item label="目标用户">
              <a-select></a-select>
            </a-form-item>
            <a-row>
              <a-col :span="10">
                <a-form-item label="消息类型">
                  <a-radio-group v-model="message.msgType">
                    <a-radio-button v-for="(label, code) in $messages.enums.msgType" :key="code" :value="code">{{label}}</a-radio-button>
                  </a-radio-group>
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="使用模板">
                  <a-switch v-model="message.useTemplate"></a-switch>
                </a-form-item>
              </a-col>
              <a-col :span="8">
                <a-form-item label="选择模板" v-if="message.useTemplate">
                  <a-select v-model="message.templateId" allowClear>

                  </a-select>
                </a-form-item>
              </a-col>
            </a-row>
            <div v-if="message.useTemplate">
              <a-form-item label="消息预览">
                <div class="preview-box">{{messagePreview}}</div>
              </a-form-item>
              <a-form-item label="填充字段">
                <a-row :gutter="16">
                  <a-col v-for="i in 8" :span="6">
                    <a-input addonBefore="用户名"></a-input>
                  </a-col>
                </a-row>
              </a-form-item>
            </div>
            <a-form-item label="消息内容" v-else>
              <a-textarea v-model="message.content" :autosize="{minRows: 6, maxRows: 12}"></a-textarea>
            </a-form-item>
            <a-button type="primary" icon="message">推送消息</a-button>
          </a-form>
        </div>
      </a-col>
      <a-col :span="10">
        <div class="page-content">
          <h3>消息模板</h3>
          <a-row class="table-toolbar">
            <a-button icon="plus">创建</a-button>
          </a-row>
        </div>
      </a-col>
    </a-row>
  </div>
</template>

<script>
  import Breadcrumb from "../../../../components/Breadcrumb";
  export default {
    name: "Message",
    components: {Breadcrumb},
    data() {
      return {
        message: {},
        messagePreview: ''
      }
    }
  }
</script>

<style scoped>
  .preview-box {
    border: 1px solid #e8e8e8;
    border-radius: 4px;
    padding: 8px;
    min-height: 136px;
  }
  .table-toolbar {
    line-height: 3rem;
  }
</style>