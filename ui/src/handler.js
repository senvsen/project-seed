import api from './api/index'

export default {
  '/user': {
    fetchData: api.auth.getUserPage,
    handleAdd: api.auth.addUser,
    handleEdit: api.auth.updateUser,
    handleDelete: api.auth.deleteUser,
    handleBatchDelete: api.auth.batchDeleteUser,
  },
}