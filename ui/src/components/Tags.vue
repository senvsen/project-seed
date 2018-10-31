<template>
  <div>
    <template v-for="tag in tags">
      <a-tooltip v-if="tag.length > 20" :key="tag" :title="tag">
        <a-tag :color="color" :key="tag" closable :after-close="() => handleClose(tag)">
          {{`${tag.slice(0, 20)}...`}}
        </a-tag>
      </a-tooltip>
      <a-tag :color="color" v-else :key="tag" closable :after-close="() => handleClose(tag)">{{tag}}</a-tag>
    </template>
    <a-input v-if="inputVisible" ref="input" type="text" class="tags-input" :value="inputValue"
             @change="handleInputChange" @blur="handleInputConfirm" @keyup.enter="handleInputConfirm"/>
    <a-tag v-else @click="showInput" class="add-btn">
      <a-icon type="plus"/> 新增
    </a-tag>
  </div>
</template>

<script>
  export default {
    name: "Tags",
    props: {
      options: {
        type: Array,
        required: false,
        default() {
          return [];
        }
      },
      color: {
        type: String,
        required: false
      }
    },
    data() {
      return {
        tags: this.options,
        inputValue: '',
        inputVisible: false,
      }
    },
    methods: {
      handleClose(removedTag) {
        this.tags = this.tags.filter(tag => tag !== removedTag);
      },
      handleInputChange(e) {
        this.inputValue = e.target.value;
      },
      handleInputConfirm() {
        const inputValue = this.inputValue;
        let tags = this.tags;
        if (inputValue && tags.indexOf(inputValue) === -1) {
          tags = [...tags, inputValue];
        }
        this.inputVisible = false;
        this.inputValue = '';
        this.tags = tags;
        this.$emit('confirm', tags);
      },
      showInput() {
        this.inputVisible = true;
        this.$nextTick(function () {
          this.$refs.input.focus();
        });
      }
    }
  }
</script>

<style scoped>
  .tags-input {
    width: 120px;
  }
  .add-btn {
    background: #fff;
    border-style: dashed;
  }
</style>