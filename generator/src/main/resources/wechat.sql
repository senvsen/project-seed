USE test;

DROP TABLE IF EXISTS wx_mp_account;
CREATE TABLE wx_mp_account (
  id BIGINT(19) NOT NULL COMMENT '公众号账户ID',
  account_name VARCHAR(50) DEFAULT NULL UNIQUE COMMENT '账户名称',
  related_type TINYINT(4) DEFAULT NULL COMMENT '关联类型',
  related_id VARCHAR(36) DEFAULT NULL COMMENT '关联ID',
  app_name VARCHAR(30) DEFAULT NULL COMMENT '公众号名称',
  app_id VARCHAR(18) DEFAULT NULL UNIQUE COMMENT '公众号AppId',
  app_secret VARCHAR(32) DEFAULT NULL COMMENT '公众号Secret',
  token VARCHAR(20) DEFAULT NULL COMMENT '公众号Token',
  encoding_aes_key VARCHAR(43) DEFAULT NULL COMMENT '公众号EncodingAesKey',
	created_at DATETIME DEFAULT NULL COMMENT '创建时间',
	created_by BIGINT(19) DEFAULT NULL COMMENT '创建人ID',
	updated_at DATETIME DEFAULT NULL COMMENT '更新时间',
	updated_by BIGINT(19) DEFAULT NULL COMMENT '更新人ID',
	deleted TINYINT(1) DEFAULT NULL COMMENT '删除标记',
	PRIMARY KEY (id)
) COMMENT '微信公众号账户';

DROP TABLE IF EXISTS wx_mp_welcome_message;
CREATE TABLE wx_mp_welcome_message (
  id BIGINT(19) NOT NULL COMMENT '欢迎语ID',
  account_id BIGINT(19) NOT NULL UNIQUE COMMENT '公众号账户ID',
  active TINYINT(1) DEFAULT NULL COMMENT '启用标记',
  message VARCHAR(500) DEFAULT NULL COMMENT '欢迎语内容',
	created_at DATETIME DEFAULT NULL COMMENT '创建时间',
	created_by BIGINT(19) DEFAULT NULL COMMENT '创建人ID',
	updated_at DATETIME DEFAULT NULL COMMENT '更新时间',
	updated_by BIGINT(19) DEFAULT NULL COMMENT '更新人ID',
	PRIMARY KEY (id)
) COMMENT '公众号欢迎语';

DROP TABLE IF EXISTS wx_mp_auto_reply;
CREATE TABLE wx_mp_auto_reply (
  id BIGINT(19) NOT NULL COMMENT '自动回复ID',
  account_id BIGINT(19) NOT NULL COMMENT '公众号账户ID',
  keywords VARCHAR(200) DEFAULT NULL COMMENT '关键词',
  match_rule TINYINT(4) DEFAULT NULL COMMENT '匹配规则',
  reply VARCHAR(500) DEFAULT NULL COMMENT '回复内容',
	created_at DATETIME DEFAULT NULL COMMENT '创建时间',
	created_by BIGINT(19) DEFAULT NULL COMMENT '创建人ID',
	updated_at DATETIME DEFAULT NULL COMMENT '更新时间',
	updated_by BIGINT(19) DEFAULT NULL COMMENT '更新人ID',
	deleted TINYINT(1) DEFAULT NULL COMMENT '删除标记',
	PRIMARY KEY (id)
) COMMENT '公众号自动回复';

DROP TABLE IF EXISTS wx_mp_event_handler;
CREATE TABLE wx_mp_event_handler (
  id BIGINT(19) NOT NULL COMMENT '事件处理ID',
  account_id BIGINT(19) NOT NULL COMMENT '公众号账户ID',
  event_type VARCHAR(100) DEFAULT NULL COMMENT '事件类型',
  handler_class VARCHAR(255) DEFAULT NULL COMMENT '处理类路径',
	created_at DATETIME DEFAULT NULL COMMENT '创建时间',
	created_by BIGINT(19) DEFAULT NULL COMMENT '创建人ID',
	updated_at DATETIME DEFAULT NULL COMMENT '更新时间',
	updated_by BIGINT(19) DEFAULT NULL COMMENT '更新人ID',
	deleted TINYINT(1) DEFAULT NULL COMMENT '删除标记',
	PRIMARY KEY (id)
) COMMENT '公众号事件处理';