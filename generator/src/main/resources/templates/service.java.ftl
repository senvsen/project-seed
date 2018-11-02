package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};
<#if cfg.notRelateTable??>
<#else>
import ${cfg.class.relatedId};
</#if>

/**
 * <p>
 * ${table.comment!} 服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

    <#if cfg.notRelateTable??>
    <#else>
    /**
     * 增量保存${table.comment!}关系
     * @param relatedId 关联关系
     * @return 保存结果
     */
    boolean batchSave(RelatedId<Long> relatedId);
    </#if>
}
</#if>
