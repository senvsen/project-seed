import request from './request'

const prefix = '/auth';

export default {
  addUser(userCreate) {
    return request.post(`${prefix}/user`, userCreate);
  },
  updateUser(id, userUpdate) {
    return request.put(`${prefix}/user/${id}`, userUpdate);
  },
  batchSaveUser(userUpdateList) {
    return request.put(`${prefix}/user/batch-save`, userUpdateList);
  },
  deleteUser(id) {
    return request.delete(`${prefix}/user/${id}`);
  },
  batchDeleteUser(ids) {
    return request.put(`${prefix}/user/batch-delete`, ids);
  },
  getUser(id) {
    return request.get(`${prefix}/user/${id}`);
  },
  getUserList(query) {
    return request.post(`${prefix}/user/list`, query);
  },
  getUserPage(page, size, query, ascs, descs) {
    const ascsParam = ascs.join('&ascs=');
    const descsParam = descs.join('&descs=');
    return request.post(`${prefix}/user/page?page=${page}&size=${size}&descs=${descsParam}&ascs=${ascsParam}`, query);
  },
  addRole(roleCreate) {
    return request.post(`${prefix}/role`, roleCreate);
  },
  updateRole(id, roleUpdate) {
    return request.put(`${prefix}/role/${id}`, roleUpdate);
  },
  batchSaveRole(roleUpdateList) {
    return request.put(`${prefix}/role/batch-save`, roleUpdateList);
  },
  deleteRole(id) {
    return request.delete(`${prefix}/role/${id}`);
  },
  batchDeleteRole(ids) {
    return request.put(`${prefix}/role/batch-delete`, ids);
  },
  getRole(id) {
    return request.get(`${prefix}/role/${id}`);
  },
  getRoleList(query) {
    return request.post(`${prefix}/role/list`, query);
  },
  getRolePage(page, size, query, ascs, descs) {
    const ascsParam = ascs.join('&ascs=');
    const descsParam = descs.join('&descs=');
    return request.post(`${prefix}/role/page?page=${page}&size=${size}&descs=${descsParam}&ascs=${ascsParam}`, query);
  },
  addPrivilege(privilegeCreate) {
    return request.post(`${prefix}/user`, privilegeCreate);
  },
  updatePrivilege(id, privilegeUpdate) {
    return request.put(`${prefix}/privilege/${id}`, privilegeUpdate);
  },
  batchSavePrivilege(privilegeUpdateList) {
    return request.put(`${prefix}/privilege/batch-save`, privilegeUpdateList);
  },
  deletePrivilege(id) {
    return request.delete(`${prefix}/privilege/${id}`);
  },
  batchDeletePrivilege(ids) {
    return request.put(`${prefix}/privilege/batch-delete`, ids);
  },
  getPrivilege(id) {
    return request.get(`${prefix}/privilege/${id}`);
  },
  getPrivilegeList(query) {
    return request.post(`${prefix}/privilege/list`, query);
  },
  getPrivilegePage(page, size, query, ascs, descs) {
    const ascsParam = ascs.join('&ascs=');
    const descsParam = descs.join('&descs=');
    return request.post(`${prefix}/privilege/page?page=${page}&size=${size}&descs=${descsParam}&ascs=${ascsParam}`, query);
  },
  addDepartment(departmentCreate) {
    return request.post(`${prefix}/department`, departmentCreate);
  },
  updateDepartment(id, departmentUpdate) {
    return request.put(`${prefix}/department/${id}`, departmentUpdate);
  },
  batchSaveDepartment(departmentUpdateList) {
    return request.put(`${prefix}/department/batch-save`, departmentUpdateList);
  },
  deleteDepartment(id) {
    return request.delete(`${prefix}/department/${id}`);
  },
  batchDeleteDepartment(ids) {
    return request.put(`${prefix}/department/batch-delete`, ids);
  },
  getDepartment(id) {
    return request.get(`${prefix}/department/${id}`);
  },
  getDepartmentList(query) {
    return request.post(`${prefix}/department/list`, query);
  },
  getDepartmentPage(page, size, query, ascs, descs) {
    const ascsParam = ascs.join('&ascs=');
    const descsParam = descs.join('&descs=');
    return request.post(`${prefix}/department/page?page=${page}&size=${size}&descs=${descsParam}&ascs=${ascsParam}`, query);
  },
}