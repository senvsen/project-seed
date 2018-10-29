import api from './api/index'

const config = {
  '/user': {
    fetchData() {
      return api.auth.getUserPage;
    },
    handleAdd() {
      return api.auth.getUserPage;
    },
    handleEdit() {

    },
    handleDelete() {},
    handleBatchDelete() {},
  }
};

export default config