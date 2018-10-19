package ${cfg.package.dto};

<#list cfg.fieldPackages as pkg>
import ${pkg};
</#list>
<#if cfg.hasDeserializer>
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ${cfg.class.deserializer};
</#if>
import ${cfg.class.superDto};
<#if swagger2>
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
</#if>
<#if entityLombokModel>
import lombok.Data;
import lombok.EqualsAndHashCode;
</#if>

/**
 * <p>
 * ${cfg.className.create}
 * </p>
 *
 * @author ${author}
 * @date ${date}
 */
<#if entityLombokModel>
@Data
@EqualsAndHashCode(callSuper = false)
</#if>
<#if swagger2>
@ApiModel(value = "${cfg.className.create}", description = "${cfg.className.create}对象")
</#if>
public class ${cfg.className.create} extends ${cfg.className.superDto} {

    private static final long serialVersionUID = 1L;
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    <#if (!field.keyFlag && !field.fill??)>

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
    @JsonDeserialize(using = ${cfg.className.deserializer}.class)
        </#if>
    private ${field.propertyType} ${field.propertyName};
    </#if>
</#list>
<#------------  END 字段循环遍历  ---------->

    @Override
    @ApiModelProperty(hidden = true)
    public boolean isValid() {
        return true;
    }

}
