<template>
  <div>
    <a-row :gutter="16">
      <a-col :span="10">
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
      <a-col :span="14">
        <div class="page-content">
          <h3>消息模板</h3>
          <a-row class="table-toolbar">
            <a-button icon="plus">创建</a-button>
          </a-row>
          <a-table size="small"
                   :columns="$messages.columns['template'].tableColumns"
                   :dataSource="templates"
                   :rowKey="record => record.id"
                   :rowSelection="{selectedRowKeys: selectedKeys, onChange: onSelectChange}"
                   :pagination="false" :loading="loading" :scroll="{y: 'calc(100vh - 365px)'}">
            <template slot="expandedRowRender" slot-scope="record">
              <a-row :gutter="16">
                <a-col :span="12" v-for="column in $messages.columns['template'].expandedColumns" :key="column.title">
                  <a-row>
                    <a-col :span="6" class="has-text-right">
                      {{column.title}}：
                    </a-col>
                    <a-col :span="18">
                      {{column.isDate ? $utils.date(record[column.dataIndex]).format('YYYY-MM-DD HH:mm:ss') : record[column.dataIndex]}}
                    </a-col>
                  </a-row>
                </a-col>
              </a-row>
            </template>
            <template slot="opt" slot-scope="record">
              <a-button size="small" class="mr-1" @click="editRecord(record)">{{$messages.operation.editBtn}}</a-button>
              <a-popconfirm :title="$messages.operation.deleteTip" trigger="click" placement="top" @confirm="handleDeleteRecord(record)"
                            :okText="$messages.operation.confirmText" :cancelText="$messages.operation.cancelText">
                <a-button size="small" class="mr-1">{{$messages.operation.deleteBtn}}</a-button>
              </a-popconfirm>
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
        messagePreview: '',
        templates: [{}],
        pager: {
          current: 1,
          total: 1,
          pageSize: this.$messages.pager.pageSize
        },
        loading: false,
        selectedKeys: [],
      }
    },
    methods: {
      fetchTemplates() {


      },
      onSelectChange(keys) {
        this.selectedKeys = keys;
      },
      handlePagerChange(page, pageSize) {
        this.pager.current = page;
        this.pager.pageSize = pageSize;
        this.fetchTemplates();
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