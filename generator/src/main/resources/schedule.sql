USE test;

DROP TABLE IF EXISTS schedule_job;
CREATE TABLE schedule_job (
  id BIGINT(19) NOT NULL COMMENT '定时任务ID',
  class_name VARCHAR(255) DEFAULT NULL COMMENT '任务执行类路径',
  cron_expression VARCHAR(30) DEFAULT NULL COMMENT 'CRON表达式',
  job_name VARCHAR(30) DEFAULT NULL COMMENT '任务名称',
  job_group VARCHAR(30) DEFAULT NULL COMMENT '任务组',
  trigger_name VARCHAR(30) DEFAULT NULL COMMENT '触发器名称',
  trigger_group VARCHAR(30) DEFAULT NULL COMMENT '触发器组',
  paused TINYINT(1) DEFAULT NULL COMMENT '已暂停',
  description VARCHAR(30) DEFAULT NULL COMMENT '定时任务描述',
  created_at DATETIME DEFAULT NULL COMMENT '创建时间',
  created_by BIGINT(19) DEFAULT NULL COMMENT '创建人ID',
  updated_at DATETIME DEFAULT NULL COMMENT '更新时间',
  updated_by BIGINT(19) DEFAULT NULL COMMENT '更新人ID',
  deleted TINYINT(1) DEFAULT NULL COMMENT '删除标记',
  PRIMARY KEY (id)
) COMMENT '定时任务';
