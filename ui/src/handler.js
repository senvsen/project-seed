import api from './api/index'

export default {
  '/user': {
    fetchData: api.auth.getUserPage,
    handleAdd: api.auth.addUser,
    handleEdit: api.auth.updateUser,
    handleDelete: api.auth.deleteUser,
    handleBatchDelete: api.auth.batchDeleteUser,
  },
  '/role': {
    fetchData: api.auth.getRolePage,
    handleAdd: api.auth.addRole,
    handleEdit: api.auth.updateRole,
    handleDelete: api.auth.deleteRole,
    handleBatchDelete: api.auth.batchDeleteRole,
  },
  '/privilege': {
    fetchData: api.auth.getPrivilegePage,
    handleAdd: api.auth.addPrivilege,
    handleEdit: api.auth.updatePrivilege,
    handleDelete: api.auth.deletePrivilege,
    handleBatchDelete: api.auth.batchDeletePrivilege,
  },
  '/department': {
    fetchData: api.auth.getDepartmentPage,
    handleAdd: api.auth.addDepartment,
    handleEdit: api.auth.updateDepartment,
    handleDelete: api.auth.deleteDepartment,
    handleBatchDelete: api.auth.batchDeleteDepartment,
  },
}