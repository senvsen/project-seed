package ${cfg.dtoPackage};

<#list table.importPackages as pkg>
import ${pkg};
</#list>
<#if swagger2>
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
</#if>
<#if entityLombokModel>
import lombok.Data;
import lombok.EqualsAndHashCode;
</#if>

/**
 *
 * @author ${author}
 * @date ${date}
 */
<#if entityLombokModel>
@Data
@EqualsAndHashCode(callSuper = false)
</#if>
<#if swagger2>
@ApiModel(value="${entity}创建DTO", description="${entity}创建对象")
</#if>
public class ${entity}Create extends ${cfg.superDto} {

    private static final long serialVersionUID = 1L;
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>

    <#if field.comment!?length gt 0>
    <#if swagger2>
    @ApiModelProperty(value = "${field.comment}")
    <#else>
    /**
     * ${field.comment}
     */
    </#if>
    </#if>
    private ${field.propertyType} ${field.propertyName};
</#list>
<#------------  END 字段循环遍历  ---------->

    @Override
    @ApiModelProperty(hidden = true)
    public boolean isValid() {
        return false;
    }

}
