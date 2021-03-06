USE seed;

DROP TABLE IF EXISTS sys_filter_chain;
CREATE TABLE sys_filter_chain (
    id BIGINT(19) NOT NULL COMMENT '权限过滤链ID',
    url VARCHAR(255) DEFAULT NULL COMMENT '目标URL',
    filter VARCHAR(30) DEFAULT NULL COMMENT '过滤器名称',
    sort_code INT(10) DEFAULT NULL COMMENT '排序码',
    created_at DATETIME DEFAULT NULL COMMENT '创建时间',
    created_by BIGINT(19) DEFAULT NULL COMMENT '创建人ID',
    updated_at DATETIME DEFAULT NULL COMMENT '更新时间',
    updated_by BIGINT(19) DEFAULT NULL COMMENT '更新人ID',
    deleted TINYINT(1) DEFAULT NULL COMMENT '删除标记',
    PRIMARY KEY (id)
) COMMENT '权限过滤链';

DROP TABLE IF EXISTS sys_store_file;
CREATE TABLE sys_store_file (
    id BIGINT(19) NOT NULL COMMENT '文件ID',
    filename VARCHAR(255) DEFAULT NULL COMMENT '文件名',
    group_name VARCHAR(10) DEFAULT NULL COMMENT '文件存储组',
    path VARCHAR(255) DEFAULT NULL COMMENT '文件路径',
    privacy TINYINT(1) DEFAULT NULL COMMENT '隐私文件标记',
    privacy_code VARCHAR(32) DEFAULT NULL COMMENT '加密串',
    created_at DATETIME DEFAULT NULL COMMENT '创建时间',
    created_by BIGINT(19) DEFAULT NULL COMMENT '创建人ID',
    PRIMARY KEY (id)
) COMMENT '上传文件';