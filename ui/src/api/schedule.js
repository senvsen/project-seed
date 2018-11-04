import request from './request'

const prefix = '/schedule';

export default {
  addJob(jobCreate) {
    return request.post(`${prefix}/job`);
  },
  updateJob(id, jobUpdate) {
    return request.put(`${prefix}/job/${id}`, jobUpdate);
  },
  batchSaveJob(jobUpdateList) {
    return request.put(`${prefix}/job/batch-save`, jobUpdateList);
  },
  deleteJob(id) {
    return request.delete(`${prefix}/job/${id}`);
  },
  batchDeleteJob(ids) {
    return request.put(`${prefix}/job/batch-delete`, ids);
  },
  getJob(id) {
    return request.get(`${prefix}/job/${id}`);
  },
  getJobList(query) {
    return request.post(`${prefix}/job/list`, query);
  },
  getJobPage(page, size, query, ascs, descs) {
    const ascsParam = ascs.join('&ascs=');
    const descsParam = descs.join('&descs=');
    return request.post(`${prefix}/job/page?page=${page}&size=${size}&descs=${descsParam}&ascs=${ascsParam}`, query);
  },
  pauseJob(id) {
    return request.put(`${prefix}/job/${id}/pause`);
  },
  resumeJob(id) {
    return request.put(`${prefix}/job/${id}/resume`);
  }
}