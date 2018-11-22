<template>
  <div>
    <a-form>
      <a-form-item label="消息类型" required>
        <a-radio-group v-model="message.msgType">
          <a-radio-button v-for="(label, msgType) in $messages.enums.wxMsgTypes" :key="msgType" :value="msgType">{{label}}</a-radio-button>
        </a-radio-group>
      </a-form-item>
      <a-form-item label="内容" v-if="message.msgType === 'text'" required>
        <a-textarea v-model="message.content" :autosize="{minRows: 2, maxRows: 5}" placeholder="请填写消息内容"></a-textarea>
      </a-form-item>
      <a-form-item label="MediaID" v-if="['image', 'voice', 'video'].indexOf(message.msgType) !== -1" required>
        <a-input v-model="message.mediaId" placeholder="请填写mediaId"></a-input>
      </a-form-item>
      <div v-if="['video', 'music'].indexOf(message.msgType) !== -1">
        <a-form-item label="标题">
          <a-input v-model="message.title" placeholder="请填写标题"></a-input>
        </a-form-item>
        <a-form-item label="描述">
          <a-textarea v-model="message.description" :autosize="{minRows: 2, maxRows: 5}" placeholder="请填写描述"></a-textarea>
        </a-form-item>
      </div>
      <div v-if="message.msgType === 'music'">
        <a-form-item label="音乐链接">
          <a-input v-model="message.misicUrl" placeholder="请填写音乐链接"></a-input>
        </a-form-item>
        <a-form-item label="高质量音乐链接">
          <a-input v-model="message.hqMusicUrl" placeholder="请填写高质量音乐链接"></a-input>
        </a-form-item>
        <a-form-item label="音乐缩略图ID" required>
          <a-input v-model="message.thumbMediaId" placeholder="请填写音乐缩略图ID"></a-input>
        </a-form-item>
      </div>
      <div v-if="message.msgType === 'news'">
        <a-form-item :label="'图文消息（共' + message.articles.length + '条）'">
          <div v-for="(article, index) in message.articles" :key="index" class="article-box">
            <div class="title-highlight-border">
              图文 {{index + 1}}
              <a-popconfirm title="确定要移除该图文消息吗？" trigger="click" placement="topRight" @confirm="removeArticle(index)"
                            :okText="$messages.operation.confirmText" :cancelText="$messages.operation.cancelText">
                <a-button type="danger" size="small" shape="circle" icon="delete" class="is-pulled-right"></a-button>
              </a-popconfirm>
            </div>
            <a-form-item label="图文标题" :labelCol="$style.form.label" :wrapperCol="$style.form.wrapper" required>
              <a-input v-model="article.title" placeholder="请填写图文标题"></a-input>
            </a-form-item>
            <a-form-item label="图片链接" :labelCol="$style.form.label" :wrapperCol="$style.form.wrapper" required>
              <a-input v-model="article.title" placeholder="请填写图片链接"></a-input>
            </a-form-item>
            <a-form-item label="点击跳转链接" :labelCol="$style.form.label" :wrapperCol="$style.form.wrapper" required>
              <a-input v-model="article.title" placeholder="请填写图文点击跳转链接"></a-input>
            </a-form-item>
            <a-form-item label="图文描述" :labelCol="$style.form.label" :wrapperCol="$style.form.wrapper" required>
              <a-textarea v-model="article.title" :autosize="{minRows: 2, maxRows: 2}" placeholder="请填写图文描述"></a-textarea>
            </a-form-item>
          </div>
          <a-button type="dashed" block icon="plus" @click="addArticle">添加图文消息</a-button>
        </a-form-item>
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