import request from './request'

const prefix = '/sys';

export default {
  addFilterChain(filterChainCreate) {
    return request.post(`${prefix}/filter-chain`, filterChainCreate);
  },
  updateFilterChain(id, filterChainUpdate) {
    return request.put(`${prefix}/filter-chain/${id}`, filterChainUpdate);
  },
  batchSaveFilterChain(filterChainUpdateList) {
    return request.put(`${prefix}/filter-chain/batch-save`, filterChainUpdateList);
  },
  deleteFilterChain(id) {
    return request.delete(`${prefix}/filter-chain/${id}`);
  },
  batchDeleteFilterChain(ids) {
    return request.put(`${prefix}/filter-chain/batch-delete`, ids);
  },
  getFilterChain(id) {
    return request.get(`${prefix}/filter-chain/${id}`);
  },
  getFilterChainList(query) {
    return request.post(`${prefix}/filter-chain/list`, query);
  },
  getFilterChainPage(page, size, query, ascs, descs) {
    const ascsParam = ascs.join('&ascs=');
    const descsParam = descs.join('&descs=');
    return request.post(`${prefix}/filter-chain/page?page=${page}&size=${size}&descs=${descsParam}&ascs=${ascsParam}`, query);
  },
}