<template>
  <div>
    <a-form>
      <a-form-item label="消息类型">
        <a-select v-model="message.msgType" allowClear>
          <a-select-option v-for="(label, msgType) in $messages.enums.wxMsgTypes" :key="msgType" :value="msgType">{{label}}</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="内容" v-if="message.msgType === 'text'"></a-form-item>
      <a-form-item label="MediaID" v-if="['image', 'voice', 'video'].indexOf(message.msgType) !== -1"></a-form-item>
      <div v-if="[].indexOf(message.msgType) !== -1">
        <a-form-item label="标题"></a-form-item>
        <a-form-item label="描述"></a-form-item>
      </div>
      <div v-if="message.msgType === 'music'">
        <a-form-item label="音乐链接"></a-form-item>
        <a-form-item label="高质量音乐链接"></a-form-item>
        <a-form-item label="音乐缩略图ID"></a-form-item>
      </div>
      <div v-if="message.msgType === 'news'">
        <a-form-item :label="'图文消息（共' + message.articles.length + '条）'"></a-form-item>
        <div v-for="(article, index) in message.articles" :key="index" class="article-box">
          <div class="title-highlight-border">
            图文 {{index + 1}}
            <a-popconfirm title="确定要移除该图文消息吗？" trigger="click" placement="topRight" @confirm="removeArticle(index)"
                          :okText="$messages.operation.confirmText" :cancelText="$messages.operation.cancelText">
              <a-button type="danger" size="small" shape="circle" icon="delete" class="is-pulled-right"></a-button>
            </a-popconfirm>
          </div>
          <a-row :gutter="16">
            <a-col :span="12">
              <a-form-item label="图文标题" :labelCol="$style.form.label" :wrapperCol="$style.form.wrapper">
                <a-input v-model="article.title" placeholder="请填写图文标题"></a-input>
              </a-form-item>
              <a-form-item label="图文描述" :labelCol="$style.form.label" :wrapperCol="$style.form.wrapper">
                <a-textarea v-model="article.title" :autosize="{minRows: 2, maxRows: 2}" placeholder="请填写图文描述"></a-textarea>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label="图片链接" :labelCol="$style.form.label" :wrapperCol="$style.form.wrapper">
                <a-input v-model="article.title" placeholder="请填写图片链接"></a-input>
              </a-form-item>
              <a-form-item label="点击跳转链接" :labelCol="$style.form.label" :wrapperCol="$style.form.wrapper">
                <a-input v-model="article.title" placeholder="请填写图文点击跳转链接"></a-input>
              </a-form-item>
            </a-col>
          </a-row>
        </div>
        <a-button type="dashed" block icon="plus" @click="addArticle">添加图文消息</a-button>
      </div>
    </a-form>
  </div>
</template>

<script>
  export default {
    name: "WxMessageForm",
    computed: {
      message() {
        return this.$store.getters.wxMessage;
      }
    },
    watch: {
      message(val) {
        this.$store.dispatch('setWxMessage', val);
      }
    },
    methods: {
      addArticle() {
        this.message.articles.push({});
      },
      removeArticle(index) {
        this.message.articles.splice(index, 1);
      }
    }
  }
</script>

<style scoped>
  .article-box {
    border: 1px solid #e8e8e8;
    border-radius: 4px;
    padding: 8px;
    margin-bottom: 8px;
  }
</style>