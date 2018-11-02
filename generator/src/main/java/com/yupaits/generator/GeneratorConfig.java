package com.yupaits.generator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

import static com.yupaits.generator.GeneratorConsts.AUTHOR;

/**
 * 代码生成器配置
 * @author yupaits
 * @date 2018/10/25
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "GeneratorConfig", description = "代码生成器配置")
public class GeneratorConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "MySQL地址（IP或者域名）")
    private String dbHost;

    @ApiModelProperty(value = "MySQL用户")
    private String dbUsername;

    @ApiModelProperty(value = "MySQL密码")
    private String dbPassword;

    @ApiModelProperty(value = "数据库名称")
    private String database;

    @ApiModelProperty(value = "项目路径")
    private String projectPath;

    @ApiModelProperty(value = "开发人员")
    private String developer = AUTHOR;

    @ApiModelProperty(value = "模块名称")
    private String moduleName;

    @ApiModelProperty(value = "数据表名")
    private String tableName;

    @ApiModelProperty(value = "是否为中间关联表")
    private boolean relatedTable = false;

    @ApiModelProperty(value = "是否设置控制器为RESTful风格")
    private boolean restful = true;

    @ApiModelProperty(value = "是否存在逻辑删除deleted字段")
    private Boolean hasLogicDeleteField = true;

    @ApiModelProperty(value = "是否存在乐观锁version字段")
    private Boolean hasVersionField = false;

    @ApiModelProperty(hidden = true)
    public boolean isValid() {
        return !StringUtils.isAnyBlank(dbHost, dbUsername, dbPassword, database, projectPath, moduleName, tableName);
    }
}
