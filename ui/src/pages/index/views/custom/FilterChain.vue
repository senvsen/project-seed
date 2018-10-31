<template>
  <div class="page">
    <breadcrumb/>
    <div class="page-content">
      <a-row :gutter="16">
        <a-col :span="16">
          <a-row class="toolbar">
            <a-button icon="plus" class="mr-1" @click="addFilterChain">创建</a-button>
            <a-button icon="reload" @click="handleRefresh">刷新</a-button>
          </a-row>
          <a-spin :spinning="loading">
            <div class="filter-chain-container">
              <div v-for="chain in filterChains" :key="chain.id" class="filter-chain-box mb-1">
                <a-row>
                  <a-col :span="10">
                    <span><a-icon type="link"/> 目标URL：</span>
                    <a-input size="small" v-model="chain.url" v-if="showInput(chain.id)" class="edit-input"></a-input>
                    <b v-else>{{chain.url}}</b>
                  </a-col>
                  <a-col :span="10">
                    <span><a-icon type="filter"/> 过滤器：</span>
                    <a-input size="small" v-model="chain.filter" v-if="showInput(chain.id)" class="edit-input"></a-input>
                    <b v-else>{{chain.filter}}</b>
                  </a-col>
                  <a-col :span="4">
                    <a-button size="small" :icon="showInput(chain.id) ? 'check' : 'edit'" class="mr-1" @click="toggleEditChain(chain)"></a-button>
                    <a-popconfirm title="确定删除该条过滤规则吗？" trigger="click" placement="top" @confirm="handleDeleteFilterChain(chain.id)"
                                  :okText="$messages.operation.confirmText" :cancelText="$messages.operation.cancelText">
                      <a-button size="small" icon="delete"></a-button>
                    </a-popconfirm>
                  </a-col>
                </a-row>
              </div>
            </div>
          </a-spin>
        </a-col>
        <a-col :span="8">
          <a-alert type="info" showIcon>
            <template slot="message">
              <div>过滤器说明：</div>
              <div><b>anon：</b>无参，表示可匿名使用,可以理解为匿名用户或游客</div>
              <div><b>authc：</b>无参，表示需认证才能使用</div>
              <div><b>authcBasic：</b>无参，表示httpBasic认证</div>
              <div><b>logout：</b>无参，表示注销登录</div>
              <div><b>perms：</b>参数可写多个，多参时必须加上引号，且参数之间用逗号分割，如/admins/user/**=perms["user:add:*,user:modify:*"]，
                当有多个参数时必须每个参数都通过才算通过，相当于isPermitedAll()方法</div>
              <div><b>port：</b>当请求的URL端口不是8081时，跳转到schemal://serverName:8081?queryString，
                其中schmal是协议http或https等，serverName是你访问的Host，8081是Port端口，queryString是你访问的URL里的?后面的参数</div>
              <div><b>rest：</b>根据请求的方法，相当于/admins/user/**=perms[user:method]，其中method为post，get，delete等</div>
              <div><b>roles：</b>参数可写多个，多个时必须加上引号,且参数之间用逗号分割，如/admins/user/**=roles["admin,guest"]，
                当有多个参数时必须每个参数都通过才算通过，相当于hasAllRoles()方法</div>
              <div><b>ssl：</b>无参,表示安全的URL请求，协议为https</div>
              <div><b>user：</b>无参,表示必须存在用户，当登入操作时不做检查</div>
              <a-divider/>
              <div>注意事项：</div>
              <div><b>user和authc的区别：</b>user表示用户不一定已通过认证，只要曾被Shiro记住过登录状态的用户就可以正常发起请求，比如rememberMe。
                以前的一个用户登录时开启了rememberMe，然后他关闭浏览器，下次再访问时他就是一个user，而不会authc。
              </div>
            </template>
          </a-alert>
        </a-col>
      </a-row>
    </div>

    <a-modal title="创建鉴权规则"
             :maskClosable="false"
             :visible="addVisible"
             @ok="handleAddFilterChain"
             @cancel="() => {this.addVisible = false}">
      <a-form>
        <a-form-item label="目标URL" :labelCol="$style.modalForm.label" :wrapperCol="$style.modalForm.wrapper" required>
          <a-input v-model="filterChain.url" placeholder="请填写目标URL"></a-input>
        </a-form-item>
        <a-form-item label="过滤器" :labelCol="$style.modalForm.label" :wrapperCol="$style.modalForm.wrapper" required>
          <a-input v-model="filterChain.filter" placeholder="请填写过滤器规则"></a-input>
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script>
  import Breadcrumb from "../../../../components/Breadcrumb";
  export default {
    name: "FilterChain",
    components: {Breadcrumb},
    data() {
      return {
        filterChains: [
          {"id":"1055374084029657090","url":"/favicon.ico","filter":"anon","sortCode":1,"createdAt":"2018-10-25T16:22:44","createdBy":null,"updatedAt":"2018-10-25T16:22:44","updatedBy":null},
          {"id":"1055374084163874817","url":"/login","filter":"anon","sortCode":2,"createdAt":"2018-10-25T16:22:44","createdBy":null,"updatedAt":"2018-10-25T16:22:44","updatedBy":null},
          {"id":"1055374084163874818","url":"/css/**.css","filter":"anon","sortCode":3,"createdAt":"2018-10-25T16:22:44","createdBy":null,"updatedAt":"2018-10-25T16:22:44","updatedBy":null},
          {"id":"1055374084218400769","url":"/js/**.js","filter":"anon","sortCode":4,"createdAt":"2018-10-25T16:22:44","createdBy":null,"updatedAt":"2018-10-25T16:22:44","updatedBy":null},
          {"id":"1055374084218400770","url":"/img/**","filter":"anon","sortCode":5,"createdAt":"2018-10-25T16:22:44","createdBy":null,"updatedAt":"2018-10-25T16:22:44","updatedBy":null},
        ],
        loading: false,
        editMode: true,
        editId: '',
        editBtnLoading: false,
        addVisible: false,
        filterChain: {}
      }
    },
    created() {
      this.fetchData();
    },
    methods: {
      fetchData() {
        this.loading = true;
        this.$api.sys.getFilterChainList({orderByAsc: 'sort_code'}).then(res => {
          this.filterChains = res.data;
          this.loading = false;
        }).catch(() => {
          this.loading = false;
        });
      },
      addFilterChain() {
        this.filterChain = {};
        this.addVisible = true;
      },
      handleAddFilterChain() {
        this.$api.sys.addFilterChain(this.filterChain).then(() => {
          this.$message.success(this.$messages.successResult.create);
          this.addVisible = false;
          this.fetchData();
        });
      },
      handleDeleteFilterChain(chainId) {
        this.$api.sys.deleteFilterChain(chainId).then(() => {
          this.$message.success(this.$messages.successResult.delete);
          this.fetchData();
        });
      },
      handleRefresh() {
        this.fetchData();
      },
      toggleEditChain(chain) {
        if (this.showInput(chain.id)) {
          this.editBtnLoading = true;
          this.$api.sys.updateFilterChain(chain.id, chain).then(() => {
            this.$message.success(this.$messages.successResult.edit);
            this.editMode = false;
            this.editId = '';
            this.editBtnLoading = false;
          }).catch(() => {
            this.editMode = false;
            this.editId = '';
            this.editBtnLoading = false;
          });
        } else {
          this.editMode = true;
          this.editId = chain.id;
        }
      },
      showInput(chainId) {
        return this.editMode && this.editId === chainId;
      }
    }
  }
</script>

<style scoped>
  .filter-chain-container {
    height: calc(100vh - 292px);
    overflow-y: auto;
  }
  .toolbar {
    line-height: 3rem;
  }
  .filter-chain-box {
    padding: 8px;
    border: 1px solid #e8e8e8;
    border-radius: 4px;
  }
  .edit-input {
    width: calc(100% - 86px);
  }
</style>