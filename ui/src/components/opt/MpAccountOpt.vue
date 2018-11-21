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
             @ok="handleMenuSave"
             @cancel="menuVisible = false"
             width="810px">

    </a-modal>

    <a-modal title="编辑欢迎语"
             :maskClosable="false"
             :visible="welcomeMessageVisible"
             @ok="handleWelcomeMessageSave"
             @cancel="welcomeMessageVisible = false"
             width="960px">
      <wx-message-form/>
    </a-modal>

    <a-modal title="自动回复管理"
             :maskClosable="false"
             :visible="autoReplyVisible"
             :footer="null"
             @cancel="autoReplyVisible = false"
             width="680px">

    </a-modal>

    <a-modal title="事件处理管理"
             :maskClosable="false"
             :visible="eventHandlerVisible"
             :footer="null"
             @cancel="eventHandlerVisible = false"
             width="680px">

    </a-modal>
  </span>
</template>

<script>
  import WxMessageForm from "../wx/WxMessageForm";
  export default {
    name: "MpAccountOpt",
    components: {WxMessageForm},
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
        menu: {},
        welcomeMessage: {},
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
        autoReplyLoading: false,
        eventHandlerLoading: false,
        autoReplyQuery: {},
        eventHandlerQuery: {},
        autoReplySortable: {
          ascs: [],
          descs: []
        },
        eventHandlerSortable: {
          ascs: [],
          descs: []
        },
      }
    },
    methods: {
      fetchMenu() {
        this.$api.wx.getMpMenu(this.account.id).then(res => {
          this.menu = res.data;
        });
      },
      fetchWelcomeMessage() {
        this.$api.wx.getMpWelcomeMessageList({accountId: this.account.id}).then(res => {
          if (res.data.length > 0) {
            this.welcomeMessage = res.data[0];
          }
        });
      },
      fetchAutoReplies() {
        this.autoReplyLoading = true;
        this.$api.wx.getMpAutoReplyPage(this.autoReplyPager.current, this.autoReplyPager.pageSize, this.autoReplyQuery,
          this.autoReplySortable.ascs, this.autoReplySortable.descs).then(res => {
          this.autoReplies = res.data.records;
          this.autoReplyPager.current = res.data.current;
          this.autoReplyPager.total = res.data.total;
          this.autoReplyLoading = false;
        }).catch(() => {
          this.autoReplyLoading = false;
        });
      },
      fetchEventHandlers() {
        this.$api.wx.getMpEventHandlerPage(this.eventHandlerPager.current, this.eventHandlerPager.pageSize,
          this.eventHandlerQuery, this.eventHandlerSortable.ascs, this.eventHandlerSortable.descs).then(res => {
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
        this.fetchMenu();
        this.menuVisible = true;
      },
      editWelcomeMessage() {
        this.fetchWelcomeMessage();
        this.$store.dispatch('setWxMessage', this.welcomeMessage.message);
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
      handleMenuSave() {
        this.$api.wx.saveMpMenu(this.account.id, this.menu).then(() => {
          this.$message.success(this.$messages.successResult.save);
          this.menuVisible = false;
        });
      },
      handleWelcomeMessageSave() {
        this.$api.wx.updateMpWelcomeMessage(this.welcomeMessage.id, this.welcomeMessage).then(() => {
          this.$message.success(this.$messages.successResult.update);
          this.welcomeMessageVisible = false;
        });
      }
    }
  }
</script>

<style scoped>

</style>