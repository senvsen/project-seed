USE test;

DROP TABLE IF EXISTS auth_user;
CREATE TABLE auth_user (
	id BIGINT(18) NOT NULL COMMENT '主键ID',
	username VARCHAR(30) DEFAULT NULL COMMENT '用户名',
	password VARCHAR(50) DEFAULT NULL COMMENT '密码',
	name VARCHAR(30) DEFAULT NULL COMMENT '姓名',
	phone VARCHAR(30) DEFAULT NULL COMMENT '手机号码',
	email VARCHAR(50) DEFAULT NULL COMMENT '邮箱',
	gender TINYINT(4) DEFAULT NULL COMMENT '性别',
	birthday DATE DEFAULT NULL COMMENT '生日',
	avatar VARCHAR(255) DEFAULT NULL COMMENT '头像',
	created_at DATETIME DEFAULT NULL COMMENT '创建时间',
  created_by BIGINT(18) DEFAULT NULL COMMENT '创建人ID',
  updated_at DATETIME DEFAULT NULL COMMENT '更新时间',
	updated_by BIGINT(18) DEFAULT NULL COMMENT '更新人ID',
	enabled TINYINT(1) DEFAULT NULL COMMENT '账户可用',
  deleted TINYINT(1) DEFAULT NULL COMMENT '删除标记',
	PRIMARY KEY (id)
);

DROP TABLE IF EXISTS auth_role;
CREATE TABLE auth_role (
	id BIGINT(18) NOT NULL COMMENT '主键ID',
	role_key VARCHAR(30) DEFAULT NULL COMMENT '角色Key',
	name VARCHAR(30) DEFAULT NULL COMMENT '角色名',
	description VARCHAR(255) DEFAULT NULL COMMENT '角色描述',
	department_id BIGINT(18) DEFAULT NULL COMMENT '所属部门ID',
	created_at DATETIME DEFAULT NULL COMMENT '创建时间',
  created_by BIGINT(18) DEFAULT NULL COMMENT '创建人ID',
  updated_at DATETIME DEFAULT NULL COMMENT '更新时间',
	updated_by BIGINT(18) DEFAULT NULL COMMENT '更新人ID',
  deleted TINYINT(1) DEFAULT NULL COMMENT '删除标记',
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS auth_privilege;
CREATE TABLE auth_privilege (
	id BIGINT(18) NOT NULL COMMENT '主键ID',
	privilege_type TINYINT(4) DEFAULT NULL COMMENT '权限类型',
	privilege_key VARCHAR(30) DEFAULT NULL COMMENT '权限Key',
	name VARCHAR(30) DEFAULT NULL COMMENT '权限名',
	description VARCHAR(255) DEFAULT NULL COMMENT '权限描述',
	created_at DATETIME DEFAULT NULL COMMENT '创建时间',
  created_by BIGINT(18) DEFAULT NULL COMMENT '创建人ID',
  updated_at DATETIME DEFAULT NULL COMMENT '更新时间',
	updated_by BIGINT(18) DEFAULT NULL COMMENT '更新人ID',
  deleted TINYINT(1) DEFAULT NULL COMMENT '删除标记',
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS auth_user_role;
CREATE TABLE auth_user_role (
  user_id BIGINT(18) NOT NULL COMMENT '用户ID',
  role_id BIGINT(18) NOT NULL COMMENT '角色ID',
  created_at DATETIME DEFAULT NULL COMMENT '创建时间',
  created_by BIGINT(18) DEFAULT NULL COMMENT '创建人ID',
  updated_at DATETIME DEFAULT NULL COMMENT '更新时间',
	updated_by BIGINT(18) DEFAULT NULL COMMENT '更新人ID',
  deleted TINYINT(1) DEFAULT NULL COMMENT '删除标记',
  PRIMARY KEY (user_id, role_id)
);

DROP TABLE IF EXISTS auth_role_privilege;
CREATE TABLE auth_role_privilege (
  role_id BIGINT(18) NOT NULL COMMENT '角色ID',
  privilege_id BIGINT(18) NOT NULL COMMENT '权限ID',
  created_at DATETIME DEFAULT NULL COMMENT '创建时间',
  created_by BIGINT(18) DEFAULT NULL COMMENT '创建人ID',
  updated_at DATETIME DEFAULT NULL COMMENT '更新时间',
	updated_by BIGINT(18) DEFAULT NULL COMMENT '更新人ID',
  deleted TINYINT(1) DEFAULT NULL COMMENT '删除标记',
  PRIMARY KEY (role_id, privilege_id)
);

DROP TABLE IF EXISTS auth_department;
CREATE TABLE auth_department (
	id BIGINT(18) NOT NULL COMMENT '部门ID',
	department_key VARCHAR(30) DEFAULT NULL COMMENT '部门Key',
	name VARCHAR(30) DEFAULT NULL COMMENT '部门名称',
	parent_id VARCHAR(30) DEFAULT NULL COMMENT '父级部门ID',
	created_at DATETIME DEFAULT NULL COMMENT '创建时间',
	created_by BIGINT(18) DEFAULT NULL COMMENT '创建人ID',
	updated_at DATETIME DEFAULT NULL COMMENT '更新时间',
	updated_by BIGINT(18) DEFAULT NULL COMMENT '更新人ID',
	deleted TINYINT(1) DEFAULT NULL COMMENT '删除标记',
	PRIMARY KEY (id)
);