<template>
  <span>
    <a-dropdown placement="bottomRight">
      <a-menu slot="overlay" @click="handleAccountOptClick">
        <a-menu-item key="menu">导航菜单</a-menu-item>
        <a-menu-item key="welcome-message">欢迎语</a-menu-item>
        <a-menu-item key="auto-reply">自动回复</a-menu-item>
        <a-menu-item key="event-handler">事件处理</a-menu-item>
      </a-menu>
      <a-button size="small">
        操作 <a-icon type="down"/>
      </a-button>
    </a-dropdown>

    <a-modal title="编辑公众号菜单"
             :maskClosable="false"
             :visible="menuVisible"
             :footer="null"
             @cancel="menuVisible = false"
             width="900px">
      <a-spin :spinning="menuLoading">
        <wx-menu-editor :accountId="account.id" @synced="menuVisible = false"/>
      </a-spin>
    </a-modal>

    <a-modal title="编辑欢迎语"
             :maskClosable="false"
             :visible="welcomeMessageVisible"
             @ok="handleWelcomeMessageSave"
             @cancel="welcomeMessageVisible = false"
             width="680px">
      <a-spin :spinning="welcomeMessageLoading">
        <div>启用欢迎语：
          <a-switch v-model="welcomeMessage.active">
            <a-icon type="check" slot="checkedChildren"/>
            <a-icon type="cross" slot="unCheckedChildren"/>
          </a-switch>
        </div>
        <wx-message-form v-if="welcomeMessage.active"/>
      </a-spin>
    </a-modal>

    <a-modal title="自动回复管理"
             :maskClosable="false"
             :visible="autoReplyVisible"
             :footer="null"
             @cancel="handleAutoReplyClose"
             width="900px">
      <a-row class="table-toolbar">
        <div class="is-pulled-right">
          <a-button icon="reload" @click="fetchAutoReplies">刷新</a-button>
        </div>
        <a-button icon="plus" class="mr-1" @click="addAutoReply">{{$messages.toolbar.createBtn}}</a-button>
      </a-row>
      <a-table size="small"
               :columns="$messages.columns['auto-reply'].tableColumns"
               :dataSource="autoReplies"
               :pagination="false" :loading="autoReplyLoading" :scroll="{y: 408}">
        <template slot="expandedRowRender" slot-scope="record">
          <a-row :gutter="16">
            <a-col :span="12" v-for="column in $messages.columns['auto-reply'].expandedColumns" :key="column.title">
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
        <template slot="keywords" slot-scope="record">
          <a-tag v-for="(keyword, index) in record.keywords" :key="index">{{keyword}}</a-tag>
        </template>
        <template slot="opt" slot-scope="record">
          <a-button size="small" class="mr-1" @click="editAutoReply(record)">编辑</a-button>
          <a-popconfirm title="确定要删除该自动回复吗？" trigger="click" placement="topRight" @confirm="handleDeleteAutoReply(record.id)"
                        :okText="$messages.operation.confirmText" :cancelText="$messages.operation.cancelText">
            <a-button size="small" class="mr-1">删除</a-button>
          </a-popconfirm>
        </template>
      </a-table>
      <a-pagination size="small" class="mt-2"
                    v-model="autoReplyPager.current"
                    :total="autoReplyPager.total"
                    :pageSize.sync="autoReplyPager.pageSize"
                    :pageSizeOptions="$messages.pager.pageSizeOptions"
                    :showQuickJumper="$messages.pager.showQuickJumper"
                    :showSizeChanger="$messages.pager.showSizeChanger"
                    @change="handleAutoReplyPagerChange"/>

      <div v-if="editAutoReplyVisible">
        <a-divider>编辑自动回复 {{autoReply.id}}</a-divider>
        <a-form>
          <a-form-item label="关键字" required>
            <tags :options="autoReply.keywords" @change="handleKeywordsChange"/>
          </a-form-item>
          <a-form-item label="匹配规则" required>
            <a-radio-group v-model="autoReply.matchRule">
              <a-radio-button v-for="(label, code) in $messages.enums.matchRule" :key="code" :value="parseInt(code)">{{label}}</a-radio-button>
            </a-radio-group>
          </a-form-item>
          <wx-message-form/>
          <a-form-item>
            <a-button type="primary" class="mr-1" @click="handleAutoReplySave">提交</a-button>
            <a-button @click="editAutoReplyVisible = false">取消</a-button>
          </a-form-item>
        </a-form>
      </div>
    </a-modal>

    <a-modal title="事件处理管理"
             :maskClosable="false"
             :visible="eventHandlerVisible"
             :footer="null"
             @cancel="handleEventHandlerClose"
             width="900px">
      <a-row class="table-toolbar">
        <div class="is-pulled-right">
          <a-button icon="reload" @click="fetchEventHandlers">刷新</a-button>
        </div>
        <a-button icon="plus" class="mr-1" @click="addEventHandler">{{$messages.toolbar.createBtn}}</a-button>
      </a-row>
      <a-table size="small"
               :columns="$messages.columns['event-handler'].tableColumns"
               :dataSource="eventHandlers"
               :pagination="false" :loading="eventHandlerLoading" :scroll="{y: 408}">
        <template slot="expandedRowRender" slot-scope="record">
          <a-row :gutter="16">
            <a-col :span="12" v-for="column in $messages.columns['event-handler'].expandedColumns" :key="column.title">
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
        <template slot="event-type" slot-scope="record">
          {{$messages.enums.wxEventTypes[record.eventType]}}
        </template>
        <template slot="opt" slot-scope="record">
          <a-button size="small" class="mr-1" @click="editEventHandler(record)">编辑</a-button>
          <a-popconfirm title="确定要删除该公众号事件处理吗？" trigger="click" placement="topRight" @confirm="handleDeleteEventHandler(record.id)"
                        :okText="$messages.operation.confirmText" :cancelText="$messages.operation.cancelText">
            <a-button size="small" class="mr-1">删除</a-button>
          </a-popconfirm>
        </template>
      </a-table>
      <a-pagination size="small" class="mt-2"
                    v-model="eventHandlerPager.current"
                    :total="eventHandlerPager.total"
                    :pageSize.sync="eventHandlerPager.pageSize"
                    :pageSizeOptions="$messages.pager.pageSizeOptions"
                    :showQuickJumper="$messages.pager.showQuickJumper"
                    :showSizeChanger="$messages.pager.showSizeChanger"
                    @change="handleEventHandlerPagerChange"/>
      <div v-if="editEventHandlerVisible">
        <a-divider>编辑公众号事件处理 {{eventHandler.id}}</a-divider>
        <a-form>
          <a-row :gutter="16">
            <a-col :span="12">
              <a-form-item label="事件类型" required>
                <a-select v-model="eventHandler.eventType" placeholder="请选择事件类型">
                  <a-select-option v-for="(label, type) in $messages.enums.wxEventTypes" :key="type" :value="type" :disabled="type === 'enter_agent'">{{label}}</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label="处理类路径" required>
                <a-input v-model="eventHandler.handlerClass" placeholder="请填写处理类路径"></a-input>
              </a-form-item>
            </a-col>
          </a-row>
          <a-form-item>
            <a-button type="primary" class="mr-1" @click="handleEventHandlerSave">提交</a-button>
            <a-button @click="editEventHandlerVisible = false">取消</a-button>
          </a-form-item>
        </a-form>
      </div>
    </a-modal>
  </span>
