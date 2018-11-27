package com.yupaits.batch.consts;

import com.google.common.collect.Maps;
import com.yupaits.auth.controller.*;
import com.yupaits.auth.entity.*;
import com.yupaits.msg.controller.SystemNoticeController;
import com.yupaits.msg.controller.TemplateController;
import com.yupaits.msg.entity.MessageUser;
import com.yupaits.msg.entity.SystemNotice;
import com.yupaits.msg.entity.Template;
import com.yupaits.schedule.controller.JobController;
import com.yupaits.schedule.entity.Job;
import com.yupaits.sys.controller.FilterChainController;
import com.yupaits.sys.entity.FilterChain;
import com.yupaits.wx.controller.MpAccountController;
import com.yupaits.wx.controller.MpAutoReplyController;
import com.yupaits.wx.controller.MpEventHandlerController;
import com.yupaits.wx.entity.MpAccount;
import com.yupaits.wx.entity.MpAutoReply;
import com.yupaits.wx.entity.MpEventHandler;

import java.util.Map;

/**
 * @author yupaits
 * @date 2018/11/26
 */
public class MigrationDataConsts {

    public static final String[] ENTITIES = new String[]{"user", "user_role", "role", "role_privilege", "privilege",
            "department", "system_notice", "message_user", "template", "job", "filter_chain", "mp_account",
            "mp_auto_reply", "mp_event_handler"};

    public static final Map<String, String> READER_SQL_MAP = Maps.newHashMap();
    public static final Map<String, Class> READER_ROW_BEAN_MAP = Maps.newHashMap();
    public static final Map<String, String[]> WRITER_FIELD_NAMES_MAP = Maps.newHashMap();
    public static final Map<String, String> ENTITY_TABLE_MAP = Maps.newHashMap();
    public static final Map<String, Class> ENTITY_CONTROLLER_MAP = Maps.newHashMap();

