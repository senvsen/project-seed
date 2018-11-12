import api from './api/index'

export default {
  '/user': {
    fetchData: api.auth.getUserPage,
    handleAdd: api.auth.addUser,
    handleEdit: api.auth.updateUser,
    handleDelete: api.auth.deleteUser,
    handleBatchDelete: api.auth.batchDeleteUser,
  },
  '/schedule': {
    fetchData: api.schedule.getJobPage,
    handleAdd: api.schedule.addJob,
    handleEdit: api.schedule.updateJob,
    handleDelete: api.schedule.deleteJob,
    handleBatchDelete: api.schedule.batchDeleteJob,
  },
  '/mp-account': {
    fetchData: api.wx.getMpAccountPage,
    handleAdd: api.wx.addMpAccount,
    handleEdit: api.wx.updateMpAccount,
    handleDelete: api.wx.deleteMpAccount,
    handleBatchDelete: api.wx.batchDeleteMpAccount,
  }
}