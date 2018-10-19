package ${cfg.package.vo};

<#list cfg.fieldPackages as pkg>
import ${pkg};
</#list>
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ${cfg.class.serializer};
<#if swagger2>
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
</#if>
<#if entityLombokModel>
import lombok.Data;
</#if>

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * ${table.comment!}VO
 * </p>
 *
 * @author ${author}
 * @date ${date}
 */
<#if entityLombokModel>
@Data
</#if>
<#if swagger2>
@ApiModel(value = "${entity}VO", description = "${entity}VO对象")
</#if>
public class ${entity}VO implements Serializable {

    private static final long serialVersionUID = 1L;
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    <#if (logicDeleteFieldName!"") != field.name>

        <#if field.comment!?length gt 0>
            <#if swagger2>
    @ApiModelProperty(value = "${field.comment}")
        <#else>
        /**
         * ${field.comment}
         */
            </#if>
        </#if>
        <#if field.propertyType == "Long">
    @JsonSerialize(using = ${cfg.className.serializer}.class)
        </#if>
    private ${field.propertyType} ${field.propertyName};
    </#if>
</#list>
<#------------  END 字段循环遍历  ---------->
}
