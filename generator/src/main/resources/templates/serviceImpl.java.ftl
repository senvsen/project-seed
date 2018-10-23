package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;
<#if cfg.notRelateTable??>
<#else>
import ${cfg.class.relatedId};
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
</#if>

/**
 * <p>
 * ${table.comment!} 服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if cfg.notRelateTable??>
<#else>
@Slf4j
</#if>
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

    <#if cfg.notRelateTable??>
    <#else>
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean batchSave(RelatedId relatedId) {
        remove(new QueryWrapper<${entity}>()
                    .eq(relatedId.getFirstId().getFieldName(), relatedId.getFirstId().getValue())
                    .notIn(relatedId.getSecondIds().getFieldName(), relatedId.getSecondIds().getValues()));
        List<${entity}> add${entity}List = Lists.newArrayList();
        relatedId.getSecondIds().getValues().forEach(secondId -> {
            ${entity} ${cfg.obj.entity} = getOne(new QueryWrapper<${entity}>()
                    .eq("deleted", false)
                    .eq(relatedId.getFirstId().getFieldName(), relatedId.getFirstId().getValue())
                    .eq(relatedId.getSecondIds().getFieldName(), secondId));
            if (${cfg.obj.entity} == null) {
                ${cfg.obj.entity} = new ${entity}();
                try {
                    ${entity}.class.getDeclaredField(relatedId.getFirstId().getFieldName()).set(${cfg.obj.entity}, relatedId.getFirstId().getValue());
                    ${entity}.class.getDeclaredField(relatedId.getSecondIds().getFieldName()).set(${cfg.obj.entity}, secondId);
                    add${entity}List.add(${cfg.obj.entity});
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    log.warn("创建${table.comment!}关系出错, 参数: {}[{}], {}[{}]", relatedId.getFirstId().getFieldName(),
                            relatedId.getFirstId().getValue(), relatedId.getSecondIds().getFieldName(), secondId, e);
                }
            }
        });
        return add${entity}List.size() == 0 || saveBatch(add${entity}List);
    }
    </#if>
}
</#if>