    static {
        READER_SQL_MAP.put("user", "select id, username, password, name, phone, email, gender, birthday, avatar, created_at, created_by, updated_at, updated_by, enabled, deleted from auth_user where deleted = 1");
        READER_SQL_MAP.put("user_role", "select id, user_id, role_id, created_at, created_by, updated_at, updated_by, deleted from auth_user_role where deleted = 1");
        READER_SQL_MAP.put("role", "select id, role_key, name, description, department_id, created_at, created_by, updated_at, updated_by, deleted from auth_role where deleted = 1");
        READER_SQL_MAP.put("role_privilege", "select id, role_id, privilege_id, created_at, created_by, updated_at, updated_by, deleted from auth_role_privilege where deleted = 1");
        READER_SQL_MAP.put("privilege", "select id, privilege_type, privilege_key, name, description, created_at, created_by, updated_at, updated_by, deleted from auth_privilege where deleted = 1");
        READER_SQL_MAP.put("department", "select id, department_key, name, parent_id, created_at, created_by, updated_at, updated_by, deleted from auth_department where deleted = 1");
        READER_SQL_MAP.put("system_notice", "select id, msg_level, msg_content, start_time, end_time, created_at, created_by, updated_at, updated_by, deleted from msg_system_notice where deleted = 1");
        READER_SQL_MAP.put("message_user", "select id, message_id, user_id, need_remove, created_at, created_by, send_at, deleted from msg_message_user where deleted = 1");
        READER_SQL_MAP.put("template", "select id, name, msg_type, template_pattern, fill_fields, created_at, created_by, updated_at, updated_by, deleted from msg_template where deleted = 1");
        READER_SQL_MAP.put("job", "select id, class_name, cron_expression, job_name, job_group, trigger_name, trigger_group, paused, description, created_at, created_by, updated_at, updated_by, deleted from schedule_job where deleted = 1");
        READER_SQL_MAP.put("filter_chain", "select id, url, filter, sort_code, created_at, created_by, updated_at, updated_by, deleted from sys_filter_chain where deleted = 1");
        READER_SQL_MAP.put("mp_account", "select id, account_name, related_type, related_id, app_name, app_id, app_secret, token, encoding_aes_key, created_at, created_by, updated_at, updated_by, deleted from wx_mp_account where deleted = 1");
        READER_SQL_MAP.put("mp_auto_reply", "select id, account_id, keywords, match_rule, reply, created_at, created_by, updated_at, updated_by, deleted from wx_mp_auto_reply where deleted = 1");
        READER_SQL_MAP.put("mp_event_handler", "select id, account_id, event_type, handler_class, created_at, created_by, updated_at, updated_by, deleted from wx_mp_event_handler where deleted = 1");

        READER_ROW_BEAN_MAP.put("user", User.class);
        READER_ROW_BEAN_MAP.put("user_role", UserRole.class);
        READER_ROW_BEAN_MAP.put("role", Role.class);
        READER_ROW_BEAN_MAP.put("role_privilege", RolePrivilege.class);
        READER_ROW_BEAN_MAP.put("privilege", Privilege.class);
        READER_ROW_BEAN_MAP.put("department", Department.class);
        READER_ROW_BEAN_MAP.put("system_notice", SystemNotice.class);
        READER_ROW_BEAN_MAP.put("message_user", MessageUser.class);
        READER_ROW_BEAN_MAP.put("template", Template.class);
        READER_ROW_BEAN_MAP.put("job", Job.class);
        READER_ROW_BEAN_MAP.put("filter_chain", FilterChain.class);
        READER_ROW_BEAN_MAP.put("mp_account", MpAccount.class);
        READER_ROW_BEAN_MAP.put("mp_auto_reply", MpAutoReply.class);
        READER_ROW_BEAN_MAP.put("mp_event_handler", MpEventHandler.class);

        WRITER_FIELD_NAMES_MAP.put("user", new String[]{"id", "username", "password", "name", "phone", "email", "gender", "birthday", "avatar", "createdAt", "createdBy", "updatedAt", "updatedBy", "enabled", "deleted"});
        WRITER_FIELD_NAMES_MAP.put("user_role", new String[]{"id", "userId", "roleId", "createdAt", "createdBy", "updatedAt", "updatedBy", "deleted"});
        WRITER_FIELD_NAMES_MAP.put("role", new String[]{"id", "roleKey", "name", "description", "departmentId", "createdAt", "createdBy", "updatedAt", "updatedBy", "deleted"});
        WRITER_FIELD_NAMES_MAP.put("role_privilege", new String[]{"id", "roleId", "privilegeId", "createdAt", "createdBy", "updatedAt", "updatedBy", "deleted"});
        WRITER_FIELD_NAMES_MAP.put("privilege", new String[]{"id", "privilegeType", "privilegeKey", "name", "description", "createdAt", "createdBy", "updatedAt", "updatedBy", "deleted"});
        WRITER_FIELD_NAMES_MAP.put("department", new String[]{"id", "departmentKey", "name", "parentId", "createdAt", "createdBy", "updatedAt", "updatedBy", "deleted"});
        WRITER_FIELD_NAMES_MAP.put("system_notice", new String[]{"id", "msgLevel", "msgContent", "startTime", "endTime", "createdAt", "createdBy", "updatedAt", "updatedBy", "deleted"});
        WRITER_FIELD_NAMES_MAP.put("message_user", new String[]{"id", "messageId", "userId", "needRemove", "createdAt", "createdBy", "sendAt", "deleted"});
        WRITER_FIELD_NAMES_MAP.put("template", new String[]{"id", "name", "msgType", "templatePattern", "fillFields", "createdAt", "createdBy", "updatedAt", "updatedBy", "deleted"});
        WRITER_FIELD_NAMES_MAP.put("job", new String[]{"id", "className", "cronExpression", "jobName", "jobGroup", "triggerName", "triggerGroup", "paused", "description", "createdAt", "createdBy", "updatedAt", "updatedBy", "deleted"});
        WRITER_FIELD_NAMES_MAP.put("filter_chain", new String[]{"id", "url", "filter", "sortCode", "createdAt", "createdBy", "updatedAt", "updatedBy", "deleted"});
        WRITER_FIELD_NAMES_MAP.put("mp_account", new String[]{"id", "accountName", "relatedType", "relatedId", "appName", "appId", "appSecret", "token", "encodingAesKey", "createdAt", "createdBy", "updatedAt", "updatedBy", "deleted"});
        WRITER_FIELD_NAMES_MAP.put("mp_auto_reply", new String[]{"id", "accountId", "keywords", "matchRule", "reply", "createdAt", "createdBy", "updatedAt", "updatedBy", "deleted"});
        WRITER_FIELD_NAMES_MAP.put("mp_event_handler", new String[]{"id", "accountId", "eventType", "handlerClass", "createdAt", "createdBy", "updatedAt", "updatedBy", "deleted"});

        ENTITY_TABLE_MAP.put("user", "auth_user");
        ENTITY_TABLE_MAP.put("user_role", "auth_user_role");
        ENTITY_TABLE_MAP.put("role", "auth_role");
        ENTITY_TABLE_MAP.put("role_privilege", "auth_role_privilege");
        ENTITY_TABLE_MAP.put("privilege", "auth_privilege");
        ENTITY_TABLE_MAP.put("department", "auth_department");
        ENTITY_TABLE_MAP.put("system_notice", "msg_system_notice");
        ENTITY_TABLE_MAP.put("message_user", "msg_message_user");
        ENTITY_TABLE_MAP.put("template", "msg_template");
        ENTITY_TABLE_MAP.put("job", "schedule_job");
        ENTITY_TABLE_MAP.put("filter_chain", "sys_filter_chain");
        ENTITY_TABLE_MAP.put("mp_account", "wx_mp_account");
        ENTITY_TABLE_MAP.put("mp_auto_reply", "wx_mp_auto_reply");
        ENTITY_TABLE_MAP.put("mp_event_handler", "wx_mp_event_handler");

        ENTITY_CONTROLLER_MAP.put("user", UserController.class);
        ENTITY_CONTROLLER_MAP.put("user_role", UserRoleController.class);
        ENTITY_CONTROLLER_MAP.put("role", RoleController.class);
        ENTITY_CONTROLLER_MAP.put("role_privilege", RolePrivilegeController.class);
        ENTITY_CONTROLLER_MAP.put("privilege", PrivilegeController.class);
        ENTITY_CONTROLLER_MAP.put("department", DepartmentController.class);
        ENTITY_CONTROLLER_MAP.put("system_notice", SystemNoticeController.class);
        ENTITY_CONTROLLER_MAP.put("template", TemplateController.class);
        ENTITY_CONTROLLER_MAP.put("job", JobController.class);
        ENTITY_CONTROLLER_MAP.put("filter_chain", FilterChainController.class);
        ENTITY_CONTROLLER_MAP.put("mp_account", MpAccountController.class);
        ENTITY_CONTROLLER_MAP.put("mp_auto_reply", MpAutoReplyController.class);
        ENTITY_CONTROLLER_MAP.put("mp_event_handler", MpEventHandlerController.class );
    }
}
