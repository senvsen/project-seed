package com.yupaits.generator;

import java.io.File;

/**
 * @author yupaits
 * @date 2018/10/31
 */
public class GeneratorConsts {

    //JDBC配置
    static final String DATABASE_NAME = "test";
    static final String DB_HOST = "localhost";
    static final String JDBC_URL_PATTERN = "jdbc:mysql://%s:3306/%s?useUnicode=true&useSSL=false&characterEncoding=utf8";
    static final String JDBC_USERNAME = "root";
    static final String JDBC_PASSWORD = "sql123";
    static final String JDBC_DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";

    //项目在硬盘的路径
//    public static final String PROJECT_PATH = "D:" + File.separator + "Projects" + File.separator + "project-seed";
    static final String PROJECT_PATH = "E:" + File.separator + "Demo" + File.separator + "project-seed";
    //Java文件路径
    static final String JAVA_PATH = "/src/main/java";
    //资源文件路径
    private static final String RESOURCES_PATH = "/src/main/resources";
    //Mapper xml文件路径
    static final String MAPPER_XML_PATH = RESOURCES_PATH + "/mapper/";
    static final String HTML_TEMPLATES_PATH = RESOURCES_PATH + "/templates/";

    static final String BASE_PACKAGE = "com.yupaits";
    static final String BASE_PACKAGE_PATH = "/com/yupaits/";
    static final String AUTHOR = "yupaits";

    static final String BASE_DTO_CLASS = "com.yupaits.commons.core.BaseDTO";
    static final String DELETED_FIELD_NAME = "deleted";
    static final String VERSION_FIELD_NAME = "version";

    //控制台交互
    static final String REST_CONTROLLER_STYLE_TIP = "是否设置控制器为RESTful风格";
    static final String RELATED_TABLE_TIP = "是否为中间关联表";
    static final String DELETED_COLUMN_TIP = "是否存在逻辑删除deleted字段";
    static final String VERSION_COLUMN_TIP = "是否存在乐观锁version字段";
    static final String TRUE_VAL = "y";

    static final String RESULT_CLASS = "com.yupaits.commons.result.Result";
    static final String RESULT_WRAPPER_CLASS = "com.yupaits.commons.result.ResultWrapper";
    static final String MODEL_WRAPPER_CLASS = "com.yupaits.commons.result.ModelWrapper";
    static final String RESULT_CODE_CLASS = "com.yupaits.commons.result.ResultCode";
    static final String SERIALIZER_CLASS = "com.yupaits.commons.core.serializer.LongSerializer";
    static final String DESERIALIZER_CLASS = "com.yupaits.commons.core.serializer.LongDeserializer";
    static final String VALIDATE_UTILS_CLASS = "com.yupaits.commons.utils.ValidateUtils";
    static final String FOREIGN_ID_CLASS = "com.yupaits.commons.core.identity.ForeignId;";
    static final String RELATED_ID_CLASS = "com.yupaits.commons.core.identity.RelatedId;";
    static final String SERVICE_EXCEPTION_CLASS = "com.yupaits.commons.exceptions.ServiceException;";

    static final String MYBATIS_PLUS_PACKAGE_PREFIX = "com.baomidou.mybatisplus";
}
