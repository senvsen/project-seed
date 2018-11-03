<template>
  <span>
    <a-popconfirm title="确定禁用该用户吗？" trigger="click" placement="top" @confirm="handleDisabled"
                  :okText="$messages.operation.confirmText" :cancelText="$messages.operation.cancelText" v-if="rowData.enabled">
      <a-button size="small" class="mr-1">禁用</a-button>
    </a-popconfirm>
    <a-popconfirm title="确定解禁该用户吗？" trigger="click" placement="top" @confirm="handleEnabled"
                  :okText="$messages.operation.confirmText" :cancelText="$messages.operation.cancelText" v-else>
      <a-button size="small" class="mr-1">解禁</a-button>
    </a-popconfirm>
    <a-button size="small" @click="assignRoles">分配角色</a-button>

    <a-modal title="分配角色"
             :maskClosable="false"
             :visible="assignVisible"
             @ok="handleSave"
             @cancel="assignVisible = false">
      <a-input-search v-model="keyword" placeholder="请填写查找关键字" class="mb-3" @search="handleSearchRoles"></a-input-search>
      <a-checkbox v-for="role in roles" :key="role.id" class="role-item mr-1" :checked="roleChecked(role.id)" @click="toggleCheckRole(role.id)">
        <a-icon type="user"/> {{role.roleKey}} {{role.name}}
      </a-checkbox>
    </a-modal>
  </span>
</template>

<script>
  export default {
    name: "UserOpt",
    props: {
      rowData: {
        type: Object,
        required: true
      }
    },
    computed: {
      user() {
        return this.rowData;
      }
    },
    data() {
      return {
        assignVisible: false,
        roles: [
          {"id":"1058264270262611969","roleKey":"admin","name":"系统管理员","description":"系统管理员，拥有最高管理权限","departmentId":"1058256237444837377","createdAt":"2018-11-02T15:47:18","createdBy":"1058256010751074305","updatedAt":"2018-11-02T16:26:36","updatedBy":"1058256010751074305"}
        ],
        checkedRoleIds: [],
        keyword: ''
      }
    },
    methods: {
      fetchRoles() {
        this.$api.auth.getRoleList({keyword: this.keyword}).then(res => {
          this.roles = res.data;
        });
      },
      fetchUserRoles() {
        this.$api.auth.getUserRoleList({fieldName: 'userId', value: this.user.id}).then(res => {
          const roleIds = [];
          res.data.forEach(userRole => {
            roleIds.push(userRole.roleId);
          });
          this.checkedRoleIds = roleIds;
        });
      },
      handleSearchRoles() {
        this.fetchRoles();
      },
      handleDisabled() {
        this.user.enabled = false;
        this.$api.auth.updateUser(this.user.id, this.user).then(() => {
          this.$message.success(this.$messages.successResult.update);
          this.$emit('updated');
        });
      },
      handleEnabled() {
        this.user.enabled = true;
        this.$api.auth.updateUser(this.user.id, this.user).then(() => {
          this.$message.success(this.$messages.successResult.update);
          this.$emit('updated');
        });
      },
      assignRoles() {
        this.fetchUserRoles();
        this.assignVisible = true;
      },
      roleChecked(roleId) {
        return this.checkedRoleIds.indexOf(roleId) !== -1;
      },
      toggleCheckRole(roleId) {
        if (this.roleChecked(roleId)) {
          this.checkedRoleIds.splice(this.checkedRoleIds.indexOf(roleId), 1);
        } else {
          this.checkedRoleIds.push(roleId);
        }
      },
      handleSave() {
        this.$api.auth.batchSaveUserRoles({
          firstId: {fieldName: 'userId', value: this.user.id},
          secondIds: {fieldName: 'roleId', values: this.checkedRoleIds}
        }).then(() => {
          this.$message.success(this.$messages.successResult.save);
          this.assignVisible = false;
        });
      }
    }
  }
</script>

<style scoped>
  .role-item {
    padding: 6px;
    border: 1px solid #e8e8e8;
    border-radius: 4px;
  }
</style>