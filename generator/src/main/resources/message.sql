USE test;

DROP TABLE IF EXISTS msg_system_notice;
CREATE TABLE msg_system_notice (
    id BIGINT(19) NOT NULL COMMENT '系统公告ID',
    msg_level TINYINT(4) DEFAULT NULL COMMENT '公告等级',
    msg_content VARCHAR(1000) DEFAULT NULL COMMENT '公告内容',
    start_time DATETIME DEFAULT NULL COMMENT '开始时间',
    end_time DATETIME DEFAULT NULL COMMENT '结束时间',
    created_at DATETIME DEFAULT NULL COMMENT '创建时间',
    created_by BIGINT(19) DEFAULT NULL COMMENT '创建人ID',
    updated_at DATETIME DEFAULT NULL COMMENT '更新时间',
    updated_by BIGINT(19) DEFAULT NULL COMMENT '更新人ID',
    deleted TINYINT(1) DEFAULT NULL COMMENT '删除标记',
    PRIMARY KEY (id)
) COMMENT '系统公告';

DROP TABLE IF EXISTS msg_notification;
CREATE TABLE msg_notification (
    id BIGINT(19) NOT NULL COMMENT '通知ID',
    operator BIGINT(10) DEFAULT NULL COMMENT '操作者ID',
    action TINYINT(4) DEFAULT NULL COMMENT '动作',
    target_id BIGINT(19) DEFAULT NULL COMMENT '目标对象ID',
    target_type TINYINT(4) DEFAULT NULL COMMENT '目标对象类型',
    target_owner BIGINT(19) DEFAULT NULL COMMENT '目标对象所有者',
    send_at DATETIME DEFAULT NULL COMMENT '发送时间',
    has_read TINYINT(1) DEFAULT NULL COMMENT '已读标记',
    PRIMARY KEY (id)
) COMMENT '通知';

DROP TABLE IF EXISTS msg_message;
CREATE TABLE msg_message (
    id BIGINT(19) NOT NULL COMMENT '消息ID',
    msg_type TINYINT(4) DEFAULT NULL COMMENT '消息类型',
    use_template TINYINT(1) DEFAULT NULL COMMENT '使用消息模板',
    msg_template_id BIGINT(19) DEFAULT NULL COMMENT '消息模板ID',
    payload VARCHAR(1000) DEFAULT NULL COMMENT '模板参数',
    content VARCHAR(1000) DEFAULT NULL COMMENT '消息内容',
    PRIMARY KEY (id)
) COMMENT '消息';

DROP TABLE IF EXISTS msg_message_user;
CREATE TABLE msg_message_user (
    id BIGINT(19) NOT NULL COMMENT '主键ID',
    message_id BIGINT(19) NOT NULL COMMENT '消息ID',
    user_id BIGINT(19) NOT NULL COMMENT '目标用户ID',
    need_remove TINYINT(1) DEFAULT NULL COMMENT '清除原消息标记',
    created_at DATETIME DEFAULT NULL COMMENT '创建时间',
    created_by BIGINT(19) DEFAULT NULL COMMENT '创建人ID',
    send_at DATETIME DEFAULT NULL COMMENT '发送时间',
    deleted TINYINT(1) DEFAULT NULL COMMENT '删除标记',
    PRIMARY KEY (id)
) COMMENT '消息-用户';

DROP TABLE IF EXISTS msg_template;
CREATE TABLE msg_template (
    id BIGINT(19) NOT NULL COMMENT '消息模板ID',
    name VARCHAR(30) DEFAULT NULL COMMENT '模板名称',
    msg_type TINYINT(4) DEFAULT NULL COMMENT '消息类型',
    template_pattern VARCHAR(1000) DEFAULT NULL COMMENT '模板格式',
    fill_fields VARCHAR(500) DEFAULT NULL COMMENT '模板填充字段',
    created_at DATETIME DEFAULT NULL COMMENT '创建时间',
    created_by BIGINT(19) DEFAULT NULL COMMENT '创建人ID',
    updated_at DATETIME DEFAULT NULL COMMENT '更新时间',
    updated_by BIGINT(19) DEFAULT NULL COMMENT '更新人ID',
    deleted TINYINT(1) DEFAULT NULL COMMENT '删除标记',
    PRIMARY KEY (id)
) COMMENT '消息模板';