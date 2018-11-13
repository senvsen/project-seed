<template>
  <div>
    <a-row :gutter="16">
      <a-col :span="16">
        <div class="page-content">
          <h3>新的公告</h3>
          <a-form>
            <a-form-item label="通知内容">
              <a-textarea v-model="systemNotice.msgContent" :autosize="{minRows: 5, maxRows: 10}"></a-textarea>
            </a-form-item>
            <a-form-item label="通知等级">
              <a-radio-group v-model="systemNotice.msgLevel">
                <a-radio-button v-for="(label, code) in $messages.enums.msgLevel" :key="code" :value="code">{{label}}</a-radio-button>
              </a-radio-group>
            </a-form-item>
            <a-form-item label="公告时间">
              <a-range-picker v-model="noticeTimes"
                              showTime format="YYYY-MM-DD HH:mm:ss"
                              allowClear
                              @change="handleTimeChange"></a-range-picker>
            </a-form-item>
            <a-form-item>
              <a-button type="primary" icon="notification" @click="handleSave">创建公告</a-button>
            </a-form-item>
          </a-form>
        </div>
      </a-col>
      <a-col :span="8">
        <div class="page-content">
          <h3>当前公告
            <!--<a-button size="small" icon="clock-circle-o" class="is-pulled-right">历史公告</a-button>-->
          </h3>
          <div class="notice-container">
            <div v-for="notice in notices" :key="notice.id" class="notice-box mb-2">
              <p>
                <a-tag :color="noticeColor(notice.msgLevel)">{{$messages.enums.msgLevel[notice.msgLevel] || '未知'}}</a-tag>
                {{notice.msgContent}}
              </p>
              <span>
                <a-icon type="calendar" class="mr-1"/>
                <span class="notice-time has-text-mute">{{$utils.date(notice.startTime).format('YYYY年MM月DD日HH:mm:ss')}} - {{$utils.date(notice.endTime).format('YYYY年MM月DD日HH:mm:ss')}}</span>
              </span>
              <span class="is-pulled-right">
                <a-button size="small" icon="edit" class="mr-1" @click="edit(notice)"></a-button>
                <a-popconfirm title="确定要删除该条公告吗？" trigger="click" placement="topRight" @confirm="handleDelete(notice.id)"
                              :okText="$messages.operation.confirmText" :cancelText="$messages.operation.cancelText">
                  <a-button size="small" icon="delete"></a-button>
                </a-popconfirm>
              </span>
            </div>
          </div>
        </div>
      </a-col>
    </a-row>

    <a-modal title="编辑公告"
             :maskClosable="false"
             :visible="editVisible"
             @ok="handleEdit"
             @cancel="editVisible = false">
      <a-form>
        <a-form-item label="通知内容">
          <a-textarea v-model="editNotice.msgContent" :autosize="{minRows: 5, maxRows: 10}"></a-textarea>
        </a-form-item>
        <a-form-item label="通知等级">
          <a-radio-group v-model="editNotice.msgLevel">
            <a-radio-button v-for="(label, code) in $messages.enums.msgLevel" :key="code" :value="parseInt(code)">{{label}}</a-radio-button>
          </a-radio-group>
        </a-form-item>
        <a-form-item label="公告时间">
          <a-range-picker :value="editNoticeTimes"
                          showTime
                          format="YYYY-MM-DD HH:mm:ss"
                          allowClear
                          @change="handleEditTimeChange"></a-range-picker>
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script>
  import moment from 'moment';
  import Breadcrumb from "../../../../components/Breadcrumb";
  export default {
    name: "SystemNotice",
    components: {Breadcrumb},
    data() {
      return {
        notices: [],
        systemNotice: {},
        noticeTimes: [],
        editVisible: false,
        editNotice: {},
        editNoticeTimes: []
      }
    },
    created() {
      this.fetchNotices();
    },
    methods: {
      fetchNotices() {
        this.$api.msg.getSystemNoticeList({type: 'available'}).then(res => {
          this.notices = res.data;
        });
      },
      edit(notice) {
        this.editNotice = JSON.parse(JSON.stringify(notice));
        this.editNoticeTimes = [moment(notice.startTime), moment(notice.endTime)];
        this.editNotice.startTime = this.$utils.date(notice.startTime).format('YYYY-MM-DD HH:mm:ss');
        this.editNotice.endTime = this.$utils.date(notice.endTime).format('YYYY-MM-DD HH:mm:ss');
        this.editVisible = true;
      },
      handleTimeChange(dates, dateStrings) {
        this.systemNotice.startTime = dateStrings[0];
        this.systemNotice.endTime = dateStrings[1];
      },
      handleEditTimeChange(dates, dateStrings) {
        this.editNoticeTimes = dates;
        this.editNotice.startTime = dateStrings[0];
        this.editNotice.endTime = dateStrings[1];
      },
      handleSave() {
        this.$api.msg.addSystemNotice(this.systemNotice).then(() => {
          this.$message.success(this.$messages.successResult.create);
          this.systemNotice = {};
          this.noticeTimes = [];
          this.fetchNotices();
        });
      },
      handleEdit() {
        this.$api.msg.updateSystemNotice(this.editNotice.id, this.editNotice).then(() => {
          this.$message.success(this.$messages.successResult.update);
          this.editVisible = false;
          this.fetchNotices();
        });
      },
      handleDelete(id) {
        this.$api.msg.deleteSystemNotice(id).then(() => {
          this.$message.success(this.$messages.successResult.delete);
          this.fetchNotices();
        });
      },
      noticeColor(msgLevel) {
        let color = '#e8e8e8';
        switch (msgLevel) {
          case 1:
            color = '#1890ff';
            break;
          case 2:
            color = '#faad14';
            break;
          case 3:
            color = '#f5222d';
            break;
        }
        return color;
      }
    }
  }
</script>

<style scoped>
  .notice-container {
    height: calc(100vh - 292px);
    width: 100%;
    overflow-y: auto;
  }
  .notice-box {
    border: 1px solid #e8e8e8;
    border-radius: 4px;
    padding: 8px;
  }
  .notice-time {
    font-size: 12px;
  }
</style>