package com.yupaits.batch;

import com.google.common.collect.Maps;
import com.yupaits.auth.entity.*;
import com.yupaits.msg.entity.MessageUser;
import com.yupaits.msg.entity.SystemNotice;
import com.yupaits.msg.entity.Template;
import com.yupaits.schedule.entity.Job;
import com.yupaits.sys.entity.FilterChain;
import com.yupaits.wx.entity.MpAccount;
import com.yupaits.wx.entity.MpAutoReply;
import com.yupaits.wx.entity.MpEventHandler;
import com.yupaits.wx.entity.MpWelcomeMessage;

import java.util.Map;

/**
 * @author yupaits
 * @date 2018/11/26
 */
public class BatchConsts {

    public static final String[] ENTITIES = new String[]{"user", "user_role", "role", "role_privilege", "privilege",
            "department", "system_notice", "message_user", "template", "job", "filter_chain", "mp_account",
            "mp_welcome_message", "mp_auto_reply", "mp_event_handler"};

    public static final Map<String, String> READER_SQL_MAP = Maps.newHashMap();
    public static final Map<String, Class> READER_ROW_BEAN_MAP = Maps.newHashMap();
    public static final Map<String, String[]> WRITER_FIELD_NAMES_MAP = Maps.newHashMap();
    public static final Map<String, String> PROCESSOR_TABLE_MAP = Maps.newHashMap();

    static {
        READER_SQL_MAP.put("user", "select id, username, password, name, phone, email, gender, birthday, avatar, created_at, created_by, updated_at, updated_by, enabled, deleted from auth_user where deleted = 1");
        READER_SQL_MAP.put("user_role", "");
        READER_SQL_MAP.put("role", "");
        READER_SQL_MAP.put("role_privilege", "");
        READER_SQL_MAP.put("privilege", "");
        READER_SQL_MAP.put("department", "");
        READER_SQL_MAP.put("system_notice", "");
        READER_SQL_MAP.put("message_user", "");
        READER_SQL_MAP.put("template", "");
        READER_SQL_MAP.put("job", "");
        READER_SQL_MAP.put("filter_chain", "");
        READER_SQL_MAP.put("mp_account", "");
        READER_SQL_MAP.put("mp_welcome_message", "");
        READER_SQL_MAP.put("mp_auto_reply", "");
        READER_SQL_MAP.put("mp_event_handler", "");

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
        READER_ROW_BEAN_MAP.put("mp_welcome_message", MpWelcomeMessage.class);
        READER_ROW_BEAN_MAP.put("mp_auto_reply", MpAutoReply.class);
        READER_ROW_BEAN_MAP.put("mp_event_handler", MpEventHandler.class);

        WRITER_FIELD_NAMES_MAP.put("user", new String[]{"id", "username", "password", "name", "phone", "email", "gender", "birthday", "avatar", "createdAt", "createdBy", "updatedAt", "updatedBy", "enabled", "deleted"});
        WRITER_FIELD_NAMES_MAP.put("user_role", new String[]{});
        WRITER_FIELD_NAMES_MAP.put("role", new String[]{});
        WRITER_FIELD_NAMES_MAP.put("role_privilege", new String[]{});
        WRITER_FIELD_NAMES_MAP.put("privilege", new String[]{});
        WRITER_FIELD_NAMES_MAP.put("department", new String[]{});
        WRITER_FIELD_NAMES_MAP.put("system_notice", new String[]{});
        WRITER_FIELD_NAMES_MAP.put("message_user", new String[]{});
        WRITER_FIELD_NAMES_MAP.put("template", new String[]{});
        WRITER_FIELD_NAMES_MAP.put("job", new String[]{});
        WRITER_FIELD_NAMES_MAP.put("filter_chain", new String[]{});
        WRITER_FIELD_NAMES_MAP.put("mp_account", new String[]{});
        WRITER_FIELD_NAMES_MAP.put("mp_welcome_message", new String[]{});
        WRITER_FIELD_NAMES_MAP.put("mp_auto_reply", new String[]{});
        WRITER_FIELD_NAMES_MAP.put("mp_event_handler", new String[]{});

        PROCESSOR_TABLE_MAP.put("user", "auth_user");
        PROCESSOR_TABLE_MAP.put("user_role", "");
        PROCESSOR_TABLE_MAP.put("role", "");
        PROCESSOR_TABLE_MAP.put("role_privilege", "");
        PROCESSOR_TABLE_MAP.put("privilege", "");
        PROCESSOR_TABLE_MAP.put("department", "");
        PROCESSOR_TABLE_MAP.put("system_notice", "");
        PROCESSOR_TABLE_MAP.put("message_user", "");
        PROCESSOR_TABLE_MAP.put("template", "");
        PROCESSOR_TABLE_MAP.put("job", "");
        PROCESSOR_TABLE_MAP.put("filter_chain", "");
        PROCESSOR_TABLE_MAP.put("mp_account", "");
        PROCESSOR_TABLE_MAP.put("mp_welcome_message", "");
        PROCESSOR_TABLE_MAP.put("mp_auto_reply", "");
        PROCESSOR_TABLE_MAP.put("mp_event_handler", "");
    }
}
