<template>
  <div>
    <a-row :gutter="16">
      <a-col :span="10">
        <div class="page-content">
          <h3>新的消息</h3>
          <a-form>
            <a-form-item label="目标用户">
              <a-select mode="multiple"
                        labelInValue
                        :value="selectedUsers"
                        placeholder="请输入关键字查找并选择目标用户"
                        class="users-select"
                        :filterOption="false"
                        @search="fetchUser"
                        @change="handleUserKeywordChange"
                        :notFoundContent="fetching ? undefined : null">
                <a-spin slot="notFoundContent" size="small" v-if="fetching"/>
                <a-select-option v-for="user in users" :key="user.id">{{user.username}} {{user.name}}</a-select-option>
              </a-select>
            </a-form-item>
            <a-row>
              <a-col :span="12">
                <a-form-item label="消息类型">
                  <a-radio-group v-model="message.msgType" @change="handleMsgTypeChange">
                    <a-radio-button v-for="(label, code) in $messages.enums.msgType" :key="code" :value="parseInt(code)">{{label}}</a-radio-button>
                  </a-radio-group>
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label="使用模板">
                  <a-switch v-model="message.useTemplate" @change="handleUseTemplateChange"></a-switch>
                </a-form-item>
              </a-col>
            </a-row>
            <div v-if="message.useTemplate">
              <a-form-item label="消息预览">
                <div class="preview-box">{{previewMessage}}</div>
              </a-form-item>
              <a-form-item label="填充字段" v-if="selectedTemplate.fillFields && selectedTemplate.fillFields.length > 0">
                <a-row :gutter="16">
                  <a-col :span="12" v-for="field in selectedTemplate.fillFields" :key="field">
                    <a-row>
                      <a-col :span="6" class="has-text-right">
                        {{field}}：
                      </a-col>
                      <a-col :span="18">
                        <a-input v-model="message.payload[field]" @input="messagePreview"></a-input>
                      </a-col>
                    </a-row>
                  </a-col>
                </a-row>
              </a-form-item>
            </div>
            <a-form-item label="消息内容" v-else>
              <a-textarea v-model="message.content" :autosize="{minRows: 6, maxRows: 12}"></a-textarea>
            </a-form-item>
            <a-button type="primary" icon="message" @click="pushMessage">推送消息</a-button>
          </a-form>
        </div>
      </a-col>
      <a-col :span="14">
        <div class="page-content">
          <h3>消息模板</h3>
          <a-row class="table-toolbar">
            <div class="is-pulled-right">
              <span>消息类型：
                <a-select v-model="query.msgType" allowClear placeholder="选择类型" class="search-select mr-2">
                  <a-select-option v-for="(label, code) in $messages.enums.msgType" :key="code" :value="code">{{label}}</a-select-option>
                </a-select>
              </span>
              <span>关键字：
                <a-input v-model="query.keyword" placeholder="请填写搜索关键字" class="search-input mr-2"></a-input>
              </span>
              <a-button icon="search" @click="handleSearch">搜索</a-button>
            </div>
            <a-button icon="plus" @click="addTemplate">创建</a-button>
          </a-row>
          <a-table size="small"
                   :columns="$messages.columns['template'].tableColumns"
                   :dataSource="templates"
                   :pagination="false" :loading="loading" :scroll="{y: 'calc(100vh - 365px)'}">
            <template slot="expandedRowRender" slot-scope="record">
              <a-row :gutter="16">
                <a-col :span="12" v-for="column in $messages.columns['template'].expandedColumns" :key="column.title">
                  <a-row>
                    <a-col :span="6" class="has-text-right">
                      {{column.title}}：
                    </a-col>
                    <a-col :span="18">
                      {{$utils.text.expandColumnFormat(record, column)}}
                    </a-col>
                  </a-row>
                </a-col>
              </a-row>
            </template>
            <template slot="msgType" slot-scope="record">
              {{$messages.enums.msgType[record.msgType]}}
            </template>
            <template slot="opt" slot-scope="record">
              <a-button size="small" class="mr-1" @click="editTemplate(record)">{{$messages.operation.editBtn}}</a-button>
              <a-popconfirm :title="$messages.operation.deleteTip" trigger="click" placement="top" @confirm="handleDeleteTemplate(record)"
                            :okText="$messages.operation.confirmText" :cancelText="$messages.operation.cancelText">
                <a-button size="small" class="mr-1">{{$messages.operation.deleteBtn}}</a-button>
              </a-popconfirm>
              <a-button size="small" class="mr-1" @click="selectTemplate(record)"
                        v-if="message.useTemplate && message.msgType === record.msgType">选择</a-button>
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
      </a-col>
    </a-row>

    <a-modal :title="modal.title"
             :maskClosable="false"
             :visible="modal.visible"
             @ok="modal.ok"
             @cancel="() => {modal.visible = false}" width="800px">
      <a-form>
        <a-form-item label="模板名称">
          <a-input v-model="template.name" placeholder="请填写模板名称"></a-input>
        </a-form-item>
        <a-form-item label="消息类型">
          <a-radio-group v-model="template.msgType">
            <a-radio-button v-for="(label, code) in $messages.enums.msgType" :key="code" :value="parseInt(code)">{{label}}</a-radio-button>
          </a-radio-group>
        </a-form-item>
        <a-form-item label="模板格式">
          <a-textarea v-model="template.templatePattern" :autosize="{minRows: 5, maxRows: 10}" placeholder="请填写模板的格式"></a-textarea>
        </a-form-item>
        <a-form-item label="填充字段">
          <a-alert type="info" showIcon class="mb-1">
            <div slot="message">
              填充字段名称使用<code>{{</code>和<code>}}</code>进行包裹，如：<code>{{</code>name<code>}}</code>即表示名为 name 的填充字段。
              <br>
              你可以使用 <a-button size="small" icon="bulb" :loading="identifyLoading" @click="autoIdentify">自动识别</a-button> 一键生成填充字段信息!
            </div>
          </a-alert>
          <tags color="blue" :options="$store.getters.fillFields" @change="handleFieldsChange"></tags>
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script>
  import Breadcrumb from "../../../../components/Breadcrumb";
  import Tags from "../../../../components/Tags";
  import debounce from 'lodash/debounce';

  export default {
    name: "Message",
    components: {Tags, Breadcrumb},
    data() {
      return {
        message: {
          payload: {},
          content: ''
        },
        templates: [],
        pager: {
          current: 1,
          total: 1,
          pageSize: this.$messages.pager.pageSize
        },
        loading: false,
        users: [],
        fetching: false,
        selectedUsers: [],
        selectedTemplate: {},
        previewMessage: '',
        query: {
          keyword: '',
        },
        sortable: {
          ascs: [],
          descs: []
        },
        modal: {
          title: '',
          visible: false,
          ok: () => {}
        },
        template: {},
        identifyLoading: false,
      }
    },
    created() {
      this.fetchUser = debounce(this.fetchUser, 500);
      this.fetchTemplates();
    },
    methods: {
      fetchUser(keyword) {
        this.fetching = true;
        this.$api.auth.getUserPage(1, 10, {keyword: keyword}, [], []).then(res => {
          this.users = res.data.records;
          this.fetching = false;
        }).catch(() => {
          this.fetching = false;
        });
      },
      handleUserKeywordChange(selectedUsers) {
        this.selectedUsers = selectedUsers;
        this.users = [];
        this.fetching = false;
      },
      fetchTemplates() {
        this.loading = true;
        this.$api.msg.getTemplatePage(this.pager.current, this.pager.pageSize, this.query,
          this.sortable.ascs, this.sortable.descs).then(res => {
          this.templates = res.data.records;
          this.pager.current = res.data.current;
          this.pager.total = res.data.total;
          this.loading = false;
        }).catch(() => {
          this.loading = false;
        });
      },
      handleMsgTypeChange() {
        if (this.message.useTemplate) {
          this.selectedTemplate = {};
          this.message.payload = {};
          this.previewMessage = '';
        }
      },
      handleUseTemplateChange(value) {
        if (value) {
          this.message.content = '';
        } else {
          this.selectedTemplate = {};
          this.message.payload = {};
          this.previewMessage = '';
        }
      },
      messagePreview() {
        this.previewMessage = this.$utils.text.formatTemplatePreview(this.selectedTemplate.templatePattern,
          this.selectedTemplate.fillFields, this.message.payload);
      },
      pushMessage() {
        if (this.message.useTemplate) {
          this.message.msgTemplateId = this.selectedTemplate.id;
        }
        const userIds = this.selectedUsers.map(user => {return user.key});
        this.$api.msg.addMessage({
          message: this.message,
          relatedId: {
            firstId: {fieldName: 'messageId'},
            secondIds: {fieldName: 'userId', values: userIds}
          }
        }).then(() => {
          this.$message.success(this.$messages.successResult.operation);
          this.selectedUsers = [];
          this.message = {
            payload: {},
            content: ''
          };
          this.selectedTemplate = {};
          this.previewMessage = '';
        });
      },
      handleSearch() {
        this.fetchTemplates();
      },
      addTemplate() {
        this.template = {
          templatePattern: ''
        };
        this.$store.dispatch('setFillFields', []);
        this.modal = {
          title: '创建模板',
          visible: true,
          ok: this.handleAddTemplate
        };
      },
      editTemplate(template) {
        this.template = JSON.parse(JSON.stringify(template));
        this.$store.dispatch('setFillFields', template.fillFields);
        this.modal = {
          title: '编辑模板',
          visible: true,
          ok: this.handleEditTemplate
        };
      },
      selectTemplate(template) {
        this.selectedTemplate = JSON.parse(JSON.stringify(template));
        this.messagePreview();
      },
      handleAddTemplate() {
        this.$api.msg.addTemplate(this.template).then(() => {
          this.$message.success(this.$messages.successResult.create);
          this.modal.visible = false;
          this.fetchTemplates();
        });
      },
      handleEditTemplate() {
        this.$api.msg.updateTemplate(this.template.id, this.template).then(() => {
          this.$message.success(this.$messages.successResult.update);
          this.modal.visible = false;
          this.fetchTemplates();
        });
      },
      handleDeleteTemplate(template) {
        this.$api.msg.deleteTemplate(template.id).then(() => {
          this.$message.success(this.$messages.successResult.delete);
          this.fetchTemplates();
        });
      },
      handlePagerChange(page, pageSize) {
        this.pager.current = page;
        this.pager.pageSize = pageSize;
        this.fetchTemplates();
      },
      autoIdentify() {
        this.identifyLoading = true;
        const fields = this.$utils.text.parseFields(this.template.templatePattern);
        this.handleFieldsChange(fields);
        this.identifyLoading = false;
      },
      handleFieldsChange(fields) {
        this.template.fillFields = fields;
        this.$store.dispatch('setFillFields', fields);
      },
    }
  };
</script>

<style scoped>
  .users-select {
    width: 100%;
  }
  .preview-box {
    border: 1px solid #e8e8e8;
    border-radius: 4px;
    padding: 8px;
    min-height: 136px;
    line-height: 24px;
  }
  .table-toolbar {
    line-height: 3rem;
  }
  .search-select {
    width: 100px;
  }
  .search-input {
    width: 160px;
  }
</style>