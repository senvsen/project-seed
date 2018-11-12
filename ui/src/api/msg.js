import request from './request'

const prefix = '/msg';

export default {
  addMessage(messageCreate) {
    return request.post(`${prefix}/message`, messageCreate);
  },
  deleteMessage(id) {
    return request.delete(`${prefix}/message/${id}`);
  },
  batchDeleteMessage(ids) {
    return request.put(`${prefix}/message/batch-delete`, ids);
  },
  getMessage(id) {
    return request.get(`${prefix}/message/${id}`);
  },
  getMessageList(query) {
    return request.post(`${prefix}/message/list`, query);
  },
  getMessagePage(page, size, query, ascs, descs) {
    const ascsParam = ascs.join('&ascs=');
    const descsParam = descs.join('&descs=');
    return request.post(`${prefix}/message/page?page=${page}&size=${size}&descs=${descsParam}&ascs=${ascsParam}`, query);
  },
  addTemplate(templateCreate) {
    return request.post(`${prefix}/template`, templateCreate);
  },
  updateTemplate(id, templateUpdate) {
    return request.put(`${prefix}/template/${id}`, templateUpdate);
  },
  batchSaveTemplate(templateUpdateList) {
    return request.put(`${prefix}/template/batch-save`, templateUpdateList);
  },
  deleteTemplate(id) {
    return request.delete(`${prefix}/template/${id}`);
  },
  batchDeleteTemplate(ids) {
    return request.put(`${prefix}/template/batch-delete`, ids);
  },
  getTemplate(id) {
    return request.get(`${prefix}/template/${id}`);
  },
  getTemplateList(query) {
    return request.post(`${prefix}/template/list`, query);
  },
  getTemplatePage(page, size, query, ascs, descs) {
    const ascsParam = ascs.join('&ascs=');
    const descsParam = descs.join('&descs=');
    return request.post(`${prefix}/template/page?page=${page}&size=${size}&descs=${descsParam}&ascs=${ascsParam}`, query);
  },
  addSystemNotice(systemNoticeCreate) {
    return request.post(`${prefix}/system-notice`, systemNoticeCreate);
  },
  updateSystemNotice(id, systemNoticeUpdate) {
    return request.put(`${prefix}/system-notice/${id}`, systemNoticeUpdate);
  },
  batchSaveSystemNotice(systemNoticeUpdateList) {
    return request.put(`${prefix}/system-notice/batch-save`, systemNoticeUpdateList);
  },
  deleteSystemNotice(id) {
    return request.delete(`${prefix}/system-notice/${id}`);
  },
  batchDeleteSystemNotice(ids) {
    return request.put(`${prefix}/system-notice/batch-delete`, ids);
  },
  getSystemNotice(id) {
    return request.get(`${prefix}/system-notice/${id}`);
  },
  getSystemNoticeList(query) {
    return request.post(`${prefix}/system-notice/list`, query);
  },
  getSystemNoticePage(page, size, query, ascs, descs) {
    const ascsParam = ascs.join('&ascs=');
    const descsParam = descs.join('&descs=');
    return request.post(`${prefix}/system-notice/page?page=${page}&size=${size}&descs=${descsParam}&ascs=${ascsParam}`, query);
  },
}