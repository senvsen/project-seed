<template>
  <div class="page">
    <breadcrumb/>
    <a-row :gutter="16">
      <a-col :span="8">
        <div class="page-content">
          <a-row class="page-header">
            <span class="header-title"><a-icon type="team" class="mr-1"/>部门</span>
            <a-button size="small" icon="plus" class="ml-2" @click="addDepartment(0)">主部门</a-button>
            <div class="is-pulled-right">
              <span v-if="selectedDepartment.id">
                <a-button size="small" icon="plus" @click="addDepartment(selectedDepartment.id)">子部门</a-button>
                <a-button size="small" icon="edit" class="ml-1" @click="editDepartment">编辑</a-button>
                <a-popconfirm title="确定删除选中的部门吗？" trigger="click" placement="top" @confirm="handleDeleteDepartment"
                              :okText="$messages.operation.confirmText" :cancelText="$messages.operation.cancelText">
                  <a-button size="small" icon="delete" class="ml-1">删除</a-button>
                </a-popconfirm>
              </span>
            </div>
          </a-row>
          <a-spin :spinning="departmentLoading">
            <div class="container">
              <department :departments="departments"
                          :selected-department="selectedDepartment"
                          @toggleSelect="(dep, index) => toggleSelectDepartment(dep, index)"/>
            </div>
          </a-spin>
        </div>
      </a-col>
      <a-col :span="8">
        <div class="page-content">
          <a-row class="page-header">
            <span class="header-title"><a-icon type="user" class="mr-1"/>角色</span>
            <a-button size="small" icon="plus" class="ml-2" v-if="selectedDepartment.id" @click="addRole">创建</a-button>
            <div class="is-pulled-right">
              <span v-if="selectedRole.id">
                <a-button size="small" icon="edit" @click="editRole">编辑</a-button>
                <a-popconfirm title="确定删除选中的角色吗？" trigger="click" placement="top" @confirm="handleDeleteRole"
                              :okText="$messages.operation.confirmText" :cancelText="$messages.operation.cancelText">
                  <a-button size="small" icon="delete" class="ml-1">删除</a-button>
                </a-popconfirm>
              </span>
            </div>
          </a-row>
          <a-spin :spinning="roleLoading">
            <div class="container">
              <div v-for="(role, index) in roles" :key="role.id" class="item-box mb-1"
                   :title="role.description"
                   :class="{'is-active': selectedRole.id === role.id}"
                   @click="toggleSelectRole(role, index)">
                <a-row>
                  <a-col :span="10">
                    <a-icon type="user" class="mr-1"/> {{role.roleKey}}
                  </a-col>
                  <a-col :span="14">
                    {{role.name}}
                  </a-col>
                </a-row>
              </div>
            </div>
          </a-spin>
        </div>
      </a-col>
      <a-col :span="8">
        <div class="page-content">
          <a-row class="page-header">
            <span class="header-title"><a-icon type="solution" class="mr-1"/>权限</span>
            <a-button size="small" icon="plus" class="ml-2" @click="addPrivilege">创建</a-button>
            <a-button size="small" icon="save" class="ml-1" v-if="selectedRole.id" @click="saveRolePrivileges">保存</a-button>
            <div class="is-pulled-right" v-if="selectedPrivilege.id">
              <a-button size="small" icon="edit" class="ml-1" @click="editPrivilege">编辑</a-button>
              <a-popconfirm title="确定删除选中的权限吗？" trigger="click" placement="top" @confirm="handleDeletePrivilege"
                            :okText="$messages.operation.confirmText" :cancelText="$messages.operation.cancelText">
                <a-button size="small" icon="delete" class="ml-1">删除</a-button>
              </a-popconfirm>
            </div>
          </a-row>
          <a-spin :spinning="privilegeLoading">
            <div class="container">
              <div v-for="(privilege, index) in privileges" :key="privilege.id" class="item-box mb-1"
                   :title="privilege.description"
                   :class="{'is-active': selectedPrivilege.id === privilege.id}"
                   @click="toggleSelectPrivilege(privilege, index)">
                <a-row>
                  <a-col :span="12">
                    <a-checkbox :checked="privilegeChecked(privilege.id)" v-if="selectedRole.id"
                                @click.stop="toggleCheckPrivilege(privilege.id)"></a-checkbox>
                    <a-icon type="solution" class="ml-1 mr-1"/> {{privilege.privilegeKey}}
                  </a-col>
                  <a-col :span="12">
                    <a-tag>{{$messages.enums.privilegeType[privilege.privilegeType]}}</a-tag> {{privilege.name}}
                  </a-col>
                </a-row>
              </div>
            </div>
          </a-spin>
        </div>
      </a-col>
    </a-row>

    <a-modal :title="modal.title"
             :maskClosable="false"
             :visible="modal.visible"
             @ok="modal.ok"
             @cancel="() => {modal.visible = false}">
      <component :is="modal.formComponent"></component>
    </a-modal>
  </div>