</template>

<script>
  import WxMessageForm from "../wx/WxMessageForm";
  import WxMenuEditor from "../wx/WxMenuEditor";
  import Tags from "../Tags";
  export default {
    name: "MpAccountOpt",
    components: {Tags, WxMenuEditor, WxMessageForm},
    props: {
      rowData: {
        type: Object,
        required: true
      }
    },
    computed: {
      account() {
        return this.rowData;
      }
    },
    data() {
      return {
        menuVisible: false,
        welcomeMessageVisible: false,
        autoReplyVisible: false,
        eventHandlerVisible: false,
        welcomeMessage: {
          active: false,
          message: {}
        },
        autoReplies: [],
        eventHandlers: [],
        autoReplyPager: {
          current: 1,
          total: 1,
          pageSize: this.$messages.pager.pageSize
        },
        eventHandlerPager: {
          current: 1,
          total: 1,
          pageSize: this.$messages.pager.pageSize
        },
        menuLoading: false,
        welcomeMessageLoading: false,
        autoReplyLoading: false,
        eventHandlerLoading: false,
        autoReplySortable: {
          ascs: [],
          descs: []
        },
        eventHandlerSortable: {
          ascs: [],
          descs: []
        },
        autoReply: {},
        eventHandler: {},
        editAutoReplyVisible: false,
        editEventHandlerVisible: false,
      }
    },
    methods: {
      fetchMenu() {
        this.menuLoading = true;
        this.$api.wx.getMpMenu(this.account.id).then(res => {
          let buttons = res.data.menu.buttons;
          if (buttons) {
            buttons.forEach(button => {
              if (button.subButtons && button.subButtons.length > 0) {
                button.sub_button = button.subButtons;
                button.type = '';
              }
              delete button.subButtons;
            });
            this.$store.dispatch('setWxMenu', {button: buttons});
          }
          this.menuLoading = false;
        }).catch(() => {
          this.menuLoading = false;
        });
      },
      fetchWelcomeMessage() {
        this.welcomeMessageLoading = true;
        this.$api.wx.getMpWelcomeMessageList({accountId: this.account.id}).then(res => {
          if (res.data.length > 0) {
            this.welcomeMessage = res.data[0];
            this.$store.dispatch('setWxMessage', this.welcomeMessage.message);
          }
          this.welcomeMessageLoading = false;
        }).catch(() => {
          this.welcomeMessageLoading = false;
        });
      },
      fetchAutoReplies() {
        this.autoReplyLoading = true;
        this.$api.wx.getMpAutoReplyPage(this.autoReplyPager.current, this.autoReplyPager.pageSize, 
          {accountId: this.account.id}, this.autoReplySortable.ascs, this.autoReplySortable.descs).then(res => {
          this.autoReplies = res.data.records;
          this.autoReplyPager.current = res.data.current;
          this.autoReplyPager.total = res.data.total;
          this.autoReplyLoading = false;
        }).catch(() => {
          this.autoReplyLoading = false;
        });
      },
      fetchEventHandlers() {
        this.eventHandlerLoading = true;
        this.$api.wx.getMpEventHandlerPage(this.eventHandlerPager.current, this.eventHandlerPager.pageSize,
          {accountId: this.account.id}, this.eventHandlerSortable.ascs, this.eventHandlerSortable.descs).then(res => {
          this.eventHandlers = res.data.records;
          this.eventHandlerPager.current = res.data.current;
          this.eventHandlerPager.total = res.data.total;
          this.eventHandlerLoading = false;
        }).catch(() => {
          this.eventHandlerLoading = false;
        });
      },
      handleAccountOptClick({key}) {
        switch (key) {
          case 'menu':
            this.editMenu();
            break;
          case 'welcome-message':
            this.editWelcomeMessage();
            break;
          case 'auto-reply':
            this.autoReplyManage();
            break;
          case 'event-handler':
            this.eventHandlerManage();
            break;
          default:
            console.error('不支持的操作项');
        }
      },
      editMenu() {
        this.$store.dispatch('setWxMenu', {button: []});
        this.fetchMenu();
        this.menuVisible = true;
      },
      editWelcomeMessage() {
        this.fetchWelcomeMessage();
        this.welcomeMessageVisible = true;
      },
      autoReplyManage() {
        this.fetchAutoReplies();
        this.autoReplyVisible = true;
      },
      eventHandlerManage() {
        this.fetchEventHandlers();
        this.eventHandlerVisible = true;
      },
      addAutoReply() {
        const reply = {articles: []};
        this.$store.dispatch('setWxMessage', reply);
        this.autoReply = {keywords: [], reply: reply};
        this.editAutoReplyVisible = true;
      },
      editAutoReply(autoReply) {
        this.autoReply = JSON.parse(JSON.stringify(autoReply));
        this.$store.dispatch('setWxMessage', autoReply.reply);
        this.editAutoReplyVisible = true;
      },
      addEventHandler() {
        this.eventHandler = {accountId: this.account.id};
        this.editEventHandlerVisible = true;
      },
      editEventHandler(eventHandler) {
        this.eventHandler = JSON.parse(JSON.stringify(eventHandler));
        this.editEventHandlerVisible = true;
      },
      handleWelcomeMessageSave() {
        this.welcomeMessage.message = this.$store.getters.wxMessage;
        if (this.welcomeMessage.id) {
          this.$api.wx.updateMpWelcomeMessage(this.welcomeMessage.id, this.welcomeMessage).then(() => {
            this.$message.success(this.$messages.successResult.update);
            this.welcomeMessageVisible = false;
          });
        } else {
          this.welcomeMessage.accountId = this.account.id;
          this.$api.wx.addMpWelcomeMessage(this.welcomeMessage).then(() => {
            this.$message.success(this.$messages.successResult.create);
            this.welcomeMessageVisible = false;
          });
        }
      },
      handleAutoReplySave() {
        this.autoReply.reply = this.$store.getters.wxMessage;
        if (this.autoReply.id) {
          this.$api.wx.updateMpAutoReply(this.autoReply.id, this.autoReply).then(() => {
            this.$message.success(this.$messages.successResult.update);
            this.editAutoReplyVisible = false;
            this.fetchAutoReplies();
          });
        } else {
          this.autoReply.accountId = this.account.id;
          this.$api.wx.addMpAutoReply(this.autoReply).then(() => {
            this.$message.success(this.$messages.successResult.create);
            this.editAutoReplyVisible = false;
            this.fetchAutoReplies();
          });
        }
      },
      handleEventHandlerSave() {
        if (this.eventHandler.id) {
          this.$api.wx.updateMpEventHandler(this.eventHandler.id, this.eventHandler).then(() => {
            this.$message.success(this.$messages.successResult.update);
            this.editEventHandlerVisible = false;
            this.fetchEventHandlers();
          });
        } else {
          this.$api.wx.addMpEventHandler(this.eventHandler).then(() => {
            this.$message.success(this.$messages.successResult.create);
            this.editEventHandlerVisible = false;
            this.fetchEventHandlers();
          });
        }
      },
      handleDeleteAutoReply(id) {
        this.$api.wx.deleteMpAutoReply(id).then(() => {
          this.$message.success(this.$messages.successResult.delete);
          this.fetchAutoReplies();
        });
      },
      handleDeleteEventHandler(id) {
        this.$api.wx.deleteMpEventHandler(id).then(() => {
          this.$message.success(this.$messages.successResult.delete);
          this.fetchEventHandlers();
        });
      },
      handleKeywordsChange(keywords) {
        this.autoReply.keywords = [];
        keywords.forEach(keyword => {
          this.autoReply.keywords.push(keyword);
        });
      },
      handleAutoReplyPagerChange(page, pageSize) {
        this.autoReplyPager.current = page;
        this.autoReplyPager.pageSize = pageSize;
        this.fetchAutoReplies();
      },
      handleEventHandlerPagerChange(page, pageSize) {
        this.eventHandlerPager.current = page;
        this.eventHandlerPager.pageSize = pageSize;
        this.fetchEventHandlers();
      },
      handleAutoReplyClose() {
        this.autoReplyVisible = false;
        this.editAutoReplyVisible = false;
      },
      handleEventHandlerClose() {
        this.eventHandlerVisible = false;
        this.editEventHandlerVisible = false;
      }
    }
  }
</script>

<style scoped>
  .table-toolbar {
    line-height: 3rem;
  }
</style>