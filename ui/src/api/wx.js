import request from './request'

const prefix = '/wx';

export default {
  addMpAccount(mpAccountCreate) {
    return request.post(`${prefix}/mp-account`, mpAccountCreate);
  },
  updateMpAccount(id, mpAccountUpdate) {
    return request.put(`${prefix}/mp-account/${id}`, mpAccountUpdate);
  },
  batchSaveMpAccount(mpAccountUpdateList) {
    return request.put(`${prefix}/mp-account/batch-save`, mpAccountUpdateList);
  },
  deleteMpAccount(id) {
    return request.delete(`${prefix}/mp-account/${id}`);
  },
  batchDeleteMpAccount(ids) {
    return request.put(`${prefix}/mp-account/batch-delete`, ids);
  },
  getMpAccount(id) {
    return request.get(`${prefix}/mp-account/${id}`);
  },
  getMpAccountList(query) {
    return request.post(`${prefix}/mp-account/list`, query);
  },
  getMpAccountPage(page, size, query, ascs, descs) {
    const ascsParam = ascs.join('&ascs=');
    const descsParam = descs.join('&descs=');
    return request.post(`${prefix}/mpAccount/page?page=${page}&size=${size}&descs=${descsParam}&ascs=${ascsParam}`, query);
  },
  addMpWelcomeMessage(mpWelcomeMessageCreate) {
    return request.post(`${prefix}/mp-welcome-message`, mpWelcomeMessageCreate);
  },
  updateMpWelcomeMessage(id, mpWelcomeMessageUpdate) {
    return request.put(`${prefix}/mp-welcome-message/${id}`, mpWelcomeMessageUpdate);
  },
  batchSaveMpWelcomeMessage(mpWelcomeMessageUpdateList) {
    return request.put(`${prefix}/mp-welcome-message/batch-save`, mpWelcomeMessageUpdateList);
  },
  deleteMpWelcomeMessage(id) {
    return request.delete(`${prefix}/mp-welcome-message/${id}`);
  },
  batchDeleteMpWelcomeMessage(ids) {
    return request.put(`${prefix}/mp-welcome-message/batch-delete`, ids);
  },
  getMpWelcomeMessage(id) {
    return request.get(`${prefix}/mp-welcome-message/${id}`);
  },
  getMpWelcomeMessageList(query) {
    return request.post(`${prefix}/mp-welcome-message/list`, query);
  },
  getMpWelcomeMessagePage(page, size, query, ascs, descs) {
    const ascsParam = ascs.join('&ascs=');
    const descsParam = descs.join('&descs=');
    return request.post(`${prefix}/mp-welcome-message/page?page=${page}&size=${size}&descs=${descsParam}&ascs=${ascsParam}`, query);
  },
  addMpAutoReply(mpAutoReplyCreate) {
    return request.post(`${prefix}/mp-auto-reply`, mpAutoReplyCreate);
  },
  updateMpAutoReply(id, mpAutoReplyUpdate) {
    return request.put(`${prefix}/mp-auto-reply/${id}`, mpAutoReplyUpdate);
  },
  batchSaveMpAutoReply(mpAutoReplyUpdateList) {
    return request.put(`${prefix}/mp-auto-reply/batch-save`, mpAutoReplyUpdateList);
  },
  deleteMpAutoReply(id) {
    return request.delete(`${prefix}/mp-auto-reply/${id}`);
  },
  batchDeleteMpAutoReply(ids) {
    return request.put(`${prefix}/mp-auto-reply/batch-delete`, ids);
  },
  getMpAutoReply(id) {
    return request.get(`${prefix}/mp-auto-reply/${id}`);
  },
  getMpAutoReplyList(query) {
    return request.post(`${prefix}/mp-auto-reply/list`, query);
  },
  getMpAutoReplyPage(page, size, query, ascs, descs) {
    const ascsParam = ascs.join('&ascs=');
    const descsParam = descs.join('&descs=');
    return request.post(`${prefix}/mp-auto-reply/page?page=${page}&size=${size}&descs=${descsParam}&ascs=${ascsParam}`, query);
  },
  addMpEventHandler(mpEventHandlerCreate) {
    return request.post(`${prefix}/mp-event-handler`, mpEventHandlerCreate);
  },
  updateMpEventHandler(id, mpEventHandlerUpdate) {
    return request.put(`${prefix}/mp-event-handler/${id}`, mpEventHandlerUpdate);
  },
  batchSaveMpEventHandler(mpEventHandlerUpdateList) {
    return request.put(`${prefix}/mp-event-handler/batch-save`, mpEventHandlerUpdateList);
  },
  deleteMpEventHandler(id) {
    return request.delete(`${prefix}/mp-event-handler/${id}`);
  },
  batchDeleteMpEventHandler(ids) {
    return request.put(`${prefix}/mp-event-handler/batch-delete`, ids);
  },
  getMpEventHandler(id) {
    return request.get(`${prefix}/mp-event-handler/${id}`);
  },
  getMpEventHandlerList(query) {
    return request.post(`${prefix}/mp-event-handler/list`, query);
  },
  getMpEventHandlerPage(page, size, query, ascs, descs) {
    const ascsParam = ascs.join('&ascs=');
    const descsParam = descs.join('&descs=');
    return request.post(`${prefix}/mp-event-handler/page?page=${page}&size=${size}&descs=${descsParam}&ascs=${ascsParam}`, query);
  },
}