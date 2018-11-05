<template>
  <div class="page">
    <breadcrumb/>
    <a-row>
      <a-col :span="18" :offset="3">
        <div class="page-content">
          <a-row class="toolbar">
            <a-button icon="plus" class="mr-1" @click="addFilterChain">创建</a-button>
            <a-button icon="reload" @click="handleRefresh">刷新</a-button>
            <span class="is-pulled-right">
              <a-button icon="book" @click="infoVisible = true">帮助</a-button>
            </span>
          </a-row>
          <a-spin :spinning="loading">
            <div class="filter-chain-container">
              <div v-for="(chain, index) in filterChains" :key="chain.id" class="filter-chain-box mb-1">
                <a-row>
                  <a-col :span="10">
                    <span><a-icon type="link"/> 目标URL：</span>
                    <a-input size="small" v-model="filterChain.url" v-if="showInput(chain.id)" class="edit-input"></a-input>
                    <b v-else>{{chain.url}}</b>
                  </a-col>
                  <a-col :span="10">
                    <span><a-icon type="filter"/> 过滤器：</span>
                    <a-input size="small" v-model="filterChain.filter" v-if="showInput(chain.id)" class="edit-input"></a-input>
                    <b v-else>{{chain.filter}}</b>
                  </a-col>
                  <a-col :span="4">
                    <template v-if="showInput(chain.id)">
                      <a-button size="small" icon="check" class="mr-1" @click="handleEdit">确认</a-button>
                      <a-button size="small" icon="close" @click="handleCancelEdit">取消</a-button>
                    </template>
                    <template v-else>
                      <a-row>
                        <a-col :span="12">
                          <a-button size="small" icon="arrow-up" class="mr-1" v-if="!isFirst(index)" @click="moveUp(index)"></a-button>
                          <a-button size="small" icon="arrow-down" v-if="!isLast(index)" @click="moveDown(index)"></a-button>
                        </a-col>
                        <a-col :span="12">
                          <div class="is-pulled-right">
                            <a-button size="small" icon="edit" class="mr-1" @click="editChain(chain)" ></a-button>
                            <a-popconfirm title="确定删除该条过滤规则吗？" trigger="click" placement="top" @confirm="handleDeleteFilterChain(chain.id)"
                                          :okText="$messages.operation.confirmText" :cancelText="$messages.operation.cancelText">
                              <a-button size="small" icon="delete"></a-button>
                            </a-popconfirm>
                          </div>
                        </a-col>
                      </a-row>
                    </template>
                  </a-col>
                </a-row>
              </div>
            </div>
          </a-spin>
        </div>
      </a-col>
    </a-row>

    <a-modal title="帮助信息"
             :visible="infoVisible"
             :footer="null"
             @cancel="infoVisible = false">
      <div>过滤器说明：</div>
      <div><b>anon：</b>无参，表示可匿名使用,可以理解为匿名用户或游客</div>
      <div><b>authc：</b>无参，表示需认证才能使用</div>
      <div><b>authcBasic：</b>无参，表示httpBasic认证</div>
      <div><b>logout：</b>无参，表示注销登录</div>
      <div><b>perms：</b>参数可写多个，多参时必须加上引号，且参数之间用逗号分割，如 <code>/admins/user/**=perms["user:add:*,user:modify:*"]</code>，
        当有多个参数时必须每个参数都通过才算通过，相当于isPermitedAll()方法</div>
      <div><b>port：</b>当请求的URL端口不是8081时，跳转到 <code>schemal://serverName:8081?queryString</code>，
        其中schmal是协议http或https等，serverName是你访问的Host，8081是Port端口，queryString是你访问的URL里的?后面的参数</div>
      <div><b>rest：</b>根据请求的方法，相当于 <code>/admins/user/**=perms[user:method]</code>，其中method为post，get，delete等</div>
      <div><b>roles：</b>参数可写多个，多个时必须加上引号，且参数之间用逗号分割，如 <code>/admins/user/**=roles["admin,guest"]</code>，
        当有多个参数时必须每个参数都通过才算通过，相当于hasAllRoles()方法</div>
      <div><b>ssl：</b>无参,表示安全的URL请求，协议为https</div>
      <div><b>user：</b>无参,表示必须存在用户，当登入操作时不做检查</div>
      <a-divider/>
      <div>注意事项：</div>
      <div><b>user和authc的区别：</b>user表示用户不一定已通过认证，只要曾被Shiro记住过登录状态的用户就可以正常发起请求，比如rememberMe。
        以前的一个用户登录时开启了rememberMe，然后他关闭浏览器，下次再访问时他就是一个user，而不会authc。
      </div>
    </a-modal>

    <a-modal title="创建鉴权规则"
             :maskClosable="false"
             :visible="addVisible"
             @ok="handleAddFilterChain"
             @cancel="addVisible = false">
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
        filterChains: [],
        loading: false,
        infoVisible: false,
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
        this.handleCancelEdit();
        this.fetchData();
      },
      editChain(chain) {
        this.filterChain = JSON.parse(JSON.stringify(chain));
        this.editMode = true;
        this.editId = chain.id;
      },
      handleEdit() {
        this.editBtnLoading = true;
        this.$api.sys.updateFilterChain(this.filterChain.id, this.filterChain).then(() => {
          this.$message.success(this.$messages.successResult.edit);
          this.handleCancelEdit();
        }).catch(() => {
          this.handleCancelEdit();
        });
      },
      handleCancelEdit() {
        this.editMode = false;
        this.editId = '';
        this.editBtnLoading = false;
      },
      showInput(chainId) {
        return this.editMode && this.editId === chainId;
      },
      isFirst(index) {
        return index === 0;
      },
      isLast(index) {
        return index === this.filterChains.length - 1;
      },
      moveUp(index) {
        this.handleSwap(index, index - 1);
      },
      moveDown(index) {
        this.handleSwap(index, index + 1);
      },
      handleSwap(index1, index2) {
        let filterChains = this.filterChains;
        const tempSortCode = filterChains[index2].sortCode;
        filterChains[index2].sortCode = filterChains[index1].sortCode;
        filterChains[index1].sortCode = tempSortCode;
        this.$api.sys.batchSaveFilterChain([filterChains[index1], filterChains[index2]]).then(() => {
          this.$set(filterChains, index1, filterChains.splice(index2, 1, filterChains[index1])[0]);
          this.filterChains = filterChains;
        });
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
    width: calc(100% - 90px);
  }
</style>