</template>

<script>
  import Breadcrumb from "../../../../components/Breadcrumb";
  import DepartmentForm from "../../../../components/form/DepartmentForm";
  import RoleForm from "../../../../components/form/RoleForm";
  import PrivilegeForm from "../../../../components/form/PrivilegeForm";
  import Department from "../../../../components/Department";
  export default {
    name: "RBAC",
    components: {Department, Breadcrumb},
    data() {
      return {
        departments: [],
        roles: [],
        privileges: [
          {"id":"1058276280144551938","privilegeType":2,"privilegeKey":"privilege:add","name":"添加权限","description":"添加权限信息","createdAt":"2018-11-02T16:35:02","createdBy":"1058256010751074305","updatedAt":"2018-11-02T18:05:21","updatedBy":"1058256010751074305"},
          {"id":"1058300620688949249","privilegeType":1,"privilegeKey":"sidebar:user","name":"用户管理","description":"侧边栏用户管理菜单项","createdAt":"2018-11-02T18:11:45","createdBy":"1058256010751074305","updatedAt":"2018-11-02T18:11:45","updatedBy":"1058256010751074305"}
        ],
        departmentLoading: false,
        roleLoading: false,
        privilegeLoading: false,
        selectedDepartment: {},
        selectedRole: {},
        selectedPrivilege: {},
        checkedPrivilegeIds: [],
        modal: {
          title: '',
          visible: false,
          formComponent: undefined,
          ok: () => {}
        },
        selectedIndex: {
          department: -1,
          role: -1,
          privilege: -1
        }
      }
    },
    mounted() {
      this.fetchData();
    },
    methods: {
      fetchData() {
        this.fetchDepartments('0');
        this.fetchPrivileges();
      },
      fetchDepartments(parentId, department) {
        this.departmentLoading = true;
        this.$api.auth.getDepartmentList({parentId: parentId}).then(res => {
          if (parentId === '0') {
            this.departments = res.data;
          } else {
            this.$set(department, 'subDepartments', res.data);
          }
          this.departmentLoading = false;
        }).catch(() => {
          this.departmentLoading = false;
        });
      },
      fetchRoles() {
        this.roleLoading = true;
        this.$api.auth.getRoleList({departmentId: this.selectedDepartment.id}).then(res => {
          this.roles = res.data;
          this.roleLoading = false;
        }).catch(() => {
          this.roleLoading = false;
        });
      },
      fetchPrivileges() {
        this.privilegeLoading = true;
        this.$api.auth.getPrivilegeList({}).then(res => {
          this.privileges = res.data;
          this.privilegeLoading = false;
        }).catch(() => {
          this.privilegeLoading = false;
        });
      },
      fetchRolePrivileges() {
        this.$api.auth.getRolePrivilegeList({fieldName: 'roleId', value: this.selectedRole.id}).then(res => {
          const privilegeIds = [];
          res.data.forEach(rolePrivilege => {
            privilegeIds.push(rolePrivilege.privilegeId);
          });
          this.checkedPrivilegeIds = privilegeIds;
        });
      },
      addDepartment(parentId) {
        this.$store.dispatch('setRecord', {parentId: parentId});
        this.modal = {
          title: '添加部门',
          visible: true,
          formComponent: DepartmentForm,
          ok: this.handleAddDepartment
        };
      },
      addRole() {
        this.$store.dispatch('setRecord', {departmentId: this.selectedDepartment.id});
        this.modal = {
          title: '创建角色',
          visible: true,
          formComponent: RoleForm,
          ok: this.handleAddRole
        };
      },
      addPrivilege() {
        this.$store.dispatch('setRecord', {});
        this.modal = {
          title: '创建权限',
          visible: true,
          formComponent: PrivilegeForm,
          ok: this.handleAddPrivilege
        };
      },
      editDepartment() {
        this.$store.dispatch('setRecord', JSON.parse(JSON.stringify(this.selectedDepartment)));
        this.modal = {
          title: '编辑部门',
          visible: true,
          formComponent: DepartmentForm,
          ok: this.handleEditDepartment
        };
      },
      editRole() {
        this.$store.dispatch('setRecord', JSON.parse(JSON.stringify(this.selectedRole)));
        this.modal = {
          title: '编辑角色',
          visible: true,
          formComponent: RoleForm,
          ok: this.handleEditRole
        };
      },
      editPrivilege() {
        this.$store.dispatch('setRecord', JSON.parse(JSON.stringify(this.selectedPrivilege)));
        this.modal = {
          title: '编辑权限',
          visible: true,
          formComponent: PrivilegeForm,
          ok: this.handleEditPrivilege
        };
      },
      handleAddDepartment() {
        this.$api.auth.addDepartment(this.$store.getters.record).then(() => {
          this.$message.success(this.$messages.successResult.create);
          this.modal.visible = false;
          if (this.$store.getters.record.parentId === 0) {
            this.fetchDepartments('0');
          } else {
            this.fetchDepartments(this.selectedDepartment.id, this.selectedDepartment);
          }
        });
      },
      handleAddRole() {
        this.$api.auth.addRole(this.$store.getters.record).then(() => {
          this.$message.success(this.$messages.successResult.create);
          this.modal.visible = false;
          this.fetchRoles();
          this.$store.dispatch('setRolesFetched', false);
        })
      },
      handleAddPrivilege() {
        this.$api.auth.addPrivilege(this.$store.getters.record).then(() => {
          this.$message.success(this.$messages.successResult.create);
          this.modal.visible = false;
          this.fetchPrivileges();
        });
      },
      handleEditDepartment() {
        const department = this.$store.getters.record;
        this.$api.auth.updateDepartment(department.id, department).then(() => {
          this.$message.success(this.$messages.successResult.edit);
          this.selectedDepartment = department;
          if (department.parentId === '0') {
            this.$set(this.departments, this.selectedIndex.department, department);
          } else {
            const parentDepartment = this.getDepartmentById(this.departments, department.parentId);
            this.$set(parentDepartment.subDepartments, this.selectedIndex.department, department);
          }
          this.modal.visible = false;
        });
      },
      handleEditRole() {
        const role = this.$store.getters.record;
        this.$api.auth.updateRole(role.id, role).then(() => {
          this.$message.success(this.$messages.successResult.edit);
          this.selectedRole = role;
          this.$set(this.roles, this.selectedIndex.role, role);
          this.modal.visible = false;
          this.$store.dispatch('setRolesFetched', false);
        });
      },
      handleEditPrivilege() {
        const privilege = this.$store.getters.record;
        this.$api.auth.updatePrivilege(privilege.id, privilege).then(() => {
          this.$message.success(this.$messages.successResult.edit);
          this.selectedPrivilege = privilege;
          this.$set(this.privileges, this.selectedIndex.privilege, privilege);
          this.modal.visible = false;
        });
      },
      toggleSelectDepartment(department, index) {
        if (department.id === this.selectedDepartment.id) {
          this.selectedDepartment = {};
          this.roles = [];
          this.selectedIndex.department = -1;
        } else {
          this.selectedDepartment = department;
          this.fetchRoles();
          this.fetchDepartments(department.id, department);
          this.selectedIndex.department = index;
        }
        this.selectedRole = {};
        this.checkedPrivilegeIds = [];
      },
      toggleSelectRole(role, index) {
        if (role.id === this.selectedRole.id) {
          this.selectedRole = {};
          this.checkedPrivilegeIds = [];
          this.selectedIndex.role = -1;
        } else {
          this.selectedRole = role;
          this.fetchRolePrivileges();
          this.selectedIndex.role = index;
        }
      },
      toggleSelectPrivilege(privilege, index) {
        if (privilege.id === this.selectedPrivilege.id) {
          this.selectedPrivilege = {};
          this.selectedIndex.privilege = -1;
        } else {
          this.selectedPrivilege = privilege;
          this.selectedIndex.privilege = index;
        }
      },
      saveRolePrivileges() {
        this.$api.auth.batchSaveRolePrivileges({
          firstId: {fieldName: 'roleId', value: this.selectedRole.id},
          secondIds: {fieldName: 'privilegeId', values: this.checkedPrivilegeIds}
        }).then(() => {
          this.$message.success(this.$messages.successResult.save);
        });
      },
      privilegeChecked(privilegeId) {
        return this.checkedPrivilegeIds.indexOf(privilegeId) !== -1;
      },
      toggleCheckPrivilege(privilegeId) {
        if (this.privilegeChecked(privilegeId)) {
          this.checkedPrivilegeIds.splice(this.checkedPrivilegeIds.indexOf(privilegeId), 1);
        } else {
          this.checkedPrivilegeIds.push(privilegeId);
        }
      },
      handleDeleteDepartment() {
        this.$api.auth.deleteDepartment(this.selectedDepartment.id).then(() => {
          this.$message.success(this.$messages.successResult.delete);
          const parentDepartment = this.getDepartmentById(this.departments, this.selectedDepartment.parentId);
          this.fetchDepartments(this.selectedDepartment.parentId, parentDepartment);
          this.selectedDepartment = {};
          this.roles = [];
          this.selectedRole = {};
          this.checkedPrivilegeIds = [];
        });
      },
      handleDeleteRole() {
        this.$api.auth.deleteRole(this.selectedRole.id).then(() => {
          this.$message.success(this.$messages.successResult.delete);
          this.selectedRole = {};
          this.checkedPrivilegeIds = [];
          this.fetchRoles();
          this.$store.dispatch('setRolesFetched', false);
        });
      },
      handleDeletePrivilege() {
        this.$api.auth.deletePrivilege(this.selectedPrivilege.id).then(() => {
          this.$message.success(this.$messages.successResult.delete);
          if (this.privilegeChecked(this.selectedPrivilege.id)) {
            this.checkedPrivilegeIds.splice(this.checkedPrivilegeIds.indexOf(this.selectedPrivilege.id), 1);
          }
          this.selectedPrivilege = {};
          this.fetchPrivileges();
        });
      },
      getDepartmentById(departments, id) {
        for (let i = 0; i < departments.length; i++) {
          if (departments[i].id === id) {
            return departments[i];
          } else if (departments[i].subDepartments instanceof Array) {
            return this.getDepartmentById(departments[i].subDepartments, id);
          }
        }
      }
    }
  }
</script>

<style scoped>
  .page-header {
    line-height: 2rem;
  }
  .header-title {
    font-size: 18px;
    font-weight: bold;
  }
  .container {
    height: calc(100vh - 292px);
    width: 100%;
    overflow-y: auto;
  }
  .item-box {
    padding: 6px;
    border: 1px solid #e8e8e8;
    border-radius: 4px;
  }
  .item-box:hover {
    cursor: pointer;
    background: #f5f5f5;
  }
  .is-active {
    color: #f5f5f5;
    background: #1890ff;
  }
  .is-active:hover {
    background: #40a9ff;
  }
</style>